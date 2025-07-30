package com.cdac.custom_exception;

public class InvalidInputException extends RuntimeException {
	public InvalidInputException(String mesg) {
		super(mesg);
	}
}
