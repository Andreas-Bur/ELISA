package main;

import bgFunc.AutoProgramsPath;
import gui.MainApp;
import gui.MyTrayIcon;
import javafx.application.Application;
import javafx.stage.Stage;
import jna.key.KeyHook;
import speech.SpeechRecognizerThread;

public class Main extends Application {

	public static boolean quit = false;
	MainApp mainApp;
	private MyTrayIcon trayIcon;
	private KeyHook keyHook;
	Stage primaryStage;
	Thread speechThread;
	SpeechRecognizerThread srt;
	public static volatile long totalTime;

	@Override
	public void start(Stage primaryStage) throws Exception {

		/*
		 * Logger rootLogger = LogManager.getLogManager().getLogger("");
		 * rootLogger.setLevel(Level.FINE); for (Handler h :
		 * rootLogger.getHandlers()) { h.setLevel(Level.FINE); }
		 */

		this.primaryStage = primaryStage;
		long time = System.nanoTime();
		totalTime = System.nanoTime();
		setupAutoProgramsPath();
		System.out.println("setupAutoProgramsPath: " + (System.nanoTime() - time) / 1000000000.0);
		time = System.nanoTime();
		setupSpeechRecognizerThread();
		System.out.println("setupSpeechRecognizerThread: " + (System.nanoTime() - time) / 1000000000.0);
		time = System.nanoTime();
		setupWindow();
		System.out.println("setupWindow: " + (System.nanoTime() - time) / 1000000000.0);
		time = System.nanoTime();
		setupSystemTray();
		System.out.println("setupSystemTray: " + (System.nanoTime() - time) / 1000000000.0);
		time = System.nanoTime();
		setupKeyHook();
		System.out.println("setupKeyHook: " + (System.nanoTime() - time) / 1000000000.0);
		time = System.nanoTime();
		setupBackgroundThread();
		System.out.println("setupBackgroundThread: " + (System.nanoTime() - time) / 1000000000.0);
		time = System.nanoTime();
	}

	private void setupAutoProgramsPath() {
		AutoProgramsPath.setup();
	}

	private void setupWindow() {
		mainApp = new MainApp();
		mainApp.showWindow(primaryStage);
	}

	private void setupSystemTray() {
		trayIcon = new MyTrayIcon();
	}

	private void setupKeyHook() {
		keyHook = new KeyHook();
		Thread keyHookThread = new Thread(keyHook);
		keyHookThread.start();
	}

	public void setupSpeechRecognizerThread() {
		srt = new SpeechRecognizerThread();
		speechThread = new Thread(srt);
		speechThread.start();
	}

	public void setupBackgroundThread() {
		BackgroundThread bt = new BackgroundThread();
		Thread backgroundThread = new Thread(bt);
		backgroundThread.start();
	}

	private void quitProgram() {

		try {
			mainApp.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		trayIcon.removeTrayIcon();

		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public class BackgroundThread implements Runnable {

		@Override
		public void run() {
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
