package com.example.findmypet.service;

import com.example.findmypet.dto.NotificationDTO;
import com.example.findmypet.entity.pets.SeenPet;
import com.example.findmypet.enumeration.NotificationType;

import java.util.List;

public interface NotificationService {

    void sendNotification(String title, String content, NotificationType notificationType, SeenPet seenPet);
    List<NotificationDTO> findAllByUser(String userEmail);

}
