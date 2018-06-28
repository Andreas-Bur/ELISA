package bgFunc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;

public class Processes {

	public Processes() {
		// TODO Auto-generated constructor stub
	}

	public static String[] getPathsOfRunningProcesses() {

		ArrayList<String> list = new ArrayList<>();

		try {
			String line;
			Process p = Runtime.getRuntime().exec("wmic process get ExecutablePath");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			line = input.readLine(); //skip first line with title
			
			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					list.add(line.trim());
				}
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		String[] out = (String[]) list.toArray(new String[list.size()]);

		return out;
	}
	
	public static int[] getPIDsOfProcess(String name) {
		ArrayList<Integer> pids = new ArrayList<>();
		try {
			Process p = Runtime.getRuntime().exec("wmic process where name=\""+name+"\" get processid");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			input.readLine(); //skip first line with title
			input.readLine(); //skip second empty line

			String line = input.readLine();
			while(line != null) {
				if(!line.equals("")) {
					pids.add(Integer.parseInt(line.trim()));
				}
				line = input.readLine();
			}

			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		int[] out = new int[pids.size()];
		for(int i = 0; i < out.length; i++) {
			out[i] = pids.get(i);
		}
		
		return out;
	}
	
	public static String getTitleOfProcess(String name) {

		try {
			String line;
			//Process p = Runtime.getRuntime().exec("chcp 65001");

			Process p = Runtime.getRuntime().exec("tasklist /v /fo csv /fi \"IMAGENAME eq "+name+"\"");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			line = input.readLine(); //skip first line with title
			
			while ((line = input.readLine()) != null) {
				System.out.println(line);
				String[] fields = line.split(",");
				if (fields[0].substring(1, fields[0].length()-1).toLowerCase().equals(name.toLowerCase())) {
					String titleField = fields[fields.length-1].substring(1, fields[fields.length-1].length()-1);
					return titleField;
				}
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean isProcessRunning(String path) {
		String[] processes = getPathsOfRunningProcesses();
		for(String process : processes) {
			if(process.toLowerCase().equals(path.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		//System.out.println(Arrays.toString(getPIDsOfProcess(("firefox.exe"))));
		System.out.println(getTitleOfProcess("firefox.exe"));
	}

}
