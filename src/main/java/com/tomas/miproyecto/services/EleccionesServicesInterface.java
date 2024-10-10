package com.tomas.miproyecto.services;


import com.tomas.miproyecto.dtos.CargosByDistritoDTO;
import com.tomas.miproyecto.dtos.DistritoDTO;
import com.tomas.miproyecto.dtos.ResultadoDTO;
import com.tomas.miproyecto.dtos.SeccionesByDitstritoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EleccionesServicesInterface {

    List<DistritoDTO> getDistritos(String distritoNombre);

    CargosByDistritoDTO getCargosByDistrito (Long distritoId);

    List<SeccionesByDitstritoDTO> getSeccionesByDistrito(Long distritoId, Long seccionId);

    ResultadoDTO getResultados(Long distritoId, Long seccionId);
}
