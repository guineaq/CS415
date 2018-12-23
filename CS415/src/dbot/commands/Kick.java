package dbot.commands;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Kick extends Commands {
	
	private static String args[];
	
	public static void setArgs(String args[]) {
		Kick.args = args;
	}
	
	public static void kick(MessageReceivedEvent event) {
		if(args.length != 1) {
			sendMessage("Invalid amount of parameters, <userName>");
		}
		
		if(event.getChannelType() == ChannelType.PRIVATE) {
			sendMessage("You can't use this command in private chat!");
			return;
		}

		final Member member = findMemberByString(event, args[0]);
		if(member == null) {
			sendMessage("No such user exists.");
			return;
		}
		event.getGuild().getController().kick(member).queue();;
		
		sendMessage("User " + member.getNickname() + " has been kicked. Good riddance.");
	}
}
