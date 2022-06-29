/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appboleta.xml;

import java.io.File;
import java.io.IOException;
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
    private Element documento;

    
    
public void generaXML() throws ParserConfigurationException, TransformerException, SAXException, IOException{

                
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
               
                /*
                <ResumenSegmento>
      <TotalesSegmento>
        <TpoDoc>39</TpoDoc>
        <TotAnulado>1234</TotAnulado>
        <TotalesServicio>
          <TpoServ>1</TpoServ>
          <PeriodoDevengo>1999-05</PeriodoDevengo>
          <TotDoc>1234</TotDoc>
          <TotMntExe>33</TotMntExe>
          <TotMntTotal>33</TotMntTotal>
          <TotMntNoFact>1234</TotMntNoFact>
          <TotMntPeriodo>1234</TotMntPeriodo>
          <TotSaldoAnt>1234</TotSaldoAnt>
          <TotVlrPagar>1234</TotVlrPagar>
          <TotTicket>33</TotTicket>
        </TotalesServicio>
      </TotalesSegmento>
    </ResumenSegmento>
                
                
                */
                
                
                
                
                guardarDocumento();
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
