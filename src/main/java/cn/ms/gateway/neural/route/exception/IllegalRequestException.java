package cn.ms.gateway.neural.route.exception;

public class IllegalRequestException extends RuntimeException {

	private static final long serialVersionUID = 7815426752583648734L;

	public IllegalRequestException() {
		super();
	}

	public IllegalRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalRequestException(String message) {
		super(message);
	}

	public IllegalRequestException(Throwable cause) {
		super(cause);
	}

}
