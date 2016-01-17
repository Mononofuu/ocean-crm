package com.becomejavasenior.access;

import com.becomejavasenior.Comment;
import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.Language;
import com.becomejavasenior.User;
import com.becomejavasenior.impl.AuthServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        try {
            user.setPassword(AuthServiceImpl.getEncryptedPassword(request.getParameter("password"), user.getLogin()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error(e.getMessage());
            errors.add(e.getMessage());
            request.getRequestDispatcher("/registration.jsp").forward(request, response);
        }
        try {
            new AuthServiceImpl().registration(user);
        } catch (DataBaseException e) {
            LOGGER.error(e.getMessage());
            errors.add(e.getMessage());
            request.setAttribute("error", errors);
            request.getRequestDispatcher("/registration.jsp").forward(request, response);
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
            request.getRequestDispatcher("/registration.jsp").forward(request, response);
        }
    }
}
