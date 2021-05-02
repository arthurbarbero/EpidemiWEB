package br.gov.sp.fatec.epidemiweb.Security;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    private static final String KEY = "bl*32XXbDjnL$wnl";

    public static String generateToken(Authentication usuario) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LoginModel usuarioSemSenha = new LoginModel();
        usuarioSemSenha.setEmail(usuario.getName());
        if (!usuario.getAuthorities().isEmpty()) {
            usuarioSemSenha.setGroups(usuario.getAuthorities().stream().map(x -> x.getAuthority().toString()).collect(Collectors.toList()).toArray(new String[usuario.getAuthorities().size()]));
        }
        String usuarioJson = mapper.writeValueAsString(usuarioSemSenha);
        Date agora = new Date();
        Long hora = 1000L * 60L * 60L; // Uma hora
        return Jwts.builder().claim("userDetails", usuarioJson).setIssuer("br.gov.sp.fatec")
                .setSubject(usuario.getName()).setExpiration(new Date(agora.getTime() + hora))
                .signWith(SignatureAlgorithm.HS512, KEY).compact();
    }

    public static Authentication parseToken(String token) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String credentialsJson = Jwts.parser()
            .setSigningKey(KEY)
            .parseClaimsJws(token)
            .getBody()
            .get("userDetails", String.class);
        
        LoginModel usuario = mapper.readValue(credentialsJson, LoginModel.class);

        UserDetails userDetails = User.builder()
                .username(usuario.getEmail())
                .password("secret")
                .authorities(usuario.getGroups())
            .build();
        
        return new UsernamePasswordAuthenticationToken(
                    usuario.getEmail(), 
                    usuario.getPassword(),
                    userDetails.getAuthorities()
                );
    }
}
