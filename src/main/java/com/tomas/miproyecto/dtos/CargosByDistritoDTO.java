package com.tomas.miproyecto.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargosByDistritoDTO {

    private Long id;
    private String nombre;
    private List<CargoDTO> cargos  = new ArrayList<>();
}
