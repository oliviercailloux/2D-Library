package view;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

/**
 * Based on https://xmlgraphics.apache.org/batik/using/svg-generator.html (with
 * minor modifications).
 *
 */
public class SVGDrawable {

	public static void main(String[] args) throws Exception {
		SVGDrawable test = new SVGDrawable();
		test.CreateBook("Thibaud", "FirstBook1", 400, 200, 50, 1);
		test.CreateBook("Thibaud", "FirstBook2", 400, 200, 50, 2);
		test.CreateBook("Thibaud", "FirstBook3", 400, 200, 50, 3);
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
	public void CreateBook(String author, String title, int height , int width, int thickness , int position ) throws IOException, ParserConfigurationException {
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
		SVGGraphics2D g = new SVGGraphics2D(ctx, false);

		// To positionate our Title and Author
		FontMetrics metrics = g.getFontMetrics();
		author="By " + author;
		int xT1 = (int)( width - metrics.stringWidth(title)) / 2;
		int yT1 = (int)(( height - metrics.getHeight()) / 2) + metrics.getAscent();
		int xA1 = (int)( width - metrics.stringWidth(author)) / 2;
		int yA1 = (int)(( height- metrics.getHeight())/1.5) + metrics.getAscent();
		int xT2 = (int)( thickness - metrics.stringWidth(title)) / 2;
		int yT2 = (int)(( height - metrics.getHeight()) / 2) + metrics.getAscent();
		int xA2 = (int)( thickness - metrics.stringWidth(author)) / 2;
		int yA2 = (int)(( height- metrics.getHeight())/1.5) + metrics.getAscent();
		int xT3 = (int) ( height - metrics.stringWidth(title)) / 3;
		int yT3 = (int) (( thickness - metrics.getHeight()) / 2) +  metrics.getAscent();
		int xA3 = (int) ((height - metrics.stringWidth(author))/1.5) ;
		int yA3 = (int) (( thickness - metrics.getHeight())/2) + metrics.getAscent();
		
		
		//g.setPaint(Color.black);
	

		// Creation whether the position is 1 : face view
		if(position == 1){
			g.drawString(title,xT1 , yT1);
			g.drawString(author, xA1, yA1);
			g.setSVGCanvasSize(new Dimension(width, height));
		}

		// Creation whether the position is 2 : profile view  
		else if(position == 2){
			g.drawString(title,xT2 , yT2);
			g.drawString(author, xA2, yA2);
			g.setSVGCanvasSize(new Dimension(thickness, height));
		}

		// Creation whether the position is 3 : profile view lying down
		else{
			g.drawString(title,xT3 , yT3);
			g.drawString(author, xA3, yA3);
			g.setSVGCanvasSize(new Dimension(height, thickness));
		}

		// Finally, stream out SVG using UTF-8 encoding.
		boolean useCSS = true; // we want to use CSS style attributes
		try (Writer out = new OutputStreamWriter(new FileOutputStream(title + ".svg"), "UTF-8")) {
			g.stream(out, useCSS);
		}
	}
}