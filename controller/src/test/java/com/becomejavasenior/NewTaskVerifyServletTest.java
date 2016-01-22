package com.becomejavasenior;

import com.becomejavasenior.verification.NewTaskVerifyServlet;
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
public class NewTaskVerifyServletTest extends Mockito {
    @Test
    public void testServlet() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("tasktext")).thenReturn("");
        when(req.getParameter("taskresponsible")).thenReturn("");
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        when(resp.getWriter()).thenReturn(pWriter);
        new NewTaskVerifyServlet().process(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(sWriter.getBuffer().toString(), new TypeReference<HashMap<String, String>>() {
        });
        verify(req, atLeastOnce()).getParameter("tasktext");
        verify(req, atLeastOnce()).getParameter("taskresponsible");
        verify(resp, atLeastOnce()).getWriter();
        assertEquals(3, map.size());
    }
}
