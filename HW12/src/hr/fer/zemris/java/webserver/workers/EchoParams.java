package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The class which outputs back to user parameters it obtained in a HTML table.
 * 
 * @author Vjeran
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		StringBuilder sb = new StringBuilder(
			"<html>\r\n" +
			" <head>\r\n" +
			" 	<title>Parameters</title>\r\n"+
			" </head>\r\n" +
			" <body>\r\n" +
			"  <h1>Parameters</h1>\r\n" +
			"  <table border='1'>\r\n" +
			"    <tr>" +
			"      <td><b>Name</b></td>" +
			"      <td><b>Value</b></td>" + 
			"    </tr>\r\n"
		);
		
		for(String par : context.getParameterNames()){
			sb.append(
				"<tr><td>" +
				par + 
				"</td><td>" + 
				context.getParameter(par) +
				"</td></tr>\r\n"
			);
		}
		
		sb.append(
			"  </table>\r\n" +
			" </body>\r\n" +
			"</html>\r\n"
		);
		
		context.setMimeType("text/html");
		try {
			context.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
