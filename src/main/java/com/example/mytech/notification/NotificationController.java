package com.example.mytech.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    @Autowired
    NotificationService service;

    @PostMapping
    public String senNotificationByToken(@RequestBody NotificationMessage notificationMessage ){
        return firebaseMessagingService.sendNotificationByToken(notificationMessage);
    }



    @PostMapping("/course/{courseId}")
    public ResponseEntity<String> sendNotificationToAllUsersInCourse(@PathVariable String courseId,
                                                                     @RequestBody NotificationMessage notificationMessage) {
        try {
            service.sendNotificationToAllUsersInCourse(courseId, notificationMessage.getTitle(),
                    notificationMessage.getBody());
            return ResponseEntity.ok("Success Sending Notifications");
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Sending Notifications");
        }
    }
}
