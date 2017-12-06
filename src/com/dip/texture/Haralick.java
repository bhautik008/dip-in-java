package com.dip.texture;

import java.awt.image.BufferedImage;

public class Haralick {
	
    public static double Energy(double[][] coocurrenceMatrix){
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += coocurrenceMatrix[i][j] * coocurrenceMatrix[i][j];
            }
        }
        return r;
    }
    
    public static BufferedImage Energy(double[][] coocurrenceMatrix, int width, int height){
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        double r;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r = coocurrenceMatrix[i][j] * coocurrenceMatrix[i][j];
                newImg.setRGB(j, i, (byte) r);
            }
        }
        return newImg;
    }
    
    public static double Entropy(double[][] coocurrenceMatrix){
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += coocurrenceMatrix[i][j] * applyLog(coocurrenceMatrix[i][j], 2);
            }
        }
        return -r;
    }
    
    public static double Contrast(double[][] coocurrenceMatrix){
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += Math.abs(i - j) * coocurrenceMatrix[i][j];
            }
        }
        return r;
    }
    
    public static double Inertia(double[][] coocurrenceMatrix){
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += Math.pow((i - j),2) * coocurrenceMatrix[i][j];
            }
        }
        
        return r;
    }
    
    public static double Correlation(double[][] coocurrenceMatrix){
        double meanI = 0;
        double stdI = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                meanI += coocurrenceMatrix[i][j];
            }
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                stdI += Math.pow(i - meanI, 2) * coocurrenceMatrix[i][j];
            }
        }
        
        double meanJ = 0;
        double stdJ = 0;
        for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
            for (int i = 0; i < coocurrenceMatrix.length; i++) {
                meanJ += coocurrenceMatrix[i][j];
            }
            for (int i = 0; i < coocurrenceMatrix.length; i++) {
                stdJ += Math.pow(j - meanJ, 2) * coocurrenceMatrix[i][j];
            }
        }
        
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += (i * j * coocurrenceMatrix[i][j] - meanI * meanJ) / stdI * stdJ;
            }
        }
        return r;
    }
    
    public static double TextureHomogeneity(double[][] coocurrenceMatrix){
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += coocurrenceMatrix[i][j] / (1 + Math.abs(i - j));
            }
        }
        return r;
    }
    
    public static double InverseDifference(double[][] coocurrenceMatrix){
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += coocurrenceMatrix[i][j] / Math.abs(i - j);
            }
        }
        return r;
    }
    
    public static double InverseDifferenceMoment(double[][] coocurrenceMatrix){
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += coocurrenceMatrix[i][j] / (1 + Math.pow((i - j), 2));
            }
        }
        return r;
    }
    
    public static double ClusterTendency(double[][] coocurrenceMatrix){
        double[] meanI = new double[coocurrenceMatrix.length];
        double[] meanJ = new double[coocurrenceMatrix[0].length];
        
        for (int i = 0; i < meanI.length; i++) {
            for (int j = 0; j < coocurrenceMatrix.length; j++) {
                meanI[i] += coocurrenceMatrix[i][j];
            }
            meanI[i] /= coocurrenceMatrix.length;
        }
        
        for (int j = 0; j < meanJ.length; j++) {
            for (int i = 0; i < coocurrenceMatrix.length; i++) {
                meanJ[i] += coocurrenceMatrix[i][j];
            }
            meanJ[j] /= coocurrenceMatrix[0].length;
        }
        
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += Math.pow((i - meanI[i]) + (j - meanJ[j]), 2) * coocurrenceMatrix[i][j];
            }
        }
        return r;
    }
    
    public static double ClusterShade(double[][] coocurrenceMatrix){
        double[] meanI = new double[coocurrenceMatrix.length];
        double[] meanJ = new double[coocurrenceMatrix[0].length];
        
        for (int i = 0; i < meanI.length; i++) {
            for (int j = 0; j < coocurrenceMatrix.length; j++) {
                meanI[i] += coocurrenceMatrix[i][j];
            }
            meanI[i] /= coocurrenceMatrix.length;
        }
        
        for (int j = 0; j < meanJ.length; j++) {
            for (int i = 0; i < coocurrenceMatrix.length; i++) {
                meanJ[i] += coocurrenceMatrix[i][j];
            }
            meanJ[j] /= coocurrenceMatrix[0].length;
        }
        
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += Math.pow((i - meanI[i]) + (j - meanJ[j]), 3) * coocurrenceMatrix[i][j];
            }
        }
        return r;
    }
    
    public static double ClusterProminence(double[][] coocurrenceMatrix){
        double[] meanI = new double[coocurrenceMatrix.length];
        double[] meanJ = new double[coocurrenceMatrix[0].length];
        
        for (int i = 0; i < meanI.length; i++) {
            for (int j = 0; j < coocurrenceMatrix.length; j++) {
                meanI[i] += coocurrenceMatrix[i][j];
            }
            meanI[i] /= coocurrenceMatrix.length;
        }
        
        for (int j = 0; j < meanJ.length; j++) {
            for (int i = 0; i < coocurrenceMatrix.length; i++) {
                meanJ[i] += coocurrenceMatrix[i][j];
            }
            meanJ[j] /= coocurrenceMatrix[0].length;
        }
        
        double r = 0;
        for (int i = 0; i < coocurrenceMatrix.length; i++) {
            for (int j = 0; j < coocurrenceMatrix[0].length; j++) {
                r += Math.pow((i - meanI[i]) + (j - meanJ[j]), 4) * coocurrenceMatrix[i][j];
            }
        }
        return r;
    }
	
    private static double applyLog(double a, double b) {
    	return Math.log(a) / Math.log(b);
    }
}