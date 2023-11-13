package com.gopang.controller;

import com.gopang.dto.LoginDTO;
import com.gopang.dto.TokenDTO;
import com.gopang.security.jwt.JwtFilter;
import com.gopang.security.jwt.TokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/authenticate") // 이충헌
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginDTO loginDTO){
        // 이멜주소와 비번을 사용해서 UsernamePasswordAuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword());
        // 인증관리자를 가져와 authenticationToken 객체를 인증 인증이 성공하면 (a.T)객체 반환 이 객체에는 인증된 사용자의 권한정보를 포함
        // Authentication 객체를 사용해서 토큰 생성,토큰을 포함하는 ResponseEntity 객체도 반환
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 저 위 해당 객체를 SecurityContextHolder에 저장 (스프링 시큐리티 컨택스트 저장!)
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // authentication 객체를 createToken 메소를 이용해서 jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        // response header에 jwt token을 넣어줌
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,"Bearer" + jwt);
        // tokenDTO를 이용해 리스폰스 바디에도 넣어줘서 반환
        return new ResponseEntity<>(new TokenDTO(jwt),httpHeaders, HttpStatus.OK);
               /*
        1.사용자가 이메일 주소와 비밀번호를 입력하여 로그인 요청.
        2.서버는 로그인 요청을 받고, LoginDTO 객체를 매개변수로 하여 authorize() 메서드를 호출.
        3.authorize() 메서드는 LoginDTO 객체의 이메일 주소와 비밀번호를 사용하여 UsernamePasswordAuthenticationToken 객체 생성.
        4.authorize() 메서드는 authenticationManagerBuilder 객체에서 인증 관리자를 가져와서 AuthenticationToken 객체 인증.
        5.인증이 성공하면 Authentication 객체가 반환됩니다.
        6.authorize() 메서드는 Authentication 객체를 사용하여 토큰을 생성하고, 토큰을 포함하는 ResponseEntity 객체 반환.
        7.서버는 ResponseEntity 객체를 브라우저에 전송합니다.
        8.브라우저는 ResponseEntity 객체에서 토큰을 추출하고, 토큰을 사용하여 애플리케이션에 요청보냄
         */
    }
}
