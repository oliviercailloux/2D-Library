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
import java.util.Scanner;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

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

	private static int[][] titleXY = new int[3][2];
	private static int[] heig = new int[3];
	public static void main(String[] args) throws Exception {
		System.out.println("Enter the number corresponding to your name (complete the main if necessary to launch your method:");
		System.out.println("1 : Hugo");
		System.out.println("2 : Olympie");
		System.out.println("3 : Fanny");
		System.out.println("4 : Thibaud");
		System.out.println("5 : Elie");
		System.out.println("6 : Merlene");

		Author olympie = new Author("Suquet", "Olympie");
		Author merlene = new Author("Lejeune", "Merlène");
		
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
		try (Scanner scan = new Scanner(System.in)) {
			String s = scan.nextLine();
			try {
				int choice = Character.getNumericValue(s.charAt(0));
				switch (choice) {
				case 1:
					weshwesh(library);
					break;
				case 6:
					drawTitle(library);
					System.out.println("I draw a library with titles");
					break;
				default:
					System.out.println("Please complete the switch in the main to make your method run.");
					break;
				}
			} catch (Exception e) {
				System.out.println("getmessage");
				System.out.println(e.getMessage());
				System.out.println(" ");
				System.out.println("toString");
				System.out.println(e.toString());
				System.out.println(" ");
				System.out.println("printStackTrace");
				e.printStackTrace();
				System.out.println("Please register a number of the list");
			}
		}
		
	}


	public static void weshwesh(Library lib) throws IOException, ParserConfigurationException {
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
		SVGGraphics2D g = new SVGGraphics2D(ctx, true);

		
		int dimCanvasX = 2000;
		int dimCanvasY = 1500;
		int thiknessEdges = 20;

		g.setSVGCanvasSize(new Dimension(dimCanvasX, dimCanvasY));

		int nbShelves = lib.getShelves().size();
		int spaceBetweenShelves = 0;
		if (nbShelves > 0) {
			spaceBetweenShelves = (dimCanvasY - thiknessEdges * (2 + nbShelves - 1)) / nbShelves;
		}

		// define the back and the outlines of the library
		Shape fond = new Rectangle(0, 0, dimCanvasX, dimCanvasY);
		List<Shape> edges = getEdges(dimCanvasX, dimCanvasY, thiknessEdges);

		g.setPaint(Color.decode("#565633"));
		g.fill(fond);
		g.setPaint(Color.decode("#FFCCEE"));
		for (Shape edge : edges) {
			g.fill(edge);
		}
		
		// define the shelves of the library
		for (int i = 1; i <= nbShelves; i++) {
			Shape shelf = new Rectangle(0, thiknessEdges * i + (i) * spaceBetweenShelves, dimCanvasX, thiknessEdges);
			g.fill(shelf);
		}

		
		
		// list of random colors
		List<Color> colors = new ArrayList<>();
		colors.add(Color.pink);
		colors.add(Color.CYAN);
		colors.add(Color.BLUE);
		colors.add(Color.yellow);
		colors.add(Color.ORANGE);

		// get books.
		List<Shape> books = CreateBooks(spaceBetweenShelves
				, dimCanvasX
				, thiknessEdges
				, nbShelves);

		// add books
		Random randomGenerator = new Random();
		int lastColorIndex = -1;
		for (Shape book : books) {
			int colorIndex = -1;
			do {
				colorIndex = randomGenerator.nextInt(colors.size());
			} while (colorIndex == lastColorIndex);
			lastColorIndex = colorIndex;
			g.setPaint(colors.get(colorIndex));
			g.fill(book);
		}

		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		try (Writer out = new OutputStreamWriter(new FileOutputStream("libraryDroite.svg"), "UTF-8")) {
			g.stream(out, useCSS);
		}

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
			, int nbShelves){
		List<Shape> books = new ArrayList<>();
		int width = 60;
		int spaceBtwnTopBookVsTopEdge = 30;
		int height = spaceBetweenShelves - spaceBtwnTopBookVsTopEdge;

		// LE NBBOOKS DOIT ETRE EGAL AU NOMBRE DE LIVRE DANS LA LIBRAIRIE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! TO DO
		int nbBooks = 3;
		
		int placeInOneShelf = dimCanvasX - 2 * thiknessEdges;

		int placeLeftInCurrShelf = placeInOneShelf;
		int shelfNumber = 1;
		Random randomGenerator = new Random();
		
		
		for (int i = 0; i < nbBooks; i++) {
			Shape book = null;

			int randomWidth = width + randomGenerator.nextInt(30);
			int randomHeightGap = randomGenerator.nextInt(100);
			if (placeLeftInCurrShelf <= randomWidth) {
				// go to another shelf
				placeLeftInCurrShelf = placeInOneShelf;
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
				placeLeftInCurrShelf = placeInOneShelf;
				shelfNumber++;
			} else {
				// stay in the current shelf

				placeLeftInCurrShelf -= randomWidth;

			}
		}
		return books;
	}

	public static void drawTitle(Library lib) throws IOException, ParserConfigurationException {
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
		SVGGraphics2D g = new SVGGraphics2D(ctx, true);
		
		int dimCanvasX = 2000;
		int dimCanvasY = 1500;
		int thiknessEdges = 20;

		g.setSVGCanvasSize(new Dimension(dimCanvasX, dimCanvasY));

		int nbShelves = lib.getShelves().size();
		int spaceBetweenShelves = 0;
		if (nbShelves > 0) {
			spaceBetweenShelves = (dimCanvasY - thiknessEdges * (2 + nbShelves - 1)) / nbShelves;
		}

		// define the back and the outlines of the library
		Shape fond = new Rectangle(0, 0, dimCanvasX, dimCanvasY);
		List<Shape> edges = getEdges(dimCanvasX, dimCanvasY, thiknessEdges);

		g.setPaint(Color.decode("#565633"));
		g.fill(fond);
		g.setPaint(Color.decode("#FFCCEE"));
		for (Shape edge : edges) {
			g.fill(edge);
		}
		
		// define the shelves of the library
		for (int i = 1; i <= nbShelves; i++) {
			Shape shelf = new Rectangle(0, thiknessEdges * i + (i) * spaceBetweenShelves, dimCanvasX, thiknessEdges);
			g.fill(shelf);
		}
		
		// list of random colors
		List<Color> colors = new ArrayList<>();
		colors.add(Color.pink);
		colors.add(Color.CYAN);
		colors.add(Color.BLUE);
		colors.add(Color.yellow);
		colors.add(Color.ORANGE);

		// get books
		List<Shape> books = CreateBooks(spaceBetweenShelves
				, dimCanvasX
				, thiknessEdges
				, nbShelves);

		// add books
		Random randomGenerator = new Random();
		int lastColorIndex = -1;
		int indexShelf = 0;
		int indexBook = 0;
		
		for (Shape book : books) {
			int colorIndex = -1;
			
			// generate a random color for this book
			do {
				colorIndex = randomGenerator.nextInt(colors.size());
			} while (colorIndex == lastColorIndex);
			
			lastColorIndex = colorIndex;
			
			//select this color
			g.setPaint(colors.get(colorIndex));
			
			//paint the book with no rotation (TODO)
			int bookRotation = 0;
			g.rotate(Math.toRadians(bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
			g.fill(book);
			g.rotate(Math.toRadians(-bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
			
			//select the black color for the title
			g.setPaint(Color.black);
			
			//draw the title with the same rotation as the book
			g.rotate(Math.toRadians(+90+bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
			int fo = 70;
			g.setFont(new Font("TimesRoman", Font.PLAIN, fo));
			
			String bookTitle = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getTitle();
			String authorFirstName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor().getFirstName();
			String authorLastName = lib.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor().getLastName();		
			String bookString = bookTitle+" - "+authorFirstName+" "+authorLastName;
			
			// change the size of the title if it is too long
			if (g.getFontMetrics().stringWidth(bookString) > heig[indexBook]-25){
				while( g.getFontMetrics().stringWidth(bookString) > heig[indexBook]-25){
					fo = fo -3;
					g.setFont(new Font("TimesRoman", Font.PLAIN, fo));
				}
				// Y : vers la gauche
				// X : vers le bas
				g.drawString(bookString,titleXY[indexBook][0] + ((heig[indexBook]-g.getFontMetrics().stringWidth(bookString))/2) , (float) (titleXY[indexBook][1] - ((book.getBounds2D().getWidth() - g.getFontMetrics().getHeight()) / 2)));
				//g.drawString(bookString,titleXY[indexBook][0] + 15, (float) (titleXY[indexBook][1] - ((book.getBounds2D().getWidth() - g.getFontMetrics().getHeight()) / 2)));			
			}
			else g.drawString(bookString,titleXY[indexBook][0] + ((heig[indexBook]-g.getFontMetrics().stringWidth(bookString))/2) , (float) (titleXY[indexBook][1] - ((book.getBounds2D().getWidth() - g.getFontMetrics().getHeight()) / 2)));
				//g.drawString(bookString, titleXY[indexBook][0] + 15, titleXY[indexBook][1]-15);
			//System.out.println(heig[indexBook]);
			//System.out.println(g.getFontMetrics().stringWidth(bookString));
			g.rotate(Math.toRadians(-90-bookRotation), titleXY[indexBook][0], titleXY[indexBook][1]);
			
			if (indexShelf + 1 < lib.getShelves().size()){
				indexShelf++;
			}
			if (indexBook + 1 < lib.getShelves().get(indexShelf).getBooks().size()){
				indexBook++;
			}
		}

		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		try (Writer out = new OutputStreamWriter(new FileOutputStream("libraryDroiteWithTitles.svg"), "UTF-8")) {
			g.stream(out, useCSS);
		}
		System.out.println("end");
	}
	
}
