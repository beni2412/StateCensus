package StateCensus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StateCodeTest {
	private StateCensusAnalyser stateCensusAnalyser;
	public static final String STATE_CODE_FILE_PATH = "./IndiaStateCode.csv";
	private static final String STATE_CODE_WRONG_FILE_PATH = "./src/test/resources/IndiaStateCodeData.csv";
	private static final String CODE_WRONG_TYPE_FILE_PATH = "./StateCode.txt";
	private static final String CODE_WRONGHEADER_FILE_PATH = "./IndiaStateCensusData.csv";
	private static final String CODE_WRONG_DELIMITER_FILE_PATH = "./StateCodeDelimiter.csv";

	@Before
	public void initialize() {
		stateCensusAnalyser = new StateCensusAnalyser();

	}

	@Test
	public void givenStateCodeCSVFile_ShouldReturnNumberOfRecords() throws CensusAnalyserException {
		int numOfEntries = stateCensusAnalyser.loadIndiaStateCodeData(STATE_CODE_FILE_PATH);
		Assert.assertEquals(37, numOfEntries);
	}

	@Test
	public void givenStateCode_WrongCSVFile_ShouldThrowException() {
		try {
			stateCensusAnalyser.loadIndiaStateCodeData(STATE_CODE_WRONG_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}
	
	@Test
	public void givenStateCode_WrongFileType_ShouldThrowException()  {
	   try {
		stateCensusAnalyser.loadIndiaStateCodeData(CODE_WRONG_TYPE_FILE_PATH);
	   }catch(CensusAnalyserException e) {
		   Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, e.type);
	   }
	}
	
	@Test
	public void givenStateCode_WrongHeader_ShouldThrowException()  {
	   try {
		stateCensusAnalyser.loadIndiaStateCodeData(CODE_WRONGHEADER_FILE_PATH);
	   }catch(CensusAnalyserException e) {
		   Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
	   }
	}
	
	 @Test
		public void givenStateCode_WrongDelimiter_ShouldThrowException()  {
		   try {
			stateCensusAnalyser.loadIndiaStateCodeData(CODE_WRONG_DELIMITER_FILE_PATH);
		   }catch(CensusAnalyserException e) {
			   Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
		   }
		}
	
}
