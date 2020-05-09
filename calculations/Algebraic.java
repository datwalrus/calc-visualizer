package calculations;

import java.util.Arrays;

/*
 * This class contains an assortment of methods used to simplify and solve algebraic expressions.
 * Specifically focusing on algebraic expressions, not calculus and differential equations.
 */
public class Algebraic {
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
	public SplitInfo splitter(String str, char ch) {
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
		String result = str.substring((index1 + 1), (index3 - 1));
		SplitInfo end = new SplitInfo(result, (index2 - (index1 + 1)));
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
				SplitInfo info = splitter(str, '^');
				char[] operand = info.getExpression().toCharArray();
				int pos = info.getIndex();
				
				// If digits surround exponent, solve them.
				if ((Character.isDigit(operand[pos-1])) && (Character.isDigit(operand[pos+1]))) {
					String ans = expon(operand, pos);
				}
				// If one or both are variables, exit loop and continue with simplification.
				// Do not create new varInfo object yet, create object after finishing exponential simplification,
				// when needed.
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
	// Deprecate and reform into a new method, multdiv that uses an if statement from a additional parameter.
	public String multiplication(SplitInfo split) {
		String exp = split.getExpression();
		int index = split.getIndex();
		VarInfo[] vars = split.getVars();
		if (vars.length == 0) {
			float n1 = Float.parseFloat(exp.substring(0,index-1));
			float n2 = Float.parseFloat(exp.substring(index+1, exp.length()));
			float fin = n1*n2;
			String result = String.valueOf(fin);
			return result;
		}
		else if (vars.length == 2) {
			char v1 = vars[0].getVar();
			String o1 = vars[0].getOrder().getString();
			char v2 = vars[1].getVar();
			String o2 = vars[1].getOrder().getString();
			if (v1 == v2) {
				boolean numeric = true;
				String order = null;
				try {
					float o3 = Float.parseFloat(o1) + Float.parseFloat(o2);
					order = String.valueOf(o3);
				} catch (NumberFormatException e) {
					numeric = false;
				}
				if (numeric) {
					String result = Character.toString(v1) + "^" + order;
					return result;
				}
				else {
					Boolean numeric2 = true;
					String result;
					try {
						float temp = Float.parseFloat(o2);
					} catch (NumberFormatException e) {
						numeric2 = false;
					}
					if (numeric2) {
						result = Character.toString(v1) + "^" + o2 + o1;
					}
					else {
						result = Character.toString(v1) + "^" + o1 + o2;
					}
					return result;
				}
			}
			else {
				if (Float.parseFloat(o1) > 1) {
					
				}
			}
		}
		return "";
	}
	public String division(SplitInfo split) {
		return "";
	}
	public String addition(SplitInfo split) {
		return "";
	}
	public String subtraction(SplitInfo split) {
		return "";
	}
}
