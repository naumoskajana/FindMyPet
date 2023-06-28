package com.example.findmypet.service;

import com.example.findmypet.dto.NotificationDTO;
import com.example.findmypet.enumeration.NotificationType;

import java.util.List;

public interface NotificationService {

    void sendNotification(String title, String content, String token, String userEmail, NotificationType notificationType);
    List<NotificationDTO> findAllByUser(String userEmail);

}
