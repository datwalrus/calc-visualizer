package calculations;

/* Data structure.
 * Converts strings to floats and floats to strings efficiently.
 * Almost all strings should be initialized as StringFloats. 
 * This avoids the need to create float objects for calculations.
 */

public class StringDouble {
	// Fields
	String str;
	double dbl;
	boolean isBoth = true;
	
	// Primary constructor.
	/* @args String s Input is a string that is fed to the Float parseFloat() method.
	 * Depending on the try statement, isBoth is set to true or false to ensure no passed exceptions.
	 */
	StringDouble(String s) {
		str = s;
		try {
			dbl = Double.parseDouble(s);
		} catch (NumberFormatException e) {
			isBoth = false;
		}
	}
	StringDouble(double d) {
		dbl = d;
		str = String.valueOf(d);
	}
	
	// Getters
	double getDouble() {
		return dbl;
	}
	String getString() {
		return str;
	}
	boolean getBool() {
		return isBoth;
	}
	
}
