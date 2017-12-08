package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import static hr.fer.zemris.java.tecaj.hw07.shell.commands.ArgumentParser.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * The implementation of {@link ShellCommand} interface. This command writes content of the given file to 
 * {@link MyShell} console. The first argument is path to some file and is mandatory. The second argument 
 * is charset name that will be used to interpret chars from bytes. If not provided, a default platform 
 * charset is used.
 * 
 * @author Vjeco
 */
public class CatCommand extends AbstractCommand {

	/**
	 * Instantiates a new cat command.
	 */
	public CatCommand() {
		super("cat", 
			  "Writes contents of the file given by first argument to console.\n"
			  + "It takes two arguments. The first argument is path to some file and is mandatory.\n"
			  + "The second argument is charset name that it used to interpret chars from bytes.\n"
			  + "If not provided, a default platform charset is used.");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = getArguments(arguments);
		int len = args.size();
		
		if(len != 1 && len != 2){
			env.writeln("Illegal number of arguments.");
		} else {
			Path path = env.getCurrentPath().resolve(args.get(0)).normalize();
			
			try {
				byte[] content = Files.readAllBytes(path);
				
				if(len == 1){
					env.writeln(new String(content, Charset.defaultCharset()));
				} else {
					String charset = args.get(1);
					try {
						env.writeln(new String(content, Charset.forName(charset)));
					} catch (UnsupportedCharsetException e){
						env.writeln("Illegal charset.");
					}
				}
			} catch (IOException e) {
				env.writeln("Unable to read from given file.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}
}
