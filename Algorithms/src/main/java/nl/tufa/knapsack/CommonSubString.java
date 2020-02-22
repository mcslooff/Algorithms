package nl.tufa.knapsack;

import java.util.ArrayList;
import java.util.List;

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
