package com.becomejavasenior;

import com.becomejavasenior.verification.NewDealVerifyServlet;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.mockito.Mockito;

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
public class NewDealVerifyServletTest extends Mockito {
    @Test
    public void testServlet() throws IOException{
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("newdealname")).thenReturn("");
        when(req.getParameter("budget")).thenReturn("hjskkf");
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        when(resp.getWriter()).thenReturn(pWriter);
        new NewDealVerifyServlet().process(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(sWriter.getBuffer().toString(), new TypeReference<HashMap<String, String>>() {
        });
        verify(req, atLeastOnce()).getParameter("newdealname");
        verify(req, atLeastOnce()).getParameter("budget");
        verify(resp, atLeastOnce()).getWriter();
        assertEquals(2, map.size());
    }
}
