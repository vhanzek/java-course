package hr.fer.zemris.java.hw11.jnotepadpp.editor;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * The listener interface for receiving editor's caret events.
 * The class that is interested in processing a editorCaret
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEditorCaretListener<code> method. When
 * the editorCaret event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EditorCaretEvent
 * 
 * @author Vjeran
 */
public class EditorCaretListener implements CaretListener {
	
	/** The frame of program. */
	private JNotepadPP frame;
	
	/**
	 * Instantiates a new editor's caret listener.
	 *
	 * @param frame the frame of the program
	 */
	public EditorCaretListener(JNotepadPP frame) {
		this.frame = frame;
	}
	
	@Override
	public void caretUpdate(CaretEvent e) {
		int selectionLen = Math.abs(e.getDot() - e.getMark());
		changeCaseActions(e, selectionLen);
		updateStatusBar(e, selectionLen);
	}

	/**
	 * Method for updating {@link JNotepadPP} status bar. For every character
	 * written in editor, status bar changes its state appropriately.
	 *
	 * @param e the caret event
	 * @param selectionLen the length of selected text
	 */
	private void updateStatusBar(CaretEvent e, int selectionLen) {
		Editor editor = (Editor) e.getSource();		
		JPanel infoPanel = (JPanel) frame.getContentPane().getComponent(2);
		
		//length label
		JLabel lengthLabel = (JLabel) infoPanel.getComponent(0);
		lengthLabel.setText("lenght : " + editor.getText().length());
		
		//document info label
		JLabel docInfoLabel = (JLabel) infoPanel.getComponent(1);

		int col = 0;
		try {
			col = EditorUtil.getColumn(editor);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
		
		docInfoLabel.setText(" Ln : " + editor.getLineCount() + 
							 "   Col : " + col + 
							 "   Sel : " + selectionLen + " | " + 
							 	(selectionLen > 0 ? EditorUtil.getNoLinesInSelectedText(editor) : 0) + " ");
	}

	/**
	 * Method for checking if some text in editor has been selected,
	 * and if there is selected text, method enables specified components
	 * in {@link JNotepadPP} frame by calling method {@code setSelectionActionsEnabled}.
	 *
	 * @param e the caret event
	 * @param selectionLen the lenght of the selected text
	 */
	private void changeCaseActions(CaretEvent e, int selectionLen) {
		frame.setSelectionActionsEnabled(selectionLen > 0 ? true : false);
	}
}
