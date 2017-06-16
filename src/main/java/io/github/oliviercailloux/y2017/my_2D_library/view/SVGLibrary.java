package io.github.oliviercailloux.y2017.my_2D_library.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import io.github.oliviercailloux.y2017.my_2D_library.model.Book;
import io.github.oliviercailloux.y2017.my_2D_library.model.Library;

/**
 * Based on https://xmlgraphics.apache.org/batik/using/svg-generator.html (with
 * minor modifications).
 *
 */
public class SVGLibrary {

	public static final Logger LOGGER = LoggerFactory.getLogger(SVGLibrary.class);

	private Library library;

	private SVGGraphics2D graphics;

	public SVGLibrary(Library library) throws ParserConfigurationException {
		this.library = library;
		this.graphics = generateSVG();
	}

	public Library getLibrary() {
		return library;
	}

	/***
	 * Generate the borders of the library.
	 * 
	 * @param dimCanvasX
	 * @param dimCanvasY
	 * @param thinknessEdges
	 * @return List of the borders of the library.
	 */
	private List<Shape> getEdges(int dimCanvasX, int dimCanvasY, int thinknessEdges) {
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
	 * 
	 * @param leaning
	 * @param Library
	 * @param leaning
	 */
	public void generate(boolean leaning, String bkColor, String bColor, String sColor)
			throws IOException, ParserConfigurationException {

		int dimCanvasX = (int) ((int) library.getFrameSizeW() - (0.055 * library.getFrameSizeW()));
		int dimCanvasY = 1500;
		int thiknessEdges = 20;

		graphics.setSVGCanvasSize(new Dimension(dimCanvasX, dimCanvasY));

		int nbShelves = library.getShelves().size();
		int spaceBetweenShelves = 0;
		if (nbShelves > 0) {
			spaceBetweenShelves = (dimCanvasY - thiknessEdges * (2 + nbShelves - 1)) / nbShelves;
		}

		// define the back and the outlines of the library
		drawBackOutlines(dimCanvasX, dimCanvasY, thiknessEdges, bkColor, sColor);

		// define the shelves of the library
		List<Shape> shelves = drawShelves(nbShelves, thiknessEdges, spaceBetweenShelves, dimCanvasX, sColor);

		// get the width of a shelf
		int shelfWidth = dimCanvasX - 2 * thiknessEdges;

		// get books
		drawBooksAndTitles(spaceBetweenShelves, dimCanvasX, thiknessEdges, shelfWidth, leaning, shelves, bColor);

		// TODO : LINK SVG

		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes

		try (Writer out = new OutputStreamWriter(new FileOutputStream("library.svg"), "UTF-8")) {
			graphics.stream(out, useCSS);
		}
	}

	/***
	 * Draw the shelves
	 * 
	 * @param graphics
	 * @param nbShelves
	 * @param thiknessEdges
	 * @param spaceBetweenShelves
	 * @param dimCanvasX
	 * @return
	 */
	private List<Shape> drawShelves(int nbShelves, int thiknessEdges, int spaceBetweenShelves, int dimCanvasX,
			String sColor) {
		List<Shape> shelves = new ArrayList<>();
		switch (sColor) {
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
	 * 
	 * @return the SVGGraphics2D on which we are drawing
	 * @throws ParserConfigurationException
	 */
	private SVGGraphics2D generateSVG() throws ParserConfigurationException {
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
	 * 
	 * @param dimCanvasX
	 * @param dimCanvasY
	 * @param thiknessEdges
	 * @param graphics
	 */
	private void drawBackOutlines(int dimCanvasX, int dimCanvasY, int thiknessEdges, String bkColor, String sColor) {
		Shape fond = new Rectangle(0, 0, dimCanvasX, dimCanvasY);
		List<Shape> edges = getEdges(dimCanvasX, dimCanvasY, thiknessEdges);
		switch (bkColor) {
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
		switch (sColor) {
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
	 * 
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
	 * 
	 * @param graphics
	 * @param bookRotation
	 * @param book
	 * @param library
	 * @param bookX
	 * @param bookY
	 * @param indexBook
	 * @param indexShelf
	 * @param bookHeight
	 */

	private void drawBooksAndTitles(int spaceBetweenShelves, int dimCanvasX, int thiknessEdges, int shelfWidth,
			boolean leaning, List<Shape> shelves, String bColor) {
		List<Shape> bookShapes = new ArrayList<>();
		int idealWidth = (int) (0.6 * (float) (shelfWidth / library.getShelves().get(0).getBooks().size()));
		// int spaceBtwnTopBookVsTopEdge = 30;
		// int height = spaceBetweenShelves - spaceBtwnTopBookVsTopEdge;
		int idealHeight = (int) (0.8 * spaceBetweenShelves);
		// int spaceBtwnTopBookVsTopEdge = spaceBetweenShelves - idealHeight;

		int placeLeftInShelf = shelfWidth;
		int shelfNumber = 1;
		Random randomGenerator = new Random();
		// int emptySpace = shelfWidth;
		int counterBooks = library.getShelves().get(shelfNumber - 1).getBooks().size();
		for (Book book : library.getListOfAllTheBooks()) {
			Shape bookShape = null;
			int randomWidth = idealWidth + randomGenerator.nextInt(30);
			int randomHeightGap = randomGenerator.nextInt(50);
			int heightSup = idealHeight + randomHeightGap;
			if (heightSup > spaceBetweenShelves)
				heightSup = spaceBetweenShelves;
			setSizeBook(spaceBetweenShelves, shelfWidth, book, idealWidth, idealHeight, randomWidth, heightSup);
			int width = book.getwidth();
			int height = book.getheight();

			if (placeLeftInShelf <= width) {
				// go to another shelf
				placeLeftInShelf = shelfWidth;
				shelfNumber++;
				if (shelfNumber > library.getShelves().size()) {
					// no place in the lib
					System.out.println("no place in the lib !!");
				}
			}
			int bookX = dimCanvasX - thiknessEdges - placeLeftInShelf;
			int bookY = shelfNumber * thiknessEdges + (shelfNumber - 1) * spaceBetweenShelves + spaceBetweenShelves
					- height;
			bookShape = new Rectangle(bookX, bookY, width, height);
			bookShapes.add(bookShape);
			counterBooks--;
			// if (placeLeftInCurrShelf <= width){
			if (counterBooks == 0) {
				// go to another shelf
				placeLeftInShelf = shelfWidth;
				shelfNumber++;
				if (shelfNumber < library.getShelves().size())
					counterBooks = library.getShelves().get(shelfNumber - 1).getBooks().size();
			} else {
				// stay in the current shelf
				placeLeftInShelf -= width;
			}
			// emptySpace -= book.getBounds().getWidth();
		}

		int indexShelf = 0;
		int indexBook = 0;
		int lastColorIndex = -1;
		placeLeftInShelf = shelfWidth;
		for (Shape bookShape : bookShapes) {
			double YOfTheShelf = shelves.get(indexShelf).getBounds().getY();
			boolean isLastBookOfTheShelf = (indexBook + 1)
					% library.getShelves().get(indexShelf).getBooks().size() == 0;
			int[] table = drawBook(randomGenerator, isLastBookOfTheShelf, bookShape, library.getShelves().get(indexShelf).getBooks().get(indexBook), placeLeftInShelf, YOfTheShelf,
					leaning, bColor, lastColorIndex);
			lastColorIndex = table[2];
			int bookRotation = table[0];
			double bookX = bookShape.getBounds().getX();
			double bookY;
			if (isLastBookOfTheShelf) {
				bookY = table[1];
				System.out.println("bookY : " + bookY);
			} else {
				bookY = bookShape.getBounds().getY();
			}
			double bookHeight = bookShape.getBounds().getHeight();
			double bookWidth = bookShape.getBounds().getWidth();
			String bookTitle = library.getShelves().get(indexShelf).getBooks().get(indexBook).getTitle();
			String authorFirstName = library.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor()
					.getFirstName();
			String authorLastName = library.getShelves().get(indexShelf).getBooks().get(indexBook).getAuthor()
					.getLastName();
			int bookYear = library.getShelves().get(indexShelf).getBooks().get(indexBook).getYear();
			String bookString = bookTitle + " - " + authorFirstName + " " + authorLastName + " - " + bookYear;
			drawTitle(bookRotation, bookString, bookShape, bookX, bookY, indexBook, bookHeight, bColor, bookWidth);
			if (isLastBookOfTheShelf) {
				indexShelf++;
				indexBook = 0;
				placeLeftInShelf = shelfWidth;
			} else {
				indexBook++;
				placeLeftInShelf -= bookShape.getBounds().getWidth();
			}
		}

	}

	private int[] drawBook(Random randomGenerator, boolean isLastBook, Shape bookShape, Book book, double emptySpace,
			double yOfTheShelf, boolean leaning, String bColor, int lastColorIndex) {

		List<Color> randomColors = new ArrayList<>();
		Color pink = null;
		Color purple = null;
		Color blue = null;
		Color yellow = null;
		Color orange = null;
		Color green = null;
		Color red = null;
		switch (bColor) {
		case "Light":
			pink = Color.decode("#FF66FF");
			purple = Color.decode("#CC99FF");
			blue = Color.decode("#33CCFF");
			yellow = Color.decode("#FFFF66");
			orange = Color.decode("#FFCC66");
			green = Color.decode("#98d7a7");
			red = Color.decode("#cc5151");
			break;
		case "Dark":
			pink = Color.decode("#990033");
			purple = Color.decode("#330033");
			blue = Color.decode("#000033");
			yellow = Color.decode("#CC9900");
			orange = Color.decode("#993300");
			green = Color.decode("#008000");
			red = Color.decode("#851818");
			break;
		default:
			pink = Color.pink;
			purple = Color.decode("#9933FF");
			blue = Color.BLUE;
			yellow = Color.yellow;
			orange = Color.ORANGE;
			green = Color.decode("#92c544");
			red = Color.decode("#d41c1c");
			break;
		}
		randomColors.add(pink);
		randomColors.add(purple);
		randomColors.add(blue);
		randomColors.add(yellow);
		randomColors.add(orange);
		randomColors.add(green);
		randomColors.add(red);
		int colorIndex = -1;

		// generate a random color for this book
		do {
			colorIndex = randomGenerator.nextInt(randomColors.size());
		} while (colorIndex == lastColorIndex);

		lastColorIndex = colorIndex;

		// select this color
		if (book.getColor()==null) graphics.setPaint(randomColors.get(colorIndex));
		else graphics.setPaint(book.getColor());

		// paint the book (with rotation if the last book)
		int[] result = new int[3];
		result[2] = colorIndex;
		int bookRotation = 0;
		if (isLastBook && leaning) {// on penche le dernier livre
			bookRotation = -15 - randomGenerator.nextInt(10);
			System.out.println(
					"XEED : " + emptySpace + " : " + Math.abs(Math.sin(90 - bookRotation) * bookShape.getBounds().getWidth())
							+ " : " + Math.abs(Math.sin(bookRotation) * bookShape.getBounds().getHeight()));
			if (!(emptySpace > Math.sin(90 - bookRotation) * bookShape.getBounds().getWidth()
					+ Math.sin(bookRotation) * bookShape.getBounds().getHeight()))
				System.out.println("si qlq voit cette erreur le dire a merlene");
			if (emptySpace > Math.abs(Math.sin(90 - bookRotation) * bookShape.getBounds().getWidth())
					+ Math.abs(Math.sin(bookRotation) * bookShape.getBounds().getHeight())) {// il
																						// faut
																						// qu'il
																						// reste
																						// au
																						// moins
																						// trois
																						// fois
																						// la
																						// largeur
																						// du
																						// livre
				// Height between the top left corner of the book and the shelf
				// when leaning
				double hauteurRotation = bookShape.getBounds().getHeight() * Math.cos(Math.toRadians(bookRotation));
				// The new Y coordinate of the leaning rectangle (so that it is
				// placed on the shelf)
				double newY = yOfTheShelf - hauteurRotation;
				Rectangle newRectangle = new Rectangle((int) bookShape.getBounds().getX(), (int) newY,
						(int) bookShape.getBounds().getWidth(), (int) bookShape.getBounds().getHeight());
				int newBookX = (int) newRectangle.getBounds().getX();
				int newBookY = (int) newRectangle.getBounds().getY();
				graphics.rotate(Math.toRadians(bookRotation), newBookX, newBookY);
				graphics.fill(newRectangle);
				graphics.rotate(Math.toRadians(-bookRotation), newBookX, newBookY);
				result[1] = newBookY;
			} else {// pas assez de place pour le pencher
				bookRotation = 0;
				double hauteurRotation = bookShape.getBounds().getHeight() * Math.cos(Math.toRadians(bookRotation));
				double newY = yOfTheShelf - hauteurRotation;
				Rectangle newRectangle = new Rectangle((int) bookShape.getBounds().getX(), (int) newY,
						(int) bookShape.getBounds().getWidth(), (int) bookShape.getBounds().getHeight());
				graphics.fill(newRectangle);
				result[1] = (int) newY;
			}
		} else {
			double hauteurRotation = bookShape.getBounds().getHeight() * Math.cos(Math.toRadians(bookRotation));
			double newY = yOfTheShelf - hauteurRotation;
			Rectangle newRectangle = new Rectangle((int) bookShape.getBounds().getX(), (int) newY,
					(int) bookShape.getBounds().getWidth(), (int) bookShape.getBounds().getHeight());
			graphics.fill(newRectangle);
			result[1] = (int) newY;
		}
		result[0] = bookRotation;
		return result;
	}

	private void drawTitle(int bookRotation, String bookString, Shape book, double bookX, double bookY, int indexBook,
			double bookHeight, String bColor, double bookWidth) {

		// select the black color for the title
		if (bColor.equals("Dark"))
			graphics.setPaint(Color.white);
		else
			graphics.setPaint(Color.black);

		// draw the title with the same rotation as the book

		graphics.rotate(Math.toRadians(+90 + bookRotation), bookX, bookY);
		int fontSize = 70;
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));

		// change the size of the title if it is too long
		if (graphics.getFontMetrics().stringWidth(bookString) > 6 * bookHeight / 10) {
			while (graphics.getFontMetrics().stringWidth(bookString) > 6 * bookHeight / 10) {
				fontSize = fontSize - 3;
				graphics.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
			}
			graphics.drawString(bookString,
					(float) (bookX + ((bookHeight - graphics.getFontMetrics().stringWidth(bookString)) / 2)),
					(float) (bookY - ((bookWidth) / 4)));
		} else
			graphics.drawString(bookString,
					(float) (bookX + ((bookHeight - graphics.getFontMetrics().stringWidth(bookString)) / 2)),
					(float) (bookY - ((bookWidth) / 4)));

		graphics.rotate(Math.toRadians(-90 - bookRotation), bookX, bookY);
	}

	public void setSizeBook(int ShelfHeight, int ShelfWidth, Book book, int idealWidth, int idealHeight, int widthSup,
			int heightSup) {

		if (!(book.getwidth() >= idealWidth && book.getwidth() <= widthSup)) {
			book.setwidth(widthSup);
		}
		if (!(book.getheight() >= idealHeight && book.getheight() <= heightSup)) {
			book.setheight(heightSup);
		}
		if (!(book.getwidth() >= book.getheight() / 10 && book.getwidth() <= book.getheight() / 8)) {
			book.setwidth(book.getheight() / 9);
		}
	}

	public void setLibrary(Library library2) {
		this.library = library2;
	}

	public void convert() throws Exception {
		String svg_URI_input = Paths.get("library.svg").toUri().toURL().toString();
		TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);
		OutputStream png_ostream = new FileOutputStream("library.png");
		TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);

		PNGTranscoder my_converter = new PNGTranscoder();
		my_converter.transcode(input_svg_image, output_png_image);

		png_ostream.flush();
		png_ostream.close();
	}

}