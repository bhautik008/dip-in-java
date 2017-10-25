package com.dip.math;

public class MathFourierTransform {
	
	public enum Direction {Forward, Backward};

	public static void DFT(ComplexNumber[] data, Direction direction){
        int n = data.length;
        ComplexNumber[] c = new ComplexNumber[n];
        
        for ( int i = 0; i < n; i++ ){
        	c[i] = new ComplexNumber(0, 0);
            double sumRe = 0;
            double sumIm = 0;
            double phim = 2 * Math.PI * i / n;
            
            for ( int j = 0; j < n; j++ ){
            	double gRe = data[j].real;
                double gIm = data[j].imaginary;
                double cosw = Math.cos(phim * j);
                double sinw = Math.sin(phim * j);
                if(direction == Direction.Backward)
                	sinw = -sinw;
                	sumRe += ( gRe * cosw + data[j].imaginary * sinw );
                	sumIm += ( gIm * cosw - data[j].real * sinw );
                }
            c[i] = new ComplexNumber(sumRe, sumIm);
        }
        
        if(direction == Direction.Backward){
            for (int i = 0; i < c.length; i++) {
            	data[i].real = c[i].real / n;
                data[i].imaginary = c[i].imaginary / n;
            }
        }
        else{
            for (int i = 0; i < c.length; i++) {
                data[i].real = c[i].real;
                data[i].imaginary = c[i].imaginary;
            }
        }
    }
	
    public static void DFT2(ComplexNumber[][] data, Direction direction){
        
        int n = data.length;
        int m = data[0].length;
        ComplexNumber[] row = new ComplexNumber[Math.max(m, n)];
        
        for ( int i = 0; i < n; i++ ){
        	for ( int j = 0; j < n; j++ ) {
        		row[j] = data[i][j];
        	}
        		
        	MathFourierTransform.DFT( row, direction );
        	
        	for( int j = 0; j < n; j++ ) {
        		data[i][j] = row[j];
        	}
        }
        
        ComplexNumber[]	col = new ComplexNumber[n];

        for ( int j = 0; j < n; j++ ){
        	for ( int i = 0; i < n; i++ ) {
        		col[i] = data[i][j];
        	}
        	
        	MathFourierTransform.DFT( col, direction );
        	for ( int i = 0; i < n; i++ ) {
        		data[i][j] = col[i];
        	}
        }
    }
	
	public static void fft(ComplexNumber[] data, Direction direction) {
		double[] real = ComplexNumber.getReal(data);
		double[] imaginary = ComplexNumber.getImaginary(data);
		
		if(direction == Direction.Forward) {
			fft(real, imaginary);
		} else {
			fft(imaginary, real);
		}
		
		if(direction == Direction.Forward) {
			for(int i=0; i<real.length;i++) {
				data[i] = new ComplexNumber(real[i], imaginary[i]);
			}
		} else {
			int n = real.length;
			for(int i=0;i<n;i++) {
				data[i] = new ComplexNumber(real[i] / n, imaginary[i] / n);
			}
		}
	}
	
	public static void fft2(ComplexNumber[][] data, Direction direction) {
		int n = data.length;
		int m = data[0].length;
		
		for(int i=0;i<n;i++) {
			ComplexNumber[] row = data[i];
			
			MathFourierTransform.fft(row, direction);
			
			for(int j=0;j<m;j++) {
				data[i][j] = row[j];
			}
		}
		
		ComplexNumber[] col = new ComplexNumber[n];
		for(int j=0;j<m;j++) {
			for(int i=0; i<n;i++) {
				col[i] = data[i][j];
			}
			
			MathFourierTransform.fft(col, direction);
			
			for(int i=0;i<n;i++) {
				data[i][j] = col[i];
			}
		}
	}
	
	private static void fft(double[] real, double[] imag) {
        int n = real.length;
        if (n == 0) {
            return;
        } else if ((n & (n - 1)) == 0)  // Is power of 2
            transformRadix2(real, imag);
        else  // More complicated algorithm for arbitrary sizes
            transformBluestein(real, imag);
    }
	
