package io.github.dauphine.lejema160.controller;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class ExtractBookData {
	
	public static void main(String args[]){
		// remplacer l'identifiant ci-dessous par l'identifiant du livre voulu dans la librairie du congrès
		ConnectionToCongressLibrary connexion = new ConnectionToCongressLibrary("2014495444"); 
		
		String fileTest = connexion.getResult();
		
		String tabResult[] = new String[3];
		tabResult = ExtractData(fileTest);
		for (int i = 0; i<3; i++){
			System.out.println(tabResult[i]);
		}
		
		return;
		
	}

	
	/**
	 * 
	 * @param xmlFile
	 * @return
	 * 
	 * resultat[0] contains the autor
	 * resultat[1] contains the title
	 * resultat[2] contains the date of publication
	 */
	public static String[] ExtractData(String xmlStr) throws IllegalArgumentException {
		
		String result[] = new String[3]; // tableau contenant le titre, l'auteur et la date de parrution du livre
		
		/*
		Document xmldoc = convertStringToDocument(xmlStr);
		 
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		Document myDocument= db.parse(xmldoc);
		*/
		
		
		//Recherche et extraction de l'auteur
		int index = xmlStr.indexOf("tag=\"100\"");
		if (index == -1){ 								// Si on ne trouve pas la section contenant l'auteur
			throw new IllegalArgumentException();
		}
		index = xmlStr.indexOf("code=\"a\"", index);
		if (index == -1){
			throw new IllegalArgumentException();
		}
		int indexBeginAutor = xmlStr.indexOf('>', index);
		int indexEndAutor = xmlStr.indexOf('<', indexBeginAutor+1);
		if (indexBeginAutor == -1 || indexEndAutor == -1){
			throw new IllegalArgumentException();
		}
		result[1] = xmlStr.substring(indexBeginAutor+1, indexEndAutor);
		
		//Recherche et extraction du titre
		index = xmlStr.indexOf("tag=\"245\"");
		if (index == -1){
			throw new IllegalArgumentException();
		}
		index = xmlStr.indexOf("code=\"a\"", index);
		if (index == -1){
			throw new IllegalArgumentException();
		}
		
		int indexBeginTitle = xmlStr.indexOf('>', index);
		int indexEndTitle = xmlStr.indexOf('<', indexBeginTitle+1);
		if (indexBeginTitle == -1 || indexEndTitle == -1){
			throw new IllegalArgumentException();
		}
		
		result[0] = xmlStr.substring(indexBeginTitle+1, indexEndTitle);
		
		
		//Recherche et extraction de la date de publication
		index = xmlStr.indexOf("tag=\"260\"");
		if (index == -1){
			throw new IllegalArgumentException();
		}
		index = xmlStr.indexOf("code=\"c\"", index);
		if (index == -1){
			throw new IllegalArgumentException();
		}
		
		int indexBeginDate = xmlStr.indexOf('>', index);
		int indexEndDate = xmlStr.indexOf('<', indexBeginDate+1);
		if (indexBeginDate == -1 || indexEndDate == -1){
			throw new IllegalArgumentException();
		}
		
		result[2] = xmlStr.substring(indexBeginDate+1, indexEndDate-1);
		
		
		return result; 
		
	}
	
	 private static Document convertStringToDocument(String xmlStr) {
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	        DocumentBuilder builder;  
	        try  
	        {  
	            builder = factory.newDocumentBuilder();  
	            Document doc = builder.parse( new InputSource( new StringReader( xmlStr ) ) ); 
	            return doc;
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } 
	        return null;
	    }


}

