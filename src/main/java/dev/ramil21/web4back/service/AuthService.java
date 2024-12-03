package dev.ramil21.web4back.service;


import dev.ramil21.web4back.dao.IRefreshTokenDao;
import dev.ramil21.web4back.dao.IUserDao;
import dev.ramil21.web4back.dto.TokensDto;
import dev.ramil21.web4back.exceptions.UserEmailExistsException;
import dev.ramil21.web4back.model.RefreshToken;
import dev.ramil21.web4back.model.Role;
import dev.ramil21.web4back.model.User;
import dev.ramil21.web4back.util.PasswordUtil;
import dev.ramil21.web4back.util.RefreshTokenUtil;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.rmi.ServerException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Stateless
public class AuthService {

    @EJB
    private IUserDao UserDao;

    @EJB
    private IRefreshTokenDao refreshTokenDao;

    @EJB
    private TokenService tokenService;

    @Inject
    private PasswordUtil passwordUtil;
    @Inject
    private RefreshTokenUtil refreshTokenUtil;

    public TokensDto registerUser(String email, String password) throws NoSuchAlgorithmException, UserEmailExistsException, ServerException {
        if (UserDao.findByEmail(email).isPresent()) {
            throw new UserEmailExistsException("User with email " + email + " already exists");
        }

        byte[] salt = passwordUtil.generateSalt();
        byte[] hashedPassword = passwordUtil.hashPassword(password, salt);

        User user = User.builder()
                .email(email)
                .passwordHash(Arrays.toString(hashedPassword))
                .salt(Arrays.toString(salt))
                .role(Role.USER)
                .creationTime(LocalDateTime.now())
                .build();

        UserDao.save(user);

        // TODO: add field ActivationLink to User model
        // TODO: then send mail

        //sendSignUpEmail(user.getEmail(), username, password);

        TokensDto tokensDto = tokenService.generateTokens(user.getId(), user.getEmail(), user.getRole());

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .refreshToken(tokensDto.getRefreshToken())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(30))
                .build();

        refreshTokenDao.save(refreshToken);

        return tokensDto;
    }

}
