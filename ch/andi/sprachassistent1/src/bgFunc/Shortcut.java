package bgFunc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import feedback.AlertController;

public class Shortcut {

	public static String getTargetPath(File shortcutFile) {
		try {
			InputStream input = new FileInputStream(shortcutFile);
			String output = parsePath(getByteContent(input));
			return output;
		} catch (FileNotFoundException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}
		return null;
	}

	private static String parsePath(byte[] bytes) {

		try {
			final int pathname_pos_offset = 16;
			final int shell_item_offset = 76;

			// +2 sodass Bytes mit Längen imbegriffen sind
			final int shell_item_list_length = twoBytesToInt(bytes, shell_item_offset) + 2;
			final int file_locator_info_offset = shell_item_offset + shell_item_list_length;
			final int pathname_offset = file_locator_info_offset + bytes[file_locator_info_offset + pathname_pos_offset];

			final int path_length = getPathLength(bytes, pathname_offset);
			String pathname = new String(bytes, pathname_offset, path_length);

			return pathname;
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}

	}

	private static int getPathLength(byte[] bytes, int pathname_offset) {
		int path_length = 0;

		while (true) {

			if (bytes[pathname_offset + path_length] == 0) {
				break;
			} else {
				path_length++;
			}
		}

		return path_length;
	}

	private static byte[] getByteContent(InputStream input) {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		try {
			int curByte = input.read();
			while (curByte != -1) {
				outStream.write(curByte);
				curByte = input.read();
			}
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
			return null;
		}

		return outStream.toByteArray();
	}

	private static int twoBytesToInt(byte[] bytes, int pos) {

		int unsignedInt1 = (bytes[pos] & 255);
		int unsignedInt2 = (bytes[pos + 1] & 255);
		int output = (unsignedInt2 << 8) | unsignedInt1;

		return output;
	}

}
