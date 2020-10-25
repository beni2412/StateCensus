package StateCensus;

import com.opencsv.bean.CsvBindByName;

public class IndiaCensusCSV {
	@CsvBindByName(column = "State", required = true)
	public String state;
	
	@CsvBindByName(column = "Population", required = true)
	public String population;
	
	@CsvBindByName(column = "AreaInSqKm", required = true)
	public String areaInSqKm;
	
	@CsvBindByName(column = "DensityPerSqKm", required = true)
	public String densityPerSqKm;
	
	public int getPopulationData() {
		int pop = Integer.parseInt(population);
		return pop;
	}
	
	@Override
	public String toString() {
		return "IndiaCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';	}
}
