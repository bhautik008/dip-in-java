package trash;

import javax.swing.JOptionPane;

import com.dip.filter.FourierTransform;
import com.dip.filter.NoiseProcess;
import com.dip.filter.NoiseProcess.NoiseType;
import com.dip.image.MakeImage;

import Catalano.Imaging.FastBitmap;
//import Catalano.Imaging.Filters.FourierTransform;

class Pixel {
	
	static final String IMG_PATH = "D:\\\\testimg3.jpg";
	static final String OTPT_PATH = "D:\\\\output2.jpg";
	
    static public void main(String args[]) throws Exception {
    	myFourier();
    	//catalon();
    }
    
    public static void catalon() {	
    	FastBitmap fb = new FastBitmap(IMG_PATH);
    	fb.toGrayscale();
    	JOptionPane.showMessageDialog(null, fb.toIcon(), "Image", JOptionPane.PLAIN_MESSAGE);
    	
//    	FourierTransform ft = new FourierTransform(fb);
//    	ft.Forward();
//    	fb = ft.toFastBitmap();
//    	
//    	JOptionPane.showMessageDialog(null, fb.toIcon(), "Fourier Transform", JOptionPane.PLAIN_MESSAGE);	
    }
    
    public static void myFourier() {
    	MakeImage mi = new MakeImage(IMG_PATH);
    	mi.toGrayScale();
    	
    	NoiseProcess noiseImg = new  NoiseProcess(mi);
    	noiseImg.addNoise(NoiseType.SaltAndPepper);
    	MakeImage nImage = noiseImg.toMakeImage();
    	nImage.showIcon("Noised Image");
    }
}