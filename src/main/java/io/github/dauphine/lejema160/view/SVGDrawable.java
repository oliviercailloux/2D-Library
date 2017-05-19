package io.github.dauphine.lejema160.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import io.github.dauphine.lejema160.controller.BookSort;
import io.github.dauphine.lejema160.model.Author;
import io.github.dauphine.lejema160.model.Book;
import io.github.dauphine.lejema160.model.Library;
import io.github.dauphine.lejema160.model.Shelf;

/**
 * Based on https://xmlgraphics.apache.org/batik/using/svg-generator.html (with
 * minor modifications).
 *
 */
public class SVGDrawable {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SVGDrawable.class);

	private static int[][] titleXY = new int[3][2];
	private static int[] heig = new int[3];

	public static void main(String[] args) throws Exception {
		System.out.println("I'm creating the exemple library...");

		Author olympie = new Author("Suquet", "Olympie");
		Author merlene = new Author("Lejeune", "Merlène");

		boolean leaning = true;

		Book book1 = new Book();
		book1.setAuthor(olympie);
		book1.setTitle("Recette de la soupe qui fait grandir");
		book1.setYear(2014);
		Book book2 = new Book();
		book2.setAuthor(olympie);
		book2.setTitle("DICO");
		book2.setYear(202);
		Book book3 = new Book();
		book3.setAuthor(merlene);
		book3.setTitle("Tutoriel beauté");
		book3.setYear(2506);
		List<Book> books = new ArrayList<>();
		books.add(book1);
		books.add(book2);
		books.add(book3);
		Shelf shelf = new Shelf(books);
		Shelf shelf2 = new Shelf(books);
		Shelf shelf3 = new Shelf(books);
		List<Shelf> shelves = new ArrayList<>();
		shelves.add(shelf);
		shelves.add(shelf2);
		shelves.add(shelf3);
		Library library = new Library(shelves);
		generate(library, leaning);
		System.out.println("I drew a library with titles !");
		try {
			Svg2jpg.convert();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Window2DLibrary("2D_LIBRARY PROJECT");
	}


	/***
	 * Generate the borders of the library.
	 * @param dimCanvasX
	 * @param dimCanvasY
	 * @param thinknessEdges
	 * @return List of the borders of the library.
	 */
	private static List<Shape> getEdges(int dimCanvasX, int dimCanvasY, int thinknessEdges) {
		List<Shape> res = new ArrayList<Shape>();
		Shape left = new Rectangle(0, 0, thinknessEdges, dimCanvasY);
		Shape right = new Rectangle(dimCanvasX - thinknessEdges, 0, thinknessEdges, dimCanvasY);
		Shape top = new Rectangle(0, 0, dimCanvasX, thinknessEdges);
		Shape bot = new Rectangle(0, dimCanvasY - thinknessEdges, dimCanvasX, thinknessEdges);
		res.add(top);
		res.add(bot);
		res.add(right);
		res.add(left);
		return res;
	}

	/***
	 * Create random shapes of books placed in the library.
	 * @param spaceBetweenShelves
	 * @param dimCanvasX
	 * @param thiknessEdges
	 * @param nbShelves
	 * @return Random shapes of books placed in the library.
	 */
	private static List<Shape> CreateBooks(
			int spaceBetweenShelves
			, int dimCanvasX
			, int thiknessEdges
			, int nbShelves
			, int shelfWidth){
		List<Shape> books = new ArrayList<>();
		int width = 60;
		int spaceBtwnTopBookVsTopEdge = 30;
		int height = spaceBetweenShelves - spaceBtwnTopBookVsTopEdge;

		// LE NBBOOKS DOIT ETRE EGAL AU NOMBRE DE LIVRE DANS LA LIBRAIRIE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TO DO
		int nbBooks = 3;

		int placeLeftInCurrShelf = shelfWidth;
		int shelfNumber = 1;
		Random randomGenerator = new Random();


		for (int i = 0; i < nbBooks; i++) {
			Shape book = null;

			int randomWidth = width + randomGenerator.nextInt(30);
			int randomHeightGap = randomGenerator.nextInt(100);
			if (placeLeftInCurrShelf <= randomWidth) {
				// go to another shelf
				placeLeftInCurrShelf = shelfWidth;
				shelfNumber++;
				if(shelfNumber>nbShelves){
					// what do we do when not enough place in library?
				}
			}
			book = new Rectangle(dimCanvasX - thiknessEdges - placeLeftInCurrShelf
					,shelfNumber * thiknessEdges + (shelfNumber - 1) * spaceBetweenShelves + spaceBtwnTopBookVsTopEdge + randomHeightGap
					,randomWidth
					, height - randomHeightGap);
			heig[i]=height - randomHeightGap;
			titleXY[i][0] = dimCanvasX - thiknessEdges - placeLeftInCurrShelf;
			titleXY[i][1] = shelfNumber * thiknessEdges + (shelfNumber - 1) * spaceBetweenShelves + spaceBtwnTopBookVsTopEdge + randomHeightGap;
			books.add(book);
			if (placeLeftInCurrShelf <= width) {
				// go to another shelf
				placeLeftInCurrShelf = shelfWidth;
				shelfNumber++;
			} else {
				// stay in the current shelf

				placeLeftInCurrShelf -= randomWidth;

			}
		}
		return books;
	}

	/***
	 * Generate the SVG Library.
	 * @param leaning 
	 * @param Library
	 * @param leaning
	 */
	public static void generate(Library lib, boolean leaning) throws IOException, ParserConfigurationException {
		// Define the SVG Graphics 2D
		SVGGraphics2D graphics = generateSVG();


		int dimCanvasX = (int)lib.getFrameSizeW()-80;
		System.out.println(dimCanvasX);
		int dimCanvasY = 1500;
		int thiknessEdges = 20;

		graphics.setSVGCanvasSize(new Dimension(dimCanvasX, dimCanvasY));

		int nbShelves = lib.getShelves().size();
		int spaceBetweenShelves = 0;
		if (nbShelves > 0) {
			spaceBetweenShelves = (dimCanvasY - thiknessEdges * (2 + nbShelves - 1)) / nbShelves;
		}

		// define the back and the outlines of the library
		drawBackOutlines(graphics, dimCanvasX, dimCanvasY, thiknessEdges);

		// define the shelves of the library
		List<Shape> shelves = new ArrayList<>();
		for (int i = 1; i <= nbShelves; i++) {
			Shape shelf = new Rectangle(0, thiknessEdges * i + (i) * spaceBetweenShelves, dimCanvasX, thiknessEdges);
			shelves.add(shelf);
			graphics.fill(shelf);
		}

		// get the width of a shelf
		int shelfWidth = dimCanvasX - 2*thiknessEdges;


		// get books
		List<Shape> books = CreateBooks(spaceBetweenShelves
				, dimCanvasX
				, thiknessEdges
				, nbShelves
				, shelfWidth);
		double emptySpace = shelfWidth;
		for (Shape book : books){
			emptySpace -= book.getBounds().getWidth();
		}

		// add books
		Random randomGenerator = new Random();
		int indexShelf = 1;
		int indexBook = 0;

		for (Shape book : books) {

			int bookRotation = drawBook(randomGenerator, books, book, graphics, emptySpace, indexBook, indexShelf, shelves, leaning);

			drawTitle(graphics, bookRotation, books, book, lib, indexBook, indexShelf);

			if (indexBook + 1 >= lib.getShelves().get(indexShelf).getBooks().size()){
				indexShelf++;
			}
			indexBook++;
		}

		// TODO : LINK SVG

		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
	
		try (Writer out = new OutputStreamWriter(new FileOutputStream("library.svg"), "UTF-8")) {
			graphics.stream(out, useCSS);
			
		}
	}

	/***
	 * Generate the SVG
	 * @return the SVGGraphics2D on which we are drawing
	 * @throws ParserConfigurationException
	 */
	private static SVGGraphics2D generateSVG() throws ParserConfigurationException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		// Get a DOMImplementation.
		DOMImplementation domImpl = db.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document.
		String svgNS = "http://www.w3.org/2000/svg";
		Document document = domImpl.createDocument(svgNS, "svg", null);

		// Create an instance of the SVG Generator.
		SVGGeneratorContext ctx = SVGGeneratorContext.createDefault(document);
		ctx.setEmbeddedFontsOn(true);
		return new SVGGraphics2D(ctx, true);
	}

	/***
	 * Define the back and the outlines of the library.
	 * @param dimCanvasX
	 * @param dimCanvasY
	 * @param thiknessEdges
	 * @param graphics
	 */
	private static void drawBackOutlines(SVGGraphics2D graphics, int dimCanvasX, int dimCanvasY, int thiknessEdges){
		Shape fond = new Rectangle(0, 0, dimCanvasX, dimCanvasY);
		List<Shape> edges = getEdges(dimCanvasX, dimCanvasY, thiknessEdges);

		graphics.setPaint(Color.decode("#565633"));
		graphics.fill(fond);
		graphics.setPaint(Color.decode("#FFCCEE"));
		for (Shape edge : edges) {
			graphics.fill(edge);
		}
	}

	/***
	 * Draw the books
	 * @param randomGenerator
	 * @param books
	 * @param book
	 * @param graphics
	 * @param emptySpace
	 * @param indexBook
	 * @param indexShelf
	 * @param shelves
	 * @param leaning 
	 * @return
	 */
	private static int drawBook(Random randomGenerator, List<Shape> books, Shape book, SVGGraphics2D graphics, double emptySpace, int indexBook, int indexShelf, List<Shape> shelves, boolean leaning){
		// list of random colors
		List<Color> colors = new ArrayList<>();
		colors.add(Color.pink);
		colors.add(Color.CYAN);
		colors.add(Color.BLUE);
		colors.add(Color.yellow);
		colors.add(Color.ORANGE);

		int colorIndex = -1;
		int lastColorIndex = -1;

		// generate a random color for this book
		do {
			colorIndex = randomGenerator.nextInt(colors.size());
		} while (colorIndex == lastColorIndex);

		lastColorIndex = colorIndex;

		//select this color
		graphics.setPaint(colors.get(colorIndex));

		//paint the book (with rotation if the last book)
		int bookRotation = 0;
		if (isLastBook(books, book) && leaning){
			bookRotation = -30;
			if(emptySpace>3*book.getBounds().getWidth()){
				// Height between the top left corner of the book and the shelf when leaning
				double hauteurRotation = book.getBounds().getHeight()*Math.cos(Math.toRadians(bookRotation));
				// The new Y coordinate of the leaning rectangle (so that it is placed on the shelf)
				double newY = shelves.get(indexShelf -1).getBounds().getY()-hauteurRotation;
				Rectangle newRectangle = new Rectangle((int)book.getBounds().getX(), (int)newY, (int)book.getBounds().getWidth(), (int)book.getBounds().getHeight());
				titleXY[indexBook][0] = (int)newRectangle.getBounds().getX();
				titleXY[indexBook][1] = (int)newRectangle.getBounds().getY();
				graphics.rotate(Math.toRadians(bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
				graphics.fill(newRectangle);
				graphics.rotate(Math.toRadians(-bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
			}
		}
		else {
			graphics.fill(book);
		}
		return bookRotation;
	}

	/***
	 * Draw the title of the book
	 * @param graphics
	 * @param bookRotation
	 * @param books
	 * @param book
	 * @param lib
	 * @param indexBook
	 * @param indexShelf
	 * @param indexShelf2 
	 * @param indexBook2 
	 */
	private static void drawTitle(SVGGraphics2D graphics, int bookRotation, List<Shape> books, Shape book, Library lib, int indexBook, int indexShelf){
		//select the black color for the title
		graphics.setPaint(Color.black);

		//draw the title with the same rotation as the book

		graphics.rotate(Math.toRadians(+90+bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
		int fontSize = 70;
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

		String bookTitle = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getTitle();
		String authorFirstName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor().getFirstName();
		String authorLastName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor().getLastName();		
		String bookString = bookTitle+" - "+authorFirstName+" "+authorLastName;

		// change the size of the title if it is too long
		if (graphics.getFontMetrics().stringWidth(bookString) > heig[indexBook]-25){
			while( graphics.getFontMetrics().stringWidth(bookString) > heig[indexBook]-25){
				fontSize = fontSize -3;
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			}
			graphics.drawString(bookString,titleXY[indexBook][0] + ((heig[indexBook]-graphics.getFontMetrics().stringWidth(bookString))/2) , (float) (titleXY[indexBook][1] - ((book.getBounds2D().getWidth() - graphics.getFontMetrics().getHeight()) / 2)));
		}
		else graphics.drawString(bookString,titleXY[indexBook][0] + ((heig[indexBook]-graphics.getFontMetrics().stringWidth(bookString))/2) , (float) (titleXY[indexBook][1] - ((book.getBounds2D().getWidth() - graphics.getFontMetrics().getHeight()) / 2)));

		graphics.rotate(Math.toRadians(-90-bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
	}

	/***
	 * Assert if the book is the last book of the list books
	 * @param books
	 * @param book
	 * @return true if the book is the last book of the list books
	 */
	private static boolean isLastBook(List<Shape> books, Shape book){
		return books.get(books.size()-1)==book;
	}


}