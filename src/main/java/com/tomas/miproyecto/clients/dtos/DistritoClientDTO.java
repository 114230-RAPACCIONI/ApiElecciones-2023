package com.tomas.miproyecto.clients.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistritoClientDTO {

    @JsonProperty("distritoId")
    private Long id;
    @JsonProperty("distritoNombre")
    private String name;

}
