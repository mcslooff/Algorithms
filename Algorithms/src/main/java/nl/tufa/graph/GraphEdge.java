package nl.tufa.graph;

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
