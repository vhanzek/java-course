package hr.fer.zemris.cmdapps.trazilica.vector;


import java.util.List;

import hr.fer.zemris.cmdapps.trazilica.Vocabulary;
import hr.fer.zemris.cmdapps.trazilica.Word;

/**
 * The utility class offering static methods for working with {@link Vector}
 * class.
 * 
 * @author Vjeran
 */
public class VectorUtil {
	
	/**
	 * Method for getting vector components based on given {@code testedWords}
	 * and defined {@code vocabulary}.
	 *
	 * @param testedWords the tested words
	 * @param voc the vocabulary
	 * @return the vector components
	 */
	public static double[] getVectorComponents(List<String> testedWords, Vocabulary voc) {
		Word[] allWords = voc.getAllWords();
		int len = allWords.length;
		double[] components = new double[len];
		
		for(int i = 0; i < len; i++){
			Word word = allWords[i];
			if(testedWords.contains(word.getValue())){
				double tf = word.calculateTf(testedWords);
				double idf = word.calculateIdf(voc);
				components[i] = tf * idf;
			} else {
				components[i] = 0;
			}
		}
		return components;
	}
	
	/**
	 * Method for calculating similarity between two given vectors.
	 *
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return the vector's similarity
	 */
	public static double calculateSimilarity(Vector v1, Vector v2){
		double numerator = v1.scalarProduct(v2);
		double denominator = v1.norm() * v2.norm();
		
		return numerator/denominator;
	}
}
