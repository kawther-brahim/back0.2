package backend.repository;

import backend.model.ERole;
import backend.model.role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<role,Long> {
    Optional<role> findByNom(ERole nom);
}
