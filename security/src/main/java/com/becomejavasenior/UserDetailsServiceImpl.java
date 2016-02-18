package com.becomejavasenior;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Anton Sakhno <sakhno83@gmail.com>
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger LOGGER = LogManager.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user ;
        Set<GrantedAuthority> roles = new HashSet<>();
        try {
            user = userService.getUserByLogin(login);
            if(user!=null){
                Role role = userService.getUserRole(user);
                if(role!=null){
                    roles.add(new SimpleGrantedAuthority(role.getName()));
                }else{
                    throw new UsernameNotFoundException("user not granted");
                }

            }else {
                throw new UsernameNotFoundException("user not found");
            }
        } catch (DataBaseException e) {
            LOGGER.error(e);
            throw new UsernameNotFoundException("error reading user from DB");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),roles);
    }

}
