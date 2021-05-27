package backend.repository;

import backend.model.personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonneRepository extends JpaRepository<personne, Long> {

    @Query("select u from personne  u where u.username=:login ")
    personne loadUser(@Param(value = "login") String login);

    Optional<personne> findByUsername(String username);

    Boolean existsByUsername(String username);


    Boolean existsByEmail(String email);


}