	private static void transformRadix2(double[] real, double[] imag) {
        int n = real.length;
        int levels = 31 - Integer.numberOfLeadingZeros(n);
        double[] cosTable = new double[n / 2];
        double[] sinTable = new double[n / 2];
        for (int i = 0; i < n / 2; i++) {
            cosTable[i] = Math.cos(2 * Math.PI * i / n);
            sinTable[i] = Math.sin(2 * Math.PI * i / n);
        }

        for (int i = 0; i < n; i++) {
            int j = Integer.reverse(i) >>> (32 - levels);
            if (j > i) {
                    double temp = real[i];
                    real[i] = real[j];
                    real[j] = temp;
                    temp = imag[i];
                    imag[i] = imag[j];
                    imag[j] = temp;
            }
        }

        for (int size = 2; size <= n; size *= 2) {
            int halfsize = size / 2;
            int tablestep = n / size;
            for (int i = 0; i < n; i += size) {
                for (int j = i, k = 0; j < i + halfsize; j++, k += tablestep) {
                    double tpre =  real[j+halfsize] * cosTable[k] + imag[j+halfsize] * sinTable[k];
                    double tpim = -real[j+halfsize] * sinTable[k] + imag[j+halfsize] * cosTable[k];
                    real[j + halfsize] = real[j] - tpre;
                    imag[j + halfsize] = imag[j] - tpim;
                    real[j] += tpre;
                    imag[j] += tpim;
                }
            }
            
            if (size == n)
                break;
        }
    }
	
	private static void transformBluestein(double[] real, double[] imag) {
        int n = real.length;
        int m = Integer.highestOneBit(n * 2 + 1) << 1;

        double[] cosTable = new double[n];
        double[] sinTable = new double[n];
        for (int i = 0; i < n; i++) {
            int j = (int)((long)i * i % (n * 2));
            cosTable[i] = Math.cos(Math.PI * j / n);
            sinTable[i] = Math.sin(Math.PI * j / n);
        }

        double[] areal = new double[m];
        double[] aimag = new double[m];
        for (int i = 0; i < n; i++) {
            areal[i] =  real[i] * cosTable[i] + imag[i] * sinTable[i];
            aimag[i] = -real[i] * sinTable[i] + imag[i] * cosTable[i];
        }
        double[] breal = new double[m];
        double[] bimag = new double[m];
        breal[0] = cosTable[0];
        bimag[0] = sinTable[0];
        for (int i = 1; i < n; i++) {
            breal[i] = breal[m - i] = cosTable[i];
            bimag[i] = bimag[m - i] = sinTable[i];
        }

        double[] creal = new double[m];
        double[] cimag = new double[m];
        convolve(areal, aimag, breal, bimag, creal, cimag);

        for (int i = 0; i < n; i++) {
            real[i] =  creal[i] * cosTable[i] + cimag[i] * sinTable[i];
            imag[i] = -creal[i] * sinTable[i] + cimag[i] * cosTable[i];
        }
    }
	
	private static void convolve(double[] x, double[] y, double[] out) {
		int n = x.length;
		convolve(x, new double[n], y, new double[n], out, new double[n]);
	}
	
	private static void convolve(double[] xreal, double[] ximag, double[] yreal, double[] yimag, double[] outreal, double[] outimag) {
		int n = xreal.length;
		
		fft(xreal, ximag);
		fft(yreal, yimag);
		for (int i = 0; i < n; i++) {
			double temp = xreal[i] * yreal[i] - ximag[i] * yimag[i];
			ximag[i] = ximag[i] * yreal[i] + xreal[i] * yimag[i];
			xreal[i] = temp;
		}
		
		inverseTransform(xreal, ximag);
		
		for (int i = 0; i < n; i++) {
			outreal[i] = xreal[i] / n;
			outimag[i] = ximag[i] / n;
		}
	}
	
	private static void inverseTransform(double[] real, double[] imag) {
		fft(imag, real);
    }
	
	/**
     * Shift zero-frequency component to center of spectrum.
     * @param x Data.
     * @param direction Transformation direction.
     */
    public static void FFTShift1D(double[] x, Direction direction) {
        
        if (x.length == 1)
            return;
        
        double[] temp = x.clone();
        int move = x.length / 2;
        
        if(direction == Direction.Forward){
            int c = 0;
            for (int i = x.length - move; i < x.length; i++)
                x[c++] = temp[i];

            for (int i = 0; i < x.length - move; i++)
                x[c++] = temp[i];
            
        }
        else{
            int c = 0;
            for (int i = move; i < x.length; i++)
                x[c++] = temp[i];
            
            for (int i = 0; i < move; i++)
                x[c++] = temp[i];
            
        }
    }
    
