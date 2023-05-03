package com.example.findmypet.config.map;

import com.example.findmypet.dto.AddressMunicipalityDTO;
import com.example.findmypet.exceptions.CouldNotFetchAddressAndMunicipalityException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapUtil {

    public static AddressMunicipalityDTO getAddressAndMunicipality(Double latitude, Double longitude){
        AddressMunicipalityDTO addressMunicipalityDTO = new AddressMunicipalityDTO();
        try {
            String urlString = "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + latitude + "&lon=" + longitude;
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            String address = jsonObject.getString("name");
            String municipality = jsonObject.getJSONObject("address").getString("municipality");

            addressMunicipalityDTO.setAddress(address);
            addressMunicipalityDTO.setMunicipality(municipality);
        }
        catch (Exception e){
            throw new CouldNotFetchAddressAndMunicipalityException();
        }

        return addressMunicipalityDTO;
    }

}
