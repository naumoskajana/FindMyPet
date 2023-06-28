package com.example.findmypet.service.implementation;

import com.example.findmypet.dto.NotificationDTO;
import com.example.findmypet.enumeration.NotificationType;
import com.example.findmypet.repository.NotificationRepository;
import com.example.findmypet.service.NotificationService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void sendNotification(String title, String content, String token, NotificationType notificationType) {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(content)
                            .build())
                    .setToken(token)
                    .build();

            FirebaseMessaging.getInstance().send(message);

            com.example.findmypet.entity.user.Notification notification = new com.example.findmypet.entity.user.Notification();
            notification.setTitle("Нова локација");
            notification.setBody(content);
            notification.setToken(token);
            notification.setNotificationType(notificationType);
            notificationRepository.save(notification);
        }
        catch (FirebaseMessagingException e){
            System.out.println(e);
        }
    }

    @Override
    public List<NotificationDTO> findAllByUser(String token) {
        return notificationRepository.findAllByUser(token).stream().map(com.example.findmypet.entity.user.Notification::getAsNotificationDTO).collect(Collectors.toList());
    }

}
