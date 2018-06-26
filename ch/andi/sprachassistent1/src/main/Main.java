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
	Thread speechThread;
	SpeechRecognizerThread srt;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/*Logger rootLogger = LogManager.getLogManager().getLogger("");
		rootLogger.setLevel(Level.FINE);
		for (Handler h : rootLogger.getHandlers()) {
		    h.setLevel(Level.FINE);
		}*/
		
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
		//fenster.dispatchEvent(new WindowEvent(fenster, WindowEvent.WINDOW_CLOSING));
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
