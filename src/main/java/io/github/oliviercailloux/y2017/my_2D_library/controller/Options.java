package io.github.oliviercailloux.y2017.my_2D_library.controller;

import java.awt.Color;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.oliviercailloux.y2017.my_2D_library.model.Book;
import io.github.oliviercailloux.y2017.my_2D_library.model.Library;
import io.github.oliviercailloux.y2017.my_2D_library.model.Shelf;

public class Options {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Options.class);
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
