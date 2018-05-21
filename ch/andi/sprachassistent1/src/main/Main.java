package main;

import bgFunc.AutoProgramsPath;
import gui.MainApp;
import gui.MyTrayIcon;
import javafx.application.Application;
import javafx.stage.Stage;
import jna.key.KeyHook;
import speech.SpeechRecognizerThread;

public class Main extends Application{

	public static boolean quit = false;
	MainApp mainApp;
	private MyTrayIcon trayIcon;
	private KeyHook keyHook;
	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.primaryStage = primaryStage;
		
		setupAutoProgramsPath();
		setupWindow();
		setupSystemTray();
		setupKeyHook();
		setupSpeechRecognizerThread();
		setupBackgroundThread();
		
	}
	
	private void setupAutoProgramsPath() {
		AutoProgramsPath.setup();
	}

	private void setupWindow() {
		mainApp = new MainApp();
		mainApp.showWindow(primaryStage);
		//MainApp.launch(new String[0]);
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
		SpeechRecognizerThread srt = new SpeechRecognizerThread();
		Thread speechThread = new Thread(srt);
		speechThread.start();
	}
	
	public void setupBackgroundThread() {
		BackgroundThread bt = new BackgroundThread();
		Thread backgroundThread = new Thread(bt);
		backgroundThread.start();
	}
	
	public void quitProgram() {
		//fenster.dispatchEvent(new WindowEvent(fenster, WindowEvent.WINDOW_CLOSING));
		try {
			mainApp.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		trayIcon.removeTrayIcon();
		keyHook.unhook();
		
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
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
