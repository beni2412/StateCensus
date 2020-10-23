package StateCensus;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StateCensusAnalyserTest {
	private StateCensusAnalyser stateCensusAnalyser;
	public static final String STATE_CENSUS_FILE_PATH = "./IndiaStateCensusData.csv";
	private static final String STATE_CENSUS_WRONG_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";

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
}
