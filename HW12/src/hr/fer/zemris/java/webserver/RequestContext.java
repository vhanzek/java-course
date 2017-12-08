package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The class {@code RequestContext} representing server request and its context i.e.
 * it represents response for the client.
 * 
 * @see "http://code.tutsplus.com/tutorials/http-headers-for-dummies--net-8039"
 * 
 * @author Vjeran
 */
public class RequestContext {
	
	/** The used charset. */
	private Charset charset;
	
	/** The output stream. */
	private OutputStream outputStream;
	
	/** The encoding. */
	public String encoding = "UTF-8";
	
	/** The status code. */
	public int statusCode = 200;
	
	/** The status text. */
	public String statusText = "OK";
	
	/** The mime type. */
	public String mimeType = "text/html";
	
	/** The parameters. */
	private Map<String, String> parameters;
	
	/** The temporary parameters. */
	private Map<String, String> temporaryParameters;
	
	/** The persistent parameters. */
	private Map<String, String> persistentParameters;
	
	/** The output cookies. */
	private List<RCCookie> outputCookies;
	
	/** The flag for checking if header is generated or not. */
	private boolean headerGenerated;
	
	/**
	 * Instantiates a new server request context.
	 *
	 * @param outputStream the output stream
	 * @param parameters the parameters
	 * @param persistentParameters the persistent parameters
	 * @param outputCookies the output cookies
	 */
	public RequestContext(OutputStream outputStream, Map<String,String> parameters, 
			Map<String,String> persistentParameters, List<RCCookie> outputCookies){
		if(outputStream == null){
			throw new IllegalArgumentException(
				"Output stream must not be null!"
			);
		}
		this.outputStream = outputStream;
		this.parameters = (parameters == null) ? new HashMap<>() : Collections.unmodifiableMap(parameters);
		this.persistentParameters = (persistentParameters == null) ? new HashMap<>() : persistentParameters;
		this.temporaryParameters = new HashMap<>();
		this.outputCookies = (outputCookies == null) ? new ArrayList<>() : outputCookies;
	}
	
	
	
	/**
	 * Gets the parameter.
	 *
	 * @param name the name
	 * @return the parameter
	 */
	public String getParameter(String name){
		return parameters.get(name);
	}
	
