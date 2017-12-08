package hr.fer.zemris.java.hw11.jnotepadpp.editor;

import java.nio.file.Path;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * The class representing editor used in {@link JNotepadPP} program.
 * It has methods {@link #isModified()} and {@link #setUnmodified()}
 * for checking if there is unsaved content in editor. Also, it contains
 * {@link #filePath} which is the path to currently opened file.
 * 
 * @author Vjeran
 */
public class Editor extends JTextArea {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5850966660005099944L;

	/** The path to currently opened file. */
	private Path filePath;
	
	/** The document listener. */
	private EditorDocListener docListener;
	
	/** The close tab used by this editor. */
	private CloseTab closeTab;
	
	/**
	 * Instantiates a new {@link JNotepadPP} editor.
	 *
	 * @param filePath path to currently opened file
	 * @param closeTab the editor's close tab
	 */
	public Editor(Path filePath, CloseTab closeTab) {
		this.filePath = filePath;
		this.closeTab = closeTab;
	}

	/**
	 * Gets the path to currently opened file.
	 *
	 * @return the path to currently opened file
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Checks if editor has been modified.
	 *
	 * @return <code>true<code>, if is modified
	 * 		   <code>false</code> otherwise
	 */
	public boolean isModified(){
		return docListener.isChanged();
	}
	
	/**
	 * Sets the editor as unmodified.
	 */
	public void setUnmodified(){
		docListener.setChanged(false);
	}
	
	/**
	 * Sets the editor's document listener.
	 *
	 * @param listener the document listener
	 */
	public void setDocListener(EditorDocListener listener) {
		this.docListener = listener;
	}
	
	/**
	 * Method for changing currently used icon in editor's tab.
	 */
	public void changeIcon(){
		closeTab.changeIcon();
	}
}
