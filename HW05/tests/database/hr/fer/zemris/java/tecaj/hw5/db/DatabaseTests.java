package hr.fer.zemris.java.tecaj.hw5.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.tecaj.hw5.db.fields.FirstNameValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fields.IFieldValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fields.JmbagValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.fields.LastNameValueGetter;
import hr.fer.zemris.java.tecaj.hw5.db.lexer.parser.QueryParserException;
import hr.fer.zemris.java.tecaj.hw5.db.operators.EqualsOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.GreaterThanOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.IComparisonOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessThanOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LessThanOrEqualOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.LikeOperator;
import hr.fer.zemris.java.tecaj.hw5.db.operators.NotEqualOperator;

public class DatabaseTests {
	StudentRecord record;

	@Before
	public void setUp() {
		record = new StudentRecord("0000000003", "Bengalka", "Krešo", 5);
	}

	@Test
	public void testRegularCreation() {
		assertEquals("Invalid string.", "0000000003", record.getJmbag());
		assertEquals("Invalid string.", "Krešo", record.getFirstName());
		assertEquals("Invalid string.", "Bengalka", record.getLastName());
		assertEquals("Invalid string.", 5, record.getFinalGrade());
	}

	@Test
	public void testEqual() {
		IFilter filter1 = new QueryFilter(" firstName   = \"Krešo\" ");
		IFilter filter2 = new QueryFilter(" firstName   = \"Vojko\" ");
		assertEquals("Invalid string.", true, filter1.accepts(record));
		assertEquals("Invalid string.", false, filter2.accepts(record));
	}

	@Test
	public void testNotEqual() {
		IFilter filter1 = new QueryFilter(" firstName   != \"Krešo\" ");
		IFilter filter2 = new QueryFilter(" firstName   != \"Vojko\" ");
		assertEquals("Invalid string.", false, filter1.accepts(record));
		assertEquals("Invalid string.", true, filter2.accepts(record));
	}

	@Test
	public void testGreater() {
		IFilter filter1 = new QueryFilter(" firstName   > \"Krešo\" ");
		IFilter filter2 = new QueryFilter(" firstName   > \"Vojko\" ");
		assertEquals("Invalid string.", false, filter1.accepts(record));
		assertEquals("Invalid string.", false, filter2.accepts(record));
	}

	@Test
	public void testGreaterEqual() {
		IFilter filter1 = new QueryFilter(" firstName   >= \"Krešo\" ");
		IFilter filter2 = new QueryFilter(" firstName   >= \"Vojko\" ");
		assertEquals("Invalid string.", true, filter1.accepts(record));
		assertEquals("Invalid string.", false, filter2.accepts(record));
	}

	@Test
	public void testLess() {
		IFilter filter1 = new QueryFilter(" firstName   < \"Krešo\" ");
		IFilter filter2 = new QueryFilter(" firstName   < \"Vojko\" ");
		assertEquals("Invalid string.", false, filter1.accepts(record));
		assertEquals("Invalid string.", true, filter2.accepts(record));
	}

	@Test
	public void testLessEqual() {
		IFilter filter1 = new QueryFilter(" firstName   <= \"Krešo\" ");
		IFilter filter2 = new QueryFilter(" firstName   <= \"Vojko\" ");
		assertEquals("Invalid string.", true, filter1.accepts(record));
		assertEquals("Invalid string.", true, filter2.accepts(record));
	}

	@Test
	public void testWildcard() {
		IFilter filter1 = new QueryFilter(" lastName LIKE \"Be*ka\" ");
		IFilter filter2 = new QueryFilter(" lastName LIKE \"Be*vka\" ");
		IFilter filter3 = new QueryFilter(" jmbag LIKE \"*3\" ");
		IFilter filter4 = new QueryFilter(" jmbag LIKE \"123*\" ");
		IFilter filter5 = new QueryFilter(" lastName LIKE \"*\" ");
		IFilter filter6 = new QueryFilter(" lastName LIKE \"Bengalka\" ");
		assertEquals("Invalid string.", true, filter1.accepts(record));
		assertEquals("Invalid string.", false, filter2.accepts(record));
		assertEquals("Invalid string.", true, filter3.accepts(record));
		assertEquals("Invalid string.", false, filter4.accepts(record));
		assertEquals("Invalid string.", true, filter5.accepts(record));
		assertEquals("Invalid string.", true, filter6.accepts(record));
	}

	@Test
	public void testMultipleExpressions() {
		IFilter filter1 = new QueryFilter(
				" lastName LIKE \"Be*ka\" anD firstName   <= \"Krešo\" aNd  firstName   >= \"Krešo\"  "
						+ " And   firstName   != \"Vojko\" ");
		assertEquals("Invalid string.", true, filter1.accepts(record));
	}

	@Test
	public void testDatabaseMap() throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		StudentDatabase database = new StudentDatabase(lines);
		assertEquals("Invalid string.", true, database.forJMBAG("0000000003").getLastName().equals("Bosnić"));
	}

	@Test
	public void testRecordEquals() {
		StudentRecord record1 = new StudentRecord("0000000003", "Vojko", "V", 4);
		assertEquals("Invalid string.", true, record.equals(record1));
	}

	@Test(expected = NullPointerException.class)
	public void testNull1() {
		IFieldValueGetter getter = new FirstNameValueGetter();
		getter.get(null);
	}

	@Test(expected = NullPointerException.class)
	public void testNull2() {
		IFieldValueGetter getter = new LastNameValueGetter();
		getter.get(null);
	}

	@Test(expected = NullPointerException.class)
	public void testNull3() {
		IFieldValueGetter getter = new JmbagValueGetter();
		getter.get(null);
	}

	@Test(expected = NullPointerException.class)
	public void testNull4() {
		IComparisonOperator operator = new EqualsOperator();
		operator.satisfied(null, "abc");
	}

	@Test(expected = NullPointerException.class)
	public void testNull5() {
		IComparisonOperator operator = new NotEqualOperator();
		operator.satisfied(null, "abc");
	}

	@Test(expected = NullPointerException.class)
	public void testNull6() {
		IComparisonOperator operator = new GreaterThanOrEqualOperator();
		operator.satisfied(null, "abc");
	}

	@Test(expected = NullPointerException.class)
	public void testNull7() {
		IComparisonOperator operator = new LessThanOrEqualOperator();
		operator.satisfied(null, "abc");
	}

	@Test(expected = NullPointerException.class)
	public void testNull8() {
		IComparisonOperator operator = new LessThanOperator();
		operator.satisfied(null, "abc");
	}

	@Test(expected = NullPointerException.class)
	public void testNull9() {
		IComparisonOperator operator = new GreaterThanOperator();
		operator.satisfied(null, "abc");
	}

	@Test(expected = NullPointerException.class)
	public void testNull10() {
		IComparisonOperator operator = new LikeOperator();
		operator.satisfied(null, "abc");
	}

	@Test(expected = QueryException.class)
	public void testIllegalLikeOperator() {
		IComparisonOperator operator = new LikeOperator();
		operator.satisfied("abc", "12*34*56");
	}
	
	@Test(expected = QueryException.class)
	public void testSyntaxError(){
		IFilter filter = new QueryFilter(" lastKrName LIKE \"Be*ka\" dico");
		filter.accepts(record);
	}
	
	@Test(expected = QueryParserException.class)
	public void testIllegalOrderOfElements(){
		IFilter filter = new QueryFilter(" \"Baka\" = firstName and > LIKE");
		filter.accepts(record);
	}
	
	

}
