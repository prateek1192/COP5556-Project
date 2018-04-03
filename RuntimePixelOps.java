/**
 * 
 * Useful functions for dealing with pixels.  
 * 
 * An image is an array of pixels.  Each pixel is an int containing four color components: alpha (transparency), red, green, and blue.
 *
 *
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


public class RuntimePixelOps
{
	
	public static final String className = "cop5556sp18/RuntimePixelOps";

//values for selecting individual color intensities in packed pixels
public static final int SELECT_RED = 0x00ff0000,
                        SELECT_GRN = 0x0000ff00,
                        SELECT_BLU = 0x000000ff,
                        SELECT_ALPHA = 0xff000000;

//bits to shift to convert to and from int
public static final int SHIFT_ALPHA  = 24,
						SHIFT_RED = 16, 
                        SHIFT_GRN = 8,
                        SHIFT_BLU = 0;

//values for zeroing individual colors in packed pixels
public static final int ZERO_RED = 0xff00ffff,
                        ZERO_GRN = 0xffff00ff,
                        ZERO_BLU = 0xffffff00,
						ZERO_ALPHA = 0x00ffffff;




//above values in arrays indexed by color for convenience
public static final int[] BITMASKS = {SELECT_ALPHA, SELECT_RED, SELECT_GRN, SELECT_BLU, },
                          ZERO = {ZERO_ALPHA, ZERO_RED, ZERO_GRN, ZERO_BLU,  },
                          BITOFFSETS = {SHIFT_ALPHA, SHIFT_RED, SHIFT_GRN, SHIFT_BLU, };

	public static final String JVMClassName = "cop5555/runtime/Pixel";


	public static final int ALPHA = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
    /* extract a sample from a pixel, 
     * color code should be RED,GRN, or BLU from interface ImageConstants*/
	public static final String getSampleSig = "(II)I";
	public static int getSample(int pixel, int color) {
		assert ALPHA <= color && color <= BLUE : "illegal value for color in getSample";
		int s = (pixel & BITMASKS[color]) >>> BITOFFSETS[color];
		return s;
	}
	
	//updates indicated color component.  Other components remain the same.
	//value is truncated to be between 0 and 255.
	public static final String setSampleSig = "(III)I";
	public static int setSample(int value, int pixel, int color) {
		assert ALPHA <= color && color <= BLUE : "illegal value for color in getSample";
		int p = pixel & ZERO[color];
		int val = truncate(value);
		int np = p | (val << BITOFFSETS[color]);
		return np;
	}

	/*extract red sample*/
	public static final String getRedSig = "(I)I";
	public static int getRed(int pixel) {
		return (pixel & SELECT_RED) >>> SHIFT_RED;
	}

	/*	extract green sample*/
	public static final String getGreenSig = "(I)I";
	public static int getGreen(int pixel) {
		return (pixel & SELECT_GRN) >>> SHIFT_GRN;
	}

	// extract blue sample
	public static final String getBlueSig = "(I)I";
	public static int getBlue(int pixel) {
		return (pixel & SELECT_BLU) >>> SHIFT_BLU;
	}
	
	// extract alpha sample
	public static final String getAlphaSig = "(I)I";
	public static int getAlpha(int pixel) {
		return (pixel & SELECT_ALPHA) >>> SHIFT_ALPHA;
	}

	
	/*create a pixel with the given color values.
       Values less than 0 or greater than 255 are truncated.
	 */
	public static final String makePixelSig = "(IIII)I";
	public static int makePixel(int alphaVal, int redVal, int grnVal, int bluVal) {
			int pixel =  ((truncate(alphaVal) << SHIFT_ALPHA) | (truncate(redVal) << SHIFT_RED)
					| (truncate(grnVal) << SHIFT_GRN) | (truncate(bluVal) << SHIFT_BLU));

			return pixel;
	}

/*	negates the pixel*/
	public static final String notSig = "(I)I";

	public static int not(int val) {
		return ~val | SELECT_ALPHA;
	}

/*	truncates an int to value in range of [0,Z)*/
	static public int truncate(int z) {
		if (z < 0)
			return 0;
		else if (z > 255)
			return 255;
		else
			return z;
	}
	
	/* String showing pixel in Hex format, alpha, red, grn, and blu
	 * components are each two digits.
	 */
	public static String toString(int val)
	{  return Integer.toHexString(val); }
}