package com.coop.org.exception;

public class RetailMaterialManagementException extends RetailException {

	public RetailMaterialManagementException(){}
	public RetailMaterialManagementException(String message) {
		this.message = message;
	}
	public RetailMaterialManagementException(String message,String errorCode) {
		this.message = message;
		this.errorCode = errorCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}