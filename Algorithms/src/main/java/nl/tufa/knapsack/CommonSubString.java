package nl.tufa.knapsack;

import java.util.ArrayList;
import java.util.List;

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

public class CommonSubString<T> {

	List<T> word1 = new ArrayList<T>();
	List<T> word2 = new ArrayList<T>();

	public CommonSubString(List<T> word1, List<T> word2) {
		this.word1 = word1;
		this.word2 = word2;
	}

	@SuppressWarnings("unchecked")
	public CommonSubString(String word1, String word2) {
		this.word1 = (List<T>) stringToList(word1);
		this.word2 = (List<T>) stringToList(word2);
	}

	private List<String> stringToList(String word) {
		List<String> r = new ArrayList<String>();
		for (int i = 0; i < word.length(); i++) {
			r.add(word.substring(i, i + 1));
		}
		return r;
	}

	public double getMatchRatio() {

		int[][] cells = new int[word1.size()][word2.size()];

		System.out.print("\t");
		for (int j = 0; j < word2.size(); j++) {
			System.out.print(word2.get(j) + "\t");
		}
		System.out.println();
		
		for (int i = 0; i < word1.size(); i++) {
			System.out.print(word1.get(i) + "\t");
			for (int j = 0; j < word2.size(); j++) {
				if (word1.get(i).equals(word2.get(j))) {
					cells[i][j] = cells[i != 0 ? i - 1 : i][j != 0 ? j - 1 : j] + 1;
				} else {
					if (cells[i != 0 ? i - 1 : i][j] > cells[i][j != 0 ? j - 1 : j]) {
						cells[i][j] = cells[i != 0 ? i - 1 : i][j];
					} else {
						cells[i][j] = cells[i][j != 0 ? j - 1 : j];
					}
				}
				System.out.print(cells[i][j] + "\t");
			}
			System.out.println();
		}
		
		return (double)cells[word1.size() - 1][word2.size() - 1] / (double)(word1.size()>word2.size()?word1.size():word2.size());
	}
}
