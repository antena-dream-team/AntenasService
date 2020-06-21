package br.gov.sp.fatec.login.controller;

import br.gov.sp.fatec.login.domain.Login;
import br.gov.sp.fatec.user.domain.User;
import br.gov.sp.fatec.security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("dev/login")
public class LoginController {

    @Autowired
    private AuthenticationManager auth;

    @PostMapping
    @CrossOrigin(exposedHeaders = "Token")
    public ResponseEntity<User> login(@RequestBody Login login, HttpServletResponse response) throws JsonProcessingException {
        Authentication credentials = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        User user = (User) auth.authenticate(credentials).getPrincipal();
        user.setPassword(null);
        response.setHeader("Token", JwtUtils.generateToken(user));
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
