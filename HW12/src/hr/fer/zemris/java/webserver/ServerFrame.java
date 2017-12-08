package hr.fer.zemris.java.webserver;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/**
 * The frame which starts the {@link SmartHttpServer}. With this frame's disposal, 
 * server will also be stopped.
 * 
 * @author Vjeran
 */
public class ServerFrame extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5862766902456842433L;
	
	/** The text area. */
	private JTextArea area;
	
	/**
	 * Instantiates a new server frame.
	 *
	 * @param configuration the path to server properties file
	 */
	public ServerFrame(String configuration) {
		SmartHttpServer server = new SmartHttpServer(configuration);
		server.start();
		
		setTitle("Server");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(300, 300, 300, 200);
		setMinimumSize(getSize());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				server.stop();
			}
		});
		
		initGUI();
	}

	/**
	 * Method for initializing GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		area = new JTextArea("# Close the window to stop server.\nServer started...\n");
		area.setEditable(false);
		cp.add(new JScrollPane(area), BorderLayout.CENTER);
	}
	
	/**
	 * Method for informing user that server has been connected to certain address.
	 * 
	 * @param the client address 
	 */
	public void setConnected(InetAddress address){
		area.append("Server connected to " + address.toString().substring(1) + ".\n");
		repaint();
	}
}
