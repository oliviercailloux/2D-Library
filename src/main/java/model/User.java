package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	/**
	 * 	Create a user Library
	 * @param user's books
	 * @param frame size : depend of the window configuration which can be integrate in the main 
	 */
	public Library createLibrary(List<Book> books, double frameSizeW, double frameSizeH){
		Library myLib = new Library();
		List<Shelf> newShelves= new ArrayList<Shelf>();
		List<Book> list = new ArrayList<Book>();
		for(int index = 1; index <= books.size(); index++){
			list.add(books.get(index-1));
			if (index == books.size()){
				newShelves.add(new Shelf(list));
				list = new ArrayList<Book>();
			}
		}
		myLib.setShelves(newShelves);
		myLib.setFrameSizeH(frameSizeH);
		myLib.setFrameSizeW(frameSizeW);
		return myLib;
	}
}
