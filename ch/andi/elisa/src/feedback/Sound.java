package feedback;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private final static String hotword_activation_path = "res/hotword_activation2.wav";
	private final static String error_sound_path = "res/error2.wav";

	public static void playHotwordActivated() {
		playSound(hotword_activation_path);
	}
	
	public static void playErrorSound() {
		playSound(error_sound_path);
	}

	private static void playSound(String filename) {

		try {

			Clip clip = AudioSystem.getClip();

			clip.addLineListener(new LineListener() {
				@Override
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP) {
						clip.close();
					}
				}
			});

			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
			clip.start();

		} catch (LineUnavailableException e) {
			AlertController.showErrorDialog("Soundfehler", "Es konnte kein Ton abgespielt werden. Versuchen Sie, den Computer neu zu starten.");
			e.printStackTrace();
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
}
