import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class MainClass {
	public static final int NUM_OF_SORTS = 1;
	public static final int NUM_OF_BLOCKS = 3;
	public static final int MAX_SPEED = 100;
	public static final double WHEEL_DIAMETER = 55;
	public static final double AXEL_LENGTH = 138;
	public static final int GRAB_DIST = 100;
	
	public static LightSensor ls = new LightSensor(SensorPort.S1);
	public static DifferentialPilot pilot = new DifferentialPilot(MainClass.WHEEL_DIAMETER, MainClass.AXEL_LENGTH, Motor.B, Motor.A);
	
	public static boolean isFinished = false;
	public static int sortIndex = 0;
	
	private static int currentPos = -1;
	private static int nextPos = -1;
	
	public static boolean isClawOpen = true;
	public static boolean openClaw = true;
	
	public static MovStack movementStack = new MovStack();
	
	public static int[] Values = {1,2,3};
	
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
		
		//Display current and next pos (is updated by set and get methods)
		LCD.drawString("Cpos:-1", 0, 0);
		LCD.drawString("Npos:-1", 0, 1);
		LCD.refresh();
		
		Behavior[] ba = {
				new BubbleSort(),
				new MoveBlocks(),
				new Reset(),
				new Claw(),
				new NextPos()
		};
		Arbitrator arb = new Arbitrator(ba);
		
		arb.start();
	}
	
	//For Calibration
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
		return new int[] {low,high};
	}
	
	/////TEMP - instead of sensing values
	public static int getValue() {
		return Values[currentPos];
	}
	/////TEMP - instead of sensing values - while sorting
	public static void switchValues(int index1, int index2) {
		int temp = Values[index1];
		Values[index1] = Values[index2];
		Values[index2] = temp;
	}
	
	public static void setCurrentPos(int newPos) {
		if (newPos != currentPos) {
			currentPos = newPos;
			LCD.drawInt(currentPos, 5, 0);
			LCD.refresh();
		}
	}
	public static int getCurrentPos() {
		return currentPos;
	}

	public static void setNextPos(int newPos) {
		if (newPos != nextPos) {
			nextPos = newPos;
			LCD.drawInt(nextPos, 5, 1);
			LCD.refresh();
		}
	}
	public static int getNextPos() {
		return nextPos;
	}
}
