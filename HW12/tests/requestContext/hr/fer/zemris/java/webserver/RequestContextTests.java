package hr.fer.zemris.java.webserver;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class RequestContextTests {
	private static final String HEADER = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html; charset=UTF-8\r\n"
			+ "Set-Cookie: ime=\"Marko\"; Domain=fer.hr; Path=/; Max-Age=21\r\n"
			+ "Set-Cookie: ime=\"Ivan\"; Domain=ffzg.hr; Path=/; Max-Age=20\r\n\r\n";
	
	@Test
	 public void testConstructorWithoutNull() throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream("test1.txt");
		HashMap<String, String>parameters = new HashMap<>();
		HashMap<String, String>persistentParameters = new HashMap<>();
		List<RCCookie> outputCookies = new ArrayList<>();
		
		parameters.put("test", "123");
		persistentParameters.put("test", "456");
		
		RequestContext rc = new RequestContext(os, parameters, persistentParameters, outputCookies);
		assertEquals(rc.getParameter("test"), "123");
		assertEquals(rc.getPersistentParameter("test"), "456");
	}
	
	@Test
	 public void testConstructorWithNull() throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream("test1.txt");
		
		RequestContext rc = new RequestContext(os, null, null, null);
		assertEquals(rc.getParameter("test"), null);
		assertEquals(rc.getPersistentParameter("test"), null);
	}
	
	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	 public void testConstructorOSNull() throws IOException {
		FileOutputStream os = new FileOutputStream("test1.txt");
		RequestContext rc = new RequestContext(null, null, null, null);
		os.close();
	}
	
	@Test
	 public void testSetParameters() throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream("test1.txt");
		RequestContext rc = new RequestContext(os, null, null, null);
		
		rc.setPersistentParameter("test", "456");
		rc.setTemporaryParameter("test", "123");
		
		assertEquals(rc.getPersistentParameter("test"), "456");
		assertEquals(rc.getTemporaryParameter("test"), "123");
		
		rc.removePersistentParameter("test");
		rc.removeTemporaryParameter("test");
		
		assertEquals(rc.getPersistentParameter("test"), null);
		assertEquals(rc.getTemporaryParameter("test"), null);
	}
	
	@Test
	 public void testAddingCookie() throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream("test1.txt");
		RequestContext rc = new RequestContext(os, null, null, null);
		
		RCCookie cookie = new RCCookie("ime", "val", 100, "127.0.0.1", "/", false);
		
		cookie.setDomain("127.0.0.2");
		cookie.setName("name");
		cookie.setPath("//");
		cookie.setValue("val1");
		
		assertEquals(cookie.getName(), "name");
		assertEquals(cookie.getValue(), "val1");
		assertEquals(cookie.getMaxAge(), Integer.valueOf(100));
		assertEquals(cookie.getDomain(), "127.0.0.2");
		assertEquals(cookie.getPath(), "//");
		assertEquals(cookie.isHttpOnly(), false);
		
		RCCookie cookie1 = new RCCookie("ime", "val", 100, "127.0.0.1", "/", true);
		
		assertEquals(cookie1.isHttpOnly(), true);
		
		rc.addRCCookie(cookie);
	}
	
	@Test
	 public void testDefault() throws FileNotFoundException {
		FileOutputStream os = new FileOutputStream("test1.txt");
		RequestContext rc = new RequestContext(os, null, null, null);
		
		assertEquals(rc.statusCode, 200);
		assertEquals(rc.statusText, "OK");
		assertEquals(rc.mimeType, "text/html");
		assertEquals(rc.encoding, "UTF-8");
		
		rc.setMimeType("text/plain");
		rc.setStatusCode(100);
		rc.setEncoding("US");
		rc.setStatusText("NOTOK");
		
		assertEquals(rc.statusCode, 100);
		assertEquals(rc.statusText, "NOTOK");
		assertEquals(rc.mimeType, "text/plain");
		assertEquals(rc.encoding, "US");
	}

	@Test
	public void testCharset1() {
		RequestContext rc = fillInSomeData(System.out);
		assertEquals("Invalid value", null, rc.getCharset());
	}

	@Test
	public void testCharset2() throws IOException {
		OutputStream os = new ByteArrayOutputStream();
		RequestContext rc = fillInSomeData(os);
		rc.write("");
		assertEquals("Invalid value", StandardCharsets.UTF_8, rc.getCharset());
	}

	@Test
	public void testCharset3() throws IOException {
		OutputStream os = new ByteArrayOutputStream();
		RequestContext rc = fillInSomeData(os);
		rc.write("");
		rc.setCharset(StandardCharsets.US_ASCII);
		assertEquals("Invalid value", StandardCharsets.US_ASCII, rc.getCharset());
	}

	@Test
	public void testWrite1() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext rc = fillInSomeData(os);
		rc.write("esi mi dobar");
		assertEquals("Invalid output", HEADER + "esi mi dobar", new String(os.toByteArray(), StandardCharsets.UTF_8));
	}

	@Test
	public void testWrite2() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext rc = fillInSomeData(os);
		rc.write("");
		
		assertEquals("Invalid output", HEADER, new String(os.toByteArray(), StandardCharsets.UTF_8));
	}

	@Test
	public void testWrite3() throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext rc = fillInSomeData(os);
		rc.setCharset(StandardCharsets.ISO_8859_1);
		rc.write("nego šta nego da sam dobar");
		assertEquals("Invalid output",
				HEADER + new String("nego šta nego da sam dobar".getBytes(), StandardCharsets.ISO_8859_1),
				new String(os.toByteArray(), StandardCharsets.ISO_8859_1));
	}

	@Test(expected = RuntimeException.class)
	public void testChangeAfterHeader1() throws IOException {
		RequestContext rc = fillInSomeData(System.out);
		rc.write("");
		rc.setEncoding("UTF-8");

	}

	@Test(expected = RuntimeException.class)
	public void testC() throws IOException {
		RequestContext rc = new RequestContext(System.out, null, null, null);
		rc.write("");
		rc.setMimeType("novi");
	}

	@Test(expected = RuntimeException.class)
	public void testChangeAfterHeader3() throws IOException {
		RequestContext rc = new RequestContext(System.out, null, null, null);
		rc.write("");
		rc.setEncoding("haha");
	}

	@Test(expected = RuntimeException.class)
	public void testChangeAfterHeader4() throws IOException {
		RequestContext rc = new RequestContext(System.out, null, null, null);
		rc.write("");
		rc.setStatusText("haha");
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void testCookie1() {
		RCCookie cookie = new RCCookie(null, "hej", null, null, null, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCookie2() {
		@SuppressWarnings("unused")
		RCCookie cookie = new RCCookie("hoj", null, null, null, null, false);
	}

	@Test
	public void testCookie3() {
		RCCookie cookie = new RCCookie("Svi", "smo", 1, "Zdravko", "Mamić", false);
		assertEquals("Invalid output", "Svismo1ZdravkoMamić", cookie.getName() + cookie.getValue()
				+ Integer.toString(cookie.getMaxAge()) + cookie.getDomain() + cookie.getPath());
	}

	private RequestContext fillInSomeData(OutputStream os) {
		Map<String, String> params = new HashMap<>();
		params.put("1", "Matej");
		params.put("2", "Dunja");

		Map<String, String> perParams = new HashMap<>();
		perParams.put("3", "Ivo");
		perParams.put("4", "Mislav");

		List<RCCookie> cookies = new ArrayList<>();
		cookies.add(new RCCookie("ime", "Marko", 21, "fer.hr", "/", false));
		cookies.add(new RCCookie("ime", "Ivan", 20, "ffzg.hr", "/", false));

		return new RequestContext(os, params, perParams, cookies);
	}
}