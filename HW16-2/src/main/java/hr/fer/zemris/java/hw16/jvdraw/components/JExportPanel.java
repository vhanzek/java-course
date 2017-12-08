package hr.fer.zemris.java.hw16.jvdraw.components;

import java.awt.FlowLayout;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import hr.fer.zemris.java.hw16.jvdraw.actions.JVDrawActions;

/**
 * The panel used in {@link JVDrawActions#exportAction}. The panel
 * contains 3 options - JPG, PNG, GIF, all three representing exported
 * image type.
 * 
 * @author Vjeran
 */
public class JExportPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3027339380503717185L;
	
	/** The options. */
	private ButtonGroup options;
	
	/**
	 * Instantiates a new export options panel.
	 */
	public JExportPanel() {
		initPanel();
	}

	/**
	 * Method for initializing this panel's GUI.
	 */
	private void initPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		options = new ButtonGroup();
		options.add(new JRadioButton("JPG"));
		options.add(new JRadioButton("PNG"));
		options.add(new JRadioButton("GIF"));
		
		Enumeration<AbstractButton> optionButtons = options.getElements();
		while(optionButtons.hasMoreElements()){
			add(optionButtons.nextElement());
		};
	}
	
	/**
	 * Gets the selected button text.
	 *
	 * @return the selected button text
	 */
	public String getSelectedButtonText() {
        for (Enumeration<AbstractButton> buttons = options.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) return button.getText();
        }
        return null;
    }

}
