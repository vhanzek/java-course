package hr.fer.zemris.java.custom.scripting.parser;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.TagType;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.EndNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import static hr.fer.zemris.java.custom.scripting.parser.ParserConstants.*;

/**
 * The Class SmartScriptParser used for making structured document tree from elements given to parser by lexical analyser {@link Lexer}.
 * 
 * @author Vjeco
 */
public class SmartScriptParser {
	
	/** The document node. Top level node in a document tree.*/
	private DocumentNode documentNode;
	
	/** The document stack. */
	private ObjectStack documentStack;

	/**
	 * Instantiates a new smart script parser.
	 *
	 * @param documentBody the document text that needs to be parsed
	 */
	public SmartScriptParser(String documentBody){
		documentNode = new DocumentNode();
		documentStack = new ObjectStack();
		
		documentStack.push(documentNode);
		parse(documentBody);
	}
	
	/**
	 * Method for parsing document text.
	 * <p> Method calls {@link Lexer} method <code>nextToken()</code> until it returns EOF token.
	 * If token type is TEXT, then {@link TextNode} is added as child to top level node {@link DocumentNode}.
	 * If token type is TAG, then method determines tag type, parses it, adds the token as a child to document node and, additionally, 
	 * if tag type is FOR, it becomes new top level node for other nodes as long as the end tag occurs.</p>
	 *
	 * @param docBody the document text that needs to be parsed
	 */
	private void parse(String docBody){
		Lexer lexer = new Lexer(docBody);
		Token token = lexer.nextToken();
		
		while(token.getType() != TokenType.EOF){			
			if(token.getType() == TokenType.TEXT){
				String text = token.getValue();
				Node top = (Node) documentStack.peek();
				top.addChildNode(new TextNode(text));
			} else {
				String tag = token.getValue()
						  		  .substring(2, token.getValue().length() - 2)
						  		  .toLowerCase()
						  		  .trim();
				
				Object[] parsedTag = determineTagType(tag);
				
				String tagElements = (String) parsedTag[0];
				TagType type = (TagType) parsedTag[1];
				
				Node top = (Node) documentStack.peek();
				
				switch(type){
				case ECHO : EchoNode echoNode = new EchoNode(parseTag(tagElements));
							top.addChildNode(echoNode);
							break;
					
				case FOR : 	ArrayIndexedCollection elements = parseTag(tagElements);
							ForLoopNode forLoopNode = checkElements(elements);
							top.addChildNode(forLoopNode);
							documentStack.push(forLoopNode);
							break;
					
				case END : 	top.addChildNode(new EndNode());
							documentStack.pop();
							if(documentStack.isEmpty()){
								throw new SmartScriptParserException(
									"Illegal use of end tag."
								);									
							}
				}
			}
			
			token = lexer.nextToken();
		}
	}

	/**
	 * Method for parsing tag elements. Elements can be put without spaces and method can still recognize element types.
	 * 
	 * @throws SmartScriptParserException if invalid character occurs inside of the tag
	 * @param tagElements the tag elements
	 * @return the array indexed collection of tag elements
	 */
	private ArrayIndexedCollection parseTag(String tagElements) {
			ArrayIndexedCollection elements = new ArrayIndexedCollection();
			checkEscapeSequence(tagElements);
			tagElements = tagElements.replaceAll("\\\\\"", "\u0001");
			
			Matcher matcher = Pattern.compile("(" + DOUBLE + ")|(" + INTEGER + ")|" + STRING + "|@(" + VALID +"+)|"
											+ "([a-z|\\_]" + VALID + "*)|(" + OPERATOR + ")|(" + INVALID + ")").matcher(tagElements);

			while(matcher.find()){
					if(matcher.group(1) != null){
						String doubleString = matcher.group(1);
						elements.add(new ElementConstantDouble(Double.parseDouble(doubleString)));
					} else if (matcher.group(2) != null){
						String integerString = matcher.group(2);
						elements.add(new ElementConstantInteger(Integer.parseInt(integerString)));
					} else if (matcher.group(3) != null){
						String string = matcher.group(3);
						string = string.replaceAll("\u0001", "\"");
						elements.add(new ElementString(string));
					} else if (matcher.group(4) != null){
						String function = matcher.group(4);
						if(isValidFunctionName(function)){
							elements.add(new ElementFunction(function));} 
					} else if (matcher.group(5) != null){
						String variable = matcher.group(5);
						if(isValidVariableName(variable)){
							elements.add(new ElementVariable(variable));}
					} else if (matcher.group(6) != null){		
						String operator = matcher.group(6);
						elements.add(new ElementOperator(operator));
					} else if (matcher.group(7) != null){
						throw new SmartScriptParserException(
							"Invalid character."
						);
					}
			}
			
			return elements;
	}
	
