package com.seminario.integrador.pawplan.controller.values;

import java.util.List;


import lombok.Data;

@Data
public class ClientesDeVeterinarieRs extends Response{
    private List<ClienteDTO> clientes;
}
