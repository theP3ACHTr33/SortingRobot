import lejos.nxt.Button;
import lejos.robotics.subsumption.Behavior;

public class Reset implements Behavior {
	public void action() {
		if (MainClass.getCurrentPos() == -1) {
			//wait till ENTER is pressed then incrememnt sort and restart
			if (Button.ENTER.isDown()) {
				MainClass.isFinished = false;
				MainClass.sortIndex = (MainClass.sortIndex + 1) % MainClass.NUM_OF_SORTS;
			}
		} else {
			MainClass.setNextPos(-1);
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return (MainClass.isFinished);
	}
}