    /**
     * Shift zero-frequency component to center of spectrum.
     * @param <E> Object.
     * @param x Data.
     * @param direction Transformation direction.
     */
    public static <E> void FFTShift1D(E[] x, Direction direction) {
        
        if (x.length == 1)
            return;
        
        E[] temp = x.clone();
        int move = x.length / 2;
        
        if(direction == Direction.Forward){
            int c = 0;
            for (int i = x.length - move; i < x.length; i++)
                x[c++] = temp[i];

            for (int i = 0; i < x.length - move; i++)
                x[c++] = temp[i];
        }
        else{
            int c = 0;
            for (int i = move; i < x.length; i++)
                x[c++] = temp[i];
            
            for (int i = 0; i < move; i++)
                x[c++] = temp[i];
        }
    }
    
    /**
     * Shift zero-frequency component to center of spectrum.
     * @param x Data.
     * @param direction Direction.
     */
    public static void FFTShift2D(double[][] x, Direction direction){
        FFTShift2D(x, direction, 1);
        FFTShift2D(x, direction, 2);
    }
    
    /**
     * Shift zero-frequency component to center of spectrum.
     * @param x Data.
     * @param direction Direction.
     * @param dimension Dimension.
     */
    public static void FFTShift2D(double[][] x, Direction direction, int dimension){
        
        //Create a copy
        double[][] temp = new double[x.length][x[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                temp[i][j] = x[i][j];
            }
        }
        
        if(direction == Direction.Forward){
            //Perform fftshift in the first dimension
            if(dimension == 1){
                int move =  temp.length / 2;
                for (int i = 0; i < move; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[i][j] = temp[temp.length - move + i][j];
                    }
                }

                for (int i = move; i < x.length; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[i][j] = temp[i - move][j];
                    }
                }

            }

            //Perform fftshift in the second dimension
            if (dimension == 2){
                for (int i = 0; i < x.length; i++) {
                    FFTShift1D(x[i], MathFourierTransform.Direction.Forward);
                }
            }
        }
        else{
            if(dimension == 1){
                int move =  temp.length / 2;
                for (int i = 0; i < x.length - move; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[i][j] = temp[move+i][j];
                    }
                }
                for (int i = 0; i < move; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[x.length-move+i][j] = temp[i][j];
                    }
                }
                
            }
            if(dimension == 2){
                for (int i = 0; i < x.length; i++) {
                    FFTShift1D(x[i], MathFourierTransform.Direction.Backward);
                }
            }
        }
    }
    
    /**
     * Shift zero-frequency component to center of spectrum.
     * @param x Data.
     * @param direction Direction.
     */
    public static <E> void FFTShift2D(E[][] x, Direction direction){
        FFTShift2D(x, direction, 1);
        FFTShift2D(x, direction, 2);
    }
    
    /**
     * Shift zero-frequency component to center of spectrum.
     * @param x Data.
     * @param direction Direction.
     * @param dimension Dimension.
     */
    public static <E> void FFTShift2D(E[][] x, Direction direction, int dimension){
        
        //Create a copy
        E[][] temp = (E[][])new Object[x.length][x[0].length];
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < x[0].length; j++) {
                temp[i][j] = x[i][j];
            }
        }
        
        if(direction == Direction.Forward){
            //Perform fftshift in the first dimension
            if(dimension == 1){
                int move =  temp.length / 2;
                for (int i = 0; i < move; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[i][j] = temp[temp.length - move + i][j];
                    }
                }

                for (int i = move; i < x.length; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[i][j] = temp[i - move][j];
                    }
                }

            }

            //Perform fftshift in the second dimension
            if (dimension == 2){
                for (int i = 0; i < x.length; i++) {
                    FFTShift1D(x[i], MathFourierTransform.Direction.Forward);
                }
            }
        }
        else{
            if(dimension == 1){
                int move =  temp.length / 2;
                for (int i = 0; i < x.length - move; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[i][j] = temp[move+i][j];
                    }
                }
                for (int i = 0; i < move; i++) {
                    for (int j = 0; j < x[0].length; j++) {
                        x[x.length-move+i][j] = temp[i][j];
                    }
                }
                
            }
            if(dimension == 2){
                for (int i = 0; i < x.length; i++) {
                    FFTShift1D(x[i], MathFourierTransform.Direction.Backward);
                }
            }
        }
    }
}