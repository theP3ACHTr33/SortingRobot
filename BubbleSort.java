import lejos.robotics.subsumption.Behavior;

public class BubbleSort implements Behavior{
	private int blockAValue = -1, blockBValue = -1, blockAPos = 0, blockBPos = 1;
	private boolean madeChanges = false;
	
	public void action() {
		if (blockAValue == -1) {
			if (MainClass.currentPos != blockAPos) {
				MainClass.nextPos = blockAPos;
			} else {
				blockAValue = MainClass.getValue();
			}
		} else if (blockBValue == -1) {
			if (MainClass.currentPos != blockBPos) {
				MainClass.nextPos = blockBPos;
			} else {
				blockBValue = MainClass.getValue();
			}
		} else if (blockAValue > blockBValue) {
			MainClass.movementStack.addNode(-1, blockBPos);
			MainClass.movementStack.addNode(blockBPos,blockAPos);
			MainClass.movementStack.addNode(blockAPos,-1);
			MainClass.switchValues(blockAPos, blockBPos);
			int temp = blockAValue;
			blockAValue = blockBValue;
			blockBValue = temp;
			madeChanges = true;
		} else {
			blockAValue = -1;
			blockBValue = -1;
			if (blockBPos < MainClass.NUM_OF_BLOCKS -1) {
				blockAPos +=1;
				blockBPos +=1;
			} else {
				blockAPos = 0;
				blockBPos = 1;
				if (!madeChanges) {
					MainClass.isFinished = true;
				}
			}
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return true;
	}
}
