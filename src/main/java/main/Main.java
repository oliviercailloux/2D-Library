package main;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import model.Author;
import model.Book;
import model.Library;
import model.Shelf;
import model.User;
import view.SVGDrawable;
import controler.readFile;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException {
	
		List<Book> books = controler.readFile.read();
		Library Lib = model.User.createLibrary(books, 50, 70);
		view.SVGDrawable.drawTitle(Lib);
		System.out.println("Fait");
		
		
	}

}
