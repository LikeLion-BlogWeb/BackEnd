package dev.blog.changuii.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    @Value(value = "springboot.jwt.secret")
    private String secret;
    private final UserDetailsService userDetailsService;
    //토큰 유효 시간
    private final Long tokenValidMillisecond = 1000L * 60 * 60;

    @PostConstruct
    protected void init(){
        logger.info("secret Key 초기화");
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String email, List<String> roles){
        logger.info("토큰 생성");
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);

        Date now = new Date();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return token;
    }

    @Transactional
    public Authentication getAuthentication(String token){
        logger.info("토큰 인증 시작");
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(this.getEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token){
        String email = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        return email;
    }

    public String resolveToken(HttpServletRequest request){
        try {
            return request.getHeader("Authorization").replace("Bearer ", "");
        }catch (NullPointerException e){
            logger.info("토큰이 없습니다.");
            return "empty";
        }
    }

    public boolean validToken(String token){
        try {
            logger.info("토큰 유효성 검사");
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            logger.info("토큰 유효성 실패");
            return false;
        }
    }

}
