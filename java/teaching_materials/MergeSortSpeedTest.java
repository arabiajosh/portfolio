/**
 * CS 445 Fall 2020
 * Recitation 08 - Merge Sort Speed Testing
 * This program performs a simple speed test
 * on two merge sort implementations to show
 * how preallocating space for the merge can
 * improve performance.
 * Command to run:
 * java MergeSortSpeedTest [arr_length]
 * @author Joshua Arabia
 * @since 27 October 2020
 */

import sorts.MergeSortDemo;
import java.util.Random;

public class MergeSortSpeedTest {

	private static Random r = new Random();
	// The two arrays we'll be sorting
	private static Integer[] a, b;
	// The array length
	private static int n;

	public static void main(String[] args) {

		// Read in the two command line arguments.
		// There isn't much to do if that doesn't
		// work...
		try {
			n = Integer.parseInt(args[0]);
		} catch(Exception e) {
			System.out.println("Exception while parsing command line arguments:");
			e.printStackTrace();
			System.exit(0);
		}

		a = new Integer[n];
		b = new Integer[n];

		// Using the Random instance we created above,
		// we can fill the arrays with values. The 
		// algorithm we are given is generic in type
		// T, so we need to wrap each value as an
		// instance of the Integer class
		for(int i = 0; i < n; i++) {
			// As an important note, we want to call
			// the nextInt() method with no arguments
			// so that that the values are spread over
			// the whole range of int values. If we
			// used a range much smaller than n, we would
			// have a large number of duplicates, which
			// can add noise to the results of the sort
			// based on where these duplicates are distributed
			Integer num = Integer.valueOf(r.nextInt());
			a[i] = num;
			b[i] = num;
		}

		// Using the System.nanoTime() method, 
		// we can estimate the performance of the 
		// first implementation with preallocation
		long startA = System.nanoTime();
		MergeSortDemo.mergeSort(a, n);
		long endA = System.nanoTime();

		// Repeat the process for the second implementation
		long startB = System.nanoTime();
		MergeSortDemo.mergeSortB(b, n);
		long endB = System.nanoTime();

		// Display the results
		System.out.printf("--Results--\n\nTrial Size: %d\n\n", n);
		System.out.printf("With Preallocation: %.3fs\n", convertTimeUtil(startA, endB));
		System.out.printf("Without Preallocation: %.3fs\n", convertTimeUtil(startB, endB));
	}

	/**
	 * Converts start and stop time, in nanoseconds, 
	 * to a period length in seconds.
	 * @param start The lower end of the range
	 * @param end The upper end of the range
	 * @return The length of the range, in seconds
	 */
	private static double convertTimeUtil(long start, long end) {
		return ((double) (end-start)) / 1000000000;
	}
}