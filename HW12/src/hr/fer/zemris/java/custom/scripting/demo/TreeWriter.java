package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.EndNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * The example class for working with {@link INodeVisitor}. This class
 * reconstructs the document node got from {@link SmartScriptParser} class
 * and prints it to standard output.
 * 
 * @author Vjeran
 */
public class TreeWriter {
	
	/**
	 * The visitor class used for reconstructing and printing document body.
	 * 
	 * @author Vjeran
	 */
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
			visitChildren(node);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}

		@Override
		public void visitEndNode(EndNode node) {
			System.out.print(node.toString());
		}
		
		/**
		 * Helper method for visiting all children nodes of the
		 * given parameter.
		 *
		 * @param node the node whose children will be visited
		 */
		private void visitChildren(Node node) {
			for(int i = 0; i < node.numberOfChildren(); i++){
				Node n = node.getChild(i);
				n.accept(this);
			}
		}
	}
	
	/**
	 * The main method. Program starts here.
	 *
	 * @param args the command line arguments. 
	 * 			   Method expects one argument, path to the file on the disk.
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			throw new IllegalArgumentException(
				"Illegal number of arguments!"
			);
		}
		String docBody = Demo1.readFromDisk(args[0]);
		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
}
