package nl.tufa.graph;

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

public class GraphEdge {
	
	private GraphNode start = null;
	private GraphNode end = null;
	private Float weight = null;

	public GraphEdge(GraphNode start, GraphNode end, Float weight) throws NullPointerException, IllegalArgumentException {
		super();
		if(start==null || end==null) throw new NullPointerException();
		if(start.equals(end)) throw new IllegalArgumentException("Start and end nade are the same.");
		this.start = start;
		this.end = end;
		this.weight = weight;
	}
	
	public GraphNode getStart() {
		return start;
	}
	public void setStart(GraphNode start) throws IllegalArgumentException {
		if(start==null) throw new NullPointerException();
		if(start.equals(end)) throw new IllegalArgumentException("Start and end nade are the same.");
		this.start = start;
	}
	public GraphNode getEnd() {
		return end;
	}
	public void setEnd(GraphNode end) throws IllegalArgumentException {
		if(end==null) throw new NullPointerException();
		if(start.equals(end)) throw new IllegalArgumentException("Start and end nade are the same.");
		this.end = end;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphEdge other = (GraphEdge) obj;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}
	
}
