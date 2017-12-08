package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.JVDrawActions;
import hr.fer.zemris.java.hw16.jvdraw.color.BackgroundColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.color.ForegroundColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.color.JColorDisplay;
import hr.fer.zemris.java.hw16.jvdraw.components.DrawingButtonGroup;
import hr.fer.zemris.java.hw16.jvdraw.components.JDrawingList;
import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.GeoObjectsDrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.drawing.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

import static hr.fer.zemris.java.hw16.jvdraw.actions.JVDrawActions.*;

/**
 * The main class representing simple program where user can pick several
 * geometric shapes and draw them in different colors to {@link JDrawingCanvas} 
 * component. Also, it offers options for user to save current work, open new
 * .JVD file or export painting as image file.
 * 
 * @author Vjeran
 */
public class JVDraw extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -9144482477412895958L;
	
	/** The Constant DEFAULT_FOREGROUND_COLOR. */
	public static final Color DEFAULT_FOREGROUND_COLOR = Color.BLACK;
	
	/** The Constant DEFAULT_BACKGROUND_COLOR. */
	public static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
	
	/** The foreground color area. */
	private JColorArea foreground;
	
	/** The background color area. */
	private JColorArea background;
	
	/**
	 * The main method where program starts.
	 *
	 * @param args the command line arguments, unused in this example
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

	/**
	 * Instantiates a new {@code JVDraw} frame.
	 */
	public JVDraw(){
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setBounds(300, 300, 600, 600);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		initGUI();
	}

	/**
	 * Method for initializing program's GUI.
	 */
	private void initGUI() {		
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		//drawing buttons
		DrawingButtonGroup drawingButtons = new DrawingButtonGroup();
		
		//color areas
		foreground = new ForegroundColorArea(DEFAULT_FOREGROUND_COLOR);
		background = new BackgroundColorArea(DEFAULT_BACKGROUND_COLOR);
		
		//tool bar
		JToolBar toolbar = createToolbar(drawingButtons);
		cp.add(toolbar, BorderLayout.PAGE_START);		
		
		//color display
		JColorDisplay colorDisplay = new JColorDisplay(foreground, background);
		cp.add(colorDisplay, BorderLayout.PAGE_END);
		addColorChangeListeners(colorDisplay);
		
		//drawing canvas
		GeoObjectsDrawingModel drawingModel = new GeoObjectsDrawingModel();
		JDrawingCanvas drawingCanvas = new JDrawingCanvas(drawingModel, drawingButtons);
		cp.add(drawingCanvas, BorderLayout.CENTER);
		addColorChangeListeners(drawingCanvas);
		
		//menu bar
		JVDrawActions.initialize(this, drawingModel, drawingCanvas);
		JMenuBar menuBar = createMenuBar();
		setJMenuBar(menuBar);
		
		//objects list
		DrawingObjectListModel listModel = new DrawingObjectListModel(drawingModel);
		JList<GeometricalObject> objectsList = new JDrawingList(listModel);
		cp.add(new JScrollPane(objectsList), BorderLayout.LINE_END);
	}

	/**
	 * Helper method for adding listeners to {@link #foreground}
	 * and {@link #background} color areas.
	 *
	 * @param listener the color change listener
	 */
	private void addColorChangeListeners(ColorChangeListener listener) {
		foreground.addColorChangeListener(listener);
		background.addColorChangeListener(listener);
	}
	
	/**
	 * Helper method for creating menu bar used in this program.
	 * Menu bar contains one menu with 4 menu items - "Open",
	 * "Save", "Save As", "Export".
	 *
	 * @return the new {@code JMenuBar} 
	 */
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		//menu bar
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		fileMenu.add(new JMenuItem(openAction));
		fileMenu.add(new JMenuItem(saveAction));
		fileMenu.add(new JMenuItem(saveAsAction));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(exportAction));
		
		return menuBar;
	}

	/**
	 * <p>Helper method for creating tool bar. All buttons in a tool bar
	 * are shown as name + icon representing certain actions. Also, there
	 * are two {@link JColorArea} for choosing foreground and background 
	 * colors.</p>
	 * 
	 * <p>For example, button for drawing line has name "Line" and
	 * corresponding icon.</p>
	 *
	 * @return the new {@link JToolBar}
	 */
	private JToolBar createToolbar(ButtonGroup drawingButtons) {
		JToolBar toolBar = new JToolBar();
		
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(foreground);
		toolBar.add(background);
		
		toolBar.addSeparator();
		
		Enumeration<AbstractButton> buttons = drawingButtons.getElements();
		while(buttons.hasMoreElements()){
			toolBar.add(buttons.nextElement());
		};
		
		return toolBar;
	}
}
