package hr.fer.zemris.java.tecaj.hw3.prob1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * The Class Prob1Test.
 */
public class Prob1Test {


	/**
	 * Test not null.
	 */
	@Test
	public void testNotNull() {
		Lexer lexer = new Lexer("");
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	
	/**
	 * Test null input.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new Lexer(null);
	}

	/**
	 * Test empty.
	 */
	@Test
	public void testEmpty() {
		Lexer lexer = new Lexer("");
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	/**
	 * Test get returns last next.
	 */
	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	/**
	 * Test rad after eof.
	 */
	@Test(expected=LexerException.class)
	public void testRadAfterEOF() {
		Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	/**
	 * Test no actual content.
	 */
	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		Lexer lexer = new Lexer("   \r\n\t    ");
		
		assertEquals("Input had no content. Lexer should generated only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	/**
	 * Test two words.
	 */
	@Test
	public void testTwoWords() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "Štefanija"),
			new Token(TokenType.WORD, "Automobil"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Test word starting with escape.
	 */
	@Test
	public void testWordStartingWithEscape() {
		Lexer lexer = new Lexer("  \\1st  \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "1st"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Test invalid escape ending.
	 */
	@Test(expected=LexerException.class)
	public void testInvalidEscapeEnding() {
		Lexer lexer = new Lexer("   \\");  // this is three spaces and a single backslash -- 4 letters string

		// will throw!
		lexer.nextToken();
	}

	/**
	 * Test invalid escape.
	 */
	@Test(expected=LexerException.class)
	public void testInvalidEscape() {
		Lexer lexer = new Lexer("   \\a    ");

		// will throw!
		lexer.nextToken();
	}

	/**
	 * Test single escaped digit.
	 */
	@Test
	public void testSingleEscapedDigit() {
		Lexer lexer = new Lexer("  \\1  ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "1"),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Test word with many escapes.
	 */
	@Test
	public void testWordWithManyEscapes() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  ab\\1\\2cd\\3 ab\\2\\1cd\\4\\\\ \r\n\t   ");

		// We expect the following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "ab12cd3"),
			new Token(TokenType.WORD, "ab21cd4\\"), // this is 8-letter long, not nine! Only single backslash!
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Test two numbers.
	 */
	@Test
	public void testTwoNumbers() {
		// Lets check for several numbers...
		Lexer lexer = new Lexer("  1234\r\n\t 5678   ");

		Token correctData[] = {
			new Token(TokenType.NUMBER, Long.valueOf(1234)),
			new Token(TokenType.NUMBER, Long.valueOf(5678)),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Test too big number.
	 */
	@Test(expected=LexerException.class)
	public void testTooBigNumber() {
		Lexer lexer = new Lexer("  12345678912123123432123   ");

		// will throw!
		lexer.nextToken();
	}

	/**
	 * Test word with many escapes and numbers.
	 */
	@Test
	public void testWordWithManyEscapesAndNumbers() {
		// Lets check for several words...
		Lexer lexer = new Lexer("  ab\\123cd ab\\2\\1cd\\4\\\\ \r\n\t   ");

		// We expect following stream of tokens
		Token correctData[] = {
			new Token(TokenType.WORD, "ab1"),
			new Token(TokenType.NUMBER, Long.valueOf(23)),
			new Token(TokenType.WORD, "cd"),
			new Token(TokenType.WORD, "ab21cd4\\"), // this is 8-letter long, not nine! Only single backslash!
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Test some symbols.
	 */
	@Test
	public void testSomeSymbols() {
		// Lets check for several symbols...
		Lexer lexer = new Lexer("  -.? \r\n\t ##   ");

		Token correctData[] = {
			new Token(TokenType.SYMBOL, Character.valueOf('-')),
			new Token(TokenType.SYMBOL, Character.valueOf('.')),
			new Token(TokenType.SYMBOL, Character.valueOf('?')),
			new Token(TokenType.SYMBOL, Character.valueOf('#')),
			new Token(TokenType.SYMBOL, Character.valueOf('#')),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}

	/**
	 * Test combined input.
	 */
	@Test
	public void testCombinedInput() {
		// Lets check for several symbols...
		Lexer lexer = new Lexer("Janko 3! Jasmina 5; -24");

		Token correctData[] = {
			new Token(TokenType.WORD, "Janko"),
			new Token(TokenType.NUMBER, Long.valueOf(3)),
			new Token(TokenType.SYMBOL, Character.valueOf('!')),
			new Token(TokenType.WORD, "Jasmina"),
			new Token(TokenType.NUMBER, Long.valueOf(5)),
			new Token(TokenType.SYMBOL, Character.valueOf(';')),
			new Token(TokenType.SYMBOL, Character.valueOf('-')),
			new Token(TokenType.NUMBER, Long.valueOf(24)),
			new Token(TokenType.EOF, null)
		};

		checkTokenStream(lexer, correctData);
	}
	
	// Helper method for checking if lexer generates the same stream of tokens
	/**
	 * Check token stream.
	 *
	 * @param lexer the lexer
	 * @param correctData the correct data
	 */
	// as the given stream.
	private void checkTokenStream(Lexer lexer, Token[] correctData) {
		int counter = 0;
		for(Token expected : correctData) {
			Token actual = lexer.nextToken();
			String msg = "Checking token "+counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}

	// ----------------------------------------------------------------------------------------------------------
	// --------------------- Second part of tests; uncomment when everything above works ------------------------
	// ----------------------------------------------------------------------------------------------------------

	
	
	/**
	 * Test null state.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testNullState() {
		new Lexer("").setState(null);
	}
	
	
	/**
	 * Test not null in extended.
	 */
	@Test
	public void testNotNullInExtended() {
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);
		
		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	
	/**
	 * Test empty in extended.
	 */
	@Test
	public void testEmptyInExtended() {
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);
		
		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	
	/**
	 * Test get returns last next in extended.
	 */
	@Test
	public void testGetReturnsLastNextInExtended() {
		// Calling getToken once or several times after calling nextToken must return each time what nextToken returned...
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);
		
		Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	
	/**
	 * Test rad after eof in extended.
	 */
	@Test(expected=LexerException.class)
	public void testRadAfterEOFInExtended() {
		Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}
	
	
	/**
	 * Test no actual content in extended.
	 */
	@Test
	public void testNoActualContentInExtended() {
		// When input is only of spaces, tabs, newlines, etc...
		Lexer lexer = new Lexer("   \r\n\t    ");
		lexer.setState(LexerState.EXTENDED);
		
		assertEquals("Input had no content. Lexer should generated only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}
	
	
	/**
	 * Test multipart input.
	 */
	@Test
	public void testMultipartInput() {
		// Test input which has parts which are tokenized by different rules...
		Lexer lexer = new Lexer("Janko 3# Ivana26\\a 463abc#zzz");

		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "Janko"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, Long.valueOf(3)));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('#')));
		
		lexer.setState(LexerState.EXTENDED);
		
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "Ivana26\\a"));
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "463abc"));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('#')));
		
		lexer.setState(LexerState.BASIC);
		
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "zzz"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
		
	}
	
	/**
	 * Check token.
	 *
	 * @param actual the actual
	 * @param expected the expected
	 */
	private void checkToken(Token actual, Token expected) {
			String msg = "Token are not equal.";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
	}
}
