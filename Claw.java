import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Claw implements Behavior {
	private static int clawStage = 0;
	
	public void action() {
		switch (clawStage) {
		case 0: 
			MainClass.pilot.rotate(90, true); 
			clawStage += 1; 
			break;
		case 1: case 3: case 7: case 9: 
			if (!MainClass.pilot.isMoving()) {
				clawStage +=1;
			} 
			break;
		case 2: 
			MainClass.pilot.travel(MainClass.GRAB_DIST,true); 
			clawStage +=1; 
			break;
		case 4: 
			if (MainClass.isClawOpen) {
				Motor.C.backward();
			} else {
				Motor.C.forward();
			}
			clawStage += 1;
			break;
		case 5:
			if (Motor.C.isStalled()) {
				clawStage +=1;
				Motor.C.stop();
			}
			break;
		case 6: 
			MainClass.pilot.travel(-MainClass.GRAB_DIST,true);
			clawStage +=1;
			break;
		case 8:
			MainClass.pilot.rotate(-90, true);
			clawStage += 1;
			break;
		case 10:
			MainClass.isClawOpen = MainClass.openClaw;
			clawStage = 0;
			break;
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return (MainClass.isClawOpen != MainClass.openClaw);
	}
	
}
