package com.dip.texture;

import com.dip.image.MakeImage;

public class GrayLevelCooccurrenceMatrix {
	
	public static enum Degree{
		Degree_0,
		Degree_45,
		Degree_90,
		Degree_135,
		Degree_180,
		Degree_225,
		Degree_270,
		Degree_310
	};
	
	private Degree degree = Degree.Degree_0;
	private int distance = 1;
	private MakeImage mi;
	private int numPairs;
	private boolean normalize = false;
	
	public Degree getDegree() {
		return degree;
	}
	public void setDegree(Degree degree) {
		this.degree = degree;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public GrayLevelCooccurrenceMatrix(MakeImage mi) {
		this.mi = mi;
	}
	
	public GrayLevelCooccurrenceMatrix(MakeImage mi, int distance, Degree degree) {
		this.mi = mi;
		this.distance = distance;
		this.degree = degree;
	}
	
	public GrayLevelCooccurrenceMatrix(MakeImage mi, int distance, Degree degree, boolean normalize) {
		this.mi = mi;
		this.distance = distance;
		this.degree = degree;
		this.normalize = normalize;
	}
	
	public double[][] compute(){
		int maxGray = 255;
		this.numPairs = 0;
		
		double[][] coocurrence = new double[maxGray + 1][maxGray + 1];
		int width = mi.getWidth();
		int height = mi.getHeight();
		
		switch (degree) {
			case Degree_0:
	            for (int i = 0; i < height; i++) {
	                for (int j = distance; j < width; j++) {
	                    coocurrence[mi.getGrayR(i, j - distance)][mi.getGrayR(i, j)]++;
	                    numPairs++;
	                }
	            }
	        break;
	        case Degree_45:
	            for (int x = distance; x < height; x++) {
	                for (int y = 0; y < width - distance; y++) {
	                    coocurrence[mi.getGrayR(x, y)][mi.getGrayR(x - distance, y + distance)]++;
	                    numPairs++;
	                }
	            }
	        break;
	        case Degree_90:
	            for (int i = distance; i < height; i++) {
	                for (int j = 0; j < width; j++) {
	                    coocurrence[mi.getGrayR(i - distance, j)][mi.getGrayR(i, j)]++;
	                    numPairs++;
	                }
	            }
	        break;
	        case Degree_135:
	            for (int x = distance; x < height; x++) {
	                int steps = width - 1;
	                for (int y = 0; y < width - distance; y++) {
	                    coocurrence[mi.getGrayR(x, steps - y)][mi.getGrayR(x - distance, steps -distance - y)]++;
	                    numPairs++;
	                }
	            }
	        break;
			/*case Degree_180:
					for(int y=0;y<height;y++) {
						for(int x=distance;x<width;x++) {
							coocurrence[mi.getGray(x, y)][mi.getGray(x, y)]++;
							numPairs++;
						}
					}
				break;
			case Degree_225:
					for(int y=0;y<height;y++) {
						for(int x=distance;x<width;x++) {
							coocurrence[mi.getGray(x, y)][mi.getGray(x, y)]++;
							numPairs++;
						}
					}
				break;
			case Degree_270:
					for(int y=0;y<height;y++) {
						for(int x=distance;x<width;x++) {
							coocurrence[mi.getGray(x, y)][mi.getGray(x, y)]++;
							numPairs++;
						}
					}
				break;
			case Degree_310:
					for(int y=0;y<height;y++) {
						for(int x=distance;x<width;x++) {
							coocurrence[mi.getGray(x, y)][mi.getGray(x, y)]++;
							numPairs++;
						}
					}
				break;*/
		}
		
		if(normalize) Normalize(coocurrence, numPairs == 0 ? 1 : numPairs);
		
		return coocurrence;
	}
	
	private void Normalize(double[][] coocurrenceMatrix, int numPairs) {
		for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                coocurrenceMatrix[i][j] /= numPairs;
            }
        }
	}
}
