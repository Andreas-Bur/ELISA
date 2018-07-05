package execute;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenWebsite {

	public static void open(String path) {
		
		Desktop dt = Desktop.getDesktop();
		try {
			dt.browse(new URI(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}
}
