package cn.ms.gateway.neural.route.exception;

public class NotFoundProviderException extends RuntimeException {

	private static final long serialVersionUID = 7815426752583648734L;

	public NotFoundProviderException() {
		super();
	}

	public NotFoundProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundProviderException(String message) {
		super(message);
	}

	public NotFoundProviderException(Throwable cause) {
		super(cause);
	}

}
