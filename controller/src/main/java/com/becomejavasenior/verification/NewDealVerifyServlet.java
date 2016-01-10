package com.becomejavasenior.verification;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
@WebServlet(name="new_deal_verify", urlPatterns = "/new_deal_verify")
public class NewDealVerifyServlet extends AbstractVerifyServlet {
    @Override
    protected Map<String, String> verifyData(HttpServletRequest req) {
        Map<String, String> result = new HashMap<>();
        String dealName = req.getParameter("newdealname");
        if("".equals(dealName)){
            result.put("newdealname", "Введите имя сделки");
        }
        String dealBudget = req.getParameter("budget");
        if(!"".equals(dealBudget)&&!checkString(dealBudget, BUDGET_PATTERN)){
            result.put("budget", "Поле может содержать только целое число или число с плавающей точкой");
        }
        return result;
    }
}
