package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The interface representing one {@link MyShell} command. Every command has a
 * name, description and a method {@code executeCommand} for executing that command.
 * 
 * @author Vjeco
 */
public interface ShellCommand {


	/**
	 * Gets the command name.
	 *
	 * @return the command name
	 */
	public String getCommandName();
	
	/**
	 * Gets the command description.
	 *
	 * @return the command description
	 */
	public List<String> getCommandDescription();

	/**
	 * Method for executing this command. The second argument is a single string which 
	 * represents everything that user entered after the command name. It returns
	 * {@link ShellStatus} which determines whether the shell should continue working.
	 *
	 * @param env shell's environment
	 * @param arguments everything after the command name
	 * @return the shell status
	 */
	public ShellStatus executeCommand(Environment env, String arguments);

}
