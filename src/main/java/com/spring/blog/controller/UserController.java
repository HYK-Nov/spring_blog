package com.spring.blog.controller;

import com.spring.blog.config.jwt.TokenProvider;
import com.spring.blog.dto.AccessTokenResponseDTO;
import com.spring.blog.entity.User;
import com.spring.blog.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Duration;

@Controller
public class UserController {
    private final UsersService usersService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;  // 암호구문 비교하기 위해 추가
    private final TokenProvider tokenProvider;

    @Autowired
    public UserController(UsersService usersService, BCryptPasswordEncoder bCryptPasswordEncoder, TokenProvider tokenProvider){
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/signup")
    public String signupForm(){
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signup(User user){
        usersService.save(user);
        return "redirect:/login";
    }

    @PostMapping("/login")
    @ResponseBody   // REST 컨트롤러가 아닌 컨트롤러에서 REST 형식으로 리턴하게 함
    public ResponseEntity<?> authenticate(User user){
//        폼에서 입력한 로그인 아이디를 이용해 DB 전체 정보 얻어오기
        User userInfo = usersService.getByCredentials(user.getLoginId());

//        유저가 폼에서 날려주는 정보는 id랑 pw인데, 먼저 id를 통해 위에서 정보를 얻어오고
//        pw는 암호화 구문끼리 비교해야하므로, 이 경우 bCryptEncoder의 .match(평문, 암호문) 를 이용하면
//        같은 암호화 구문끼리는 비교하는 효과가 생김
//        상단에 BCryptPasswordEncoder 의존성을 생성한 후, if문 내부에서 비교
                                        // 폼에서 날려준 평문       // DB에 들어있던 암호문
        if (bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword())){
                            // id와 pw를 모두 정확하게 입력한 사용자에게 토큰 발급
                                            // 사용자 정보를 토대로, 2시간 동안 유효한 액세스 토큰 생성
            String token = tokenProvider.generateToken(userInfo, Duration.ofHours(2));
//            json으로 리턴하고 싶으면, 클래스 요소를 리턴해야 함
//            AccessTokenResponseDTO를 dto 패키지에 생성. 멤버변수로 accessToken만 가짐
            AccessTokenResponseDTO tokenDTO = new AccessTokenResponseDTO(token);
            
            return ResponseEntity.ok(tokenDTO); // 발급 성공시 토큰 리턴
        }else {
            return ResponseEntity.badRequest().body("login failed");    // id나 pw가 틀리면 로그인 실패
        }
    }
}
