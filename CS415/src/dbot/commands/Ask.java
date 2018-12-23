package dbot.commands;

import java.util.Random;

public class Ask extends Commands {

	public static synchronized void answer() {
		int choice = new Random().nextInt(100);
		int intensity = new Random().nextInt(5);
		
		String str = "";
		
		if(choice < 50) {
			switch(intensity) {
			case 0:
				str = "Probably no.";
				break;
			case 1:
				str = "I think it's a no.";
				break;
			case 2:
				str = "No.";
				break;
			case 3:
				str = "Heck no.";
				break;
			case 4:
				str = "NOPE.";
				break;
			}
		} else {
			switch(intensity) {
			case 0:
				str = "Probably, yeah.";
				break;
			case 1:
				str = "Leaning towards yes.";
				break;
			case 2:
				str = "Yeah.";
				break;
			case 3:
				str = "Yes.";
				break;
			case 4:
				str = "YES.";
				break;
			}
		}
		sendMessage(str);		
	}
}
