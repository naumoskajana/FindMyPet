package com.example.findmypet.web;

import com.example.findmypet.dto.MessageDTO;
import com.example.findmypet.dto.SendMessageDTO;
import com.example.findmypet.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final MessageService messageService;

    public ChatController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public void sendMessage(@RequestBody SendMessageDTO sendMessageDTO) {
        messageService.sendMessage(sendMessageDTO);
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessages(@RequestParam("senderEmail") String senderEmail,
                                                        @RequestParam("recipientEmail") String recipientEmail) {
        return ResponseEntity.ok(messageService.findAllBySenderAndRecipient(senderEmail, recipientEmail));
    }

}
