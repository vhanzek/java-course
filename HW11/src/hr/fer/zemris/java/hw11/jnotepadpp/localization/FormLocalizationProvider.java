package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * The class derived from {@link LocalizationProviderBridge}. When frame is opened, 
 * it calls {@link #connect()} and when frame is closed, it calls {@link #disconnect()}. 
 * For each created frame in a program it is necessary to add an instance variable of this 
 * type and in its constructor create it.
 * 
 * @author Vjeran
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Instantiates a new form localization provider. It registers 
	 * itself as a {@link WindowListener} to given {@code frame}.
	 *
	 * @param provider the localization provider
	 * @param frame the frame of the program
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});

	}

}
