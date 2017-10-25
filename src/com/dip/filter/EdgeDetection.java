package com.dip.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class EdgeDetection {
	
	public static enum LaplacianMode{
		Negative,
		Positive
	};
	
	public static BufferedImage applyRoberts(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		double xVal,yVal,gVal;
		
		int[][] robertX = {{1,0},
							{0,-1}};

		int[][] robertY = {{0,-1},
							{-1,0}};
		
		BufferedImage newImage  = new BufferedImage(width, height, mi.getType());
		
		for(int x=1;x<width-1;x++) {
			for(int y=1;y<height-1;y++) {
				xVal = (robertX[0][0]*mi.getPixel(x - 1, y - 1))+(robertX[0][1]*mi.getPixel(x - 1, y))
							+(robertX[1][0]*mi.getPixel(x, y - 1))+(robertX[1][1]*mi.getPixel(x, y));
					
				yVal = (robertY[0][0]*mi.getPixel(x - 1, y - 1))+(robertY[0][1]*mi.getPixel(x - 1, y))
							+(robertY[1][0]*mi.getPixel(x, y - 1))+(robertY[1][1]*mi.getPixel(x, y));
					
				gVal = Math.abs(xVal) + Math.abs(yVal);
				newImage.setRGB(x, y, (int) gVal);
				
			}
		}
		
		return newImage;
	}
	
	public static BufferedImage applySobel(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		double xVal, yVal, gVal;
		
		int[][] sobelX = {{-1,0,1},
							{-2,0,2},
							{-1,0,1}};
		
		int[][] sobelY = {{1,2,1},
							{0,0,0},
							{-1,-2,-1}};
		
		BufferedImage newImage  = new BufferedImage(width, height, mi.getType());
		
		for(int x=1;x<width-1;x++) {
			for(int y=1;y<height-1;y++) {
				xVal = (sobelX[0][0]*mi.getPixel(x - 1, y - 1)) + (sobelX[0][1]*mi.getPixel(x, y - 1)) + (sobelX[0][2]*mi.getPixel(x + 1, y - 1))
						 + (sobelX[1][0]*mi.getPixel(x - 1, y)) 	+ (sobelX[1][1]*mi.getPixel(x, y))	   + (sobelX[1][2]*mi.getPixel(x + 1, y))
						 + (sobelX[2][0]*mi.getPixel(x - 1, y + 1)) + (sobelX[2][1]*mi.getPixel(x, y + 1)) + (sobelX[2][2]*mi.getPixel(x + 1, y + 1));
						
				yVal = (sobelY[0][0]*mi.getPixel(x - 1, y - 1)) + (sobelY[0][1]*mi.getPixel(x, y - 1)) + (sobelY[0][2]*mi.getPixel(x + 1, y - 1))
						 + (sobelY[1][0]*mi.getPixel(x - 1, y)) 	+ (sobelY[1][1]*mi.getPixel(x, y))	   + (sobelY[1][2]*mi.getPixel(x + 1, y))
						 + (sobelY[2][0]*mi.getPixel(x - 1, y + 1)) + (sobelY[2][1]*mi.getPixel(x, y + 1)) + (sobelY[2][2]*mi.getPixel(x + 1, y + 1));
					
				gVal = Math.abs(xVal) + Math.abs(yVal);
				newImage.setRGB(x, y, (int) gVal);
			}
		}
		
		return newImage;
	}
	
	public static BufferedImage applyPrewitt(MakeImage mi) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		double xVal, yVal, gVal;
		
		int[][] prewittX = {{-1,0,1},
							{-1,0,1},
							{-1,0,1}};
		
		int[][] prewittY = {{1,1,1},
							{0,0,0},
							{-1,-1,-1}};
		
		BufferedImage newImage  = new BufferedImage(width, height, mi.getType());
		
		for(int x=1;x<width-1;x++) {
			for(int y=1;y<height-1;y++) {
				xVal = (prewittX[0][0]*mi.getPixel(x - 1, y - 1)) + (prewittX[0][1]*mi.getPixel(x, y - 1)) + (prewittX[0][2]*mi.getPixel(x + 1, y - 1))
						 + (prewittX[1][0]*mi.getPixel(x - 1, y)) 	+ (prewittX[1][1]*mi.getPixel(x, y))	   + (prewittX[1][2]*mi.getPixel(x + 1, y))
						 + (prewittX[2][0]*mi.getPixel(x - 1, y + 1)) + (prewittX[2][1]*mi.getPixel(x, y + 1)) + (prewittX[2][2]*mi.getPixel(x + 1, y + 1));
						
				yVal = (prewittY[0][0]*mi.getPixel(x - 1, y - 1)) + (prewittY[0][1]*mi.getPixel(x, y - 1)) + (prewittY[0][2]*mi.getPixel(x + 1, y - 1))
						 + (prewittY[1][0]*mi.getPixel(x - 1, y)) 	+ (prewittY[1][1]*mi.getPixel(x, y))	   + (prewittY[1][2]*mi.getPixel(x + 1, y))
						 + (prewittY[2][0]*mi.getPixel(x - 1, y + 1)) + (prewittY[2][1]*mi.getPixel(x, y + 1)) + (prewittY[2][2]*mi.getPixel(x + 1, y + 1));
					
				gVal = Math.abs(xVal) + Math.abs(yVal);
				newImage.setRGB(x, y, (int) gVal);
			}
		}
		
		return newImage;
	}
	
	public static BufferedImage applyLaplacian(MakeImage mi, LaplacianMode mode) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		double xVal, yVal, gVal;
		
		int[][] laplacianN = {{0,-1,0},
								{-1,4,-1},
								{0,-1,0}};
		
		int[][] laplacianP = {{0,1,0},
								{1,-4,1},
								{0,1,0}};
		
		BufferedImage newImage  = new BufferedImage(width, height, mi.getType());
		
		for(int x=1;x<width-1;x++) {
			for(int y=1;y<height-1;y++) {
				if(mode == LaplacianMode.Negative) {
					xVal = (laplacianN[0][0]*mi.getPixel(x - 1, y - 1)) + (laplacianN[0][1]*mi.getPixel(x, y - 1)) + (laplacianN[0][2]*mi.getPixel(x + 1, y - 1))
					 		+ (laplacianN[1][0]*mi.getPixel(x - 1, y)) 	+ (laplacianN[1][1]*mi.getPixel(x, y))	   + (laplacianN[1][2]*mi.getPixel(x + 1, y))
					 		+ (laplacianN[2][0]*mi.getPixel(x - 1, y + 1)) + (laplacianN[2][1]*mi.getPixel(x, y + 1)) + (laplacianN[2][2]*mi.getPixel(x + 1, y + 1));
					
					yVal = (laplacianN[0][0]*mi.getPixel(x - 1, y - 1)) + (laplacianN[0][1]*mi.getPixel(x, y - 1)) + (laplacianN[0][2]*mi.getPixel(x + 1, y - 1))
					 		+ (laplacianN[1][0]*mi.getPixel(x - 1, y)) 	+ (laplacianN[1][1]*mi.getPixel(x, y))	   + (laplacianN[1][2]*mi.getPixel(x + 1, y))
					 		+ (laplacianN[2][0]*mi.getPixel(x - 1, y + 1)) + (laplacianN[2][1]*mi.getPixel(x, y + 1)) + (laplacianN[2][2]*mi.getPixel(x + 1, y + 1));
				} else {
					xVal = (laplacianP[0][0]*mi.getPixel(x - 1, y - 1)) + (laplacianP[0][1]*mi.getPixel(x, y - 1)) + (laplacianP[0][2]*mi.getPixel(x + 1, y - 1))
					 		+ (laplacianP[1][0]*mi.getPixel(x - 1, y)) 	+ (laplacianP[1][1]*mi.getPixel(x, y))	   + (laplacianP[1][2]*mi.getPixel(x + 1, y))
					 		+ (laplacianP[2][0]*mi.getPixel(x - 1, y + 1)) + (laplacianP[2][1]*mi.getPixel(x, y + 1)) + (laplacianP[2][2]*mi.getPixel(x + 1, y + 1));
					
					yVal = (laplacianP[0][0]*mi.getPixel(x - 1, y - 1)) + (laplacianP[0][1]*mi.getPixel(x, y - 1)) + (laplacianP[0][2]*mi.getPixel(x + 1, y - 1))
					 		+ (laplacianP[1][0]*mi.getPixel(x - 1, y)) 	+ (laplacianP[1][1]*mi.getPixel(x, y))	   + (laplacianP[1][2]*mi.getPixel(x + 1, y))
					 		+ (laplacianP[2][0]*mi.getPixel(x - 1, y + 1)) + (laplacianP[2][1]*mi.getPixel(x, y + 1)) + (laplacianP[2][2]*mi.getPixel(x + 1, y + 1));
				}
				gVal = Math.abs(xVal) + Math.abs(yVal);
				newImage.setRGB(x, y, (int) gVal);
			}
		}
		
		return newImage;
	}
	
	public static BufferedImage applyHighBoost(MakeImage mi, MakeImage blurImg, double sigma) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		
//		double[][] kernel = {
//						{-1,-1,-1},
//						{-1,8,-1},
//						{-1,-1,-1},
//						};
//		kernel[1][1] += sigma; 
		
		BufferedImage newImage  = new BufferedImage(width, height, mi.getType());
		for(int x=0;x<width;x++) {
			for(int y=0;y<height;y++) {
				int originalPixel = mi.getPixel(x, y);
				int blurPixel = blurImg.getPixel(x, y);
				
				int result = (int) (sigma*originalPixel - blurPixel);
				newImage.setRGB(x, y, result);
			}
		}
		
		return newImage;
	}
}
