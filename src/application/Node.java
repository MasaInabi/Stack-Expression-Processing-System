package application;

//Masa Inabi
//1231024

public class Node {
	public Object element;
	public Node next;

	public Node(Object element) {
		this.element = element;
		this.next = null;
	}

	public Node(Object element, Node next) {
		this.element = element;
		this.next = next;
	}
}
