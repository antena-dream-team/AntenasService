package br.gov.sp.fatec.security;

import br.gov.sp.fatec.user.domain.User;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.Date;

public class JwtUtils {

    private static final String KEY = "spring.jwt.sec";

    public static String generateToken(User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        user.setPassword(null);
        String userJson = mapper.writeValueAsString(user);
        Date agora = new Date();
        Long hora = 1000L * 60L * 60L; // Uma hora
        return Jwts.builder().claim("userDetails", userJson)
                .setIssuer("br.gov.sp.fatec")
                .setSubject(user.getName())
                .setExpiration(new Date(agora.getTime() + hora))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public static User parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser()
                .setSigningKey(KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userDetails", String.class);
        return mapper.readValue(credentialsJson, User.class);
    }
}
