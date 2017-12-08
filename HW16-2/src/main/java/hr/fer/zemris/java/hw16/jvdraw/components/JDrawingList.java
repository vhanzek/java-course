package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.drawing.DrawingObjectListModel;
import hr.fer.zemris.java.hw16.jvdraw.objects.GeometricalObject;

/**
 * <p>The class {@code JDrawingList} representing list of all drawn geometric
 * object in drawing editor.
 * 
 * <p>This list has two features. When {@code DEL} key is pressed and some
 * geometric object is selected, that object is deleted both from the list and 
 * drawing editor. Also, when some object is double clicked, it shows to user
 * information about selected object and offers possibility to modify object's
 * properties.
 * 
 * @author Vjeran
 */
public class JDrawingList extends JList<GeometricalObject>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5212403151547341184L;
		
	/**
	 * Instantiates a new list showing all drawn objects.
	 *
	 * @param model the list model
	 */
	public JDrawingList(DrawingObjectListModel model) {
		super(model);
		initList();
	}

	/**
	 * Method for initializing list's GUI.
	 */
	private void initList() {
		// design
		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
		setFixedCellWidth(100);

		// listeners
		addKeyListener(listKeyListener);
		addMouseListener(listMouseListener);
		
	}

	/** 
	 * This list's key listener. Its functionality is described
	 * in documentation of this {@link JDrawingList} class.
	 */
	private static final KeyListener listKeyListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			Component comp = e.getComponent();
			if (comp instanceof JDrawingList) {
				JDrawingList list = (JDrawingList) comp;
				if(e.getKeyCode() == KeyEvent.VK_DELETE){
					DrawingObjectListModel model = 
						(DrawingObjectListModel) list.getModel();
					GeometricalObject object = list.getSelectedValue();
					if(object != null){
						model.getModel().remove(list.getSelectedValue());
					}
				}
			}
		}
	};
	
	/** 
	 * This list's mouse listener. Its functionality is described
	 * in documentation of this {@link JDrawingList} class.
	 */
	private static final MouseListener listMouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			Component comp = e.getComponent();
			if (comp instanceof JDrawingList) {
				JDrawingList list = (JDrawingList) comp;
				if(e.getClickCount() == 2){
					GeometricalObject object = list.getSelectedValue();
					if(object != null){
						JMessagePanel messagePanel = new JMessagePanel(object);
						int reply = JOptionPane.showConfirmDialog(list.getParent(), messagePanel);
						
						if(reply == JOptionPane.YES_OPTION){
							messagePanel.processChange();
							DrawingObjectListModel model = 
								(DrawingObjectListModel) list.getModel();
							model.getModel().change(object);
						}
					}
				}
			}
		}
	};
}
