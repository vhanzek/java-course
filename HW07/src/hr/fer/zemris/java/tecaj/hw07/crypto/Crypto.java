package hr.fer.zemris.java.tecaj.hw07.crypto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The main class for checking file digest, encrypting, decrypting by
 * using classes {@link Digester}, {@link Decrypter}, {@link Encrypter}.
 * 
 * @author Vjeco
 */
public class Crypto {
	
	/** The reader for reading from standard input. */
	private static BufferedReader reader;
	
	/**
	 * Static block for initializing reader.
	 */
	static{
		reader = new BufferedReader(
					new InputStreamReader(System.in));
	}
	
	/**
	 * The main method. Program starts here. Program expects minimally two,
	 * maximally tree command line arguments. First argument represents type 
	 * of work - checking SHA-256 digest, encrypting, decrypting. If selected
	 * type of work is checking digest, then second argument represents path to
	 * file for digesting, however, if selected type of work is encrypting or
	 * decrypting, then second and third argument represent file for reading from
	 * (second) and file for writing to (third).
	 * 
	 * @param args the command line arguments
	 * @throws IOException signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if(args.length == 0 || args.length > 3){
			throw new IllegalArgumentException(
				"Illegal number of elements"
			);
		}
		
		String task = args[0];
		
		try {
			switch(task){
			case "checksha":
				processCheckSha(args);
				break;
			case "encrypt":
				processEncrypt(args);
				break;
			case "decrypt":
				processDecrpyt(args);
				break;
			default:
				throw new IllegalArgumentException(
					"Illegal first argument."
				);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
	}

	/**
	 * Method for checking SHA-256 digest of given file. It uses {@link DigestChecker} 
	 * for that type of work.
	 *
	 * @param args the command line arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void processCheckSha(String[] args) throws IOException {
		if(args.length > 2){
			throw new IllegalArgumentException(
				"Illegal number of elements"
			);
		}
		String fileName = args[1];
		Path digestTest = Paths.get("./" + args[1]).normalize().toAbsolutePath();
		
		System.out.printf("Please provide expected sha-256 digest for %s:%n>", fileName);
		String expDigest = reader.readLine();
		
		Digester checker = new Digester();		
		
		String digest = checker.digest(digestTest);
		if(digest.equals(expDigest)){
			System.out.printf("Digesting completed. Digest of %s matches expected digest.", fileName);
		} else {
			System.out.printf("Digesting completed. Digest of %s does not match the expected digest. "
							  + "Digest was: %s", fileName, digest);
		}
	}
	
	/**
	 * Method for encrypting file. It uses instance of a class {@link Encrypter}
	 * for that type of work.
	 *
	 * @param args the command line arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void processEncrypt(String[] args) throws IOException {
		String clearText = args[1];
		String cipherText = args[2];
		Path clearTextPath = Paths.get("./" + args[1]).normalize().toAbsolutePath();
		Path cipherTextPath = Paths.get("./" + args[2]).normalize().toAbsolutePath();
		createFile(cipherTextPath);
		
		String[] data = scan();
		
		Encrypter enc = new Encrypter(data[0], data[1]);
		enc.doWork(clearTextPath, cipherTextPath);
		
		System.out.printf("Encryption completed. Generated file %s based on file %s.%n", cipherText, clearText);
	}

	/**
	 * Method for decrypting file. It uses instance of a class {@link Decrypter}
	 * for that type of work.
	 *
	 * @param args the command line arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void processDecrpyt(String[] args) throws IOException {
		String cipherText = args[1];
		String clearText = args[2];
		Path cipherTextPath = Paths.get("./" + args[1]).normalize().toAbsolutePath();
		Path clearTextPath = Paths.get("./" + args[2]).normalize().toAbsolutePath();
		createFile(clearTextPath);
		
		String[] data = scan();
		
		Decrypter dec = new Decrypter(data[0], data[1]);
		dec.doWork(cipherTextPath, clearTextPath);
		
		System.out.printf("Decryption completed. Generated file %s based on file %s.%n", clearText, cipherText);
		
	}
	
	/**
	 * Creates new file in this folder. If file at the given path exists, method 
	 * deletes it and then makes new one.
	 *
	 * @param path the path of new file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void createFile(Path path) throws IOException {
		Files.deleteIfExists(path);
		Files.createFile(path);
	}

	/**
	 * Scans password and initialization vector for cipher
	 * used for encrypting/decrypting files.
	 *
	 * @return the password and initialization vector
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String[] scan() throws IOException {
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n>");
		String password = reader.readLine();
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n>");
		String initVector = reader.readLine();
		
		return new String[]{password, initVector};
	}
}
