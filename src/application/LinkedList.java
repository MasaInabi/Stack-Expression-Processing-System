package application;

//Masa Inabi
//1231024

public class LinkedList {

	Node head;
	private int size;

	public LinkedList() {
		head = null;
		size = 0;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void addFirst(Object element) {
		head = new Node(element, head);
		size++;
	}

	
	
	 public void addLast(Object element) {
	        Node newNode = new Node(element, null);

	        if (isEmpty()) {
	            head = newNode;
	        } else {
	            Node current = head;

	            while (current.next != null) {
	                current = current.next;
	            }

	            current.next = newNode;
	        }

	        size++;
	    }
	 
	 
	 
	public Object removeFirst() {
		if (isEmpty())
			return null;
		Object e = head.element;
		head = head.next;
		size--;
		return e;
	}

	public Object first() {
		if (isEmpty())
			return null;
		return head.element;
	}
	public void clear() {
	    head = null;
	    size = 0;
	}


	
	
	public Object get(int index) {
	    Node curr = head;
	    int i = 0;

	    while (curr != null) {
	        if (i == index)
	            return curr.element;
	        curr = curr.next;
	        i++;
	    }

	    return null; 
	}

	public int size() {
		return size;
	}
}
