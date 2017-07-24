package io.github.oliviercailloux.twod_library.model;

import java.awt.Color;
import java.util.Comparator;
import java.util.Objects;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Book is a class that describes a book. It has a title and an author.
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
	 * The year of the book
	 */
	private int year;

	/**
	 * The width of the book
	 */
	private int width;
	/**
	 * The height of the book
	 */
	private int height;
	/**
	 * the color of the book
	 */
	private Color color;

	/**
	 * Book's constructor with the title and the author of the book.
	 * 
	 * @param the
	 *            title of the book
	 * @param the
	 *            author of the book
	 */
	public Book(String title, Author author, int year) {
		this.title = title;
		this.author = author;
		this.year = year;
	}

	public Book() {}

	/**
	 * Getter of the title of the book
	 * 
	 * @return the title of the book
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter of the title of the book
	 * 
	 * @param the
	 *            title of the book
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter of the author of the book
	 * 
	 * @return the author of the book
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * Setter of the author of the book
	 * 
	 * @param the
	 *            author of the book
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

	/**
	 * Getter of the author of the book
	 * 
	 * @return the author of the book
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Setter of the year of the book
	 * 
	 * @param the
	 *            year of the book
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/***
	 * 
	 */
	public void generateSizeX() {
		int width = 60;
		Random randomGenerator = new Random();
		int randomWidth = width + randomGenerator.nextInt(30);
		setWidth(randomWidth);
	}

	/***
	 * 
	 */
	public void generateSizeY() {
		int height = 30;
		Random randomGenerator = new Random();
		int randomHeightGap = randomGenerator.nextInt(100);
		height = height - randomHeightGap;
		setHeight(height);
	}

	/***
	 * 
	 */
	@Override
	public String toString() {
		return "Book [title=" + title + "]";
	}

	/***
	 * 
	 */
	public int hashCode() {
		return Objects.hash(author, color, height, title, width, year);
	}

	/***
	 * 
	 */
	public boolean isEqualTo(Object book) {
		if (book == null)
			return false;
		if (book == this)
			return true;
		if (!(book instanceof Book))
			return false;
		Book castedBook = (Book) book;
		return this.author.equals(castedBook.author) && this.color.equals(castedBook.color)
				&& this.height == castedBook.height && this.title.equals(castedBook.title)
				&& this.width == castedBook.width && this.year == castedBook.year;
	}

	/***
	 * 
	 * @param position
	 * @param word
	 */
	public void setBookAttribute(int position, String word) {

		switch (position) {
		case 0:
			this.author.setLastName(word);
			break;
		case 1:
			this.author.setFirstName(word);
			break;
		case 2:
			this.title = word;
			break;
		case 3:
			int year = Integer.parseInt(word);
			this.year = year;
			break;
		case 4:
			if (word == "") {
				this.generateSizeX();
			} else {
				int dimx = Integer.parseInt(word);
				this.width = dimx;
			}
			break;
		case 5:
			if (word == "") {
				this.generateSizeY();
			} else {
				int dimy = Integer.parseInt(word);
				this.height = dimy;
			}
			break;
		case 6:
			switch (word) {
			case "rose":
				this.color = Color.pink;
				break;
			case "violet":
				this.color = Color.decode("#9933FF");
				break;
			case "bleu":
				this.color = Color.BLUE;
				break;
			case "orange":
				this.color = Color.ORANGE;
				break;
			case "jaune":
				this.color = Color.yellow;
				break;
			case "vert":
				this.color = Color.decode("#92c544");
				break;
			case "rouge":
				this.color = Color.decode("#d41c1c");
			}
			break;
		case 7:
			if (word == "End") {
				break;
			}

		}
	}

	/***
	 * Return true if this book is older than the bookToCompare.
	 * 
	 * @param book
	 *            to compare.
	 * @return true if this book is older than the book to compare.
	 */
	public boolean compareYear(Book bookToCompare) {
		return this.year <= bookToCompare.getYear();
	}

}

/**
 * A comparator of books by title
 * 
 * @author lejema160
 *
 */
class BookCompareByTitle implements Comparator<Book> {

	/**
	 * implementation of the compare method of Comparator<>
	 * 
	 * @param b1
	 *            b2 the books we compare
	 * @return an int, less than 0 if b1 is lexically after b2 more than 0 if b1
	 *         is lexically before b2 equal to 0 if b1 is equal to b2
	 */
	@Override
	public int compare(Book b1, Book b2) {
		return (b1.getTitle().compareTo(b2.getTitle()));
	}
}

class BookCompareByAuthor implements Comparator<Book> {

	/**
	 * implementation of the compare method of Comparator<>
	 * 
	 * @param b1
	 *            b2 the books we compare
	 * @return an int, less than 0 if b1 is lexically after b2 more than 0 if b1
	 *         is lexically before b2 equal to 0 if b1 is equal to b2
	 */
	@Override
	public int compare(Book b1, Book b2) {
		return (b1.getAuthor().getLastName().compareTo(b2.getAuthor().getLastName()));
	}
}