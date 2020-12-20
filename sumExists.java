/*
 * Taylor Barnes
 * 
 * Given a list of numbers, check if any two numbers on the list sum up to a given solution
 * To do this, we first sort the list using merge sort - O(nlgn)
 * Then, we iterate across the sorted list from both ends at once - O(n)
 * Output: the first two numbers found that add up to the proposed sum
 */

import java.util.Random;



public class sumExists {
	final static int SIZE = 60; //how many numbers in the list
	final static int ADDEND_MAX = 400; //how large the numbers can be
	final static int RAND_SUM_MAX = 800; //how large the sum can be

	public static void main(String[] args) {
		Random rand = new Random();
		int[] ar = new int[SIZE];
		
		//create a list of random numbers
		for(int i = 0; i < SIZE; i++) {
			ar[i] = Math.abs(rand.nextInt() % ADDEND_MAX);
		}
		
		sort(ar, 0, SIZE); //merge sort the list - O(nlgn)
		
		//print the entire list for reference
		for(int i = 0; i < SIZE; i++) {
			System.out.printf("%3d ", ar[i]);
			if(i % 20 == 19) System.out.println("");
		}
		System.out.printf("\n\n\n");
		
		int x = Math.abs(rand.nextInt() % RAND_SUM_MAX); //random proposed sum
		System.out.printf("Looking for two numbers in list that sum to %d...\n", x);
		sum(ar, SIZE, x);
	}
	
	/*
	 * iterate over the list from both ends until the two ends meet - O(n)
	 * or until two numbers add up to the proposed sum
	 */
	public static void sum(int[] a, int sz, int x) {
		int i = 0;
		int j = sz - 1;
		
		while(true) {
			if(i >= j) //when the two ends meet
				break;
				
			if(a[i] + a[j] > x) //if the right end + the left end is too big
				j--;
			else if(a[i] + a[j] < x) //if the right end + the left end is too small
				i++;
			else { //if the two ends add up to the proposed sum
				System.out.printf("Found %d + %d = %d\n", a[i], a[j], x);
				return;
			}
		}
		
		System.out.printf("No addends found!");
	}
	
	
	
	public static void merge(int[] a, int startInd, int leftSize, int rightSize) {
		int newSize = leftSize + rightSize; //size of the current selection
		int i, l = 0, r = 0;
		int[] left = new int[leftSize];
		int[] right = new int[rightSize];
		
		//populate two arrays, one representing the left half of what's being sorted, one representing the right half
		for(i = 0; i < leftSize; i++) {
			left[i] = a[i + startInd];
		}
		for(i = 0; i < rightSize; i++) {
			right[i] = a[i + startInd + leftSize];
		}
		
		//iterate across the two arrays, merging them together
		for(i = 0; i < newSize; i++) {
			if(l >= leftSize) { //if the left array is empty
				a[i + startInd] = right[r];
				r++;
			}
			else if(r >= rightSize) { //if the right array is empty
				a[i + startInd] = left[l];
				l++;
			}
			else if(left[l] < right[r]) { //if the next option in the left array is smaller
				a[i + startInd] = left[l];
				l++;
			}
			else{ //if the next option in the right array is smaller
				a[i + startInd] = right[r];
				r++;
			}
		}
	}
	
	//recursive algorithm
	public static void sort(int[] a, int startInd, int sz) {
		if(sz < 2) {
			return;
		}
		else {
			int demarc = ((sz - 1) / 2) + 1; //halfway through the current selection
			sort(a, startInd, demarc); //sort the left half of current selection
			sort(a, startInd + demarc, sz - demarc); //sort the right half of current selection
			merge(a, startInd, demarc, sz - demarc); //merge those two halves
		}
		
		return;
	}
}
