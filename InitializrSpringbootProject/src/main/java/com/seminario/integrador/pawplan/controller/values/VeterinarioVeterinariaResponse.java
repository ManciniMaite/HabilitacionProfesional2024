package com.seminario.integrador.pawplan.controller.values;



import lombok.Data;

import java.util.ArrayList;

@Data
public class VeterinarioVeterinariaResponse extends Response {

    private ArrayList<VeterinarioXciudad> veterinariosIndependientes;
    private ArrayList<VeterinariaXCiudad> veterinarias;
}
