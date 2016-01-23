package com.becomejavasenior;

import com.becomejavasenior.exception.IncorrectDataException;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Lybachevskiy.Vladislav
 */
public interface AuthService {

    User authenticate(String attemptedPassword, String login) throws IncorrectDataException, NoSuchAlgorithmException, InvalidKeySpecException;

    void registration (User user) throws DataBaseException;
}
