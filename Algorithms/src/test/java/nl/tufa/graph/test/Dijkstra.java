package nl.tufa.graph.test;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.graph.Graph;
import nl.tufa.graph.GraphNode;

@SuppressWarnings("deprecation")
public class Dijkstra {
	
	@Test
	public void setupGraph1() {
		
		Graph g = new Graph();
		Assert.assertNotNull(g);
		
		try {
			GraphNode start = g.addNode(new GraphNode("start"));
			GraphNode end = g.addNode(new GraphNode("end"));
			g.addNode(new GraphNode("A"));
			g.addNode(new GraphNode("B"));
			
			g.createEdge("start", "A", (float) 6 );
			g.createEdge("start", "B", (float) 2 );
			g.createEdge("B", "A", (float) 3 );
			g.createEdge("A", "end", (float) 1 );
			g.createEdge("B", "end", (float) 5 );
			
			Float value = g.getLowestCost(start, end);
			
			Assert.assertEquals((float) 6, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void setupGraph2() {
		
		Graph g = new Graph();
		
		try {
			GraphNode start = g.addNode(new GraphNode("start"));
			GraphNode end = g.addNode(new GraphNode("end"));
			
			g.createEdge("start", "a", (float) 5 );
			g.createEdge("start", "b", (float) 2 );
			g.createEdge("b", "a", (float) 8 );
			g.createEdge("a", "c", (float) 4 );
			g.createEdge("a", "d", (float) 2 );
			g.createEdge("b", "d", (float) 7 );
			g.createEdge("c", "d", (float) 6 );
			g.createEdge("c", "end", (float) 3 );
			g.createEdge("d", "end", (float) 1 );
			
			//System.out.println(g.toString());
			
			Float value = g.getLowestCost(start, end);
			
			Assert.assertEquals((float) 8, value);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
