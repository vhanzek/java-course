package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The implementation of {@link ShellCommand} interface. If started with no arguments, command lists 
 * names of all supported {@link MyShell} commands. If started with a single argument, command prints 
 * name and the description of selected command (or prints appropriate error message if no such command exists).
 * 
 * @author Vjeco
 */
public class HelpCommand extends AbstractCommand {

	/**
	 * Instantiates a new help command.
	 */
	public HelpCommand() {
		super("help", 
			  "Lists names of all shell commands if no arguments are given.\n"
			  + "If one argument is given, prints name and the description of the selected command.");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null){
			for(ShellCommand cmd : env.commands()){
				env.writeln(cmd.getCommandName());
			}
		} else {
			ShellCommand cmd = contains(env, arguments);
			if(cmd != null){
				env.writeln(cmd.getCommandName() + ":");
				cmd.getCommandDescription().forEach(d -> env.writeln(d));
			} else {
				env.writeln("Illegal command name.");
			};
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Checks if certain command exist in a {@link MyShell} defined commands.
	 *
	 * @param env the shell's environment
	 * @param command the command to be searched
	 * @return the shell command with given name
	 */
	private ShellCommand contains(Environment env, String command) {
		for(ShellCommand cmd : env.commands()){
			if(cmd.getCommandName().equals(command)){
				return cmd;
			}
		}
		return null;
	}

}
