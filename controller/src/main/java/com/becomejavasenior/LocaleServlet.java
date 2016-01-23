package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * created by Alekseichenko Sergey <mononofuu@gmail.com>
 */
@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {
    static final Logger LOGGER = LogManager.getRootLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String lang = req.getParameter("lang");
        Config.set(req.getSession(), Config.FMT_LOCALE, new java.util.Locale(lang));
        String previousURL = req.getHeader("referer");
        try {
            resp.sendRedirect(previousURL);
        } catch (IOException e) {
            LOGGER.catching(e);
        }
    }
}
