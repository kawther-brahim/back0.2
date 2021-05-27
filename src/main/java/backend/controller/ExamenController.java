package backend.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;

import backend.model.examen;

import backend.repository.ExamenRepository;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ExamenController {
	
	
	@Autowired
	private ExamenRepository examenRepository;

	@Autowired
	ServletContext context;
	
	/*@Autowired
	private ClientRepository client;*/
	
	@GetMapping("/examen")
	public List<examen> getAllExams(){
		List<examen> examens=new ArrayList<>();
		List<examen> allExamen=this.examenRepository.findAll();
		
		for(examen e:allExamen) {
			if (e.isDelete()==false) {
				examens.add(e);
			}
		}
		return examens;
	}


	@GetMapping("/archive/examen")
	public List<examen> getArchiveExams(){
		List<examen> examens=new ArrayList<>();
		List<examen> allExamen=this.examenRepository.findAll();
		
		for(examen e:allExamen) {
			if (e.isDelete()==true) {
				examens.add(e);
			}
		}
		return examens;
	}
	
	
	
	
	/*@PostMapping("/examen")
	public examen addExam(@Valid @RequestBody examen e) {
		e.setDelete(false);
		return this.examenRepository.save(e);
	}*/



	@GetMapping(path="/Imgexamens/{id}")
	public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
		examen ex   = examenRepository.findById(id).get();
		return Files.readAllBytes(Paths.get(context.getRealPath("/Images/")+ex.getFileName()));
	}

	@PostMapping("/examen")
	public examen createArticle (@RequestParam("file") MultipartFile file,
												  @RequestParam("examen") String examen) throws JsonParseException, JsonMappingException, Exception
	{
		System.out.println("Ok .............");
		examen arti = new ObjectMapper().readValue(examen, examen.class);
		boolean isExit = new File(context.getRealPath("/Images/")).exists();
		if (!isExit)
		{
			new File (context.getRealPath("/Images/")).mkdir();
			System.out.println("mk dir.............");
		}
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
		File serverFile = new File (context.getRealPath("/Images/"+File.separator+newFileName));
		try
		{
			System.out.println("Image");
			FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

		}catch(Exception e) {
			e.printStackTrace();
		}


		arti.setFileName(newFileName);
		examen art = examenRepository.save(arti);
		return art;
	}
	@PutMapping("/examen/{id}")
	public ResponseEntity<examen> updateExam(@PathVariable(value = "id") long examenId,@Valid @RequestBody examen examDetails) throws ResourceNotFoundException {
		examen exam = examenRepository.findById(examenId).orElseThrow(() -> new ResourceNotFoundException("Exam not found for this id :: " + examenId));

		exam.setLibelle(examDetails.getLibelle());
		exam.setDescription(examDetails.getDescription());
		exam.setCode(examDetails.getCode());
		exam.setConstructeur(examDetails.getConstructeur());
		exam.setTechnologie(examDetails.getTechnologie());
		exam.setDuree(examDetails.getDuree());
		exam.setPrix(examDetails.getPrix());
		exam.setNiveau(examDetails.getNiveau());
	
		final examen updatedExam = examenRepository.save(exam);
		return ResponseEntity.ok(updatedExam);
	}



	/*@PutMapping("/examen/{id}")
	public ResponseEntity<examen> updateExam(@PathVariable(value = "id") long examenId,@RequestParam("file") MultipartFile file,
											 @Valid @RequestBody examen examDetails) throws JsonParseException, JsonMappingException, Exception ,ResourceNotFoundException {
		examen exam = examenRepository.findById(examenId).orElseThrow(() -> new ResourceNotFoundException("Exam not found for this id :: " + examenId));

		System.out.println("Ok .............");

		boolean isExit = new File(context.getRealPath("/Images/")).exists();
		if (!isExit)
		{
			new File (context.getRealPath("/Images/")).mkdir();
			System.out.println("mk dir.............");
		}
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
		File serverFile = new File (context.getRealPath("/Images/"+File.separator+newFileName));
		try
		{
			System.out.println("Image");
			FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

		}catch(Exception e) {
			e.printStackTrace();
		}


		exam.setFileName(newFileName);
		exam.setLibelle(examDetails.getLibelle());
		exam.setDescription(examDetails.getDescription());

		exam.setPrix(examDetails.getPrix());
		exam.setNiveau(examDetails.getNiveau());

		final examen updatedExam = examenRepository.save(exam);
		return ResponseEntity.ok(updatedExam);
	}*/


	@DeleteMapping("/examen/{id}")
	public Map<String, Boolean> deleteExam(@PathVariable(value = "id") long examenId)throws ResourceNotFoundException {
		examen exam = examenRepository.findById(examenId).orElseThrow(() -> new ResourceNotFoundException("Exam not found for this id :: " + examenId));

		exam.setDelete(true);
		final examen updatedExam = examenRepository.save(exam);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


	@DeleteMapping("/examen")
	public Map<String, Boolean> deleteAllExams()throws ResourceNotFoundException {

		List<examen> allExamen=this.examenRepository.findAll();

		for(examen e:allExamen) {


			e.setDelete(true);
			final examen updatedExam = examenRepository.save(e);

		}
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}



	
	
	/*@PostMapping("/examen/{idexamen}/client/{idclient}")
	public String inscription(@PathVariable(value = "idclient") int clientId,@PathVariable(value = "idexamen")  int examenId) {
		
		
		try {
			client cl = client.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client not found for this id :: "));
			examen e = examenRepository.findById(examenId).orElseThrow(() -> new ResourceNotFoundException("Examen not found for this id :: " ));
			cl.getExamens().add(e);
			e.getClients().add(cl);
			examenRepository.save(e);
			
			return "inscription effectué avec succes";
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "no";
		
	}*/

	

}
