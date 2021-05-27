package backend.controller;

import backend.exception.ResourceNotFoundException;
import backend.model.*;
import backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class inscriptionExamController {

    @Autowired
    private Inscription_ExamenRepository inscriptionRepsitory;

    @Autowired
    private ExamenRepository examen;

    @Autowired
    private ClientRepository client;

    @GetMapping("/inscriptionAttentes")
    public List<inscriptionExamen> getAllInscriptionAttente() {

        List<inscriptionExamen> demandesAttente = new ArrayList<>();
        List<inscriptionExamen> allDemandes = this.inscriptionRepsitory.findAll();

        for (inscriptionExamen d : allDemandes) {
            if (d.getEtat().equals("En Attente")) {

                demandesAttente.add(d);
            }
        }
        return demandesAttente;
    }


    @GetMapping("/inscriptionAcceptes")
    public List<inscriptionExamen> getAllInscriptionAccepte() {

        List<inscriptionExamen> demandesAccepte = new ArrayList<>();
        List<inscriptionExamen> allDemandes = this.inscriptionRepsitory.findAll();

        for (inscriptionExamen d : allDemandes) {
            if (d.getEtat().equals("Acceptée")) {
                demandesAccepte.add(d);
            }
        }
        return demandesAccepte;
    }

    @GetMapping("/inscriptionRefuses")
    public List<inscriptionExamen> getAllInscriptionRefuse() {

        List<inscriptionExamen> demandesRefuse = new ArrayList<>();
        List<inscriptionExamen> allDemandes = this.inscriptionRepsitory.findAll();

        for (inscriptionExamen d : allDemandes) {
            if (d.getEtat().equals("Refusée")) {
                demandesRefuse.add(d);
            }
        }
        return demandesRefuse;
    }


    @PostMapping("/examen/{examenId}/client/{clientId}")
    public inscriptionExamen addDemande(@PathVariable long clientId, @PathVariable long examenId,@Valid @RequestBody inscriptionExamen i) {

        try {
            client cl = client.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client not found for this id :: "));
            backend.model.examen e = examen.findById(examenId).orElseThrow(() -> new ResourceNotFoundException("Examen not found for this id :: "));


            i.setClient(cl);
            i.setExamen(e);
            i.setEtat("En Attente");

            return inscriptionRepsitory.save(i);


        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping ("/accepte/inscription/{id}")
    public ResponseEntity<inscriptionExamen> accepteDemande(@PathVariable(value = "id") long demandeId)throws ResourceNotFoundException {
        inscriptionExamen i = inscriptionRepsitory.findById(demandeId).orElseThrow(() -> new ResourceNotFoundException("demande not found for this id :: " + demandeId));


        i.setEtat("Acceptée");
        final inscriptionExamen updatedExam = inscriptionRepsitory.save(i);
        return ResponseEntity.ok(updatedExam);
    }

    @PutMapping ("/refuser/inscription/{id}")
    public ResponseEntity<inscriptionExamen> refuserDemande(@PathVariable(value = "id") long demandeId)throws ResourceNotFoundException {
        inscriptionExamen i = inscriptionRepsitory.findById(demandeId).orElseThrow(() -> new ResourceNotFoundException("demande not found for this id :: " + demandeId));


        i.setEtat("Refusée");
        final inscriptionExamen updatedExam = inscriptionRepsitory.save(i);
        return ResponseEntity.ok(updatedExam);
    }
    @GetMapping("/clientP/{cin}/inscriptions")
    public List<inscriptionExamen> getInscriptionsByClient(@PathVariable long cin){
        return this.inscriptionRepsitory.findByClientCin(cin);
    }

}
