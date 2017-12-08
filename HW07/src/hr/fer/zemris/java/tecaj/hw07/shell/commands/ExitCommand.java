package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The implementation of {@link ShellCommand} interface. This command terminates {@link MyShell} future work.
 * 
 * @author Vjeco
 */
public class ExitCommand extends AbstractCommand {

	/**
	 * Instantiates a new exit command.
	 */
	public ExitCommand() {
		super("exit", "This command exits MyShell.");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		env.write("Goodbye!");
		return ShellStatus.TERMINATE;
	}

}
