package com.becomejavasenior.impl;

import com.becomejavasenior.DashboardService;
import com.becomejavasenior.Event;
import com.becomejavasenior.interfacedao.DashboardDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kramar on 27.01.16.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDAO dashboardDAO;
    @Autowired
    private EventTemplateDAOImpl eventDAO;

    @Override
    public Map<String, Object> getDashboardInformation() {

        Map<String, Object> result = new HashMap<>();

        List<Map<String, Object>> dashboardInfo = dashboardDAO.readDasboardInformation();
        for (Map<String, Object> m : dashboardInfo) {
            result.put((String) m.get("parameter"), m.get("total"));
        }


        List<Event> events = eventDAO.readLastEvents(4);
        result.put("events", events);

        return result;

    }

}
