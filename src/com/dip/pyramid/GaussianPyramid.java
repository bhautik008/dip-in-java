package com.dip.pyramid;

import java.awt.image.BufferedImage;
import com.dip.filter.SpatialFilter;
import com.dip.image.MakeImage;

public class GaussianPyramid {
	
	public static MakeImage[] applyFilter(MakeImage mi, int level) {
		MakeImage[] pyramids = new MakeImage[level+1];
		pyramids[0] = mi;
		BufferedImage blurImg = mi.getBufferedImage();
		for(int i=1;i<=level;i++) {
			BufferedImage gaussianImg = applyGaussianBlur(blurImg);
			int width = (int) Math.floor(gaussianImg.getWidth()/2);
			int height = (int) Math.floor(gaussianImg.getHeight()/2);
			BufferedImage newImg = new BufferedImage(width, height, mi.getType());
			for(int y=0;y<height;y++) {
				for(int x=0;x<width;x++) {
					newImg.setRGB(x, y, gaussianImg.getRGB(x*2, y*2));
				}
			}
			pyramids[i] = new MakeImage(newImg);
			blurImg = newImg;
		}
		return pyramids;
	}
	
	private static BufferedImage applyGaussianBlur(BufferedImage image) {
		return SpatialFilter.applyFiler(image, 3);
	}
	
}
