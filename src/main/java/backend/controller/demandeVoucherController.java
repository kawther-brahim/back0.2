package backend.controller;

import backend.exception.ResourceNotFoundException;
import backend.model.*;
import backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class demandeVoucherController {


    @Autowired
    private demande_voucherRepository demandeRepsitory;

    @Autowired
    private FormationRepository formation;

    @Autowired
    private ExamenRepository examen;

    @Autowired
    private Admin_EntrepriseRepository admin;

    @GetMapping("/demandesVFAttentes")
    public List<demandeVoucher> getAllDemandesFAttente() {

        return demandeRepsitory.getDemandeVFAtt();
    }


    @GetMapping("/demandesVFAcceptes")
    public List<demandeVoucher> getAllDemandesFAccepte() {


        return demandeRepsitory.getDemandeVFAcc();
    }

    @GetMapping("/demandesVFRefuses")
    public List<demandeVoucher> getAllDemandesFRefuse() {

        return demandeRepsitory.getDemandeVFRef();
    }

    @GetMapping("/demandesVEAttentes")
    public List<demandeVoucher> getAllDemandesEAttente() {

        return demandeRepsitory.getDemandeVEAtt();
    }

    @GetMapping("/demandesVEAcceptes")
    public List<demandeVoucher> getAllDemandesEAccepte() {


        return demandeRepsitory.getDemandeVEAcc();
    }

    @GetMapping("/demandesVERefuses")
    public List<demandeVoucher> getAllDemandesERefuse() {

        return demandeRepsitory.getDemandeVERef();
    }

    @GetMapping("/admin/{cin}/demandesV")
    public List<demandeVoucher> getDemandesByClient(@PathVariable long cin){
        return this.demandeRepsitory.findByAdminEntrepriseRcs(cin);
    }


    @PostMapping("/formation/{formationId}/admin/{adminId}")
    public demandeVoucher addDemande(@PathVariable long adminId, @PathVariable long formationId,@Valid @RequestBody demandeVoucher d) {

        try {
            admin_entreprise cl = admin.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("admin not found for this id :: "));
            backend.model.formation f = formation.findById(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation not found for this id :: "));


            d.setAdmin(cl);
            d.setFormation(f);
            d.setEtat("En Attente");
            d.setDate(LocalDate.now());
            d.setNbPlace(d.getNbPlace());
            return demandeRepsitory.save(d);


        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/examen/{examenId}/admin/{adminId}")
    public demandeVoucher addDemandeExamen(@PathVariable long adminId, @PathVariable long examenId,@Valid @RequestBody demandeVoucher d) {

        try {
            admin_entreprise cl = admin.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("admin not found for this id :: "));
            backend.model.examen e = examen.findById(examenId).orElseThrow(() -> new ResourceNotFoundException("examen not found for this id :: "));


            d.setAdmin(cl);
            d.setExamen(e);
            d.setEtat("En Attente");
            d.setNbPlace(d.getNbPlace());
            d.setDate(LocalDate.now());
            return demandeRepsitory.save(d);


        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/accepte/demandeV/{id}")
    public ResponseEntity<demandeVoucher> accepteDemande(@PathVariable(value = "id") long demandeId)throws ResourceNotFoundException {
        demandeVoucher d = demandeRepsitory.findById(demandeId).orElseThrow(() -> new ResourceNotFoundException("demande not found for this id :: " + demandeId));


        d.setEtat("Acceptée");
        final demandeVoucher updatedExam = demandeRepsitory.save(d);
        return ResponseEntity.ok(updatedExam);
    }

    @PutMapping ("/refuser/demandeV/{id}")
    public ResponseEntity<demandeVoucher> refuserDemande(@PathVariable(value = "id") long demandeId)throws ResourceNotFoundException {
        demandeVoucher d = demandeRepsitory.findById(demandeId).orElseThrow(() -> new ResourceNotFoundException("demande not found for this id :: " + demandeId));


        d.setEtat("Refusée");
        final demandeVoucher updatedExam = demandeRepsitory.save(d);
        return ResponseEntity.ok(updatedExam);
    }

    /*@GetMapping("/listeEtudiants/formation/{formationId}")
    public List<demandeVoucher> listEtudiants(@PathVariable long formationId)
    {
        List<demandedevis> demandesAcceptee = new ArrayList<>();
        List<demandedevis> allDemandes= this.demandeRepsitory.findByFormationId(formationId);
        for (demandedevis d : allDemandes) {
            if (d.getEtat().equals("Acceptée")) {
                demandesAcceptee.add(d);
            }
        }
        return demandesAcceptee;
    }*/
}
