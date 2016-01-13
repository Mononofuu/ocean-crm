package com.becomejavasenior;

import com.becomejavasenior.impl.DealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.SWITCH;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by kramar on 19.11.15.
 */
@WebServlet("/dealslist")
public class DealsListServlet extends HttpServlet{
    static final Logger logger = LogManager.getRootLogger();
    private DaoFactory dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            List<Deal> dealsList = new ArrayList<Deal>();

            dao = new PostgreSqlDaoFactory();

            GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
            GenericDao<User> userDao = dao.getDao(User.class);
            GenericDao<Filter> filterDao = dao.getDao(Filter.class);
            GenericDao<Contact> contactDao = dao.getDao(Contact.class);

            DealService dealService = new DealServiceImpl();

            List<DealStatus> dealStatusList = dealStatusDao.readAllLite();
            List<User> userList = userDao.readAllLite();
            List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
            List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));

            String action = request.getParameter("action");
            if (action == null) {
                dealsList = dealService.findDeals();
            } else {
                switch (action) {
                    case "applyfilter":

                        List<List<Deal>> list = new ArrayList<>();
                        String filterName = request.getParameter("selectedfilter");
                        switch (filterName) {
                            case "open":
                                dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_OPENED);
                                list.add(dealsList);
                                break;
                            case "my":
                                int userId = ((User) request.getSession().getAttribute("user")).getId();
                                dealsList = dealService.findDealsByUser(userId);
                                list.add(dealsList);
                                break;
                            case "success":
                                dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_SUCCESS);
                                list.add(dealsList);
                                break;
                            case "fail":
                                dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_CLOSED_AND_NOT_IMPLEMENTED);
                                list.add(dealsList);
                                break;
                            case "notask":
                                dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_WITHOUT_TASKS);
                                list.add(dealsList);
                                break;
                            case "expired":
                                dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_WITH_EXPIRED_TASKS);
                                list.add(dealsList);
                                break;
                            case "deleted":
                                dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_DELETED);
                                list.add(dealsList);
                                break;
                            default:
