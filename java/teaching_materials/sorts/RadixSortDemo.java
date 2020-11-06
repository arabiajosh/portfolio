package sorts;

// CS 0445 Fall 2020
// Demonstration of RadixSort on an array of String.  Note that this algorithm
// is simpler in idea than in implementation.  Consider the overhead necessary to
// set it up and also the extra space necessary for the bins.  Note that the run-time
// is clearly O(KN) where K is the maximum length of a String.

import java.util.*;

public class RadixSortDemo
{
	public static int numValues = 27;
	
	public static void RadixSort(String [] data)
	{	
		ArrayList<Queue<String>> bins;  // bins will be an ArrayList of Queue<String>
		if (data.length > 0)
		{
			// Get max length of any String and convert all Strings to upper case.
			// If we want a case-sensitive sort then we need separate bins for upper
			// and lower case letters.
			int max = data[0].length();
			data[0] = data[0].toUpperCase();
			for (int i = 1; i < data.length; i++)
			{
				if (data[i].length() > max) max = data[i].length();
				data[i] = data[i].toUpperCase();
			}
			
			// Create bins.  We need one for each possible letter.  We are actually
			// creating 27 bins here -- one for each letter plus one for a "null"
			// character (i.e. no character at that position).  We add this extra
			// character so that words like "A", "AA", and "AAA" will be ordered
			// as expected (with "A" being the "smallest").  In effect we are
			// considering "A" to be "A@@" and "AA" to be "AA@".
			bins = new ArrayList<Queue<String>>();
			for (int i = 0; i < numValues; i++)
				bins.add(new LinkedList<String>());
			
			char curr;
			for (int k = max-1; k >= 0; k--)  // Iterate max times, once for each loc
			{								  // going from right to left
				for (int i = 0; i < data.length; i++)  // Process all N strings
				{
					if (data[i].length()-1 < k)
						curr = '@';  // No char so use '@'.  This char comes before
									 // all of the letters in the ASCII table so it
									 // will be "smaller" than any real letter in a
									 // word.
					else
						curr = data[i].charAt(k);
					// Convert the char value into an array index so we can access
					// the bins.  Since '@' is the "smallest" character that will be
					// bin 0 and all others will come afterward.
					int loc = curr - '@';
					// Put the string into the correct bin
					bins.get(loc).offer(data[i]);
				}
				// Now take the strings out of the bins and put them back into
				// the array.
				int count = 0;
				for (int j = 0; j < bins.size(); j++)
				{
					while (!bins.get(j).isEmpty())
					{
						data[count] = bins.get(j).poll();
						count++;
					}
				}
			}
		}
	}
	
	public static void main(String [] args)
	{
		System.out.println("Original data: ");
		for (int i = 0; i < args.length; i++)
			System.out.print(args[i] + " ");
		System.out.println();
		
		RadixSort(args);
		
		System.out.println("Sorted data: ");
		for (int i = 0; i < args.length; i++)
			System.out.print(args[i] + " ");
		System.out.println();
	}
}			
		