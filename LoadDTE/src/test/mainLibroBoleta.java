/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.appboleta.xml.LibroBoletaXML;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 * @author esteban
 */
public class mainLibroBoleta {
    
    public static void main(String[] args) throws ParserConfigurationException{
         LibroBoletaXML objlibro = new LibroBoletaXML();
         objlibro.generaXML();
         
     }
}
