package hr.fer.zemris.java.cstr;

import java.util.Objects;

/**
 * The Class CString represent unmodifiable strings on which substring methods (and similar) are executed in O(1) complexity.
 * This principle is achieved by sharing array of characters between strings.
 * 
 * @author Vjeco
 */
public class CString {
	
	/** The array of characters in a CString. */
	private char[] data;
	
	/** The offset of the used string.*/
	private int offset;
	
	/** The length of the used string. */
	private int length;
	
	/**
	 * Instantiates a new CString. Uses only one part of the shared data, beginning on the offset and has given length.
	 * 
	 * @throws IllegalArgumentException if <code>data</code> is <code>null</code> or if offset or length are negative.
	 * @throws IndexOutOfBoundsException if sum of offset and length are bigger than data length.
	 *
	 * @param data the shared data of characters
	 * @param offset the offset of the current string
	 * @param length the length of the current string
	 */
	public CString(char[] data, int offset, int length) {
		if(Objects.isNull(data)){
			throw new IllegalArgumentException(
				"Data is null."
			);
		}
		if(offset + length > data.length){
			throw new IndexOutOfBoundsException(
				"New CString length goes out of bounds."
			);
		}
		if(offset < 0){
			throw new IllegalArgumentException(
				"Offset is negative."
			);
		}
		if(length < 0){
			throw new IllegalArgumentException(
				"Length is negative."
			);
		} 
		
		this.offset = offset;
		this.length = length;
		this.data = data;
	}	
	
	/**
	 * Instantiates a new CString. Uses whole shared data.
	 *
	 * @param data the array of characters
	 */
	public CString(char[] data) {
		this(data, 0, data.length);
	}
	
	/**
	 * Instantiates a new CString. If original uses whole internal character array this CString reuses that array, 
	 * otherwise creates array new array with minimal required size and copies data from original character array.
	 *
	 * @param original the original CString
	 */
	public CString(CString original){
		if(original.offset == 0 && original.length == original.data.length){
			this.data = original.data;
		} else {
			this.data = new char[original.length()];
			System.arraycopy(original.data, original.offset, this.data, 0, original.length());
		}
		
		this.offset = 0;
		this.length = original.length();		
	}
	
	/**
	 * Returns new CString based on given string.
	 *
	 * @param s the string for converting to CString
	 * @return the obtained CString
	 */
	public static CString fromString(String s){
		char[] stringData = s.toCharArray();
		return new CString(stringData, 0, stringData.length);
	}
	
	/**
	 * Returns length of the current used string.
	 *
	 * @return the length of the current used string
	 */
	public int length(){
		return length;
	}
	
	/**
	 * Returns character at given index.
	 * 
	 * @throws IndexOutOfBoundsException if given index is out of bounds
	 *
	 * @param index the index
	 * @return the character at specified index
	 */
	public char charAt(int index){
		if(index < 0 || index > length){
			throw new IndexOutOfBoundsException(
				"Given index is out of bounds."
			);
		}
		return data[offset + index];
	}
	
	/**
	 * Returns current used string as a array of characters.
	 *
	 * @return the used array of characters
	 */
	public char[] toCharArray(){
		char[] charArray = new char[length];
		System.arraycopy(data, offset, charArray, 0, length);
		return charArray;
	}
	
	/**
	 * Returns String representation of the class CString.
	 * 
	 * @return string representation of the internal character array
	 */
	public String toString(){
		if(length == 0) return "";
		
		StringBuilder sb = new StringBuilder();
		for(int i = offset; i < offset + length; i++){
			sb.append(data[i]);
		}
		
		return sb.toString();
	}
	
