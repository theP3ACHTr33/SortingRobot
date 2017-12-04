import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class NextPos implements Behavior {	
	private int movingStage = 0;
	private int greyDist = 0;
	private boolean facingPositive = true;
	
	public void action() {
		//Maybe addapt to use pilot
		
		int reading = MainClass.ls.readValue();
		if (reading > 100) { reading = 100; }//150?
		if (reading < 0) { reading = 0; }//-50?
		switch(movingStage) {
		case 0: 
			if ((MainClass.getCurrentPos() > MainClass.getNextPos() && facingPositive) ||
					(MainClass.getCurrentPos() < MainClass.getNextPos() && !facingPositive) || 
					(MainClass.getCurrentPos()+1 == MainClass.getNextPos() && !facingPositive)) {
				if (facingPositive) {
					MainClass.pilot.rotate(180);
				} else {
					MainClass.pilot.rotate(-180);
				}
				movingStage +=1;
				facingPositive = !facingPositive;
			} else {
				movingStage +=2;
			}
			break;
		case 1:
			if (!MainClass.pilot.isMoving()) {
				movingStage +=1; 
			} 
			break;
		case 2: 
			Motor.A.setSpeed(MainClass.MAX_SPEED);
			Motor.B.setSpeed(MainClass.MAX_SPEED);
			greyDist = Motor.A.getTachoCount();
			Motor.A.forward();
			Motor.B.forward();
			movingStage +=1;
			break;
		case 3:
			adjustSpeed(reading);

			if (Math.abs(70 - reading) > 4) {
				greyDist = Motor.A.getTachoCount();
			}
			if (Motor.A.getTachoCount() - greyDist > 10) {
				movingStage +=1;
			}
			break;
		case 4:
			adjustSpeed(reading);
			
			if (!facingPositive) {
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
		return (MainClass.getCurrentPos() != MainClass.getNextPos() || !facingPositive);
	}
	
	private void black(int increment) {
		Motor.A.stop();
		Motor.B.stop();
		MainClass.setCurrentPos(MainClass.getCurrentPos() + increment);
		movingStage = 0;
	}
	
	private void adjustSpeed(int reading) {
		if (facingPositive) {
			Motor.A.setSpeed((reading < 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
			Motor.B.setSpeed((reading > 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
		} else {
			Motor.B.setSpeed((reading < 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
			Motor.A.setSpeed((reading > 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
		}
	}
}
