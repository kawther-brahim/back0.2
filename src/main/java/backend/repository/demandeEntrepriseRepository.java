package backend.repository;

import backend.model.demandeEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface demandeEntrepriseRepository extends JpaRepository<demandeEntreprise, Long> {

    List<demandeEntreprise> findByEntrepriseRcs(long rcs);
}
