/**
 * 
 * A simple globalLog that can be used to record a trace of 
 * an instrumented program.
 * 
 * The output can be used for grading and debugging.
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

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 
 * A simple globalLog that can be used to record a trace of 
 * an instrumented program.
 * 
 * The output can be used for grading and debugging.
 *
 */
public class RuntimeLog {

	private StringBuffer sb;

	public static RuntimeLog globalLog;
	public static ArrayList<BufferedImage> globalImageLog;
	
	public static void initLog() {
		globalLog = new RuntimeLog();
		globalLog.sb = new StringBuffer();
		globalImageLog = new ArrayList<BufferedImage>();
	}
	
	public static void globalLogAddImage(BufferedImage image) {
		if (globalImageLog != null) globalImageLog.add(image);
	}
	
	public static void addImage(BufferedImage image) {
		globalImageLog.add(image);
	}
	
	public static void globalLogAddEntry(String entry){
		if (globalLog != null) globalLog.addEntry(entry);
	}
	
	private void addEntry(String entry) {
		sb.append(entry);
	}

	public static String getGlobalString() {
		return (globalLog != null) ? globalLog.toString() : "";
	}

	@Override
	public String toString() {
		return sb.toString();
	}

	public static void resetLogToNull() {
		globalLog = null;
		globalImageLog = null;
	}

}
