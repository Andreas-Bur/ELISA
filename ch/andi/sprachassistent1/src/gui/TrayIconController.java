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

import javafx.application.Platform;
import main.Main;

public class TrayIconController {
	public static final Image DEFAULT_ICON = Toolkit.getDefaultToolkit().getImage("res/gear_white.png");
	public static final Image LOADING_ICON = Toolkit.getDefaultToolkit().getImage("res/gear_blue.png");
	public static final Image SUCCESS_ICON = Toolkit.getDefaultToolkit().getImage("res/gear_green.png");
	public static final Image ERROR_ICON = Toolkit.getDefaultToolkit().getImage("res/gear_red.png");
	
	private final MenuItem exitItem = new MenuItem("Beenden");
	private final MenuItem openItem = new MenuItem("Fenster öffnen");

	private final TrayIcon trayIcon = new TrayIcon(DEFAULT_ICON, "Elisa");
	private final SystemTray tray = SystemTray.getSystemTray();

	Main main;

	public TrayIconController(Main main) {
		if (!SystemTray.isSupported()) {
			AlertController.showErrorDialog("Systemfehler", "Der Infobereich wird nicht unterstützt. Es konnte kein Symbol erstellt werden.");
			return;
		}
		
		this.main = main;

		addPopup();
		addTrayIcon();
		addListeners();
	}

	private void addPopup() {
		PopupMenu popup = new PopupMenu();
		popup.add(exitItem);
		popup.add(openItem);
		trayIcon.setPopupMenu(popup);
	}

	private void addTrayIcon() {
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private void addListeners() {
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.quit = true;
			}
		});

		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						main.setupWindow();
					}
				});
			}
		});
	}
	
	public void setIcon(Image icon) {
		trayIcon.setImage(icon);
	}
	
	public void setDefaultIcon() {
		trayIcon.setImage(DEFAULT_ICON);
	}

	public void removeTrayIcon() {
		tray.remove(trayIcon);
	}
}
