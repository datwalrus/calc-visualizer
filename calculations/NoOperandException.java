package calculations;

public class NoOperandException extends Throwable{
	Object exception;
	static final long serialVersionUID = 0;
	NoOperandException() {}
	
	NoOperandException(String message) {
		super(message);
	}
}
