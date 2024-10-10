package com.tomas.miproyecto.clients.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoClientDTO {

    private Long id;
    private String eleccionTipo;
    private String recuentoTipo;
    private String padronTipo;
    private Long distritoId;
    private String distritoNombre;
    private Long seccionProvincialId;
    private String seccionProvincialNombre;
    private Long seccionId;
    private String seccionNombre;
    private String circuitoId;
    private String circuitoNombre;
    private Long mesaId;
    private String mesaTipo;
    private Long mesaElectores;
    private Long cargoId;
    private String cargoNombre;
    private Long agrupacionId;
    private String agrupacionNombre;
    private String listaNumero;
    private String listaNombre;
    private String votosTipo;
    private BigDecimal votosCantidad;
    private Long anio;
}
