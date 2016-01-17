package com.becomejavasenior;

import com.becomejavasenior.verification.NewCompanyVerifyServlet;
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
public class NewCompanyVerifyServletTest extends Mockito {
    @Test
    public void testServlet() throws IOException, ServletException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("newcompanyname")).thenReturn("");
        when(req.getParameter("newcompanyphone")).thenReturn("hjskkf");
        when(req.getParameter("newcompanyemail")).thenReturn("ksjakdjkkf");
        when(req.getParameter("newcompanywebaddress")).thenReturn("ljgksagj");
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        when(resp.getWriter()).thenReturn(pWriter);
        new NewCompanyVerifyServlet().process(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(sWriter.getBuffer().toString(), new TypeReference<HashMap<String, String>>() {
        });
        verify(req, atLeastOnce()).getParameter("newcompanyname");
        verify(req, atLeastOnce()).getParameter("newcompanyphone");
        verify(req, atLeastOnce()).getParameter("newcompanyemail");
        verify(req, atLeastOnce()).getParameter("newcompanywebaddress");
        verify(resp).getWriter();
        assertEquals(4, map.size());
    }

}
