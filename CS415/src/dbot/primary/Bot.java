package dbot.primary;

import javax.security.auth.login.LoginException;

import dbot.listeners.BotListener;
import dbot.util.BotUtil;
import dbot.util.FeatureFlags;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class Bot {

	// Library Dependency
	//
	// Using 3.3.1_307 JDA
	public static JDA jda;
	
	public static void main(String args[]) {
		FeatureFlags.initFlags();
		
		try {
			jda = new JDABuilder(AccountType.BOT)
					.setToken(BotUtil.getToken())
					.addEventListener(new BotListener())
					.buildBlocking();
			jda.setAutoReconnect(true);
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (RateLimitedException e) {
			e.printStackTrace();
		}
	}
	
}
