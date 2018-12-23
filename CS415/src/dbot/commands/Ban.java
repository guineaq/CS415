package dbot.commands;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class Ban extends Commands {
	
	private static String args[];

	public static void setArgs(String args[]) {
		Ban.args = args;
	}
	
	public static void ban(MessageReceivedEvent event) {
		if(args.length != 2) {
			sendMessage("Invalid amount of parameters, <userName> <days>");
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
		
		int days = Integer.parseInt(args[1]);
		event.getGuild().getController().ban(member, days);
		
		sendMessage("User " + member.getNickname() + " has been banned. Good riddance.");
	}
	
}
