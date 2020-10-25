package StateCensus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StateCensusAnalyserTest {
	private StateCensusAnalyser stateCensusAnalyser;
	public static final String STATE_CENSUS_FILE_PATH = "./IndiaStateCensusData.csv";
	private static final String STATE_CENSUS_WRONG_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
	private static final String CENSUS_WRONG_TYPE_FILE_PATH = "./IndiaStateCensusData.txt";
	private static final String CENSUS_WRONGHEADER_FILE_PATH = "./IndiaStateCode.csv";
	private static final String CENSUS_WRONG_DELIMITER_FILE_PATH = "./CensusDelimiter.csv";

	@Before
	public void initialize() {
		stateCensusAnalyser = new StateCensusAnalyser();

	}

	@Test
	public void givenStateCensusCSVFile_ShouldReturnNumberOfRecords() throws CSVException {
		int numOfEntries = stateCensusAnalyser.loadIndiaCensusData(STATE_CENSUS_FILE_PATH);
		Assert.assertEquals(29, numOfEntries);
	}

	@Test
	public void givenStateCensus_WrongCSVFile_ShouldThrowException() {
		try {
			stateCensusAnalyser.loadIndiaCensusData(STATE_CENSUS_WRONG_FILE_PATH);
		} catch (CSVException e) {
			Assert.assertEquals(CSVException.ExceptionType.UNABLE_TO_PARSE, e.type);
		}
	}
	
	@Test
	public void givenStateCensus_WrongFileType_ShouldThrowException()  {
	   try {
		stateCensusAnalyser.loadIndiaCensusData(CENSUS_WRONG_TYPE_FILE_PATH);
	   }catch(CSVException e) {
		   Assert.assertEquals(CSVException.ExceptionType.UNABLE_TO_PARSE, e.type);
	   }
	}
	
	@Test
	public void givenStateCensus_WrongHeader_ShouldThrowException()  {
	   try {
		stateCensusAnalyser.loadIndiaCensusData(CENSUS_WRONGHEADER_FILE_PATH);
	   }catch(CSVException e) {
		   Assert.assertEquals(CSVException.ExceptionType.UNABLE_TO_PARSE, e.type);
	   }
	}
	
	 @Test
		public void givenStateCensus_WrongDelimiter_ShouldThrowException()  {
		   try {
			stateCensusAnalyser.loadIndiaCensusData(CENSUS_WRONG_DELIMITER_FILE_PATH);
		   }catch(CSVException e) {
			   Assert.assertEquals(CSVException.ExceptionType.UNABLE_TO_PARSE, e.type);
		   }
		}
	
}
