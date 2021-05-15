/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getdte;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.xml.sax.SAXException;

/**
 *
 * @author esteban
 */
public class GETDTE {

    /**
     * @param args the command line arguments
     * @throws org.xml.sax.SAXException
     */
    public static void main(String[] args) throws SAXException, Exception {
     
         byte[] arrayCert = Files.readAllBytes(Paths.get("/home/esteban/appdte/certificate/eguenul"));
     
      
         getCompras objgetCompras = new getCompras();
         /*
         
          public String getComprasJSON(byte[] arrayCert,String login,String clavecert, String rutempresa, String mes_periodo, String year_periodo) throws ParserConfigurationException, SAXException, Exception{
 
         */      
         
         
         /*
         
         arrayCert es el certificado digital en formato arraybytes
         eguenul es el login del usuario del certificado digital
         amulen1956 es la clave del certificado digital a usar
         77813960-K es el rut de la empresa a consultar los documentos de compra
         04 es el mes a consultar en este caso sería el mes de abril
         2021 es el año del periodo a consultar en este caso año 2021
         palena.sii.cl es el server a utilizar
         */
         
         
         String strJSON = objgetCompras.getComprasJSON(arrayCert,"eguenul","amulen1956","77813960-K","04","2021","palena.sii.cl");
      
         String ruta = "/home/esteban/COMPRAS.json";
         
        try (PrintWriter writer = new PrintWriter(ruta, "UTF-8")) {
            writer.println(strJSON);
        }
        
        /*
        
         arrayCert es el certificado digital en formato arraybytes
         eguenul es el login del usuario del certificado digital
         amulen1956 es la clave del certificado digital a usar
         77813960-K es el rut de la empresa a consultar los documentos de ventas
         04 es el mes a consultar en este caso sería el mes de abril
         2021 es el año del periodo a consultar en este caso año 2021
         palena.sii.cl es el server a utilizar
      */
         getVentas objgetVentas = new getVentas();
        String strJSON2 =   objgetVentas.getVentasJSON(arrayCert,"eguenul","amulen1956","77813960-K","04","2021","palena.sii.cl");
         
         
          String ruta2 = "/home/esteban/VENTAS.json";
         
        try (PrintWriter writer2 = new PrintWriter(ruta2, "UTF-8")) {
            writer2.println(strJSON2);
        }
         
         
         
         
         
      
    }
    
}
