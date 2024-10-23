package ir.maktabsharif.achareh.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;  // کلید سری از فایل پیکربندی بارگذاری می‌شود

    private static final long JWT_EXPIRATION_MS = 10000 * 60 * 60 * 2;  // انقضای 20 ساعت

    // استخراج نام کاربری از JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // استخراج تاریخ انقضا از JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // استخراج نقش‌ها از JWT
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // تولید JWT برای یک کاربر
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        // اضافه کردن پیشوند "ROLE_" به همه نقش‌ها
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(grantedAuthority -> "ROLE_" + grantedAuthority.getAuthority())  // اضافه کردن پیشوند "ROLE_"
                .collect(Collectors.toList());

        claims.put("roles", roles);  // اضافه کردن نقش‌ها به claims

        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_MS))  // تنظیم انقضا برای 2 ساعت
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)  // استفاده از الگوریتم امن‌تر HS512
                .compact();
    }

    // اعتبارسنجی JWT
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}