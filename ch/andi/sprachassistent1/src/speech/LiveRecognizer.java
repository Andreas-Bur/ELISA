package speech;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import main.Main;

public class LiveRecognizer {

	public LiveRecognizer() {
		Configuration config = new Configuration();
		config.setAcousticModelPath("file:sphinx_data\\model_parameters");
		config.setDictionaryPath("file:sphinx_data\\etc\\voxforge_edited.dic");
		config.setGrammarPath("sphinx_data\\etc");
		config.setGrammarName("my_model");
		config.setUseGrammar(true);
		
		LiveSpeechRecognizer recognizer;
		
		try {
			recognizer = new LiveSpeechRecognizer(config);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		recognizer.startRecognition(true);
		
		while(!Main.quit) {
			SpeechResult result = recognizer.getResult();
			System.out.println("result: " + decode(result.getHypothesis()));
		}
		
	}
	
	static String decode(String input) {
		input = input.replace("%ue%", "ü");
		input = input.replace("%oe%", "ö");
		input = input.replace("%ae%", "ä");
		return input;
	}
	
}
