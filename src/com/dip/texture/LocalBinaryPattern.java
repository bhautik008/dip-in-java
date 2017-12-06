package com.dip.texture;

import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class LocalBinaryPattern {
	
	public static BufferedImage applyLBP(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		if(!mi.isGrayScale()) {
			mi.toGrayScale();
		}
		int gray, sum = 0;
		for(int y=1;y<height-1;y++) {
			for(int x=1;x<width-1;x++) {
				gray = mi.getGray(x, y);
				sum = 0;
				if((mi.getGray(x - 1, y - 1) - gray) >= 0) sum += 128;
				if((mi.getGray(x - 1, y) - gray) >= 0) sum += 64;
				if((mi.getGray(x - 1, y + 1) - gray) >= 0) sum += 32;
				if((mi.getGray(x, y + 1) - gray) >= 0) sum += 16;
				if((mi.getGray(x + 1, y + 1) - gray) >= 0) sum += 8;
				if((mi.getGray(x + 1, y) - gray) >= 0) sum += 4;
				if((mi.getGray(x + 1, y - 1) - gray) >= 0) sum += 2;
				if((mi.getGray(x, y - 1) - gray) >= 0) sum += 1;
				newImg.setRGB(x, y, (byte)sum);
			}
		}
		return newImg;
	}
}
