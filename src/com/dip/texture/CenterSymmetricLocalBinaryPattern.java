package com.dip.texture;

import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class CenterSymmetricLocalBinaryPattern {
	
	public static BufferedImage applyCSLBP(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		if(!mi.isGrayScale()) {
			mi.toGrayScale();
		}
		double threshold = 0.1;
		int sum = 0;
		for(int y=1;y<height-1;y++) {
			for(int x=1;x<width-1;x++) {
				//threshold = mi.getGray(x, y);
				sum = 0;
//				if (Math.abs(mi.getGray(x - 1, y - 1) - mi.getGray(x + 1, y + 1)) >= threshold)    sum += 8;
//                if (Math.abs(mi.getGray(x - 1, y) - mi.getGray(x + 1, y)) >= threshold)            sum += 4;
//                if (Math.abs(mi.getGray(x - 1, y + 1) - mi.getGray(x + 1, y - 1)) >= threshold)    sum += 2;
//                if (Math.abs(mi.getGray(x, y + 1) - mi.getGray(x, y - 1)) >= threshold)            sum += 1;
				
				if (Math.abs(mi.getGray(x + 1, y - 1) - mi.getGray(x - 1, y + 1)) >= threshold)    sum += 8;
				if (Math.abs(mi.getGray(x + 1, y) - mi.getGray(x - 1, y)) >= threshold)            sum += 4;
				if (Math.abs(mi.getGray(x + 1, y + 1) - mi.getGray(x - 1, y - 1)) >= threshold)    sum += 2;
				if (Math.abs(mi.getGray(x, y + 1) - mi.getGray(x, y - 1)) >= threshold)            sum += 1;
				
				newImg.setRGB(x, y, (byte)sum);
			}
		}
		return newImg;
	}
}
