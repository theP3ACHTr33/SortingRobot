import lejos.nxt.LCD;
import lejos.robotics.subsumption.Behavior;

public class MoveBlocks implements Behavior {
	public void action() {
		LCD.drawString("movB", 0, 6);
		LCD.drawInt(MainClass.movBlockStage, 0, 2);
		LCD.refresh();
		if (MainClass.isClawOpen) {
			if (MainClass.currentPos != MainClass.movementStack.getFromPos()) {
				MainClass.nextPos = MainClass.movementStack.getFromPos();
			} else {
				MainClass.openClaw = false;
			}
		} else {
			if (MainClass.currentPos != MainClass.movementStack.getToPos()) {
				MainClass.nextPos = MainClass.movementStack.getToPos();
			} else {
				MainClass.openClaw = true;
				MainClass.movementStack.remove();
			}
		}
//		switch (MainClass.movBlockStage) {
//		case 1: case 3: case 5: case 7: 
//			if (!(MainClass.pilot.isMoving() || Motor.C.isMoving())) {
//				MainClass.movBlockStage +=1;
//			}
//			break;
//		case 0:  
//			MainClass.nextPos = MainClass.movementStack.getFromPos();
//			MainClass.movBlockStage +=1;
//			break;
//		case 2:
//			MainClass.openClaw = false;
//			MainClass.movBlockStage +=1;
//			break;
//		case 4:
//			MainClass.nextPos = MainClass.movementStack.getToPos();
//			MainClass.movBlockStage +=1;
//			break;
//		case 6:
//			MainClass.openClaw = true;
//			MainClass.movBlockStage +=1;
//			break;
//		case 8:
//			MainClass.movBlockStage =0;
//			MainClass.movementStack.remove();
//			LCD.drawInt(MainClass.movementStack.getLength(), 0, 1);
//			break;
//		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return (MainClass.movementStack.getLength() > 0);
	}

}
