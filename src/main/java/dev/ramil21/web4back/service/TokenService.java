package dev.ramil21.web4back.service;

import dev.ramil21.web4back.dto.TokensDto;
import dev.ramil21.web4back.model.Role;
import dev.ramil21.web4back.util.JwtTokenUtil;
import dev.ramil21.web4back.util.RefreshTokenUtil;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.rmi.ServerException;

@Stateless
public class TokenService {

    @Inject
    private JwtTokenUtil jwtTokenUtil;

    @Inject
    private RefreshTokenUtil refreshTokenUtil;

    public TokensDto generateTokens(Long id, String email, Role role) throws ServerException {
        String jwtToken = jwtTokenUtil.generateJwtToken(id, email, role);
        String refreshToken = refreshTokenUtil.generateRefreshToken();
        return new TokensDto(jwtToken, refreshToken);
    }

}
