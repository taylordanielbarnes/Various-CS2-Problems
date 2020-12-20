/*
 * Taylor Barnes
 * Rod-cutting problem - Dynamic Programming
 * 
 * Given a rod of length n that can be cut into smaller rods, each length with a different price, find max possible revenue
 * Start by considering the smallest possible rod, and find the revenue from that
 * Then iterate over every other possible size up to the limit
 * Keep up with a table of each smaller possibility along the way to aid calculations
 * Dynamic programming allows this to be done in O(n^2)
 * 
 * Input: n prices, separated by spaces
 * The nth price represents the price of a rod of length n
 * Example:
 * 1 5 20 9 10 17 17 20 24 30
 * null
 */

import java.util.*;
import java.io.*;



public class cutRod {
	final static int SIZE = 10; //length of rod, change this depending on input

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
		
		int[] price = new int[SIZE + 1];
		price[0] = 0;
		for(int i = 1; i < SIZE + 1; i++) {
			price[i] = Integer.parseInt(strArr.get(i - 1));
		}
		
		
		int n = SIZE;
		int c = 0; //cost of a single cut, use 0 if the problem does not call for cut cost
		int[] r = new int[SIZE + 1]; //optimized revenue of rod with length i
		int[] s = new int[SIZE + 1]; //optimized location of first cut along rod with length i
		int[] cuts = new int[SIZE + 1]; //optimized number of cuts on rod with length i
		
		
		r[0] = 0; //no money can be made off a rod with no length
		cuts[0] = 0;
		for(int j = 1; j <= n; j++) { //find the max possible revenue for length j = 1, 2, ... n
			int q = -1; //negative revenue, always trigger the if statement once
			for(int i = 1; i <= j; i++) { //for every possible first cut of a rod with length j
				if(q < price[i] + r[j - i]) { //if we're well-suited to make our first cut at i
					if(i < j) { //if a cut is being made
						q = price[i] + r[j - i] - c; //update the maximum possible revenue
						cuts[j] = cuts[j - i] + 1;
					}
					else { //if the max revenue comes from leaving the rod uncut
						q = price[i] + r[j - i]; //update the maximum possible revenue
						cuts[j] = cuts[j - i];
					}
					s[j] = i; //record where the cut was made
				}
			}
			r[j] = q; //record the maximum possible revenue for length j
		}
		
		
		//print results
		System.out.printf("\nRod Length:\t\t\t\t\t\t\t");
		for(int i = 1; i <= n; i++)
			System.out.printf("%3d ", i); //number the columns
		System.out.printf("\nPrice of each length of cut:\t\t\t\t\t");
		for(int i = 1; i <= n; i++)
			System.out.printf("%3d ", price[i]); //price table
		System.out.printf("\nMax revenue from each possible length:\t\t\t\t");
		for(int i = 1; i <= n; i++)
			System.out.printf("%3d ", r[i]); //revenue table
		System.out.printf("\nLength to cut off of rod of given length:\t\t\t");
		for(int i = 1; i <= n; i++)
			System.out.printf("%3d ", s[i]); //location of first cut
		System.out.printf("\nNumber of cuts to maximize revenue from rod of given length:\t");
		for(int i = 1; i <= n; i++)
			System.out.printf("%3d ", cuts[i]); //number of cuts
		System.out.printf("\n\nLengths of rod that maximize revenue: ");
		
		//print where the cuts should be
		while(n > 0) {
			System.out.printf("%d ", s[n]);
			n = n - s[n];
		}
	}
}
