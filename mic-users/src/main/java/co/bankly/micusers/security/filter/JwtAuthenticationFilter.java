package co.bankly.micusers.security.filter;

import co.bankly.micusers.models.domain.ResponseObject;
import co.bankly.micusers.security.util.JwtUtil;
import co.bankly.micusers.security.vo.Jeton;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    // authentication successfully use library auth0 to create token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        if (request.getServletPath().equals(JwtUtil.REFRESH_JETON))
            filterChain.doFilter(request, response);
        else {
            // result of authenticate authResult
            User user = (User) authResult.getPrincipal();
            // Crypto algo symmetric with une secret
            Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);

            List<String> authority = user.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toList());
            String jwtSuccessToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRED_JETON))
                    .withIssuer(request.getRequestURL().toString())
                    .withClaim(JwtUtil.AUTHORITIES, authority)
                    .sign(algorithm);

            String jwtRefreshToken = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtUtil.EXPIRED_JETON_REFRESH * 30))
                    .withIssuer(request.getRequestURL().toString())
                    .sign(algorithm);

            ObjectMapper mapper = new ObjectMapper();
            Jeton jeton = new Jeton(jwtSuccessToken, jwtRefreshToken);

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            ResponseObject<Jeton> responseObject = new ResponseObject<>(true, "connected successfully", jeton);
            ResponseEntity<ResponseObject<Jeton>> responseEntity = new ResponseEntity<>(responseObject, HttpStatus.OK);
            response.getWriter().println(mapper.writeValueAsString(responseEntity));
        }
    }
}
