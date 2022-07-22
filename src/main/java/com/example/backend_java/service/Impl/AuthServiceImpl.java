package com.example.backend_java.service.Impl;

import com.example.backend_java.config.Config;
import com.example.backend_java.constant.Constant;
import com.example.backend_java.domain.entity.TokenEntity;
import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.domain.request.LoginRequest;
import com.example.backend_java.domain.request.LogoutRequest;
import com.example.backend_java.domain.response.ErrResponse;
import com.example.backend_java.domain.response.JwtResponse;
import com.example.backend_java.domain.response.ResponseResponse;
import com.example.backend_java.repository.TokenRepository;
import com.example.backend_java.repository.UserRepository;
import com.example.backend_java.service.AuthService;
import com.example.backend_java.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtUtils jwtUtils;
    BCryptPasswordEncoder _passwordEncoder = new BCryptPasswordEncoder();

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, TokenRepository tokenRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtUtils = jwtUtils;
    }

    // Decodes a URL encoded string using `UTF-8`
    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
    }

    @Override
    public ResponseEntity<?> login(HttpServletResponse request, LoginRequest login) {
        try {
            UserEntity entity = userRepository.findByTaiKhoan(login.getTaiKhoan());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Tài khoản của bạn bị sai"));
            } else if (!entity.isStatus()) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Tài khoản bị khóa"));
            } else if (!bCryptPasswordEncoder.matches(decodeValue(login.getMatKhau()), entity.getMatKhau())) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Mật khẩu của bạn bị sai"));
            } else {

                tokenRepository.deleteTaiKhoan(login.getTaiKhoan());

                Authentication authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(login.getTaiKhoan(), login.getMatKhau()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
                // tạo token
                String jwt = Config.TOKEN_PREFIX + " " + jwtUtils.generateJwtToken(userDetails);
                // thêm vào header
                request.addHeader(Config.HEADER_STRING, jwt);

                TokenEntity tokenEntity = new TokenEntity();
                tokenEntity.setTaiKhoan(login.getTaiKhoan());
                tokenEntity.setToken(jwtUtils.generateJwtToken(userDetails));
                tokenEntity.setNgayHetHan(jwtUtils.generateExpirationDate());
                tokenRepository.save(tokenEntity);

                return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, new JwtResponse(jwt)));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Đăng nhập thất bại"));
        }
    }

    @Override
    public ResponseEntity<?> logout(LogoutRequest logout) {
        try {
            TokenEntity entity = tokenRepository.findByToken(logout.getToken());
            if (entity == null) {
                return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Không thấy token"));
            }
            tokenRepository.delete(entity);
            return ResponseEntity.ok(new ResponseResponse<>(Constant.SUCCESS, Constant.MGS_SUCCESS, "Xóa thành công"));
        } catch (Throwable ex) {
            return ResponseEntity.ok(new ErrResponse<>(Constant.FAILURE, Constant.MGS_FAILURE, "Đăng xuất thất bại"));
        }
    }

}
