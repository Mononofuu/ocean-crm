package com.becomejavasenior.user.services;


import com.becomejavasenior.user.dto.User;

public interface UserService {

    public User login(String login, String pass) throws IncorrectDataException;

    public void registration (String login, String name);
}
