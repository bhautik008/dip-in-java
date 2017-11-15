package com.dip.filter;

import com.dip.image.MakeImage;

public class Histogram {

	public static int[] getHistogram(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		
		int[] histogram = new int[2001];
		int[][] pixels = mi.getRasterIntMatrix();
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				histogram[pixels[y][x]]++;
			}
		}
		
		return histogram;
	}
	
}
