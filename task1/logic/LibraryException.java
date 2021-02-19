package by.epam.training.task1.logic;

public class LibraryException extends Exception {

	private static final long serialVersionUID = 5043981760222615301L;

	public LibraryException() {
		super();
	}

	public LibraryException(String message, Throwable cause) {
		super(message, cause);
	}

	public LibraryException(String message) {
		super(message);
	}

	public LibraryException(Throwable cause) {
		super(cause);
	}

}
