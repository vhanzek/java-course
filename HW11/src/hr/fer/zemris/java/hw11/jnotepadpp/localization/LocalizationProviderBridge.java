package hr.fer.zemris.java.hw11.jnotepadpp.localization;


/**
 * The class which is a decorator for some other {@link ILocalizationProvider}. 
 * This class offers two additional methods: {@link #connect()} and {@link #disconnect()}, 
 * and it manages a connection status. When user calls {@code connect()} on it, the method 
 * will register an instance of anonymous {@link ILocalizationListener} on the decorated object. 
 * When user calls {@code disconnect()}, this object will be de-registered from decorated object.
 * 
 * @author Vjeran
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider{
	
	/** The localization provider, decorated object. */
	private ILocalizationProvider provider;
	
	/** The listener. */
	private ILocalizationListener listener;
	
	/** The flag for checking if listener is connected to decorated object.*/
	private boolean connected;
	
	/**
	 * Instantiates a new localization provider bridge.
	 *
	 * @param provider the localization provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}
	
	/**
	 * Method for removing registered listener. It also changes flag
	 * {@link #connected} to <code>true</code>.
	 */
	public void disconnect(){
		if(connected){
			provider.removeLocalizationListener(listener);
			this.connected = true;
		}
	}
	
	/**
	 * Method for registering an instance of anonymous 
	 * {@link ILocalizationListener} on the decorated object.
	 */
	public void connect(){
		if(!connected){
			this.connected = true;
			listener = new ILocalizationListener() {
				@Override
				public void localizationChanged() {
					fire();
				}
			};
			provider.addLocalizationListener(listener);
		}
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
}
