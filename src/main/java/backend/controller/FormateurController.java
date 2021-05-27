package backend.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import backend.model.*;
import backend.repository.CertificationRepository;
import backend.repository.ExperienceRepository;
import backend.repository.Parcours_scolaireRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import backend.exception.ResourceNotFoundException;
import backend.repository.FormateurRepository;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class FormateurController {
	
	
	@Autowired
	private FormateurRepository formateur;

    @Autowired
    ServletContext context;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private Parcours_scolaireRepository parcours_scolaireRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private CertificationRepository certificationRepository;


    @GetMapping("/formateurs")
    public List<formateur> getAllFormateur()
    {
        List<formateur> formateurs=new ArrayList<>();
        List<formateur> allFormateur=this.formateur.findAll();

        for(formateur f:allFormateur) {
            if (f.isDelete()==false) {
                formateurs.add(f);
            }
        }
        return formateurs;
    }

    @GetMapping("/archive/formateurs")
    public List<formateur> getArchiveFormateur()
    {
        List<formateur> formateurs=new ArrayList<>();
        List<formateur> allFormateur=this.formateur.findAll();

        for(formateur f:allFormateur) {
            if (f.isDelete()==true) {
                formateurs.add(f);
            }
        }
        return formateurs;
    }

    @GetMapping("/formateur/{Id}")
    public formateur getFormateur(@PathVariable(value = "Id") long Id) throws ResourceNotFoundException {
        formateur form = formateur.findById(Id).orElseThrow(() -> new ResourceNotFoundException("formateur not found for this id :: " + Id));
        return form;
    }

    @GetMapping("/formateurs/dispo/{dated}/{datef}")
    public List<formateur> getDisposFormateur(@PathVariable(value = "dated") Date dated, @PathVariable(value = "datef") Date datef){
        return formateur.getDispoFormateur(dated,datef);
    }

	@GetMapping("/formateurs/{username}")
	public long getFormateurById(@PathVariable(value = "username") String username){
		return formateur.getFormateurId(username);
	}

    @PostMapping("/formateurs")
    public formateur addFormateur(@Valid @RequestBody formateur f) {
        f.setPassword(encoder.encode(f.getPassword()));
        f.setRole("formateur");
        f.setDelete(false);
        return this.formateur.save(f);
    }
    @PutMapping("/formateurs/{id}")
    public ResponseEntity<formateur> updateFormateur(@PathVariable(value = "id") long Id,@Valid @RequestBody formateur formateurDetails)
            throws ResourceNotFoundException {
        formateur form = formateur.findById(Id).orElseThrow(() -> new ResourceNotFoundException("formateur not found for this id :: " + Id));


        form.setCin(formateurDetails.getCin());
        form.setUsername(formateurDetails.getUsername());
        form.setPrenom(formateurDetails.getPrenom());
        form.setAdresse(formateurDetails.getAdresse());
        form.setEmail(formateurDetails.getEmail());
        form.setPassword(formateurDetails.getPassword());
        form.setTel(formateurDetails.getTel());
        form.setCompetence(formateurDetails.getCompetence());
        form.setTypeformateur(formateurDetails.getTypeformateur());

        final formateur updatedFormateur = formateur.save(form);
        return ResponseEntity.ok(updatedFormateur);
    }


    @PutMapping("/formateur/{id}")
    public ResponseEntity<formateur> updateFormateurAd(@PathVariable(value = "id") long Id,@Valid @RequestBody formateur formateurDetails)
            throws ResourceNotFoundException {
        formateur form = formateur.findById(Id).orElseThrow(() -> new ResourceNotFoundException("formateur not found for this id :: " + Id));



        form.setUsername(formateurDetails.getUsername());
        form.setPrenom(formateurDetails.getPrenom());
        form.setAdresse(formateurDetails.getAdresse());
        form.setEmail(formateurDetails.getEmail());
        form.setPassword(form.getPassword());
        form.setTel(formateurDetails.getTel());
        form.setTypeformateur(formateurDetails.getTypeformateur());

        final formateur updatedFormateur = formateur.save(form);
        return ResponseEntity.ok(updatedFormateur);
    }

    @DeleteMapping("/formateurs/{id}")
    public Map<String, Boolean> deleteFormateur(@PathVariable(value = "id") long Id)
            throws ResourceNotFoundException {
        formateur form = formateur.findById(Id).orElseThrow(() -> new ResourceNotFoundException("formateur not found for this id :: " + Id));

        form.setDelete(true);
        final formateur updatedFormateur = formateur.save(form);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PostMapping("/formateur/{cin}")
    public formateur addPhoto (@PathVariable(value = "cin") long cin,@RequestParam("file") MultipartFile file) throws JsonParseException, JsonMappingException, Exception
    {
        System.out.println("Ok .............");
        formateur pers = formateur.findById(cin).orElseThrow(() -> new ResourceNotFoundException("Formateur not found for this id :: " + cin));

        boolean isExit = new File(context.getRealPath("/photoF/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/photoF/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File (context.getRealPath("/photoF/"+File.separator+newFileName));
        try
        {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

        }catch(Exception e) {
            e.printStackTrace();
        }


        pers.setFileName(newFileName);
        formateur f1 = formateur.save(pers);
        return f1;
    }

    @GetMapping(path="/Imgformateurs/{cin}")
    public byte[] getPhoto(@PathVariable("cin") Long cin) throws Exception{
        formateur f   = formateur.findById(cin).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/photoF/")+f.getFileName()));
    }
    //select * from demandedevis i inner join client_passager c on c.cin=i.client_id inner join formation e on e.id=i.formation_id where client_id=11827219 and etat='Acceptée'




	@DeleteMapping("/formateurs")
	public Map<String, Boolean> deleteAllFormateur()throws ResourceNotFoundException {

		List<formateur> allFormateur=this.formateur.findAll();

		for(formateur f:allFormateur) {
			f.setDelete(true);

			final formateur updatedFormateur = formateur.save(f);
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

    @PostMapping("certification/formateur/{cin}")
    public certification addCertification(@PathVariable long cin, @Valid @RequestBody certification e) {

        try {
            return formateur.findById(cin).map(formateur -> { e.setFormateur(formateur);
                return certificationRepository.save(e);
            }).orElseThrow(() -> new ResourceNotFoundException("certification not found"));
        } catch (ResourceNotFoundException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        return e;

    }

    @GetMapping("/certification/formateur/{cin}")
    public List<certification> getCertificationByFormateur(@PathVariable long cin){
        return this.certificationRepository.findByFormateurCin(cin, Sort.by("datedeb").descending());

    }
    @PutMapping("/formateur/{cin}/certification/{id}")
    public ResponseEntity<certification> updateCertif(@PathVariable(value = "cin") long cin,@PathVariable(value = "id") long Id,@Valid @RequestBody certification exp)
            throws ResourceNotFoundException {
        certification exper = certificationRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("certification not found for this id :: " + Id));

        exper.setDatedeb(exp.getDatedeb());
        exper.setNom(exp.getNom());
        final certification updatedExp = certificationRepository.save(exper);
        return ResponseEntity.ok(updatedExp);
    }

    @PostMapping("experience/formateur/{cin}")
    public experience addExperience(@PathVariable long cin, @Valid @RequestBody experience e) {

        try {
            return formateur.findById(cin).map(formateur -> { e.setFormateur(formateur);
                return experienceRepository.save(e);
            }).orElseThrow(() -> new ResourceNotFoundException("materiel not found"));
        } catch (ResourceNotFoundException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        return e;

    }

    @GetMapping("/experience/formateur/{cin}")
    public List<experience> getExperienceByFormateur(@PathVariable long cin){
        return this.experienceRepository.findByFormateurCin(cin, Sort.by("datedeb").descending());

    }

    @PostMapping("parcours/formateur/{cin}")
    public parcours_scolaire addParcours(@PathVariable long cin, @Valid @RequestBody parcours_scolaire e) {

        try {
            return formateur.findById(cin).map(formateur -> { e.setFormateur(formateur);
                return parcours_scolaireRepository.save(e);
            }).orElseThrow(() -> new ResourceNotFoundException("materiel not found"));
        } catch (ResourceNotFoundException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        return e;

    }

    @GetMapping("/parcours/formateur/{cin}")
    public List<parcours_scolaire> getParcoursByFormateur(@PathVariable long cin){
        return this.parcours_scolaireRepository.findByFormateurCin(cin,Sort.by("datedeb").descending());

    }
    @PutMapping("/formateur/{cin}/experience/{id}")
    public ResponseEntity<experience> updateExperience(@PathVariable(value = "cin") long cin,@PathVariable(value = "id") long Id,@Valid @RequestBody experience exp)
            throws ResourceNotFoundException {
        experience exper = experienceRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("experience not found for this id :: " + Id));

        exper.setDatedeb(exp.getDatedeb());
        exper.setDatefin(exp.getDatefin());
        exper.setDescription(exp.getDescription());
        exper.setPoste(exp.getPoste());
        exper.setSociete(exp.getSociete());

        final experience updatedExp = experienceRepository.save(exper);
        return ResponseEntity.ok(updatedExp);
    }
    @PutMapping("/formateur/{cin}/parcours/{id}")
    public ResponseEntity<parcours_scolaire> updateParcours(@PathVariable(value = "cin") long cin,@PathVariable(value = "id") long Id,@Valid @RequestBody parcours_scolaire exp)
            throws ResourceNotFoundException {
        parcours_scolaire exper = parcours_scolaireRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("parcours_scolaire not found for this id :: " + Id));

        exper.setDatedeb(exp.getDatedeb());
        exper.setDatefin(exp.getDatefin());
        exper.setEtablissement(exp.getEtablissement());
        exper.setSpecialite(exp.getSpecialite());
        final parcours_scolaire updatedExp = parcours_scolaireRepository.save(exper);
        return ResponseEntity.ok(updatedExp);
    }
}
