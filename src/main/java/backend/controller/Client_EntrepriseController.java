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

import backend.model.*;
import backend.repository.ClientRepository;
import backend.repository.VoucherRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;

import backend.repository.Client_EntrepriseRepository;
import backend.repository.EntrepriseRepository;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Client_EntrepriseController {

	
	@Autowired
	private ClientRepository cltERepository;
	
	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	ServletContext context;

	@Autowired
	PasswordEncoder encoder;
	
		
	@GetMapping("/clientsentreprise")
	public List<client> getAllClients(){
		
		List<client> clientsEse=new ArrayList<>();
		List<client> allClients=this.cltERepository.findAll();
		
		for(client c:allClients) {
			if (c.isDelete()==false) {
				clientsEse.add(c);
			}
		}
		return clientsEse;
		
	}
	
	@GetMapping("/archive/clientsentreprise")
	public List<client> getArchiveClients(){
		
		List<client> clientsEse=new ArrayList<>();
		List<client> allClients=this.cltERepository.findAll();
		
		for(client c:allClients) {
			if (c.isDelete()==true) {
				clientsEse.add(c);
			}
		}
		return clientsEse;
		
	}

	@DeleteMapping("/clientsentreprise")
	public Map<String, Boolean> deleteAllClientEntreprises()throws ResourceNotFoundException {

		List<client> allClient=this.cltERepository.findAll();

		for(client c:allClient) {
			c.setDelete(true);
			final client updatedClient = cltERepository.save(c);
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}


	@GetMapping("/voucher/{code}/list")
	public List<client> getClientsByVoucher(@PathVariable String code){
		List<client> clientsEse=new ArrayList<>();
		List<client> allClients =this.cltERepository.findByVoucherCode(code);

		for(client c:allClients) {
			if (c.isDelete()==false) {
				clientsEse.add(c);
			}
		}
		return clientsEse;
	}
	@GetMapping("/entreprises/{entrepriseId}/clientsentreprise")
	public List<client> getClientsByEntreprise(@PathVariable long entrepriseId){
		List<client> clientsEse=new ArrayList<>();
		List<client> allClients= this.cltERepository.findByEntrepriseRcs(entrepriseId);
		for(client c:allClients) {
			if (c.isDelete()==false) {
				clientsEse.add(c);
			}
		}
		return clientsEse;
	}
	@GetMapping("/entreprises/{entrepriseId}/clientsentreprise/archive")
	public List<client> getClientsByEntrepriseArchive(@PathVariable long entrepriseId){
		List<client> clientsEse=new ArrayList<>();
		List<client> allClients= this.cltERepository.findByEntrepriseRcs(entrepriseId);
		for(client c:allClients) {
			if (c.isDelete()==true) {
				clientsEse.add(c);
			}
		}
		return clientsEse;
	}

	@PostMapping("/clientsentreprise")
	public client addClient(@Valid @RequestBody client cltP) {

		cltP.setPassword( encoder.encode(cltP.getPassword()));
		cltP.setDelete(false);
		cltERepository.save(cltP);
		return cltP;
	}

	@PutMapping("/accept/entreprise/{entrepriseId}/clientsentreprise/{cin}")
	public ResponseEntity<client>  accepteClientEntreprise(@PathVariable long entrepriseId,@PathVariable long cin,@Valid @RequestBody client cltP) throws ResourceNotFoundException {

		client c = cltERepository.findById(cin).orElseThrow(() -> new ResourceNotFoundException("Client Entreprise not found for this id :: " + cin));
		entreprise e= entrepriseRepository.findById(entrepriseId).orElseThrow(() -> new ResourceNotFoundException(" Entreprise not found for this id :: " + entrepriseId));
		c.setEntreprise(e);
		final client updatedClient = cltERepository.save(c);
		return ResponseEntity.ok(updatedClient);

	}


	@DeleteMapping("/clientsentreprise/{id}")
	public Map<String, Boolean> deleteClient(@PathVariable(value = "id") long clientId)throws ResourceNotFoundException {
		client c = cltERepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client entreprise not found for this id :: " + clientId));

		c.setDelete(true);
		c.setEntreprise(null);
		final client updatedClient = cltERepository.save(c);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	@GetMapping("/clientP/{Id}")
	public client getClient(@PathVariable(value = "Id") long Id) throws ResourceNotFoundException {
		client clp = cltERepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("client not found for this id :: " + Id));
		return clp;
	}

	@PostMapping("/clientP/{cin}")
	public client addPhoto (@PathVariable(value = "cin") long cin,@RequestParam("file") MultipartFile file) throws JsonParseException, JsonMappingException, Exception
	{
		System.out.println("Ok .............");
		client pers = cltERepository.findById(cin).orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + cin));

		boolean isExit = new File(context.getRealPath("/photoC/")).exists();
		if (!isExit)
		{
			new File (context.getRealPath("/photoC/")).mkdir();
			System.out.println("mk dir.............");
		}
		String filename = file.getOriginalFilename();
		String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
		File serverFile = new File (context.getRealPath("/photoC/"+File.separator+newFileName));
		try
		{
			System.out.println("Image");
			FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

		}catch(Exception e) {
			e.printStackTrace();
		}


		pers.setFileName(newFileName);
		client cp = cltERepository.save(pers);
		return cp;
	}

	@GetMapping(path="/ImgClients/{cin}")
	public byte[] getPhoto(@PathVariable("cin") Long cin) throws Exception{
		client c   = cltERepository.findById(cin).get();
		return Files.readAllBytes(Paths.get(context.getRealPath("/photoC/")+c.getFileName()));
	}

	@GetMapping("/voucher/client/{cin}/examen")
	public List<voucher> getVoucherByClientexamen(@PathVariable long cin){
		List<voucher> vouchers=new ArrayList<>();
		List<voucher> allVoucher= this.voucherRepository.findByClientCin(cin);
		for(voucher v:allVoucher) {
			if (v.getFormation()==null) {
				vouchers.add(v);
			}
		}
		return vouchers;
	}
	@GetMapping("/voucher/client/{cin}/formation")
	public List<voucher> getVoucherByClientformation(@PathVariable long cin){
		List<voucher> vouchers=new ArrayList<>();
		List<voucher> allVoucher= this.voucherRepository.findByClientCin(cin);
		for(voucher v:allVoucher) {
			if (v.getExamen()==null) {
				vouchers.add(v);
			}
		}
		return vouchers;
	}



}

