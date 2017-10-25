package trash;

// from com.dip.library;

public class Fourier {

	public static double[] discreteFT(double[]fdata, int N, boolean fwd){
		double X[] = new double[2*N];
		double omega;
		int k, ki, kr, n;
		
		if (fwd){
			omega = 2.0*Math.PI/N;
		} else {
			omega = -2.0*Math.PI/N;
		}
		
		for(k=0; k<N; k++) {
			kr = 2*k;
			ki = 2*k+1;
			X[kr] = 0.0;
			X[ki] = 0.0;
			for(n=0; n<N; ++n) {
				X[kr] += fdata[2*n]*Math.cos(omega*n*k) + fdata[2*n+1]*Math.sin(omega*n*k);
				X[ki] += -fdata[2*n]*Math.sin(omega*n*k) + fdata[2*n+1]*Math.cos(omega*n*k);
			}
		}
		
		if (fwd) {
			for(k=0; k<N; ++k) {
				X[2*k] /= N;
				X[2*k + 1] /= N;
			}
		}
		
		return X;
	}
	
}
