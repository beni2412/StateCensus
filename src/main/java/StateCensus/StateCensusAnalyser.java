package StateCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.cg.csvbuilder.CSVBuilderFactory;
import com.cg.csvbuilder.CSVException;
import com.cg.csvbuilder.ICSVBuilder;
import com.cg.csvbuilder.OpenCSVBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {
	
	List<IndiaCensusCSV> csvCensusList;
	List<IndiaStateCSV> csvStateList;

	public StateCensusAnalyser() {
		this.csvCensusList = new ArrayList<IndiaCensusCSV>();
		this.csvStateList = new ArrayList<IndiaStateCSV>();

	}

	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Not CSV file",
					CensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE);
		}
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvCensusList = csvBuilder.getCSVFileIList(reader, IndiaCensusCSV.class);
			return csvCensusList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException("File not found",
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException("File data not proper",
					CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);

		}catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
	}

	public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Not CSV file",
					CensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE);
		}
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
			csvStateList = csvBuilder.getCSVFileIList(reader, IndiaStateCSV.class);
			return csvStateList.size();
		} catch (IOException e) {
			throw new CensusAnalyserException("File not found",
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException("File data not proper",
					CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}

	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}
}
