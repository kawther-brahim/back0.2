package backend.repository;

import backend.model.clientPassager;
import backend.model.demandedevis;
import backend.model.notification;
import backend.model.personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface Client_PassagerRepository  {
    //Optional<clientPassager> findByUsername(String username);

   /* @Query(
            value = "select * from demandedevis d inner join client_passager c on(d.client_id=c.cin) inner join formation f on(f.id=d.formation_id) where c.cin=?1",
            nativeQuery = true)
    List<demandedevis> getDemandeByClient(long cin);*/
}


