package hr.fer.zemris.java.hw3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.EndNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The Class SmartScriptTester used for testing {@link SmartScriptParser}'s work.
 * 
 * @author Vjeco
 */
public class SmartScriptTester {

	/**
	 * The method that starts with the beginning of the program.
	 *
	 * @param args the command line arguments. Unused in this example.
	 */
	public static void main(String[] args) {
		String originalDocumentBody = null;
		
		for(int i = 1; i < 6; i++){
			String docBody = null;
			try {
				docBody = new String(
						Files.readAllBytes(Paths.get("examples/doc" + i + ".txt")), StandardCharsets.UTF_8);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			System.err.println(docBody);
			SmartScriptParser parser = null;
			try {
			parser = new SmartScriptParser(docBody);
			} catch(SmartScriptParserException e) {
				System.err.println(e.getMessage());
				System.exit(-1);
			} 
			DocumentNode document = parser.getDocumentNode();
			originalDocumentBody = createOriginalDocumentBody(document);
			System.out.println(originalDocumentBody);
		}
		
		SmartScriptParser p = null;
		try {
		p = new SmartScriptParser(originalDocumentBody);
		} catch(SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		} 
		DocumentNode document = p.getDocumentNode();
		String originalDocumentBody1 = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody1);
		
		System.out.println(originalDocumentBody.equals(originalDocumentBody1)); //prints: true
	}

	/**
	 * Creates the original document body by walking through the nodes of top level node, {@link DocumentNode}.
	 * Method return original document whose values are hidden inside the document tree.
	 *
	 * @param document the top level node
	 * @return the original string (document)
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		StringBuilder original = new StringBuilder();
		
		for(int i = 0; i < document.numberOfChildren(); i++){
			Node child = document.getChild(i);

			original.append(processChild(child));
		}
		
		return original.toString();
		

	}

	/**
	 * Method for processing child nodes. 
	 * Method checks the type of node and according to that information creates string corresponding to current node.
	 *
	 * @param child the tested child node
	 * @return the string which represents certain node
	 */
	private static String processChild(Node child) {
		StringBuilder node = new StringBuilder();
		
		if(child instanceof TextNode){
			TextNode textNode = (TextNode) child;
			node.append(textNode.toString());
		} else if(child instanceof ForLoopNode){
			ForLoopNode forLoopNode = (ForLoopNode) child;
			node.append(forLoopNode.toString());
			
			for(int j = 0; j < forLoopNode.numberOfChildren(); j++){
				Node forChild = forLoopNode.getChild(j);
				String forString = processChild(forChild);
				node.append(forString);				
			}			
		} else if(child instanceof EchoNode){
			EchoNode echoNode = (EchoNode) child;
			node.append(echoNode.toString());
		} else if(child instanceof EndNode){
			EndNode endNode = (EndNode) child;
			node.append(endNode.toString());
		} else {
			throw new SmartScriptParserException(
				"Document node can not be a child."
			);
		}
		
		return node.toString();
	}
}
