package calculations;

/* Data structure.
 * Converts strings to floats and floats to strings efficiently.
 * Almost all strings should be initialized as StringFloats. 
 * This avoids the need to create float objects for calculations.
 */

public class StringFloat {
	// Fields
	String str;
	float flt;
	boolean isBoth = true;
	
	// Primary constructor.
	/* @args String s Input is a string that is fed to the Float parseFloat() method.
	 * Depending on the try statement, isBoth is set to true or false to ensure no passed exceptions.
	 */
	StringFloat(String s) {
		str = s;
		try {
			flt = Float.parseFloat(s);
		} catch (NumberFormatException e) {
			isBoth = false;
		}
	}
	StringFloat(float f) {
		flt = f;
		str = String.valueOf(f);
	}
	
	// Getters
	float getFloat() {
		return flt;
	}
	String getString() {
		return str;
	}
	boolean getBool() {
		return isBoth;
	}
	
}
