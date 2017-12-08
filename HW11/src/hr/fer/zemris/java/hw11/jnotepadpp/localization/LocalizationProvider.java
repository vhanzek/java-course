package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This class is a singleton class. There can be only one instance of this
 * class. It  loads the resource bundle for used language and stores reference 
 * to it. Method {@link #getString(String)} uses loaded resource bundle to translate 
 * the requested key.
 * 
 * @author Vjeran
 */
public class LocalizationProvider extends AbstractLocalizationProvider implements ILocalizationProvider{
	
	/** The path to files with translations. */
	private static final String TRANSLATIONS = "hr.fer.zemris.java.hw11.jnotepadpp.translations";
	
	/** The currently used language. */
	private String language;
	
	/** The resource bundle. */
	private ResourceBundle bundle;
	
	/** The instance of this class. */
	private static LocalizationProvider instance = null;
	
	/**
	 * Gets the single instance of <code>LocalizationProvider</code>
	 *
	 * @return single instance of <code>LocalizationProvider</code>
	 */
	public static LocalizationProvider getInstance() {
	      if(instance == null) {
	         instance = new LocalizationProvider();
	      }
	      return instance;
	}
	
	/**
	 * Instantiates a new localization provider. Sets the language to 
	 * English by default.
	 */
	private LocalizationProvider(){
		this.language = "en";
		Locale locale = Locale.forLanguageTag(language);
		this.bundle = ResourceBundle.getBundle(TRANSLATIONS, locale);
	}
	
	/**
	 * Changes currently used language to given language. If they are
	 * the same, nothing happens.
	 *
	 * @param language the new language
	 */
	public void setLanguage(String language){
		if(this.language != language){
			this.language = language;
			Locale locale = Locale.forLanguageTag(language);
			this.bundle = ResourceBundle.getBundle(TRANSLATIONS, locale);
			fire();
		}
	}
	
	/**
	 * Gets the currently used language of a program.
	 * 
	 * @return currently used language
	 */
	public String getLanguage() {
		return language;
	}
	
	@Override
	public String getString(String key){
		String value = bundle.getString(key);
		return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
	}
}
