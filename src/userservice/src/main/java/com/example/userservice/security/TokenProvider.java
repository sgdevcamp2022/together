package com.example.userservice.security;

import com.example.userservice.exception.CustomException;
import com.example.userservice.exception.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenProvider {
    private final Environment env;

    @Autowired
    public TokenProvider(Environment env) {
        this.env = env;
    }

    public String buildAccessToken(String userId) {
        String accessToken = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.access_expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();
        return accessToken;
    }

    public String buildRefreshToken(String email) {
        String refreshToken = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.refresh_expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();
        return refreshToken;
    }

    public void checkValidToken(String token) {
        try{
            Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(token).getBody()
                    .getSubject();
        } catch (ExpiredJwtException e){
            throw new CustomException(ErrorCode.EXPIRED_REFRESH_TOKEN);
        }
    }

    /**
     * gateway에서 accesstoken은 이미 검증 완료
     */
    public String getIdFromAccessToken(String atk) {
        String userId = null;
        try{
            userId = Jwts.parser().setSigningKey(env.getProperty("token.secret"))
                    .parseClaimsJws(atk).getBody()
                    .getSubject();
        } catch (ExpiredJwtException e) {
            userId = e.getClaims().getSubject();
        } if (userId == null) throw new CustomException(ErrorCode.BAD_TOKEN); // 토큰 검증 에러

        return userId;
    }
}
