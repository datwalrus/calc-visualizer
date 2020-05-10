package calculations;

/* Data structure.
 * This data structure is used to contain a char array, and the location of the operand within it.
 * Used in method: splitter
 */

public class SplitInfo {
	// Fields
	String exp;
	int ind;
	VarInfo[] vars;
	int numVars;
	
	// For splitting expressions at operands.
	// This is separate in order to utilize the order of operations.
	SplitInfo(String s, int i){
		exp = s;
		ind = i;
	}
	// For splitting expressions at variables.
	SplitInfo(String s, int i, VarInfo[] v) {
		exp = s;
		ind = i;
		vars = v;
	}
	
	// Getters
	String getExpression() {
		return exp;
	}
	int getIndex() {
		return ind;
	}
	VarInfo[] getVars() {
		return vars;
	}
}
