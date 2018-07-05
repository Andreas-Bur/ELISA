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

	public static void playHotwordActivated() {
		playSound(hotword_activation_path);
	}

	private static void playSound(String filename) {

		System.out.println("playSound");

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
}
