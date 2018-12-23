package dbot.util;

public class BotUtil {

	public static String getToken() {
		return "NTI2MTU3NDIyOTMxMjE0MzU3.DwBGUw.xhlR9uLgRGDQwCS8pFCHE-GLCn0";
	}

	/**
	 * Supplies standard Error Messages for invalid command input
	 * 
	 * @param errorCode
	 *            1 - Invalid Amount of Parameters<p>
	 *            2 - Incorrect Format<p>
	 * @return Message
	 */
	public static String ErrorMsg(int errorCode) {
		// Add support for randomized 
		switch (errorCode) {
		case 1:
			return "You gave incorrect amount of parameters >:c";
		case 2:
			return "Uh, this isn't the correct format.";
		default:
			return "";
		}
	}
	
	public static void Debug(String string) {
		System.out.println("::DEBUG:: " + string);
	}
}
