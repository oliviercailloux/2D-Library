package io.github.dauphine.lejema160.view;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class svg2jpg {
	public static void convert() throws Exception{
		 
		        String svg_URI_input = Paths.get("/users/jammfa16/git/2D_library/library.svg").toUri().toURL().toString();
		        TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);        
		      
		        OutputStream png_ostream = new FileOutputStream("/users/jammfa16/git/2D_library/library.png");
		        TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);              
		       
		        PNGTranscoder my_converter = new PNGTranscoder();        
		        my_converter.transcode(input_svg_image, output_png_image);
		      
		        png_ostream.flush();
		        png_ostream.close();        
		    }
		     
	
}


