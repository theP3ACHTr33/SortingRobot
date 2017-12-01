import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class NextPos implements Behavior {	
	public void action() {
		LCD.drawString("mov", 0, 6);
		LCD.refresh();
		int reading = MainClass.ls.readValue();
		if (reading > 100) { reading = 100; }
		if (reading < 0) { reading = 0; }
		LCD.drawInt(MainClass.movingStage, 0, 0);
		switch(MainClass.movingStage) {
		case 0: 
			if ((MainClass.currentPos > MainClass.nextPos && MainClass.facingPositive) ||
					(MainClass.currentPos < MainClass.nextPos && !MainClass.facingPositive) || 
					(MainClass.currentPos+1 == MainClass.nextPos && !MainClass.facingPositive)) {
				if (MainClass.facingPositive) {
					MainClass.pilot.rotate(180);
				} else {
					MainClass.pilot.rotate(-180);
				}
				MainClass.movingStage +=1;
				MainClass.facingPositive = !MainClass.facingPositive;
			} else {
				MainClass.movingStage +=2;
			}
			break;
		case 1:
			if (!MainClass.pilot.isMoving()) {
				MainClass.movingStage +=1; 
			} 
			break;
		case 2: 
			Motor.A.setSpeed(MainClass.MAX_SPEED);
			Motor.B.setSpeed(MainClass.MAX_SPEED);
			MainClass.greyDist = Motor.A.getTachoCount();
			Motor.A.forward();
			Motor.B.forward();
			MainClass.movingStage +=1;
			break;
		case 3:
			adjustSpeed(reading);

			if (Math.abs(70 - reading) > 4) {
				MainClass.greyDist = Motor.A.getTachoCount();
			}
			if (Motor.A.getTachoCount() - MainClass.greyDist > 10) {
				MainClass.movingStage +=1;
			}
			break;
		case 4:
			adjustSpeed(reading);
			
			LCD.clear();
			
			if (!MainClass.facingPositive) {
				if (reading > 170 || reading < 30) { black(-1); }
			} else {
				if (reading > 170 || reading < 30) { black(1); }	
			}
			LCD.drawInt(MainClass.currentPos, 0, 4);
			break;
		}
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return (MainClass.currentPos != MainClass.nextPos || !MainClass.facingPositive);
	}
	
	private void black(int increment) {
		Motor.A.stop();
		Motor.B.stop();
		MainClass.currentPos +=increment;
		MainClass.movingStage = 0;
	}
	
	private void adjustSpeed(int reading) {
		if (MainClass.facingPositive) {
			Motor.A.setSpeed((reading < 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
			Motor.B.setSpeed((reading > 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
		} else {
			Motor.B.setSpeed((reading < 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
			Motor.A.setSpeed((reading > 70)?(MainClass.MAX_SPEED):(MainClass.MAX_SPEED-Math.abs(70-reading)));
		}
	}
}
