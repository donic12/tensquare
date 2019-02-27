package util;

import entity.Identity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

@ConfigurationProperties("jwt.config")
public class JwtUtil {
    private String key;
    private long ttl;//一个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    //生成Token
    public String createToken(Identity identity) {
        long nowMillis = System.currentTimeMillis();
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId(identity.getId())
                .setSubject(identity.getUserName())
                .setIssuedAt(new Date(nowMillis))
                .claim("role", identity.getRole())
                .signWith(SignatureAlgorithm.HS256, key);
        if (identity.getIssuer() != null) {
            jwtBuilder.setIssuer(identity.getIssuer());
        }
        if (ttl > 0) {
            jwtBuilder.setExpiration(new Date(nowMillis + ttl));
        }
        return jwtBuilder.compact();
    }

    public Identity parseToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token).getBody();
        String id = claims.getId();
        String userName = claims.getSubject();
        String role = claims.get("role").toString();
        String issuer = claims.getIssuer();
        // 封装成pojo
        Identity identity = new Identity();
        identity.setId(id);
        identity.setUserName(userName);
        identity.setRole(role);
        identity.setIssuer(issuer);
        identity.setDuration(claims.getExpiration().getTime());
        return identity;
    }

}
