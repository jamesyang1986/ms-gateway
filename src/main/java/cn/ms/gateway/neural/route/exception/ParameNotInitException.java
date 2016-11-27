package cn.ms.gateway.neural.route.exception;

public class ParameNotInitException extends RuntimeException {

	private static final long serialVersionUID = 7815426752583648734L;

	public ParameNotInitException() {
		super();
	}

	public ParameNotInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameNotInitException(String message) {
		super(message);
	}

	public ParameNotInitException(Throwable cause) {
		super(cause);
	}

}
