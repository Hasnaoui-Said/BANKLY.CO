package co.bankly.micusers.security.filter;

import co.bankly.micusers.security.util.JwtUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    // any request this method execute et verify sign of user
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals(JwtUtil.REFRESH_JETON)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationJeton = request.getHeader(JwtUtil.AUTH_HEADER);
            if (authorizationJeton != null && authorizationJeton.startsWith(JwtUtil.BEARER)) {
                try {
                    String jwt = authorizationJeton.substring(JwtUtil.BEARER.length());
                    Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
                    JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
                    String username = decodedJWT.getSubject();
                    String[] roles = decodedJWT.getClaim(JwtUtil.AUTHORITIES).asArray(String.class);
                    Collection<GrantedAuthority> authorities = new ArrayDeque<>();
                    for (String role : roles) {
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    // Authenticate user
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    // do it!, next
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    response.setHeader("error-message", e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}