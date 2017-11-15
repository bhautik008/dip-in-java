package com.dip.filter;

import java.awt.image.BufferedImage;

import com.dip.filter.integer.IntegralImage;
import com.dip.image.MakeImage;

public class NiBlackThreshold {
	
	MakeImage mi;
	int radius = 15, width, height;
	
	public NiBlackThreshold(MakeImage mi) {
		this.mi = mi;
		this.width = mi.getWidth();
		this.height = mi.getHeight();
	}
	
	public NiBlackThreshold(MakeImage mi, int radius) {
		this.mi = mi;
		this.radius = radius;
		this.width = mi.getWidth();
		this.height = mi.getHeight();
	}
	
	public BufferedImage applyMean() {
		BufferedImage newImg = new BufferedImage(width, height, mi.getType());
		IntegralImage ii = new IntegralImage(mi);
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				int v = (int) ii.getRectangleMean(x, y, radius);
				newImg.setRGB(x, y, clampValues(v, 0, 255));
			}
		}
		return newImg;
	}
	
	public BufferedImage applyVariance() {
		BufferedImage newImg = new BufferedImage(width, height, mi.getType());
		
		IntegralImage intImage = new IntegralImage(mi);
        IntegralImage intImage2 = new IntegralImage(mi, 2);
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				float m1 = intImage.getRectangleMean(x, y, radius);
                float m2 = intImage2.getRectangleMean(x, y, radius);
                float val = m2 - (m1*m1);
				newImg.setRGB(x, y, clampValues((int) val, 0, 255));
			}
		}
		return newImg;
	}
	
	public int clampValues(int value, int min, int max){
        if(value < min)
            return min;
        else if(value > max)
            return max;
        return value;
    }
}