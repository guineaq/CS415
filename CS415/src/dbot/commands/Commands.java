package dbot.commands;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Commands {
	
	protected static MessageChannel channel;
	
	public synchronized static void setChannel(MessageChannel channel) {
		Commands.channel = channel;
	}
	
	protected synchronized static void sendMessage(String string) {
		channel.sendMessage(string).queue();
	}

	protected synchronized static Member findMemberByString(MessageReceivedEvent event, String name) {
		return event.getGuild().getMembersByName(name, true).get(0);
	}
}
