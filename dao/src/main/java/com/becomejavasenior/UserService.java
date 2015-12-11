package com.becomejavasenior;


import com.becomejavasenior.exception.IncorrectDataException;

public interface UserService {

    User login(String login, String pass) throws IncorrectDataException;

    void registration (String login, String name);
}
