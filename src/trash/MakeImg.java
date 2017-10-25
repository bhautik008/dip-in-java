package trash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dip.image.MakeImage;
import com.dip.image.MakeImage.ColorScale;

public class MakeImg {

//	private BufferedImage bufferedImage;
//    private WritableRaster raster;
//    private int[] pixels;
//    private byte[] pixelGray;
//    private int size;
//    
//	public enum ColorScale{
//		Grayscale,
//		RGB,
//		ARGB
//	}
//	
//	/*
//	 * Constructor
//	 */
//	
//	public MakeImage() {}
//	
//	public MakeImage(MakeImage mi) {
//		
//	}
//	
//	public MakeImage(BufferedImage image) {
//		
//	}
//	
//	public MakeImage(Image image) {
//		
//	}
//	
//	public MakeImage(String path) {
//		
//	}
//	
//	public MakeImage(int x, int y) {
//		
//	}
//	
//	public MakeImage(int x, int y, ColorScale colorScale) {
//		
//	}
//	
//	/*
//	 * Prepare and refresh methods
//	 */
//	public void prepare() {
//		if(getType() == BufferedImage.TYPE_BYTE_GRAY) {
//			refresh();
//		} else if(getType() == BufferedImage.TYPE_INT_ARGB || getType() == BufferedImage.TYPE_4BYTE_ABGR) {
//			toARGB();
//		} else {
//			toRGB();
//		}
//	}
//	
//	public void refresh() {
//		raster = getRaster();
//		if(isGrayscale()) {
//			pixelGray = ((DataBufferByte)raster.getDataBuffer()).getData();
//			size = pixelGray.length;
//		} else {
//			pixels = ((DataBufferInt)raster.getDataBuffer()).getData();
//			size = pixels.length;
//		}
//	}
//	
//	
//	
//	/*
//	 * image information methods
//	 */
//	public int getType() {
//		return this.bufferedImage.getType();
//	}
//	
//	public BufferedImage getBufferedImage() {
//		return this.bufferedImage;
//	}
//	
//	public WritableRaster getRaster() {
//		return this.bufferedImage.getRaster();
//	}
//	
//	public boolean isGrayscale() {
//		if(getType() == BufferedImage.TYPE_BYTE_GRAY) {
//			return true;
//		}
//		return false;
//	}
//	
//	/*
//	 * conversion methods
//	 */
//	public void toARGB() {
//		
//	}
//	
//	public void toRGB() {
//		
//	}
	
	private BufferedImage bufferedImage;
    private WritableRaster raster;
    private int[][] pixels;
    private boolean isImport = false;
    
	public enum ColorScale{
		Grayscale,
		RGB,
		ARGB
	}
	
	public MakeImg(MakeImg mi) {
		this.bufferedImage = mi.getBufferedImage();
		this.isImport = true;
		refresh();
	}
	
	public MakeImg(BufferedImage img) {
		this.bufferedImage = img;
		this.isImport = true;
		refresh();
	}
	
	public MakeImg(Image img) {
		this.bufferedImage = (BufferedImage) img;
		this.isImport = true;
		refresh();
	}
	
	public MakeImg(ImageIcon img) {
		this.bufferedImage = (BufferedImage) img.getImage();
		this.isImport = true;
		refresh();
	}
	
	public MakeImg(String path) {
		try {
			this.bufferedImage = ImageIO.read(new File(path));
			this.isImport = true;
		} catch(IOException e) {
			e.printStackTrace();
		}
		refresh();
	}
	
	public MakeImg(int width, int height) {
		this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		refresh();
	}
	
