package backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import backend.model.client_entreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;
import backend.model.entreprise;
import backend.repository.EntrepriseRepository;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EntrepriseController {
	
	@Autowired
	private EntrepriseRepository entrepriseRepository;
	
	@GetMapping("/entreprises")
	public List<entreprise> getAllEntreprise(){
		List<entreprise> entreprises=new ArrayList<>();
		List<entreprise> allEntreprise=this.entrepriseRepository.findAll();
		
		for(entreprise e:allEntreprise) {
			if (e.isDelete()==false) {
				entreprises.add(e);
			}
		}
		return entreprises;
	}
	
	
	@GetMapping("/archive/entreprises")
	public List<entreprise> geArchiveEntreprise(){
		List<entreprise> entreprises=new ArrayList<>();
		List<entreprise> allEntreprise=this.entrepriseRepository.findAll();
		
		for(entreprise e:allEntreprise) {
			if (e.isDelete()==true) {
				entreprises.add(e);
			}
		}
		return entreprises;
	}
	
	@PostMapping("/entreprises")
	public entreprise addEntreprise(@Valid @RequestBody entreprise e) {
		e.setDelete(false);
		return this.entrepriseRepository.save(e);
	}
	
	
	@PutMapping("/entreprises/{id}")
	public ResponseEntity<entreprise> updateEntreprise(@PathVariable(value = "id") long entrepriseId,@Valid @RequestBody entreprise entrepriseDetails) throws ResourceNotFoundException {
		entreprise ese = entrepriseRepository.findById(entrepriseId).orElseThrow(() -> new ResourceNotFoundException("entreprise not found for this id :: " + entrepriseId));

		ese.setNom(entrepriseDetails.getNom());

		
		final entreprise updatedEntreprise = entrepriseRepository.save(ese);
		return ResponseEntity.ok(updatedEntreprise);
	}
	
	
	@DeleteMapping("/entreprises/{id}")
	public Map<String, Boolean> deleteEntreprise(@PathVariable(value = "id") long entrepriseId)throws ResourceNotFoundException {
		entreprise ese = entrepriseRepository.findById(entrepriseId).orElseThrow(() -> new ResourceNotFoundException("Entreprise not found for this id :: " + entrepriseId));

		ese.setDelete(true);
		
		final entreprise updatedEntreprise = entrepriseRepository.save(ese);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/entreprises")
	public Map<String, Boolean> deleteAllEntreprises()throws ResourceNotFoundException {

		List<entreprise> allEntreprise=this.entrepriseRepository.findAll();

		for(entreprise e:allEntreprise) {
			e.setDelete(true);
			final entreprise updatedEntreprise = entrepriseRepository.save(e);
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
