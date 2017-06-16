package io.github.oliviercailloux.y2017.my_2D_library.controller;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RESTConnection to the library of Congress
 * Extract the title, the author and the date of publication of the book.
 *
 */
public class ConnectionToCongressLibrary {

	public static final Logger LOGGER = LoggerFactory.getLogger(ConnectionToCongressLibrary.class);

	/**
	 * the code of the book on the site library of the congress
	 */
	private String bookCodeIdentifier;

	/***
	 * Constructor of the connection with the code of the book
	 * 
	 * @param code
	 */
	public ConnectionToCongressLibrary(String code) {
		this.bookCodeIdentifier = code;
	}

	/***
	 * Make the connection with the site and get the book's datas marcxml in a
	 * string
	 * 
	 * @return result, the code marcxml of the datas of the book
	 * @throws ProcessingException
	 */
	public String getMarcXML() throws ProcessingException {
		String result;
		try {
			Client client = ClientBuilder.newClient();
			WebTarget t1 = client.target("https://lccn.loc.gov");
			WebTarget t2 = t1.path("{idxml}");
			WebTarget t3 = t2.resolveTemplate("idxml", bookCodeIdentifier + "/marcxml", false);
			result = t3.request(MediaType.TEXT_PLAIN).get(String.class);
			client.close();
		} catch (ProcessingException e) { //FIXME
			result = "ERROR - book code identifier does not exist";
		}
		return result;
	}

	/**
	 * 
	 * @return a table of 3 String named result. 
	 * result[0] contains the title
	 * result[1] contains the author 
	 * result[2] contains the date of publication
	 * 
	 * @throws IllegalArgumentException
	 */
	public String[] extractData() throws IllegalArgumentException {

		String xmlStr = this.getMarcXML();

		String result[] = new String[3];

		// Recherche et extraction de l'auteur
		int index = xmlStr.indexOf("tag=\"100\"");
		if (index == -1) { // Si on ne trouve pas la section contenant l'auteur
			throw new IllegalArgumentException();
		}
		index = xmlStr.indexOf("code=\"a\"", index);
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		int indexBeginAutor = xmlStr.indexOf('>', index);
		int indexEndAutor = xmlStr.indexOf('<', indexBeginAutor + 1);
		if (indexBeginAutor == -1 || indexEndAutor == -1) {
			throw new IllegalArgumentException();
		}
		result[1] = xmlStr.substring(indexBeginAutor + 1, indexEndAutor);

		// Recherche et extraction du titre
		index = xmlStr.indexOf("tag=\"245\"");
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		index = xmlStr.indexOf("code=\"a\"", index);
		if (index == -1) {
			throw new IllegalArgumentException();
		}

		int indexBeginTitle = xmlStr.indexOf('>', index);
		int indexEndTitle = xmlStr.indexOf('<', indexBeginTitle + 1);
		if (indexBeginTitle == -1 || indexEndTitle == -1) {
			throw new IllegalArgumentException();
		}

		result[0] = xmlStr.substring(indexBeginTitle + 1, indexEndTitle);

		// Recherche et extraction de la date de publication
		index = xmlStr.indexOf("tag=\"260\"");
		if (index == -1) {
			throw new IllegalArgumentException();
		}
		index = xmlStr.indexOf("code=\"c\"", index);
		if (index == -1) {
			throw new IllegalArgumentException();
		}

		int indexBeginDate = xmlStr.indexOf('>', index);
		int indexEndDate = xmlStr.indexOf('<', indexBeginDate + 1);
		if (indexBeginDate == -1 || indexEndDate == -1) {
			throw new IllegalArgumentException();
		}

		result[2] = xmlStr.substring(indexBeginDate + 1, indexEndDate - 1);

		return result;

	}
}