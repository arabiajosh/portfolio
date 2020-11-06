package data_structures;

/**
 * CS 445 Fall 2020
 * Supplementary Materials - Queue 
 * Implements a generic array backed queue, 
 * similar to what is needed to complete
 * Assignment 1. For simplicity, we assume
 * the queue contains no null elements
 * @author Joshua Arabia
 * @since 1 September 2020
 */

public class Queue<T> {

	private static final int DEFAULT_EXTRA_SPACES = 10;

	private T[] data;
	private int head;
	private int size;
	private int capacity;

	/**
	 * Default constructor
	 */
	public Queue() {
		data = null;
		head = -1;
		size = 0;
	}

	/**
	 * Initializes the queue with the given
	 * number of spaces
	 * @param size The initial queue size
	 */
	// @SuppressWarnings("unchecked")
	public Queue(int size) {
		// Casting to a generic array here will
		// cause the compiler to issue a warning.
		// We can prevent this by uncommenting the
		// @SuppressWarnings directive above the
		// method, but I usually just leave it active
		data = (T[]) new Object[size];
		this.head = -1;
		this.size = 0;
	}

	/**
	 * Initializes the queue with the given
	 * array, adding a few extra spaces for
	 * leeway
	 * @param original The data to copy
	 */
	public Queue(T[] original) {
		data = (T[]) new Object[original.length + DEFAULT_EXTRA_SPACES];
		this.head = -1;
		this.size = 0;
		for(int i = 0; i < original.length; i++) {
			data[i] = original[i];
			size++;
		}

		if(size > 0) {
			head = 0;
		}
	}

	/**
	 * Add the given element to the queue,
	 * and return the number of elements
	 * after the operation completes
	 * @param elem The element to add
	 * @return The size of the queue after
	 * the addition
	 */
	public int push(T elem) {
		if(size == data.length) {
			// Check if we need to resize, and
			// double capacity if so
			resize(2 * data.length);
		}

		if(size == 0) {
			data[0] = elem;
			head = 0;
		} else {
			// Calculate the position to insert
			// at, remembering to wrap back to
			// the start of the array if needed
			int ins = (head + size) % data.length;
			data[ins] = elem;
		}

		size++;
		return size;
	}

	/**
	 * Add all the given elements to the 
	 * queue, and return the number of 
	 * elements after the operation completes
	 * @param elem The element to add
	 * @return The size of the queue after
	 * the addition
	 */
	public int push(T[] elems) {
		if(size + elems.length >= data.length) {
			// Check if we need to resize, and
			// increase capacity if so. The amount
			// to increase capacity by depends on
			// how many elements need to be added
			resize(2 * (data.length + elems.length));
		}

		if(size == 0) {
			for(int i = 0; i < elems.length; i++) {
				data[i] = elems[i];
			}
			head = 0;
		} else {
			// The procedure is similar to above, but
			// now we need a separate variable to keep
			// track of where we're inserting
			int ins = (head + size) % data.length;
			for(int i = 0; i < elems.length; i++) {
				data[ins] = elems[i];
				ins = (ins + 1) % data.length;
			}
		}
		size += elems.length;
		return size;
	}

	/**
	 * Returns the first element in the queue,
	 * which would be returned if a single
	 * pop operation occurred
	 * @return The first element in the queue,
	 * or null if the queue is empty
	 */
	public T peek() {
		if(size == 0) {
			return null;
		} else {
			return data[head];
		}
	}

	/**
	 * Removes and returns the first element
	 * in the queue
	 * @return The first element in the queue,
	 * or null if the queue is empty
	 */
	public T pop() {
		if(size == 0) {
			return null;
		} else {
			T ret = (T) data[head];
			// We don't need to actually set an
			// element to null to delete it here. 
			// Instead, we can just change the
			// bounds of the queue on the array
			// to ignore it
			head = (head + 1) % data.length;
			size--;
			if(size == 0) {
				// If we just emptied the queue, we
				// need to set our head to the 
				// indicator value -1
				head = -1;
			}

			return ret;
		}
	}

	/**
	 * Removes the first n elements of the queue
	 * and returns them as an array. Pads with 
	 * nulls if necessary
	 * @param n The number of elements to remove
	 * @return An array of the removed elements
	 */
	public T[] pop(int n) {
		T[] ret = (T[]) new Object[n];
		if(size == 0) {
			// Java Object arrays default to
			// containing nulls, so we can just
			// return here
			return ret;
		} else {
			for(int i = 0, j = head; i < n; i++) {
				ret[i] = (T) data[j];
				j = (j + 1) % data.length;
			}

			head = (head + n) % data.length;
			size -= n;
			if(size == 0) {
				head = -1;
			}

			return ret;
		}
	}

	/**
	 * Returns the number of elements in the queue
	 * @return The queue size
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the number of spaces in the queue
	 * @return The queue capacity
	 */
	public int capacity() {
		return data.length;
	}

	/**
	 * Returns a string representation of this
	 * queue, wherein each element is displayed
	 * with its own string representation
	 * @return A string representation of the queue
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0, j = head; i < size; i++) {
			sb.append(data[j].toString());
			if(i != size - 1) {
				sb.append(", ");
			}

			j = (j + 1) % data.length;
		}

		return sb.append("]").toString();
	}

	/**
	 * Utility: Resizes the queue to have a 
	 * capacity equal to the given value. Does
	 * nothing if capacity is not greater than
	 * the current size
	 * @param capacity The desired capacity of
	 * the queue
	 */
	private void resize(int capacity) {
		if(capacity < size) {
			return;
		} else {
			T[] newData = (T[]) new Object[capacity];
			int curCapacity = data.length;
			for(int i = 0, j = head; i < capacity; i++) {
				newData[i] = data[j];
				j = (j + 1) % data.length;
			}

			data = newData;
			head = 0;
		}
	}
}