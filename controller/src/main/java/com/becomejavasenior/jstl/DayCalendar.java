package com.becomejavasenior.jstl;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class DayCalendar extends AbstractCalendar {
    private static final long serialVersionUID = 1648295728552039943L;

    @Override
    protected String getDateFormatString() {
        return "HH:mm";
    }

    @Override
    public int doStartTag() throws JspException {
        StringBuilder output = new StringBuilder();
        Calendar startDay = GregorianCalendar.getInstance();
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.MILLISECOND, 0);
        initializeTasks(startDay, 1); //инициируем карту со списками задач на отображаемый период
        output.append("<table class=\"table table-bordered table-striped table-condensed align-table\">");
        output.append("<tr>");
        output.append("<td width=\"100px\"></td>");
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("EEEE, d MMMM");
        output.append("<td>"+dayDateFormat.format(startDay.getTime())+"</td>");
        output.append("</tr>");
        //задачи на весь день (по времени 23:59)
        output.append("<tr>");
        output.append("<td>Весь день</td>");
        startDay.set(Calendar.HOUR_OF_DAY, 23);
        startDay.set(Calendar.MINUTE, 59);
        output.append("<td>");
        generateCellContent(output, startDay);
        output.append("</td>");
        output.append("</tr>");
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);

        do{
            output.append("<tr>");
            output.append("<td>"+getDateFormat().format(startDay.getTime())+"</td>");
            output.append("<td>");
            generateCellContent(output, startDay);
            output.append("</td>");
            startDay.add(Calendar.MINUTE, 30);
            output.append("</tr>");
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
