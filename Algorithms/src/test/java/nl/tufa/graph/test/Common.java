package nl.tufa.graph.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import nl.tufa.common.MathUtils;

@SuppressWarnings("deprecation")
public class Common {
	
	@Test
	public void withIntegerGCD() {
		
		int gcd = (int) MathUtils.gcd(9, 15);
		
		Assert.assertEquals(3, gcd);
		
	}
	
	@Test
	public void withIntegerArrayGCD() {
		
		Double[] values = {(double) 9, (double) 15, (double) 27};
		
		int gcd = (int) MathUtils.findGCD(values);
		
		Assert.assertEquals(3, gcd);
	}
	
	@Test
	public void withDecimalGCD() {
		
		double gcd = MathUtils.gcd(1.20, 22.50);
		
		Assert.assertEquals(0.30, gcd);
	}
	
	@Test
	public void withDecimalListGCD_1() {
		
		List<Double> list = new ArrayList<Double>();
		
		list.add(1.50);
		list.add(22.50);
		list.add(3.50);
		
		double gcd = MathUtils.findGCD(list);
		
		Assert.assertEquals(0.50, gcd);
		
	}
	
	@Test
	public void withDecimalListGCD_2() {
		
		List<Double> list = new ArrayList<Double>();
		
		list.add((double)1);
		list.add((double)3);
		list.add((double)2);
		list.add((double)0.5);
		
		double gcd = MathUtils.findGCD(list);
		
		Assert.assertEquals(0.50, gcd);
		
	}
	
	@Test
	public void withDecimalListGCD_3() {
		
		List<Double> list = new ArrayList<Double>();
		
		list.add((double)1);
		list.add((double)3);
		list.add((double)2);
		list.add((double)2.5);
		
		double gcd = MathUtils.findGCD(list);
		
		Assert.assertEquals(0.50, gcd);
		
	}
}
