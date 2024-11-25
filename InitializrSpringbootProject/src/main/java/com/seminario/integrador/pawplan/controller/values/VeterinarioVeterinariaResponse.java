package com.seminario.integrador.pawplan.controller.values;

import com.seminario.integrador.pawplan.model.Veterinaria;
import com.seminario.integrador.pawplan.model.Veterinario;
import java.util.ArrayList;

public class VeterinarioVeterinariaResponse extends Response {

    private ArrayList<Veterinario> veterinariosIndependientes;
    private ArrayList<Veterinaria> veterinarias;

    public ArrayList<Veterinario> getVeterinariosIndependientes() {
        return veterinariosIndependientes;
    }

    public void setVeterinariosIndependientes(ArrayList<Veterinario> veterinariosIndependientes) {
        this.veterinariosIndependientes = veterinariosIndependientes;
    }

    public ArrayList<Veterinaria> getVeterinarias() {
        return veterinarias;
    }

    public void setVeterinarias(ArrayList<Veterinaria> veterinarias) {
        this.veterinarias = veterinarias;
    }
    
    
}
