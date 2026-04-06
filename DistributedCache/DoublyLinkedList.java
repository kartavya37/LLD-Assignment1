
public class DoublyLinkedList {
	private ListNode head;
	private ListNode tail;
	private int size;

	public DoublyLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}

	private ListNode findNode(String value) {
		ListNode trav = head;

		while (trav != null) {
			if (trav.getValue().equals(value)) {
				return trav;
			}

			trav = trav.getNext();
		}

		return null;
	}

	public void insertAtHead(String value) {
		if (size == 0) {
			head = new ListNode(value, null, null);
			tail = head;
			size++;
			return;
		}

		ListNode node = new ListNode(value, null, head);
		head.setPrev(node);
		head = node;
		size++;
	}

	public void delete(String value) {
		if (size == 0)
			return;

		ListNode foundNode = findNode(value);
		if (foundNode == null) {
			return;
		}

		if (size == 1) {
			head = tail = null;

			foundNode = null;
		} else if (foundNode == head) {
			head = head.getNext();
			head.setPrev(null);

			foundNode = null;
		} else if (foundNode == tail) {
			ListNode prevNode = foundNode.getPrev();
			prevNode.setNext(null);
			tail = prevNode;

			foundNode = null;
		} else {
			ListNode prevNode = foundNode.getPrev();
			ListNode nextNode = foundNode.getNext();

			prevNode.setNext(nextNode);
			nextNode.setPrev(prevNode);

			foundNode = null;
		}

		size--;
	}

	public void moveToHead(String value) {
		if (size == 0)
			return;

		ListNode foundNode = findNode(value);
		if (foundNode == null) {
			return;
		}

		ListNode prevNode = foundNode.getPrev();
		ListNode nextNode = foundNode.getNext();

		if (prevNode == null && nextNode == null) {
			return;
		} else if (prevNode == null) {
			return;
		} else if (nextNode == null) {
			foundNode.reset();

			prevNode.setNext(null);
			foundNode.setNext(head);
			head.setPrev(foundNode);
			head = foundNode;
			tail = prevNode;
		} else {
			foundNode.reset();

			prevNode.setNext(nextNode);
			nextNode.setPrev(prevNode);

			foundNode.setNext(head);
			head.setPrev(foundNode);
			head = foundNode;
		}
	}

	public String getTailValue() {
		if (tail == null)
			return null;
		return tail.getValue();
	}

	public int getSize() {
		return size;
	}

	public void deleteAtTail() {
		if (size == 0)
			return;

		if (head == tail) {
			head = tail = null;
		} else {
			ListNode prevNode = tail.getPrev();
			tail = prevNode;
			prevNode.setNext(null);
		}

		size--;
	}
}

class ListNode {
	String value;
	ListNode prev;
	ListNode next;

	public ListNode(String value, ListNode prev, ListNode next) {
		this.value = value;
		this.prev = prev;
		this.next = next;
	}

	public void setPrev(ListNode prev) {
		this.prev = prev;
	}

	public void setNext(ListNode next) {
		this.next = next;
	}

	public ListNode getPrev() {
		return this.prev;
	}

	public ListNode getNext() {
		return this.next;
	}

	public String getValue() {
		return this.value;
	}

	public void reset() {
		this.prev = null;
		this.next = null;
	}
}
