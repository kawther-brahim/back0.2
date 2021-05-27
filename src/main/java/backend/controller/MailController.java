package backend.controller;

import backend.exception.ResourceNotFoundException;
import backend.model.admin_entreprise;
import backend.model.formateur;
import backend.repository.Admin_EntrepriseRepository;
import backend.repository.FormateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class MailController {

    private formateur f;

    @Autowired
    private FormateurRepository formateur;

    @Autowired
    private Admin_EntrepriseRepository adminEse;

    private JavaMailSender javaMailSender;

    @Autowired
    public MailController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @PostMapping("/sendEmail")
    public void send(@Valid @RequestBody formateur f) throws MailException,ResourceNotFoundException{

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            formateur form = formateur.findById(f.getCin()).orElseThrow(() -> new ResourceNotFoundException("formateur not found for this id :: " ));
            mail.setTo(form.getEmail());
            mail.setSubject("création compte");
            mail.setText("Bonjour, \n\r Votre Compte a été crée : \n\r Login: "+form.getEmail()+". \n\r Password:"+form.getPassword()+". \n\r Bien Cordialement.");
            javaMailSender.send(mail);
        } catch (MailException mailException) {
            System.out.println(mailException);
        }
        //return "Congratulations! Your mail has been send to the user.";
    }

    @PostMapping("/sendEmailAdmin")
    public void sendMail(@Valid @RequestBody admin_entreprise a) throws MailException,ResourceNotFoundException{

        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            admin_entreprise admin = adminEse.findById(a.getCin()).orElseThrow(() -> new ResourceNotFoundException("admin entreprise not found for this id :: " ));
            mail.setTo(admin.getEmail());
            mail.setSubject("création compte");
            mail.setText("Bonjour, \n\r Votre Compte a été crée : \n\r Login: "+admin.getEmail()+". \n\r Password:"+admin.getPassword()+". \n\r Bien Cordialement.");
            javaMailSender.send(mail);
        } catch (MailException mailException) {
            System.out.println(mailException);
        }
        //return "Congratulations! Your mail has been send to the user.";
    }
}
