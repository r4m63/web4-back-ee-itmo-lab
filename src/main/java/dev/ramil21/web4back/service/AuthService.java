package dev.ramil21.web4back.service;


import dev.ramil21.web4back.dao.IUserDao;
import jakarta.ejb.EJB;

public class AuthService {

    @EJB
    private IUserDao userDao;

}
