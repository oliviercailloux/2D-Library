package io.github.dauphine.lejema160.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.dauphine.lejema160.controller.BookSort;

/**
 * @author lejema160
 * @author OlympieSuquet
 * Book is a class that describes a book. It has a title and an author.
 */
public class Book {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Book.class);

	/**
	 * The title of the book.
	 */
	private String title;
	
	/**
	 * The author of the book.
	 */
	private Author author;
	
	/**
	 * 
	 */
	private int year;
	
	/**
	 * 
	 */
	private int width;
	/**
	 * 
	 */
	private int height;
	/**
	 * 
	 */
	private Color color;
	/**
	 * Book's constructor with the title and the author of the book.
	 * @param the title of the book
	 * @param the author of the book
	 */
	public Book(String title, Author author, int year) {
		this.title = title;
		this.author = author;
		this.year=year;
	}
	
	public Book() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Getter of the title of the book
	 * @return the title of the book
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Setter of the title of the book
	 * @param the title of the book
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Getter of the author of the book
	 * @return the author of the book
	 */
	public Author getAuthor() {
		return author;
	}
	
	/**
	 * Setter of the author of the book
	 * @param the author of the book
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	/**
	 * Getter of the author of the book
	 * @return the author of the book
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Setter of the year of the book
	 * @param the year of the book
	 */
	public void setYear(int year) {
		this.year = year;
	}
	public int getwidth() {
		return width;
	}

	public void setwidth(int width) {
		this.width = width;
	}

	public int getheight() {
		return height;
	}

	public void setheight(int height) {
		this.height = height;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void generatecolor(){
		Random randomGenerator = new Random();
		int lastColorIndex = -1;
		
		List<Color> colors = new ArrayList<>();
		colors.add(Color.pink);
		colors.add(Color.CYAN);
		colors.add(Color.BLUE);
		colors.add(Color.yellow);
		colors.add(Color.ORANGE);
		
		int colorIndex = -1;
		
		// generate a random color for this book
		do {
			colorIndex = randomGenerator.nextInt(colors.size());
		} while (colorIndex == lastColorIndex);
		
		lastColorIndex = colorIndex;
		
		//select this color
		setColor(colors.get(colorIndex));
		}
	
	
	public void generatesizeX(){
		int width = 60;
		Random randomGenerator = new Random();
		int randomWidth = width + randomGenerator.nextInt(30);
		setwidth(randomWidth);
	}
	public void generatesizeY(){
		int height =30;
		Random randomGenerator = new Random();
		int randomHeightGap = randomGenerator.nextInt(100);
		height = height - randomHeightGap;
		setheight(height);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [title=" + title + "]";
	}
	
	public int hashCode(){
		return Objects.hash(author, color, height, title, width, year);
	}

	public boolean equals(Object book){
		if (book == null) return false;
		if (book == this) return true;
		if (!(book instanceof Book)) return false;
		Book castedBook = (Book) book;
		return this.author.equals(castedBook.author) && this.color.equals(castedBook.color)
				&& this.height == castedBook.height && this.title.equals(castedBook.title)
				&& this.width == castedBook.width && this.year == castedBook.year;
	}
	
}







