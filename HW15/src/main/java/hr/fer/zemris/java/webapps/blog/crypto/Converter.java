package hr.fer.zemris.java.webapps.blog.crypto;

/**
 * This class consists only static methods that convert hexadecimal strings to
 * bytes, and vice-versa.
 * 
 * @author Vjeco
 */
public class Converter {
	
	/** The hexadecimal characters. */
	protected final static String hexCharacters = "0123456789abcdef";
	
	/**
	 * Converts hexadecimal string to byte array.
	 *
	 * @param input the input to convert
	 * @return the output byte array
	 */
	public static byte[] hexToBytes(String input){
		checkInput(input);
		int len = input.length();
		byte[] data = new byte[len/2]; 
		for (int i = 0; i < len; i += 2) {
	        data[i/2] = (byte) ((Character.digit(input.charAt(i), 16) << 4) + 
	        					 Character.digit(input.charAt(i+1), 16));
	    }
	    return data;
	}

	/**
	 * Converts bytes to hexadecimal string.
	 *
	 * @param bytes the input byte array
	 * @return the output string
	 */
	public static String bytesToHex(byte[] bytes) {
		if(bytes == null){
			throw new IllegalArgumentException(
				"Input must not be null."
			);
		}
		final char[] hexArray = hexCharacters.toCharArray();
	    char[] hexChars = new char[bytes.length * 2];
	    for (int i = 0; i < bytes.length; i++) {
	        int v = bytes[i] & 0xFF;
	        hexChars[i * 2] = hexArray[v >>> 4];
	        hexChars[i * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	/**
	 * Method for checking hexadecimal input.
	 * 
	 * @param input the hexadecimal input
	 * @throws IllegalArgumentException 
	 * 					if input is {@code null} or if input contains 
	 * 					some non-hexadecimal characters								
	 */
	private static void checkInput(String input) {
		if(input == null){
			throw new IllegalArgumentException(
				"Input must not be null."
			);
		}
		for(int i = 0, len = input.length(); i < len; i++){
			if(!hexCharacters.contains(String.valueOf(input.charAt(i)))){
				throw new IllegalArgumentException(
					"Input must be a hexadecimal number."
				);
			}
		}
	}
}
