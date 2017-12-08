package hr.fer.zemris.cmdapps.trazilica;

import java.util.List;

/**
 * The class representing one word. It contains {@link #value} as instance of the
 * class {@link String} and number of word occurrences in documents.
 * 
 * @author Vjeran
 */
public class Word {
	
	/** The value. */
	private String value;
	
	/** The number of word occurrences. */
	private int wordOccurrence;
	
	/**
	 * Instantiates a new word.
	 *
	 * @param value the value
	 * @param docOccurance the word occurrences
	 */
	public Word(String value, int docOccurance) {
		this.value = value;
		this.wordOccurrence = docOccurance;
	}

	/**
	 * Instantiates a new word. {@code wordOccurrence} is set to 1.
	 *
	 * @param value the value
	 */
	public Word(String value) {
		this.value = value;
		this.wordOccurrence = 1;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Gets the number of word occurrences.
	 *
	 * @return the number of word occurrence
	 */
	public int getDocOccurance() {
		return wordOccurrence;
	}
	
	/**
	 * Increment number of word occurrences in documents.
	 */
	public void incrementOccurance(){
		this.wordOccurrence++;
	}
	
	/**
	 * Calculate word's term frequency value.
	 *
	 * @param docWords the list of words in document
	 * @return the word's term frequency value
	 */
	public double calculateTf(List<String> docWords){
		int counter = 0;
		for(String w : docWords){
			if(w.equals(value)) counter++;
		}
		return counter;
		
	}
	
	/**
	 * Calculate word's inverse document frequency value.
	 *
	 * @param voc the vocabulary
	 * @return the word's inverse document frequency value
	 */
	public double calculateIdf(Vocabulary voc){
		int noDocs = voc.getDocumentCount();
		int noDocWithWord = voc.getDocOccurrenceFor(value);
		
		return Math.log10(noDocs/noDocWithWord);
	}
}
