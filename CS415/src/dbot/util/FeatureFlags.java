package dbot.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class FeatureFlags {

	public static boolean ask = true;
	public static boolean choose = true;
	public static boolean dice = true;
	public static boolean rollStat = true;
	public static boolean timeout = true;
	public static boolean kick = true;
	public static boolean ban = true;

	private static NodeList featureFlags;

	public static void initFlags() {
		boolean flag_no_settings = false;
		
		try {
			File inputFile = new File("settings.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			featureFlags = doc.getElementsByTagName("feature");
		} catch (Exception e) {
			e.printStackTrace();
			flag_no_settings = true;
		}
		
		if (!flag_no_settings) {
			for(int i = 0; i < featureFlags.getLength(); i++) {
				String bool = featureFlags.item(i).getTextContent();
				switch(i) {
				case 0: 
					if (bool.equals("false"))
						ask = false;
					break;
				case 1: 
					if (bool.equals("false"))
						choose = false;
					break;
				case 2: 
					if (bool.equals("false"))
						dice = false;
					break;
				case 3: 
					if (bool.equals("false"))
						rollStat = false;
					break;
				case 4: 
					if (bool.equals("false"))
						timeout = false;
					break;
				case 5: 
					if (bool.equals("false"))
						kick = false;
					break;
				case 6: 
					if (bool.equals("false"))
						ban = false;
					break;
				}
			}
		} else {
			BotUtil.Debug("No setting file acquired, enabling all features by default.");
		}
	}

}
