package main;

import bgFunc.AutoProgramsPath;
import bgFunc.Processes;
import bgFunc.Startup;
import gui.MainApp;
import gui.TrayIconController;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jna.KeyHook;
import speech.SpeechRecognizerThread;

public class Main extends Application {

	private static MainApp mainApp = null;
	public KeyHook keyHook;
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
		
		if(Processes.isElisaRunning()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Info Dialog");
			alert.setHeaderText("Fehler");
			alert.setContentText("ELISA läuft bereits. Rufen Sie das Fenster über das Taskleistensymbol auf oder schliessen Sie ELISA zuerst, bevor Sie das Programm neu starten.");
			alert.showAndWait();
			System.exit(0);
		}

		new Startup();
		System.out.println("Startup: " + (System.nanoTime() - time) / 1000000000.0);
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

	public static void quitProgram() {

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
}
