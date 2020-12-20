/*
 * Taylor Barnes
 * Longest Palindrome Subsequence - Dynamic Programming
 * 
 * Given a string of length n, find the longest palindrome subsequence of the string
 * Note: this is not the longest palindrome substring problem.
 * Find palindrome subsequences within small substrings, then iterate up to larger substrings
 * Dynamic programming allows this to be done in O(n^2)
 */

import java.util.*;



public class LPS {
	public static void main(String[] args) {
		
		//Take in a string as user input
		Scanner scnr = new Scanner(System.in);
		System.out.printf("Input string: ");
		String str = scnr.nextLine();
		str = str.replaceAll("[^a-zA-Z]", ""); //remove any spaces, numbers, and punctuation
		for(int i = 0; i < str.length(); i++) //put all letters in lower case
			if(Character.isUpperCase(str.charAt(i)))
				str = str.replace(str.charAt(i), Character.toLowerCase(str.charAt(i)));
		
		//special cases
		if(str.length() == 1)
			System.out.printf("LPS = %s\nLength: %d", str, 1);
		else if(str.length() == 0)
			System.out.printf("No letters available.\n");
			
			
		else {
			int len = str.length();
			int[][] solTable = new int[len + 1][len + 1]; //table of solutions
			
			//populate table with base cases
			for(int i = 2; i < len + 1; i++) {
				solTable[i][i] = 1;
				solTable[i][i - 1] = 0;
				solTable[i][i - 2] = 0;
			} solTable[1][1] = 1;
			
			for(int i = len - 1; i > 0; i--) {
				for(int j = i + 1; j < len + 1; j++) {
					if(str.charAt(i - 1) == str.charAt(j - 1)) //if the first letter == last letter
						solTable[i][j] = solTable[i + 1][j - 1] + 2;
					else
						solTable[i][j] = (solTable[i][j - 1] > solTable[i + 1][j]) ? (solTable[i][j - 1]) : (solTable[i + 1][j]);
				}
			}
			System.out.printf("\nLength of LPS: %d\n", solTable[1][len]); //Answer to LPS problem
			printLPS(solTable, str, len); //full solution to LPS problem
		}
		
		scnr.close(); //close resources
	}
	
	public static void printLPS(int[][] solTable, String str, int len) {
		int i = 1, j = len;
		int max = solTable[i][j];
		int stickyMax = max; //the highest number in the table
		ArrayList<Character> printable = new ArrayList<Character>();
		
		//move backward across table
		while(max > 0) {
			if(solTable[i][j - 1] == solTable[i + 1][j]) {
				if(max > solTable[i + 1][j - 1]) { //if we have found a pair of matching letters
					printable.add(str.charAt(j - 1));
					max = solTable[i + 1][j - 1];
				}
				i++;
				j--;
			}
			else if(solTable[i][j - 1] > solTable[i + 1][j])
				j--;
			else
				i++;
		}
		
		System.out.printf("LPS: ");
		for(i = 0; i < stickyMax; i++) {
			if(i < printable.size())
				System.out.printf("%c", printable.get(i));
			else //print backward
				System.out.printf("%c", printable.get(stickyMax - i - 1));
		}
	}
}
