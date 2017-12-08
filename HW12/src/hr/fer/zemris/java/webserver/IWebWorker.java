package hr.fer.zemris.java.webserver;

/**
 * The interface represents web worker i.e. some class that knows how to
 * communicate with the client through {@link RequestContext} class.
 * 
 * @author Vjeran
 */
public interface IWebWorker {
	
	/**
	 * Method for processing request. It sends certain information
	 * (image, text, HTML etc.) to connected client.
	 *
	 * @param context the request context
	 */
	public void processRequest(RequestContext context);
}
