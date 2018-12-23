/**
 *
 */
package dbot.primary;

/**
 * @author CW Park
 *
 *         Created on 2016. 7. 10.
 */
public class CommandParser {

	public CommandContainer parse(String s) {
		if(!s.startsWith("!")) {
			return null;
		}
		String base = s.replaceFirst("!", "");
		String base2;
		
		int index = 0;
		while(base.charAt(index) != ' ') {
			index++;
			if(index >= base.length())
				break;
		}
		
		if(index < base.length()) {
			base2 = base.substring(index + 1);
		} else {
			base2 = "";
		}
		
		String[] split = base.split("[,\\s]+");
		String[] split2 = base2.split("[,][\\s]*");

		String cmd;
		if (split.length > 0) {
			cmd = split[0].toLowerCase();
		} else {
			cmd = null;
		}
		
		// Every arguments are lower case as well!
		String[] args = new String[split2.length];
		if (split2.length > 0) {
			for (int i = 0; i < split2.length; i++) {
				args[i] = split2[i].toLowerCase();
			}
		} else {
			args = null;
		}
		
		return new CommandContainer(cmd, args);
	}

	public class CommandContainer {

		public final String cmd;
		public final String[] args;

		public CommandContainer(String cmd, String[] args) {
			this.cmd = cmd;
			this.args = args;
		}
		
		public boolean argsIsEmpty() {
			try {
				if(args.length > 0) {
					return false;
				} else {
					return true;
				}
			} catch(NullPointerException e) {
				return true;
			}
		}
		
		public int length() {
			return args.length;
		}
	}
}
