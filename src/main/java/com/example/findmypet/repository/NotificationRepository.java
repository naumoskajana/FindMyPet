package com.example.findmypet.repository;

import com.example.findmypet.entity.user.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM notifications n WHERE n.token = :token")
    List<Notification> findAllByUser(@Param("token") String token);

}
