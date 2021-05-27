package backend.repository;


import backend.model.parcours_scolaire;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Parcours_scolaireRepository extends JpaRepository<parcours_scolaire, Long> {
    List<parcours_scolaire> findByFormateurCin(long cin, Sort s);
}
