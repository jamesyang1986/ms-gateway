package cn.ms.gateway.neural.route.exception;

public class NotFoundRouteRuleException extends RuntimeException {

	private static final long serialVersionUID = 7815426752583648734L;

	public NotFoundRouteRuleException() {
		super();
	}

	public NotFoundRouteRuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundRouteRuleException(String message) {
		super(message);
	}

	public NotFoundRouteRuleException(Throwable cause) {
		super(cause);
	}

}
