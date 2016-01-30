//package com.becomejavasenior;
//
//import com.becomejavasenior.impl.DealServiceImpl;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by kramar on 19.11.15.
// */
//@WebServlet("/dealslist")
//public class DealsListServlet extends HttpServlet{
//    static final Logger logger = LogManager.getRootLogger();
//    private DaoFactory dao;
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.process(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.process(req, resp);
//    }
//
//    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        try {
//
//            List<Deal> dealsList = new ArrayList<Deal>();
//
//            dao = new PostgreSqlDaoFactory();
//
//            GenericDao<DealStatus> dealStatusDao = dao.getDao(DealStatus.class);
//            GenericDao<User> userDao = dao.getDao(User.class);
//            GenericDao<Filter> filterDao = dao.getDao(Filter.class);
//            GenericDao<Contact> contactDao = dao.getDao(Contact.class);
//
//            DealService dealService = new DealServiceImpl();
//
//            List<DealStatus> dealStatusList = dealStatusDao.readAllLite();
//            List<User> userList = userDao.readAllLite();
//            List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
//            List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));
//
//            String action = request.getParameter("action");
//            if (action == null) {
//                dealsList = dealService.findDeals();
//            } else {
//                switch (action) {
//                    case "applyfilter":
//
//                        List<String> list = new ArrayList<>();
//                        String filterName = request.getParameter("selectedfilter");
//                        if(filterName.equals("my")){
//                            int userId = ((User) request.getSession().getAttribute("user")).getId();
//                            list.add("selectedfilter_"+filterName+"_"+userId);
//                        }else if(!filterName.equals("")){
//                            list.add("selectedfilter_"+filterName);
//                        }
//
//                        String dealStatusId = request.getParameter("phase");
//                        if (dealStatusId != null && dealStatusId.equals("") == false) {
//                            list.add("phase_"+dealStatusId);
//                        }
//
//                        String dealUserId = request.getParameter("manager");
//                        if (dealUserId != null && dealUserId.equals("") == false) {
//                            list.add("manager_"+dealUserId);
//                        }
//
//                        String tags = request.getParameter("tags");
//                        if (tags != null) {
//                            String tag = tags.trim().replaceAll("\\s+", "','");
//                            if (!tag.equals("")) {
//                                list.add("tags_"+tag);
//                            }
//                        }
//
//                        String when = request.getParameter("when");
//                        if(!when.equals("ALL_TIME")) {
//                            if(when.equals("PERIOD")){
//                            String dateFrom = request.getParameter("date_from");
//                            String dateTo = request.getParameter("date_to");
//                            if (!dateFrom.equals("") && !dateTo.equals("")) {
//                                list.add("when_" + when + "_" + dateFrom + "_" + dateTo);
//                            }else{
//                                logger.error("Bad date in filter in DealsListServlet");
//                            }
//                            }else{
//                                list.add("when_" + when);
//                            }
//                        }
//
//                        String tasks = request.getParameter("tasks");
//                        if(!tasks.equals("IGNORE")) {
//                            list.add("tasks_" + tasks);
//                        }
//
//                        dealsList = dealService.findDealsByFilters(list);
//                }
//
//           }
//                request.setAttribute("deals", dealsList);
//                request.setAttribute("deals_map", dealStatusList);
//                request.setAttribute("managers", userList);
//                request.setAttribute("filterperiod", filterPeriods);
//                request.setAttribute("filtertask", filterTaskTypes);
//
//                request.getRequestDispatcher("jsp/dealslist.jsp").forward(request, response);
//
//            }catch(DataBaseException e){
//                logger.error("Error when prepearing data for dealslist.jsp", e);
//            }catch(ServletException e){
//                logger.error("Error when prepearing data for dealslist.jsp", e);
//        }
//    }
//    }