	/**
	 * Return index of the given character. If character does not exist, method returns -1.
	 *
	 * @param c the searched character
	 * @return the index of the searched character
	 */
	public int indexOf(char c){
		for(int i = offset; i < offset + length; i++){
			if(data[i] == c){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * Checks if CString starts with given CString.
	 * 
	 * @throws IllegalArgumentException if given CString is <code>null</code>.
	 *
	 * @param s the starting string
	 * @return true, if string starts with given string, false otherwise
	 */
	public boolean startsWith(CString s){
		if(Objects.isNull(s)){
			throw new IllegalArgumentException(
				"Given starting string is null."
			);
		}
		char[] start = s.toCharArray();
		
		for(int i = offset, len = start.length; i < offset + len; i++){
			if(data[i] != start[i - offset]){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if CString ends with given CString.
	 * 
	 * @throws IllegalArgumentException if given CString is <code>null</code>.
	 *
	 * @param s the ending string
	 * @return true, if string ends with given string, false otherwise
	 */
	public boolean endsWith(CString s){
		if(Objects.isNull(s)){
			throw new IllegalArgumentException(
				"Given ending string is null."
			);
		}
		char[] end = s.toCharArray();
		int dataEndIndex = offset + length;
		
		for(int i = dataEndIndex - end.length; i < dataEndIndex; i++){
			if(data[i] != end[i - dataEndIndex + end.length]){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if CString in its internal array of characters contains given CString.
	 *
	 * @param s the searched string
	 * @return true, if successful, false otherwise
	 */
	public boolean contains(CString s){
		if(Objects.isNull(s)){
			throw new IllegalArgumentException(
				"Given string is null."
			);
		}
		char[] string = s.toCharArray();
		
		for(int i = offset, len = string.length; i <= offset + length - len; i++){
			int j = 0;
			
			while(data[i + j] == string[j++]){
				if(j == len) return true;
			}
		}
	
		return false;
	}
	
	/**
	 * Method returns new CString which represents a part of original string. Complexity is O(1).
	 * 
	 * @throws IndexOutOfBoundsException if <code>startIndex</code> or <code>endIndex</code>
	 * @throws IllegalArgumentException if <code>startIndex</code> is bigger than <code>endIndex</code>
	 *
	 * @param startIndex the start index of the new CString
	 * @param endIndex the end index of the new CString
	 * @return the new CString which represents a part of original string
	 */
	public CString substring(int startIndex, int endIndex){
		if(startIndex < 0 || startIndex >= length){
			throw new IndexOutOfBoundsException(
				"Start index is out of bounds."
			);
		}
		if(endIndex < 0 || endIndex > length){
			throw new IndexOutOfBoundsException(
				"End index is out of bounds."
			);
		}
		if(endIndex <= startIndex){
			throw new IllegalArgumentException(
				"Start index is bigger than or equal to end index."
			);
		}
		return new CString(data, startIndex, endIndex - startIndex);
	}
	
	/**
	 * Returns first n characters of the current used string.
	 * 
	 * @throws IndexOutOfBoundsException if n is out of bounds
	 *
	 * @param n the number of the first n characters
	 * @return the obtained string
	 */
	public CString left(int n){
		if(n < 0 || n > length){
			throw new IndexOutOfBoundsException(
				"Given number is out of bounds."
			);
		}
		return new CString(toCharArray(), 0, n);
	}
	
	/**
	 * Returns last n characters of the current used string.
	 * 
	 * @throws IndexOutOfBoundsException if n is out of bounds
	 *
	 * @param n the number of the last n characters
	 * @return the obtained string
	 */
	public CString right(int n){
		if(n < 0 || n > length){
			throw new IndexOutOfBoundsException(
				"Given number is out of bounds."
			);
		}
		return new CString(toCharArray(), length - n, n);
	}
	
	/**
	 * Concatenates given string to this one and returns the result
	 *
	 * @param s the string to be added 
	 * @return the the result string
	 */
	public CString add(CString s){
		int totalLength = length + s.length();
		char[] newData = new char[totalLength];
		
		System.arraycopy(toCharArray(), 0, newData, 0, length);
		System.arraycopy(s.toCharArray(), 0, newData, length, s.length());
		
		return new CString(newData);
	}
	
	/**
	 * Replaces all of the <code>oldChar</code> with the <code>newChar</code> in current used string.
	 *
	 * @param oldChar the old character which needs to be replaced with new char
	 * @param newChar the new character
	 * @return the result string
	 */
	public CString replaceAll(char oldChar, char newChar){
		char[] newData = new char[length];
		
		for(int i = offset; i < offset + length; i++){
			if(data[i] == oldChar){
				newData[i - offset] = newChar;
			} else {
				newData[i - offset] = data[i];
			}
		}
		
		return new CString(newData);
		
	}
	
	/**
	 * Replaces all of the <code>oldStr</code> with the <code>newStr</code> in current used string.
	 *
	 * @param oldStr the old string which needs to be replaced with the new string
	 * @param newStr the new string
	 * @return the result string
	 */
	public CString replaceAll(CString oldStr, CString newStr){
		StringBuilder sb = new StringBuilder();
		char[] oldChars = oldStr.toCharArray();
		char[] newChars = newStr.toCharArray();
		int j;
		
		for(int i = offset; i < offset + length; i++){
			j = 0;
			
			if(data[i] == oldChars[j]){
				while(data[i + j] == oldChars[j]){
					j++;
					if(i + j == length || j == oldStr.length) break;
				}
				if(j == oldStr.length){
					j = 0;
					i += oldStr.length - 1;
					while(j != newStr.length){
						sb.append(newChars[j++]);
					}
				} else {
					while(j-- + 1 != 0){
						sb.append(data[i++]);
					}
					i--;
				}
			} else {
				sb.append(data[i]);
			}
		}
		
		return fromString(sb.toString());
	}	
}
