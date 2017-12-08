package hr.fer.zemris.java.tecaj.hw07.shell;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.CatCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.CopyCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ExitCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HelpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.HexDumpCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.LsCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.MkdirCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.SymbolCommand;
import hr.fer.zemris.java.tecaj.hw07.shell.commands.TreeCommand;

/**
 * The class MyShell representing implementation of a Unix-like shell. User writes commands
 * into shell's console, and shell executes selected command.
 * 
 * <p><ul>There are 10 defined commands:
 * 		<li>{@link CatCommand}</li>
 * 		<li>{@link CharsetCommand}</li>
 * 		<li>{@link CopyCommand}</li>
 * 		<li>{@link ExitCommand}</li>
 * 		<li>{@link HelpCommand}</li>
 * 		<li>{@link HexDumpCommand}</li>
 * 		<li>{@link LsCommand}</li>
 * 		<li>{@link MkdirCommand}</li>
 * 		<li>{@link SymbolCommand}</li>
 * 		<li>{@link TreeCommand}</li>
 * </ul>
 */
public class MyShell {
	
	/** All defined commands in MyShell. */
	private final static Map<String, ShellCommand> commands;

	/**
	 * Static block that initializes commands.
	 */
	static {
		commands = new HashMap<>();
		
		ShellCommand[] commandArray = { 
			new CatCommand(),
			new CharsetsCommand(), 
			new CopyCommand(),
			new ExitCommand(),
			new HelpCommand(),
			new HexDumpCommand(),
			new LsCommand(),
			new MkdirCommand(),
			new SymbolCommand(), 
			new TreeCommand()
		};
		
		for (ShellCommand command : commandArray) {
			commands.put(command.getCommandName(), command);
		}
	}

	/**
	 * The implementation of interface {@link Environment}. This class is passed to every shell command.
	 * 
	 * @author Vjeco
	 */
	private static class EnvironmentImpl implements Environment {
		
		/** The current path. */
		private Path currentPath;
		
		/** The shell's reader. */
		private BufferedReader reader;
		
		/** The shell's writer. */
		private BufferedWriter writer;	
		
		/** The multiline symbol. */
		private Character multilineSymbol;
		
		/** The prompt symbol. */
		private Character promptSymbol;
		
		/** The morelines symbol. */
		private Character morelinesSymbol;

		/**
		 * Instantiates a new {@link Environment} implementation.
		 * {@code promptSymbol} is initially set to '>', {@code morelinesSymbol} is set
		 * to '\' and multilineSymbol is set to '|'. Reader reads from standard input and
		 * writer writer to standard output.
		 */
		public EnvironmentImpl(){
			reader = new BufferedReader(
						new InputStreamReader(
							new BufferedInputStream(System.in)));
			writer = new BufferedWriter(
						new OutputStreamWriter(
							new BufferedOutputStream(System.out)));
			
			currentPath = Paths.get(".").normalize().toAbsolutePath();
			promptSymbol = '>';
			morelinesSymbol = '\\';
			multilineSymbol = '|';
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#readLine()
		 */
		@Override
		public String readLine() throws IOException{
			return reader.readLine().trim();
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#write(java.lang.String)
		 */
		@Override
		public void write(String text) {
			try {
				writer.write(text);
				writer.flush();
			} catch (IOException e){
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#writeln(java.lang.String)
		 */
		@Override
		public void writeln(String text) {
			try{
				writer.write(text + "\n");
				writer.flush();	
			} catch (IOException e){
				System.err.println(e.getMessage());
				System.exit(-1);
			}
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#commands()
		 */
		public Iterable<ShellCommand> commands() {
			return commands.values();
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#getMultilineSymbol()
		 */
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#setMultilineSymbol(java.lang.Character)
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#getPromptSymbol()
		 */
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#setPromptSymbol(java.lang.Character)
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#getMorelinesSymbol()
		 */
		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#setMorelinesSymbol(java.lang.Character)
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}

		/**
		 * @see hr.fer.zemris.java.tecaj.hw07.shell.Environment#getCurrentPath()
		 */
		public Path getCurrentPath() {
			return currentPath;
		}
	}

	/** The environment implementation used in this shell. */
	public static Environment environment = new EnvironmentImpl();
	
	/**
	 * The main method. Program starts here. Implementation of MyShell.
	 *
	 * @param args the command line arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		
		ShellCommand shellCommand;
		ShellStatus status = null;
		environment.writeln("Welcome to MyShell v 1.0");
		
		do {		
			environment.write(environment.getPromptSymbol().toString() + " ");
			String input = processInput();
			
			String cmd;
			String arg = null;
			if (input.contains(" ")){
				String [] elements = input.split(" ", 2);
				cmd = elements[0];
				arg = elements[1];
			} else {
				cmd = input;
			}
				
			shellCommand = commands.get(cmd.toLowerCase());
			if(shellCommand == null) {
				environment.writeln("Unknown command!");
				continue;
			};
				
			status = shellCommand.executeCommand(environment, arg);
		} while (status != ShellStatus.TERMINATE);			
}

	/**
	 * Method for scanning input command. If line ends with {@code morelinesSymbol}, command input
	 * can stretch through multiple lines, otherwise it reads only one line.
	 *
	 * @return the result command string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String processInput() throws IOException {
		char multiline = environment.getMultilineSymbol();
		char morelines = environment.getMorelinesSymbol();
		
		String input = environment.readLine();	
		
		if(input.endsWith(String.valueOf(morelines))){
			input = trimInput(input);
			while(true) {
				environment.write(String.valueOf(multiline) + " ");
				String line = environment.readLine();
				if(line.endsWith(String.valueOf(morelines))){
					line = trimInput(line);
					input += line;
				} else {
					input += line;
					break;
				}
			}
		}
		
		return input;
	}

	/**
	 * Method for removing {@code morelinesSymbol} from the end of the line.
	 *
	 * @param input the input string
	 * @return the result string
	 */
	private static String trimInput(String input) {
		return input.substring(0, input.length() - 1);
	}
}
