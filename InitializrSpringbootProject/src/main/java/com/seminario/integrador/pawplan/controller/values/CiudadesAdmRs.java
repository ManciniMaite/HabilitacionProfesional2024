/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller.values;

import com.seminario.integrador.pawplan.model.Ciudad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maite
 */
public class CiudadesAdmRs extends Response {
    ArrayList<Ciudad> ciudades;

    public ArrayList<Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(ArrayList<Ciudad> ciudades) {
        this.ciudades = ciudades;
    }
    
    
}
