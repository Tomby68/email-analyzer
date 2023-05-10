package for_assignment3;

import java.util.Arrays;

public class DisjointSet {
	private int[] sets;
	
	public void createSets(int size) {
		sets = new int[size];
		for (int i = 0; i < sets.length; i++) {
			sets[i] = -1;
		}
	}
	
	public int find(int item) {
		if (sets[item] < 0) {
			return item;
		}
		sets[item] = find(sets[item]);
		return sets[item];
	}
	
	public void union(int item1, int item2) {
		int s1 = find(item1);
		int s2 = find(item2);
		if (sets[s1] > sets[s2]) {
			sets[s2] += sets[s1];
			sets[s1] = s2;
		} else {
			sets[s1] += sets[s2];
			sets[s2] = s1;
		}
	}
	
	public int getTeamSize(int index) {
		if (sets[index] < 0) {
			return sets[index] * -1;
		}
		return getTeamSize(sets[index]);
	}
	
	public String toString() {
		return Arrays.toString(sets);
	}
	
}
