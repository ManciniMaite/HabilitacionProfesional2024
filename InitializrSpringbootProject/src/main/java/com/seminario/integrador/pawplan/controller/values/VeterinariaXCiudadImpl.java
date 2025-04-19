package com.seminario.integrador.pawplan.controller.values;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class VeterinariaXCiudadImpl implements VeterinariaXCiudad {

    private Long id;
    private String razonSocial;
    private List<VeterinarioXciudad> veterinarios = new ArrayList<>();

    public VeterinariaXCiudadImpl(Long id, String razonSocial) {
        this.id = id;
        this.razonSocial = razonSocial;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getRazonSocial() {
        return razonSocial;
    }

    public void setVeterinarios(List<VeterinarioXciudad> veterinarios) {
        this.veterinarios = veterinarios;
    }
}

