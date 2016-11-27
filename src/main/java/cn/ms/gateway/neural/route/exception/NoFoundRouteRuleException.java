package cn.ms.gateway.neural.route.exception;

public class NoFoundRouteRuleException extends RuntimeException {

	private static final long serialVersionUID = 7815426752583648734L;

	public NoFoundRouteRuleException() {
		super();
	}

	public NoFoundRouteRuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoFoundRouteRuleException(String message) {
		super(message);
	}

	public NoFoundRouteRuleException(Throwable cause) {
		super(cause);
	}

}
