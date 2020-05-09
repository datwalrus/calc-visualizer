package calculations;

/* Data structure.
 * Handles variables, coefficients, and their order with a singular object.
 * 
 */

public class VarInfo {
	// Fields
	StringDouble coeff;
	StringDouble var;
	StringDouble order;
	
	// Constructor
	VarInfo(String c, String v, String o) {
		coeff = new StringDouble(c);
		var = new StringDouble(v);
		order = new StringDouble(o);
	}
	StringDouble getCoeff() {
		return coeff;
	}
	StringDouble getVar() {
		return var;
	}
	StringDouble getOrder() {
		return order;
	}
}
