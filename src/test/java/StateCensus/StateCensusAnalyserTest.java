package StateCensus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

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
	public void givenStateCensusCSVFile_ShouldReturnNumberOfRecords() throws CensusAnalyserException {
		int numOfEntries = stateCensusAnalyser.loadIndiaCensusData(STATE_CENSUS_FILE_PATH);
		Assert.assertEquals(29, numOfEntries);
	}

	@Test
	public void givenStateCensus_WrongCSVFile_ShouldThrowException() {
		try {
			stateCensusAnalyser.loadIndiaCensusData(STATE_CENSUS_WRONG_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
		}
	}

	@Test
	public void givenStateCensus_WrongFileType_ShouldThrowException() {
		try {
			stateCensusAnalyser.loadIndiaCensusData(CENSUS_WRONG_TYPE_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE, e.type);
		}
	}

	@Test
	public void givenStateCensus_WrongHeader_ShouldThrowException() {
		try {
			stateCensusAnalyser.loadIndiaCensusData(CENSUS_WRONGHEADER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
		}
	}

	@Test
	public void givenStateCensus_WrongDelimiter_ShouldThrowException() {
		try {
			stateCensusAnalyser.loadIndiaCensusData(CENSUS_WRONG_DELIMITER_FILE_PATH);
		} catch (CensusAnalyserException e) {
			Assert.assertEquals(CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE, e.type);
		}
	}

	@Test
	public void censusSortedOnState() throws CensusAnalyserException {
		stateCensusAnalyser.loadIndiaCensusData(STATE_CENSUS_FILE_PATH);
		String sortedCensusData = stateCensusAnalyser.getSortedCensuByState();
		IndiaCensusCSV[] censusCsv = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
		Assert.assertEquals("Andhra Pradesh", censusCsv[0].state);
	}
}