	/**
	 * Gets the parameter names.
	 *
	 * @return the parameter names
	 */
	public Set<String> getParameterNames(){
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Gets the persistent parameter.
	 *
	 * @param name the name
	 * @return the persistent parameter
	 */
	public String getPersistentParameter(String name){
		return persistentParameters.get(name);
	}
	
	/**
	 * Gets the persistent parameter names.
	 *
	 * @return the persistent parameter names
	 */
	public Set<String> getPersistentParameterNames(){
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}
	
	/**
	 * Sets the persistent parameter.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void setPersistentParameter(String name, String value){
		persistentParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter.
	 *
	 * @param name the name
	 */
	public void removePersistentParameter(String name){
		persistentParameters.remove(name);
	}
	
	/**
	 * Gets the temporary parameter.
	 *
	 * @param name the name
	 * @return the temporary parameter
	 */
	public String getTemporaryParameter(String name){
		return temporaryParameters.get(name);
	}
	
	/**
	 * Gets the temporary parameter names.
	 *
	 * @return the temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames(){
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Sets the temporary parameter.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void setTemporaryParameter(String name, String value){
		temporaryParameters.put(name, value);
	}
	
	/**
	 * Removes the temporary parameter.
	 *
	 * @param name the name
	 */
	public void removeTemporaryParameter(String name){
		temporaryParameters.remove(name);
	}
	
	
	/**
	 * Sets the encoding.
	 *
	 * @param encoding the new encoding
	 */
	public void setEncoding(String encoding) {
		throwExceptionIf();
		this.encoding = encoding;
	}

	/**
	 * Sets the status code.
	 *
	 * @param statusCode the new status code
	 */
	public void setStatusCode(int statusCode) {
		throwExceptionIf();
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text.
	 *
	 * @param statusText the new status text
	 */
	public void setStatusText(String statusText) {
		throwExceptionIf();
		this.statusText = statusText;
	}
	

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	public Charset getCharset() {
		return charset;
	}

	/**
	 * Sets the charset.
	 *
	 * @param charset the new charset
	 */
	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	/**
	 * Sets the mime type.
	 *
	 * @param mimeType the new mime type
	 */
	public void setMimeType(String mimeType) {
		throwExceptionIf();
		this.mimeType = mimeType;
	}
	
	/**
	 * Method for throwing exception if some property wants to be changed
	 * after the header has been generated.
	 */
	private void throwExceptionIf(){
		if(headerGenerated){
			throw new RuntimeException(
				"This property can not be changed beacause header was already generated."
			);
		}
	}

	/**
	 * Method for writing specified data to this {@link #outputStream}.
	 * First time this method or {@link #write(String)} method are called,
	 * request header is generated by calling method {@link #createHeader()}.
	 * Any time after that, creating header part is skipped.
	 *
	 * @param data the data
	 * @return this request context
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RequestContext write(byte[] data) throws IOException {
		if(!headerGenerated){
			charset = Charset.forName(encoding);
			outputStream.write(createHeader());
			headerGenerated = true;	
		}
		outputStream.write(data);
		return this;
	}
	
	/**
	 * Method for writing specified text to this {@link #outputStream}.
	 * First time this method or {@link #write(byte[])} method are called,
	 * request header is generated by calling method {@link #createHeader()}.
	 * Any time after that, creating header part is skipped.
	 *
	 * @param text the text
	 * @return this request context
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public RequestContext write(String text) throws IOException {
		return write(text.getBytes(Charset.forName(encoding)));
	}
	
	/**
	 * Method for creating header based on current context.
	 *
	 * @return the byte array representation of the generated header
	 */
	private byte[] createHeader() {
		StringBuilder header = new StringBuilder();
		
		//first line
		header.append("HTTP/1.1 " + statusCode + " " + statusText);
		addLineEnd(header);
		
		//second line
		header.append("Content-Type: " + mimeType);
		if(mimeType.startsWith("text/")){
			header.append("; charset=" + encoding);
		}
		addLineEnd(header);
		
		//cookies
		if(!outputCookies.isEmpty()){
			for(RCCookie c : outputCookies){
				header.append("Set-Cookie: " + c.getName() + "=\"" + c.getValue() + "\"");
				addCookie(header, "Domain", c.getDomain());
				addCookie(header, "Path", c.getPath());
				addCookie(header, "Max-Age", c.getMaxAge());
				if(c.httpOnly) header.append("; HttpOnly");
				addLineEnd(header);
			}
		}
		
		//header end
		addLineEnd(header);
		
		return header.toString().getBytes(StandardCharsets.ISO_8859_1);
	}

	/**
	 * Method for adding cookie into request header.
	 *
	 * @param header the header
	 * @param name the name of the cookie
	 * @param value the value of the cookie
	 */
	private void addCookie(StringBuilder header, String name, Object value){
		if(value != null){
			header.append("; " + name + "=" + value);
		}
	}

	/**
	 * Method for adding line end into request header.
	 *
	 * @param header the header
	 */
	private void addLineEnd(StringBuilder header) {
		header.append("\r\n");
	}

	/**
	 * Method for adding cookie into {@link #outputCookies} list.
	 *
	 * @param cookie the cookie to be added to list
	 */
	public void addRCCookie(RCCookie cookie){
		outputCookies.add(cookie);
	}
	
	/**
	 * The static class representing web cookie.
	 * 
	 * @see "https://en.wikipedia.org/wiki/HTTP_cookie"
	 * 
	 * @author Vjeran
	 */
	public static class RCCookie{
		
		/** The name. */
		private String name;
		
		/** The value. */
		private String value;
		
		/** The domain. */
		private String domain;
		
		/** The path. */
		private String path;
		
		/** The max age. */
		private Integer maxAge;
		
		/** The flag for checking if cookie is intended for HTTP only. */
		private boolean httpOnly;
		
		/**
		 * Instantiates a new RC cookie.
		 *
		 * @param name the name
		 * @param value the value
		 * @param maxAge the max age
		 * @param domain the domain
		 * @param path the path
		 * @param httpOnly the flag
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean httpOnly) {
			if(name == null || value == null){
				throw new IllegalArgumentException(
					"Name or value of the cookie must not be null."
				);
			}
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
			this.httpOnly = httpOnly;
		}

		/**
		 * Sets the name.
		 *
		 * @param name the new name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Sets the value.
		 *
		 * @param value the new value
		 */
		public void setValue(String value) {
			this.value = value;
		}

		/**
		 * Sets the domain.
		 *
		 * @param domain the new domain
		 */
		public void setDomain(String domain) {
			this.domain = domain;
		}

		/**
		 * Sets the path.
		 *
		 * @param path the new path
		 */
		public void setPath(String path) {
			this.path = path;
		}

		/**
		 * Sets the max age.
		 *
		 * @param maxAge the new max age
		 */
		public void setMaxAge(Integer maxAge) {
			this.maxAge = maxAge;
		}

		/**
		 * Sets the HTTP only.
		 *
		 * @param httpOnly the new HTTP only
		 */
		public void setHttpOnly(boolean httpOnly) {
			this.httpOnly = httpOnly;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Gets the domain.
		 *
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Gets the path.
		 *
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Gets the max age.
		 *
		 * @return the max age
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Checks if cookie is intended for HTTp only.
		 * 
		 * @return <code>true</code> if is HTTP only,
		 * 		   <code>false</code> otherwise
		 */
		public Object isHttpOnly() {
			return httpOnly;
		}
	}
}
