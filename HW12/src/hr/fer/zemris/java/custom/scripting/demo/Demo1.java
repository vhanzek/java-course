package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The first example class for working with {@link SmartScriptEngine}.
 * 
 * @author Vjeran
 */
public class Demo1 {
	
	/**
	 * The main method. Program starts here.
	 *
	 * @param args the command line arguments, unused in this example
	 */
	public static void main(String[] args) {
		String documentBody = readFromDisk("webroot/scripts/osnovni.smscr");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies))
		.execute();
	}

	/**
	 * Helper method for reading file from the given path and
	 * returning its content as a string.
	 *
	 * @param string the path to the file
	 * @return the content of the file as a string
	 */
	public static String readFromDisk(String string) {
		Path path = Paths.get(string);
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(content, Charset.forName("UTF-8"));
	}

}
