package dbot.logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Logger {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
	private static Timestamp timestamp;
	private static String logTime;

	public synchronized static void initLogger() {
		timestamp = new Timestamp(System.currentTimeMillis());
		logTime = sdf.format(timestamp).replace(".", "");
	}

	public synchronized static void CreateLog(String input) {
		try (FileWriter f = new FileWriter(("logs/log" + logTime + ".txt"), true);
				BufferedWriter b = new BufferedWriter(f);
				PrintWriter p = new PrintWriter(b);) {
			p.println(input);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public synchronized static String getTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}
}
