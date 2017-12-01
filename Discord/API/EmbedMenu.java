package Discord.API;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

import sx.blah.discord.handle.obj.IEmbed.IEmbedField;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IReaction;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.EmbedBuilder;

public class EmbedMenu {
	static String[] numbers = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	public static boolean calculatorMode = false;
	public static boolean isInCalculatorMode = false;
	public static boolean isCalcOperationChosen = false;
	public static boolean completedOperation = false;
	public static String operation = "";
	public static int firstNum;
	public static int secondNum;
	public static String number = "";
	public static EmbedBuilder calcBuilder = new EmbedBuilder();

	public static boolean parseReaction(IMessage message, IReaction reaction, IUser user) {
		String reactionEmoji = EmojiParser.parseToAliases(reaction.getEmoji().getName());
		if (reactionEmoji.equals(":thinking:")) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withColor(255, 0, 0);
			builder.appendField("What is this?",
					"This is an embed menu. Click on the reactions to navigate the interface!", false);
			message.edit(builder.build());
			return true;
		} else if (reactionEmoji.equals(":question:")) {
			EmbedBuilder builder = new EmbedBuilder();
			builder.withColor(255, 0, 0);
			builder.withTitle("List of commands (prefix = !)");
			builder.appendField("ping", "pong!", false);
			builder.appendField("isuser [username]", "checks if username is valid", false);
			builder.appendField("obfuscate [user]", "encrypts the target message with caesar cipher", false);
			builder.appendField("obfuscate [amount of time][s/m/h] [user]",
					"encrypts the target message with caesar cipher for the specified amount of time", false);
			builder.appendField("unobfuscate [user]", "reverses the obfuscate for a user if they are obfuscated",
					false);
			builder.appendField("blacklist [user]", "deletes target user messages upon sending", false);
			builder.appendField("blacklist [amount of time][s/m/h] [user]",
					"deletes target user messages upon sending for the specified amount of time", false);
			builder.appendField("whitelist [user]", "reverses a blacklist for a user if they are blacklisted", false);
			builder.appendField("remindme [amount of time][s/m/h] [text]",
					"reminds author of message with specified text at specified time", false);
			builder.appendField("remind [user] [amount of time][s/m/h] [text]",
					"reminds target user with specified text at specified time", false);
			builder.appendField("status", "displays the status of the user", false);
			builder.appendField("top ", "displays the top users", false);
			builder.appendField("givexp ", "gives the specified amount of xp to the user", false);
			builder.appendField("takexp ", "takes the specified amount of xp from the user", false);
			builder.appendField("setnotification ", "blocks the output of the current channel", false);
			builder.appendField("clearnotification", "clears the channel in which notifications take place", false);
			builder.appendField("timezone set", "sets your timezone location (UTC)", false);
			builder.appendField("timezone get", "gets the timezone of the user, if user has specified it", false);
			builder.appendField("localtime ", "gets the current time of day for the specified user", false);
			builder.appendField("block ", "blocks the output of the current channel", false);
			builder.appendField("unblock ", "unblocks the output of the current channel", false);
			builder.appendField("blockxp ", "blocks the xp counting of the current channel", false);
			builder.appendField("unblockxp ", "unblocks the xp counting of the current channel", false);

			message.edit(builder.build());
			return true;
		} else if (reactionEmoji.equals(":iphone:")) {
			return calculatorMode = EmbedMenuCalculator.init(message);
		} else if (reactionEmoji.equals(":file_cabinet:")) {
			if (user.getDisplayName(message.getGuild()).equals("Rares")) {
				message.getChannel().sendMessage("Rares detected! Quit deleting messages.");
			} else {
				message.delete();
			}
			return true;

		} else if (calculatorMode) {
			return EmbedMenuCalculator.reactionCalculator(reactionEmoji, message);
		}
		
		return false;
	}

	
	public static void addReactions(IMessage m) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.addReaction(EmojiManager.getForAlias("thinking"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.addReaction(EmojiManager.getForAlias("question"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.addReaction(EmojiManager.getForAlias("iphone"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.addReaction(EmojiManager.getForAlias("file_cabinet"));
	}

	public static void removeReactions(IMessage m) {
		try {
			Thread.sleep(75);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.removeAllReactions();
	}

	public static void InitMenu(IMessage message) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.withColor(255, 0, 0);
		builder.appendField("What is this?", "A menu", false);
		IMessage m = message.getChannel().sendMessage(builder.build());
		addReactions(m);
	}

	public static void processReactions(IMessage message, IReaction reaction, IUser user) {
		boolean afterBot = false;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!user.isBot()) {
			afterBot = true;
		}
		if (afterBot) {
			if (parseReaction(message, reaction, user)) {
				message.removeReaction(user, reaction);
			}

			System.out.println("Removed " + user.getName() + "'s reaction. ");
		}

	}
}
