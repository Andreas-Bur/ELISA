package main;

import java.awt.event.WindowEvent;

import gui.MyTrayIcon;
import gui.MyWindow;
import jna.key.KeyHook;

public class Main {

	public static boolean quit = false;
	private MyWindow fenster;
	private MyTrayIcon trayIcon;
	private KeyHook keyHook;

	public Main() {
		setupWindow();
		setupSystemTray();
		setupKeyHook();
		setupBackgroundThread();
	}

	private void setupWindow() {
		fenster = new MyWindow();

	}

	private void setupSystemTray() {
		trayIcon = new MyTrayIcon();
	}

	private void setupKeyHook() {
		keyHook = new KeyHook();
		Thread keyHookThread = new Thread(keyHook);
		keyHookThread.start();
	}
	
	public void setupBackgroundThread() {
		BackgroundThread bt = new BackgroundThread();
		Thread backgroundThread = new Thread(bt);
		backgroundThread.start();
	}

	public static void main(String[] args) {
		Main main = new Main();
	}

	public void quitProgram() {
		fenster.dispatchEvent(new WindowEvent(fenster, WindowEvent.WINDOW_CLOSING));
		trayIcon.removeTrayIcon();
		keyHook.unhook();
		System.exit(0);
	}

	public class BackgroundThread implements Runnable {

		@Override
		public void run() {
			System.out.println("run");

			while (true) {
				if (quit) {
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
