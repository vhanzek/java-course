package hr.fer.zemris.java.hw11.jnotepadpp.localization;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract class which implements {@link ILocalizationProvider}. It adds the 
 * ability to register, de-register and inform ({@link #fire()}) listeners. 
 * It is an abstract class – it does not implement {@code getString} method.
 * 
 * @author Vjeran
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
	
	/** The registered listeners. */
	List<ILocalizationListener> listeners;
	
	/**
	 * Instantiates a new abstract localization provider.
	 */
	public AbstractLocalizationProvider() {
		this.listeners = new ArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}
	
	/**
	 * Method for informing all registered listeners that language
	 * change has been occurred.
	 */
	public void fire(){
		listeners.forEach(l -> l.localizationChanged());
	}

}
