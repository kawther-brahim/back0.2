package backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;


import backend.repository.Client_PassagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;
import backend.model.formation;
import backend.model.formateur;
import backend.model.salle;
import backend.repository.FormateurRepository;
import backend.repository.FormationRepository;
import backend.repository.SalleRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class FormationController {
	
	
	@Autowired
	private FormationRepository formationRepository;
	
	@Autowired
	private SalleRepository salleRepository;
	
	@Autowired
	private FormateurRepository formateurRepository;


	
	/*@Autowired
	private ClientRepository client;*/
	
	@GetMapping("/formation")
	public List<formation> getAllFormations(){
		
		List<formation> formation=new ArrayList<>();
		List<formation> allFormation=this.formationRepository.findAll();
		
		for(formation f:allFormation) {
			if (f.isDelete()==false) {
				formation.add(f);
			}
		}
		return formation;
	}



	
	@GetMapping("/archive/formation")
	public List<formation> getArchiveFormations(){
		
		List<formation> formation=new ArrayList<>();
		List<formation> allFormation=this.formationRepository.findAll();
		
		for(formation f:allFormation) {
			if (f.isDelete()==true) {
				formation.add(f);
			}
		}
		return formation;
	}


	@GetMapping("/formateurs/{formateurId}/formation")
	public List<formation> getformationByFormateur(@PathVariable long formateurId){
		


		List<formation> formation=new ArrayList<>();
		List<formation> Formationf=this.formationRepository.findByFormateurCin(formateurId);

		for(formation f:Formationf) {
			if (f.isDelete()==false) {
				formation.add(f);
			}
		}
		return formation;
	}

	@GetMapping("/formations/{id}")
	public formation getformationById(@PathVariable long id) throws ResourceNotFoundException{
		return this.formationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("formation not found  "));

	}
	
	@GetMapping("/salles/{salleId}/formation")
	public List<formation> getFormationBySalle(@PathVariable long salleId){
		
		return this.formationRepository.findBySalleId(salleId);

	}
	

	@PostMapping("/salles/{salleId}/formateurs/{formateurId}/formation")
	public formation addFormation(@PathVariable long salleId,@PathVariable long formateurId,@Valid @RequestBody formation f) throws ResourceNotFoundException {

	
			 
			 formateur formateur = formateurRepository.findById(formateurId).orElseThrow(() -> new ResourceNotFoundException("formateur not found  "));
			 
			 salle salle =salleRepository.findById(salleId).orElseThrow(() -> new ResourceNotFoundException("salle not found  "));
			 if(salle !=null && formateur !=null) {
				 f.setFormateur(formateur);
				f.setSalle(salle);
			f.setDelete(false);
			formationRepository.save(f);
			return f;	
			 }
			return null;
	}
	

	@PutMapping("/formation/{id}")
	public ResponseEntity<formation> updateFormation(@PathVariable(value = "id") long formationId,@Valid @RequestBody formation formationDetails) throws ResourceNotFoundException {
		formation f = formationRepository.findById(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation not found for this id :: " + formationId));


        salle s=salleRepository.findById(formationDetails.getSalle().getId()).orElseThrow(() -> new ResourceNotFoundException("Salle not found "));
        s=new salle(formationDetails.getSalle().getId(),s.getNom(),s.getCapacite());

        formateur fr=formateurRepository.findById(formationDetails.getFormateur().getCin()).orElseThrow(() -> new ResourceNotFoundException("Salle not found "));
        fr=new formateur(formationDetails.getFormateur().getCin(),fr.getUsername(),fr.getPrenom(),fr.getEmail(),fr.getPassword(),fr.getAdresse(),fr.getTel(),fr.getRole(),fr.getExperience(),fr.getCompetence(),fr.getTypeformateur(),fr.getParcours_scolaire());




        f.setLibelle(formationDetails.getLibelle());
        f.setConstructeur(formationDetails.getConstructeur());
        f.setTechnologie(formationDetails.getTechnologie());
		f.setDescription(formationDetails.getDescription());
		f.setDatedeb(formationDetails.getDatedeb());
		f.setPrix(formationDetails.getPrix());
		f.setDatefin(formationDetails.getDatefin());
		f.setSupport_cours(formationDetails.getSupport_cours());
		f.setSalle(s);
		f.setFormateur(fr);
		
		final formation updatedFormation = formationRepository.save(f);
		return ResponseEntity.ok(updatedFormation);
	}
	
	
	@DeleteMapping("/formation/{id}")
	public Map<String, Boolean> deleteFormation(@PathVariable(value = "id") long formationId)throws ResourceNotFoundException {
		formation f = formationRepository.findById(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation not found for this id :: " + formationId));

		f.setDelete(true);
		final formation updatedFormation = formationRepository.save(f);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
	/*@PostMapping("/formation/{idformation}/client/{idclient}")
	public String demandeDevis(@PathVariable(value = "idclient") long clientId,@PathVariable(value = "idformation")  long formationId) {
		
		
		try {
			client_passager cl = client.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client not found for this id :: "));
			formation f = formationRepository.findById(formationId).orElseThrow(() -> new ResourceNotFoundException("Formation not found for this id :: " ));
			cl.getFormations().add(f);
			f.getClients().add(cl);
			formationRepository.save(f);
			
			return "inscription effectué avec succes";
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "no";
		
	}*/

}
