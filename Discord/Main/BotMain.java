package Discord.Main;



import java.util.List;
import java.util.Scanner;

import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;

public class BotMain implements Runnable{
	
	public static final IDiscordClient bot = createClient("bot user token here", true);
	Scanner scan = new Scanner(System.in);
	public static void main(String args[]){
			
			EventDispatcher dis = bot.getDispatcher();
			dis.registerListener(new BotListener());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			(new Thread(new BotMain())).start();
			
		
	}
	
	public static IDiscordClient createClient(String token, boolean login) {
	    ClientBuilder clientBuilder = new ClientBuilder();
	    clientBuilder.withToken(token);
	    try {
			if (login) {
	      		return clientBuilder.login();
	    	} else {
	      		return clientBuilder.build();
	    	}
		} catch (DiscordException e) {
			e.printStackTrace();
			return null;
		}
	  }

	@Override
	public void run() {
		List<IChannel>list = bot.getChannels();
		IChannel channel = null;
		for(IChannel i : list) {
			System.out.println(i.getName());
			if(i.getName().equals("testing")) {
				channel = i;
			}
		}
		while(true) {
			String toSend = scan.nextLine();
			channel.sendMessage(toSend);
		}
		
	}

}