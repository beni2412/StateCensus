package com.cg.csvbuilder;

public class CSVException extends Exception {
	enum ExceptionType {
		UNABLE_TO_PARSE
	}

	ExceptionType type;

	public CSVException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}

}
