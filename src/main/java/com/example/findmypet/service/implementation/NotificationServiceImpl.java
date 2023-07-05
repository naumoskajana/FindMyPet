package com.example.findmypet.service.implementation;

import com.example.findmypet.dto.NotificationDTO;
import com.example.findmypet.entity.pets.SeenPet;
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
    public void sendNotification(String title, String content, NotificationType notificationType, SeenPet seenPet) {
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(title)
                            .setBody(content)
                            .build())
                    .setToken(seenPet.getLostPet().getPetOwner().getDeviceToken())
                    .build();

            FirebaseMessaging.getInstance().send(message);

            com.example.findmypet.entity.user.Notification notification = new com.example.findmypet.entity.user.Notification();
            notification.setTitle(title);
            notification.setBody(content);
            notification.setUserEmail(seenPet.getLostPet().getPetOwner().getEmail());
            notification.setNotificationType(notificationType);
            notification.setSeenPet(seenPet);
            notificationRepository.save(notification);
        }
        catch (FirebaseMessagingException e){
            System.out.println(e);
        }
    }

    @Override
    public List<NotificationDTO> findAllByUser(String userEmail) {
        return notificationRepository.findAllByUser(userEmail).stream().map(com.example.findmypet.entity.user.Notification::getAsNotificationDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }

}
