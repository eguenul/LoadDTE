/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdte.sii.utilidades;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author esteban
 */
public class ConfigClass_1 {
    private final String pathcert;
   
     private final String pathenvironment;
     private final String serverauth;
    private final String serveracceptdte;
            
    
    public ConfigClass_1() throws ParserConfigurationException, SAXException, IOException{
       
        
       
     String  filepath = "appcontconf.xml";
	
         DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	 DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 Document doc = docBuilder.parse(filepath);
         
         
        NodeList nlpathcert = doc.getElementsByTagName("path-certificate");
        Element elpathcert = (Element) nlpathcert.item(0);
        this.pathcert = elpathcert.getFirstChild().getNodeValue();
         
          NodeList nodepathenvironment = doc.getElementsByTagName("environment-url");
          Element elpathenvironment =  (Element) nodepathenvironment.item(0);
          this.pathenvironment = elpathenvironment.getFirstChild().getNodeValue();
          
         NodeList nodeserverauth =  doc.getElementsByTagName("server-auth");
         Element elserverauth =  (Element) nodeserverauth.item(0);  
         this.serverauth = elserverauth.getFirstChild().getNodeValue();
         
         
         NodeList nodeserveracceptdte =  doc.getElementsByTagName("server-acceptdte");
         Element elserveracceptdte =  (Element) nodeserveracceptdte.item(0);  
         this.serveracceptdte = elserveracceptdte.getFirstChild().getNodeValue();
         

}

    public String getPathcert() {
        return pathcert;
    }

    public String getPathenvironment() {
        return pathenvironment;
    }
    
    
    

    public String getServerauth() {
        return serverauth;
    }

    public String getServeracceptdte() {
        return serveracceptdte;
    }
    
    
    
}
