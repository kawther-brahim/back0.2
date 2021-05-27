package backend.service;

import backend.model.notification;
import backend.model.salle;
import backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@org.springframework.stereotype.Service
public class Service {


        @Autowired
        private SimpMessagingTemplate template;

        @Autowired
        private NotificationRepository notificationRepository;

        public void sendToUser(String message)
        {
            notification n=new notification(message);
            this.template.convertAndSend("/topic/user",n);
        }
        public void sendToAdmin(String message)
        {
            notification n=new notification(message);



                   this.template.convertAndSend("/topic/admin",n);



        }
        public void generateNotificationsForAdmin(long cin) {

                /*for(int i=0;i<5;i++)
                {
                    int x=i+1;*/
                    //this.sendToAdmin("Notification sent from user : "+x);

        notification n =this.notificationRepository.getNotification(cin);

                        this.sendToAdmin(n.getContent());




                //}
        }
        public void generateNotificationsToUser(long cin) {
            notification n =this.notificationRepository.getNotification(cin);

            this.sendToUser(n.getContent());

        }
}
