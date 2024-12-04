package banquemisr.challenge05.TaskManagement.Security.Filter;


import banquemisr.challenge05.TaskManagement.Security.SecurityConstants;
import banquemisr.challenge05.TaskManagement.User.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import static banquemisr.challenge05.TaskManagement.Security.Filter.AuthenticationFilter.createCookie;


@AllArgsConstructor
public class JWTHandlerFilter extends OncePerRequestFilter {
    private final SecurityConstants securityConstants;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authToken = getToken(request, securityConstants.getAUTH_TOKEN());
        String refreshToken = getToken(request, securityConstants.getREFRESH_TOKEN());


        Boolean byPass = request.getRequestURI().contains(securityConstants.getAUTHENTICATE_PATH()) ||
                request.getRequestURI().contains(securityConstants.getPUBLIC())
                || request.getRequestURI().contains(securityConstants.getH2_DATABASE())
                || request.getRequestURI().contains(securityConstants.getSWAGGER())
                || request.getRequestURI().contains(securityConstants.getSWAGGER_CONFIG());
        if (byPass) {
            if(request.getRequestURI().contains(securityConstants.getSWAGGER())
                    || request.getRequestURI().contains(securityConstants.getSWAGGER_CONFIG()))
            {
                Authentication authentication = new UsernamePasswordAuthenticationToken("", null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
            return;
        }
        if (authToken == null) {
            response.setStatus(401);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("msg", "Authentication required");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
            return;
        }
        String code;
        try {
            code = JWT.require(Algorithm.HMAC512(securityConstants.getSECRET_KEY()))
                    .build()
                    .verify(authToken)
                    .getSubject();

            if (System.currentTimeMillis() + 300000 >
                    JWT.require(Algorithm.HMAC512(securityConstants.getSECRET_KEY()))
                            .build()
                            .verify(refreshToken)
                            .getExpiresAt().getTime()) {
                refreshToken = JWT.create()
                        .withSubject(code)
                        .withExpiresAt(new Date(System.currentTimeMillis() +
                                Long.valueOf(securityConstants.getREFRESH_TOKEN_EXPIRATION())))
                        .sign(Algorithm.HMAC512(securityConstants.getSECRET_KEY()));
                response.addCookie(createCookie(refreshToken, securityConstants.getREFRESH_TOKEN()));

            }


        } catch (Exception e) {
            code = JWT.require(Algorithm.HMAC512(securityConstants.getSECRET_KEY()))
                    .build()
                    .verify(refreshToken)
                    .getSubject();

            authToken = JWT.create()
                    .withSubject(code)
                    .withExpiresAt(new Date(System.currentTimeMillis() +
                            Long.valueOf(securityConstants.getAUTH_TOKEN_EXPIRATION())))
                    .sign(Algorithm.HMAC512(securityConstants.getSECRET_KEY()));

            response.addCookie(createCookie(authToken, securityConstants.getAUTH_TOKEN()));}
        UserDetails user = userService.loadUserByUsername(code);
        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) user.getAuthorities();

        Authentication authentication = new UsernamePasswordAuthenticationToken(code, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
    private String getToken(HttpServletRequest request, String tokenName) {
        Cookie cookie = WebUtils.getCookie(request, tokenName);
        return cookie != null ? cookie.getValue():null;
    }
}
