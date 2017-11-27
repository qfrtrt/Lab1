package welcome;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class GraphTest extends TestCase{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() {
		Graph graph = new Graph(null, null);
		String result = graph.generateNewText("");
		assertEquals("",result);
	}
	
	@Test
	public void test2() {
		Graph graph = new Graph(null, null);
		String result = graph.generateNewText("donuts");
		assertEquals("donuts",result);
	}
	
	@Test
	public void test3() {
		Graph graph = new Graph(null, null);
		String result = graph.generateNewText("donuts the and night");
		assertEquals("donuts in the evening and night", result);
	}
	
//

}
