public class MovQueue {
	//first node in linked list
	public MovNode first;
	//last node in ll
	public MovNode last;
	//length of ll
	public int length;
	
	//add a node to end fo the ll
	public void addNode(int inputFromPos, int inputToPos) {
		MovNode temp = new MovNode();
		temp.fromPos = inputFromPos;
		temp.toPos = inputToPos;
		if (last == null) {
			first = temp;
			last = temp;
		} else {
			last.next = temp;
			last = temp;
		}
		length +=1;
	}
	
	//remove first node in ll
	public void removeNode() {
		if (length == 1 || length == 0) {
			first = null;
			last = null;
			length = 0;
		} else {
			first = first.next;
			length -=1;
		}
	}
	
	//fromPos of first node in ll
	public int getFromPos() {
		return (first == null)?-2:first.fromPos;
	}
	
	//toPos of first node in ll
	public int getToPos() {
		return (first == null)?-2:first.toPos;
	}
	
	public boolean isEmpty() {
		return (length == 0);
	}
	
	public int getLength() {
		return length;
	}
}
