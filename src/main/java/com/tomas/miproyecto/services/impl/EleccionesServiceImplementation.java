package com.tomas.miproyecto.services.impl;

import com.tomas.miproyecto.clients.ClientRestTemplate;
import com.tomas.miproyecto.clients.dtos.CargoClientDTO;
import com.tomas.miproyecto.clients.dtos.ResultadoClientDTO;
import com.tomas.miproyecto.dtos.*;
import com.tomas.miproyecto.services.EleccionesServicesInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class EleccionesServiceImplementation implements EleccionesServicesInterface {

    @Autowired
    ClientRestTemplate clientRestTemplate;

    @Autowired
    ModelMapper modelMapper;

    public List<DistritoDTO> getDistritos(String distritoNombre) {

        List<DistritoDTO> distritos = Arrays.stream(clientRestTemplate.getDistritos().getBody()).map(x -> new DistritoDTO(x.getId(), x.getName())).toList();

        if (distritoNombre == null) {
            return distritos;
        } else {
            distritos = distritos.stream().filter(x -> x.getNombre().toLowerCase().contains(distritoNombre.toLowerCase())).toList();
        }
        return distritos;
    }

    public CargosByDistritoDTO getCargosByDistrito(Long distritoId) {
        CargosByDistritoDTO cargosByDistritoDTO = Arrays.stream(clientRestTemplate.getDistritos().getBody()).filter(x -> x.getId() == distritoId).map(x -> new CargosByDistritoDTO(x.getId(), x.getName(), new ArrayList<>())).toList().get(0);

        List<CargoDTO> cargosDTOList = new ArrayList<>();

        List<CargoClientDTO> cargoClientDTOList = Arrays.stream(clientRestTemplate.getCargos().getBody()).filter(x -> x.getDistritoId() == distritoId).toList();

        for (int i = 0; i < cargoClientDTOList.size(); i++) {

            cargosDTOList.add(new CargoDTO(cargoClientDTOList.get(i).getCargoId(), cargoClientDTOList.get(i).getCargoNombre()));
        }
        cargosByDistritoDTO.setCargos(cargosDTOList);

        return cargosByDistritoDTO;
    }

    public List<SeccionesByDitstritoDTO> getSeccionesByDistrito(Long distritoId, Long seccionId) {

        if (seccionId == null) {

            return Arrays.stream(clientRestTemplate.getSeccionesByDistrito().getBody())
                    .filter(x -> x.getDistritoId() == distritoId)
                    .map(x -> new SeccionesByDitstritoDTO(x.getSeccionId(), x.getSeccionNombre()))
                    .toList();

        } else {
            return Arrays.stream(clientRestTemplate.getSeccionesByDistrito().getBody())
                    .filter(x -> x.getDistritoId() == distritoId && x.getSeccionId() == seccionId)
                    .map(x -> new SeccionesByDitstritoDTO(x.getSeccionId(), x.getSeccionNombre()))
                    .toList();
        }
    }

    public ResultadoDTO getResultados(Long distritoId, Long seccionId) {

        ResultadoDTO resultadoDTO = new ResultadoDTO(
                getDistritos(null).stream().filter(x -> x.getId() == distritoId).toList().get(0).getNombre(),
                getSeccionesByDistrito(distritoId, seccionId).get(0).getNombre(), new ArrayList<>());

        Map<String, BigDecimal> partidosPoliticosMaps = new HashMap<>();

        List<ResultadoClientDTO> resultadoClientDTOList = Arrays.stream(clientRestTemplate.getResultados(seccionId).getBody())
                .filter(x -> x.getDistritoId() == distritoId).toList();

        resultadoClientDTOList.stream().forEach(x -> partidosPoliticosMaps.putIfAbsent(x.getAgrupacionNombre(), new BigDecimal(0)));
        partidosPoliticosMaps.remove("");

        resultadoClientDTOList.stream().filter(x -> x.getVotosTipo().equals("EN BLANCO")
                        || x.getVotosTipo().equals("RECURRIDO")
                        || x.getVotosTipo().equals("IMPUGNADO")
                        || x.getVotosTipo().equals("NULO"))
                .forEach(x -> partidosPoliticosMaps.putIfAbsent(x.getVotosTipo(), new BigDecimal(0)));


        for (int i = 0; i < resultadoClientDTOList.size(); i++) {

            if (partidosPoliticosMaps.containsKey(resultadoClientDTOList.get(i).getAgrupacionNombre())) {
                BigDecimal total = partidosPoliticosMaps.get(resultadoClientDTOList.get(i).getAgrupacionNombre());
                total = total.add(resultadoClientDTOList.get(i).getVotosCantidad());
                partidosPoliticosMaps.replace(resultadoClientDTOList.get(i).getAgrupacionNombre(), total);

            } else if (partidosPoliticosMaps.containsKey(resultadoClientDTOList.get(i).getVotosTipo())) {

                BigDecimal total = partidosPoliticosMaps.get(resultadoClientDTOList.get(i).getVotosTipo());
                total = total.add(resultadoClientDTOList.get(i).getVotosCantidad());
                partidosPoliticosMaps.replace(resultadoClientDTOList.get(i).getVotosTipo(), total);
            }
        }

        List<String> names = partidosPoliticosMaps.keySet().stream().toList();

        List<ResultadoInnerResultadoDTO> resultadoInnerResultadoDTOList = new ArrayList<>();

        BigDecimal total = getTotal(partidosPoliticosMaps);

        for (int i = 0; i < names.size(); i++) {
            resultadoInnerResultadoDTOList.add(
                    new ResultadoInnerResultadoDTO((long) i,
                            names.get(i), partidosPoliticosMaps.get(names.get(i)),
                            partidosPoliticosMaps.get(names.get(i)).divide(total,4,RoundingMode.HALF_UP).multiply(new BigDecimal(100))));

        }
        resultadoInnerResultadoDTOList.sort(Comparator.comparing(ResultadoInnerResultadoDTO::getVotos).reversed());

        for (int i = 0; i < resultadoInnerResultadoDTOList.size(); i++) {
            resultadoInnerResultadoDTOList.get(i).setOrden((long) i + 1);
        }
        resultadoDTO.setResultados(resultadoInnerResultadoDTOList);

        return resultadoDTO;
    }

    public BigDecimal getTotal(Map<String, BigDecimal> partidosPoliticosMap) {
        BigDecimal total = new BigDecimal(0);
        List<BigDecimal> votos = partidosPoliticosMap.values().stream().toList();

        for (int i = 0; i < votos.size(); i++) {
            total = total.add(votos.get(i));
        }
        return total;
    }

    public void getValoresFormateados(){
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < values.size(); i++) {
            sum = sum.add(values.get(i));
        }

        // Normalizar y imprimir los valores
        for (int i = 0; i < values.size(); i++) {
            // Normalizar dividiendo por la suma y redondeando a 7 decimales
            BigDecimal normalizedValue = values.get(i).divide(sum, 7, RoundingMode.FLOOR);

            // Si el valor normalizado es menor que 0.0001, lo ajustamos a 0.0001
            if (normalizedValue.compareTo(new BigDecimal("0.0001")) < 0) {
                System.out.print("0.0001 ");
            } else {
                System.out.printf(Locale.US, "%.4f ", normalizedValue);
            }
        }
    }
}
