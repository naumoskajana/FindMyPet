package com.example.findmypet.entity.location;

import com.example.findmypet.dto.LocationDTO;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coordinates_id")
    private Coordinate coordinates;

    @Column(name = "municipality")
    private String municipality;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    public LocationDTO getAsLocationDTO(){
        LocationDTO locationDTO = new LocationDTO();
        if (this.coordinates != null){
            locationDTO.setCoordinates(this.coordinates.getAsCoordinateDTO());
        }
        locationDTO.setMunicipality(this.municipality);
        locationDTO.setAddress(this.address);
        return locationDTO;
    }

}
