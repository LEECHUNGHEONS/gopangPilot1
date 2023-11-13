package com.gopang.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopang.security.JsonLoginProcessFilter;
import com.gopang.security.jwt.JwtFilter;
import com.gopang.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final UserDetailsService userService;
    private final JsonLoginProcessFilter jsonLoginProcessFilter;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;

    //jwt
    private final JwtFilter jwtFilter;
    private final TokenProvider tokenProvider;

//    public SecurityConfig(@Lazy UserDetailsService userService,
//                          @Lazy JsonLoginProcessFilter jsonLoginProcessFilter,
//                          ObjectMapper objectMapper,
//                          JwtFilter jwtFilter,
//                          @Lazy AuthenticationManager authenticationManager,
//                          TokenProvider tokenProvider
//    ) {
//        this.userService = userService;
//        this.jsonLoginProcessFilter = jsonLoginProcessFilter;
//        this.objectMapper = objectMapper;
//        this.jwtFilter = jwtFilter;
//        this.authenticationManager = authenticationManager;
//        this.tokenProvider = tokenProvider;
//    }
//"/join","/cart","/cart/items","/items/{item_id}","/main/items","/categories"
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

       httpSecurity
               .authorizeHttpRequests()//HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정
               .requestMatchers("/login","/join","/authenticate").permitAll() // 여기 엔드포인트들은 인증없이 접근 가능 그 외는 인증 필요
               .anyRequest().authenticated()
               .and()
               .formLogin().disable()
               .csrf().disable() // 외부 post를 받아야하니 csrf는 꺼준다.
               .cors().configurationSource(corsConfigurationSource())
               .and().httpBasic().disable()
               .logout().logoutSuccessUrl("/home")
               .and().headers().frameOptions().sameOrigin(); // 동일한 출처에서만 표시가능, 다른 도메인에서 웹페이지에서 표시x (csrf 공격방지에 도움,보안향상)
       httpSecurity
               .addFilterBefore(new JwtFilter(tokenProvider),UsernamePasswordAuthenticationFilter.class)
               //.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class
               .addFilterBefore(jsonLoginProcessFilter, UsernamePasswordAuthenticationFilter.class)
               .authenticationProvider(daoAuthenticationProvider())
               .exceptionHandling()
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션을 사용하지 않겠다

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() throws Exception{
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        return provider;
    }

    @Bean
    public JsonLoginProcessFilter jsonLoginProcessFilter(){
        JsonLoginProcessFilter jsonLoginProcessFilter = new JsonLoginProcessFilter(objectMapper,authenticationManager);
        jsonLoginProcessFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            String token = tokenProvider.createToken(authentication);

            // jwt 토큰을 http 응답으로 반환
            response.setContentType("application/json");
            response.getWriter().write("{\"token\" : \"" + token + "\"}");
            //response.getWriter().println("Success Login");
        });
        return jsonLoginProcessFilter;
    }

    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder();
    }

    @Bean // 없애도 되는 설정
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://localhost:8080")); // uri는 변경 예정 !!!!!!
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
