package hr.fer.zemris.java.hw11.jnotepadpp.editor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * The class representing clock label. It shows current time
 * in "yyyy/MM/dd HH:mm:ss" format and is aligned to right.
 * 
 * @author Vjeran
 */
public class ClockLabel extends JLabel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7983487206918146267L;
	
	/** The simple date format. */
	private final SimpleDateFormat sdf;
	
	/**
	 * Instantiates a new clock label.
	 */
	public ClockLabel() {
		sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SwingUtilities.invokeLater(()-> {
			Timer t = new Timer(100, null);
			t.addActionListener(e -> {
				if(ClockLabel.this.isShowing()){
					setText(sdf.format(new Date()).toString());
				} else {
					t.stop();
				}
			});
			t.start();
		});
	}
}
