package io.github.dauphine.lejema160.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lejema160
 * @author OlympieSuquet
 * Library is a class that describes the library, with several shelves and books.
 */
public class Library {

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
	public Library(List<Shelf> shelves) {
		this.shelves = shelves;
		Toolkit atk= Toolkit.getDefaultToolkit();
		Dimension dim =atk.getScreenSize();
		int w=dim.width;
		this.frameSizeW=w-10;
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

}