package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;

/**
 * This class has only one static method that "knows" how to separate arguments given in a 
 * {@link MyShell} command.
 * 
 * @author Vjeco
 */
public abstract class ArgumentParser {
	/**
	 * The unused unicode character. /
	 */
	private final static String UNUSED_CHAR = "\uffff";
	
	/**
	 * Gets the command arguments. In command where a file-path is expected, paths with spaces are
	 * allowed using quotes. Inside a quote, \\ is interpreted as \ and \" as ".
	 *
	 * @param text the text with all arguments together
	 * @return the list of arguments
	 */
	public static List<String> getArguments(String text) {
		List<String> list = new ArrayList<String>();
		text = text.replaceAll("\\\\\"", UNUSED_CHAR);
		
		if(text != null){
			Matcher matcher = Pattern.compile("\"([^\"]+)\"|([^\\s]+)").matcher(text.trim());
			
			while(matcher.find()){
				if(matcher.group(1) != null) {
					list.add(matcher.group(1).replaceAll("\\\\\\\\", "\\\\").replaceAll(UNUSED_CHAR, "\""));
				} else if(matcher.group(2) != null) {
					list.add(matcher.group(2).replaceAll(UNUSED_CHAR, "\""));
				}
			}
		}
		
		return list;
	}
}
