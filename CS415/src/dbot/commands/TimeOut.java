package dbot.commands;

import java.util.List;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class TimeOut extends Commands {
	
	private static String args[];
	
	public static void setArgs(String args[]) {
		TimeOut.args = args;
	}
	
	public static void timeOut(MessageReceivedEvent event) {
		if(args.length != 2) {
			sendMessage("Invalid amount of parameters, <userName> <time in seconds>");
		}
		
		if(event.getChannelType() == ChannelType.PRIVATE) {
			sendMessage("You can't use this command in private chat!");
			return;
		}
		
		List<Role> role = null;
		Member member = null;
		try {
			member = findMemberByString(event, args[0]);
		} catch (Exception e) {
			sendMessage("User named " + args[0] + " was not found, perhaps you typed their nickname?");
			return;
		}
		
		try {
			role = event.getGuild().getRolesByName("silenced", true);
		} catch(Exception e) {
			sendMessage("Create a new role named 'silenced' in order to use this command.");
		}
		
		final Role nrole = role.get(0);
		event.getGuild().getController().addSingleRoleToMember(member, nrole).queue();
		sendMessage("User named: " + member.getNickname() + " has been timed out for " + args[1] + " seconds.");
		
		final int ncount = Integer.parseInt(args[1]);

		Thread t = new Thread(new Runnable() {
			int count = ncount;
			
			public void run() {
				while(count > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count--;
				}
				event.getGuild().getController().removeSingleRoleFromMember(member, nrole).queue();;
				sendMessage("Timeout for " + member.getNickname() + " is now over.");
			}
		});
		t.start();
	}
}
