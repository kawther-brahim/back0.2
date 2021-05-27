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
public class demandeEntrepriseController {

    @Autowired
    private demandeEntrepriseRepository demandeEntrepriseRepository;

    @Autowired
    private EntrepriseRepository entreprise;

    @Autowired
    private ClientRepository client1;


    @PostMapping("/client/{clientId}/entreprise/{identreprise}")
    public demandeEntreprise addDemande(@PathVariable long clientId, @PathVariable long identreprise, @Valid @RequestBody demandeEntreprise demande) {

        try {
            backend.model.client cl = client1.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client not found for this id :: "));
            backend.model.entreprise e = entreprise.findById(identreprise).orElseThrow(() -> new ResourceNotFoundException("identreprise not found for this id :: "));


            demande.setClient(cl);
            demande.setEntreprise(e);
            demande.setEtat("En Attente");
            demande.setDate(LocalDateTime.now());
            return demandeEntrepriseRepository.save(demande);


        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/demande/entreprise/{rcs}")
    public List<demandeEntreprise> getAllDemandeEntreprise(@PathVariable long rcs)
    {

        List<demandeEntreprise> demandesAttente = new ArrayList<>();
        List<demandeEntreprise> allDemandes = this.demandeEntrepriseRepository.findByEntrepriseRcs(rcs);

        for (demandeEntreprise d : allDemandes) {
            if (d.getEtat().equals("En Attente")) {

                demandesAttente.add(d);
            }
        }
        return demandesAttente;
    }

    @PutMapping("/accept/entreprise/{entrepriseId}/clientsentreprise/{cin}/demande/{id}")
    public ResponseEntity<demandeEntreprise> accepteClientEntreprise(@PathVariable long id, @PathVariable long cin,@PathVariable long entrepriseId) throws ResourceNotFoundException {

        client c = client1.findById(cin).orElseThrow(() -> new ResourceNotFoundException("Client Entreprise not found for this id :: " + cin));
        backend.model.entreprise e= entreprise.findById(entrepriseId).orElseThrow(() -> new ResourceNotFoundException(" Entreprise not found for this id :: " + entrepriseId));
        demandeEntreprise d=demandeEntrepriseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("demande Entreprise not found for this id :: " + id));
        c.setEntreprise(e);
        d.setEtat("Acceptée");


        final client updatedClient = client1.save(c);
        final demandeEntreprise updateddemande = demandeEntrepriseRepository.save(d);
        return ResponseEntity.ok(updateddemande);

    }

}
