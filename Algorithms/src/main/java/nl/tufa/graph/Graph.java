package nl.tufa.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
	Copyright 2020 M.C.Slooff
	
	This file is part of 'Algorithms'
	
	'Algorithms' is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	'Algorithms' is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with 'Algorithms'.  If not, see <https://www.gnu.org/licenses/>.
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.	
*/

public class Graph {

	private List<GraphEdge> edges = new ArrayList<GraphEdge>();
	private List<GraphNode> nodes = new ArrayList<GraphNode>();
	private GraphNode start = null;
	private GraphNode end = null;

	public Graph() {
	}

	public Graph(GraphNode start, GraphNode end) throws NullPointerException, DuplicatedNodeException {
		if (start == null || end == null)
			throw new NullPointerException();
		this.addNode(start);
		this.addNode(end);
		this.start = start;
		this.end = end;
	}

	public GraphNode getStart() {
		return start;
	}

	public void setStart(GraphNode start) {
		this.start = start;
	}

	public GraphNode getEnd() {
		return end;
	}

	public void setEnd(GraphNode end) {
		this.end = end;
	}

	public GraphNode addNode(GraphNode node) throws DuplicatedNodeException, NullPointerException {
		if (node == null)
			throw new NullPointerException();
		if (!nodes.contains(node)) {
			nodes.add(node);
		} else {
			throw new DuplicatedNodeException();
		}
		return node;
	}

	public void addEdge(GraphEdge edge) throws NullPointerException, DuplicateEdgeException {
		if (edge == null)
			throw new NullPointerException();
		if (!edges.contains(edge)) {
			edges.add(edge);
		} else {
			throw new DuplicateEdgeException();
		}
	}

	private GraphNode findNodeByTag(String tag) {

		Optional<GraphNode> node = nodes.stream().filter(n -> n.getTag().equals(tag)).findFirst();
		if (node.isPresent())
			return node.get();
		return new GraphNode(tag);
	}

	public GraphEdge createEdge(String startTag, String endTag, Float weight)
			throws NullPointerException, DuplicateEdgeException {

		GraphNode start = findNodeByTag(startTag);
		GraphNode end = findNodeByTag(endTag);

		return createEdge(start, end, weight);

	}

	public GraphEdge createEdge(GraphNode start, GraphNode end, Float weight)
			throws NullPointerException, DuplicateEdgeException {

		GraphEdge edge = new GraphEdge(start, end, weight);
		this.addEdge(edge);

		return edge;
	}

	private Float lowestCost(GraphNode start) {
		
		List<GraphEdge> edges = this.edges.stream().filter(edge -> edge.getStart().equals(start))
				.collect(Collectors.toList());

		if (start.cost == null)
			start.cost = (float) 0;
		for (GraphEdge edge : edges) {
			//System.out.print(edge.getStart().getTag() + "-" + edge.getEnd().getTag() + "=" + edge.getWeight());

			Float total = start.cost + edge.getWeight();
			//System.out.println(" (" + edge.getEnd().getTag() + ": cur=" + edge.getEnd().cost + ", new=" + total + ")");

			if (edge.getEnd().cost == null || total < edge.getEnd().cost) {
				edge.getEnd().cost = total;
			}
			
			lowestCost(edge.getEnd());
		}

		return this.end.cost;
	}

	public Float getLowestCost(GraphNode start, GraphNode end) throws Exception {

		if (start == null && this.start == null)
			throw new Exception("No start node specified to start from.");
		if (end == null && this.end == null)
			throw new Exception("No end node specified to end at.");
		this.start = (start != null ? start : this.start);
		this.end = (end != null ? end : this.end);
		if (this.start == null || this.end == null)
			throw new Exception("No start and/or end node in graph.");

		for (GraphNode node : nodes) {
			node.cost = null;
		}
		System.out.println("----------------------------");
		return lowestCost(this.start);
	}

	@Override
	public String toString() {
		String result = "";
		for (GraphEdge edge : edges) {
			result += "[" + edge.getStart().getTag() + "-" + edge.getEnd().getTag() + ":" + edge.getWeight() + "]\n";
		}
		return result;
	}

}
