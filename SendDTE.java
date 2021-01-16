/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.appdte.sii.utilidades.AppDTE;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author esteban
 */
public class SendDTE {

    
    public static void main(String[] args) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, Exception{   
   
     /* EJEMPLO DE CARGA DE  DTE */
     
     /* CARGO EL CONTENIDO DESDE UN ARCHIVO JSON */
     
     byte[] arrayCert = Files.readAllBytes(Paths.get("/home/esteban/appdte/certificate/paola"));
     
     AppDTE objdte = new AppDTE();
     String stringDTE = cargaJSON();
     System.out.print(stringDTE);
     
     
     Object[] arrayObjetos = objdte.sendDTE(stringDTE, "paola", "Coval2020", "13900614-3", true,arrayCert);
     
     
     System.out.print("EL TRACK ID ES:" + arrayObjetos[0] + "\n"  );
     System.out.print(arrayObjetos[1]);
   
     File file = new File("/home/esteban/archivo.xml"); 
     OutputStream os  = new FileOutputStream(file); 
      
      os.write((byte[]) arrayObjetos[1]);
     
}
    
public static String cargaJSON() throws FileNotFoundException, IOException{
   String cadena="";
   String cadena2 = "";
        FileReader f = new FileReader("/home/esteban/DTEPRUEBA.json");
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine())!=null) {
              cadena2 = cadena2+cadena; 
            }
        }
     return cadena2;
}
    
}
