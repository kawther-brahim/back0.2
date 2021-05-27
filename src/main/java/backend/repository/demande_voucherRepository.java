package backend.repository;

import backend.model.demandeVoucher;

import backend.model.demandedevis;
import backend.model.formateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface demande_voucherRepository extends JpaRepository<demandeVoucher, Long> {


    List<demandeVoucher> findByAdminEntrepriseRcs(long AdminEntrepriseCin);
    List<demandeVoucher> findByFormationId(long formationId);

    @Query(
            value = "select * from demande_voucher where examen_id is null and etat='En Attente'",
            nativeQuery = true)
    List<demandeVoucher> getDemandeVFAtt();

    @Query(
            value = "select * from demande_voucher where examen_id is null and etat='Acceptée'",
            nativeQuery = true)
    List<demandeVoucher> getDemandeVFAcc();

    @Query(
            value = "select * from demande_voucher where examen_id is null and etat='Refusée'",
            nativeQuery = true)
        List<demandeVoucher> getDemandeVFRef();


    @Query(
            value = "select * from demande_voucher where formation_id is null and etat='En Attente'",
            nativeQuery = true)
    List<demandeVoucher> getDemandeVEAtt();

    @Query(
            value = "select * from demande_voucher where formation_id is null and etat='Acceptée'",
            nativeQuery = true)
    List<demandeVoucher> getDemandeVEAcc();

    @Query(
            value = "select * from demande_voucher where formation_id is null and etat='Refusée'",
            nativeQuery = true)
    List<demandeVoucher> getDemandeVERef();


}
