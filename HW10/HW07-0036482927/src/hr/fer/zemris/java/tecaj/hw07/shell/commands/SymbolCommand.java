package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The implementation of {@link ShellCommand} interface. If command is started with one argument, 
 * it returns symbol for the type of symbol given by selected argument. If command is started with
 * two arguments, first represents symbol type and the second one represents new symbol for that 
 * symbol type. It changes currently used symbol for selected type to a new symbol.
 * 
 * @author Vjeco
 */
public class SymbolCommand extends AbstractCommand {
	
	/** The Constant MORELINES. */
	private final static String MORELINES = "MORELINES";
	
	/** The Constant MULTILINE. */
	private final static String MULTILINE = "MULTILINE";
	
	/** The Constant PROMPT. */
	private final static String PROMPT = "PROMPT";

	/**
	 * Instantiates a new symbol command.
	 */
	public SymbolCommand() {
		super("symbol", 
			  "If one argument is given, prints symbol for selected type of symbol.\n"
			  + "If two arguments are given, sets value of the symbol of given type to new value.");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if(arguments == null){
			env.writeln("Illegal number of arguments.");
			return ShellStatus.CONTINUE;
		} 		
		String[] args = arguments.trim().split("[\\s]+");
		
		boolean one = (args.length == 1);
		Character newSymbol = null;
		Character oldSymbol = null;
		
		if(!one){
			if(args[1].trim().length() == 1){
				newSymbol = args[1].charAt(0);
			} else {
				env.writeln("Illegal new symbol.");
				return ShellStatus.CONTINUE;
			}
		}
		
		switch(args[0]){
		case PROMPT:
			if(one) {
				printSymbol(env, env.getPromptSymbol(), PROMPT);
			} else {
				oldSymbol = env.getPromptSymbol();
				env.setPromptSymbol(newSymbol);
				printSetSymbol(env, oldSymbol, newSymbol, PROMPT);
			}
			break;
		case MULTILINE:
			if(one) {
				printSymbol(env, env.getMultilineSymbol(), MULTILINE);
			} else {
				oldSymbol = env.getMultilineSymbol();
				env.setMultilineSymbol(newSymbol);
				printSetSymbol(env, oldSymbol, newSymbol, MULTILINE);
			}
			break;
		case MORELINES:
			if(one) {
				printSymbol(env, env.getMorelinesSymbol(), MORELINES);
			} else {
				oldSymbol = env.getMorelinesSymbol();
				env.setMorelinesSymbol(newSymbol);
				printSetSymbol(env, oldSymbol, newSymbol, MORELINES);
			}
			break;
		default:
			env.writeln("Illegal type of the symbol.");
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * Prints symbol change information.
	 *
	 * @param env the shell's environment
	 * @param oldSymbol the old symbol
	 * @param newSymbol the new symbol
	 * @param strSymbol the type of the symbol
	 */
	private void printSetSymbol(Environment env, Character oldSymbol, Character newSymbol, String strSymbol){
		env.writeln("Symbol for " + strSymbol + " changed from '" + oldSymbol + "' to '" + newSymbol + "'");
	}
	
	/**
	 * Prints the symbol for selected type.
	 *
	 * @param env the shell's environment
	 * @param symbol the symbol
	 * @param strSymbol the type of the symbol
	 */
	private void printSymbol(Environment env, Character symbol, String strSymbol){
		env.writeln("Symbol for " + strSymbol + " is '" + symbol + "'");
	}

}
