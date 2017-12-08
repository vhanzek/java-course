package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.EndNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import static hr.fer.zemris.java.custom.scripting.exec.operations.Operations.*;

/**
 * <p>This class represents the engine for executing .smscr scripts.</p>
 * 
 * <p>First, text of the script is parsed by {@link SmartScriptParser} 
 * class and a document tree with {@link #documentNode} as top-level 
 * node is constructed. That node is used to visit all existing nodes
 * and do necessary actions.</p>
 * 
 * <p>In this example, instance of {@link RequestContext} class writes 
 * execution result to specified {@link OutputStream} by calling
 * {@linkplain RequestContext#write(String)} method.</p>
 * 
 * @author Vjeran
 */
public class SmartScriptEngine {
	
	/** The document node. */
	private DocumentNode documentNode;
	
	/** The request context. */
	private RequestContext requestContext;
	
	/** The instance of {@link ObjectMultistack}. */
	private ObjectMultistack multistack = new ObjectMultistack();
	
	/** 
	 * The class for visitation of all nodes in {@link #documentNode} 
	 * constructed by {@link SmartScriptParser} class. 
	 */
	private INodeVisitor visitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			multistack.push(variable, new ValueWrapper(node.getStartExpression().asText()));
			while(true){
				if(multistack.peek(variable).numCompare(node.getEndExpression().asText()) > 0){
					break;
				}
				visitChildren(node);
				multistack.peek(variable).increment(node.getStepExpression().asText());
			}
			multistack.remove(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Stack<Object> tempStack = new Stack<>();
			List<Element> elements = node.getElements();
			
			for(Element e : elements){
				if(e instanceof ElementVariable){
					tempStack.push(multistack.peek(e.asText()).getValue());
				} else if(e instanceof ElementConstantDouble){
					tempStack.push(((ElementConstantDouble) e).getValue());
				} else if (e instanceof ElementConstantInteger){
					tempStack.push(((ElementConstantInteger) e).getValue());
				} else if (e instanceof ElementString){
					tempStack.push(((ElementString) e).getValue());
				} else if (e instanceof ElementOperator){
					getOperation((((ElementOperator) e).getSymbol())).execute(tempStack, requestContext);
				} else if (e instanceof ElementFunction){
					getOperation(((ElementFunction) e).getName()).execute(tempStack, requestContext);
				}
			}
			
			if(!tempStack.isEmpty()){
				for(int i = 0, size = tempStack.size(); i < size; i++){
					try {
						Object obj = tempStack.get(i);
						if(tempStack.get(i) instanceof String){
							String string = (String) obj;
							obj = string.replaceAll("[\\\\][n]", "\n")
										.replaceAll("[\\\\][r]", "\r")
										.replaceAll("[\\\\][t]", "\t");
						}
						requestContext.write(obj.toString());
					} catch (IOException e) {
						throw new RuntimeException(
							"Unable to write text."
						);
					}
				}
			}
		}
		
		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}

		@Override
		public void visitEndNode(EndNode node) {
		}
	
		/**
		 * Helper method for visiting all children nodes of the given 
		 * parameter. Parameter can be instance of {@link ForLoopNode} 
		 * or {@link DocumentNode} since those  nodes contain children nodes.
		 *
		 * @param node the node whose children will be visited
		 */
		private void visitChildren(Node node) {
			for(int i = 0; i < node.numberOfChildren(); i++){
				Node n = node.getChild(i);
				n.accept(this);
			}
		}
	};
	
	/**
	 * Instantiates a new smart script engine.
	 *
	 * @param documentNode the document node
	 * @param requestContext the request context
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	/**
	 * Method that starts engine work. It starts with {@link #documentNode},
	 * by calling {@code accept(Visitor visitor)} method whose job is delegated
	 * to {@link #visitor} class.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}
}
