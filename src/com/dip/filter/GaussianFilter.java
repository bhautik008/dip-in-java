package com.dip.filter;

import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class GaussianFilter {
	
	private static final double PI = Math.PI;

	public static BufferedImage applyFilter(MakeImage mi, double sigma) {
		int width =  mi.getWidth();
		int height = mi.getHeight();
		
		BufferedImage newImage = new BufferedImage(width, height, mi.getType());
		
		int lines = (int) Math.ceil(5*sigma);
		int[][] kernel = new int[lines][lines];
		
		for(int i=0;i<lines;i++) {
			for(int j=0;j<lines;j++) {
				double result = Math.exp(-1*(i*i+j*j)/(2*sigma*sigma)) / (2*PI*sigma*sigma);
				kernel[i][j] = (int) result;
			}
		}
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				
			}
		}
		
		return newImage;
	}
	
}
