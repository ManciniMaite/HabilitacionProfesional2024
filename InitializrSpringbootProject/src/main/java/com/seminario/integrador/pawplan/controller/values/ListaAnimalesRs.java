/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.seminario.integrador.pawplan.controller.values;

import com.seminario.integrador.pawplan.model.Animal;
import java.util.List;

/**
 *
 * @author maite
 */
public class ListaAnimalesRs extends Response{
    private List<Animal> animales;

    public List<Animal> getAnimales() {
        return animales;
    }

    public void setAnimales(List<Animal> animales) {
        this.animales = animales;
    }
    
}
