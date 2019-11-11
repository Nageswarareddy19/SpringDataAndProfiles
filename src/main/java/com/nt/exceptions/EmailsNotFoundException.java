package com.nt.exceptions;

public class EmailsNotFoundException extends RuntimeException {

	public EmailsNotFoundException(String msg) {
		super(msg);
	}
}
