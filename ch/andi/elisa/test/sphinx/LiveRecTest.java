package sphinx;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class LiveRecTest {

	public static void main(String[] args) throws Exception {
		long t0 = System.currentTimeMillis();

		Configuration configuration_de = new Configuration();

		configuration_de.setAcousticModelPath("file:sphinx_data_small/model_parameters");
		configuration_de.setDictionaryPath("file:sphinx_data_small/etc/voxforge_small.dic");
		configuration_de.setGrammarPath("file:sphinx_data_small/etc/");
		configuration_de.setGrammarName("my_model");
		configuration_de.setUseGrammar(true);

		// configuration.setSampleRate(8000);

		// System.setProperty("java.util.logging.config.file",
		// "ignoreAllSphinx4LoggingOutput");
		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration_de);

		recognizer.startRecognition(true);

		while (true) {
			long start = System.currentTimeMillis();
			System.out.println("start: " + (start - t0) / 1000.0);

			SpeechResult result = recognizer.getResult();
			System.out.println("result: " + decode(result.getHypothesis()));

			System.out.println("---" + (System.currentTimeMillis() - start) / 1000.0);

			System.out.println("done");
		}

	}

	static String decode(String input) {
		input = input.replace("%ue%", "ü");
		input = input.replace("%oe%", "ö");
		input = input.replace("%ae%", "ä");
		return input;
	}
}