package cn.ms.gateway.neural.route.exception;

public class NoFoundProviderException extends RuntimeException {

	private static final long serialVersionUID = 7815426752583648734L;

	public NoFoundProviderException() {
		super();
	}

	public NoFoundProviderException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoFoundProviderException(String message) {
		super(message);
	}

	public NoFoundProviderException(Throwable cause) {
		super(cause);
	}

}
