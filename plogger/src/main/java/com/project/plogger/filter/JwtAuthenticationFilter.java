package com.project.plogger.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.plogger.provider.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = parseBearerToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            String id = jwtProvider.validate(token);
            if (id == null) {
                filterChain.doFilter(request, response);
                return;
            }

            setContext(request, id);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization)
            return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer)
            return null;

        String token = authorization.substring(7);
        return token;
        
    }

        // security context 생성 및 등록
        private void setContext(HttpServletRequest request, String userId) {

            // 접근 주체에 대한 인증 토큰 생성
            AbstractAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, AuthorityUtils.NO_AUTHORITIES);
    
            // 생성한 인증 토큰이 어떤 요청에 대한 내용인 상세 정보 추가
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
            // 빈 security context 생성
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
    
            // 생성한 빈 security context에 authenticationToken 주입
            securityContext.setAuthentication(authenticationToken);
    
            // 생성한 security context 등록
            SecurityContextHolder.setContext(securityContext);
        }
}