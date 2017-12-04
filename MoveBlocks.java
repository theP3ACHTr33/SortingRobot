import lejos.robotics.subsumption.Behavior;

public class MoveBlocks implements Behavior {	
	public void action() {
		if (MainClass.isClawOpen) {
			if (MainClass.getCurrentPos() != MainClass.movementStack.getFromPos()) {
				MainClass.setNextPos(MainClass.movementStack.getFromPos());
			} else {
				MainClass.openClaw = false;
			}
		} else {
			if (MainClass.getCurrentPos() != MainClass.movementStack.getToPos()) {
				MainClass.setNextPos(MainClass.movementStack.getToPos());
			} else {
				MainClass.openClaw = true;
				MainClass.movementStack.remove();
			}
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return (MainClass.movementStack.getLength() > 0);
	}

}
