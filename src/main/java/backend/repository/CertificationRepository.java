package backend.repository;

import backend.model.admin_entreprise;
import backend.model.certification;
import backend.model.parcours_scolaire;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CertificationRepository extends JpaRepository<certification, Long> {
    List<certification> findByFormateurCin(long cin, Sort s);

}
