package com.tomas.miproyecto.controllers;

import com.tomas.miproyecto.dtos.CargosByDistritoDTO;
import com.tomas.miproyecto.dtos.DistritoDTO;
import com.tomas.miproyecto.dtos.ResultadoDTO;
import com.tomas.miproyecto.dtos.SeccionesByDitstritoDTO;
import com.tomas.miproyecto.services.EleccionesServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EleccionesController {

    @Autowired
    EleccionesServicesInterface eleccionesServicesInterface;


    @GetMapping("/distritos")
    public ResponseEntity<List<DistritoDTO>> getDistritos(@RequestParam(value = "distrito_nombre", required = false) String distrito_nombre) {

        return ResponseEntity.ok(eleccionesServicesInterface.getDistritos(distrito_nombre));
    }

    @GetMapping("/cargos")
    public ResponseEntity<CargosByDistritoDTO> getCargosByDistrito(@RequestParam(value = "distrito_id", required = true) Long distrito_id) {

        return ResponseEntity.ok(eleccionesServicesInterface.getCargosByDistrito(distrito_id));
    }

    @GetMapping("/secciones")
    public ResponseEntity<List<SeccionesByDitstritoDTO>> getSeccionesByDistrito(
            @RequestParam(value = "distrito_id", required = true) Long distrito_id,
            @RequestParam(value = "seccion_id", required = false) Long seccion_id) {

        return ResponseEntity.ok(eleccionesServicesInterface.getSeccionesByDistrito(distrito_id, seccion_id));
    }

    @GetMapping("/resultados")
    public ResponseEntity<ResultadoDTO> getResultados(
            @RequestParam(value = "distrito_id", required = true) Long distrito_id,
            @RequestParam(value = "seccion_id", required = true) Long seccion_id) {

        return ResponseEntity.ok(eleccionesServicesInterface.getResultados(distrito_id, seccion_id));
    }

}
