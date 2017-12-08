package hr.fer.zemris.java.tecaj.hw5.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

/**
 * The class representing student database. Every student's data is packed inside the class {@link StudentRecord}.
 * There are 4 attributes: JMBAG, last name, first name and final grade. Database does not support {@code null} values.
 * 
 * @author Vjeco
 */
public class StudentDatabase {
	
	/** The list student records. */
	private List<StudentRecord> studentRecords;
	
	/** 
	 * The student table.
	 * Allows fast retrieval of student records when JMBAG is known.
	 */
	private SimpleHashtable<String,StudentRecord> studentTable;
	
	/**
	 * Instantiates a new student database.
	 *
	 * @param studentRecordLines 
	 * 				the list of lines read from specified file with students
	 */
	public StudentDatabase(List<String> studentRecordLines) {
		this.studentRecords = new ArrayList<>(64);
		this.studentTable = new SimpleHashtable<>(128);
		processLines(studentRecordLines);
	}

	/**
	 * Method for processing lines read from specified file with students.
	 * All attributes in a file are separated by whitespaces.
	 *
	 * @param lines the lines with student attributes
	 */
	private void processLines(List<String> lines) {
		for(String line : lines){
			String[] elements = line.split("\\t");
			if(elements.length != 4){
				throw new QueryException(
					"Illegal line form in a databse file."
				);
			}
			StudentRecord record = new StudentRecord(
										elements[0], elements[1], 
										elements[2], Integer.parseInt(elements[3]));
			studentRecords.add(record);
			studentTable.put(record.getJmbag(), record);
		}
	}

	/**
	 * Method for fast retrieval of student records when JMBAG is known.
	 *
	 * @param jmbag the student's JMBAG
	 * @return the student record with given JMBAG
	 */
	public StudentRecord forJMBAG(String jmbag){
		return studentTable.get(jmbag);
	}
	
	/**
	 * Method for filtering database depending on given {@link IFilter}.
	 *
	 * @param filter the criteria for filtering
	 * @return the list with "survived" students, i.e. student records
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> tempRecords = new ArrayList<>();
		
		for(StudentRecord record : studentRecords){
			if(filter.accepts(record)){
				tempRecords.add(record);
			}
		}
		
		return tempRecords;
	}

	/**
	 * Gets the student records.
	 *
	 * @return the student records
	 */
	public List<StudentRecord> getStudentRecords() {
		return studentRecords;
	}

	/**
	 * Gets the student table.
	 *
	 * @return the student table
	 */
	public SimpleHashtable<String, StudentRecord> getStudentTable() {
		return studentTable;
	}	
}
