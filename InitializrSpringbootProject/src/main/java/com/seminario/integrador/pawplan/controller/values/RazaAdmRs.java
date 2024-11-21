/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller.values;

import com.seminario.integrador.pawplan.model.Raza;
import java.util.List;

/**
 *
 * @author maite
 */
public class RazaAdmRs extends Response{
    List<Raza> razas;

    public List<Raza> getRazas() {
        return razas;
    }

    public void setRazas(List<Raza> razas) {
        this.razas = razas;
    }
}
