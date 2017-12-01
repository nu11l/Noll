package Discord.Main;

import Discord.API.CommandProcessor;
import Discord.API.EmbedMenu;
import sx.blah.discord.api.events.EventSubscriber;
//import sx.blah.discord.handle.impl.events.guild.channel.TypingEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionAddEvent;

public class BotListener {
	public static String prefix = new String("!");
	
	@EventSubscriber
	public void onMessageEventTest(MessageReceivedEvent event) {
		if(event.getMessage().getContent().toLowerCase().startsWith(prefix)) {
			CommandProcessor.processCommand(event.getMessage(), prefix);
		}
		CommandProcessor.reactToMessage(event.getMessage());
	}
	@EventSubscriber
	public void onReactionAddEvent(ReactionAddEvent event) {
		EmbedMenu.processReactions(event.getMessage(), event.getReaction(), event.getUser());
		
	}
	
	/*@EventSubscriber
	public void onTypingEvent(TypingEvent event) {
		CommandProcessor.tellToStop(event.getChannel());
	}*/
	
	
}
