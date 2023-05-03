package com.example.findmypet.entity.location;

import com.example.findmypet.dto.CoordinateDTO;
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
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coordinates")
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @JsonIgnore
    public CoordinateDTO getAsCoordinateDTO(){
        CoordinateDTO coordinateDTO = new CoordinateDTO();
        coordinateDTO.setLongitude(this.longitude);
        coordinateDTO.setLatitude(this.latitude);
        return coordinateDTO;
    }

}
