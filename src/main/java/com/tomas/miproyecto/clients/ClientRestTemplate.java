package com.tomas.miproyecto.clients;


import com.tomas.miproyecto.clients.dtos.CargoClientDTO;
import com.tomas.miproyecto.clients.dtos.DistritoClientDTO;
import com.tomas.miproyecto.clients.dtos.ResultadoClientDTO;
import com.tomas.miproyecto.clients.dtos.SeccionClientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClientRestTemplate {

    RestTemplate restTemplate = new RestTemplate();

    @Value("${api.url}")
    private String URL;

    public ResponseEntity<DistritoClientDTO[]> getDistritos() {

        ResponseEntity<DistritoClientDTO[]> response = null;
        try {
            response = restTemplate.getForEntity(URL + "distritos", DistritoClientDTO[].class);
            if (response.getStatusCode().equals("200")) {
                return response;
            }
        } catch (Exception ex) {
            System.out.println("ERROR" + ex);
        }
        return response;
    }

    public ResponseEntity<CargoClientDTO[]> getCargos() {

        ResponseEntity<CargoClientDTO[]> response = null;
        try {
            response = restTemplate.getForEntity(URL + "cargos", CargoClientDTO[].class);
            if (response.getStatusCode().equals("200")) {
                return response;
            }
        } catch (Exception ex) {
            System.out.println("ERROR" + ex);
        }
        return response;
    }

    public ResponseEntity<SeccionClientDTO[]> getSeccionesByDistrito() {

        ResponseEntity<SeccionClientDTO[]> response = null;
        try {
            response = restTemplate.getForEntity(URL + "secciones", SeccionClientDTO[].class);
            if (response.getStatusCode().equals("200")) {
                return response;
            }
        } catch (Exception ex) {
            System.out.println("ERROR" + ex);
        }
        return response;
    }

    public ResponseEntity<ResultadoClientDTO[]> getResultados(Long seccionId) {

        ResponseEntity<ResultadoClientDTO[]> response = null;
        try {
            response = restTemplate.getForEntity(URL + "resultados?seccionId=" + seccionId, ResultadoClientDTO[].class);
            if (response.getStatusCode().equals("200")) {
                return response;
            }
        } catch (Exception ex) {
            System.out.println("ERROR" + ex);
        }
        return response;
    }
}
