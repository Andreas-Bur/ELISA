package main;

import java.io.IOException;

import bgFunc.AutoProgramsPath;
import gui.MainApp;
import gui.TrayIconController;
import javafx.application.Application;
import javafx.stage.Stage;
import jna.key.KeyHook;
import speech.SpeechRecognizerThread;

public class Main extends Application {

	public static boolean quit = false;
	private MainApp mainApp = null;
	private KeyHook keyHook;
	private Stage primaryStage;
	private Thread speechThread;
	private SpeechRecognizerThread srt;
	public static volatile TrayIconController trayIconController;
	public static volatile long totalTime;

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.primaryStage = primaryStage;
		long time = System.nanoTime();
		totalTime = System.nanoTime();

		startup();
		System.out.println("firstSetup: " + (System.nanoTime() - time) / 1000000000.0);
		time = System.nanoTime();
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

	private void startup() {
		try {
			new Startup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setupAutoProgramsPath() {
		AutoProgramsPath.setup();
	}

	public void setupWindow() {
		if (mainApp == null) {
			mainApp = new MainApp();
		}
		mainApp.showWindow(primaryStage);
	}

	private void setupSystemTray() {
		trayIconController = new TrayIconController(this);
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

		trayIconController.removeTrayIcon();

		try {
			mainApp.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}

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
