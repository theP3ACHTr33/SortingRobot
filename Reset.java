import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;

public class Reset implements Behavior {
	public void action() {
		LCD.drawString("res", 0, 6);
		LCD.refresh();
		if (MainClass.currentPos == -1) {
			//Button.ENTER.waitForPressAndRelease();
			//MainClass.isFinished = false;
			//MainClass.sortIndex = (MainClass.sortIndex + 1) % MainClass.NUM_OF_SORTS;
			
			if (Button.ENTER.isDown()) {
				MainClass.movementStack.addNode(1,2);
				//MainClass.isFinished = false;
				//MainClass.sortIndex = (MainClass.sortIndex + 1) % MainClass.NUM_OF_SORTS;
			}
		} else {
			MainClass.nextPos = -1;
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		//return (MainClass.isFinished);
		return(MainClass.movementStack.getLength() == 0);
	}
}
