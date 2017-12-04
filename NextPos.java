import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class NextPos implements Behavior {	
	//stage thta procedure is at
	private int movingStage = 0;
	//used for getting stable line follow, basically if stays greyish for a distence then
	//  the robot is nicely on the line so then can start looking for a sudden black spike
	private int greyDist = 0;
	
	public void action() {
		//##Maybe addapt to use pilot
		
		//get reading of light sensor
		int reading = MainClass.ls.readValue();
		//adjust so doesnt go crazy
		if (reading > 100) { reading = 100; }//150?
		if (reading < 0) { reading = 0; }//-50?
		//switch statement # performs its case and goes back to arbitrator
		switch(movingStage) {
		//adjust direction that is facing - next behaviour run is Rotate
		case 0: 
			if (MainClass.getCurrentPos() >= MainClass.getNextPos()) {
				MainClass.facing = 3;
			} else {
				MainClass.facing = 1;
			}
			movingStage +=1;
			break;
		//continue when is no longer moving
		case 1:
			if (!MainClass.pilot.isMoving()) {
				movingStage +=1; 
			} 
			break;
		//start off following line
		case 2: 
			Motor.A.setSpeed(MainClass.MAX_SPEED);
			Motor.B.setSpeed(MainClass.MAX_SPEED);
			greyDist = Motor.A.getTachoCount();
			Motor.A.forward();
			Motor.B.forward();
			movingStage +=1;
			break;
		//is keep doing till is nicely on the line, using greyDist
		case 3:
			adjustSpeed(reading);

			if (Math.abs(70 - reading) > 4) {
				greyDist = Motor.A.getTachoCount();
			}
			if (Motor.A.getTachoCount() - greyDist > 10) {
				movingStage +=1;
			}
			break;
		//stop (using black method) when have black spike
		case 4:
			adjustSpeed(reading);
			
			if (MainClass.facing == 3) {
				if (reading > 170 || reading < 30) { black(-1); }
			} else {
				if (reading > 170 || reading < 30) { black(1); }	
			}
			break;
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		//do action if not in correct pos or is facing towards negative, in which case will
		//  turn around
		return (MainClass.getCurrentPos() != MainClass.getNextPos() || MainClass.facing == 3);
	}
	
	//method when done moving to adjascent pos
	private void black(int increment) {
		//reset motors and stage, increment currentPos
		Motor.A.stop();
		Motor.B.stop();
		MainClass.setCurrentPos(MainClass.getCurrentPos() + increment);
		movingStage = 0;
	}
	
	//algorithm for adjusting wheel speed
	private void adjustSpeed(int reading) {
		if (MainClass.facing == 1) {
			Motor.A.setSpeed((reading < 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
			Motor.B.setSpeed((reading > 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
		} else {
			Motor.B.setSpeed((reading < 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
			Motor.A.setSpeed((reading > 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
		}
	}
}
