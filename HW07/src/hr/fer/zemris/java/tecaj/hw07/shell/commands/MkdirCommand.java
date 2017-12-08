package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import static hr.fer.zemris.java.tecaj.hw07.shell.commands.ArgumentParser.*;

/**
 * The implementation of {@link ShellCommand} interface. Command takes a single argument: directory name, 
 * and creates the appropriate directory structure. For example, if command is "mkdir dir", it creates new 
 * directory in this project's directory. However, if if command is "mkdir dir1/dir2", it creates two 
 * directories and puts them in a given directory structure.
 * 
 * @author Vjeco
 */
public class MkdirCommand extends AbstractCommand {

	/**
	 * Instantiates a new mkdir command.
	 */
	public MkdirCommand() {
		super("mkdir", 
			  "Takes one argument: directory name, and creates appropriate directory structure.");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = getArguments(arguments);
		
		if(args.size() != 1){
			env.writeln("Illegal number of arguments.");
		} else {
			Path path = env.getCurrentPath().resolve(args.get(0)).normalize();
			
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				env.writeln("Unable to make directory.");
			}
		}

		return ShellStatus.CONTINUE;
	}

}
