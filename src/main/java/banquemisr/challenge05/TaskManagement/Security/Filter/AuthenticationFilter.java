package banquemisr.challenge05.TaskManagement.Security.Filter;

import banquemisr.challenge05.TaskManagement.Security.AuthenticateController;
import banquemisr.challenge05.TaskManagement.Security.SecurityConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private final SecurityConstants securityConstants;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException
    {
        try {
            AuthenticateController.AuthDTO authDTO = mapper.readValue(request.getInputStream(),AuthenticateController.AuthDTO.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authDTO.getEmail(),authDTO.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setStatus(401);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", "Wrong credentials please try again");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
        response.getWriter().flush();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException,
            ServletException {
        String authToken = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.valueOf(securityConstants.getAUTH_TOKEN_EXPIRATION())))
                .sign(Algorithm.HMAC512(securityConstants.getSECRET_KEY()));

        String refreshToken = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + Long.valueOf(securityConstants.getREFRESH_TOKEN_EXPIRATION())))
                .sign(Algorithm.HMAC512(securityConstants.getSECRET_KEY()));

        List<String> authorities = authResult.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", "Authentication is successfull");
        jsonObject.addProperty("roles", authorities.toString());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObject);
        response.addCookie(createCookie(refreshToken, securityConstants.getREFRESH_TOKEN()));
        response.addCookie(createCookie(authToken, securityConstants.getAUTH_TOKEN()));
        response.getWriter().flush();
    }
    public static Cookie createCookie(String token, String tokenName) {
        Cookie cookie = new Cookie(tokenName, token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }
}
