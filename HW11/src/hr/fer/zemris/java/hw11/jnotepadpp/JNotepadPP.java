package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw11.jnotepadpp.editor.ClockLabel;
import hr.fer.zemris.java.hw11.jnotepadpp.editor.CloseTab;
import hr.fer.zemris.java.hw11.jnotepadpp.editor.Editor;
import hr.fer.zemris.java.hw11.jnotepadpp.editor.EditorCaretListener;
import hr.fer.zemris.java.hw11.jnotepadpp.editor.EditorDocListener;
import hr.fer.zemris.java.hw11.jnotepadpp.editor.EditorUtil;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.localization.LocalizationProvider;

/**
 * The main class which represents simple implementation of program
 * Notepad++.
 * 
 * @author Vjeran
 */
public class JNotepadPP extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 751653141532945136L;
	
	/** The second part of the program's title. */
	private static final String TITLE_NAME = " - JNotepad++";
	
	/** The new tab title. */
	private static final String NEW_TAB_TITLE = "new";
	
	/** The currently used editor. */
	private Editor editor;
	
	/** The tabbed pane of the frame. */
	private JTabbedPane tabbedPane;
	
	/** The currently opened file path in used editor. */
	private Path openedFilePath;
	
	/** The class for handling language change. */
	private FormLocalizationProvider flp;
	
	{
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
	}
	
	/**
	 * The main method. Program starts here by invoking EDT.
	 *
	 * @param args the arguments. Command line arguments, unused in this example.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

	/**
	 * Instantiates a new JNotepad++.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setBounds(300, 300, 600, 600);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				removeDocuments(0, tabbedPane.getTabCount());
			}
		});
		
		initGUI();
	}

	/**
	 * Method for initializing program's GUI.
	 */
	private void initGUI() {		
		JMenuBar menuBar = createMenuBar();
		setJMenuBar(menuBar);
		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		//tabbed pane
		createTabbedPane();
		cp.add(tabbedPane, BorderLayout.CENTER);
		
		//toolbar
		JToolBar toolbar = createToolBar();
		cp.add(toolbar, BorderLayout.PAGE_START);
		
		JPanel infoPanel = createStatusBar();
		cp.add(infoPanel, BorderLayout.PAGE_END);
	}
	

	

	/*-------------------------------------------- Creation helpers ------------------------------------------------------*/

	/**
	 * Helper method for creating menu bar used in this program.
	 * Menu bar contains 4 menus - File, Edit, Tools and Languages.
	 *
	 * @return the new {@link JMenuBar} 
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//file menu
		LocJMenu fileMenu = new LocJMenu("File", flp);
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(newDocumentAction));
		fileMenu.add(new JMenuItem(openDocumentAction));
		fileMenu.add(new JMenuItem(saveDocumentAction));
		fileMenu.add(new JMenuItem(saveAsDocumentAction));
		fileMenu.add(new JMenuItem(statisticsAction));
		fileMenu.add(new JMenuItem(closeDocumentAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exitAction));
		
		//edit menu
		LocJMenu editMenu = new LocJMenu("Edit", flp);
		menuBar.add(editMenu);
		
		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));
		
		//tools menu
		LocJMenu toolsMenu = new LocJMenu("Tools", flp);
		menuBar.add(toolsMenu);
		
		LocJMenu caseMenu = new LocJMenu("ChangeCase", flp);
		caseMenu.add( new JMenuItem(upperCaseAction));
		caseMenu.add(new JMenuItem(lowerCaseAction));
		caseMenu.add(new JMenuItem(toggleCaseAction));

		LocJMenu sortMenu = new LocJMenu("Sort", flp);
		sortMenu.add(new JMenuItem(ascendingSortAction));
		sortMenu.add(new JMenuItem(descendingSortAction));
		toolsMenu.add(sortMenu);
		
		toolsMenu.add(new JMenuItem(uniqueAction));
		
		//languages menu
		LocJMenu languagesMenu = new LocJMenu("Languages", flp);
		languagesMenu.add(new JMenuItem(englishLanguageAction));
		languagesMenu.add(new JMenuItem(croatianLanguageAction));
		languagesMenu.add(new JMenuItem(germanLanguageAction));
		menuBar.add(languagesMenu);
		
		return menuBar;
	}
	
	/**
	 * Helper method for creating specified action. It sets its
	 * accelerator key, mnemonic key, short description and its icon.
	 *
	 * @param action the action
	 * @param accKey the accelerator key
	 * @param mnemonicKey the mnemonic key
	 * @param description the short description
	 * @param iconName the icon name
	 */
	private void createAction(Action action, String accKey, int mnemonicKey, String description, String iconName) {
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(accKey));
		action.putValue(Action.MNEMONIC_KEY, mnemonicKey);
		action.putValue(Action.SHORT_DESCRIPTION, description);	
		action.putValue(Action.SMALL_ICON, Icons.getInstance().getIcon(iconName));
	}
	
	/**
	 * Helper method for creating tabbed pane of the program.
	 * At the beginning  of the program there is one tab opened 
	 * and titled as new.
	 */
	private void createTabbedPane() {
		tabbedPane = new JTabbedPane();
		addTab(NEW_TAB_TITLE, null);
		
		tabbedPane.addChangeListener(l -> {
			setCurrentFrameTitle();
			editor = (Editor) ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport().getView();
			openedFilePath = editor.getFilePath();
			setSaveActionsEnabled(editor.isModified());
		});
	}

	/**
	 * <p>Helper method for creating tool bar. All buttons in a tool bar
	 * are shown as icons representing certain actions.</p>
	 * 
	 * <p>For example, button for saving current editor's text has icon 
	 * of floppy disc.</p>
	 *
	 * @return the new {@link JToolBar}
	 */
	private JToolBar createToolBar(){
		JToolBar toolbar = new JToolBar();
		
		toolbar.add(newDocumentAction);
		toolbar.add(openDocumentAction);
		toolbar.add(saveDocumentAction);
		toolbar.add(saveAsDocumentAction);
		toolbar.add(statisticsAction);
		toolbar.add(closeDocumentAction);
		
		toolbar.addSeparator();
		
		toolbar.add(cutAction);
		toolbar.add(copyAction);
		toolbar.add(pasteAction);
		
		toolbar.addSeparator();
		
		toolbar.add(upperCaseAction);
		toolbar.add(lowerCaseAction);
		toolbar.add(toggleCaseAction);
		
		toolbar.addSeparator();
		
		toolbar.add(ascendingSortAction);
		toolbar.add(descendingSortAction);
		
		return toolbar;
	}
	
	/**
	 * Helper method for creating editor's status bar. It contains 
	 * three {@link JLabel}s. First one shown length of the text,
	 * second one shows number of lines, current caret position in a line
	 * and selection range. The third label represents clock.
	 *
	 * @return the new status bar
	 */
	private JPanel createStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		
		JLabel lengthLabel = new JLabel("length : 0");
		lengthLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		
		JLabel docInfoLabel = new JLabel(" Ln : 1   Col : 1   Sel : 0 | 0 ");
		docInfoLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		
		JLabel clockLabel = new ClockLabel();
		clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		panel.add(lengthLabel);
		panel.add(docInfoLabel);
		panel.add(clockLabel);
		
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		
		return panel;
	}
	
	
	/*-------------------------------------------- Frame helpers ------------------------------------------------------*/

	/**
	 * Method for checking and removing documents if there is any unsaved 
	 * document in given range. It checks only tabs beginning at the {@code offset} 
	 * and ending at {@code offset} + {@code length}. If there is only one tab with 
	 * unmodified i.e. saved editor, when that tab is closed, whole program will stop
	 * working.
	 *
	 * @param offset the offset
	 * @param length the length
	 */
	public void removeDocuments(int offset, int length) {
		boolean cancel = false;
		for(int i = offset; i < offset + length; i++){
			tabbedPane.setSelectedIndex(i);
			Editor editor = (Editor) ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport().getView();
			int reply = 0;
			if (editor.isModified()){
	            reply = JOptionPane.showConfirmDialog(
	                		 		JNotepadPP.this, 
	                		 		"Do you want to save file " + (editor.getFilePath() != null ? 
	                		 									   editor.getFilePath() : 
	                		 									   "new") + "?", 
	                		 		"System message",
	                		 		JOptionPane.YES_NO_CANCEL_OPTION
	                		 );
	             if (reply == JOptionPane.YES_OPTION) {
	                if(saveAs()) writeToFile();
	             } else if (reply == JOptionPane.CANCEL_OPTION){
	            	 cancel = true;
	            	 break;
	             }
			}
		}
		
		if(offset != 0 && length == 1 ||
		   offset == 0 && tabbedPane.getTabCount() != 1){
			tabbedPane.remove(offset);
		} else {
			if(!cancel) dispose();
		}
	}
	
	/**
	 * Method for adding new tab i.e. new editor.
	 *
	 * @param title the title of the editor 
	 * @param filePath the file path of the new editor
	 */
	private void addTab(String title, Path filePath) {
		CloseTab closeTab = new CloseTab(tabbedPane, this);
		Editor newEditor = new Editor(filePath, closeTab);
		
		newEditor.setDocListener(new EditorDocListener(newEditor, this));
		newEditor.addCaretListener(new EditorCaretListener(this));
		
		JScrollPane scrollEditor = new JScrollPane(newEditor);		
		
		tabbedPane.addTab(title, scrollEditor);
		tabbedPane.setSelectedComponent(scrollEditor);
		
		int currentIndex = tabbedPane.getSelectedIndex();
		tabbedPane.setTabComponentAt(currentIndex, closeTab);
		tabbedPane.setToolTipTextAt(currentIndex, filePath != null ? 
								  				  filePath.toAbsolutePath().toString() :
								  				  title);
		
		editor = newEditor;
		openedFilePath = filePath;
		setCurrentFrameTitle();
		setSelectionActionsEnabled(false);
		setSaveActionsEnabled(false);
	}
	
	/**
	 * Sets the current frame title.
	 */
	private void setCurrentFrameTitle() {
		setTitle(tabbedPane.getToolTipTextAt(tabbedPane.getSelectedIndex()) + TITLE_NAME);
	}
	
	/**
	 * Helper method for changing all necessary titles when selected 
	 * tab changes.
	 */
	private void changeTitles() {
		int currentIndex = tabbedPane.getSelectedIndex();
		tabbedPane.setToolTipTextAt(currentIndex, openedFilePath.toAbsolutePath().toString());
		tabbedPane.setTitleAt(currentIndex, openedFilePath.getFileName().toString());
		setCurrentFrameTitle();
		
		tabbedPane.getTabComponentAt(currentIndex).revalidate();
	}
	
	
	
	/*-------------------------------------------- File helpers ------------------------------------------------------*/
	
	/**
	 * Method which opens {@link JFileChooser} and ask user to choose
	 * where and under what name certain document will be saved. If
	 * selected file already exist, user is asked if he wants to replace
	 * it with the new one.
	 *
	 * @return <code>true</code>, if successful
	 * 		   <code>false</code> otherwise 
	 */
	private boolean saveAs() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save As");
		
		while(true){
			int retValue = fc.showSaveDialog(JNotepadPP.this);
			if (retValue != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
					JNotepadPP.this,
					"Nothing is saved.",
					"Warning",
					JOptionPane.WARNING_MESSAGE
				);
				return false;
			}
			openedFilePath = fc.getSelectedFile().toPath();
			
			if(Files.exists(openedFilePath)){
				 int reply = JOptionPane.showConfirmDialog(
								JNotepadPP.this,
								openedFilePath.getFileName() + " already exists.\nDo you want to replace it?",
								"Confirm Save As",
								JOptionPane.YES_NO_OPTION
						 	);
				
				if (reply == JOptionPane.YES_OPTION){
					break;
				}
			} else break;
		}
		
		return true;
	}
	
	/**
	 * Method for writing text of currently used editor to file
	 * selected in {@link #saveAs()} method.
	 */
	private void writeToFile() {
		byte[] content = editor.getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(openedFilePath, content);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(
				JNotepadPP.this,
				"Error writing file " + openedFilePath,
				"Error",
				JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		
		setSaveActionsEnabled(false);
		editor.setUnmodified();
		editor.changeIcon();
		changeTitles();
	}

	


	/*------------------------------------------------- Actions -----------------------------------------------------------*/
	
	/**
	 * Enables or disables, depending on parameter {@code enabled}, 
	 * save actions ({@link #saveDocumentAction} and {@link #saveAsDocumentAction}.
	 *
	 * @param enabled <code>true</code> if actions will be enabled
	 * 				  <code>false</code> otherwise
	 */
	public void setSaveActionsEnabled(boolean enabled){
		saveAsDocumentAction.setEnabled(enabled);
		saveDocumentAction.setEnabled(enabled);
	}
	
	/**
	 * Enables or disables, depending on parameter {@code enabled}, all
	 * actions that are dependent on length of selected text. If no text is
	 * selected (length = 0), actions will be disabled, otherwise, if the is
	 * some text selected, they will be enabled.
	 *
	 * @param enabled <code>true</code> if actions will be enabled
	 * 				  <code>false</code> otherwise
	 */
	public void setSelectionActionsEnabled(boolean enabled) {
		upperCaseAction.setEnabled(enabled);
		lowerCaseAction.setEnabled(enabled);
		toggleCaseAction.setEnabled(enabled);
		ascendingSortAction.setEnabled(enabled);
		descendingSortAction.setEnabled(enabled);
	}
	
	//File menu actions
	
	/** 
	 * The action for creating new document, i.e. creating new tab. 
	 */
	private final Action newDocumentAction = new LocalizableAction("New", flp) {

		private static final long serialVersionUID = 7899368972956034799L;

		{
			createAction(this, "control N", KeyEvent.VK_N, "Add new editor.", "new");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			addTab(NEW_TAB_TITLE, null);
		}
	};
	
	/** 
	 * The action for opening file that already exists on the disc.
	 * User is asked which file wants to open. After file has been selected,
	 * content of that file is copied to newly added editor. 
	 */
	private final Action openDocumentAction = new LocalizableAction("Open", flp) {

		private static final long serialVersionUID = -2796634880921047137L;

		{
			createAction(this, "control O", KeyEvent.VK_O, "Open existing file from disc.", "open");
		}
		
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open");
			
			int retValue = fc.showOpenDialog(JNotepadPP.this);
			if (retValue != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File file = fc.getSelectedFile();
			Path filePath = file.toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
					JNotepadPP.this,
					"File " + file.getAbsolutePath() + " does not exist!",
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			byte[] content;
			try {
				content = Files.readAllBytes(filePath);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(
					JNotepadPP.this,
					"Error reading file " + file.getAbsolutePath(),
					"Error",
					JOptionPane.ERROR_MESSAGE
				);
				return;
			}
			
			String text = new String(content, StandardCharsets.UTF_8);
		
			String fileName = filePath.getFileName().toString();
			addTab(fileName, filePath);
			editor.setText(text);
		}
	};
	
	/** 
	 * The action for saving file (text in current used editor) to disc.
	 * User is asked where and under what file needs to be saved. If file already
	 * exist, a user is again asked if he wants to replace it. Also, if editor has not
	 * been modified i.e. if text has been saved, program automatically saves
	 * editor's text to file associated with early mentioned editor. After file has 
	 * been selected, content of that editor is copied to selected file. 
	 */
	private final Action saveDocumentAction = new LocalizableAction("Save", flp) {

		private static final long serialVersionUID = -3330931000709851504L;
		
		{
			createAction(this, "control S", KeyEvent.VK_S, "Save current file to disc.", "save");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean flag = true;
			if (openedFilePath == null) {
				flag = saveAs();
			}
			if(flag) {
				writeToFile();
			}
		}
	};
	
	/** 
	 * The action similar to {@link #saveDocumentAction}, but it always asks
	 * user where and under which name file wants to be saved.
	 */
	private final Action saveAsDocumentAction = new LocalizableAction("SaveAs", flp) {

		private static final long serialVersionUID = 6366520240770468350L;

		{
			createAction(this, "control alt pressed S", KeyEvent.VK_A, "Save current file to disc under specified name.", "save as");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (saveAs()) writeToFile();
		}
	};
	
	/** 
	 * The action that shows editor's statistics in new {@link JOptionPane}.
	 */
	public final Action statisticsAction = new LocalizableAction("Stat", flp) {

		private static final long serialVersionUID = 70178771341626582L;
		
		{
			createAction(this, "control shift pressed S", KeyEvent.VK_T, "Get document statistics.", "statistics");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] info = EditorUtil.getInformation(editor);
			JOptionPane.showMessageDialog(
				JNotepadPP.this, 
				"Your document has " + info[0] + " character(s), " + info[1] + 
				" non-blank character(s) and " + info[2] + " line(s).", 
				"Statistics", 
				JOptionPane.INFORMATION_MESSAGE
			);
		}		
	};
	
	/** 
	 * The action for closing specified document i.e. removing selected tab.
	 */
	public final Action closeDocumentAction = new LocalizableAction("Close", flp) {
		
		private static final long serialVersionUID = -7990560770170460604L;
		
		{
			createAction(this, "control W", KeyEvent.VK_L, "Close current tab.", "close");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int currentIndex = tabbedPane.getSelectedIndex();
			removeDocuments(currentIndex, 1);
		}
	};
	
	/** 
	 * The action for exiting whole program. Before exit, if there is any unsaved
	 * document, user is asked if he wants to save specified documents.
	 */
	private final Action exitAction = new LocalizableAction("Exit", flp) {
		
		private static final long serialVersionUID = -7990560770170460604L;

		{
			createAction(this, "alt F4", KeyEvent.VK_E, "Exit program.", "exit");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			removeDocuments(0, tabbedPane.getTabCount());
		}
	};
	
	//Edit menu actions
	/** 
	 * The action which cuts selected text in a document.
	 */
	private final Action cutAction = new LocalizableAction("Cut", flp) {

		private static final long serialVersionUID = 70178771341626582L;

		{
			createAction(this, "control X", KeyEvent.VK_T, "Remove selected text and copy it into clipboard.", "cut");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.cut();
		}
	};
	
	/** 
	 * The action which copies selected text into clipboard.
	 */
	private final Action copyAction = new LocalizableAction("Copy", flp) {

		private static final long serialVersionUID = 7628783364274402725L;

		{
			createAction(this, "control C", KeyEvent.VK_C, "Copy selected text into clipboard.", "copy");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.copy();
		}
	};
	
	/** 
	 * The action which pastes text from clipboard to editor.
	 */
	private final Action pasteAction = new LocalizableAction("Paste", flp) {

		private static final long serialVersionUID = 1623178307979843751L;

		{
			createAction(this, "control V", KeyEvent.VK_P, "Paste text from clipboard.", "paste");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			editor.paste();
		}
	};
	
	
	//Tools menu actions
	/** 
	 * The action for changing selected text to upper case.
	 */
	private final Action upperCaseAction = new LocalizableAction("ToUpper", flp) {

		private static final long serialVersionUID = 8561091307169087482L;
		
		{
			createAction(this, "control U", KeyEvent.VK_U, "Set selected text to upper case.", "upper");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EditorUtil.replaceSelectedText(editor, t -> t.toUpperCase());
		}
	};
	
	/** 
	 * The action for changing selected text to lower case.
	 */
	private final Action lowerCaseAction = new LocalizableAction("ToLower", flp) {

		private static final long serialVersionUID = 8561091307169087482L;
		
		{
			createAction(this, "control L", KeyEvent.VK_L, "Set selected text to lower case.", "lower");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EditorUtil.replaceSelectedText(editor, t -> t.toLowerCase());
		}
	};
	
	/** 
	 * The action for toggling case in selected text from document.
	 */
	private final Action toggleCaseAction = new LocalizableAction("ToggleCase", flp) {

		private static final long serialVersionUID = 8561091307169087482L;
		
		{
			createAction(this, "control T", KeyEvent.VK_I, "Invert selected text case.", "toggle");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EditorUtil.replaceSelectedText(editor, t -> toggleCase(t));
		}

		private String toggleCase(String text){
			char[] chars = text.toCharArray();
			
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				chars[i] = Character.isLowerCase(c) ? Character.toUpperCase(c) : Character.toLowerCase(c);
			}
			
			return new String(chars);
		}
	};
	
	/** 
	 * The action for sorting selected text ascending. It uses language-appropriate 
	 * instance of class {@link Collator} for comparing characters.
	 */
	private final Action ascendingSortAction = new LocalizableAction("AscendingSort", flp) {

		private static final long serialVersionUID = -6489276609958382939L;
		
		{
			createAction(this, "control A", KeyEvent.VK_A, "Sort selected lines ascending.", "ascending");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EditorUtil.sortSelectedText(
				editor, 
				EditorUtil.ASC, 
				Collator.getInstance(new Locale(LocalizationProvider.getInstance().getLanguage()))
			);
			
		}
	};
	
	/** 
	 * The action for sorting selected text descending. It uses language-appropriate 
	 * instance of class {@link Collator} for comparing characters.
	 */
	private final Action descendingSortAction = new LocalizableAction("DescendingSort", flp) {

		private static final long serialVersionUID = -6489276609958382939L;
		
		{
			createAction(this, "control D", KeyEvent.VK_D, "Sort selected lines descending.", "descending");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EditorUtil.sortSelectedText(
					editor, 
					EditorUtil.DESC, 
					Collator.getInstance(new Locale(LocalizationProvider.getInstance().getLanguage()))
				);
		}
	};
	
	/** 
	 * The action for removing all duplicated lines from selected text.
	 */
	private final Action uniqueAction = new LocalizableAction("Unique", flp) {

		private static final long serialVersionUID = 5568479492837973531L;
		
		{
			createAction(this, "control alt pressed U", KeyEvent.VK_U, "Remove from selection all lines which are duplicates.", "");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			EditorUtil.removeDuplicateLines(editor);
		}
	};
	
	
	//Languages menu actions
	/**
	 * The action for changing program's language to English.
	 */
	private final Action englishLanguageAction = new LocalizableAction("EN", flp) {

		private static final long serialVersionUID = -5598165548165510133L;

		{
			createAction(this, "control shift E", KeyEvent.VK_E, "Change language to English.", "");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	/**
	 * The action for changing program's language to Croatian.
	 */
	private final Action croatianLanguageAction = new LocalizableAction("HR", flp) {

		
		private static final long serialVersionUID = 1L;

		{
			createAction(this, "control shift C", KeyEvent.VK_C, "Change language to Croatian.", "");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");			
		}
	};
	
	/**
	 * The action for changing program's language to German.
	 */
	private final Action germanLanguageAction = new LocalizableAction("DE", flp) {

		private static final long serialVersionUID = 3625532375254563021L;

		{
			createAction(this, "control shift G", KeyEvent.VK_G, "Change language to German.", "");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};
}
