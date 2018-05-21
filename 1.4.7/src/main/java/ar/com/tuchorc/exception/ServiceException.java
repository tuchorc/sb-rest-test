package ar.com.tuchorc.exception;

import java.io.Serializable;

public class ServiceException extends Exception implements Serializable {

	private String className = "N/A";
	private String methodName = "N/A";
	private ErrorCode errorCode;

	public ServiceException(ErrorCode errorCode) {
		super(errorCode.name());
		this.errorCode = errorCode;
	}

	public ServiceException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public ServiceException(String message, ErrorCode errorCode, String className, String methodName) {
		super(message);
		this.className = className;
		this.methodName = methodName;
		this.errorCode = errorCode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "ServiceException{" +
				"class ='" + className + '\'' +
				", method ='" + methodName + '\'' +
				", errorCode =" + errorCode + '\'' +
				", message =" + this.getMessage() +
				'}';
	}
}
