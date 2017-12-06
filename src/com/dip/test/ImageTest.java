package com.dip.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;

import javax.print.attribute.standard.Media;

import com.dip.filter.Blending;
import com.dip.filter.EdgeDetection;
import com.dip.filter.EdgeDetection.LaplacianMode;
import com.dip.filter.GaussianFilter;
import com.dip.filter.Highpass;
import com.dip.filter.Histogram;
import com.dip.filter.Highpass.HighPassMode;
import com.dip.filter.MedianFilter;
import com.dip.filter.NoiseProcess;
import com.dip.filter.NoiseProcess.NoiseType;
import com.dip.gui.GUIFrame;
import com.dip.filter.SpatialFilter;
import com.dip.filter.Threshold;
import com.dip.image.MakeImage;
import com.dip.pyramid.GaussianPyramid;
import com.dip.pyramid.LaplacianPyramid;
import com.dip.texture.CenterSymmetricLocalBinaryPattern;
import com.dip.texture.GrayLevelCooccurrenceMatrix;
import com.dip.texture.GrayLevelCooccurrenceMatrix.Degree;
import com.dip.texture.Haralick;
import com.dip.texture.LocalBinaryPattern;

public class ImageTest {

	static final String[] IMG_PATH = {
			"D:\\\\z\\\\testimg3.jpg",	// 0
			"D:\\\\z\\\\lena.jpg",		// 1
			"D:\\\\z\\\\grayscale.jpg",	// 2
			"D:\\\\z\\\\myimg.jpg",		// 3
			"D:\\\\z\\\\men1.jpg",		// 4
			"D:\\\\z\\\\men2.jpg",		// 5
			"D:\\\\z\\\\monkey.jpg",	// 6
			"D:\\\\z\\\\galblack.jpeg",	// 7
			"D:\\\\z\\\\harsh.jpg",		// 8
	};
	static final String OTPT_PATH = "D:\\\\z\\\\output\\\\output.jpg";
	
	public static void main(String[] args) {
		myMethod();
		//GUIFrame mainFrame = new GUIFrame();
	}
	
	public static void myMethod() {
//		MakeImage mi = new MakeImage(IMG_PATH[6]);
//		mi.toGrayScale();
		
		//mi.showIcon("Original Image");
    	//NoiseProcess noiseImg = new  NoiseProcess(mi);
    	//noiseImg.addNoise(NoiseType.SaltAndPepper);
    	//mi = noiseImg.toMakeImage();
    	//mi.showIcon("Noise Image");
    	
    	//applyHighPass(mi);
    	//spatialFilter(mi);
    	//medianFilter(mi);
    	//applySobel(mi);
    	//applyPrewitt(mi);
    	//applyRobert(mi);
    	//applyLaplacian(mi, LaplacianMode.Positive);    	
		//applyUnsharpMasking(mi);
    	//applyHighBoost(mi);
		//applyThresholding(mi);
		//applyLBP(mi);
		//applyCooccurrence(mi);
		//applyPyramids(mi);
		applyBending();
	}
	
	public static void applyBending() {
		MakeImage img1 = new MakeImage(IMG_PATH[6]);
		MakeImage img2 = new MakeImage(IMG_PATH[1]);
		Blending blending = new Blending(img1, img2, 0.5);
		blending.applyBlending();
		MakeImage result = blending.getResult();
		result.showIcon();
	}
	
	public static void applyPyramids(MakeImage mi) {
		MakeImage[] pyramid = GaussianPyramid.applyFilter(mi, 3);
//		pyramid[0].showIcon("Original Image");
//		for(int i=1;i<pyramid.length;i++) {
//			pyramid[i].showIcon("Level "+i+" Gaussian Image");
//		}
		MakeImage[] lPyramid = new MakeImage[pyramid.length];
		for(int j=0;j<lPyramid.length;j++) {
			lPyramid[j] = new MakeImage(EdgeDetection.applyLaplacian(pyramid[j], LaplacianMode.Negative));
		}
		for(int i=0;i<lPyramid.length;i++) {
			lPyramid[i].showIcon("Level "+i+" Laplacian Image");
		}
	}
	
	public static void applyCooccurrence(MakeImage mi) {
		GrayLevelCooccurrenceMatrix gcc = new GrayLevelCooccurrenceMatrix(mi);
		double[][] gccmatrix = gcc.compute();
//		for(int i=0;i<gccmatrix.length;i++) {
//			for(int j=0;j<gccmatrix[0].length;j++) {
//				System.out.print((int) gccmatrix[i][j]+" ");
//			}
//			System.out.println();
//		}
		
		double energy = Haralick.Energy(gccmatrix);
		double entropy = Haralick.Entropy(gccmatrix);
		double contrast = Haralick.Contrast(gccmatrix);
		double correlation = Haralick.Correlation(gccmatrix);
		double homogeneity = Haralick.TextureHomogeneity(gccmatrix);
		double shade = Haralick.ClusterShade(gccmatrix);
		double prominence = Haralick.ClusterProminence(gccmatrix);
		
		System.out.println("Enery: "+energy);
		System.out.println("Entropy: "+entropy);
		System.out.println("Contrast: "+contrast);
		System.out.println("Correlation: "+correlation);
		System.out.println("Homogeneity: "+homogeneity);
		System.out.println("Shade: "+shade);
		System.out.println("Prominence: "+prominence);
	}
	
