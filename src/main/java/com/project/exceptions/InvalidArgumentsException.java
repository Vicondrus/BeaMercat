package com.project.exceptions;

//easy to use exception, used for providing input errors

public class InvalidArgumentsException extends Exception {

	public InvalidArgumentsException(String s) {
		super(s);
	}

}
