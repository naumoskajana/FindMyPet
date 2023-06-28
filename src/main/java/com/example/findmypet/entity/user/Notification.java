package com.example.findmypet.entity.user;

import com.example.findmypet.dto.NotificationDTO;
import com.example.findmypet.entity.pets.SeenPet;
import com.example.findmypet.enumeration.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "body")
    private String body;

    @Column(name = "user_email")
    private String userEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "seen_pet_id")
    private SeenPet seenPet;

    @JsonIgnore
    public NotificationDTO getAsNotificationDTO(){
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setTitle(this.title);
        notificationDTO.setBody(this.body);
        notificationDTO.setType(this.notificationType.name());
        notificationDTO.setSeenPetDTO(this.seenPet.getAsSeenPetDTO());
        return notificationDTO;
    }

}
