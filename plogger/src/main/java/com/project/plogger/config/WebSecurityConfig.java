package com.project.plogger.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.plogger.config.WebSecurityConfig.FailedAuthenticationEntryPoint;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.filter.JwtAuthenticationFilter;
import com.project.plogger.handler.OAuth2SuccessHandler;
import com.project.plogger.service.Implement.OAuth2UserServiceImplement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

        private final OAuth2SuccessHandler oAuth2SuccessHandler;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;
        private final OAuth2UserServiceImplement oAuth2Service;

        @Bean
        protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

                httpSecurity
                                .cors(cors -> cors
                                                .configurationSource(corsConfigurationSource()))
                                .csrf(CsrfConfigurer::disable)
                                .httpBasic(HttpBasicConfigurer::disable)
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(request -> request

                                                // authController
                                                .requestMatchers(HttpMethod.GET, "/api/v1/auth/**").permitAll()
                                                .requestMatchers(HttpMethod.POST,"/api/v1/auth/id-check", "/api/v1/auth/tel-auth","/api/v1/auth/tel-auth-check","/api/v1/auth/sign-up","/api/v1/auth/sign-in","/api/v1/auth/send-auth","/api/v1/auth/find-id","/api/v1/auth/password-send-auth","/api/v1/auth/find-password").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/v1/recruit/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/v1/active/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/v1/qna/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/api/v1/mypage/**").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/file/**").permitAll()
                                                .requestMatchers(HttpMethod.GET,"/api/v1/follow/**").permitAll()
                                                
                                                // recruitController
                                                .requestMatchers(HttpMethod.POST, "/api/v1/recruit","/api/v1/recruit/","/api/v1/recruit/*/comments","/api/v1/recruit/*/comments/*",
                                                                "/api/v1/recruit/join/*", "/api/v1/recruit/report/*",
                                                                "/api/v1/recruit/like/*",
                                                                "/api/v1/recruit/scrap/*")
                                                                .authenticated()
                                                .requestMatchers(HttpMethod.PATCH, "/api/v1/recruit/*",
                                                                "/api/v1/recruit/iscompleted/*")
                                                                .authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/v1/recruit/*",
                                                                "/api/v1/recruit/*/comments/*")
                                                                .authenticated()
                                                
                                                // activeController
                                                .requestMatchers(HttpMethod.POST, "/api/v1/active/*",
                                                                "/api/v1/active/*/comments",
                                                                "/api/v1/active/like/*",
                                                                "/api/v1/report/active/*",
                                                                "/api/v1/active/tag/*/*")
                                                                .authenticated()
                                                .requestMatchers(HttpMethod.PATCH,"/api/v1/active/*",
                                                                "/api/v1/active/*/comments/*")
                                                                .authenticated()
                                                .requestMatchers(HttpMethod.DELETE,"/api/v1/active/*",
                                                                "/api/v1/active/*/comments/*",
                                                                "/api/v1/active/tag/*/*/*")
                                                                .authenticated()
                                                                
                                                // qnaController
                                                .requestMatchers(HttpMethod.POST,"/api/v1/qna",
                                                                "/api/v1/qna/*/comments")
                                                                .authenticated()
                                                .requestMatchers(HttpMethod.PATCH,"/api/v1/qna/*",
                                                                "/api/v1/qna/*/comments/*")
                                                                .authenticated()
                                                .requestMatchers(HttpMethod.DELETE,"/api/v1/qna/*",
                                                                "/api/v1/qna/*/comments/*")
                                                                .authenticated()
                                                                
                                                
                     

                                                // userController
                                                .requestMatchers(HttpMethod.PATCH, "/api/v1/mypage",
                                                                "/api/v1/mypage/tel-auth",
                                                                "/api/v1/mypage/tel-auth-check", "/api/v1/mypage/comment",
                                                                "/api/v1/mypage/update-password").authenticated()
                                                

                                                
                                                

                                                // alertController
                                                .requestMatchers(HttpMethod.GET,"/api/v1/alert/**").authenticated()
                                                .requestMatchers(HttpMethod.POST,"/api/v1/alert").authenticated()
                                                .requestMatchers(HttpMethod.PATCH,"/api/v1/alert/*/read").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/v1/alert/*").authenticated()
                                                
                                                
                                                // chatController
                                                .requestMatchers(HttpMethod.GET,"/api/v1/chat/**").authenticated()
                                                .requestMatchers(HttpMethod.POST,"/api/v1/chat/rooms","/api/v1/chat/rooms/*/messages","/api/v1/chat/rooms/*/join").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/v1/chat/rooms/*").authenticated()
                                                
                                                // fileController
                                                .requestMatchers(HttpMethod.POST, "/file/**").authenticated()
                                                
                                                // followController
                                                .requestMatchers(HttpMethod.POST,"/api/v1/follow").authenticated()
                                                .requestMatchers(HttpMethod.DELETE, "/api/v1/follow/*").authenticated()

                                                // mileageController
                                                .requestMatchers(HttpMethod.GET, "/api/v1/mileage/**").authenticated()
                                                
                                                // gifticonController
                                                .requestMatchers(HttpMethod.GET, "/api/v1/gifticon/**").authenticated()
                                                .requestMatchers(HttpMethod.POST, "/api/v1/gifticon/","/api/v1/gifticon/*").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.PATCH, "/api/v1/gifticon/*").hasRole("ADMIN")
                                                .requestMatchers(HttpMethod.DELETE,"/api/v1/gifticon/*").hasRole("ADMIN")

                                                // adminController
                                                .requestMatchers("/api/v1/admin").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .exceptionHandling(exceptionHandling -> exceptionHandling
                                                .authenticationEntryPoint(new FailedAuthenticationEntryPoint()))
                                // oAuth2 로그인 적용
                                .oauth2Login(oauth2 -> oauth2
                                                .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                                                .authorizationEndpoint(endpoint -> endpoint
                                                                .baseUri("/api/v1/auth/sns-sign-in"))
                                                .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2Service))
                                                .successHandler(oAuth2SuccessHandler))
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return httpSecurity.build();

        }

        @Bean
        protected CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();
                configuration.addAllowedMethod("*");
                configuration.addAllowedOrigin("*");
                configuration.addAllowedHeader("*");

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);

                return source;
        }

        class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint {
                @Override
                public void commence(HttpServletRequest request, HttpServletResponse response,
                                AuthenticationException authException) throws IOException, ServletException {
                        authException.printStackTrace();
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        response.getWriter()
                                        .write("{ \"code\": \"" + ResponseCode.AUTHENTICATION_FAIL
                                                        + "\", \"message\": \""
                                                        + ResponseMessage.AUTHENTICATION_FAIL + "\" }");

                }
        }
}