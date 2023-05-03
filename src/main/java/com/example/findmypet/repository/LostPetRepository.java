package com.example.findmypet.repository;

import com.example.findmypet.entity.pets.LostPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostPetRepository extends JpaRepository<LostPet, Long> {

    @Query(nativeQuery = true, value = "SELECT * from lost_pets lp where lp.user_id = :userId")
    List<LostPet> findAllByUser(@Param("userId") Long userId);

}
