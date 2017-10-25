package com.dip.filter;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class SalfAndPepper {
	
	public static BufferedImage applyNoise(BufferedImage image, double noiseAmount){
		BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		output.setData(image.getData());

		WritableRaster out = output.getRaster();
		
		double rand;
        double halfImpulseRatio = noiseAmount / 2.0;
        noiseAmount = 1 - noiseAmount;
        
        int bands  = out.getNumBands();
        int width  = image.getWidth();  // width of the image
        int height = image.getHeight(); // height of the image
        java.util.Random randGen = new java.util.Random();
          
        for (int j=0; j<height; j++) {
            for (int i=0; i<width; i++) {
                rand = randGen.nextDouble();
                if (rand < halfImpulseRatio) {
                    for (int b=0; b<bands; b++) out.setSample(i, j, b, 0);
                } else if (rand > noiseAmount) {
                    for (int b=0; b<bands; b++) out.setSample(i, j, b, 255);
                }
            }
        }
		
		return output;
	}
}