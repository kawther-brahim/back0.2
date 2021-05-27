package backend.repository;

import backend.model.admin_entreprise;

import backend.model.materiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Admin_EntrepriseRepository extends JpaRepository<admin_entreprise, Long> {
    List<admin_entreprise> findByEntrepriseRcs(long entrepriseId);
}
