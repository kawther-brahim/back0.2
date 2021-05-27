package backend.controller;

import backend.exception.ResourceNotFoundException;

import backend.model.*;
import backend.repository.ClientRepository;
import backend.repository.Client_PassagerRepository;
import backend.repository.Demande_devisRepository;
import backend.repository.FormationRepository;
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
public class demandeController {

    @Autowired
    private Demande_devisRepository demandeRepsitory;

    @Autowired
    private FormationRepository formation;


    @Autowired
    private ClientRepository client1;

    @GetMapping("/demandesAttentes")
    public List<demandedevis> getAllDemandesAttente() {

        List<demandedevis> demandesAttente = new ArrayList<>();
        List<demandedevis> allDemandes = this.demandeRepsitory.findAll();

        for (demandedevis d : allDemandes) {
            if (d.getEtat().equals("En Attente")) {

                demandesAttente.add(d);
            }
        }
        return demandesAttente;
    }


    @GetMapping("/demandesAcceptes")
    public List<demandedevis> getAllDemandesAccepte() {

        List<demandedevis> demandesAccepte = new ArrayList<>();
        List<demandedevis> allDemandes = this.demandeRepsitory.findAll();

        for (demandedevis d : allDemandes) {
            if (d.getEtat().equals("Acceptée")) {
                demandesAccepte.add(d);
            }
        }
        return demandesAccepte;
    }

    @GetMapping("/demandesRefuses")
    public List<demandedevis> getAllDemandesRefuse() {

        List<demandedevis> demandesRefuse = new ArrayList<>();
        List<demandedevis> allDemandes = this.demandeRepsitory.findAll();

        for (demandedevis d : allDemandes) {
            if (d.getEtat().equals("Refusée")) {
                demandesRefuse.add(d);
            }
        }
        return demandesRefuse;
    }

    @GetMapping("/clientP/{cin}/demandes")
    public List<demandedevis> getDemandesByClient(@PathVariable long cin){
        return this.demandeRepsitory.findByClientCin(cin);
    }


    @PostMapping("/formation/{formationId}/client/{clientId}")
    public demandedevis addDemande(@PathVariable long clientId, @PathVariable long formationId,@Valid @RequestBody demandedevis d) {

        try {
            client cl = client1.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client not found for this id :: "));
            formation f = formation.findById(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation not found for this id :: "));


            d.setClient(cl);
            d.setFormation(f);
            d.setEtat("En Attente");
            d.setDate(LocalDateTime.now());
            return demandeRepsitory.save(d);


        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @PutMapping ("/accepte/demande/{id}")
    public ResponseEntity<demandedevis> accepteDemande(@PathVariable(value = "id") long demandeId)throws ResourceNotFoundException {
        demandedevis d = demandeRepsitory.findById(demandeId).orElseThrow(() -> new ResourceNotFoundException("demande not found for this id :: " + demandeId));


        d.setEtat("Acceptée");
        final demandedevis updatedExam = demandeRepsitory.save(d);
        return ResponseEntity.ok(updatedExam);
    }

    @PutMapping ("/refuser/demande/{id}")
    public ResponseEntity<demandedevis> refuserDemande(@PathVariable(value = "id") long demandeId)throws ResourceNotFoundException {
        demandedevis d = demandeRepsitory.findById(demandeId).orElseThrow(() -> new ResourceNotFoundException("demande not found for this id :: " + demandeId));


        d.setEtat("Refusée");
        final demandedevis updatedExam = demandeRepsitory.save(d);
        return ResponseEntity.ok(updatedExam);
    }

    @GetMapping("/listeEtudiants/formation/{formationId}")
    public List<demandedevis> listEtudiants(@PathVariable long formationId)
    {
        List<demandedevis> demandesAcceptee = new ArrayList<>();
        List<demandedevis> allDemandes= this.demandeRepsitory.findByFormationId(formationId);
        for (demandedevis d : allDemandes) {
            if (d.getEtat().equals("Acceptée")) {
                demandesAcceptee.add(d);
            }
        }
        return demandesAcceptee;
    }
}
