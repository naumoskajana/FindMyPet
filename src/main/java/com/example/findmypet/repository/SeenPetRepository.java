package com.example.findmypet.repository;

import com.example.findmypet.entity.pets.SeenPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeenPetRepository extends JpaRepository<SeenPet, Long> {

    @Query(nativeQuery = true, value = "SELECT * from seen_pets sp where sp.lost_pet_id = :lostPetId")
    List<SeenPet> findAllByLostPet(@Param("lostPetId") Long lostPetId);

}
