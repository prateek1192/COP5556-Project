/**
 * 
 * Class contains methods to manipulate and display images. It is part of the runtime support
 * for our language.
 * 
 * This code is used in the class project in COP5556 Programming Language Principles 
 * at the University of Florida, Spring 2018.
 * 
 * This software is solely for the educational benefit of students 
 * enrolled in the course during the Spring 2018 semester.  
 * 
 * This software, and any software derived from it,  may not be shared with others or posted to public web sites,
 * either during the course or afterwards.
 * 
 *  @Beverly A. Sanders, 2018
 */

package cop5556sp18;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class RuntimeImageSupport {

	public final static String className = "cop5556sp18/RuntimeImageSupport";
	public static final String StringDesc = "Ljava/lang/String;";
	public final static String IntegerDesc = "Ljava/lang/Integer;";
	public static final String ImageDesc = "Ljava/awt/image/BufferedImage;";
	public static String JFrameDesc = "Ljavax/swing/JFrame;";

	public static final String readFromFileSig = "(" + StringDesc + ")"
			+ ImageDesc;
	static BufferedImage readFromFile(String filename) {
		File f = new File(filename);
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage() + " " + filename, e);
		}
		return bi;
	}

	public static final String makeImageSig = "(II)" + ImageDesc;
	public static BufferedImage makeImage(int maxX, int maxY) {
		return new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_ARGB);
	}

	public static final String deepCopySig = "(" + ImageDesc + ")" + ImageDesc;
	public static BufferedImage deepCopy(BufferedImage image) {
		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image
				.copyData(image.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}

	public static BufferedImage resize(BufferedImage before, int maxX,
			int maxY) {
		int w = before.getWidth();
		int h = before.getHeight();
		AffineTransform at = new AffineTransform();
		at.scale(((float) maxX) / w, ((float) maxY) / h);
		AffineTransformOp scaleOp = new AffineTransformOp(at,
				AffineTransformOp.TYPE_BILINEAR);
		BufferedImage after = null;
		after = scaleOp.filter(before, after);
		return after;
	}

	public static final String readImageSig = "(" + StringDesc + IntegerDesc
			+ IntegerDesc + ")" + ImageDesc;
	/**
	 * Reads the image from the indicated URL or filename. I If the given source
	 * is not a valid URL, it assumes it is a file.
	 * 
	 * If X and Y are not null, the image is resized to this width and height.
	 * If they are null, the image stays its original size.
	 * 
	 * @param source
	 *            String with source or filename on local filesystem.
	 * @param X
	 *            Desired width of image, or null
	 * @param Y
	 *            Desired height of image, or null
	 * @return BufferedImage representing the indicated image.
	 */
	public static BufferedImage readImage(String source, Integer X, Integer Y) {
		BufferedImage image;
		try {
			URL url = new URL(source);
			image = readFromURL(url);
		} catch (MalformedURLException e) {// wasn't a URL, maybe it is a file
			image = readFromFile(source);
		}
		if (X != null) {
			return resize(image, X, Y);
		}
		return image;
	}
	//
	//
	//
	//// public static BufferedImage readFromFileAndResize(String filename, int
	// X, int Y) {
	//// return resize(readFromFile(filename), X, Y);
	//// }
	//
	public static final String writeSig = "(" + ImageDesc + StringDesc + ")V";
	/**
	 * Writes the given image to a file on the local system indicated by the
	 * given filename.
	 * 
	 * @param image
	 * @param filename
	 */
	public static void write(BufferedImage image, String filename) {
		File f = new File(filename);
		try {
			System.out.println("writing image to file " + filename
					+ "(in File.toString) " + f);
			ImageIO.write(image, "jpeg", f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public final static String readFromURLSig = "(Ljava/net/URL;)" + ImageDesc;
	/**
	 * Read and returns the image at the given URL
	 * 
	 * Throws RuntimeException if this fails
	 * 
	 * @param url
	 * @return BufferedImage representing the indicated image
	 */
	static BufferedImage readFromURL(URL url) {
		try {
			System.out.println("reading image from url " + url);
			return ImageIO.read(url);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public final static String getPixelSig = "(" + ImageDesc + "II)I";
	/**
	 * Returns the indicated pixel with the alpha component masked to 0. If the
	 * given x and y is out of bounds, return 0.
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @return
	 */
	public static int getPixel(BufferedImage image, int x, int y) {
		int pixel = (x < 0 || x >= image.getWidth() || y < 0
				|| y >= image.getHeight()) ? 0 : image.getRGB(x, y);
		return pixel;
	}

	public final static String setPixelSig = "(I" + ImageDesc + "II)V";
	/**
	 * Inserts the given pixel into the image at the indicated location. If x or
	 * y are out of bounds, do nothing.
	 * 
	 * @param argb
	 * @param image
	 * @param x
	 * @param y
	 */
	public static void setPixel(int rgb, BufferedImage image, int x, int y) {
		// TODO is this the right way to handle out of bounds pixels? Probably
		// to allow easier working with polar?
		if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
			System.out.println("pixel out of bounds");
			return;
		}
		image.setRGB(x, y, rgb);
	}

	public static String updatePixelColorSig = "(I" + ImageDesc + "III)V";
	public final static void updatePixelColor(int sampleVal,
			BufferedImage image, int x, int y, int color) {
		if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) {
			System.out.println("pixel out of bounds");
			return;
		}
		int pixel = image.getRGB(x, y);
		int newPixelVal = RuntimePixelOps.setSample(sampleVal, pixel, color);
		image.setRGB(x, y, newPixelVal);
	}

	public final static String getWidthSig = "(" + ImageDesc + ")I";
	/**
	 * Returns the width of the given image.
	 * 
	 * @param image
	 * @return
	 */
	public static int getWidth(BufferedImage image) {
		return image.getWidth();
	}

	public final static String getHeightSig = "(" + ImageDesc + ")I";

	/**
	 * Returns the height of the given image.
	 * 
	 * @param image
	 * @return
	 */
	public static int getHeight(BufferedImage image) {
		return image.getHeight();
	}

	public static String makeFrameSig = "(" + ImageDesc + ")" + JFrameDesc;
	/**
	 * Creates and shows a JFrame displaying the given image.
	 * 
	 * @param image
	 *            The image to display
	 * @return The new JFrame
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 */
	public static final JFrame makeFrame(BufferedImage image)
			throws InvocationTargetException, InterruptedException {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(image.getWidth(), image.getHeight());
		JLabel label = new JLabel(new ImageIcon(image));
		frame.add(label);
		frame.pack();
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				frame.setVisible(true);
			}
		});
		return frame;
	}

}
