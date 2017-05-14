package controler;

import java.awt.Color;

import model.Book;
import model.Library;
import model.Shelf;

public class Options {
	/**
	 * changes the color of all the book of the library
	 * @param lib
	 * @param color
	 * @return
	 */
	public Library ChangeColorBooks(Library lib, Color color){
		
		for(int i = 0; i < lib.getShelves().size(); i++){
			
			Shelf shelf = lib.getShelves().get(i);
			
			for(int j = 0; j < shelf.getBooks().size(); j++){
				
				Book book = shelf.getBooks().get(j);
				book.setColor(color);
				
			}
			
		}
		
		return lib;
	}
	
}
