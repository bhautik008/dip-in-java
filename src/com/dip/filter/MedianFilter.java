package com.dip.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import com.dip.image.MakeImage;

public class MedianFilter {
	
	public static BufferedImage applyFilter(MakeImage image, int maskSize) {
		int width = image.getWidth();
		int height = image.getHeight();
		int XLine, YLine;
		//int lines = (maskSize * 2 + 1);
		int lines = maskSize;
		int maxAry = lines*lines;
		
		BufferedImage newImage = new BufferedImage(width, height, image.getType());
			
		if(image.isGrayScale()) {
			int[] avgL = new int[maxAry];
			int median;
			for(int y=0;y<height;y++) {
				for(int x=0;x<width;x++) {
					int count = 0;
					for(int i=0;i<lines;i++) {
						XLine = x + (i - maskSize);
						for(int j=0;j<lines;j++) {
							YLine = y + (j - maskSize);
							if ((XLine >= 0) && (XLine < height) && (YLine >=0) && (YLine < width)) {
								avgL[count] = image.getPixel(XLine, YLine);
								count++;
							}
						}
					}
					java.util.Arrays.sort(avgL,0,count);
					median = count / 2;
					newImage.setRGB(x, y, avgL[median]);
				}
			}
		} else {
			int[] avgR = new int[maxAry];
			int[] avgG = new int[maxAry];
			int[] avgB = new int[maxAry];
			int median;
			for(int y=0;y<height;y++) {
				for(int x=0;x<width;x++) {
					int count = 0;
					for(int i=0;i<lines;i++) {
						XLine = x + (i - maskSize);
						for(int j=0;j<lines;j++) {
							YLine = y + (j - maskSize);
							if ((XLine >= 0) && (XLine < height) && (YLine >=0) && (YLine < width)) {
								avgR[count] = image.getRed(XLine, YLine);
								avgG[count] = image.getGreen(XLine, YLine);
								avgB[count] = image.getBlue(XLine, YLine);
								count++;
							}
						}
					}
					java.util.Arrays.sort(avgR,0,count);
					java.util.Arrays.sort(avgG,0,count);
					java.util.Arrays.sort(avgB,0,count);
					median = count / 2;
					newImage.setRGB(x, y, (new Color(avgR[median], avgG[median], avgB[median])).getRed());
				}
			}
		}
		
		return newImage;
	}
}
