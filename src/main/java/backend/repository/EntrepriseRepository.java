package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import backend.model.entreprise;

public interface EntrepriseRepository extends JpaRepository<entreprise, Long> {

}
