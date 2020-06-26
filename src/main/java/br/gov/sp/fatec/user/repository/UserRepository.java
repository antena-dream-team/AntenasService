package br.gov.sp.fatec.user.repository;

import br.gov.sp.fatec.user.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByName(String name);

    public User findByEmail(String email);

    public List<User> findByNameContainsIgnoreCase(String name);

    public User findTop1ByNameContains(String name);

    public List<User> findByIdGreaterThan(Long id);

//    public List<User> findByAuthorizationName(String name);

//    public List<User> findByAuthorizationNameContainsIgnoreCase(String name);

//    public List<User> findByNameContainsIgnoreCaseOrAuthorizationNameContainsIgnoreCase(String nameUser, String authorizationName);

    @Query("select u from User u where u.name like %?1%")
    public List<User> buscaUser(String name);
}
