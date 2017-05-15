package io.github.dauphine.lejema160.view;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;

import com.google.common.base.Throwables;

public class BufferedImageTranscoder extends ImageTranscoder {

	private BufferedImage img = null;
	private final int type;

	public BufferedImageTranscoder(int type) {
		this.type = type;
	}

	@Override
	protected void setImageSize(float width, float height) {
		if (width > 0 && height > 0) {
			super.setImageSize(width, height);
		}
	}

	@Override
	public BufferedImage createImage(int width, int height) {
		BufferedImage bi = new BufferedImage(width, height, type);
		return bi;
	}

	@Override
	public void writeImage(BufferedImage img, TranscoderOutput to)
			throws TranscoderException {
		this.img = img;
	}

	public BufferedImage getBufferedImage() {
		return img;
	}
	
	public BufferedImage createImageFromSVG(String svg) {
		Reader reader = new BufferedReader(new StringReader(svg));
		TranscoderInput svgImage = new TranscoderInput(reader);

		BufferedImageTranscoder transcoder = new BufferedImageTranscoder(1);
		transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) 200);
		transcoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, (float) 200);
		try {
			transcoder.transcode(svgImage, null);
		} catch (TranscoderException e) {
			throw Throwables.propagate(e);
		}

		return transcoder.getBufferedImage();
	}
}

