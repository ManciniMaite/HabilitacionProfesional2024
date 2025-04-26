package com.seminario.integrador.pawplan.controller.values;

import java.util.List;

import lombok.Data;

@Data
public class ClienteDTO {
    private Long clienteId;
    private String clienteNombre;
    private String clienteApellido;
    private List<AnimalDTO> animales;
    private String dni;
}