//                                dealsList = dealService.findDeals();
//                                list.add(dealsList);
                                break;
                        }

                        String dealStatusId = request.getParameter("phase");
                        String dealUserId = request.getParameter("manager");
                        String tags = request.getParameter("tags");
                        if (dealStatusId != null && dealStatusId.equals("") == false) {
                            dealsList = dealService.findDealsByStatus(Integer.valueOf(dealStatusId));
                            list.add(dealsList);
                            logger.info("DealsListServlet. Used filter dealStatusId " + dealStatusId);
                        }
                        if (dealUserId != null && dealUserId.equals("") == false) {
                            dealsList = dealService.findDealsByUser(Integer.valueOf(dealUserId));
                            list.add(dealsList);
                            logger.info("DealsListServlet. Used filter dealUserId " + dealUserId);
                        }
                        if (tags != null) {
                            String tag = tags.trim().replaceAll("\\s+", "','");
                            if (!tag.equals("")) {
                                dealsList = dealService.findDealsByTags(tag);
                                list.add(dealsList);
                                logger.info("DealsListServlet. Used filter dealTag " + tag);
                            }
                        }

                        String when = request.getParameter("when");
                        if(!when.equals("ALL_TIME")){
                            String dateFrom = request.getParameter("date_from");
                            String dateTo = request.getParameter("date_to");
                            SimpleDateFormat formatter = new SimpleDateFormat();
                            formatter.applyPattern("mm/dd/yyyy");
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, 0);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.SECOND, 0);
                            cal.set(Calendar.MILLISECOND, 0);
                            java.util.Date currentDate = cal.getTime();
                            Date dateBegin = new java.sql.Date(currentDate.getTime());
                            Date dateEnd = dateBegin;
                            boolean useFilter = true;
                            switch (when){
                                case "TODAY":
                                    break;
                                case "FOR_THREE_DAYS":
                                    cal.add(Calendar.DATE,-2);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    break;
                                case "WEEK":
                                    cal.add(Calendar.DATE,-6);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    break;
                                case "MONTH":
                                    cal.add(Calendar.MONTH,-1);
                                    cal.add(Calendar.DATE,1);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    break;
                                case "QUARTER":
                                    cal.add(Calendar.MONTH,-3);
                                    cal.add(Calendar.DATE,1);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    break;
                                case "PERIOD":
                                    if (!dateFrom.equals("") && !dateTo.equals("")){
                                        java.util.Date date = formatter.parse(dateFrom);
                                        dateBegin = new java.sql.Date(date.getTime());
                                        date = formatter.parse(dateTo);
                                        dateEnd = new java.sql.Date(date.getTime());
                                    }else{
                                        logger.error("Bad date in filter in DealsListServlet");
                                        useFilter = false;
                                    }
                                    break;
                                default:
                                    logger.info("Uncnown condition in created date filter in DealsListServlet");
                                    useFilter = false;
                                    break;
                            }
                            if(useFilter) {
                                dealsList = dealService.findDealsByCreatedDateInterval(dateBegin, dateEnd);
                                list.add(dealsList);
                            }
                        }

                        String tasks = request.getParameter("tasks");
                        if(!tasks.equals("IGNORE")){
                            SimpleDateFormat formatter = new SimpleDateFormat();
                            formatter.applyPattern("mm/dd/yyyy");
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, 0);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.SECOND, 0);
                            cal.set(Calendar.MILLISECOND, 0);
                            java.util.Date currentDate = cal.getTime();
                            Date dateBegin = new java.sql.Date(currentDate.getTime());
                            Date dateEnd = dateBegin;
                            boolean useFilter = true;
                            switch (tasks){
                                case "TODAY":
                                    break;
                                case "TOMORROW":
                                    cal.add(Calendar.DATE,1);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    dateEnd = dateBegin;
                                    break;
                                case "THIS_WEEK":
                                    int day = cal.get(Calendar.DAY_OF_WEEK);
                                    int correction = 0;
                                    switch(day){
                                        case 1:
                                            correction = 6;
                                            break;
                                        case 3:
                                        case 4:
                                        case 5:
                                        case 6:
                                        case 7:
                                            correction = day-2;
                                            break;
                                        default:
                                            break;
                                    }
                                    cal.add(Calendar.DATE,-correction);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    cal.add(Calendar.DATE,6);
                                    currentDate = cal.getTime();
                                    dateEnd = new java.sql.Date(currentDate.getTime());
                                    break;
                                case "THIS_MONTHS":
                                    cal.set(Calendar.DAY_OF_MONTH, 1);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    cal.add(Calendar.MONTH,1);
                                    cal.add(Calendar.DATE,-1);
                                    currentDate = cal.getTime();
                                    dateEnd = new java.sql.Date(currentDate.getTime());
                                    break;
                                case "THIS_QUARTER":
                                    cal.set(Calendar.DAY_OF_MONTH, 1);
                                    cal.add(Calendar.MONTH, -cal.get(Calendar.MONTH)%3);
                                    currentDate = cal.getTime();
                                    dateBegin = new java.sql.Date(currentDate.getTime());
                                    cal.add(Calendar.MONTH,3);
                                    cal.add(Calendar.DATE,-1);
                                    currentDate = cal.getTime();
                                    dateEnd = new java.sql.Date(currentDate.getTime());
                                    break;
                                case "WO_TASKS":
                                    break;
                                case "EXPIRED":
                                    break;
                                default:
                                    logger.info("Uncnown condition in created date filter in DealsListServlet");
                                    useFilter = false;
                                    break;
                            }
                            if(useFilter) {
                                switch (tasks) {
                                    case "WO_TASKS":
                                        dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_WITHOUT_TASKS);
                                        break;
                                    case "EXPIRED":
                                        dealsList = dealService.findDealsByConditions(DealService.CONDITION_DEALS_WITH_EXPIRED_TASKS);
                                        break;
                                    default:
                                        dealsList = dealService.findDealsByTasksDueDateInterval(dateBegin, dateEnd);
                                        break;
                                }
                                list.add(dealsList);
                            }
                        }

                        if (list.size() == 0) {
                            dealsList = dealService.findDeals();
                        } else {
                            dealsList = dealService.findDealsByFilters(list);
                        }
                        break;
                }


                    /*
                    case "savefilter":
                        Filter filter = new Filter();
                        filter.setName(request.getParameter("name"));
                        String periodName = request.getParameter("when");
                        User user = (User) request.getSession().getAttribute("user");
                        filter.setUser(user);
                        if(!periodName.equals("")){
                            filter.setType(FilterPeriod.valueOf(periodName));
                            if (periodName.equals("PERIOD")) {
                                SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd");
                                filter.setDate_from(new Timestamp(format.parse((request.getParameter("date_from"))).getTime()));
                                filter.setDate_to(new Timestamp(format.parse((request.getParameter("date_to"))).getTime()));
                            }
                        }

                        String statusParam = request.getParameter("phase");
                        if (statusParam != "") {
                            DealStatus status = dealStatusDao.read(Integer.parseInt(statusParam));
                            filter.setStatus(status);
                        }

                        String managerParam = request.getParameter("managers");
                        if (managerParam != "") {
                            Contact manager = contactDao.read(Integer.parseInt(managerParam));
                            filter.setManager(manager);
                        }

                        String taskType = request.getParameter("tasks");
                        if(taskType != ""){
                        filter.setTaskType(FilterTaskType.valueOf(taskType));
                        }

                        filter.setTags(request.getParameter("tags"));

                        filterDao.create(filter);

                    default:
                        dealsList = dealService.findDeals();
                        break;
                }
                */
                }
                request.setAttribute("deals", dealsList);
                request.setAttribute("deals_map", dealStatusList);
                request.setAttribute("managers", userList);
                request.setAttribute("filterperiod", filterPeriods);
                request.setAttribute("filtertask", filterTaskTypes);

                request.getRequestDispatcher("jsp/dealslist.jsp").forward(request, response);

            }catch(DataBaseException e){
                logger.error("Error when prepearing data for dealslist.jsp", e);
            }catch(ServletException e){
                logger.error("Error when prepearing data for dealslist.jsp", e);
            } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    }