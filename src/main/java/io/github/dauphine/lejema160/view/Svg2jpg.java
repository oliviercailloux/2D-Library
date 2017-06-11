package io.github.dauphine.lejema160.view;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Paths;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Svg2jpg {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Svg2jpg.class);
	
	public static void convert() throws Exception{
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

