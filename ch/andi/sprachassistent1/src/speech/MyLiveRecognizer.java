package speech;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.linguist.dflat.DynamicFlatLinguist;

public class MyLiveRecognizer extends LiveSpeechRecognizer{

	public MyLiveRecognizer() throws IOException {
		super(myConfig());
	}
	
	static Configuration myConfig() {
		Configuration config = new Configuration();
		config.setAcousticModelPath("file:sphinx_data_small\\model_parameters");
		config.setDictionaryPath("file:sphinx_data_small\\etc\\voxforge_small.dic");
		config.setGrammarPath("sphinx_data_small\\etc");
		config.setGrammarName("my_model");
		config.setUseGrammar(true);
		return config;
	}
	
}
