package com.dip.filter;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class Blending {
	
	MakeImage image1,image2, result;
	double blendingRatio = 0.5;
	
	public Blending(MakeImage img1, MakeImage img2) {
		this.image1 = img1;
		this.image2 = img2;
	}
	
	public Blending(MakeImage img1, MakeImage img2, double blendingRatio) {
		this.image1 = img1;
		this.image2 = img2;
		this.blendingRatio = blendingRatio;
	}
	
	public void setBlendingRatio(double blendingRatio) {
		this.blendingRatio = blendingRatio;
	}
	
	public MakeImage getResult() {
		if(result == null) {
			applyBlending();
		}
		return result;
	}
	
	public void applyAutoBlending() {
		int w1 = image1.getWidth();
		int h1 = image1.getHeight();
		int w2 = image2.getWidth();
		int h2 = image2.getHeight();
		
		if(w1!=w2 || h1!=h2) {
			throw new IllegalArgumentException("Dimention of images doesn't match!!!");
		}
		if(image1.isGrayScale()) {
			grayBlending();
		} else {
			rgbBlending();
		}
		BufferedImage img1 = image1.getBufferedImage();
		BufferedImage img2 = image2.getBufferedImage();
		
		Graphics2D g2d = img1.createGraphics();
	    g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
	    int x = (img1.getWidth() - img2.getWidth()) / 2;
	    int y = (img1.getHeight() - img2.getHeight()) / 2;
	    g2d.drawImage(img2, x, y, null);
	    g2d.dispose();
	    
	    result = new MakeImage(img1);
	}
	
	public void applyBlending() {
		int w1 = image1.getWidth();
		int h1 = image1.getHeight();
		int w2 = image2.getWidth();
		int h2 = image2.getHeight();
		
		if(w1!=w2 || h1!=h2) {
			throw new IllegalArgumentException("Dimention of images doesn't match!!!");
		}
		if(image1.isGrayScale()) {
			grayBlending();
		} else {
			rgbBlending();
		}
	}
	
	public void applyHalfBlending() {
		int w1 = image1.getWidth();
		int h1 = image1.getHeight();
		int w2 = image2.getWidth();
		int h2 = image2.getHeight();
		
		if(w1!=w2 || h1!=h2) {
			throw new IllegalArgumentException("Dimention of images doesn't match!!!");
		}
		if(image1.isGrayScale()) {
			grayBlendingH();
		} else {
			rgbBlendingH();
		}
	}
	
	public void grayBlending() {
		BufferedImage newImg = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		int p;
		for(int y=0;y<image1.getHeight();y++) {
			for(int x=0;x<image1.getWidth();x++) {
				p = (int) (blendingRatio*image1.getGrayR(x, y) + (1 - blendingRatio)*image2.getGrayR(x, y));
				newImg.setRGB(x, y, (byte) p);
			}
		}
		result = new MakeImage(newImg);
	}
	
	public void grayBlendingH() {
		BufferedImage newImg = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		int p;
		int w = (int) Math.ceil(image1.getWidth()/2);
		int w1 = w - 10, w2 = w + 10;
		for(int y=0;y<image1.getHeight();y++) {
			for(int x=0;x<image1.getWidth();x++) {
				if(x < w) {
					if(x > w1) {
						p = (int) (blendingRatio*image1.getPixel(x, y) + (1 - blendingRatio)*image2.getPixel(x, y));
					} else {
						p = image1.getGrayR(x, y);
					}
				} else {
					if(x < w2) {
						p = (int) (blendingRatio*image1.getPixel(x, y) + (1 - blendingRatio)*image2.getPixel(x, y));
					} else {
						p = image2.getGrayR(x, y);
					}
				}
				if(p > 255) {
					p = 255;
				} else if(p < 0) {
					p = 0;
				}
				newImg.setRGB(x, y, (byte) p);
			}
		}
		result = new MakeImage(newImg);
	}
	
	public void rgbBlending() {
		BufferedImage newImg = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_RGB);
		int r,g,b,rgb;
		for(int y=0;y<image1.getHeight();y++) {
			for(int x=0;x<image1.getWidth();x++) {
				r = (int) (blendingRatio*image1.getRed(x, y) + (1 - blendingRatio)*image2.getRed(x, y));
				g = (int) (blendingRatio*image1.getGreen(x, y) + (1 - blendingRatio)*image2.getGreen(x, y));
				b = (int) (blendingRatio*image1.getBlue(x, y) + (1 - blendingRatio)*image2.getBlue(x, y));
				
				if(r > 255) r = 255;
				if(r < 0) r = 0;
				if(g > 255) g = 255;
				if(g < 0) g = 0;
				if(b > 255) b = 255;
				if(b < 0) b = 0;
				
				rgb = (r << 16) | (g << 8) | b;
				newImg.setRGB(x, y, rgb);
			}
		}
		result = new MakeImage(newImg);
	}
	
	public void rgbBlendingH() {
		BufferedImage newImg = new BufferedImage(image1.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_RGB);
		int r,g,b,rgb;
		int w = (int) Math.ceil(image1.getWidth()/2);
		int w1 = w - 3, w2 = w + 3;
		for(int y=0;y<image1.getHeight();y++) {
			for(int x=0;x<image1.getWidth();x++) {
				if(x < w) {
					if(x > w1) {
						r = (int) (blendingRatio*image1.getRed(x, y) + (1 - blendingRatio)*image2.getRed(x, y));
						g = (int) (blendingRatio*image1.getGreen(x, y) + (1 - blendingRatio)*image2.getGreen(x, y));
						b = (int) (blendingRatio*image1.getBlue(x, y) + (1 - blendingRatio)*image2.getBlue(x, y));
					} else {
						r = image1.getRed(x, y);
						g = image1.getGray(x, y);
						b = image1.getBlue(x, y);
					}
				} else {
					if(x < w2) {
						r = (int) (blendingRatio*image1.getRed(x, y) + (1 - blendingRatio)*image2.getRed(x, y));
						g = (int) (blendingRatio*image1.getGreen(x, y) + (1 - blendingRatio)*image2.getGreen(x, y));
						b = (int) (blendingRatio*image1.getBlue(x, y) + (1 - blendingRatio)*image2.getBlue(x, y));
					} else {
						r = image2.getRed(x, y);
						g = image2.getGray(x, y);
						b = image2.getBlue(x, y);
					}
				}
				
				
				
				rgb = (255 << 24) | (r << 16) | (g << 8) | b;
				newImg.setRGB(x, y, rgb);
			}
		}
		result = new MakeImage(newImg);
	}
}
