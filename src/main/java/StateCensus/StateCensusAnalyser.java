package StateCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.cg.csvbuilder.CSVBuilderFactory;
import com.cg.csvbuilder.CSVException;
import com.cg.csvbuilder.ICSVBuilder;
import com.cg.csvbuilder.OpenCSVBuilder;
import com.google.gson.Gson;
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

		} catch (CSVException e) {
			throw new CensusAnalyserException("File data not proper", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
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
		} catch (CSVException e) {
			throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
	}

	public String getSortedCensuByState() throws CensusAnalyserException {
		if (csvCensusList == null || csvCensusList.size() == 0)

		{
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
		Comparator<IndiaCensusCSV> censusComparator = Comparator.comparing(census -> census.state);

		this.sort(censusComparator);
		String sortedStateCensus = new Gson().toJson(csvCensusList);
		return sortedStateCensus;
	}
	
	public String getStatePopulationWiseSortedCensusData() throws CensusAnalyserException {
		if(csvCensusList == null || csvCensusList.size() == 0) {
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
		Collections.sort(csvCensusList, Comparator.comparing(census -> census.getPopulationData()));
		return new Gson().toJson(csvCensusList);
	}
	
	
	
	public String getStatePopulationDensityWiseSortedCensusData() throws CensusAnalyserException {
		if(csvCensusList == null || csvCensusList.size() == 0) {
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
		Collections.sort(csvCensusList, Comparator.comparing(census -> census.getPopulationDensity()));
		Collections.reverse(csvCensusList);
		return new Gson().toJson(csvCensusList);
	}
	
	public String getStateAreaWiseSortedCensusData() throws CensusAnalyserException {
		if(csvCensusList == null || csvCensusList.size() == 0) {
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
		Collections.sort(csvCensusList, Comparator.comparing(census -> census.getArea()));
		Collections.reverse(csvCensusList);
		return new Gson().toJson(csvCensusList);
	}


	private void sort(Comparator<IndiaCensusCSV> censusComparator) {
		for (int i = 0; i < csvCensusList.size() - 1; i++) {
			for (int j = 0; j < csvCensusList.size() - 1 - i; j++) {
				IndiaCensusCSV census1 = csvCensusList.get(j);
				IndiaCensusCSV census2 = csvCensusList.get(j + 1);
				if (censusComparator.compare(census1, census2) > 0) {
					csvCensusList.set(j, census2);
					csvCensusList.set(j + 1, census1);
				}
			}
		}
	}
	
	public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
		if(csvStateList == null || csvStateList.size() == 0) {
			throw new CensusAnalyserException("File is empty", CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
		Collections.sort(csvStateList, Comparator.comparing(code -> code.stateCode));
		return new Gson().toJson(csvStateList);
	}

	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable = () -> iterator;
		int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}
	
	
}