	public MakeImg(int width, int height, ColorScale colorScale) {
		if(colorScale == ColorScale.Grayscale) {
			this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		} else if(colorScale == ColorScale.ARGB) {
			this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
			this.bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		refresh();
	}
	
	public MakeImg(int[][] image) {
		this.bufferedImage = new BufferedImage(image[0].length, image.length, BufferedImage.TYPE_BYTE_GRAY);
		refresh();
		matrixToImage(image);
	}
	
	public void refresh() {
		int width = getWidth();
		int height = getHeight();
		
		this.pixels = new int[height][width];
		
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
//		if(getType() == BufferedImage.TYPE_BYTE_GRAY) {
//			return true;
//		}
		Raster ras = getRaster();
		int elem = ras.getNumDataElements();
		if(elem == 1) {
			return true;
		}
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
	
	/*
	 * set methods
	 */
	public void setImage(BufferedImage image) {
		this.bufferedImage = image;
		isImport = true;
		refresh();
	}
	
	public void setImage(MakeImg mi) {
		this.bufferedImage = mi.getBufferedImage();
		isImport = true;
		refresh();
	}
	
	/*
	 * conversion methods
	 */
	public void toGrayScale() {
		int width = getWidth();
		int height = getHeight();
		
		BufferedImage bg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				int p = convertPixelToGray(pixels[i][j]);
				bg.setRGB(j, i, p);
			}
		}
		this.bufferedImage = bg;
		refresh();
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
		BufferedImage b = new BufferedImage(getWidth(), getHeight(), getType());
//		Graphics g = b.getGraphics();
//		g.drawImage(this.bufferedImage, 0, 0, null);
		for(int i=0;i<getHeight();i++) {
			for(int j=0;j<getWidth();j++) {
				b.setRGB(j, i, pixels[i][j]);
			}
		}
		ImageIcon img = new ImageIcon(b);
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
	
	public void showImage() {
		JOptionPane.showMessageDialog(null, toImage(), "", JOptionPane.PLAIN_MESSAGE);
	}
	
	public void showImage(String message) {
		JOptionPane.showMessageDialog(null, toImage(), message, JOptionPane.PLAIN_MESSAGE);
	}
	
	/*
	 * get pixels and color value
	 */
	public int getPixel(int x, int y) {
		return this.pixels[x][y];
	}
	
	public int[] getRGB(int x, int y) {
		int p = this.pixels[x][y];
		int[] rgb = new int[3];
		rgb[0] = p >> 16 & 0xff;
		rgb[1] = p >> 8 & 0xff;
		rgb[2] = p & 0xff;
		return rgb;
	}
	
	public int[] getARGB(int x, int y) {
		int p = this.pixels[x][y];
		int[] argb = new int[4];
		argb[0] = p >> 24 & 0xff;
		argb[1] = p >> 16 & 0xff;
		argb[2] = p >> 8 & 0xff;
		argb[3] = p & 0xff;
		return argb;
	}
	
	public int getGray(int x, int y) {
		int p = pixels[x][y];
		
		int a = (p>>24) & 0xff;
		int r = (p>>16) & 0xff;
		int g = (p>>8) & 0xff;
		int b = p & 0xff;
		
		int avg = (r + g + b) / 3;
		
		p = (a<<24) | (avg<<16) | (avg<<8) | avg;
		
		return p;
	}
	
	public void setGray(int x, int y, int value) {
		pixels[x][y] = value;
	}
	
	public void setPixel(int x, int y, int value) {
		pixels[x][y] = value;
	}
	
	public void setRGB(int x, int y, Color c) {
		int a = c.getAlpha();
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		
		int p = (a<<24) | (r<<16) | (g<<8) | b;
		
		pixels[x][y] = p;
	}
	
	public void setRGB(int x, int y, int[] rgb) {
		int p =  (rgb[0]<<16) | (rgb[1]<<8) | rgb[2];
		
		pixels[x][y] = p;
	}
	
	public void setRGB(int x, int y, int r, int g, int b) {
		int p = (r<<16) | (g<<8) | b;
		
		pixels[x][y] = p;
	}
	
	public void setRGB(int x, int y, int a, int r, int g, int b) {
		int p = (a<<24) | (r<<16) | (g<<8) | b;
		
		pixels[x][y] = p;
	}
	
	public void setImageData(int[][] ary) {
		for(int i=0;i<getHeight();i++) {
			for(int j=0;j<getWidth();j++) {
				pixels[i][j] = ary[i][j];
			}
		}
	}
}