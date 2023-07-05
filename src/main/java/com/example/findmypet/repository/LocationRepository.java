package com.example.findmypet.repository;

import com.example.findmypet.entity.location.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(nativeQuery = true, value = "SELECT DISTINCT municipality FROM public.locations;")
    List<String> getUniqueMunicipalities();
}
