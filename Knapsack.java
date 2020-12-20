/*
 * Taylor Barnes
 * Knapsack problem - Dynamic Programming
 * 
 * Given a list of n items, each with a weight and a price, determine the highest price that can be assembled from items with a maximum combined weight
 * Start with a capacity of 0, then iterate up to the actual capacity of the knapsack
 * For each capacity, iterate over every item that hasn't been collected yet.
 * By this process, determine which items maximize the price of a knapsack for each possible capacity
 * Keep a table of all the data collected on the way for dynamic programming calculations
 * Dynamic Programming allows this to be done in O(n^2)
 * 
 * Input: n weights followed by n prices
 * the ith item has a weight of i and a price of i + n
 * Example:
 * 1 2 4 6 8 3 4 6 3 7
 * 1 8 3 6 8 2 4 2 8 4
 * null
 */

import java.util.*;
import java.io.*;



public class Knapsack {
	final static int N = 10; //number of items in the knapsack
	final static int CAPACITY = 9; //number of units of weight the knapsack can hold
	
	public static void main(String[] args) {
		
		//take in all user input at once
		BufferedReader stdin = null;
		ArrayList<String> strArr = new ArrayList<String>();  
		String[] tokArr = new String[100];
		String line1 = "";
		try {
			stdin = new BufferedReader(new InputStreamReader(System.in));
			line1 = stdin.readLine();
			while(!line1.equals("null")) { //change to line1 != NULL if reading from file
				tokArr = line1.split(" ", 0);
				for(int k = 0; k < tokArr.length; k++) {
					strArr.add(tokArr[k]);
				}
				line1 = stdin.readLine();
			}
		} catch (Exception e) {}
		
		int[] W = new int[N];
		int[] P = new int[N];
		for(int i = 0; i < N; i++)
			W[i] = Integer.parseInt(strArr.get(i));
		for(int i = 0; i < N; i++)
			P[i] = Integer.parseInt(strArr.get(i + N));
		
		//build a table of the profit made from progressively more items and progressively larger weight capacity
		int[][] profit = new int[CAPACITY + 1][N + 1];
		for(int i = 0; i <= CAPACITY; i++) {
			for(int j = 0; j <= N; j++) {
				profit[i][j] = 0;
			}
		}
		
		for(int i = 1; i <= CAPACITY; i++) { //capacity
			for(int j = 1; j <= N; j++) { //item number
				profit[i][j] = profit[i][j - 1]; //profit if we don't include the new item
				
				if(i >= W[j - 1]) { //if it's possible to fit the next item
					int temp = P[j - 1] + profit[i - W[j - 1]][j - 1]; //price of new item + optimal price of remaining space
					profit[i][j] = (profit[i][j] > temp) ? profit[i][j] : temp; //which is greater, with the new item or without
				}
			}
		}
		
		System.out.printf("\nMax profit = %d\n", profit[CAPACITY][N]);
		outputItems(profit, W, CAPACITY, N);
	}
	
	
	public static void outputItems(int[][] profit, int[] W, int b, int n) {
		System.out.printf("The indices of items stolen are: ");
		
		//work backward through the table
		while(b > 0 && n > 0) { //until we hit the corner
			if(profit[b][n - 1] < profit[b][n] && profit[b - 1][n] < profit[b][n]) { //if both adjacent spots are lower than current
				System.out.printf("%d ", n - 1);
				b -= W[n - 1]; //move up by however much weight we're shedding
				n--;
			}
			else if(profit[b][n - 1] > profit[b - 1][n])
				n--;
			else
				b--;
		}
	}
}