	public static void applyThresholding(MakeImage mi) {
		MakeImage thresoldImg = new MakeImage(Threshold.biLevelThreshold(mi, 100));
		thresoldImg.showIcon("bi-level Thresold Image");
		
		MakeImage thresoldImgM = new MakeImage(Threshold.multiLevelThreshold(mi, 80, 180));
		thresoldImgM.showIcon("Multilevel Thresold Image");
		
		MakeImage thresholdImgL = new MakeImage(Threshold.locallyAdaptiveThreshold(mi, 4));
		thresholdImgL.showIcon("Locally Adaptive Thresholding");
	}
	
	public static void applyLBP(MakeImage mi) {
		MakeImage lbpImg = new MakeImage(LocalBinaryPattern.applyLBP(mi));
		lbpImg.showIcon();
		
//		MakeImage cslbpImg = new MakeImage(CenterSymmetricLocalBinaryPattern.applyCSLBP(mi));
//		cslbpImg.showIcon();
	}
	
	public static void applyNoise(MakeImage mi, NoiseType type) {
    	NoiseProcess noiseImg = new  NoiseProcess(mi);
    	//noiseImg.setNoiseAmount(0.10);
    	if(type == NoiseType.Gaussian) {
    		noiseImg.addNoise(NoiseType.Gaussian);
    	} else if(type == NoiseType.Impulse) {
    		noiseImg.addNoise(NoiseType.Impulse);
    	} else {
    		noiseImg.addNoise(NoiseType.SaltAndPepper);
    	}
    	MakeImage nImage = noiseImg.toMakeImage();
    	nImage.showIcon("Noised Image");
	}
	
	public static void applyHighPass(MakeImage mi) {
		MakeImage blurImg = new MakeImage(SpatialFilter.applyFiler(mi.getBufferedImage(), 3));
    	blurImg.showIcon("Blur Image");
    	BufferedImage newImage = Highpass.applyFilter(mi, HighPassMode.Positive);
    	mi.setImage(newImage);
    	mi.showIcon("High Pass");
	}
	
	public static void spatialFilter(MakeImage mi) {
		BufferedImage bnew = SpatialFilter.applyFiler(mi.getBufferedImage(), 5);
		mi.setImage(bnew);
		mi.showIcon("Spatial Filtering");
	}
	
	public static void medianFilter(MakeImage mi) {
		BufferedImage bnew = MedianFilter.applyFilter(mi, 3);
		mi.setImage(bnew);
		mi.showIcon("Median Filtering");
	}

	public static void applySobel(MakeImage mi) {
		BufferedImage newImage = EdgeDetection.applySobel(mi);
		mi.setImage(newImage);
		mi.showIcon("Sobel");
	}
	
	public static void applyPrewitt(MakeImage mi) {
		BufferedImage newImage = EdgeDetection.applyPrewitt(mi);
		mi.setImage(newImage);
		mi.showIcon("Prewitt");
	}
	
	public static void applyRobert(MakeImage mi) {
		BufferedImage newImage = EdgeDetection.applyRoberts(mi);
		mi.setImage(newImage);
		mi.showIcon("Robert");
	}
	
	public static void applyLaplacian(MakeImage mi, LaplacianMode mode) {
		BufferedImage newImage = EdgeDetection.applyLaplacian(mi, mode);
		mi.setImage(newImage);
		mi.showIcon("Laplacian");
	}
	
	public static void applyUnsharpMasking(MakeImage mi) {
		MakeImage blurImg = new MakeImage(SpatialFilter.applyFiler(mi.getBufferedImage(), 3));
    	blurImg.showIcon("Blur Image");
    	BufferedImage newImage = EdgeDetection.applyUnsharpMasking(mi, blurImg);
    	mi.setImage(newImage);
    	mi.showIcon("Unsharp Masking");
	}
	
	public static void applyHighBoost(MakeImage mi) {
		//MakeImage blurImg = new MakeImage(EdgeDetection.applyLaplacian(mi, LaplacianMode.Negative));
		MakeImage blurImg = new MakeImage(SpatialFilter.applyFiler(mi.getBufferedImage(), 3));
		blurImg.showIcon("Laplacian image");
		BufferedImage newImage = EdgeDetection.applyHighBoost(mi, blurImg, 1.2);
		mi.setImage(newImage);
		mi.showIcon("High Boost");
	}
}