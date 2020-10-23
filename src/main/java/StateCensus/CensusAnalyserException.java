package StateCensus;

public class CensusAnalyserException extends Exception {
	

	enum ExceptionType {
		CENSUS_FILE_PROBLEM, INCORRECT_FILE_TYPE,UNABLE_TO_PARSE;
	}

	ExceptionType type;

	public CensusAnalyserException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
