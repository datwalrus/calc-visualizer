package calculations;

/* Data structure.
 * Handles variables and their order with a singular object.
 * 
 */

public class VarInfo {
	// Fields
	char var;
	StringFloat order;
	
	// Constructor
	VarInfo(char v, String o) {
		var = v;
		order = new StringFloat(o);
	}
	char getVar() {
		return var;
	}
	StringFloat getOrder() {
		return order;
	}
}
