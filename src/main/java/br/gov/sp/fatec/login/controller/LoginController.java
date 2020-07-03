package br.gov.sp.fatec.login.controller;

import br.gov.sp.fatec.security.JwtUtils;
import br.gov.sp.fatec.user.domain.User;
import br.gov.sp.fatec.user.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dev/login")
public class LoginController {

    @Autowired
    private AuthenticationManager auth;

    @PostMapping
    public UserDTO login(@RequestBody UserDTO login) throws JsonProcessingException {
        Authentication credentials = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());
        User user = (User) auth.authenticate(credentials).getPrincipal();
        login.setPassword(null);
        login.setToken(JwtUtils.generateToken(user));
        return login;
    }
}
