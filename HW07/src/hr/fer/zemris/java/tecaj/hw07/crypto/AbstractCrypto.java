package hr.fer.zemris.java.tecaj.hw07.crypto;

import static hr.fer.zemris.java.tecaj.hw07.crypto.Converter.hexToBytes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * The abstract implementation of engines for decrypting and encrypting.
 * Has method {@code doWork} thats encrypts clear text or decrypts cipher text 
 * depending on the variable {@code encrypt}. There are two engines: {@link Decrypter}
 * and {@link Encrypter}.
 * 
 * @author Vjeco
 */
public abstract class AbstractCrypto {
	
	/** The size of one byte package */
	private final static int PACKAGE_SIZE = 4096;
	
	/** The password for encrypting/decrypting. */
	protected String password;
	
	/** The initialization vector for encrypting/decrypting. */
	protected String initVector;
	
	/** The cipher used for encrypting/decrypting text. */
	protected Cipher cipher;
	
	/** The flag for encrypted/decrypted mode. */
	private boolean encyrpt;
	
	/**
	 * Instantiates a new abstract implementation of engine for
	 * decrypting and encrypting.
	 *
	 * @param password the password for encrypting/decrypting
	 * @param initVector the initialization vector for encrypting/decrypting
	 * @param encrypt the flag for encrypted/decrypted mode
	 */
	public AbstractCrypto(String password, String initVector, boolean encrypt){
		this.password = password;
		this.initVector = initVector;
		this.encyrpt = encrypt;
		this.cipher = createCipher();
	}
	
	/**
	 * Creates (initialize) new cipher used for encrypting/decrypting. 
	 * Depending on flag {@code encrypt} cipher is initialized in encrypt 
	 * or decrypt mode. Also, {@code password} and {@code initVector} are
	 * used for initialization. This cipher uses a symmetric crypto-algorithm 
	 * AES which works with key size: 128 bit. Since AES is block cipher, it 
	 * always consumes 128 bit of data at a time.
	 *
	 * @return the cipher
	 */
	private Cipher createCipher() {
		SecretKeySpec keySpec = new SecretKeySpec(hexToBytes(password), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hexToBytes(initVector));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
		try {
			cipher.init(encyrpt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			System.err.println(e.getMessage());
			System.exit(0);
		}
		
		return cipher;
	}
	
	/**
	 * Method for encrypting clear text to cipher text or decrypting cipher text to
	 * clear text. Method does not read whole file into memory, instead, it reads
	 * 4 kB packages. Cipher encrypts/decrypts it immediately and writes it to 
	 * file got from parameter {@code toPath}.
	 *
	 * @param fromPath the file for reading
	 * @param toPath the to file for writing
	 */
	protected void doWork(Path fromPath, Path toPath){
		byte[] buf = new byte[PACKAGE_SIZE];
		
		try(FileInputStream fis = new FileInputStream(fromPath.toString());
			FileOutputStream fos = new FileOutputStream(toPath.toString())){
			while(true){
				int bytesRead = fis.read(buf);
				if(bytesRead < 1) break;
				byte[] outputBytes = cipher.update(buf, 0, bytesRead);
				fos.write(outputBytes);
			}
		
			try {
				fos.write(cipher.doFinal());
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
				System.exit(1);
			}
		} catch(IOException ex){
			System.err.println(ex.getMessage());
			System.exit(2);
		}
	}
}
