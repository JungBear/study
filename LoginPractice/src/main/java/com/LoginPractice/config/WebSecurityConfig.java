package com.LoginPractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.LoginPractice.User.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final UserService userService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http
		 .authorizeHttpRequests((requests) -> requests
					.requestMatchers("/", "/home").permitAll()
					.anyRequest().authenticated()
				)
         .formLogin()
             .loginPage("/login")
             .loginProcessingUrl("/login_proc")
             .defaultSuccessUrl("/user_access")
             .failureUrl("/access_denied") // 인증에 실패했을 때 보여주는 화면 url, 로그인 form으로 파라미터값 error=true로 보낸다.
             .and()
         .csrf().disable();		//로그인 창
		 
		 return http.build();
 }
	
	@Bean // 패스워드 암호화 관련 메소드
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	

	

	
}