package com.becomejavasenior;

import com.becomejavasenior.verification.NewContactVerifyServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class NewContactVerifyServletTest extends Mockito {
    private Logger logger = LogManager.getLogger(NewContactVerifyServletTest.class);

    @Test
    public void testServlet() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("name")).thenReturn("");//ошибка
        when(req.getParameter("phonenumber")).thenReturn("hjskkf");//ошибка
        when(req.getParameter("email")).thenReturn("ksjakdjkkf");//ошибка
        when(req.getParameter("tags")).thenReturn("ljgks// .agj");//ошибка
        when(req.getParameter("skype")).thenReturn("арвыроаы.аи");//ошибка
        //в параметрах сделки заполненно поле бюджет, но не заполненно название - ошибка
        when(req.getParameter("newdealname")).thenReturn("");
        when(req.getParameter("dealtype")).thenReturn("");
        when(req.getParameter("budget")).thenReturn("100");
        //параметры задачи пустые, ошибки быть не должно
        when(req.getParameter("taskresponsible")).thenReturn("");
        when(req.getParameter("tasktype")).thenReturn("");
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        when(resp.getWriter()).thenReturn(pWriter);
        new NewContactVerifyServlet().process(req, resp);
        String result = sWriter.getBuffer().toString();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(result, new TypeReference<HashMap<String, String>>() {
        });
        logger.debug(result);
        assertEquals(6, map.size());
    }
}
