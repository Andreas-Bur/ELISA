package main;

import java.awt.event.WindowEvent;

import gui.MyTrayIcon;
import gui.MyWindow;

public class Main {

	public static boolean quit = false;
	private MyWindow fenster;
	private MyTrayIcon trayIcon;

	public Main() {
		setupWindow();
		setupSystemTray();
		BackgroundThread bt = new BackgroundThread();
		bt.run();
	}

	public void setupWindow() {
		fenster = new MyWindow();

	}

	public void setupSystemTray() {
		trayIcon = new MyTrayIcon();
	}

	public static void main(String[] args) {
		Main main = new Main();
	}

	public void quitProgram() {
		fenster.dispatchEvent(new WindowEvent(fenster, WindowEvent.WINDOW_CLOSING));
		trayIcon.removeTrayIcon();
		System.exit(0);
	}

	class BackgroundThread implements Runnable {

		@Override
		public void run() {
			System.out.println("run");

			while (true) {
				if(quit) {
					quitProgram();
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
