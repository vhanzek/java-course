package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;

/**
 * The abstract implementation of a {@link MyShell} command.
 * 
 * @author Vjeco
 */
public abstract class AbstractCommand implements ShellCommand {
	
	/** The command name. */
	private final String commandName;
	
	/** The command description. */
	private final List<String> commandDescription;
	
	/**
	 * Constructor used in all command implementations. Command description
	 * is split by newline symbol and all parts are put into unmodifiable list.
	 *
	 * @param commandName the command name
	 * @param commandDescription the command description
	 */
	public AbstractCommand(String commandName, String commandDescription){
		this.commandName = commandName;
		this.commandDescription = Collections.unmodifiableList(
									Arrays.asList(commandDescription.split("\n")));
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#getCommandName()
	 */
	public String getCommandName() {
		return commandName;
	}
	
	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#getCommandDescription()
	 */
	public List<String> getCommandDescription() {
		return commandDescription;
	}
		
}
