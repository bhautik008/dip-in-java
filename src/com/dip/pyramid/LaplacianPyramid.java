package com.dip.pyramid;

import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class LaplacianPyramid {

	public static MakeImage[] applyFilter(MakeImage[] gPyramid) {
		MakeImage[] lPyramid = new MakeImage[gPyramid.length];
		lPyramid[gPyramid.length-1] = gPyramid[gPyramid.length-1];
		for(int i=gPyramid.length-2;i>=0;i--) {
			MakeImage g1 = gPyramid[i];
			MakeImage g2 = expandImage(gPyramid[i+1]);
			BufferedImage newImg = new BufferedImage(g1.getWidth(), g1.getHeight(), g1.getType());
			for(int y=0;y<g1.getWidth();y++) {
				for(int x=0;x<g1.getHeight();x++) {
					int p = g1.getPixel(x, y) - g2.getPixel(x, y);
					newImg.setRGB(x, y, p);
				}
			}
			lPyramid[i] = new MakeImage(newImg);
		}
		return lPyramid;
	}
	
	private static MakeImage expandImage(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		BufferedImage newImg = new BufferedImage(width*2, height*2, mi.getType());
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				int x1 = x*2;
				int y1 = y*2;
				newImg.setRGB(x1, y1, mi.getPixel(x, y));
				newImg.setRGB(x1+1, y1+1, mi.getPixel(x, y));
			}
		}
		return new MakeImage(newImg);
	}
}
