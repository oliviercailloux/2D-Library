package io.github.dauphine.lejema160.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import io.github.dauphine.lejema160.model.Author;
import io.github.dauphine.lejema160.model.Book;
import io.github.dauphine.lejema160.model.Library;

/**
 * Based on https://xmlgraphics.apache.org/batik/using/svg-generator.html (with
 * minor modifications).
 *
 */
public class SVGDrawable {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(SVGDrawable.class);

	public static void main(String[] args) throws Exception {
		System.out.println("I'm creating the exemple library...");

		Author olympie = new Author("Suquet", "Olympie");
		Author merlene = new Author("Lejeune", "Merlène");

		boolean leaning = true;
		int nbBooksPerShelf = 5;

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
		books.add(book1);
		books.add(book2);
		books.add(book3);
		books.add(book1);
		books.add(book2);
		books.add(book3);
		books.add(book1);
		books.add(book2);
		books.add(book1);
		books.add(book2);
		books.add(book3);
		books.add(book3);
		Library library = new Library(books, nbBooksPerShelf);
		generate(library, leaning, "Auto", "Auto", "Auto");
		System.out.println("I drew a library with titles !");
		try {
			Svg2jpg.convert();
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Window2DLibrary("2D_LIBRARY PROJECT");
		System.out.println("Here you go");
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
	 * Generate the SVG Library.
	 * @param leaning 
	 * @param Library
	 * @param leaning
	 */
	public static void generate(Library lib, boolean leaning, String bkColor, String bColor, String sColor) throws IOException, ParserConfigurationException {
		// Define the SVG Graphics 2D
		SVGGraphics2D graphics = generateSVG();

		int dimCanvasX = (int) ((int)lib.getFrameSizeW()-(0.055*lib.getFrameSizeW()));
		int dimCanvasY = 1500;
		int thiknessEdges = 20;

		graphics.setSVGCanvasSize(new Dimension(dimCanvasX, dimCanvasY));

		int nbShelves = lib.getShelves().size();
		int spaceBetweenShelves = 0;
		if (nbShelves > 0) {
			spaceBetweenShelves = (dimCanvasY - thiknessEdges * (2 + nbShelves - 1)) / nbShelves;
		}

		// define the back and the outlines of the library
		drawBackOutlines(graphics, dimCanvasX, dimCanvasY, thiknessEdges, bkColor, sColor);

		// define the shelves of the library
		List<Shape> shelves = drawShelves(graphics, nbShelves, thiknessEdges, spaceBetweenShelves, dimCanvasX, sColor);

		// get the width of a shelf
		int shelfWidth = dimCanvasX - 2*thiknessEdges;

		// get books
		drawBooksAndTitles(spaceBetweenShelves
				, dimCanvasX
				, thiknessEdges
				, shelfWidth
				, lib
				, graphics
				, leaning
				, shelves
				, bColor);

		// TODO : LINK SVG

		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
	
		try (Writer out = new OutputStreamWriter(new FileOutputStream("library.svg"), "UTF-8")) {
			graphics.stream(out, useCSS);
		}
	}

	
	/***
	 * Draw the shelves
	 * @param graphics
	 * @param nbShelves
	 * @param thiknessEdges
	 * @param spaceBetweenShelves
	 * @param dimCanvasX
	 * @return
	 */
	private static List<Shape> drawShelves(SVGGraphics2D graphics, int nbShelves, int thiknessEdges,
			int spaceBetweenShelves, int dimCanvasX, String sColor) {
		List<Shape> shelves = new ArrayList<>();
		switch(sColor){
			case "Light":
				graphics.setPaint(Color.decode("#CD853F"));
				break;
			case "Dark":
				graphics.setPaint(Color.decode("#660000"));
				break;
			default:
				graphics.setPaint(Color.decode("#8B4513"));
				break;
		}
		for (int i = 1; i <= nbShelves; i++) {
			Shape shelf = new Rectangle(0, thiknessEdges * i + (i) * spaceBetweenShelves, dimCanvasX, thiknessEdges);
			shelves.add(shelf);
			graphics.fill(shelf);
		}
		return shelves;
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
	private static void drawBackOutlines(SVGGraphics2D graphics, int dimCanvasX, int dimCanvasY, int thiknessEdges, String bkColor, String sColor){
		Shape fond = new Rectangle(0, 0, dimCanvasX, dimCanvasY);
		List<Shape> edges = getEdges(dimCanvasX, dimCanvasY, thiknessEdges);
		switch(bkColor){
			case "Light":
				graphics.setPaint(Color.decode("#FFF8DC"));
				break;
			case "Dark":
				graphics.setPaint(Color.decode("#330000"));
				break;
			default:
				graphics.setPaint(Color.decode("#BC8F8F"));
				break;
		}
		graphics.fill(fond);
		switch(sColor){
			case "Light":
				graphics.setPaint(Color.decode("#CD853F"));
				break;
			case "Dark":
				graphics.setPaint(Color.decode("#660000"));
				break;
			default:
				graphics.setPaint(Color.decode("#8B4513"));
				break;
		}
		for (Shape edge : edges) {
			graphics.fill(edge);
		}
	}


	/***
	 * Draw the books
	 * @param randomGenerator
	 * @param isLastBook
	 * @param book
	 * @param graphics
	 * @param emptySpace
	 * @param yOfTheShelf
	 * @param leaning
	 * @return
	 */
	
	/***
	 * Draw the title of the book
	 * @param graphics
	 * @param bookRotation
	 * @param book
	 * @param lib
	 * @param bookX
	 * @param bookY
	 * @param indexBook
	 * @param indexShelf
	 * @param bookHeight
	 */
		
	
	private static void drawBooksAndTitles(int spaceBetweenShelves, int dimCanvasX, int thiknessEdges, int shelfWidth,
			Library lib, SVGGraphics2D graphics, boolean leaning, List<Shape> shelves, String bColor) {
		List<Shape> books = new ArrayList<>();
		int idealWidth = (int)(0.6 * (float)(shelfWidth/lib.getShelves().get(0).getBooks().size()));
		//int spaceBtwnTopBookVsTopEdge = 30;
//		int height = spaceBetweenShelves - spaceBtwnTopBookVsTopEdge;
		int idealHeight = (int)(0.8 * spaceBetweenShelves);
		int spaceBtwnTopBookVsTopEdge = spaceBetweenShelves - idealHeight;
		
		int nbBooks = 0;
		for (int i = 0; i<lib.getShelves().size(); i++){
			nbBooks += lib.getShelves().get(i).getBooks().size();
		}

		int placeLeftInCurrShelf = shelfWidth;
		int shelfNumber = 1;
		Random randomGenerator = new Random();
		int emptySpace = shelfWidth;
		int counterBooks = lib.getShelves().get(shelfNumber-1).getBooks().size();
		for (int i = 0; i < nbBooks; i++) {
			Shape book = null;
			int randomWidth = idealWidth + randomGenerator.nextInt(30);
			int randomHeightGap = randomGenerator.nextInt(50);
			int heightSup = idealHeight + randomHeightGap;
			setSizeBook(spaceBetweenShelves, shelfWidth, lib.getShelves().get(shelfNumber-1).getBooks().get(i%lib.getShelves().get(0).getBooks().size()), idealWidth, idealHeight, randomWidth, heightSup);
			int width = lib.getShelves().get(shelfNumber-1).getBooks().get(i%lib.getShelves().get(0).getBooks().size()).getwidth();
			int height = lib.getShelves().get(shelfNumber-1).getBooks().get(i%lib.getShelves().get(0).getBooks().size()).getheight();
			
			if (placeLeftInCurrShelf <= width) {
				// go to another shelf
				placeLeftInCurrShelf = shelfWidth;
				shelfNumber++;
				if(shelfNumber>lib.getShelves().size()){
					// what do we do when not enough place in library?
				}
			}
			int bookX = dimCanvasX - thiknessEdges - placeLeftInCurrShelf;
			int bookY = shelfNumber * thiknessEdges + (shelfNumber - 1) * spaceBetweenShelves + spaceBetweenShelves-height;
			book = new Rectangle(bookX, bookY, width, height);
			books.add(book);
			counterBooks--;
//			if (placeLeftInCurrShelf <= width){
			if (counterBooks==0) {
				// go to another shelf
				placeLeftInCurrShelf = shelfWidth;
				shelfNumber++;
				if(shelfNumber<lib.getShelves().size()) counterBooks = lib.getShelves().get(shelfNumber-1).getBooks().size();
			} else {
				// stay in the current shelf
				placeLeftInCurrShelf -= width;
			}
			emptySpace -= book.getBounds().getWidth();
		}
		
		int indexShelf = 0;
		int indexBook = 0;
		int lastColorIndex = -1;
		placeLeftInCurrShelf = shelfWidth;
		for (Shape book : books){
			double YOfTheShelf = shelves.get(indexShelf).getBounds().getY();
			boolean isLastBookOfTheShelf = (indexBook+1) % lib.getShelves().get(indexShelf).getBooks().size() == 0;
			int[] table = drawBook(randomGenerator, isLastBookOfTheShelf, book, graphics, placeLeftInCurrShelf, YOfTheShelf, leaning, bColor, lastColorIndex);
			lastColorIndex = table[2];
			int bookRotation = table[0];
			double bookX = book.getBounds().getX();
			double bookY;
			if (isLastBookOfTheShelf) {
				bookY = table[1];
				System.out.println("bookY : " + bookY);
			}
			else {
				bookY = book.getBounds().getY();
			}
			double bookHeight = book.getBounds().getHeight();
			double bookWidth = book.getBounds().getWidth();
			String bookTitle = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getTitle();
			String authorFirstName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor().getFirstName();
			String authorLastName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor().getLastName();
			int bookYear = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getYear();
			String bookString = bookTitle+" - "+authorFirstName+" "+authorLastName+ " - " +  bookYear;
			drawTitle(graphics, bookRotation, bookString, book, bookX, bookY, indexBook, bookHeight, bColor, bookWidth);
			if (isLastBookOfTheShelf){
				indexShelf++;
				indexBook=0;
				placeLeftInCurrShelf = shelfWidth;
			}
			else{
				indexBook++;
				placeLeftInCurrShelf -= book.getBounds().getWidth();
			}
		}
		
	}
	
	
	private static int[] drawBook(Random randomGenerator, boolean isLastBook, Shape book, SVGGraphics2D graphics, double emptySpace, double yOfTheShelf, boolean leaning, String bColor, int lastColorIndex){

		// list of random colors
		List<Color> colors = new ArrayList<>();
		switch(bColor){
			case "Light":
				//pink
				colors.add(Color.decode("#FF66FF"));
				//purple
				colors.add(Color.decode("#CC99FF"));
				//blue
				colors.add(Color.decode("#33CCFF"));
				//yellow
				colors.add(Color.decode("#FFFF66"));
				//orange
				colors.add(Color.decode("#FFCC66"));
				break;
			case "Dark":
				colors.add(Color.decode("#990033"));
				colors.add(Color.decode("#330033"));
				colors.add(Color.decode("#000033"));
				colors.add(Color.decode("#CC9900"));
				colors.add(Color.decode("#993300"));
				break;
			default:
				colors.add(Color.pink);
				colors.add(Color.decode("#9933FF"));
				colors.add(Color.BLUE);
				colors.add(Color.yellow);
				colors.add(Color.ORANGE);
				break;
		}
		int colorIndex = -1;

		// generate a random color for this book
		do {
			colorIndex = randomGenerator.nextInt(colors.size());
		} while (colorIndex == lastColorIndex);

		lastColorIndex = colorIndex;

		//select this color
		graphics.setPaint(colors.get(colorIndex));

		//paint the book (with rotation if the last book)
		int[] result = new int[3];
		result[2] = colorIndex;
		int bookRotation = 0;
		if (isLastBook && leaning){//on penche le dernier livre
			bookRotation = -20 - randomGenerator.nextInt(10);
			System.out.println("XEED : " + emptySpace + " : " + Math.abs(Math.sin(90-bookRotation)*book.getBounds().getWidth()) + " : " + Math.abs(Math.sin(bookRotation)*book.getBounds().getHeight()));
			//if (!(emptySpace > Math.sin(90-bookRotation)*book.getBounds().getWidth() + Math.sin(bookRotation)*book.getBounds().getHeight())) System.out.println("si qlq voit cette erreur le dire a merlene");
			if(emptySpace > Math.abs(Math.sin(90-bookRotation)*book.getBounds().getWidth()) + Math.abs(Math.sin(bookRotation)*book.getBounds().getHeight())){//il faut qu'il reste au moins trois fois la largeur du livre
				// Height between the top left corner of the book and the shelf when leaning
				double hauteurRotation = book.getBounds().getHeight()*Math.cos(Math.toRadians(bookRotation));
				// The new Y coordinate of the leaning rectangle (so that it is placed on the shelf)
				double newY = yOfTheShelf-hauteurRotation;
				Rectangle newRectangle = new Rectangle((int)book.getBounds().getX(), (int)newY, (int)book.getBounds().getWidth(), (int)book.getBounds().getHeight());
				int newBookX = (int)newRectangle.getBounds().getX();
				int newBookY = (int)newRectangle.getBounds().getY();
				graphics.rotate(Math.toRadians(bookRotation), newBookX, newBookY);
				graphics.fill(newRectangle);
				graphics.rotate(Math.toRadians(-bookRotation), newBookX, newBookY);
				result[1]=newBookY;
			}
			else{//pas assez de place pour le pencher
				bookRotation = 0;
				double hauteurRotation = book.getBounds().getHeight()*Math.cos(Math.toRadians(bookRotation));
				double newY = yOfTheShelf-hauteurRotation;
				Rectangle newRectangle = new Rectangle((int)book.getBounds().getX(), (int)newY, (int)book.getBounds().getWidth(), (int)book.getBounds().getHeight());
				graphics.fill(newRectangle);
				result[1]=(int)newY;
			}
		}
		else {
			double hauteurRotation = book.getBounds().getHeight()*Math.cos(Math.toRadians(bookRotation));
			double newY = yOfTheShelf-hauteurRotation;
			Rectangle newRectangle = new Rectangle((int)book.getBounds().getX(), (int)newY, (int)book.getBounds().getWidth(), (int)book.getBounds().getHeight());
			graphics.fill(newRectangle);
			result[1]=(int)newY;
		}
		result[0] = bookRotation;
		return result;
	}
	
	private static void drawTitle(SVGGraphics2D graphics, int bookRotation, String bookString, Shape book, double bookX, double bookY, int indexBook, double bookHeight, String bColor,  double bookWidth){

		//select the black color for the title
		if (bColor.equals("Dark")) graphics.setPaint(Color.white);
		else graphics.setPaint(Color.black);

		//draw the title with the same rotation as the book

		graphics.rotate(Math.toRadians(+90+bookRotation), bookX, bookY);
		int fontSize = 70;
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

		// change the size of the title if it is too long
		if (graphics.getFontMetrics().stringWidth(bookString) > 6*bookHeight/10){
			while( graphics.getFontMetrics().stringWidth(bookString) > 6*bookHeight/10){
				fontSize = fontSize -3;
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			}
			graphics.drawString(bookString,(float) (bookX + ((bookHeight-graphics.getFontMetrics().stringWidth(bookString))/2)) , (float) (bookY - ((bookWidth) / 4)));
		}
		else graphics.drawString(bookString,(float) (bookX + ((bookHeight-graphics.getFontMetrics().stringWidth(bookString))/2)) , (float) (bookY - ((bookWidth) / 4)));
		
		graphics.rotate(Math.toRadians(-90-bookRotation), bookX, bookY);
	}
	
	public static void setSizeBook (int ShelfHeight, int ShelfWidth, Book book, int idealWidth, int idealHeight, int widthSup, int heightSup) {
		
		if (!(book.getwidth() >= idealWidth && book.getwidth() <= widthSup)){
			book.setwidth(widthSup);
		}
		if (!(book.getheight() >= idealHeight && book.getheight() <= heightSup)){
			book.setheight(heightSup);
		}
		if(!(book.getwidth() >= book.getheight()/10 && book.getwidth() <= book.getheight()/8)){
			book.setwidth(book.getheight()/9);
		}
	}
	

}