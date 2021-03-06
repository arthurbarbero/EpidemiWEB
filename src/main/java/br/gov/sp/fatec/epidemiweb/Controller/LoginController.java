package br.gov.sp.fatec.epidemiweb.Controller;

import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.sp.fatec.epidemiweb.Security.JwtUtils;
import br.gov.sp.fatec.epidemiweb.Security.LoginModel;

@RestController
@RequestMapping(value = "/login")
@CrossOrigin(origins = "http://localhost:8080")
public class LoginController {
    
    @Autowired
    private AuthenticationManager authManager;

    @PostMapping(value = "/")
    public LoginModel authenticate(@RequestBody LoginModel login) throws JsonProcessingException {
        Authentication auth = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        auth = authManager.authenticate(auth);
        login.setPassword(null);
        login.setTokenKey(JwtUtils.generateToken(auth));
        login.setGroups(auth.getAuthorities().stream().map(x -> x.getAuthority().toString()).collect(Collectors.toList()).toArray(new String[auth.getAuthorities().size()]));
        return login;
    }
}
