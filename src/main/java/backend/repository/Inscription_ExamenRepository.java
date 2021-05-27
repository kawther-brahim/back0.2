package backend.repository;



import backend.model.inscriptionExamen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Inscription_ExamenRepository extends JpaRepository<inscriptionExamen, Long> {
    List<inscriptionExamen> findByClientCin(long clientCin);
}
