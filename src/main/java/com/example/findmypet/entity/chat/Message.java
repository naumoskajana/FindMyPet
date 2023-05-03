package com.example.findmypet.entity.chat;

import com.example.findmypet.dto.MessageDTO;
import com.example.findmypet.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private User recipient;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_at")
    private Date sentAt;

    @JsonIgnore
    public MessageDTO getAsMessageDTO(){
        MessageDTO messageDTO = new MessageDTO();
        if (this.sender != null) {
            messageDTO.setSender(this.sender.getAsUserDTO());
        }
        if (this.recipient != null){
            messageDTO.setRecipient(this.recipient.getAsUserDTO());
        }
        messageDTO.setContent(this.content);
        messageDTO.setSentAt(this.sentAt);
        return messageDTO;
    }

}
