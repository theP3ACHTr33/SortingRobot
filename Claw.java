import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Claw implements Behavior {
	public void action() {
		LCD.drawString("Claw", 0, 6);
		LCD.refresh();
		switch (MainClass.clawStage) {
		case 0: 
			MainClass.pilot.rotate(90, true); 
			MainClass.clawStage += 1; 
			break;
		case 1: case 3: case 7: case 9: 
			if (!MainClass.pilot.isMoving()) {
				MainClass.clawStage +=1;
			} 
			break;
		case 2: 
			MainClass.pilot.travel(MainClass.GRAB_DIST,true); 
			MainClass.clawStage +=1; 
			break;
		case 4: 
			if (MainClass.isClawOpen) {
				Motor.C.backward();
			} else {
				Motor.C.forward();
			}
			MainClass.clawStage += 1;
			break;
		case 5:
			if (Motor.C.isStalled()) {
				MainClass.clawStage +=1;
				Motor.C.stop();
			}
			break;
		case 6: 
			MainClass.pilot.travel(-MainClass.GRAB_DIST,true);
			MainClass.clawStage +=1;
			break;
		case 8:
			MainClass.pilot.rotate(-90, true);
			MainClass.clawStage += 1;
			break;
		case 10:
			MainClass.isClawOpen = MainClass.openClaw;
			MainClass.clawStage = 0;
			LCD.drawInt(MainClass.movementStack.getLength(), 0, 1);
			break;
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return (MainClass.isClawOpen != MainClass.openClaw);
	}
	
}
