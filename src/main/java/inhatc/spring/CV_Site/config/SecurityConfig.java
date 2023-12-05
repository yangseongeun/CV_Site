package inhatc.spring.CV_Site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 기본 로그인 사용하기
//       http.formLogin(Customizer.withDefaults()); //로그인
//       http.logout(Customizer.withDefaults()); //로그아웃

        //로그인 처리하기
        http.formLogin(form -> form
                .loginPage("/member/login")
                .failureUrl("/member/login/error")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll());

        http.logout(Customizer.withDefaults());

        // 각 페이지에 대한 접근 권한 설정
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/", "/member/**").permitAll()
                .anyRequest().authenticated());

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

       return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
