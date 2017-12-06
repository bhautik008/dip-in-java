package com.dip.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class MakeImage {

	private BufferedImage bufferedImage;
    private int[][] pixels;
    private boolean isImport = false;
    private int strideX, strideY;
    private int[] pixelRasterInt;
    private byte[] pixelRasterByte;
    
	public enum ColorScale{
		Grayscale,
		RGB,
		ARGB
	}
	
	public MakeImage(MakeImage mi) {
		this.bufferedImage = mi.getBufferedImage();
		this.isImport = true;
		prepare();
	}
	
	public MakeImage(BufferedImage img) {
		this.bufferedImage = img;
		this.isImport = true;
		prepare();
	}
	
	public MakeImage(Image img) {
		this.bufferedImage = (BufferedImage) img;
		this.isImport = true;
		prepare();
	}
	
	public MakeImage(ImageIcon img) {
		this.bufferedImage = (BufferedImage) img.getImage();
		this.isImport = true;
		prepare();
	}
	
	public MakeImage(String path) {
		try {
			this.bufferedImage = ImageIO.read(new File(path));
			this.isImport = true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		prepare();
	}
	
	public MakeImage(int width, int height) {
		this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		refresh();
	}
	
	public MakeImage(int width, int height, ColorScale colorScale) {
		if(colorScale == ColorScale.Grayscale) {
			this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		} else if(colorScale == ColorScale.ARGB) {
			this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
			this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		refresh();
	}
	
	public MakeImage(int[][] image) {
		this.bufferedImage = new BufferedImage(image[0].length, image.length, BufferedImage.TYPE_BYTE_GRAY);
		refresh();
		matrixToImage(image);
	}
	
	public void prepare() {
		if(getType() == BufferedImage.TYPE_BYTE_GRAY) {
			refresh();
		} else if(getType() == BufferedImage.TYPE_INT_ARGB || getType() == BufferedImage.TYPE_4BYTE_ABGR){
			makeARGB();
		} else {
			makeRGB();
		}
	}
	
	public void refresh() {
		int width = getWidth();
		int height = getHeight();
		this.strideX = 1;
		this.strideY =  getWidth();
		this.pixels = new int[height][width];
		if(isGrayScale()) {
			this.pixelRasterByte = getRasterByteData();
		} else {
			this.pixelRasterInt = getRasterIntData();
		}
		if(isImport) {
			for(int i=0;i<height;i++) {
				for(int j=0;j<width;j++) {
					pixels[i][j] = this.bufferedImage.getRGB(j, i);
				}
			}
		}
	}
	
	public void clear() {
		int width = getWidth();
		int height = getHeight();
		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				pixels[i][j] = 0;
			}
		}
	}
	
	public void makeARGB() {
		BufferedImage b = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = b.getGraphics();
        g.drawImage(this.bufferedImage, 0, 0, null);
        this.bufferedImage = b;
        refresh();
        g.dispose();
	}
	
	public void makeRGB() {
		BufferedImage b = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = b.getGraphics();
        g.drawImage(this.bufferedImage, 0, 0, null);
        this.bufferedImage = b;
        refresh();
        g.dispose();
	}
	
	/*
	 * IMAGE INFO methods
	 */
	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}
	
	public WritableRaster getRaster() {
		return this.bufferedImage.getRaster();
	}
	
	public int getWidth() {
		return this.bufferedImage.getWidth();
	}
	
	public int getHeight() {
		return this.bufferedImage.getHeight();
	}
	
	public int getType() {
		return this.bufferedImage.getType();
	}
	
	public boolean isGrayScale() {
		if(getType() == BufferedImage.TYPE_BYTE_GRAY) {
			return true;
		}
//		Raster ras = getRaster();
//		int elem = ras.getNumDataElements();
//		if(elem == 1) {
//			return true;
//		}
		return false;
	}
	
	public ColorScale getColorScale() {
		if(getType() == BufferedImage.TYPE_BYTE_GRAY) {
			return ColorScale.Grayscale;
		} else if(getType() == BufferedImage.TYPE_INT_ARGB) {
			return ColorScale.ARGB;
		}
		return ColorScale.RGB;
	}
	
	public int[][] getImageData(){
		return this.pixels;
	}
	
	public byte[] getRasterByteData() {
		byte[] pixels = ((DataBufferByte)getRaster().getDataBuffer()).getData();
		return pixels;
	}
	
	public int[] getRasterIntData() {
		int[] pixels = ((DataBufferInt)getRaster().getDataBuffer()).getData();
		return pixels;
	}
	
	public int[][] getRasterByteMatrix(){
		int w = getWidth();
		int h = getHeight();
		int[][] pixels = new int[h][w];
		
		byte[] pixelByte = ((DataBufferByte)this.bufferedImage.getRaster().getDataBuffer()).getData();
		
		for(int y=0;y<h;y++) {
			for(int x=0;x<w;x++) {
				pixels[y][x] = pixelByte[x+y*w] & 0xFF;
			}
		}
		
		return pixels;
	}
	
	public int[][] getRasterIntMatrix(){
		int w = getWidth();
		int h = getHeight();
		int[][] pixels = new int[h][w];
		
		int[] pixelByte = ((DataBufferInt)this.bufferedImage.getRaster().getDataBuffer()).getData();
		
		for(int y=0;y<h;y++) {
			for(int x=0;x<w;x++) {
				pixels[y][x] = pixelByte[x*w+y];
			}
		}
		
		return pixels;
	}
	
	/*
	 * set methods
	 */
	public void setImage(BufferedImage image) {
		this.bufferedImage = image;
		isImport = true;
		refresh();
	}
	
	public void setImage(MakeImage mi) {
		this.bufferedImage = mi.getBufferedImage();
		isImport = true;
		refresh();
	}
	
	public void setImageData(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage bg = new BufferedImage(width, height, image.getType());
		
		Graphics g = bg.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		this.bufferedImage = bg;
		refresh();
	}
	
	public void setImageData(MakeImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		BufferedImage bg = new BufferedImage(width, height, image.getType());
		
		Graphics g = bg.getGraphics();
		g.drawImage(image.getBufferedImage(), 0, 0, null);
		g.dispose();
		
		this.bufferedImage = bg;
		refresh();
	}
	
	/*
	 * conversion methods
	 */
	public void toGrayScale() {
		if(!isGrayScale()) {
			int width = getWidth();
			int height = getHeight();
			
			BufferedImage bg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
			
			Graphics g = bg.getGraphics();
			g.drawImage(this.bufferedImage, 0, 0, null);
			g.dispose();
			
			this.bufferedImage = bg;
			refresh();
		}
	}
	
	public int convertPixelToGray(int p) {
		int a = (p>>24) & 0xff;
		int r = (p>>16) & 0xff;
		int g = (p>>8) & 0xff;
		int b = p & 0xff;
		
		p = (a<<24) | (r<<16) | (g<<8) | b;
		return p;
	}
	
	public void matrixToImage(int[][] img) {
		int width = img[0].length;
		int height = img.length;
		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				pixels[i][j] = img[i][j];
			}
		}
	}
	
	public int clampValues(int value, int min, int max){
        if(value < min)
            return min;
        else if(value > max)
            return max;
        return value;
    }
	
	/*
	 * export to image
	 */
	public Image toImage() {
		return Toolkit.getDefaultToolkit().createImage(this.bufferedImage.getSource());
	}
	
	public ImageIcon toIcon() {
		ImageIcon img = new ImageIcon(this.bufferedImage);
		return img;
	}
	
	public void saveAsBMP(String pathName) {
		try {
			ImageIO.write(bufferedImage, "bmp", new File(pathName));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAsPNG(String pathName) {
		try {
			ImageIO.write(bufferedImage, "png", new File(pathName));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAsJPG(String pathName) {
		try {
			ImageIO.write(bufferedImage, "jpg", new File(pathName));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showIcon() {
		JOptionPane.showMessageDialog(null, toIcon(), "", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void showIcon(String message) {
		JOptionPane.showMessageDialog(null, toIcon(), message, JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * get pixels and color value
	 */
	public int getPixel(int x, int y) {
		return this.pixels[y][x];
	}
	
	public int getPixelR(int x, int y) {
		return this.pixelRasterInt[x*strideX+y*strideY];
	}
	
	public int[] getRGB(int x, int y) {
		int p = this.pixels[y][x];
		int[] rgb = new int[3];
		rgb[0] = p >> 16 & 0xff;
		rgb[1] = p >> 8 & 0xff;
		rgb[2] = p & 0xff;
		return rgb;
	}
	
	public int[] getARGB(int x, int y) {
		int p = this.pixels[y][x];
		int[] argb = new int[4];
		argb[0] = p >> 24 & 0xff;
		argb[1] = p >> 16 & 0xff;
		argb[2] = p >> 8 & 0xff;
		argb[3] = p & 0xff;
		return argb;
	}
	
	public int getGray(int x, int y) {
		int p = pixels[y][x];
		
		int a = (p>>24) & 0xff;
		int r = (p>>16) & 0xff;
		int g = (p>>8) & 0xff;
		int b = p & 0xff;
		
		int avg = (r + g + b) / 3;
		
		p = (a<<24) | (avg<<16) | (avg<<8) | avg;
		
		return p;
	}
	
	public int getGrayR(int x, int y) {
		return this.pixelRasterByte[x*strideX+y*strideY] & 0xFF;
	}
	
	public int getAlpha(int x, int y) {
		int p = pixels[y][x];
		return p>>24 & 0xFF;
	}
	
	public int getRed(int x, int y) {
		int p = pixels[y][x];
		return p>>16 & 0xFF;
	}
	
	public int getGreen(int x, int y) {
		int p = pixels[y][x];
		return p>>8 & 0xFF;
	}

	public int getBlue(int x, int y) {
		int p = pixels[y][x];
		return p & 0xFF;
	}
	
	public void setGray(int x, int y, int value) {
		pixels[y][x] = value;
	}
	
	public void setPixel(int x, int y, int value) {
		pixels[y][x] = value;
	}
	
	public void setRGB(int x, int y, Color c) {
		int a = c.getAlpha();
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		
		int p = (a<<24) | (r<<16) | (g<<8) | b;
		
		pixels[y][x] = p;
	}
	
	public void setRGB(int x, int y, int[] rgb) {
		int p =  (rgb[0]<<16) | (rgb[1]<<8) | rgb[2];
		
		pixels[y][x] = p;
	}
	
	public void setRGB(int x, int y, int r, int g, int b) {
		int p = (r<<16) | (g<<8) | b;
		
		pixels[y][x] = p;
	}
	
	public void setRGB(int x, int y, int a, int r, int g, int b) {
		int p = (a<<24) | (r<<16) | (g<<8) | b;
		
		pixels[y][x] = p;
	}
}