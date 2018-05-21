package ar.com.tuchorc.exception;

public enum ErrorCode {

	// Service errors
	OTHER(1000),
	INVALID_PARAMS(1001),
	NO_DATA_FOUND(1002),
	INVALID_ENTITY(1003);

	private int errorCode;

	private ErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}