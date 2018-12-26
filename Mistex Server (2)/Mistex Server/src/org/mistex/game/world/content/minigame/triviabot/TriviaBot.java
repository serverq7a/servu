package org.mistex.game.world.content.minigame.triviabot;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.content.achievement.AchievementHandler;
import org.mistex.game.world.content.achievement.AchievementList;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.task.Task;

/**
 * The TriviaBot minigame
 * 
 * @author Omar | Play Boy
 */

public class TriviaBot extends Task {

	/**
	 * The current trivia question
	 */
	private static String currentQuestion = null;

	/**
	 * The current trivia answer
	 */
	private static String currentAnswer = null;

	/**
	 * The last trivia winner
	 */
	public static String lastWinner = "";

	/**
	 * Player attempts to question
	 */
	public static String attempt1 = "", attempt2 = "", attempt3 = "", attempt4 = "";

	/**
	 * The triviabot timer
	 */
	public TriviaBot() {
		super(650);
	}

	/**
	 * Executes the triviabot
	 */
	@Override
	protected void execute() {
		if (currentQuestion != null) {
			yell("[<col=D4021B>TriviaBot</col>] " + currentQuestion);
			yell("[<col=D4021B>TriviaBot</col>] Whoever answers my question correctly will be rewarded!");
			return;
		}
		assignQuestion();
	}

	/**
	 * Manually resets TriviaBot
	 */
	public static void manualReset() {
		reset();
		assignQuestion();
	}

	/**
	 * Assigns the trivia question
	 */
	private static void assignQuestion() {
		for (int i = 0; i < Questions.QUESTIONS.length; i++) {
			if (MistexUtility.random(i) == i) {
				currentQuestion = Questions.QUESTIONS[i][0];
				currentAnswer = Questions.QUESTIONS[i][1];
			}
		}
		yell("[<col=D4021B>TriviaBot</col>] " + currentQuestion);
		yell("[<col=D4021B>TriviaBot</col>] Whoever answers my question correctly will be rewarded!");
	}

	/**
	 * Resets the triviabot
	 */
	public static void reset() {
		currentAnswer = null;
		currentQuestion = null;
		attempt1 = "";
		attempt2 = "";
		attempt3 = "";
		attempt4 = "";
	}

	/**
	 * Answers the triviabot question
	 * 
	 * @param c
	 * @param message
	 */
	public static void answerQuestion(Client c, String message) {
		if (currentQuestion == null) {
			c.sendMessage("[<col=D4021B>TriviaBot</col>] I haven't assigned a question yet!");
			return;
		}
		for (int i = 0; i < BadAttempts.badAnswers.length; i++) {
			if (message.contains(BadAttempts.badAnswers[i])) {
				c.sendMessage("[<col=D4021B>TriviaBot</col>] That was a bad answer!");
				return;
			}
		}
		if (message.length() > currentAnswer.length()) {
			c.sendMessage("[<col=D4021B>TriviaBot</col>] That answer is too long!");
			return;
		}
		if (message.equalsIgnoreCase(currentAnswer)) {
			lastWinner = c.playerName;
			yell("[<col=D4021B>TriviaBot</col>] <col=D4021B>" + c.getRights().determineIcon() + "" + MistexUtility.capitalize(c.playerName) + "</col> has answered the question correctly!");
			yell("[<col=D4021B>TriviaBot</col>] The correct answer was: '<col=D4021B>" + MistexUtility.optimizeText(currentAnswer) + "'</col>.");
			yellAttempts(c, message);
			answeredCorrectly(c);
		} else {
			c.sendMessage("[<col=D4021B>TriviaBot</col>] Sorry, the answer you have entered is incorrect! Try again!");
			wrongAnswer(c, message);
		}
	}

	/**
	 * Handles player winning streak
	 * 
	 * @param c
	 */
	public static void winningStreak(Client c) {
		int winningBonus = MistexUtility.random(50000);
		if (lastWinner.equalsIgnoreCase(c.playerName)) {
			c.triviaWinningStreak++;
		} else {
			c.triviaWinningStreak = 0;
		}
		if (!lastWinner.equalsIgnoreCase(c.playerName)) {

		}
		if (c.triviaWinningStreak >= 5) {
			yell("[<col=D4021B>TriviaBot</col>] <col=D4021B>" + c.getRights().determineIcon() + "" + MistexUtility.capitalize(c.playerName) + "</col> is on a winning streak of <col=D4021B>" + MistexUtility.format(c.triviaWinningStreak) + "</col>!");
			c.sendMessage("<col=D4021B>You were given a bonus cash reward of " + MistexUtility.format(winningBonus) + "!");
			c.getBank().addItem(996, winningBonus);
		}
	}

