package com.becomejavasenior.verification;

import com.becomejavasenior.DataBaseException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class NewTaskVerifyServlet extends AbstractVerifyServlet {
    @Override
    protected Map<String, String> verifyData(HttpServletRequest req) {
        Map<String, String> result = new HashMap<>();
        String task = req.getParameter("tasktext");
        if("".equals(task)){
            result.put("tasktext", "Введите текст задачи");
        }
        String responsible = req.getParameter("taskresponsible");
        if(responsible==null||"".equals(responsible)){
            result.put("taskresponsible", "Укажите ответственного");
        }
        return result;
    }
}
