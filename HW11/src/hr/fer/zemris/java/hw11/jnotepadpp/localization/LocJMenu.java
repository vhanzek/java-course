package hr.fer.zemris.java.hw11.jnotepadpp.localization;
import javax.swing.JMenu;

/**
 * The class which is derived from class {@link JMenu}. It supports multiple languages.
 * When program's language is changed, all {@link LocJMenu} components will change
 * its name to name of the component in newly selected language.
 * 
 * @author Vjeran
 */
public class LocJMenu extends JMenu {

	/** The constant serialVersionUID. */
	private static final long serialVersionUID = 712812869722005609L;
	
	/**
	 * Instantiates a new localized {@link JMenu}.
	 *
	 * @param key the key
	 * @param lp the localization
	 */
	public LocJMenu(String key, ILocalizationProvider lp) {
		setText(lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(lp.getString(key));
			}
		});
	}
}
