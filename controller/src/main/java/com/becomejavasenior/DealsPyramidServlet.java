package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    private static final Logger LOGGER = LogManager.getLogger(DealsPyramidServlet.class);
    private static final String NEXT_JSP = "/jsp/dealspyramid.jsp";

    @Autowired
    private DealService dealService;
    @Autowired
    private ContactService contactService;

    public static boolean isBetween(LocalDate created, LocalDate startTime, LocalDate endTime) {
        LOGGER.info("Compare dates");
        LOGGER.info(startTime + " --- " + created + " --- " + endTime);
        return created.compareTo(startTime) > 0 && created.compareTo(endTime) <= 0;
    }

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.catching(e);
        }
        try {
            String action = req.getParameter("action");
            if ("savefilter".equals(action)) {
                Filter filter = new Filter();
                filter.setName(req.getParameter("name"));
                String periodName = req.getParameter("when");
                filter.setType(FilterPeriod.valueOf(periodName));
                User user = (User) req.getSession().getAttribute("user");
                filter.setUser(user);
                if ("PERIOD".equals(periodName)) {
                    SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd");
                    filter.setDate_from(new Timestamp(format.parse(req.getParameter("date_from")).getTime()));
                    filter.setDate_to(new Timestamp(format.parse(req.getParameter("date_to")).getTime()));
                }
                try {
                    int dealStatusId = Integer.parseInt(req.getParameter("phase"));
                    DealStatus status = dealService.findDealStatus(dealStatusId);
                    filter.setStatus(status);
                } catch (NumberFormatException e) {
                    LOGGER.catching(e);
                }

                String managerParam = req.getParameter("managers");
                if (managerParam != null) {
                    try {
                        int contactManagerId = Integer.parseInt(managerParam);
                        Contact manager = contactService.findContactById(contactManagerId);
                        filter.setManager(manager);
                    } catch (NumberFormatException | DataBaseException e) {
                        LOGGER.catching(e);
                    }
                }

                String taskType = req.getParameter("tasks");
                filter.setTaskType(FilterTaskType.valueOf(taskType));

                filter.setTags(req.getParameter("tags"));

                dealService.saveDealFilter(filter);
                resp.sendRedirect("/dealspyramid");
            } else if ("applyfilter".equals(action)) {
                resp.sendRedirect("/dealspyramid?selectedfilter=" + req.getParameter("selectedfilter"));
            }

        } catch (DataBaseException e) {
            LOGGER.error("Error while getting new Filter");
            LOGGER.catching(e);
        } catch (ParseException e) {
            LOGGER.error("Error while parsing date");
            LOGGER.catching(e);
        } catch (IOException e) {
            LOGGER.catching(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("selectedfilter");
        User user = (User) req.getSession().getAttribute("user");
        LOGGER.info(String.format("Selected filter: %s, for user: %s", filter, user.getLogin()));

        try {
            List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
            List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));
            List<Contact> contacts = contactService.findContactsLite();
            List<Filter> filters = dealService.findDealFilters();
            List<DealStatus> statuses = dealService.getAllDealStatuses();
            Collections.sort(statuses);

            List<Deal> deals = applyFilter(req);

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
            LOGGER.error("Error while getting DAO");
            LOGGER.catching(e);
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(NEXT_JSP);
        dispatcher.forward(req, resp);
        LOGGER.info(String.format("REDIRECTING TO %s", NEXT_JSP));
    }

    private List<Deal> applyFilter(HttpServletRequest req) throws DataBaseException {
        String filterName = req.getParameter("selectedfilter");
        List<Deal> deals = dealService.findDealsLite();

        if (filterName == null || "null".equals(filterName) || deals.isEmpty()) {
            return deals;
        }

        LOGGER.info("Applying filter: " + filterName);
        User user = (User) req.getSession().getAttribute("user");
        switch (filterName) {
            case "open":
                return deals.stream().filter(deal -> deal.getDateWhenDealClose() == null)
                        .collect(Collectors.toList());
            case "my":
                return deals.stream().filter(deal -> deal.getUser().getId() == user.getId()).collect(Collectors.toList());
            case "success":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & "SUCCESS".equals(deal.getStatus().getName()))
                        .collect(Collectors.toList());
            case "fail":
                return deals.stream()
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & "CLOSED AND NOT IMPLEMENTED".equals(deal.getStatus().getName()))
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
                        .filter(deal -> (deal.getDateWhenDealClose() != null) & "DELETED".equals(deal.getStatus().getName()))
                        .collect(Collectors.toList());
        }

        int filterId = 0;
        try {
            filterId = Integer.parseInt(filterName);
        } catch (NumberFormatException e) {
            LOGGER.catching(e);
        }

        try {
            Filter filter = dealService.findDealFilterById(filterId);
            deals = deals.stream().filter(deal -> deal.getStatus().equals(filter.getStatus()))
                    .collect(Collectors.toList());

            switch (filter.getType()) {
                case TODAY:
                    LOGGER.info("TODAY");
                    deals = deals.stream()
                            .filter(deal -> deal.getDateCreated().compareTo(new Date()) == 0)
                            .collect(Collectors.toList());
                    break;
                case FOR_THREE_DAYS:
                    LOGGER.info("FOR_THREE_DAYS");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusDays(3), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case WEEK:
                    LOGGER.info("WEEK");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusDays(7), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case MONTH:
                    LOGGER.info("MONTH");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusMonths(1), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case QUARTER:
                    LOGGER.info("QUARTER");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now().minusMonths(3), LocalDate.now()))
                            .collect(Collectors.toList());
                    break;
                case PERIOD:
                    LOGGER.info("PERIOD");
                    deals = deals.stream()
                            .filter(deal -> isBetween(deal.getDateCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), filter.getDate_from().toLocalDateTime().toLocalDate(), filter.getDate_to().toLocalDateTime().toLocalDate()))
                            .collect(Collectors.toList());
                    break;
                case ALL_TIME:
                    break;
            }

            if (filter.getManager() != null) {
                LOGGER.info("MANAGER");
                deals = deals.stream()
                        .filter(deal -> deal.getMainContact().equals(filter.getManager()))
                        .collect(Collectors.toList());
            }

            switch (filter.getTaskType()) {
                case WO_TASKS:
                    LOGGER.info("WO TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks() == null)
                            .collect(Collectors.toList());
                    break;
                case EXPIRED:
                    LOGGER.info("EXPIRED TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> task.getDueTime().compareTo(new Date()) < 0))
                            .collect(Collectors.toList());
                    break;
                case TODAY:
                    LOGGER.info("TODAY TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> task.getDueTime().compareTo(new Date()) == 0))
                            .collect(Collectors.toList());
                    break;
                case TOMORROW:
                    LOGGER.info("TOMORROW TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusDays(1))))
                            .collect(Collectors.toList());
                    break;
                case THIS_WEEK:
                    LOGGER.info("THIS WEEK TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusDays(7))))
                            .collect(Collectors.toList());
                    break;
                case THIS_MONTHS:
                    LOGGER.info("THIS MONTH TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusMonths(1))))
                            .collect(Collectors.toList());
                    break;
                case THIS_QUARTER:
                    LOGGER.info("THIS QUARTER TASKS");
                    deals = deals.stream()
                            .filter(deal -> deal.getTasks()
                                    .stream().anyMatch(task -> isBetween(task.getDueTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now(), LocalDate.now().plusMonths(3))))
                            .collect(Collectors.toList());
                    break;
                case IGNORE:
                    LOGGER.info("IGNORE TASKS");

                    break;
            }

            if (filter.getTags() != null && !filter.getTags().isEmpty()) {
                LOGGER.info("TAGS: " + filter.getTags().length());
                deals = deals.stream().filter(deal -> deal.getTags().stream().anyMatch(tag -> filter.getTags().contains(tag.getName()))).collect(Collectors.toList());
            }
        } catch (DataBaseException e) {
            LOGGER.catching(e);
        }

        return deals;
    }
}
