package trash;
// from com.dip.filter
import java.awt.image.BufferedImage;

import com.dip.image.MakeImage;

public class HighBoost {

	public static BufferedImage applyFilter(MakeImage mi, int[][] kernelm) {
		int width = mi.getWidth();
		int height = mi.getHeight();
		BufferedImage newImage = new BufferedImage(width,height,mi.getType());
		
//		for(int x=1;x<width-1;x++) {
//			for(int y=1;y<height-1;y++) {
//				xVal = (kernel[0][0]*mi.getPixel(x - 1, y - 1)) + (kernel[0][1]*mi.getPixel(x, y - 1)) + (kernel[0][2]*mi.getPixel(x + 1, y - 1))
//				 		+ (kernel[1][0]*mi.getPixel(x - 1, y)) 	+ (kernel[1][1]*mi.getPixel(x, y))	   + (kernel[1][2]*mi.getPixel(x + 1, y))
//				 		+ (kernel[2][0]*mi.getPixel(x - 1, y + 1)) + (kernel[2][1]*mi.getPixel(x, y + 1)) + (kernel[2][2]*mi.getPixel(x + 1, y + 1));
//				
//				yVal = (kernel[0][0]*mi.getPixel(x - 1, y - 1)) + (kernel[0][1]*mi.getPixel(x, y - 1)) + (kernel[0][2]*mi.getPixel(x + 1, y - 1))
//				 		+ (kernel[1][0]*mi.getPixel(x - 1, y)) 	+ (kernel[1][1]*mi.getPixel(x, y))	   + (kernel[1][2]*mi.getPixel(x + 1, y))
//				 		+ (kernel[2][0]*mi.getPixel(x - 1, y + 1)) + (kernel[2][1]*mi.getPixel(x, y + 1)) + (kernel[2][2]*mi.getPixel(x + 1, y + 1));
//				gVal = Math.abs(xVal) + Math.abs(yVal);
//				newImage.setRGB(x, y, (int) gVal);
//			}
//		}
		
		return newImage;
	}
	
	/*
	 * Laplacian old code
	 */
//	Color c00 = new Color(mi.getPixel(x-1, y-1));
//	Color c01 = new Color(mi.getPixel(x-1, y));
//	Color c02 = new Color(mi.getPixel(x-1, y+1));
//	Color c10 = new Color(mi.getPixel(x, y-1));
//	Color c11 = new Color(mi.getPixel(x, y));
//	Color c12 = new Color(mi.getPixel(x, y+1));
//	Color c20 = new Color(mi.getPixel(x+1, y-1));
//	Color c21 = new Color(mi.getPixel(x+1, y));
//	Color c22 = new Color(mi.getPixel(x+1, y+1));
//	int r,g,b;
//	if(mode == LaplacianMode.Negative) {
//		r = (int) ((laplacianN[0][0]*c00.getRed()) + (laplacianN[0][1]*c01.getRed()) + (laplacianN[0][2]*c02.getRed())
//            	+ (laplacianN[1][0]*c10.getRed()) + (laplacianN[1][1]*c11.getRed()) + (laplacianN[1][2]*c12.getRed()) +
//            	+ (laplacianN[2][0]*c20.getRed()) + (laplacianN[2][1]*c21.getRed()) + (laplacianN[2][2]*c22.getRed()));
//		g = (int) ((laplacianN[0][0]*c00.getGreen()) + (laplacianN[0][1]*c01.getGreen()) + (laplacianN[0][2]*c02.getGreen())
//        		+ (laplacianN[1][0]*c10.getGreen()) + (laplacianN[1][1]*c11.getGreen()) + (laplacianN[1][2]*c12.getGreen()) +
//        		+ (laplacianN[2][0]*c20.getGreen()) + (laplacianN[2][1]*c21.getGreen()) + (laplacianN[2][2]*c22.getGreen()));
//		b = (int) ((laplacianN[0][0]*c00.getBlue()) + (laplacianN[0][1]*c01.getBlue()) + (laplacianN[0][2]*c02.getBlue())
//        		+ (laplacianN[1][0]*c10.getBlue()) + (laplacianN[1][1]*c11.getBlue()) + (laplacianN[1][2]*c12.getBlue()) +
//        		+ (laplacianN[2][0]*c20.getBlue()) + (laplacianN[2][1]*c21.getBlue()) + (laplacianN[2][2]*c22.getBlue()));
//	} else {
//		r = (int) ((laplacianP[0][0]*c00.getRed()) + (laplacianP[0][1]*c01.getRed()) + (laplacianP[0][2]*c02.getRed())
//            	+ (laplacianP[1][0]*c10.getRed()) + (laplacianP[1][1]*c11.getRed()) + (laplacianP[1][2]*c12.getRed()) +
//            	+ (laplacianP[2][0]*c20.getRed()) + (laplacianP[2][1]*c21.getRed()) + (laplacianP[2][2]*c22.getRed()));
//		g = (int) ((laplacianP[0][0]*c00.getGreen()) + (laplacianP[0][1]*c01.getGreen()) + (laplacianP[0][2]*c02.getGreen())
//        		+ (laplacianP[1][0]*c10.getGreen()) + (laplacianP[1][1]*c11.getGreen()) + (laplacianP[1][2]*c12.getGreen()) +
//        		+ (laplacianP[2][0]*c20.getGreen()) + (laplacianP[2][1]*c21.getGreen()) + (laplacianP[2][2]*c22.getGreen()));
//		b = (int) ((laplacianP[0][0]*c00.getBlue()) + (laplacianP[0][1]*c01.getBlue()) + (laplacianP[0][2]*c02.getBlue())
//        		+ (laplacianP[1][0]*c10.getBlue()) + (laplacianP[1][1]*c11.getBlue()) + (laplacianP[1][2]*c12.getBlue()) +
//        		+ (laplacianP[2][0]*c20.getBlue()) + (laplacianP[2][1]*c21.getBlue()) + (laplacianP[2][2]*c22.getBlue()));
//	}
//    r = Math.min(255, Math.max(0, r));
//    g = Math.min(255, Math.max(0, g));
//    b = Math.min(255, Math.max(0, b));
//    Color color = new Color(r, g, b);
//	newImage.setRGB(x, y, color.getRGB());
}
