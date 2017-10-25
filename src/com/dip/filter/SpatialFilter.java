package com.dip.filter;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class SpatialFilter {
	
	public static BufferedImage applyFiler(BufferedImage image, int radius, int sigma) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		float[] kernelMatrix = new float[radius*radius];
		for(int i=0;i<kernelMatrix.length;i++) {
			kernelMatrix[i] = (float) 1 / sigma;
		}
		
		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		
		BufferedImageOp op = new ConvolveOp(new Kernel(radius, radius, kernelMatrix));
		newImage = op.filter(image, newImage);
		
		return newImage;
	}

	public static BufferedImage applyFiler(BufferedImage image, int radius) {
		int width = image.getWidth();
		int height = image.getHeight();
		int sigma = radius*radius;
		float[] kernelMatrix = new float[sigma];
		for(int i=0;i<kernelMatrix.length;i++) {
			kernelMatrix[i] = (float) 1 / sigma;
		}
		
		BufferedImage newImage = new BufferedImage(width, height, image.getType());
		
		BufferedImageOp op = new ConvolveOp(new Kernel(radius, radius, kernelMatrix));
		newImage = op.filter(image, newImage);
		
		return newImage;
	}
}
