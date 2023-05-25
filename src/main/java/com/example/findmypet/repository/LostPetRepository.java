package com.example.findmypet.repository;

import com.example.findmypet.entity.pets.LostPet;
import com.example.findmypet.enumeration.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostPetRepository extends JpaRepository<LostPet, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM lost_pets lp WHERE lp.user_id = :userId")
    List<LostPet> findAllByUser(@Param("userId") Long userId);

    @Query("SELECT lp FROM LostPet lp " +
            "JOIN Location l ON l.id = lp.lostAtLocation.id " +
            "WHERE (:#{#keyword == null} = true OR LOWER(lp.name) LIKE %:#{#keyword}%) " +
            "AND (:#{#petTypes.isEmpty()} = true OR lp.petType IN :#{#petTypes}) " +
            "AND (:#{#municipalities.isEmpty()} = true OR LOWER(l.municipality) IN :#{#municipalities})")
    List<LostPet> findAllSearch(@Param("keyword") String keyword,
                                @Param("petTypes") List<PetType> petTypes,
                                @Param("municipalities") List<String> municipalities);

}
