package com.example.backend_java.utils;

import com.example.backend_java.config.Config;
import com.example.backend_java.config.WebSecurityConfig;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.repository.TokenRepository;
import com.example.backend_java.service.Impl.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final TokenRepository tokenRepository;

    public JwtUtils(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generateJwtToken(UserDetailsImpl userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + Config.RefreshExpirationMs))
                .signWith(SignatureAlgorithm.HS512, Config.SECRET).compact();
    }

    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + Config.RefreshExpirationMs);
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(Config.SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    // lấy token từ header -> lấy thông tin của người đăng nhập
    public static UserEntity getUserEntity(HttpServletRequest request) {
        try {
            String token = request.getHeader(Config.HEADER_STRING);
            if (token != null) {
                // parse the token.
                String username = Jwts.parser().setSigningKey(Config.SECRET)
                        .parseClaimsJws(token.replace(Config.TOKEN_PREFIX, "")).getBody().getSubject();
                UserEntity userEntity = WebSecurityConfig.mUser.findByTaiKhoan(username);
                if (userEntity != null) {
                    return userEntity;
                }
            }
        } catch (Exception e) {
            System.out.println("getUserEntity ERROR: " + e.toString());
        }
        return null;
    }
}
