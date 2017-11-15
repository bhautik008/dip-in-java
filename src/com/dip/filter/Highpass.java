package com.dip.filter;

import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class Highpass {
	
	public static enum HighPassMode{
		Negative,
		Positive
	}
	
	public static BufferedImage applyFilter(MakeImage mi, HighPassMode mode) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		double pVal;
		int[][] positiveKernel = {
							{0,-1,0},
							{-1,8,-1},
							{0,-1,0}
						};
		
		int[][] negativeKernel = {
							{-1,-1,-1},
							{-1,8,-1},
							{-1,-1,-1}
						};
		
		BufferedImage newImage = new BufferedImage(width,height,mi.getType());
		
		for(int y=1;y<height-1;y++) {
			for(int x=1;x<width-1;x++) {
				if(mode == HighPassMode.Positive) {
					pVal = ((positiveKernel[0][0]*mi.getPixel(x - 1, y - 1)) + (positiveKernel[0][1]*mi.getPixel(x, y - 1)) + (positiveKernel[0][2]*mi.getPixel(x + 1, y - 1))
					 		+ (positiveKernel[1][0]*mi.getPixel(x - 1, y)) 	+ (positiveKernel[1][1]*mi.getPixel(x, y))	   + (positiveKernel[1][2]*mi.getPixel(x + 1, y))
					 		+ (positiveKernel[2][0]*mi.getPixel(x - 1, y + 1)) + (positiveKernel[2][1]*mi.getPixel(x, y + 1)) + (positiveKernel[2][2]*mi.getPixel(x + 1, y + 1))) / 9;
				} else {
					pVal = ((negativeKernel[0][0]*mi.getPixel(x - 1, y - 1)) + (negativeKernel[0][1]*mi.getPixel(x, y - 1)) + (negativeKernel[0][2]*mi.getPixel(x + 1, y - 1))
					 		+ (negativeKernel[1][0]*mi.getPixel(x - 1, y)) 	+ (negativeKernel[1][1]*mi.getPixel(x, y))	   + (negativeKernel[1][2]*mi.getPixel(x + 1, y))
					 		+ (negativeKernel[2][0]*mi.getPixel(x - 1, y + 1)) + (negativeKernel[2][1]*mi.getPixel(x, y + 1)) + (negativeKernel[2][2]*mi.getPixel(x + 1, y + 1))) / 9;
				}
				newImage.setRGB(x, y, (int) Math.abs(pVal));
			}
		}
		
		return newImage;
	}
	
}
