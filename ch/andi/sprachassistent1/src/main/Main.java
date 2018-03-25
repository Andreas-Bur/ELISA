package main;

import gui.MyTrayIcon;
import gui.MyWindow;

public class Main {

	public Main() {
		setupWindow();
		setupSystemTray();
	}
	
	public void setupWindow() {
		MyWindow fenster = new MyWindow();
		
	}
	
	public void setupSystemTray() {
		MyTrayIcon trayIcon = new MyTrayIcon();
	}

	public static void main(String[] args) {
		Main main = new Main();
	}

}
