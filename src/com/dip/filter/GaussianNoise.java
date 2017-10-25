package com.dip.filter;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class GaussianNoise {
	
	private static double defaultAmount = 20.0;

	public static BufferedImage applyNoise(BufferedImage image){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		output.setData(image.getData());

		Raster source = image.getRaster();
		WritableRaster out = output.getRaster();
		
		int currentVal;
		double newVal, gaussian;
		int bands = out.getNumBands();
		int width = image.getWidth();
		int height = image.getHeight();
		java.util.Random randGen = new java.util.Random();
		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				gaussian = randGen.nextGaussian();
				
				for(int k=0;k<bands;k++) {
					newVal = defaultAmount * gaussian;
					currentVal = source.getSample(j, i, k);
					newVal = currentVal + newVal;
					if(newVal < 0)
						newVal = 0;
					if(newVal > 255)
						newVal = 255;
					out.setSample(j, i, k, newVal);	
				}
			}
		}
		
		return output;
	}
}
