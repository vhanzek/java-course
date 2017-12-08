package hr.fer.zemris.java.webserver;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The class representing my own HTTP server. All server properties are
 * read from the "server.properties" file on the disk.
 * 
 * @author Vjeran
 */
public class SmartHttpServer {
	
	/** The constant representing last upper case letter. */
	private final static int MAX = 90;
	
	/** The constant representing first upper case letter. */
	private final static int MIN = 65;
	
	/** The constant representing {@link #killSessions} repetition period. */
	private final static int PERIOD = 300;
	
	/** 
	 * The daemon thread whose task is to check {@link #sessions} map every
	 * n seconds where n is given by constant {@link #PERIOD}, and remove every
	 * session that is not valid anymore. 
	 */
	final TimerTask killSessions = new TimerTask() {
		
		@Override
		public void run() {
			for (Map.Entry<String,SessionMapEntry> entry : sessions.entrySet()) {
				if(now() > entry.getValue().getValidUntil()){
					sessions.remove(entry.getKey());
				}
			}
		}
	};
	
	/** The server frame. */
	public static ServerFrame serverFrame;

	/** The address of the server. */
	private String address;
	
	/** The port of the server. */
	private int port;
	
	/** The worker threads. */
	private int workerThreads;
	
	/** The session timeout. */
	private int sessionTimeout;
	
	/** The map of mime types. */
	private Map<String,String> mimeTypes = new HashMap<>();
	
	/** The server thread. */
	private ServerThread serverThread;
	
	/** The thread pool. */
	private ExecutorService threadPool;
	
	/** The document root. */
	private Path documentRoot;
	
	/** The workers map. */
	private Map<String,IWebWorker> workersMap;
	
	/** The sessions. */
	private volatile Map<String, SessionMapEntry> sessions = new HashMap<>();
	
	/** The session random. */
	private Random sessionRandom = new Random();
	
	/**
	 * Instantiates a new HTTP server.
	 *
	 * @param configFileName the path to server properties file
	 */
	public SmartHttpServer(String configFileName) {
		Properties props = new Properties(configFileName);
		
		this.address = props.getProperty("server.address");
		this.port = props.getIntProperty("server.port");
		this.workerThreads = props.getIntProperty("server.workerThreads");
		this.documentRoot = props.getPathProperty("server.documentRoot").toAbsolutePath();
		this.mimeTypes = Properties.getPropertiesForName(props.getProperty("server.mimeConfig"));
		this.sessionTimeout = props.getIntProperty("session.timeout");
		this.workersMap = getWorkersMap(props);
		
		this.serverThread = new ServerThread();
	}
	
	/**
	 * Helper method for retrieving all defined {@link IWebWorker}.
	 * It returns map where keys are names of the workers classes and
	 * values are the instances of worker classes.
	 *
	 * @param props the utility class for reading properties
	 * @return the web workers map
	 */
	private Map<String, IWebWorker> getWorkersMap(Properties props) {
		Map<String, IWebWorker> workersMap = new HashMap<>();
		Map<String, String> workers = 
			Properties.getPropertiesForName(props.getProperty("server.workers"));
		
		workers.forEach((path, fqcn) -> {
			Object newObject = null;
			try{
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				newObject = referenceToClass.newInstance();
			} catch (Exception e){
				e.printStackTrace();
			}
			IWebWorker iww = (IWebWorker)newObject;
			workersMap.put(path, iww);
		});
		
		return workersMap;
	}

