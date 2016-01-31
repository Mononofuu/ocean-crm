package com.becomejavasenior;

import com.becomejavasenior.impl.DealServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kramar on 19.11.15.
 */
@WebServlet("/dealslist")
public class DealsListServlet extends HttpServlet{
    private final static Logger LOGGER = LogManager.getLogger(DealsListServlet.class);
    private static DaoFactory daoFactory;

    static {
        try {
            daoFactory = new PostgreSqlDaoFactory();
        } catch (DataBaseException e) {
            LOGGER.error("Error init DAO for dealslist.jsp", e);
        }
    }

    ;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            this.process(req, resp);
        }catch(UnknownHostException e){
            LOGGER.error("Error calling process for dealslist.jsp", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            this.process(req, resp);
        }catch(UnknownHostException e){
            LOGGER.error("Error calling process for dealslist.jsp", e);
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, UnknownHostException {

        try {

            List<Deal> dealsList = new ArrayList<>();

            GenericDao<DealStatus> dealStatusDao = daoFactory.getDao(DealStatus.class);
            GenericDao<User> userDao = daoFactory.getDao(User.class);

            DealService dealService = new DealServiceImpl();

            List<DealStatus> dealStatusList = dealStatusDao.readAllLite();
            List<User> userList = userDao.readAllLite();
            List<FilterPeriod> filterPeriods = new ArrayList<>(Arrays.asList(FilterPeriod.values()));
            List<FilterTaskType> filterTaskTypes = new ArrayList<>(Arrays.asList(FilterTaskType.values()));

            String action = request.getParameter("action");
            if (action == null) {
                dealsList = dealService.findDeals();
            } else if ("applyfilter".equals(action)) {
                dealsList = dealService.findDealsByFilters(getFiltersList(request));
            }
            request.setAttribute("deals", dealsList);
            request.setAttribute("deals_map", dealStatusList);
            request.setAttribute("managers", userList);
            request.setAttribute("filterperiod", filterPeriods);
            request.setAttribute("filtertask", filterTaskTypes);

            request.getRequestDispatcher("jsp/dealslist.jsp").forward(request, response);

        } catch (DataBaseException e) {
            LOGGER.error("Error when prepearing data for dealslist.jsp", e);
        } catch (ServletException e) {
            LOGGER.error("Error when prepearing data for dealslist.jsp", e);
        }
    }

    private static List<String> getFiltersList(HttpServletRequest request){

        List<String> list = new ArrayList<>();
        addSelectedFilter(request,list);
        addFilter(request,list,"phase");
        addFilter(request,list,"manager");
        addTagsFilter(request,list);
        addWhenFilter(request,list);
        addTaskFilter(request,list);
        return list;

    }

    private static List<String> addSelectedFilter(HttpServletRequest request,List<String> list){
        String filterName = request.getParameter("selectedfilter");
        if("my".equals(filterName)){
            int userId = ((User) request.getSession().getAttribute("user")).getId();
            list.add("selectedfilter_"+filterName+"_"+userId);
        }else if(!"".equals(filterName)){
            list.add("selectedfilter_"+filterName);
        }
        return list;
    }

    private static List<String> addFilter(HttpServletRequest request,List<String> list, String parameterName){
        String parameterId = request.getParameter(parameterName);
        if (parameterId != null && !"".equals(parameterId)) {
            list.add(parameterName + "_" + parameterId);
        }
        return list;
    }

    private static List<String> addTagsFilter(HttpServletRequest request,List<String> list){
        String tags = request.getParameter("tags");
        if (tags != null) {
            String tag = tags.trim().replaceAll("\\s+", "','");
            if (!"".equals(tag)) {
                list.add("tags_"+tag);
            }
        }
        return list;
    }

    private static List<String> addWhenFilter(HttpServletRequest request,List<String> list){
        String when = request.getParameter("when");
        if(!"ALL_TIME".equals(when)) {
            if("PERIOD".equals(when)){
                String dateFrom = request.getParameter("date_from");
                String dateTo = request.getParameter("date_to");
                if (!"".equals(dateFrom) && !"".equals(dateTo)) {
                    list.add("when_" + when + "_" + dateFrom + "_" + dateTo);
                }else{
                    LOGGER.error("Bad date in filter in DealsListServlet");
                }
            }else{
                list.add("when_" + when);
            }
        }
        return list;
    }

    private static List<String> addTaskFilter(HttpServletRequest request,List<String> list){
        String tasks = request.getParameter("tasks");
        if(!"IGNORE".equals(tasks)) {
            list.add("task_" + tasks);
        }
        return list;
    }

}