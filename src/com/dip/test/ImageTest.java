package com.dip.test;

import java.awt.image.BufferedImage;

import javax.print.attribute.standard.Media;

import com.dip.filter.EdgeDetection;
import com.dip.filter.EdgeDetection.LaplacianMode;
import com.dip.filter.MedianFilter;
import com.dip.filter.NoiseProcess;
import com.dip.filter.NoiseProcess.NoiseType;
import com.dip.filter.SpatialFilter;
import com.dip.image.MakeImage;

public class ImageTest {

	static final String[] IMG_PATH = {
			"D:\\\\testimg3.jpg",
			"D:\\\\lena.jpg",
			"D:\\\\grayscale.jpg",
	};
	static final String OTPT_PATH = "D:\\\\output2.jpg";
	
	public static void main(String[] args) {
		myMethod();
	}
	
	public static void myMethod() {
		MakeImage mi = new MakeImage(IMG_PATH[1]);
    	mi.toGrayScale();
    	//mi.showIcon("Original Image");
    	
    	//NoiseProcess noiseImg = new  NoiseProcess(mi);
    	//noiseImg.addNoise(NoiseType.SaltAndPepper);
    	//mi = noiseImg.toMakeImage();
    	//mi.showIcon("Noise Image");
    	
    	//spatialFilter(mi);
    	//medianFilter(mi);
    	//applySobel(mi);
    	//applyPrewitt(mi);
    	//applyRobert(mi);
    	//applyLaplacian(mi, LaplacianMode.Positive);
    	
    	MakeImage blurImg = new MakeImage(EdgeDetection.applyLaplacian(mi, LaplacianMode.Negative));
    	applyHighBoost(mi, blurImg);
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
	
	public static void applyHighBoost(MakeImage mi, MakeImage blurImg) {
		BufferedImage newImage = EdgeDetection.applyHighBoost(mi, blurImg, 1);
		mi.setImage(newImage);
		mi.showIcon("High Boost");
	}
}