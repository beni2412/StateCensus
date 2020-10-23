package StateCensus;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {
	public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Not CSV file",
					CensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE);
		}
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			
			Iterator<IndiaCensusCSV> censusCSVIterator = this.getCSVFileIterator(reader, IndiaCensusCSV.class);
			Iterable<IndiaCensusCSV> csvIterable = () -> censusCSVIterator;
			int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
			return numOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("File not found",
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		} catch (RuntimeException e) {
			throw new CensusAnalyserException("File data not proper",
					CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);

		}
	}

	public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
		if (!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Not CSV file",
					CensusAnalyserException.ExceptionType.INCORRECT_FILE_TYPE);
		}
		try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			Iterator<IndiaStateCSV> censusCSVIterator = getCSVFileIterator(reader, IndiaStateCSV.class);
			Iterable<IndiaStateCSV> csvIterable = () -> censusCSVIterator;
			int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
			return numOfEntries;
		} catch (IOException e) {
			throw new CensusAnalyserException("File not found",
					CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
		}  catch (RuntimeException e) {
			throw new CensusAnalyserException("File data not proper",
					CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);

		}

	}

	private<E> Iterator<E> getCSVFileIterator(Reader reader, Class<E> csvClass) throws CensusAnalyserException{
		try {
		CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
		csvToBeanBuilder.withType(csvClass);
		csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
		CsvToBean<E> csvToBean = csvToBeanBuilder.build();
		return csvToBean.iterator();
		} catch(IllegalStateException e) {
			throw new CensusAnalyserException("File data not proper",
					CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
		}
	}
}
