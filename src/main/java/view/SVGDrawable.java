package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import model.Author;
import model.Book;
import model.Shelf;
import model.Library;

/**
 * Based on https://xmlgraphics.apache.org/batik/using/svg-generator.html (with
 * minor modifications).
 *
 */
public class SVGDrawable {

	public static void main(String[] args) throws Exception {
		SVGDrawable test = new SVGDrawable();
		/*Author a= new Author("pierre","dupont");
		Book b= new Book("FirstBook1",a,1990);
		//Book b2= new Book("FirstBook2",a,1990);;
		test.CreateBook(b,250,600);
		//test.CreateBook(b2, 400, 200, 50, 2);
		//test.CreateBook("FANNY", "FirstBook2", 400, 200, 50, 3);
		*/
		
		
		Author olympie = new Author("Olympie", "Suquet");
		Author merlene = new Author("Merlène", "Lejeune");
		Book book1 = new Book();
		book1.setAuthor(olympie);
		book1.setTitle("Titre popo");
		book1.setYear(2014);
		Book book2 = new Book();
		book2.setAuthor(olympie);
		book2.setTitle("tutitre pam");
		book2.setYear(202);
		Book book3 = new Book();
		book3.setAuthor(merlene);
		book3.setTitle("Tuto beauté");
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
		//Book b2= new Book("FirstBook2",a,1990);;
		test.CreateLibrary(library,250,600);
		//test.CreateBook(b2, 400, 200, 50, 2);
		//test.CreateBook("FANNY", "FirstBook2", 400, 200, 50, 3);
		
	}

	/**
	 * @param author
	 * @param title
	 * @param height
	 * @param width
	 * @param thickness
	 * @param position
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * 
	 * CreateBook generates an image of the book defined with the parameters taken in entry
	 * It displays the title and the author, respect the dimensions that are given in arguments (width, height and thickness)
	 * It can be displayed in 3 different positions :
	 * position number 1 is for the face view
	 * position number 2 is for the profile view
	 * and position 3 is for the profile view, with the book lying down.
	 */
	public void CreateLibrary(Library lib, int width, int height) throws IOException, ParserConfigurationException {
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
		SVGGraphics2D g = new SVGGraphics2D(ctx,true);
		g.setSVGCanvasSize(new Dimension(2000, 1500));
		/*
		//Modification of the color
		g.fillRect(0,0,width/3, height/3);
		g.fillRect(50,0,width/3, height/3);
	
		g.setColor(Color.cyan);
		System.out.println(g.getColor());
		*/
		
		
		//THIBAUD ET OLYMPIE
		
		int nbShelves = lib.getShelves().size();
		int spaceBetweenShelves = 0;
		if (nbShelves > 0){
			spaceBetweenShelves = 1500/nbShelves;
		}
				
		//define the back and the outlines of the library
		Shape fond = new Rectangle(0, 0, 2000, 1500);
		Shape baretteCoteGauche = new Rectangle(0, 0, 20, 1500);
		Shape baretteCoteDroit = new Rectangle(1980, 0, 20, 1500);
		Shape baretteHaut = new Rectangle(0, 0, 2000, 20);
		Shape baretteBas = new Rectangle(0, 1480, 2000, 20);
				
		g.setPaint(Color.decode("#565633"));
		g.fill(fond);
		g.setPaint(Color.decode("#FFCCEE"));
		g.fill(baretteHaut);
		g.fill(baretteBas);
		g.fill(baretteCoteDroit);
		g.fill(baretteCoteGauche);
				
		//define the shelves
		//if there is one shelf, no need to create any
		//if two, need to create one
		//if three, need to create two
		//etc...
		Shape shelf;
				
		for (int i = 1; i <= nbShelves; i++){
					
			shelf = new Rectangle(0, i*spaceBetweenShelves, 2000, 20);
			g.fill(shelf);
					
		}
				
				
				//FIN THIBAUD ET OLYMPIE
		
		
		// To positionnate our Title and Author
		FontMetrics metrics = g.getFontMetrics();
		String author="By bidule";
		int xT1 = (int)( width - metrics.stringWidth("tuto")) / 2;
		int yT1 = (int)(( height - metrics.getHeight()) / 2) + metrics.getAscent();
		int xA1 = (int)( width - metrics.stringWidth(author)) / 2;
		int yA1 = (int)(( height- metrics.getHeight())/1.5) + metrics.getAscent();
		/*int xT3 = (int) ( height - metrics.stringWidth(b.getTitle())) / 3;
		int yT3 = (int) (( thickness - metrics.getHeight()) / 2) +  metrics.getAscent();
		int xA3 = (int) ((height - metrics.stringWidth(author))/1.5) ;
		int yA3 = (int) (( thickness - metrics.getHeight())/2) + metrics.getAscent();*/

		
		
		//TEST NEW
		
		Shape book1 = new Rectangle(0,50, width, height); // position dans ton canvas general (g) esntuite nb pixel sur x et nb pixel sur ta coo y
		Shape book2 = new Rectangle(300,50, width, height);
		Shape book3 = new Rectangle(600,50, width/2, height);
		Shape book4 = new Rectangle(900,50, width, height);
		g.setPaint(Color.pink);// de la je vais prendre un pinceau rose (je ne fais rien je prend un piceau dans ma main uniquement
		g.drawString("tuto",xT1 , yT1);
		g.drawString(author, xA1, yA1);
		g.fill(book1);//avec mon pinceau rose je dessine dans mon canvas g une forme pleine (ici le rectangle book1)
		g.setPaint(Color.yellow); // je chnage de pinceau :D ... et rebelote
		g.fill(book2);
		g.setPaint(Color.blue);
		g.fill(book3);
		//g.translate(-60,500);
		g.setPaint(Color.CYAN);
		g.fill(book4);
		
		
/*
		// Creation whether the position is 1 : face view
		if(position == 1){
			g.drawString(b.getTitle(),xT1 , yT1);
			g.drawString(author, xA1, yA1);
			g.setSVGCanvasSize(new Dimension(width, height));
		}

		// Creation whether the position is 3 : profile view lying down
		else{
			g.drawString(b.getTitle(),xT3 , yT3);
			g.drawString(author, xA3, yA3);
			g.setSVGCanvasSize(new Dimension(height, thickness));
		}*/
		/*g.drawString(b.getTitle(),xT1 , yT1);
		g.drawString(author, xA1, yA1);
		g.setSVGCanvasSize(new Dimension(width, height));*/
		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		try (Writer out = new OutputStreamWriter(new FileOutputStream("tuto.svg"), "UTF-8")) {
			g.stream(out, useCSS);
		}

	}
	
