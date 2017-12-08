package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

import static hr.fer.zemris.java.tecaj.hw07.shell.commands.ArgumentParser.*;

/**
 * The implementation of {@link ShellCommand} interface. Command takes one argument: directory name and then 
 * writes a directory listing (not recursively). The output consists of 4 columns. First column indicates if 
 * current object is directory (d), readable (r), writable (w) and executable (x). Second column contains object 
 * size in bytes that is right aligned and occupies 10 characters. Follows file creation date/time and finally file name.
 * 
 * @author Vjeco
 */
public class LsCommand extends AbstractCommand {

	/**
	 * Instantiates a new ls command.
	 */
	public LsCommand() {
		super("ls", "Lists all of the files and directories (but not recursively) in a given directory.");
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
			String dir = args.get(0);
			Path path = env.getCurrentPath().resolve(dir).normalize();
			if(Files.exists(path) || !Files.isDirectory(path)){
				try {
					processFiles(env, path);
				} catch (IOException e) {
					throw new IllegalStateException();
				}
			} else {
				env.writeln("Given path does not exist or is not directory.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Method processes all of the file in a given directory, but not recursively.
	 *
	 * @param env the shell's environment
	 * @param path the path of the directory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processFiles(Environment env, Path path) throws IOException {
		for(File file : path.toFile().listFiles()){
			Path filePath = file.toPath();
			BasicFileAttributes attrs = getAttributes(filePath);
			
			boolean[] props = getProps(filePath);
			long size = attrs.size();
			String formattedDateTime = getDate(attrs);
			String fileName = filePath.getFileName().toString();
			
			env.writeln(String.format("%c%c%c%c%10s %s %s", 
											props[0] ? 'd' : '-',
											props[1] ? 'r' : '-',
											props[2] ? 'w' : '-',
											props[3] ? 'x' : '-',
											size,
											formattedDateTime,
											fileName));
		}
	}

	/**
	 * Returns the file attributes (properties) - d, r, w, x.
	 *
	 * @param path the path of the file
	 * @return the properties
	 */
	private boolean[] getProps(Path path) {
		boolean[] props = new boolean[4];
		props[0] = Files.isDirectory(path);
		props[1] = Files.isReadable(path);
		props[2] = Files.isWritable(path);
		props[3] = Files.isExecutable(path);
		return props;
	}

	/**
	 * Returns the file creation date and time.
	 *
	 * @param attrs the attributes of the file
	 * @return the file creation date and time
	 */
	private String getDate(BasicFileAttributes attrs) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileTime fileTime = attrs.creationTime();
		return sdf.format(new Date(fileTime.toMillis()));
	}

	/**
	 * Gets the attributes.
	 *
	 * @param file the file
	 * @return the attributes
	 */
	private BasicFileAttributes getAttributes(Path file) {
		BasicFileAttributeView faView = Files.getFileAttributeView(
			file, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
		);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		return attributes;
	}

}
