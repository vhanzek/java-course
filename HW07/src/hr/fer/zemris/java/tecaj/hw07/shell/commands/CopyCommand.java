package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import static hr.fer.zemris.java.tecaj.hw07.shell.commands.ArgumentParser.*;
import static java.nio.file.Files.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The implementation of {@link ShellCommand} interface. This command expects two arguments: source 
 * file name and destination file name. Is destination file exists, user is asked for permission to 
 * overwrite it. Copy command works only with files. If the second argument is directory, it is assumed 
 * that user wants to copy the original file into that directory using the original file name.
 * 
 * @author Vjeco
 */
public class CopyCommand extends AbstractCommand {

	/**
	 * Instantiates a new copy command.
	 */
	public CopyCommand() {
		super("copy", "Copies the file from source (first argument) to a destination (second argument).");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = getArguments(arguments);
		
		if(args.size() != 2){
			env.writeln("Illegal number of arguments.");
		} else {
			Path src = env.getCurrentPath().resolve(args.get(0)).normalize();
			Path dest = env.getCurrentPath().resolve(args.get(1)).normalize(); 
			
			try {
				copy(env, src, dest);
			} catch (IOException e) {
				throw new IllegalStateException();
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method for checking source and destination files and copying content of a source file
	 * to a destination file.
	 *
	 * @param env the shell's environment
	 * @param src the source file
	 * @param dest the destination file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void copy(Environment env, Path src, Path dest) throws IOException {
		if(notExists(src)){
			env.writeln("Source file does not exist.");
		} else if (isDirectory(src)){
			env.writeln("Source file is a directory.");
		} else if(isDirectory(dest) && notExists(dest)){
			env.writeln("Destination folder does not exist.");
		} else {
			if(isDirectory(dest)){
				dest = dest.resolve(src.getFileName());
			}
			if(exists(dest)){
				if(askUser(env)){
					copyFiles(src, dest, false);
				} else return;
			} else {
				createFile(dest);
				copyFiles(src, dest, true);
			}
		}
	}

	/**
	 * Method only for copying content of a source file to a destination file using
	 * file streams. If {@code append} is set to {@code false}, destination file (if exists)
	 * is overwritten.
	 *
	 * @param src the source file
	 * @param dest the destination file
	 * @param append the append
	 */
	private void copyFiles(Path src, Path dest, boolean append) {
		try(BufferedInputStream bis = new BufferedInputStream(
										new FileInputStream(src.toFile()));
			BufferedOutputStream bos = new BufferedOutputStream(
										new FileOutputStream(dest.toFile(), append))){
			
			byte[] buf = new byte[1024];
			
			while(true){
				int bytesRead = bis.read(buf);
				if(bytesRead < 1) break;
				bos.write(buf, 0, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		} 
	}

	/**
	 * Asks user for permission for overwriting certain file.
	 *
	 * @param env the shell's environment
	 * @return {@code true}, if overwriting is allowed, {@code false} otherwise
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private boolean askUser(Environment env) throws IOException {
		env.writeln("Destination folder already exists. Do you want to overwrite it? YES/NO");
		
		while(true){
			String answer = env.readLine().toLowerCase();
			switch(answer){
			case "yes": return true;
			case "no": return false;	
			default: env.writeln("Illegal answer. Answers can be: YES or NO.");
		}		
	}
		
	}

}
