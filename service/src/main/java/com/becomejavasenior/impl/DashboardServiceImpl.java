package com.becomejavasenior.impl;

import com.becomejavasenior.DashboardService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kramar on 27.01.16.
 */
public class DashboardServiceImpl implements DashboardService{




    @Override
    public Map<String, Object> getDasboardInformation() {

        return new DashboardTemplateDAOImpl().readDasboardInformation();

    }

}
