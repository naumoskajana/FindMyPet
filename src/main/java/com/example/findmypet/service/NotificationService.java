package com.example.findmypet.service;

import com.example.findmypet.entity.user.Notification;

import java.util.List;

public interface NotificationService {

    void sendNotification(String content, String token);
    List<Notification> findAllByUser(String token);

}
