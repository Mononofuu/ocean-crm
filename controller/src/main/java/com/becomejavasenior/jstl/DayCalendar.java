package com.becomejavasenior.jstl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class DayCalendar extends AbstractCalendar {
    private static final long serialVersionUID = 1648295728552039943L;
    private static final Logger LOGGER = LogManager.getLogger(DayCalendar.class);

    @Override
    protected String getDateFormatString() {
        return "HH:mm";
    }

    @Override
    public int doStartTag() throws JspException {
        Locale locale = getCurrentLocale();
        ResourceBundle labels = ResourceBundle.getBundle("messages", locale);
        StringBuilder output = new StringBuilder();
        Calendar startDay = GregorianCalendar.getInstance();
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.MILLISECOND, 0);
        initializeTasks(startDay, 1); //инициируем карту со списками задач на отображаемый период
        output.append("<table class=\"table table-bordered table-striped table-condensed align-table\">");
        output.append(TR);
        output.append("<td width=\"100px\"></td>");
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("EEEE, d MMMM", locale);
        output.append(TD+dayDateFormat.format(startDay.getTime())+TD_CLOSE);
        output.append(TR_CLOSE);
        //задачи на весь день (по времени 23:59)
        output.append(TR);
        output.append(TD+labels.getString("label.allday")+TD_CLOSE);
        startDay.set(Calendar.HOUR_OF_DAY, 23);
        startDay.set(Calendar.MINUTE, 59);
        output.append(TD);
        generateCellContent(output, startDay);
        output.append(TD_CLOSE);
        output.append(TR_CLOSE);
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);

        do{
            output.append(TR);
            output.append(TD+getDateFormat().format(startDay.getTime())+TD_CLOSE);
            output.append(TD);
            generateCellContent(output, startDay);
            output.append(TD_CLOSE);
            startDay.add(Calendar.MINUTE, 30);
            output.append(TR_CLOSE);
        }while (!"00:00".equals(getDateFormat().format(startDay.getTime())));
        output.append("</table>");
        try {
            pageContext.getOut().print(output.toString());
        } catch (IOException e) {
            getLogger().error(e);
        }
        getActualTasks().clear();
        return SKIP_BODY;
    }

}
