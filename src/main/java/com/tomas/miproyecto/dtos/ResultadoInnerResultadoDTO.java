package com.tomas.miproyecto.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoInnerResultadoDTO {

    private Long orden;
    private String nombre;
    private BigDecimal votos;
    private BigDecimal porcentaje;
}
