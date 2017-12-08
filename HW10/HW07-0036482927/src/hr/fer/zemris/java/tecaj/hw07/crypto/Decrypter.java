package hr.fer.zemris.java.tecaj.hw07.crypto;

/**
 * The engine used for decrypting file from cipher text to clear text.
 * 
 * @author Vjeco
 */
public class Decrypter extends AbstractCrypto{

	/**
	 * Instantiates a new engine for decrypting with variable
	 * {@code encrypt} set to {@code false}.
	 *
	 * @param password the password
	 * @param initVector the initialization vector
	 */
	public Decrypter(String password, String initVector) {
		super(password, initVector, false);
	}
}
