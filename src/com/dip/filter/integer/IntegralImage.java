package com.dip.filter.integer;

import com.dip.image.MakeImage;

public class IntegralImage {
	
protected int[][] integralImage = null;
    
    private int width;
    private int height;

    public IntegralImage(MakeImage makeImage) {
        this(makeImage, 1);
    }
    
    public IntegralImage(MakeImage makeImage, int power) {
        this.width = makeImage.getWidth();
        this.height = makeImage.getHeight();
        Process(makeImage, power);
    }
    
    protected IntegralImage(int width, int height){
        this.width = width;
        this.height = height;
        this.integralImage = new int[height + 1][width  + 1];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int[][] getInternalData(){
        return integralImage;
    }
    
    public int getInternalData(int x, int y){
        return integralImage[x][y];
    }
    
    public static IntegralImage FrommakeImage(MakeImage makeImage){
        return FrommakeImage(makeImage, 1);
    }
    
    public static IntegralImage FrommakeImage(MakeImage makeImage, int power){
        // get source image size
        int width  = makeImage.getWidth();
        int height = makeImage.getHeight();

        // create integral image
        IntegralImage im = new IntegralImage( width, height );
        int[][] integralImage = im.integralImage;

        for (int i = 1; i <= height; i++) {
            
            int rowSum = 0;
            
            for (int j = 1; j <= width; j++) {
                rowSum += Math.pow(makeImage.getGray(i - 1, j - 1), power);
                integralImage[i][j] = rowSum + integralImage[i - 1][j];
            }
        }
        
        return im;
    }
    
    private void Process(MakeImage makeImage, int power){
        if (!makeImage.isGrayScale()) {
            try {
                throw new Exception("IntegralImage works only with Grayscale images");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        integralImage = new int[height + 1][width + 1];
        
        for (int y = 1; y < height + 1; y++) {
            int rowSum = 0;
            for (int x = 1; x < width + 1; x++) {
                rowSum += Math.pow(makeImage.getGray(x - 1, y - 1), power);
                integralImage[y][x] = rowSum + integralImage[y - 1][x];
            }
        }
    }
    
    public int getRectangleSum(int x1, int y1, int x2, int y2){
        // check if requested rectangle is out of the image
        if ( ( x2 < 0 ) || ( y2 < 0 ) || ( x1 >= height ) || ( y1 >= width ) )
            return 0;

        if ( x1 < 0 ) x1 = 0;
        if ( y1 < 0 ) y1 = 0;

        x2++;
        y2++;

        if ( x2 > height )  x2 = height;
        if ( y2 > width ) y2 = width;

        return integralImage[x2][y2] + integralImage[x1][y1] - integralImage[x1][y2] - integralImage[x2][y2];
    }
    
    public int getRectangleSum(int x, int y, int radius){
        return getRectangleSum(x - radius, y - radius, x + radius, y + radius);
    }
    
    public int getHarrXWavelet(int x, int y, int radius){
        int y1 = y - radius;
        int y2 = y + radius - 1;

        int a = getRectangleSum( x, y1, x + radius - 1, y2 );
        int b = getRectangleSum( x - radius, y1, x - 1, y2 );

        return (int) ( a - b );
    }
    
    public int getHaarYWavelet( int x, int y, int radius ){
        int x1 = x - radius;
        int x2 = x + radius - 1;

        float a = getRectangleSum( x1, y, x2, y + radius - 1 );
        float b = getRectangleSum( x1, y - radius, x2, y - 1 );

        return (int) ( a - b );
    }
    
    public float getRectangleMean(int x1, int y1, int x2, int y2){
        // check if requested rectangle is out of the image
        if ( ( x2 < 0 ) || ( y2 < 0 ) || ( x1 >= height ) || ( y1 >= width ) )
            return 0;

        if ( x1 < 0 ) x1 = 0;
        if ( y1 < 0 ) y1 = 0;

        x2++;
        y2++;

        if ( x2 > height ) x2 = height;
        if ( y2 > width ) y2 = width;

        // return sum divided by actual rectangles size
        return (float) ( (double) ( integralImage[x2][y2] + integralImage[x1][y1] - integralImage[x1][y2] - integralImage[x2][y1] ) /
            (double) ( ( x2 - x1 ) * ( y2 - y1 ) ) );
    }
    
    public float getRectangleMean(int x, int y, int radius){
        return getRectangleMean(x - radius, y - radius, x + radius, y + radius);
    }
    
    public int getRectangleSumUnsafe( int x1, int y1, int x2, int y2 ){
        x2++;
        y2++;

        return integralImage[x2][y2] + integralImage[x1][y1] - integralImage[x2][y1] - integralImage[x1][y2];
    }
    
    public int getRectangleSumUnsafe( int x, int y, int radius ){
        return getRectangleSumUnsafe( x - radius, y - radius, x + radius, y + radius );
    }
    
    public float getRectangleMeanUnsafe( int x1, int y1, int x2, int y2 ){
        x2++;
        y2++;

        // return sum divided by actual rectangles size
        return (float) ( (double) ( integralImage[x2][y2] + integralImage[x1][y1] - integralImage[x1][y2] - integralImage[x2][y1] ) /
            (double) ( ( x2 - x1 ) * ( y2 - y1 ) ) );
    }
    
    public float getRectangleMeanUnsafe( int x, int y, int radius ){
        return getRectangleMeanUnsafe( x - radius, y - radius, x + radius, y + radius );
    }
	
}
