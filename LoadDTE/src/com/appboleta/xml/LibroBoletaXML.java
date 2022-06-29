/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appboleta.xml;

import com.appboleta.json.EnvioLibroJson;
import com.appboleta.json.CaratulaJson;
import com.appboleta.json.LibroBoletaJson;
import com.appboleta.json.MainLibroJson;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author esteban
 */
public class LibroBoletaXML {
 
     private Document doc;
  
    
    
public void generaXML(String stringLibroJson ) throws ParserConfigurationException, TransformerException, SAXException, IOException{

    
    InputStream isjson = new ByteArrayInputStream(stringLibroJson.getBytes("UTF-8")); 
    BufferedReader br1 = new BufferedReader(new InputStreamReader(isjson));
  
  
    Gson gson = new Gson(); 
    MainLibroJson objmainLibroJson = gson.fromJson(br1, MainLibroJson.class);
    LibroBoletaJson objLibroBoletaJson = objmainLibroJson.getLibroBoleta();
    EnvioLibroJson objEnvioLibroJson = objLibroBoletaJson.getEnvioLibro();
    CaratulaJson objCaratulaJson = objEnvioLibroJson.getCaratula();
    
    
    
    
    
    
    
                
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                
		this.doc = docBuilder.newDocument();
                this.doc.setXmlStandalone(true);
		/* incio la seccion de caratula */
                Element libroboleta = this.doc.createElement("LibroBoleta");
                Attr attrversion = this.doc.createAttribute("version");
	        attrversion.setValue("1.0"); 
                libroboleta.setAttributeNode(attrversion);
                
                this.doc.appendChild(libroboleta);
                
                Element EnvioLibro  = this.doc.createElement("EnvioLibro");
                Attr attrid = this.doc.createAttribute("ID");
	        attrid.setValue("EnvioLibro"); 
                EnvioLibro.setAttributeNode(attrid);
             
                libroboleta.appendChild(EnvioLibro);
               
                
             
                
                
                  //inicio de encabezado de documento
		Element caratula = this.doc.createElement("Caratula");
		
                
                Element RutEmisorLibro = this.doc.createElement("RutEmisorLibro");
            
                caratula.appendChild(RutEmisorLibro);
                
                
                Element RutEnvia = this.doc.createElement("RutEnvia");
                caratula.appendChild(RutEnvia);
                
                                
                
                Element PeriodoTributario = this.doc.createElement("PeriodoTributario");
                caratula.appendChild(PeriodoTributario);
                
                Element FchResol = this.doc.createElement("FchResol");    
                caratula.appendChild(FchResol);
                
                
                
                Element NroResol = this.doc.createElement("NroResol");
                caratula.appendChild(NroResol);
                
                
                Element TipoLibro = this.doc.createElement("TipoLibro");
                caratula.appendChild(TipoLibro);
                
                
                
                Element TipoEnvio = this.doc.createElement("TipoEnvio");
                caratula.appendChild(TipoEnvio);
                
                
                
                Element NroSegmento = this.doc.createElement("NroSegmento");
                caratula.appendChild(NroSegmento);
                
                
                Element FolioNotificacion = this.doc.createElement("FolioNotificacion");
                caratula.appendChild(FolioNotificacion);
                
                libroboleta.appendChild(caratula);             
            
                
                EnvioLibro.appendChild(caratula);
               
                
                
                /* resumen de segmente */
                Element ResumenSegmento = this.doc.createElement("ResumenSegmento");
                Element TotalesSegmento = this.doc.createElement("TotalesSegmento");
       
                EnvioLibro.appendChild(ResumenSegmento);
                ResumenSegmento.appendChild(TotalesSegmento);
                
                
                
               Element TpoDoc = this.doc.createElement("TpoDoc");
               TotalesSegmento.appendChild(TpoDoc);
               
               Element TotAnulado = this.doc.createElement("TotAnulado"); 
               TotalesSegmento.appendChild(TotAnulado);
               
                Element TotalesServicio = this.doc.createElement("TotalesServicio"); 
               TotalesSegmento.appendChild(TotalesServicio);
               
               Element TpoServ = this.doc.createElement("TpoServ");
               TotalesServicio.appendChild(TpoServ);
               
               Element PeriodoDevengo = this.doc.createElement("PeriodoDevengo");
               TotalesServicio.appendChild(PeriodoDevengo);
               
               Element TotDoc = this.doc.createElement("TotDoc");
               TotalesServicio.appendChild(TotDoc);
               
               Element TotMntExe = this.doc.createElement("TotMntExe");
               TotalesServicio.appendChild(TotMntExe);
               
               Element TotMntTotal = this.doc.createElement("TotMntTotal");
               TotalesServicio.appendChild(TotMntTotal);
               
               Element TotMntNoFact = this.doc.createElement("TotMntNoFact");
               TotalesServicio.appendChild(TotMntNoFact);
               
               Element TotMntPeriodo = this.doc.createElement("TotMntPeriodo");
               TotalesServicio.appendChild(TotMntPeriodo);
               
               
               Element TotSaldoAnt = this.doc.createElement("TotSaldoAnt");
               TotalesServicio.appendChild(TotSaldoAnt);
               
               Element TotVlrPagar = this.doc.createElement("TotVlrPagar");
               TotalesServicio.appendChild(TotVlrPagar);
               
               
               Element TotTicket = this.doc.createElement("TotTicket");
               TotalesServicio.appendChild(TotTicket);
               
              resumenPeriodo(EnvioLibro);
               
              
              addDetalle(EnvioLibro);
               
               
               
                guardarDocumento();
}               
    
private void resumenPeriodo(Element EnvioLibro){
     Element ResumenPeriodo = this.doc.createElement("ResumenPeriodo");
     EnvioLibro.appendChild(ResumenPeriodo);
    
    Element TotalesPeriodo = this.doc.createElement("TotalesPeriodo");
    ResumenPeriodo.appendChild(TotalesPeriodo);
    
    Element TpoDoc = this.doc.createElement("TpoDoc");
    TotalesPeriodo.appendChild(TpoDoc);
    
    Element TotAnulado = this.doc.createElement("TotAnulado");
    TotalesPeriodo.appendChild(TotAnulado);
    
     
     Element TotalesServicio = this.doc.createElement("TotalesServicio");
     TotalesPeriodo.appendChild(TotalesServicio);
     
    Element TpoServ = this.doc.createElement("TpoServ");
    TotalesServicio.appendChild(TpoServ);
    
    
    Element PeriodoDevengado = this.doc.createElement("PeriodoDevengado");
    TotalesServicio.appendChild(PeriodoDevengado);
    
    
    Element TotDoc = this.doc.createElement("TotDoc");
    TotalesServicio.appendChild(TotDoc);
    
    Element TotMntExe = this.doc.createElement("TotMntExe");
    TotalesServicio.appendChild(TotMntExe);
    
    Element TotMntNeto = this.doc.createElement("TotMntNeto");
    TotalesServicio.appendChild(TotMntNeto);
    
    
    Element TasaIVA = this.doc.createElement("TasaIVA");
    TotalesServicio.appendChild(TasaIVA);
    
    
    Element TotMntIVA = this.doc.createElement("TotMntIVA");
    TotalesServicio.appendChild(TotMntIVA);
    
    
    Element TotMntTotal = this.doc.createElement("TotMntTotal");
    TotalesServicio.appendChild(TotMntTotal);
    
    Element TotMntNoFact = this.doc.createElement("TotMntNoFact");
    TotalesServicio.appendChild(TotMntNoFact);
   
   Element TotMntPeriodo = this.doc.createElement("TotMntPeriodo");
   TotalesServicio.appendChild(TotMntPeriodo);
   
   
   Element TotSaldoAnt = this.doc.createElement("TotSaldoAnt");
   TotalesServicio.appendChild(TotSaldoAnt);
   
   
   Element TotVlrPagar = this.doc.createElement("TotVlrPagar");
   TotalesServicio.appendChild(TotVlrPagar);
   
   Element TotTicket = this.doc.createElement("TotTicket");
   TotalesServicio.appendChild(TotTicket);
   
}



private void addDetalle(Element EnvioLibro){
    
      Element Detalle = this.doc.createElement("Detalle");
      EnvioLibro.appendChild(Detalle);
      
      Element TpoDoc = this.doc.createElement("TpoDoc");
      Detalle.appendChild(TpoDoc);
      
      Element FolioDoc = this.doc.createElement("FolioDoc");
      Detalle.appendChild(FolioDoc);
      
      Element Anulado = this.doc.createElement("Anulado");
      Detalle.appendChild(Anulado);
      
      Element TpoServ = this.doc.createElement("TpoServ");
      Detalle.appendChild(TpoServ);
      
      Element FchEmiDoc = this.doc.createElement("FchEmiDoc");
      Detalle.appendChild(FchEmiDoc);
      
      Element FchVencDoc = this.doc.createElement("FchVencDoc");
      Detalle.appendChild(FchVencDoc);
      
      Element PeriodoDesde = this.doc.createElement("PeriodoDesde");
      Detalle.appendChild(PeriodoDesde);
      
      
      Element PeriodoHasta = this.doc.createElement("PeriodoHasta");
      Detalle.appendChild(PeriodoHasta);
      
      
      Element CdgSIISucur = this.doc.createElement("CdgSIISucur");
      Detalle.appendChild(CdgSIISucur);
      
      Element RUTCliente = this.doc.createElement("RUTCliente");
      Detalle.appendChild(RUTCliente);
      
      
      Element CodIntCli = this.doc.createElement("CodIntCli");
      Detalle.appendChild(CodIntCli);
      
      Element MntExe = this.doc.createElement("MntExe");
      Detalle.appendChild(MntExe);
      
      
      Element MntTotal = this.doc.createElement("MntTotal");
      Detalle.appendChild(MntTotal);
     
      
      Element MntNoFact = this.doc.createElement("MntNoFact");
      Detalle.appendChild(MntNoFact);
      
      Element MntPeriodo = this.doc.createElement("MntPeriodo");
      Detalle.appendChild(MntPeriodo);
      
      
      Element SaldoAnt = this.doc.createElement("SaldoAnt");
      Detalle.appendChild(SaldoAnt);
      
      Element VlrPagar = this.doc.createElement("VlrPagar");
      Detalle.appendChild(VlrPagar);
      
      Element TotTicketBoleta = this.doc.createElement("TotTicketBoleta");
      Detalle.appendChild(TotTicketBoleta);
      
      /*
      
        <TpoDoc>39</TpoDoc>
      <FolioDoc>745</FolioDoc>
      <Anulado>A</Anulado>
      <TpoServ>1</TpoServ>
      <FchEmiDoc>2012-12-13</FchEmiDoc>
      <FchVencDoc>2012-12-13</FchVencDoc>
      <PeriodoDesde>2012-12-13</PeriodoDesde>
      <PeriodoHasta>2012-12-13</PeriodoHasta>
      <CdgSIISucur>745</CdgSIISucur>
      <RUTCliente>str1234</RUTCliente>
      <CodIntCli>str1234</CodIntCli>
      <MntExe>33</MntExe>
      <MntTotal>33</MntTotal>
      <MntNoFact>1234</MntNoFact>
      <MntPeriodo>1234</MntPeriodo>
      <SaldoAnt>1234</SaldoAnt>
      <VlrPagar>1234</VlrPagar>
      <TotTicketBoleta>33</TotTicketBoleta>
      
      */
      
      
      
      
      
      
    
}







    public void guardarDocumento() throws TransformerException, ParserConfigurationException, SAXException, IOException{
        
        
           //esta seccion se encarga de hacer la identacion
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();     
              
                transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
                
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
               
                 
                
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
                                          //luego guardo el documento    
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2"); 
                       
               
                            
                DOMSource source = new DOMSource(this.doc);              
                StreamResult result = new StreamResult(new File("/home/esteban/appdte/DTE/LIBROBOLETA"+".xml"));
		transformer.transform(source, result);                
		
        
   }
            
    
    
}
