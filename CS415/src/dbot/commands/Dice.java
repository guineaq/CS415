package dbot.commands;

import dbot.commands.exceptions.InvalidParamException;
import dbot.util.CustomList;

/**
 * Dice Roller Class
 * @version <b>1.0</b><p>
 * Handles Dice rolls with drop/keep and modifiers and
 * generates Pseudo-random Error Messages<p>
 * 
 * <b>Thread Safe</b>
 */
public class Dice extends Commands {

	private static String diceString;
	
	public synchronized static void setDiceString(String diceString) {
		Dice.diceString = diceString;
	}
	
	public synchronized static String rollDice() {
		String result = "Your rolls were: ";
		
		// Regular Expressions
		String regex_d1 = "[\\d]+[d][\\d]+";
		String regex_drop = "[\\d]+[d][\\d]+[d][\\d]+";
		String regex_keep = "[\\d]+[d][\\d]+[k][\\d]+";
		String regex_mod = "[\\d]+[d][\\d]+[+-][\\d]+";
		String regex_drop_mod = "[\\d]+[d][\\d]+[d][\\d]+[+-][\\d]+";
		String regex_keep_mod = "[\\d]+[d][\\d]+[k][\\d]+[+-][\\d]+";
		// ----------------------------------------------------------------------

		// Large Branch
		if(diceString.matches(regex_d1) || diceString.matches(regex_mod)) {
			// Numeric d numeric roll
			try {
				result += roll_branch_d();
			} catch (InvalidParamException e) {
				sendMessage(get_rand_message_invalid_param());
				return "ErrorCode D001";
			}
		} else if(diceString.matches(regex_drop)) {
			//Drop lowest
			try {
				result += roll_branch_dk(false);
			} catch (InvalidParamException e) {
				sendMessage(get_rand_message_invalid_param());
				return "ErrorCode D011";
			}
		} else if(diceString.matches(regex_keep)) {
			//Keep highest
			try {
				result += roll_branch_dk(true);
			} catch (InvalidParamException e) {
				sendMessage(get_rand_message_invalid_param());
				return "ErrorCode D021";
			}
		} else if(diceString.matches(regex_drop_mod)) {
			//Drop lowest, with modifiers
			try {
				result += roll_branch_dk(false);
			} catch (InvalidParamException e) {
				sendMessage(get_rand_message_invalid_param());
				return "ErrorCode D031";
			}
		} else if(diceString.matches(regex_keep_mod)) {
			//Keep highest, with modifiers
			try {
				result += roll_branch_dk(true);
			} catch (InvalidParamException e) {
				sendMessage(get_rand_message_invalid_param());
				return "ErrorCode D041";
			}
		} else {
			sendMessage(get_rand_message());
			return "ErrorCode D002";
		}
		// ----------------------------------------------------------------------
			
		sendMessage(result);
		return result;
	}

	private synchronized static String roll_branch_d() throws InvalidParamException {
		InvalidParamException e = new InvalidParamException();
		
		// Primitives
		String[] raw = diceString.split("[d|+|-]");
		String substring = "";
		
		boolean add = false;
		boolean subtract = false;
		
		int no = Integer.parseInt(raw[0]);
		int face = Integer.parseInt(raw[1]);
		int mod = 0;
		int total = 0;
		// ----------------------------------------------------------------------
		
		// Initialization of Primitive Variables
		if(diceString.contains("+")) {
			add = true;
		} else if (diceString.contains("-")) {
			subtract = true;
		}
		
		if(add) {
			mod += Integer.parseInt(raw[2]);
		} else if(subtract) {
			mod -= Integer.parseInt(raw[2]);
		}
		// ----------------------------------------------------------------------
		
		// Error Detection
		if((no <= 0) || (face <= 0)) {
			throw e;
		}
		// ----------------------------------------------------------------------
		
		// Rolls and Substring generation
		for(int i = 0; i < no; i++) {
			int rolled = (int)(Math.random() * face + 1);
			total += rolled;
			
			if(i+1 < no) {
				substring += rolled + ", ";
			} else {
				substring += rolled + ". ";
			}
		}
		
		total = total + mod;
		substring += "Total is: " + total;
		if(add || subtract) {
			substring += " with modifier: " + mod;
		}
		// ----------------------------------------------------------------------
		
		return substring;
	}
	
	private synchronized static String roll_branch_dk(boolean keep) throws InvalidParamException {
		InvalidParamException e = new InvalidParamException();
		
		// Primitives
		String[] raw = diceString.split("[d|k|+|-]");		
		String substring = "";
		String dropped = " Dices dropped: ";
		
		boolean add = false;
		boolean subtract = false;
		
		int no = Integer.parseInt(raw[0]);
		int face = Integer.parseInt(raw[1]);
		int drop;
		int mod = 0;
		int total = 0;
		// ----------------------------------------------------------------------
		
		// Initialization of Primitive Variables
		if(diceString.contains("+")) {
			add = true;
		} else if (diceString.contains("-")) {
			subtract = true;
		}
		
		if(keep) {
			drop = no - Integer.parseInt(raw[2]);
		} else {
			drop = Integer.parseInt(raw[2]);
		}
		
		if(add) {
			mod += Integer.parseInt(raw[3]);
		} else if(subtract) {
			mod -= Integer.parseInt(raw[3]);
		}
		// ----------------------------------------------------------------------
		
		// Error Detection
		if((no <= 0) || (face <= 0) || (drop <= 0)) {
			throw e;
		} else if(!keep && (no <= drop)) {
			throw e;
		}
		// ----------------------------------------------------------------------
		
		/* As initialization of class objects eat a lot of resource, we do it after we are 100% sure
		 * that we won't be failing anything
		 */
		CustomList rolled = new CustomList();
		
		// Generate rolls
		for(int i = 0; i < no; i++) {
			rolled.add((int)(Math.random() * face + 1));
		}
		// ----------------------------------------------------------------------
		
		// Process drop and keep
		for(int i = 0; i < drop; i++) {
			if(i+1 < drop) {
				dropped += rolled.removeMin() + ", ";
			} else {
				dropped += rolled.removeMin() + "";
			}
		}
		// ----------------------------------------------------------------------
		
		// Generate substring
		for(int i = 0; i < rolled.size(); i++) {
			int x = rolled.get(i);
			total += x;
			
			if(i+1 < rolled.size()) {
				substring += x + ", ";
			} else {
				substring += x + ". ";
			}
		}
		
		total = total + mod;
		substring += "Total is: " + total;
		if(add || subtract) {
			substring += " with modifier: " + mod;
		}
		substring += dropped;
		// ----------------------------------------------------------------------
		
		return substring;
	}
	
	// ----------------------------------------------------------------------
	// ----------------------------------------------------------------------
	
	private synchronized static String get_rand_message() {
		return "Insert witty random message here";
	}
	
	private synchronized static String get_rand_message_invalid_param() {
		return "Insert rant about invalid numerics";
	}
}
