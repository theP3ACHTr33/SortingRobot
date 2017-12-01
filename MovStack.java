
public class MovStack {
	private MovNode top;
	private int length = 0;
	
	public void addNode() {
		addNode(-1,-1);
	}
	
	public void addNode(int inputFromValue, int inputToValue) {
		System.out.println(inputFromValue+ "" + inputToValue);
		MovNode temp = new MovNode();
		temp.next = top;
		temp.fromPos = inputFromValue;
		temp.toPos = inputToValue;
		top = temp;
		length +=1;
	}
	
	public int getFromPos() {
		return top.fromPos;
	}
	
	public int getToPos() {
		return top.toPos;
	}
	
	public void remove() {
		if (top != null) {
			top = top.next;
			length -=1;
		}
	}
	
	public int getLength() {
		return length;
	}
	
	public boolean isEmpty() {
		return (length > 0);
	}
	
	public String toString() {
		String toReturn = "MovStack(";
		MovNode temp = top;
		while (temp != null) {
			toReturn += "(fromPos=" + temp.fromPos + ",toPos=" + temp.toPos + ")";
			temp = temp.next;
		}
		toReturn += ")";
		return toReturn;
	}
}
