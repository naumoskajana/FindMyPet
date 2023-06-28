package com.example.findmypet.service;

import com.example.findmypet.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    void sendNotification(String content, String token);
    List<NotificationDTO> findAllByUser(String token);

}
