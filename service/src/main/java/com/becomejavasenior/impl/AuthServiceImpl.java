package com.becomejavasenior.impl;

import com.becomejavasenior.DataBaseException;
import com.becomejavasenior.PostgreSqlDaoFactory;
import com.becomejavasenior.User;
import com.becomejavasenior.exception.IncorrectDataException;
import com.becomejavasenior.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    public User getUser(String login) {
        List users = new ArrayList<>();
        try {
            users = new PostgreSqlDaoFactory().getDao(User.class).readAll();
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

    public static String getEncryptedPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 45;
        int iterations = 20000;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        return Arrays.toString(f.generateSecret(spec).getEncoded());
    }

    public void registration(User user) throws DataBaseException {
        if (alreadyRegistered(user)) {
            LOGGER.error("User [" + user.getLogin() + "] already registered.");
            throw new DataBaseException("User [" + user.getLogin() + "] already registered.");
        }
        new PostgreSqlDaoFactory().getDao(User.class).create(user);
    }

    private boolean alreadyRegistered(User user) throws DataBaseException {
        List<User> users = new PostgreSqlDaoFactory().getDao(User.class).readAll();
        for (User userFromDb : users) {
            if (user.getLogin().equals(userFromDb.getLogin())) {
                return true;
            }
        }
        return false;
    }
}
