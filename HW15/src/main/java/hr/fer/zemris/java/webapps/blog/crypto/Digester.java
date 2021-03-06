package hr.fer.zemris.java.webapps.blog.crypto;

import static hr.fer.zemris.java.webapps.blog.crypto.Converter.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The engine for digesting content of a file. It uses SHA, <i>Standard Hashing
 * Algorithm</i>, which means the digest will always be 256-bits long, no matter 
 * how long is the original file you digested. Generally speaking, the original 
 * data can not be reconstructed from the digest and this is not what the digests 
 * are used for.
 * 
 * @author Vjeco
 */
public class Digester {
	
	/**  The size of one byte package. */
	private final static int PACKAGE_SIZE = 4096;
	
	/** The instance of class used for digesting file content. */
	private static MessageDigest sha;
	
	static {
		try {
			sha = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}

	/**
	 * Method for digesting content of a given file. It returns hexadecimal of
	 * obtained digest.
	 *
	 * @param input the input
	 * @return the digested bytes as a hexadecimal string
	 */
	public static String digest(Path input){
		byte[] buf = new byte[PACKAGE_SIZE];
		try(FileInputStream fis = new FileInputStream(input.toString())){
			while(true){
				int bytesRead = fis.read(buf);
				if(bytesRead < 1) break;
				sha.update(buf, 0, bytesRead);
			}
		} catch (IOException ex){
			System.err.println(ex.getMessage());
			System.exit(0);
		}

		return bytesToHex(sha.digest());	
	}
	
	/**
	 * Method for digesting content of a given instance of {@link String} class. 
	 * It returns hexadecimal of obtained digest.
	 *
	 * @param string the string
	 * @return the digested bytes as a hexadecimal string
	 */
	public static String digest(String string){
		return bytesToHex(sha.digest(string.getBytes()));	
	}
}
