package nl.tufa.knapsack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import nl.tufa.common.MathUtils;

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

/**
 * <p>
 * Knapsack class is intended to provide a solution to the knapsack-problem.
 * Basically the knapsack problem is the problem of fitting a maximum value of
 * items with different size and different values into a limited space.
 * </p>
 * <b>Example</b>
 * <p>
 * The most popular example is that of a thief having a backpack of size 4.
 * There are several items to fit into the backpack:
 * <ul>
 * <li>guitar size 1, worth 1500
 * <li>stereo size 4, worth 3000
 * <li>lap-top size 2, worth 2000
 * </ul>
 * The example is obvious for these three item, the thief should place guitar
 * and lap-top in the bag. This leaves 1 unit of space left but has a total
 * value of 3500 which beats the 3000 of the stereo. The knapsack problem can in
 * theory be solved using permutations but the calculation time will not be
 * favorable. The knapsack problem algorithm utilizes a different technique in
 * solving the issue in an attempt to minimize computational time.
 * </p>
 * <p>
 * This implementation allows for fractional sized items and fractional knapsack
 * sizes. After setting the size of the knapsack and adding items to the
 * possible items list the knapsack uses a GCD algorithm to calculate the
 * largest sub-knapsack size for solving the sub-problems.
 * </p>
 * 
 * @author M.C.Slooff
 * @version 1.0
 * @param <T>
 * @see https://en.wikipedia.org/wiki/Knapsack_problem
 */
public class Knapsack<T extends IKnapsackItem> implements Set<T> {

	@SuppressWarnings({ "unused", "hiding" })
	private class SubKnapsack<T extends IKnapsackItem> {

		private double size;
		private Set<IKnapsackItem> items = new HashSet<IKnapsackItem>();

		public SubKnapsack() {
		}

		public SubKnapsack(double size) {
			this.size = size;
		}

		public void setSize(double size) {
			this.size = size;
		}

		public double getSize() {
			return this.size;
		}

		public double getValue() {
			double value = (double) 0;
			for (IKnapsackItem item : items) {
				value += item.getValue();
			}
			return value;
		}

		public double getContentSize() {
			double size = (double) 0;
			for (IKnapsackItem item : items) {
				size += item.getSize();
			}
			return size;
		}

		public double getFreeSpace() {
			return size - getContentSize();
		}

		public Set<IKnapsackItem> getContent() {
			return items;
		}

		public void addItem(IKnapsackItem item) {
			// System.out.println("Adding: " + item.toString());
			items.add(item);
		}

		public void addAll(Collection<? extends IKnapsackItem> c) {
			for (IKnapsackItem item : c) {
				if (!items.contains(item)) {
					items.add(item);
					// System.out.println("Adding: " + item.toString());
				}
			}
		}
	}

	private double knapsackSize;
	private Set<T> possibleItems = new HashSet<T>();
	private Set<T> bestFit = new HashSet<T>();

	public Knapsack(double size) {
		this.knapsackSize = size;
	}
	
	public void setKnapsackSize(double size) {
		this.knapsackSize = size;
	}
	
	public double getKnapsackSize() {
		return this.knapsackSize;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void calculateBestFit() {

		if (possibleItems == null || possibleItems.size() == 0) {
			bestFit = new HashSet<T>();
			return;
		}

		SubKnapsack[][] matrix = null;

		System.out.println("Calculating best sub-knapsack size.");
		List<Double> sizes = new ArrayList<Double>();
		for (IKnapsackItem item : possibleItems) {
			System.out.println("Adding " + item.getSize() + " to possible sizes.");
			sizes.add(item.getSize());
		}
		System.out.println("Adding knapsack size (" + this.knapsackSize + ") to list.");
		sizes.add(knapsackSize);
		System.out.println("Calculating GCD for items.");
		double s = MathUtils.findGCD(sizes);

		int subKnapsackCount = (int) (knapsackSize / s);
		System.out.println("GCD=" + s + ", creating " + subKnapsackCount + " sub-knapsack.");
		matrix = new SubKnapsack[possibleItems.size()][subKnapsackCount];

		int j = 0;
		for (IKnapsackItem item : possibleItems) { // rows
			System.out.print(item.getTag() + "\t");
			for (int i = 0; i < subKnapsackCount; i++) { // columns
				matrix[j][i] = new SubKnapsack();
				matrix[j][i].setSize((i + 1) * s);

				// Start by checking if size is large enough to put current item
				// in the sub-knapsack.
				if (matrix[j][i].getFreeSpace() >= item.getSize()) {
					matrix[j][i].addItem(item);
				}

				// Calculate column referring the remaining available space.
				int col = (int) (matrix[j][i].getFreeSpace() / s) - 1;
				int row = (j == 0 ? j : j - 1);

				// Check if there is space left and if so find the items
				// to fill up the remaining space.
				if (matrix[j][i].getFreeSpace() > 0) {
					matrix[j][i].addAll(matrix[row][col].getContent());
				}

				// Check if the row above has a higher total value. If so
				// adopt the content of the sub-knapsack above.
				if (matrix[row][i].getValue() > matrix[j][i].getValue()) {
					// Row above has higher value, copy that one instead.
					matrix[j][i].getContent().clear();
					matrix[j][i].addAll(matrix[row][i].getContent());
				}

				System.out.print(matrix[j][i].getValue() + "\t");
			}
			System.out.println("");
			j++;
		}

		Set<T> max = matrix[possibleItems.size() - 1][subKnapsackCount - 1].getContent();
		double val = 0;
		double size = 0;
		for (IKnapsackItem item : max) {
			System.out.println(item);
			val += item.getValue();
			size += item.getSize();
		}
		System.out.println("totals: " + size + " out of " + this.knapsackSize + ", " + val);

		bestFit = matrix[possibleItems.size() - 1][subKnapsackCount - 1].getContent();
	}

	public Set<T> getItems() {
		return bestFit;
	}

	public double getTotalValue() {
		double val = 0;
		for (IKnapsackItem item : bestFit) {
			val += item.getValue();
		}
		return val;
	}

	public double getContentSize() {
		double val = 0;
		for (IKnapsackItem item : bestFit) {
			val += item.getSize();
		}
		return val;
	}

	public double getUtilization() {
		double val = 0;
		for (IKnapsackItem item : bestFit) {
			val += item.getSize();
		}
		return val / this.knapsackSize;
	}

	@Override
	public boolean add(T e) {
		return possibleItems.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return possibleItems.addAll(c);
	}

	@Override
	public void clear() {
		possibleItems.clear();
	}

	@Override
	public boolean contains(Object o) {
		return possibleItems.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return possibleItems.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return possibleItems.isEmpty();
	}

	@Override
	public Iterator<T> iterator() {
		return possibleItems.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return possibleItems.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return possibleItems.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return possibleItems.retainAll(c);
	}

	@Override
	public int size() {
		return possibleItems.size();
	}

	@Override
	public Object[] toArray() {
		return possibleItems.toArray();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return possibleItems.toArray(a);
	}

}
