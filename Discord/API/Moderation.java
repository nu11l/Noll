package Discord.API;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

public class Moderation {
	public static void Obfuscate(String[] command, IUser sender, IGuild guild, IMessage message, IChannel channel) {
		if (command.length == 2) {
			command[1] = command[1].replaceAll("[<>@!]", "");
			List<IRole> role = sender.getRolesForGuild(guild);
			for (IRole r : role) {
				if (r.getName().equals("Admin")) {
					IUser user = CommandProcessor.findAllOfUser(message, command[1]);
					if (user == null) {
						channel.sendMessage("User does not exist!");
						return;
					} else if (user.getName().equals(sender.getName())) {
						channel.sendMessage("What are you doing?");
						return;
					}
					CommandProcessor.obfuscateList.add(user.getName());
				}
			}

		} else if (command.length == 3) {
			// time condition
			TimeUnit t;
			String unitOfTime;
			String timeValue = command[1].replaceAll("[^0-9]", "");
			String alteredTime = command[1].replaceAll("[0-9]", "");

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

			// String id = command[2].replaceAll("[<>@!]", "");
			List<IRole> role = sender.getRolesForGuild(guild);
			for (IRole r : role) {
				if (r.getName().equals("Admin")) {

					IUser user = CommandProcessor.findAllOfUser(message, command[2]);
					if (user == null) {
						channel.sendMessage("User does not exist!");
						return;
					} else if (user.getName().equals(sender.getName())) {
						channel.sendMessage("What are you doing?");
						return;
					}
					channel.sendMessage(
							"Obfuscating " + user + "'s messages for " + timeValue + " " + unitOfTime + "(s)");
					CommandProcessor.obfuscateList.add(user.getName());
					ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
					ses.schedule(new Runnable() {
						@Override
						public void run() {
							if (CommandProcessor.obfuscateList.contains(user.getName())) {
								CommandProcessor.obfuscateList.remove(user.getName());
								channel.sendMessage(user + " has been unobfuscated!");
							}

							return;
						}
					}, Integer.parseInt(timeValue), t);
				}
			}
		}
	}
	
	public static void Blacklist(String[] command, IUser sender, IGuild guild, IMessage message, IChannel channel) {
		if (command.length == 2) {
			command[1] = command[1].replaceAll("[<>@!]", "");
			List<IRole> role = sender.getRolesForGuild(guild);
			for (IRole r : role) {
				if (r.getName().equals("Admin")) {
					IUser user = CommandProcessor.findAllOfUser(message, command[1]);
					if (user == null) {
						channel.sendMessage("User does not exist!");
						return;
					} else if (user.getName().equals(sender.getName())) {
						channel.sendMessage("What are you doing?");
						return;
					}
					CommandProcessor.blacklist.add(user.getName());
				}
			}

		} else if (command.length == 3) {
			// time condition
			TimeUnit t;
			String unitOfTime;
			String timeValue = command[1].replaceAll("[^0-9]", "");
			String alteredTime = command[1].replaceAll("[0-9]", "");

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

			command[2] = command[2].replaceAll("[<>@!]", "");
			List<IRole> role = sender.getRolesForGuild(guild);
			IUser user = CommandProcessor.findAllOfUser(message, command[2]);
			if (user == null) {
				channel.sendMessage("User does not exist!");
				return;
			} else if (user.getName().equals(sender.getName())) {
				channel.sendMessage("What are you doing?");
				return;
			}
			for (IRole r : role) {
				if (r.getName().equals("Admin")) {
					channel.sendMessage("Blacklisting " + user + " for " + timeValue + " " + unitOfTime + "(s)");
					CommandProcessor.blacklist.add(user.getName());
					ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
					ses.schedule(new Runnable() {
						@Override
						public void run() {
							if (CommandProcessor.blacklist.contains(user.getName())) {
								CommandProcessor.blacklist.remove(user.getName());
								channel.sendMessage(user + " has been whitelisted!");
							}

							return;
						}
					}, Integer.parseInt(timeValue), t);
				}
			}

		}
	}
	
	public static void Whitelist(String[] command, IUser sender, IGuild guild, IMessage message, IChannel channel) {
		if (command.length == 2) {
			command[1] = command[1].replaceAll("[<>@!]", "");
			List<IRole> role = sender.getRolesForGuild(guild);
			for (IRole r : role) {
				if (r.getName().equals("Admin")) {
					IUser user = CommandProcessor.findAllOfUser(message, command[1]);
					if (user == null) {
						channel.sendMessage("User does not exist!");
						return;
					} else if (user.getName().equals(sender.getName())) {
						channel.sendMessage("What are you doing?");
						return;
					}
					CommandProcessor.blacklist.remove(user.getName());
				}
			}

		}
	}
	
	public static void UnObfuscate(String[] command, IUser sender, IGuild guild, IMessage message, IChannel channel) {
		if (command.length == 2) {
			command[1] = command[1].replaceAll("[<>@!]", "");
			List<IRole> role = sender.getRolesForGuild(guild);
			for (IRole r : role) {
				if (r.getName().equals("Admin")) {
					IUser user = CommandProcessor.findAllOfUser(message, command[1]);
					if (user == null) {
						channel.sendMessage("User does not exist!");
						return;
					} else if (user.getName().equals(sender.getName())) {
						channel.sendMessage("What are you doing?");
						return;
					}
					CommandProcessor.obfuscateList.remove(user.getName());
				}
			}

		}
	}
	
}
