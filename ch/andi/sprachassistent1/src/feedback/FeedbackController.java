package feedback;

import java.awt.Image;

import main.Main;

public class FeedbackController implements Runnable {

	final Image icon;
	final long millis;

	public FeedbackController(Image icon, long millis) {
		this.icon = icon;
		this.millis = millis;
	}

	public void run() {
		try {
			Main.trayIconController.setIcon(icon);
			if (millis > 0) {
				Thread.sleep(millis);
				Main.trayIconController.setDefaultIcon();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}