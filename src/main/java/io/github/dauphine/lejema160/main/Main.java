package io.github.dauphine.lejema160.main;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.dauphine.lejema160.model.Book;
import io.github.dauphine.lejema160.model.Library;
import io.github.dauphine.lejema160.view.Svg2jpg;
import io.github.dauphine.lejema160.view.Window2DLibrary;

public class Main {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException, ParserConfigurationException {
		
		// lire le fichier afin de créer une liste de livres
		List<Book> books = io.github.dauphine.lejema160.controller.readFile.read();
		//Library Lib = io.github.dauphine.lejema160.model.User.createLibrary(books, 50, 70);
		
		// créer une librairie à partir de la liste de livres
		int nbBooksPerShelf = 15;
		Library Lib = new Library(books, nbBooksPerShelf);
		boolean leaning = true;
		
		
		io.github.dauphine.lejema160.view.SVGDrawable.generate(Lib, leaning, "Auto", "Auto", "Auto");
				
		// lancement de l'interface graphique
		try {
			Svg2jpg.convert();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Window2DLibrary("2D_LIBRARY PROJECT");
	}

}