	public void CreateBook(Book b, int width, int height) throws IOException, ParserConfigurationException {
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
		SVGGraphics2D g = new SVGGraphics2D(ctx,true);
		g.setSVGCanvasSize(new Dimension(2000, 1500));
		/*
		//Modification of the color
		g.fillRect(0,0,width/3, height/3);
		g.fillRect(50,0,width/3, height/3);
	
		g.setColor(Color.cyan);
		System.out.println(g.getColor());
		*/
		
		
		//THIBAUD ET OLYMPIE
		Shape fond = new Rectangle(0, 0, 2000, 1500);
		Shape baretteCoteGauche = new Rectangle(0, 0, 20, 1500);
		Shape baretteCoteDroit = new Rectangle(1980, 0, 20, 1500);
		Shape baretteHaut = new Rectangle(0, 0, 2000, 20);
		Shape baretteBas = new Rectangle(0, 1480, 2000, 20);
		
		Shape shelf1 = new Rectangle(0, 666, 2000, 20);
		Shape shelf2 = new Rectangle(1333, 0, 2000, 20);
		
		g.setPaint(Color.decode("#565633"));
		g.fill(fond);
		g.setPaint(Color.decode("#FFCCEE"));
		g.fill(baretteHaut);
		g.fill(baretteBas);
		g.fill(baretteCoteDroit);
		g.fill(baretteCoteGauche);
		g.fill(shelf1);
		g.fill(shelf2);
		
		
		// To positionnate our Title and Author
		FontMetrics metrics = g.getFontMetrics();
		String author="By " + b.getAuthor().getFirstName()+" "+ b.getAuthor().getLastName();
		int xT1 = (int)( width - metrics.stringWidth(b.getTitle())) / 2;
		int yT1 = (int)(( height - metrics.getHeight()) / 2) + metrics.getAscent();
		int xA1 = (int)( width - metrics.stringWidth(author)) / 2;
		int yA1 = (int)(( height- metrics.getHeight())/1.5) + metrics.getAscent();
		/*int xT3 = (int) ( height - metrics.stringWidth(b.getTitle())) / 3;
		int yT3 = (int) (( thickness - metrics.getHeight()) / 2) +  metrics.getAscent();
		int xA3 = (int) ((height - metrics.stringWidth(author))/1.5) ;
		int yA3 = (int) (( thickness - metrics.getHeight())/2) + metrics.getAscent();*/

		
		
		//TEST NEW
		
		Shape book1 = new Rectangle(0,50, width, height); // position dans ton canvas general (g) esntuite nb pixel sur x et nb pixel sur ta coo y
		Shape book2 = new Rectangle(300,50, width, height);
		Shape book3 = new Rectangle(600,50, width/2, height);
		Shape book4 = new Rectangle(900,50, width, height);
		g.setPaint(Color.pink);// de la je vais prendre un pinceau rose (je ne fais rien je prend un piceau dans ma main uniquement
		g.drawString(b.getTitle(),xT1 , yT1);
		g.drawString(author, xA1, yA1);
		g.fill(book1);//avec mon pinceau rose je dessine dans mon canvas g une forme pleine (ici le rectangle book1)
		g.setPaint(Color.yellow); // je chnage de pinceau :D ... et rebelote
		g.fill(book2);
		g.setPaint(Color.blue);
		g.fill(book3);
		//g.translate(-60,500);
		g.setPaint(Color.CYAN);
		g.fill(book4);
		
		
/*
		// Creation whether the position is 1 : face view
		if(position == 1){
			g.drawString(b.getTitle(),xT1 , yT1);
			g.drawString(author, xA1, yA1);
			g.setSVGCanvasSize(new Dimension(width, height));
		}

		// Creation whether the position is 3 : profile view lying down
		else{
			g.drawString(b.getTitle(),xT3 , yT3);
			g.drawString(author, xA3, yA3);
			g.setSVGCanvasSize(new Dimension(height, thickness));
		}*/
		/*g.drawString(b.getTitle(),xT1 , yT1);
		g.drawString(author, xA1, yA1);
		g.setSVGCanvasSize(new Dimension(width, height));*/
		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		try (Writer out = new OutputStreamWriter(new FileOutputStream(b.getTitle() + ".svg"), "UTF-8")) {
			g.stream(out, useCSS);
		}

	}
}
