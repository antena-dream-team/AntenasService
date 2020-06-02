package br.gov.sp.fatec.user.controller;

import br.gov.sp.fatec.security.domain.Authorization;
import br.gov.sp.fatec.security.repository.AuthorizationRepository;
import br.gov.sp.fatec.user.domain.User;
import br.gov.sp.fatec.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "dev/user")
public class UserController {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private AuthorizationRepository authorizationRepository;
//
//    public void setUsuarioRepo(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public void setAutorizacaoRepo(AuthorizationRepository authorizationRepository) {
//        this.authorizationRepository = authorizationRepository;
//    }
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @Transactional
//    public User incluirUsuario(String nome, String senha, String nomeAutorizacao) {
//        Authorization authorization = authorizationRepository.findBy(nomeAutorizacao);
//        // Se nao existe
//        if(authorization == null) {
//            // Cria nova
//            authorization = new Authorization();
//            authorization.setNome(nomeAutorizacao);
//            authorizationRepository.save(authorization);
//        }
//        User user = new User();
//        user.setNome(nome);
//        user.setSenha(md5(senha));
//        user.setAutorizacoes(new ArrayList<Authorization>());
//        user.getAuthorities().add(authorization);
//        userRepository.save(user);
//        return user;
//    }
//
//    @Override
//    public List<User> buscar(String nome) {
//        return userRepository.findByNomeContainsIgnoreCase(nome);
//    }
//
//    @Override
//    @PreAuthorize("isAuthenticated()")
//    public User buscar(Long id) {
//        Optional<User> user =  userRepository.findById(id);
//        if(user.isPresent()) {
//            return user.get();
//        }
//        return null;
//    }
//
//    @Override
//    @PreAuthorize("isAuthenticated()")
//    public List<User> todos() {
//        List<User> retorno = new ArrayList<User>();
//        for(User user: userRepository.findAll()) {
//            retorno.add(user);
//        }
//        return retorno;
//    }
//
//    @Override
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @Transactional
//    public User salvar(User user) {
//        if(!user.getAuthorities().isEmpty()) {
//            for(Authorization aut: user.getAuthorities()) {
//                // Se nao existe, cria
//                if(aut.getId() == null && authorizationRepository.findByNome(aut.getNome()) == null) {
//                    authorizationRepository.save(aut);
//                }
//            }
//        }
//        user.setSenha(md5(user.getSenha()));
//        return userRepository.save(user);
//    }
//
//    private String md5(String senha) {
//        try {
//            MessageDigest algorithm = MessageDigest.getInstance("MD5");
//            byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
//
//            StringBuilder hexString = new StringBuilder();
//            hexString.append("{MD5}");
//            for (byte b : messageDigest) {
//                hexString.append(String.format("%02x", 0xFF & b));
//            }
//            return hexString.toString();
//        } catch (NoSuchAlgorithmException exception) {
//            exception.printStackTrace();
//            // Unexpected - do nothing
//        } catch (UnsupportedEncodingException exception) {
//            exception.printStackTrace();
//            // Unexpected - do nothing
//        }
//        return senha;
//    }
}
