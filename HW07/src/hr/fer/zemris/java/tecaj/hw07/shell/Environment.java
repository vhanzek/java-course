package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.IOException;
import java.nio.file.Path;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * The interface Environment used for as a link between OS and shell. This is an abstraction 
 * which is passed to each defined command. The each implemented command communicates  with 
 * user (reads user input and writes response) only through this interface.
 * 
 * @author Vjeco
 */
public interface Environment {
	
	/**
	 * Reads line from some input stream.
	 *
	 * @return the line as a string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	String readLine() throws IOException;
	
	/**
	 * Writes given text to some output stream.
	 *
	 * @param text the text to be written
	 */
	void write(String text);
	
	/**
	 * Writes given text to some output stream and puts newline character at the end.
	 *
	 * @param text the text to be written
	 */
	void writeln(String text);
	
	/**
	 * Returns collection that can be iterated of all defined commands in {@link MyShell}.
	 *
	 * @return the collection of commands
	 */
	Iterable<ShellCommand> commands();
	
	/**
	 * Gets the currently used multiline symbol.
	 *
	 * @return the multiline symbol
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol to a new one.
	 *
	 * @param symbol the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Gets the currently used prompt symbol.
	 *
	 * @return the prompt symbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol.
	 *
	 * @param symbol the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Gets the currently used morelines symbol.
	 *
	 * @return the morelines symbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol.
	 *
	 * @param symbol the new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Gets the current path of the shell.
	 *
	 * @return the current path of the shell
	 */
	Path getCurrentPath();

}
