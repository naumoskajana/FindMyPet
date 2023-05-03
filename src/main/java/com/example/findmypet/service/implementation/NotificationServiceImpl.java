package com.example.findmypet.service.implementation;

import com.example.findmypet.enumeration.NotificationType;
import com.example.findmypet.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(String content, String token) {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle("Нова локација")
                            .setBody(content)
                            .build())
                    .setToken(token)
                    .build();

            FirebaseMessaging.getInstance().send(message);
        }
        catch (FirebaseMessagingException e){
            System.out.println(e);
        }
    }

}
