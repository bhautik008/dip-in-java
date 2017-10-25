package trash;

// from com.dip.library;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;

import javax.imageio.ImageIO;

public class ImageToPixel {
	
	private String img_path;
	
	BufferedImage image;
	int width;
	int height;

	String path = null;
	int[][] pixelArray;
	int[] dimensions;
	
	public ImageToPixel(String path) {
		this.img_path = path;
	}
	
	public int[][] convertImage() {
		try {
			File input = new File(img_path);
	        image = ImageIO.read(input);
	        width = image.getWidth();
	        height = image.getHeight();
	        
	        Raster ras = image.getRaster();
	        
	        int elem = ras.getNumDataElements();
	        
	        pixelArray = new int[height][width];
	        
	        if(elem == 1) {
		        for(int i=0; i<height; i++) {
		        	for(int j=0; j<width; j++) {
		        		pixelArray[i][j] = image.getRGB(i, j);
		        	}
		        }
	        } else {
	        	for(int i=0; i<height; i++) {
		        	for(int j=0; j<width; j++) {
		        		pixelArray[i][j] = convertToGray(image.getRGB(i, j));
		        	}
		        }
	        }
	         
	    } catch (Exception e) {
	    	  e.printStackTrace();
	    }
		return pixelArray;
	}
	
	private int convertToGray(int p) {
		int a = (p>>24) & 0xff;
		int r = (p>>16) & 0xff;
		int g = (p>>8) & 0xff;
		int b = p & 0xff;
		
		int avg = (r + g + b) / 3;
		
		p = (a<<24) | (avg<<16) | (avg<<8) | avg;
		
		return p;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void createImage(BufferedImage image, String path) {
		File outputFile = new File(path);
    	try {
			ImageIO.write(image, "jpg", outputFile);
			System.out.println("Image successfully created!!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
