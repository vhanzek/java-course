package hr.fer.zemris.java.hw11.jnotepadpp.editor;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * The listener interface for receiving editor's document 
 * events. The class that is interested in processing a document
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEditorDocListener<code> method. When
 * the editorDoc event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EditorDocEvent
 */
public class EditorDocListener implements DocumentListener {
	
	/** The flag for keeping track if something in document has been modified. */
	private boolean changed;
	
	/** The currently used editor. */
	private Editor editor;
	
	/** The frame of the program. */
	private JNotepadPP frame;

	/**
	 * Instantiates a new editor's document listener, and registers
	 * itself to given editor's document.
	 *
	 * @param editor the editor
	 * @param frame the frame
	 */
	public EditorDocListener(Editor editor, JNotepadPP frame) {
		this.editor = editor;
		this.frame = frame;
		editor.getDocument().addDocumentListener(this);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		processChange();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		processChange();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		processChange();
	}
	
	/**
	 * Helper method for executing specified actions if something 
	 * in document has been modified.
	 */
	private void processChange() {
		if(!changed) {
			changed = true;
			editor.changeIcon();
		}
		frame.setSaveActionsEnabled(true);
	}
	
	/**
	 * Checks if editor's text has been modified.
	 *
	 * @return <code>true</code>, if is changed
	 * 		   <code>false</code> otherwise
	 */
	public boolean isChanged() {
		return changed;
	}
	
	/**
	 * Sets editor's text to modified or unmodified.
	 *
	 * @param changed the new flag
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}

}
