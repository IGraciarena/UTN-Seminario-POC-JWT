package utn.poc.configurations;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import utn.poc.exceptions.NotFoundException;
import utn.poc.models.User;
import utn.poc.repositories.UserRepository;
import utn.poc.utils.GrantedAuthorities;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static utn.poc.utils.Constants.USER_NOT_FOUND;

public class JwtFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(JwtProperties.HEADER_STRING);

        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX,"");

        String username = JWT.require(HMAC512(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();

        if (username != null) {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
            UserConfigurationToken principal = new UserConfigurationToken(user.getUsername(), user.getPwd(), GrantedAuthorities.getGrantedAuthority(user.getRole()));

            return new UsernamePasswordAuthenticationToken(username, null, principal.getAuthorities());
        }

        return null;
    }
}
