package Discord.API;

import com.vdurmont.emoji.EmojiManager;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;

public class EmbedMenuCalculator {
	static String[] numbers = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	public static boolean isInCalculatorMode = false;
	public static boolean isCalcOperationChosen = false;
	public static boolean completedOperation = false;
	public static String operation = "";
	public static int firstNum;
	public static int secondNum;
	public static String number = "";
	public static EmbedBuilder calcBuilder = new EmbedBuilder();
	
	public static boolean init(IMessage message) {
		calcBuilder.withColor(255, 0, 0);
		calcBuilder.withTitle("Calculator");
		calcBuilder.withDesc("");
		message.edit(calcBuilder.build());
		addCalcReactions(message);
		//addCalcOperationReactions(message); //comment out if operations are wanted separately
		isInCalculatorMode = true;
		if(isInCalculatorMode) {
			return true;
		}
		return false;
	}
	
	public static boolean reactionCalculator(String reactionEmoji, IMessage message) {
		for (int i = 0; i < numbers.length; i++) {
			if (reactionEmoji.equals(":" + numbers[i] + ":")) {
				if (!completedOperation) {
					number += i;
					calcBuilder.appendDescription("" + i);
					message.edit(calcBuilder.build());

				} else {
					isInCalculatorMode = false;
					isCalcOperationChosen = false;
					completedOperation = false;
					firstNum = 0;
					secondNum = 0;
					number = "";
					calcBuilder = new EmbedBuilder();
					EmbedBuilder builder = new EmbedBuilder();
					builder.withColor(255, 0, 0);
					builder.appendField("What is this?",
							"This is an embed menu. Click on the reactions to navigate the interface!", false);
					message.edit(builder.build());
					message.removeAllReactions();
					EmbedMenu.addReactions(message);
					EmbedMenu.calculatorMode = false;
					return true;
				}

				return true;

			}
		}
		boolean returnValue = false;

		if (reactionEmoji.equals(":heavy_plus_sign:")) {
			System.out.println("plus");
			calcBuilder.appendDescription(" " + "+ ");
			operation = "p";
			returnValue = true;
		} else if (reactionEmoji.equals(":heavy_minus_sign:")) {
			calcBuilder.appendDescription(" " + "- ");
			operation = "mi";
			returnValue = true;
		} else if (reactionEmoji.equals(":heavy_multiplication_x:")) {
			calcBuilder.appendDescription(" " + "x ");
			operation = "mu";
			returnValue = true;
		} else if (reactionEmoji.equals(":heavy_division_sign:")) {
			calcBuilder.appendDescription(" " + "/ ");
			operation = "d";
			returnValue = true;
		} else if (reactionEmoji.equals(":heavy_check_mark:")) {
			if (!isCalcOperationChosen) {
				message.removeAllReactions();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addCalcOperationReactions(message);
				//message.delete(); //comment this out and uncomment the above if separate operation reactions are wanted
			} else {
				secondNum = Integer.parseInt(number);
				number = "";
				int outputNum = 0;
				if(operation.equals("p")) {
					outputNum = firstNum + secondNum;
				}else if(operation.equals("mi")) {
					outputNum = firstNum - secondNum;
				}else if(operation.equals("mu")) {
					outputNum = firstNum * secondNum;
				}else if(operation.equals("d")) {
					outputNum = firstNum / secondNum;
				}
				
				calcBuilder.appendDescription(" = " + outputNum);
				message.edit(calcBuilder.build());
				completedOperation = true;
				return true;
			}
		}
		if (returnValue) {
			isCalcOperationChosen = true;
			message.edit(calcBuilder.build());
			firstNum = Integer.parseInt(number);
			number = "";
			addCalcReactions(message); //uncomment this if separate operation reactions are desired
			
		}
		return returnValue;

	
	}
	
	public static void addCalcOperationReactions(IMessage m) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		m.addReaction(EmojiManager.getForAlias("heavy_plus_sign"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m.addReaction(EmojiManager.getForAlias("heavy_minus_sign"));

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.addReaction(EmojiManager.getForAlias("heavy_multiplication_x"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.addReaction(EmojiManager.getForAlias("heavy_division_sign"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		m.addReaction(EmojiManager.getForAlias("heavy_check_mark"));
	}

	public static void addCalcReactions(IMessage m) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		m.removeAllReactions();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < numbers.length; i++) {
			m.addReaction(EmojiManager.getForAlias(numbers[i]));
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		m.addReaction(EmojiManager.getForAlias("heavy_check_mark"));
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
