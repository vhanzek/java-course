package hr.fer.zemris.java.tecaj.hw07.crypto;

/**
 * The engine used for encrypting clear text to cipher text.
 * 
 * @author Vjeco
 */
public class Encrypter extends AbstractCrypto{

	/**
	 * Instantiates a new engine for encrypting with variable
	 * {@code encrypt} set to {@code true}.
	 *
	 * @param password the password
	 * @param initVector the initialization vector
	 */
	public Encrypter(String password, String initVector) {
		super(password, initVector, true);
	}

}
