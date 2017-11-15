package com.dip.filter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import com.dip.image.MakeImage;

public class Threshold {
	
	public static BufferedImage biLevelThreshold(MakeImage mi, int thresholdVal) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		
		BufferedImage newImg = new BufferedImage(width, height, mi.getType());
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				int[] rgb = mi.getRGB(x, y);
				int avg = (rgb[0]+rgb[1]+rgb[2])/3;
				Color c;
				if(avg >= thresholdVal) {
					c = new Color(255, 255, 255);
				} else {
					c = new Color(0, 0, 0);
				}
				newImg.setRGB(x, y, c.getRGB());
			}
		}
		
		return newImg;
	}
	
	public static BufferedImage autoThreshold(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		BufferedImage newImg = new BufferedImage(width, height, img.getType());
		int thresholdVal = getAvgThresholdVal(img);
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				Color c = new Color(img.getRGB(x, y));
				int avg = (c.getRed()+c.getGreen()+c.getBlue())/3;
				Color c1;
				if(avg >= thresholdVal) {
					c1 = new Color(255, 255, 255);
				} else {
					c1 = new Color(0, 0, 0);
				}
				newImg.setRGB(x, y, c1.getRGB());
			}
		}
		
		return newImg;
	}
	
	public static BufferedImage multiLevelThreshold(MakeImage mi, int thresholdMin, int thresholdMax) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		
		BufferedImage newImg = new BufferedImage(width, height, mi.getType());
		
		if(mi.isGrayScale()) {
			for(int y=0;y<height;y++) {
				for(int x=0;x<width;x++) {
					int[] rgb = mi.getRGB(x, y);
					int avg = (rgb[0]+rgb[1]+rgb[2])/3;
					Color c;
					if(avg <= thresholdMin) {
						c = new Color(0, 0, 0);
					} else if(avg <= thresholdMax && avg > thresholdMin){
						c = new Color(220, 220, 220);
					} else{
						c = new Color(255,255,255);
					}
					newImg.setRGB(x, y, c.getRGB());
				}
			}
		} else {
			for(int y=0;y<height;y++) {
				for(int x=0;x<width;x++) {
					int[] rgb = mi.getRGB(x, y);
					int avg = (rgb[0]+rgb[1]+rgb[2])/3;
					Color c;
					if(avg <= thresholdMin) {
						c = new Color(255, 0, 0);
					} else if(avg <= thresholdMax && avg > thresholdMin){
						c = new Color(0, 225, 0);
					} else{
						c = new Color(0,0,255);
					}
					newImg.setRGB(x, y, c.getRGB());
				}
			}
		}
		
		return newImg;
	}
	
	public static BufferedImage locallyAdaptiveThreshold(MakeImage mi, int split) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		
		BufferedImage[] splitImgs = splitImage(mi.getBufferedImage(), split);
		
		BufferedImage[] thresholdImgs = new BufferedImage[splitImgs.length];
		for(int i=0;i<splitImgs.length;i++) {
			thresholdImgs[i] = autoThreshold(splitImgs[i]);
		}
		
		BufferedImage newImg = mergeImage(thresholdImgs, width, height, mi.getType());
		
		return newImg;
	}
	
	public static BufferedImage niblackThreshold(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		double k = 0.2D, c = 0;
		BufferedImage newImg = new BufferedImage(width, height, mi.getType());
		
		NiBlackThreshold nT = new NiBlackThreshold(mi);
		MakeImage mImg = new MakeImage(nT.applyMean());
		MakeImage vImg = new MakeImage(nT.applyVariance());
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				float p = mi.getPixel(x, y);
				float mP = mImg.getPixel(x, y);
				float vP = vImg.getPixel(x, y);
				
				int g = (p > (mP + k * Math.sqrt(vP) - c)) ? 255 : 0;
				newImg.setRGB(x, y, g);
			}
		}
		
		return newImg;
	}
	
	public static int getAvgThresholdVal(MakeImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		int total = 0;
		int count = 0;
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				int[] rgb = img.getRGB(x, y);
				total += (rgb[0]+rgb[1]+rgb[2])/3;
				count++;
			}
		}
		
		int result = (int) total/count;
		
		return result;
	}
	
	public static int getAvgThresholdVal(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		int total = 0;
		int count = 0;
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				Color c = new Color(img.getRGB(x, y));
				total += (c.getRed()+c.getGreen()+c.getBlue())/3;
				count++;
			}
		}
		
		int result = (int) total/count;
		
		return result;
	}
	
	private static BufferedImage mergeImage(BufferedImage[] images, int width, int height, int type) {
		BufferedImage newImg = new BufferedImage(width, height, type);
		int wT =0 , hT = 0, col = 0;
		for(int k=0;k<images.length;k++) {
			BufferedImage temp = images[k];
			int w = temp.getWidth();
			int h = temp.getHeight();
			Graphics2D g = newImg.createGraphics();
			g.drawImage(temp, wT, hT, w+wT, h+hT, 0, 0, w, h, null);
			
			wT += w;
			col++;
			if(col == 4) {
				col = 0;
				hT += h;
				wT = 0;
			}
			g.dispose();
		}
		
		return newImg;
	}
	
	private static BufferedImage[] splitImage(BufferedImage img, int split) {
		int w = img.getWidth() / split;
		int h = img.getHeight() / split;
		
		BufferedImage[] splitImgs = new BufferedImage[split*split];
		int count = 0;
		for(int y=0;y<split;y++) {
			for(int x=0;x<split;x++) {
				splitImgs[count] = new BufferedImage(w, h, img.getType());
				Graphics2D g = splitImgs[count].createGraphics();
				g.drawImage(img, 0, 0, w, h, w*x,h*y, w*x+w, h*y+h, null);
				g.dispose();
				count++;
			}
		}
		
		return splitImgs;
	}
}
