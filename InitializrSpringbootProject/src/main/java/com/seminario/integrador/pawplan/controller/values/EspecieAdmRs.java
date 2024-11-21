/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller.values;

import com.seminario.integrador.pawplan.model.Especie;
import java.util.ArrayList;
/**
 *
 * @author maite
 */
public class EspecieAdmRs extends Response{
    private ArrayList<Especie> especies;

    public ArrayList<Especie> getEspecies() {
        return especies;
    }

    public void setEspecies(ArrayList<Especie> especies) {
        this.especies = especies;
    }
    
}
