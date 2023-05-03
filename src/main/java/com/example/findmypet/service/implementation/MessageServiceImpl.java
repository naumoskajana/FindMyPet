package com.example.findmypet.service.implementation;

import com.example.findmypet.dto.MessageDTO;
import com.example.findmypet.dto.SendMessageDTO;
import com.example.findmypet.entity.chat.Message;
import com.example.findmypet.entity.user.User;
import com.example.findmypet.repository.MessageRepository;
import com.example.findmypet.service.MessageService;
import com.example.findmypet.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository,
                              SimpMessagingTemplate simpMessagingTemplate,
                              UserService userService) {
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    @Override
    public void sendMessage(SendMessageDTO sendMessageDTO) {
        Message message = new Message();
        User sender = userService.findByEmail(sendMessageDTO.getSenderEmail());
        message.setSender(sender);
        User recipient = userService.findByEmail(sendMessageDTO.getRecipientEmail());
        message.setRecipient(recipient);
        message.setContent(sendMessageDTO.getContent());
        Date now = new Date();
        message.setSentAt(now);
        messageRepository.save(message);
        simpMessagingTemplate.convertAndSendToUser(message.getRecipient().getEmail(), "/queue/messages", message);
    }

    @Override
    public List<MessageDTO> findAllBySenderAndRecipient(String senderEmail, String recipientEmail) {
        User sender = userService.findByEmail(senderEmail);
        User recipient = userService.findByEmail(recipientEmail);
        return messageRepository.findBySenderAndRecipient(sender.getId(), recipient.getId())
                .stream()
                .map(Message::getAsMessageDTO)
                .collect(Collectors.toList());
    }
}
