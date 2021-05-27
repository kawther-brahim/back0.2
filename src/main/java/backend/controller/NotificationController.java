package backend.controller;

import backend.exception.ResourceNotFoundException;
import backend.model.notification;
import backend.model.salle;
import backend.repository.NotificationRepository;
import backend.repository.PersonneRepository;
import backend.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class NotificationController {

    @Autowired
    private Service service;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PersonneRepository personneRepository;

    @GetMapping("/notifyAdmin/{cin}")
    public void notifyAdmin(@PathVariable long cin)
    {
        this.service.generateNotificationsForAdmin(cin);
    }
    @GetMapping("/notifyUser/{cin}")
    public void notifyUser(@PathVariable long cin)
    {
        this.service.generateNotificationsToUser(cin);
    }

    @PostMapping("/personne/{personneCin}/notify")
    public notification addNotif(@PathVariable long personneCin,@Valid @RequestBody notification n)
    {
        try {
            return personneRepository.findById(personneCin).map(personne -> { n.setPersonne(personne);
            n.setViewed(false);
            return notificationRepository.save(n);
            }).orElseThrow(() -> new ResourceNotFoundException("notification not found"));
        } catch (ResourceNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return n;
    }

    @GetMapping("/notific/{id}")
    public void updateNot(@PathVariable(value = "id") long PersonneId){
        this.notificationRepository.update(PersonneId);

    }

    @GetMapping("/notif/{cinP}")
    public List<notification> getAllNotification(@PathVariable long cinP)
    {
       return this.notificationRepository.findAllNotif(cinP);
    }

    @GetMapping("/notifs/{cinP}")
    public int getNbNotif(@PathVariable long cinP){
        return this.notificationRepository.nbNotif(cinP);
    }

}
