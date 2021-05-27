package backend.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.model.materiel;


@Repository
public interface MaterielRepository extends JpaRepository<materiel, String>  {
	List<materiel> findBySalleId(long salleId);

}