package application;

//Masa Inabi
//1231024

public class Stack {
	private LinkedList list;

	public Stack() {
		list = new LinkedList();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void push(Object element) {
		list.addFirst(element);
	}

	public Object pop() {
		return list.removeFirst();
	}

	public Object top() {
		return list.first();
	}

	public int size() {
		return list.size();
	}
}
