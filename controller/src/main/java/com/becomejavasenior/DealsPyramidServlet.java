package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@WebServlet("/dealspyramid")
public class DealsPyramidServlet extends HttpServlet {
    private final static Logger logger = LogManager.getLogger(DealsPyramidServlet.class);
    private final static String nextJSP = "/jsp/dealspyramid.jsp";
    private DaoFactory dao;

    public static boolean isBetween(LocalDate created, LocalDate startTime, LocalDate endTime) {
        logger.info("Compare dates");
        logger.info(startTime + " --- " + created + " --- " + endTime);
        return created.compareTo(startTime) > 0 && created.compareTo(endTime) <= 0;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        try {
            GenericDao<DealStatus> statusDao = dao.getDao(DealStatus.class);
            GenericDao<Filter> filterDao = dao.getDao(Filter.class);
            GenericDao<Contact> contactDao = dao.getDao(Contact.class);

            String action = req.getParameter("action");
            if (action.equals("savefilter")) {
                Filter filter = new Filter();
                filter.setName(req.getParameter("name"));
                String periodName = req.getParameter("when");
                filter.setType(FilterPeriod.valueOf(periodName));
                if (periodName.equals("PERIOD")) {
                    SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd");
                    filter.setDate_from(new Timestamp(format.parse((req.getParameter("date_from"))).getTime()));
                    filter.setDate_to(new Timestamp(format.parse((req.getParameter("date_to"))).getTime()));
                }
                DealStatus status = statusDao.read(Integer.parseInt(req.getParameter("phase")));
                filter.setStatus(status);

                String managerParam = req.getParameter("managers");
                if (managerParam != null) {
                    Contact manager = contactDao.read(Integer.parseInt(managerParam));
                    filter.setManager(manager);
                }

                String taskType = req.getParameter("tasks");
                filter.setTaskType(FilterTaskType.valueOf(taskType));

                filter.setTags(req.getParameter("tags"));

                filterDao.create(filter);
                resp.sendRedirect("/dealspyramid");
            } else if (action.equals("applyfilter")) {
                resp.sendRedirect("/dealspyramid?selectedfilter=" + req.getParameter("selectedfilter"));
            }

        } catch (DataBaseException e) {
            logger.error("Error while getting new Filter");
            logger.catching(e);
        } catch (ParseException e) {
            logger.error("Error while parsing date");
            logger.catching(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("selectedfilter");
        logger.info(String.format("Selected filter: %s", filter));

        try {
            dao = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            logger.error("Error while getting DAO Factory");
            logger.catching(e);
        }

        try {
            GenericDao<Deal> dealDao = dao.getDao(Deal.class);
            GenericDao<DealStatus> statusDao = dao.getDao(DealStatus.class);
            GenericDao<Filter> filterDao = dao.getDao(Filter.class);
            GenericDao<Contact> contactDao = dao.getDao(Contact.class);

            List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
            List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));
            List<Contact> contacts = contactDao.readAll();
            List<Filter> filters = filterDao.readAll();
            List<DealStatus> statuses = statusDao.readAll();
            Collections.sort(statuses);

            List<Deal> deals = dealDao.readAll();
            deals = applyFilter(deals, filter);

            SortedMap<DealStatus, List<Deal>> dealsToStatus = new TreeMap<>();
            for (DealStatus status : statuses) {
                dealsToStatus.put(status, new ArrayList<>());
                deals.stream().filter(deal -> deal.getStatus().equals(status)).forEach(deal1 -> dealsToStatus.get(status).add(deal1));
            }


            req.setAttribute("filterperiod", filterPeriods);
            req.setAttribute("filtertask", filterTaskTypes);
            req.setAttribute("managers", contacts);
            req.setAttribute("deals_map", dealsToStatus);
            req.setAttribute("filters", filters);

        } catch (DataBaseException e) {
            logger.error("Error while getting DAO");
            logger.catching(e);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(req, resp);
        logger.info(String.format("REDIRECTING TO %s", nextJSP));
    }

    private List<Deal> applyFilter(List<Deal> deals, String filterName) {
        if (filterName == null || filterName.equals("null")) {
            return deals;
        }
        logger.info("Applying filter: " + filterName);
        switch (filterName) {
            case "open":
                return deals.stream().filter(deal -> deal.getDateWhenDealClose() == null)
                        .collect(Collectors.toList());
            case "my":
                return deals; //TODO
            case "success":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & deal.getStatus().getName().equals("SUCCESS"))
                        .collect(Collectors.toList());
            case "fail":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & deal.getStatus().getName().equals("CLOSED AND NOT IMPLEMENTED"))
                        .collect(Collectors.toList());
            case "notask":
                return deals.stream()
                        .filter(deal -> deal.getTasks().isEmpty())
                        .collect(Collectors.toList());
            case "expired":
                return deals.stream()
                        .filter(deal -> (deal.getTasks()
                                .stream().filter(task -> task.getDueTime().before(new Date()))
                                .collect(Collectors.toList())) != null)
                        .collect(Collectors.toList());
            case "deleted":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & deal.getStatus().getName().equals("DELETED"))
                        .collect(Collectors.toList());
        }

        try {
            GenericDao<Filter> filterDao = dao.getDao(Filter.class);
            Filter filter = filterDao.read(Integer.parseInt(filterName));

            deals = deals.stream().filter(deal -> deal.getStatus().equals(filter.getStatus()))
                    .collect(Collectors.toList());

            switch (filter.getType()) {
                case TODAY:
                    logger.info("TODAY");
                    deals = deals.stream()
                            .filter(deal -> deal.getDateCreated().compareTo(new Date()) == 0)
                            .collect(Collectors.toList());
                    break;
                case FOR_THREE_DAYS:
                    logger.info("FOR_THREE_DAYS");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusDays(3), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case WEEK:
                    logger.info("WEEK");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusDays(7), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case MONTH:
                    logger.info("MONTH");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusMonths(1), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case QUARTER:
                    logger.info("QUARTER");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusMonths(3), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case PERIOD:
                    logger.info("PERIOD");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), filter.getDate_from().toLocalDateTime().toLocalDate(), filter.getDate_to().toLocalDateTime().toLocalDate()))
                            .collect(Collectors.toList());
                    break;
                case ALL_TIME:
                    break;
            }

            if (filter.getManager() != null) {
                logger.info("MANAGER");
                deals = deals.stream()
                        .filter(deal -> deal.getMainContact().equals(filter.getManager()))
                        .collect(Collectors.toList());
            }

            switch (filter.getTaskType()) {
                case WO_TASKS:
                    logger.info("WO TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks() == null)
                            .collect(Collectors.toList());
                    break;
                case EXPIRED:
                    logger.info("EXPIRED TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> task.getDueTime().compareTo(new Date()) < 0))
                            .collect(Collectors.toList());
                    break;
                case TODAY:
                    logger.info("TODAY TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> task.getDueTime().compareTo(new Date()) == 0))
                            .collect(Collectors.toList());
                    break;
                case TOMORROW:
                    logger.info("TOMORROW TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusDays(1))))
                            .collect(Collectors.toList());
                    break;
                case THIS_WEEK:
                    logger.info("THIS WEEK TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusDays(7))))
                            .collect(Collectors.toList());
                    break;
                case THIS_MONTHS:
                    logger.info("THIS MONTH TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusMonths(1))))
                            .collect(Collectors.toList());
                    break;
                case THIS_QUARTER:
                    logger.info("THIS QUARTER TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusMonths(3))))
                            .collect(Collectors.toList());
                    break;
                case IGNORE:
                    logger.info("IGNORE TASKS");

                    break;
            }

            if (filter.getTags() != null && !filter.getTags().isEmpty()) {
                logger.info("TAGS: " + filter.getTags().length());
                deals = deals.stream().filter(deal -> deal.getTags().stream().anyMatch(tag -> filter.getTags().contains(tag.getName()))).collect(Collectors.toList());
            }

        } catch (DataBaseException e) {
            logger.error("Error while getting DAO");
            logger.catching(e);
        }
        return deals;
    }
}
