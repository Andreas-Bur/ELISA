package gui;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Main;

public class MyTrayIcon {
	static final Image img = Toolkit.getDefaultToolkit().getImage("res/brains.png");
	private final MenuItem exitItem = new MenuItem("Exit");

	private final TrayIcon trayIcon = new TrayIcon(img);
	private final SystemTray tray = SystemTray.getSystemTray();

	public MyTrayIcon() {
		if (!SystemTray.isSupported()) {
			System.err.println("SystemTray not supported");
			return;
		}

		addPopup();
		addTrayIcon();
		addListener();

	}

	private void addPopup() {
		PopupMenu popup = new PopupMenu();
		popup.add(exitItem);
		trayIcon.setPopupMenu(popup);
	}

	private void addTrayIcon() {
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private void addListener() {
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("set quit true");
				Main.quit = true;
			}
		});
	}

	public void removeTrayIcon() {
		tray.remove(trayIcon);
	}

}