	/**
	 * Checks if number of elements in for loop node is legal and is certain element instance of desired class.
	 *
	 * @throws SmartScriptParserException if number of elements is illegal or if certain element is not instance of desired class
	 * @param elements the elements for checking
	 * @return the new for loop node filled with elements
	 */
	private ForLoopNode checkElements(ArrayIndexedCollection elements) {			
		if(elements.size() != 3 && elements.size() != 4){
			throw new SmartScriptParserException(
				"Number of elements in for loop is invalid."
			);
		}	
		if (!(elements.get(0) instanceof ElementVariable)){
			throw new SmartScriptParserException(
				"First element in for loop is not a variable."
			);
		}
		if (!isElementLegal(elements.get(1)) || !isElementLegal(elements.get(2))){
			throw new SmartScriptParserException(
				"Start expression or end expression in for loop is not valid."
			);
		}
		if (elements.size() == 4){
			if(!isElementLegal(elements.get(3))){
				throw new SmartScriptParserException(
					"Step expression in for loop is not valid."
				);
			} else {
				return new ForLoopNode((ElementVariable) elements.get(0), (Element) elements.get(1), 
										(Element) elements.get(2), (Element) elements.get(3));
			}
		} else {
			return new ForLoopNode((ElementVariable) elements.get(0), (Element) elements.get(1), 
									(Element) elements.get(2), null);
		}
	}

	/**
	 * Checks if element is legal. Element is legal if it's a string, double or integer constant.
	 *
	 * @param element the tested element
	 * @return true, if is element legal, false otherwise
	 */
	private boolean isElementLegal(Object element) {
		return (!(element instanceof ElementOperator) &&  !(element instanceof ElementFunction));
	}
	
	/**
	 * Checks if there is illegal escape sequence in given string. Escape sequence is legal if it's character \ or ".
	 *
	 * @throws SmartScriptParserException if there is illegal escape sequence.
	 * @param string the tested string
	 */
	private void checkEscapeSequence(String string) {
		if (string.matches(".*\\\\[^\\|^\"].*")){
			throw new SmartScriptParserException(
				"Illegal escape sequence."
			);
		} 
	}

	/**
	 * Checks if given string is valid variable name. Valid name Starts with the letter followed by letters, numbers or _.
	 *  
	 * @throws SmartScriptParserException if variable name is invalid
	 * @param variable the variable
	 * @return true, if is valid variable name, exception otherwise
	 */
	private boolean isValidVariableName(String variable){
		if(variable.matches(VARIABLE)){
			return true;
		} else {
			throw new SmartScriptParserException(
				"Invalid variable name."
			);
		}
	}
	
	/**
	 * Checks if given string is valid function name. Valid name Starts with the letter followed by letters, numbers or _.
	 *  
	 * @throws SmartScriptParserException if function name is invalid
	 * @param function the function
	 * @return true, if is valid function name, exception otherwise
	 */
	private boolean isValidFunctionName(String function){
		if(function.matches(FUNCTION)){
			return true;
		} else {
			throw new SmartScriptParserException(
				"Invalid function name."
			);
		}
	}

	/**
	 * Method for determining tag type. Legal tag names are FOR, END and =.
	 * 
	 * @throws SmartScriptParserException if tag has illegal name or if tag is empty
	 * @param tag the text of the tag without {$ and $}
	 * @return the object[] first slot is for a tag elements without tag name, and second is for tag type
	 */
	private Object[] determineTagType(String tag) {
		Object[] array = new Object[2];
		
		if(tag.isEmpty()){
			throw new SmartScriptParserException(
				"Empty tag."
			);
		} else if (tag.startsWith("for ")){
			array[0] = tag.substring(4);
			array[1] = TagType.FOR;
		} else if (tag.startsWith("end")){
			array[0] = null;
			array[1] = TagType.END;
		} else if (tag.startsWith("=")){
			array[0] = tag.substring(1);
			array[1] = TagType.ECHO;
		} else {
			throw new SmartScriptParserException(
				"Tag has illegal name."
			);
		}
		
		return array;
	}

	/**
	 * Gets the document node.
	 *
	 * @return the document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Gets the document stack.
	 *
	 * @return the document stack
	 */
	public ObjectStack getDocumentStack() {
		return documentStack;
	}
}
