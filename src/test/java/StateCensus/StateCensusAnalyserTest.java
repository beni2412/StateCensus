package StateCensus;

import org.junit.Assert;
import org.junit.Test;

public class StateCensusAnalyserTest {
	public static final String STATE_CENSUS_FILE_PATH = "./IndiaStateCensusData.csv";
	
	@Test
	public void givenStateCensusCSVFile_ShouldReturnNumberOfRecords() throws CensusAnalyserException{
		StateCensusAnalyser stateCensusAnalyser = new StateCensusAnalyser();
		int numOfEntries = stateCensusAnalyser.loadIndiaCensusData(STATE_CENSUS_FILE_PATH);
		Assert.assertEquals(29, numOfEntries);
	}
}