	/**
	 * Method for starting server.
	 */
	protected synchronized void start() {
		if(!serverThread.isAlive()){
			serverThread.start();
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
		Timer timer = new Timer(true);
		timer.schedule(killSessions, PERIOD, PERIOD);
	}
	
	/**
	 * Method for stopping server.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdown();
	}
	
	/**
	 * Helper method which returns current time in seconds.
	 *
	 * @return the current time in seconds
	 */
	private long now(){
		return System.currentTimeMillis() / 1000;
	}
	
	/**
	 * The thread which starts when server is opened.
	 * 
	 * @author Vjeran
	 */
	protected class ServerThread extends Thread {
		
		@Override
		public void run() {
			try{
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(
					new InetSocketAddress((InetAddress) null, port)
				);
				serverSocket.setSoTimeout(1000);
				
				while(true) {
					try{	
						Socket client = serverSocket.accept();
						ClientWorker cw = new ClientWorker(client);
						threadPool.execute(cw);
					} catch (SocketTimeoutException | SocketException e){
						if(isInterrupted())	break;
					}					
				}	
				serverSocket.close();
			} catch (IOException ex){
				System.out.println(ex.getMessage());
			} 
		}
	}
	
	/**
	 * The class which represents work that needs to be processed when 
	 * certain client connects to this server.
	 * 
	 * @author Vjeran
	 */
	private class ClientWorker implements Runnable {
		
		/** The client socket. */
		private Socket csocket;
		
		/** The input stream. */
		private PushbackInputStream istream;
		
		/** The output stream. */
		private OutputStream ostream;
		
		/** The version. */
		private String version;
		
		/** The method. */
		private String method;
		
		/** The requested path. */
		private Path requestedPath;
		
		/** The parameters. */
		private Map<String,String> params = new HashMap<String, String>();
		
		/** The persistent parameters. */
		private Map<String,String> permParams = null;
		
		/** The output cookies. */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/** The session ID. */
		@SuppressWarnings("unused")
		private String SID;
		
		/**
		 * Instantiates a new client worker.
		 *
		 * @param csocket the client socket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			serverFrame.setConnected(csocket.getInetAddress());
		}
		
		@Override
		public void run() {
			try{
				istream = new PushbackInputStream(
					csocket.getInputStream()
				);
				ostream = new BufferedOutputStream(
					csocket.getOutputStream()
				);				
				
				//Header
				byte[] requestBytes = readRequest();
				if(requestBytes == null) {
					sendError(400, "Bad request");
					return;
				}
				
				String requestStr = new String(requestBytes, StandardCharsets.US_ASCII);
				List<String> requestHeaders = extractHeaders(requestStr);
				if(requestHeaders.isEmpty()){
					sendError(400, "Bad request");
					return;
				}

				String[] firstLineElements = requestHeaders.get(0).split(" ");
				if(firstLineElements.length != 3){
					sendError(400, "Bad request");
					return;
				} 
				
				method = firstLineElements[0];
				version = firstLineElements[2];
				if(!method.toUpperCase().equals("GET")) {
					sendError(405, "Method Not Allowed");
					return;
				}
				if(!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1")){
					sendError(505, "HTTP Version Not Supported");
					return;
				}
				
				checkSession(requestHeaders);
				
				//Path and parameters
				String path;
				String paramString;
				if(firstLineElements[1].contains("?")){
					String[] elems = firstLineElements[1].split("\\?");
					path = elems[0]; 
					paramString = elems[1];
					parseParameters(paramString);
				} else {
					path = firstLineElements[1];
				}

				//Requested path
				requestedPath = Paths.get(path.startsWith("/") ? path.substring(1) : path);
				requestedPath = documentRoot.resolve(requestedPath);
				if (!requestedPath.startsWith(documentRoot)) {
					sendError(403, "Forbidden");
					return;
				}
				
				//Requested context
				RequestContext rc = new RequestContext(ostream, params, permParams, outputCookies);
				
				//Web workers
				if(path.startsWith("/ext/")){
					Class<?> referenceToClass = 
						this.getClass().getClassLoader().loadClass(
							"hr.fer.zemris.java.webserver.workers." + path.substring(5)
						);
					IWebWorker iww = (IWebWorker) referenceToClass.newInstance();
					processIww(iww, rc);
					return;
				}
				
				IWebWorker iww = workersMap.get(path);
				if(iww != null){
					processIww(iww, rc);
					return;
				}
				
				//Check requested path
				if(Files.notExists(requestedPath) || 
				   !Files.isReadable(requestedPath) || 
				   !Files.isRegularFile(requestedPath)){
					
					sendError(404, "Bad requested path");
					return;
				}
				
				//Extension
				int index = path.lastIndexOf(".");
				String extension = path.substring(index + 1);
				
				//Mime type
				String mimeType = mimeTypes.get(extension);
				if(mimeType == null){
					mimeType = "application/octet-stream";
				}
				rc.setMimeType(mimeType);
				rc.setStatusCode(200);				

				//Write to output stream
				if(extension.equals("smscr")){
					rc.setMimeType("text/plain");
					String docBody = readFromDisk(requestedPath.toString());
					new SmartScriptEngine(
						new SmartScriptParser(docBody).getDocumentNode(), rc)
					.execute();
				} else {
					byte[] content = Files.readAllBytes(requestedPath);
					rc.write(content);
				}

				ostream.flush();
				closeStreams();	
				
			} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ex){
				System.out.println(ex.getMessage());
			} finally {
				try {
					csocket.close();
				} catch (IOException ignorable) {
				}
			}
		}

		/**
		 * Helper method for generating unique session ID.
		 * It returns string that is a concatenation of 20 upper case letters.
		 *
		 * @return the new session ID
		 */
		private synchronized String generateSID() {
			char[] sid = new char[20];
			for(int i = 0; i < 20; i++){
				sid[i] = (char) (sessionRandom.nextInt((MAX - MIN) + 1) + MIN);
			}
			
			return new String(sid);
		}
		
		/**
		 * Helper method for creating new session and registering it to
		 * {@link SmartHttpServer#sessions} map. Also, new {@link RCCookie}
		 * is added to {@link #outputCookies} list with name "sid".
		 *
		 * @param sid the session ID
		 * @return the session map entry
		 */
		private synchronized SessionMapEntry createSession(String sid) {
			sid = generateSID();
			SessionMapEntry entry = new SessionMapEntry(
				sid, now() + sessionTimeout, new ConcurrentHashMap<>()
			);
			sessions.put(sid, entry);
			outputCookies.add(new RCCookie("sid", sid, null, address, "/", true));
			return entry;
		}

		/**
		 * Helper method for checking if certain session already exists i.e.
		 * if user has already visited site. If the answer is negative, new
		 * session is created and registered to {@link SmartHttpServer#sessions}
		 * map. Also, if session exists, its period of validity will be extended.
		 *
		 * @param headers the list of all request headers
		 */
		private synchronized void checkSession(List<String> headers) {
			String sidCandidate = null;
			for(String line : headers){
				if(!line.startsWith("Cookie:")) continue;
				
				line = line.substring(7).trim();
				String[] elements = line.split(";");
				for(String e : elements){
					String[] cookie = e.split("=");
					if(cookie[0].equals("sid")){
						sidCandidate = cookie[1].substring(1, cookie[1].length() - 1);
					}
				}
			}

			SessionMapEntry entry = null;
			if(sidCandidate == null){
				entry = createSession(sidCandidate);
			} else {
				entry = sessions.get(sidCandidate);
				if(entry == null){
					entry = createSession(sidCandidate);
				} else if(now() > entry.getValidUntil()){
					sessions.remove(sidCandidate, entry);
					entry = createSession(sidCandidate);
				} else {
					entry.validUntil = now() + sessionTimeout;
				}
			}
			
			this.SID = entry.getSid();
			this.permParams = entry.getMap();
		}
		
		/**
		 * Helper method for processing given {@link IWebWorker}. After the
		 * data has been sent, input and output streams will be closed.
		 *
		 * @param iww the web worker
		 * @param rc the request context
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private void processIww(IWebWorker iww, RequestContext rc) throws IOException {
			iww.processRequest(rc);
			ostream.flush();
			closeStreams();
		}

		/**
		 * Helper method for closing input and output stream.
		 *
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private void closeStreams() throws IOException {
			istream.close();
			ostream.close();
		}

		/**
		 * Helper method for reading file from the given path and
		 * returning its content as a string.
		 *
		 * @param string the path to the file
		 * @return the content of the file as a string
		 */
		private  String readFromDisk(String string) {
			Path path = Paths.get(string);
			byte[] content = null;
			try {
				content = Files.readAllBytes(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new String(content, Charset.forName("UTF-8"));
		}

		/**
		 * Helper method for extracting headers from the request.
		 *
		 * @param requestHeader the request header
		 * @return the list of all extracted headers
		 */
		private List<String> extractHeaders(String requestHeader) {
			List<String> headers = new ArrayList<String>();
            String currentLine = null;
            
            for(String s : requestHeader.split("\n")) {
                if(s.isEmpty()) break;
                char c = s.charAt(0);
                if(c==9 || c==32) {
                    currentLine += s;
                } else {
                    if(currentLine != null) {
                        headers.add(currentLine);
                    }
                    currentLine = s;
                }
            }
            if(!currentLine.isEmpty()) {
                headers.add(currentLine);
            }
            return headers;
		}

		/**
		 * Parses the parameters.
		 *
		 * @param paramString the param string
		 */
		private void parseParameters(String paramString) {
			String[] parameters = paramString.split("&");
			
			for(String par : parameters){
				String[] elements = par.split("=");
				if(elements.length == 2){
					params.put(elements[0], elements[1]);
				}
			}
		}

		/**
		 * Read request.
		 *
		 * @return the byte[]
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private byte[] readRequest() throws IOException {
			int state = 0;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
l: 			while(true) {
				int b = istream.read();
				if(b == -1) return null;
				if(b != 13) {
					bos.write(b);
				}
				switch(state) {
				case 0:
					if(b == 13) { state=1; } else if(b==10) state=4;
					break;
				case 1:
					if(b == 10) { state=2; } else state=0;
					break;
				case 2:
					if(b == 13) { state=3; } else state=0;
					break;
				case 3:
					if(b == 10) { break l; } else state=0;
					break;
				case 4:
					if(b == 10) { break l; } else state=0;
					break;
				}
			}
			return bos.toByteArray();
		}
		
		/**
		 * Send error.
		 *
		 * @param statusCode the status code
		 * @param statusText the status text
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			ostream.write(
				("HTTP/1.1 " + statusCode + " " + statusText + "\r\n"+
				"Server: SmartHttpServer\r\n"+
				"Content-Type: text/html; charset=UTF-8\r\n"+
				"Content-Length: 0\r\n"+
				"Connection: close\r\n"+
				"\r\n").getBytes(StandardCharsets.US_ASCII)
			);
			ostream.flush();
		}
	}
	
	/**
	 * The Class SessionMapEntry.
	 */
	private static class SessionMapEntry {
		
		/** The session ID. */
		private String sid;
		
		/** The time when session becomes invalid. */
		private long validUntil;
		
		/** The parameters map. */
		private Map<String,String> map;
		
		/**
		 * Instantiates a new session entry.
		 *
		 * @param sid the session ID
		 * @param validUntil the time when session becomes invalid
		 * @param map the parameters map
		 */
		public SessionMapEntry(String sid, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.validUntil = validUntil;
			this.map = map;
		}

		/**
		 * Gets the session ID.
		 *
		 * @return the session ID
		 */
		public String getSid() {
			return sid;
		}

		/**
		 * Gets the time when session becomes invalid.
		 *
		 * @return the time when session becomes invalid
		 */
		public long getValidUntil() {
			return validUntil;
		}

		/**
		 * Gets the parameters map.
		 *
		 * @return the parameters map
		 */
		public Map<String, String> getMap() {
			return map;
		}
	}
	
	/**
	 * The main method, program starts here. Server is encapsulated 
	 * into GUI component i.e. server starts when the frame is opened and
	 * stops when frame is closed.
	 *
	 * @param args the command line arguments. 
	 * 			   Method expects one argument, path to the properties file on the disk.
	 */
	public static void main(String[] args) {
		if(args.length != 1){
			throw new IllegalArgumentException(
				"Illegal number of arguments!"
			);
		}
		
		SwingUtilities.invokeLater(() -> {
			serverFrame = new ServerFrame(args[0]);
			serverFrame.setVisible(true);
		});
	}
}
