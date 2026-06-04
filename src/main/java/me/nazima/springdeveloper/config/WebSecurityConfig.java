package me.nazima.springdeveloper.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailsService userService;

    @Bean
    public WebSecurityCustomizer configure() {

        // H2 Console과 static 자원 인증 제외
        return (WebSecurity web) -> web.ignoring()
                .requestMatchers("/h2-console/**")
                .requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/static/**"));

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // 1./login, /signup 인증 필터를 고치지 않고 바로 컨트럴로로 전달되도록 설정
//        // 2.그 외의 모든 요청은 인증을 거치도록 설정
//        // 3.로그인 폼 페이지 URL 설정
//        // 4.로그인 성공 시 어느 페이지로 갈지 URL 설정 (목록 페이지 URL)
//        // 5.로그아웃 성공했을떄 어느 페이지로 갈지  URL 설정(로그인 페이지 폼 페이지)
//        // 6.로그아웃 했을 떄 세션 정보를 무효화 할지 여부를 설정(true)
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                PathPatternRequestMatcher.withDefaults().matcher("/login"),
                                PathPatternRequestMatcher.withDefaults().matcher("/signup"),
                                PathPatternRequestMatcher.withDefaults().matcher("/user")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
         /*
            1.Spring Security 가 사용자 인증을 위해서 사용할 AuthenticationProvider 생성 및 설정
            2.AuthenticationProvider 가 DB에서 사용자 정보를 읽어오기 위해 사용할 서비스 설정
            3.AuthenticationProvider 가 사용자 암호를 암호화하기 위해 사용할 encoder 설정
            4.AutenticationManager 에게 위에서 생성 및 설정한 AuthenticationProvider 전달
             */

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);

        return new ProviderManager(authProvider);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}