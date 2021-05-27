package backend.controller;

import backend.exception.ResourceNotFoundException;
import backend.model.*;
import backend.repository.Admin_EntrepriseRepository;
import backend.repository.EntrepriseRepository;
import backend.repository.PersonneRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class Admin_EntrepriseController {


    @Autowired
    private Admin_EntrepriseRepository personne;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    ServletContext context;

    @Autowired
    PasswordEncoder encoder;


    @GetMapping("/admin")
    public List<admin_entreprise> getAllAdmin()
    {
        List<admin_entreprise> adminEse=new ArrayList<>();
        List<admin_entreprise> allAdmin=this.personne.findAll();

        for(admin_entreprise p:allAdmin) {
            if (p.isDelete()==false) {
                adminEse.add(p);
            }
        }
        return adminEse;
    }

    @GetMapping("/admin/archive/{id}")
    public List<admin_entreprise> getArchiveAdmin(@PathVariable long id)
    {
        List<admin_entreprise> adminEse=new ArrayList<>();
        List<admin_entreprise> allAdmin=this.personne.findByEntrepriseRcs(id);

        for(admin_entreprise p:allAdmin) {
            if (p.isDelete()==true) {
                adminEse.add(p);
            }
        }
        return adminEse;
    }

    @GetMapping("/admin/{id}")
    public List<admin_entreprise> getAdminByEntreprise(@PathVariable(value = "id") long Id) throws ResourceNotFoundException {
        return personne.findByEntrepriseRcs(Id);
    }


    @GetMapping("/admins/{cin}")
    public admin_entreprise getAdminByCin(@PathVariable(value = "cin") long cin) throws ResourceNotFoundException {
        return personne.findById(cin).orElseThrow(() -> new ResourceNotFoundException("Admin not found for this id :: " + cin));
    }

    @PostMapping("/entreprises/{entrepriseId}/admin")
    public admin_entreprise addAdmin(@PathVariable long entrepriseId, @Valid @RequestBody admin_entreprise a) {

        try {
            return entrepriseRepository.findById(entrepriseId).map(entreprise -> { a.setEntreprise(entreprise);
                a.setPassword( encoder.encode(a.getPassword()));
                a.setRole("entreprise");
            a.setDelete(false);
                return personne.save(a);
            }).orElseThrow(() -> new ResourceNotFoundException("entreprise not found"));
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return a;

    }
    
    @PutMapping("/admin/{id}")
    public ResponseEntity<admin_entreprise> updateAdmin(@PathVariable(value = "id") Long adminId,@Valid @RequestBody admin_entreprise admineseDetails)
            throws ResourceNotFoundException {
        admin_entreprise a = personne.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("Admin not found for this id :: " + adminId));

        /*entreprise e=entrepriseRepository.findById(admineseDetails.getEntreprise().getRcs()).orElseThrow(() -> new ResourceNotFoundException("Entreprise not found for this id :: "));
        e=new entreprise(admineseDetails.getEntreprise().getRcs(),e.getNom());*/
        a.setUsername(admineseDetails.getUsername());
        a.setPrenom(admineseDetails.getPrenom());
        a.setAdresse(admineseDetails.getAdresse());
        a.setPassword(admineseDetails.getPassword());
        a.setEmail(admineseDetails.getEmail());
        a.setTel(admineseDetails.getTel());


        final admin_entreprise updatePersonne = personne.save(a);
        return ResponseEntity.ok(updatePersonne);
    }



    @PostMapping("/admin/{cin}")
    public admin_entreprise addPhoto (@PathVariable(value = "cin") long cin,@RequestParam("file") MultipartFile file) throws JsonParseException, JsonMappingException, Exception
    {
        System.out.println("Ok .............");
        admin_entreprise pers = personne.findById(cin).orElseThrow(() -> new ResourceNotFoundException("Admin not found for this id :: " + cin));

        boolean isExit = new File(context.getRealPath("/PhotosA/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/PhotosA/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File (context.getRealPath("/PhotosA/"+File.separator+newFileName));
        try
        {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

        }catch(Exception e) {
            e.printStackTrace();
        }


        pers.setFileName(newFileName);
        admin_entreprise a = personne.save(pers);
        return a;
    }



    @GetMapping(path="/Imgadmin/{cin}")
    public byte[] getPhoto(@PathVariable("cin") Long cin) throws Exception{
        admin_entreprise p   = personne.findById(cin).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/PhotosA/")+p.getFileName()));
    }

    @DeleteMapping("/admins")
    public Map<String, Boolean> deleteAllAdmin()throws ResourceNotFoundException {

        List<admin_entreprise> allAdmin=this.personne.findAll();

        for(admin_entreprise a:allAdmin) {
            a.setDelete(true);

            final admin_entreprise updatedAdmin = personne.save(a);
        }

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/admin/{id}")
    public Map<String, Boolean> deleteAdmin(@PathVariable(value = "id") long Id)
            throws ResourceNotFoundException {
        admin_entreprise form = personne.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Admin not found for this id :: " + Id));

        form.setDelete(true);
        final admin_entreprise updatedFormateur = personne.save(form);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }



















}
