package me.nazima.springdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import me.nazima.springdeveloper.dao.User;
import me.nazima.springdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    @DisplayName("generateToken(): 유저 정보와 만료 기간을 전달해 토큰을 생성")
    void generateTokenTest() {

        // given
        User testUser = userRepository.save(
                User.builder()
                        .email("user@gmail.com")
                        .password("test")
                        .nickname("testUser")
                        .build()
        );

        // when
        String token = tokenProvider.generateToken(
                testUser,
                Duration.ofDays(14)
        );

        // then
        Long userId= Jwts.parser().setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());

    }
}
