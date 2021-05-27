package backend.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import backend.model.client;
import backend.model.clientPassager;
import backend.model.demandedevis;
import backend.model.formateur;
import backend.repository.ClientRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;

import backend.repository.Client_PassagerRepository;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Client_PassagerController {
	
	@Autowired
	private ClientRepository cltPRepository;

	@Autowired
	ServletContext context;

	@Autowired
	PasswordEncoder encoder;


	@GetMapping("/clientpassagers")
	public List<client> getAllClient(){
		List<client> cltPassagers=new ArrayList<>();
		List<client> allClt=this.cltPRepository.findAll();
		
		for(client c:allClt) {
			if (c.isDelete()==false) {
				cltPassagers.add(c);
			}
		}
		return cltPassagers;
	}
	
	@GetMapping("/archive/clientpassagers")
	public List<client> getArchiveClient(){
		List<client> cltPassagers=new ArrayList<>();
		List<client> allClt=this.cltPRepository.findAll();
		
		for(client c:allClt) {
			if (c.isDelete()==true) {
				cltPassagers.add(c);
			}
		}
		return cltPassagers;
	}

	@PostMapping("/clientpassagers")
	public clientPassager addClient(@Valid @RequestBody clientPassager c) {
		c.setPassword( encoder.encode(c.getPassword()));
		c.setRole("client");
		c.setDelete(false);
		return this.cltPRepository.save(c);
	}
	
	
	@PutMapping("/clientpassagers/{id}")
	public ResponseEntity<client> updateClient(@PathVariable(value = "id") long clientId,@Valid @RequestBody clientPassager clientDetails) throws ResourceNotFoundException {
		client cltP = cltPRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client passager not found for this id :: " + clientId));

		cltP.setUsername(clientDetails.getUsername());
		cltP.setPrenom(clientDetails.getPrenom());
		cltP.setEmail(clientDetails.getEmail());
		cltP.setPassword(clientDetails.getPassword());
		cltP.setTel(clientDetails.getTel());
		cltP.setAdresse(clientDetails.getAdresse());
		cltP.setNiveau(clientDetails.getNiveau());
		
		final client updatedClient = cltPRepository.save(cltP);
		return ResponseEntity.ok(updatedClient);
	}
	
	

	@DeleteMapping("/clientpassagers")
	public Map<String, Boolean> deleteAllClientPassagers()throws ResourceNotFoundException {

		List<client> allClient=this.cltPRepository.findAll();

		for(client c:allClient) {
			c.setDelete(true);
			final client updatedClient = cltPRepository.save(c);
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}
