package welcome;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GraphTestBlack {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test1() {
		Graph graph = new Graph(null, null);
		String result = graph.queryBridgeWords("lunch","noon");
		assertEquals("at",result);
	}
	
	@Test
	public void test2() {
		Graph graph = new Graph(null, null);
		String result = graph.queryBridgeWords(this","i");
		assertEquals("No bridge words from this to i!",result);
	}
	
	@Test
	public void test3() {
		Graph graph = new Graph(null, null);
		String result = graph.queryBridgeWords(,noon);
		assertEquals("No eaten or in in the graph!",result);
	}
\
}
