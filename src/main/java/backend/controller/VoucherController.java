package backend.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import backend.model.*;
import backend.repository.ExamenRepository;
import backend.repository.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;
import backend.repository.EntrepriseRepository;

import backend.repository.VoucherRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class VoucherController {

	
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private EntrepriseRepository entrepriseRepository;

	@Autowired
	private FormationRepository formationRepository;

	@Autowired
	private ExamenRepository examenRepository;
	
	@GetMapping("/vouchers")
	public List<voucher> getAllVouchers(){
		
		List<voucher> vouchers=new ArrayList<>();
		List<voucher> allVoucher=this.voucherRepository.findAll();
		
		for(voucher v:allVoucher) {
			if (v.isDelete()==false) {
				vouchers.add(v);
			}
		}
		return vouchers;
		
	}
	
	@GetMapping("/archive/vouchers")
	public List<voucher> getArchiveVouchers(){
		
		List<voucher> vouchers=new ArrayList<>();
		List<voucher> allVoucher=this.voucherRepository.findAll();
		
		for(voucher v:allVoucher) {
			if (v.isDelete()==true) {
				vouchers.add(v);
			}
		}
		return vouchers;
		
	}
	
	@GetMapping("/entreprises/{entrepriseId}/vouchers")
	public List<voucher> getVouchersByEntreprise(@PathVariable long entrepriseId){
		return this.voucherRepository.findByEntrepriseRcs(entrepriseId);
	}


	@GetMapping("/entreprises/{entrepriseId}/vouchers/formation")
	public List<voucher> getVouchersByEntrepriseFormation(@PathVariable long entrepriseId){
		List<voucher> vouchers=new ArrayList<>();
		List<voucher> allVoucher=this.voucherRepository.findByEntrepriseRcs(entrepriseId);
		for(voucher v:allVoucher) {
			if (v.getExamen()==null) {
				vouchers.add(v);
			}
		}
		return vouchers;
	}
	@GetMapping("/entreprises/{entrepriseId}/vouchers/examen")
	public List<voucher> getVouchersByEntrepriseExamen(@PathVariable long entrepriseId){
		List<voucher> vouchers=new ArrayList<>();
		List<voucher> allVoucher=this.voucherRepository.findByEntrepriseRcs(entrepriseId);
		for(voucher v:allVoucher) {
			if (v.getFormation()==null) {
				vouchers.add(v);
			}
		}
		return vouchers;
	}
	
	@PostMapping("/entreprises/{entrepriseId}/formation/{formationId}/vouchers")
	public voucher addVoucher(@PathVariable long entrepriseId,@PathVariable long formationId,@Valid @RequestBody voucher v) throws ResourceNotFoundException {


			entreprise e= entrepriseRepository.findById(entrepriseId).orElseThrow(() -> new ResourceNotFoundException("entreprise not found  "));
			formation f=  formationRepository.findById(formationId).orElseThrow(() -> new ResourceNotFoundException("formation not found  "));
			if(e !=null && f !=null) {
				v.setFormation(f);
				v.setEntreprise(e);
				v.setDelete(false);
				voucherRepository.save(v);
				return v;
			}
			return null;

	}

	@PostMapping("/entreprises/{entrepriseId}/examen/{examenId}/vouchers")
	public voucher addVoucherExam(@PathVariable long entrepriseId,@PathVariable long examenId,@Valid @RequestBody voucher v) throws ResourceNotFoundException {


		entreprise e= entrepriseRepository.findById(entrepriseId).orElseThrow(() -> new ResourceNotFoundException("entreprise not found  "));
		examen exam=  examenRepository.findById(examenId).orElseThrow(() -> new ResourceNotFoundException("examen not found  "));
		if(e !=null && exam !=null) {
			v.setExamen(exam);
			v.setEntreprise(e);
			v.setDelete(false);
			voucherRepository.save(v);
			return v;
		}
		return null;

	}
	
	
	@PutMapping("/vouchers/{id}")
	public ResponseEntity<voucher> updateVoucher(@PathVariable(value = "id") String voucherId,@Valid @RequestBody voucher voucherDetails) throws ResourceNotFoundException {
		voucher v = voucherRepository.findById(voucherId).orElseThrow(() -> new ResourceNotFoundException("Voucher not found for this id :: " + voucherId));


		v.setCode(voucherDetails.getCode());
		v.setEntreprise(voucherDetails.getEntreprise());
		v.setFormation(voucherDetails.getFormation());
		
		final voucher updatedVoucher = voucherRepository.save(v);
		return ResponseEntity.ok(updatedVoucher);
	}
	
	
	@DeleteMapping("/vouchers/{id}")
	public Map<String, Boolean> deleteVoucher(@PathVariable(value = "id") String voucherId)throws ResourceNotFoundException {
		voucher v = voucherRepository.findById(voucherId).orElseThrow(() -> new ResourceNotFoundException("Voucher not found for this id :: " + voucherId));

		v.setDelete(true);
		final voucher updatedVoucher = voucherRepository.save(v);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	
	
	
	
	
	
}
