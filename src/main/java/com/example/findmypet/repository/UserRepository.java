package com.example.findmypet.repository;

import com.example.findmypet.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "SELECT id, first_name, last_name, email, password, user_type, phone_number, activated, device_token from users u where u.email = :email")
    User findByEmail(@Param("email") String email);

}
