package dev.ramil21.web4back.service;


import dev.ramil21.web4back.dao.IRefreshTokenDao;
import dev.ramil21.web4back.dao.IUserDao;
import dev.ramil21.web4back.dao.implementations.RefreshTokenDaoImpl;
import dev.ramil21.web4back.dao.implementations.UserDaoImpl;
import dev.ramil21.web4back.dto.TokensDto;
import dev.ramil21.web4back.exceptions.UserEmailExistsException;
import dev.ramil21.web4back.model.RefreshToken;
import dev.ramil21.web4back.model.Role;
import dev.ramil21.web4back.model.User;
import dev.ramil21.web4back.util.JwtTokenUtil;
import dev.ramil21.web4back.util.PasswordUtil;
import dev.ramil21.web4back.util.RefreshTokenUtil;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.rmi.ServerException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Slf4j
@Stateless
public class AuthService {

    @EJB
    private IUserDao UserDao;

    @EJB
    private IRefreshTokenDao refreshTokenDao;

    @Inject
    private JwtTokenUtil jwtTokenUtil;

    @Inject
    private RefreshTokenUtil refreshTokenUtil;

    @Inject
    private PasswordUtil passwordUtil;

    public TokensDto registerUser(String email, String password) throws NoSuchAlgorithmException, UserEmailExistsException, ServerException {
        if (UserDao.findByEmail(email).isPresent()) {
            log.error("USER WITH MAIL {} ALREADY EXISTS", email);
            throw new UserEmailExistsException("User with email " + email + " already exists");
        }

        String salt = passwordUtil.generateSalt();
        String hashedPassword = passwordUtil.hashPassword(password, salt);

        User user = User.builder()
                .email(email)
                .passwordHash(hashedPassword)
                .salt(salt)
                .role(Role.USER)
                .creationTime(LocalDateTime.now())
                .build();
        log.info("User created | AuthService");

        UserDao.save(user);

        log.info("User saved | AuthService");

        // TODO: add field ActivationLink to User model
        // TODO: then send mail to activate account

        //sendSignUpEmail(user.getEmail(), username, password);

        TokensDto tokensDto = new TokensDto(
                jwtTokenUtil.generateJwtToken(user.getId(), user.getEmail(), user.getRole()),
                refreshTokenUtil.generateRefreshToken()
        );

        log.info("Token generated | AuthService");

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .refreshToken(tokensDto.getRefreshToken())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(30))
                .build();

        refreshTokenDao.save(refreshToken);

        log.info("Refresh token saved | AuthService");

        return tokensDto;
    }

}
