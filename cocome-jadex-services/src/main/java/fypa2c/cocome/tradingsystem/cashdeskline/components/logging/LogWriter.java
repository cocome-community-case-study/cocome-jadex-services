package fypa2c.cocome.tradingsystem.cashdeskline.components.logging;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

/**
 * Provides static methods to write logs 
 * of fired events into the corresponding log files in the logs folder.
 *
 * @author Florian Abt
 */
public abstract class LogWriter{
	
	public volatile static File path;

	/**
	 * Writes a LogEntry to the corresponding log file in the logs folder.
	 * If the folder or the file doesn't exists it will be created. 
	 * 
	 * @param entry , to write to the file
	 * @return true if write process completed successfully 
	 */
	public static boolean writeLog(LogEntry entry,String logComponent) {
		if(!initializePath()) {
			return false;
		}
		//create log file line
		List<String> lines = Arrays.asList(entry.toString());
		Path file = Paths.get(path.getPath()+"/"+logComponent+".log");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("An IO-Exception occurs, failing to write the LogEntry to the log file.");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * to start a new log folder. A new folder with new log files is created for the next simulation.
	 */
	public static void startNewLogFolder() {
		path = null;
	}
	
	private static boolean initializePath() {
		if(path == null) {
			path = new File("logs/Simulation"+System.currentTimeMillis());
			boolean success = path.mkdirs();
			if(!success) {
				System.out.println("Couldn't create directory "+path.getPath()+"");
				return false;
			}
		}
		return true;
	}
	
	
}
