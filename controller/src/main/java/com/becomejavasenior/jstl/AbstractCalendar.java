package com.becomejavasenior.jstl;

import com.becomejavasenior.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class AbstractCalendar extends TagSupport{
    private static final Logger LOGGER = LogManager.getLogger(AbstractCalendar.class);
    private List<Task> tasks;
    private Map<String, List<Task>> actualTasks = new HashMap<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat(getDateFormatString());
    protected final static String TD = "<td>";
    protected final static String TD_CLOSE = "</td>";
    protected final static String TR = "<tr>";
    protected final static String TR_CLOSE = "</tr>";

    /**
     * Метод возвращающий строку для использования в качестве ключа к actualTasks
     */
    protected abstract String getDateFormatString();

    /**
     * Метод переопределяется если нужно задать дополнительный CSS класс для форматирования карточек с задачами.
     */
    protected String getCssClassForCellContent(){
        return "";
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public Map<String, List<Task>> getActualTasks() {
        return actualTasks;
    }

    @Override
    public abstract int doStartTag() throws JspException;

    protected void initializeTasks(Calendar startDate, int days) {
        long start = startDate.getTime().getTime();
        Calendar endDate = (Calendar)startDate.clone();
        endDate.add(Calendar.DAY_OF_MONTH, days);
        endDate.add(Calendar.MILLISECOND, -1);
        long end = endDate.getTime().getTime();
        for(Task task: tasks){
            long dueTime = task.getDueTime().getTime();
            if(start<=dueTime&&dueTime<=end){
                String key = dateFormat.format(task.getDueTime());
                if(actualTasks.containsKey(key)){
                    actualTasks.get(key).add(task);
                }else{
                    List<Task> newTasks = new ArrayList<>();
                    newTasks.add(task);
                    actualTasks.put(key, newTasks);
                }
            }
        }
    }

    protected void generateCellContent(StringBuilder output, Calendar startDay){
        List<Task> timetasks = actualTasks.get(dateFormat.format(startDay.getTime()));
        if(timetasks!=null){
            for(Task task: timetasks){
                output.append("<div class=\"element "+getCssClassForCellContent());
                if(new Date().getTime()>task.getDueTime().getTime()){
                    output.append(" missed\">");
                }else{
                    output.append("\">");
                }
                output.append("Тип: "+task.getType()+"<br>");
                output.append(task.getComment()+"<br>");
                if(task.getSubject()!= null){
                    output.append(task.getSubject().getName());
                }
                output.append("</div>");
            }
        }
    }
}
