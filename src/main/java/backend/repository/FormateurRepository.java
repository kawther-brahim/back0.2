package backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import backend.model.formateur;

import java.sql.Date;
import java.util.List;


@Repository
public interface FormateurRepository extends JpaRepository<formateur, Long> {
    @Query(
            value = "select * from formateur where delete=false and cin not in (SELECT distinct fr.cin FROM formateur fr  join formation f on f.id_formateur=fr.cin WHERE (f.datedeb = ?1 or f.datefin = ?2) AND f.delete=false)",
            nativeQuery = true)
    List<formateur> getDispoFormateur(Date dated, Date datef);

    @Query(
            value = "select cin from formateur where username=?1 and delete=false ",
            nativeQuery = true)
    long getFormateurId(String username);
}
