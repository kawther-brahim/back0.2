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

import backend.model.service;
import backend.repository.ServiceRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ServiceController {


    @Autowired
    private ServiceRepository serviceRepository;

	/*@Autowired
	private ClientRepository client;*/

    @GetMapping("/services")
    public List<service> getAllServices(){

        List<service> services=new ArrayList<>();
        List<service> allService=this.serviceRepository.findAll();

        for(service o:allService) {
            if (o.isDelete()==false) {
                services.add(o);
            }
        }
        return services;

    }


    @GetMapping("/archive/services")
    public List<service> getArchiveServices(){

        List<service> services=new ArrayList<>();
        List<service> allService=this.serviceRepository.findAll();

        for(service o:allService) {
            if (o.isDelete()==true) {
                services.add(o);
            }
        }
        return services;
    }


    @PostMapping("/services")
    public service addService(@Valid @RequestBody service o) {
        o.setDelete(false);
        return this.serviceRepository.save(o);
    }


    @PutMapping("/services/{id}")
    public ResponseEntity<service> updateService(@PathVariable(value = "id") long serviceId,@Valid @RequestBody service serviceDetails) throws ResourceNotFoundException {
        service o = serviceRepository.findById(serviceId).orElseThrow(() -> new ResourceNotFoundException("Service not found for this id :: " + serviceId));

        o.setLibelle(serviceDetails.getLibelle());
        o.setDescription(serviceDetails.getDescription());
        o.setPrix(serviceDetails.getPrix());
        final service updatedService = serviceRepository.save(o);
        return ResponseEntity.ok(updatedService);
    }


    @DeleteMapping("/services/{id}")
    public Map<String, Boolean> deleteService(@PathVariable(value = "id") long serviceId)throws ResourceNotFoundException {
        service o = serviceRepository.findById(serviceId).orElseThrow(() -> new ResourceNotFoundException("Service not found for this id :: " + serviceId));

        o.setDelete(true);
        final service updatedService = serviceRepository.save(o);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

	/*@PostMapping("/offre/{idoffre}/client/{idclient}")
	public String inscription(@PathVariable(value = "idclient") int clientId,@PathVariable(value = "idoffre")  int offreId) {


		try {
			client cl = client.findById(clientId).orElseThrow(() -> new ResourceNotFoundException("client not found for this id :: "));
			offre o = offreRepository.findById(offreId).orElseThrow(() -> new ResourceNotFoundException("Offre not found for this id :: " ));

			cl.getOffres().add(o);
			o.getClients().add(cl);
			offreRepository.save(o);

			return "inscription effectué avec succes";
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "no";

	}*/

}