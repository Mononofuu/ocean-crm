package com.becomejavasenior.verification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public abstract class AbstractVerifyServlet extends HttpServlet{
    protected static final String PHONE_PATTERN = "^([+]?[0-9\\s-\\(\\)]{3,25})*$";
    protected static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    protected static final String URL_PATTERN = "^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,4})$";
    protected static final String TAG_PATTERN = "^[а-яА-ЯёЁa-zA-Z0-9# ]+$";
    protected static final String BUDGET_PATTERN = "\\-?\\d+(\\.\\d{0,})?";
    protected static final String SKYPE_PATTERN = "^[a-zA-Z0-9,\\._-]{6,32}$";
    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        StringWriter writer = new StringWriter();
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        Map<String, String> errors = verifyData(req);
        mapper.writeValue(writer, errors);
        resp.getWriter().write(writer.toString());
    }

    protected boolean checkString(String str, String pattern){
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * Метод проверяет на коректность данные из формы и возвращает ответ в виде Map<"имя параметра формы","сообщение об ошибке">
     */
    protected abstract Map<String, String> verifyData(HttpServletRequest req);
}
