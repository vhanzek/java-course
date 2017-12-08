package hr.fer.zemris.cmdapps.trazilica;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hr.fer.zemris.cmdapps.trazilica.vector.Vector;
import hr.fer.zemris.cmdapps.trazilica.vector.VectorUtil;

/**
 * The class representing vocabulary used in {@link Search} class.
 * It contains all defined words retrieved from set of documents.
 * Also, it contains vector representations of every offered document.
 * 
 * @author Vjeran
 */
public class Vocabulary {
	
	/** The all defined words. */
	private Word[] words;
	
	/** The words map. */
	private Map<String, Integer> wordsMap;
	
	/** The document vectors map. */
	private Map<String, Vector> docVectors;
	
	/** The document count. */
	private int documentCount;

	/**
	 * Instantiates a new vocabulary.
	 *
	 * @param words the words
	 */
	public Vocabulary(Word[] words) {
		this.words = words;
		this.wordsMap = new HashMap<>();
		for(Word word : words){
			this.wordsMap.put(word.getValue(), word.getDocOccurance());
		}
	}
	
	/**
	 * Method for creating vector representation of every offered document.
	 *
	 * @param articleMap
	 *            the article map containing file paths as keys
	 *            and list of words as values
	 * 
	 */
	public void createDocVectors(Map<String, List<String>> articleMap) {
		this.documentCount = articleMap.size();
		this.docVectors = new HashMap<>();
		
		for(Map.Entry<String, List<String>> entry : articleMap.entrySet()){
			double[] components = 
				VectorUtil.getVectorComponents(entry.getValue(), this);
			docVectors.put(entry.getKey(), new Vector(components));
		}
	}

	/**
	 * Gets all defined words.
	 *
	 * @return the all defined words
	 */
	public Word[] getAllWords() {
		return words;
	}

	/**
	 * Gets the document vectors.
	 *
	 * @return the document vectors
	 */
	public Map<String, Vector> getDocVectors() {
		return docVectors;
	}

	/**
	 * Gets the number of offered documents.
	 *
	 * @return the document count
	 */
	public int getDocumentCount() {
		return documentCount;
	}
	
	/**
	 * Gets the words as string.
	 *
	 * @return the words as string
	 */
	public Set<String> getWordsAsString(){
		return wordsMap.keySet();
	}
	
	/**
	 * Gets the document occurrence for given word.
	 *
	 * @param word the word
	 * @return the given word occurrences in documents
	 */
	public int getDocOccurrenceFor(String word){
		return wordsMap.get(word);
	}
}
