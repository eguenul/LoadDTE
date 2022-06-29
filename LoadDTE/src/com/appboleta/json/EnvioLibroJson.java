/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appboleta.json;

import java.util.List;

/**
 *
 * @author esteban
 */
public class EnvioLibroJson {
   private CaratulaJson Caratula;
   /*
  private List<DetalleJson> detalle; 
*/
    public CaratulaJson getCaratula() {
        return Caratula;
    }

    public void setCaratula(CaratulaJson Caratula) {
        this.Caratula = Caratula;
    }
/*
    public List<DetalleJson> getDetalle() {
        return detalle;
    }
/*
    public void setDetalle(List<DetalleJson> detalle) {
        this.detalle = detalle;
    }
    
 */  
     
    
    
}
