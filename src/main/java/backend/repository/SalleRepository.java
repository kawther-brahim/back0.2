package backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.model.salle;

import java.sql.Date;
import java.util.List;


@Repository
public interface SalleRepository extends JpaRepository<salle, Long>  {
    @Query(
            value = "select * from salle where delete=false and id not in (SELECT distinct s.id FROM salle s  join formation f on f.id_salle=s.id WHERE (f.datedeb = ?1 or f.datefin = ?2) AND f.delete=false)",
            nativeQuery = true)
    List<salle> getDispoSalle(Date dated,Date datef);


    @Query(
            value = "select id from salle where nom=?1 and delete=false ",
            nativeQuery = true)
    long getSalleId(String nom);






}
