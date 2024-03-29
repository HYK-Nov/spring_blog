package com.spring.blog.config;

import com.spring.blog.service.UserService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration  // 설정 클래스 상위에 붙이는 어노테이션
public class BasicSecurityConfig {  // 베이직 방식 인증을 사용하도록 설정하는 파일

    //    등록할 시큐리티 서비스 멤버변수 작성
    private final UserDetailsService userService;

    @Autowired
    public BasicSecurityConfig(UserDetailsService userService) {
        this.userService = userService;
    }

    //    정적 파일이나 .jsp 파일 등 스프링 시큐리티가 적용되지 않을 영역 설정
    @Bean   // @Configuration 어노테이션 붙은 클래스 내부 메서드가 리턴하는 자료는 자동으로 빈에 등록
    public WebSecurityCustomizer configure() {
        return web -> web.ignoring()    // 시큐리티 적용 안 할 경로
                .requestMatchers("/static/**", "")  // 기본 경로는 src/main/java/resources 로 잡히고
                                                        // 추후 설정할 정적자원 저장 경로에 보안 해제
                .dispatcherTypeMatchers(DispatcherType.FORWARD);    // MVC 방식에서 View 파일을 로딩하는 것을 보안 범위에서 해제
                                                                    // 이 설정을 하지 않으면, .jsp 파일이 화면에 출력되지 않음
    }

//    http 요청에 대한 웹 보안 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(authorizeConfig -> authorizeConfig   // 인증, 인가 설정 시작부에 사용하는 메서드
                        .requestMatchers("/login", "/signup", "/user")
                        .permitAll()    // 위 경로들은 인증 없이 접속 가능
                        .anyRequest()   // 위에 적힌 경로 말고는
                        .authenticated())   // 로그인 필수
                .formLogin(formLoginConfig -> formLoginConfig   // 로그인 폼으로 로그인 제어
//                        .loginPage("/login")    // 로그인 페이지로 지정할 주소
//                        .defaultSuccessUrl("/blog/list")    // 로그인 하면 처음으로 보여질 페이지
                        .disable())   // 토큰 사용시 폼로그인은 사용하지 않음
                .logout(logoutConfig -> logoutConfig    // 로그아웃 관련 설정
                        .logoutUrl("/logout")   // default가 "/logout"이기 때문에 설정 할 필요 없음
                        .logoutSuccessUrl("/login") // 로그아웃 성공했으면 넘어갈 경로
                        .invalidateHttpSession(true))   // 로그아웃하면 다음 접속시 로그인이 풀려있게 설정
                .csrf(csrfConfig -> csrfConfig.disable())// csrf 공격 방지용 토큰 사용X
                .sessionManagement(sessionConfig -> sessionConfig   // 세션을 무상태성(비사용) 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))    // 토큰 사용시 필요
                .build();
    }

//    위의 설정을 따라가는 인증은 어떤 서비스 클래스를 통해서 설정할 것인가?
    @Bean
    public AuthenticationManager authenticationManager
    (HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) throws  Exception{
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder);
        return builder.build();
    }

//    암호화 모듈 임포트
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
