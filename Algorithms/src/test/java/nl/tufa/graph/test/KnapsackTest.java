package nl.tufa.graph.test;

import java.util.Set;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.knapsack.CommonSubString;
import nl.tufa.knapsack.Knapsack;
import nl.tufa.knapsack.KnapsackItem;

@SuppressWarnings("deprecation")
public class KnapsackTest {

	@Test
	public void withDiscreteSizes_1() {

		Knapsack<KnapsackItem> k = new Knapsack<KnapsackItem>(4);

		k.add(new KnapsackItem("guitar", 1, 1500));
		k.add(new KnapsackItem("stereo", 4, 3000));
		k.add(new KnapsackItem("laptop", 3, 2000));

		k.calculateBestFit();
		Set<KnapsackItem> content = k.getItems();
		double value = 0;
		for (KnapsackItem item : content) {
			value += item.getValue();
		}

		Assert.assertEquals((double) 3500, value);
	}

	@Test
	public void withDiscreteSizes_2() {

		Knapsack<KnapsackItem> k = new Knapsack<KnapsackItem>(4);

		k.add(new KnapsackItem("guitar", 1, 1500));
		k.add(new KnapsackItem("stereo", 4, 3000));
		k.add(new KnapsackItem("laptop", 3, 2000));
		k.add(new KnapsackItem("phone", 1, 2000));

		k.calculateBestFit();
		Set<KnapsackItem> content = k.getItems();
		double value = 0;
		for (KnapsackItem item : content) {
			value += item.getValue();
		}

		Assert.assertEquals((double) 4000, value);
	}

	@Test
	public void withDecimalSizes_1() {

		Knapsack<KnapsackItem> k = new Knapsack<KnapsackItem>(4);

		k.add(new KnapsackItem("guitar", (double) 1, 1500));
		k.add(new KnapsackItem("stereo", (double) 4, 3000));
		k.add(new KnapsackItem("laptop", (double) 3, 2000));
		k.add(new KnapsackItem("diamond", (double) 0.5, 1000));

		k.calculateBestFit();
		Set<KnapsackItem> content = k.getItems();
		double value = 0;
		for (KnapsackItem item : content) {
			value += item.getValue();
		}

		Assert.assertEquals((double) 3500, value);
	}

	@Test
	public void withDecimalSizes_2() {

		Knapsack<KnapsackItem> k = new Knapsack<KnapsackItem>(4.6);

		k.add(new KnapsackItem("guitar", (double) 1, 1500));
		k.add(new KnapsackItem("stereo", (double) 4, 3000));
		k.add(new KnapsackItem("laptop", (double) 3, 2000));
		k.add(new KnapsackItem("diamond", (double) 0.5, 1000));

		k.calculateBestFit();
		Set<KnapsackItem> content = k.getItems();
		double value = 0;
		for (KnapsackItem item : content) {
			value += item.getValue();
		}

		Assert.assertEquals((double) 4500, value);
	}
	
	@Test
	public void withCommonSubString() {
		
		double m = 0;
		CommonSubString<String> k = new CommonSubString<String>("fish", "fosh");
		System.out.println();
		m = k.getMatchRatio();
		System.out.println(m);
		System.out.println();
		Assert.assertEquals(0.75, m);
		
		k = new CommonSubString<String>("fosh", "fort");
		System.out.println();
		m = k.getMatchRatio();
		System.out.println(m);
		System.out.println();
		Assert.assertEquals(0.5, m);
		
		k = new CommonSubString<String>("banaan", "aanhanger");
		System.out.println();
		m = k.getMatchRatio();
		System.out.println(m);
		System.out.println();
		Assert.assertEquals((double)4/(double)9, m);
		
		k = new CommonSubString<String>("marcel", "marcel");
		System.out.println();
		m = k.getMatchRatio();
		System.out.println(m);
		System.out.println();
		Assert.assertEquals((double)1, m);
		
	}
}
