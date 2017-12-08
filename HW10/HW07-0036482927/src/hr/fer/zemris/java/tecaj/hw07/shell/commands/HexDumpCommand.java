package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import static hr.fer.zemris.java.tecaj.hw07.shell.commands.ArgumentParser.getArguments;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;

/**
 * The implementation of {@link ShellCommand} interface. This command produces hex-output of
 * a selected file. Every line contains, starting from left to right: current offset in a file, 
 * 16 bytes as a hexadecimal strings separated by space character, 16 characters from the text file.
 * 
 * @author Vjeco
 */
public class HexDumpCommand extends AbstractCommand {
	
	/** The size of a byte package used in hex-output */ 
	private final static int PACKAGE_SIZE = 16;

	/**
	 * Instantiates a new hex dump command.
	 */
	public HexDumpCommand() {
		super("hexdump", "Expects a single argument: file name, and produces hex-output of content in that file.");
	}

	/**
	 * @see hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand#execute(hr.fer.zemris.java.tecaj.hw07.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args = getArguments(arguments);

		if (args.size() != 1) {
			env.writeln("Illegal argument number.");
		} else {
			Path file = env.getCurrentPath().resolve(args.get(0)).normalize();
			if (Files.isRegularFile(file)) {
				processHexdump(env, file);
			} else {
				env.writeln("Given path is directory.");
			}
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Method for processing content of the file and transforming it to hexadecimal output.
	 *
	 * @param env the shell's environment
	 * @param file the file to be processed
	 */
	private void processHexdump(Environment env, Path file) {
		try (BufferedInputStream bis = new BufferedInputStream(
										new FileInputStream(file.toFile()))) {
			int offset = 0;
			byte[] buf = new byte[PACKAGE_SIZE];
			
			while (true) {				
				int bytesRead = bis.read(buf);
				if (bytesRead < 1) break;
				env.write(String.format("%08X: ", offset & 0xFFFFFFFF));
				offset += bytesRead;
				
				printHex(env, buf, bytesRead);
				printText(env, buf, bytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Prints {@code len} characters of the text from selected file.
	 *
	 * @param env the shell's environment
	 * @param buf the byte buffer
	 * @param len the number of the bytes read
	 */
	private void printText(Environment env, byte[] buf, int len) {
		for (int i = 0; i < len; i++){
			int byteValue = (int) buf[i];
			byte[] oneChar = new byte[]{buf[i]};
			boolean standard = byteValue >= 32 && byteValue <= 127;
			env.write(standard ? new String(oneChar) : ".");
		}
		env.writeln("");
	}

	/**
	 * Prints line with hexadecimal strings separated by one space.
	 *
	 * @param env the shell's environment
	 * @param buf the byte buffer
	 * @param len the number of the bytes read
	 */
	private void printHex(Environment env, byte[] buf, int len) {	
		printHexLine(env, buf, len);
		if (len < PACKAGE_SIZE) {
			fillLine(env, len);
		}
		env.write("| ");
	}

	/**
	 * Prints {@code len} hexadecimal representation of bytes from selected file.
	 *
	 * @param env the shell's environment
	 * @param buf the byte buffer
	 * @param len the number of the bytes read
	 */
	private void printHexLine(Environment env, byte[] buf, int len) {
		for (int i = 1; i <= len; i++) {
			env.write(String.format("%02X ", buf[i - 1]));
			if (i == PACKAGE_SIZE / 2) {
				env.write("| ");
			}
		}
	}
	
	/**
	 * Fills hexadecimal strings line with spaces if line contains less than 16 bytes.
	 *
	 * @param env the shell's environment
	 * @param len the number of the bytes read
	 */
	private void fillLine(Environment env, int len) {
		int half = PACKAGE_SIZE / 2;
		int spaces = 0;
		
		if(len <= half){
			spaces = (half - len) * 3;
			int emptyHalfSpaces = half * 3 + 1;
			if(spaces > 0){
				env.write(String.format("%" + spaces + "s|%" + emptyHalfSpaces + "s", "", ""));
			} else {
				env.write(String.format("|%" + emptyHalfSpaces + "s", ""));
			}
		} else {
			spaces = (PACKAGE_SIZE - len) * 3;
			env.write(String.format("%" + spaces + "s", ""));
		}
	}
}
