package ir.maktabsharif.achareh.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.key}")
    private String key;
    @Value("${jwt.expireDate}")
    private long expireDate;
    public String generateToken(UserDetails userDetails){
        return buildToken(new HashMap<>(),userDetails,expireDate);
    }

    public String buildToken(
            Map<String,Object> claims,
            UserDetails userDetails,
            long expireIn
    ){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireIn))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyByte = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyByte);
    }
}
