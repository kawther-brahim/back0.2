package backend.controller;

import backend.exception.ResourceNotFoundException;
import backend.model.examen;
import backend.model.formateur;
import backend.model.personne;
import backend.repository.FormateurRepository;
import backend.repository.PersonneRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class PersonneController {


    @Autowired
    private PersonneRepository personne;

    @Autowired
    ServletContext context;

    @GetMapping("/personnes")
    public List<backend.model.personne> getAllPersonne()
    {
        List<backend.model.personne> personnes=new ArrayList<>();
        List<personne> allPersonne=this.personne.findAll();

        for(personne p:allPersonne) {
            if (p.isDelete()==false) {
                personnes.add(p);
            }
        }
        return personnes;
    }

    @GetMapping("/personnes/archive")
    public List<backend.model.personne> getArchivePersonne()
    {
        List<backend.model.personne> personnes=new ArrayList<>();
        List<personne> allPersonne=this.personne.findAll();

        for(personne p:allPersonne) {
            if (p.isDelete()==true) {
                personnes.add(p);
            }
        }
        return personnes;
    }

    @GetMapping("/personnes/{id}")
    public personne getPersonne(@PathVariable(value = "id") long Id) throws ResourceNotFoundException {
        personne pers = personne.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Personne not found for this id :: " + Id));
        return pers;
    }

    @PostMapping("/personnes")
    public personne addPersonne(@Valid @RequestBody personne p) {
        p.setDelete(false);
        return this.personne.save(p);
    }


    @PutMapping("/personnes/{id}")
    public ResponseEntity<personne> updatePersonne(@PathVariable(value = "id") long Id,@Valid @RequestBody personne personneDetails)
            throws ResourceNotFoundException {
        personne pers = personne.findById(Id).orElseThrow(() -> new ResourceNotFoundException("Personne not found for this id :: " + Id));

        pers.setUsername(personneDetails.getUsername());
        pers.setPrenom(personneDetails.getPrenom());
        pers.setAdresse(personneDetails.getAdresse());

        pers.setEmail(personneDetails.getEmail());
        pers.setPassword(personneDetails.getPassword());
        pers.setTel(personneDetails.getTel());


        final personne updatePersonne = personne.save(pers);
        return ResponseEntity.ok(updatePersonne);
    }



    @PostMapping("/personnes/{cin}")
    public personne addPhoto (@PathVariable(value = "cin") long cin,@RequestParam("file") MultipartFile file) throws JsonParseException, JsonMappingException, Exception
    {
        System.out.println("Ok .............");
        personne pers = personne.findById(cin).orElseThrow(() -> new ResourceNotFoundException("Personne not found for this id :: " + cin));

        boolean isExit = new File(context.getRealPath("/Photos/")).exists();
        if (!isExit)
        {
            new File (context.getRealPath("/Photos/")).mkdir();
            System.out.println("mk dir.............");
        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File (context.getRealPath("/Photos/"+File.separator+newFileName));
        try
        {
            System.out.println("Image");
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

        }catch(Exception e) {
            e.printStackTrace();
        }


        pers.setFileName(newFileName);
        personne p = personne.save(pers);
        return p;
    }



    @GetMapping(path="/Imgpersonnes/{cin}")
    public byte[] getPhoto(@PathVariable("cin") Long cin) throws Exception{
        personne p   = personne.findById(cin).get();
        return Files.readAllBytes(Paths.get(context.getRealPath("/Photos/")+p.getFileName()));
    }











}
