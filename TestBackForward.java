import lejos.robotics.subsumption.Behavior;

public class TestBackForward implements Behavior {
	public void action() {
		MainClass.nextPos = (MainClass.nextPos == 1)?0:1;
		MainClass.openClaw = !MainClass.openClaw;
	}
	
	public void suppress() {
		
	}
	
	public boolean takeControl() {
		return true;
	}
}