	/**
	 * Yells the attempts
	 * 
	 * @param c
	 * @param message
	 */
	public static void yellAttempts(Client c, String message) {
		if (attempt1 == "" && attempt2 == "" && attempt3 == "" && attempt4 == "") {
		} else if (!(attempt1 == "") && attempt2 == "" && attempt3 == "" && attempt4 == "") {
			yell("[<col=D4021B>TriviaBot</col>] Attempted answers: <col=D4021B>" + attempt1 + "</col>!");
		} else if (!(attempt1 == "") && !(attempt2 == "") && attempt3 == "" && attempt4 == "") {
			yell("[<col=D4021B>TriviaBot</col>] Attempted answers: <col=D4021B>" + attempt1 + "</col>, <col=D4021B>" + attempt2 + "</col>!");
		} else if (!(attempt1 == "") && !(attempt2 == "") && !(attempt3 == "") && attempt4 == "") {
			yell("[<col=D4021B>TriviaBot</col>] Attempted answers: <col=D4021B>" + attempt1 + "</col>, <col=D4021B>" + attempt2 + "</col>, <col=D4021B>" + attempt3 + "</col>!");
		} else if (!(attempt1 == "") && !(attempt2 == "") && !(attempt3 == "") && !(attempt4 == "")) {
			yell("[<col=D4021B>TriviaBot</col>] Attempted answers: <col=D4021B>" + attempt1 + "</col>, <col=D4021B>" + attempt2 + "</col>, <col=D4021B>" + attempt3 + "</col>, <col=D4021B>" + attempt4 + "</col>!");
		}
	}

	/**
	 * What happens when player has wrong answer
	 * 
	 * @param c
	 * @param message
	 */
	public static void wrongAnswer(Client c, String message) {
		if (attempt1 == "" && attempt2 == "" && attempt3 == "" && attempt4 == "") {
			attempt1 = message;
		} else if (!(attempt1 == "") && attempt2 == "" && attempt3 == "" && attempt4 == "") {
			attempt2 = message;
		} else if (!(attempt1 == "") && !(attempt2 == "") && attempt3 == "" && attempt4 == "") {
			attempt3 = message;
		} else if (!(attempt1 == "") && !(attempt2 == "") && !(attempt3 == "") && attempt4 == "") {
			attempt4 = message;
		} else if (!(attempt1 == "") && !(attempt2 == "") && !(attempt3 == "") && !(attempt4 == "")) {

		}
	}

	/**
	 * Player has answered the question correctly
	 * 
	 * @param c
	 */
	public static void answeredCorrectly(Client c) {
		reset();
		resetStreak();
		int coinsToGive = MistexUtility.random(100000);
		c.triviaWon += 1;
		c.getBank().addItem(996, coinsToGive);
		c.sendMessage("[<col=D4021B>TriviaBot</col>] You have won <col=D4021B>" + MistexUtility.format(coinsToGive) + "</col> coins! It was banked.");
		winningStreak(c);
		if (c.triviaWon == 1)
			c.sendMessage("@war@You have started the achievement: Braniac ");
		InterfaceText.writeText(new AchievementTab(c));
		if (c.triviaWon == 100)
			AchievementHandler.activateAchievement(c, AchievementList.BRANIAC);
	}

	/**
	 * Resets people with a winstreak
	 */
	public static void resetStreak() {
		for (int p = 0; p < World.players.length; p++) {
			if (World.players[p] != null) {
				Client c2 = (Client) World.players[p];
				if (!lastWinner.equalsIgnoreCase(c2.playerName)) {
					if (c2.triviaWinningStreak >= 2) {
						c2.sendMessage("@red@Your winning streak of " + MistexUtility.format(c2.triviaWinningStreak) + " has been ended by " + lastWinner + "!");
					}
				}
			}
		}
	}

	/**
	 * Yells the triviabot to server
	 * 
	 * @param string
	 */
	public static void yell(String string) {
		for (int j = 0; j < World.players.length; j++) {
			if (World.players[j] != null) {
				Client c2 = (Client) World.players[j];
				c2.sendMessage(string);
			}
		}
	}

}
