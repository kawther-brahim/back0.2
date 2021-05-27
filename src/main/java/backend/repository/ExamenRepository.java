package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.model.examen;



@Repository
public interface ExamenRepository extends JpaRepository<examen, Long>  {

}
