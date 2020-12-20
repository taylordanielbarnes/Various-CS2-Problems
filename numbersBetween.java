/*
 * Taylor Barnes
 * 
 * Determine how many numbers exist in a list within a certain range
 * Start by sorting a list of random numbers - quicksort is average O(nlgn)
 * Then binary search for the upper and lower bounds
 * And subtract their indices to determine how many numbers in the list lie between them
 */

import java.util.Random;


public class numbersBetween {
	final static int SIZE = 100; //how many numbers in the list
	final static int MAX = 10000; //how large the numbers can be
	final static int LEFT = 0; //lower limit on numbers in query
	final static int RIGHT = 100; //upper limit on numbers in query
	
	public static void main(String[] args) {
		Random rand = new Random();
		int[] ar = new int[SIZE];
		
		//create a list of random numbers
		for(int i = 0; i < SIZE; i++)
			ar[i] = Math.abs(rand.nextInt() % MAX);
		
		sort(ar, 0, SIZE - 1); //quicksort the list
		
		//print the sorted contents of the array
		for(int i = 0; i < SIZE; i++) {
			System.out.printf("%5d ", ar[i]);
			if(i % 20 == 19) System.out.println("");
		}
		
		query(ar, LEFT, RIGHT); //run algorithm to find upper and lower bounds
	}
	
	//recursive sorting algorithm
	public static void sort(int[] a, int p, int r) {
		int q = partition(a, p, r); //set pivot point
		if(p < q - 1)
			sort(a, p, q - 1); //sort everything less than the pivot
		if(q + 1 < r)
			sort(a, q + 1, r); //sort everything greater than the pivot
		return;
	}
	
	public static int partition(int[] a, int p, int r) {
		int q = p;
		int temp;
		
		for(int i = p; i < r; i++) { //iterate across everything to the left/right of the pivot
			if(a[i] <= a[r]) { //change this line to >= to sort in decreasing order
			
				//swap the selected number if it is on the wrong side of the pivot
				temp = a[i];
				a[i] = a[q];
				a[q] = temp;
								
				q++;
			}
		}
		
		//put the pivot in its place
		temp = a[q];
		a[q] = a[r];
		a[r] = temp;
		
		return q;
	}
	
	
	public static void query(int[] a, int low, int high) {
		int lowInd, highInd;
		lowInd = bins(a, 0, a.length - 1, low); //search for lower bound
		highInd = bins(a, 0, a.length - 1, high); //search for upper bound
		
		//print results
		int sol = highInd - lowInd;
		if(sol == 1)
			System.out.printf("\nThere is 1 number in the array between [%d, %d]", low, high);
		else
			System.out.printf("\nThere are %d numbers in the array between [%d, %d]", sol, low, high);
	}
	
	//binary search for the number closest to the target
	public static int bins(int[] a, int bottom, int top, int target) {
		int median = (top - bottom) / 2;
		
		//base case: if the array has only 2 elements
		if((top - bottom) + 1 < 3) {
			if(a[bottom] >= target)
				return bottom;
			else
				return bottom + 1;
		}
	
		//if the median number is closest to the target
		if(a[median + bottom + 1] > target && a[median + bottom] <= target)
			return median + bottom + 1;
		if(a[median + bottom] >= target && a[median + bottom - 1] < target)
			return median + bottom;
		
		//if we need to continue binary searching
		if(a[median + bottom] > target)
			return bins(a, bottom, median + bottom - 1, target);
		else if(a[median + bottom] < target)
			return bins(a, median + bottom + 1, top, target);
		else
			return median + bottom;
	}
}