package io.github.dauphine.lejema160.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lejema160
 * @author OlympieSuquet
 * Library is a class that describes the library, with several shelves and books.
 */
public class Library {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Library.class);

	/**
	 * The list of the shelves of the library
	 */
	private List<Shelf> shelves;
	/**
	 * The Height of the library is define by the size of users's frame
	 */
	private double frameSizeH;
	/**
	 * The Weigth of the library is define by the size of users's frame
	 */
	private double frameSizeW;
	/**
	 * Constructor of a library with a list of shelves
	 */

	
	public Library(List<Book> books, int nbBooksPerShelf) {
		this.shelves = createLibrary(books, nbBooksPerShelf);
		Toolkit atk= Toolkit.getDefaultToolkit();
		Dimension dim =atk.getScreenSize();
		int w=dim.width;
		this.frameSizeW=w;
	}
	

	public Library() {
		super();
	}


	public double getFrameSizeH() {
		return frameSizeH;
	}



	public void setFrameSizeH(double frameSizeH) {
		this.frameSizeH = frameSizeH;
	}



	public double getFrameSizeW() {
		return frameSizeW;
	}



	public void setFrameSizeW(double frameSizeW) {
		this.frameSizeW = frameSizeW;
	}



	/**
	 * Getter of the list of shelves
	 * @return the list of shelves of the library
	 */
	public List<Shelf> getShelves() {
		return shelves;
	}
	
	/**
	 * Setter of the list of shelves
	 * @param a list of shelves
	 */
	public void setShelves(List<Shelf> shelves) {
		this.shelves = shelves;
	}
	
	public static List<Shelf> createLibrary(List<Book> books, int nbBooksPerShelf){
		List<Shelf> newShelves= new ArrayList<>();
		List<Book> list = new ArrayList<>();
		for(int index = 0; index < books.size(); index++){
			list.add(books.get(index));
			if ((index+1)%nbBooksPerShelf==0 || index+1 == books.size()){
				newShelves.add(new Shelf(list));
				list = new ArrayList<Book>();
			}
			
		}
		return newShelves;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Library [shelves=" + shelves.toString() + "]";
	}
	
	public int hashCode(){
		return Objects.hash(frameSizeH, frameSizeW, shelves);
	}
	public boolean equals(Library library){
		return this.frameSizeH == library.frameSizeH && this.frameSizeW == library.frameSizeW;
		//return this.shelves.equals(library.shelves);
	}


}