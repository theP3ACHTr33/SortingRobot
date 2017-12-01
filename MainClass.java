import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MainClass {
	public static final int NUM_OF_SORTS = 2;
	public static final int NUM_OF_BLOCKS = 4;
	public static final int MAX_SPEED = 100;
	public static final double WHEEL_DIAMETER = 55;
	public static final double AXEL_LENGTH = 138;
	
	public static boolean isFinished = false;
	
	public static LightSensor ls = new LightSensor(SensorPort.S1);
	public static int movingStage = 0;
	public static int greyDist = 0;
	public static int currentPos = -1;
	public static int nextPos = 1;
	public static boolean facingPositive = true;
	
	public static final int GRAB_DIST = 100;
	public static DifferentialPilot pilot = new DifferentialPilot(MainClass.WHEEL_DIAMETER, MainClass.AXEL_LENGTH, Motor.B, Motor.A);
	public static boolean isClawOpen = true;
	public static boolean openClaw = true;
	public static int clawStage = 0;
	
	public static MovStack movementStack = new MovStack();
	public static int movBlockStage = 0;
	
//	public static int sortIndex = 0;
	
	public static int[] Values = {4,3,2,1};
	
	public static void main(String[] args) {
		//Calabrate Light Sensor
		ls.setLow(getHighLow("Dark")[0]);
		ls.setHigh(getHighLow("Bright")[1]);
		try{
			Button.ENTER.waitForPressAndRelease();
		} catch(Exception e){}
		
		pilot.setRotateSpeed(50);
		pilot.setTravelSpeed(50);
		
		//##change claw to start pos
		
		Behavior[] ba = {
				//new TestBackForward(),
				new BubbleSort(),
				new MoveBlocks(),
				//new Reset(),
				new Claw(),
				new NextPos()
		};
		Arbitrator arb = new Arbitrator(ba);
		
		arb.start();
	}
	
	private static int[] getHighLow(String message) {
		int reading, low, high;
		
		LCD.drawString(message, 0,0);
		try{
			Button.ENTER.waitForPressAndRelease();
		} catch(Exception e){}
		reading = ls.getNormalizedLightValue();
		low = reading;
		high = reading;
		while (!Button.ESCAPE.isDown()) {
			reading = ls.getNormalizedLightValue();
			if (high < reading) {
				high = reading;
			} else if (low > reading) {
				low = reading;
			}
			LCD.drawInt(reading, 0, 1);
			LCD.drawInt(low, 0, 2);
			LCD.drawInt(high, 0, 3);
			LCD.refresh();
		}
		LCD.clear();
		LCD.refresh();
		return new int[] {low,high};
	}
	
	public static int getValue() {
		return Values[currentPos];
	}
	
	public static void switchValues(int index1, int index2) {
		int temp = Values[index1];
		Values[index1] = Values[index2];
		Values[index2] = temp;
	}
}
