package Discord.API;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;
import sx.blah.discord.util.RequestBuffer;

public class RemindCommand {

	public static void RemindMe(String[] command, IChannel channel, IUser sender) {
		if (command.length >= 3) {
			// time condition
			TimeUnit t;
			String unitOfTime;
			String timeValue = command[1].replaceAll("[^0-9]", "");
			String alteredTime = command[1].replaceAll("[0-9]", "");
			String remindMessageTemp = "";
			final String finalmessage;

			switch (alteredTime) {
			case "h":
				t = TimeUnit.HOURS;
				unitOfTime = "hour";
				break;
			case "s":
				t = TimeUnit.SECONDS;
				unitOfTime = "second";
				break;
			case "m":
				t = TimeUnit.MINUTES;
				unitOfTime = "minute";
				break;
			default:
				t = TimeUnit.HOURS;
				unitOfTime = "hours";
				break;
			}
			channel.sendMessage("Reminding " + sender.getName() + " in " + timeValue + " " + unitOfTime + "(s)");
			for (int i = 2; i < command.length; i++) {
				remindMessageTemp += command[i] + " ";
			}
			finalmessage = remindMessageTemp;
			// String id = command[2].replaceAll("[<>@!]", "");
			ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
			ses.schedule(new Runnable() {
				@Override
				public void run() {
					channel.sendMessage(sender.mention());
					// channel.sendMessage("Reminding " + sender.mention() +
					// ": " + finalmessage);
					EmbedBuilder builder = new EmbedBuilder();
					builder.withColor(255, 0, 0);
					builder.appendField("Reminder", finalmessage, false);
					RequestBuffer.request(() -> channel.sendMessage(builder.build()));

					return;
				}
			}, Integer.parseInt(timeValue), t);

		}
	}
	
	
	public static void Remind(String[] command, IChannel channel, IUser sender, IMessage message) {
		if (command.length >= 4) {
			// time condition
			TimeUnit t;
			String unitOfTime;
			String timeValue = command[2].replaceAll("[^0-9]", "");
			String alteredTime = command[2].replaceAll("[0-9]", "");
			String remindMessageTemp = "";
			final String finalmessage;

			switch (alteredTime) {
			case "h":
				t = TimeUnit.HOURS;
				unitOfTime = "hour";
				break;
			case "s":
				t = TimeUnit.SECONDS;
				unitOfTime = "second";
				break;
			case "m":
				t = TimeUnit.MINUTES;
				unitOfTime = "minute";
				break;
			default:
				t = TimeUnit.HOURS;
				unitOfTime = "hours";
				break;
			}
			IUser user = CommandProcessor.findAllOfUser(message, command[1]);
			if (user == null) {
				channel.sendMessage("User does not exist!");
				return;
			} else if (user.getName().equals(sender.getName())) {
				channel.sendMessage("What are you doing?");
				return;
			}
			channel.sendMessage("Reminding " + user.getName() + " in " + timeValue + " " + unitOfTime + "(s)");
			for (int i = 3; i < command.length; i++) {
				remindMessageTemp += command[i] + " ";
			}
			finalmessage = remindMessageTemp;
			// String id = command[2].replaceAll("[<>@!]", "");
			ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
			ses.schedule(new Runnable() {
				@Override
				public void run() {
					channel.sendMessage(user.mention());
					// channel.sendMessage("Reminding " + user.mention() +
					// ": " + finalmessage);
					EmbedBuilder builder = new EmbedBuilder();
					builder.withColor(255, 0, 0);
					builder.appendField("Reminder", finalmessage, false);
					RequestBuffer.request(() -> channel.sendMessage(builder.build()));

					return;
				}
			}, Integer.parseInt(timeValue), t);

		}
	}
	
	
}
