/**
 * CS 445 Fall 2020
 * Recitation 09 - Radix Sort Speed Testing
 * This program performs a simple speed test
 * on a radix sorting algorithm, using a variant
 * of last week's merge sort as a control.
 * Command to run:
 * java RadixSortSpeedTest [arr_length] [max_word_length]
 * @author Joshua Arabia
 * @since 2 November 2020
 */

import sorts.MergeSortQuickDemo;
import sorts.RadixSortDemo;
import java.util.Random;

public class RadixSortSpeedTest {

	private static Random r = new Random();
	// The two arrays we'll be sorting
	private static String[] s, s2;
	// The array and word lengths, respectively
	private static int n, l;

	public static void main(String[] args) {
		// Read in the two command line arguments.
		// There isn't much to do if that doesn't
		// work...
		try {
			n = Integer.parseInt(args[0]);
			l = Integer.parseInt(args[1]);
		} catch(Exception e) {
			System.out.println("Exception while parsing command line arguments:");
			e.printStackTrace();
			System.exit(0);
		}
		
		s = new String[n];
		s2 = new String[n];

		// Generate the contents of each of the
		// arrays to be sorted, and fill them
		for(int i = 0; i < n; i++) {
			String w = randWord(l);
			s[i] = w;
			s2[i] = w;
		}

		// Perform the sort on each array using
		// a different method while timing the
		// operation 
		long mergeStart = System.nanoTime();
		MergeSortQuickDemo.mergeSort(s, n);
		long mergeEnd = System.nanoTime();

		long radixStart = System.nanoTime();
		RadixSortDemo.RadixSort(s2);
		long radixEnd = System.nanoTime();

		// Display the results
		System.out.println("-- Results --");
		System.out.printf("n : %d\nl : %d\n", n, l);
		System.out.printf("MergeSort : %f\n", convertTimeUtil(mergeStart, mergeEnd));
		System.out.printf("RadixSort : %f\n", convertTimeUtil(radixStart, radixEnd));
	}

	/**
	 * Generates a random string of capital letters
	 * of between 1 and maxSize characters
	 * @param maxSize the longest possible length
	 * @return A string of random capital letters
	 */
	public static String randWord(int maxSize) {
		int wordLength = r.nextInt(maxSize-1)+1;
		char[] c = new char[wordLength];
		for(int i = 0; i < wordLength; i++) {
			c[i] = (char) (r.nextInt(26)+65);
		}
		return new String(c);
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