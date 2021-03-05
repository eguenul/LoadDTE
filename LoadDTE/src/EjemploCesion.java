

import com.appdte.json.AECjson;
import com.appdte.json.CedenteJson;
import com.appdte.json.CesionJson;
import com.appdte.json.CesionarioJson;
import com.appdte.json.IdDteCesionjson;
import com.appdte.sii.cesion.MainCesion;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author esteban
 */
public class EjemploCesion {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
public static void main(String[] args) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, UnrecoverableEntryException, UnrecoverableKeyException, KeyException, MarshalException, XMLSignatureException, Exception {
        // TODO code application logic here
   
        String contenidodte = "";
        String cadenadte = "";
         byte[] arrayCert = Files.readAllBytes(Paths.get("/home/esteban/appdte/certificate/paola"));
     
           FileReader f2 = new FileReader("/home/esteban/dterespaldo.xml");
        try (BufferedReader b = new BufferedReader(f2)) {
            while((cadenadte = b.readLine())!=null) {
                contenidodte = contenidodte + cadenadte + "\n";
            }
        }
      
         byte[]arrayAEC;
        arrayAEC = contenidodte.getBytes("Windows-1252");
         
        /* cargo el archivo XML que contiene DTE o AEC ANTERIOR */
        String pathdte = "/home/esteban/dterespaldo.xml";
        
        
        
        
   String cadena="";
   String cadena2 = "";
   
        /* cargo el archivo json */
        FileReader f = new FileReader("/home/esteban/cesion.json");
        
        
        try (BufferedReader b = new BufferedReader(f)) {
            while((cadena = b.readLine())!=null) {
              cadena2 = cadena2+cadena; 
            }
        }

    InputStream isjson = new ByteArrayInputStream(cadena2.getBytes("UTF-8")); 
    BufferedReader br1 = new BufferedReader(new InputStreamReader(isjson));
  
  
    Gson gson = new Gson(); 
    AECjson objaeccesionjson = gson.fromJson(br1, AECjson.class);
       
        int secuencia = 0;
        
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        
        
        Document doc = docBuilder.parse(pathdte);
        
        if(doc.getElementsByTagName("Cesion").getLength()>0){
            secuencia = doc.getElementsByTagName("Cesion").getLength();
            secuencia++;
        }else{
            secuencia = 1;
        }   
        
        System.out.print(secuencia);
        
        
          List<CesionJson> arraycesion = objaeccesionjson.getCesiones();
           
          CedenteJson objcedente = new CedenteJson();
          CesionarioJson objcesionario = new CesionarioJson();
          
          String montocesion="";
          String ultimovencimiento = "";
            for (CesionJson objcesion:arraycesion){
                montocesion = objcesion.getMontocesion();
                ultimovencimiento = objcesion.getUltimovencimiento();
                objcedente = objcesion.getCedente();
                objcesionario = objcesion.getCesionario();
            }
    
        Node folio = doc.getElementsByTagName("Folio").item(0);             
        Node tipodte = doc.getElementsByTagName("TipoDTE").item(0);
        Node rutemisor = doc.getElementsByTagName("RUTEmisor").item(0);                
        Node rutreceptor = doc.getElementsByTagName("RUTRecep").item(0);
        Node fechaemis = doc.getElementsByTagName("FchEmis").item(0);
        Node mnttotal =doc.getElementsByTagName("MntTotal").item(0);
        
        
        IdDteCesionjson objiddtecesion = new IdDteCesionjson();
        objiddtecesion.setFchemis(fechaemis.getTextContent());
        objiddtecesion.setTipodte(tipodte.getTextContent());
        objiddtecesion.setFolio(folio.getTextContent());
        objiddtecesion.setRutreceptor(rutreceptor.getTextContent());
        objiddtecesion.setRutemisor(rutemisor.getTextContent());
        objiddtecesion.setRutemisor(rutemisor.getTextContent());
        objiddtecesion.setMnttotal(mnttotal.getTextContent());
        
        CesionJson objcesion = new CesionJson();
        objcesion.setMontocesion(montocesion);
        objcesion.setUltimovencimiento(ultimovencimiento);
        
        objcesion.setSeqcesion(String.valueOf(secuencia));
        
        objcesion.setCedente(objcedente);
        
        objcesion.setCesionario(objcesionario);
        objcesion.setIddte(objiddtecesion);
        
        
        
        
        
         /* preparao la creaci�n de los archivos xml y su firma */
         
         AECjson objAEC = new AECjson();
         objAEC.setRutcedente(objcedente.getRut());
         objAEC.setRutcesionario(objcesionario.getRut());
         ArrayList<CesionJson> arraycesion2 = new ArrayList<>();
         arraycesion2.add(objcesion);
         objAEC.setCesiones(arraycesion2);
         
         
         
         final Gson gson2 = new Gson();
         final String stringJSON = gson2.toJson(objAEC);
         System.out.print(stringJSON);
         
   
         
/*
         MainCesion objMainCesion = new MainCesion("paola", "Coval2020", secuencia, "77050405-8",arrayCert);
  */ 
    

	/* STRING JSON DE LA NUEVA CESION, ARRAY BYTE DEL AEC ANTERIOR, RUT EMPRESA CEDENTE, CORREO. EL PARAMETRO AEC SE MANTIENE PARA ESTABLECER QUE ES UN ARCHIVO EXTERNO */	

        MainCesion objMainCesion = new MainCesion("paola","Coval2020",secuencia,"77050405-8",arrayCert,"maullin.sii.cl");
        
         Object[] arrayObjetos =   objMainCesion.sendCesion(stringJSON, arrayAEC, "77050405-8", "eguenul@yahoo.com", "AEC");
        
          
     System.out.print("EL TRACK ID ES:" + arrayObjetos[0] + "\n"  );
     System.out.print(arrayObjetos[1]);
   
     File file = new File("/home/esteban/archivocesion.xml"); 
     OutputStream os  = new FileOutputStream(file); 
      
     os.write((byte[]) arrayObjetos[1]);
    
    }
    
}