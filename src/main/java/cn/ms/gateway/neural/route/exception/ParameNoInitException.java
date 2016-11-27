package cn.ms.gateway.neural.route.exception;

public class ParameNoInitException extends RuntimeException {

	private static final long serialVersionUID = 7815426752583648734L;

	public ParameNoInitException() {
		super();
	}

	public ParameNoInitException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParameNoInitException(String message) {
		super(message);
	}

	public ParameNoInitException(Throwable cause) {
		super(cause);
	}

}
