package Economy;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class SelfUpdate {
	
	public static void runUpdate(String currentPath) {
		List<String> arguments = new ArrayList<String>();

		String separator = System.getProperty("file.separator");
		String path = System.getProperty("java.home") + separator + "bin" + separator + "java";
		arguments.add(path);
		arguments.add("-jar");
		arguments.add(currentPath);

		ProcessBuilder processBuilder = new ProcessBuilder(new String[0]);
		processBuilder.command(arguments);
		try {
			processBuilder.start();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Failed to start self-update process");
		}
		System.exit(0);
	}

	public static void update() {
		String path = null;
		try {
			path = new File(SelfUpdate.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getCanonicalPath();
			path = URLDecoder.decode(path, "UTF-8");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't get path to current launcher jar/exe");
			return;
		}
		try {
			SelfUpdate.runUpdate(path);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Send this to an admin:\n" + e.getMessage() + "");
			System.exit(0);
		}
	}
}