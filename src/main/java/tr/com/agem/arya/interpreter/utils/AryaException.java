package tr.com.agem.arya.interpreter.utils;

public class AryaException extends Exception {
	
	private static final long serialVersionUID = 5910560190516861831L;

	public AryaException() {
    }

    public AryaException(String message) {
        super(message);
    }

    public AryaException(Throwable cause) {
        super(cause);
    }

    public AryaException(String message, Throwable cause) {
        super(message, cause);
    }

}