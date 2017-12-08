package hr.fer.zemris.java.hw11.jnotepadpp.localization;

/**
 * The objects which are instances of classes that implement 
 * this interface will be able to give us the translations for 
 * given keys. For this reason there is a declared method 
 * {@link #getString(String)}. It takes the key and gives back 
 * the localization. Each {@code ILocalizationProvider} will be 
 * automatically the Subject that will notify all registered listeners 
 * when a selected language has changed. For that purpose, the 
 * {@code ILocalizationProvider} interface also declares a method 
 * for registration and a method for de-registration of listeners.
 * 
 * @author Vjeran
 */
public interface ILocalizationProvider {
	
	/**
	 * Adds the localization listener.
	 *
	 * @param l the listener
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Removes the localization listener.
	 *
	 * @param l the listener
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Method takes the key and gives back the appropriate
	 * translation.
	 *
	 * @param key the key
	 * @return the string in currently used language
	 */
	String getString(String key);
}
