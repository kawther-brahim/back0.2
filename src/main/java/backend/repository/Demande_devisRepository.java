package backend.repository;

import backend.model.demandedevis;

import backend.model.materiel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Demande_devisRepository extends JpaRepository<demandedevis, Long> {

    List<demandedevis> findByClientCin(long clientCin);
    List<demandedevis> findByFormationId(long formationCin);
}
