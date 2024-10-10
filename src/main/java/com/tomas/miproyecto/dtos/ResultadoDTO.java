package com.tomas.miproyecto.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoDTO {

    private String distrito;
    private String seccion;
    private List<ResultadoInnerResultadoDTO> resultados = new ArrayList<>();
}
