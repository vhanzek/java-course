package hr.fer.zemris.cmdapps.trazilica;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import hr.fer.zemris.cmdapps.trazilica.query.QueryCommand;
import hr.fer.zemris.cmdapps.trazilica.query.QueryResult;
import hr.fer.zemris.cmdapps.trazilica.vector.Vector;

/**
 * The main class which initializes {@link Vocabulary} and document vectors
 * using {@link Vector} class. Four commands are defined - 'query arguments'
 * which searches for the best result in all offered document by comparing
 * query vector and specified document vector. Second command is 'results' which
 * prints results from the last executed query. The third command 'type n'
 * which prints n-th result file of the last executed query, and the last is 
 * 'exit' which exits the shell.
 * 
 * @author Vjeran
 */
public class Search {
	
	/** The query results. */
	private static List<QueryResult> results;

	/**
	 * The main method where program starts.
	 *
	 * @param args the command line arguments. Unused in this example
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			throw new IllegalArgumentException(
				"Illegal number of command line arguments is provided!"
			);
		}
		
		File dir = new File(args[0]);
		Vocabulary voc = null;
		
		long start = System.nanoTime();
		try {
			voc = initializeVocabulary(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		long end = System.nanoTime();
		
		System.out.printf("It took %.3f seconds to initialize vocabulary.%n", (end - start)*10E-10);
		System.out.println("Veličina riječnika je " + voc.getAllWords().length + " riječi.\n");
		
		while(true){
			System.out.printf("Enter command > ");
			
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			String command = sc.nextLine();
			if(processCommand(command, voc)) {
				sc.close();
				System.out.println("Thank you and goodbye!");
				break;
			}
		}
	}
	
	
	/*--------------------------------- SEARCH INITIALIZATION ---------------------------------*/
	
	/**
	 * Method for initializing vocabulary defined by class {@link Vocabulary}.
	 * The method receives instance of class {@link File} representing directory
	 * with all defined documents and then processes it by counting all non-stop
	 * words and creating vectors for every document in the directory.
	 *
	 * @param dir the directory with all defined documents
	 * @return the vocabulary
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static Vocabulary initializeVocabulary(File dir) throws IOException {
		Map<String, List<String>> articleMap = new HashMap<>();
		List<Word> words = new ArrayList<>();
		
		List<String> stopWords = getStopWords();	
		File[] articles = dir.listFiles();
		
		for(File article : articles){
			List<String> articleWords = getWordsFromArticle(article, stopWords);
			articleMap.put(article.getAbsolutePath(), articleWords);
			addWords(words, new HashSet<>(articleWords));
		}
		
		Vocabulary voc = new Vocabulary(words.toArray(new Word[words.size()]));
		voc.createDocVectors(articleMap);
		
		return voc;
	}
	
	/**
	 * Helper method for adding words from the certain article to list with
	 * all defined non-stop words. If the list {@code words} already contains
	 * given word, the word occurrence is increased.
	 *
	 * @param words the all defined words
	 * @param articleWords the article words
	 */
	private static void addWords(List<Word> words, Set<String> articleWords) {
		for(String arWord : articleWords){
			Word word = containsWord(arWord, words);
			if(word == null){
				words.add(new Word(arWord));
			} else {
				word.incrementOccurance();
			}
		}
	}

	/**
	 * Helper method for checking if the list {@code words} contains
	 * given {@code word}. If it does, that instance of class {@link Word}
	 * is returned, otherwise, <code>null</code> is returned
	 *
	 * @param word the word
	 * @param words the words
	 * @return the tested word
	 */
	private static Word containsWord(String word, List<Word> words) {
		for(Word w : words){
			if(w.getValue().equals(word)) return w;
		}
		return null;
	}


	/*--------------------------------- COMMAND PROCESSING ---------------------------------*/
	
