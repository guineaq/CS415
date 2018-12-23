package dbot.listeners;

import dbot.commands.Ask;
import dbot.commands.Ban;
import dbot.commands.Choose;
import dbot.commands.Dice;
import dbot.commands.Kick;
import dbot.commands.RollStat;
import dbot.commands.TimeOut;
import dbot.primary.CommandParser;
import dbot.primary.CommandParser.CommandContainer;
import dbot.util.FeatureFlags;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

	private CommandParser parser;

	public BotListener() {
		parser = new CommandParser();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		// Data provided by every event in JDA
		@SuppressWarnings("unused")
		JDA jda = event.getJDA();
		@SuppressWarnings("unused")
		long responseNumber = event.getResponseNumber();

		// Message Information
		User author = event.getAuthor();
		Message message = event.getMessage();
		MessageChannel channel = event.getChannel();

		String msg = message.getContent();

		boolean isBot = author.isBot();

		if (isBot) {
			return;
		}

		CommandContainer cc = parser.parse(msg);
		if (cc == null) {
			return;
		}

		switch (cc.cmd) {
		// DICE
		case "dice":
		case "roll":
			if(!FeatureFlags.dice) break;
			Dice.setChannel(channel);
			Dice.setDiceString(cc.args[0]);
			Dice.rollDice();
			break;
		case "choose":
			if(!FeatureFlags.choose) break;
			Choose.setChannel(channel);
			Choose.setArgs(cc.args);
			Choose.choose();
			break;

		case "rhet":
		case "ask":
			if(!FeatureFlags.ask) break;
			Ask.setChannel(channel);
			Ask.answer();
			break;

		case "stat":
			if(!FeatureFlags.rollStat) break;
			RollStat.setChannel(channel);
			if (!cc.argsIsEmpty())
				RollStat.setRollString(cc.args[0]);
			RollStat.rollStat();
			break;
		case "silence":
		case "timeout":
			if(!FeatureFlags.timeout) break;
			TimeOut.setChannel(channel);
			TimeOut.setArgs(cc.args);
			TimeOut.timeOut(event);
			break;
		case "kick":
			if(!FeatureFlags.kick) break;
			Kick.setChannel(channel);
			Kick.setArgs(cc.args);
			Kick.kick(event);
		case "ban":
			if(!FeatureFlags.ban) break;
			Ban.setChannel(channel);
			Ban.setArgs(cc.args);
			Ban.ban(event);
		}
	}

}
