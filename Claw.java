import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class Claw implements Behavior {
	//stage the grab/release procedure is at, clawStage +=1 increments to next step
	private static int clawStage = 0;
	
	public void action() {
		//go to stage of procedure then return to arbitrator
		switch (clawStage) {
		case 0: 
			//Move to face object
			MainClass.facing = 2;
			clawStage += 1; 
			break;
		case 1: case 3: case 7:
			//wait till is finished moving, is done after couple of stages
			if (!MainClass.pilot.isMoving()) {
				clawStage +=1;
			} 
			break;
		case 2: 
			//Move forward GRAB_DIST constant
			MainClass.pilot.travel(MainClass.GRAB_DIST,true); 
			clawStage +=1; 
			break;
		case 4: 
			//toggle Claw
			if (MainClass.isClawOpen) {
				Motor.C.backward();
			} else {
				Motor.C.forward();
			}
			clawStage += 1;
			break;
		case 5:
			//Move to next stage only when Motor.C(claw motor) is stalled (not moving)
			if (Motor.C.isStalled()) {
				clawStage +=1;
				Motor.C.stop();
			}
			break;
		case 6:
			//Move backward GRAB_DIST constant, back into initial position
			MainClass.pilot.travel(-MainClass.GRAB_DIST,true);
			clawStage +=1;
			break;
		case 8:
			//Adjust isClawOpen (represents actual value of claw position)
			MainClass.isClawOpen = MainClass.openClaw;
			clawStage = 0;
			break;
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		//intended claw state doesnt match actual claw state
		//note is only checked when in correct position on line due to being lower priority to NextPos behaviour
		return (MainClass.isClawOpen != MainClass.openClaw);
	}
	
}
