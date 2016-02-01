package com.becomejavasenior.jstl;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class WeekCalendar extends AbstractCalendar {
    private static final long serialVersionUID = 1648849355736839943L;

    @Override
    protected String getDateFormatString() {
        return "E_HH:mm";
    }

    @Override
    protected String getCssClassForCellContent() {
        return "smalltext";
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
        while (startDay.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY){
            startDay.add(Calendar.DAY_OF_MONTH, -1);
        }
        initializeTasks(startDay, 7); //инициируем карту со списками задач на отображаемый период
        output.append("<table class=\"table table-bordered table-striped table-condensed align-table\">");
        output.append(TR);
        output.append("<td></td>");
        SimpleDateFormat weekDaysDateFormat = new SimpleDateFormat("E, dd/MM", locale);
        for(int i=0;i<7;i++){
            output.append(TD+weekDaysDateFormat.format(startDay.getTime())+TD_CLOSE);
            startDay.add(Calendar.DAY_OF_MONTH, 1);
        }
        output.append(TR_CLOSE);
        startDay.add(Calendar.DAY_OF_MONTH, -7);
        //задачи на весь день (по времени 23:59)
        output.append(TR);
        output.append(TD+labels.getString("label.allday")+TD_CLOSE);
        startDay.set(Calendar.HOUR_OF_DAY, 23);
        startDay.set(Calendar.MINUTE, 59);
        for(int i=1;i<8;i++, startDay.add(Calendar.DAY_OF_MONTH, 1)){
            output.append(TD);
            generateCellContent(output, startDay);
            output.append(TD_CLOSE);
        }
        output.append(TR_CLOSE);
        startDay.set(Calendar.HOUR_OF_DAY, 0);
        startDay.set(Calendar.MINUTE, 0);
        startDay.add(Calendar.DAY_OF_MONTH, -7);
        startDay.add(Calendar.DAY_OF_MONTH, 1);
        String condition = getDateFormat().format(startDay.getTime()); //записываем в строку дату вторника и время 00:00 чтобы остановить цикл
        startDay.add(Calendar.DAY_OF_MONTH, -1);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        do{
            output.append(TR);
            output.append(TD+timeFormat.format(startDay.getTime())+TD_CLOSE);
            for(int i=0;i<7;i++, startDay.add(Calendar.DAY_OF_MONTH, 1)){
                output.append(TD);
                generateCellContent(output, startDay);
                output.append(TD_CLOSE);
            }
            startDay.add(Calendar.DAY_OF_MONTH, -7);
            startDay.add(Calendar.MINUTE, 30);
            output.append(TR_CLOSE);
        }while (!condition.equals(getDateFormat().format(startDay.getTime())));
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
