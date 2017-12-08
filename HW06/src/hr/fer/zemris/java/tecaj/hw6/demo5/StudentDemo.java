package hr.fer.zemris.java.tecaj.hw6.demo5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Double.*;

/**
 * The Class StudentDemo used for working with collection streams and processing list of 
 * instances of {@link StudentRecord}.
 * 
 * @author Vjeco
 */
public class StudentDemo {
	
	/** The comparator by points descending. */
	private static Comparator<StudentRecord> PO_BODOVIMA_SILAZNO = (r1, r2) -> {
		Double r1Bod = r1.getBrBodMI() + r1.getBrBodZI() + r1.getBrBodLV();
		Double r2Bod = r2.getBrBodMI() + r2.getBrBodZI() + r2.getBrBodLV();
	    return r2Bod.compareTo(r1Bod);
	};
	
	/** The comparator by JMBAG ascending. */
	private static Comparator<StudentRecord> PO_JMBAGU_UZLAZNO = (r1, r2) -> r1.getJmbag().compareTo(r1.getJmbag());

	/**
	 * The main method. Program starts here.
	 * Method gets data from the file "student.txt" that can be found in this project.
	 *
	 * @param args the arguments. Unused in this example.
	 * @throws IOException signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"));
		List<StudentRecord> records = convert(lines);
		
		System.out.println("BROJ STUDENATA MI+ZI+LV > 25 :");
		long broj = records.stream()
						   .filter(r -> r.getBrBodMI() + r.getBrBodZI() + r.getBrBodLV() > 25)
						   .count();
		System.out.println(broj);
		System.out.println("******************************************************************\n");
		
		System.out.println("BROJ ODLIKAŠA :");
		long broj5 = records.stream()
						    .filter(r -> r.getOcjena() == 5)
						    .count();
		System.out.println(broj5);
		System.out.println("******************************************************************\n");
		
		System.out.println("ODLIKAŠI :");
		List<StudentRecord> odlikasi = records.stream()
						    				  .filter(r -> r.getOcjena()  == 5)
						    				  .collect(Collectors.toList());
		odlikasi.forEach(System.out::println);
		System.out.println("******************************************************************\n");
		
		System.out.println("ODLIKAŠI SORTIRANO :");
		List<StudentRecord> odlikasiSortirano = records.stream()
						    				  		   .filter(r -> r.getOcjena() == 5)
						    				  		   .sorted(PO_BODOVIMA_SILAZNO)
						    				  		   .collect(Collectors.toList());
		odlikasiSortirano.forEach(System.out::println);
		System.out.println("******************************************************************\n");
		
		System.out.println("NEPOLOŽENI JMBAGOVI :");
		List<String> nepolozeniJMBAGovi = records.stream()
						    				  	 .filter(r -> r.getOcjena() == 1)
						    				  	 .sorted(PO_JMBAGU_UZLAZNO)
						    				  	 .map(StudentRecord::getJmbag)
						    				  	 .collect(Collectors.toList());
		nepolozeniJMBAGovi.forEach(System.out::println);
		System.out.println("******************************************************************\n");
		
		System.out.println("PO OCJENAMA :");
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = records.stream()
						    				  	 				  .collect(Collectors.groupingBy(StudentRecord::getOcjena));
		printMapByGrades(mapaPoOcjenama);
		System.out.println("******************************************************************\n");
		
		System.out.println("PO OCJENAMA BROJČANO :");
		Map<Integer, Integer> mapaPoOcjenama2 = records.stream()
													   .collect(Collectors.toMap(StudentRecord::getOcjena,
															   					 r -> Integer.valueOf(1),
															   					 (oldVal, newVal) -> Integer.valueOf(oldVal + 1)
															   	));
		printMapByGrades2(mapaPoOcjenama2);
		System.out.println("******************************************************************\n");
		
		System.out.println("PROLAZ/NEPROLAZ :");
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = records.stream()
						    						   			  .collect(Collectors.partitioningBy(r -> r.getOcjena() != 1));
		printPassedFailed(prolazNeprolaz);
		System.out.println("******************************************************************\n");

	}

	/**
	 * Prints the students that passed the subject, and those that did not.
	 *
	 * @param passedFailed the students that passed the subject, and those that did not
	 */
	private static void printPassedFailed(Map<Boolean, List<StudentRecord>> passedFailed) {
		System.out.println("PROŠLI: ");
		passedFailed.get(true).forEach(System.out::println);
		System.out.println();
		System.out.println("PALI: ");
		passedFailed.get(false).forEach(System.out::println);
	}

	/**
	 * Prints the number of students with certain grade.
	 *
	 * @param mapByGrades the map by grades
	 */
	private static void printMapByGrades2(Map<Integer, Integer> mapByGrades) {
		mapByGrades.forEach((k,v)->System.out.println("Ocjena " + k + ": " + v));
	}

	/**
	 * Prints the list of students with certain grade.
	 *
	 * @param mapByGrades the map by grades
	 */
	private static void printMapByGrades(Map<Integer, List<StudentRecord>> mapByGrades) {
		for(int i = 1; i <= 5; i++){
			System.out.printf("OCJENA %d:%n", i);
			mapByGrades.get(i).forEach(System.out::println);
			if(i != 5) {System.out.println();}
		}
	}

	/**
	 * Convert the list of strings got from reading all lines from file "studenti.txt" to list of students,
	 * i.e. list of instances of {@link StudentRecord}.
	 *
	 * @param lines the lines
	 * @return the list
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for(String line : lines){
			String[] elements = line.split("\\s");
			records.add(new StudentRecord(elements[0], elements[1], elements[2], 
										  parseDouble(elements[3]), parseDouble(elements[4]), 
										  parseDouble(elements[5]), Integer.parseInt(elements[6])));
		}
		
		return records;
	}

}
