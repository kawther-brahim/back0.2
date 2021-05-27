package backend.repository;
import java.sql.Date;
import java.util.List;

import backend.model.formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.model.formation;


@Repository
public interface FormationRepository extends JpaRepository<formation, Long> {

	List<formation> findBySalleId(long salleId);
	List<formation> findByFormateurCin(long formateurId);


	@Query(
			value = "select * from formateur where delete=false and cin not in (SELECT distinct fr.cin FROM formateur fr  join formation f on f.id_formateur=fr.cin WHERE (f.datedeb = ?1 or f.datefin = ?2) AND f.delete=false)",
			nativeQuery = true)
	List<formateur> getDispoFormateur(Date dated, Date datef);
}
