package com.dip.filter;

import com.dip.image.MakeImage.ColorScale;
import com.dip.math.ComplexNumber;
import com.dip.math.MathFourierTransform;

public class FourierTransform {
	
	static final String FFTO_PATH = "D:\\\\\\\\fftoutput.jpg";
	
	static final double PI = Math.PI;
	static final double J = Math.sqrt(Math.abs(-1));
	static int[][] aryPixel;
	static int width, height;
	static ComplexNumber[][] data;
	
	boolean isFouriered = false;
	
//	public FourierTransform(MakeImage image) {
//		width = image.getWidth();
//		height = image.getHeight();
//		
//		data = new ComplexNumber[height][width];
//		
//		for(int i=0;i<height;i++) {
//			for(int j=0;j<width;j++) {
//				data[i][j] = new ComplexNumber(0,0);
//				data[i][j].real = (double) image.getPixel(i, j);
//			}
//		}
//	}
//	
//	public void startDFT() {
//		if(!isFouriered) {
//			for ( int x = 0; x < height; x++ ){
//                for ( int y = 0; y < width; y++ ){
//                    if ( ( ( x + y ) & 0x1 ) != 0 ){
//                        data[x][y].real *= -1;
//                        data[x][y].imaginary *= -1;
//                    }
//                }
//            }
//			
//			MathFourierTransform.fft2(data, MathFourierTransform.Direction.Forward);
//			isFouriered = true;
//		}
//	}
//	
//	public MakeImage toMakeImage() {
//		MakeImage image = new MakeImage(width, height, ColorScale.Grayscale);
//		
//		if(isFouriered) {
//			double[][] mag = new double[height][width];
//            double min = Double.MAX_VALUE;
//            double max = -Double.MAX_VALUE;
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                    //Compute log for perceptual scaling and +1 since log(0) is undefined.
//                    mag[i][j] = Math.log(data[i][j].getMagnitude() + 1);
//                    
//                    if(mag[i][j] < min) min = mag[i][j];
//                    if(mag[i][j] > max) max = mag[i][j];
//                }
//            }
//            
//            for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                	image.setGray(i, j, (int)scale(min, max, 0, 255, mag[i][j]));
//                }
//            }
//		} else {
//			for (int i = 0; i < height; i++) {
//                for (int j = 0; j < width; j++) {
//                	int real = (int)data[i][j].real;
//                	image.setGray(i, j, image.clampValues(real, 0, 255));
//                }
//            }
//		}
//		return image;
//	}
//	
//	private double scale(double fromMin, double fromMax, double toMin, double toMax, double x) {
//		if (fromMax - fromMin == 0) return 0;
//        return (toMax - toMin) * (x - fromMin) / (fromMax - fromMin) + toMin;
//	}
}

/*
 * OLD CODE
 */

//double[] fftAry = new double[dimensions[1]];
//
//for(int i=0; i<aryPixel.length; i++) {
//	double totalX = 0;
//	for(int j=0; j<aryPixel[i].length; j++) {
//		//double w = Math.cos((2*PI)/aryPixel[i].length) -  (Math.sin((2*PI)/aryPixel[i].length));
//		double w = Math.exp(((2)*J*PI)/aryPixel[i].length);
//		w = Math.pow(w, (i*j));
//		double f = aryPixel[i][j] * w;
//		totalX += f;
//	}
//	//System.out.println("F of "+i+" = "+totalX);
//	fftAry[i] = totalX;
//}
//
//double[][] fMary = fourierMatrix(fftAry);

//BufferedImage image = new  BufferedImage(fftAry.length, fftAry.length, BufferedImage.TYPE_INT_RGB);
//
//for(int i=0;i<fMary.length;i++) {
//	for(int j=0;j<fMary[i].length;j++) {
//		int rgb = (int) (fMary[i][j] * 2);
//		image.setRGB(i, j, rgb);
//	}
//}
//
//ImageToPixel.createImage(image, FFTO_PATH);

//public static double[][] fourierMatrix(double[] fftAry) {
//double[][] fMatrix = new double[fftAry.length][fftAry.length];
//for(int i=0;i<fMatrix.length;i++) {
//	for(int j=0;j<fMatrix[i].length;j++) {
//		if(i==0 || j==0) {
//			fMatrix[i][j] = 1;
//		} else {
//			fMatrix[i][j] = fftAry[j];
//		}
//		System.out.print(fMatrix[i][j]+" ");
//	}
//	System.out.println("");
//}
//return fMatrix;
//}