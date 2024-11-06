package ir.maktabsharif.achareh.config.jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {

//        String requestURI = request.getRequestURI();
        // مسیرهای عمومی که نیازی به احراز هویت ندارند
//        if (
//                        requestURI.startsWith("/public/**") ||
//                        requestURI.startsWith("/swagger-ui") ||
//                        requestURI.startsWith("/v3/api-docs") ||
//                        requestURI.startsWith("/swagger-ui.html")
//        ) {
//            chain.doFilter(request, response);
//            return;
//        }

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (io.jsonwebtoken.security.SignatureException ex) {
                handleException(response, "امضای توکن معتبر نیست.", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (io.jsonwebtoken.ExpiredJwtException ex) {
                handleException(response, "توکن منقضی شده است.", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (Exception ex) {
                handleException(response, "خطای احراز هویت رخ داده است.", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        // بررسی اینکه اگر نام کاربری null باشد، خطای ۴۰۳ بازگردانده شود
//        if (username == null || SecurityContextHolder.getContext().getAuthentication() == null) {
//            handleException(response, "دسترسی شما به این منبع مجاز نیست.", HttpServletResponse.SC_FORBIDDEN);
//            return;
//        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                Claims claims = jwtUtil.extractAllClaims(jwt);

                // استخراج Authorities از JWT
                List<String> authorities = claims.get("authorities", List.class);

                // تبدیل Authorities به SimpleGrantedAuthority
                List<SimpleGrantedAuthority> grantedAuthorities = authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, grantedAuthorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        chain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8"); // تنظیم encoding
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
