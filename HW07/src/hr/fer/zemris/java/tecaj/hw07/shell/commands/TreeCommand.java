package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import static hr.fer.zemris.java.tecaj.hw07.shell.commands.ArgumentParser.getArguments;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The implementation of {@link ShellCommand} interface. This command expects a single argument: directory name
 * and prints a tree (each directory level shifts output two characters to the right).
 * 
 * @author Vjeco
 */
public class TreeCommand extends AbstractCommand {

	/**
	 * Instantiates a new tree command.
	 */
	public TreeCommand() {
		super("tree", "Has a single argument: directory name. Prints file tree beginning from given directory.");
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
			if(Files.exists(path) || !Files.isDirectory(path)){
				TreeAnalyzer analyzer = new TreeAnalyzer(env);
				try {
					Files.walkFileTree(path, analyzer);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				env.writeln("Given path does not exist or is not directory.");
			}
		}
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * The inner static class used for recursive file visit.
	 * 
	 * @author Vjeco
	 */
	private static class TreeAnalyzer extends SimpleFileVisitor<Path>{
		
		/** The current indentation. */
		private int indentation;
		
		/** The shell's environment. */
		private Environment env;
		
		/**
		 * Instantiates a new tree analyzer.
		 *
		 * @param env the shell's environment
		 */
		public TreeAnalyzer(Environment env) {
			this.env = env;
		}

		/**
		 * @see java.nio.file.SimpleFileVisitor#preVisitDirectory(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
		 */
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			print(dir);
			indentation += 2;
			return FileVisitResult.CONTINUE;
		}

		/**
		 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
		 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			print(file);
			return FileVisitResult.CONTINUE;
		}

		/**
		 * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
		 */
		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			indentation -= 2;
			return FileVisitResult.CONTINUE;
		}
		
		/**
		 * Prints the current file.
		 *
		 * @param path the path to current file
		 */
		private void print(Path path) {
			if(indentation == 0){
				env.writeln(path.getFileName().toString());
			} else {
				env.writeln(String.format("%" + indentation + "s%s", "", path.getFileName()));	
			}
		}
	}

}
