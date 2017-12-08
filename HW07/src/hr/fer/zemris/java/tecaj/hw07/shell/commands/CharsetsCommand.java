package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.nio.charset.Charset;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The implementation of {@link ShellCommand} interface. This command takes no arguments and 
 * lists names of supported charsets for this Java platform. A single charset name is written per line.
 * 
 * @see Charset#availableCharsets()
 * 
 * @author Vjeco
 */
public class CharsetsCommand extends AbstractCommand {

	/**
	 * Instantiates a new charsets command.
	 */
	public CharsetsCommand() {
		super("charsets", "Lists names of supported charsets for this Java platform.");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for(String charset : Charset.availableCharsets().keySet()){
			env.writeln(charset);
		}
		
		return ShellStatus.CONTINUE;
	}

}
