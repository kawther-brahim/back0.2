package backend.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import backend.model.formateur;
import backend.repository.FormateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;
import backend.model.salle;
import backend.repository.SalleRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class SalleController {
	
	
	@Autowired
	private SalleRepository salleRepository;

	@Autowired
	private FormateurRepository formateurRepository;


	
	@GetMapping("/salles")
	public List<salle> getAllSalle(){
		List<salle> salles=new ArrayList<>();
		List<salle> allSalle=this.salleRepository.findAll();
		
		for(salle s:allSalle) {
			if (s.isDelete()==false) {
				salles.add(s);
			}
		}
		return salles;
	}


	@GetMapping("/salles/dispo/{dated}/{datef}")
	public List<salle> getDisposSalle(@PathVariable(value = "dated") Date dated,@PathVariable(value = "datef") Date datef){
		return salleRepository.getDispoSalle(dated,datef);
	}


	@GetMapping("/salles/{nom}")
	public long getSalleById(@PathVariable(value = "nom") String nomSalle){
		return salleRepository.getSalleId(nomSalle);
	}


		@GetMapping("/salles/archive")
	public List<salle> geArchiveSalle(){
		List<salle> salles=new ArrayList<>();
		List<salle> allSalle=this.salleRepository.findAll();
		
		for(salle s:allSalle) {
			if (s.isDelete()==true) {
				salles.add(s);
			}
		}
		return salles;
	}
	
	
	@PostMapping("/salles")
	public salle addSalle(@Valid @RequestBody salle s) {
		s.setDelete(false);
		return this.salleRepository.save(s);
	}
	
	
	@PutMapping("/salles/{id}")
	public ResponseEntity<salle> updateSalle(@PathVariable(value = "id") long salleId,@Valid @RequestBody salle salleDetails) throws ResourceNotFoundException {
		salle salleFormation = salleRepository.findById(salleId).orElseThrow(() -> new ResourceNotFoundException("salle not found for this id :: " + salleId));

		salleFormation.setNom(salleDetails.getNom());
		salleFormation.setCapacite(salleDetails.getCapacite());
		
		
		final salle updatedSalle = salleRepository.save(salleFormation);
		return ResponseEntity.ok(updatedSalle);
	}
	
	
	@DeleteMapping("/salles/{id}")
	public Map<String, Boolean> deleteSalle(@PathVariable(value = "id") long salleId)throws ResourceNotFoundException {
		salle salleFormation = salleRepository.findById(salleId).orElseThrow(() -> new ResourceNotFoundException("Salle not found for this id :: " + salleId));

		salleFormation.setDelete(true);
		
		final salle updatedSalle = salleRepository.save(salleFormation);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/salles")
	public Map<String, Boolean> deleteAllSalle()throws ResourceNotFoundException {

		List<salle> allSalle=this.salleRepository.findAll();

		for(salle s:allSalle) {
			s.setDelete(true);

			final salle updatedSalle = salleRepository.save(s);
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
	
	
	
	
	

}
