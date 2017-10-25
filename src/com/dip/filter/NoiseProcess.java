package com.dip.filter;

import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class NoiseProcess {

	public enum NoiseType{
		SaltAndPepper,
		Impulse,
		Gaussian
	}
	
	public MakeImage mImage;
	private double noiseAmount = 0.05;
	
	public NoiseProcess(MakeImage image) {
		mImage = image;
	}
	
	public void addNoise(NoiseType noiseType) {
		if(noiseType == NoiseType.SaltAndPepper) {
			BufferedImage noiseImage = SalfAndPepper.applyNoise(mImage.getBufferedImage(), this.noiseAmount);
			mImage.setImage(noiseImage);
		} else if(noiseType == NoiseType.Impulse) {
			BufferedImage noiseImage = ImpulseNoise.applyNoise(mImage.getBufferedImage(), this.noiseAmount);
			mImage.setImage(noiseImage);
		} else if(noiseType == NoiseType.Gaussian) {
			BufferedImage noiseImage = GaussianNoise.applyNoise(mImage.getBufferedImage());
			mImage.setImage(noiseImage);
		}
	}
	
	public void setNoiseAmount(double amount) {
		noiseAmount = amount;
	}
	
	public MakeImage toMakeImage() {
		return mImage;
	}
}