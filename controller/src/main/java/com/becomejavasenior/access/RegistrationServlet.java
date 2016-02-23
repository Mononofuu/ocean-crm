package com.becomejavasenior.access;

import com.becomejavasenior.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
@WebServlet(name = "RegistrationServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)
public class RegistrationServlet extends HttpServlet {

    private final static Logger LOGGER = LogManager.getLogger(RegistrationServlet.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        user.setName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        user.setPhoneHome(request.getParameter("phoneMob"));
        user.setPhoneWork(request.getParameter("phoneWork"));
        String languageStringParameter = request.getParameter("language");
        loadPhoto(request, user);
        user.setLanguage(languageStringParameter.length() != 0 ? Language.valueOf(languageStringParameter) : null);
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment();
        comment.setText(request.getParameter("comment"));
        comments.add(comment);
        user.setComments(comments);
        List<String> errors = new ArrayList<>();
        user.setPassword(shaPasswordEncoder.encodePassword(request.getParameter("password"), null));
        try {
            authService.registration(user);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("/jsp/registration.jsp").forward(request, response);
            return;
        }
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/signin");
        }
    }

    private void loadPhoto(HttpServletRequest request, User user) throws IOException, ServletException {
        Part part = request.getPart("photo");
        byte[] imageToBytes = new byte[part.getInputStream().available()];
        part.getInputStream().read(imageToBytes);
        user.setPhoto(imageToBytes);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("userName") == null) {
            request.getRequestDispatcher("/jsp/registration.jsp").forward(request, response);
        }
    }
}
