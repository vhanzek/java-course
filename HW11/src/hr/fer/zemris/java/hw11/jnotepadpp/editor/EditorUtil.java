package hr.fer.zemris.java.hw11.jnotepadpp.editor;

import java.text.Collator;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * Utility class with helper static methods for editor used in 
 * {@link JNotepadPP} class.
 * 
 * @author Vjeran
 */
public class EditorUtil {
	
	/** The descending sort. */
	public static final String DESC = "DESC";
	
	/** The ascending sort. */
	public static final String ASC = "ASC";
	
	/**
	 * Gets the information of the currently used document.
	 * It returns three values - length of the text, number
	 * of not blank characters, and number of lines.
	 *
	 * @param editor the editor
	 * @return the information about the document
	 */
	public static int[] getInformation(Editor editor){
		String text = editor.getText();
		int[] info = new int[3];
		info[2] = 1;
		
		info[0] = text.length();
		
		char[] charArray = text.toCharArray();		
		for(char c : charArray){
			if(Character.isWhitespace(c)){
				info[1]++;
			}
			if(c == '\n'){
				info[2]++;
			}
		}
		return info;
	}
	
	/**
	 * <p>Gets the cardinal number of the current column, i.e. 
	 * gets the caret position in a line.</p> 
	 * 
	 * <p>For example, if caret is at the beginning of the line, 
	 * method returns 0.</p>
	 *
	 * @param editor the editor
	 * @return the caret position
	 * @throws BadLocationException the bad location exception
	 */
	public static int getColumn(Editor editor) throws BadLocationException{
		Caret caret = editor.getCaret();
		return -editor.getLineStartOffset(editor.getLineOfOffset(caret.getDot())) + caret.getDot() + 1;
	}
	
	/**
	 * Gets the number of selected lines in the editor.
	 *
	 * @param editor the editor
	 * @return the number of selected lines
	 */
	public static int getNoLinesInSelectedText(Editor editor) {
		String text = editor.getSelectedText();
		char[] charArray = text.toCharArray();
		int counter = 0;
		
		for(char ch : charArray){
			if(ch == '\n'){
				counter++;
			}
		}
		return counter + 1;
	}
	
	/**
	 * <p>Method for replacing selected text in the editor with the 
	 * new one. {@link CaseChanger} is an interface that defines how the
	 * result string is going to look like.</p>
	 * 
	 * <p>Used in {@link JNotepadPP} class for switching case of the 
	 * selected text.</p>
	 *
	 * @param editor the editor
	 * @param caseChanger the case changer
	 */
	public static void replaceSelectedText(Editor editor, CaseChanger caseChanger){
		String selectedText = editor.getSelectedText();
		if(selectedText == null){
			return;
		}
		
		String newText = caseChanger.apply(selectedText);
		Document doc = editor.getDocument();
		Caret caret = editor.getCaret();
		
		int dot = caret.getDot();
		int mark = caret.getMark();
		int offset = Math.min(dot, mark);
		
		try {
			doc.remove(offset, newText.length());
			doc.insertString(offset, newText, null);
			caret.setDot(dot);
			caret.moveDot(mark);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the range of the selected text i.e. returns two
	 * parameters, number of the line where selection begins,
	 * and number of the line where selection ends.
	 *
	 * @param editor the editor
	 * @return the range of the selected text
	 */
	public static int[] getSelectionRange(Editor editor){
		Caret caret = editor.getCaret();
		
		int lineNo1 = 0;
		int lineNo2 = 0;
		int selectionStart = Math.min(caret.getDot(), caret.getMark());
		int selectionEnd = Math.max(caret.getDot(), caret.getMark());
		try {
			lineNo1 = editor.getLineOfOffset(selectionStart);
			lineNo2 = editor.getLineOfOffset(selectionEnd);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		return new int[]{lineNo1, lineNo2};
	}
	
	/**
	 * Method for removing duplicated lines from selected text.
	 * Only the first occurrence is retained.
	 *
	 * @param editor the editor
	 */
	public static void removeDuplicateLines(Editor editor){
		Document doc = editor.getDocument();
		Set<String> lines = new LinkedHashSet<>();
		StringBuilder sb = new StringBuilder();
		int[] range = getSelectionRange(editor);

		try {
			if(range[0] == range[1]) return;
			
			int start = editor.getLineStartOffset(range[0]);
			int end = editor.getLineEndOffset(range[1]);
		
			for(int i = range[0]; i <= range[1]; i++){
				int lineStart = editor.getLineStartOffset(i);
				int lineEnd = editor.getLineEndOffset(i);
					
				lines.add(doc.getText(lineStart, lineEnd - lineStart));
			}
			
			doc.remove(start, end - start);
			lines.forEach(s->sb.append(s));
			doc.insertString(start, sb.toString(), null);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Method for sorting selected text. Sort type (ascending and
	 * descending) is defined by {@code sortType}. Also, for every 
	 * supported language in {@link JNotepadPP} program, method
	 * uses instance of class {@link Collator} for comparing certain
	 * characters.
	 *
	 * @param editor the editor
	 * @param sortType the sort type
	 * @param collator appropriate collator
	 */
	public static void sortSelectedText(Editor editor, String sortType, Collator collator) {
		Document doc = editor.getDocument();
		int[] range = getSelectionRange(editor);
		
		try {			
			for(int i = range[0]; i <= range[1]; i++){
				int lineStart = editor.getLineStartOffset(i);
				int lineEnd = editor.getLineEndOffset(i);
					
				String text = doc.getText(lineStart, lineEnd - lineStart);
				if(i != range[1]) text = text.substring(0, lineEnd - (lineStart + 1));
				doc.remove(lineStart, lineEnd - lineStart);
				
				String[] split = text.split("");
			    Arrays.sort(split, collator);
			    String sorted = "";
			    for (int j = 0, n = split.length; j < split.length; j++){
			      sorted += (sortType.equals(ASC) ? split[j] : split[n - (j+1)]);
			    }
			    if(i != range[1]) sorted += "\n";
				doc.insertString(lineStart, sorted, null);
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The interface CaseChanger used as a strategy in 
	 * {@code replaceSelectedText} method.
	 * 
	 * @author Vjeran
	 */
	public interface CaseChanger {
		
		/**
		 * Method that changes given text.
		 *
		 * @param text the text
		 * @return the string
		 */
		String apply(String text);
	}

	
}
