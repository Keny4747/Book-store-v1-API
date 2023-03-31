package pe.todotic.bookstoreapi_s2.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final static String SECRET = "abc2304h234bnnjvjske234ADF234234234asdfsdfhlcvbxyoieerlteorwerabc2304h234bnnjvjske234ADF234234234asdfsdfhlkjlcvbxyoieerlteorwerabc2304h234bnnjvjske234ADF234234234asdfsdfhlkjlcvbxyoieerlteorwerabc2304h234bnnjvjske234ADF234234234asdfsdfhlkjlcvbxyoieerlteorwerabc2304h234bnnjvjske234ADF234234234asdfsdfhlkjlcvbxyoieerlteorwerabc2304h234bnnjvjske234ADF234234234asdfsdfhlkjlcvbxyoieerlteorwerabc2304h234bnnjvjske234ADF234234234asdfsdfhlkjlcvbxyoieerlteorwer";
    public String createToken(Authentication authentication){
        Long durationInMilli = 3600 * 24 * 30L * 1000;
        Long nowInMilli = new Date().getTime();

        Date expirationDate = new Date(nowInMilli + durationInMilli);
        String role = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        byte [] secretBytes = Decoders.BASE64.decode(SECRET);
        SecretKey key = Keys.hmacShaKeyFor(secretBytes);
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("auth",role)
                .signWith(key, SignatureAlgorithm.ES512)
                .setExpiration(expirationDate)
                .compact();
    }

}
