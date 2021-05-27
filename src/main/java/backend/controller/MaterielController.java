package backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;
import backend.model.materiel;
import backend.model.salle;
import backend.repository.MaterielRepository;
import backend.repository.SalleRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MaterielController {
	
	
	@Autowired
	private MaterielRepository materielRepository;
	
	@Autowired
	private SalleRepository salleRepository;
	
		
	@GetMapping("/materiels")
	public List<materiel> getAllMaterials(){
		
		List<materiel> materiels=new ArrayList<>();
		List<materiel> allMateriel=this.materielRepository.findAll();
		
		for(materiel s:allMateriel) {
			if (s.isDelete()==false) {
				materiels.add(s);
			}
		}
		return materiels;
		
	}
	
	@GetMapping("/materiels/archive")
	public List<materiel> getArchiveMaterials(){
		
		List<materiel> materiels=new ArrayList<>();
		List<materiel> allMateriel=this.materielRepository.findAll();
		
		for(materiel m:allMateriel) {
			if (m.isDelete()==true) {
				materiels.add(m);
			}
		}
		return materiels;
		
	}
	
	
	@GetMapping("/salles/{salleId}/materiels")
	public List<materiel> getMaterialsBySalle(@PathVariable long salleId){
		return this.materielRepository.findBySalleId(salleId);
	}
	
	
	@PostMapping("/salles/{salleId}/materiels")
	public materiel addMateriel(@PathVariable long salleId,@Valid @RequestBody materiel m) {

		try {
			return salleRepository.findById(salleId).map(salle -> { m.setSalle(salle);
			m.setDelete(false);
			return materielRepository.save(m);
			    }).orElseThrow(() -> new ResourceNotFoundException("materiel not found"));
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
			
	}
	
	
	@PutMapping("/materiels/{id}")
	public ResponseEntity<materiel> updateMateriel(@PathVariable(value = "id") String materielId,@Valid @RequestBody materiel materielDetails) throws ResourceNotFoundException {
		materiel mat = materielRepository.findById(materielId).orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + materielId));

		salle s=salleRepository.findById(materielDetails.getSalle().getId()).orElseThrow(() -> new ResourceNotFoundException("Salle not found for this id :: " + materielId));
		s=new salle(materielDetails.getSalle().getId(),s.getNom(),s.getCapacite());
		mat.setNom(materielDetails.getNom());
		mat.setEtat(materielDetails.getEtat());
		mat.setSalle(s);
	
		
		final materiel updatedMateriel = materielRepository.save(mat);
		return ResponseEntity.ok(updatedMateriel);
	}
	
	
	@DeleteMapping("/materiels/{id}")
	public Map<String, Boolean> deleteMateriel(@PathVariable(value = "id") String materielId)throws ResourceNotFoundException {
		materiel mat = materielRepository.findById(materielId).orElseThrow(() -> new ResourceNotFoundException("Materiel not found for this id :: " + materielId));

		mat.setDelete(true);
		final materiel updatedMateriel = materielRepository.save(mat);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@DeleteMapping("/materiels")
	public Map<String, Boolean> deleteAllMateriel()throws ResourceNotFoundException {
		List<materiel> allMateriel=this.materielRepository.findAll();

		for(materiel s:allMateriel) {
			s.setDelete(true);
			final materiel updatedMateriel = materielRepository.save(s);
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	


}
