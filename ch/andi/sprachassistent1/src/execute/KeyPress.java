package execute;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class KeyPress {

	static Robot robot;

	public static void press(int key) {
		try {
			robot = new Robot();
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyPress(key);
			robot.keyRelease(key);
			System.out.println("pressed: "+key);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
