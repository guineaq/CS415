package dbot.commands;

import java.util.Random;

public class Choose extends Commands {
	
	private static String[] args;
	
	public synchronized static void setArgs(String[] args) {
		Choose.args = args;
	}
	
	public synchronized static void choose() {
		int index = new Random().nextInt(args.length);
		
		String str = "I choose: " + args[index];
		sendMessage(str);
	}
}
