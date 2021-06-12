package com.tomtom.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tomtom.ecommerce.utils.StringUtil;

@RestControllerAdvice
public class EcommerceExceptionController {

	@ExceptionHandler(EcommerceException.class)
	public ResponseEntity<EcommerceError> handle(EcommerceException ee) {
		EcommerceError error = new EcommerceError();
		error.setMessage(ee.getMessage());
		if (StringUtil.isBlank(ee.getCode()))
			error.setCode("500");
		else
			error.setCode(ee.getCode());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// can handle many other excceptions here

}
