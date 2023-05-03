package com.example.findmypet.repository;

import com.example.findmypet.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(nativeQuery = true, value = "SELECT id, sender_id, recipient_id, content, sent_at from messages m WHERE (m.sender_id = :senderId and m.recipient_id = :recipientId) OR (m.sender_id = :recipientId and m.recipient_id = :senderId)")
    List<Message> findBySenderAndRecipient(@Param("senderId") Long senderId, @Param("recipientId") Long recipientId);

}
