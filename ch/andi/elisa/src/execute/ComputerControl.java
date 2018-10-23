package execute;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sun.jna.platform.win32.User32;

import feedback.AlertController;

public class ComputerControl {

	public static void lockComputer() {
		User32.INSTANCE.LockWorkStation();
	}

	public static void takeScreenshot() {
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		DateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
		DateFormat formatTime = new SimpleDateFormat("HH.mm.ss");
		Date time = Calendar.getInstance().getTime();
		try {
			BufferedImage screenshot = new Robot().createScreenCapture(screen);
			ImageIO.write(screenshot, "png", new File(System.getProperty("user.home") + "/Desktop/Bildschirmfoto am "
					+ formatDate.format(time) + " um " + formatTime.format(time) + " Uhr.png"));
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Schreiben");
			e.printStackTrace();
		}
	}
}
