package banquemisr.challenge05.TaskManagement.Security.Filter;

import banquemisr.challenge05.TaskManagement.Exception.ErrorResponse;
import com.auth0.jwt.exceptions.JWTVerificationException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;


public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try
        {
            filterChain.doFilter(request,response);
        }
        catch (JWTVerificationException ex)
        {
            response.setStatus(403);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("msg", "You have an un-authentic token");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonObject.toString());
            response.getWriter().flush();
        }
        catch (RuntimeException ex)
        {
            response.setStatus(400);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("msg", "BAD REQUEST!");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
        }
        catch (Exception ex)
        {
            response.setStatus(400);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("msg", "BAD REQUEST!");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
        }

    }
}
