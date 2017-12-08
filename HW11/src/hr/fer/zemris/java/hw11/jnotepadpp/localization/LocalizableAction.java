package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * The class represents certain action. This action support multiple languages.
 * When program's language is changed, all components using this action will change
 * its name to name in newly selected language.
 * 
 * @author Vjeran
 */
public abstract class LocalizableAction extends AbstractAction{

	/** The constant serialVersionUID. */
	private static final long serialVersionUID = -7126275210283030911L;
	
	/**
	 * Instantiates a new localizable action.
	 *
	 * @param key the key
	 * @param lp the localization provider
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		putValue(Action.NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, lp.getString(key));
			}
		});
	}

}
