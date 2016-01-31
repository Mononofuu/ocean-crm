package com.becomejavasenior.impl;

import com.becomejavasenior.DashboardService;
import com.becomejavasenior.Event;
import com.becomejavasenior.GenericTemplateDAO;
import com.becomejavasenior.interfacedao.DashboardDAO;
import com.becomejavasenior.interfacedao.EventDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kramar on 27.01.16.
 */
public class DashboardServiceImpl implements DashboardService{

    private ApplicationContext context;
    private DashboardDAO dashboardDAO;
    private EventTemplateDAOImpl eventDAO;

    @Override
    public Map<String, Object> getDasboardInformation() {

        Map<String, Object> result = new HashMap<>();
        context = new ClassPathXmlApplicationContext("spring-datasource.xml");
        dashboardDAO = (DashboardTemplateDAOImpl)context.getBean("dashboardDAO");
        eventDAO = (EventTemplateDAOImpl)context.getBean("eventDAO");

        List<Map<String,Object>> dashboardInfo = dashboardDAO.readDasboardInformation();
        for (Map<String,Object> m : dashboardInfo){
            result.put( (String) m.get("parameter"), m.get("total"));
        }


        List<Event> events = eventDAO.readLastEvents(4);
        result.put("events", events);

        return result;

    }

}
