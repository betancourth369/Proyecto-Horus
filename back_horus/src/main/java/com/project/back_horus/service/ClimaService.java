package com.project.back_horus.service;

import com.project.back_horus.dto.ClimaDTO;
import com.project.back_horus.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
public class ClimaService {

    private final RestTemplate restTemplate = new RestTemplate();

    public ClimaDTO obtenerClima(String ciudad) {
        String apiKey = System.getenv("WEATHERAPI_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró la variable de entorno WEATHERAPI_KEY");
        }

        String url = "https://api.weatherapi.com/v1/current.json"
                + "?key=" + apiKey
                + "&q=" + java.net.URLEncoder.encode(ciudad, java.nio.charset.StandardCharsets.UTF_8)
                + "&lang=es";

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> respuesta = restTemplate.getForObject(url, Map.class);

            if (respuesta == null) {
                throw new ResourceNotFoundException("No se encontró el clima para: " + ciudad);
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> location = (Map<String, Object>) respuesta.get("location");

            @SuppressWarnings("unchecked")
            Map<String, Object> current = (Map<String, Object>) respuesta.get("current");

            @SuppressWarnings("unchecked")
            Map<String, Object> condicion = (Map<String, Object>) current.get("condition");

            ClimaDTO dto = new ClimaDTO();
            dto.setCiudad((String) location.get("name"));
            dto.setRegion((String) location.get("region"));
            dto.setPais((String) location.get("country"));
            dto.setTemperatura(((Number) current.get("temp_c")).doubleValue());
            dto.setSensacionTermica(((Number) current.get("feelslike_c")).doubleValue());
            dto.setHumedad(((Number) current.get("humidity")).intValue());
            dto.setDescripcion((String) condicion.get("text"));
            dto.setHoraLocal((String) location.get("localtime"));

            return dto;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceNotFoundException("No se pudo obtener el clima para: " + ciudad);
        }
    }
}