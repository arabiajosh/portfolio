package data_structures;

/**
 * CS 445 Fall 2020
 * Supplementary Materials - Linked List 
 * Implements a generic linked list, with
 * all basic operations required to complete
 * the Assignment 2 MyStringBuilder class. As
 * with the Queue class, assume no null elements
 * @author Joshua Arabia
 * @since 15 September 2020
 */

public class LinkedList<T> {

	private Node head;
	private Node tail;
	private int size;

	/**
	 * Default constructor
	 */
	public LinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Create a new LinkedList with initialized
	 * with the elements in the T array elems
	 * @param elems The elements to include
	 */
	public LinkedList(T[] elems) {
		head = null;
		tail = null;
		size = 0;

		// Check if there are elements to add,
		// if not there is no more else to do
		if(elems != null && elems.length > 0) {
			// We set the head individually because
			// there is currently no list to attach
			// it to
			head = new Node(elems[0]);
			tail = head;

			Node cur = head;
			for(int i = 1; i < elems.length; i++) {
				// For every element in the elems array
				// append it to the end of the list and
				// move up one step
				cur.next = new Node(elems[i]);
				cur = cur.next;
				tail = cur;
			}

			size = elems.length;
		}
	}

	/**
	 * Adds the given element to the end of
	 * this list, returning the size after the
	 * operation completes
	 * @param elem The element to be added
	 * @return The size after the addition
	 */
	public int append(T elem) {
		tail.next = new Node(elem);
		tail = tail.next;
		return ++size;
	}

	/**
	 * Inserts the given element at the specified
	 * position, returning the size after the
	 * operation completes, or -1 if the position is
	 * invalid
	 * @param elem The element to be added
	 * @param pos The position to add the element
	 * @return -1 if pos is invalid, or the size after
	 * the addition otherwise
	 */
	public int insert(T elem, int pos) {
		if(pos < 0 || pos > size + 1) {
			// In a more complex implementation you
			// might throw an error for an invalid
			// position. Here we just return the indicator
			// value -1
			return -1;
		} else {
			// Special case where we prepend to the list.
			// We need to handle this seperately because
			// there is no node before head to attach the 
			// new node to
			if(pos == 0) {
				Node ins = new Node(elem, head);
				head = ins;
				size++;
			} else {
				// When it comes time to insert, before will
				// correspond to the node whose .next reference
				// will point to the new node
				Node before = head;
				for(int i = 0; i < pos - 1; i++) {
					before = before.next;
				}

				// Since we haven't performed the insert yet,
				// before.next refers to the node that will
				// come after the new node
				Node ins = new Node(elem, before.next);
				before.next = ins;
				size++;
			}

			return size;
		}
	}

	/**
	 * Deletes the element at position pos, 
	 * returning whatever was deleted
	 * @param pos The position to delete at
	 * @return The deleted element, null if
	 * the index is invalid
	 */
	public T delete(int pos) {
		if(pos < 0 || pos >= size) {
			return null;
		} else if(pos == 0) {
			Node ret = head;
			// One interesting aspect of linked lists
			// is that we don't actually need to set
			// deleted elements to null. Simply removing
			// them from the chain is enough
			head = head.next;
			size--;
			return (T) ret.data;
		} else {
			Node prev = null;
			Node cur = head;
			for(int i = 0; i < pos; i++) {
				prev = cur;
				cur = cur.next;
			}

			prev.next = cur.next;
			// Special case where we delete the 
			// last element of the list. Need 
			// to move the tail reference back one
			if(tail == cur) {
				tail = prev;
			}
			size--;
			return (T) cur.data;
		}
	}

	/**
	 * Tests whether at least one instance
	 * of the given element occurs at least
	 * once in the list
	 * @param elem The element to search for
	 * @return True if the element is found,
	 * false otherwise
	 */
	public boolean contains(T elem) {
		Node cur = head;
		while(cur != null) {
			if(cur.data == elem) {
				// Found a match, so return true
				return true;
			}

			cur = cur.next;
		}
		
		// No matches found, so return false
		return false;
	}

	/**
	 * Finds the first occurence of the given
	 * element, and returns its index. If no
	 * instance is found, returns -1
	 * @param elem The element to search for
	 * @return The index of the first occurence
	 * of elem, or -1 if none are found
	 */
	public int indexOf(T elem) {
		Node cur = head;
		int i = 0;
		while(cur != null) {
			if(cur.data == elem) {
				return i;
			}

			cur = cur.next;
			i++;
		}

		return -1;
	}

	/**
	 * Replaces the element at pos with the 
	 * given element, returning the replaced
	 * element.
	 * @param pos The position to replace at
	 * @param elem The element to replace with
	 * @return The replaced element, or null
	 * if the index is invalid
	 */
	public T replace(int pos, T elem) {
		if(pos < 0 || pos >= size) {
			return null;
		} else {
			Node cur = head;
			for(int i = 0; i < pos; i++) {
				cur = cur.next;
			}

			T ret = (T) cur.data;
			cur.data = elem;
			return ret;
		}
	}

	/**
	 * Gets the first element of this list
	 * @return The first element of the list
	 */
	public Node<T> head() {
		return head;
	}

	/**
	 * Gets the number of elements in the list
	 * @return The size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Tests whether the list is empty. That is,
	 * is the size 0
	 * @return True if size is zero, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Constructs a string containing each of 
	 * the elements in the list, in the order they
	 * appear
	 * @return A string representation of this list
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Node cur = head;
		String curString;
		while(cur != null) {
			if(cur == head) {
				curString = String.format("* %s", cur.data.toString());
			} else {
				curString = String.format(" -> %s", cur.data.toString());
			}
			sb.append(curString);
			cur = cur.next;
		}

		return sb.toString();
	}

	/**
	 * This private class represents a single
	 * node in the linked list, and is also
	 * generic in type T. Since it is an inner
	 * class, the LinkedList outer class can
	 * access all of its members directly,
	 * regardless of privacy level
	 */
	private static class Node<T>{

		private T data;
		private Node next;

		public Node(T data) {
			this.data = data;
		}

		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
}