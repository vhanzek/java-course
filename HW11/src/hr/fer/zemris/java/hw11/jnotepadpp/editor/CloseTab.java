package hr.fer.zemris.java.hw11.jnotepadpp.editor; 
 
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

import java.awt.*;
import java.awt.event.*;
import static hr.fer.zemris.java.hw11.jnotepadpp.Icons.*;
 
/**
 * The class representing  close tab in {@link JNotepadPP} program.
 * It contains one icon, which is red if current editor has unsaved 
 * content, otherwise it's blue, name of the tab and button for closing tab.
 * 
 * @author Vjeran
 */
public class CloseTab extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5435505121625778431L;
	
	/** The size of the button in tab. */
	private static final int BUTTON_SIZE = 17;
	
	/** The tabbed pane. */
	private final JTabbedPane tabbedPane;
	
	/** The current icon that is showing. */
	private ImageIcon currentIcon;
	
	/** The frame of {@link JNotepadPP} program. */
	private JNotepadPP frame;
	
	/** The label icon. */
	private JLabel labelIcon;
 
    /**
     * Instantiates a new close tab.
     *
     * @param tabbedPane the tabbed pane
     * @param frame the frame of the program
     */
    public CloseTab(JTabbedPane tabbedPane, JNotepadPP frame) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        this.currentIcon = BLUE_DISK;
        if (tabbedPane == null) {
            throw new NullPointerException("Given tabbed pane is null.");
        }
        this.frame = frame;
        this.tabbedPane = tabbedPane;
        setOpaque(false);
        
        initGUI();
    }
    
    /**
     * Method for initializing GUI.
     */
    private void initGUI(){
    	labelIcon = new JLabel(currentIcon);
        add(labelIcon);
        labelIcon.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 5));
        labelIcon.setSize(17, 17);
        
        JLabel label = new JLabel() {
			private static final long serialVersionUID = 5757059168166249090L;

			public String getText() {
                int i = tabbedPane.indexOfTabComponent(CloseTab.this);
                if (i != -1) {
                    return tabbedPane.getTitleAt(i);
                }
                return null;
            }
        };
         
        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 5));

        JButton button = new TabButton();
        add(button);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }
    
    /**
     * Helper method for changing color of icon used in a tab.
     */
    public void changeIcon(){
    	currentIcon = currentIcon.equals(BLUE_DISK) ?	RED_DISK : BLUE_DISK;    	
    	labelIcon.setIcon(currentIcon);
    }

	/**
	 * The class representing button used in a tab. Button has a little x on it,
	 * and if it is clicked it removes that tab from tabbed pane.
	 * 
	 * @author Vjeran
	 */
	private class TabButton extends JButton implements ActionListener {
    	
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = -4214174302426619388L;

		/**
		 * Instantiates a new tab button.
		 */
		public TabButton() {
			setToolTipText("Close this tab.");
            setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
            
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setBorder(BorderFactory.createEtchedBorder());
            setBorderPainted(false);
            
            addMouseListener(buttonMouseListener);
            setRolloverEnabled(true);
            addActionListener(this);
        }
		
		@Override
        public void actionPerformed(ActionEvent e) {
            int index = tabbedPane.indexOfTabComponent(CloseTab.this);
         
            if (index != -1) {
            	frame.removeDocuments(index, 1);
            }
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            ButtonModel model = getModel();
            

            if (model.isPressed()) {
                g2.translate(1, 1);
            }
            g2.setStroke(new BasicStroke(2));
            g2.setColor(Color.BLACK);
            if (model.isRollover()) {
                g2.setColor(Color.RED);
            }
            
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }
 
    /** The mouse listener for tab button. */
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
 
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
}