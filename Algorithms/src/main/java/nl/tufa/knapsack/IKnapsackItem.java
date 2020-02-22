package nl.tufa.knapsack;

public interface IKnapsackItem {
	
	public String getTag();
	public void setTag(String tag);
	
	public Double getValue();
	public void setValue(Double value);
	
	public Double getSize();
	public void setSize(Double size);
	
}
