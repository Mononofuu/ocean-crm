package com.becomejavasenior.jstl;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class MonthCalendar extends AbstractCalendar {
    private static final long serialVersionUID = 1648298866902039943L;
    private static final String TH_CLOSE = "</th>";

    @Override
    protected String getDateFormatString() {
        return "dd/MM/YYYY";
    }

    @Override
    public int doStartTag() throws JspException {
        Locale locale = getCurrentLocale();
        ResourceBundle labels = ResourceBundle.getBundle("messages", locale);
        StringBuilder output = new StringBuilder();
        output.append("<table class=\"table table-bordered table-striped table-condensed monthtable\">" +
                "                <tr>" +
                "                     <th class=\"cellelement\">"+labels.getString("label.monday")+TH_CLOSE+
                "                     <th class=\"cellelement\">"+labels.getString("label.tuesday")+TH_CLOSE+
                "                     <th class=\"cellelement\">"+labels.getString("label.wednesday")+TH_CLOSE+
                "                     <th class=\"cellelement\">"+labels.getString("label.thursday")+TH_CLOSE+
                "                     <th class=\"cellelement\">"+labels.getString("label.friday")+TH_CLOSE+
                "                     <th class=\"cellelement\">"+labels.getString("label.saturday")+TH_CLOSE+
                "                     <th class=\"cellelement\">"+labels.getString("label.sunday")+TH_CLOSE+
                "                </tr>");
        Calendar startDay = GregorianCalendar.getInstance();
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.set(Calendar.SECOND, 0);
        startDay.set(Calendar.MILLISECOND, 0);
        startDay.set(Calendar.DAY_OF_MONTH, 1);

        while(startDay.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
            startDay.add(Calendar.DAY_OF_MONTH, -1);
        }

        initializeTasks(startDay, 45); //инициируем карту со списками задач на отображаемый период

        for(int week=0;week<5;week++){
            output.append(TR);
            for(int dayOfWeek=0;dayOfWeek<7;dayOfWeek++){
                output.append(TD+startDay.get(Calendar.DAY_OF_MONTH));
                generateCellContent(output, startDay);
                output.append(TD_CLOSE);
                startDay.add(Calendar.DAY_OF_MONTH, 1);
            }
            output.append(TR_CLOSE);
        }
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
