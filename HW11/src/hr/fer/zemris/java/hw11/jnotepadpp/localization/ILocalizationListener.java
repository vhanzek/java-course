package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * The listener interface for receiving localization events.
 * The class that is interested in processing a localization
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addILocalizationListener<code> method. When
 * the ILocalization event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ILocalizationEvent
 * 
 * @author Vjeran
 */
public interface ILocalizationListener {
	
	/**
	 * Executes when language has been changed.
	 */
	void localizationChanged();
}