	/**
	 * Method for processing {@link Search} command. As described in documentation
	 * of this class, there are four used commands. 
	 *
	 * @param command the command
	 * @param voc the vocabulary
	 * @return <code>true</code>, if shell needs to close
	 * 		   <code>false</code> otherwise
	 */
	private static boolean processCommand(String command, Vocabulary voc) {
		String[] commandParts = command.split("\\s", 2);
		switch(commandParts[0]){
			case "query":
				List<String> args = getValidArguments(commandParts[1], voc.getWordsAsString());
				QueryCommand queryCommand = new QueryCommand(args);
				queryCommand.executeQuery(voc);
				setResults(queryCommand.getResults());
				break;
			case "results":
				processResultsCommand();
				break;
			case "type":
				processTypeCommand(commandParts[1]);
				break;
			case "exit":
				return true;
			default: 
				System.err.println("Nepoznata naredba.");
				break;
		}
		return false;		
	}

	/**
	 * Helper method for printing results got from the last query.
	 */
	private static void processResultsCommand() {
		if(results == null) {
			System.err.println("Results are not yet calculated!");
			return;
		}
		
		for(int i = 0, n = results.size(); i < n; i++){
			System.out.printf("[%2d] %s%n", i, results.get(i));
		}
	}
	
	/**
	 * Helper method for printing content of the n-th result file to
	 * standard output.
	 *
	 * @param resultNo the number of the result
	 */
	private static void processTypeCommand(String resultNo) {
		if(results == null) {
			System.err.println("Results are not yet calculated!");
			return;
		}
		
		int resNo = 0;
		try{
			resNo = Integer.parseInt(resultNo);
		} catch (NumberFormatException nfe){
			System.err.println("Invalid argument of command 'type'!");
			return;
		}
		
		QueryResult res = results.get(resNo);
		
		String lines = new String(new char[90]).replace("\0", "-");
		System.out.println(lines);
		System.out.println("Dokument: " + res.getFilePath());
		System.out.println(lines);
		
		Path path = Paths.get(res.getFilePath());
		List<String> docLines = null;
		try {
			docLines = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		docLines.forEach(l -> System.out.println(l));
	}

	
	/*--------------------------------- HELPER METHODS ---------------------------------*/

	/**
	 * Method returns all valid (non-stop) words from the given query.
	 * For example, if command 'query i generous' is executed, only the
	 * second word will be used to create query vector because the first
	 * is not valid (stop) word.
	 *
	 * @param arguments the arguments
	 * @param allWords the all words
	 * @return the valid arguments
	 */
	private static List<String> getValidArguments(String arguments, Set<String> allWords) {
		String[] queries = arguments.split("\\s");
		List<String> args = new ArrayList<>();
		for(String q : queries){
			if(allWords.contains(q)) args.add(q);
		}
		return args;
	}
	
	/**
	 * Helper method for getting all valid words from the given article.
	 *
	 * @param article the article
	 * @param stopWords the stop words
	 * @return the words from article
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static List<String> getWordsFromArticle(File article, List<String> stopWords) throws IOException {
		String content = new String(Files.readAllBytes(article.toPath()));
		List<String> articleWords = new ArrayList<>();
		
		String word = "";
		for(char ch : content.toCharArray()){
			if(Character.isAlphabetic(ch)){
				word += ch;
			} else {
				if (!word.trim().equals("")) {
					word = word.toLowerCase();
                    if (!stopWords.contains(word)) {
                        articleWords.add(word);
                    }
                }
				word = "";
			}
		}
		return articleWords;
	}
	
	/**
	 * Helper method for getting all stop word from specified file.
	 *
	 * @return the all stop words
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static List<String> getStopWords() throws IOException {
		Path stopWordsPath = Paths.get("./src/hrvatske-stop_rijeci.txt");
		List<String> stopWords = Files.readAllLines(stopWordsPath, StandardCharsets.UTF_8);
		return stopWords;
	}

	
	/*------------------------------ RESULTS SETTER ------------------------------*/

	/**
	 * Sets the results.
	 *
	 * @param results the new results
	 */
	public static void setResults(List<QueryResult> results) {
		Search.results = results;
	}
}
