package com.tomtom.ecommerce.exceptions;

import org.springframework.http.HttpStatus;

public class EcommerceException extends RuntimeException {

	private static final long serialVersionUID = 5861310537366287163L;

	String code;

	public EcommerceException() {
		super();
	}

	public EcommerceException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public EcommerceException(final String message) {
		super(message);
	}

	public EcommerceException(final String message, HttpStatus statusCode) {
		super(message);
		code = String.valueOf(statusCode.value());
	}

	public EcommerceException(final Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public String getCode() {
		return code;
	}

}
