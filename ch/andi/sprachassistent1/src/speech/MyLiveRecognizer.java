package speech;

import java.io.IOException;

import bgFunc.Startup;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.Microphone;

public class MyLiveRecognizer extends LiveSpeechRecognizer {

	public MyLiveRecognizer() throws IOException {
		super(myConfig());

	}

	public MyLiveRecognizer(Microphone mic) throws IOException {
		super(myConfig(), mic);

	}

	static Configuration myConfig() {
		Configuration config = new Configuration();
		config.setAcousticModelPath("file:"+Startup.paramDir);
		config.setDictionaryPath("file:"+Startup.sphinxDir+"\\voxforge_small.dic");
		config.setGrammarPath("file:"+Startup.sphinxDir);
		config.setGrammarName("my_model");
		config.setUseGrammar(true);
		return config;
	}
}
