package calculations;

import java.util.Arrays;

/* @Author datwalrus
 * @Date 05/07/2020
 * 
 * Use { (, ), +, -, /, *, ^ } as delimiters.
 * Maintain string structure, to ensure proper treatment of order
 * 
 * 
 */

/* 
 * This data structure contains the variable and order.
 */
class varInfo {
	char var;
	int order;
	
	// Constructor
	varInfo(char v, int o) {
		var = v;
		order = o;
	}
}


/* 
 * This data structure is used to contain a char array, and the location of the operand within it.
 * Used in method: splitter
 */
class splitInfo {
	char[] exp;
	int ind;
	varInfo var1;
	varInfo var2;
	int numVars;
	// For splitting expressions at operands.
	// This is separate in order to utilize the order of operations.
	splitInfo(char[] c, int i){
		exp = c;
		ind = i;
	}
	// For splitting expressions at variables.
	splitInfo(char[] c, int i, varInfo v1, varInfo v2, int n) {
		exp = c;
		ind = i;
		var1 = v1;
		var2 = v2;
		numVars = n;
	}
	char[] getExpression() {
		return exp;
	}
	int getIndex() {
		return ind;
	}
	varInfo getVar1() {
		return var1;
	}
	varInfo getVar2() {
		return var2;
	}
	int getNumVars() {
		return numVars;
	}
}

/*
 * This class contains an assortment of methods used to simplify and solve algebraic expressions.
 * Specifically focusing on algebraic expressions, not calculus and differential equations.
 */
public class algebraic {
	public String main() {
		return "a";
	}
	// Used to match parentheses, if this string is found in 
	// another string, it will be split after ')'.
	protected char[] delims = {'+','-','/','*'};

	// Checks for parentheses.
	public boolean containsParen(String str) {
		if ((str.contains("("))&&(str.contains(")"))) { return true; }
		return false;
	}
	// Removes parentheses and simplify segment, will handle inner parentheses first automatically.
	public String parenthesesSimplify(String str) {
		char[] iter = str.toCharArray();
		int startParen = 0;
		int endParen = 0;
		
		boolean operationStart = false; // false to add multiplication symbol, true to do nothing
		boolean operationEnd = false;   // false to add multiplication symbol, true to do nothing
		// Locate parenthetical statement, set aside to solve.
		for (int i = 0; i < iter.length; i++) {
			char ch = iter[i];
			if ( ch == '(') { 
				startParen = i; 
				if (i > 0) { 
					if ((iter[i-1] == '/')||(iter[i-1] == '+')||(iter[i-1] == '-')) { operationStart = true; }
					else { operationStart = false; }
				}
				else { operationStart = false; }
				continue;
			}
			if ( ch == ')') { 
				endParen = i; 
				if (i < iter.length) {
					if ((iter[i+1] == '/')||(iter[i+1] == '+')||(iter[i+1] == '-')) { operationEnd = true; }
					else { operationEnd = false; }
				}
				else { operationEnd = false; }
				break; 
			}
		}
		// Simplify parenthetical statement.
		String toSimplify = str.substring(startParen + 1, endParen - 1);
		String simplified = simplify(toSimplify);
		StringBuilder innerParen = new StringBuilder();

		// if-else statements to properly order simplified parenthetical statement.
		if ((startParen == 0) && (endParen != iter.length)) {
			innerParen.append(simplified);
			if (operationEnd == false) { innerParen.append("*"); }
			innerParen.append(str.substring(endParen + 1));
		}
		else if ((startParen != 0) && (endParen !=iter.length)) {
			innerParen.append(str.substring(0, startParen - 1));
			if (operationStart == false) { innerParen.append("*"); }
			innerParen.append(simplified);
			if (operationEnd == false) { innerParen.append("*"); }
			innerParen.append(str.substring(endParen + 1));
		}
		else if ((endParen == iter.length) && (startParen != 0)) {
			innerParen.append(str.substring(0, startParen - 1));
			if (operationStart == false) { innerParen.append("*"); }
			innerParen.append(simplified);
		}
		return innerParen.toString();
	}
	// Split string at delimiter requested
	public splitInfo splitter(String str, char ch) {
		char[] start = str.toCharArray();
		int index1 = 0;
		int index2 = 0;
		int index3 = 0;
		for (int i = 0; i < start.length; i++) {
			if (start[i] == ch) {
				index2 = i;
				break;
			}
		}
		for (int i = 0; i < index2; i++) {
			for (int j = 0; j < 4; j++) {
				if (start[i] == delims[j]) {
					index1 = i;
				}
			}
		}
		for (int i = index2+1; i < start.length; i++) {
			for (int j = 0; j < 4; j++) {
				if (start[i] == delims[j]) {
					index3 = i;
					break;
				}
			}
		}
		char[] result = str.substring((index1 + 1), (index3 - 1)).toCharArray();
		splitInfo end = new splitInfo(result, (index2 - (index1 + 1)));
		return end;
	}
	// Parse string, returning value for some calculation.
	public String simplify(String str) {
		String begin = str;
		boolean exState = true;
		// Simplify exponents as much as possible.
		do {
			if (str.contains("^")) {
				//for loop to calculate out first exponent
				splitInfo info = splitter(str, '^');
				char[] operand = info.getExpression();
				int pos = info.getIndex();
				
				// If digits surround exponent, solve them.
				if ((Character.isDigit(operand[pos-1])) && (Character.isDigit(operand[pos+1]))) {
					String ans = expon(operand, pos);
				}
				// If one or both are variables, exit loop and continue with simplification.
				else {
					exState = false;
				}
			}
		} while ((str.contains("^")) && (exState == true));
		
		boolean multState = true;
		// Simplify multiplication/division expressions
		do {
			if ((str.contains("*")) || (str.contains("/"))) {
				
			}
		} while ((str.contains("*") || str.contains("/")) && (multState == true));
		return "";
	}

	// Handles float exponentiation by parsing floats from string elements then recombining float into string.
	public String expon(char[] str, int index) {
		int len = str.length;

		String p1 = Arrays.copyOfRange(str, 0, index - 1).toString();
		float digit_1 = Float.parseFloat(p1);

		String p2 = Arrays.copyOfRange(str, index + 1, len - 1).toString();
		float digit_2 = Float.parseFloat(p2);

		float ans = (float) Math.pow(digit_1, digit_2);
		String result = String.valueOf(ans);
		return result;
	}
	// Handles float multiplication by parsing float from string elements then recombining float into string.
	public String multiplication(char[] str, int index, char var1, char var2, int numVars) {
		return "";
	}
	public float division(String str, int index) {
		return 0;
	}
	public float addition(String str, int index) {
		return 0;
	}
	public float subtraction(String str, int index) {
		return 0;
	}
}
