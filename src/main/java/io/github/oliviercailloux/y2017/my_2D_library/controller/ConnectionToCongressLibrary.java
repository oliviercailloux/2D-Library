package io.github.oliviercailloux.y2017.my_2D_library.controller;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * RESTConnection to the library of Congress
 * @author lejema160
 *
 */
public class ConnectionToCongressLibrary {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ConnectionToCongressLibrary.class);
	
	/**
	 * the code of the book on the site library of the congress
	 */
	private String bookCodeIdentifier;

	public void main (String [] args){
		ConnectionToCongressLibrary connect = new ConnectionToCongressLibrary("2013413127");
		System.out.println(connect.getResult());
	}
	
	/***
	 * Constructor of the connection with the code of the book
	 * @param code
	 */
	public ConnectionToCongressLibrary(String code){
		this.bookCodeIdentifier = code;
	}

	/***
	 * Make the connection with the site and get the book's datas marcxml in a string
	 * @return result, the code marcxml of the datas of the book
	 * @throws ProcessingException
	 * Dans le marcxml on a : 
	 * en 100 a) l'auteur
	 * en 245 a) le titre
	 * en 260 c) la date de publi
	 */
	public String getResult() throws ProcessingException{
		String result;
		try {
			Client client = ClientBuilder.newClient();
			WebTarget t1 = client.target("https://lccn.loc.gov");
			WebTarget t2 = t1.path("{idxml}");
			WebTarget t3 = t2.resolveTemplate("idxml", bookCodeIdentifier+"/marcxml", false);
			result = t3.request(MediaType.TEXT_PLAIN).get(String.class);
			client.close();	
		}
		catch (ProcessingException e){
			result = "ERROR - book code identifier does not exist";
		}
		return result;
	}
}