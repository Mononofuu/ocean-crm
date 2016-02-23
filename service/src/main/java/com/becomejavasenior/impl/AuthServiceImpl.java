package com.becomejavasenior.impl;

import com.becomejavasenior.*;
import com.becomejavasenior.exception.IncorrectDataException;
import com.becomejavasenior.interfacedao.GrantsDAO;
import com.becomejavasenior.interfacedao.UserDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Lybachevskiy.Vladislav
 */
@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    @Autowired
    @Qualifier(value = "HibernateUserDAO")
    public UserDAO userDAO;
    @Autowired
    private GrantsDAO grantsDAO;
    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    public String getEncryptedPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 45;
        int iterations = 20000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        return Arrays.toString(f.generateSecret(spec).getEncoded());
    }

    public User getUser(String login) {
        List users = new ArrayList<>();
        try {
            users = userDAO.readAll();
        } catch (DataBaseException e) {
            LOGGER.error(e.getMessage());
        }
        for (Object user : users) {
            if (user instanceof User) {
                if (((User) user).getLogin().equals(login)) {
                    return (User) user;
                }
            }
        }
        return null;
    }

    @Override
    public User authenticate(String attemptedPassword, String login)
            throws NoSuchAlgorithmException, InvalidKeySpecException, IncorrectDataException {
        User user = getUser(login);
        if (user == null) {
            LOGGER.error("unknown user");
            throw new IncorrectDataException("unknown user");
        }
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, login);
        return user.getPassword().equals(encryptedAttemptedPassword) ? user : null;
    }

    public void registration(User user) throws ServiceException{
        try {
            if (alreadyRegistered(user)) {
                LOGGER.error("User [" + user.getLogin() + "] already registered.");
                throw new ServiceException("User [" + user.getLogin() + "] already registered.");
            }
            Grants grants = new Grants();
            user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), null));
            grants.setUser(userDAO.create(user));
            Role role = new Role();
            role.setId(2);
            grants.setRole(role);
            grantsDAO.create(grants);
        } catch (DataBaseException e) {
            throw new ServiceException(e);
        }
    }

    private boolean alreadyRegistered(User user) throws DataBaseException {
        List<User> users = userDAO.readAll();
        for (User userFromDb : users) {
            if (user.getLogin().equals(userFromDb.getLogin())) {
                return true;
            }
        }
        return false;
    }
}
