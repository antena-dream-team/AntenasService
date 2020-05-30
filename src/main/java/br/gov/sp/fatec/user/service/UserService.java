package br.gov.sp.fatec.user.service;

import br.gov.sp.fatec.security.domain.Authorization;
import br.gov.sp.fatec.security.repository.AuthorizationRepository;
import br.gov.sp.fatec.user.domain.User;
import br.gov.sp.fatec.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    public void setUsuarioRepo(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setAutorizacaoRepo(AuthorizationRepository authorizationRepository) {
        this.authorizationRepository = authorizationRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public User incluirUsuario(String login, String password, String authorizationName) {
        Authorization authorization = authorizationRepository.findByName(authorizationName);
        // Se nao existe
        if(authorization == null) {
            // Cria nova
            authorization = new Authorization();
            authorization.setNome(authorizationName);
            authorizationRepository.save(authorization);
        }
        User user = new User();
        user.setEmail(login);
        user.setPassword(md5(password));
        user.setAuthorizations(new ArrayList<>());
        user.getAuthorizations().add(authorization);
        userRepository.save(user);
        return user;
    }

    public List<User> buscar(String login) {
        return userRepository.findByNameContainsIgnoreCase(login);
    }

    @PreAuthorize("isAuthenticated()")
    public User buscar(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if(user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @PreAuthorize("isAuthenticated()")
    public List<User> todos() {
        List<User> retorno = new ArrayList<User>();
        for(User user: userRepository.findAll()) {
            retorno.add(user);
        }
        return retorno;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public User salvar(User user) {
        List<Authorization> authorizations = user.getAuthorizations();

        if(!authorizations.isEmpty()) {
            for(Authorization aut: authorizations) {
                // Se nao existe, cria
                if(aut.getId() == null && authorizationRepository.findByName(aut.getNome()) == null) {
                    authorizationRepository.save(aut);
                }
            }
        }
        user.setPassword(md5(user.getPassword()));
        return userRepository.save(user);
    }

    private String md5(String password) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));

            StringBuilder hexString = new StringBuilder();
            hexString.append("{MD5}");
            for (byte b : messageDigest) {
                hexString.append(String.format("%02x", 0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException exception) {
            exception.printStackTrace();
            // Unexpected - do nothing
        } catch (UnsupportedEncodingException exception) {
            exception.printStackTrace();
            // Unexpected - do nothing
        }
        return password;
    }
}
