package com.appdte.sii.utilidades;

import com.appdte.models.DetalleDteModel;
import com.appdte.models.DteModel;
import com.appdte.models.ReferenciaModel;
import com.appdte.json.ReceptorJson;
import com.appdte.json.EmisorJson;
import com.appdte.json.IdDteJson;
import com.appdte.json.ReferenciaJson;
import com.appdte.json.DetalleDteJson;
import com.appdte.json.TotalesJson;
import com.appdte.json.DteJson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import com.appdte.sii.cl.Semilla;
import com.appdte.sii.cl.Token;
import com.appdte.sii.cl.UploadSii;
/*
import appventas.movimientos.BlobDTE;

*/
import com.google.gson.Gson;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class AppDTE {
    String environment;
    String pathcaf;
    public AppDTE(byte[] arrayCAF, String loginuser, String environment) throws FileNotFoundException, IOException{
        
     this.environment = environment;
     File file = new File(loginuser+ "CAF"+".xml"); 
     OutputStream os  = new FileOutputStream(file); 
     os.write(arrayCAF);
     this.pathcaf = loginuser+ "CAF"+".xml";
     
    }
            
            
    
    
    
    @SuppressWarnings("empty-statement")
    
    public  Object[] sendDTE(String stringDTE,String certificado,String clave,String rutEnvia, boolean blReferencia, byte[] arrayCert) throws TransformerException, ParserConfigurationException, SAXException, IOException, Exception{

    
  
   String pathdata = "";
  
   /* CARGO LOS PARAMETROS DE CONFIGURACION */
   String pathdte = "";
   String urlenvironment = this.environment;
   /* ingreso el DTE en formato JSON */
             
             
   System.out.println("Reading JSON from a file");
   System.out.println("----------------------------");
  
   
  
    InputStream isjson = new ByteArrayInputStream(stringDTE.getBytes("UTF-8")); 
    BufferedReader br1 = new BufferedReader(new InputStreamReader(isjson));
  
  
    Gson gson = new Gson(); 
    DteJson objdtejson = gson.fromJson(br1, DteJson.class);
 
        
     
     /* DATOS DEL EMISOR EN JSON */
     EmisorJson objemisor = objdtejson.getEmisor();
     
     
     
     
     
    /* DATOS DEL RECEPTOR EN JSON */
    ReceptorJson objreceptor = objdtejson.getReceptor();
    IdDteJson iddoc = objdtejson.getIdDte();
    TotalesJson totales = objdtejson.getTotales(); 
   /* inicializar el xml */        
    DteModel objdte = new DteModel();
    ClassDteDao obj = new ClassDteDao();
    DetalleDteModel objdetalledte = new DetalleDteModel();
    /* DEFINO DATOS DEL EMISOR Y RECEPTOR 
   */
    objdte.setRutemisor(objemisor.getRutemisor());
    objdte.setTipodte(iddoc.getTipoDTE());
    objdte.setNumdte(iddoc.getNumDTE());
    
    FuncionesCAF objFuncionCAF = new FuncionesCAF();
    
    /* VALIDAMOS CAF */
    if(objFuncionCAF.validaCAF(pathcaf, objemisor.getRutemisor(),Integer.parseInt(iddoc.getTipoDTE()), Integer.parseInt(iddoc.getNumDTE()))==false){
    
        return null;
        
    }
    
    
    objdte.setFechadte(iddoc.getFechaEmision());
    
    if(Integer.parseInt(iddoc.getTipoDTE())==52){
        objdte.setTipotraslado(iddoc.getTipotraslado());
    }
    
    
    String[] arrayrutemisor = objdte.getRutemisor().split("-");
    
    String rutemisor = arrayrutemisor[0];
    String nombredte = "DTE"+rutemisor+"F"+iddoc.getNumDTE()+"T"+iddoc.getTipoDTE();
        
    
    
    
    
    objdte.setFecharesol(objemisor.getFecharesol());
    objdte.setNumresol(objemisor.getNumresol());
    
    /* DATOS DEL EMISOR EN EL XML */
  
   objdte.setRsemisor(objemisor.getRsemisor());
   objdte.setGiroemisor(objemisor.getGiroemisor());
   objdte.setActecoemisor(objemisor.getActecoemisor());
  
    
   objdte.setCodigosii(objemisor.getCodsiisucur());
   objdte.setDiremisor(objemisor.getDiremisor());
   objdte.setCmnaemisor(objemisor.getCmnaemisor());
   objdte.setCiuemisor(objemisor.getCiuemisor());
    
    
    /* DATOS DEL RECEPTOR EN EL XML */
    objdte.setRutreceptor(objreceptor.getRutreceptor());
    objdte.setRsreceptor(objreceptor.getRsreceptor());
    objdte.setGiroreceptor(objreceptor.getGiroreceptor());
    objdte.setCmnareceptor(objreceptor.getCmnareceptor());
    objdte.setCiureceptor(objreceptor.getCiureceptor());
    objdte.setDirreceptor(objreceptor.getDirreceptor());
    objdte.setNumdte(iddoc.getNumDTE());
    
    
    /* DEFINO EL TOTAL Y TASA DE IMPUESTO */
     objdte.setMontofecto(totales.getMontoafecto());
     objdte.setMontexento(totales.getMontoexento());
     objdte.setMontoiva(totales.getMontoiva());
    objdte.setTasaiva(totales.getTasaiva());
    objdte.setMontototal(totales.getMontototal());
      
    /* INICIALIZO EL OBJETO DE DOCUMENTO */
    obj.crearXml(objdte);
    
      /* cargo los detalles */
     List<DetalleDteJson> detalle = objdtejson.getDetalleDteJson();
  
    
   Timbre objTimbre = new Timbre("",nombredte,pathdata,this.pathcaf);
   String auxDescripcion;
for (DetalleDteJson i :  detalle){     
  if(i.getNrolinea()==1){  
       objTimbre.setItem1(i.getNmbitem());
       auxDescripcion = i.getNmbitem();
 }
   
    
    objdetalledte.setNrolinea(i.getNrolinea());
    objdetalledte.setTpocodigo(i.getTpocodigo());
    objdetalledte.setVlrcodigo(i.getVlrcodigo());
    objdetalledte.setNmbitem(i.getNmbitem());
    objdetalledte.setDscitem(i.getNmbitem());
    objdetalledte.setQtyitem(i.getQtyitem());
    objdetalledte.setPrcitem(i.getPrcitem());
    objdetalledte.setDescuentopct(i.getDescuentopct());
    objdetalledte.setDescuentomonto(i.getDescuentomonto());
    objdetalledte.setMontoitem(i.getMontoitem());
    obj.agregaDetalle(objdetalledte);
    
    }
    
  
   ReferenciaJson referencia = objdtejson.getReferencia();
   ReferenciaModel objReferenciaModel = new ReferenciaModel();
   objReferenciaModel.setRazonref(referencia.getRazonref());
   objReferenciaModel.setNumref(referencia.getNumref());
   
   objReferenciaModel.setFecharef(referencia.getFecharef());
   objReferenciaModel.setFolioref(referencia.getFolioref());
   objReferenciaModel.setCodref(referencia.getCodref());
    
   /*     objReferenciaModel.setTpoDocRef(referencia.getTpoDocRef()); */
       
objReferenciaModel.setTpoDocRef(referencia.getTpoDocRef());
    
obj.agregaRegerencia(objReferenciaModel,blReferencia);
    
auxDescripcion = objTimbre.getItem1();
obj.guardarDocumento(nombredte,pathdte);
objTimbre.creaTimbre(objdte, auxDescripcion,rutemisor);
  
    
/* preparo el DTE para firmar */
SignDTE objFirma = new SignDTE();
objFirma.signDTE("",nombredte,certificado,clave, arrayCert);
   
    /* ahora envuelvo el DTE en un sobre electrónico */
   
EnvioDTE objenvio = new EnvioDTE(this.environment);
objenvio.generaEnvio(objdte,nombredte,pathdte,rutEnvia);
   
SignENVDTE objFirmaENV = new SignENVDTE();
objFirmaENV.signENVDTE("",nombredte,certificado,clave,arrayCert);
    
/*
BlobDTE objblob = new BlobDTE();
    
objblob.registrarXML(idmovimiento,"ENVDTE"+rutemisor+"F"+iddoc.getNumDTE()+"T"+iddoc.getTipoDTE()+".xml");
*/


 /* OBTENGO LA SEMILLA PARA AUTENTIFICARME AL SII   */ 
 
  Semilla objsemilla = new Semilla();

  
 
String valorsemilla =  objsemilla.getSeed(urlenvironment);
 
 Token objtoken = new Token(urlenvironment);
 String valortoken =  objtoken.getToken(valorsemilla,certificado,clave,nombredte);


 UploadSii objupload = new UploadSii(urlenvironment);


 String valortrackid = objupload.uploadSii(valortoken,"",nombredte,objdte.getRutemisor(),rutEnvia);

    
 
 getBytesDTE objByte = new getBytesDTE();
 Object[] arrayObjetos = new Object[2];

 arrayObjetos[0] = valortrackid;
 arrayObjetos[1] = objByte.getBytesArray(nombredte);
    
 return arrayObjetos;
    
 
 
 
}
   

}