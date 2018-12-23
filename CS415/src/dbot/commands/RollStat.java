package dbot.commands;

import java.util.Random;

import dbot.util.CustomList;

public class RollStat extends Commands {

	private static boolean heroic = false;
	private static boolean strict = false;
	private static boolean sad = false;
	private static boolean mad = false;

	public synchronized static void setRollString(String rollstring) {	
		switch (rollstring) {
		case "heroic":
			heroic = true;
			strict = false;
			sad = false;
			mad = false;
			break;
		case "strict":
			strict = true;
			heroic = false;
			sad = false;
			mad = false;
			break;
		case "sad":
			strict = false;
			heroic = false;
			sad = true;
			mad = false;
			break;
		case "mad":
			strict = false;
			heroic = false;
			mad = true;
			sad = false;
			break;
		default:
			heroic = false;
			strict = false;
			sad = false;
			mad = false;
			break;
		}
	}

	public synchronized static String rollStat() {
		String result = "Your stats are: ";

		int[] stats = new int[6];

		if (heroic) {
			for (int i = 0; i < stats.length; i++) {
				CustomList rolled = roll6(3);
				rolled.removeMin();
				stats[i] = rolled.getTotal() + 6;
			}
		} else if (strict) {
			for (int i = 0; i < stats.length; i++) {
				stats[i] = roll6(3).getTotal();
			}
		} else if (mad) {
			for (int i = 0; i < stats.length - 3; i++) {
				CustomList rolled = roll6(4);
				rolled.removeMin();
				stats[i] = rolled.getTotal() + 2;
			}

			for (int i = 3; i < stats.length; i++) {
				CustomList rolled = roll6(4);
				rolled.removeMin();
				stats[i] = rolled.getTotal() - 2;
			}
		} else if (sad) {
			CustomList rolled = roll6(3);
			rolled.removeMin();
			stats[0] = rolled.getTotal() + 6;

			for (int i = 1; i < stats.length; i++) {
				rolled = roll6(4);
				rolled.removeMin();
				stats[i] = rolled.getTotal() - 1;
			}
		} else {
			for (int i = 0; i < stats.length; i++) {
				CustomList rolled = roll6(4);
				rolled.removeMin();
				stats[i] = rolled.getTotal();
			}
		}

		for (int i = 0; i < stats.length; i++) {
			if (i + 1 != stats.length) {
				result += stats[i] + ", ";
			} else {
				result += stats[i] + " ";
			}
		}

		if (heroic) {
			result += "Using heroic (3d6d1+6) rules";
		} else if (strict) {
			result += "Using strict (3d6) rules";
		} else if (sad) {
			result += "Using single ability weighted rule";
		} else if (mad) {
			result += "Using multiple ability weighted rule";
		} else {
			result += "Using standard (4d6d1) rule";
		}

		int total_mod = 0;
		
		for(int i = 0; i < stats.length; i++) {			
			if(stats[i] < 10) {
				total_mod = total_mod + (((stats[i] - 10) / 2) + ((stats[i] - 10) % 2)); 
			} else {
				total_mod += (stats[i] - 10) / 2;
			}
		}
		
		result += " and total mod of " + total_mod;
		
		heroic = false;
		strict = false;
		sad = false;
		mad = false;

		sendMessage(result);
		return result;
	}

	/*
	 * private synchronized static String get_err_message() { return "Error"; }
	 */
	private synchronized static CustomList roll6(int no) {
		Random rand = new Random();
		CustomList rolled = new CustomList();

		for (int i = 0; i < no; i++) {
			rolled.add(rand.nextInt(6) + 1);
		}

		return rolled;
	}
}
