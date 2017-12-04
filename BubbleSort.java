import lejos.robotics.subsumption.Behavior;

public class BubbleSort implements Behavior{
	//the values and positions of objects being checked
	private int blockAValue = -1, blockBValue = -1, blockAPos = 0, blockBPos = 1;
	//whether a pass of the algorithm made any changes (a pass with no changes = algorithm finished)
	private boolean madeChanges = false;
	
	public void action() {
		//getting blockAValue
		if (blockAValue == -1) {
			if (MainClass.getCurrentPos() != blockAPos) {
				MainClass.setNextPos(blockAPos);
			} else {
				blockAValue = MainClass.getValue();
			}
		//getting blockBValue
		} else if (blockBValue == -1) {
			if (MainClass.getCurrentPos() != blockBPos) {
				MainClass.setNextPos(blockBPos);
			} else {
				blockBValue = MainClass.getValue();
			}
		//switching 2 cvlaues using movementQueue and MoveBlocks behaviour
		} else if (blockAValue > blockBValue) {
			//phisically switch objects
			MainClass.movementQueue.addNode(blockAPos,-1);
			MainClass.movementQueue.addNode(blockBPos,blockAPos);
			MainClass.movementQueue.addNode(-1, blockBPos);
			//switch values in array in MainClass (the one for hardcoded values)
			MainClass.switchValues(blockAPos, blockBPos);
			//switch blockAValue and blockBValue to no longer satisfy blockAValue > blockBValue condition
			int temp = blockAValue;
			blockAValue = blockBValue;
			blockBValue = temp;
			//change was made so other pass will be needed
			madeChanges = true;
		} else {
			//Move along BlockA and BlockB by 1
			blockAValue = blockBValue;
			blockBValue = -1;
			blockAPos +=1;
			blockBPos +=1;
			//If at the end of the line of blocks
			if (blockBPos >= MainClass.NUM_OF_BLOCKS) {
				//reset to beginning
				blockAPos = 0;
				blockBPos = 1;
				//if no changes were made then algorithm is finished
				if (!madeChanges) {
					MainClass.isFinished = true;
				}
			}
			//reset madeChanges for next pass
			madeChanges = false;
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return true;
	}
}
