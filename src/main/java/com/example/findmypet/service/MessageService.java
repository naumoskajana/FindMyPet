package com.example.findmypet.service;

import com.example.findmypet.dto.MessageDTO;
import com.example.findmypet.dto.SendMessageDTO;

import java.util.List;

public interface MessageService {

    void sendMessage(SendMessageDTO sendMessageDTO);
    List<MessageDTO> findAllBySenderAndRecipient(String senderEmail, String recipientEmail);

}
