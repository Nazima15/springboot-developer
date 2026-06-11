package me.nazima.springdeveloper.config.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Getter
public class JwtFactory {

    // 기본 이메일
    private String subject = "test@gmail.com";

    // 발급 시간
    private Date issuedAt = new Date();

    // 만료 시간 (14일)
    private Date expiresAt =
            new Date(new Date().getTime() + Duration.ofDays(14).toMillis());

    // 추가 정보(id 등)
    private Map<String, Object> claims = Collections.emptyMap();

    @Builder
    public JwtFactory(
            String subject,
            Date issuedAt,
            Date expiresAt,
            Map<String, Object> claims
    ) {

        this.subject =
                subject != null ? subject : this.subject;

        this.issuedAt =
                issuedAt != null ? issuedAt : this.issuedAt;

        this.expiresAt =
                expiresAt != null ? expiresAt : this.expiresAt;

        this.claims =
                claims != null ? claims : this.claims;
    }

    // 기본값을 가진 JwtFactory 객체 생성
    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }
    public String createToken(JwtProperties jwtProperties){
        return Jwts.builder().setSubject(subject)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(jwtProperties.getIssuer())
                .setExpiration(expiresAt)
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }
}