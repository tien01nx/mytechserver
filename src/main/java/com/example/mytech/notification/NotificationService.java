package com.example.mytech.notification;

import com.example.mytech.entity.UserCourse;
import com.example.mytech.repository.UserCourseRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private  FirebaseMessaging firebaseMessaging;



    public void sendNotification(String title, String body,String token) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(token)
                .build();

        firebaseMessaging.send(message);
    }

    @Autowired
    private UserCourseRepository userCourseRepository;

    public void sendNotificationToAllUsersInCourse(String courseId, String title, String body) throws FirebaseMessagingException {
        List<UserCourse> userCourses = userCourseRepository.findByUser_Id(courseId);

        for (UserCourse userCourse : userCourses) {
            String token = userCourse.getTokenNotification();
            sendNotification("title", "body", token);
        }
    }


}