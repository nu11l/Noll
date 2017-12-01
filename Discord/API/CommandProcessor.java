package Discord.API;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;

//import com.vdurmont.emoji.Emoji;
//import com.vdurmont.emoji.EmojiLoader;
//import com.vdurmont.emoji.EmojiManager;
//import com.vdurmont.emoji.EmojiParser;

//import sx.blah.discord.api.internal.json.objects.EmojiObject;
//import sx.blah.discord.api.internal.json.objects.ReactionEmojiObject;
//import sx.blah.discord.handle.impl.obj.EmojiImpl;
//import sx.blah.discord.handle.impl.obj.ReactionEmoji;
import sx.blah.discord.handle.obj.IChannel;
//import sx.blah.discord.handle.obj.IEmoji;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
//import sx.blah.discord.handle.obj.IReaction;
//import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;
//import sx.blah.discord.util.EmbedBuilder;
//import sx.blah.discord.util.RequestBuffer;

public class CommandProcessor {
	static ArrayList<String> blacklist = new ArrayList<String>();
	static ArrayList<String> obfuscateList = new ArrayList<String>();
	public static final int shift = 7; // for the obfuscate command

	static IUser findUser(IMessage message, String name) {
		List<IUser> user = message.getGuild().getUsers();
		for (IUser temp : user) {
			if (temp.getName().equals(name)) {
				return temp;

			} else if (temp.getDisplayName(message.getGuild()).equals(name)) {
				return temp;
			}

		}

		return null;
	}

	static boolean isValidUser(String username) {
		for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {

			if (username.toUpperCase().indexOf(alphabet) >= 0) {

				return false;

			}

		}
		return true;
	}

	static IUser findAllOfUser(IMessage message, String username) {
		String id = username.replaceAll("[<>@!]", "");
		IUser userTemp;
		if (!isValidUser(id)) {
			userTemp = findUser(message, username);
			if (userTemp == null) {
				return null;
			}
		} else {
			userTemp = message.getGuild().getUserByID(Long.parseLong(id));
		}
		return userTemp;
	}

	
	
	public static void processCommand(IMessage message, String prefix) {
		IUser sender = message.getAuthor();
		IChannel channel = message.getChannel();
		IGuild guild = message.getGuild();

		String[] command = message.getContent().replaceFirst(prefix, "").split(" ");
		
		if(guild == null) {
			System.out.println(message.getAuthor() + ": " + message.getContent());
		}

		if (command[0].equals("ping")) {
			channel.sendMessage("pong!");
		} else if (command[0].equals("avatar")) {
			if (command.length == 2) {
				command[1] = command[1].replaceAll("[<>@!]", "");
				if (!isValidUser(command[1])) {
					channel.sendMessage(command[1] + " Is not a valid user!");
				}
				IUser user = guild.getUserByID(Long.parseLong(command[1]));
				channel.sendMessage(user.mention() + "'s avatar: " + user.getAvatarURL());
			} else {
				channel.sendMessage("Please enter a valid arguments!" + prefix + "avatar @user");
			}
		} else if (command[0].equals("shitpost")) {
			String output = "";
			for (int i = 1; i < command.length; i++) {
				for (int j = 0; j < command[i].length(); j++) {
					if (command[i].charAt(j) > 'a' && command[i].charAt(j) < 'z') {
						if (command[i].charAt(j) != 'b') {
							output += ":regional_indicator_" + command[i].charAt(j) + ": ";
						} else {
							output += " :b: ";
						}
					}

				}
				output += "   ";
			}
			channel.sendMessage(output);
			System.out.println(output);
		} else if (command[0].equals("aesthetify")) {
			String output = "";
			for (int i = 1; i < command.length; i++) {
				for (int j = 0; j < command[i].length(); j++) {
					output += "*" + Character.toUpperCase(command[i].charAt(j)) + "*" + " ";
				}
				output += "  ";
			}
			channel.sendMessage(output);
		} else if (command[0].equals("blacklist")) {
			Moderation.Blacklist(command, sender, guild, message, channel);
		} else if (command[0].equals("whitelist")) {
			Moderation.Whitelist(command, sender, guild, message, channel);
		} else if (command[0].equals("obfuscate")) {
			Moderation.Obfuscate(command, sender, guild, message, channel);
		} else if (command[0].equals("unobfuscate")) {
			Moderation.UnObfuscate(command, sender, guild, message, channel);
		} else if (command[0].equals("isuser")) {
			if (command.length == 2) {
				IUser user = findUser(message, command[1]);
				if (user != null) {
					channel.sendMessage("Found user!");
				} else {
					channel.sendMessage("User does not exist");
				}

			}
		
		} else if (command[0].equals("decipher")) {
			String output = "";
			for (int i = 1; i < command.length; i++) {
				for (int j = 0; j < command[i].length(); j++) {
					if (command[i].charAt(j) != ' ') {
						char c = (char) (command[i].charAt(j) - shift);
						if (c > 'z') {
							c = (char) (c - 26);
						} else if (c < 'a') {
							c = (char) (c + 26);
						}
						output += c;
					}

				}
				output += ' ';
			}
			channel.sendMessage(output);
			System.out.println(output);
		} else if (command[0].equals("remindme")) {
			RemindCommand.RemindMe(command, channel, sender);
		} else if (command[0].equals("remind")) {
			RemindCommand.Remind(command, channel, sender, message);

		} else if (command[0].equals("menu")) {
			EmbedMenu.InitMenu(message);
			
		}
		/*
		 * else if(command[0].equals("status")) { if(sender ==
		 * findAllOfUser(message, "null")) { EmbedBuilder builder = new
		 * EmbedBuilder(); builder.withColor(255, 0, 0); builder.appendField(
		 * "Status of " + message.getAuthor().getName(), "Awesome Person",
		 * false); RequestBuffer.request(() ->
		 * channel.sendMessage(builder.build())); } //channel.sendMessage(
		 * "Reminding " + user.mention() + ": " + finalmessage);
		 * 
		 * }
		 */
	}

	
	
	

	public static void reactToMessage(IMessage message) {
		for (Iterator<String> iterator = blacklist.iterator(); iterator.hasNext();) {
			String s = iterator.next();
			if (message.getAuthor().getName().equals(s)) {
				message.delete();
			}
		}
		/*
		 * if(message.getAuthor() == findAllOfUser(message, "null")) {
		 * message.getChannel().sendMessage(message.getContent());
		 * message.delete(); }
		 */
		for (Iterator<String> iterator = obfuscateList.iterator(); iterator.hasNext();) {
			String s = iterator.next();
			if (message.getAuthor().getName().equals(s)) {
				String editedMessage = "";
				int len = message.getContent().length();
				for (int i = 0; i < len; i++) {
					if (message.getContent().charAt(i) != ' ') {
						char c = (char) (message.getContent().charAt(i) + shift);
						if (c > 'z') {
							c = (char) (c - 26);
						} else if (c < 'a') {
							c = (char) (c + 26);
						}
						editedMessage += c;
					} else {
						editedMessage += ' ';
					}

				}
				message.delete();
				message.getChannel().sendMessage(message.getAuthor()
						+ "'s message has been obfuscated! Decipher it to read it! " + editedMessage);

				len = 0;
			}
		}

	}
}

// }
