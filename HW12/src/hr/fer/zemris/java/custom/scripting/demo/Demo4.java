package hr.fer.zemris.java.custom.scripting.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The fourth example class for working with {@link SmartScriptEngine}.
 * 
 * @author Vjeran
 */
public class Demo4 {
	
	/**
	 * The main method. Program starts here.
	 *
	 * @param args the command line arguments, unused in this example
	 */
	public static void main(String[] args) {
		String documentBody = Demo1.readFromDisk("webroot/scripts/fibonacci.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies))
		.execute();
	}
}
