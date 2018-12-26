import java.applet.AppletContext;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedList;

@SuppressWarnings("unused")
public class Client extends RSApplet {
	
	
	public static ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
	
	public static FogFilter fogFilter = new FogFilter(110, 450f, new Color(200, 192, 168));
	public static DepthDebugFilter debugFilter = new DepthDebugFilter();
	private NumberFormat format = NumberFormat.getInstance();
	public boolean showXP;
	public boolean showBonus;
	public int gainedExpY = 0;
	public static boolean xpGained = false, canGainXP = true;
	public static int totalXP = 0;
	public Sprite[] cacheSprite;
	private String familiarSpecial;
	public boolean hasSummoning = false;
	private LinkedList<XPGain> gains = new LinkedList<XPGain>();

	public void addXP(int skillID, int xp) {
		if (xp != 0 && canGainXP) {
			// totalXP += xp;
			gains.add(new XPGain(skillID, xp));
		}
	}
	
	private final int[] hitmarks562 = {31, 32, 33, 34};

	public class XPGain {
		/**
		 * The skill which gained the xp
		 */
		private int skill;

		/**
		 * The XP Gained
		 */
		private int xp;
		private int y;
		private int alpha = 0;

		public XPGain(int skill, int xp) {
			this.skill = skill;
			this.xp = xp;
		}

		public void increaseY() {
			y++;
		}

		public int getSkill() {
			return skill;
		}

		public int getXP() {
			return xp;
		}

		public int getY() {
			return y;
		}

		public int getAlpha() {
			return alpha;
		}

		public void increaseAlpha() {
			alpha += alpha < 256 ? 30 : 0;
			alpha = alpha > 256 ? 256 : alpha;
		}

		public void decreaseAlpha() {
			alpha -= alpha > 0 ? 30 : 0;
			alpha = alpha > 256 ? 256 : alpha;
		}
	}
	
	public byte[] getAnims(int index) {
		try {
			File model = new File(Signlink.findcachedir()+"/Anims/" + index + ".gz");
			byte[] aByte = new byte[(int)model.length()];
			FileInputStream Fis = new FileInputStream(model);
			Fis.read(aByte);
			Fis.close();
			return aByte;
		} catch(Exception e) {
			return null;
		}
	}
	
	public void anims() {
		for(int AnimIndex = 0; AnimIndex < 4000; AnimIndex++) {
			byte[] abyte0 = getAnims(AnimIndex);
			if(abyte0 != null && abyte0.length > 0) {
			decompressors[2].method234(abyte0.length, abyte0, AnimIndex);
			System.out.println("Repacked " + AnimIndex + ".");
			}
		}
	}

	public void displayXPCounter() {
		int x = clientSize == 0 ? 414 : clientWidth - 308;
		int y = clientSize == 0 ? 3 : -38;
		int currentIndex = 0;
		int offsetY = 0;
		int stop = 70;
		this.imageLoader[21].drawSprite(x - 11, 50 + y);
		int x2 = clientSize == 0 ? 513 - 110 : Client.clientWidth - 320;
        int length = Integer.toString(totalXP).length();
        int i = length == 1 ? 5 : ((length - 1) * 5);
        normalFont.method382(0xFFFFFD, x2 + 11, "XP:", (clientSize == 0 ? 67 : 25), true);
        if (xpCounter <= 214700000) {
            normalFont.method382(0xFFFFFD, x2 + 95 - i,
                NumberFormat.getIntegerInstance().format(totalXP), (clientSize == 0 ? 67 : 25), true);
        } else if (xpCounter >= 214700000) {
            normalFont.method382(0xFF0000, x2 + 95 - i, "Lots!", (clientSize == 0 ? 67 : 25), true);
        }
		if (!this.gains.isEmpty()) {
			Iterator<Client.XPGain> it$ = this.gains.iterator();
			while (it$.hasNext()) {
				Client.XPGain gain = (Client.XPGain) it$.next();
				if (gain.getY() < stop) {
					if (gain.getY() <= 10) {
						gain.increaseAlpha();
					}
					if (gain.getY() >= stop - 10) {
						gain.decreaseAlpha();
					}
					gain.increaseY();
				} else if (gain.getY() >= stop) {
					it$.remove();
				}
				Sprite sprite = this.imageLoader[(gain.getSkill() + 82)];
				if (this.gains.size() > 1) {
					offsetY = currentIndex * 28;
				}
				if (gain.getY() < stop) {
					sprite.drawSprite(x + 15 - sprite.myWidth / 2, gain.getY() - 5 + offsetY + 75 - sprite.myHeight / 2, gain.getAlpha());
					this.smallText.method389(true, x + 35, 13395456, "+" + String.format("%,d", gain.getXP()) + "xp", gain.getY() + offsetY + 75);
				}
				currentIndex++;
			}
		}
	}

	private Sprite loadingBarFull;
	private Sprite loadingBarEmpty;

	/* Shops */
	private int currencyImageAmount = 12;
	private Sprite[] currencyImage = new Sprite[currencyImageAmount];
	private Sprite stock;
	private Sprite infinity;

	/* Announcements */
	private int announcementFading = 0;
	private int announcementMoving = clientWidth - 2;
	public boolean AnnouncementBarOn = true;

	/* Background */
	public Sprite backgroundFix;
	public Sprite realBackground;

	/* Clans */
	Sprite[] clanIcons = new Sprite[9];
	private final String[] clanTitles;

	/* Alert */
	public AlertHandler alertHandler;
	public Sprite alertBack;
	public Sprite alertBorder;
	public Sprite alertBorderH;

	/* Client settings */
	private static boolean newMenus = true;
	private static boolean newCursors = true;
	public static boolean newDamage = true;
	private static boolean newHPBars = true;
	public static boolean customHD = true;
	public static boolean enableHoverBox = true;
	public static boolean toggleHPOn = true;
	public static boolean newHitmarkers = false;
	public static boolean enableTweening = false;
	public static boolean enableBuffering = true;
	public static boolean depthTesting = false;//DO NOT ADD THIS TO ANY OF THE SETTINGS TAB!
	public static boolean showSkillOrbs = true;
	public static boolean enableBubbles = true;

	private void saveGameSettings() {
		try {
			File file = new File(Signlink.findcachedir() + "/GameSettings.dat");
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			out.writeBoolean(newMenus);
			out.writeBoolean(newCursors);
			out.writeBoolean(newDamage);
			out.writeBoolean(newHPBars);
			out.writeBoolean(customHD);
			out.writeBoolean(enableHoverBox);
			out.writeBoolean(toggleHPOn);
			out.writeBoolean(enableTweening);
			out.writeBoolean(enableBuffering);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadGameSettings() {
		try {
			File file = new File(Signlink.findcachedir() + "/GameSettings.dat");
			if (!file.exists()) {
				return;
			}
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			newMenus = in.readBoolean();
			newCursors = in.readBoolean();
			newDamage = in.readBoolean();
			newHPBars = in.readBoolean();
			customHD = in.readBoolean();
			enableHoverBox = in.readBoolean();
			toggleHPOn = in.readBoolean();
			enableTweening = in.readBoolean();
			enableBuffering = in.readBoolean();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveGoals(String name) {
		try {
			File file = new File(Signlink.findcachedir() + "/goals/" + name.trim().toLowerCase() + ".dat");
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			for (int index = 0; index < SkillConstants.goalData.length; index++) {
				out.writeInt(SkillConstants.goalData[index][0]);
				out.writeInt(SkillConstants.goalData[index][1]);
				out.writeInt(SkillConstants.goalData[index][2]);
				out.writeUTF(SkillConstants.goalType);
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadGoals(String name) {
		try {
			File file = new File(Signlink.findcachedir() + "/goals/" + name.trim().toLowerCase() + ".dat");
			if (!file.exists()) {
				for (int index = 0; index < SkillConstants.goalData.length; index++) {
					SkillConstants.goalData[index][0] = -1;
					SkillConstants.goalData[index][1] = -1;
					SkillConstants.goalData[index][2] = -1;
				}
				return;
			}
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			for (int index = 0; index < SkillConstants.goalData.length; index++) {
				SkillConstants.goalData[index][0] = in.readInt();
				SkillConstants.goalData[index][1] = in.readInt();
				SkillConstants.goalData[index][2] = in.readInt();
				SkillConstants.goalType = in.readUTF();
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadGameSettingsText() {
		if (!newHPBars)
			sendFrame126("Toggle HP Bar: @red@OFF", 45526);
		else
			sendFrame126("Toggle HP Bar: @gre@ON", 45526);
		if (!newDamage)
			sendFrame126("Toggle 10x Hits: @red@OFF", 45527);
		else
			sendFrame126("Toggle x10 Hits: @gre@ON", 45527);
		if (!newCursors)
			sendFrame126("Toggle Cursors: @red@OFF", 45528);
		else
			sendFrame126("Toggle Cursors: @gre@ON", 45528);
		if (!newMenus)
			sendFrame126("Toggle Menus: @red@OFF", 45529);
		else
			sendFrame126("Toggle Menus: @gre@ON", 45529);
		if (!customHD)
			sendFrame126("Toggle Custom HD: @red@OFF", 45530);
		else
			sendFrame126("Toggle Custom HD: @gre@ON", 45530);
		if (!enableHoverBox)
			sendFrame126("Toggle Hover Box: @red@OFF", 45531);
		else
			sendFrame126("Toggle Hover Box: @gre@ON", 45531);
		if (!toggleHPOn)
			sendFrame126("Toggle HP Above: @red@OFF", 45532);
		else
			sendFrame126("Toggle HP Above: @gre@ON", 45532);
		if (!enableTweening)
			sendFrame126("Toggle Tweening: @red@OFF", 47527);
		else
			sendFrame126("Toggle Tweening: @gre@ON", 47527);
		 if (!showSkillOrbs) {
		      sendFrame126("Toggle Skill Orbs: @red@OFF", 47529);
		    } else {
		      sendFrame126("Toggle Skill Orbs: @gre@ON", 47529);
		    }
		if (!enableBuffering) {
			sendFrame126("Toggle Buffering: @red@OFF", 47530);
		} else {
			sendFrame126("Toggle Buffering: @gre@ON", 47530);
		}
	}

	/** Cursors **/
	public static String cursorInfo[] = { "Walk-to", "Take", "Use", "Talk-to", "Open", "Net", "Bait", "Cage", "Harpoon", "Chop", "Bury", "Pray-at", "Mine", "Eat", "Drink", "Wield", "Wear", "Remove", "Attack", "Enter", "Exit", "Climb-up", "Climb-down", "Search", "Steal", "Smelt", "Clean", "Back", "Deposit Bank", "Inspect", "Pick", "Zoom",
			"Pointless", "Settings", "Pointless", "Pointless", "Accept", "Decline", "Cast Ice Barrage on", "Cast Blood Barrage on", "Cast Shadow Barrage on", "Cast Smoke Barrage on", "Cast Ice Blitz on", "Cast Blood Blitz on", "Cast Shadow Blitz on", "Cast Smoke Blitz on", "Cast Ice Burst on", "Cast Blood Burst on", "Cast Shadow Burst on",
			"Cast Smoke Burst on", "Cast Ice Rush on", "Cast Blood Rush on", "Cast Shadow Rush on", "Cast Smoke Rush on", "Link", "Split Private", "Graphics", "Audio", "Pointless", "Pointless", "Click", "Information", "Cast High level alchemy on", "Cast Low level alchemy on", "Value", "Select Starter", "Craft-rune", "Pointless", "Withdraw",
			"Slash", "Pull", "Browse Item Database", "Browse Quick", "Continue", "Toggle", };

	public static final String RandomAnnouncements = randomMessages();

	public static String randomMessages() {
		int maxNumber = 12;
		int randomNumber = (int) Math.floor(Math.random() * maxNumber);
		if (randomNumber == 0) {
			return "Fun Fact: Butterflies taste with their feet.";
		} else if (randomNumber == 1) {
			return "Fun Fact: The Hawaiian alphabet has 12 letters. ";
		} else if (randomNumber == 2) {
			return "Fun Fact: Honey is the only food that doesn't spoil.";
		} else if (randomNumber == 3) {
			return "Fun Fact: More people are killed annually by donkeys than die in aircrashes. ";
		} else if (randomNumber == 4) {
			return "Fun Fact: A Saudi Arabian woman can get a divorce if her husband doesn't give her coffee.";
		} else if (randomNumber == 5) {
			return "Fun Fact: Ketchup was sold in the 1830s as medicine";
		} else if (randomNumber == 6) {
			return "Fun Fact: It is physically impossible for pigs to look up into the sky.";
		} else if (randomNumber == 7) {
			return "Fun Fact: Hot water is heavier than cold.";
		} else if (randomNumber == 8) {
			return "Fun Fact: Guinea pigs and rabbits can't sweat.";
		} else if (randomNumber == 9) {
			return "Fun Fact: Sloths take two weeks to digest their food.";
		} else if (randomNumber == 10) {
			return "Fun Fact: You canï¿½ï¿½ï¿½t kill yourself by holding your breath ";
		} else if (randomNumber == 11) {
			return "Fun Fact: Your heart beats over 100,000 times a day.";
		}
		return "Please wait as we ";
	}

	public static String getAnnouncement() {
		return RandomAnnouncements;
	}

	public final String loyaltyRank(final int i) {
		switch (i) {
		case 1:
			return "Mod ";
		case 2:
			return "Admin ";
		case 3:
			return "Developer ";
		case 4:
			return "Ceo ";
		case 5:
			return "GFX ";
		case 6:
			return "Support ";
		case 20:
			return "Trusted Dicer ";
		case 100:
			return "Veteran ";
			/* Achievement Titles */
		case 7:
			return "Law men "; // LAW ABIDING CITIZEN
		case 8:
			return "Munchy "; // MUNCHIES
		case 9:
			return "Thirsty "; // MR THIRSTY
		case 10:
			return "Beast "; // BEAST
		case 11:
			return "Weak "; // WEAKLING
		case 12:
			return "Gamer "; // THE GAMER
		case 13:
			return "Duelist "; // THE DUELIST
		case 14:
			return "Viewer "; // THE VIEWER
		case 15:
			return "Trader "; // THE TRADER
		case 16:
			return "Selfie "; // Self-concious
		case 17:
			return "900 "; // Over 900
		case 18:
			return "Brainiac "; // Brainiac
		case 19:
			return "Holy "; // Holy Monk
		case 21:
			return "Home "; // HOME SICK
		case 22:
			return "Hunter "; // CORPOREAL HUNTER
		case 23:
			return "Baiter "; //Master Baither
		case 24:
			return "Crabs "; //Crabs
		case 25:
			return "TzTok "; // Jad Killer
		case 26:
			return "Nazi "; // NAZI
		case 27:
			return "Iron Chef "; // NAZI
		case 28:
			return "Blazed "; // BLAZED
		case 29:
			return "Oreo "; // Oreo
		case 31:
			return "Wowza "; // Wowza
		case 30:
			return "Mystic "; // Mystic
		case 50:
			return "Donator ";
		case 51:
			return "Super ";
		case 52:
			return "Extreme ";
		case 53:
			return "Elite ";
			/* Play Boy Custom */
		case 300:
			return "Sexy ";
		case 301:
			return "Wolf ";
		case 302:
			return "War-cheif ";
		case 303:
			return "Captain ";
		case 304:
			return "Commander ";
		case 305:
			return "The Real";
			
			/* Micheal Custom */
			
			/* Evan Custom */
			
			/* Bigshot Custom */
			
			/* Mod Maddie Custom */
		case 200:
			return "Meow ";
		case 201:
			return "Unitato ";
			
			/* Skyline Custom */
		case 202:
			return "Forum Mod ";
		case 203:
			return "Sexy ";
			
		default:
			return "";
		}
	}

	private static String getAmount(int i) {
		String s = String.valueOf(i);
		for (int k = s.length() - 3; k > 0; k -= 3)
			s = s.substring(0, k) + "," + s.substring(k);
		if (s.length() > 8)
			s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
		else if (s.length() > 4)
			s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
		return " " + s;
	}

	public final String amountString(int j) {
		if (j >= 0 && j < 10000)
			return String.valueOf(j);
		if (j >= 10000 && j < 10000000)
			return j / 1000 + "K";
		if (j >= 10000000 && j < 999999999)
			return j / 1000000 + "M";
		if (j >= 999999999)
			return "*";
		else
			return "?";
	}

	public static final byte[] ReadFile(String s) {
		try {
			byte abyte0[];
			File file = new File(s);
			int i = (int) file.length();
			abyte0 = new byte[i];
			DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
			datainputstream.readFully(abyte0, 0, i);
			datainputstream.close();
			return abyte0;
		} catch (Exception e) {
			System.out.println((new StringBuilder()).append("Read Error: ").append(s).toString());
			return null;
		}
	}

	private void stopMidi() {
		Signlink.midifade = 0;
		Signlink.midi = "stop";
	}

	private boolean menuHasAddFriend(int j) {
		if (j < 0)
			return false;
		int k = menuActionID[j];
		if (k >= 2000)
			k -= 2000;
		return k == 337;
	}

	public void drawChannelButtons(int xPosOffset, int yPosOffset) {
		imageLoader[32].drawSprite(5 + xPosOffset, 142 + yPosOffset);
		String text[] = { "On", "Friends", "Off", "Hide" };
		int textColor[] = { 65280, 0xffff00, 0xff0000, 65535 };
		int[] x = { 5, 62, 119, 176, 233, 290, 347, 404 };
		switch (cButtonCPos) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			chatButtons[1].drawSprite(x[cButtonCPos] + xPosOffset, 142 + yPosOffset);
			break;
		}
		if (cButtonHPos == cButtonCPos) {
			switch (cButtonHPos) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				chatButtons[0].drawSprite(x[cButtonHPos] + xPosOffset, 142 + yPosOffset);
				break;
			case 7:
				chatButtons[3].drawSprite(x[cButtonHPos] + xPosOffset, 142 + yPosOffset);
				break;
			}
		} else {
			switch (cButtonHPos) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				chatButtons[2].drawSprite(x[cButtonHPos] + xPosOffset, 142 + yPosOffset);
				break;
			case 7:
				chatButtons[3].drawSprite(x[cButtonHPos] + xPosOffset, 142 + yPosOffset);
				break;
			}
		}
		smallText.method382(0xffffff, x[0] + 28 + xPosOffset, "All", 157 + yPosOffset, true);
		smallText.method382(0xffffff, x[1] + 28 + xPosOffset, "Game", 152 + yPosOffset, true);
		smallText.method382(0xffffff, x[2] + 28 + xPosOffset, "Public", 152 + yPosOffset, true);
		smallText.method382(0xffffff, x[3] + 28 + xPosOffset, "Private", 152 + yPosOffset, true);
		smallText.method382(0xffffff, x[4] + 28 + xPosOffset, "Clan", 152 + yPosOffset, true);
		smallText.method382(0xffffff, x[5] + 28 + xPosOffset, "Trade", 152 + yPosOffset, true);
		smallText.method382(0xffffff, x[6] + 28 + xPosOffset, "Yell", 152 + yPosOffset, true);
		smallText.method382(0xffffff, x[7] + 55 + xPosOffset, "Screenshot", 157 + yPosOffset, true);
		smallText.method382(textColor[filterMessages ? 1 : 0], x[1] + 28 + xPosOffset, filterMessages ? "Filter" : "All", 163 + yPosOffset, true);
		smallText.method382(textColor[publicChatMode], x[2] + 28 + xPosOffset, text[publicChatMode], 163 + yPosOffset, true);
		smallText.method382(textColor[privateChatMode], x[3] + 28 + xPosOffset, text[privateChatMode], 163 + yPosOffset, true);
		smallText.method382(textColor[clanChatMode], x[4] + 28 + xPosOffset, text[clanChatMode], 163 + yPosOffset, true);
		smallText.method382(textColor[tradeMode], x[5] + 28 + xPosOffset, text[tradeMode], 163 + yPosOffset, true);
		smallText.method382(textColor[yellMode], x[6] + 28 + xPosOffset, text[yellMode], 163 + yPosOffset, true);
	}

	public String getPrefix(int rights) {
		switch (rights) {
		case 1:
			return "@cr1@";
		case 2:
			return "@cr2@";
		case 3:
			return "@cr3@";
		case 4:
			return "@cr4@";
		case 5:
			return "@cr5@";
		case 6:
			return "@cr6@";
		case 7:
			return "@cr7@";
			
		default:
			return "";
		}
	}

	public int getPrefixRights(String prefix) {
		if (prefix.equals("@cr1@")) {
			return 1;
		} else if (prefix.equals("@cr2@")) {
			return 2;
		} else if (prefix.equals("@cr3@")) {
			return 3;
		} else if (prefix.equals("@cr4@")) {
			return 4;
		} else if (prefix.equals("@cr5@")) {
			return 5;
		} else if (prefix.equals("@cr6@")) {
			return 6;
		} else if (prefix.equals("@cr7@")) {
			return 7;
		}
		return 0;
	}

	public void hitmarkDraw(int hitLength, int type, int icon, int damage, int move, int opacity) {
		if (damage > 0) {
			Sprite end1 = null, middle = null, end2 = null;
			int x = 0;
			switch (hitLength) {
			case 1:
				x = 8;
				break;
			case 2:
				x = 4;
				break;
			case 3:
				x = 1;
				break;
			}
			switch (type) {
			case 0:
				end1 = hitMark[0];
				middle = hitMark[1];
				end2 = hitMark[2];
				break;
			case 1:
				end1 = hitMark[3];
				middle = hitMark[4];
				end2 = hitMark[5];
				break;
			case 2:
				end1 = hitMark[6];
				middle = hitMark[7];
				end2 = hitMark[8];
				break;
			}
			if (type <= 1 || icon != -1)
				hitIcon[icon].drawTransparentSprite(spriteDrawX - 34 + x, spriteDrawY - 14 + move, opacity);
			end1.drawTransparentSprite(spriteDrawX - 12 + x, spriteDrawY - 12 + move, opacity);
			x += 4;
			for (int i = 0; i < hitLength * 2; i++) {
				middle.drawTransparentSprite(spriteDrawX - 12 + x, spriteDrawY - 12 + move, opacity);
				x += 4;
			}
			end2.drawTransparentSprite(spriteDrawX - 12 + x, spriteDrawY - 12 + move, opacity);
			if (opacity > 100)
				(type == 1 ? bigHit : smallHit).drawText(0xffffff, String.valueOf(damage), spriteDrawY + (type == 1 ? 2 : 32) + move, spriteDrawX + 4);
		} else {
			Sprite block = new Sprite("/Hits/block");
			block.drawTransparentSprite(spriteDrawX - 12, spriteDrawY - 14 + move, opacity);
		}
	}
	
	public void tabToReplyPm() {
		String name = null;
		for(int k = 0; k < 500; k++) {
		    if(chatMessages[k] == null) {
		    	continue;
		    }
		    int l = chatTypes[k];
		    if(l == 3 || l == 7) {
				name = chatNames[k];
				break;
		    }
		}
		if(name == null || name.length() <= 0) {
		    pushMessage("You haven't received any messages to which you can reply.", 0, "");
		    return;
		}
		if(name.contains("@")) {
		    name = name.substring(5);
		}
		long nameAsLong = TextClass.longForName(name.trim());
		int k3 = -1;
		for(int i4 = 0; i4 < friendsCount; i4++) {
		    if(friendsListAsLongs[i4] != nameAsLong)
			continue;
		    k3 = i4;
		    break;
		}
		if(k3 != -1) {
		    if(friendsNodeIDs[k3] > 0)  {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 3;
				aLong953 = friendsListAsLongs[k3];
				aString1121 = "Enter message to send to " + friendsList[k3];
		    } else {
		    	pushMessage("That player is currently offline.", 0, "");
		    }
		}
	}

	public void drawChatArea() {
		int offsetX = 0;
		int offsetY = clientSize != 0 ? clientHeight - 165 : 0;
		if (clientSize == 0) {
			aRSImageProducer_1166.setCanvas();
		}
		Rasterizer.lineOffsets = anIntArray1180;
		TextDrawingArea textDrawingArea = aTextDrawingArea_1271;
		if (showChat) {
			if (clientSize == 0) {
				imageLoader[0].drawSprite(0 + offsetX, 0 + offsetY);
			} else {
				DrawingArea.drawAlphaGradient(7, offsetY - 8, 506, 130, 0, 0, 70);
				DrawingArea.drawLine(121 + offsetY, 0x6d6a57, 505, 7);
				DrawingArea.drawLine(6 + offsetY, 0x6d6a57, 505, 7);
			}
		}
		drawChannelButtons(offsetX, offsetY);
		if (messagePromptRaised) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
			newFancyFont.drawCenteredString(aString1121, 259 + offsetX, 60 + offsetY, 0, -1);
			newFancyFont.drawCenteredString(promptInput + "*", 259 + offsetX, 80 + offsetY, 128, -1);
		} else if (inputDialogState == 1) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
			newFancyFont.drawCenteredString("Enter amount:", 259 + offsetX, 60 + offsetY, 0, -1);
			newFancyFont.drawCenteredString(amountOrNameInput + "*", 259 + offsetX, 80 + offsetY, 128, -1);
		} else if (inputDialogState == 7) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
			newFancyFont.drawCenteredString("Enter New Passowrd:", 259 + offsetX, 60 + offsetY, 0, -1);
			newFancyFont.drawCenteredString(amountOrNameInput + "*", 259 + offsetX, 80 + offsetY, 128, -1);

		} else if (inputDialogState == 2) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
			newBoldFont.drawCenteredString("Enter name:", 259 + offsetX, 60 + offsetY, 0, -1);
			newBoldFont.drawCenteredString(amountOrNameInput + "*", 259 + offsetX, 80 + offsetY, 128, -1);
		} else if (aString844 != null) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
			newFancyFont.drawCenteredString(aString844, 259 + offsetX, 60 + offsetY, 0, -1);
			newFancyFont.drawCenteredString("Click to continue", 259 + offsetX, 80 + offsetY, 128, -1);
		} else if (backDialogID != -1) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
			drawInterface(0, 20 + offsetX, RSInterface.interfaceCache[backDialogID], 20 + offsetY);
		} else if (dialogID != -1) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
			drawInterface(0, 20 + offsetX, RSInterface.interfaceCache[dialogID], 20 + offsetY);
		} else if (!quickChat && showChat) {
			int messageY = -3;
			int scrollPosition = 0;
			DrawingArea.setDrawingArea(121 + offsetY, 8 + offsetX, 512 + offsetX, 7 + offsetY);
			messages : for (int index = 0; index < 500; index++)
				if (chatMessages[index] != null) {
					int chatType = chatTypes[index];
					int positionY = (70 - messageY * 14) + anInt1089 + 6;
					String name = chatNames[index];
					final String time = "[" + "" + "]";
					byte playerRights = 0;
					if (name != null && name.startsWith("@cr1@")) {
						name = name.substring(5);
						playerRights = 1;
					} else if (name != null && name.startsWith("@cr2@")) {
						name = name.substring(5);
						playerRights = 2;
					} else if (name != null && name.startsWith("@cr3@")) {
						name = name.substring(5);
						playerRights = 3;
					} else if (name != null && name.startsWith("@cr4@")) {
						name = name.substring(5);
						playerRights = 4;
					} else if (name != null && name.startsWith("@cr5@")) {
						name = name.substring(5);
						playerRights = 5;
					} else if (name != null && name.startsWith("@cr6@")) {
						name = name.substring(5);
						playerRights = 6;
					} else if (name != null && name.startsWith("@cr7@")) {
						name = name.substring(5);
						playerRights = 7;
					} else if (name != null && name.startsWith("@cr8@")) {
						name = name.substring(5);
						playerRights = 8;
					} else if (name != null && name.startsWith("@cr9@")) {
						name = name.substring(5);
						playerRights = 9;
					} else if (name != null && name.startsWith("@cr10@")) {
						name = name.substring(6);
						playerRights = 10;
					}
					if (chatType == 0) {
						if (chatTypeView == 5 || chatTypeView == 0) {
							boolean flag = false;
							for (int Fmessage = 0; Fmessage < filteredMessages.length; Fmessage++) {
								if (chatMessages[index].startsWith(filteredMessages[Fmessage]) && filterMessages)
									flag = true;
							}
							
							if (!flag) {
								if (positionY > 0 && positionY < 210) {
									int xPos = 11;
									newNormalFont.drawBasicString(chatMessages[index], xPos + offsetX, positionY + offsetY, clientSize == 0 ? 0 : 0xffffff, clientSize == 0 ? -1 : 0);
								}
								scrollPosition++;
								messageY++;
							}
						}
					}
					if ((chatType == 1 || chatType == 2) && (chatType == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(name))) {
						if (chatTypeView == 1 || chatTypeView == 0 || (playerRights > 0 && playerRights <= 4)) {
							if (positionY > 0 && positionY < 210) {
								int xPos = 11;
								switch (playerRights) {
								case 1:
									modIcons[0].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 2:
									modIcons[1].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 3:
									modIcons[2].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
								case 4:
									modIcons[3].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 5:
									modIcons[4].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 6:
									modIcons[5].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 7:
									modIcons[6].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
									//new omar
									
								case 8:
									modIcons[7].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
								case 9:
									modIcons[8].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
								case 10:
									modIcons[9].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
									
								}
								newNormalFont.drawBasicString(name + ":", xPos + offsetX, positionY + offsetY, clientSize == 0 ? 0 : 0xffffff, clientSize == 0 ? -1 : 0);
								xPos += newRegularFont.getTextWidth(name) + 7;
								newNormalFont.drawBasicString(chatMessages[index], xPos + offsetX - 2, positionY + offsetY, clientSize == 0 ? 250 : 0x7FA9FF, clientSize == 0 ? -1 : 0);
							}
							scrollPosition++;
							messageY++;
						}
					}
					if ((chatType == 3 || chatType == 7) && (splitPrivateChat == 0 || chatTypeView == 2) && (chatType == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(name))) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							if (positionY > 0 && positionY < 210) {
								int xPos = 11;
								switch (playerRights) {
								case 1:
									modIcons[0].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 2:
									modIcons[1].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 3:
									modIcons[2].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
								case 4:
									modIcons[3].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 5:
									modIcons[4].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 6:
									modIcons[5].drawSprite(xPos + 1 + offsetX, positionY - 12 + offsetY);
									xPos += 14;
									break;
								case 7:
									modIcons[6].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
									//new omar
									
								case 8:
									modIcons[7].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
								case 9:
									modIcons[8].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
								case 10:
									modIcons[9].drawSprite(xPos + 1 + offsetX, positionY - 11 + offsetY);
									xPos += 14;
									break;
									
								}
								newRegularFont.drawBasicString("From", xPos + offsetX, positionY + offsetY, isFixed() ? 0 : 0xffffff, isFixed() ? -1 : 0);
								xPos += newRegularFont.getTextWidth("From ");
								RSFont font = playerRights != 0 ? newRegularFont : newRegularFont;
								font.drawBasicString(name + ":", xPos + offsetX, positionY + offsetY, isFixed() ? 0 : 0xffffff, isFixed() ? -1 : 0);
								xPos += font.getTextWidth(name) + 8;
								newRegularFont.drawBasicString(chatMessages[index], xPos + offsetX, positionY + offsetY, isFixed() ? 0x800000 : 0xFF5256, isFixed() ? -1 : 0);
							}
							scrollPosition++;
							messageY++;
						}
					}
					if (chatType == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(name))) {
						if (chatTypeView == 3 || chatTypeView == 0) {
							if (positionY > 0 && positionY < 210) {
								newRegularFont.drawBasicString(name + " " + chatMessages[index], 11 + offsetX, positionY + offsetY, clientSize == 0 ? 0x800080 : 0xFF00D4, clientSize == 0 ? -1 : 0);
							}
							scrollPosition++;
							messageY++;
						}
					}
					if (chatType == 5 && splitPrivateChat == 0 && privateChatMode < 2) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							if (positionY > 0 && positionY < 210)
								newRegularFont.drawBasicString(chatMessages[index], 11 + offsetX, positionY + offsetY, clientSize == 0 ? 0x800000 : 0xFF5256, clientSize == 0 ? -1 : 0);
							scrollPosition++;
							messageY++;
						}
					}
					if (chatType == 6 && (splitPrivateChat == 0 || chatTypeView == 2) && privateChatMode < 2) {
						if (chatTypeView == 2 || chatTypeView == 0) {
							if (positionY > 0 && positionY < 210) {
								newRegularFont.drawBasicString("To " + name + ":", 11 + offsetX, positionY + offsetY, clientSize == 0 ? 0 : 0xffffff, clientSize == 0 ? -1 : 0);
								newRegularFont.drawBasicString(chatMessages[index], 15 + newRegularFont.getTextWidth("To :" + name) + offsetX + offsetX, positionY + offsetY, clientSize == 0 ? 0x800000 : 0xFF5256, clientSize == 0 ? -1 : 0);
							}
							scrollPosition++;
							messageY++;
						}
					}
					if (chatType == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(name))) {
						if (chatTypeView == 3 || chatTypeView == 0) {
							if (positionY > 0 && positionY < 210)
								textDrawingArea.method385(0x7e3200, name + " " + chatMessages[index], positionY + offsetY, 11 + offsetX);
							scrollPosition++;
							messageY++;
						}
					}
					if (chatType == 11 && (clanChatMode == 0)) {
						if (chatTypeView == 11) {
							if (positionY > 0 && positionY < 210)
								textDrawingArea.method385(0x7e3200, name + " " + chatMessages[index], positionY, 11);
							scrollPosition++;
							messageY++;
						}
					}
					if (chatType == 12) {
						if (chatTypeView == 11 || chatTypeView == 0) {
							if (positionY > 3 && positionY < 130) {
								int positionX = 11;
								String title = "<col=0000FF>" + clanTitles[index] + "</col>";
								String username = (chatRights[index] > 0 ? "<img=" + (chatRights[index] - 1) + ">" : "") + TextClass.fixName(chatNames[index]);
								String message = "<col=800000>" + chatMessages[index] + "</col>";
								newRegularFont.drawBasicString("[" + title + "] " + username + ": " + message, positionX, positionY + offsetY, clientSize == 0 ? 0 : 0xffffff, clientSize == 0 ? -1 : 0);
							}
							scrollPosition++;
							messageY++;
						}
					}
				}
			DrawingArea.defaultDrawingAreaSize();
			anInt1211 = scrollPosition * 14 + 7 + 5;
			if (anInt1211 < 111) {
				anInt1211 = 111;
			}
			if (clientSize == 0)
				drawScrollbar(114, anInt1211 - anInt1089 - 113, 7, 496, anInt1211);
			else
				drawTransparentScrollBar(114, anInt1211 - anInt1089 - 113, 7 + offsetY, 497, anInt1211);
			String name;
			if (myPlayer != null && myPlayer.name != null) {
				name = myPlayer.name;
			} else {
				name = TextClass.fixName(myUsername);
			}
			switch (myPrivilege) {
			case 1:
				modIcons[0].drawSprite(12 + offsetX, 122 + offsetY);
				offsetX += 14;
				break;
			case 2:
				modIcons[1].drawSprite(12 + offsetX, 122 + offsetY);
				offsetX += 14;
				break;
			case 3:
				newModIcons[0].drawSprite(12 + offsetX, 122 + offsetY);
				offsetX += 14;
				break;
			case 4:
				modIcons[3].drawSprite(12 + offsetX, 122 + offsetY);
				offsetX += 14;
				break;
			case 5:
				modIcons[4].drawSprite(12 + offsetX, 122 + offsetY);
				offsetX += 14;
				break;
			case 6:
				modIcons[5].drawSprite(12 + offsetX, 122 + offsetY);
				offsetX += 14;
				break;
			case 7:
				modIcons[6].drawSprite(12 + offsetX, 121 + offsetY);
				offsetX += 14;
				break;
				//new omar
				
			case 8:
				modIcons[7].drawSprite(12 + offsetX, 121 + offsetY);
				offsetX += 14;
				break;
			case 9:
				modIcons[8].drawSprite(12 + offsetX, 121 + offsetY);
				offsetX += 14;
				break;
			case 10:
				modIcons[9].drawSprite(12 + offsetX, 121 + offsetY);
				offsetX += 14;
				break;
				
			}
			if (muteReason.length() > 0) {
				normalFont.method389(clientSize == 0 ? false : true, 11 + offsetX, clientSize == 0 ? 0 : 0xffffff, "You are currently muted. Reason: " + muteReason + ".", 133 + offsetY);
			} else {
				DrawingArea.setDrawingArea(140 + offsetY, 8, 509, 120 + offsetY);
				normalFont.method389(clientSize == 0 ? false : true, 11 + offsetX, clientSize == 0 ? 0 : 0xffffff, name, 133 + offsetY);
				imageLoader[75].drawSprite(textDrawingArea.getTextWidth(name) + 11 + offsetX, 123 + offsetY);
				normalFont.method389(clientSize == 0 ? false : true, textDrawingArea.getTextWidth(name) + 24 + offsetX, clientSize == 0 ? 0 : 0xffffff, ": ", 133 + offsetY);
				newNormalFont.drawBasicString(inputString + "*", 20 + newRegularFont.getTextWidth(name + ": ") + offsetX - -3, 133 + offsetY, clientSize == 0 ? 255 : 0x7FA9FF, clientSize == 0 ? -1 : 0);
				DrawingArea.defaultDrawingAreaSize();
			}
			if (clientSize == 0)
				DrawingArea.method339(121 + offsetY, clientSize == 0 ? 0x807660 : 0xffffff, 505, 7);
		} else if (quickChat) {
			imageLoader[27].drawSprite(0 + offsetX, 0 + offsetY);
		}
		if (isFixed()) {
			if (menuOpen)
				drawMenu(0, 338);
		} else {
			if (menuOpen && menuScreenArea == 2) {
				drawMenu();
			}
		}
		if (clientSize == 0) {
			aRSImageProducer_1166.drawGraphics(338, super.graphics, 0);
		}
		aRSImageProducer_1165.setCanvas();
		Rasterizer.lineOffsets = anIntArray1182;
	}
	
	public void init() {
		try {
			appletWidth = Integer.parseInt(getParameter("width"));
			appletHeight = Integer.parseInt(getParameter("height"));
			nodeID = 10;
			portOff = 0;
			setHighMem();
			isMembers = true;
			Signlink.startpriv(InetAddress.getByName(server));
			instance = this;
			clientSize = 0;
			clientWidth = 765;
			clientHeight = 503;
			gameAreaWidth = 512;
			gameAreaHeight = 334;
			initClientFrame(appletWidth, appletHeight);
		} catch (Exception exception) {
			return;
		}
	}

	public void startRunnable(Runnable runnable, int i) {
		if (i > 10)
			i = 10;
		if (Signlink.mainapp != null) {
			Signlink.startthread(runnable, i);
		} else {
			super.startRunnable(runnable, i);
		}
	}

	public Socket openSocket(int port) throws IOException {
		return new Socket(InetAddress.getByName(server), port);
	}

	private boolean processMenuClick() {
		if (activeInterfaceType != 0)
			return false;
		int j = super.clickMode3;
		if (spellSelected == 1 && super.saveClickX >= 503 && super.saveClickY >= 160 && super.saveClickX <= 765 && super.saveClickY <= 205)
			j = 0;
		if (menuOpen) {
			if (j != 1) {
				int k = super.mouseX;
				int j1 = super.mouseY;
				if (menuScreenArea == 0) {
					k -= 4;
					j1 -= 4;
				}
				if (menuScreenArea == 1) {
					k -= 519;
					j1 -= 168;
				}
				if (menuScreenArea == 2) {
					k -= 5;
					j1 -= 338;
				}
				if (menuScreenArea == 3) {
					k -= 516;
					j1 -= 0;
				}
				if (k < menuOffsetX - 10 || k > menuOffsetX + menuWidth + 10 || j1 < menuOffsetY - 10 || j1 > menuOffsetY + menuHeight + 10) {
					menuOpen = false;
					if (menuScreenArea == 1)
						if (menuScreenArea == 2)
							inputTaken = true;
				}
			}
			if (j == 1) {
				int l = menuOffsetX;
				int k1 = menuOffsetY;
				int i2 = menuWidth;
				int k2 = super.saveClickX;
				int l2 = super.saveClickY;
				if (menuScreenArea == 0) {
					k2 -= 4;
					l2 -= 4;
				}
				if (menuScreenArea == 1) {
					k2 -= 519;
					l2 -= 168;
				}
				if (menuScreenArea == 2) {
					k2 -= 5;
					l2 -= 338;
				}
				if (menuScreenArea == 3) {
					k2 -= 519;
					l2 -= 0;
				}
				int i3 = -1;
				for (int j3 = 0; j3 < menuActionRow; j3++) {
					int k3 = k1 + 31 + (menuActionRow - 1 - j3) * 15;
					if (k2 > l && k2 < l + i2 && l2 > k3 - 13 && l2 < k3 + 3)
						i3 = j3;
				}
				System.out.println(i3);
				if (i3 != -1)
					doAction(i3);
				menuOpen = false;
				if (menuScreenArea == 1)
					if (menuScreenArea == 2) {
						inputTaken = true;
					}
			}
			return true;
		} else {
			if (j == 1 && menuActionRow > 0) {
				int i1 = menuActionID[menuActionRow - 1];
				if (i1 == 632 || i1 == 78 || i1 == 867 || i1 == 431 || i1 == 53 || i1 == 74 || i1 == 454 || i1 == 539 || i1 == 493 || i1 == 847 || i1 == 447 || i1 == 1125) {
					int l1 = menuActionCmd2[menuActionRow - 1];
					int j2 = menuActionCmd3[menuActionRow - 1];
					RSInterface class9 = RSInterface.interfaceCache[j2];
					if (class9.aBoolean259 || class9.aBoolean235) {
						aBoolean1242 = false;
						anInt989 = 0;
						interfaceId = j2;
						itemFrom = l1;
						activeInterfaceType = 2;
						anInt1087 = super.saveClickX;
						anInt1088 = super.saveClickY;
						if (RSInterface.interfaceCache[j2].parentID == openInterfaceID)
							activeInterfaceType = 1;
						if (RSInterface.interfaceCache[j2].parentID == backDialogID)
							activeInterfaceType = 3;
						return true;
					}
				}
			}
			if (j == 1 && (anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2)
				j = 2;
			if (j == 1 && menuActionRow > 0)
				doAction(menuActionRow - 1);
			if (j == 2 && menuActionRow > 0)
				determineMenuSize();
			return false;
		}

	}

	public static int totalRead = 0;

	public static String getFileNameWithoutExtension(String fileName) {
		File tmpFile = new File(fileName);
		tmpFile.getName();
		int whereDot = tmpFile.getName().lastIndexOf('.');
		if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2) {
			return tmpFile.getName().substring(0, whereDot);
		}
		return "";
	}

	private void saveMidi(boolean flag, byte abyte0[]) {
		Signlink.midifade = flag ? 1 : 0;
		Signlink.midisave(abyte0, abyte0.length);
	}

	private void method22() {
		try {
			anInt985 = -1;
			aClass19_1056.removeAll();
			aClass19_1013.removeAll();
			Rasterizer.clearTextureCount();
			unlinkMRUNodes();
			worldController.initToNull();
			System.gc();
			for (int i = 0; i < 4; i++)
				aClass11Array1230[i].method210();

			for (int l = 0; l < 4; l++) {
				for (int k1 = 0; k1 < 104; k1++) {
					for (int j2 = 0; j2 < 104; j2++)
						byteGroundArray[l][k1][j2] = 0;

				}

			}

			ObjectManager objectManager = new ObjectManager(byteGroundArray, intGroundArray);
			int k2 = aByteArrayArray1183.length;
			buffer.createFrame(0);
			if (!aBoolean1159) {
				for (int i3 = 0; i3 < k2; i3++) {
					int i4 = ((anIntArray1234[i3] >> 8) << 6) - baseX;
					int k5 = ((anIntArray1234[i3] & 0xff) << 6) - baseY;
					byte abyte0[] = aByteArrayArray1183[i3];
					if (abyte0 != null)
						objectManager.method180(abyte0, k5, i4, (anInt1069 - 6) * 8, (anInt1070 - 6) * 8, aClass11Array1230);
				}

				for (int j4 = 0; j4 < k2; j4++) {
					int l5 = ((anIntArray1234[j4] >> 8) << 6) - baseX;
					int k7 = ((anIntArray1234[j4] & 0xff) << 6) - baseY;
					byte abyte2[] = aByteArrayArray1183[j4];
					if (abyte2 == null && anInt1070 < 800)
						objectManager.method174(k7, 64, 64, l5);
				}

				anInt1097++;
				if (anInt1097 > 160) {
					anInt1097 = 0;
					buffer.createFrame(238);
					buffer.writeWordBigEndian(96);
				}
				buffer.createFrame(0);
				for (int i6 = 0; i6 < k2; i6++) {
					byte abyte1[] = aByteArrayArray1247[i6];
					if (abyte1 != null) {
						int l8 = ((anIntArray1234[i6] >> 8) << 6) - baseX;
						int k9 = ((anIntArray1234[i6] & 0xff) << 6) - baseY;
						objectManager.method190(l8, aClass11Array1230, k9, worldController, abyte1);
					}
				}

			}
			if (aBoolean1159) {
				for (int j3 = 0; j3 < 4; j3++) {
					for (int k4 = 0; k4 < 13; k4++) {
						for (int j6 = 0; j6 < 13; j6++) {
							int l7 = anIntArrayArrayArray1129[j3][k4][j6];
							if (l7 != -1) {
								int i9 = l7 >> 24 & 3;
								int l9 = l7 >> 1 & 3;
								int j10 = l7 >> 14 & 0x3ff;
								int l10 = l7 >> 3 & 0x7ff;
								int j11 = (j10 / 8 << 8) + l10 / 8;
								for (int l11 = 0; l11 < anIntArray1234.length; l11++) {
									if (anIntArray1234[l11] != j11 || aByteArrayArray1183[l11] == null)
										continue;
									objectManager.method179(i9, l9, aClass11Array1230, k4 * 8, (j10 & 7) * 8, aByteArrayArray1183[l11], (l10 & 7) * 8, j3, j6 * 8);
									break;
								}

							}
						}

					}

				}

				for (int l4 = 0; l4 < 13; l4++) {
					for (int k6 = 0; k6 < 13; k6++) {
						int i8 = anIntArrayArrayArray1129[0][l4][k6];
						if (i8 == -1)
							objectManager.method174(k6 * 8, 8, 8, l4 * 8);
					}

				}

				buffer.createFrame(0);
				for (int l6 = 0; l6 < 4; l6++) {
					for (int j8 = 0; j8 < 13; j8++) {
						for (int j9 = 0; j9 < 13; j9++) {
							int i10 = anIntArrayArrayArray1129[l6][j8][j9];
							if (i10 != -1) {
								int k11 = i10 >> 14 & 0x3ff;
								int i12 = i10 >> 3 & 0x7ff;
								int j12 = (k11 / 8 << 8) + i12 / 8;
								for (int k12 = 0; k12 < anIntArray1234.length; k12++) {
									if (anIntArray1234[k12] != j12 || aByteArrayArray1247[k12] == null)
										continue;
								}

							}
						}

					}

				}

			}
			buffer.createFrame(0);
			objectManager.method171(aClass11Array1230, worldController);
			aRSImageProducer_1165.setCanvas();
			buffer.createFrame(0);
			int k3 = ObjectManager.anInt145;
			if (k3 > plane)
				k3 = plane;
			if (k3 < plane - 1)
				k3 = plane - 1;
			if (lowMem)
				worldController.method275(ObjectManager.anInt145);
			else
				worldController.method275(0);
			for (int i5 = 0; i5 < 104; i5++) {
				for (int i7 = 0; i7 < 104; i7++)
					spawnGroundItem(i5, i7);

			}

			anInt1051++;
			if (anInt1051 > 98) {
				anInt1051 = 0;
				buffer.createFrame(150);
			}
			method63();
		} catch (Exception exception) {
		}
		ObjectDefinition.mruNodes1.unlinkAll();
		if (super.mainFrame != null) {
			buffer.createFrame(210);
			buffer.writeDWord(0x3f008edd);
		}
		if (lowMem && Signlink.cache_dat != null) {
			int j = onDemandFetcher.getModelCount();
			for (int i1 = 0; i1 < j; i1++) {
				int l1 = onDemandFetcher.getModelIndex(i1);
				if ((l1 & 0x79) == 0)
					Model.method461(i1);
			}

		}
		System.gc();
		Rasterizer.resetTextures();
		onDemandFetcher.method566();
		int k = (anInt1069 - 6) / 8 - 1;
		int j1 = (anInt1069 + 6) / 8 + 1;
		int i2 = (anInt1070 - 6) / 8 - 1;
		int l2 = (anInt1070 + 6) / 8 + 1;
		if (aBoolean1141) {
			k = 49;
			j1 = 50;
			i2 = 49;
			l2 = 50;
		}
		for (int l3 = k; l3 <= j1; l3++) {
			for (int j5 = i2; j5 <= l2; j5++)
				if (l3 == k || l3 == j1 || j5 == i2 || j5 == l2) {
					int j7 = onDemandFetcher.method562(0, j5, l3);
					if (j7 != -1)
						onDemandFetcher.method560(j7, 3);
					int k8 = onDemandFetcher.method562(1, j5, l3);
					if (k8 != -1)
						onDemandFetcher.method560(k8, 3);
				}
		}
	}

	private void unlinkMRUNodes() {
		ObjectDefinition.mruNodes1.unlinkAll();
		ObjectDefinition.mruNodes2.unlinkAll();
		EntityDefinition.mruNodes.unlinkAll();
		ItemDefinition.modelcache.unlinkAll();
		ItemDefinition.iconcache.unlinkAll();
		Player.mruNodes.unlinkAll();
		SpotAnimDefinition.aMRUNodes_415.unlinkAll();
	}

	private void method24(int i) {
		int ai[] = aClass30_Sub2_Sub1_Sub1_1263.myPixels;
		int j = ai.length;
		for (int k = 0; k < j; k++)
			ai[k] = 0;

		for (int l = 1; l < 103; l++) {
			int i1 = 24628 + (103 - l) * 512 * 4;
			for (int k1 = 1; k1 < 103; k1++) {
				if ((byteGroundArray[i][k1][l] & 0x18) == 0)
					worldController.method309(ai, i1, i, k1, l);
				if (i < 3 && (byteGroundArray[i + 1][k1][l] & 8) != 0)
					worldController.method309(ai, i1, i + 1, k1, l);
				i1 += 4;
			}

		}

		int j1 = 0xFFFFFF;
		int l1 = 0xee0000;
		aClass30_Sub2_Sub1_Sub1_1263.method343();
		for (int i2 = 1; i2 < 103; i2++) {
			for (int j2 = 1; j2 < 103; j2++) {
				if ((byteGroundArray[i][j2][i2] & 0x18) == 0)
					method50(i2, j1, j2, l1, i);
				if (i < 3 && (byteGroundArray[i + 1][j2][i2] & 8) != 0)
					method50(i2, j1, j2, l1, i + 1);
			}

		}

		aRSImageProducer_1165.setCanvas();
		anInt1071 = 0;
		for (int k2 = 0; k2 < 104; k2++) {
			for (int l2 = 0; l2 < 104; l2++) {
				int i3 = worldController.method303(plane, k2, l2);
				if (i3 != 0) {
					i3 = i3 >> 14 & 0x7fff;
					int j3 = ObjectDefinition.forID(i3).anInt746;
					if (j3 >= 0) {
						int k3 = k2;
						int l3 = l2;
						aClass30_Sub2_Sub1_Sub1Array1140[anInt1071] = mapFunctions[j3];
						anIntArray1072[anInt1071] = k3;
						anIntArray1073[anInt1071] = l3;
						anInt1071++;
					}
				}
			}

		}
	}

	public void setNorth() {
		anInt1278 = 0;
		anInt1131 = 0;
		anInt896 = 0;
		minimapInt2 = 0;
		minimapInt3 = 0;
		minimapInt1 = 0;
	}

	private void spawnGroundItem(int i, int j) {
		NodeList class19 = groundArray[plane][i][j];
		if (class19 == null) {
			worldController.method295(plane, i, j);
			return;
		}
		int k = 0xfa0a1f01;
		Object obj = null;
		for (Item item = (Item) class19.reverseGetFirst(); item != null; item = (Item) class19.reverseGetNext()) {
			ItemDefinition itemDefinition = ItemDefinition.forID(item.ID);
			int l = itemDefinition.value;
			if (itemDefinition.stackable)
				l *= item.anInt1559 + 1;
			if (l > k) {
				k = l;
				obj = item;
			}
		}

		class19.insertTail(((Node) (obj)));
		Object obj1 = null;
		Object obj2 = null;
		for (Item class30_sub2_sub4_sub2_1 = (Item) class19.reverseGetFirst(); class30_sub2_sub4_sub2_1 != null; class30_sub2_sub4_sub2_1 = (Item) class19.reverseGetNext()) {
			if (class30_sub2_sub4_sub2_1.ID != ((Item) (obj)).ID && obj1 == null)
				obj1 = class30_sub2_sub4_sub2_1;
			if (class30_sub2_sub4_sub2_1.ID != ((Item) (obj)).ID && class30_sub2_sub4_sub2_1.ID != ((Item) (obj1)).ID && obj2 == null)
				obj2 = class30_sub2_sub4_sub2_1;
		}

		int i1 = i + (j << 7) + 0x60000000;
		worldController.method281(i, i1, ((Animable) (obj1)), method42(plane, (j << 7) + 64, (i << 7) + 64), ((Animable) (obj2)), ((Animable) (obj)), plane, j);
	}

	private void method26(boolean flag) {
		for (int j = 0; j < npcCount; j++) {
			Npc npc = npcArray[npcIndices[j]];
			int k = 0x20000000 + (npcIndices[j] << 14);
			if (npc == null || !npc.isVisible() || npc.desc.aBoolean93 != flag)
				continue;
			int l = npc.x >> 7;
			int i1 = npc.y >> 7;
			if (l < 0 || l >= 104 || i1 < 0 || i1 >= 104)
				continue;
			if (npc.anInt1540 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
				if (anIntArrayArray929[l][i1] == anInt1265)
					continue;
				anIntArrayArray929[l][i1] = anInt1265;
			}
			if (!npc.desc.aBoolean84)
				k += 0x80000000;
			worldController.method285(plane, npc.anInt1552, method42(plane, npc.y, npc.x), k, npc.y, ((npc.anInt1540 - 1) << 6) + 60, npc.x, npc, npc.aBoolean1541);
		}
	}

	public void drawHoverBox(int xPos, int yPos, String text) {
		String[] results = text.split("\n");
		int height = (results.length * 16) + 6;
		int width;
		width = smallText.getTextWidth(results[0]) + 6;
		for (int i = 1; i < results.length; i++)
			if (width <= smallText.getTextWidth(results[i]) + 6)
				width = smallText.getTextWidth(results[i]) + 6;
		DrawingArea.drawPixels(height, yPos, xPos, 0xFFFFA0, width);
		DrawingArea.fillPixels(xPos, width, height, 0, yPos);
		yPos += 14;
		for (int i = 0; i < results.length; i++) {
			smallText.method389(false, xPos + 3, 0, results[i], yPos);
			yPos += 16;
		}
	}

	public void drawTooltipBox(String inputText, int xPos, int yPos, int color) {
		String[] lineSplit = inputText.split("\n");
		int height = (lineSplit.length * 16) + 6;
		int width;
		width = newRegularFont.getTextWidth(lineSplit[0]) + 6;
		for (int textWidth = 1; textWidth < lineSplit.length; textWidth++) {
			if (width <= newRegularFont.getTextWidth(lineSplit[textWidth]) + 6) {
				width = newRegularFont.getTextWidth(lineSplit[textWidth]) + 6;
			}
		}
		DrawingArea.drawFilledPixels(xPos, yPos, width, height, color);
		DrawingArea.drawUnfilledPixels(xPos, yPos, width, height, 0);
		yPos += 14;
		for (int textLine = 0; textLine < lineSplit.length; textLine++) {
			newRegularFont.drawBasicString(lineSplit[textLine], xPos + 3, yPos, 0, -1);
			yPos += 16;
		}
	}

	public void drawHoverBox2(int xPos, int yPos, int lineColour, int boxColour, int boxOpacity, String text) {
		String[] results = text.split("\n");
		int height = (results.length * 16) + 6;
		int width;
		width = boldFont.getTextWidth(results[0]) + 6;
		for (int i = 1; i < results.length; i++)
			if (width <= boldFont.getTextWidth(results[i]) + 6)
				width = boldFont.getTextWidth(results[i]) + 6;
		DrawingArea.drawAlphaFilledPixels(xPos, yPos, width, height, boxColour, boxOpacity);
		DrawingArea.fillPixels(xPos, width, height, lineColour, yPos);
		yPos += 14;
		for (int i = 0; i < results.length; i++) {
			boldFont.method389(false, xPos + 3, 0xFFFFF0, results[i], yPos);
			yPos += 16;
		}
	}

	private void buildInterfaceMenu(int i, RSInterface class9, int k, int l, int i1, int j1) {
		if (class9 == null)
			class9 = RSInterface.interfaceCache[21356];
		if (class9.type != 0 || class9.children == null || class9.isMouseoverTriggered)
			return;
		if (k < i || i1 < l || k > i + class9.width || i1 > l + class9.height)
			return;
		int k1 = class9.children.length;
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = class9.childX[l1] + i;
			int j2 = (class9.childY[l1] + l) - j1;
			RSInterface class9_1 = RSInterface.interfaceCache[class9.children[l1]];
			i2 += class9_1.anInt263;
			j2 += class9_1.anInt265;
			if (super.clickMode3 != 0) {
				k = super.clickX;
				i1 = super.clickY;
			}
			if ((class9_1.hoverType >= 0 || class9_1.anInt216 != 0) && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height)
				if (class9_1.hoverType >= 0)
					anInt886 = class9_1.hoverType;
				else
					anInt886 = class9_1.id;
			if (class9_1.type == 8 && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height) {
				anInt1315 = class9_1.id;
			}
			if (class9_1.type == 0) {
				buildInterfaceMenu(i2, class9_1, k, j2, i1, class9_1.scrollPosition);
				if (super.clickMode1 > 0 || super.clickMode2 > 0 || super.clickMode3 > 0) {
					if (class9_1.scrollMax > class9_1.height)
						method65(i2 + class9_1.width, class9_1.height, k, i1, class9_1, j2, true, class9_1.scrollMax);
				}
			} else {
				if (class9_1.atActionType == 1 && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height) {
					boolean flag = false;
					boolean flag1 = false;
					int type = 0;
					if (class9_1.contentType != 0)
						flag = buildFriendsListMenu(class9_1);
					if (class9_1.tooltip.startsWith("[CC]")) {
						flag1 = true;
						clanName = RSInterface.interfaceCache[class9_1.id - 800].message;
					}

					if (flag1) {
						if (!RSInterface.interfaceCache[class9_1.id - 800].message.equals("")) {
							menuActionName[menuActionRow] = "General";
							menuActionID[menuActionRow] = 1321;
							menuActionCmd3[menuActionRow] = class9_1.id;
							menuActionRow++;
							menuActionName[menuActionRow] = "Captain";
							menuActionID[menuActionRow] = 1320;
							menuActionCmd3[menuActionRow] = class9_1.id;
							menuActionRow++;
							menuActionName[menuActionRow] = "Lieutenant";
							menuActionID[menuActionRow] = 1319;
							menuActionCmd3[menuActionRow] = class9_1.id;
							menuActionRow++;
							menuActionName[menuActionRow] = "Sergeant";
							menuActionID[menuActionRow] = 1318;
							menuActionCmd3[menuActionRow] = class9_1.id;
							menuActionRow++;
							menuActionName[menuActionRow] = "Corporal";
							menuActionID[menuActionRow] = 1317;
							menuActionCmd3[menuActionRow] = class9_1.id;
							menuActionRow++;
							menuActionName[menuActionRow] = "Recruit";
							menuActionID[menuActionRow] = 1316;
							menuActionCmd3[menuActionRow] = class9_1.id;
							menuActionRow++;
							menuActionName[menuActionRow] = "Not ranked";
							menuActionID[menuActionRow] = 1315;
							menuActionCmd3[menuActionRow] = class9_1.id;
							menuActionRow++;
						}
					}
					String u = class9_1.tooltip;
					if (openInterfaceID == 39710 && !u.startsWith("Close") && !u.startsWith("Pouches") && !u.startsWith("Scrolls") || openInterfaceID == 38710 && !u.startsWith("Close") && !u.startsWith("Pouches") && !u.startsWith("Scrolls")
							|| (invOverlayInterfaceID != -1 && RSInterface.interfaceCache[invOverlayInterfaceID].id != 5715 && (u.startsWith("Infuse") || u.startsWith("Transform")))) {
						flag1 = true;
						if (u.startsWith("Infuse"))
							type = 1;
						else if (u.startsWith("Transform"))
							type = 2;
					}
					if (!flag && !flag1) {
						menuActionName[menuActionRow] = class9_1.tooltip;
						menuActionID[menuActionRow] = 315;
						menuActionCmd3[menuActionRow] = class9_1.id;
						menuActionRow++;
					}
				}
				if (class9_1.atActionType == 2 && spellSelected == 0 && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height) {
					String s = class9_1.selectedActionName;
					if (s.indexOf(" ") != -1)
						s = s.substring(0, s.indexOf(" "));
					if (class9_1.spellName.endsWith("Rush") || class9_1.spellName.endsWith("Burst") || class9_1.spellName.endsWith("Blitz") || class9_1.spellName.endsWith("Barrage") || class9_1.spellName.endsWith("strike") || class9_1.spellName.endsWith("bolt") || class9_1.spellName.equals("Crumble undead") || class9_1.spellName.endsWith("blast")
							|| class9_1.spellName.endsWith("wave") || class9_1.spellName.equals("Claws of Guthix") || class9_1.spellName.equals("Flames of Zamorak") || class9_1.spellName.equals("Magic Dart")) {
						menuActionName[menuActionRow] = "Autocast @gre@" + class9_1.spellName;
						menuActionID[menuActionRow] = 104;
						menuActionCmd3[menuActionRow] = class9_1.id;
						menuActionRow++;
					}
					if (Config.debugMode)
						menuActionName[menuActionRow] = s + " @gre@" + class9_1.spellName + " @whi@(@gre@" + class9_1.id + "@whi@)";
					else
						menuActionName[menuActionRow] = s + " @gre@" + class9_1.spellName;
					menuActionID[menuActionRow] = 626;
					menuActionCmd3[menuActionRow] = class9_1.id;
					menuActionRow++;
				}
				if (class9_1.atActionType == 3 && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height) {
					menuActionName[menuActionRow] = "Close";
					menuActionID[menuActionRow] = 200;
					menuActionCmd3[menuActionRow] = class9_1.id;
					menuActionRow++;
				}
				if (class9_1.atActionType == 4 && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height) {
					menuActionName[menuActionRow] = class9_1.tooltip;
					menuActionID[menuActionRow] = 169;
					menuActionCmd3[menuActionRow] = class9_1.id;
					menuActionRow++;
				}
				if (class9_1.atActionType == 5 && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height) {
					menuActionName[menuActionRow] = class9_1.tooltip;
					menuActionID[menuActionRow] = 646;
					menuActionCmd3[menuActionRow] = class9_1.id;
					menuActionRow++;
				}
				if (class9_1.atActionType == 6 && !aBoolean1149 && k >= i2 && i1 >= j2 && k < i2 + class9_1.width && i1 < j2 + class9_1.height) {
					menuActionName[menuActionRow] = class9_1.tooltip;
					menuActionID[menuActionRow] = 679;
					menuActionCmd3[menuActionRow] = class9_1.id;
					menuActionRow++;
				}
				// clan chat
				if (k >= i2 && i1 >= j2 && k < i2 + (class9_1.type == 4 ? 100 : class9_1.width) && i1 < j2 + class9_1.height) {
					if (class9_1.actions != null) {
						if ((class9_1.type == 4 && class9_1.message.length() > 0) || class9_1.type == 5) {
							for (int action = class9_1.actions.length - 1; action >= 0; action--) {
								if (class9_1.actions[action] != null) {
									menuActionName[menuActionRow] = class9_1.actions[action] + (class9_1.type == 4 ? " " + class9_1.message : "");
									menuActionID[menuActionRow] = 647;
									menuActionCmd2[menuActionRow] = action;
									menuActionCmd3[menuActionRow] = class9_1.id;
									menuActionRow++;
								}
							}
						}
					}
				}
				if (class9_1.type == 2) {
					int k2 = 0;
					for (int l2 = 0; l2 < class9_1.height; l2++) {
						for (int i3 = 0; i3 < class9_1.width; i3++) {
							int j3 = i2 + i3 * (32 + class9_1.invSpritePadX);
							int k3 = j2 + l2 * (32 + class9_1.invSpritePadY);
							if (k2 < 20) {
								j3 += class9_1.spritesX[k2];
								k3 += class9_1.spritesY[k2];
							}
							if (k >= j3 && i1 >= k3 && k < j3 + 32 && i1 < k3 + 32) {
								itemTo = k2;
								lastActiveInvInterface = class9_1.id;
								if (class9_1.inv[k2] > 0) {
									ItemDefinition itemDefinition = ItemDefinition.forID(class9_1.inv[k2] - 1);
									if (itemSelected == 1 && class9_1.isInventoryInterface) {
										if (class9_1.id != anInt1284 || k2 != anInt1283) {
											menuActionName[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDefinition.name;
											menuActionID[menuActionRow] = 870;
											menuActionCmd1[menuActionRow] = itemDefinition.ID;
											menuActionCmd2[menuActionRow] = k2;
											menuActionCmd3[menuActionRow] = class9_1.id;
											menuActionRow++;
										}
									} else if (spellSelected == 1 && class9_1.isInventoryInterface) {
										if ((spellUsableOn & 0x10) == 16) {
											menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDefinition.name;
											menuActionID[menuActionRow] = 543;
											menuActionCmd1[menuActionRow] = itemDefinition.ID;
											menuActionCmd2[menuActionRow] = k2;
											menuActionCmd3[menuActionRow] = class9_1.id;
											menuActionRow++;
										}
									} else if (!(class9_1.id >= RSInterface.BANK_CHILDREN_BASE_ID + 50 && class9_1.id <= RSInterface.BANK_CHILDREN_BASE_ID + 58)) {
										if (class9_1.isInventoryInterface) {
											for (int l3 = 4; l3 >= 3; l3--)
												if (itemDefinition.actions != null && itemDefinition.actions[l3] != null) {
													menuActionName[menuActionRow] = itemDefinition.actions[l3] + " @lre@" + itemDefinition.name;
													if (l3 == 3)
														menuActionID[menuActionRow] = 493;
													if (l3 == 4)
														menuActionID[menuActionRow] = 847;
													menuActionCmd1[menuActionRow] = itemDefinition.ID;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = class9_1.id;
													menuActionRow++;
												} else if (l3 == 4) {
													menuActionName[menuActionRow] = "Drop @lre@" + itemDefinition.name;
													menuActionID[menuActionRow] = 847;
													menuActionCmd1[menuActionRow] = itemDefinition.ID;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = class9_1.id;
													menuActionRow++;
												}

										}
										if (class9_1.usableItemInterface) {
											menuActionName[menuActionRow] = "Use @lre@" + itemDefinition.name;
											menuActionID[menuActionRow] = 447;
											menuActionCmd1[menuActionRow] = itemDefinition.ID;
											menuActionCmd2[menuActionRow] = k2;
											menuActionCmd3[menuActionRow] = class9_1.id;
											menuActionRow++;
										}
										if (class9_1.isInventoryInterface && itemDefinition.actions != null) {
											for (int i4 = 2; i4 >= 0; i4--)
												if (itemDefinition.actions[i4] != null) {
													menuActionName[menuActionRow] = itemDefinition.actions[i4] + " @lre@" + itemDefinition.name;
													if (i4 == 0)
														menuActionID[menuActionRow] = 74;
													if (i4 == 1)
														menuActionID[menuActionRow] = 454;
													if (i4 == 2)
														menuActionID[menuActionRow] = 539;
													menuActionCmd1[menuActionRow] = itemDefinition.ID;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = class9_1.id;
													menuActionRow++;
												}

										}
										if (class9_1.actions != null) {
											for (int j4 = 4; j4 >= 0; j4--)
												if (class9_1.actions[j4] != null) {
													menuActionName[menuActionRow] = class9_1.actions[j4] + " @lre@" + itemDefinition.name;
													if (j4 == 0)
														menuActionID[menuActionRow] = 632;
													if (j4 == 1)
														menuActionID[menuActionRow] = 78;
													if (j4 == 2)
														menuActionID[menuActionRow] = 867;
													if (j4 == 3)
														menuActionID[menuActionRow] = 431;
													if (j4 == 4)
														menuActionID[menuActionRow] = 53;
													menuActionCmd1[menuActionRow] = itemDefinition.ID;
													menuActionCmd2[menuActionRow] = k2;
													menuActionCmd3[menuActionRow] = class9_1.id;
													menuActionRow++;
												}

										}
										if (Config.debugMode) {
											menuActionName[menuActionRow] = "Examine @lre@" + itemDefinition.name + " @gre@(@whi@" + (class9_1.inv[k2] - 1) + "@gre@)";
										} else {
											menuActionName[menuActionRow] = "Examine @lre@" + itemDefinition.name;
										}
										menuActionID[menuActionRow] = 1125;
										menuActionCmd1[menuActionRow] = itemDefinition.ID;
										menuActionCmd2[menuActionRow] = k2;
										menuActionCmd3[menuActionRow] = class9_1.id;
										menuActionRow++;
									}
								}
							}
							k2++;
						}
					}
				}
			}
		}
	}

	private void drawTransparentScrollBar(int height, int viewOffY, int yPos, int xPos, int fillHeight) {
		imageLoader[73].drawARGBSprite(xPos, yPos);
		imageLoader[74].drawARGBSprite(xPos, (yPos + height) - 16);
		DrawingArea.method342(0xffffff, xPos, 100, yPos + 16, height - 32);
		DrawingArea.method342(0xffffff, xPos + 15, 100, yPos + 16, height - 32);
		int borderHeight = ((height - 32) * height) / fillHeight;
		if (borderHeight < 10) {
			borderHeight = 10;
		}
		int lineOffset = ((height - 32 - borderHeight) * viewOffY) / (fillHeight - height);
		DrawingArea.method335(0xffffff, (yPos + 16) + lineOffset, 14, 5 + ((yPos + 16) + lineOffset + (borderHeight - 5)) - ((yPos + 16) + lineOffset), 50, xPos + 1);
	}

	public void drawScrollbar(int j, int k, int l, int i1, int j1) {
		try {
			scrollBar1.drawSprite(i1, l);
			scrollBar2.drawSprite(i1, (l + j) - 16);
			DrawingArea.drawPixels(j - 32, l + 16, i1, 0x000001, 16);
			DrawingArea.drawPixels(j - 32, l + 16, i1, 0x3d3426, 15);
			DrawingArea.drawPixels(j - 32, l + 16, i1, 0x342d21, 13);
			DrawingArea.drawPixels(j - 32, l + 16, i1, 0x2e281d, 11);
			DrawingArea.drawPixels(j - 32, l + 16, i1, 0x29241b, 10);
			DrawingArea.drawPixels(j - 32, l + 16, i1, 0x252019, 9);
			DrawingArea.drawPixels(j - 32, l + 16, i1, 0x000001, 1);
			int k1 = ((j - 32) * j) / j1;
			if (k1 < 8)
				k1 = 8;
			int l1 = ((j - 32 - k1) * k) / (j1 - j);
			DrawingArea.drawPixels(k1, l + 16 + l1, i1, 0x4d4233, 16);
			DrawingArea.method341(l + 16 + l1, 0x000001, k1, i1);
			DrawingArea.method341(l + 16 + l1, 0x817051, k1, i1 + 1);
			DrawingArea.method341(l + 16 + l1, 0x73654a, k1, i1 + 2);
			DrawingArea.method341(l + 16 + l1, 0x6a5c43, k1, i1 + 3);
			DrawingArea.method341(l + 16 + l1, 0x6a5c43, k1, i1 + 4);
			DrawingArea.method341(l + 16 + l1, 0x655841, k1, i1 + 5);
			DrawingArea.method341(l + 16 + l1, 0x655841, k1, i1 + 6);
			DrawingArea.method341(l + 16 + l1, 0x61553e, k1, i1 + 7);
			DrawingArea.method341(l + 16 + l1, 0x61553e, k1, i1 + 8);
			DrawingArea.method341(l + 16 + l1, 0x5d513c, k1, i1 + 9);
			DrawingArea.method341(l + 16 + l1, 0x5d513c, k1, i1 + 10);
			DrawingArea.method341(l + 16 + l1, 0x594e3a, k1, i1 + 11);
			DrawingArea.method341(l + 16 + l1, 0x594e3a, k1, i1 + 12);
			DrawingArea.method341(l + 16 + l1, 0x514635, k1, i1 + 13);
			DrawingArea.method341(l + 16 + l1, 0x4b4131, k1, i1 + 14);
			DrawingArea.method339(l + 16 + l1, 0x000001, 15, i1);
			DrawingArea.method339(l + 17 + l1, 0x000001, 15, i1);
			DrawingArea.method339(l + 17 + l1, 0x655841, 14, i1);
			DrawingArea.method339(l + 17 + l1, 0x6a5c43, 13, i1);
			DrawingArea.method339(l + 17 + l1, 0x6d5f48, 11, i1);
			DrawingArea.method339(l + 17 + l1, 0x73654a, 10, i1);
			DrawingArea.method339(l + 17 + l1, 0x76684b, 7, i1);
			DrawingArea.method339(l + 17 + l1, 0x7b6a4d, 5, i1);
			DrawingArea.method339(l + 17 + l1, 0x7e6e50, 4, i1);
			DrawingArea.method339(l + 17 + l1, 0x817051, 3, i1);
			DrawingArea.method339(l + 17 + l1, 0x000001, 2, i1);
			DrawingArea.method339(l + 18 + l1, 0x000001, 16, i1);
			DrawingArea.method339(l + 18 + l1, 0x564b38, 15, i1);
			DrawingArea.method339(l + 18 + l1, 0x5d513c, 14, i1);
			DrawingArea.method339(l + 18 + l1, 0x625640, 11, i1);
			DrawingArea.method339(l + 18 + l1, 0x655841, 10, i1);
			DrawingArea.method339(l + 18 + l1, 0x6a5c43, 7, i1);
			DrawingArea.method339(l + 18 + l1, 0x6e6046, 5, i1);
			DrawingArea.method339(l + 18 + l1, 0x716247, 4, i1);
			DrawingArea.method339(l + 18 + l1, 0x7b6a4d, 3, i1);
			DrawingArea.method339(l + 18 + l1, 0x817051, 2, i1);
			DrawingArea.method339(l + 18 + l1, 0x000001, 1, i1);
			DrawingArea.method339(l + 19 + l1, 0x000001, 16, i1);
			DrawingArea.method339(l + 19 + l1, 0x514635, 15, i1);
			DrawingArea.method339(l + 19 + l1, 0x564b38, 14, i1);
			DrawingArea.method339(l + 19 + l1, 0x5d513c, 11, i1);
			DrawingArea.method339(l + 19 + l1, 0x61553e, 9, i1);
			DrawingArea.method339(l + 19 + l1, 0x655841, 7, i1);
			DrawingArea.method339(l + 19 + l1, 0x6a5c43, 5, i1);
			DrawingArea.method339(l + 19 + l1, 0x6e6046, 4, i1);
			DrawingArea.method339(l + 19 + l1, 0x73654a, 3, i1);
			DrawingArea.method339(l + 19 + l1, 0x817051, 2, i1);
			DrawingArea.method339(l + 19 + l1, 0x000001, 1, i1);
			DrawingArea.method339(l + 20 + l1, 0x000001, 16, i1);
			DrawingArea.method339(l + 20 + l1, 0x4b4131, 15, i1);
			DrawingArea.method339(l + 20 + l1, 0x544936, 14, i1);
			DrawingArea.method339(l + 20 + l1, 0x594e3a, 13, i1);
			DrawingArea.method339(l + 20 + l1, 0x5d513c, 10, i1);
			DrawingArea.method339(l + 20 + l1, 0x61553e, 8, i1);
			DrawingArea.method339(l + 20 + l1, 0x655841, 6, i1);
			DrawingArea.method339(l + 20 + l1, 0x6a5c43, 4, i1);
			DrawingArea.method339(l + 20 + l1, 0x73654a, 3, i1);
			DrawingArea.method339(l + 20 + l1, 0x817051, 2, i1);
			DrawingArea.method339(l + 20 + l1, 0x000001, 1, i1);
			DrawingArea.method341(l + 16 + l1, 0x000001, k1, i1 + 15);
			DrawingArea.method339(l + 15 + l1 + k1, 0x000001, 16, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x000001, 15, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x3f372a, 14, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x443c2d, 10, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x483e2f, 9, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x4a402f, 7, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x4b4131, 4, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x564b38, 3, i1);
			DrawingArea.method339(l + 14 + l1 + k1, 0x000001, 2, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x000001, 16, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x443c2d, 15, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x4b4131, 11, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x514635, 9, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x544936, 7, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x564b38, 6, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x594e3a, 4, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x625640, 3, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x6a5c43, 2, i1);
			DrawingArea.method339(l + 13 + l1 + k1, 0x000001, 1, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x000001, 16, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x443c2d, 15, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x4b4131, 14, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x544936, 12, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x564b38, 11, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x594e3a, 10, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x5d513c, 7, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x61553e, 4, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x6e6046, 3, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x7b6a4d, 2, i1);
			DrawingArea.method339(l + 12 + l1 + k1, 0x000001, 1, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x000001, 16, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x4b4131, 15, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x514635, 14, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x564b38, 13, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x594e3a, 11, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x5d513c, 9, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x61553e, 7, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x655841, 5, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x6a5c43, 4, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x73654a, 3, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x7b6a4d, 2, i1);
			DrawingArea.method339(l + 11 + l1 + k1, 0x000001, 1, i1);
		} catch (Exception e) {

		}
	}

	private void updateNPCs(Buffer buffer, int i) {
		anInt839 = 0;
		anInt893 = 0;
		method139(buffer);
		method46(i, buffer);
		method86(buffer);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (npcArray[l].anInt1537 != loopCycle) {
				npcArray[l].desc = null;
				npcArray[l] = null;
			}
		}

		if (buffer.currentOffset != i) {
			Signlink.reporterror(myUsername + " size mismatch in getnpcpos - pos:" + buffer.currentOffset + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < npcCount; i1++)
			if (npcArray[npcIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
				throw new RuntimeException("eek");
			}

	}

	private int cButtonHPos;
	private int cButtonCPos;

	private void processChatModeClick() {
		int[] x = { 5, 62, 119, 176, 233, 290, 347, 404 };
		if (super.mouseX >= x[0] && super.mouseX <= x[0] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 0;
			inputTaken = true;
		} else if (super.mouseX >= x[1] && super.mouseX <= x[1] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 1;
			inputTaken = true;
		} else if (super.mouseX >= x[2] && super.mouseX <= x[2] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 2;
			inputTaken = true;
		} else if (super.mouseX >= x[3] && super.mouseX <= x[3] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 3;
			inputTaken = true;
		} else if (super.mouseX >= x[4] && super.mouseX <= x[4] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 4;
			inputTaken = true;
		} else if (super.mouseX >= x[5] && super.mouseX <= x[5] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 5;
			inputTaken = true;
		} else if (super.mouseX >= x[6] && super.mouseX <= x[6] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 6;
			inputTaken = true;
		} else if (super.mouseX >= x[7] && super.mouseX <= x[7] + 111 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			cButtonHPos = 7;
			inputTaken = true;
		} else {
			cButtonHPos = -1;
			inputTaken = true;
		}
		if (super.clickMode3 == 1) {
			if (super.saveClickX >= x[0] && super.saveClickX <= x[0] + 56 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				if (clientSize != 0) {
					if (channel != 0) {
						cButtonCPos = 0;
						chatTypeView = 0;
						inputTaken = true;
						channel = 0;
					} else {
						showChat = showChat ? false : true;
					}
				} else {
					cButtonCPos = 0;
					chatTypeView = 0;
					inputTaken = true;
					channel = 0;
				}
				buffer.createFrame(95);
				buffer.writeWordBigEndian(publicChatMode);
				buffer.writeWordBigEndian(privateChatMode);
				buffer.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= x[1] && super.saveClickX <= x[1] + 56 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				if (clientSize != 0) {
					if (channel != 1 && clientSize != 0) {
						cButtonCPos = 1;
						chatTypeView = 5;
						inputTaken = true;
						channel = 1;
					} else {
						showChat = showChat ? false : true;
					}
				} else {
					cButtonCPos = 1;
					chatTypeView = 5;
					inputTaken = true;
					channel = 1;
				}
				buffer.createFrame(95);
				buffer.writeWordBigEndian(publicChatMode);
				buffer.writeWordBigEndian(privateChatMode);
				buffer.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= x[2] && super.saveClickX <= x[2] + 56 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				if (clientSize != 0) {
					if (channel != 2 && clientSize != 0) {
						cButtonCPos = 2;
						chatTypeView = 1;
						inputTaken = true;
						channel = 2;
					} else {
						showChat = showChat ? false : true;
					}
				} else {
					cButtonCPos = 2;
					chatTypeView = 1;
					inputTaken = true;
					channel = 2;
				}
				buffer.createFrame(95);
				buffer.writeWordBigEndian(publicChatMode);
				buffer.writeWordBigEndian(privateChatMode);
				buffer.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= x[3] && super.saveClickX <= x[3] + 56 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				if (clientSize != 0) {
					if (channel != 3 && clientSize != 0) {
						cButtonCPos = 3;
						chatTypeView = 2;
						inputTaken = true;
						channel = 3;
					} else {
						showChat = showChat ? false : true;
					}
				} else {
					cButtonCPos = 3;
					chatTypeView = 2;
					inputTaken = true;
					channel = 3;
				}
				buffer.createFrame(95);
				buffer.writeWordBigEndian(publicChatMode);
				buffer.writeWordBigEndian(privateChatMode);
				buffer.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= x[4] && super.saveClickX <= x[4] + 56 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				if (clientSize != 0) {
					if (channel != 4 && clientSize != 0) {
						cButtonCPos = 4;
						chatTypeView = 11;
						inputTaken = true;
						channel = 4;
					} else {
						showChat = showChat ? false : true;
					}
				} else {
					cButtonCPos = 4;
					chatTypeView = 11;
					inputTaken = true;
					channel = 4;
				}
				buffer.createFrame(95);
				buffer.writeWordBigEndian(publicChatMode);
				buffer.writeWordBigEndian(privateChatMode);
				buffer.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= x[5] && super.saveClickX <= x[5] + 56 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				if (clientSize != 0) {
					if (channel != 5 && clientSize != 0) {
						cButtonCPos = 5;
						chatTypeView = 3;
						inputTaken = true;
						channel = 5;
					} else {
						showChat = showChat ? false : true;
					}
				} else {
					cButtonCPos = 5;
					chatTypeView = 3;
					inputTaken = true;
					channel = 5;
				}
				buffer.createFrame(95);
				buffer.writeWordBigEndian(publicChatMode);
				buffer.writeWordBigEndian(privateChatMode);
				buffer.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= x[6] && super.saveClickX <= x[6] + 56 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				if (clientSize != 0) {
					if (channel != 6 && clientSize != 0) {
						cButtonCPos = 6;
						chatTypeView = 6;
						inputTaken = true;
						channel = 6;
					} else {
						showChat = showChat ? false : true;
					}
				} else {
					cButtonCPos = 6;
					chatTypeView = 6;
					inputTaken = true;
					channel = 6;
				}
				buffer.createFrame(95);
				buffer.writeWordBigEndian(publicChatMode);
				buffer.writeWordBigEndian(privateChatMode);
				buffer.writeWordBigEndian(tradeMode);
			} else if (super.saveClickX >= 404 && super.saveClickX <= 515 && super.saveClickY >= clientHeight - 23 && super.saveClickY <= clientHeight) {
				RSFrame.takeScreenshot();
			}
			if (!showChat) {
				cButtonCPos = -1;
			}
		}
	}

	public void method33(int i) {
		try {
			int j = Varp.cache[i].anInt709;
			if (j == 0)
				return;
			int k = variousSettings[i];
			if (j == 1) {
				if (k == 1)
					Rasterizer.calculatePalette(0.90000000000000002D);
				if (k == 2)
					Rasterizer.calculatePalette(0.80000000000000004D);
				if (k == 3)
					Rasterizer.calculatePalette(0.69999999999999996D);
				if (k == 4)
					Rasterizer.calculatePalette(0.59999999999999998D);
				ItemDefinition.iconcache.unlinkAll();
				welcomeScreenRaised = true;
			}
			if (j == 3) {
				boolean flag1 = musicEnabled;
				if (k == 0) {
					adjustVolume(musicEnabled, 0);
					musicEnabled = true;
				}
				if (k == 1) {
					adjustVolume(musicEnabled, -400);
					musicEnabled = true;
				}
				if (k == 2) {
					adjustVolume(musicEnabled, -800);
					musicEnabled = true;
				}
				if (k == 3) {
					adjustVolume(musicEnabled, -1200);
					musicEnabled = true;
				}
				if (k == 4)
					musicEnabled = false;
				if (musicEnabled != flag1 && !lowMem) {
					if (musicEnabled) {
						nextSong = currentSong;
						songChanging = true;
						onDemandFetcher.method558(2, nextSong);
					} else {
						stopMidi();
					}
					prevSong = 0;
				}
			}
			if (j == 4) {
				if (k == 0) {
					aBoolean848 = true;
					setWaveVolume(0);
				}
				if (k == 1) {
					aBoolean848 = true;
					setWaveVolume(-400);
				}
				if (k == 2) {
					aBoolean848 = true;
					setWaveVolume(-800);
				}
				if (k == 3) {
					aBoolean848 = true;
					setWaveVolume(-1200);
				}
				if (k == 4)
					aBoolean848 = false;
			}
			if (j == 5)
				anInt1253 = k;
			if (j == 6)
				anInt1249 = k;
			if (j == 8) {
				splitPrivateChat = k;
				inputTaken = true;
			}
			if (j == 9)
				anInt913 = k;
		} catch (Exception _e) {
		}
	}

	public void updateEntities() {
		try {
			int anInt974 = 0;
			for (int j = -1; j < playerCount + npcCount; j++) {
				Object obj;
				if (j == -1)
					obj = myPlayer;
				else if (j < playerCount)
					obj = playerArray[playerIndices[j]];
				else
					obj = npcArray[npcIndices[j - playerCount]];
				if (obj == null || !((Entity) (obj)).isVisible())
					continue;
				if (obj instanceof Npc) {
					EntityDefinition entityDefinition = ((Npc) obj).desc;
					if (entityDefinition.childrenIDs != null)
						entityDefinition = entityDefinition.method161();
					if (entityDefinition == null)
						continue;
				}
				if (j < playerCount) {
					int l = 30;
					Player player = (Player) obj;
					if (player.headIcon >= 0) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1) {
							if (player.skullIcon < 2) {
								skullIcons[player.skullIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
								l += 25;
							}
							/*
							 * if (player.headIcon < 13) {
							 * headIcons[player.headIcon].drawSprite(spriteDrawX
							 * - 12, spriteDrawY - l); l += 18; } } }
							 */
							if (player.headIcon < 18) {
								headIcons[player.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l - headIconOffset);
								l += 26;
							}
						}
					}
					if (j >= 0 && anInt855 == 10 && anInt933 == playerIndices[j]) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIconsHint[player.hintIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
					}
				} else {
					EntityDefinition entityDef_1 = ((Npc) obj).desc;
					if (entityDef_1.anInt75 >= 0 && entityDef_1.anInt75 < headIcons.length) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIcons[entityDef_1.anInt75].drawSprite(spriteDrawX - 12, spriteDrawY - 30);
					}
					if (anInt855 == 1 && anInt1222 == npcIndices[j - playerCount] && loopCycle % 20 < 10) {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1)
							headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
					}
				}
				if (((Entity) (obj)).textSpoken != null && (j >= playerCount || publicChatMode == 0 || publicChatMode == 3 || publicChatMode == 1 && isFriendOrSelf(((Player) obj).name))) {
					npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height);
					if (spriteDrawX > -1 && anInt974 < anInt975) {
						anIntArray979[anInt974] = chatTextDrawingArea.method384(((Entity) (obj)).textSpoken) / 2;
						anIntArray978[anInt974] = chatTextDrawingArea.anInt1497;
						anIntArray976[anInt974] = spriteDrawX;
						anIntArray977[anInt974] = spriteDrawY;
						anIntArray980[anInt974] = ((Entity) (obj)).anInt1513;
						anIntArray981[anInt974] = ((Entity) (obj)).anInt1531;
						anIntArray982[anInt974] = ((Entity) (obj)).textCycle;
						aStringArray983[anInt974++] = ((Entity) (obj)).textSpoken;
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 >= 1 && ((Entity) (obj)).anInt1531 <= 3) {
							anIntArray978[anInt974] += 10;
							anIntArray977[anInt974] += 5;
						}
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 4)
							anIntArray979[anInt974] = 60;
						if (anInt1249 == 0 && ((Entity) (obj)).anInt1531 == 5)
							anIntArray978[anInt974] += 5;
					}
				}
				if (((Entity) (obj)).loopCycleStatus > loopCycle) {
					try {
						npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height + 15);
						if (spriteDrawX > -1) {
							int i1 = (((Entity) (obj)).currentHealth * 30) / ((Entity) (obj)).maxHealth;
							if (i1 > 30)
								i1 = 30;
							int HpPercent = (((Entity) (obj)).currentHealth * 56) / ((Entity) (obj)).maxHealth;
							if (HpPercent > 56)
								HpPercent = 56;
							if (toggleHPOn)
								chatTextDrawingArea.drawText(0xff0000, (new StringBuilder()).append(((Entity) (Entity) obj).currentHealth).append("/").append(((Entity) (Entity) obj).maxHealth).toString(), spriteDrawY - 9, spriteDrawX);
							int j1 = (((Entity) (Entity) obj).currentHealth * 30) / ((Entity) (Entity) obj).maxHealth;
							if (newHPBars == false) {
								DrawingArea.drawPixels(5, spriteDrawY - 3, spriteDrawX - 15, 65280, i1);
								DrawingArea.drawPixels(5, spriteDrawY - 3, (spriteDrawX - 15) + i1, 0xff0000, 30 - i1);
							} else {
								HPBarEmpty.drawSprite(spriteDrawX - 28, spriteDrawY - 3);
								HPBarFull = new Sprite(Signlink.findcachedir() + "Interfaces/Player/HP0.png", HpPercent, 7);
								HPBarFull.drawSprite(spriteDrawX - 28, spriteDrawY - 3);
							}
						}
					} catch (Exception e) {
					}
				}
				if (newHitmarkers) {
					for (int j1 = 0; j1 < 4; j1++)
						if (((Entity) (obj)).hitsLoopCycle[j1] > loopCycle) {
							npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height / 2);
							if (spriteDrawX > -1) {
								switch (j1) {
								case 1:
									spriteDrawY += 20;
									break;
								case 2:
									spriteDrawY += 40;
									break;
								case 3:
									spriteDrawY += 60;
									break;
								case 4:
									spriteDrawY += 80;
									break;
								case 5:
									spriteDrawY += 100;
									break;
								case 6:
									spriteDrawY += 120;
									break;
								}
								Entity e = ((Entity) (obj));
								if (e.hitmarkMove[j1] > -30)
									e.hitmarkMove[j1]--;
								if (e.hitmarkMove[j1] < -26)
									e.hitmarkTrans[j1] -= 5;
								hitmarkDraw(String.valueOf(e.hitArray[j1]).length(), e.hitMarkTypes[j1], e.hitIcon[j1], e.hitArray[j1], e.hitmarkMove[j1], e.hitmarkTrans[j1]);
							}
						}
				} else {
					for(int j2 = 0; j2 < 4; j2++) {
						if(((Entity) (obj)).hitsLoopCycle[j2] > loopCycle) {
							npcScreenPos(((Entity) (obj)), ((Entity) (obj)).height / 2);
							if(spriteDrawX > -1) {
								if(j2 == 0 && ((Entity) (obj)).hitArray[j2] > 99)
									((Entity) (obj)).hitMarkTypes[j2] = 3;
								else if(j2 == 1 && ((Entity) (obj)).hitArray[j2] > 99)
									((Entity) (obj)).hitMarkTypes[j2] = 3;
								else if(j2 == 2 && ((Entity) (obj)).hitArray[j2] > 99)
									((Entity) (obj)).hitMarkTypes[j2] = 3;
								else if(j2 == 3 && ((Entity) (obj)).hitArray[j2] > 99)
									((Entity) (obj)).hitMarkTypes[j2] = 3;
								if(j2 == 1) {
									spriteDrawY -= 20;
								}
								if(j2 == 2) {
									spriteDrawX -= (((Entity) (obj)).hitArray[j2] > 99 ? 30 : 20);
									spriteDrawY -= 10;
								}
								if(j2 == 3) {
									spriteDrawX += (((Entity) (obj)).hitArray[j2] > 99 ? 30 : 20);
									spriteDrawY -= 10;
								}
								if(((Entity) (obj)).hitMarkTypes[j2] == 3) {
									spriteDrawX -= 8;
								}
								cacheSprite[hitmarks562[((Entity) (obj)).hitMarkTypes[j2]]].draw24BitSprite(spriteDrawX - 12, spriteDrawY - 12);
								smallText.drawText(0xffffff, String.valueOf(((Entity) (obj)).hitArray[j2]), spriteDrawY + 3, (((Entity) (obj)).hitMarkTypes[j2] == 3 ? spriteDrawX + 7:spriteDrawX - 1));
							}
						}
					}
				}
			}
			for (int k = 0; k < anInt974; k++) {
				int k1 = anIntArray976[k];
				int l1 = anIntArray977[k];
				int j2 = anIntArray979[k];
				int k2 = anIntArray978[k];
				boolean flag = true;
				while (flag) {
					flag = false;
					for (int l2 = 0; l2 < k; l2++)
						if (l1 + 2 > anIntArray977[l2] - anIntArray978[l2] && l1 - k2 < anIntArray977[l2] + 2 && k1 - j2 < anIntArray976[l2] + anIntArray979[l2] && k1 + j2 > anIntArray976[l2] - anIntArray979[l2] && anIntArray977[l2] - anIntArray978[l2] < l1) {
							l1 = anIntArray977[l2] - anIntArray978[l2];
							flag = true;
						}

				}
				spriteDrawX = anIntArray976[k];
				spriteDrawY = anIntArray977[k] = l1;
				String s = aStringArray983[k];
				if (anInt1249 == 0) {
					int i3 = 0xffff00;
					if (anIntArray980[k] < 6)
						i3 = anIntArray965[anIntArray980[k]];
					if (anIntArray980[k] == 6)
						i3 = anInt1265 % 20 >= 10 ? 0xffff00 : 0xff0000;
					if (anIntArray980[k] == 7)
						i3 = anInt1265 % 20 >= 10 ? 65535 : 255;
					if (anIntArray980[k] == 8)
						i3 = anInt1265 % 20 >= 10 ? 0x80ff80 : 45056;
					if (anIntArray980[k] == 9) {
						int j3 = 150 - anIntArray982[k];
						if (j3 < 50)
							i3 = 0xff0000 + 1280 * j3;
						else if (j3 < 100)
							i3 = 0xffff00 - 0x50000 * (j3 - 50);
						else if (j3 < 150)
							i3 = 65280 + 5 * (j3 - 100);
					}
					if (anIntArray980[k] == 10) {
						int k3 = 150 - anIntArray982[k];
						if (k3 < 50)
							i3 = 0xff0000 + 5 * k3;
						else if (k3 < 100)
							i3 = 0xff00ff - 0x50000 * (k3 - 50);
						else if (k3 < 150)
							i3 = (255 + 0x50000 * (k3 - 100)) - 5 * (k3 - 100);
					}
					if (anIntArray980[k] == 11) {
						int l3 = 150 - anIntArray982[k];
						if (l3 < 50)
							i3 = 0xffffff - 0x50005 * l3;
						else if (l3 < 100)
							i3 = 65280 + 0x50005 * (l3 - 50);
						else if (l3 < 150)
							i3 = 0xffffff - 0x50000 * (l3 - 100);
					}
					if(anIntArray981[k] == 0) {
						chatTextDrawingArea.drawText(0, s, spriteDrawY + 1, spriteDrawX + 1);
						chatTextDrawingArea.drawText(i3, s, spriteDrawY, spriteDrawX);
					}
					if(anIntArray981[k] == 1) {
						chatTextDrawingArea.method386(0, s, spriteDrawX + 1, anInt1265, spriteDrawY + 1);
						chatTextDrawingArea.method386(i3, s, spriteDrawX, anInt1265, spriteDrawY);
					}
					if(anIntArray981[k] == 2) {
						chatTextDrawingArea.method387(spriteDrawX+ 1, s, anInt1265, spriteDrawY + 1, 0);
						chatTextDrawingArea.method387(spriteDrawX, s, anInt1265, spriteDrawY, i3);
					}
					if(anIntArray981[k] == 3) {
						chatTextDrawingArea.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY + 1, spriteDrawX+ 1, 0);
						chatTextDrawingArea.method388(150 - anIntArray982[k], s, anInt1265, spriteDrawY, spriteDrawX, i3);
					}
					if(anIntArray981[k] == 4) {
						int i4 = chatTextDrawingArea.method384(s);
						int k4 = ((150 - anIntArray982[k]) * (i4 + 100)) / 150;
						DrawingArea.setDrawingArea(334, spriteDrawX - 50, spriteDrawX + 50, 0);
						chatTextDrawingArea.method385(0, s, spriteDrawY + 1, (spriteDrawX+ 1 + 50) - k4);
						chatTextDrawingArea.method385(i3, s, spriteDrawY, (spriteDrawX + 50) - k4);
						DrawingArea.defaultDrawingAreaSize();
					}
					if(anIntArray981[k] == 5) {
						int j4 = 150 - anIntArray982[k];
						int l4 = 0;
						if(j4 < 25)
							l4 = j4 - 25;
						else
						if(j4 > 125)
							l4 = j4 - 125;
						DrawingArea.setDrawingArea(spriteDrawY + 5, 0, 512, spriteDrawY - chatTextDrawingArea.anInt1497 - 1);
						chatTextDrawingArea.drawText(0, s, spriteDrawY + 1 + l4, spriteDrawX + 1);
						chatTextDrawingArea.drawText(i3, s, spriteDrawY + l4, spriteDrawX);
						DrawingArea.defaultDrawingAreaSize();
					}
				} else {
					chatTextDrawingArea.drawText(0, s, spriteDrawY + 1, spriteDrawX + 1);
					chatTextDrawingArea.drawText(0xffff00, s, spriteDrawY, spriteDrawX);
				}
			}
		} catch (Exception e) {
		}
	}

	private void delFriend(long l) {
		try {
			if (l == 0L)
				return;
			for (int i = 0; i < friendsCount; i++) {
				if (friendsListAsLongs[i] != l)
					continue;
				friendsCount--;
				for (int j = i; j < friendsCount; j++) {
					friendsList[j] = friendsList[j + 1];
					friendsNodeIDs[j] = friendsNodeIDs[j + 1];
					friendsListAsLongs[j] = friendsListAsLongs[j + 1];
				}

				buffer.createFrame(215);
				buffer.writeQWord(l);
				break;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("18622, " + false + ", " + l + ", " + runtimeexception.toString());
			throw new RuntimeException();
		}
	}

	public void handleTabArea(boolean fixed) {
		if (fixed) {
			imageLoader[2].drawSprite(0, 0);
		} else {
			if (clientWidth >= smallTabs) {
				for (int positionX = clientWidth - 480, positionY = clientHeight - 37, index = 0; positionX <= clientWidth - 30 && index < 16; positionX += 30, index++) {
					imageLoader[29].drawSprite(positionX, positionY);
				}
				if (showTab) {
					imageLoader[25].drawTransparentSprite(clientWidth - 197, clientHeight - 37 - 267, 150);
					imageLoader[26].drawSprite(clientWidth - 204, clientHeight - 37 - 274);
				}
			} else {
				for (int positionX = clientWidth - 240, positionY = clientHeight - 74, index = 0; positionX <= clientWidth - 30 && index < 8; positionX += 30, index++) {
					imageLoader[29].drawSprite(positionX, positionY);
				}
				for (int positionX = clientWidth - 240, positionY = clientHeight - 37, index = 0; positionX <= clientWidth - 30 && index < 8; positionX += 30, index++) {
					imageLoader[29].drawSprite(positionX, positionY);
				}
				if (showTab) {
					imageLoader[25].drawTransparentSprite(clientWidth - 197, clientHeight - 74 - 267, 150);
					imageLoader[26].drawSprite(clientWidth - 204, clientHeight - 74 - 274);
				}
			}
		}
		if (invOverlayInterfaceID == -1) {
			drawTabHover(fixed);
			if (showTab) {
				drawTabs(fixed);
			}
			drawSideIcons(fixed);
		}
	}

	public int tabHover = -1;

	public void drawTabHover(boolean fixed) {
		if (fixed) {
			if (tabHover != -1) {
				if (tabInterfaceIDs[tabHover] != -1) {
					int[] positionX = { 0, 30, 60, 120, 150, 180, 210, 90, 30, 60, -1, 120, 150, 180, 90, 0, 210 };
					int[] positionY = { 0, 0, 0, 0, 0, 0, 0, 298, 298, 298, -1, 298, 298, 298, 0, 298, 298 };
					if (tabHover != 10) {
						imageLoader[50].drawSprite(8 + positionX[tabHover], positionY[tabHover]);
					}
				}
			}
		} else {
			if (tabHover != -1) {
				int[] tab = { 0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16 };
				int[] positionX = { 0, 30, 60, 90, 120, 150, 180, 210, 0, 30, 60, 90, 120, 150, 180, 210 };
				int offsetX = 0;
				for (int index = 0; index < tab.length; index++) {
					if (tabInterfaceIDs[tab[index]] != -1) {
						if (tabHover == tab[index]) {
							offsetX = index > 7 && clientWidth >= smallTabs ? 240 : 0;
							imageLoader[50].drawARGBSprite((clientWidth - (clientWidth >= smallTabs ? 480 : 240)) + positionX[index] + offsetX, clientHeight - (clientWidth >= smallTabs ? 37 : (index < 8 ? 74 : 37)));
						}
					}
				}
			}
		}
	}

	public void drawTabs(boolean fixed) {
		if (fixed) {
			int xPos = 2;
			int yPos = 0;
			if (tabID < tabInterfaceIDs.length && tabInterfaceIDs[tabID] != -1) {
				switch (tabID) {
				case 0:
				case 1:
				case 2:
					xPos += tabID * 30;
					yPos = 0;
					break;
				case 3:
				case 4:
				case 5:
				case 6:
					xPos += (tabID + 1) * 30;
					yPos = 0;
					break;
				case 7:
					xPos = 2 + ((tabID - 4) * 30);
					yPos = 299;
					break;
				case 8:
				case 9:
				case 11:
				case 12:
				case 13:
					xPos = 2 + ((tabID - 7) * 30);
					yPos = 299;
					break;
				case 14:
					xPos = 92;
					yPos = 0;
					break;
				case 15:
					xPos = 2;
					yPos = 299;
					break;
				case 16:
					xPos = 212;
					yPos = 299;
					break;
				}
				if (tabID != 10) {
					imageLoader[49].drawARGBSprite(xPos + 5, yPos);
				}
			}
		} else {
			int[] tab = { 0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16 };
			int[] positionX = { 0, 30, 60, 90, 120, 150, 180, 210, 0, 30, 60, 90, 120, 150, 180, 210 };
			for (int index = 0; index < tab.length; index++) {
				int offsetX = clientWidth >= smallTabs ? 481 : 241;
				if (offsetX == 481 && index > 7) {
					offsetX -= 240;
				}
				int offsetY = clientWidth >= smallTabs ? 37 : (index > 7 ? 37 : 74);
				if (tabID == tab[index] && tabInterfaceIDs[tab[index]] != -1) {
					imageLoader[49].drawARGBSprite((clientWidth - offsetX - 0) + positionX[index], (clientHeight - offsetY) + 0);
				}
			}
		}
	}

	public void drawSideIcons(boolean fixed) {
		if (fixed) {
			int[] id = { 33, 34, 35, 36, 37, 38, 39, 40, 48, 41, 42, 46, 43, 44, 45, 47 };
			int[] tab = { 0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16 };
			int[] positionX = { 13, 42, 72, 102, 132, 164, 192, 222, 12, 43, 74, // +5
					102, 132, 162, 192, 222 };
			int[] positionY = { 9, 9, 8, 8, 8, 8, 8, 8, 307, 306, 306, 307, 306, 306, 306, 308 };
			for (int index = 0; index < 16; index++) {
				if (tabInterfaceIDs[tab[index]] != -1) {
					if (id[index] != -1) {
						imageLoader[id[((tabInterfaceIDs[14] == 26224 || tabInterfaceIDs[14] == 27224) && index == 3) ? 16 : index]].drawSprite(positionX[index], positionY[index]);
					}
				}
			}
		} else {
			int[] id = { 33, 34, 35, 36, 37, 38, 39, 40, 48, 41, 42, 46, 43, 44, 45, 47 };
			int[] tab = { 0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16 };
			int[] positionX = { 8, 37, 67, 97, 127, 159, 187, 217, 7, 38, 69, 97, 127, 157, 187, 217 };
			int[] positionY = { 9, 9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 9, 8, 8, 8, 9 };
			for (int index = 0; index < tab.length; index++) {
				int offsetX = clientWidth >= smallTabs ? 482 : 242;
				if (offsetX == 482 && index > 7) {
					offsetX -= 240;
				}
				int offsetY = clientWidth >= smallTabs ? 37 : (index > 7 ? 37 : 74);
				if (tabInterfaceIDs[tab[index]] != -1) {
					if (id[index] != -1) {
						imageLoader[id[((tabInterfaceIDs[14] == 26224 || tabInterfaceIDs[14] == 27224) && index == 3) ? 16 : index]].drawSprite((clientWidth - offsetX) + positionX[index], (clientHeight - offsetY) + positionY[index]);
					}
				}
			}
		}
	}

	private void processTabAreaTooltips(int TabHoverId) {
		String[] tooltipString = { "Combat Styles", "Achievements", "Stats", "Inventory", "Worn Equipment", "Prayer List", "Magic Spellbook", "Clan Chat", "Friends List", "Ignore List", "Logout", "Options", "Emotes", "Player Profiler", "Information List", "Summoning", "Game Settings" };
		menuActionName[1] = tooltipString[TabHoverId];
		menuActionID[1] = 1076;
		menuActionRow = 2;
	}

	private void processTabAreaHovers() {
		if (clientSize == 0) {
			int positionX = clientWidth - 244;
			int positionY = 169, positionY2 = clientHeight - 36;
			if (mouseInRegion(clientWidth - 21, 0, clientWidth, 21)) {
				tabHover = 10;
				processTabAreaTooltips(tabHover);
			} else if (mouseInRegion(positionX, positionY, positionX + 30, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 0;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 30, positionY, positionX + 60, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 1;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 60, positionY, positionX + 90, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 2;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 90, positionY, positionX + 120, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 14;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 120, positionY, positionX + 150, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 3;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 150, positionY, positionX + 180, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 4;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 180, positionY, positionX + 210, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 5;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 210, positionY, positionX + 240, positionY + 36)) {
				needDrawTabArea = true;
				tabHover = 6;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX, positionY2, positionX + 30, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 15;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 30, positionY2, positionX + 60, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 8;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 60, positionY2, positionX + 90, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 9;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 90, positionY2, positionX + 120, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 7;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 120, positionY2, positionX + 150, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 11;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 150, positionY2, positionX + 180, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 12;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 180, positionY2, positionX + 210, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 13;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX + 210, positionY2, positionX + 240, positionY2 + 36)) {
				needDrawTabArea = true;
				tabHover = 16;
				processTabAreaTooltips(tabHover);
				tabAreaAltered = true;
			} else {
				needDrawTabArea = true;
				tabHover = -1;
				tabAreaAltered = true;
			}
		} else {
			int[] positionX = { 0, 30, 60, 90, 120, 150, 180, 210, 0, 30, 60, 90, 120, 150, 180, 210 };
			int[] tab = { 0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16 };
			int offsetX = (clientWidth >= smallTabs ? clientWidth - 480 : clientWidth - 240);
			int positionY = (clientWidth >= smallTabs ? clientHeight - 37 : clientHeight - 74);
			int secondPositionY = clientHeight - 37;
			int secondOffsetX = clientWidth >= smallTabs ? 240 : 0;
			if (mouseInRegion(clientWidth - 21, 0, clientWidth, 21)) {
				tabHover = 10;
			} else if (mouseInRegion(positionX[0] + offsetX, positionY, positionX[0] + offsetX + 30, positionY + 37)) {
				tabHover = tab[0];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[1] + offsetX, positionY, positionX[1] + offsetX + 30, positionY + 37)) {
				tabHover = tab[1];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[2] + offsetX, positionY, positionX[2] + offsetX + 30, positionY + 37)) {
				tabHover = tab[2];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[3] + offsetX, positionY, positionX[3] + offsetX + 30, positionY + 37)) {
				tabHover = tab[3];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[4] + offsetX, positionY, positionX[4] + offsetX + 30, positionY + 37)) {
				tabHover = tab[4];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[5] + offsetX, positionY, positionX[5] + offsetX + 30, positionY + 37)) {
				tabHover = tab[5];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[6] + offsetX, positionY, positionX[6] + offsetX + 30, positionY + 37)) {
				tabHover = tab[6];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[7] + offsetX, positionY, positionX[7] + offsetX + 30, positionY + 37)) {
				tabHover = tab[7];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[8] + offsetX + secondOffsetX, secondPositionY, positionX[8] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[8];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[9] + offsetX + secondOffsetX, secondPositionY, positionX[9] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[9];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[10] + offsetX + secondOffsetX, secondPositionY, positionX[10] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[10];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[11] + offsetX + secondOffsetX, secondPositionY, positionX[11] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[11];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[12] + offsetX + secondOffsetX, secondPositionY, positionX[12] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[12];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[13] + offsetX + secondOffsetX, secondPositionY, positionX[13] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[13];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[14] + offsetX + secondOffsetX, secondPositionY, positionX[14] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[14];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else if (mouseInRegion(positionX[15] + offsetX + secondOffsetX, secondPositionY, positionX[15] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
				tabHover = tab[15];
				processTabAreaTooltips(tabHover);
				needDrawTabArea = true;
				tabAreaAltered = true;
			} else {
				tabHover = -1;
				needDrawTabArea = true;
				tabAreaAltered = true;
			}
		}
	}

	public boolean mouseInRegion(int x1, int y1, int x2, int y2) {
		if (super.mouseX >= x1 && super.mouseX <= x2 && super.mouseY >= y1 && super.mouseY <= y2)
			return true;
		return false;
	}

	public boolean clickInRegion(int x1, int y1, int x2, int y2) {
		if (super.saveClickX >= x1 && super.saveClickX <= x2 && super.saveClickY >= y1 && super.saveClickY <= y2)
			return true;
		return false;
	}

	private void processTabAreaClick() {
		if (clientSize == 0) {
			int positionX = clientWidth - 244;
			int positionY = 169;
			if (super.clickMode3 == 1) {
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[0] != -1) {
					needDrawTabArea = true;
					tabID = 0;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[1] != -1) {
					needDrawTabArea = true;
					tabID = 1;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[2] != -1) {
					needDrawTabArea = true;
					tabID = 2;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[14] != -1) {
					needDrawTabArea = true;
					tabID = 14;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[3] != -1) {
					needDrawTabArea = true;
					tabID = 3;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[4] != -1) {
					needDrawTabArea = true;
					tabID = 4;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[5] != -1) {
					needDrawTabArea = true;
					tabID = 5;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[6] != -1) {
					needDrawTabArea = true;
					tabID = 6;
					tabAreaAltered = true;
				}
				positionX = clientWidth - 244;
				positionY = clientHeight - 36;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[15] != -1) {
					needDrawTabArea = true;
					tabID = 15;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[8] != -1) {
					needDrawTabArea = true;
					tabID = 8;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[9] != -1) {
					needDrawTabArea = true;
					tabID = 9;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[7] != -1) {
					needDrawTabArea = true;
					tabID = 7;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(clientWidth - 21, 0, clientWidth, 21) && tabInterfaceIDs[10] != -1) {
					needDrawTabArea = true;
					tabID = 10;
					tabAreaAltered = true;
				}

				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[11] != -1) {
					needDrawTabArea = true;
					tabID = 11;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[12] != -1) {
					needDrawTabArea = true;
					tabID = 12;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[13] != -1) {
					needDrawTabArea = true;
					tabID = 13;
					tabAreaAltered = true;
				}
				positionX += 30;
				if (clickInRegion(positionX, positionY, positionX + 30, positionY + 36) && tabInterfaceIDs[16] != -1) {
					needDrawTabArea = true;
					tabID = 16;
					tabAreaAltered = true;
				}
			}
		} else {
			int[] positionX = { 0, 30, 60, 90, 120, 150, 180, 210, 0, 30, 60, 90, 120, 150, 180, 210 };
			int[] tab = { 0, 1, 2, 14, 3, 4, 5, 6, 15, 8, 9, 7, 11, 12, 13, 16 };
			int offsetX = (clientWidth >= smallTabs ? clientWidth - 480 : clientWidth - 240);
			int positionY = (clientWidth >= smallTabs ? clientHeight - 37 : clientHeight - 74);
			int secondPositionY = clientHeight - 37;
			int secondOffsetX = clientWidth >= smallTabs ? 240 : 0;
			if (super.clickMode3 == 1) {
				if (clickInRegion(positionX[0] + offsetX, positionY, positionX[0] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[0]] != -1) {
					if (tabID == tab[0]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[0];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[1] + offsetX, positionY, positionX[1] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[1]] != -1) {
					if (tabID == tab[1]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[1];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[2] + offsetX, positionY, positionX[2] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[2]] != -1) {
					if (tabID == tab[2]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[2];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[3] + offsetX, positionY, positionX[3] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[3]] != -1) {
					if (tabID == tab[3]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[3];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[4] + offsetX, positionY, positionX[4] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[4]] != -1) {
					if (tabID == tab[4]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[4];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[5] + offsetX, positionY, positionX[5] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[5]] != -1) {
					if (tabID == tab[5]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[5];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[6] + offsetX, positionY, positionX[6] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[6]] != -1) {
					if (tabID == tab[6]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[6];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[7] + offsetX, positionY, positionX[7] + offsetX + 30, positionY + 37) && tabInterfaceIDs[tab[7]] != -1) {
					if (tabID == tab[7]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[7];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[8] + offsetX + secondOffsetX, secondPositionY, positionX[8] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[8]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[8];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[9] + offsetX + secondOffsetX, secondPositionY, positionX[9] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[9]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[9];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[10] + offsetX + secondOffsetX, secondPositionY, positionX[10] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[10]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[10];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[11] + offsetX + secondOffsetX, secondPositionY, positionX[11] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[11]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[11];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[12] + offsetX + secondOffsetX, secondPositionY, positionX[12] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[12]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[12];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[13] + offsetX + secondOffsetX, secondPositionY, positionX[13] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[13]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[13];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[14] + offsetX + secondOffsetX, secondPositionY, positionX[14] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[14]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[14];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(positionX[15] + offsetX + secondOffsetX, secondPositionY, positionX[15] + offsetX + secondOffsetX + 30, secondPositionY + 37)) {
					if (tabID == tab[15]) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = tab[15];
					needDrawTabArea = true;
					tabAreaAltered = true;
				} else if (clickInRegion(clientWidth - 21, 0, clientWidth, 21)) {
					if (tabID == 10) {
						showTab = !showTab;
					} else {
						showTab = true;
					}
					tabID = 10;
					needDrawTabArea = true;
					tabAreaAltered = true;
				}
			}
		}
	}

	private void drawTabArea() {
		if (clientSize == 0) {
			aRSImageProducer_1163.setCanvas();
		}
		Rasterizer.lineOffsets = anIntArray1181;
		handleTabArea(clientSize == 0);
		int y = clientWidth >= smallTabs ? 37 : 74;
		if (showTab) {
			if (invOverlayInterfaceID != -1) {
				drawInterface(0, (clientSize == 0 ? 34 : clientWidth - 197), RSInterface.interfaceCache[invOverlayInterfaceID], (clientSize == 0 ? 37 : clientHeight - y - 267));
			} else if (tabInterfaceIDs[tabID] != -1) {
				drawInterface(0, (clientSize == 0 ? 34 : clientWidth - 197), RSInterface.interfaceCache[tabInterfaceIDs[tabID]], (clientSize == 0 ? 37 : clientHeight - y - 267));
			}
			if (isFixed()) {
				if (menuOpen)
					drawMenu(516, 168);
			} else {
				if (menuOpen && menuScreenArea == 1) {
					drawMenu();
				}
			}
			if (clientSize == 0)
				aRSImageProducer_1163.drawGraphics(168, super.graphics, 516);
			aRSImageProducer_1165.setCanvas();
			Rasterizer.lineOffsets = anIntArray1182;
		}
	}

	private int smallTabs = 1000;

	private void method37(int j) {
		if (GameConfiguration.MOVING_TEXTURES) {
			if (Rasterizer.lastTexture[17] >= j) {
				Background background = Rasterizer.textureImages[17];
				int k = background.anInt1452 * background.anInt1453 - 1;
				int j1 = background.anInt1452 * anInt945 * 2;
				byte abyte0[] = background.aByteArray1450;
				byte abyte3[] = aByteArray912;
				for (int i2 = 0; i2 <= k; i2++)
					abyte3[i2] = abyte0[i2 - j1 & k];

				background.aByteArray1450 = abyte3;
				aByteArray912 = abyte0;
				Rasterizer.resetTexture(17);
				anInt854++;
				if (anInt854 > 1235) {
					anInt854 = 0;
					buffer.createFrame(226);
					buffer.writeWordBigEndian(0);
					int l2 = buffer.currentOffset;
					buffer.writeWord(58722);
					buffer.writeWordBigEndian(240);
					buffer.writeWord((int) (Math.random() * 65536D));
					buffer.writeWordBigEndian((int) (Math.random() * 256D));
					if ((int) (Math.random() * 2D) == 0)
						buffer.writeWord(51825);
					buffer.writeWordBigEndian((int) (Math.random() * 256D));
					buffer.writeWord((int) (Math.random() * 65536D));
					buffer.writeWord(7130);
					buffer.writeWord((int) (Math.random() * 65536D));
					buffer.writeWord(61657);
					buffer.writeBytes(buffer.currentOffset - l2);
				}
			}
			if (Rasterizer.lastTexture[24] >= j) {
				Background background_1 = Rasterizer.textureImages[24];
				int l = background_1.anInt1452 * background_1.anInt1453 - 1;
				int k1 = background_1.anInt1452 * anInt945 * 2;
				byte abyte1[] = background_1.aByteArray1450;
				byte abyte4[] = aByteArray912;
				for (int j2 = 0; j2 <= l; j2++)
					abyte4[j2] = abyte1[j2 - k1 & l];

				background_1.aByteArray1450 = abyte4;
				aByteArray912 = abyte1;
				Rasterizer.resetTexture(24);
			}
			if (Rasterizer.lastTexture[34] >= j) {
				Background background_2 = Rasterizer.textureImages[34];
				int i1 = background_2.anInt1452 * background_2.anInt1453 - 1;
				int l1 = background_2.anInt1452 * anInt945 * 2;
				byte abyte2[] = background_2.aByteArray1450;
				byte abyte5[] = aByteArray912;
				for (int k2 = 0; k2 <= i1; k2++)
					abyte5[k2] = abyte2[k2 - l1 & i1];

				background_2.aByteArray1450 = abyte5;
				aByteArray912 = abyte2;
				Rasterizer.resetTexture(34);
			}
			if (Rasterizer.lastTexture[40] >= j) {
				Background background_2 = Rasterizer.textureImages[40];
				int i1 = background_2.anInt1452 * background_2.anInt1453 - 1;
				int l1 = background_2.anInt1452 * anInt945 * 1;
				byte abyte2[] = background_2.aByteArray1450;
				byte abyte5[] = aByteArray912;
				for (int k2 = 0; k2 <= i1; k2++)
					abyte5[k2] = abyte2[k2 - l1 & i1];

				background_2.aByteArray1450 = abyte5;
				aByteArray912 = abyte2;
				Rasterizer.resetTexture(40);
			}
		}
	}

	private void method38() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = myPlayerIndex;
			else
				j = playerIndices[i];
			Player player = playerArray[j];
			if (player != null && player.textCycle > 0) {
				player.textCycle--;
				if (player.textCycle == 0)
					player.textSpoken = null;
			}
		}
		for (int k = 0; k < npcCount; k++) {
			int l = npcIndices[k];
			Npc npc = npcArray[l];
			if (npc != null && npc.textCycle > 0) {
				npc.textCycle--;
				if (npc.textCycle == 0)
					npc.textSpoken = null;
			}
		}
	}

	private void calcCameraPos() {
		int i = (anInt1098 << 7) + 64;
		int j = (anInt1099 << 7) + 64;
		int k = method42(plane, j, i) - anInt1100;
		if (xCameraPos < i) {
			xCameraPos += anInt1101 + ((i - xCameraPos) * anInt1102) / 1000;
			if (xCameraPos > i)
				xCameraPos = i;
		}
		if (xCameraPos > i) {
			xCameraPos -= anInt1101 + ((xCameraPos - i) * anInt1102) / 1000;
			if (xCameraPos < i)
				xCameraPos = i;
		}
		if (zCameraPos < k) {
			zCameraPos += anInt1101 + ((k - zCameraPos) * anInt1102) / 1000;
			if (zCameraPos > k)
				zCameraPos = k;
		}
		if (zCameraPos > k) {
			zCameraPos -= anInt1101 + ((zCameraPos - k) * anInt1102) / 1000;
			if (zCameraPos < k)
				zCameraPos = k;
		}
		if (yCameraPos < j) {
			yCameraPos += anInt1101 + ((j - yCameraPos) * anInt1102) / 1000;
			if (yCameraPos > j)
				yCameraPos = j;
		}
		if (yCameraPos > j) {
			yCameraPos -= anInt1101 + ((yCameraPos - j) * anInt1102) / 1000;
			if (yCameraPos < j)
				yCameraPos = j;
		}
		i = (anInt995 << 7) + 64;
		j = (anInt996 << 7) + 64;
		k = method42(plane, j, i) - anInt997;
		int l = i - xCameraPos;
		int i1 = k - zCameraPos;
		int j1 = j - yCameraPos;
		int k1 = (int) Math.sqrt(l * l + j1 * j1);
		int l1 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
		int i2 = (int) (Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;
		if (l1 < 128)
			l1 = 128;
		if (l1 > 383)
			l1 = 383;
		if (yCameraCurve < l1) {
			yCameraCurve += anInt998 + ((l1 - yCameraCurve) * anInt999) / 1000;
			if (yCameraCurve > l1)
				yCameraCurve = l1;
		}
		if (yCameraCurve > l1) {
			yCameraCurve -= anInt998 + ((yCameraCurve - l1) * anInt999) / 1000;
			if (yCameraCurve < l1)
				yCameraCurve = l1;
		}
		int j2 = i2 - xCameraCurve;
		if (j2 > 1024)
			j2 -= 2048;
		if (j2 < -1024)
			j2 += 2048;
		if (j2 > 0) {
			xCameraCurve += anInt998 + (j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		if (j2 < 0) {
			xCameraCurve -= anInt998 + (-j2 * anInt999) / 1000;
			xCameraCurve &= 0x7ff;
		}
		int k2 = i2 - xCameraCurve;
		if (k2 > 1024)
			k2 -= 2048;
		if (k2 < -1024)
			k2 += 2048;
		if (k2 < 0 && j2 > 0 || k2 > 0 && j2 < 0)
			xCameraCurve = i2;
	}

	private void drawMenu(int xOffSet, int yOffSet) {
		int xPos = menuOffsetX - (xOffSet - 4);
		int yPos = (-yOffSet + 4) + menuOffsetY;
		int menuW = menuWidth;
		int menuH = menuHeight + 1;
		inputTaken = true;
		tabAreaAltered = true;
		if (newMenus) {
			// Border
			DrawingArea.method338(yPos + 2, menuH - 4, 256, 0x706a5e, menuW, xPos);
			DrawingArea.method338(yPos + 1, menuH - 2, 256, 0x706a5e, menuW - 2, xPos + 1);
			DrawingArea.method338(yPos, menuH, 200, 0x706a5e, menuW - 4, xPos + 2);
			// Outer Border
			DrawingArea.method338(yPos + 1, menuH - 2, 256, 0xffffff, menuW - 6, xPos + 3); // 0x2d2822
			DrawingArea.method338(yPos + 2, menuH - 4, 256, 0xffffff, menuW - 4, xPos + 2); // 0x2d2822
			DrawingArea.method338(yPos + 3, menuH - 6, 256, 0xffffff, menuW - 2, xPos + 1); // 0x2d2822
			// Menu Fill Side Border
			DrawingArea.method338(yPos + 19, menuH - 22, 256, 0x524a3d, menuW - 4, xPos + 2);
			DrawingArea.method338(yPos + 20, menuH - 22, 256, 0x524a3d, menuW - 6, xPos + 3);
			// Menu Fill
			DrawingArea.method335(0x112329, yPos + 20, menuW - 6, menuH - 23, 230, xPos + 3);
			// Menu Header
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x2a291b, yPos + 2);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a261b, yPos + 3);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x252116, yPos + 4);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x211e15, yPos + 5);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1e1b12, yPos + 6);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1a170e, yPos + 7);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 2, 0x15120b, yPos + 8);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x100d08, yPos + 10);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 11);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x080703, yPos + 12);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 13);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 14);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 15);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 16);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 17);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a291b, yPos + 18);
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x564943, yPos + 19);
			newBoldFont.drawBasicString("Choose Option", xPos + 3, yPos + 14, 0xc6b895, 50);
		} else if (newMenus == false) {
			DrawingArea.drawPixels(menuH - 4, yPos + 2, xPos, 0x706a5e, menuW);
			DrawingArea.drawPixels(menuH - 2, yPos + 1, xPos + 1, 0x706a5e, menuW - 2);
			DrawingArea.drawPixels(menuH, yPos, xPos + 2, 0x706a5e, menuW - 4);
			DrawingArea.drawPixels(menuH - 2, yPos + 1, xPos + 3, 0x2d2822, menuW - 6);
			DrawingArea.drawPixels(menuH - 4, yPos + 2, xPos + 2, 0x2d2822, menuW - 4);
			DrawingArea.drawPixels(menuH - 6, yPos + 3, xPos + 1, 0x2d2822, menuW - 2);
			DrawingArea.drawPixels(menuH - 22, yPos + 19, xPos + 2, 0x524a3d, menuW - 4);
			DrawingArea.drawPixels(menuH - 22, yPos + 20, xPos + 3, 0x524a3d, menuW - 6);
			DrawingArea.drawPixels(menuH - 23, yPos + 20, xPos + 3, 0x2b271c, menuW - 6);
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x2a291b, yPos + 2);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a261b, yPos + 3);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x252116, yPos + 4);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x211e15, yPos + 5);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1e1b12, yPos + 6);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1a170e, yPos + 7);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 2, 0x15120b, yPos + 8);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x100d08, yPos + 10);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 11);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x080703, yPos + 12);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 13);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 14);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 15);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 16);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 17);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a291b, yPos + 18);
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x564943, yPos + 19);
			chatTextDrawingArea.method385(0xc6b895, "Choose Option", yPos + 14, xPos + 3);
		}
		int mouseX = super.mouseX - (xOffSet);
		int mouseY = (-yOffSet) + super.mouseY;
		for (int l1 = 0; l1 < menuActionRow; l1++) {
			int textY = yPos + 31 + (menuActionRow - 1 - l1) * 15;
			int disColor = 0xc6b895;
			if (mouseX > xPos && mouseX < xPos + menuW && mouseY > textY - 13 && mouseY < textY + 3) {
				DrawingArea.drawPixels(15, textY - 11, xPos + 3, 0x6f695d, menuWidth - 6);
				disColor = 0xeee5c6;
				if (newCursors)
					detectCursor(l1);
			}
			// chatTextDrawingArea.method389(true, xPos + 3, disColor,
			// menuActionName[l1], textY);
			newBoldFont.drawBasicString(menuActionName[l1], xPos + 3, textY, disColor, 0);
		}
	}

	private void drawMenu() {
		int i = menuOffsetX;
		int j = menuOffsetY;
		int k = menuWidth;
		int j1 = super.mouseX;
		int k1 = super.mouseY;
		int l = menuHeight + 1;
		int xPos = menuOffsetX - (-1);
		int yPos = (+4) + menuOffsetY;
		int menuW = menuWidth;
		int menuH = menuHeight + 1;
		if (menuScreenArea == 1 && (clientSize > 0)) {
			i += 519; // +extraWidth;
			j += 168; // +extraHeight;
		}
		if (menuScreenArea == 2 && (clientSize > 0)) {
			j += 338;
		}
		if (menuScreenArea == 3 && (clientSize > 0)) {
			i += 515;
			j += 0;
		}
		if (menuScreenArea == 0) {
			j1 -= 4;
			k1 -= 4;
		}
		if (menuScreenArea == 1) {
			if (!(clientSize > 0)) {
				j1 -= 519;
				k1 -= 168;
			}
		}
		if (menuScreenArea == 2) {
			if (!(clientSize > 0)) {
				j1 -= 17;
				k1 -= 338;
			}
		}
		if (menuScreenArea == 3 && !(clientSize > 0)) {
			j1 -= 515;
			k1 -= 0;
		}
//		 DrawingArea.drawPixels(height, yPos, xPos, color, width);
//		 DrawingArea.fillPixels(xPos, width, height, color, yPos);
		if (newMenus) {
			// Border
			DrawingArea.method338(yPos + 2, menuH - 4, 256, 0x706a5e, menuW, xPos);
			DrawingArea.method338(yPos + 1, menuH - 2, 256, 0x706a5e, menuW - 2, xPos + 1);
			DrawingArea.method338(yPos, menuH, 200, 0x706a5e, menuW - 4, xPos + 2);
			// Outer Border
			DrawingArea.method338(yPos + 1, menuH - 2, 256, 0xffffff, menuW - 6, xPos + 3); // 0x2d2822
			DrawingArea.method338(yPos + 2, menuH - 4, 256, 0xffffff, menuW - 4, xPos + 2); // 0x2d2822
			DrawingArea.method338(yPos + 3, menuH - 6, 256, 0xffffff, menuW - 2, xPos + 1); // 0x2d2822
			// Menu Fill Side Border
			DrawingArea.method338(yPos + 19, menuH - 22, 256, 0x524a3d, menuW - 4, xPos + 2);
			DrawingArea.method338(yPos + 20, menuH - 22, 256, 0x524a3d, menuW - 6, xPos + 3);
			// Menu Fill
			DrawingArea.method335(0x112329, yPos + 20, menuW - 6, menuH - 23, 230, xPos + 3);
			// Menu Header
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x2a291b, yPos + 2);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a261b, yPos + 3);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x252116, yPos + 4);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x211e15, yPos + 5);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1e1b12, yPos + 6);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1a170e, yPos + 7);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 2, 0x15120b, yPos + 8);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x100d08, yPos + 10);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 11);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x080703, yPos + 12);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 13);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 14);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 15);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 16);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 17);
			DrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a291b, yPos + 18);
			DrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x564943, yPos + 19);
			newBoldFont.drawBasicString("Choose Option", xPos + 3, yPos + 14, 0xc6b895, 50);
		} else if (newMenus == false) {
			DrawingArea.drawPixels(l - 4, j + 2, i, 0x706a5e, k);
			DrawingArea.drawPixels(l - 2, j + 1, i + 1, 0x706a5e, k - 2);
			DrawingArea.drawPixels(l, j, i + 2, 0x706a5e, k - 4);
			DrawingArea.drawPixels(l - 2, j + 1, i + 3, 0x2d2822, k - 6);
			DrawingArea.drawPixels(l - 4, j + 2, i + 2, 0x2d2822, k - 4);
			DrawingArea.drawPixels(l - 6, j + 3, i + 1, 0x2d2822, k - 2);
			DrawingArea.drawPixels(l - 22, j + 19, i + 2, 0x524a3d, k - 4);
			DrawingArea.drawPixels(l - 22, j + 20, i + 3, 0x524a3d, k - 6);
			DrawingArea.drawPixels(l - 23, j + 20, i + 3, 0x2b271c, k - 6);
			DrawingArea.fillPixels(i + 3, k - 6, 1, 0x2a291b, j + 2);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x2a261b, j + 3);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x252116, j + 4);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x211e15, j + 5);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x1e1b12, j + 6);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x1a170e, j + 7);
			DrawingArea.fillPixels(i + 2, k - 4, 2, 0x15120b, j + 8);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x100d08, j + 10);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x090a04, j + 11);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x080703, j + 12);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x090a04, j + 13);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x070802, j + 14);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x090a04, j + 15);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x070802, j + 16);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x090a04, j + 17);
			DrawingArea.fillPixels(i + 2, k - 4, 1, 0x2a291b, j + 18);
			DrawingArea.fillPixels(i + 3, k - 6, 1, 0x564943, j + 19);
			chatTextDrawingArea.method385(0xc6b895, "Choose Option", j + 14, i + 3);
		}
		for (int l1 = 0; l1 < menuActionRow; l1++) {
			int i2 = j + 31 + (menuActionRow - 1 - l1) * 15;
			int j2 = 0xc6b895;
			if (j1 > i && j1 < i + k && k1 > i2 - 13 && k1 < i2 + 3) {

				DrawingArea.drawPixels(15, i2 - 11, i + 3, 0x6f695d, menuWidth - 6);
				j2 = 0xeee5c6;
				if (newCursors)
					detectCursor(l1);
			}
			newBoldFont.drawBasicString(menuActionName[l1], i + 3, i2, j2, 0);
		}
	}

	private void addFriend(long l) {
		try {
			if (l == 0L)
				return;
			if (friendsCount >= 100 && anInt1046 != 1) {
				pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
				return;
			}
			if (friendsCount >= 200) {
				pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
				return;
			}
			String s = TextClass.fixName(TextClass.nameForLong(l));
			for (int i = 0; i < friendsCount; i++)
				if (friendsListAsLongs[i] == l) {
					pushMessage(s + " is already on your friend list", 0, "");
					return;
				}
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage("Please remove " + s + " from your ignore list first", 0, "");
					return;
				}

			if (s.equals(myPlayer.name)) {
				pushMessage("You can't add yourself!", 0, "");
				return;
			} else {
				friendsList[friendsCount] = s;
				friendsListAsLongs[friendsCount] = l;
				friendsNodeIDs[friendsCount] = 0;
				friendsCount++;
				buffer.createFrame(188);
				buffer.writeQWord(l);
				return;
			}
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("15283, " + (byte) 68 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private int method42(int i, int j, int k) {
		int l = k >> 7;
		int i1 = j >> 7;
		if (l < 0 || i1 < 0 || l > 103 || i1 > 103)
			return 0;
		int j1 = i;
		if (j1 < 3 && (byteGroundArray[1][l][i1] & 2) == 2)
			j1++;
		int k1 = k & 0x7f;
		int l1 = j & 0x7f;
		int i2 = intGroundArray[j1][l][i1] * (128 - k1) + intGroundArray[j1][l + 1][i1] * k1 >> 7;
		int j2 = intGroundArray[j1][l][i1 + 1] * (128 - k1) + intGroundArray[j1][l + 1][i1 + 1] * k1 >> 7;
		return i2 * (128 - l1) + j2 * l1 >> 7;
	}

	private static String intToKOrMil(int j) {
		if (j == 2126432612)
			return "@inf@";
		if (j < 0x186a0)
			return String.valueOf(j);
		if (j < 0x989680)
			return j / 1000 + "K";
		else
			return j / 0xf4240 + "M";
	}

	private void resetLogout() {
		try {
			if (socketStream != null)
				socketStream.close();
		} catch (Exception _ex) {
		}
		socketStream = null;
		alertHandler.alert = null;
		loggedIn = false;
		loginScreenState = 0;
		clickedQuickPrayers = false;
		// myUsername = "";
		// myPassword = "";
		titleScreenFade = 256;
		fadeTitleBox = -500;
		unlinkMRUNodes();
		worldController.initToNull();
		for (int i = 0; i < 4; i++)
			aClass11Array1230[i].method210();
		System.gc();
		stopMidi();
		currentSong = -1;
		nextSong = -1;
		prevSong = 0;
		if (newCursors)
			super.setCursor(0);
	}

	public int getXPForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points = (int) (points + Math.floor(lvl + 300.0D * Math.pow(2.0D, lvl / 7.0D)));
			if (lvl >= level) {
				return output;
			}
			output = (int) Math.floor(points / 4);
		}
		return 0;
	}

	public String[] skillNames = { "Attack", "Hitpoints", "Mining", "Strength", "Agility", "Smithing", "Defence", "Herblore", "Fishing", "Range", "Thieving", "Cooking", "Prayer", "Crafting", "Firemaking", "Magic", "Fletching", "Woodcutting", "Runecrafting", "Slayer", "Farming", "Construction", "Hunter", "Summoning", "Dungeoneering" };

	public String setMessage(int level) {
		String[] messages = new String[6];
		String message = "";
		int[] skillID = { 0, 3, 14, 2, 16, 13, 1, 15, 10, 4, 17, 7, 5, 12, 11, 6, 9, 8, 20, 18, 19, 21, 22, 23, 24 };
		int init = SkillConstants.goalData[skillID[level]][0];
		int goal = SkillConstants.goalData[skillID[level]][1];
		messages[0] = (skillNames[level] + ": " + currentStats[skillID[level]] + "/" + maxStats[skillID[level]] + "\\n");
		messages[1] = ("Current XP:" + (maxStats[skillID[level]] < 99 ? "     " : "") + String.format("%, d", currentExp[skillID[level]]) + "\\n");
		messages[2] = ("Next level:      " + String.format("%, d", getXPForLevel(maxStats[skillID[level]] + 1)) + "\\n");
		messages[3] = ("Remainder:     " + String.format("%, d", getXPForLevel(maxStats[skillID[level]] + 1) - currentExp[skillID[level]]));
		message = messages[0] + messages[1];
		boolean newLine = false;
		if (maxStats[skillID[level]] < 99) {
			message += messages[2] + messages[3];
			newLine = true;
		}

		if ((currentExp[skillID[level]] < 200000000) && init > -1 && goal > -1) {
			messages[4] = ((newLine ? "\\n" : "") + SkillConstants.goalType + "     " + (SkillConstants.goalType.endsWith("Level: ") ? Integer.valueOf(getLevelForXP(goal)) : String.format("%,d", goal)) + "\\n");
			int remainder = goal - currentExp[skillID[level]] - (SkillConstants.goalType.endsWith("Level: ") ? 1 : 0);
			if (remainder < 0)
				remainder = 0;
			messages[5] = ("Remainder:       " + String.format("%,d", remainder));
			SkillConstants.goalData[skillID[level]][2] = (int) ( ((currentExp[skillID[level]] - init) / (double) (goal - init)) * 100 );
			if (SkillConstants.goalData[skillID[level]][2] > 100)
				SkillConstants.goalData[skillID[level]][2] = 100;
			message += messages[4] + messages[5];
		}
		return message;
	}

	public int getLevelForXP(int exp) {
		int points = 0;
		int output = 0;
		if (exp > 13034430)
			return 99;
		for (int lvl = 1; lvl <= 99; lvl++) {
			points = (int) (points + Math.floor(lvl + 300.0D * Math.pow(2.0D, lvl / 7.0D)));
			output = (int) Math.floor(points / 4);
			if (output >= exp) {
				return lvl;
			}
		}
		return 0;
	}

	private void method45() {
		aBoolean1031 = true;
		for (int j = 0; j < 7; j++) {
			anIntArray1065[j] = -1;
			for (int k = 0; k < IdentityKitDefinition.length; k++) {
				if (IdentityKitDefinition.cache[k].aBoolean662 || IdentityKitDefinition.cache[k].anInt657 != j + (aBoolean1047 ? 0 : 7))
					continue;
				anIntArray1065[j] = k;
				break;
			}
		}
	}

	private void method46(int i, Buffer buffer) {
		while (buffer.bitPosition + 21 < i * 8) {
			int k = buffer.readBits(14);
			if (k == 16383)
				break;
			if (npcArray[k] == null)
				npcArray[k] = new Npc();
			Npc npc = npcArray[k];
			npcIndices[npcCount++] = k;
			npc.anInt1537 = loopCycle;
			int l = buffer.readBits(5);
			if (l > 15)
				l -= 32;
			int i1 = buffer.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = buffer.readBits(1);
			npc.desc = EntityDefinition.forID(buffer.readBits(14));
			int k1 = buffer.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = k;
			npc.anInt1540 = npc.desc.aByte68;
			npc.anInt1504 = npc.desc.anInt79;
			npc.anInt1554 = npc.desc.walkAnim;
			npc.anInt1555 = npc.desc.anInt58;
			npc.anInt1556 = npc.desc.anInt83;
			npc.anInt1557 = npc.desc.anInt55;
			npc.anInt1511 = npc.desc.standAnim;
			npc.setPos(myPlayer.smallX[0] + i1, myPlayer.smallY[0] + l, j1 == 1);
		}
		buffer.finishBitAccess();
	}

	private boolean typingPassword = false;
	private TextDrawingArea aTextDrawingArea_1273;
	public RSFont newFancyFont;

	public void processGameLoop() {
		if (rsAlreadyLoaded || loadingError || genericLoadingError)
			return;
		loopCycle++;
		if (!loggedIn)
			loginRenderer.processLoginScreen();
		else
			mainGameProcessor();
		processOnDemandQueue();
	}

	private void method47(boolean flag) {
		if (myPlayer.x >> 7 == destX && myPlayer.y >> 7 == destY)
			destX = 0;
		int j = playerCount;
		if (flag)
			j = 1;
		for (int l = 0; l < j; l++) {
			Player player;
			int i1;
			if (flag) {
				player = myPlayer;
				i1 = myPlayerIndex << 14;
			} else {
				player = playerArray[playerIndices[l]];
				i1 = playerIndices[l] << 14;
			}
			if (player == null || !player.isVisible())
				continue;
			player.aBoolean1699 = (lowMem && playerCount > 50 || playerCount > 200) && !flag && player.anInt1517 == player.anInt1511;
			int j1 = player.x >> 7;
			int k1 = player.y >> 7;
			if (j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104)
				continue;
			if (player.aModel_1714 != null && loopCycle >= player.anInt1707 && loopCycle < player.anInt1708) {
				player.aBoolean1699 = false;
				player.anInt1709 = method42(plane, player.y, player.x);
				worldController.method286(plane, player.y, player, player.anInt1552, player.anInt1722, player.x, player.anInt1709, player.anInt1719, player.anInt1721, i1, player.anInt1720);
				continue;
			}
			if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
				if (anIntArrayArray929[j1][k1] == anInt1265)
					continue;
				anIntArrayArray929[j1][k1] = anInt1265;
			}
			player.anInt1709 = method42(plane, player.y, player.x);
			worldController.method285(plane, player.anInt1552, player.anInt1709, i1, player.y, 60, player.x, player, player.aBoolean1541);
		}
	}

	private boolean promptUserForInput(RSInterface class9) {
		int j = class9.contentType;
		if (anInt900 == 2) {
			if (j == 201) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 1;
				aString1121 = "Enter name of friend to add to list";
			}
			if (j == 202) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 2;
				aString1121 = "Enter name of friend to delete from list";
			}
		}
		if (j == 205) {
			anInt1011 = 250;
			return true;
		}
		if (j == 677) {
			inputTaken = true;
			messagePromptRaised = true;
			amountOrNameInput = "";
			promptInput = "";
			inputDialogState = 0;
			interfaceButtonAction = 6200;
			friendsListAction = -1;
			aString1121 = "Enter name of the player you would like kicked.";
		}
		if (j == 550) {
			if (RSInterface.interfaceCache[18135].message.startsWith("Join")) {
				inputTaken = true;
				inputDialogState = 0;
				messagePromptRaised = true;
				promptInput = "";
				friendsListAction = 14;
				interfaceButtonAction = -1;
				aString1121 = "Enter the name of the chat you wish to join";
			} else {
				buffer.createFrame(185);
				buffer.writeWord(49627);
			}
		}
		if (j == 997) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 12;
			aString1121 = "Enter your target level";
		}
		if (j == 998) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 13;
			aString1121 = "Enter your target experience";
		}
		if (j == 999) {
			if (SkillConstants.selectedSkillId > -1) {
				SkillConstants.goalData[SkillConstants.selectedSkillId][0] = -1;
				SkillConstants.goalData[SkillConstants.selectedSkillId][1] = -1;
				SkillConstants.goalData[SkillConstants.selectedSkillId][2] = -1;
				saveGoals(myUsername);
			}
		}

		if (class9.id == RSInterface.BANK_CHILDREN_BASE_ID + 7) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 11;
			aString1121 = "Enter a keyword to search for";
		}

		if (j == 501) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 4;
			aString1121 = "Enter name of player to add to list";
		}
		if (j == 22222) {
			inputTaken = true;
			messagePromptRaised = true;
			amountOrNameInput = "";
			promptInput = "";
			inputDialogState = 0;
			interfaceButtonAction = 6199;
			friendsListAction = -1;
			aString1121 = "Enter a name for the clan chat.";
		}
		if (j == 502) {
			inputTaken = true;
			inputDialogState = 0;
			messagePromptRaised = true;
			promptInput = "";
			friendsListAction = 5;
			aString1121 = "Enter name of player to delete from list";
		}
		if (j >= 300 && j <= 313) {
			int k = (j - 300) / 2;
			int j1 = j & 1;
			int i2 = anIntArray1065[k];
			if (i2 != -1) {
				do {
					if (j1 == 0 && --i2 < 0)
						i2 = IdentityKitDefinition.length - 1;
					if (j1 == 1 && ++i2 >= IdentityKitDefinition.length)
						i2 = 0;
				} while (IdentityKitDefinition.cache[i2].aBoolean662 || IdentityKitDefinition.cache[i2].anInt657 != k + (aBoolean1047 ? 0 : 7));
				anIntArray1065[k] = i2;
				aBoolean1031 = true;
			}
		}
		if (j >= 314 && j <= 323) {
			int l = (j - 314) / 2;
			int k1 = j & 1;
			int j2 = anIntArray990[l];
			if (k1 == 0 && --j2 < 0)
				j2 = anIntArrayArray1003[l].length - 1;
			if (k1 == 1 && ++j2 >= anIntArrayArray1003[l].length)
				j2 = 0;
			anIntArray990[l] = j2;
			aBoolean1031 = true;
		}
		if (j == 324 && !aBoolean1047) {
			aBoolean1047 = true;
			method45();
		}
		if (j == 325 && aBoolean1047) {
			aBoolean1047 = false;
			method45();
		}
		if (j == 326) {
			buffer.createFrame(101);
			buffer.writeWordBigEndian(aBoolean1047 ? 0 : 1);
			for (int i1 = 0; i1 < 7; i1++)
				buffer.writeWordBigEndian(anIntArray1065[i1]);

			for (int l1 = 0; l1 < 5; l1++)
				buffer.writeWordBigEndian(anIntArray990[l1]);

			return true;
		}
		if (j == 613)
			canMute = !canMute;
		if (j >= 601 && j <= 612) {
			clearTopInterfaces();
			if (reportAbuseInput.length() > 0) {
				buffer.createFrame(218);
				buffer.writeQWord(TextClass.longForName(reportAbuseInput));
				buffer.writeWordBigEndian(j - 601);
				buffer.writeWordBigEndian(canMute ? 1 : 0);
			}
		}
		return false;
	}

	private void method49(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Player player = playerArray[k];
			int l = buffer.readUnsignedByte();
			if ((l & 0x40) != 0)
				l += buffer.readUnsignedByte() << 8;
			method107(l, k, buffer, player);
		}
	}

	public void method50(int i, int k, int l, int i1, int j1) {
		int k1 = worldController.method300(j1, l, i);
		if ((k1 ^ 0xffffffffffffffffL) != -1L) {
			int l1 = worldController.method304(j1, l, i, k1);
			int k2 = l1 >> 6 & 3;
			int i3 = l1 & 0x1f;
			int k3 = k;
			if (k1 > 0)
				k3 = i1;
			int ai[] = aClass30_Sub2_Sub1_Sub1_1263.myPixels;
			int k4 = 24624 + l * 4 + (103 - i) * 512 * 4;
			int i5 = k1 >> 14 & 0x7fff;
			ObjectDefinition class46_2 = ObjectDefinition.forID(i5);
			if ((class46_2.anInt758 ^ 0xffffffff) == 0) {
				if (i3 == 0 || i3 == 2) {
					if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[1024 + k4] = k3;
						ai[1536 + k4] = k3;
					} else if ((k2 ^ 0xffffffff) == -2) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[3 + k4] = k3;
					} else if (k2 == 2) {
						ai[k4 - -3] = k3;
						ai[3 + (k4 + 512)] = k3;
						ai[3 + (k4 + 1024)] = k3;
						ai[1536 + (k4 - -3)] = k3;
					} else if (k2 == 3) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[2 + k4 + 1536] = k3;
						ai[k4 + 1539] = k3;
					}
				}
				if (i3 == 3)
					if (k2 == 0)
						ai[k4] = k3;
					else if (k2 == 1)
						ai[k4 + 3] = k3;
					else if (k2 == 2)
						ai[k4 + 3 + 1536] = k3;
					else if (k2 == 3)
						ai[k4 + 1536] = k3;
				if (i3 == 2)
					if (k2 == 3) {
						ai[k4] = k3;
						ai[k4 + 512] = k3;
						ai[k4 + 1024] = k3;
						ai[k4 + 1536] = k3;
					} else if (k2 == 0) {
						ai[k4] = k3;
						ai[k4 + 1] = k3;
						ai[k4 + 2] = k3;
						ai[k4 + 3] = k3;
					} else if (k2 == 1) {
						ai[k4 + 3] = k3;
						ai[k4 + 3 + 512] = k3;
						ai[k4 + 3 + 1024] = k3;
						ai[k4 + 3 + 1536] = k3;
					} else if (k2 == 2) {
						ai[k4 + 1536] = k3;
						ai[k4 + 1536 + 1] = k3;
						ai[k4 + 1536 + 2] = k3;
						ai[k4 + 1536 + 3] = k3;
					}
			}
		}
		k1 = worldController.method302(j1, l, i);
		if (k1 != 0) {
			int i2 = worldController.method304(j1, l, i, k1);
			int l2 = i2 >> 6 & 3;
			int j3 = i2 & 0x1f;
			int l3 = k1 >> 14 & 0x7fff;
			ObjectDefinition class46_1 = ObjectDefinition.forID(l3);
			if (class46_1.anInt758 != -1) {
				Background background_1 = mapScenes[class46_1.anInt758];
				if (background_1 != null) {
					int j5 = (class46_1.anInt744 * 4 - background_1.anInt1452) / 2;
					int k5 = (class46_1.anInt761 * 4 - background_1.anInt1453) / 2;
					background_1.drawBackground(48 + l * 4 + j5, 48 + (104 - i - class46_1.anInt761) * 4 + k5);
				}
			} else if (j3 == 9) {
				int l4 = 0xeeeeee;
				if (k1 > 0)
					l4 = 0xee0000;
				int ai1[] = aClass30_Sub2_Sub1_Sub1_1263.myPixels;
				int l5 = 24624 + l * 4 + (103 - i) * 512 * 4;
				if (l2 == 0 || l2 == 2) {
					ai1[l5 + 1536] = l4;
					ai1[l5 + 1024 + 1] = l4;
					ai1[l5 + 512 + 2] = l4;
					ai1[l5 + 3] = l4;
				} else {
					ai1[l5] = l4;
					ai1[l5 + 512 + 1] = l4;
					ai1[l5 + 1024 + 2] = l4;
					ai1[l5 + 1536 + 3] = l4;
				}
			}
		}
		k1 = worldController.method303(j1, l, i);
		if (k1 != 0) {
			int j2 = k1 >> 14 & 0x7fff;
			ObjectDefinition class46 = ObjectDefinition.forID(j2);
			if (class46.anInt758 != -1) {
				Background background = mapScenes[class46.anInt758];
				if (background != null) {
					int i4 = (class46.anInt744 * 4 - background.anInt1452) / 2;
					int j4 = (class46.anInt761 * 4 - background.anInt1453) / 2;
					background.drawBackground(48 + l * 4 + i4, 48 + (104 - i - class46.anInt761) * 4 + j4);
				}
			}
		}
	}

	private void loadTitleScreen() {
		aBackground_966 = new Background(titleStreamLoader, "titlebox", 0);
		aBackground_967 = new Background(titleStreamLoader, "titlebutton", 0);
		aBackgroundArray1152s = new Background[12];
		int j = 0;
		try {
			j = Integer.parseInt(getParameter("fl_icon"));
		} catch (Exception _ex) {
		}
		if (j == 0) {
			for (int k = 0; k < 12; k++)
				aBackgroundArray1152s[k] = new Background(titleStreamLoader, "runes", k);

		} else {
			for (int l = 0; l < 12; l++)
				aBackgroundArray1152s[l] = new Background(titleStreamLoader, "runes", 12 + (l & 3));

		}
		aClass30_Sub2_Sub1_Sub1_1201 = new Sprite(128, 265);
		aClass30_Sub2_Sub1_Sub1_1202 = new Sprite(128, 265);
		System.arraycopy(aRSImageProducer_1110.raster, 0, aClass30_Sub2_Sub1_Sub1_1201.myPixels, 0, 33920);

		System.arraycopy(aRSImageProducer_1111.raster, 0, aClass30_Sub2_Sub1_Sub1_1202.myPixels, 0, 33920);

		anIntArray851 = new int[256];
		for (int k1 = 0; k1 < 64; k1++)
			anIntArray851[k1] = k1 * 0x40000;

		for (int l1 = 0; l1 < 64; l1++)
			anIntArray851[l1 + 64] = 0xff0000 + 1024 * l1;

		for (int i2 = 0; i2 < 64; i2++)
			anIntArray851[i2 + 128] = 0xffff00 + 4 * i2;

		for (int j2 = 0; j2 < 64; j2++)
			anIntArray851[j2 + 192] = 0xffffff;

		anIntArray852 = new int[256];
		for (int k2 = 0; k2 < 64; k2++)
			anIntArray852[k2] = k2 * 1024;

		for (int l2 = 0; l2 < 64; l2++)
			anIntArray852[l2 + 64] = 65280 + 4 * l2;

		for (int i3 = 0; i3 < 64; i3++)
			anIntArray852[i3 + 128] = 65535 + 0x40000 * i3;

		for (int j3 = 0; j3 < 64; j3++)
			anIntArray852[j3 + 192] = 0xffffff;

		anIntArray853 = new int[256];
		for (int k3 = 0; k3 < 64; k3++)
			anIntArray853[k3] = k3 * 4;

		for (int l3 = 0; l3 < 64; l3++)
			anIntArray853[l3 + 64] = 255 + 0x40000 * l3;

		for (int i4 = 0; i4 < 64; i4++)
			anIntArray853[i4 + 128] = 0xff00ff + 1024 * i4;

		for (int j4 = 0; j4 < 64; j4++)
			anIntArray853[j4 + 192] = 0xffffff;

		anIntArray850 = new int[256];
		anIntArray1190 = new int[32768];
		anIntArray1191 = new int[32768];
		randomizeBackground(null);
		anIntArray828 = new int[32768];
		anIntArray829 = new int[32768];
		// drawLoadingText(10, Config.LOADING_MESSAGE);
		if (!aBoolean831) {
			drawFlames = true;
			aBoolean831 = true;
			startRunnable(this, 2);
		}
	}

	private static void setHighMem() {
		Scene.lowMem = false;
		Rasterizer.lowMem = false;
		lowMem = false;
		ObjectManager.lowMem = false;
		ObjectDefinition.lowMem = false;
	}
	
	private static void setLowMem() {
		Scene.lowMem = true;
		Rasterizer.lowMem = true;
		lowMem = true;
		ObjectManager.lowMem = false;
		ObjectDefinition.lowMem = true;
	}

	public static void main(String args[]) {
		try {
			nodeID = 10;
			portOff = 0;
			setLowMem();
			isMembers = true;
			Signlink.storeid = 32;
			Signlink.startpriv(InetAddress.getLocalHost());
			instance = new Client();
			instance.createClientFrame(765, 503);
			loadGameSettings();
		} catch (Exception exception) {
		}
	}

	private void loadingStages() {
		if (lowMem && loadingStage == 2 && ObjectManager.anInt131 != plane) {
			aRSImageProducer_1165.setCanvas();
			imageLoader[62].drawSprite(0, 0);
			aRSImageProducer_1165.drawGraphics(4, super.graphics, 4);
			loadingStage = 1;
			aLong824 = System.currentTimeMillis();
		}
		if (loadingStage == 1) {
			int j = method54();
			if (j != 0 && System.currentTimeMillis() - aLong824 > 0x57e40L) {
				Signlink.reporterror(myUsername + " glcfb " + aLong1215 + "," + j + "," + lowMem + "," + decompressors[0] + "," + onDemandFetcher.getNodeCount() + "," + plane + "," + anInt1069 + "," + anInt1070);
				aLong824 = System.currentTimeMillis();
			}
		}
		if (loadingStage == 2 && plane != anInt985) {
			anInt985 = plane;
			method24(plane);
		}
	}

	private int method54() {
		if (!floorMaps.equals("") || !objectMaps.equals("")) {
			floorMaps = "";
			objectMaps = "";
		}
		for (int i = 0; i < aByteArrayArray1183.length; i++) {
			floorMaps += "  " + anIntArray1235[i];
			objectMaps += "  " + anIntArray1236[i];
			if (aByteArrayArray1183[i] == null && anIntArray1235[i] != -1)
				return -1;
			if (aByteArrayArray1247[i] == null && anIntArray1236[i] != -1)
				return -2;
		}
		boolean flag = true;
		for (int j = 0; j < aByteArrayArray1183.length; j++) {
			byte abyte0[] = aByteArrayArray1247[j];
			if (abyte0 != null) {
				int k = ((anIntArray1234[j] >> 8) << 6) - baseX;
				int l = ((anIntArray1234[j] & 0xff) << 6) - baseY;
				if (aBoolean1159) {
					k = 10;
					l = 10;
				}
				flag &= ObjectManager.method189(k, abyte0, l);
			}
		}
		if (!flag)
			return -3;
		if (aBoolean1080) {
			return -4;
		} else {
			loadingStage = 2;
			ObjectManager.anInt131 = plane;
			method22();
			buffer.createFrame(121);
			return 0;
		}
	}

	private void method55() {
		for (Animable_Sub4 class30_sub2_sub4_sub4 = (Animable_Sub4) aClass19_1013.reverseGetFirst(); class30_sub2_sub4_sub4 != null; class30_sub2_sub4_sub4 = (Animable_Sub4) aClass19_1013.reverseGetNext())
			if (class30_sub2_sub4_sub4.anInt1597 != plane || loopCycle > class30_sub2_sub4_sub4.anInt1572)
				class30_sub2_sub4_sub4.unlink();
			else if (loopCycle >= class30_sub2_sub4_sub4.anInt1571) {
				if (class30_sub2_sub4_sub4.anInt1590 > 0) {
					Npc npc = npcArray[class30_sub2_sub4_sub4.anInt1590 - 1];
					if (npc != null && npc.x >= 0 && npc.x < 13312 && npc.y >= 0 && npc.y < 13312)
						class30_sub2_sub4_sub4.method455(loopCycle, npc.y, method42(class30_sub2_sub4_sub4.anInt1597, npc.y, npc.x) - class30_sub2_sub4_sub4.anInt1583, npc.x);
				}
				if (class30_sub2_sub4_sub4.anInt1590 < 0) {
					int j = -class30_sub2_sub4_sub4.anInt1590 - 1;
					Player player;
					if (j == unknownInt10)
						player = myPlayer;
					else
						player = playerArray[j];
					if (player != null && player.x >= 0 && player.x < 13312 && player.y >= 0 && player.y < 13312)
						class30_sub2_sub4_sub4.method455(loopCycle, player.y, method42(class30_sub2_sub4_sub4.anInt1597, player.y, player.x) - class30_sub2_sub4_sub4.anInt1583, player.x);
				}
				class30_sub2_sub4_sub4.method456(anInt945);
				worldController.method285(plane, class30_sub2_sub4_sub4.anInt1595, (int) class30_sub2_sub4_sub4.aDouble1587, -1, (int) class30_sub2_sub4_sub4.aDouble1586, 60, (int) class30_sub2_sub4_sub4.aDouble1585, class30_sub2_sub4_sub4, false);
			}

	}

	public AppletContext getAppletContext() {
		if (Signlink.mainapp != null)
			return Signlink.mainapp.getAppletContext();
		else
			return super.getAppletContext();
	}

	private void drawLogo() {
		byte abyte0[] = titleStreamLoader.getDataForName("title.dat");
		Sprite sprite = new Sprite(abyte0, this);
		aRSImageProducer_1110.setCanvas();
		sprite.method346(0, 0);
		aRSImageProducer_1111.setCanvas();
		sprite.method346(-637, 0);
		aRSImageProducer_1107.setCanvas();
		sprite.method346(-128, 0);
		aRSImageProducer_1108.setCanvas();
		sprite.method346(-202, -371);
		aRSImageProducer_1109.setCanvas();
		sprite.method346(0, 0);
		aRSImageProducer_1112.setCanvas();
		sprite.method346(0, -265);
		aRSImageProducer_1113.setCanvas();
		sprite.method346(-562, -265);
		aRSImageProducer_1114.setCanvas();
		sprite.method346(-128, -171);
		aRSImageProducer_1115.setCanvas();
		sprite.method346(-562, -171);
		sprite = null;
		System.gc();
	}
	private void processOnDemandQueue()
	{
		do
		{
			OnDemandData onDemandData;
			do
			{
				onDemandData = onDemandFetcher.getNextNode();
				if(onDemandData == null)
					return;
				if(onDemandData.dataType == 0)
				{
					Model.load(onDemandData.buffer, onDemandData.ID);
					if((onDemandFetcher.getModelIndex(onDemandData.ID) & 0x62) != 0)
					{
						if(backDialogID != -1)
							inputTaken = true;
					}
				}
				if(onDemandData.dataType == 1) {
            		Class36.load(onDemandData.ID, onDemandData.buffer);
                }				
				if(onDemandData.dataType == 2 && onDemandData.ID == nextSong && onDemandData.buffer != null)
					saveMidi(songChanging, onDemandData.buffer);
				if(onDemandData.dataType == 3 && loadingStage == 1)
				{
					for(int i = 0; i < aByteArrayArray1183.length; i++)
					{
						if(anIntArray1235[i] == onDemandData.ID)
						{
							aByteArrayArray1183[i] = onDemandData.buffer;
							if(onDemandData.buffer == null)
								anIntArray1235[i] = -1;
							break;
						}
						if(anIntArray1236[i] != onDemandData.ID)
							continue;
						aByteArrayArray1247[i] = onDemandData.buffer;
						if(onDemandData.buffer == null)
							anIntArray1236[i] = -1;
						break;
					}

				}
			} while(onDemandData.dataType != 93 || !onDemandFetcher.method564(onDemandData.ID));
			ObjectManager.method173(new Buffer(onDemandData.buffer), onDemandFetcher);
		} while(true);
	}

	public void calcFlamesPosition() {
		char c = '\u0100';
		for (int j = 10; j < 117; j++) {
			int k = (int) (Math.random() * 100D);
			if (k < 50)
				anIntArray828[j + (c - 2 << 7)] = 255;
		}
		for (int l = 0; l < 100; l++) {
			int i1 = (int) (Math.random() * 124D) + 2;
			int k1 = (int) (Math.random() * 128D) + 128;
			int k2 = i1 + (k1 << 7);
			anIntArray828[k2] = 192;
		}

		for (int j1 = 1; j1 < c - 1; j1++) {
			for (int l1 = 1; l1 < 127; l1++) {
				int l2 = l1 + (j1 << 7);
				anIntArray829[l2] = (anIntArray828[l2 - 1] + anIntArray828[l2 + 1] + anIntArray828[l2 - 128] + anIntArray828[l2 + 128]) / 4;
			}

		}

		anInt1275 += 128;
		if (anInt1275 > anIntArray1190.length) {
			anInt1275 -= anIntArray1190.length;
			int i2 = (int) (Math.random() * 12D);
			randomizeBackground(aBackgroundArray1152s[i2]);
		}
		for (int j2 = 1; j2 < c - 1; j2++) {
			for (int i3 = 1; i3 < 127; i3++) {
				int k3 = i3 + (j2 << 7);
				int i4 = anIntArray829[k3 + 128] - anIntArray1190[k3 + anInt1275 & anIntArray1190.length - 1] / 5;
				if (i4 < 0)
					i4 = 0;
				anIntArray828[k3] = i4;
			}

		}

		System.arraycopy(anIntArray969, 1, anIntArray969, 0, c - 1);

		anIntArray969[c - 1] = (int) (Math.sin((double) loopCycle / 14D) * 16D + Math.sin((double) loopCycle / 15D) * 14D + Math.sin((double) loopCycle / 16D) * 12D);
		if (anInt1040 > 0)
			anInt1040 -= 4;
		if (anInt1041 > 0)
			anInt1041 -= 4;
		if (anInt1040 == 0 && anInt1041 == 0) {
			int l3 = (int) (Math.random() * 2000D);
			if (l3 == 0)
				anInt1040 = 1024;
			if (l3 == 1)
				anInt1041 = 1024;
		}
	}

	private void drawHeadIcon() {
		if (anInt855 != 2)
			return;
		calcEntityScreenPos((anInt934 - baseX << 7) + anInt937, anInt936 << 1, (anInt935 - (baseY << 7) + anInt938));
		if (spriteDrawX > -1 && loopCycle % 20 < 10)
			headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
	}

	private void mainGameProcessor() {
		checkSize();
		if (systemUpdateTimer > 1)
			systemUpdateTimer--;
		if (anInt1011 > 0)
			anInt1011--;
		for (int j = 0; j < 5; j++)
			if (!parsePacket())
				break;

		if (!loggedIn)
			return;
		synchronized (mouseDetection.syncObject) {
			if (flagged) {
				if (super.clickMode3 != 0 || mouseDetection.coordsIndex >= 40) {
					buffer.createFrame(45);
					buffer.writeWordBigEndian(0);
					int j2 = buffer.currentOffset;
					int j3 = 0;
					for (int j4 = 0; j4 < mouseDetection.coordsIndex; j4++) {
						if (j2 - buffer.currentOffset >= 240)
							break;
						j3++;
						int l4 = mouseDetection.coordsY[j4];
						if (l4 < 0)
							l4 = 0;
						else if (l4 > 502)
							l4 = 502;
						int k5 = mouseDetection.coordsX[j4];
						if (k5 < 0)
							k5 = 0;
						else if (k5 > 764)
							k5 = 764;
						int i6 = l4 * 765 + k5;
						if (mouseDetection.coordsY[j4] == -1 && mouseDetection.coordsX[j4] == -1) {
							k5 = -1;
							l4 = -1;
							i6 = 0x7ffff;
						}
						if (k5 == anInt1237 && l4 == anInt1238) {
							if (anInt1022 < 2047)
								anInt1022++;
						} else {
							int j6 = k5 - anInt1237;
							anInt1237 = k5;
							int k6 = l4 - anInt1238;
							anInt1238 = l4;
							if (anInt1022 < 8 && j6 >= -32 && j6 <= 31 && k6 >= -32 && k6 <= 31) {
								j6 += 32;
								k6 += 32;
								buffer.writeWord((anInt1022 << 12) + (j6 << 6) + k6);
								anInt1022 = 0;
							} else if (anInt1022 < 8) {
								buffer.writeDWordBigEndian(0x800000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							} else {
								buffer.writeDWord(0xc0000000 + (anInt1022 << 19) + i6);
								anInt1022 = 0;
							}
						}
					}

					buffer.writeBytes(buffer.currentOffset - j2);
					if (j3 >= mouseDetection.coordsIndex) {
						mouseDetection.coordsIndex = 0;
					} else {
						mouseDetection.coordsIndex -= j3;
						for (int i5 = 0; i5 < mouseDetection.coordsIndex; i5++) {
							mouseDetection.coordsX[i5] = mouseDetection.coordsX[i5 + j3];
							mouseDetection.coordsY[i5] = mouseDetection.coordsY[i5 + j3];
						}

					}
				}
			} else {
				mouseDetection.coordsIndex = 0;
			}
		}
		if (super.clickMode3 != 0) {
			long l = (super.aLong29 - aLong1220) / 50L;
			if (l > 4095L)
				l = 4095L;
			aLong1220 = super.aLong29;
			int k2 = super.saveClickY;
			if (k2 < 0)
				k2 = 0;
			else if (k2 > 502)
				k2 = 502;
			int k3 = super.saveClickX;
			if (k3 < 0)
				k3 = 0;
			else if (k3 > 764)
				k3 = 764;
			int k4 = k2 * 765 + k3;
			int j5 = 0;
			if (super.clickMode3 == 2)
				j5 = 1;
			int l5 = (int) l;
			buffer.createFrame(241);
			buffer.writeDWord((l5 << 20) + (j5 << 19) + k4);
		}
		if (anInt1016 > 0)
			anInt1016--;
		if (super.keyArray[1] == 1 || super.keyArray[2] == 1 || super.keyArray[3] == 1 || super.keyArray[4] == 1)
			aBoolean1017 = true;
		if (aBoolean1017 && anInt1016 <= 0) {
			anInt1016 = 20;
			aBoolean1017 = false;
			buffer.createFrame(86);
			buffer.writeWord(anInt1184);
			buffer.method432(minimapInt1);
		}
		if (super.awtFocus && !aBoolean954) {
			aBoolean954 = true;
			buffer.createFrame(3);
			buffer.writeWordBigEndian(1);
		}
		if (!super.awtFocus && aBoolean954) {
			aBoolean954 = false;
			buffer.createFrame(3);
			buffer.writeWordBigEndian(0);
		}
		loadingStages();
		method115();
		anInt1009++;
		if (anInt1009 > 750)
			dropClient();
		method114();
		method95();
		method38();
		anInt945++;
		if (crossType != 0) {
			crossIndex += 20;
			if (crossIndex >= 400)
				crossType = 0;
		}
		if (atInventoryInterfaceType != 0) {
			atInventoryLoopCycle++;
			if (atInventoryLoopCycle >= 15) {
				if (atInventoryInterfaceType == 2) {
				}
				if (atInventoryInterfaceType == 3)
					inputTaken = true;
				atInventoryInterfaceType = 0;
			}
		}
		if (activeInterfaceType != 0) {
			anInt989++;
			if (super.mouseX > anInt1087 + 5 || super.mouseX < anInt1087 - 5 || super.mouseY > anInt1088 + 5 || super.mouseY < anInt1088 - 5)
				aBoolean1242 = true;
			if (super.clickMode2 == 0) {
				if (activeInterfaceType == 2) {
				}
				if (activeInterfaceType == 3)
					inputTaken = true;
				activeInterfaceType = 0;
				if (aBoolean1242 && anInt989 >= 15) {
					lastActiveInvInterface = -1;
					processRightClick();

					/* Checking for adding to bank tab */
					if (lastActiveInvInterface <= RSInterface.BANK_CHILDREN_BASE_ID + 58 && lastActiveInvInterface >= RSInterface.BANK_CHILDREN_BASE_ID + 50) {
						Point southWest, northEast;
						if (clientSize == 0) {
							southWest = new Point(27, 75);
							northEast = new Point(457, 41);
						} else {
							int xOffset = clientWidth / 2;
							int yOffset = clientHeight / 2;
							southWest = new Point(xOffset - 234, yOffset - 92);
							northEast = new Point(xOffset + 194, yOffset - 128);
						}
						int[] slots = new int[9];
						for (int i = 0; i < slots.length; i++)
							slots[i] = i == 0 ? (int) southWest.getX() : (47 * i) + (int) southWest.getX();
						for (int i = 0; i < slots.length; i++) {
							if (super.mouseX >= slots[i] && super.mouseX <= slots[i] + 48 && super.mouseY >= northEast.getY() && super.mouseY <= southWest.getY()) {
								RSInterface rsi = RSInterface.interfaceCache[RSInterface.BANK_CHILDREN_BASE_ID + 50 + i];
								if (rsi.interfaceShown) {
									continue;
								}
								buffer.createFrame(214);
								buffer.method433(lastActiveInvInterface);
								buffer.method424(0);
								buffer.method433(itemFrom);
								buffer.method431(RSInterface.BANK_CHILDREN_BASE_ID + 50 + i);
								return;
							}
						}
					}

					if (lastActiveInvInterface == interfaceId && itemTo != itemFrom) {
						RSInterface class9 = RSInterface.interfaceCache[interfaceId];
						int itemId = 0;
						if ((anInt913 == 1 || variousSettings[1009] == 0) && class9.contentType == 206)
							itemId = 1;
						if (class9.inv[itemTo] <= 0)
							itemId = 0;
						if (class9.aBoolean235) {
							int l2 = itemFrom;
							int l3 = itemTo;
							class9.inv[l3] = class9.inv[l2];
							class9.invStackSizes[l3] = class9.invStackSizes[l2];
							class9.inv[l2] = -1;
							class9.invStackSizes[l2] = 0;
						} else if (itemId == 1) {
							int i3 = itemFrom;
							for (int i4 = itemTo; i3 != i4;) {
								if (i3 > i4) {
									class9.swapInventoryItems(i3, i3 - 1);
									i3--;
								} else {
									if (i3 < i4) {
										class9.swapInventoryItems(i3, i3 + 1);
										i3++;
									}
								}
							}
						} else {
							class9.swapInventoryItems(itemFrom, itemTo);
						}
						buffer.createFrame(214);
						buffer.method433(interfaceId);
						buffer.method424(itemId);
						buffer.method433(itemFrom);
						buffer.method431(itemTo);
					}
				} else if ((anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2)
					determineMenuSize();
				else if (menuActionRow > 0)
					doAction(menuActionRow - 1);
				atInventoryLoopCycle = 10;
				super.clickMode3 = 0;
			}
		}
		if (Scene.anInt470 != -1) {
			int k = Scene.anInt470;
			int k1 = Scene.anInt471;
			boolean flag = doWalkTo(0, 0, 0, 0, myPlayer.smallY[0], 0, 0, k1, myPlayer.smallX[0], true, k);
			Scene.anInt470 = -1;
			if (flag) {
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 1;
				crossIndex = 0;
			}
		}
		if (super.clickMode3 == 1 && aString844 != null) {
			aString844 = null;
			inputTaken = true;
			super.clickMode3 = 0;
		}
		if (!processMenuClick()) {
			processMainScreenClick();
			processTabAreaClick();
			processChatModeClick();
			processMapAreaClick();
			processMapAreaMouse();
		}

		if (super.clickMode2 == 1 || super.clickMode3 == 1)
			anInt1213++;
		if (anInt1500 != 0 || anInt1044 != 0 || anInt1129 != 0) {
			if (anInt1501 < 0 && !menuOpen) {
				anInt1501++;
				if (anInt1501 == 0) {
					if (anInt1500 != 0) {
						inputTaken = true;
					}
					if (anInt1044 != 0) {
					}
				}
			}
		} else if (anInt1501 > 0) {
			anInt1501--;
		}
		if (loadingStage == 2)
			method108();
		if (loadingStage == 2 && aBoolean1160)
			calcCameraPos();
		for (int i1 = 0; i1 < 5; i1++)
			anIntArray1030[i1]++;

		method73();
		super.idleTime++;
		if (super.idleTime > 4500) {
			anInt1011 = 250;
			super.idleTime -= 500;
			buffer.createFrame(202);
		}
		anInt1010++;
		if (anInt1010 > 50)
			buffer.createFrame(0);
		try {
			if (socketStream != null && buffer.currentOffset > 0) {
				socketStream.queueBytes(buffer.currentOffset, buffer.buffer);
				buffer.currentOffset = 0;
				anInt1010 = 0;
			}
		} catch (IOException _ex) {
			dropClient();
		} catch (Exception exception) {
			resetLogout();
		}
	}

	private void method63() {
		Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst();
		for (; class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext())
			if (class30_sub1.anInt1294 == -1) {
				class30_sub1.anInt1302 = 0;
				method89(class30_sub1);
			} else {
				class30_sub1.unlink();
			}

	}

	void resetImageProducers() {
		if (aRSImageProducer_1107 != null)
			return;
		super.fullGameScreen = null;
		aRSImageProducer_1166 = null;
		aRSImageProducer_1164 = null;
		aRSImageProducer_1163 = null;
		aRSImageProducer_1165 = null;
		aRSImageProducer_1125 = null;
		aRSImageProducer_1110 = new GraphicalComponent(128, 265);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1111 = new GraphicalComponent(128, 265);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1107 = new GraphicalComponent(509, 171);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1108 = new GraphicalComponent(360, 132);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1109 = new GraphicalComponent(getClientWidth(), getClientHeight());
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1112 = new GraphicalComponent(202, 238);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1113 = new GraphicalComponent(203, 238);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1114 = new GraphicalComponent(74, 94);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1115 = new GraphicalComponent(75, 94);
		DrawingArea.setAllPixelsToZero();
		if (titleStreamLoader != null) {
			drawLogo();
			loadTitleScreen();
		}
		welcomeScreenRaised = true;
	}
	
	private BufferedImage bufferedImage;
	private Graphics2D graphics2d;
	private int frameAngle;
	
	public void drawSmoothLoadingText(int percent, String message) {
        for(float f = LP; f < (float)percent; f = (float)((double)f + 0.29999999999999999D)) {
            drawLoadingText((int)f, message);
        }
        LP = percent;
    }
	
	public void drawLoadingText(int percent, String message) {
		lastPercent = percent;
		lastMessage = message;
        resetImageProducers();
        aRSImageProducer_1109.setCanvas();
        if (titleStreamLoader == null) {
            super.drawLoadingText(percent, message);
            return;
        }
		clientWidth = myWidth;
		clientHeight = myHeight;
		bufferedImage = new BufferedImage(clientWidth, clientHeight, BufferedImage.TYPE_INT_RGB);
		graphics2d = bufferedImage.createGraphics();
		graphics2d.setColor(Color.BLACK);
		graphics2d.fillRect(0, 0, clientWidth, clientHeight);
		graphics2d.setColor(Color.WHITE);
		graphics2d.setFont(new Font("Georgia", Font.PLAIN, 16));
		graphics2d.drawString("Loading Mistex Client Resources..", 10, 24);
		graphics2d.drawString("Please wait patiently.", 15, 45);
		graphics2d.drawString("" + message, clientWidth / 2 - 77, clientHeight / 2 + 90);
		graphics2d.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		graphics2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		graphics2d.setColor(Color.WHITE);
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.drawArc((clientWidth >> 1) - 50, (clientHeight >> 1) - 50, 100, 100, 360 - frameAngle, (percent * 360 / 97));
		frameAngle = (frameAngle + 1) % 360;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics2d.setFont(new Font("Georgia", Font.PLAIN, 21));
		graphics2d.drawString(percent + 1 + "%", (clientWidth >> 1) - (graphics2d.getFontMetrics().stringWidth(percent + "%") >> 1), (clientHeight >> 1) + 7);
		graphics.drawImage(bufferedImage, 0, 0, null);
    }

	private void method65(int i, int j, int k, int l, RSInterface class9, int i1, boolean flag, int j1) {
		int anInt992;
		if (aBoolean972)
			anInt992 = 32;
		else
			anInt992 = 0;
		aBoolean972 = false;
		if (k >= i && k < i + 16 && l >= i1 && l < i1 + 16) {
			class9.scrollPosition -= anInt1213 * 4;
			if (flag) {
			}
		} else if (k >= i && k < i + 16 && l >= (i1 + j) - 16 && l < i1 + j) {
			class9.scrollPosition += anInt1213 * 4;
			if (flag) {
			}
		} else if (k >= i - anInt992 && k < i + 16 + anInt992 && l >= i1 + 16 && l < (i1 + j) - 16 && anInt1213 > 0) {
			int l1 = ((j - 32) * j) / j1;
			if (l1 < 8)
				l1 = 8;
			int i2 = l - i1 - 16 - l1 / 2;
			int j2 = j - 32 - l1;
			class9.scrollPosition = ((j1 - j) * i2) / j2;
			if (flag) {
			}
			aBoolean972 = true;
		}
	}

	private boolean method66(int i, int j, int k) {
		int i1 = i >> 14 & 0x7fff;
		int j1 = worldController.method304(plane, k, j, i);
		if (j1 == -1)
			return false;
		int k1 = j1 & 0x1f;
		int l1 = j1 >> 6 & 3;
		if (k1 == 10 || k1 == 11 || k1 == 22) {
			ObjectDefinition class46 = ObjectDefinition.forID(i1);
			int i2;
			int j2;
			if (l1 == 0 || l1 == 2) {
				i2 = class46.anInt744;
				j2 = class46.anInt761;
			} else {
				i2 = class46.anInt761;
				j2 = class46.anInt744;
			}
			int k2 = class46.anInt768;
			if (l1 != 0)
				k2 = (k2 << l1 & 0xf) + (k2 >> 4 - l1);
			doWalkTo(2, 0, j2, 0, myPlayer.smallY[0], i2, k2, j, myPlayer.smallX[0], false, k);
		} else {
			doWalkTo(2, l1, 0, k1 + 1, myPlayer.smallY[0], 0, 0, j, myPlayer.smallX[0], false, k);
		}
		crossX = super.saveClickX;
		crossY = super.saveClickY;
		crossType = 2;
		crossIndex = 0;
		return true;
	}

	private Archive streamLoaderForName(int i, String s, String s1, int j, int k) {
		byte abyte0[] = null;
		int l = 5;
		try {
			if (decompressors[0] != null)
				abyte0 = decompressors[0].decompress(i);
		} catch (Exception _ex) {

		}
		if (abyte0 != null) {
			Archive jagexArchive = new Archive(abyte0);
			return jagexArchive;
		}
		int j1 = 0;
		while (abyte0 == null) {
			String s2 = "Unknown error";
			drawLoadingText(k, "Requesting " + s);
			try {
				int k1 = 0;
				DataInputStream datainputstream = openJagGrabInputStream(s1 + j);
				byte abyte1[] = new byte[6];
				datainputstream.readFully(abyte1, 0, 6);
				Buffer buffer = new Buffer(abyte1);
				buffer.currentOffset = 3;
				int i2 = buffer.read3Bytes() + 6;
				int j2 = 6;
				abyte0 = new byte[i2];
				System.arraycopy(abyte1, 0, abyte0, 0, 6);

				while (j2 < i2) {
					int l2 = i2 - j2;
					if (l2 > 1000)
						l2 = 1000;
					int j3 = datainputstream.read(abyte0, j2, l2);
					if (j3 < 0) {
						s2 = "Length error: " + j2 + "/" + i2;
						throw new IOException("EOF");
					}
					j2 += j3;
					int k3 = (j2 * 100) / i2;
					if (k3 != k1)
						drawLoadingText(k, "Loading " + s + " - " + k3 + "%");
					k1 = k3;
				}
				datainputstream.close();
				try {
					if (decompressors[0] != null)
						decompressors[0].method234(abyte0.length, abyte0, i);
				} catch (Exception _ex) {
					decompressors[0] = null;
				}
			} catch (IOException ioexception) {
				if (s2.equals("Unknown error"))
					s2 = "Connection error";
				abyte0 = null;
			} catch (NullPointerException _ex) {
				s2 = "Null error";
				abyte0 = null;
				if (!Signlink.reporterror)
					return null;
			} catch (ArrayIndexOutOfBoundsException _ex) {
				s2 = "Bounds error";
				abyte0 = null;
				if (!Signlink.reporterror)
					return null;
			} catch (Exception _ex) {
				s2 = "Unexpected error";
				abyte0 = null;
				if (!Signlink.reporterror)
					return null;
			}
			if (abyte0 == null) {
				for (int l1 = l; l1 > 0; l1--) {
					if (j1 >= 3) {
						drawLoadingText(k, "Game updated - please reload page");
						l1 = 10;
					} else {
						drawLoadingText(k, s2 + " - Retrying in " + l1);
					}
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				l *= 2;
				if (l > 60)
					l = 60;
				aBoolean872 = !aBoolean872;
			}

		}

		Archive streamLoader_1 = new Archive(abyte0);
		return streamLoader_1;
	}

	private void dropClient() {
		if (anInt1011 > 0) {
			resetLogout();
			return;
		}
		aRSImageProducer_1165.setCanvas();
		imageLoader[63].drawSprite(0, 0);
		aRSImageProducer_1165.drawGraphics(4, super.graphics, 4);
		mapState = 0;
		destX = 0;
		RSSocket rsSocket = socketStream;
		loggedIn = false;
		loginFailures = 0;
		login(myUsername, myPassword, true);
		if (!loggedIn)
			resetLogout();
		try {
			rsSocket.close();
		} catch (Exception _ex) {
		}
	}

	public String clanName = "";

	private void doAction(int i) {
		if (i < 0)
			return;
		if (inputDialogState != 0) {
			inputDialogState = 0;
			inputTaken = true;
		}
		int j = menuActionCmd2[i];
		int k = menuActionCmd3[i];
		int l = menuActionID[i];
		int i1 = menuActionCmd1[i];
		if (l >= 2000)
			l -= 2000;
		if (l == 1107) {
			if (tabID == 6 && tabInterfaceIDs[6] == 12855) {
				if (super.mouseX >= (clientSize == 0 ? 565 : clientWidth - 178) && super.mouseX <= (clientSize == 0 ? 585 : clientWidth - 159) && super.mouseY >= (clientSize == 0 ? 218 : clientHeight - 328) && super.mouseY <= (clientSize == 0 ? 237 : clientHeight - 308)) {
					sendPacket185(12856, -1, 135);
				}
			}
		}
		if (l == 1050) {
			RSFrame.takeScreenshot();
		}
		if (l == 1005) {
			openInterfaceID = 60600;
		}
		if (l == 696) {
			setNorth();
		}
		if (l == 476) {
			alertHandler.close();
		}
		if (l == 474) {
			counterOn = !counterOn;
			gains.clear();
		}
		if (l == 475) {
			buffer.createFrame(62);
		}
		switch(l) {
		case 1516: //Summoning - cast
			//make an int to check if player has a familiar like pack yak, to use on item
			buffer.createFrame(185);
			buffer.writeWord(5003);
		break;
		
		case 1520: //Summoning - Attack
			//make packet to handle attacking
		break;
		
		case 1524: //Summoning - Interact
			buffer.createFrame(185);
			buffer.writeWord(5004);
		break;
		
		case 1528: //Summoning - Renew Familiar
			buffer.createFrame(185);
			buffer.writeWord(5005);
		break;
		
		case 1532: //Summoning - Take BoB
			buffer.createFrame(185);
			buffer.writeWord(5006);
		break;
		
		case 1536: //Summoning - Dimiss
			buffer.createFrame(185);
			buffer.writeWord(5007);
		break;
		
		case 1540: //Summoning - Call Follower
			buffer.createFrame(185);
			buffer.writeWord(5008);
		break;
		
		case 1544: //Summoning - Follower Details
			buffer.createFrame(185);
			buffer.writeWord(5009);
		break;
		
		case 1052: //
			buffer.createFrame(185);
			buffer.writeWord(5010);
		break;
		}
		if (l == 582) {
			Npc npc = npcArray[i1];
			if (npc != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(57);
				buffer.method432(anInt1285);
				buffer.method432(i1);
				buffer.method431(anInt1283);
				buffer.method432(anInt1284);
			}
		}
		if (l == 234) {
			boolean flag1 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag1)
				flag1 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(236);
			buffer.method431(k + baseY);
			buffer.writeWord(i1);
			buffer.method431(j + baseX);
		}
		if (l == 62 && method66(i1, k, j)) {
			buffer.createFrame(192);
			buffer.writeWord(anInt1284);
			buffer.method431(i1 >> 14 & 0x7fff);
			buffer.method433(k + baseY);
			buffer.method431(anInt1283);
			buffer.method433(j + baseX);
			buffer.writeWord(anInt1285);
		}
		if (l == 511) {
			boolean flag2 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag2)
				flag2 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(25);
			buffer.method431(anInt1284);
			buffer.method432(anInt1285);
			buffer.writeWord(i1);
			buffer.method432(k + baseY);
			buffer.method433(anInt1283);
			buffer.writeWord(j + baseX);
		}
		if (l == 74) {
			buffer.createFrame(122);
			buffer.method433(k);
			buffer.method432(j);
			buffer.method431(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 315) {
			RSInterface class9 = RSInterface.interfaceCache[k];
			boolean flag8 = true;
			if (class9.contentType > 0 || class9.id == RSInterface.BANK_CHILDREN_BASE_ID + 7) {
				if ((class9.contentType == 997) || (class9.contentType == 998) || (class9.contentType == 999)) {
			          String[] spriteNames = { "Attack", "Hitpoints", "Mining", "Strength", "Agility", "Smithing", "Defence", "Herblore", "Fishing", "Range", "Thieving", "Cooking", "Prayer", "Crafting", "Firemaking", "Magic", "Fletching", "Woodcutting", "Runecrafting", "Slayer", "Farming", "Construction", "Hunter", "Summoning", "Dungeoneering" };
			          int index = class9.id - 79924;
			          if (index >= 50)
			            index -= 50;
			          if (index >= 25)
			            index -= 25;
			          SkillConstants.selectedSkillId = SkillConstants.skillIdForName(spriteNames[index]);
			        }
				flag8 = promptUserForInput(class9);
			}
			if (flag8) {

				switch (k) {
				case 36004:
					toggleSize(0);
					break;
				case 36007:
					toggleSize(1);
					break;
				case 36010:
					toggleSize(2);
					break;
				default:
					buffer.createFrame(185);
					buffer.writeWord(k);
					break;

				}
			}
		}
		if (l == 561) {
			Player player = playerArray[i1];
			if (player != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1188 += i1;
				if (anInt1188 >= 90) {
					buffer.createFrame(136);
					anInt1188 = 0;
				}
				buffer.createFrame(128);
				buffer.writeWord(i1);
			}
		}
		if (l == 20) {
			Npc class30_sub2_sub4_sub1_sub1_1 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_1 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_1.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_1.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(155);
				buffer.method431(i1);
			}
		}
		if (l == 779) {
			Player class30_sub2_sub4_sub1_sub2_1 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_1 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_1.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_1.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(153);
				buffer.method431(i1);
			}
		}
		if (l == 519)
			if (!menuOpen)
				worldController.method312(super.saveClickY - 4, super.saveClickX - 4);
			else
				worldController.method312(k - 4, j - 4);
		if (l == 1062) {
			anInt924 += baseX;
			if (anInt924 >= 113) {
				buffer.createFrame(183);
				buffer.writeDWordBigEndian(0xe63271);
				anInt924 = 0;
			}
			method66(i1, k, j);
			buffer.createFrame(228);
			buffer.method432(i1 >> 14 & 0x7fff);
			buffer.method432(k + baseY);
			buffer.writeWord(j + baseX);
		}
		if (l == 679 && !aBoolean1149) {
			buffer.createFrame(40);
			buffer.writeWord(k);
			aBoolean1149 = true;
		}
		if (l == 647) {
			buffer.createFrame(213);
			buffer.writeWord(k);
			buffer.writeWord(j);
			switch (k) {
			case 18304:
				if (j == 0) {
					inputTaken = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 8;
					aString1121 = "Enter your clan chat title";
				}
				break;
			}
		}
		if (l == 431) {
			buffer.createFrame(129);
			buffer.method432(j);
			buffer.writeWord(k);
			buffer.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 337 || l == 42 || l == 792 || l == 322) {
			String s = menuActionName[i];
			int k1 = s.indexOf("@whi@");
			if (k1 != -1) {
				long l3 = TextClass.longForName(s.substring(k1 + 5).trim());
				if (l == 337)
					addFriend(l3);
				if (l == 42)
					addIgnore(l3);
				if (l == 792)
					delFriend(l3);
				if (l == 322)
					delIgnore(l3);
			}
		}
		if (l == 1315) {
			clanRank(65, clanName, 0);
		}
		if (l == 1316) {
			clanRank(65, clanName, 1);
		}
		if (l == 1317) {
			clanRank(65, clanName, 2);
		}
		if (l == 1318) {
			clanRank(65, clanName, 3);
		}
		if (l == 1319) {
			clanRank(65, clanName, 4);
		}
		if (l == 1320) {
			clanRank(65, clanName, 5);
		}
		if (l == 1321) {
			clanRank(65, clanName, 6);
		}
		if (l == 53) {
			buffer.createFrame(135);
			buffer.method431(j);
			buffer.method432(k);
			buffer.method431(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 539) {
			buffer.createFrame(16);
			buffer.method432(i1);
			buffer.method433(j);
			buffer.method433(k);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 484 || l == 6) {
			String s1 = menuActionName[i];
			int l1 = s1.indexOf("@whi@");
			if (l1 != -1) {
				s1 = s1.substring(l1 + 5).trim();
				String s7 = TextClass.fixName(TextClass.nameForLong(TextClass.longForName(s1)));
				boolean flag9 = false;
				for (int j3 = 0; j3 < playerCount; j3++) {
					Player class30_sub2_sub4_sub1_sub2_7 = playerArray[playerIndices[j3]];
					if (class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.name == null || !class30_sub2_sub4_sub1_sub2_7.name.equalsIgnoreCase(s7))
						continue;
					doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_7.smallX[0]);
					if (l == 484) {
						buffer.createFrame(139);
						buffer.method431(playerIndices[j3]);
					}
					if (l == 6) {
						anInt1188 += i1;
						if (anInt1188 >= 90) {
							buffer.createFrame(136);
							anInt1188 = 0;
						}
						buffer.createFrame(128);
						buffer.writeWord(playerIndices[j3]);
					}
					flag9 = true;
					break;
				}

				if (!flag9)
					pushMessage("Unable to find " + s7, 0, "");
			}
		}
		if (l == 870) {
			buffer.createFrame(53);
			buffer.writeWord(j);
			buffer.method432(anInt1283);
			buffer.method433(i1);
			buffer.writeWord(anInt1284);
			buffer.method431(anInt1285);
			buffer.writeWord(k);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 847) {
			buffer.createFrame(87);
			buffer.method432(i1);
			buffer.writeWord(k);
			buffer.method432(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 626) {
			RSInterface class9_1 = RSInterface.interfaceCache[k];
			spellSelected = 1;
			spellID = class9_1.id;
			anInt1137 = k;
			spellUsableOn = class9_1.spellUsableOn;
			itemSelected = 0;
			String s4 = class9_1.selectedActionName;
			if (s4.indexOf(" ") != -1)
				s4 = s4.substring(0, s4.indexOf(" "));
			String s8 = class9_1.selectedActionName;
			if (s8.indexOf(" ") != -1)
				s8 = s8.substring(s8.indexOf(" ") + 1);
			spellTooltip = s4 + " " + class9_1.spellName + " " + s8;
			if (spellUsableOn == 16) {
				tabID = 3;
				tabAreaAltered = true;
			}
			return;
		}
		if (l == 78) {
			buffer.createFrame(117);
			buffer.method433(k);
			buffer.method433(i1);
			buffer.method431(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 27) {
			Player class30_sub2_sub4_sub1_sub2_2 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_2 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_2.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_2.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt986 += i1;
				if (anInt986 >= 54) {
					buffer.createFrame(189);
					buffer.writeWordBigEndian(234);
					anInt986 = 0;
				}
				buffer.createFrame(73);
				buffer.method431(i1);
			}
		}
		if (l == 213) {
			boolean flag3 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag3)
				flag3 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(79);
			buffer.method431(k + baseY);
			buffer.writeWord(i1);
			buffer.method432(j + baseX);
		}
		if (l == 632) {
			buffer.createFrame(145);
			buffer.method432(k);
			buffer.method432(j);
			buffer.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 1004) {
			if (tabInterfaceIDs[10] != -1) {
				tabID = 10;
				tabAreaAltered = true;
			}
		}
		if (l == 1500) {
			prayClicked = !prayClicked;
			buffer.createFrame(185);
			buffer.writeWord(5000);
		}
		if (l == 1506) {
			buffer.createFrame(185);
			buffer.writeWord(5001);
		}
		if (l == 1012) {
			yellMode = 3;
			inputTaken = true;
		}
		if (l == 1011) {
			yellMode = 2;
			inputTaken = true;
		}
		if (l == 1010) {
			yellMode = 1;
			inputTaken = true;
		}
		if (l == 1009) {
			yellMode = 0;
			inputTaken = true;
		}
		if (l == 1003) {
			clanChatMode = 2;
			inputTaken = true;
		}
		if (l == 1002) {
			clanChatMode = 1;
			inputTaken = true;
		}
		if (l == 1001) {
			clanChatMode = 0;
			inputTaken = true;
		}
		if (l == 1000) {
			cButtonCPos = 4;
			chatTypeView = 11;
			inputTaken = true;
		}
		if (l == 999) {
			cButtonCPos = 0;
			chatTypeView = 0;
			inputTaken = true;
		}
		if (l == 998) {
			cButtonCPos = 1;
			chatTypeView = 5;
			inputTaken = true;
		}
		if (l == 997) {
			publicChatMode = 3;
			inputTaken = true;
		}
		if (l == 798) {
			filterMessages = true;
			inputTaken = true;
		}
		if (l == 797) {
			filterMessages = false;
			inputTaken = true;
		}
		if (l == 996) {
			publicChatMode = 2;
			inputTaken = true;
		}
		if (l == 995) {
			publicChatMode = 1;
			inputTaken = true;
		}
		if (l == 994) {
			publicChatMode = 0;
			inputTaken = true;
		}
		if (l == 993) {
			cButtonCPos = 2;
			chatTypeView = 1;
			inputTaken = true;
		}
		if (l == 992) {
			privateChatMode = 2;
			inputTaken = true;
		}
		if (l == 991) {
			privateChatMode = 1;
			inputTaken = true;
		}
		if (l == 990) {
			privateChatMode = 0;
			inputTaken = true;
		}
		if (l == 989) {
			cButtonCPos = 3;
			chatTypeView = 2;
			inputTaken = true;
		}
		if (l == 987) {
			tradeMode = 2;
			inputTaken = true;
		}
		if (l == 986) {
			tradeMode = 1;
			inputTaken = true;
		}
		if (l == 985) {
			tradeMode = 0;
			inputTaken = true;
		}
		if (l == 984) {
			cButtonCPos = 5;
			chatTypeView = 3;
			inputTaken = true;
		}
		if (l == 983) {
			duelMode = 2;
			inputTaken = true;
		}
		if (l == 982) {
			duelMode = 1;
			inputTaken = true;
		}
		if (l == 981) {
			duelMode = 0;
			inputTaken = true;
		}
		if (l == 980) {
			cButtonCPos = 6;
			chatTypeView = 4;
			inputTaken = true;
		}
		if (l == 493) {
			buffer.createFrame(75);
			buffer.method433(k);
			buffer.method431(j);
			buffer.method432(i1);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 652) {
			boolean flag4 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag4)
				flag4 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(156);
			buffer.method432(j + baseX);
			buffer.method431(k + baseY);
			buffer.method433(i1);
		}
		if (l == 94) {
			boolean flag5 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag5)
				flag5 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(181);
			buffer.method431(k + baseY);
			buffer.writeWord(i1);
			buffer.method431(j + baseX);
			buffer.method432(anInt1137);
		}
		if (l == 646) {
			buffer.createFrame(185);
			buffer.writeWord(k);
			RSInterface class9_2 = RSInterface.interfaceCache[k];
			if (class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
				int i2 = class9_2.valueIndexArray[0][1];
				if (variousSettings[i2] != class9_2.requiredValues[0]) {
					variousSettings[i2] = class9_2.requiredValues[0];
					method33(i2);
				}
			}
		}
		if (l == 225) {
			Npc class30_sub2_sub4_sub1_sub1_2 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_2 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_2.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_2.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1226 += i1;
				if (anInt1226 >= 85) {
					buffer.createFrame(230);
					buffer.writeWordBigEndian(239);
					anInt1226 = 0;
				}
				buffer.createFrame(17);
				buffer.method433(i1);
			}
		}
		if (l == 965) {
			Npc class30_sub2_sub4_sub1_sub1_3 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_3 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_3.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_3.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				anInt1134++;
				if (anInt1134 >= 96) {
					buffer.createFrame(152);
					buffer.writeWordBigEndian(88);
					anInt1134 = 0;
				}
				buffer.createFrame(21);
				buffer.writeWord(i1);
			}
		}
		if (l == 413) {
			Npc class30_sub2_sub4_sub1_sub1_4 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_4 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_4.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_4.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(131);
				buffer.method433(i1);
				buffer.method432(anInt1137);
			}
		}
		if (l == 200)
			clearTopInterfaces();
		if (l == 1025) {
			Npc class30_sub2_sub4_sub1_sub1_5 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_5 != null) {
				EntityDefinition entityDefinition = class30_sub2_sub4_sub1_sub1_5.desc;
				if (entityDefinition.childrenIDs != null)
					entityDefinition = entityDefinition.method161();
				if (entityDefinition != null) {
					String s9;
					if (entityDefinition.description != null)
						s9 = new String(entityDefinition.description);
					else
						s9 = "It's a " + entityDefinition.name + ".";
					pushMessage(s9, 0, "");
				}
			}
		}
		if (l == 900) {
			method66(i1, k, j);
			buffer.createFrame(252);
			buffer.method433(i1 >> 14 & 0x7fff);
			buffer.method431(k + baseY);
			buffer.method432(j + baseX);
		}
		if (l == 412) {
			Npc class30_sub2_sub4_sub1_sub1_6 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_6 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_6.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_6.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(72);
				buffer.method432(i1);
			}
		}
		if (l == 365) {
			Player class30_sub2_sub4_sub1_sub2_3 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_3 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_3.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_3.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(249);
				buffer.method432(i1);
				buffer.method431(anInt1137);
			}
		}
		if (l == 729) {
			Player class30_sub2_sub4_sub1_sub2_4 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_4 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_4.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_4.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(39);
				buffer.method431(i1);
			}
		}
		if (l == 577) {
			Player class30_sub2_sub4_sub1_sub2_5 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_5 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_5.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_5.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(139);
				buffer.method431(i1);
			}
		}
		if (l == 956 && method66(i1, k, j)) {
			buffer.createFrame(35);
			buffer.method431(j + baseX);
			buffer.method432(anInt1137);
			buffer.method432(k + baseY);
			buffer.method431(i1 >> 14 & 0x7fff);
		}
		if (l == 567) {
			boolean flag6 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag6)
				flag6 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(23);
			buffer.method431(k + baseY);
			buffer.method431(i1);
			buffer.method431(j + baseX);
		}
		if (l == 867) {
			if ((i1 & 3) == 0)
				anInt1175++;
			if (anInt1175 >= 59) {
				buffer.createFrame(200);
				buffer.writeWord(25501);
				anInt1175 = 0;
			}
			buffer.createFrame(43);
			buffer.method431(k);
			buffer.method432(i1);
			buffer.method432(j);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 543) {
			buffer.createFrame(237);
			buffer.writeWord(j);
			buffer.method432(i1);
			buffer.writeWord(k);
			buffer.method432(anInt1137);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 606) {
			String s2 = menuActionName[i];
			int j2 = s2.indexOf("@whi@");
			if (j2 != -1)
				if (openInterfaceID == -1) {
					clearTopInterfaces();
					reportAbuseInput = s2.substring(j2 + 5).trim();
					canMute = false;
					for (int i3 = 0; i3 < RSInterface.interfaceCache.length; i3++) {
						if (RSInterface.interfaceCache[i3] == null || RSInterface.interfaceCache[i3].contentType != 600)
							continue;
						reportAbuseInterfaceID = openInterfaceID = RSInterface.interfaceCache[i3].parentID;
						break;
					}

				} else {
					pushMessage("Please close the interface you have open before using 'report abuse'", 0, "");
				}
		}
		if (l == 491) {
			Player class30_sub2_sub4_sub1_sub2_6 = playerArray[i1];
			if (class30_sub2_sub4_sub1_sub2_6 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_6.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_6.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				buffer.createFrame(14);
				buffer.method432(anInt1284);
				buffer.writeWord(i1);
				buffer.writeWord(anInt1285);
				buffer.method431(anInt1283);
			}
		}
		if (l == 639) {
			String s3 = menuActionName[i];
			int k2 = s3.indexOf("@whi@");
			if (k2 != -1) {
				long l4 = TextClass.longForName(s3.substring(k2 + 5).trim());
				int k3 = -1;
				for (int i4 = 0; i4 < friendsCount; i4++) {
					if (friendsListAsLongs[i4] != l4)
						continue;
					k3 = i4;
					break;
				}

				if (k3 != -1 && friendsNodeIDs[k3] > 0) {
					inputTaken = true;
					inputDialogState = 0;
					messagePromptRaised = true;
					promptInput = "";
					friendsListAction = 3;
					aLong953 = friendsListAsLongs[k3];
					aString1121 = "Enter message to send to " + friendsList[k3];
				}
			}
		}
		if (l == 454) {
			buffer.createFrame(41);
			buffer.writeWord(i1);
			buffer.method432(j);
			buffer.method432(k);
			atInventoryLoopCycle = 0;
			atInventoryInterface = k;
			atInventoryIndex = j;
			atInventoryInterfaceType = 2;
			if (RSInterface.interfaceCache[k].parentID == openInterfaceID)
				atInventoryInterfaceType = 1;
			if (RSInterface.interfaceCache[k].parentID == backDialogID)
				atInventoryInterfaceType = 3;
		}
		if (l == 478) {
			Npc class30_sub2_sub4_sub1_sub1_7 = npcArray[i1];
			if (class30_sub2_sub4_sub1_sub1_7 != null) {
				doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub1_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub1_7.smallX[0]);
				crossX = super.saveClickX;
				crossY = super.saveClickY;
				crossType = 2;
				crossIndex = 0;
				if ((i1 & 3) == 0)
					anInt1155++;
				if (anInt1155 >= 53) {
					buffer.createFrame(85);
					buffer.writeWordBigEndian(66);
					anInt1155 = 0;
				}
				buffer.createFrame(18);
				buffer.method431(i1);
			}
		}
		if (l == 113) {
			method66(i1, k, j);
			buffer.createFrame(70);
			buffer.method431(j + baseX);
			buffer.writeWord(k + baseY);
			buffer.method433(i1 >> 14 & 0x7fff);
		}
		if (l == 872) {
			method66(i1, k, j);
			buffer.createFrame(234);
			buffer.method433(j + baseX);
			buffer.method432(i1 >> 14 & 0x7fff);
			buffer.method433(k + baseY);
		}
		if (l == 502) {
			method66(i1, k, j);
			buffer.createFrame(132);
			buffer.method433(j + baseX);
			buffer.writeWord(i1 >> 14 & 0x7fff);
			buffer.method432(k + baseY);
		}
		if(l == 1125) {
			ItemDefinition itemDefinition = ItemDefinition.forID(i1);
			RSInterface class9_4 = RSInterface.interfaceCache[k];
			String s5;
			if (class9_4 != null && class9_4.invStackSizes[j] >= 0x186a0) {
				DecimalFormatSymbols separator = new DecimalFormatSymbols();
				separator.setGroupingSeparator(',');
				DecimalFormat formatter = new DecimalFormat("#,###,###,###", separator);
				s5 = formatter.format(class9_4.invStackSizes[j]) + " x " + itemDefinition.name;
			} else if (itemDefinition.description != null)
				s5 = new String(itemDefinition.description);
			else
				s5 = "It's a " + itemDefinition.name + ".";
			pushMessage(s5, 0, "");
		}
		
		if (l == 169) {
			buffer.createFrame(185);
			buffer.writeWord(k);
			RSInterface class9_3 = RSInterface.interfaceCache[k];
			if (class9_3.valueIndexArray != null && class9_3.valueIndexArray[0][0] == 5) {
				int l2 = class9_3.valueIndexArray[0][1];
				variousSettings[l2] = 1 - variousSettings[l2];
				method33(l2);
			}
		}
		
		if (l == 447) {
			itemSelected = 1;
			anInt1283 = j;
			anInt1284 = k;
			anInt1285 = i1;
			selectedItemName = ItemDefinition.forID(i1).name;
			spellSelected = 0;
			return;
		}
		if (l == 1226) {
			int j1 = i1 >> 14 & 0x7fff;
			ObjectDefinition class46 = ObjectDefinition.forID(j1);
			String s10;
			if (class46.description != null)
				s10 = new String(class46.description);
			else
				s10 = "It's a " + class46.name + ".";
			pushMessage(s10, 0, "");
		}
		if (l == 244) {
			boolean flag7 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, k, myPlayer.smallX[0], false, j);
			if (!flag7)
				flag7 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, k, myPlayer.smallX[0], false, j);
			crossX = super.saveClickX;
			crossY = super.saveClickY;
			crossType = 2;
			crossIndex = 0;
			buffer.createFrame(253);
			buffer.method431(j + baseX);
			buffer.method433(k + baseY);
			buffer.method432(i1);
		}
		if (l == 646) {
			buffer.createFrame(185);
			buffer.writeWord(k);
			RSInterface class9_2 = RSInterface.interfaceCache[k];
			if (class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
				int i2 = class9_2.valueIndexArray[0][1];
				if (variousSettings[i2] != class9_2.requiredValues[0]) {
					variousSettings[i2] = class9_2.requiredValues[0];
					method33(i2);
				}
			}
		}

		if (l == 1448) {
			ItemDefinition itemDef_1 = ItemDefinition.forID(i1);
			String s6;
			if (itemDef_1.description != null)
				s6 = new String(itemDef_1.description);
			else
				s6 = "It's a " + itemDef_1.name + ".";
			pushMessage(s6, 0, "");
		}
		itemSelected = 0;
		spellSelected = 0;

	}

	public void sendString(int identifier, String text) {
		text = identifier + "," + text;
		buffer.createFrame(127);
		buffer.writeWordBigEndian(text.length() + 1);
		buffer.writeString(text);
	}

	public void sendStringAsLong(String string) {
		buffer.createFrame(60);
		buffer.writeQWord(TextClass.longForName(string));
	}

	private void method70() {
		anInt1251 = 0;
		int j = (myPlayer.x >> 7) + baseX;
		int k = (myPlayer.y >> 7) + baseY;
		if (j >= 3053 && j <= 3156 && k >= 3056 && k <= 3136)
			anInt1251 = 1;
		if (j >= 3072 && j <= 3118 && k >= 9492 && k <= 9535)
			anInt1251 = 1;
		if (anInt1251 == 1 && j >= 3139 && j <= 3199 && k >= 3008 && k <= 3062)
			anInt1251 = 0;
	}

	public void run() {
		if (drawFlames) {

		} else {
			super.run();
		}
	}

	private void build3dScreenMenu() {
		if (itemSelected == 0 && spellSelected == 0) {
			menuActionName[menuActionRow] = "Walk here";
			menuActionID[menuActionRow] = 519;
			menuActionCmd2[menuActionRow] = super.mouseX;
			menuActionCmd3[menuActionRow] = super.mouseY;
			menuActionRow++;
		}
		int j = -1;
		for (int k = 0; k < Model.resourceCount; k++) {
			int l = Model.resourceId[k];
			int i1 = l & 0x7f;
			int j1 = l >> 7 & 0x7f;
			int k1 = l >> 29 & 3;
			int l1 = l >> 14 & 0x7fff;
			if (l == j)
				continue;
			j = l;
			if (k1 == 2 && worldController.method304(plane, i1, j1, l) >= 0) {
				ObjectDefinition class46 = ObjectDefinition.forID(l1);
				if (class46.childrenIDs != null)
					class46 = class46.method580();
				if (class46 == null)
					continue;
				if (itemSelected == 1) {
					menuActionName[menuActionRow] = "Use " + selectedItemName + " with @cya@" + class46.name;
					menuActionID[menuActionRow] = 62;
					menuActionCmd1[menuActionRow] = l;
					menuActionCmd2[menuActionRow] = i1;
					menuActionCmd3[menuActionRow] = j1;
					menuActionRow++;
				} else if (spellSelected == 1) {
					if ((spellUsableOn & 4) == 4) {
						menuActionName[menuActionRow] = spellTooltip + " @cya@" + class46.name;
						menuActionID[menuActionRow] = 956;
						menuActionCmd1[menuActionRow] = l;
						menuActionCmd2[menuActionRow] = i1;
						menuActionCmd3[menuActionRow] = j1;
						menuActionRow++;
					}
				} else {
					if (class46.actions != null) {
						for (int i2 = 4; i2 >= 0; i2--)
							if (class46.actions[i2] != null) {
								menuActionName[menuActionRow] = class46.actions[i2] + " @cya@" + class46.name;
								if (i2 == 0)
									menuActionID[menuActionRow] = 502;
								if (i2 == 1)
									menuActionID[menuActionRow] = 900;
								if (i2 == 2)
									menuActionID[menuActionRow] = 113;
								if (i2 == 3)
									menuActionID[menuActionRow] = 872;
								if (i2 == 4)
									menuActionID[menuActionRow] = 1062;
								menuActionCmd1[menuActionRow] = l;
								menuActionCmd2[menuActionRow] = i1;
								menuActionCmd3[menuActionRow] = j1;
								menuActionRow++;
							}

					}
					if (!Config.debugMode)
						menuActionName[menuActionRow] = "Examine @cya@" + class46.name;
					else if (class46.anIntArray773 != null)
						menuActionName[menuActionRow] = "Examine @cya@" + class46.name + " Models: "+Arrays.toString(class46.anIntArray773);
					menuActionID[menuActionRow] = 1226;
					menuActionCmd1[menuActionRow] = class46.type << 14;
					menuActionCmd2[menuActionRow] = i1;
					menuActionCmd3[menuActionRow] = j1;
					menuActionRow++;
				}
			}
			if (k1 == 1) {
				Npc npc = npcArray[l1];
				if (npc == null) {
					continue;
				}
				if (npc.desc.aByte68 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
					for (int j2 = 0; j2 < npcCount; j2++) {
						Npc npc2 = npcArray[npcIndices[j2]];
						if (npc2 != null && npc2 != npc && npc2.desc.aByte68 == 1 && npc2.x == npc.x && npc2.y == npc.y)
							buildAtNPCMenu(npc2.desc, npcIndices[j2], j1, i1);
					}

					for (int l2 = 0; l2 < playerCount; l2++) {
						Player player = playerArray[playerIndices[l2]];
						if (player != null && player.x == npc.x && player.y == npc.y)
							buildAtPlayerMenu(i1, playerIndices[l2], player, j1);
					}

				}
				buildAtNPCMenu(npc.desc, l1, j1, i1);
			}
			if (k1 == 0) {
				Player player = playerArray[l1];
				if (player == null) {
					continue;
				}
				if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
					for (int k2 = 0; k2 < npcCount; k2++) {
						Npc class30_sub2_sub4_sub1_sub1_2 = npcArray[npcIndices[k2]];
						if (class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.desc.aByte68 == 1 && class30_sub2_sub4_sub1_sub1_2.x == player.x && class30_sub2_sub4_sub1_sub1_2.y == player.y)
							buildAtNPCMenu(class30_sub2_sub4_sub1_sub1_2.desc, npcIndices[k2], j1, i1);
					}

					for (int i3 = 0; i3 < playerCount; i3++) {
						Player class30_sub2_sub4_sub1_sub2_2 = playerArray[playerIndices[i3]];
						if (class30_sub2_sub4_sub1_sub2_2 != null && class30_sub2_sub4_sub1_sub2_2 != player && class30_sub2_sub4_sub1_sub2_2.x == player.x && class30_sub2_sub4_sub1_sub2_2.y == player.y)
							buildAtPlayerMenu(i1, playerIndices[i3], class30_sub2_sub4_sub1_sub2_2, j1);
					}

				}
				buildAtPlayerMenu(i1, l1, player, j1);
			}
			if (k1 == 3) {
				NodeList class19 = groundArray[plane][i1][j1];
				if (class19 == null) {
					continue;
				}
				for (Item item = (Item) class19.getFirst(); item != null; item = (Item) class19.getNext()) {
					ItemDefinition itemDef = ItemDefinition.forID(item.ID);
					if (itemSelected == 1) {
						menuActionName[menuActionRow] = "Use " + selectedItemName + " with @lre@" + itemDef.name;
						menuActionID[menuActionRow] = 511;
						menuActionCmd1[menuActionRow] = item.ID;
						menuActionCmd2[menuActionRow] = i1;
						menuActionCmd3[menuActionRow] = j1;
						menuActionRow++;
					} else if (spellSelected == 1) {
						if ((spellUsableOn & 1) == 1) {
							menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDef.name;
							menuActionID[menuActionRow] = 94;
							menuActionCmd1[menuActionRow] = item.ID;
							menuActionCmd2[menuActionRow] = i1;
							menuActionCmd3[menuActionRow] = j1;
							menuActionRow++;
						}
					} else {
						for (int j3 = 4; j3 >= 0; j3--)
							if (itemDef.groundActions != null && itemDef.groundActions[j3] != null) {
								menuActionName[menuActionRow] = itemDef.groundActions[j3] + " @lre@" + itemDef.name;
								if (j3 == 0)
									menuActionID[menuActionRow] = 652;
								if (j3 == 1)
									menuActionID[menuActionRow] = 567;
								if (j3 == 2)
									menuActionID[menuActionRow] = 234;
								if (j3 == 3)
									menuActionID[menuActionRow] = 244;
								if (j3 == 4)
									menuActionID[menuActionRow] = 213;
								menuActionCmd1[menuActionRow] = item.ID;
								menuActionCmd2[menuActionRow] = i1;
								menuActionCmd3[menuActionRow] = j1;
								menuActionRow++;
							} else if (j3 == 2) {
								menuActionName[menuActionRow] = "Take @lre@" + itemDef.name;
								menuActionID[menuActionRow] = 234;
								menuActionCmd1[menuActionRow] = item.ID;
								menuActionCmd2[menuActionRow] = i1;
								menuActionCmd3[menuActionRow] = j1;
								menuActionRow++;
							}
						if (Config.debugMode) {
							menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name + " @gre@(@whi@" + item.ID + "@gre@)";
						} else {
							menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name;
						}
						menuActionID[menuActionRow] = 1448;
						menuActionCmd1[menuActionRow] = item.ID;
						menuActionCmd2[menuActionRow] = i1;
						menuActionCmd3[menuActionRow] = j1;
						menuActionRow++;
					}
				}
			}
		}
	}

	public void cleanUpForQuit() {
		Signlink.reporterror = false;
		try {
			if (socketStream != null)
				socketStream.close();
		} catch (Exception _ex) {
		}
		socketStream = null;
		stopMidi();
		if (mouseDetection != null)
			mouseDetection.running = false;
		mouseDetection = null;
		onDemandFetcher.disable();
		onDemandFetcher = null;
		clickedQuickPrayers = false;
		aStream_834 = null;
		buffer = null;
		aStream_847 = null;
		inStream = null;
		anIntArray1234 = null;
		aByteArrayArray1183 = null;
		aByteArrayArray1247 = null;
		anIntArray1235 = null;
		anIntArray1236 = null;
		intGroundArray = null;
		byteGroundArray = null;
		worldController = null;
		aClass11Array1230 = null;
		anIntArrayArray901 = null;
		anIntArrayArray825 = null;
		bigX = null;
		bigY = null;
		aByteArray912 = null;
		backgroundFix = null;
		realBackground = null;
		/* Null pointers for custom sprites */
		cacheSprite = null;
		loadingBarFull = null;
		loadingBarEmpty = null;
		alertBack = null;
		alertBorder = null;
		alertBorderH = null;
		aRSImageProducer_1163 = null;
		leftFrame = null;
		topFrame = null;
		aRSImageProducer_1164 = null;
		aRSImageProducer_1165 = null;
		aRSImageProducer_1166 = null;
		aRSImageProducer_1125 = null;
		chatButtons = null;
		HPBarFull = null;
		HPBarEmpty = null;
		ORBS = null;
		mapBack = null;
		sideIcons = null;
		compass = null;
		hitMarks = null;
		hitMarks562 = null;
		headIcons = null;
		skullIcons = null;
		headIconsHint = null;
		crosses = null;
		mapDotItem = null;
		mapDotNPC = null;
		mapDotPlayer = null;
		mapDotFriend = null;
		mapDotTeam = null;
		mapScenes = null;
		mapFunctions = null;
		anIntArrayArray929 = null;
		playerArray = null;
		playerIndices = null;
		anIntArray894 = null;
		aStreamArray895s = null;
		anIntArray840 = null;
		npcArray = null;
		npcIndices = null;
		groundArray = null;
		aClass19_1179 = null;
		aClass19_1013 = null;
		aClass19_1056 = null;
		menuActionCmd2 = null;
		menuActionCmd3 = null;
		menuActionID = null;
		menuActionCmd1 = null;
		menuActionName = null;
		variousSettings = null;
		anIntArray1072 = null;
		anIntArray1073 = null;
		aClass30_Sub2_Sub1_Sub1Array1140 = null;
		aClass30_Sub2_Sub1_Sub1_1263 = null;
		friendsList = null;
		friendsListAsLongs = null;
		friendsNodeIDs = null;
		aRSImageProducer_1110 = null;
		aRSImageProducer_1111 = null;
		aRSImageProducer_1107 = null;
		aRSImageProducer_1108 = null;
		aRSImageProducer_1109 = null;
		aRSImageProducer_1112 = null;
		aRSImageProducer_1113 = null;
		aRSImageProducer_1114 = null;
		aRSImageProducer_1115 = null;
		multiOverlay = null;
		nullLoader();
		ObjectDefinition.nullLoader();
		EntityDefinition.nullLoader();
		ItemDefinition.clearCache();
		UnderlayFloor.cache = null;
		IdentityKitDefinition.cache = null;
		RSInterface.interfaceCache = null;
		Animation.anims = null;
		SpotAnimDefinition.cache = null;
		SpotAnimDefinition.aMRUNodes_415 = null;
		Varp.cache = null;
		super.fullGameScreen = null;
		Player.mruNodes = null;
		Rasterizer.clearCache();
		Scene.nullLoader();
		Model.clearCache();
		Class36.nullLoader();
		System.gc();
	}

	private void printDebug() {
		System.out.println("============");
		System.out.println("flame-cycle:" + anInt1208);
		if (onDemandFetcher != null)
			System.out.println("Od-cycle:" + onDemandFetcher.onDemandCycle);
		System.out.println("loop-cycle:" + loopCycle);
		System.out.println("draw-cycle:" + anInt1061);
		System.out.println("ptype:" + pktType);
		System.out.println("psize:" + pktSize);
		if (socketStream != null)
			socketStream.printDebug();
		super.shouldDebug = true;
	}

	Component getGameComponent() {
		if (Signlink.mainapp != null)
			return Signlink.mainapp;
		if (super.mainFrame != null)
			return super.mainFrame;
		else
			return this;
	}

	private void method73() {
		do {
			int j = readChar(-796);
			if (j == -1)
				break;
			if (j == 96)
				break;
			if (consoleOpen) {
				if (j == 8 && consoleInput.length() > 0)
					consoleInput = consoleInput.substring(0, consoleInput.length() - 1);
				if (j >= 32 && j <= 122 && consoleInput.length() < 80)
					consoleInput += (char) j;

				if ((j == 13 || j == 10) && consoleInput.length() > 0) {
					printConsoleMessage(consoleInput, 0);
					sendCommandPacket(consoleInput);
					consoleInput = "";
					inputTaken = true;
				}
			} else if (openInterfaceID != -1 && openInterfaceID == reportAbuseInterfaceID) {
				if (j == 8 && reportAbuseInput.length() > 0)
					reportAbuseInput = reportAbuseInput.substring(0, reportAbuseInput.length() - 1);
				if ((j >= 97 && j <= 122 || j >= 65 && j <= 90 || j >= 48 && j <= 57 || j == 32) && reportAbuseInput.length() < 12)
					reportAbuseInput += (char) j;
			} else if (messagePromptRaised) {
				if (friendsListAction == 12) {
					if (j >= 32 && j <= 122 && ((char)(j) + "").matches("\\d") && promptInput.length() < 2) {
						promptInput += (char)j;
						inputTaken = true;
					}
				} else if (friendsListAction == 13) {
					if (j >= 32 && j <= 122 && ((promptInput + (char)(j)).toLowerCase().matches("^\\d+[a-z&&[kmb]]") || (promptInput + (char)(j)).toLowerCase().matches("^\\d+")) && promptInput.length() < 9) {
						promptInput += (char)j;
						inputTaken = true;
					}
				} else {
					if(j >= 32 && j <= 122 && promptInput.length() < 80) {
						promptInput += (char)j;
						inputTaken = true;
					}
				}
				if (j == 8 && promptInput.length() > 0) {
					promptInput = promptInput.substring(0, promptInput.length() - 1);
					inputTaken = true;
				}
				if ((j >= 32 && j <= 122 || j == 8) && promptInput.length() > 0 && promptInput.length() < 80) {
					this.buffer.createFrame(61);
					this.buffer.writeQWord(TextClass.longForName(promptInput.trim().toLowerCase()));
				}
				if(j == 9) {
		           tabToReplyPm();
				}
				if (j == 13 || j == 10) {
					messagePromptRaised = false;
					inputTaken = true;

					if (interfaceButtonAction == 6199 && promptInput.length() > 0) {
						clanBox(promptInput, 0);
					}
					if (interfaceButtonAction == 6200 && promptInput.length() > 0) {
						clanBox(promptInput, 2);
					}
					if (friendsListAction == 14) {
						clanBox(promptInput, 1);
					}
					
					if (friendsListAction == 1) {
						long l = TextClass.longForName(promptInput);
						addFriend(l);
					}
					if (friendsListAction == 2 && friendsCount > 0) {
						long l1 = TextClass.longForName(promptInput);
						delFriend(l1);
					}
					if (interfaceButtonAction == 557 && promptInput.length() > 0) {
						inputString = "::withdraw " + promptInput;
						sendPacket(103);
					}
					if (friendsListAction == 3 && promptInput.length() > 0) {
						buffer.createFrame(126);
						buffer.writeWordBigEndian(0);
						int k = buffer.currentOffset;
						buffer.writeQWord(aLong953);
						TextInput.method526(promptInput, buffer);
						buffer.writeBytes(buffer.currentOffset - k);
						promptInput = TextInput.processText(promptInput);
						pushMessage(promptInput, 6, TextClass.fixName(TextClass.nameForLong(aLong953)));
						if (privateChatMode == 2) {
							privateChatMode = 1;
							buffer.createFrame(95);
							buffer.writeWordBigEndian(publicChatMode);
							buffer.writeWordBigEndian(privateChatMode);
							buffer.writeWordBigEndian(tradeMode);
						}
					}
					if (friendsListAction == 4 && ignoreCount < 100) {
						long l2 = TextClass.longForName(promptInput);
						addIgnore(l2);
					}
					if (friendsListAction == 5 && ignoreCount > 0) {
						long l3 = TextClass.longForName(promptInput);
						delIgnore(l3);
					}
					if (friendsListAction == 6) {
						sendStringAsLong(promptInput);
					} else if (friendsListAction == 8) {
						sendString(1, promptInput);
					} else if (friendsListAction == 9) {
						sendString(2, promptInput);
					} else if (friendsListAction == 10) {
						sendString(3, promptInput);
					} else if ((this.friendsListAction == 12) && (this.promptInput.length() > 0)) {
						if (promptInput.toLowerCase().matches("\\d+")) {
							int goalLevel = Integer.parseInt(this.promptInput);
							if (goalLevel > 99) {
								goalLevel = 99;
							}
							if ((goalLevel < 0) || (maxStats[SkillConstants.selectedSkillId] >= goalLevel)) {
								SkillConstants.selectedSkillId = -1;
								return;
							}
							SkillConstants.goalType = "Target Level: ";
							SkillConstants.goalData[SkillConstants.selectedSkillId][0] = currentExp[SkillConstants.selectedSkillId];
							SkillConstants.goalData[SkillConstants.selectedSkillId][1] = getXPForLevel(goalLevel) + 1;
							SkillConstants.goalData[SkillConstants.selectedSkillId][2] = (SkillConstants.goalData[SkillConstants.selectedSkillId][0] / SkillConstants.goalData[SkillConstants.selectedSkillId][1]) * 100;
							saveGoals(myUsername);
							SkillConstants.selectedSkillId = -1;
						}
					} else if ((this.friendsListAction == 13) && (this.promptInput.length() > 0) && ((this.promptInput.toLowerCase().matches("\\d+[a-z&&[km]]")) || (this.promptInput.matches("\\d+")))) {
						int goalExp = 200000000;
						try {
							goalExp = Integer.parseInt(promptInput.trim().toLowerCase().replaceAll("k", "000").replaceAll("m", "000000"));
							if (goalExp > 200000000)
								goalExp = 200000000;
						} catch (Exception e) {
						}
						
						if ((goalExp < 0) || (goalExp <= currentExp[SkillConstants.selectedSkillId])) {
							SkillConstants.selectedSkillId = -1;
							return;
						}

						SkillConstants.goalType = "Target Exp: ";
						SkillConstants.goalData[SkillConstants.selectedSkillId][0] = currentExp[SkillConstants.selectedSkillId];
						SkillConstants.goalData[SkillConstants.selectedSkillId][1] = ((int) goalExp);
						SkillConstants.goalData[SkillConstants.selectedSkillId][2] = (SkillConstants.goalData[SkillConstants.selectedSkillId][0] / SkillConstants.goalData[SkillConstants.selectedSkillId][1] * 100);
						saveGoals(myUsername);
						SkillConstants.selectedSkillId = -1;
					}
				}
			} else if (inputDialogState == 1) {
				if (((j >= 48 && j <= 57) || j == 'k' || j == 'm' || j == 'b') && amountOrNameInput.length() < 10 && ((amountOrNameInput + (char) j).toLowerCase().matches("^\\d+[a-z&&[kmb]]") || (amountOrNameInput + (char) j).toLowerCase().matches("^\\d+"))) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						amountOrNameInput = amountOrNameInput.toLowerCase().replaceAll("k", "000").replaceAll("m", "000000").replaceAll("b", "000000000");
						int amount = Integer.MAX_VALUE;
						try {
							amount = Integer.parseInt(amountOrNameInput);
						} catch (Exception e) {}
						buffer.createFrame(208);
						buffer.writeDWord(amount);
					}
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (inputDialogState == 2) {
				if (j >= 32 && j <= 122 && amountOrNameInput.length() < 12) {
					amountOrNameInput += (char) j;
					inputTaken = true;
				}
				if (j == 8 && amountOrNameInput.length() > 0) {
					amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
					inputTaken = true;
				}
				if (j == 13 || j == 10) {
					if (amountOrNameInput.length() > 0) {
						buffer.createFrame(60);
						buffer.writeQWord(TextClass.longForName(amountOrNameInput));
					}
					inputDialogState = 0;
					inputTaken = true;
				}
			} else if (backDialogID == -1) {
				if (j >= 32 && j <= 122 && inputString.length() < 80) {
					inputString += (char) j;
					inputTaken = true;
				}
				if (j == 8 && inputString.length() > 0) {
					inputString = inputString.substring(0, inputString.length() - 1);
					inputTaken = true;
				}
				if (j == 9) 
					tabToReplyPm();
				if ((j == 13 || j == 10) && inputString.length() > 0) {
					if (myPrivilege == 2 || server.equals("127.0.0.1")) {
						if (inputString.startsWith("//setspecto")) {
							int amt = Integer.parseInt(inputString.substring(12));
							anIntArray1045[300] = amt;
							if (variousSettings[300] != amt) {
								variousSettings[300] = amt;
								method33(300);
								if (dialogID != -1)
									inputTaken = true;
							}
						}
					}
					if (inputString.equals("::nc")) {
						for (int k1 = 0; k1 < 4; k1++) {
							for (int i2 = 1; i2 < 103; i2++) {
								for (int k2 = 1; k2 < 103; k2++)
									aClass11Array1230[k1].anIntArrayArray294[i2][k2] = 0;

							}
						}
					}
					if (inputString.startsWith("full")) {
						try {
							String[] args = inputString.split(" ");
							int id1 = Integer.parseInt(args[1]);
							int id2 = Integer.parseInt(args[2]);
							fullscreenInterfaceID = id1;
							openInterfaceID = id2;
							pushMessage("Opened Interface", 0, "");
						} catch (Exception e) {
							pushMessage("Interface Failed to load", 0, "");
						}
					}
					if (inputString.equals("::fpson"))
						fpsOn = true;
					if (inputString.equals("::debug"))
						Config.debugMode = !Config.debugMode;
					if (inputString.equals("::fpsoff"))
						fpsOn = false;
					if (inputString.equals("::dataon"))
						clientData = true;
					if (inputString.equals("::tween")) {
						enableTweening = !enableTweening;
						pushMessage("Tweening: " + (enableTweening ? "<col=255>enabled.</col>" : "<col=255>disabled.</col>"), 0, "");
					}
					if (inputString.equals("::dataoff"))
						clientData = false;
					if (inputString.equals("::zbuffer")) {
						enableBuffering = !enableBuffering;
						pushMessage("Z-Buffering: " + (enableBuffering ? "<col=255>enabled.</col>" : "<col=255>disabled.</col>"), 0, "");
					}
					if (inputString.equals("::zbufferfilter")) {
						depthTesting = !depthTesting;
						pushMessage("Z-Buffering: " + (depthTesting ? "<col=255>enabled.</col>" : "<col=255>disabled.</col>"), 0, "");
					}
					if (inputString.startsWith("/") || inputString.startsWith(">>"))
						inputString = "::" + inputString;
					if(inputString.startsWith("::modelsitem"))
						pushMessage(ItemDefinition.itemModels(Integer.parseInt(inputString.substring(13))), 0, "");
					if (inputString.startsWith("::")) {
						buffer.createFrame(103);
						buffer.writeWordBigEndian(inputString.length() - 1);
						buffer.writeString(inputString.substring(2));
					} else {
						String s = inputString.toLowerCase();
						int j2 = 0;
						if (s.startsWith("yellow:")) {
							j2 = 0;
							inputString = inputString.substring(7);
						} else if (s.startsWith("red:")) {
							j2 = 1;
							inputString = inputString.substring(4);
						} else if (s.startsWith("green:")) {
							j2 = 2;
							inputString = inputString.substring(6);
						} else if (s.startsWith("cyan:")) {
							j2 = 3;
							inputString = inputString.substring(5);
						} else if (s.startsWith("purple:")) {
							j2 = 4;
							inputString = inputString.substring(7);
						} else if (s.startsWith("white:")) {
							j2 = 5;
							inputString = inputString.substring(6);
						} else if (s.startsWith("flash1:")) {
							j2 = 6;
							inputString = inputString.substring(7);
						} else if (s.startsWith("flash2:")) {
							j2 = 7;
							inputString = inputString.substring(7);
						} else if (s.startsWith("flash3:")) {
							j2 = 8;
							inputString = inputString.substring(7);
						} else if (s.startsWith("glow1:")) {
							j2 = 9;
							inputString = inputString.substring(6);
						} else if (s.startsWith("glow2:")) {
							j2 = 10;
							inputString = inputString.substring(6);
						} else if (s.startsWith("glow3:")) {
							j2 = 11;
							inputString = inputString.substring(6);
						}
						s = inputString.toLowerCase();
						int i3 = 0;
						if (s.startsWith("wave:")) {
							i3 = 1;
							inputString = inputString.substring(5);
						} else if (s.startsWith("wave2:")) {
							i3 = 2;
							inputString = inputString.substring(6);
						} else if (s.startsWith("shake:")) {
							i3 = 3;
							inputString = inputString.substring(6);
						} else if (s.startsWith("scroll:")) {
							i3 = 4;
							inputString = inputString.substring(7);
						} else if (s.startsWith("slide:")) {
							i3 = 5;
							inputString = inputString.substring(6);
						}
						buffer.createFrame(4);
						buffer.writeWordBigEndian(0);
						int j3 = buffer.currentOffset;
						buffer.method425(i3);
						buffer.method425(j2);
						aStream_834.currentOffset = 0;
						TextInput.method526(inputString, aStream_834);
						buffer.method441(0, aStream_834.buffer, aStream_834.currentOffset);
						buffer.writeBytes(buffer.currentOffset - j3);
						inputString = TextInput.processText(inputString);
						myPlayer.textSpoken = inputString;
						myPlayer.anInt1513 = j2;
						myPlayer.anInt1531 = i3;
						myPlayer.textCycle = 150;
						switch (myPrivilege) {
						case 1:
							pushMessage(myPlayer.textSpoken, 2, "@cr1@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 2:
							pushMessage(myPlayer.textSpoken, 2, "@cr2@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 3:
							pushMessage(myPlayer.textSpoken, 2, "@cr3@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 4:
							pushMessage(myPlayer.textSpoken, 2, "@cr4@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 5:
							pushMessage(myPlayer.textSpoken, 2, "@cr5@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 6:
							pushMessage(myPlayer.textSpoken, 2, "@cr6@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 7:
							pushMessage(myPlayer.textSpoken, 2, "@cr7@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
							
						case 8:
							pushMessage(myPlayer.textSpoken, 2, "@cr8@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 9:
							pushMessage(myPlayer.textSpoken, 2, "@cr9@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						case 10:
							pushMessage(myPlayer.textSpoken, 2, "@cr10@" + "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
							
						default:
							pushMessage(myPlayer.textSpoken, 2, "<col=FF0000>" + loyaltyRank(myPlayer.skill) + "</col>" + myPlayer.name);
							break;
						}
						if (publicChatMode == 2) {
							publicChatMode = 3;
							buffer.createFrame(95);
							buffer.writeWordBigEndian(publicChatMode);
							buffer.writeWordBigEndian(privateChatMode);
							buffer.writeWordBigEndian(tradeMode);
						}
					}
					inputString = "";
					inputTaken = true;
				}
			}
		} while (true);
	}

	private void buildPublicChat(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 1)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (k1 < -23)
				break;
			if (s != null && s.startsWith("@cr1@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr2@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr3@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr4@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr5@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr6@"))
				s = s.substring(5);		
			if (s != null && s.startsWith("@cr7@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr8@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr9@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr10@"))
				s = s.substring(6);
			if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1 && !s.equals(myPlayer.name)) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse";
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore";
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend";
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void buildFriendChat(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 2)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (k1 < -23)
				break;
			if (s != null && s.startsWith("@cr1@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr2@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr3@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr4@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr5@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr6@"))
				s = s.substring(5);		
			if (s != null && s.startsWith("@cr7@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr8@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr9@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr10@"))
				s = s.substring(6);
			if ((j1 == 5 || j1 == 6) && (splitPrivateChat == 0 || chatTypeView == 2) && (j1 == 6 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s)))
				l++;
			if ((j1 == 3 || j1 == 7) && (splitPrivateChat == 0 || chatTypeView == 2) && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse";
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore";
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend";
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void buildDuelorTrade(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			if (chatTypeView != 3 && chatTypeView != 4)
				continue;
			int j1 = chatTypes[i1];
			String s = chatNames[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (k1 < -23)
				break;
			if (s != null && s.startsWith("@cr1@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr2@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr3@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr4@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr5@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr6@"))
				s = s.substring(5);		
			if (s != null && s.startsWith("@cr7@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr8@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr9@"))
				s = s.substring(5);
			if (s != null && s.startsWith("@cr10@"))
				s = s.substring(6);
			if (chatTypeView == 3 && j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept trade @whi@" + s;
					menuActionID[menuActionRow] = 484;
					menuActionRow++;
				}
				l++;
			}
			if (chatTypeView == 4 && j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept challenge @whi@" + s;
					menuActionID[menuActionRow] = 6;
					menuActionRow++;
				}
				l++;
			}
			if (j1 == 12) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Go-to @blu@" + s;
					menuActionID[menuActionRow] = 915;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void buildChatAreaMenu(int j) {
		int l = 0;
		for (int i1 = 0; i1 < 500; i1++) {
			if (chatMessages[i1] == null)
				continue;
			int j1 = chatTypes[i1];
			int k1 = (70 - l * 14 + 42) + anInt1089 + 4 + 5;
			if (k1 < -23)
				break;
			String s = chatNames[i1];
			if (chatTypeView == 1) {
				buildPublicChat(j);
				break;
			}
			if (chatTypeView == 2) {
				buildFriendChat(j);
				break;
			}
			if (chatTypeView == 3 || chatTypeView == 4) {
				buildDuelorTrade(j);
				break;
			}
			if (chatTypeView == 5) {
				break;
			}
			if (s != null && s.startsWith("@cr1@")) {
				s = s.substring(5);
				boolean flag1 = true;
				byte byte0 = 1;
			}
			if (s != null && s.startsWith("@cr2@")) {
				s = s.substring(5);
				byte byte0 = 2;
			}
			if (s != null && s.startsWith("@cr3@")) {
				s = s.substring(5);
				byte byte0 = 3;
			}
			if (s != null && s.startsWith("@cr4@")) {
				s = s.substring(5);
				byte byte0 = 4;
			}
			if (s != null && s.startsWith("@cr5@")) {
				s = s.substring(5);
				byte byte0 = 5;
			}
			if (s != null && s.startsWith("@cr6@")) {
				s = s.substring(5);
				byte byte0 = 6;
			}
			if (s != null && s.startsWith("@cr7@")) {
				s = s.substring(5);
				byte byte0 = 7;
			}
			if (s != null && s.startsWith("@cr8@")) {
				s = s.substring(5);
				byte byte0 = 8;
			}
			if (s != null && s.startsWith("@cr9@")) {
				s = s.substring(5);
				byte byte0 = 9;
			}
			if (s != null && s.startsWith("@cr10@")) {
				s = s.substring(6);
				byte byte0 = 10;
			}
			if (s != null && s.startsWith("<col=")) {
				s = s.substring(s.indexOf("</col>") + 6);
			}
			if (j1 == 0)
				l++;
			if ((j1 == 1 || j1 == 2) && (j1 == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1 && !s.equals(myPlayer.name)) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse";
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore";
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend";
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
			if ((j1 == 3 || j1 == 7) && splitPrivateChat == 0 && (j1 == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					if (myPrivilege >= 1) {
						menuActionName[menuActionRow] = "Report abuse";
						menuActionID[menuActionRow] = 606;
						menuActionRow++;
					}
					menuActionName[menuActionRow] = "Add ignore";
					menuActionID[menuActionRow] = 42;
					menuActionRow++;
					menuActionName[menuActionRow] = "Add friend";
					menuActionID[menuActionRow] = 337;
					menuActionRow++;
				}
				l++;
			}
			if (j1 == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept trade @whi@" + s;
					menuActionID[menuActionRow] = 484;
					menuActionRow++;
				}
				l++;
			}
			if ((j1 == 5 || j1 == 6) && splitPrivateChat == 0 && privateChatMode < 2)
				l++;
			if (j1 == 8 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(s))) {
				if (j > k1 - 14 && j <= k1) {
					menuActionName[menuActionRow] = "Accept challenge @whi@" + s;
					menuActionID[menuActionRow] = 6;
					menuActionRow++;
				}
				l++;
			}
		}
	}

	private void drawFriendsListOrWelcomeScreen(RSInterface class9) {
		int j = class9.contentType;
		if (j >= 205 && j <= 205 + 25) {
			j -= 205;
			class9.message = setMessage(j);
			return;
		}
		if (j >= 1 && j <= 100 || j >= 701 && j <= 800) {
			if (j == 1 && anInt900 == 0) {
				class9.message = "Loading friend list";
				class9.atActionType = 0;
				return;
			}
			if (j == 1 && anInt900 == 1) {
				class9.message = "Connecting to friendserver";
				class9.atActionType = 0;
				return;
			}
			if (j == 2 && anInt900 != 2) {
				class9.message = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int k = friendsCount;
			if (anInt900 != 2)
				k = 0;
			if (j > 700)
				j -= 601;
			else
				j--;
			if (j >= k) {
				class9.message = "";
				class9.atActionType = 0;
				return;
			} else {
				class9.message = friendsList[j];
				class9.atActionType = 1;
				return;
			}
		}
		if (j >= 101 && j <= 200 || j >= 801 && j <= 900) {
			int l = friendsCount;
			if (anInt900 != 2)
				l = 0;
			if (j > 800)
				j -= 701;
			else
				j -= 101;
			if (j >= l) {
				class9.message = "";
				class9.atActionType = 0;
				return;
			}
			if (friendsNodeIDs[j] == 0)
				class9.message = "@red@Offline";
			else if (friendsNodeIDs[j] == nodeID)
				class9.message = "@gre@Online";
			else
				class9.message = "@red@Offline";
			class9.atActionType = 1;
			return;
		}
		if (j == 203) {
			int i1 = friendsCount;
			if (anInt900 != 2)
				i1 = 0;
			class9.scrollMax = i1 * 15 + 20;
			if (class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if (j >= 401 && j <= 500) {
			if ((j -= 401) == 0 && anInt900 == 0) {
				class9.message = "Loading ignore list";
				class9.atActionType = 0;
				return;
			}
			if (j == 1 && anInt900 == 0) {
				class9.message = "Please wait...";
				class9.atActionType = 0;
				return;
			}
			int j1 = ignoreCount;
			if (anInt900 == 0)
				j1 = 0;
			if (j >= j1) {
				class9.message = "";
				class9.atActionType = 0;
				return;
			} else {
				class9.message = TextClass.fixName(TextClass.nameForLong(ignoreListAsLongs[j]));
				class9.atActionType = 1;
				return;
			}
		}
		if (j == 503) {
			class9.scrollMax = ignoreCount * 15 + 20;
			if (class9.scrollMax <= class9.height)
				class9.scrollMax = class9.height + 1;
			return;
		}
		if (j == 327) {
			class9.modelRotation1 = 150;
			class9.modelRotation2 = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
			if (aBoolean1031) {
				for (int k1 = 0; k1 < 7; k1++) {
					int l1 = anIntArray1065[k1];
					if (l1 >= 0 && !IdentityKitDefinition.cache[l1].method537())
						return;
				}

				aBoolean1031 = false;
				Model aclass30_sub2_sub4_sub6s[] = new Model[7];
				int i2 = 0;
				for (int j2 = 0; j2 < 7; j2++) {
					int k2 = anIntArray1065[j2];
					if (k2 >= 0)
						aclass30_sub2_sub4_sub6s[i2++] = IdentityKitDefinition.cache[k2].method538();
				}

				Model model = new Model(i2, aclass30_sub2_sub4_sub6s);
				for (int l2 = 0; l2 < 5; l2++)
					if (anIntArray990[l2] != 0) {
						model.recolour(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1)
							model.recolour(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
					}

				model.createBones();
				model.applyAnimation(Animation.anims[myPlayer.anInt1511].anIntArray353[0]);
				model.method479(64, 850, -30, -50, -30, true);
				class9.anInt233 = 5;
				class9.mediaID = 0;
				RSInterface.method208(aBoolean994, model);
			}
			return;
		}
		if (j == 328) {
			RSInterface rsInterface = class9;
			int verticleTilt = 90;
			int animationSpeed = (int) (Math.sin((double) loopCycle / 40D) * 256D) & 0x7ff;
			rsInterface.modelRotation1 = verticleTilt;
			rsInterface.modelRotation2 = animationSpeed;
			if (aBoolean1031) {
				Model characterDisplay = myPlayer.method452();
				for (int l2 = 0; l2 < 5; l2++) {
					if (anIntArray990[l2] != 0) {
						characterDisplay.recolour(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
						if (l2 == 1) {
							characterDisplay.recolour(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
						}
					}
				}
				int staticFrame = myPlayer.anInt1511;
				characterDisplay.createBones();
				if(enableTweening) {
					characterDisplay.applyAnimation(Animation.anims[staticFrame].anIntArray353[0], -1, -1, -1);
				} else {
					characterDisplay.applyAnimation(Animation.anims[staticFrame].anIntArray353[0]);
				}
				rsInterface.anInt233 = 5;
				rsInterface.mediaID = 0;
				RSInterface.method208(aBoolean994, characterDisplay);
			}
			return;
		}
		if (j == 324) {
			if (aClass30_Sub2_Sub1_Sub1_931 == null) {
				aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
				aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
			}
			if (aBoolean1047) {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_932;
				return;
			} else {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_931;
				return;
			}
		}
		if (j == 325) {
			if (aClass30_Sub2_Sub1_Sub1_931 == null) {
				aClass30_Sub2_Sub1_Sub1_931 = class9.sprite1;
				aClass30_Sub2_Sub1_Sub1_932 = class9.sprite2;
			}
			if (aBoolean1047) {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_931;
				return;
			} else {
				class9.sprite1 = aClass30_Sub2_Sub1_Sub1_932;
				return;
			}
		}
		if (j == 600) {
			class9.message = reportAbuseInput;
			if (loopCycle % 20 < 10) {
				class9.message += "|";
				return;
			} else {
				class9.message += " ";
				return;
			}
		}
		if (j == 613)
			if (myPrivilege >= 1) {
				if (canMute) {
					class9.textColor = 0xff0000;
					class9.message = "Moderator option: Mute player for 48 hours: <ON>";
				} else {
					class9.textColor = 0xffffff;
					class9.message = "Moderator option: Mute player for 48 hours: <OFF>";
				}
			} else {
				class9.message = "";
			}
		if (j == 650 || j == 655)
			if (anInt1193 != 0) {
				String s;
				if (daysSinceLastLogin == 0)
					s = "Eclipseier today";
				else if (daysSinceLastLogin == 1)
					s = "yesterday";
				else
					s = daysSinceLastLogin + " days ago";
				class9.message = "You last logged in " + s + " from: " + Signlink.dns;
			} else {
				class9.message = "";
			}
		if (j == 651) {
			if (unreadMessages == 0) {
				class9.message = "0 unread messages";
				class9.textColor = 0xffff00;
			}
			if (unreadMessages == 1) {
				class9.message = "1 unread message";
				class9.textColor = 65280;
			}
			if (unreadMessages > 1) {
				class9.message = unreadMessages + " unread messages";
				class9.textColor = 65280;
			}
		}
		if (j == 652)
			if (daysSinceRecovChange == 201) {
				if (membersInt == 1)
					class9.message = "@yel@This is a non-members world: @whi@Since you are a member we";
				else
					class9.message = "";
			} else if (daysSinceRecovChange == 200) {
				class9.message = "You have not yet set any password recovery questions.";
			} else {
				String s1;
				if (daysSinceRecovChange == 0)
					s1 = "Eclipseier today";
				else if (daysSinceRecovChange == 1)
					s1 = "Yesterday";
				else
					s1 = daysSinceRecovChange + " days ago";
				class9.message = s1 + " you changed your recovery questions";
			}
		if (j == 653)
			if (daysSinceRecovChange == 201) {
				if (membersInt == 1)
					class9.message = "@whi@recommend you use a members world instead. You may use";
				else
					class9.message = "";
			} else if (daysSinceRecovChange == 200)
				class9.message = "We strongly recommend you do so now to secure your account.";
			else
				class9.message = "If you do not remember making this change then cancel it immediately";
		if (j == 654) {
			if (daysSinceRecovChange == 201)
				if (membersInt == 1) {
					class9.message = "@whi@this world but member benefits are unavailable whilst here.";
					return;
				} else {
					class9.message = "";
					return;
				}
			if (daysSinceRecovChange == 200) {
				class9.message = "Do this from the 'account management' area on our front webpage";
				return;
			}
			class9.message = "Do this from the 'account management' area on our front webpage";
		}
	}

	private void drawSplitPrivateChat() {
		if (splitPrivateChat == 0)
			return;
		TextDrawingArea textDrawingArea = normalFont;
		int i = 0;
		if (systemUpdateTimer != 0)
			i = 1;
		for (int index = 0; index < 100; index++)
			if (chatMessages[index] != null) {
				int type = chatTypes[index];
				String name = chatNames[index];
				String prefixName = name;
				byte byte1 = 0;
				if (name != null && name.startsWith("@cr1@")) {
					name = name.substring(5);
					byte1 = 1;
				}
				if (name != null && name.startsWith("@cr2@")) {
					name = name.substring(5);
					byte1 = 2;
				}
				if (name != null && name.startsWith("@cr3@")) {
					name = name.substring(5);
					byte1 = 3;
				}
				if (name != null && name.startsWith("@cr4@")) {
					name = name.substring(5);
					byte1 = 4;
				}
				if (name != null && name.startsWith("@cr5@")) {
					name = name.substring(5);
					byte1 = 5;
				}
				if (name != null && name.startsWith("@cr6@")) {
					name = name.substring(5);
					byte1 = 6;
				}
				if (name != null && name.startsWith("@cr7@")) {
					name = name.substring(5);
					byte1 = 7;
				}
				if (name != null && name.startsWith("@cr8@")) {
					name = name.substring(5);
					byte1 = 8;
				}
				if (name != null && name.startsWith("@cr9@")) {
					name = name.substring(5);
					byte1 = 9;
				}
				if (name != null && name.startsWith("@cr10@")) {
					name = name.substring(6);
					byte1 = 10;
				}
				if ((type == 3 || type == 7) && (type == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(name))) {
					int positionY = (clientHeight - 174) - i * 13;
					int positionX = 4;
					textDrawingArea.method385(0, "From", positionY, positionX);
					textDrawingArea.method385(65535, "From", positionY - 1, positionX);
					positionX += textDrawingArea.getTextWidth("From ");
					switch (byte1) {
					case 1:
						modIcons[0].drawSprite(positionX + 1, positionY - 12);
						positionX += 14;
						break;
					case 2:
						modIcons[1].drawSprite(positionX + 1, positionY - 12);
						positionX += 14;
						break;
					case 3:
						modIcons[2].drawSprite(positionX + 1, positionY - 11);
						positionX += 14;
						break;
					case 4:
						modIcons[3].drawSprite(positionX + 1, positionY - 12);
						positionX += 14;
						break;
					case 5:
						modIcons[4].drawSprite(positionX + 1, positionY - 12);
						positionX += 14;
						break;
					case 6:
						modIcons[5].drawSprite(positionX + 1, positionY - 12);
						positionX += 14;
						break;
					case 7:
						modIcons[6].drawSprite(positionX + 1, positionY - 11);
						positionX += 14;
						break;
					case 8:
						modIcons[7].drawSprite(positionX + 1, positionY - 11);
						positionX += 14;
						break;
					case 9:
						modIcons[8].drawSprite(positionX + 1, positionY - 11);
						positionX += 14;
						break;
					case 10:
						modIcons[9].drawSprite(positionX + 1, positionY - 11);
						positionX += 14;
						break;
					}
					textDrawingArea.method385(0, name + ": " + chatMessages[index], positionY, positionX);
					textDrawingArea.method385(65535, name + ": " + chatMessages[index], positionY - 1, positionX);
					if (++i >= 5)
						return;
				}
				if (type == 5 && privateChatMode < 2) {
					int i1 = (clientHeight - 174) - i * 13;
					textDrawingArea.method385(0, chatMessages[index], i1, 4);
					textDrawingArea.method385(65535, chatMessages[index], i1 - 1, 4);
					if (++i >= 5)
						return;
				}
				if (type == 6 && privateChatMode < 2) {
					int j1 = (clientHeight - 174) - i * 13;
					textDrawingArea.method385(0, "To " + name + ": " + chatMessages[index], j1, 4);
					textDrawingArea.method385(65535, "To " + name + ": " + chatMessages[index], j1 - 1, 4);
					if (++i >= 5)
						return;
				}
			}
	}

	public boolean filterMessages = false;
	public String[] filteredMessages = { "You catch", "You smelt", "You steal",
			"You successfully cook", "You accidentally burn",
			"You manage to", "You get some", "You swing" };// add more
	
	public void pushMessage(String s, int i, String s1) {
		if (i == 0 && dialogID != -1) {
			aString844 = s;
			super.clickMode3 = 0;
		}
		if (backDialogID == -1)
			inputTaken = true;
		for (int j = 499; j > 0; j--) {
			chatTypes[j] = chatTypes[j - 1];
			chatNames[j] = chatNames[j - 1];
			chatMessages[j] = chatMessages[j - 1];
			chatRights[j] = chatRights[j - 1];
			clanTitles[j] = clanTitles[j - 1];
		}
		chatTypes[0] = i;
		chatNames[0] = s1;
		chatMessages[0] = s;
		chatRights[0] = rights;
		clanTitles[0] = clanname;
	}

	public static void setTab(int id) {
		tabID = id;
		tabAreaAltered = true;
	}

	public void processMapAreaClick() {
		if (super.mouseX >= 742 && super.mouseX <= 764 && super.mouseY >= 1 && super.mouseY <= 23) {
			logIconHPos = 1;
		} else {
			logIconHPos = 0;
		}
	}

	private void resetImageProducers2() {
		if (aRSImageProducer_1166 != null)
			return;
		nullLoader();
		super.fullGameScreen = null;
		aRSImageProducer_1107 = null;
		aRSImageProducer_1108 = null;
		aRSImageProducer_1109 = null;
		aRSImageProducer_1110 = null;
		aRSImageProducer_1111 = null;
		aRSImageProducer_1112 = null;
		aRSImageProducer_1113 = null;
		aRSImageProducer_1114 = null;
		aRSImageProducer_1115 = null;
		aRSImageProducer_1166 = new GraphicalComponent(516, 165); // chat
																					// back
		aRSImageProducer_1164 = new GraphicalComponent(249, 168); // mapback
		DrawingArea.setAllPixelsToZero();
		imageLoader[1].drawSprite(0, 0);
		aRSImageProducer_1163 = new GraphicalComponent(250, 335); // inventory
		aRSImageProducer_1165 = new GraphicalComponent(clientSize == 0 ? 512 : clientWidth, clientSize == 0 ? 334 : clientHeight);
		DrawingArea.setAllPixelsToZero();
		aRSImageProducer_1125 = new GraphicalComponent(249, 45);
		welcomeScreenRaised = true;
	}

	public String getDocumentBaseHost() {
		if (Signlink.mainapp != null) {
			return Signlink.mainapp.getDocumentBase().getHost().toLowerCase();
		}
		if (super.mainFrame != null) {
			return "";
		} else {
			return "";
		}
	}

	private void method81(Sprite sprite, int j, int k) {
		int l = k * k + j * j;
		if (l > 4225 && l < 0x15f90) {
			int i1 = minimapInt1 + minimapInt2 & 0x7ff;
			int j1 = Model.SINE[i1];
			int k1 = Model.COSINE[i1];
			j1 = (j1 * 256) / (minimapInt3 + 256);
			k1 = (k1 * 256) / (minimapInt3 + 256);
		} else {
			markMinimap(sprite, k, j);
		}
	}

	private void rightClickChatButtons() {
		int y = clientHeight - 503;
		int[] x = { 5, 62, 119, 176, 233, 290, 347, 404 };
		if (super.mouseX >= 7 && super.mouseX <= 23 && super.mouseY >= y + 345 && super.mouseY <= y + 361) {
			if (quickChat) {
				menuActionName[1] = "Close";
				menuActionID[1] = 1004;
				menuActionRow = 2;
			}
		}
		if (super.mouseX >= x[0] && super.mouseX <= x[0] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "View All";
			menuActionID[1] = 999;
			menuActionRow = 2;
		} else if (super.mouseX >= x[1] && super.mouseX <= x[1] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "Filter Game";
			menuActionID[1] = 798;
			menuActionName[2] = "All Game";
			menuActionID[2] = 797;
			menuActionName[3] = "View Game";
			menuActionID[3] = 998;
			menuActionRow = 4;
		} else if (super.mouseX >= x[2] && super.mouseX <= x[2] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "Hide Public";
			menuActionID[1] = 997;
			menuActionName[2] = "Off Public";
			menuActionID[2] = 996;
			menuActionName[3] = "Friends Public";
			menuActionID[3] = 995;
			menuActionName[4] = "On Public";
			menuActionID[4] = 994;
			menuActionName[5] = "View Public";
			menuActionID[5] = 993;
			menuActionRow = 6;
		} else if (super.mouseX >= x[3] && super.mouseX <= x[3] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "Off Private";
			menuActionID[1] = 992;
			menuActionName[2] = "Friends Private";
			menuActionID[2] = 991;
			menuActionName[3] = "On Private";
			menuActionID[3] = 990;
			menuActionName[4] = "View Private";
			menuActionID[4] = 989;
			menuActionRow = 5;
		} else if (super.mouseX >= x[4] && super.mouseX <= x[4] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "Off Clan chat";
			menuActionID[1] = 1003;
			menuActionName[2] = "Friends Clan chat";
			menuActionID[2] = 1002;
			menuActionName[3] = "On Clan chat";
			menuActionID[3] = 1001;
			menuActionName[4] = "View Clan chat";
			menuActionID[4] = 1000;
			menuActionRow = 5;
		} else if (super.mouseX >= x[5] && super.mouseX <= x[5] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "Off Trade";
			menuActionID[1] = 987;
			menuActionName[2] = "Friends Trade";
			menuActionID[2] = 986;
			menuActionName[3] = "On Trade";
			menuActionID[3] = 985;
			menuActionName[4] = "View Trade";
			menuActionID[4] = 984;
			menuActionRow = 5;
		} else if (super.mouseX >= x[6] && super.mouseX <= x[6] + 56 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "Hide Yell";
			menuActionID[1] = 1012;
			menuActionName[2] = "Off Yell";
			menuActionID[2] = 1011;
			menuActionName[3] = "Friends Yell";
			menuActionID[3] = 1010;
			menuActionName[4] = "On Yell";
			menuActionID[4] = 1009;
			menuActionName[5] = "View Yell";
			menuActionID[5] = 1008;
			menuActionRow = 6;
		} else if (super.mouseX >= 404 && super.mouseX <= 515 && super.mouseY >= clientHeight - 23 && super.mouseY <= clientHeight) {
			menuActionName[1] = "Take Screenshot";
			menuActionID[1] = 1050;
			menuActionRow = 2;
		}
	}

	public void processRightClick() {
		if (activeInterfaceType != 0) {
			return;
		}
		menuActionName[0] = "Cancel";
		menuActionID[0] = 1107;
		menuActionRow = 1;
		if (clientSize >= 1) {
			if (fullscreenInterfaceID != -1) {
				anInt886 = 0;
				anInt1315 = 0;
				buildInterfaceMenu((clientWidth / 2) - 765 / 2, RSInterface.interfaceCache[fullscreenInterfaceID], super.mouseX, (clientHeight / 2) - 503 / 2, super.mouseY, 0);
				if (anInt886 != anInt1026) {
					anInt1026 = anInt886;
				}
				if (anInt1315 != anInt1129) {
					anInt1129 = anInt1315;
				}
				return;
			}
		}
		if (showChat)
			buildSplitPrivateChatMenu();
		anInt886 = 0;
		anInt1315 = 0;
		if (clientSize == 0) {
			if (super.mouseX > 0 && super.mouseY > 0 && super.mouseX < 516 && super.mouseY < 338) {
				if (openInterfaceID != -1) {
					buildInterfaceMenu(4, RSInterface.interfaceCache[openInterfaceID], super.mouseX, 4, super.mouseY, 0);
				} else {
					build3dScreenMenu();
				}
			}
		} else if (clientSize >= 1) {
			if (canClick())
				//drawInterface(0, (clientWidth / 2) - 356, RSInterface.interfaceCache[anInt1018], isFixed() ? 0 : (clientHeight / 2) - 230);
				if (super.mouseX > (clientWidth / 2) - 356 && super.mouseY > (clientHeight / 2) - 230 && super.mouseX < ((clientWidth / 2) + 256) && super.mouseY < (clientHeight / 2) + 230 && openInterfaceID != -1) {
					buildInterfaceMenu((clientWidth / 2) - 356, RSInterface.interfaceCache[openInterfaceID], super.mouseX, (clientHeight / 2) - 230, super.mouseY, 0);
				} else {
					build3dScreenMenu();
				}
		}
		if (anInt886 != anInt1026) {
			anInt1026 = anInt886;
		}
		if (anInt1315 != anInt1129) {
			anInt1129 = anInt1315;
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (clientSize == 0) {
			if (super.mouseX > 516 && super.mouseY > 205 && super.mouseX < 765 && super.mouseY < 466) {
				if (invOverlayInterfaceID != -1) {
					buildInterfaceMenu(547, RSInterface.interfaceCache[invOverlayInterfaceID], super.mouseX, 205, super.mouseY, 0);
				} else if (tabInterfaceIDs[tabID] != -1) {
					buildInterfaceMenu(547, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], super.mouseX, 205, super.mouseY, 0);
				}
			}
		} else {
			int y = clientWidth >= smallTabs ? 46 : 82;
			if (super.mouseX > clientWidth - 197 && super.mouseY > clientHeight - y - 256 && super.mouseX < clientWidth - 7 && super.mouseY < clientHeight - y + 10 && showTab) {
				if (invOverlayInterfaceID != -1) {
					buildInterfaceMenu(clientWidth - 197, RSInterface.interfaceCache[invOverlayInterfaceID], super.mouseX, clientHeight - y - 256, super.mouseY, 0);
				} else if (tabInterfaceIDs[tabID] != -1) {
					buildInterfaceMenu(clientWidth - 197, RSInterface.interfaceCache[tabInterfaceIDs[tabID]], super.mouseX, clientHeight - y - 256, super.mouseY, 0);
				}
			}
		}
		if (anInt886 != anInt1048) {
			needDrawTabArea = true;
			tabAreaAltered = true;
			anInt1048 = anInt886;
		}
		if (anInt1315 != anInt1044) {
			needDrawTabArea = true;
			tabAreaAltered = true;
			anInt1044 = anInt1315;
		}
		anInt886 = 0;
		anInt1315 = 0;
		if (super.mouseX > 0 && super.mouseY > (clientSize == 0 ? 338 : clientHeight - 165) && super.mouseX < 490 && super.mouseY < (clientSize == 0 ? 463 : clientHeight - 40) && showChat) {
			if (backDialogID != -1) {
				buildInterfaceMenu(20, RSInterface.interfaceCache[backDialogID], super.mouseX, (clientSize == 0 ? 358 : clientHeight - 145), super.mouseY, 0);
			} else if (super.mouseY < (clientSize == 0 ? 463 : clientHeight - 40) && super.mouseX < 490) {
				buildChatAreaMenu(super.mouseY - (clientSize == 0 ? 338 : clientHeight - 165));
			}
		}
		if (backDialogID != -1 && anInt886 != anInt1039) {
			inputTaken = true;
			anInt1039 = anInt886;
		}
		if (backDialogID != -1 && anInt1315 != anInt1500) {
			inputTaken = true;
			anInt1500 = anInt1315;
		}

		processTabAreaHovers();
		alertHandler.processMouse(super.mouseX, super.mouseY);
		if (super.mouseX > 0 && super.mouseY > clientHeight - 165 && super.mouseX < 519 && super.mouseY < clientHeight)
			rightClickChatButtons();
		else if (super.mouseX > clientWidth - 249 && super.mouseY < 168)
			processMinimapActions();
		boolean flag = false;
		while (!flag) {
			flag = true;
			for (int j = 0; j < menuActionRow - 1; j++) {
				if (menuActionID[j] < 1000 && menuActionID[j + 1] > 1000) {
					String s = menuActionName[j];
					menuActionName[j] = menuActionName[j + 1];
					menuActionName[j + 1] = s;
					int k = menuActionID[j];
					menuActionID[j] = menuActionID[j + 1];
					menuActionID[j + 1] = k;
					k = menuActionCmd2[j];
					menuActionCmd2[j] = menuActionCmd2[j + 1];
					menuActionCmd2[j + 1] = k;
					k = menuActionCmd3[j];
					menuActionCmd3[j] = menuActionCmd3[j + 1];
					menuActionCmd3[j + 1] = k;
					k = menuActionCmd1[j];
					menuActionCmd1[j] = menuActionCmd1[j + 1];
					menuActionCmd1[j + 1] = k;
					flag = false;
				}
			}
		}
	}

	private int method83(int i, int j, int k) {
		int l = 256 - k;
		return ((i & 0xff00ff) * l + (j & 0xff00ff) * k & 0xff00ff00) + ((i & 0xff00) * l + (j & 0xff00) * k & 0xff0000) >> 8;
	}

	public boolean canClick() {
		if (mouseInRegion(clientWidth - (clientWidth < smallTabs ? 240 : 480), clientHeight - (clientWidth < smallTabs ? 74 : 37), clientWidth, clientHeight)) {
			return false;
		}
		if (showChat) {
			if (super.mouseX > 0 && super.mouseX < 519 && super.mouseY > clientHeight - 165 && super.mouseY < clientHeight || super.mouseX > clientWidth - 220 && super.mouseX < clientWidth && super.mouseY > 0 && super.mouseY < 165) {
				return false;
			}
		}
		if (mouseInRegion2(clientWidth - 216, clientWidth, 0, 172)) {
			return false;
		}
		if (showTab) {
			if (clientWidth >= smallTabs) {
				if (super.mouseX >= clientWidth - 420 && super.mouseX <= clientWidth && super.mouseY >= clientHeight - 37 && super.mouseY <= clientHeight || super.mouseX > clientWidth - 204 && super.mouseX < clientWidth && super.mouseY > clientHeight - 37 - 274 && super.mouseY < clientHeight)
					return false;
			} else {
				if (super.mouseX >= clientWidth - 210 && super.mouseX <= clientWidth && super.mouseY >= clientHeight - 74 && super.mouseY <= clientHeight || super.mouseX > clientWidth - 204 && super.mouseX < clientWidth && super.mouseY > clientHeight - 74 - 274 && super.mouseY < clientHeight)
					return false;
			}
		}
		return true;
	}

	public boolean mouseInRegion2(int x1, int x2, int y1, int y2) {
		if (super.mouseX >= x1 && super.mouseX <= x2 && super.mouseY >= y1 && super.mouseY <= y2) {
			return true;
		}
		return false;
	}

	void login(String s, String s1, boolean flag) {
		Signlink.errorname = s;
		try {
			saveGameSettings();
			if (!flag) {
				loginMessage1 = "";
				loginMessage2 = "Logging into Mistex...";
				loginRenderer.displayLoginScreen();
			}
			s = s.trim();
			server = Config.LOCALHOST_IP;
			socketStream = new RSSocket(this, openSocket(Config.serverPort + portOff));
			long l = TextClass.longForName(s);
			int i = (int) (l >> 16 & 31L);
			buffer.currentOffset = 0;
			buffer.writeWordBigEndian(14);
			buffer.writeWordBigEndian(i);
			socketStream.queueBytes(2, buffer.buffer);
			for (int j = 0; j < 8; j++)
				socketStream.read();

			int k = socketStream.read();
			int i1 = k;
			if (k == 0) {
				socketStream.flushInputStream(inStream.buffer, 8);
				inStream.currentOffset = 0;
				aLong1215 = inStream.readQWord();
				int ai[] = new int[4];
				ai[0] = (int) (Math.random() * 99999999D);
				ai[1] = (int) (Math.random() * 99999999D);
				ai[2] = (int) (aLong1215 >> 32);
				ai[3] = (int) aLong1215;
				buffer.currentOffset = 0;
				buffer.writeWordBigEndian(10);
				buffer.writeDWord(ai[0]);
				buffer.writeDWord(ai[1]);
				buffer.writeDWord(ai[2]);
				buffer.writeDWord(ai[3]);
				buffer.writeDWord(999999);
				buffer.writeString(s);
				buffer.writeString(s1);
				buffer.doKeys();
				aStream_847.currentOffset = 0;
				if (flag)
					aStream_847.writeWordBigEndian(18);
				else
					aStream_847.writeWordBigEndian(16);
				aStream_847.writeWordBigEndian(buffer.currentOffset + 36 + 1 + 1 + 2);
				aStream_847.writeWordBigEndian(255);
				aStream_847.writeWord(317);
				aStream_847.writeWordBigEndian(lowMem ? 1 : 0);
				for (int l1 = 0; l1 < 9; l1++)
					aStream_847.writeDWord(expectedCRCs[l1]);

				aStream_847.writeBytes(buffer.buffer, buffer.currentOffset, 0);
				buffer.encryption = new ISAACRandomGen(ai);
				for (int j2 = 0; j2 < 4; j2++)
					ai[j2] += 50;

				encryption = new ISAACRandomGen(ai);
				socketStream.queueBytes(aStream_847.currentOffset, aStream_847.buffer);
				k = socketStream.read();
			}
			if (k == 1) {
				try {
					Thread.sleep(2000L);
				} catch (Exception _ex) {
				}
				login(s, s1, flag);
				return;
			}
			if (k == 2) {
				loadGoals(myUsername);
				myPrivilege = socketStream.read();
				flagged = socketStream.read() == 1;
				aLong1220 = 0L;
				anInt1022 = 0;
				mouseDetection.coordsIndex = 0;
				super.awtFocus = true;
				aBoolean954 = true;
				loggedIn = true;
				buffer.currentOffset = 0;
				inStream.currentOffset = 0;
				pktType = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				pktSize = 0;
				anInt1009 = 0;
				systemUpdateTimer = 0;
				anInt1011 = 0;
				anInt855 = 0;
				menuActionRow = 0;
				menuOpen = false;
				super.idleTime = 0;
				for (int j1 = 0; j1 < 500; j1++)
					chatMessages[j1] = null;

				itemSelected = 0;
				spellSelected = 0;
				loadingStage = 0;
				anInt1062 = 0;
				anInt1278 = (int) (Math.random() * 100D) - 50;
				anInt1131 = (int) (Math.random() * 110D) - 55;
				anInt896 = (int) (Math.random() * 80D) - 40;
				minimapInt2 = (int) (Math.random() * 120D) - 60;
				minimapInt3 = (int) (Math.random() * 30D) - 20;
				minimapInt1 = (int) (Math.random() * 20D) - 10 & 0x7ff;
				mapState = 0;
				setNorth();
				anInt985 = -1;
				destX = 0;
				destY = 0;
				playerCount = 0;
				npcCount = 0;
				for (int i2 = 0; i2 < maxPlayers; i2++) {
					playerArray[i2] = null;
					aStreamArray895s[i2] = null;
				}

				for (int k2 = 0; k2 < 16384; k2++)
					npcArray[k2] = null;

				myPlayer = playerArray[myPlayerIndex] = new Player();
				aClass19_1013.removeAll();
				aClass19_1056.removeAll();
				for (int l2 = 0; l2 < 4; l2++) {
					for (int i3 = 0; i3 < 104; i3++) {
						for (int k3 = 0; k3 < 104; k3++)
							groundArray[l2][i3][k3] = null;

					}

				}

				aClass19_1179 = new NodeList();
				fullscreenInterfaceID = -1;
				anInt900 = 0;
				friendsCount = 0;
				dialogID = -1;
				backDialogID = -1;
				openInterfaceID = -1;
				invOverlayInterfaceID = -1;
				anInt1018 = -1;
				aBoolean1149 = false;
				tabID = 3;
				inputDialogState = 0;
				menuOpen = false;
				messagePromptRaised = false;
				aString844 = null;
				anInt1055 = 0;
				anInt1054 = -1;
				aBoolean1047 = true;
				method45();
				for (int j3 = 0; j3 < 5; j3++)
					anIntArray990[j3] = 0;

				for (int l3 = 0; l3 < 5; l3++) {
					atPlayerActions[l3] = null;
					atPlayerArray[l3] = false;
				}

				anInt1175 = 0;
				anInt1134 = 0;
				anInt986 = 0;
				anInt1288 = 0;
				anInt924 = 0;
				anInt1188 = 0;
				anInt1155 = 0;
				anInt1226 = 0;
				updateGameArea();
				resetImageProducers2();
				return;
			}
			if (k == 3) {
				loginMessage1 = "";
				loginMessage2 = "Invalid username or password.";
				System.out.println("Invalid username or password!");
				return;
			}
			if (k == 4) {
				loginMessage2 = "Your account has been disabled.";
				// loginMessage2 =
				// "Please check your message-center for details.";
				System.out.println("Your account has been disabled!");
				return;
			}
			if (k == 5) {
				loginMessage2 = "Your account is already logged in.";
				// loginMessage2 = "Try again in 60 secs...";
				System.out.println("Your account is already logged in!");
				return;
			}
			if (k == 6) {
				loginMessage2 = Config.clientName + " has been updated!";
				// loginMessage2 = "Please reload this page.";
				System.out.println("Mistex has been updated! Please reload client.");
				return;
			}
			if (k == 7) {
				loginMessage1 = "This world is full.";
				loginMessage2 = "Please use a different world.";
				System.out.println("This world is full!");
				return;
			}
			if (k == 8) {
				loginMessage1 = "Unable to connect.";
				loginMessage2 = "Login server offline.";
				System.out.println("Mistex is currently offline!");
				return;
			}
			if (k == 9) {
				loginMessage1 = "Login limit exceeded.";
				loginMessage2 = "Too many connections from your address.";
				System.out.println("Too many connections form your address.");
				return;
			}
			if (k == 10) {
				loginMessage1 = "Unable to connect.";
				loginMessage2 = "Bad session id.";
				System.out.println("Unable to connect, bad session id!");
				return;
			}
			if (k == 11) {
				loginMessage2 = "Login server rejected session.";
				loginMessage2 = "Please try again.";
				System.out.println("Login server rejected session.");
				return;
			}
			if (k == 12) {
				loginMessage1 = "You need a members account to login to this world.";
				loginMessage2 = "Please subscribe, or use a different world.";
				return;
			}
			if (k == 13) {
				loginMessage1 = "Could not complete login.";
				loginMessage2 = "Please try using a different world.";
				return;
			}
			if (k == 14) {
				loginMessage1 = "The server is being updated.";
				loginMessage2 = "Please wait 1 minute and try again.";
				System.out.println("The server is being updated, please try again later.");
				return;
			}
			if (k == 15) {
				loggedIn = true;
				buffer.currentOffset = 0;
				inStream.currentOffset = 0;
				pktType = -1;
				anInt841 = -1;
				anInt842 = -1;
				anInt843 = -1;
				pktSize = 0;
				anInt1009 = 0;
				systemUpdateTimer = 0;
				menuActionRow = 0;
				menuOpen = false;
				aLong824 = System.currentTimeMillis();
				return;
			}
			if (k == 16) {
				loginMessage1 = "Login attempts exceeded.";
				loginMessage2 = "Please wait 1 minute and try again.";
				System.out.println("Login attempts exceeded, please wait and try again.");
				return;
			}
			if (k == 17) {//here
				loginMessage1 = "You are standing in a members-only area.";
				loginMessage2 = "To play on this world move to a free area first";
				return;
			}
			if (k == 20) {
				loginMessage1 = "Invalid loginserver requested";
				loginMessage2 = "Please try using a different world.";
				System.out.println("Invalid loginserver requested.");
				return;
			}
			if (k == 21) {
				for (int k1 = socketStream.read(); k1 >= 0; k1--) {
					loginMessage1 = "You have only just left another world";
					loginMessage2 = "Your profile will be transferred in: " + k1 + " seconds";
					loginRenderer.displayLoginScreen();
					try {
						Thread.sleep(1000L);
					} catch (Exception _ex) {
					}
				}

				login(s, s1, flag);
				return;
			}
			
			if (k == -1) {
				if (i1 == 0) {
					if (loginFailures < 2) {
						try {
							Thread.sleep(2000L);
						} catch (Exception _ex) {
						}
						loginFailures++;
						login(s, s1, flag);
						return;
					} else {
						loginMessage1 = "No response from loginserver";
						loginMessage2 = "Please wait 1 minute and try again.";
						System.out.println("No response from loginserver, please wait and try again!");
						return;
					}
				} else {
					loginMessage1 = "No response from server";
					loginMessage2 = "Please try using a different world.";
					System.out.println("No response from server, use a different world.");
					return;
				}
			} else {
				System.out.println("response:" + k);
				loginMessage1 = "Unexpected server response";
				loginMessage2 = "Please try using a different world.";
				System.out.println("Unexpected server response.");
				return;
			}
		} catch (IOException _ex) {
			loginMessage1 = "";
			_ex.printStackTrace();
	//	} catch (Exception e) {
			//System.out.println("Error while generating uid. Skipping step.");
			//e.printStackTrace();
		}
		loginMessage2 = "Please try again in 30 seconds.";
		System.out.println("Please try again in 30 seconds.");
	}

	private boolean doWalkTo(int i, int j, int k, int i1, int j1, int k1, int l1, int i2, int j2, boolean flag, int k2) {
		byte byte0 = 104;
		byte byte1 = 104;
		for (int l2 = 0; l2 < byte0; l2++) {
			for (int i3 = 0; i3 < byte1; i3++) {
				anIntArrayArray901[l2][i3] = 0;
				anIntArrayArray825[l2][i3] = 0x5f5e0ff;
			}
		}
		int j3 = j2;
		int k3 = j1;
		anIntArrayArray901[j2][j1] = 99;
		anIntArrayArray825[j2][j1] = 0;
		int l3 = 0;
		int i4 = 0;
		bigX[l3] = j2;
		bigY[l3++] = j1;
		boolean flag1 = false;
		int j4 = bigX.length;
		int ai[][] = aClass11Array1230[plane].anIntArrayArray294;
		while (i4 != l3) {
			j3 = bigX[i4];
			k3 = bigY[i4];
			i4 = (i4 + 1) % j4;
			if (j3 == k2 && k3 == i2) {
				flag1 = true;
				break;
			}
			if (i1 != 0) {
				if ((i1 < 5 || i1 == 10) && aClass11Array1230[plane].method219(k2, j3, k3, j, i1 - 1, i2)) {
					flag1 = true;
					break;
				}
				if (i1 < 10 && aClass11Array1230[plane].method220(k2, i2, k3, i1 - 1, j, j3)) {
					flag1 = true;
					break;
				}
			}
			if (k1 != 0 && k != 0 && aClass11Array1230[plane].method221(i2, k2, j3, k, l1, k1, k3)) {
				flag1 = true;
				break;
			}
			int l4 = anIntArrayArray825[j3][k3] + 1;
			if (j3 > 0 && anIntArrayArray901[j3 - 1][k3] == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3] = 2;
				anIntArrayArray825[j3 - 1][k3] = l4;
			}
			if (j3 < byte0 - 1 && anIntArrayArray901[j3 + 1][k3] == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3] = 8;
				anIntArrayArray825[j3 + 1][k3] = l4;
			}
			if (k3 > 0 && anIntArrayArray901[j3][k3 - 1] == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 - 1] = 1;
				anIntArrayArray825[j3][k3 - 1] = l4;
			}
			if (k3 < byte1 - 1 && anIntArrayArray901[j3][k3 + 1] == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3][k3 + 1] = 4;
				anIntArrayArray825[j3][k3 + 1] = l4;
			}
			if (j3 > 0 && k3 > 0 && anIntArrayArray901[j3 - 1][k3 - 1] == 0 && (ai[j3 - 1][k3 - 1] & 0x128010e) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 - 1] = 3;
				anIntArrayArray825[j3 - 1][k3 - 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 > 0 && anIntArrayArray901[j3 + 1][k3 - 1] == 0 && (ai[j3 + 1][k3 - 1] & 0x1280183) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0 && (ai[j3][k3 - 1] & 0x1280102) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 - 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 - 1] = 9;
				anIntArrayArray825[j3 + 1][k3 - 1] = l4;
			}
			if (j3 > 0 && k3 < byte1 - 1 && anIntArrayArray901[j3 - 1][k3 + 1] == 0 && (ai[j3 - 1][k3 + 1] & 0x1280138) == 0 && (ai[j3 - 1][k3] & 0x1280108) == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 - 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 - 1][k3 + 1] = 6;
				anIntArrayArray825[j3 - 1][k3 + 1] = l4;
			}
			if (j3 < byte0 - 1 && k3 < byte1 - 1 && anIntArrayArray901[j3 + 1][k3 + 1] == 0 && (ai[j3 + 1][k3 + 1] & 0x12801e0) == 0 && (ai[j3 + 1][k3] & 0x1280180) == 0 && (ai[j3][k3 + 1] & 0x1280120) == 0) {
				bigX[l3] = j3 + 1;
				bigY[l3] = k3 + 1;
				l3 = (l3 + 1) % j4;
				anIntArrayArray901[j3 + 1][k3 + 1] = 12;
				anIntArrayArray825[j3 + 1][k3 + 1] = l4;
			}
		}
		anInt1264 = 0;
		if (!flag1) {
			if (flag) {
				int i5 = 100;
				for (int k5 = 1; k5 < 2; k5++) {
					for (int i6 = k2 - k5; i6 <= k2 + k5; i6++) {
						for (int l6 = i2 - k5; l6 <= i2 + k5; l6++)
							if (i6 >= 0 && l6 >= 0 && i6 < 104 && l6 < 104 && anIntArrayArray825[i6][l6] < i5) {
								i5 = anIntArrayArray825[i6][l6];
								j3 = i6;
								k3 = l6;
								anInt1264 = 1;
								flag1 = true;
							}

					}

					if (flag1)
						break;
				}

			}
			if (!flag1)
				return false;
		}
		i4 = 0;
		bigX[i4] = j3;
		bigY[i4++] = k3;
		int l5;
		for (int j5 = l5 = anIntArrayArray901[j3][k3]; j3 != j2 || k3 != j1; j5 = anIntArrayArray901[j3][k3]) {
			if (j5 != l5) {
				l5 = j5;
				bigX[i4] = j3;
				bigY[i4++] = k3;
			}
			if ((j5 & 2) != 0)
				j3++;
			else if ((j5 & 8) != 0)
				j3--;
			if ((j5 & 1) != 0)
				k3++;
			else if ((j5 & 4) != 0)
				k3--;
		}
		if (i4 > 0) {
			int k4 = i4;
			if (k4 > 25)
				k4 = 25;
			i4--;
			int k6 = bigX[i4];
			int i7 = bigY[i4];
			anInt1288 += k4;
			if (anInt1288 >= 92) {
				buffer.createFrame(36);
				buffer.writeDWord(0);
				anInt1288 = 0;
			}
			if (i == 0) {
				buffer.createFrame(164);
				buffer.writeWordBigEndian(k4 + k4 + 3);
			}
			if (i == 1) {
				buffer.createFrame(248);
				buffer.writeWordBigEndian(k4 + k4 + 3 + 14);
			}
			if (i == 2) {
				buffer.createFrame(98);
				buffer.writeWordBigEndian(k4 + k4 + 3);
			}
			buffer.method433(k6 + baseX);
			destX = bigX[0];
			destY = bigY[0];
			for (int j7 = 1; j7 < k4; j7++) {
				i4--;
				buffer.writeWordBigEndian(bigX[i4] - k6);
				buffer.writeWordBigEndian(bigY[i4] - i7);
			}

			buffer.method431(i7 + baseY);
			buffer.method424(super.keyArray[5] != 1 ? 0 : 1);
			return true;
		}
		return i != 1;
	}

	private void method86(Buffer buffer) {
		for (int j = 0; j < anInt893; j++) {
			int k = anIntArray894[j];
			Npc npc = npcArray[k];
			int l = buffer.readUnsignedByte();
			if ((l & 0x10) != 0) {
				int i1 = buffer.method434();
				if (i1 == 65535)
					i1 = -1;
				int i2 = buffer.readUnsignedByte();
				if (i1 == npc.anim && i1 != -1) {
					int l2 = Animation.anims[i1].anInt365;
					if (l2 == 1) {
						npc.anInt1527 = 0;
						npc.anInt1528 = 0;
						npc.anInt1529 = i2;
						npc.anInt1530 = 0;
					}
					if (l2 == 2)
						npc.anInt1530 = 0;
				} else if (i1 == -1 || npc.anim == -1 || Animation.anims[i1].anInt359 >= Animation.anims[npc.anim].anInt359) {
					npc.anim = i1;
					npc.anInt1527 = 0;
					npc.anInt1528 = 0;
					npc.anInt1529 = i2;
					npc.anInt1530 = 0;
					npc.anInt1542 = npc.smallXYIndex;
				}
			}
			if ((l & 8) != 0) {
				if (!newHitmarkers) {
					int j1 = buffer.method426();
					int j2 = buffer.method427();
					npc.updateHitData(j2, j1, loopCycle);
					npc.loopCycleStatus = loopCycle + 300;
					npc.currentHealth = buffer.method426();
					npc.maxHealth = buffer.readUnsignedByte();
				} else {
					int j1 = buffer.method426();
					int j2 = buffer.method427();
					int icon = buffer.readUnsignedByte();
					npc.updateHitData(j2, j1, loopCycle, icon);
					npc.loopCycleStatus = loopCycle + 300;
					npc.currentHealth = buffer.method426();
					npc.maxHealth = buffer.readUnsignedByte();
				}
			}

			if ((l & 0x80) != 0) {
				npc.anInt1520 = buffer.readUnsignedWord();
				int k1 = buffer.readDWord();
				npc.anInt1524 = k1 >> 16;
				npc.anInt1523 = loopCycle + (k1 & 0xffff);
				npc.anInt1521 = 0;
				npc.anInt1522 = 0;
				if (npc.anInt1523 > loopCycle)
					npc.anInt1521 = -1;
				if (npc.anInt1520 == 65535)
					npc.anInt1520 = -1;
			}
			if ((l & 0x20) != 0) {
				npc.interactingEntity = buffer.readUnsignedWord();
				if (npc.interactingEntity == 65535)
					npc.interactingEntity = -1;
			}
			if ((l & 1) != 0) {
				npc.textSpoken = buffer.readString();
				npc.textCycle = 100;

			}
			if ((l & 0x40) != 0) {
				if (newHitmarkers) {
					int l1 = buffer.method427();
					int k2 = buffer.method428();
					int icon = buffer.readUnsignedByte();
					npc.updateHitData(k2, l1, loopCycle, icon);
					npc.loopCycleStatus = loopCycle + 300;
					npc.currentHealth = buffer.method428();
					npc.maxHealth = buffer.method427();
				} else {
					int l1 = buffer.method427();
					int k2 = buffer.method428();
					npc.updateHitData(k2, l1, loopCycle);
					npc.loopCycleStatus = loopCycle + 300;
					npc.currentHealth = buffer.method428();
					npc.maxHealth = buffer.method427();
				}
			}
			if ((l & 2) != 0) {
				npc.desc = EntityDefinition.forID(buffer.method436());
				npc.anInt1540 = npc.desc.aByte68;
				npc.anInt1504 = npc.desc.anInt79;
				npc.anInt1554 = npc.desc.walkAnim;
				npc.anInt1555 = npc.desc.anInt58;
				npc.anInt1556 = npc.desc.anInt83;
				npc.anInt1557 = npc.desc.anInt55;
				npc.anInt1511 = npc.desc.standAnim;
			}
			if ((l & 4) != 0) {
				npc.anInt1538 = buffer.method434();
				npc.anInt1539 = buffer.method434();
			}
		}
	}

	private void buildAtNPCMenu(EntityDefinition entityDef, int i, int j, int k) {
		if (menuActionRow >= 400)
			return;
		if (entityDef.childrenIDs != null)
			entityDef = entityDef.method161();
		if (entityDef == null)
			return;
		if (!entityDef.aBoolean84)
			return;
		String s = entityDef.name;
		if (entityDef.combatLevel != 0)
			s = s + combatDiffColor(myPlayer.combatLevel, entityDef.combatLevel) + " (level-" + entityDef.combatLevel + ")";
		if (itemSelected == 1) {
			menuActionName[menuActionRow] = "Use " + selectedItemName + " with @yel@" + s;
			menuActionID[menuActionRow] = 582;
			menuActionCmd1[menuActionRow] = i;
			menuActionCmd2[menuActionRow] = k;
			menuActionCmd3[menuActionRow] = j;
			menuActionRow++;
			return;
		}
		if (spellSelected == 1) {
			if ((spellUsableOn & 2) == 2) {
				menuActionName[menuActionRow] = spellTooltip + " @yel@" + s;
				menuActionID[menuActionRow] = 413;
				menuActionCmd1[menuActionRow] = i;
				menuActionCmd2[menuActionRow] = k;
				menuActionCmd3[menuActionRow] = j;
				menuActionRow++;
			}
		} else {
			if (entityDef.actions != null) {
				for (int l = 4; l >= 0; l--)
					if (entityDef.actions[l] != null && !entityDef.actions[l].equalsIgnoreCase("attack")) {
						menuActionName[menuActionRow] = entityDef.actions[l] + " @yel@" + s;
						if (l == 0)
							menuActionID[menuActionRow] = 20;
						if (l == 1)
							menuActionID[menuActionRow] = 412;
						if (l == 2)
							menuActionID[menuActionRow] = 225;
						if (l == 3)
							menuActionID[menuActionRow] = 965;
						if (l == 4)
							menuActionID[menuActionRow] = 478;
						menuActionCmd1[menuActionRow] = i;
						menuActionCmd2[menuActionRow] = k;
						menuActionCmd3[menuActionRow] = j;
						menuActionRow++;
					}

			}
			if (entityDef.actions != null) {
				for (int i1 = 4; i1 >= 0; i1--)
					if (entityDef.actions[i1] != null && entityDef.actions[i1].equalsIgnoreCase("attack")) {
						char c = '\0';
						if (entityDef.combatLevel > myPlayer.combatLevel)
							c = '\u07D0';
						menuActionName[menuActionRow] = entityDef.actions[i1] + " @yel@" + s;
						if (i1 == 0)
							menuActionID[menuActionRow] = 20 + c;
						if (i1 == 1)
							menuActionID[menuActionRow] = 412 + c;
						if (i1 == 2)
							menuActionID[menuActionRow] = 225 + c;
						if (i1 == 3)
							menuActionID[menuActionRow] = 965 + c;
						if (i1 == 4)
							menuActionID[menuActionRow] = 478 + c;
						menuActionCmd1[menuActionRow] = i;
						menuActionCmd2[menuActionRow] = k;
						menuActionCmd3[menuActionRow] = j;
						menuActionRow++;
					}

			}
			if (Config.debugMode) {
				menuActionName[menuActionRow] = "Examine @cya@" + entityDef.name + "Models: "+Arrays.toString(entityDef.anIntArray94);
				//menuActionName[menuActionRow] = "Examine @yel@" + s + " @gre@(@whi@" + entityDef.type + "@gre@)";
			} else {
				menuActionName[menuActionRow] = "Examine @yel@" + s;
			}
			menuActionID[menuActionRow] = 1025;
			menuActionCmd1[menuActionRow] = i;
			menuActionCmd2[menuActionRow] = k;
			menuActionCmd3[menuActionRow] = j;
			menuActionRow++;
		}
	}

	private void buildAtPlayerMenu(int i, int j, Player player, int k) {
		if (player == myPlayer)
			return;
		if (menuActionRow >= 400)
			return;
		String s;
		if (player.skill == 0)
			s = player.name + combatDiffColor(myPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
		else
			s = "@or2@" + loyaltyRank(player.skill) + "@whi@" + player.name + combatDiffColor(myPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
		if (itemSelected == 1) {
			menuActionName[menuActionRow] = "Use " + selectedItemName + " with @whi@" + s;
			menuActionID[menuActionRow] = 491;
			menuActionCmd1[menuActionRow] = j;
			menuActionCmd2[menuActionRow] = i;
			menuActionCmd3[menuActionRow] = k;
			menuActionRow++;
		} else if (spellSelected == 1) {
			if ((spellUsableOn & 8) == 8) {
				menuActionName[menuActionRow] = spellTooltip + " @whi@" + s;
				menuActionID[menuActionRow] = 365;
				menuActionCmd1[menuActionRow] = j;
				menuActionCmd2[menuActionRow] = i;
				menuActionCmd3[menuActionRow] = k;
				menuActionRow++;
			}
		} else {
			for (int l = 4; l >= 0; l--)
				if (atPlayerActions[l] != null) {
					menuActionName[menuActionRow] = atPlayerActions[l] + " @whi@" + s;
					char c = '\0';
					if (atPlayerActions[l].equalsIgnoreCase("attack")) {
						if (player.combatLevel > myPlayer.combatLevel)
							c = '\u07D0';
						if (myPlayer.team != 0 && player.team != 0)
							if (myPlayer.team == player.team)
								c = '\u07D0';
							else
								c = '\0';
					} else if (atPlayerArray[l])
						c = '\u07D0';
					if (l == 0)
						menuActionID[menuActionRow] = 561 + c;
					if (l == 1)
						menuActionID[menuActionRow] = 779 + c;
					if (l == 2)
						menuActionID[menuActionRow] = 27 + c;
					if (l == 3)
						menuActionID[menuActionRow] = 577 + c;
					if (l == 4)
						menuActionID[menuActionRow] = 729 + c;
					menuActionCmd1[menuActionRow] = j;
					menuActionCmd2[menuActionRow] = i;
					menuActionCmd3[menuActionRow] = k;
					menuActionRow++;
				}

		}
		for (int i1 = 0; i1 < menuActionRow; i1++)
			if (menuActionID[i1] == 519) {
				menuActionName[i1] = "Walk here @whi@" + s;
				return;
			}

	}

	private void method89(Class30_Sub1 class30_sub1) {
		int i = 0;
		int j = -1;
		int k = 0;
		int l = 0;
		if (class30_sub1.anInt1296 == 0)
			i = worldController.method300(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (class30_sub1.anInt1296 == 1)
			i = worldController.method301(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (class30_sub1.anInt1296 == 2)
			i = worldController.method302(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (class30_sub1.anInt1296 == 3)
			i = worldController.method303(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298);
		if (i != 0) {
			int i1 = worldController.method304(class30_sub1.anInt1295, class30_sub1.anInt1297, class30_sub1.anInt1298, i);
			j = i >> 14 & 0x7fff;
			k = i1 & 0x1f;
			l = i1 >> 6;
		}
		class30_sub1.anInt1299 = j;
		class30_sub1.anInt1301 = k;
		class30_sub1.anInt1300 = l;
	}
	
	void mouseWheelDragged(int i, int j) {
		if (!mouseWheelDown)
			return;
		this.anInt1186 += i * 3;
		this.anInt1187 += (j << 1);
	}
	
	public void drawSmoothLoading(int i, String s)
    {
        for(float f = LP; f < (float)i; f = (float)((double)f + 0.29999999999999999D))
            drawLoadingText((int)f, s);

        LP = i;
    }

	void startUp() {
		drawSmoothLoadingText(20, "Starting up");
		loginRenderer = new LoginRenderer(this);
		Config.determineClient();
		for (int index = 0; index < 25; index++) {
			bubbles.add(new Bubble());
		}


		if (Signlink.sunjava)
			super.minDelay = 5;
		if (Signlink.cache_dat != null) {
			for (int i = 0; i < 5; i++)
				decompressors[i] = new Decompressor(Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
		}
		try {
			titleStreamLoader = streamLoaderForName(1, "title screen", "title", expectedCRCs[1], 25);
			smallText = new TextDrawingArea(false, "p11_full", titleStreamLoader);
			smallHit = new TextDrawingArea(false, "hit_full", titleStreamLoader);
			bigHit = new TextDrawingArea(true, "critical_full", titleStreamLoader);
			aTextDrawingArea_1271 = new TextDrawingArea(false, "p12_full", titleStreamLoader);
			fancyFont = new TextDrawingArea(false, "q8_full", titleStreamLoader);
			chatTextDrawingArea = new TextDrawingArea(false, "b12_full", titleStreamLoader);
			newSmallFont = new RSFont(false, "p11_full", titleStreamLoader);
			newRegularFont = new RSFont(false, "p12_full", titleStreamLoader);
			newNormalFont = new RSFont(false, "p12_full", titleStreamLoader);
			newBoldFont = new RSFont(false, "b12_full", titleStreamLoader);
			newFancyFont = new RSFont(false, "q8_full", titleStreamLoader);
			boldFont = new TextDrawingArea(false, "b12_full", titleStreamLoader);
			normalFont = new TextDrawingArea(false, "p12_full", titleStreamLoader);
			aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full", titleStreamLoader);
			drawLogo();
			loadTitleScreen();
			Archive jagexArchive = streamLoaderForName(2, "config", "config", expectedCRCs[2], 30);
			Archive streamLoader_1 = streamLoaderForName(3, "interface", "interface", expectedCRCs[3], 35);
			Archive streamLoader_2 = streamLoaderForName(4, "2d graphics", "media", expectedCRCs[4], 40);
			Archive streamLoader_3 = streamLoaderForName(6, "textures", "textures", expectedCRCs[6], 45);
			Archive streamLoader_4 = streamLoaderForName(7, "chat system", "wordenc", expectedCRCs[7], 50);
			streamLoaderForName(8, "sound effects", "sounds", expectedCRCs[8], 55);
			byteGroundArray = new byte[4][104][104];
			intGroundArray = new int[4][105][105];
			worldController = new Scene(intGroundArray);
			for (int j = 0; j < 4; j++)
				aClass11Array1230[j] = new CollisionMap();

			aClass30_Sub2_Sub1_Sub1_1263 = new Sprite(512, 512);
			Archive streamLoader_6 = streamLoaderForName(5, "update list", "versionlist", expectedCRCs[5], 60);
            drawSmoothLoadingText(60, "Connecting to update server");
			onDemandFetcher = new OnDemandFetcher();
			onDemandFetcher.start(streamLoader_6, this);
			Class36.method528(onDemandFetcher.getAnimCount());
			Model.method459(onDemandFetcher.getModelCount(), onDemandFetcher);
            drawSmoothLoadingText(80, "Unpacking media");
			ImageLoader.loadImageData(jagexArchive);
			imageLoader = ImageLoader.image;
			SkillOrbHandler.loadImages();
			HPBarFull = new Sprite(Signlink.findcachedir() + "Interfaces/Player/HP0.png", 1);
			HPBarEmpty = new Sprite(Signlink.findcachedir() + "Interfaces/Player/HP1.png", 1);
			multiOverlay = new Sprite(streamLoader_2, "overlay_multiway", 0);
			mapBack = new Background(streamLoader_2, "mapback", 0);
			try {
				SpriteLoader.loadSprites(streamLoader_2);
				cacheSprite = SpriteLoader.sprites;
			} catch (Exception e) {
				System.out.println("Unable to load sprite cache.");
			}
			alertBack = new Sprite("Alert/alertback");
			alertBorder = new Sprite("Alert/alertborder");
			alertBorderH = new Sprite("Alert/alertborderh");
			for (int index = 0; index < clanIcons.length; index++) {
				clanIcons[index] = new Sprite("/Interfaces/Clan Chat/Icons/" + index);
			}
			for (int c1 = 0; c1 < 1; c1++)
				newModIcons[c1] = new Sprite("Player/CROWN_" + c1);
			for (int c1 = 0; c1 <= 3; c1++)
				chatButtons[c1] = new Sprite(streamLoader_2, "chatbuttons", c1);
			for (int j3 = 0; j3 <= 14; j3++)
				sideIcons[j3] = new Sprite(streamLoader_2, "sideicons", j3);
			for (int index = 0; index < 12; index++) {
				scrollPart[index] = new Sprite("Scrollbar/b " + index);
			}
			for (int index = 0; index < 6; index++) {
				scrollBar[index] = new Sprite("Scrollbar/s " + index);
			}
			compass = new Sprite(streamLoader_2, "compass", 0);
			for (int s562 = 0; s562 < 4; s562++)
				hitMarks562[s562] = new Sprite("Hitmarks/SPLAT_" + s562);
			try {
				for (int k3 = 0; k3 < 100; k3++)
					mapScenes[k3] = new Background(streamLoader_2, "mapscene", k3);
			} catch (Exception _ex) {
			}
			try {
				for (int l3 = 0; l3 < 100; l3++)
					mapFunctions[l3] = new Sprite(streamLoader_2, "mapfunction", l3);
			} catch (Exception _ex) {
			}
			try {
				for (int i4 = 0; i4 < 20; i4++)
					hitMarks[i4] = new Sprite(streamLoader_2, "hitmarks", i4);
			} catch (Exception _ex) {
			}
			try {
				for (int h1 = 0; h1 < 6; h1++)
					headIconsHint[h1] = new Sprite(streamLoader_2, "headicons_hint", h1);
			} catch (Exception _ex) {
			}
			try {
				for (int j4 = 0; j4 < 8; j4++)
					headIcons[j4] = new Sprite(streamLoader_2, "headicons_prayer", j4);
				for (int idx = 0; idx < 18; idx++)
					headIcons[idx] = new Sprite("Player/Prayer/Prayer " + idx);
				for (int j45 = 0; j45 < 3; j45++)
					skullIcons[j45] = new Sprite(streamLoader_2, "headicons_pk", j45);
			} catch (Exception _ex) {
			}
			try {
				for (int i = 0; i < currencyImageAmount; i++) {
					currencyImage[i] = new Sprite("/Shop/currency " + i);
				}

			} catch (Exception _ex) {
			}
			mapFlag = new Sprite(streamLoader_2, "mapmarker", 0);
			mapMarker = new Sprite(streamLoader_2, "mapmarker", 1);
			duelArena = new Sprite(streamLoader_2, "duel_arena", 0);
			scrollBar1 = new Sprite(streamLoader_2, "scrollbar", 0);
			scrollBar2 = new Sprite(streamLoader_2, "scrollbar", 1);
			notInWild = new Sprite("/PvP/NOTINWILD1 0");
			inWild = new Sprite("/PvP/INWILD1 0");
			inTimer = new Sprite("/PvP/INCOUNT1 0");
			for (int k4 = 0; k4 < 8; k4++)
				crosses[k4] = new Sprite(streamLoader_2, "cross", k4);
			mapDotItem = new Sprite(streamLoader_2, "mapdots", 0);
			mapDotNPC = new Sprite(streamLoader_2, "mapdots", 1);
			mapDotPlayer = new Sprite(streamLoader_2, "mapdots", 2);
			mapDotFriend = new Sprite(streamLoader_2, "mapdots", 3);
			mapDotTeam = new Sprite(streamLoader_2, "mapdots", 4);
			mapDotClan = new Sprite(streamLoader_2, "mapdots", 5);
			for (int i4 = 0; i4 < 20; i4++)
				hitMark[i4] = new Sprite("/Hits/hit " + i4);
			for (int i4 = 0; i4 < 5; i4++)
				hitIcon[i4] = new Sprite("/Hits/icon " + i4);
			for (int l4 = 0; l4 < 10; l4++)
				modIcons[l4] = new Sprite(streamLoader_2, "mod_icons", l4);
			RSFont.unpackImages(modIcons, clanIcons);
			infinity = new Sprite("/Shop/infinity");
			backgroundFix = new Sprite("");
			realBackground = new Sprite("Background");
			Sprite sprite = new Sprite(streamLoader_2, "screenframe", 0);
			leftFrame = new GraphicalComponent(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);
			sprite = new Sprite(streamLoader_2, "screenframe", 1);
			topFrame = new GraphicalComponent(sprite.myWidth, sprite.myHeight);
			sprite.method346(0, 0);
			int i5 = (int) (Math.random() * 21D) - 10;
			int j5 = (int) (Math.random() * 21D) - 10;
			int k5 = (int) (Math.random() * 21D) - 10;
			int l5 = (int) (Math.random() * 41D) - 20;
			for (int i6 = 0; i6 < 100; i6++) {
				if (mapFunctions[i6] != null)
					mapFunctions[i6].method344(i5 + l5, j5 + l5, k5 + l5);
				if (mapScenes[i6] != null)
					mapScenes[i6].method360(i5 + l5, j5 + l5, k5 + l5);
			}
            drawSmoothLoadingText(83, "Unpacking textures");
			Rasterizer.unpackTextures(streamLoader_3);
			Rasterizer.calculatePalette(0.80000000000000004D);
			Rasterizer.resetTextures();
			Animation.unpackConfig(jagexArchive);
			ObjectDefinition.unpackConfig(jagexArchive);
			OverlayFloor.unpackConfig(jagexArchive);
			UnderlayFloor.unpackConfig(jagexArchive);
			ItemDefinition.unpackConfig(jagexArchive);
			EntityDefinition.unpackConfig(jagexArchive);
			IdentityKitDefinition.unpackConfig(jagexArchive);
			SpotAnimDefinition.unpackConfig(jagexArchive);
			Varp.unpackConfig(jagexArchive);
			VarBit.unpackConfig(jagexArchive);
			ItemDefinition.isMembers = isMembers;
            drawSmoothLoadingText(95, "Unpacking interfaces");
			TextDrawingArea aclass30_sub2_sub1_sub4s[] = { smallText, aTextDrawingArea_1271, chatTextDrawingArea, aTextDrawingArea_1273 };
			RSInterface.unpack(imageLoader, streamLoader_1, aclass30_sub2_sub1_sub4s, streamLoader_2);
            drawSmoothLoadingText(100, "Preparing game engine");
			for (int j6 = 0; j6 < 33; j6++) {
				int k6 = 999;
				int i7 = 0;
				for (int k7 = 0; k7 < 34; k7++) {
					if (mapBack.aByteArray1450[k7 + j6 * mapBack.anInt1452] == 0) {
						if (k6 == 999)
							k6 = k7;
						continue;
					}
					if (k6 == 999)
						continue;
					i7 = k7;
					break;
				}
				anIntArray968[j6] = k6;
				anIntArray1057[j6] = i7 - k6;
			}
			for (int l6 = 1; l6 < 153; l6++) {
				int j7 = 999;
				int l7 = 0;
				for (int j8 = 24; j8 < 177; j8++) {
					if (mapBack.aByteArray1450[j8 + l6 * mapBack.anInt1452] == 0 && (j8 > 34 || l6 > 34)) {
						if (j7 == 999) {
							j7 = j8;
						}
						continue;
					}
					if (j7 == 999) {
						continue;
					}
					l7 = j8;
					break;
				}
				anIntArray1052[l6 - 1] = j7 - 24;
				anIntArray1229[l6 - 1] = l7 - j7;
			}
			updateGameArea();
			Censor.loadConfig(streamLoader_4);
			mouseDetection = new MouseDetection(this);
			startRunnable(mouseDetection, 10);
			Animable_Sub5.clientInstance = this;
			ObjectDefinition.clientInstance = this;
			EntityDefinition.clientInstance = this;
			return;
		} catch (Exception exception) {
			exception.printStackTrace();
			Signlink.reporterror("loaderror " + lastMessage + " " + lastPercent);
		}
		loadingError = true;
	}

	static int interfaceButtonAction = 0;

	void sendPacket(int packet) {
		if (packet == 103) {
			buffer.createFrame(103);
			buffer.writeWordBigEndian(inputString.length() - 1);
			buffer.writeString(inputString.substring(2));
			inputString = "";
			promptInput = "";
			interfaceButtonAction = 0;
		}

		if (packet == 1003) {
			buffer.createFrame(103);
			inputString = "::" + inputString;
			buffer.writeWordBigEndian(inputString.length() - 1);
			buffer.writeString(inputString.substring(2));
			inputString = "";
			promptInput = "";
			interfaceButtonAction = 0;
		}
	}
	
	void clanRank(int packet, String name, int rank) {
		if (packet == 65) {
			buffer.createFrame(65);
			buffer.writeWordBigEndian(0);
			int initialOffset = buffer.currentOffset;
			buffer.writeWordBigEndian(rank);
			TextInput.method526(name, buffer);
			buffer.writeBytes(buffer.currentOffset - initialOffset);
			interfaceButtonAction = -1;
		}
	}

	private void method91(Buffer buffer, int i) {
		while (buffer.bitPosition + 10 < i * 8) {
			int j = buffer.readBits(11);
			if (j == 2047)
				break;
			if (playerArray[j] == null) {
				playerArray[j] = new Player();
				if (aStreamArray895s[j] != null)
					playerArray[j].updatePlayer(aStreamArray895s[j]);
			}
			playerIndices[playerCount++] = j;
			Player player = playerArray[j];
			player.anInt1537 = loopCycle;
			int k = buffer.readBits(1);
			if (k == 1)
				anIntArray894[anInt893++] = j;
			int l = buffer.readBits(1);
			int i1 = buffer.readBits(5);
			if (i1 > 15)
				i1 -= 32;
			int j1 = buffer.readBits(5);
			if (j1 > 15)
				j1 -= 32;
			player.setPos(myPlayer.smallX[0] + j1, myPlayer.smallY[0] + i1, l == 1);
		}
		buffer.finishBitAccess();
	}

    public String indexLocation(int cacheIndex, int index) {
        return "C:/Users/Evan/Desktop/index" + cacheIndex + "/" + (index != -1 ? index + ".gz" : "");
    }

	public void repackCacheIndex(int cacheIndex) {
		System.out.println("Started repacking index " + cacheIndex + ".");
		int indexLength = new File(indexLocation(cacheIndex, -1)).listFiles().length;
		File[] file = new File(indexLocation(cacheIndex, -1)).listFiles();
		try {
			for (int index = 0; index < indexLength; index++) {
				int fileIndex = Integer.parseInt(getFileNameWithoutExtension(file[index].toString()));
				byte[] data = fileToByteArray(cacheIndex, fileIndex);
				if (data != null && data.length > 0) {
					decompressors[cacheIndex].method234(data.length, data, fileIndex);
					System.out.println("Repacked " + fileIndex + ".");
				} else {
					System.out.println("Unable to locate index " + fileIndex + ".");
				}
			}
		} catch (Exception e) {
			System.out.println("Error packing cache index " + cacheIndex + ".");
		}
		System.out.println("Finished repacking " + cacheIndex + ".");
	}

	public byte[] fileToByteArray(int cacheIndex, int index) {
		try {
			if (indexLocation(cacheIndex, index).length() <= 0 || indexLocation(cacheIndex, index) == null) {
				return null;
			}
			File file = new File(indexLocation(cacheIndex, index));
			byte[] fileData = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			fis.read(fileData);
			fis.close();
			return fileData;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius) {
		return java.lang.Math.pow((circleX + radius - clickX), 2) + java.lang.Math.pow((circleY + radius - clickY), 2) < java.lang.Math.pow(radius, 2);
	}

	private void processMainScreenClick() {
		if (mapState != 0) {
			return;
		}
		if (super.clickMode3 == 1) {
			int clickX = super.saveClickX - 3 - (clientSize == 0 ? clientWidth - 214 : clientWidth - 163);
			int clickY = super.saveClickY - (clientSize == 0 ? 9 : 6);
			if (inCircle(0, 0, clickX, clickY, 76)) {
				clickX -= 73;
				clickY -= 75;
				int k = minimapInt1 + minimapInt2 & 0x7ff;
				int i1 = Rasterizer.SINE[k];
				int j1 = Rasterizer.COSINE[k];
				i1 = i1 * (minimapInt3 + 256) >> 8;
				j1 = j1 * (minimapInt3 + 256) >> 8;
				int k1 = clickY * i1 + clickX * j1 >> 11;
				int l1 = clickY * j1 - clickX * i1 >> 11;
				int i2 = myPlayer.x + k1 >> 7;
				int j2 = myPlayer.y - l1 >> 7;
				boolean flag1 = doWalkTo(1, 0, 0, 0, myPlayer.smallY[0], 0, 0, j2, myPlayer.smallX[0], true, i2);
				if (flag1) {
					buffer.writeWordBigEndian(clickX);
					buffer.writeWordBigEndian(clickY);
					buffer.writeWord(minimapInt1);
					buffer.writeWordBigEndian(57);
					buffer.writeWordBigEndian(minimapInt2);
					buffer.writeWordBigEndian(minimapInt3);
					buffer.writeWordBigEndian(89);
					buffer.writeWord(myPlayer.x);
					buffer.writeWord(myPlayer.y);
					buffer.writeWordBigEndian(anInt1264);
					buffer.writeWordBigEndian(63);
				}
			}
			anInt1117++;
			if (anInt1117 > 1151) {
				anInt1117 = 0;
				buffer.createFrame(246);
				buffer.writeWordBigEndian(0);
				int l = buffer.currentOffset;
				if ((int) (Math.random() * 2D) == 0)
					buffer.writeWordBigEndian(101);
				buffer.writeWordBigEndian(197);
				buffer.writeWord((int) (Math.random() * 65536D));
				buffer.writeWordBigEndian((int) (Math.random() * 256D));
				buffer.writeWordBigEndian(67);
				buffer.writeWord(14214);
				if ((int) (Math.random() * 2D) == 0)
					buffer.writeWord(29487);
				buffer.writeWord((int) (Math.random() * 65536D));
				if ((int) (Math.random() * 2D) == 0)
					buffer.writeWordBigEndian(220);
				buffer.writeWordBigEndian(180);
				buffer.writeBytes(buffer.currentOffset - l);
			}
		}
	}

	private String interfaceIntToString(int j) {
		if (j < 0x3b9ac9ff)
			return String.valueOf(j);
		else
			return "*";
	}

	private void showErrorScreen() {
		Graphics g = getGameComponent().getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 765, 503);
		method4(1);
		if (loadingError) {
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 16));
			g.setColor(Color.yellow);
			int k = 35;
			g.drawString("Sorry, an error has occured whilst loading Mistex", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, k);
			k += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, k);
			k += 30;
			g.drawString("2: Try clearing your web-browsers cache from tools->internet options", 30, k);
			k += 30;
			g.drawString("3: Try using a different game-world", 30, k);
			k += 30;
			g.drawString("4: Try rebooting your computer", 30, k);
			k += 30;
			g.drawString("5: Try selecting a different version of Java from the play-game menu", 30, k);
		}
		if (genericLoadingError) {
			aBoolean831 = false;
			g.setFont(new Font("Helvetica", 1, 20));
			g.setColor(Color.white);
			g.drawString("Error - unable to load game!", 50, 50);
			g.drawString("To play Mistex make sure you play from", 50, 100);
			g.drawString("http://www.mistex.org", 50, 150);
		}
		if (rsAlreadyLoaded) {
			aBoolean831 = false;
			g.setColor(Color.yellow);
			int l = 35;
			g.drawString("Error a copy of Mistex already appears to be loaded", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.drawString("To fix this try the following (in order):", 30, l);
			l += 50;
			g.setColor(Color.white);
			g.setFont(new Font("Helvetica", 1, 12));
			g.drawString("1: Try closing ALL open web-browser windows, and reloading", 30, l);
			l += 30;
			g.drawString("2: Try rebooting your computer, and reloading", 30, l);
			l += 30;
		}
	}

	public URL getCodeBase() {
		try {
			return new URL(server + ":" + (80 + portOff));
		} catch (Exception _ex) {
		}
		return null;
	}

	private void method95() {
		for (int j = 0; j < npcCount; j++) {
			int k = npcIndices[j];
			Npc npc = npcArray[k];
			if (npc != null)
				method96(npc);
		}
	}

	private void method96(Entity entity) {
		if (entity.x < 128 || entity.y < 128 || entity.x >= 13184 || entity.y >= 13184) {
			entity.anim = -1;
			entity.anInt1520 = -1;
			entity.anInt1547 = 0;
			entity.anInt1548 = 0;
			entity.x = ((entity.smallX[0] << 7) + entity.anInt1540 << 6);
			entity.y = ((entity.smallY[0] << 7) + entity.anInt1540 << 6);
			entity.method446();
		}
		if (entity == myPlayer && (entity.x < 1536 || entity.y < 1536 || entity.x >= 11776 || entity.y >= 11776)) {
			entity.anim = -1;
			entity.anInt1520 = -1;
			entity.anInt1547 = 0;
			entity.anInt1548 = 0;
			entity.x = (entity.smallX[0] << 7) + (entity.anInt1540 << 6);
			entity.y = (entity.smallY[0] << 7) + (entity.anInt1540 << 6);
			entity.method446();
		}
		if (entity.anInt1547 > loopCycle)
			method97(entity);
		else if (entity.anInt1548 >= loopCycle)
			method98(entity);
		else
			method99(entity);
		method100(entity);
		method101(entity);
	}

	private void method97(Entity entity) {
		int i = entity.anInt1547 - loopCycle;
		int j = (entity.anInt1543 << 7) + (entity.anInt1540 << 6);
		int k = (entity.anInt1545 << 7) + (entity.anInt1540 << 6);
		entity.x += (j - entity.x) / i;
		entity.y += (k - entity.y) / i;
		entity.anInt1503 = 0;
		if (entity.anInt1549 == 0)
			entity.turnDirection = 1024;
		if (entity.anInt1549 == 1)
			entity.turnDirection = 1536;
		if (entity.anInt1549 == 2)
			entity.turnDirection = 0;
		if (entity.anInt1549 == 3)
			entity.turnDirection = 512;
	}

	private void method98(Entity entity) {
		if (entity.anInt1548 == loopCycle || entity.anim == -1 || entity.anInt1529 != 0 || entity.anInt1528 + 1 > Animation.anims[entity.anim].method258(entity.anInt1527)) {
			int i = entity.anInt1548 - entity.anInt1547;
			int j = loopCycle - entity.anInt1547;
			int k = (entity.anInt1543 << 7) + (entity.anInt1540 << 6);
			int l = (entity.anInt1545 << 7) + (entity.anInt1540 << 6);
			int i1 = (entity.anInt1544 << 7) + (entity.anInt1540 << 6);
			int j1 = (entity.anInt1546 << 7) + (entity.anInt1540 << 6);
			entity.x = (k * (i - j) + i1 * j) / i;
			entity.y = (l * (i - j) + j1 * j) / i;
		}
		entity.anInt1503 = 0;
		if (entity.anInt1549 == 0)
			entity.turnDirection = 1024;
		if (entity.anInt1549 == 1)
			entity.turnDirection = 1536;
		if (entity.anInt1549 == 2)
			entity.turnDirection = 0;
		if (entity.anInt1549 == 3)
			entity.turnDirection = 512;
		entity.anInt1552 = entity.turnDirection;
	}

	private void method99(Entity entity) {
		entity.anInt1517 = entity.anInt1511;
		if (entity.smallXYIndex == 0) {
			entity.anInt1503 = 0;
			return;
		}
		if (entity.anim != -1 && entity.anInt1529 == 0) {
			Animation animation = Animation.anims[entity.anim];
			if (entity.anInt1542 > 0 && animation.anInt363 == 0) {
				entity.anInt1503++;
				return;
			}
			if (entity.anInt1542 <= 0 && animation.anInt364 == 0) {
				entity.anInt1503++;
				return;
			}
		}
		int i = entity.x;
		int j = entity.y;
		int k = (entity.smallX[entity.smallXYIndex - 1] << 7) + (entity.anInt1540 << 6);
		int l = (entity.smallY[entity.smallXYIndex - 1] << 7) + (entity.anInt1540 << 6);
		if (k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256) {
			entity.x = k;
			entity.y = l;
			return;
		}
		if (i < k) {
			if (j < l)
				entity.turnDirection = 1280;
			else if (j > l)
				entity.turnDirection = 1792;
			else
				entity.turnDirection = 1536;
		} else if (i > k) {
			if (j < l)
				entity.turnDirection = 768;
			else if (j > l)
				entity.turnDirection = 256;
			else
				entity.turnDirection = 512;
		} else if (j < l)
			entity.turnDirection = 1024;
		else
			entity.turnDirection = 0;
		int i1 = entity.turnDirection - entity.anInt1552 & 0x7ff;
		if (i1 > 1024)
			i1 -= 2048;
		int j1 = entity.anInt1555;
		if (i1 >= -256 && i1 <= 256)
			j1 = entity.anInt1554;
		else if (i1 >= 256 && i1 < 768)
			j1 = entity.anInt1557;
		else if (i1 >= -768 && i1 <= -256)
			j1 = entity.anInt1556;
		if (j1 == -1)
			j1 = entity.anInt1554;
		entity.anInt1517 = j1;
		int k1 = 4;
		if (entity.anInt1552 != entity.turnDirection && entity.interactingEntity == -1 && entity.anInt1504 != 0)
			k1 = 2;
		if (entity.smallXYIndex > 2)
			k1 = 6;
		if (entity.smallXYIndex > 3)
			k1 = 8;
		if (entity.anInt1503 > 0 && entity.smallXYIndex > 1) {
			k1 = 8;
			entity.anInt1503--;
		}
		if (entity.aBooleanArray1553[entity.smallXYIndex - 1])
			k1 <<= 1;
		if (k1 >= 8 && entity.anInt1517 == entity.anInt1554 && entity.anInt1505 != -1)
			entity.anInt1517 = entity.anInt1505;
		if (i < k) {
			entity.x += k1;
			if (entity.x > k)
				entity.x = k;
		} else if (i > k) {
			entity.x -= k1;
			if (entity.x < k)
				entity.x = k;
		}
		if (j < l) {
			entity.y += k1;
			if (entity.y > l)
				entity.y = l;
		} else if (j > l) {
			entity.y -= k1;
			if (entity.y < l)
				entity.y = l;
		}
		if (entity.x == k && entity.y == l) {
			entity.smallXYIndex--;
			if (entity.anInt1542 > 0)
				entity.anInt1542--;
		}
	}

	private void method100(Entity entity) {
		if (entity.anInt1504 == 0)
			return;
		if (entity.interactingEntity != -1 && entity.interactingEntity < 32768) {
			Npc npc = npcArray[entity.interactingEntity];
			if (npc != null) {
				int i1 = entity.x - npc.x;
				int k1 = entity.y - npc.y;
				if (i1 != 0 || k1 != 0)
					entity.turnDirection = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
			}
		}
		if (entity.interactingEntity >= 32768) {
			int j = entity.interactingEntity - 32768;
			if (j == unknownInt10)
				j = myPlayerIndex;
			Player player = playerArray[j];
			if (player != null) {
				int l1 = entity.x - player.x;
				int i2 = entity.y - player.y;
				if (l1 != 0 || i2 != 0)
					entity.turnDirection = (int) (Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff;
			}
		}
		if ((entity.anInt1538 != 0 || entity.anInt1539 != 0) && (entity.smallXYIndex == 0 || entity.anInt1503 > 0)) {
			int k = entity.x - ((entity.anInt1538 - baseX - baseX) << 6);
			int j1 = entity.y - ((entity.anInt1539 - baseY - baseY) << 6);
			if (k != 0 || j1 != 0)
				entity.turnDirection = (int) (Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff;
			entity.anInt1538 = 0;
			entity.anInt1539 = 0;
		}
		int l = entity.turnDirection - entity.anInt1552 & 0x7ff;
		if (l != 0) {
			if (l < entity.anInt1504 || l > 2048 - entity.anInt1504)
				entity.anInt1552 = entity.turnDirection;
			else if (l > 1024)
				entity.anInt1552 -= entity.anInt1504;
			else
				entity.anInt1552 += entity.anInt1504;
			entity.anInt1552 &= 0x7ff;
			if (entity.anInt1517 == entity.anInt1511 && entity.anInt1552 != entity.turnDirection) {
				if (entity.anInt1512 != -1) {
					entity.anInt1517 = entity.anInt1512;
					return;
				}
				entity.anInt1517 = entity.anInt1554;
			}
		}
	}

	private void method101(Entity entity) {
		try {
		entity.aBoolean1541 = false;
		if (entity.anInt1517 != -1) {
			Animation animation = Animation.anims[entity.anInt1517];
			entity.anInt1519++;
			if (entity.anInt1518 < animation.anInt352 && entity.anInt1519 > animation.method258(entity.anInt1518)) {
				entity.anInt1519 = 1;
				entity.anInt1518++;
			}
			if (entity.anInt1518 >= animation.anInt352) {
				entity.anInt1519 = 0;
				entity.anInt1518 = 0;
			}
			if (enableTweening) {
				entity.nextIdleAnimFrame = entity.anInt1518 + 1;
			}
			if (entity.nextIdleAnimFrame >= animation.anInt352) {
				entity.nextIdleAnimFrame = -1;
			}
		}
		if (entity.anInt1520 != -1 && loopCycle >= entity.anInt1523) {
			if (entity.anInt1521 < 0)
				entity.anInt1521 = 0;
			Animation animation_1 = SpotAnimDefinition.cache[entity.anInt1520].aAnimation_407;
			for (entity.anInt1522++; entity.anInt1521 < animation_1.anInt352 && entity.anInt1522 > animation_1.method258(entity.anInt1521); entity.anInt1521++)
				entity.anInt1522 -= animation_1.method258(entity.anInt1521);

			if (entity.anInt1521 >= animation_1.anInt352 && (entity.anInt1521 < 0 || entity.anInt1521 >= animation_1.anInt352)) {
				entity.anInt1520 = -1;
			}
			if (enableTweening) {
				entity.nextIdleAnimFrame = entity.anInt1518 + 1;
			}
			if (entity.nextSpotAnimFrame >= animation_1.anInt352) {
				entity.nextSpotAnimFrame = -1;
			}
		}
		if (entity.anim != -1 && entity.anInt1529 <= 1) {
			Animation animation_2 = Animation.anims[entity.anim];
			if (animation_2.anInt363 == 1 && entity.anInt1542 > 0 && entity.anInt1547 <= loopCycle && entity.anInt1548 < loopCycle) {
				entity.anInt1529 = 1;
				return;
			}
		}
		if (entity.anim != -1 && entity.anInt1529 == 0) {
			Animation animation_3 = Animation.anims[entity.anim];
			for (entity.anInt1528++; entity.anInt1527 < animation_3.anInt352 && entity.anInt1528 > animation_3.method258(entity.anInt1527); entity.anInt1527++)
				entity.anInt1528 -= animation_3.method258(entity.anInt1527);

			if (entity.anInt1527 >= animation_3.anInt352) {
				entity.anInt1527 -= animation_3.anInt356;
				entity.anInt1530++;
				if (entity.anInt1530 >= animation_3.anInt362)
					entity.anim = -1;
				if (entity.anInt1527 < 0 || entity.anInt1527 >= animation_3.anInt352)
					entity.anim = -1;
			}
			if (enableTweening) {
				entity.nextAnimFrame = entity.anInt1527 + 1;
			}
			if (entity.nextAnimFrame >= animation_3.anInt352) {
				entity.nextAnimFrame = -1;
			}
			entity.aBoolean1541 = animation_3.aBoolean358;
		}
		if (entity.anInt1529 > 0)
			entity.anInt1529--;
		} catch(Exception e) {
			
		}
	}

	private void drawGameScreen() {
		if (fullscreenInterfaceID != -1 && (loadingStage == 2 || super.fullGameScreen != null)) {
			if (loadingStage == 2) {
				method119(anInt945, fullscreenInterfaceID);
				if (openInterfaceID != -1) {
					method119(anInt945, openInterfaceID);
				}
				anInt945 = 0;
				resetAllImageProducers();
				super.fullGameScreen.setCanvas();
				Rasterizer.lineOffsets = fullScreenTextureArray;
				DrawingArea.setAllPixelsToZero();
				welcomeScreenRaised = true;
				if (openInterfaceID != -1) {
					RSInterface rsInterface_1 = RSInterface.interfaceCache[openInterfaceID];
					if (rsInterface_1.width == 512 && rsInterface_1.height == 334 && rsInterface_1.type == 0) {
						rsInterface_1.width = (clientSize == 0 ? 765 : clientWidth);
						rsInterface_1.height = (clientSize == 0 ? 503 : clientHeight);
					}
					drawInterface(0, clientSize == 0 ? 0 : (clientWidth / 2) - 765 / 2, rsInterface_1, clientSize == 0 ? 8 : (clientHeight / 2) - 503 / 2);
				}
				RSInterface rsInterface = RSInterface.interfaceCache[fullscreenInterfaceID];
				if (rsInterface.width == 512 && rsInterface.height == 334 && rsInterface.type == 0) {
					rsInterface.width = (clientSize == 0 ? 765 : clientWidth);
					rsInterface.height = (clientSize == 0 ? 503 : clientHeight);
				}
				drawInterface(0, clientSize == 0 ? 0 : (clientWidth / 2) - 765 / 2, rsInterface, clientSize == 0 ? 8 : (clientHeight / 2) - 503 / 2);
			}
			drawCount++;
			super.fullGameScreen.drawGraphics(0, super.graphics, 0);
			return;
		} else {
			if (drawCount != 0) {
				resetImageProducers2();
			}
		}
		if (welcomeScreenRaised) {
			welcomeScreenRaised = false;
			topFrame.drawGraphics(0, super.graphics, 0);
			leftFrame.drawGraphics(4, super.graphics, 0);
			inputTaken = true;
			tabAreaAltered = true;
			if (loadingStage != 2) {
				aRSImageProducer_1165.drawGraphics(clientSize == 0 ? 4 : 0, super.graphics, clientSize == 0 ? 4 : 0);
				if (clientSize == 0)
					aRSImageProducer_1164.drawGraphics(0, super.graphics, 516);
			}
		}
		if (!menuOpen) {
			processRightClick();
			if (enableHoverBox)
				drawTooltip2();
			else
				drawTooltip();
		} else {
			if (isFixed()) {
				drawMenu(4, 4);
			} else {
				drawMenu();
			}
		}
		if (invOverlayInterfaceID != -1) {
			boolean flag1 = method119(anInt945, invOverlayInterfaceID);
			if (flag1) {
			}
		}
		drawTabArea();
		if (backDialogID == -1) {
			aClass9_1059.scrollPosition = anInt1211 - anInt1089 - 110;
			if (mouseX > 478 && mouseX < 580 && mouseY > (clientSize == 0 ? 342 : clientHeight - 169))
				method65((clientSize == 0 ? 496 : 502), 110, mouseX, mouseY - (clientSize == 0 ? 348 : clientHeight - 155), aClass9_1059, 0, false, anInt1211);
			int i = anInt1211 - 110 - aClass9_1059.scrollPosition;
			if (i < 0)
				i = 0;
			if (i > anInt1211 - 110)
				i = anInt1211 - 110;
			if (anInt1089 != i) {
				anInt1089 = i;
				inputTaken = true;
			}
		}
		if (backDialogID != -1) {
			boolean flag2 = method119(anInt945, backDialogID);
			if (flag2)
				inputTaken = true;
		}
		if (atInventoryInterfaceType == 3)
			inputTaken = true;
		if (activeInterfaceType == 3)
			inputTaken = true;
		if (aString844 != null)
			inputTaken = true;
		if (menuOpen && menuScreenArea == 2)
			inputTaken = true;
		if (inputTaken) {
			if (clientSize == 0) {
				drawChatArea();
			}
			drawConsoleArea();
			inputTaken = false;
		}
		if (loadingStage == 2)
			method146();
		if (loadingStage == 2) {
			if (clientSize == 0) {
				drawMinimap();
				aRSImageProducer_1164.drawGraphics(0, super.graphics, 516);
			}
		}
		if (anInt1054 != -1)
			tabAreaAltered = true;
		if (tabAreaAltered) {
			if (anInt1054 != -1 && anInt1054 == tabID) {
				anInt1054 = -1;
				buffer.createFrame(120);
				buffer.writeWordBigEndian(tabID);
			}
			tabAreaAltered = false;
			aRSImageProducer_1125.setCanvas();
			aRSImageProducer_1165.setCanvas();
		}
		anInt945 = 0;
	}

	private boolean buildFriendsListMenu(RSInterface class9) {
		int i = class9.contentType;
		if (i >= 1 && i <= 200 || i >= 701 && i <= 900) {
			if (i >= 801)
				i -= 701;
			else if (i >= 701)
				i -= 601;
			else if (i >= 101)
				i -= 101;
			else
				i--;
			menuActionName[menuActionRow] = "Remove @whi@" + friendsList[i];
			menuActionID[menuActionRow] = 792;
			menuActionRow++;
			menuActionName[menuActionRow] = "Message @whi@" + friendsList[i];
			menuActionID[menuActionRow] = 639;
			menuActionRow++;
			return true;
		}
		if (i >= 401 && i <= 500) {
			menuActionName[menuActionRow] = "Remove @whi@" + class9.message;
			menuActionID[menuActionRow] = 322;
			menuActionRow++;
			return true;
		} else {
			return false;
		}
	}

	private void method104() {
		Animable_Sub3 class30_sub2_sub4_sub3 = (Animable_Sub3) aClass19_1056.reverseGetFirst();
		for (; class30_sub2_sub4_sub3 != null; class30_sub2_sub4_sub3 = (Animable_Sub3) aClass19_1056.reverseGetNext())
			if (class30_sub2_sub4_sub3.anInt1560 != plane || class30_sub2_sub4_sub3.aBoolean1567)
				class30_sub2_sub4_sub3.unlink();
			else if (loopCycle >= class30_sub2_sub4_sub3.anInt1564) {
				class30_sub2_sub4_sub3.method454(anInt945);
				if (class30_sub2_sub4_sub3.aBoolean1567)
					class30_sub2_sub4_sub3.unlink();
				else
					worldController.method285(class30_sub2_sub4_sub3.anInt1560, 0, class30_sub2_sub4_sub3.anInt1563, -1, class30_sub2_sub4_sub3.anInt1562, 60, class30_sub2_sub4_sub3.anInt1561, class30_sub2_sub4_sub3, false);
			}

	}

	public void drawBlackBox(int xPos, int yPos) {
		DrawingArea.drawPixels(71, yPos - 1, xPos - 2, 0x726451, 1);
		DrawingArea.drawPixels(69, yPos, xPos + 174, 0x726451, 1);
		DrawingArea.drawPixels(1, yPos - 2, xPos - 2, 0x726451, 178);
		DrawingArea.drawPixels(1, yPos + 68, xPos, 0x726451, 174);
		DrawingArea.drawPixels(71, yPos - 1, xPos - 1, 0x2E2B23, 1);
		DrawingArea.drawPixels(71, yPos - 1, xPos + 175, 0x2E2B23, 1);
		DrawingArea.drawPixels(1, yPos - 1, xPos, 0x2E2B23, 175);
		DrawingArea.drawPixels(1, yPos + 69, xPos, 0x2E2B23, 175);
		DrawingArea.method335(0, yPos, 174, 68, 220, xPos);
	}

	private void drawInterface(int scrollOffset, int interfaceX, RSInterface rsInterface, int interfaceY) {
		if (rsInterface == null)
			rsInterface = RSInterface.interfaceCache[21356];

		if (rsInterface.type != 0 || rsInterface.children == null)
			return;
		if (rsInterface.isMouseoverTriggered && anInt1026 != rsInterface.id && anInt1048 != rsInterface.id && anInt1039 != rsInterface.id)
			return;
		int i1 = DrawingArea.clipStartX;
		int j1 = DrawingArea.clipStartY;
		int k1 = DrawingArea.clipEndX;
		int l1 = DrawingArea.clipEndY;
		DrawingArea.setDrawingArea(interfaceY + rsInterface.height, interfaceX, interfaceX + rsInterface.width, interfaceY);
		int i2 = rsInterface.children.length;
		int alpha = rsInterface.transparency;
		for (int j2 = 0; j2 < i2; j2++) {
			int k2 = rsInterface.childX[j2] + interfaceX;
			int l2 = (rsInterface.childY[j2] + interfaceY) - scrollOffset;
			RSInterface class9_1 = RSInterface.interfaceCache[rsInterface.children[j2]];
			k2 += class9_1.anInt263;
			l2 += class9_1.anInt265;

			if (class9_1.contentType > 0)
				drawFriendsListOrWelcomeScreen(class9_1);
			int[] IDs = { 1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274, 1283, 1573, 1290, 1299, 1308, 1315, 1324, 1333, 1340, 1349, 1358, 1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414, 1421, 1430, 1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478, 1485, 1494, 1503, 1512, 1521, 1530, 1544, 1553, 1563,
					1593, 1635, 12426, 12436, 12446, 12456, 6004, 18471,
					/* Ancients */
					12940, 12988, 13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920, 12882, 13062, 12952, 13000, 13070, 12912, 12872, 13080, 12976, 13024, 13088, 12930, 12892, 13096 };
			for (int m5 = 0; m5 < IDs.length; m5++) {
				if (class9_1.id == IDs[m5] + 1) {
					if (m5 > 61)
						drawBlackBox(k2 + 1, l2);
					else
						drawBlackBox(k2, l2 + 1);
				}
			}
			int[] runeChildren = { 1202, 1203, 1209, 1210, 1211, 1218, 1219, 1220, 1227, 1228, 1234, 1235, 1236, 1243, 1244, 1245, 1252, 1253, 1254, 1261, 1262, 1263, 1270, 1271, 1277, 1278, 1279, 1286, 1287, 1293, 1294, 1295, 1302, 1303, 1304, 1311, 1312, 1318, 1319, 1320, 1327, 1328, 1329, 1336, 1337, 1343, 1344, 1345, 1352, 1353, 1354, 1361,
					1362, 1363, 1370, 1371, 1377, 1378, 1384, 1385, 1391, 1392, 1393, 1400, 1401, 1407, 1408, 1410, 1417, 1418, 1424, 1425, 1426, 1433, 1434, 1440, 1441, 1442, 1449, 1450, 1456, 1457, 1463, 1464, 1465, 1472, 1473, 1474, 1481, 1482, 1488, 1489, 1490, 1497, 1498, 1499, 1506, 1507, 1508, 1515, 1516, 1517, 1524, 1525, 1526, 1533, 1534,
					1535, 1547, 1548, 1549, 1556, 1557, 1558, 1566, 1567, 1568, 1576, 1577, 1578, 1586, 1587, 1588, 1596, 1597, 1598, 1605, 1606, 1607, 1616, 1617, 1618, 1627, 1628, 1629, 1638, 1639, 1640, 6007, 6008, 6011, 8673, 8674, 12041, 12042, 12429, 12430, 12431, 12439, 12440, 12441, 12449, 12450, 12451, 12459, 12460, 15881, 15882, 15885,
					18474, 18475, 18478 };
			for (int r = 0; r < runeChildren.length; r++)
				if (class9_1.id == runeChildren[r])
					class9_1.modelZoom = 775;
			if (class9_1.type == 0) {
				if (class9_1.scrollPosition > class9_1.scrollMax - class9_1.height)
					class9_1.scrollPosition = class9_1.scrollMax - class9_1.height;
				if (class9_1.scrollPosition < 0)
					class9_1.scrollPosition = 0;

				if (class9_1.id != 12855)
					drawInterface(class9_1.scrollPosition, k2, class9_1, l2);

				if (class9_1.scrollMax > class9_1.height)
					drawScrollbar(class9_1.height, class9_1.scrollPosition, l2, k2 + class9_1.width, class9_1.scrollMax);
			} else if (class9_1.type != 1)
				if (class9_1.type == 2) {
					int i3 = 0;
					for (int l3 = 0; l3 < class9_1.height; l3++) {
						for (int l4 = 0; l4 < class9_1.width; l4++) {
							int k5 = k2 + l4 * (32 + class9_1.invSpritePadX);
							int j6 = l2 + l3 * (32 + class9_1.invSpritePadY);
							if (i3 < 20) {
								k5 += class9_1.spritesX[i3];
								j6 += class9_1.spritesY[i3];
							}
							if (class9_1.inv[i3] > 0) {
								if (class9_1.id == 3900) { /*
															 * Shop interface
															 * hardcode
															 */
									if (stock == null) {
										stock = new Sprite("/Shop/stock");
									}
									stock.drawSprite(k5 - 7, j6 - 4);
								}

								if (class9_1.inv[i3] > 0) {
									int k6 = 0;
									int j7 = 0;
									int j9 = class9_1.inv[i3] - 1;
									if (k5 > DrawingArea.clipStartX - 32 && k5 < DrawingArea.clipEndX && j6 > DrawingArea.clipStartY - 32 && j6 < DrawingArea.clipEndY || activeInterfaceType != 0 && itemFrom == i3) {
										int l9 = 0;
										if (itemSelected == 1 && anInt1283 == i3 && anInt1284 == class9_1.id)
											l9 = 0xffffff;
										Sprite class30_sub2_sub1_sub1_2 = ItemDefinition.getSprite(j9, class9_1.invStackSizes[i3], l9);
										if (class30_sub2_sub1_sub1_2 != null) {
											if (activeInterfaceType != 0 && itemFrom == i3 && interfaceId == class9_1.id) {
												k6 = super.mouseX - anInt1087;
												j7 = super.mouseY - anInt1088;
												if (k6 < 5 && k6 > -5)
													k6 = 0;
												if (j7 < 5 && j7 > -5)
													j7 = 0;
												if (anInt989 < 10) {
													k6 = 0;
													j7 = 0;
												}
												class30_sub2_sub1_sub1_2.drawSprite1(k5 + k6, j6 + j7);
												if (j6 + j7 < DrawingArea.clipStartY && rsInterface.scrollPosition > 0) {
													int i10 = (anInt945 * (DrawingArea.clipStartY - j6 - j7)) / 3;
													if (i10 > anInt945 * 10)
														i10 = anInt945 * 10;
													if (class9_1.id == RSInterface.BANK_CHILDREN_BASE_ID + 63 && super.scrollDelay > 0) {
														super.scrollDelay--;
													} else {
														rsInterface.scrollPosition -= i10;
														anInt1088 += i10;
													}
												}
												if (j6 + j7 + 32 > DrawingArea.clipEndY && rsInterface.scrollPosition < rsInterface.scrollMax - rsInterface.height) {
													int j10 = (anInt945 * ((j6 + j7 + 32) - DrawingArea.clipEndY)) / 3;
													if (j10 > anInt945 * 10)
														j10 = anInt945 * 10;
													if (j10 > rsInterface.scrollMax - rsInterface.height - rsInterface.scrollPosition)
														j10 = rsInterface.scrollMax - rsInterface.height - rsInterface.scrollPosition;
													rsInterface.scrollPosition += j10;
													anInt1088 -= j10;
												}
											} else if (atInventoryInterfaceType != 0 && atInventoryIndex == i3 && atInventoryInterface == class9_1.id)
												class30_sub2_sub1_sub1_2.drawSprite1(k5, j6);
											else
												class30_sub2_sub1_sub1_2.drawSprite(k5, j6);
											if (!(class9_1.id >= RSInterface.BANK_CHILDREN_BASE_ID + 50 && class9_1.id <= RSInterface.BANK_CHILDREN_BASE_ID + 58) && (class30_sub2_sub1_sub1_2.anInt1444 == 33 || class9_1.invStackSizes[i3] != 1)) {
												int k10 = class9_1.invStackSizes[i3];
												if (intToKOrMil(k10) == "@inf@") {
													infinity.drawSprite(k5 + k6, j6 + j7);
												} else {
													smallText.method385(0, intToKOrMil(k10), j6 + 10 + j7, k5 + 1 + k6); // this
													// is
													// the
													// shadow
													if (k10 >= 1)
														smallText.method385(0xFFFF00, intToKOrMil(k10), j6 + 9 + j7, k5 + k6);
													if (k10 >= 100000)
														smallText.method385(0xFFFFFF, intToKOrMil(k10), j6 + 9 + j7, k5 + k6);
													if (k10 >= 10000000)
														smallText.method385(0x00FF80, intToKOrMil(k10), j6 + 9 + j7, k5 + k6);
													if (k10 >= 1000000000)
														smallText.method385(0x00FF80, intToKOrMil(k10), j6 + 9 + j7, k5 + k6);
												}
											}
										}
									}
								}
							} else if (class9_1.sprites != null && i3 < 20) {
								Sprite class30_sub2_sub1_sub1_1 = class9_1.sprites[i3];
								if (class30_sub2_sub1_sub1_1 != null)
									class30_sub2_sub1_sub1_1.drawSprite(k5, j6);
							}
							i3++;
						}
					}
				} else if (class9_1.type == 3) {
					boolean flag = false;
					if (anInt1039 == class9_1.id || anInt1048 == class9_1.id || anInt1026 == class9_1.id)
						flag = true;
					int j3;
					if (interfaceIsSelected(class9_1)) {
						j3 = class9_1.anInt219;
						if (flag && class9_1.anInt239 != 0)
							j3 = class9_1.anInt239;
					} else {
						j3 = class9_1.textColor;
						if (flag && class9_1.anInt216 != 0)
							j3 = class9_1.anInt216;
					}
					if (class9_1.aByte254 == 0) {
						if (class9_1.aBoolean227)
							DrawingArea.drawPixels(class9_1.height, l2, k2, j3, class9_1.width);
						else
							DrawingArea.fillPixels(k2, class9_1.width, class9_1.height, j3, l2);
					} else if (class9_1.aBoolean227)
						DrawingArea.method335(j3, l2, class9_1.width, class9_1.height, 256 - (class9_1.aByte254 & 0xff), k2);
					else
						DrawingArea.method338(l2, class9_1.height, 256 - (class9_1.aByte254 & 0xff), j3, class9_1.width, k2);
				} else if (class9_1.type == 4) {
					TextDrawingArea textDrawingArea = class9_1.textDrawingAreas;
					String s = class9_1.message;
					boolean flag1 = false;
					if (anInt1039 == class9_1.id || anInt1048 == class9_1.id || anInt1026 == class9_1.id)
						flag1 = true;
					int i4;
					if (interfaceIsSelected(class9_1)) {
						i4 = class9_1.anInt219;
						if (flag1 && class9_1.anInt239 != 0)
							i4 = class9_1.anInt239;
						if (class9_1.aString228.length() > 0)
							s = class9_1.aString228;
					} else {
						i4 = class9_1.textColor;
						if (flag1 && class9_1.anInt216 != 0)
							i4 = class9_1.anInt216;
					}
					if (class9_1.atActionType == 6 && aBoolean1149) {
						s = "Please wait...";
						i4 = class9_1.textColor;
					}
					if (class9_1.id >= 28000 && class9_1.id < 28036) { /*
																		 * Shop
																		 * interface
																		 * hardocode
																		 */
						if (RSInterface.interfaceCache[3900].invStackSizes[class9_1.id - 28000] > 0) {
							String[] data = s.split(",");
							int currency = 0;
							if (data.length > 1) {
								currency = Integer.parseInt(data[1]);
							}
							if (currencyImage[currency] == null) {
								currencyImage[currency] = new Sprite("currency " + currency);
							}
							currencyImage[currency].drawSprite(k2 - 5, l2);
							int value = Integer.parseInt(data[0]);
							if (value <= 0) {
								smallText.drawRightAlignedString("FREE", k2 + 37, l2 + DrawingArea.canvasHeight, 0xffff00);
							} else if (value < 100000) {
								smallText.drawRightAlignedString(value + "", k2 + 37, l2 + DrawingArea.canvasHeight, 0xffff00);
							} else if (value < 10000000) {
								smallText.drawRightAlignedString((value / 1000) + "K", k2 + 37, l2 + DrawingArea.canvasHeight, 0xffffff);
							} else {
								smallText.drawRightAlignedString((value / 1000000) + "M", k2 + 37, l2 + DrawingArea.canvasHeight, 0x00ff80);
							}
						}
						continue;
					}
					if ((backDialogID != -1 || dialogID != -1 || class9_1.message.contains("Click here to continue")) && (rsInterface.id == backDialogID || rsInterface.id == dialogID)) {
						if (i4 == 0xffff00) {
							i4 = 255;
						}
						if (i4 == 49152) {
							i4 = 0xffffff;
						}
					}
					if ((class9_1.parentID == 1151) || (class9_1.parentID == 12855)) {
						switch (i4) {
						case 16773120:
							i4 = 0xFE981F;
							break;
						case 7040819:
							i4 = 0xAF6A1A;
							break;
						}
					}
					for (int l6 = l2 + textDrawingArea.anInt1497; s.length() > 0; l6 += textDrawingArea.anInt1497) {
						if (s.indexOf("%") != -1) {
							do {
								int k7 = s.indexOf("%1");
								if (k7 == -1)
									break;
								if (class9_1.id < 4000 || class9_1.id > 5000 && class9_1.id != 13921 && class9_1.id != 13922 && class9_1.id != 12171 && class9_1.id != 12172)
									s = s.substring(0, k7) + amountString(extractInterfaceValues(class9_1, 0)) + s.substring(k7 + 2);
								else
									s = s.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0)) + s.substring(k7 + 2);
							} while (true);
							do {
								int l7 = s.indexOf("%2");
								if (l7 == -1)
									break;
								s = s.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1)) + s.substring(l7 + 2);
							} while (true);
							do {
								int i8 = s.indexOf("%3");
								if (i8 == -1)
									break;
								s = s.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2)) + s.substring(i8 + 2);
							} while (true);
							do {
								int j8 = s.indexOf("%4");
								if (j8 == -1)
									break;
								s = s.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3)) + s.substring(j8 + 2);
							} while (true);
							do {
								int k8 = s.indexOf("%5");
								if (k8 == -1)
									break;
								s = s.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4)) + s.substring(k8 + 2);
							} while (true);
						}
						int l8 = s.indexOf("\\n");
						String s1;
						if (l8 != -1) {
							s1 = s.substring(0, l8);
							s = s.substring(l8 + 2);
						} else {
							s1 = s;
							s = "";
						}
						RSFont font = null;
						if (textDrawingArea == smallText) {
							font = newSmallFont;
						} else if (textDrawingArea == aTextDrawingArea_1271) {
							font = newRegularFont;
						} else if (textDrawingArea == chatTextDrawingArea) {
							font = newBoldFont;
						} else {
							font = newFancyFont;

						}
						if (font == null) {
							System.out.println("Font is = null");
						}
						if (class9_1.centerText) {
							font.drawCenteredString(s1, k2 + class9_1.width / 2, l6, i4, class9_1.textShadow ? 0 : -1);
						} else {
							font.drawBasicString(s1, k2, l6, i4, class9_1.textShadow ? 0 : -1);
						}
					}
				} else if (class9_1.type == 5) {
					Sprite sprite;
					if (interfaceIsSelected(class9_1))
						sprite = class9_1.sprite2;
					else
						sprite = class9_1.sprite1;
					if (spellSelected == 1 && class9_1.id == spellID && spellID != 0 && sprite != null) {
						sprite.drawSprite(k2, l2, 0xffffff);
					} else {
						if (sprite != null)
							if (class9_1.drawsTransparent) {
								sprite.drawTransparentSprite(k2, l2, alpha);
							} else {
								sprite.drawSprite(k2, l2);
							}
					}
				} else if (class9_1.type == 6) {
					int k3 = Rasterizer.centerX;
					int j4 = Rasterizer.centerY;
					Rasterizer.centerX = k2 + class9_1.width / 2;
					Rasterizer.centerY = l2 + class9_1.height / 2;
					int i5 = Rasterizer.SINE[class9_1.modelRotation1] * class9_1.modelZoom >> 16;
					int l5 = Rasterizer.COSINE[class9_1.modelRotation1] * class9_1.modelZoom >> 16;
					boolean flag2 = interfaceIsSelected(class9_1);
					int i7;
					if (flag2)
						i7 = class9_1.anInt258;
					else
						i7 = class9_1.anInt257;
					Model model;
					if (i7 == -1) {
						model = class9_1.method209(-1, -1, flag2);
					} else {
						Animation animation = Animation.anims[i7];
						model = class9_1.method209(animation.anIntArray354[class9_1.anInt246], animation.anIntArray353[class9_1.anInt246], flag2);
					}
					if (model != null)
						model.singleRender(class9_1.modelRotation2, 0, class9_1.modelRotation1, 0, i5, l5);
					Rasterizer.centerX = k3;
					Rasterizer.centerY = j4;
				} else if (class9_1.type == 7) {
					TextDrawingArea textDrawingArea_1 = class9_1.textDrawingAreas;
					int k4 = 0;
					for (int j5 = 0; j5 < class9_1.height; j5++) {
						for (int i6 = 0; i6 < class9_1.width; i6++) {
							if (class9_1.inv[k4] > 0) {
								ItemDefinition itemDefinition = ItemDefinition.forID(class9_1.inv[k4] - 1);
								String s2 = itemDefinition.name;
								if (itemDefinition.stackable || class9_1.invStackSizes[k4] != 1)
									s2 = s2 + " x" + getAmount(class9_1.invStackSizes[k4]);
								int i9 = k2 + i6 * (115 + class9_1.invSpritePadX);
								int k9 = l2 + j5 * (12 + class9_1.invSpritePadY);
								if (class9_1.centerText)
									textDrawingArea_1.method382(class9_1.textColor, i9 + class9_1.width / 2, s2, k9, class9_1.textShadow);
								else
									textDrawingArea_1.method389(class9_1.textShadow, i9, class9_1.textColor, s2, k9);
							}
							k4++;
						}
					}
	            } else if (class9_1.type == 9) {
	                drawTooltipBox(class9_1.popupString, k2, l2, 0xFFFFA0);
				} else if (class9_1.type == 8 && (anInt1500 == class9_1.id || anInt1044 == class9_1.id || anInt1129 == class9_1.id) && anInt1501 == 0 && !menuOpen) {
					int boxWidth = 0;
					int boxHeight = 0;
					TextDrawingArea textDrawingArea_2 = aTextDrawingArea_1271;
					for (String s1 = class9_1.message; s1.length() > 0;) {
						if (s1.indexOf("%") != -1) {
							do {
								int k7 = s1.indexOf("%1");
								if (k7 == -1)
									break;
								s1 = s1.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0)) + s1.substring(k7 + 2);
							} while (true);
							do {
								int l7 = s1.indexOf("%2");
								if (l7 == -1)
									break;
								s1 = s1.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1)) + s1.substring(l7 + 2);
							} while (true);
							do {
								int i8 = s1.indexOf("%3");
								if (i8 == -1)
									break;
								s1 = s1.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2)) + s1.substring(i8 + 2);
							} while (true);
							do {
								int j8 = s1.indexOf("%4");
								if (j8 == -1)
									break;
								s1 = s1.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3)) + s1.substring(j8 + 2);
							} while (true);
							do {
								int k8 = s1.indexOf("%5");
								if (k8 == -1)
									break;
								s1 = s1.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4)) + s1.substring(k8 + 2);
							} while (true);
						}
						int l7 = s1.indexOf("\\n");
						String s4;
						if (l7 != -1) {
							s4 = s1.substring(0, l7);
							s1 = s1.substring(l7 + 2);
						} else {
							s4 = s1;
							s1 = "";
						}
						int j10 = textDrawingArea_2.getTextWidth(s4);
						if (j10 > boxWidth) {
							boxWidth = j10;
						}
						boxHeight += textDrawingArea_2.anInt1497 + 3;
					}
					boxWidth += 6;
					boxHeight += 7;
					boolean canDrawGoal = currentExp[skillIdForButton(class9_1.id)] < 200000000 && SkillConstants.goalData[skillIdForButton(class9_1.id)][0] != -1 && SkillConstants.goalData[skillIdForButton(class9_1.id)][1] != -1 && SkillConstants.goalData[skillIdForButton(class9_1.id)][2] != -1;
					boxHeight += canDrawGoal ? 25 : 1;
					int xPos = (k2 + class9_1.width) - 5 - boxWidth;
					int yPos = l2 + class9_1.height + 5;
					if (xPos < k2 + 5) {
						xPos = k2 + 5;
					}
					if (xPos + boxWidth > interfaceX + rsInterface.width) {
						xPos = (interfaceX + rsInterface.width) - boxWidth;
					}
					if (yPos + boxHeight > interfaceY + rsInterface.height) {
						yPos = (interfaceY + rsInterface.height) - boxHeight;
					}
					if (clientSize == 0) {
						if (xPos + boxWidth + interfaceX + rsInterface.width > clientWidth) {
							xPos = clientWidth - boxWidth - interfaceX - rsInterface.width - 3;
						}
					} else {
						if (xPos + boxWidth > clientWidth) {
							xPos = clientWidth - boxWidth - 15;
						}
					}
					if (yPos + boxHeight > clientHeight - (clientSize == 0 ? yPos + boxHeight - 118 : (clientWidth <= smallTabs ? 75 : 35))) {
						yPos -= boxHeight + 35;
					}
					DrawingArea.drawPixels(boxHeight, yPos, xPos, 0xFFFFA0, boxWidth + 1);
					if (canDrawGoal) {
						int goalPercentage = SkillConstants.goalData[skillIdForButton(class9_1.id)][2];
						DrawingArea.fillPixels(xPos + 4, boxWidth - 7, 17, 0, yPos + boxHeight - 21);
						DrawingArea.drawPixels(15, yPos + boxHeight - 20, 4 + xPos + 1, Color.RED.getRGB(), boxWidth - 9);
						DrawingArea.drawPixels(15, yPos + boxHeight - 20, 4 + xPos + 1, Color.GREEN.getRGB(), (int) ((boxWidth - 7) * .01 * goalPercentage) - 2);
						textDrawingArea_2.drawText(0, goalPercentage + "%", yPos - 9 + boxHeight, (int) (xPos + (boxWidth - 7 - (textDrawingArea_2.getTextWidth(goalPercentage + "%") + 10)) / 2.0 + 24));
					}
					DrawingArea.fillPixels(xPos, boxWidth + 1, boxHeight, 0, yPos);
					String s2 = class9_1.message;
					for (int j11 = yPos + textDrawingArea_2.anInt1497; s2.length() > 0; j11 += textDrawingArea_2.anInt1497 + 4) {
						if (s2.indexOf("%") != -1) {
							do {
								int k7 = s2.indexOf("%1");
								if (k7 == -1)
									break;
								s2 = s2.substring(0, k7) + interfaceIntToString(extractInterfaceValues(class9_1, 0)) + s2.substring(k7 + 2);
							} while (true);
							do {
								int l7 = s2.indexOf("%2");
								if (l7 == -1)
									break;
								s2 = s2.substring(0, l7) + interfaceIntToString(extractInterfaceValues(class9_1, 1)) + s2.substring(l7 + 2);
							} while (true);
							do {
								int i8 = s2.indexOf("%3");
								if (i8 == -1)
									break;
								s2 = s2.substring(0, i8) + interfaceIntToString(extractInterfaceValues(class9_1, 2)) + s2.substring(i8 + 2);
							} while (true);
							do {
								int j8 = s2.indexOf("%4");
								if (j8 == -1)
									break;
								s2 = s2.substring(0, j8) + interfaceIntToString(extractInterfaceValues(class9_1, 3)) + s2.substring(j8 + 2);
							} while (true);
							do {
								int k8 = s2.indexOf("%5");
								if (k8 == -1)
									break;
								s2 = s2.substring(0, k8) + interfaceIntToString(extractInterfaceValues(class9_1, 4)) + s2.substring(k8 + 2);
							} while (true);
						}
						int l11 = s2.indexOf("\\n");
						String s5;
						if (l11 != -1) {
							s5 = s2.substring(0, l11);
							s2 = s2.substring(l11 + 2);
						} else {
							s5 = s2;
							s2 = "";
						}
						if (class9_1.centerText) {
							textDrawingArea_2.method382(yPos, xPos + class9_1.width / 2, s5, j11, false);
						} else {
							if (s5.contains("\\r")) {
								String text = s5.substring(0, s5.indexOf("\\r"));
								String text2 = s5.substring(s5.indexOf("\\r") + 2);
								textDrawingArea_2.method389(false, xPos + 3, 0, text, j11);
								int rightX = boxWidth + xPos - textDrawingArea_2.getTextWidth(text2) - 2;
								textDrawingArea_2.method389(false, rightX, 0, text2, j11 + 3);
								System.out.println("Box: " + boxWidth + "");
							} else
								textDrawingArea_2.method389(false, xPos + 3, 0, s5, j11 + 3);
						}
					}
				}
		}
		DrawingArea.setDrawingArea(l1, i1, k1, j1);
	}

	public int skillIdForButton(int buttonId) {
		int[] buttonIds = { 4040, 4076, 4112, 4046, 4082, 4118, 4052, 4088, 4124, 4058, 4094, 4130, 4064, 4100, 4136, 4070, 4106, 4142, 4160, 2832, 13917 };
		int[] skillID = { 0, 3, 14, 2, 16, 13, 1, 15, 10, 4, 17, 7, 5, 12, 11, 6, 9, 8, 20, 18, 19, 21, 22, 23, 24 };
		for (int i = 0; i < buttonIds.length; i++) {
			if (buttonIds[i] == buttonId) {
				buttonId = i;
				return skillID[buttonId];
			}
		}
		return 0;
	}

	private void randomizeBackground(Background background) {
		int j = 256;
		for (int k = 0; k < anIntArray1190.length; k++)
			anIntArray1190[k] = 0;

		for (int l = 0; l < 5000; l++) {
			int i1 = (int) (Math.random() * 128D * (double) j);
			anIntArray1190[i1] = (int) (Math.random() * 256D);
		}
		for (int j1 = 0; j1 < 20; j1++) {
			for (int k1 = 1; k1 < j - 1; k1++) {
				for (int i2 = 1; i2 < 127; i2++) {
					int k2 = i2 + (k1 << 7);
					anIntArray1191[k2] = (anIntArray1190[k2 - 1] + anIntArray1190[k2 + 1] + anIntArray1190[k2 - 128] + anIntArray1190[k2 + 128]) / 4;
				}

			}
			int ai[] = anIntArray1190;
			anIntArray1190 = anIntArray1191;
			anIntArray1191 = ai;
		}
		if (background != null) {
			int l1 = 0;
			for (int j2 = 0; j2 < background.anInt1453; j2++) {
				for (int l2 = 0; l2 < background.anInt1452; l2++)
					if (background.aByteArray1450[l1++] != 0) {
						int i3 = l2 + 16 + background.anInt1454;
						int j3 = j2 + 16 + background.anInt1455;
						int k3 = i3 + (j3 << 7);
						anIntArray1190[k3] = 0;
					}
			}
		}
	}

	private void method107(int i, int j, Buffer buffer, Player player) {
		if ((i & 0x400) != 0) {
			player.anInt1543 = buffer.method428();
			player.anInt1545 = buffer.method428();
			player.anInt1544 = buffer.method428();
			player.anInt1546 = buffer.method428();
			player.anInt1547 = buffer.method436() + loopCycle;
			player.anInt1548 = buffer.method435() + loopCycle;
			player.anInt1549 = buffer.method428();
			player.method446();
		}
		if ((i & 0x100) != 0) {
			player.anInt1520 = buffer.method434();
			int k = buffer.readDWord();
			player.anInt1524 = k >> 16;
			player.anInt1523 = loopCycle + (k & 0xffff);
			player.anInt1521 = 0;
			player.anInt1522 = 0;
			if (player.anInt1523 > loopCycle)
				player.anInt1521 = -1;
			if(player.anInt1520 == 65535)
				player.anInt1520 = -1;
			try {
				if (Class36.animationlist[Integer.parseInt(Integer.toHexString(SpotAnimDefinition.cache[player.anInt1520].aAnimation_407.anIntArray353[0]).substring(0, Integer.toHexString(SpotAnimDefinition.cache[player.anInt1520].aAnimation_407.anIntArray353[0]).length() - 4), 16)].length == 0)
					onDemandFetcher.method558(1,Integer.parseInt(Integer.toHexString(SpotAnimDefinition.cache[player.anInt1520].aAnimation_407.anIntArray353[0]).substring(0,Integer.toHexString(SpotAnimDefinition.cache[player.anInt1520].aAnimation_407.anIntArray353[0]).length() - 4),16));
			} catch (Exception e) { }
		}
		if ((i & 8) != 0) {
			int l = buffer.method434();
			if (l == 65535)
				l = -1;
			int i2 = buffer.method427();
			if (l == player.anim && l != -1) {
				int i3 = Animation.anims[l].anInt365;
				if (i3 == 1) {
					player.anInt1527 = 0;
					player.anInt1528 = 0;
					player.anInt1529 = i2;
					player.anInt1530 = 0;
				}
				if (i3 == 2)
					player.anInt1530 = 0;
			} else if (l == -1 || player.anim == -1 || Animation.anims[l].anInt359 >= Animation.anims[player.anim].anInt359) {
				player.anim = l;
				player.anInt1527 = 0;
				player.anInt1528 = 0;
				player.anInt1529 = i2;
				player.anInt1530 = 0;
				player.anInt1542 = player.smallXYIndex;
			}
		}
		if ((i & 4) != 0) {
			player.textSpoken = buffer.readString();
			if (player.textSpoken.charAt(0) == '~') {
				player.textSpoken = player.textSpoken.substring(1);
				pushMessage(player.textSpoken, 2, player.name);
			} else if (player == myPlayer)
				pushMessage(player.textSpoken, 2, player.name);
			player.anInt1513 = 0;
			player.anInt1531 = 0;
			player.textCycle = 150;
		}
		if ((i & 0x80) != 0) {
			int i1 = buffer.method434();
			int j2 = buffer.readUnsignedByte();
			int j3 = buffer.method427();
			int k3 = buffer.currentOffset;
			if (player.name != null && player.visible) {
				long l3 = TextClass.longForName(player.name);
				boolean flag = false;
				if (j2 <= 1) {
					for (int i4 = 0; i4 < ignoreCount; i4++) {
						if (ignoreListAsLongs[i4] != l3)
							continue;
						flag = true;
						break;
					}

				}
				if (!flag && anInt1251 == 0)
					try {
						aStream_834.currentOffset = 0;
						buffer.method442(j3, 0, aStream_834.buffer);
						aStream_834.currentOffset = 0;
						String s = TextInput.method525(j3, aStream_834);
						player.textSpoken = s;
						player.anInt1513 = i1 >> 8;
						player.privelage = j2;
						player.anInt1531 = i1 & 0xff;
						player.textCycle = 150;
						switch (j2) {
						case 1:
							pushMessage(s, 1, "@cr1@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 2:
							pushMessage(s, 1, "@cr2@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 3:
							pushMessage(s, 1, "@cr3@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 4:
							pushMessage(s, 1, "@cr4@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 5:
							pushMessage(s, 1, "@cr5@<col=FF0000>" + loyaltyRank(player.skill) + "</col> " + player.name);
							break;
						case 6:
							pushMessage(s, 1, "@cr6@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 7:
							pushMessage(s, 1, "@cr7@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 8:
							pushMessage(s, 1, "@cr8@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 9:
							pushMessage(s, 1, "@cr9@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						case 10:
							pushMessage(s, 1, "@cr10@<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						default:
							pushMessage(s, 2, "<col=FF0000>" + loyaltyRank(player.skill) + "</col>" + player.name);
							break;
						}
					} catch (Exception exception) {
						Signlink.reporterror("cde2");
					}
			}
			buffer.currentOffset = k3 + j3;
		}
		if ((i & 1) != 0) {
			player.interactingEntity = buffer.method434();
			if (player.interactingEntity == 65535)
				player.interactingEntity = -1;
		}
		if ((i & 0x10) != 0) {
			int j1 = buffer.method427();
			byte abyte0[] = new byte[j1];
			Buffer stream_1 = new Buffer(abyte0);
			buffer.readBytes(j1, 0, abyte0);
			aStreamArray895s[j] = stream_1;
			player.updatePlayer(stream_1);
		}
		if ((i & 2) != 0) {
			player.anInt1538 = buffer.method436();
			player.anInt1539 = buffer.method434();
		}
		if ((i & 0x20) != 0) {
			if (newHitmarkers) {
				int k1 = buffer.readUnsignedByte();
				int k2 = buffer.method426();
				int icon = buffer.readUnsignedByte();
				player.updateHitData(k2, k1, loopCycle, icon);
				player.loopCycleStatus = loopCycle + 300;
				player.currentHealth = buffer.method427();
				player.maxHealth = buffer.readUnsignedByte();
			} else {
				int k1 = buffer.readUnsignedByte();
				int k2 = buffer.method426();
				player.updateHitData(k2, k1, loopCycle);
				player.loopCycleStatus = loopCycle + 300;
				player.currentHealth = buffer.method427();
				player.maxHealth = buffer.readUnsignedByte();
			}
		}
		if ((i & 0x200) != 0) {
			if (newHitmarkers) {
				int l1 = buffer.readUnsignedByte();
				int l2 = buffer.method428();
				int icon = buffer.readUnsignedByte();
				player.updateHitData(l2, l1, loopCycle, icon);
				player.loopCycleStatus = loopCycle + 300;
				player.currentHealth = buffer.readUnsignedByte();
				player.maxHealth = buffer.method427();
			} else {
				int l1 = buffer.readUnsignedByte();
				int l2 = buffer.method428();
				player.updateHitData(l2, l1, loopCycle);
				player.loopCycleStatus = loopCycle + 300;
				player.currentHealth = buffer.readUnsignedByte();
				player.maxHealth = buffer.method427();
			}
		}
	}

	private void method108() {
		try {
			int j = myPlayer.x + anInt1278;
			int k = myPlayer.y + anInt1131;
			if (anInt1014 - j < -500 || anInt1014 - j > 500 || anInt1015 - k < -500 || anInt1015 - k > 500) {
				anInt1014 = j;
				anInt1015 = k;
			}
			if (anInt1014 != j)
				anInt1014 += (j - anInt1014) / 16;
			if (anInt1015 != k)
				anInt1015 += (k - anInt1015) / 16;
			if (super.keyArray[1] == 1)
				anInt1186 += (-24 - anInt1186) / 2;
			else if (super.keyArray[2] == 1)
				anInt1186 += (24 - anInt1186) / 2;
			else
				anInt1186 /= 2;
			if (super.keyArray[3] == 1)
				anInt1187 += (12 - anInt1187) / 2;
			else if (super.keyArray[4] == 1)
				anInt1187 += (-12 - anInt1187) / 2;
			else
				anInt1187 /= 2;
			minimapInt1 = minimapInt1 + anInt1186 / 2 & 0x7ff;
			anInt1184 += anInt1187 / 2;
			if (anInt1184 < 128)
				anInt1184 = 128;
			if (anInt1184 > 383)
				anInt1184 = 383;
			int l = anInt1014 >> 7;
			int i1 = anInt1015 >> 7;
			int j1 = method42(plane, anInt1015, anInt1014);
			int k1 = 0;
			if (l > 3 && i1 > 3 && l < 100 && i1 < 100) {
				for (int l1 = l - 4; l1 <= l + 4; l1++) {
					for (int k2 = i1 - 4; k2 <= i1 + 4; k2++) {
						int l2 = plane;
						if (l2 < 3 && (byteGroundArray[1][l1][k2] & 2) == 2)
							l2++;
						int i3 = j1 - intGroundArray[l2][l1][k2];
						if (i3 > k1)
							k1 = i3;
					}

				}

			}
			anInt1005++;
			if (anInt1005 > 1512) {
				anInt1005 = 0;
				buffer.createFrame(77);
				buffer.writeWordBigEndian(0);
				int i2 = buffer.currentOffset;
				buffer.writeWordBigEndian((int) (Math.random() * 256D));
				buffer.writeWordBigEndian(101);
				buffer.writeWordBigEndian(233);
				buffer.writeWord(45092);
				if ((int) (Math.random() * 2D) == 0)
					buffer.writeWord(35784);
				buffer.writeWordBigEndian((int) (Math.random() * 256D));
				buffer.writeWordBigEndian(64);
				buffer.writeWordBigEndian(38);
				buffer.writeWord((int) (Math.random() * 65536D));
				buffer.writeWord((int) (Math.random() * 65536D));
				buffer.writeBytes(buffer.currentOffset - i2);
			}
			int j2 = k1 * 192;
			if (j2 > 0x17f00)
				j2 = 0x17f00;
			if (j2 < 32768)
				j2 = 32768;
			if (j2 > anInt984) {
				anInt984 += (j2 - anInt984) / 24;
				return;
			}
			if (j2 < anInt984) {
				anInt984 += (j2 - anInt984) / 80;
			}
		} catch (Exception _ex) {
			Signlink.reporterror("glfc_ex " + myPlayer.x + "," + myPlayer.y + "," + anInt1014 + "," + anInt1015 + "," + anInt1069 + "," + anInt1070 + "," + baseX + "," + baseY);
			throw new RuntimeException("eek");
		}
	}

	public void processDrawing() {
		if (rsAlreadyLoaded || loadingError || genericLoadingError) {
			showErrorScreen();
			return;
		}
		if (!loggedIn)
			loginRenderer.displayLoginScreen();
		else
			drawGameScreen();
		anInt1213 = 0;
	}

	private boolean isFriendOrSelf(String s) {
		if (s == null)
			return false;
		for (int i = 0; i < friendsCount; i++)
			if (s.equalsIgnoreCase(friendsList[i]))
				return true;
		return s.equalsIgnoreCase(myPlayer.name);
	}

	private static String combatDiffColor(int i, int j) {
		int k = i - j;
		if (k < -9)
			return "@red@";
		if (k < -6)
			return "@or3@";
		if (k < -3)
			return "@or2@";
		if (k < 0)
			return "@or1@";
		if (k > 9)
			return "@gre@";
		if (k > 6)
			return "@gr3@";
		if (k > 3)
			return "@gr2@";
		if (k > 0)
			return "@gr1@";
		else
			return "@yel@";
	}

	public void consoleCommands(String cmd) {
		if (cmd.equalsIgnoreCase("clc")) {
			for (int j = 0; j < 17; j++)
				consoleMessages[j] = null;
		}
		if (cmd.equalsIgnoreCase("clcb")) {
			for (int index = 0; index < 500; index++) {
				chatMessages[index] = null;
			}
		}
		if (cmd.equalsIgnoreCase("data")) {
			clientData = !clientData;
		}
		if (cmd.equalsIgnoreCase("fps")) {
			fpsOn = !fpsOn;
		}
	}

	int messager = 1;

	private void drawConsole() {
		if (consoleOpen) {
			drawConsoleMessage(messager);
			messager++;
			consoleAlpha += consoleAlpha < 100 ? 10 : 0;
			int height = isFixed() ? 334 : clientHeight / 2;
			DrawingArea.drawAlphaFilledPixels(0, 0, clientWidth, height, 5320850, consoleAlpha < 101 ? consoleAlpha : 100);
			DrawingArea.drawPixels(1, height - 19, 0, 16777215, clientWidth);
			newBoldFont.drawBasicString("-->", 11, height - 6, 16777215, 0);
			if (loopCycle % 20 < 10) {
				newBoldFont.drawBasicString(consoleInput + "|", 38, height - 6, 16777215, 0);
				return;
			} else {
				newBoldFont.drawBasicString(consoleInput, 38, height - 6, 16777215, 0);
				return;
			}
		} else {
			consoleAlpha -= consoleAlpha > 0 ? 10 : 0;
			consoleAlpha = consoleAlpha < 0 ? 0 : consoleAlpha;
			if (consoleAlpha > 0) {
				int height = isFixed() ? 334 : clientHeight / 2;
				DrawingArea.drawAlphaFilledPixels(0, 0, clientWidth, height, 5320850, consoleAlpha);
			}
		}
	}

	private void drawConsoleArea() {
		if (consoleOpen) {
			for (int index = 0, positionY = isFixed() ? 308 : (clientHeight / 2) - 26; index < 17; index++, positionY -= 18) {
				if (consoleMessages[index] != null) {
					newRegularFont.drawBasicString(consoleMessages[index], 9, positionY, 16777215, 0);
				}
			}
		}
	}

	public void consoleMessage(String s, boolean response) {
		if (backDialogID == -1)
			inputTaken = true;
		for (int index = 16; index > 0; index--) {
			consoleMessages[index] = consoleMessages[index - 1];
		}
		if (response) {
			consoleMessages[0] = "--> " + s;
		} else {
			consoleMessages[0] = s;
		}
	}

	public void drawConsoleMessage(int Message) {
		if (Message == 1) {
			printConsoleMessage("This is the developer console. To close, press the ` key on your keyboard.", 1);
			printConsoleMessage("Type 'commands' for a list of commands.", 1);
			return;
		}

	}

	public void sendCommandPacket(String cmd) {
		consoleCommands(cmd);
		buffer.createFrame(103);
		buffer.writeWordBigEndian(cmd.length() + 1);
		buffer.writeString(cmd);
	}

	public void printConsoleMessage(String s, int i) {
		if (backDialogID == -1)
			inputTaken = true;
		for (int j = 16; j > 0; j--) {
			consoleMessages[j] = consoleMessages[j - 1];
		}
		Calendar Calendar = new GregorianCalendar();
		int hour = Calendar.get(java.util.Calendar.HOUR_OF_DAY);
		int minute = Calendar.get(java.util.Calendar.MINUTE);
		int second = Calendar.get(java.util.Calendar.SECOND);
		if (i == 0)
			consoleMessages[0] = "--> " + hour + ":" + minute + ":" + second + ": " + s + "";
		else
			consoleMessages[0] = "" + hour + ":" + minute + ":" + second + ": " + s + "";
	}

	public static boolean isFixed() {
		return clientSize == 0;
	}

	public static boolean isResize() {
		return clientSize == 1;
	}

	public static boolean isFull() {
		return clientSize == 2;
	}

	private void setWaveVolume(int i) {
		Signlink.wavevol = i;
	}

	private void draw3dScreen() {
		int offsetY = (clientSize == 0 ? 0 : clientHeight - 498);
		alertHandler.processAlerts();
		if (counterOn)
			displayXPCounter();
		if (showChat)
			drawSplitPrivateChat();
		drawConsoleArea();
		drawConsole();
		SkillOrbHandler.drawOrbs();
		if (crossType == 1) {
			crosses[crossIndex / 100].drawSprite(crossX - 8 - 4, crossY - 8 - 4);
			anInt1142++;
			if (anInt1142 > 67) {
				anInt1142 = 0;
				buffer.createFrame(78);
			}
		}
		if (crossType == 2)
			crosses[4 + crossIndex / 100].drawSprite(crossX - 8 - 4, crossY - 8 - 4);
		if(anInt1018 != -1) {
			method119(anInt945, anInt1018);
			if (isFixed())
				drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 0);
			if (anInt1018 == 197 && !isFixed()) {
				skullIcons[0].drawSprite(clientWidth - 95, 168);
				String text = RSInterface.interfaceCache[199].message.replace("@yel@", "");
				newRegularFont.drawCenteredString(text, clientWidth - 82, 208, 0xE1981D, 0);
			}
			else {
				if (clientWidth >= 777 && clientHeight >= 565) {
					drawInterface(0, (clientWidth / 2) - 356, RSInterface.interfaceCache[anInt1018], isFixed() ? 0 : (clientHeight / 2) - 230);
				} else {
					drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 0);
				}
			}
		}
		if (anInt1018 == 201) {
			if (anInt1018 == 201 && isFixed()) {
				duelArena.drawSprite(472, 296);
			} else {
				duelArena.drawSprite(clientWidth - 40, 176);
			}
		}
		if (anInt1018 == 21200) {
			if (anInt1018 == 21200 && isFixed()) {
				notInWild.drawSprite(450, 290);
			} else {
				notInWild.drawSprite(clientWidth - 40, 176);
				String text = RSInterface.interfaceCache[21302].message.replace("@yel@", "");
				newRegularFont.drawCenteredString(text, clientWidth - 82, 208, 0xE1981D, 0);
			}
		}
		if (anInt1018 == 21300) {
			if (anInt1018 == 21300 && isFixed()) {
				inWild.drawSprite(450, 290);
			} else {
				inWild.drawSprite(clientWidth - 40, 176);
				String text = RSInterface.interfaceCache[21302].message.replace("@yel@", "");
				newRegularFont.drawCenteredString(text, clientWidth - 82, 208, 0xE1981D, 0);
			}
		}
		if (anInt1018 == 21400) {
			if (anInt1018 == 21400 && isFixed()) {
				inTimer.drawSprite(450, 290);
			} else {
				inTimer.drawSprite(clientWidth - 40, 176);
				String text = RSInterface.interfaceCache[21403].message.replace("@whi@", "");
				newRegularFont.drawCenteredString(text, clientWidth - 40, 176, 0xE1981D, 0);

				String text2 = RSInterface.interfaceCache[21302].message.replace("@yel@", "");
				newRegularFont.drawCenteredString(text2, clientWidth - 82, 208, 0xE1981D, 0);
			}
		}
		if (anInt1018 == 21119 || anInt1018 == 21100) {
			method119(anInt945, anInt1018);
			drawInterface(0, 0, RSInterface.interfaceCache[anInt1018], 0);
		}
		if (openInterfaceID != -1) {
			method119(anInt945, openInterfaceID);
			if(clientSize == 0) {
				drawInterface(0, 0, RSInterface.interfaceCache[openInterfaceID], 0);
			} else {
				if(clientWidth >= 777 && clientHeight >= 565) {
					drawInterface(0, clientSize == 0 ? 0 : (clientWidth / 2) - 356, RSInterface.interfaceCache[openInterfaceID], clientSize == 0 ? 0 : (clientHeight / 2) - 230);
			} else {
					drawInterface(0, 0, RSInterface.interfaceCache[openInterfaceID], 0);
				}
			}
		}
		method70();
		if (!menuOpen) {
			processRightClick();
			if (enableHoverBox)
				drawTooltip2();
			else
				drawTooltip();
		} else if (menuScreenArea == 0) {
			if (isFixed()) {
				drawMenu(4, 4);
			} else {
				drawMenu();
			}
		}
		if (anInt1055 == 1) {
			if (isFixed()) {
				multiOverlay.drawSprite(472, 296);
			} else {
				imageLoader[76].drawSprite(clientWidth - 40, 176);
			}
		}
		if (fpsOn) {
			char c = '\u01FB';
			int k = 20;
			int i1 = 0xffff00;
			if (super.fps < 15)
				i1 = 0xff0000;
			aTextDrawingArea_1271.method380("Fps:" + super.fps, c, i1, k);
			k += 15;
			Runtime runtime = Runtime.getRuntime();
			int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			i1 = 0xffff00;
			if (j1 > 0x2000000 && lowMem)
				i1 = 0xff0000;
			aTextDrawingArea_1271.method380("Mem:" + j1 + "k", c, 0xffff00, k);
			k += 15;
			aTextDrawingArea_1271.method385(0xffff00, "Mouse X: " + super.mouseX + " , Mouse Y: " + super.mouseY, 314, 5);
		}
		int x = baseX + (myPlayer.x - 6 >> 7);
		int y = baseY + (myPlayer.y - 6 >> 7);
		if (clientData) {
			aTextDrawingArea_1271.method385(0xffff00, "Fps: " + super.fps, clientHeight - 257, 5);
			Runtime runtime = Runtime.getRuntime();
			int j1 = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
			aTextDrawingArea_1271.method385(0xffff00, "Mem: " + j1 + "k", clientHeight - 243, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Mouse X: " + super.mouseX + " , Mouse Y: " + super.mouseY, clientHeight - 229, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Coords: " + x + ", " + y, clientHeight - 215, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Client resolution: " + clientWidth + "x" + clientHeight, clientHeight - 201, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Object Maps: " + objectMaps + ";", clientHeight - 187, 5);
			aTextDrawingArea_1271.method385(0xffff00, "Floor Maps: " + floorMaps + ";", clientHeight - 173, 5);

		}
		if (systemUpdateTimer != 0) {
			int timerAsSeconds = systemUpdateTimer / 50;
			int timerAsMinutes = timerAsSeconds / 60;
			timerAsSeconds %= 60;
			if (timerAsSeconds < 10)
				aTextDrawingArea_1271.method385(0xFCFC03, "Mistex update in: " + timerAsMinutes + ":0" + timerAsSeconds, 329 + offsetY, 4);
			else
				aTextDrawingArea_1271.method385(0xFCFC03, "Mistex update in: " + timerAsMinutes + ":" + timerAsSeconds, 329 + offsetY, 4);
			anInt849++;
			if (anInt849 > 75) {
				anInt849 = 0;
				buffer.createFrame(148);
			}
		}
	}

	private void addIgnore(long l) {
		try {
			if (l == 0L)
				return;
			if (ignoreCount >= 100) {
				pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
				return;
			}
			String s = TextClass.fixName(TextClass.nameForLong(l));
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					pushMessage(s + " is already on your ignore list", 0, "");
					return;
				}
			for (int k = 0; k < friendsCount; k++)
				if (friendsListAsLongs[k] == l) {
					pushMessage("Please remove " + s + " from your friend list first", 0, "");
					return;
				}

			ignoreListAsLongs[ignoreCount++] = l;
			buffer.createFrame(133);
			buffer.writeQWord(l);
			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("45688, " + l + ", " + 4 + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	public void drawXPCounter() {
		imageLoader[19].drawSprite(clientSize == 0 ? 0 : clientWidth - 210, clientSize == 0 ? 48 : 4);
		if (hoverPos == 0) {
			imageLoader[20].drawSprite(clientSize == 0 ? 0 : clientWidth - 210, clientSize == 0 ? 48 : 4);
		}
	}

	private void method114() {
		for (int i = -1; i < playerCount; i++) {
			int j;
			if (i == -1)
				j = myPlayerIndex;
			else
				j = playerIndices[i];
			Player player = playerArray[j];
			if (player != null)
				method96(player);
		}
	}

	private void method115() {
		if (loadingStage == 2) {
			for (Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
				if (class30_sub1.anInt1294 > 0)
					class30_sub1.anInt1294--;
				if (class30_sub1.anInt1294 == 0) {
					if (class30_sub1.anInt1299 < 0 || ObjectManager.method178(class30_sub1.anInt1299, class30_sub1.anInt1301)) {
						method142(class30_sub1.anInt1298, class30_sub1.anInt1295, class30_sub1.anInt1300, class30_sub1.anInt1301, class30_sub1.anInt1297, class30_sub1.anInt1296, class30_sub1.anInt1299);
						class30_sub1.unlink();
					}
				} else {
					if (class30_sub1.anInt1302 > 0)
						class30_sub1.anInt1302--;
					if (class30_sub1.anInt1302 == 0 && class30_sub1.anInt1297 >= 1 && class30_sub1.anInt1298 >= 1 && class30_sub1.anInt1297 <= 102 && class30_sub1.anInt1298 <= 102 && (class30_sub1.anInt1291 < 0 || ObjectManager.method178(class30_sub1.anInt1291, class30_sub1.anInt1293))) {
						method142(class30_sub1.anInt1298, class30_sub1.anInt1295, class30_sub1.anInt1292, class30_sub1.anInt1293, class30_sub1.anInt1297, class30_sub1.anInt1296, class30_sub1.anInt1291);
						class30_sub1.anInt1302 = -1;
						if (class30_sub1.anInt1291 == class30_sub1.anInt1299 && class30_sub1.anInt1299 == -1)
							class30_sub1.unlink();
						else if (class30_sub1.anInt1291 == class30_sub1.anInt1299 && class30_sub1.anInt1292 == class30_sub1.anInt1300 && class30_sub1.anInt1293 == class30_sub1.anInt1301)
							class30_sub1.unlink();
					}
				}
			}

		}
	}

	private void determineMenuSize() {
		if (isFixed()) {
			int boxLength = chatTextDrawingArea.getTextWidth("Choose option");
			for (int row = 0; row < menuActionRow; row++) {
				int actionLength = chatTextDrawingArea.getTextWidth(menuActionName[row]);
				if (actionLength > boxLength)
					boxLength = actionLength;
			}
			boxLength += 8;
			int offset = 15 * menuActionRow + 21;
			if (super.saveClickX > 0 && super.saveClickY > 0 && super.saveClickX < 765 && super.saveClickY < 503) {
				int xClick = super.saveClickX - boxLength / 2;
				if (xClick + boxLength > 761) {
					xClick = 761 - boxLength;
				}
				if (xClick < 0) {
					xClick = 0;
				}
				int yClick = super.saveClickY - 0;
				if (yClick + offset > 497) {
					yClick = 497 - offset;
				}
				if (yClick < 0) {
					yClick = 0;
				}
				menuOpen = true;
				menuOffsetX = xClick;
				menuOffsetY = yClick;
				menuWidth = boxLength;
				menuHeight = 15 * menuActionRow + 22;
			}
		} else {
			int i = boldFont.getTextWidth("Choose Option");
			for (int j = 0; j < menuActionRow; j++) {
				int k = boldFont.getTextWidth(menuActionName[j]);
				if (k > i)
					i = k;
			}
			i += 8;
			int l = 15 * menuActionRow + 21;
			if (clientSize == 0) {
				if (super.saveClickX > 4 && super.saveClickY > 4 && super.saveClickX < 516 && super.saveClickY < 338) {
					int i1 = super.saveClickX - 4 - i / 2;
					if (i1 + i > 512)
						i1 = 512 - i;
					if (i1 < 0)
						i1 = 0;
					int l1 = super.saveClickY - 4;
					if (l1 + l > 334)
						l1 = 334 - l;
					if (l1 < 0)
						l1 = 0;
					menuOpen = true;
					menuScreenArea = 0;
					menuOffsetX = i1;
					menuOffsetY = l1;
					menuWidth = i;
					menuHeight = 15 * menuActionRow + 22;
				}
				if (super.saveClickX > 519 && super.saveClickY > 168 && super.saveClickX < 765 && super.saveClickY < 503) {
					int j1 = super.saveClickX - 519 - i / 2;
					if (j1 < 0)
						j1 = 0;
					else if (j1 + i > 245)
						j1 = 245 - i;
					int i2 = super.saveClickY - 168;
					if (i2 < 0)
						i2 = 0;
					else if (i2 + l > 333)
						i2 = 333 - l;
					menuOpen = true;
					menuScreenArea = 1;
					menuOffsetX = j1;
					menuOffsetY = i2;
					menuWidth = i;
					menuHeight = 15 * menuActionRow + 22;
				}
				if (super.saveClickX > 0 && super.saveClickY > 338 && super.saveClickX < 516 && super.saveClickY < 503) {
					int k1 = super.saveClickX - 0 - i / 2;
					if (k1 < 0)
						k1 = 0;
					else if (k1 + i > 516)
						k1 = 516 - i;
					int j2 = super.saveClickY - 338;
					if (j2 < 0)
						j2 = 0;
					else if (j2 + l > 165)
						j2 = 165 - l;
					menuOpen = true;
					menuScreenArea = 2;
					menuOffsetX = k1;
					menuOffsetY = j2;
					menuWidth = i;
					menuHeight = 15 * menuActionRow + 22;
				}
				if (super.saveClickX > 519 && super.saveClickY > 0 && super.saveClickX < 765 && super.saveClickY < 168) {
					int j1 = super.saveClickX - 519 - i / 2;
					if (j1 < 0)
						j1 = 0;
					else if (j1 + i > 245)
						j1 = 245 - i;
					int i2 = super.saveClickY - 0;
					if (i2 < 0)
						i2 = 0;
					else if (i2 + l > 168)
						i2 = 168 - l;
					menuOpen = true;
					menuScreenArea = 3;
					menuOffsetX = j1;
					menuOffsetY = i2;
					menuWidth = i;
					menuHeight = 15 * menuActionRow + 22;
				}
			} else {
				if (super.saveClickX > 0 && super.saveClickY > 0 && super.saveClickX < clientWidth && super.saveClickY < clientHeight) {
					int i1 = super.saveClickX - 0 - i / 2;
					if (i1 + i > clientWidth)
						i1 = clientWidth - i;
					if (i1 < 0)
						i1 = 0;
					int l1 = super.saveClickY - 0;
					if (l1 + l > clientHeight)
						l1 = clientHeight - l;
					if (l1 < 0)
						l1 = 0;
					menuOpen = true;
					menuScreenArea = 0;
					menuOffsetX = i1;
					menuOffsetY = l1;
					menuWidth = i;
					menuHeight = 15 * menuActionRow + 22;
				}
			}
		}
	}

	private void method117(Buffer buffer) {
		buffer.initBitAccess();
		int j = buffer.readBits(1);
		if (j == 0)
			return;
		int k = buffer.readBits(2);
		if (k == 0) {
			anIntArray894[anInt893++] = myPlayerIndex;
			return;
		}
		if (k == 1) {
			int l = buffer.readBits(3);
			myPlayer.moveInDir(false, l);
			int k1 = buffer.readBits(1);
			if (k1 == 1)
				anIntArray894[anInt893++] = myPlayerIndex;
			return;
		}
		if (k == 2) {
			int i1 = buffer.readBits(3);
			myPlayer.moveInDir(true, i1);
			int l1 = buffer.readBits(3);
			myPlayer.moveInDir(true, l1);
			int j2 = buffer.readBits(1);
			if (j2 == 1)
				anIntArray894[anInt893++] = myPlayerIndex;
			return;
		}
		if (k == 3) {
			plane = buffer.readBits(2);
			int j1 = buffer.readBits(1);
			int i2 = buffer.readBits(1);
			if (i2 == 1)
				anIntArray894[anInt893++] = myPlayerIndex;
			int k2 = buffer.readBits(7);
			int l2 = buffer.readBits(7);
			myPlayer.setPos(l2, k2, j1 == 1);
		}
	}

	private void nullLoader() {
		aBoolean831 = false;
		while (drawingFlames) {
			aBoolean831 = false;
			try {
				Thread.sleep(50L);
			} catch (Exception _ex) {
			}
		}
		aBackground_966 = null;
		aBackground_967 = null;
		aBackgroundArray1152s = null;
		anIntArray850 = null;
		anIntArray851 = null;
		anIntArray852 = null;
		anIntArray853 = null;
		anIntArray1190 = null;
		anIntArray1191 = null;
		anIntArray828 = null;
		anIntArray829 = null;
		aClass30_Sub2_Sub1_Sub1_1201 = null;
		aClass30_Sub2_Sub1_Sub1_1202 = null;
	}

	private boolean method119(int i, int j) {
		boolean flag1 = false;
		RSInterface class9 = RSInterface.interfaceCache[j];
		for (int k = 0; k < class9.children.length; k++) {
			if (class9.children[k] == -1)
				break;
			RSInterface class9_1 = RSInterface.interfaceCache[class9.children[k]];
			if (class9_1.type == 1)
				flag1 |= method119(i, class9_1.id);
			if (class9_1.type == 6 && (class9_1.anInt257 != -1 || class9_1.anInt258 != -1)) {
				boolean flag2 = interfaceIsSelected(class9_1);
				int l;
				if (flag2)
					l = class9_1.anInt258;
				else
					l = class9_1.anInt257;
				if (l != -1) {
					Animation animation = Animation.anims[l];
					for (class9_1.anInt208 += i; class9_1.anInt208 > animation.method258(class9_1.anInt246);) {
						class9_1.anInt208 -= animation.method258(class9_1.anInt246) + 1;
						class9_1.anInt246++;
						if (class9_1.anInt246 >= animation.anInt352) {
							class9_1.anInt246 -= animation.anInt356;
							if (class9_1.anInt246 < 0 || class9_1.anInt246 >= animation.anInt352)
								class9_1.anInt246 = 0;
						}
						flag1 = true;
					}

				}
			}
		}

		return flag1;
	}

	private int method120() {
		int j = 3;
		if (yCameraCurve < 310) {
			int k = xCameraPos >> 7;
			int l = yCameraPos >> 7;
			int i1 = myPlayer.x >> 7;
			int j1 = myPlayer.y >> 7;
			if ((byteGroundArray[plane][k][l] & 4) != 0)
				j = plane;
			int k1;
			if (i1 > k)
				k1 = i1 - k;
			else
				k1 = k - i1;
			int l1;
			if (j1 > l)
				l1 = j1 - l;
			else
				l1 = l - j1;
			if (k1 > l1) {
				int i2 = (l1 * 0x10000) / k1;
				int k2 = 32768;
				while (k != i1) {
					if (k < i1)
						k++;
					else if (k > i1)
						k--;
					if ((byteGroundArray[plane][k][l] & 4) != 0)
						j = plane;
					k2 += i2;
					if (k2 >= 0x10000) {
						k2 -= 0x10000;
						if (l < j1)
							l++;
						else if (l > j1)
							l--;
						if ((byteGroundArray[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			} else {
				int j2 = (k1 * 0x10000) / l1;
				int l2 = 32768;
				while (l != j1) {
					if (l < j1)
						l++;
					else if (l > j1)
						l--;
					if ((byteGroundArray[plane][k][l] & 4) != 0)
						j = plane;
					l2 += j2;
					if (l2 >= 0x10000) {
						l2 -= 0x10000;
						if (k < i1)
							k++;
						else if (k > i1)
							k--;
						if ((byteGroundArray[plane][k][l] & 4) != 0)
							j = plane;
					}
				}
			}
		}
		if ((byteGroundArray[plane][myPlayer.x >> 7][myPlayer.y >> 7] & 4) != 0)
			j = plane;
		return j;
	}

	private int method121() {
		int j = method42(plane, yCameraPos, xCameraPos);
		if (j - zCameraPos < 800 && (byteGroundArray[plane][xCameraPos >> 7][yCameraPos >> 7] & 4) != 0)
			return plane;
		else
			return 3;
	}

	private void delIgnore(long l) {
		try {
			if (l == 0L)
				return;
			for (int j = 0; j < ignoreCount; j++)
				if (ignoreListAsLongs[j] == l) {
					ignoreCount--;
					System.arraycopy(ignoreListAsLongs, j + 1, ignoreListAsLongs, j, ignoreCount - j);

					buffer.createFrame(74);
					buffer.writeQWord(l);
					return;
				}

			return;
		} catch (RuntimeException runtimeexception) {
			Signlink.reporterror("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
		}
		throw new RuntimeException();
	}

	private void clanBox(String input, int type) {
		try {
			switch (type) {
			case 0:
				buffer.createFrame(60);
				break;
			case 1:
				buffer.createFrame(63);
				break;
			case 2:
				buffer.createFrame(64);
				break;
			}
			buffer.writeWordBigEndian(0);
			int initialOffset = buffer.currentOffset;
			TextInput.method526(input, buffer);
			buffer.writeBytes(buffer.currentOffset - initialOffset);
			return;
		} catch (RuntimeException runtimeexception) {
		}
		throw new RuntimeException();
	}

	public String getParameter(String s) {
		if (Signlink.mainapp != null)
			return Signlink.mainapp.getParameter(s);
		else
			return super.getParameter(s);
	}

	private void adjustVolume(boolean flag, int i) {
		Signlink.midivol = i;
		if (flag)
			Signlink.midi = "voladjust";
	}

	private int extractInterfaceValues(RSInterface class9, int j) {
		if (class9.valueIndexArray == null || j >= class9.valueIndexArray.length)
			return -2;
		try {
			int ai[] = class9.valueIndexArray[j];
			int k = 0;
			int l = 0;
			int i1 = 0;
			do {
				int j1 = ai[l++];
				int k1 = 0;
				byte byte0 = 0;
				if (j1 == 0)
					return k;
				if (j1 == 1)
					k1 = currentStats[ai[l++]];
				if (j1 == 2)
					k1 = maxStats[ai[l++]];
				if (j1 == 3)
					k1 = currentExp[ai[l++]];
				if (j1 == 4) {
					RSInterface class9_1 = RSInterface.interfaceCache[ai[l++]];
					int k2 = ai[l++];
					if (k2 >= 0 && k2 < ItemDefinition.totalItems && (!ItemDefinition.forID(k2).membersObject || isMembers)) {
						for (int j3 = 0; j3 < class9_1.inv.length; j3++)
							if (class9_1.inv[j3] == k2 + 1)
								k1 += class9_1.invStackSizes[j3];

					}
				}
				if (j1 == 5)
					k1 = variousSettings[ai[l++]];
				if (j1 == 6)
					k1 = anIntArray1019[maxStats[ai[l++]] - 1];
				if (j1 == 7)
					k1 = (variousSettings[ai[l++]] * 100) / 46875;
				if (j1 == 8)
					k1 = myPlayer.combatLevel;
				if (j1 == 9) {
					for (int l1 = 0; l1 < SkillConstants.skillsCount; l1++)
						if (SkillConstants.skillEnabled[l1])
							k1 += maxStats[l1];

				}
				if (j1 == 10) {
					RSInterface class9_2 = RSInterface.interfaceCache[ai[l++]];
					int l2 = ai[l++] + 1;
					if (l2 >= 0 && l2 < ItemDefinition.totalItems && (!ItemDefinition.forID(l2).membersObject || isMembers)) {
						for (int k3 = 0; k3 < class9_2.inv.length; k3++) {
							if (class9_2.inv[k3] != l2)
								continue;
							k1 = 0x3b9ac9ff;
							break;
						}

					}
				}
				if (j1 == 11)
					k1 = energy;
				if (j1 == 12)
					k1 = weight;
				if (j1 == 13) {
					int i2 = variousSettings[ai[l++]];
					int i3 = ai[l++];
					k1 = (i2 & 1 << i3) == 0 ? 0 : 1;
				}
				if (j1 == 14) {
					int j2 = ai[l++];
					VarBit varBit = VarBit.cache[j2];
					int l3 = varBit.anInt648;
					int i4 = varBit.anInt649;
					int j4 = varBit.anInt650;
					int k4 = anIntArray1232[j4 - i4];
					k1 = variousSettings[l3] >> i4 & k4;
				}
				if (j1 == 15)
					byte0 = 1;
				if (j1 == 16)
					byte0 = 2;
				if (j1 == 17)
					byte0 = 3;
				if (j1 == 18)
					k1 = (myPlayer.x >> 7) + baseX;
				if (j1 == 19)
					k1 = (myPlayer.y >> 7) + baseY;
				if (j1 == 20)
					k1 = ai[l++];
				if (byte0 == 0) {
					if (i1 == 0)
						k += k1;
					if (i1 == 1)
						k -= k1;
					if (i1 == 2 && k1 != 0)
						k /= k1;
					if (i1 == 3)
						k *= k1;
					i1 = 0;
				} else {
					i1 = byte0;
				}
			} while (true);
		} catch (Exception _ex) {
			return -1;
		}
	}

	public void detectCursor(int menuAction) {
		boolean hasFoundCursor = false;
		for (int i2 = 0; i2 < cursorInfo.length; i2++) {
			if (menuActionName[menuAction].startsWith(cursorInfo[i2])) {
				hasFoundCursor = true;
				super.setCursor(i2);
			}
		}
		if (!hasFoundCursor)
			super.setCursor(0);
	}

	private void drawTooltip2() {
		if (newCursors) {
			if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0)
				return;
			String s;
			String s2;
			if (itemSelected == 1 && menuActionRow < 2) {
				s = "@whi@Use " + selectedItemName + " with...";
			} else if (spellSelected == 1 && menuActionRow < 2) {
				s = spellTooltip + "...";
			} else {
				s = menuActionName[menuActionRow - 1];
			}
			s2 = s;
			if (menuActionRow > 2)
				s = s + "@whi@ / " + (menuActionRow - 2) + " more options";
			boldFont.method390(4, 0xffffff, s, loopCycle / 1000, 15);
			if (!s2.contains("Walk here")) {
				drawHoverBox2(super.mouseX + 10, super.mouseY - 10, 0xFFFFF0, 0, 100, s2);
			}
			boolean hasFoundCursor = false;
			for (int i1 = 0; i1 < cursorInfo.length; i1++) {
				if (menuActionName[menuActionRow - 1].startsWith(cursorInfo[i1])) {
					super.setCursor(i1);
					hasFoundCursor = true;
				}
			}
			if (!hasFoundCursor)
				super.setCursor(0);
		} else {
			if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0)
				return;
			String s;
			String s2;
			if (itemSelected == 1 && menuActionRow < 2) {
				s = "@whi@Use " + selectedItemName + " with...";
			} else if (spellSelected == 1 && menuActionRow < 2) {
				s = spellTooltip + "...";
			} else {
				s = menuActionName[menuActionRow - 1];
			}
			s2 = s;
			if (menuActionRow > 2)
				s = s + "@whi@ / " + (menuActionRow - 2) + " more options";
			boldFont.method390(4, 0xffffff, s, loopCycle / 1000, 15);
			if (!s2.contains("Walk here")) {
				drawHoverBox2(super.mouseX + 10, super.mouseY - 10, 0xFFFFF0, 0, 100, s2);
			}
		}
	}

	private void drawTooltip() {
		if (newCursors) {
			if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0)
				return;
			String s;
			if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
				super.setCursor(0);
				return;
			}
			if (itemSelected == 1 && menuActionRow < 2)
				s = "Use " + selectedItemName + " with...";
			else if (spellSelected == 1 && menuActionRow < 2)
				s = spellTooltip + "...";
			else
				s = menuActionName[menuActionRow - 1];
			if (menuActionRow > 2)
				s = s + "@whi@ / " + (menuActionRow - 2) + " more options";
			newBoldFont.drawBasicString(s, 4, 15, 0xffffff, 1);
			// chatTextDrawingArea.method390(4, 0xffffff, s, loopCycle / 1000,
			// 15);
			boolean hasFoundCursor = false;
			for (int i1 = 0; i1 < cursorInfo.length; i1++) {
				if (menuActionName[menuActionRow - 1].startsWith(cursorInfo[i1])) {
					super.setCursor(i1);
					hasFoundCursor = true;
				}
			}
			if (!hasFoundCursor)
				super.setCursor(0);
		} else if (newCursors == false) {
			String s;
			if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0)
				return;
			if (itemSelected == 1 && menuActionRow < 2)
				s = "Use " + selectedItemName + " with...";
			else if (spellSelected == 1 && menuActionRow < 2)
				s = spellTooltip + "...";
			else
				s = menuActionName[menuActionRow - 1];
			if (menuActionRow > 2)
				s = s + "@whi@ / " + (menuActionRow - 2) + " more options";
			newBoldFont.drawBasicString(s, 4, 15, 0xffffff, 1);
		}

	}

	private void drawMinimap() {
		int xPosOffset = clientSize == 0 ? 0 : clientWidth - 246;
		if (clientSize == 0)
			aRSImageProducer_1164.setCanvas();
		if (mapState == 2) {
			imageLoader[59].drawSprite((clientSize == 0 ? 32 : clientWidth - 159), (clientSize == 0 ? 9 : 5));
			if (clientSize == 0) {
				imageLoader[1].drawSprite(0 + xPosOffset, 0);
			} else {
				imageLoader[28].drawSprite(clientWidth - 167, 0);
				imageLoader[58].drawSprite(clientWidth - 172, 0);
			}
			compass.method352(33, minimapInt1, anIntArray1057, 256, anIntArray968, 25, (clientSize == 0 ? 8 : 5), (clientSize == 0 ? 11 + xPosOffset : clientWidth - 167), 33, 25);
			if (isFixed()) {
				if (menuOpen)
					drawMenu(516, 0);
			} else {
				if (menuOpen && menuScreenArea == 3)
					drawMenu();
			}
			aRSImageProducer_1165.setCanvas();
			return;
		}
		int i = minimapInt1 + minimapInt2 & 0x7ff;
		int j = 48 + myPlayer.x / 32;
		int l2 = 464 - myPlayer.y / 32;
		aClass30_Sub2_Sub1_Sub1_1263.method352(152, i, anIntArray1229, 256 + minimapInt3, anIntArray1052, l2, (clientSize == 0 ? 9 : 5), (clientSize == 0 ? 35 : clientWidth - 162), 146, j);
		compass.method352(33, minimapInt1, anIntArray1057, 256, anIntArray968, 25, (clientSize == 0 ? 8 : 5), (clientSize == 0 ? 11 + xPosOffset : clientWidth - 167), 33, 25);
		for (int j5 = 0; j5 < anInt1071; j5++) {
			int k = (anIntArray1072[j5] * 4 + 2) - myPlayer.x / 32;
			int i3 = (anIntArray1073[j5] * 4 + 2) - myPlayer.y / 32;
			markMinimap(aClass30_Sub2_Sub1_Sub1Array1140[j5], k, i3);
		}
		for (int k5 = 0; k5 < 104; k5++) {
			for (int l5 = 0; l5 < 104; l5++) {
				NodeList class19 = groundArray[plane][k5][l5];
				if (class19 != null) {
					int l = (k5 * 4 + 2) - myPlayer.x / 32;
					int j3 = (l5 * 4 + 2) - myPlayer.y / 32;
					markMinimap(mapDotItem, l, j3);
				}
			}
		}
		for (int i6 = 0; i6 < npcCount; i6++) {
			Npc npc = npcArray[npcIndices[i6]];
			if (npc != null && npc.isVisible()) {
				EntityDefinition entityDefinition = npc.desc;
				if (entityDefinition.childrenIDs != null)
					entityDefinition = entityDefinition.method161();
				if (entityDefinition != null && entityDefinition.aBoolean87 && entityDefinition.aBoolean84) {
					int i1 = npc.x / 32 - myPlayer.x / 32;
					int k3 = npc.y / 32 - myPlayer.y / 32;
					markMinimap(mapDotNPC, i1, k3);
				}
			}
		}
		for (int j6 = 0; j6 < playerCount; j6++) {
			Player player = playerArray[playerIndices[j6]];
			if (player != null && player.isVisible()) {
				int j1 = player.x / 32 - myPlayer.x / 32;
				int l3 = player.y / 32 - myPlayer.y / 32;
				boolean flag1 = false;
				boolean flag3 = false;
				for (int j3 = 0; j3 < clanList.length; j3++) {
					if (clanList[j3] == null)
						continue;
					if (!clanList[j3].equalsIgnoreCase(player.name))
						continue;
					flag3 = true;
					break;
				}
				long l6 = TextClass.longForName(player.name);
				for (int k6 = 0; k6 < friendsCount; k6++) {
					if (l6 != friendsListAsLongs[k6] || friendsNodeIDs[k6] == 0)
						continue;
					flag1 = true;
					break;
				}
				boolean flag2 = false;
				if (myPlayer.team != 0 && player.team != 0 && myPlayer.team == player.team)
					flag2 = true;
				if (flag1)
					markMinimap(mapDotFriend, j1, l3);
				else if (flag3)
					markMinimap(mapDotClan, j1, l3);
				else if (flag2)
					markMinimap(mapDotTeam, j1, l3);
				else
					markMinimap(mapDotPlayer, j1, l3);
			}
		}
		if (anInt855 != 0 && loopCycle % 20 < 10) {
			if (anInt855 == 1 && anInt1222 >= 0 && anInt1222 < npcArray.length) {
				Npc class30_sub2_sub4_sub1_sub1_1 = npcArray[anInt1222];
				if (class30_sub2_sub4_sub1_sub1_1 != null) {
					int k1 = class30_sub2_sub4_sub1_sub1_1.x / 32 - myPlayer.x / 32;
					int i4 = class30_sub2_sub4_sub1_sub1_1.y / 32 - myPlayer.y / 32;
					method81(mapMarker, i4, k1);
				}
			}
			if (anInt855 == 2) {
				int l1 = ((anInt934 - baseX) * 4 + 2) - myPlayer.x / 32;
				int j4 = ((anInt935 - baseY) * 4 + 2) - myPlayer.y / 32;
				method81(mapMarker, j4, l1);
			}
			if (anInt855 == 10 && anInt933 >= 0 && anInt933 < playerArray.length) {
				Player class30_sub2_sub4_sub1_sub2_1 = playerArray[anInt933];
				if (class30_sub2_sub4_sub1_sub2_1 != null) {
					int i2 = class30_sub2_sub4_sub1_sub2_1.x / 32 - myPlayer.x / 32;
					int k4 = class30_sub2_sub4_sub1_sub2_1.y / 32 - myPlayer.y / 32;
					method81(mapMarker, k4, i2);
				}
			}
		}
		if (destX != 0) {
			int j2 = (destX * 4 + 2) - myPlayer.x / 32;
			int l4 = (destY * 4 + 2) - myPlayer.y / 32;
			markMinimap(mapFlag, j2, l4);
		}
		if (clientSize == 0) {
			imageLoader[1].drawSprite(0 + xPosOffset, 0);
		} else {
			imageLoader[28].drawSprite(clientWidth - 167, 0);
			imageLoader[58].drawSprite(clientWidth - 172, 0);
		}
		compass.method352(33, minimapInt1, anIntArray1057, 256, anIntArray968, 25, (clientSize == 0 ? 8 : 5), (clientSize == 0 ? 11 + xPosOffset : clientWidth - 167), 33, 25);
		DrawingArea.drawPixels(3, (clientSize == 0 ? 83 : 80), (clientSize == 0 ? 104 + xPosOffset : clientWidth - 88), 0xffffff, 3);
		loadOrbs();
		if (isFixed()) {
			if (menuOpen)
				drawMenu(516, 0);
		} else {
			if (menuOpen && menuScreenArea == 3)
				drawMenu();
		}
		aRSImageProducer_1165.setCanvas();
	}

	private void npcScreenPos(Entity entity, int i) {
		calcEntityScreenPos(entity.x, i, entity.y);
	}

	private void calcEntityScreenPos(int i, int j, int l) {
		if (i < 128 || l < 128 || i > 13056 || l > 13056) {
			spriteDrawX = -1;
			spriteDrawY = -1;
			return;
		}
		int i1 = method42(plane, l, i) - j;
		i -= xCameraPos;
		i1 -= zCameraPos;
		l -= yCameraPos;
		int j1 = Model.SINE[yCameraCurve];
		int k1 = Model.COSINE[yCameraCurve];
		int l1 = Model.SINE[xCameraCurve];
		int i2 = Model.COSINE[xCameraCurve];
		int j2 = l * l1 + i * i2 >> 16;
		l = l * i2 - i * l1 >> 16;
		i = j2;
		j2 = i1 * k1 - l * j1 >> 16;
		l = i1 * j1 + l * k1 >> 16;
		i1 = j2;
		if (l >= 50) {
			spriteDrawX = Rasterizer.centerX + (i << log_view_dist) / l;
			spriteDrawY = Rasterizer.centerY + (i1 << log_view_dist) / l;
		} else {
			spriteDrawX = -1;
			spriteDrawY = -1;
		}
	}

	private void buildSplitPrivateChatMenu() {
		int offsetY = (clientSize == 0 ? 0 : clientHeight - 498);
		if (splitPrivateChat == 0)
			return;
		int yPosOffset = 0;
		if (systemUpdateTimer != 0)
			yPosOffset = 1;
		for (int index = 0; index < 500; index++)
			if (chatMessages[index] != null) {
				int chatType = chatTypes[index];
				String message = chatNames[index];
				if (name != null && name.startsWith("@cr1@")) {
					name = name.substring(5);
					boolean flag2 = true;
					byte byte0 = 1;
				}
				if (name != null && name.startsWith("@cr2@")) {
					name = name.substring(5);
					byte byte0 = 2;
				}
				if (name != null && name.startsWith("@cr3@")) {
					name = name.substring(5);
					byte byte0 = 3;
				}
				if (name != null && name.startsWith("@cr4@")) {
					name = name.substring(5);
					byte byte0 = 4;
				}
				if (name != null && name.startsWith("@cr5@")) {
					name = name.substring(5);
					byte byte0 = 5;
				}
				if (name != null && name.startsWith("@cr6@")) {
					name = name.substring(5);
					byte byte0 = 6;
				}
				if (name != null && name.startsWith("@cr7@")) {
					name = name.substring(5);
					byte byte0 = 7;
				}
				if (name != null && name.startsWith("@cr8@")) {
					name = name.substring(5);
					byte byte0 = 8;
				}
				if (name != null && name.startsWith("@cr9@")) {
					name = name.substring(5);
					byte byte0 = 9;
				}
				if (name != null && name.startsWith("@cr10@")) {
					name = name.substring(6);
					byte byte0 = 10;
				}
				if ((chatType == 3 || chatType == 7) && (chatType == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(message))) {
					int yPos = 329 - yPosOffset * 13;
					if (super.mouseX > 4 && super.mouseY - 4 > yPos - 10 + offsetY && super.mouseY - 4 <= yPos + 3 + offsetY) {
						int xPosOffset = normalFont.getTextWidth("From:  " + message + chatMessages[index]) + 25;
						if (xPosOffset > 450)
							xPosOffset = 450;
						if (super.mouseX < 4 + xPosOffset) {
							if (myPrivilege >= 1) {
								menuActionName[menuActionRow] = "Report abuse"
										+ message;
								menuActionID[menuActionRow] = 2606;
								menuActionRow++;
							}
							menuActionName[menuActionRow] = "Add ignore"
									+ message;
							menuActionID[menuActionRow] = 2042;
							menuActionRow++;
							menuActionName[menuActionRow] = "Add friend"
									+ message;
							menuActionID[menuActionRow] = 2337;
							menuActionRow++;
						}
					}
					if (++yPosOffset >= 5)
						return;
				}
				if ((chatType == 5 || chatType == 6) && privateChatMode < 2
						&& ++yPosOffset >= 5)
					return;
			}

	}

	private void method130(int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2) {
		Class30_Sub1 class30_sub1 = null;
		for (Class30_Sub1 class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1_1 != null; class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
			if (class30_sub1_1.anInt1295 != l1 || class30_sub1_1.anInt1297 != i2 || class30_sub1_1.anInt1298 != j1 || class30_sub1_1.anInt1296 != i1)
				continue;
			class30_sub1 = class30_sub1_1;
			break;
		}

		if (class30_sub1 == null) {
			class30_sub1 = new Class30_Sub1();
			class30_sub1.anInt1295 = l1;
			class30_sub1.anInt1296 = i1;
			class30_sub1.anInt1297 = i2;
			class30_sub1.anInt1298 = j1;
			method89(class30_sub1);
			aClass19_1179.insertHead(class30_sub1);
		}
		class30_sub1.anInt1291 = k;
		class30_sub1.anInt1293 = k1;
		class30_sub1.anInt1292 = l;
		class30_sub1.anInt1302 = j2;
		class30_sub1.anInt1294 = j;
	}

	private boolean interfaceIsSelected(RSInterface class9) {
		if (class9.valueCompareType == null)
			return false;
		for (int i = 0; i < class9.valueCompareType.length; i++) {
			int j = extractInterfaceValues(class9, i);
			int k = class9.requiredValues[i];
			if (class9.valueCompareType[i] == 2) {
				if (j >= k)
					return false;
			} else if (class9.valueCompareType[i] == 3) {
				if (j <= k)
					return false;
			} else if (class9.valueCompareType[i] == 4) {
				if (j == k)
					return false;
			} else if (j != k)
				return false;
		}

		return true;
	}

	private DataInputStream openJagGrabInputStream(String s) throws IOException {
		if (aSocket832 != null) {
			try {
				aSocket832.close();
			} catch (Exception _ex) {
			}
			aSocket832 = null;
		}
		aSocket832 = openSocket(43595);
		aSocket832.setSoTimeout(10000);
		java.io.InputStream inputstream = aSocket832.getInputStream();
		OutputStream outputstream = aSocket832.getOutputStream();
		outputstream.write(("JAGGRAB /" + s + "\n\n").getBytes());
		return new DataInputStream(inputstream);
	}

	public void doFlamesDrawing() {
		char c = '\u0100';
		if (anInt1040 > 0) {
			for (int i = 0; i < 256; i++)
				if (anInt1040 > 768)
					anIntArray850[i] = method83(anIntArray851[i], anIntArray852[i], 1024 - anInt1040);
				else if (anInt1040 > 256)
					anIntArray850[i] = anIntArray852[i];
				else
					anIntArray850[i] = method83(anIntArray852[i], anIntArray851[i], 256 - anInt1040);

		} else if (anInt1041 > 0) {
			for (int j = 0; j < 256; j++)
				if (anInt1041 > 768)
					anIntArray850[j] = method83(anIntArray851[j], anIntArray853[j], 1024 - anInt1041);
				else if (anInt1041 > 256)
					anIntArray850[j] = anIntArray853[j];
				else
					anIntArray850[j] = method83(anIntArray853[j], anIntArray851[j], 256 - anInt1041);

		} else {
			System.arraycopy(anIntArray851, 0, anIntArray850, 0, 256);

		}
		System.arraycopy(aClass30_Sub2_Sub1_Sub1_1201.myPixels, 0, aRSImageProducer_1110.raster, 0, 33920);

		int i1 = 0;
		int j1 = 1152;
		for (int k1 = 1; k1 < c - 1; k1++) {
			int l1 = (anIntArray969[k1] * (c - k1)) / c;
			int j2 = 22 + l1;
			if (j2 < 0)
				j2 = 0;
			i1 += j2;
			for (int l2 = j2; l2 < 128; l2++) {
				int j3 = anIntArray828[i1++];
				if (j3 != 0) {
					int l3 = j3;
					int j4 = 256 - j3;
					j3 = anIntArray850[j3];
					int l4 = aRSImageProducer_1110.raster[j1];
					aRSImageProducer_1110.raster[j1++] = ((j3 & 0xff00ff) * l3 + (l4 & 0xff00ff) * j4 & 0xff00ff00) + ((j3 & 0xff00) * l3 + (l4 & 0xff00) * j4 & 0xff0000) >> 8;
				} else {
					j1++;
				}
			}

			j1 += j2;
		}

		aRSImageProducer_1110.drawGraphics(0, super.graphics, 0);
		System.arraycopy(aClass30_Sub2_Sub1_Sub1_1202.myPixels, 0, aRSImageProducer_1111.raster, 0, 33920);

		i1 = 0;
		j1 = 1176;
		for (int k2 = 1; k2 < c - 1; k2++) {
			int i3 = (anIntArray969[k2] * (c - k2)) / c;
			int k3 = 103 - i3;
			j1 += i3;
			for (int i4 = 0; i4 < k3; i4++) {
				int k4 = anIntArray828[i1++];
				if (k4 != 0) {
					int i5 = k4;
					int j5 = 256 - k4;
					k4 = anIntArray850[k4];
					int k5 = aRSImageProducer_1111.raster[j1];
					aRSImageProducer_1111.raster[j1++] = ((k4 & 0xff00ff) * i5 + (k5 & 0xff00ff) * j5 & 0xff00ff00) + ((k4 & 0xff00) * i5 + (k5 & 0xff00) * j5 & 0xff0000) >> 8;
				} else {
					j1++;
				}
			}

			i1 += 128 - k3;
			j1 += 128 - k3 - i3;
		}

		aRSImageProducer_1111.drawGraphics(0, super.graphics, 637);
	}

	private void method134(Buffer buffer) {
		int j = buffer.readBits(8);
		if (j < playerCount) {
			for (int k = j; k < playerCount; k++)
				anIntArray840[anInt839++] = playerIndices[k];

		}
		if (j > playerCount) {
			Signlink.reporterror(myUsername + " Too many players");
			throw new RuntimeException("eek");
		}
		playerCount = 0;
		for (int l = 0; l < j; l++) {
			int i1 = playerIndices[l];
			Player player = playerArray[i1];
			int j1 = buffer.readBits(1);
			if (j1 == 0) {
				playerIndices[playerCount++] = i1;
				player.anInt1537 = loopCycle;
			} else {
				int k1 = buffer.readBits(2);
				if (k1 == 0) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = i1;
				} else if (k1 == 1) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int l1 = buffer.readBits(3);
					player.moveInDir(false, l1);
					int j2 = buffer.readBits(1);
					if (j2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 2) {
					playerIndices[playerCount++] = i1;
					player.anInt1537 = loopCycle;
					int i2 = buffer.readBits(3);
					player.moveInDir(true, i2);
					int k2 = buffer.readBits(3);
					player.moveInDir(true, k2);
					int l2 = buffer.readBits(1);
					if (l2 == 1)
						anIntArray894[anInt893++] = i1;
				} else if (k1 == 3)
					anIntArray840[anInt839++] = i1;
			}
		}
	}

	public void raiseWelcomeScreen() {
		welcomeScreenRaised = true;
	}

	private void method137(Buffer buffer, int j) {
		if (j == 84) {
			int k = buffer.readUnsignedByte();
			int j3 = anInt1268 + (k >> 4 & 7);
			int i6 = anInt1269 + (k & 7);
			int l8 = buffer.readUnsignedWord();
			int k11 = buffer.readUnsignedWord();
			int l13 = buffer.readUnsignedWord();
			if (j3 >= 0 && i6 >= 0 && j3 < 104 && i6 < 104) {
				NodeList class19_1 = groundArray[plane][j3][i6];
				if (class19_1 != null) {
					for (Item class30_sub2_sub4_sub2_3 = (Item) class19_1.reverseGetFirst(); class30_sub2_sub4_sub2_3 != null; class30_sub2_sub4_sub2_3 = (Item) class19_1.reverseGetNext()) {
						if (class30_sub2_sub4_sub2_3.ID != (l8 & 0x7fff) || class30_sub2_sub4_sub2_3.anInt1559 != k11)
							continue;
						class30_sub2_sub4_sub2_3.anInt1559 = l13;
						break;
					}

					spawnGroundItem(j3, i6);
				}
			}
			return;
		}
		if (j == 105) {
			int l = buffer.readUnsignedByte();
			int k3 = anInt1268 + (l >> 4 & 7);
			int j6 = anInt1269 + (l & 7);
			int i9 = buffer.readUnsignedWord();
			int l11 = buffer.readUnsignedByte();
			int i14 = l11 >> 4 & 0xf;
			int i16 = l11 & 7;
			if (myPlayer.smallX[0] >= k3 - i14 && myPlayer.smallX[0] <= k3 + i14 && myPlayer.smallY[0] >= j6 - i14 && myPlayer.smallY[0] <= j6 + i14 && aBoolean848 && !lowMem && anInt1062 < 50) {
				anIntArray1207[anInt1062] = i9;
				anIntArray1241[anInt1062] = i16;
				anIntArray1250[anInt1062] = Sounds.anIntArray326[i9];
				anInt1062++;
			}
		}
		if (j == 215) {
			int i1 = buffer.method435();
			int l3 = buffer.method428();
			int k6 = anInt1268 + (l3 >> 4 & 7);
			int j9 = anInt1269 + (l3 & 7);
			int i12 = buffer.method435();
			int j14 = buffer.readUnsignedWord();
			if (k6 >= 0 && j9 >= 0 && k6 < 104 && j9 < 104 && i12 != unknownInt10) {
				Item class30_sub2_sub4_sub2_2 = new Item();
				class30_sub2_sub4_sub2_2.ID = i1;
				class30_sub2_sub4_sub2_2.anInt1559 = j14;
				if (groundArray[plane][k6][j9] == null)
					groundArray[plane][k6][j9] = new NodeList();
				groundArray[plane][k6][j9].insertHead(class30_sub2_sub4_sub2_2);
				spawnGroundItem(k6, j9);
			}
			return;
		}
		if (j == 156) {
			int j1 = buffer.method426();
			int i4 = anInt1268 + (j1 >> 4 & 7);
			int l6 = anInt1269 + (j1 & 7);
			int k9 = buffer.readUnsignedWord();
			if (i4 >= 0 && l6 >= 0 && i4 < 104 && l6 < 104) {
				NodeList class19 = groundArray[plane][i4][l6];
				if (class19 != null) {
					for (Item item = (Item) class19.reverseGetFirst(); item != null; item = (Item) class19.reverseGetNext()) {
						if (item.ID != (k9 & 0x7fff))
							continue;
						item.unlink();
						break;
					}

					if (class19.reverseGetFirst() == null)
						groundArray[plane][i4][l6] = null;
					spawnGroundItem(i4, l6);
				}
			}
			return;
		}
		if (j == 160) {
			int k1 = buffer.method428();
			int j4 = anInt1268 + (k1 >> 4 & 7);
			int i7 = anInt1269 + (k1 & 7);
			int l9 = buffer.method428();
			int j12 = l9 >> 2;
			int k14 = l9 & 3;
			int j16 = anIntArray1177[j12];
			int j17 = buffer.method435();
			if (j4 >= 0 && i7 >= 0 && j4 < 103 && i7 < 103) {
				int j18 = intGroundArray[plane][j4][i7];
				int i19 = intGroundArray[plane][j4 + 1][i7];
				int l19 = intGroundArray[plane][j4 + 1][i7 + 1];
				int k20 = intGroundArray[plane][j4][i7 + 1];
				if (j16 == 0) {
					Object1 class10 = worldController.method296(plane, j4, i7);
					if (class10 != null) {
						int k21 = class10.uid >> 14 & 0x7fff;
						if (j12 == 2) {
							class10.aClass30_Sub2_Sub4_278 = new Animable_Sub5(k21, 4 + k14, 2, i19, l19, j18, k20, j17, false);
							class10.aClass30_Sub2_Sub4_279 = new Animable_Sub5(k21, k14 + 1 & 3, 2, i19, l19, j18, k20, j17, false);
						} else {
							class10.aClass30_Sub2_Sub4_278 = new Animable_Sub5(k21, k14, j12, i19, l19, j18, k20, j17, false);
						}
					}
				}
				if (j16 == 1) {
					Object2 class26 = worldController.method297(j4, i7, plane);
					if (class26 != null)
						class26.aClass30_Sub2_Sub4_504 = new Animable_Sub5(class26.uid >> 14 & 0x7fff, 0, 4, i19, l19, j18, k20, j17, false);
				}
				if (j16 == 2) {
					EntityUnit class28 = worldController.method298(j4, i7, plane);
					if (j12 == 11)
						j12 = 10;
					if (class28 != null)
						class28.aClass30_Sub2_Sub4_521 = new Animable_Sub5(class28.uid >> 14 & 0x7fff, k14, j12, i19, l19, j18, k20, j17, false);
				}
				if (j16 == 3) {
					Object3 class49 = worldController.method299(i7, j4, plane);
					if (class49 != null)
						class49.aClass30_Sub2_Sub4_814 = new Animable_Sub5(class49.uid >> 14 & 0x7fff, k14, 22, i19, l19, j18, k20, j17, false);
				}
			}
			return;
		}
		if (j == 147) {
			int l1 = buffer.method428();
			int k4 = anInt1268 + (l1 >> 4 & 7);
			int j7 = anInt1269 + (l1 & 7);
			int i10 = buffer.readUnsignedWord();
			byte byte0 = buffer.method430();
			int l14 = buffer.method434();
			byte byte1 = buffer.method429();
			int k17 = buffer.readUnsignedWord();
			int k18 = buffer.method428();
			int j19 = k18 >> 2;
			int i20 = k18 & 3;
			int l20 = anIntArray1177[j19];
			byte byte2 = buffer.readSignedByte();
			int l21 = buffer.readUnsignedWord();
			byte byte3 = buffer.method429();
			Player player;
			if (i10 == unknownInt10)
				player = myPlayer;
			else
				player = playerArray[i10];
			if (player != null) {
				ObjectDefinition class46 = ObjectDefinition.forID(l21);
				int i22 = intGroundArray[plane][k4][j7];
				int j22 = intGroundArray[plane][k4 + 1][j7];
				int k22 = intGroundArray[plane][k4 + 1][j7 + 1];
				int l22 = intGroundArray[plane][k4][j7 + 1];
				Model model = class46.method578(j19, i20, i22, j22, k22, l22, -1, -1, -1, -1);
				if (model != null) {
					method130(k17 + 1, -1, 0, l20, j7, 0, plane, k4, l14 + 1);
					player.anInt1707 = l14 + loopCycle;
					player.anInt1708 = k17 + loopCycle;
					player.aModel_1714 = model;
					int i23 = class46.anInt744;
					int j23 = class46.anInt761;
					if (i20 == 1 || i20 == 3) {
						i23 = class46.anInt761;
						j23 = class46.anInt744;
					}
					player.anInt1711 = (k4 << 7) + (i23 << 6);
					player.anInt1713 = (j7 << 7) + (j23 << 6);
					player.anInt1712 = method42(plane, player.anInt1713, player.anInt1711);
					if (byte2 > byte0) {
						byte byte4 = byte2;
						byte2 = byte0;
						byte0 = byte4;
					}
					if (byte3 > byte1) {
						byte byte5 = byte3;
						byte3 = byte1;
						byte1 = byte5;
					}
					player.anInt1719 = k4 + byte2;
					player.anInt1721 = k4 + byte0;
					player.anInt1720 = j7 + byte3;
					player.anInt1722 = j7 + byte1;
				}
			}
		}
		if (j == 151) {
			int i2 = buffer.method426();
			int l4 = anInt1268 + (i2 >> 4 & 7);
			int k7 = anInt1269 + (i2 & 7);
			int j10 = buffer.method434();
			int k12 = buffer.method428();
			int i15 = k12 >> 2;
			int k16 = k12 & 3;
			int l17 = anIntArray1177[i15];
			if (l4 >= 0 && k7 >= 0 && l4 < 104 && k7 < 104)
				method130(-1, j10, k16, l17, k7, i15, plane, l4, 0);
			return;
		}
		if (j == 4) {
			int j2 = buffer.readUnsignedByte();
			int i5 = anInt1268 + (j2 >> 4 & 7);
			int l7 = anInt1269 + (j2 & 7);
			int k10 = buffer.readUnsignedWord();
			int l12 = buffer.readUnsignedByte();
			int j15 = buffer.readUnsignedWord();
			if (i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104) {
				i5 = (i5 << 7) + 64;
				l7 = (l7 << 7) + 64;
				Animable_Sub3 class30_sub2_sub4_sub3 = new Animable_Sub3(plane, loopCycle, j15, k10, method42(plane, l7, i5) - l12, l7, i5);
				aClass19_1056.insertHead(class30_sub2_sub4_sub3);
			}
			return;
		}
		if (j == 44) {
			int k2 = buffer.method436();
			int j5 = buffer.readUnsignedWord();
			int i8 = buffer.readUnsignedByte();
			int l10 = anInt1268 + (i8 >> 4 & 7);
			int i13 = anInt1269 + (i8 & 7);
			if (l10 >= 0 && i13 >= 0 && l10 < 104 && i13 < 104) {
				Item class30_sub2_sub4_sub2_1 = new Item();
				class30_sub2_sub4_sub2_1.ID = k2;
				class30_sub2_sub4_sub2_1.anInt1559 = j5;
				if (groundArray[plane][l10][i13] == null)
					groundArray[plane][l10][i13] = new NodeList();
				groundArray[plane][l10][i13].insertHead(class30_sub2_sub4_sub2_1);
				spawnGroundItem(l10, i13);
			}
			return;
		}
		if (j == 101) {
			int l2 = buffer.method427();
			int k5 = l2 >> 2;
			int j8 = l2 & 3;
			int i11 = anIntArray1177[k5];
			int j13 = buffer.readUnsignedByte();
			int k15 = anInt1268 + (j13 >> 4 & 7);
			int l16 = anInt1269 + (j13 & 7);
			if (k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104)
				method130(-1, -1, j8, i11, l16, k5, plane, k15, 0);
			return;
		}
		if (j == 117) {
			int i3 = buffer.readUnsignedByte();
			int l5 = anInt1268 + (i3 >> 4 & 7);
			int k8 = anInt1269 + (i3 & 7);
			int j11 = l5 + buffer.readSignedByte();
			int k13 = k8 + buffer.readSignedByte();
			int l15 = buffer.readSignedWord();
			int i17 = buffer.readUnsignedWord();
			int i18 = buffer.readUnsignedByte() * 4;
			int l18 = buffer.readUnsignedByte() * 4;
			int k19 = buffer.readUnsignedWord();
			int j20 = buffer.readUnsignedWord();
			int i21 = buffer.readUnsignedByte();
			int j21 = buffer.readUnsignedByte();
			if (l5 >= 0 && k8 >= 0 && l5 < 104 && k8 < 104 && j11 >= 0 && k13 >= 0 && j11 < 104 && k13 < 104 && i17 != 65535) {
				l5 = (l5 << 7) + 64;
				k8 = (k8 << 7) + 64;
				j11 = (j11 << 7) + 64;
				k13 = (k13 << 7) + 64;
				Animable_Sub4 class30_sub2_sub4_sub4 = new Animable_Sub4(i21, l18, k19 + loopCycle, j20 + loopCycle, j21, plane, method42(plane, k8, l5) - i18, k8, l5, l15, i17);
				class30_sub2_sub4_sub4.method455(k19 + loopCycle, k13, method42(plane, k13, j11) - l18, j11);
				aClass19_1013.insertHead(class30_sub2_sub4_sub4);
			}
		}
	}

	private void method139(Buffer buffer) {
		buffer.initBitAccess();
		int k = buffer.readBits(8);
		if (k < npcCount) {
			for (int l = k; l < npcCount; l++)
				anIntArray840[anInt839++] = npcIndices[l];

		}
		if (k > npcCount) {
			Signlink.reporterror(myUsername + " Too many npcs");
			throw new RuntimeException("eek");
		}
		npcCount = 0;
		for (int i1 = 0; i1 < k; i1++) {
			int j1 = npcIndices[i1];
			Npc npc = npcArray[j1];
			int k1 = buffer.readBits(1);
			if (k1 == 0) {
				npcIndices[npcCount++] = j1;
				npc.anInt1537 = loopCycle;
			} else {
				int l1 = buffer.readBits(2);
				if (l1 == 0) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					anIntArray894[anInt893++] = j1;
				} else if (l1 == 1) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					int i2 = buffer.readBits(3);
					npc.moveInDir(false, i2);
					int k2 = buffer.readBits(1);
					if (k2 == 1)
						anIntArray894[anInt893++] = j1;
				} else if (l1 == 2) {
					npcIndices[npcCount++] = j1;
					npc.anInt1537 = loopCycle;
					int j2 = buffer.readBits(3);
					npc.moveInDir(true, j2);
					int l2 = buffer.readBits(3);
					npc.moveInDir(true, l2);
					int i3 = buffer.readBits(1);
					if (i3 == 1)
						anIntArray894[anInt893++] = j1;
				} else if (l1 == 3)
					anIntArray840[anInt839++] = j1;
			}
		}
	}

	private void markMinimap(Sprite sprite, int x, int y) {
		if (sprite == null)
			return;
		try {
			int offX = clientSize == 0 ? 0 : clientWidth - 249;
			int k = minimapInt1 + minimapInt2 & 0x7ff;
			int l = x * x + y * y;
			if (l > 6400) {
				return;
			}
			int i1 = Model.SINE[k];
			int j1 = Model.COSINE[k];
			i1 = (i1 * 256) / (minimapInt3 + 256);
			j1 = (j1 * 256) / (minimapInt3 + 256);
			int k1 = y * i1 + x * j1 >> 16;
			int l1 = y * j1 - x * i1 >> 16;
			if (clientSize == 0)
				sprite.drawSprite(((105 + k1) - sprite.anInt1444 / 2) + 4 + offX, 88 - l1 - sprite.anInt1445 / 2 - 4);
			else
				sprite.drawSprite(((77 + k1) - sprite.anInt1444 / 2) + 4 + (clientWidth - 167), 85 - l1 - sprite.anInt1445 / 2 - 4);
		} catch (Exception e) {

		}
	}

	private void method142(int i, int j, int k, int l, int i1, int j1, int k1) {
		if (i1 >= 1 && i >= 1 && i1 <= 102 && i <= 102) {
			if (lowMem && j != plane)
				return;
			int i2 = 0;
			if (j1 == 0)
				i2 = worldController.method300(j, i1, i);
			if (j1 == 1)
				i2 = worldController.method301(j, i1, i);
			if (j1 == 2)
				i2 = worldController.method302(j, i1, i);
			if (j1 == 3)
				i2 = worldController.method303(j, i1, i);
			if (i2 != 0) {
				int i3 = worldController.method304(j, i1, i, i2);
				int j2 = i2 >> 14 & 0x7fff;
				int k2 = i3 & 0x1f;
				int l2 = i3 >> 6;
				if (j1 == 0) {
					worldController.method291(i1, j, i, (byte) -119);
					ObjectDefinition class46 = ObjectDefinition.forID(j2);
					if (class46.aBoolean767)
						aClass11Array1230[j].method215(l2, k2, class46.aBoolean757, i1, i);
				}
				if (j1 == 1)
					worldController.method292(i, j, i1);
				if (j1 == 2) {
					worldController.method293(j, i1, i);
					ObjectDefinition class46_1 = ObjectDefinition.forID(j2);
					if (i1 + class46_1.anInt744 > 103 || i + class46_1.anInt744 > 103 || i1 + class46_1.anInt761 > 103 || i + class46_1.anInt761 > 103)
						return;
					if (class46_1.aBoolean767)
						aClass11Array1230[j].method216(l2, class46_1.anInt744, i1, i, class46_1.anInt761, class46_1.aBoolean757);
				}
				if (j1 == 3) {
					worldController.method294(j, i, i1);
					ObjectDefinition class46_2 = ObjectDefinition.forID(j2);
					if (class46_2.aBoolean767 && class46_2.hasActions)
						aClass11Array1230[j].method218(i, i1);
				}
			}
			if (k1 >= 0) {
				int j3 = j;
				if (j3 < 3 && (byteGroundArray[1][i1][i] & 2) == 2)
					j3++;
				ObjectManager.method188(worldController, k, i, l, j3, aClass11Array1230[j], intGroundArray, i1, k1, j);
			}
		}
	}

	private void updatePlayers(int i, Buffer buffer) {
		anInt839 = 0;
		anInt893 = 0;
		method117(buffer);
		method134(buffer);
		method91(buffer, i);
		method49(buffer);
		for (int k = 0; k < anInt839; k++) {
			int l = anIntArray840[k];
			if (playerArray[l].anInt1537 != loopCycle)
				playerArray[l] = null;
		}

		if (buffer.currentOffset != i) {
			Signlink.reporterror("Error packet size mismatch in getplayer pos:" + buffer.currentOffset + " psize:" + i);
			throw new RuntimeException("eek");
		}
		for (int i1 = 0; i1 < playerCount; i1++)
			if (playerArray[playerIndices[i1]] == null) {
				Signlink.reporterror(myUsername + " null entry in pl list - pos:" + i1 + " size:" + playerCount);
				throw new RuntimeException("eek");
			}

	}

	private void setCameraPos(int j, int k, int l, int i1, int j1, int k1) {
		int l1 = 2048 - k & 0x7ff;
		int i2 = 2048 - j1 & 0x7ff;
		int j2 = 0;
		int k2 = 0;
		int l2 = j;
		if (l1 != 0) {
			int i3 = Model.SINE[l1];
			int k3 = Model.COSINE[l1];
			int i4 = k2 * k3 - l2 * i3 >> 16;
			l2 = k2 * i3 + l2 * k3 >> 16;
			k2 = i4;
		}
		if (i2 != 0) {
			int j3 = Model.SINE[i2];
			int l3 = Model.COSINE[i2];
			int j4 = l2 * j3 + j2 * l3 >> 16;
			l2 = l2 * l3 - j2 * j3 >> 16;
			j2 = j4;
		}
		xCameraPos = l - j2;
		zCameraPos = i1 - k2;
		yCameraPos = k1 - l2;
		yCameraCurve = k;
		xCameraCurve = j1;
	}

	public void updateStrings(String str, int i) {
		switch (i) {
		case 1675:
			sendFrame126(str, 17508);
			break; // Stab
		case 1676:
			sendFrame126(str, 17509);
			break; // Slash
		case 1678:
			sendFrame126(str, 17511);
			break; // Magic
		case 1679:
			sendFrame126(str, 17512);
			break; // Range
		case 1680:
			sendFrame126(str, 17513);
			break; // Stab
		case 1681:
			sendFrame126(str, 17514);
			break; // Slash
		case 1682:
			sendFrame126(str, 17515);
			break; // Crush
		case 1683:
			sendFrame126(str, 17516);
			break; // Magic
		case 1684:
			sendFrame126(str, 17517);
			break; // Range
		case 1686:
			sendFrame126(str, 17518);
			break; // Strength
		case 1687:
			sendFrame126(str, 17519);
			break; // Prayer
		}
	}

	public void sendFrame126(String str, int i) {
		RSInterface.interfaceCache[i].message = str;
	}

	public void sendPacket185(int button, int toggle, int type) {
		switch (type) {
		case 135:
			RSInterface class9 = RSInterface.interfaceCache[button];
			boolean flag8 = true;
			if (class9.contentType > 0)
				flag8 = promptUserForInput(class9);
			if (flag8) {
				buffer.createFrame(185);
				buffer.writeWord(button);
			}
			break;
		case 646:
			buffer.createFrame(185);
			buffer.writeWord(button);
			RSInterface class9_2 = RSInterface.interfaceCache[button];
			if (class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
				if (variousSettings[toggle] != class9_2.requiredValues[0]) {
					variousSettings[toggle] = class9_2.requiredValues[0];
					method33(toggle);
				}
			}
			break;
		case 169:
			buffer.createFrame(185);
			buffer.writeWord(button);
			RSInterface class9_3 = RSInterface.interfaceCache[button];
			if (class9_3.valueIndexArray != null && class9_3.valueIndexArray[0][0] == 5) {
				variousSettings[toggle] = 1 - variousSettings[toggle];
				method33(toggle);
			}
			switch (button) {
			case 19136:
				if (toggle == 0)
					sendFrame36(173, toggle);
				if (toggle == 1)
					sendPacket185(153, 173, 646);
				break;
			}
			break;
		}
	}

	public void sendFrame36(int id, int state) {
		anIntArray1045[id] = state;
		if (variousSettings[id] != state) {
			variousSettings[id] = state;
			method33(id);
			if (dialogID != -1)
				inputTaken = true;
		}
	}

	public void sendFrame219() {
		if (invOverlayInterfaceID != -1) {
			invOverlayInterfaceID = -1;
			tabAreaAltered = true;
		}
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
		}
		if (inputDialogState != 0) {
			inputDialogState = 0;
			inputTaken = true;
		}
		openInterfaceID = -1;
		aBoolean1149 = false;
	}

	public void sendFrame248(int interfaceID, int sideInterfaceID) {
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
		}
		if (inputDialogState != 0) {
			inputDialogState = 0;
			inputTaken = true;
		}
		openInterfaceID = interfaceID;
		invOverlayInterfaceID = sideInterfaceID;
		tabAreaAltered = true;
		aBoolean1149 = false;
	}

	private boolean parsePacket() {
		if (socketStream == null)
			return false;
		try {
			int i = socketStream.available();
			if (i == 0)
				return false;
			if (pktType == -1) {
				socketStream.flushInputStream(inStream.buffer, 1);
				pktType = inStream.buffer[0] & 0xff;
				if (encryption != null)
					pktType = pktType - encryption.getNextKey() & 0xff;
				pktSize = SizeConstants.packetSizes[pktType];
				i--;
			}
			if (pktSize == -1)
				if (i > 0) {
					socketStream.flushInputStream(inStream.buffer, 1);
					pktSize = inStream.buffer[0] & 0xff;
					i--;
				} else {
					return false;
				}
			if (pktSize == -2)
				if (i > 1) {
					socketStream.flushInputStream(inStream.buffer, 2);
					inStream.currentOffset = 0;
					pktSize = inStream.readUnsignedWord();
					i -= 2;
				} else {
					return false;
				}
			if (i < pktSize)
				return false;
			inStream.currentOffset = 0;
			socketStream.flushInputStream(inStream.buffer, pktSize);
			anInt1009 = 0;
			anInt843 = anInt842;
			anInt842 = anInt841;
			anInt841 = pktType;
			switch (pktType) {
			case 81:
				updatePlayers(pktSize, inStream);
				aBoolean1080 = false;
				pktType = -1;
				return true;

			case 176:
				daysSinceRecovChange = inStream.method427();
				unreadMessages = inStream.method435();
				membersInt = inStream.readUnsignedByte();
				anInt1193 = inStream.method440();
				daysSinceLastLogin = inStream.readUnsignedWord();
				if (anInt1193 != 0 && openInterfaceID == -1) {
					Signlink.dnslookup(TextClass.method586(anInt1193));
					clearTopInterfaces();
					char c = '\u028A';
					if (daysSinceRecovChange != 201 || membersInt == 1)
						c = '\u028F';
					reportAbuseInput = "";
					canMute = false;
					for (int k9 = 0; k9 < RSInterface.interfaceCache.length; k9++) {
						if (RSInterface.interfaceCache[k9] == null || RSInterface.interfaceCache[k9].contentType != c)
							continue;
						openInterfaceID = RSInterface.interfaceCache[k9].parentID;

					}
				}
				pktType = -1;
				return true;

			case 64:
				anInt1268 = inStream.method427();
				anInt1269 = inStream.method428();
				for (int j = anInt1268; j < anInt1268 + 8; j++) {
					for (int l9 = anInt1269; l9 < anInt1269 + 8; l9++)
						if (groundArray[plane][j][l9] != null) {
							groundArray[plane][j][l9] = null;
							spawnGroundItem(j, l9);
						}
				}
				for (Class30_Sub1 class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1 != null; class30_sub1 = (Class30_Sub1) aClass19_1179.reverseGetNext())
					if (class30_sub1.anInt1297 >= anInt1268 && class30_sub1.anInt1297 < anInt1268 + 8 && class30_sub1.anInt1298 >= anInt1269 && class30_sub1.anInt1298 < anInt1269 + 8 && class30_sub1.anInt1295 == plane)
						class30_sub1.anInt1294 = 0;
				pktType = -1;
				return true;

			case 185:
				int k = inStream.method436();
				RSInterface.interfaceCache[k].anInt233 = 3;
				if (myPlayer.desc == null)
					RSInterface.interfaceCache[k].mediaID = (myPlayer.anIntArray1700[0] << 25) + (myPlayer.anIntArray1700[4] << 20) + (myPlayer.equipment[0] << 15) + (myPlayer.equipment[8] << 10) + (myPlayer.equipment[11] << 5) + myPlayer.equipment[1];
				else
					RSInterface.interfaceCache[k].mediaID = (int) (0x12345678L + myPlayer.desc.type);
				pktType = -1;
				return true;

			case 107:
				aBoolean1160 = false;
				for (int l = 0; l < 5; l++)
					aBooleanArray876[l] = false;
				xpCounter = 0;
				pktType = -1;
				return true;

			case 72:
				int i1 = inStream.method434();
				RSInterface class9 = RSInterface.interfaceCache[i1];
				for (int k15 = 0; k15 < class9.inv.length; k15++) {
					class9.inv[k15] = -1;
					class9.inv[k15] = 0;
				}
				pktType = -1;
				return true;

			case 214:
				ignoreCount = pktSize / 8;
				for (int j1 = 0; j1 < ignoreCount; j1++)
					ignoreListAsLongs[j1] = inStream.readQWord();
				pktType = -1;
				return true;

			case 166:
				aBoolean1160 = true;
				anInt1098 = inStream.readUnsignedByte();
				anInt1099 = inStream.readUnsignedByte();
				anInt1100 = inStream.readUnsignedWord();
				anInt1101 = inStream.readUnsignedByte();
				anInt1102 = inStream.readUnsignedByte();
				if (anInt1102 >= 100) {
					xCameraPos = (anInt1098 << 7) + 64;
					yCameraPos = (anInt1099 << 7) + 64;
					zCameraPos = method42(plane, yCameraPos, xCameraPos) - anInt1100;
				}
				pktType = -1;
				return true;

			case 134:
				int k1 = inStream.readUnsignedByte();
				int i10 = inStream.method439();
				int l15 = inStream.readUnsignedByte();
				int xp = currentExp[k1];
				currentExp[k1] = i10;
				currentStats[k1] = l15;
				maxStats[k1] = 1;
				expAdded = currentExp[k1] - xp;
				for (int k20 = 0; k20 < 98; k20++)
					if (i10 >= anIntArray1019[k20])
						maxStats[k1] = k20 + 2;
				pktType = -1;
				return true;

			case 71:
				int l1 = inStream.readUnsignedWord();
				int j10 = inStream.method426();
				if (l1 == 65535)
					l1 = -1;
				tabInterfaceIDs[j10] = l1;
				tabAreaAltered = true;
				pktType = -1;
				return true;

			case 74:
				int i2 = inStream.method434();
				if (i2 == 65535)
					i2 = -1;
				if (i2 != currentSong && musicEnabled && !lowMem && prevSong == 0) {
					nextSong = i2;
					songChanging = true;
					onDemandFetcher.method558(2, nextSong);
				}
				currentSong = i2;
				pktType = -1;
				return true;

			case 121:
				int j2 = inStream.method436();
				int k10 = inStream.method435();
				if (musicEnabled && !lowMem) {
					nextSong = j2;
					songChanging = false;
					onDemandFetcher.method558(2, nextSong);
					prevSong = k10;
				}
				pktType = -1;
				return true;

			case 109:
				resetLogout();
				pktType = -1;
				return false;

			case 70:
				int k2 = inStream.readSignedWord();
				int l10 = inStream.method437();
				int i16 = inStream.method434();
				RSInterface class9_5 = RSInterface.interfaceCache[i16];
				class9_5.anInt263 = k2;
				class9_5.anInt265 = l10;
				pktType = -1;
				return true;

			case 124:
				int skillID = inStream.readUnsignedByte();
				int gainedXP = inStream.readDWord();
				int totalEXP = inStream.readDWord();
				addXP(skillID, gainedXP);
				totalXP = totalEXP;
				pktType = -1;
				return true;

			case 217:
				try {
					name = inStream.readString();
					message = inStream.readString();
					clanname = inStream.readString();
					rights = inStream.readUnsignedWord();
					//message = TextInput.processText(message);
					//message = Censor.doCensor(message);
					pushMessage(message, 12, name);
				} catch(Exception e) {
					e.printStackTrace();
				}
				pktType = -1;
				return true;

			case 73:
			case 241:
				int l2 = anInt1069;
				int i11 = anInt1070;
				if (pktType == 73) {
					l2 = mapX = MapX = inStream.method435();
					i11 = mapY = MapY = inStream.readUnsignedWord();
					aBoolean1159 = false;
				}
				if (pktType == 241) {
					i11 = inStream.method435();
					inStream.initBitAccess();
					for (int j16 = 0; j16 < 4; j16++) {
						for (int l20 = 0; l20 < 13; l20++) {
							for (int j23 = 0; j23 < 13; j23++) {
								int i26 = inStream.readBits(1);
								if (i26 == 1)
									anIntArrayArrayArray1129[j16][l20][j23] = inStream.readBits(26);
								else
									anIntArrayArrayArray1129[j16][l20][j23] = -1;
							}
						}
					}
					inStream.finishBitAccess();
					l2 = inStream.readUnsignedWord();
					aBoolean1159 = true;
				}
				if (anInt1069 == l2 && anInt1070 == i11 && loadingStage == 2) {
					pktType = -1;
					return true;
				}
				anInt1069 = l2;
				anInt1070 = i11;
				baseX = (anInt1069 - 6) * 8;
				baseY = (anInt1070 - 6) * 8;
				aBoolean1141 = (anInt1069 / 8 == 48 || anInt1069 / 8 == 49) && anInt1070 / 8 == 48;
				if (anInt1069 / 8 == 48 && anInt1070 / 8 == 148)
					aBoolean1141 = true;
				loadingStage = 1;
				aLong824 = System.currentTimeMillis();
				aRSImageProducer_1165.setCanvas();
				imageLoader[62].drawSprite(0, 0);
				aRSImageProducer_1165.drawGraphics(4, super.graphics, 4);
				if (pktType == 73) {
					int k16 = 0;
					for (int i21 = (anInt1069 - 6) / 8; i21 <= (anInt1069 + 6) / 8; i21++) {
						for (int k23 = (anInt1070 - 6) / 8; k23 <= (anInt1070 + 6) / 8; k23++)
							k16++;
					}
					aByteArrayArray1183 = new byte[k16][];
					aByteArrayArray1247 = new byte[k16][];
					anIntArray1234 = new int[k16];
					anIntArray1235 = new int[k16];
					anIntArray1236 = new int[k16];
					k16 = 0;
					for (int l23 = (anInt1069 - 6) / 8; l23 <= (anInt1069 + 6) / 8; l23++) {
						for (int j26 = (anInt1070 - 6) / 8; j26 <= (anInt1070 + 6) / 8; j26++) {
							anIntArray1234[k16] = (l23 << 8) + j26;
							if (aBoolean1141 && (j26 == 49 || j26 == 149 || j26 == 147 || l23 == 50 || l23 == 49 && j26 == 47)) {
								anIntArray1235[k16] = -1;
								anIntArray1236[k16] = -1;
								k16++;
							} else {
								int k28 = anIntArray1235[k16] = onDemandFetcher.method562(0, j26, l23);
								if (k28 != -1)
									onDemandFetcher.method558(3, k28);
								int j30 = anIntArray1236[k16] = onDemandFetcher.method562(1, j26, l23);
								if (j30 != -1)
									onDemandFetcher.method558(3, j30);
								k16++;
							}
						}
					}
				}
				if (pktType == 241) {
					int l16 = 0;
					int ai[] = new int[676];
					for (int i24 = 0; i24 < 4; i24++) {
						for (int k26 = 0; k26 < 13; k26++) {
							for (int l28 = 0; l28 < 13; l28++) {
								int k30 = anIntArrayArrayArray1129[i24][k26][l28];
								if (k30 != -1) {
									int k31 = k30 >> 14 & 0x3ff;
									int i32 = k30 >> 3 & 0x7ff;
									int k32 = (k31 / 8 << 8) + i32 / 8;
									for (int j33 = 0; j33 < l16; j33++) {
										if (ai[j33] != k32)
											continue;
										k32 = -1;

									}
									if (k32 != -1)
										ai[l16++] = k32;
								}
							}
						}
					}
					aByteArrayArray1183 = new byte[l16][];
					aByteArrayArray1247 = new byte[l16][];
					anIntArray1234 = new int[l16];
					anIntArray1235 = new int[l16];
					anIntArray1236 = new int[l16];
					for (int l26 = 0; l26 < l16; l26++) {
						int i29 = anIntArray1234[l26] = ai[l26];
						int l30 = i29 >> 8 & 0xff;
						int l31 = i29 & 0xff;
						int j32 = anIntArray1235[l26] = onDemandFetcher.method562(0, l31, l30);
						if (j32 != -1)
							onDemandFetcher.method558(3, j32);
						int i33 = anIntArray1236[l26] = onDemandFetcher.method562(1, l31, l30);
						if (i33 != -1)
							onDemandFetcher.method558(3, i33);
					}
				}
				int i17 = baseX - anInt1036;
				int j21 = baseY - anInt1037;
				anInt1036 = baseX;
				anInt1037 = baseY;
				for (int j24 = 0; j24 < 16384; j24++) {
					Npc npc = npcArray[j24];
					if (npc != null) {
						for (int j29 = 0; j29 < 10; j29++) {
							npc.smallX[j29] -= i17;
							npc.smallY[j29] -= j21;
						}
						npc.x -= (i17 << 7);
						npc.y -= (j21 << 7);
					}
				}
				for (int i27 = 0; i27 < maxPlayers; i27++) {
					Player player = playerArray[i27];
					if (player != null) {
						for (int i31 = 0; i31 < 10; i31++) {
							player.smallX[i31] -= i17;
							player.smallY[i31] -= j21;
						}
						player.x -= (i17 << 7);
						player.y -= (j21 << 7);
					}
				}
				aBoolean1080 = true;
				byte byte1 = 0;
				byte byte2 = 104;
				byte byte3 = 1;
				if (i17 < 0) {
					byte1 = 103;
					byte2 = -1;
					byte3 = -1;
				}
				byte byte4 = 0;
				byte byte5 = 104;
				byte byte6 = 1;
				if (j21 < 0) {
					byte4 = 103;
					byte5 = -1;
					byte6 = -1;
				}
				for (int k33 = byte1; k33 != byte2; k33 += byte3) {
					for (int l33 = byte4; l33 != byte5; l33 += byte6) {
						int i34 = k33 + i17;
						int j34 = l33 + j21;
						for (int k34 = 0; k34 < 4; k34++)
							if (i34 >= 0 && j34 >= 0 && i34 < 104 && j34 < 104)
								groundArray[k34][k33][l33] = groundArray[k34][i34][j34];
							else
								groundArray[k34][k33][l33] = null;
					}
				}
				for (Class30_Sub1 class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetFirst(); class30_sub1_1 != null; class30_sub1_1 = (Class30_Sub1) aClass19_1179.reverseGetNext()) {
					class30_sub1_1.anInt1297 -= i17;
					class30_sub1_1.anInt1298 -= j21;
					if (class30_sub1_1.anInt1297 < 0 || class30_sub1_1.anInt1298 < 0 || class30_sub1_1.anInt1297 >= 104 || class30_sub1_1.anInt1298 >= 104)
						class30_sub1_1.unlink();
				}
				if (destX != 0) {
					destX -= i17;
					destY -= j21;
				}
				aBoolean1160 = false;
				pktType = -1;
				return true;

			case 208:
				int i3 = inStream.method437();
				anInt1018 = i3;
				pktType = -1;
				return true;

			case 99:
				mapState = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 75:
				int j3 = inStream.method436();
				int j11 = inStream.method436();
				RSInterface.interfaceCache[j11].anInt233 = 2;
				RSInterface.interfaceCache[j11].mediaID = j3;
				pktType = -1;
				return true;

			case 114:
				systemUpdateTimer = inStream.method434() * 30;
				pktType = -1;
				return true;

			case 60:
				anInt1269 = inStream.readUnsignedByte();
				anInt1268 = inStream.method427();
				while (inStream.currentOffset < pktSize) {
					int k3 = inStream.readUnsignedByte();
					method137(inStream, k3);
				}
				pktType = -1;
				return true;

			case 35:
				int l3 = inStream.readUnsignedByte();
				int k11 = inStream.readUnsignedByte();
				int j17 = inStream.readUnsignedByte();
				int k21 = inStream.readUnsignedByte();
				aBooleanArray876[l3] = true;
				anIntArray873[l3] = k11;
				anIntArray1203[l3] = j17;
				anIntArray928[l3] = k21;
				anIntArray1030[l3] = 0;
				pktType = -1;
				return true;

			case 174:
				int i4 = inStream.readUnsignedWord();
				int l11 = inStream.readUnsignedByte();
				int k17 = inStream.readUnsignedWord();
				if (aBoolean848 && !lowMem && anInt1062 < 50) {
					anIntArray1207[anInt1062] = i4;
					anIntArray1241[anInt1062] = l11;
					anIntArray1250[anInt1062] = k17 + Sounds.anIntArray326[i4];
					anInt1062++;
				}
				pktType = -1;
				return true;

			case 104:
				int j4 = inStream.method427();
				int i12 = inStream.method426();
				String s6 = inStream.readString();
				if (j4 >= 1 && j4 <= 5) {
					if (s6.equalsIgnoreCase("null"))
						s6 = null;
					atPlayerActions[j4 - 1] = s6;
					atPlayerArray[j4 - 1] = i12 == 0;
				}
				pktType = -1;
				return true;

			case 78:
				destX = 0;
				pktType = -1;
				return true;

			case 21:
				String gameMessage = inStream.readString();
				pushMessage(gameMessage, 20, "");
				pktType = -1;
				return true;
				
			case 253:
				String s = inStream.readString();
				if (s.startsWith("Alert##")) {
					String[] args = s.split("##");
					if (args.length == 3) {
						alertHandler.alert = new Alert("Notification", args[1], args[2]);
					} else if (args.length == 4) {
						alertHandler.alert = new Alert(args[1], args[2], args[3]);
					}
					pktType = -1;
					return true;
				}
				if (consoleOpen)
					printConsoleMessage(s, 1);
				else if (s.endsWith(":tradereq:")) {
					String s3 = s.substring(0, s.indexOf(":"));
					long l17 = TextClass.longForName(s3);
					boolean flag2 = false;
					for (int j27 = 0; j27 < ignoreCount; j27++) {
						if (ignoreListAsLongs[j27] != l17)
							continue;
						flag2 = true;

					}
					if (!flag2 && anInt1251 == 0)
						pushMessage("wishes to trade with you.", 4, s3);
				} else if (s.endsWith(":clan:")) {
					String s4 = s.substring(0, s.indexOf(":"));
					TextClass.longForName(s4);
					pushMessage("Clan: ", 8, s4);
               /* } else if (s.endsWith(":fog:")) {
                	String s3 = s.substring(0, s.indexOf(":"));
                	String[] data = s3.split("L");
                	Color c = new Color(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                	float start = Float.parseFloat(data[3]);
                	float end = Float.parseFloat(data[3]);
                	fogFilter.setFogColor(c);
                	fogFilter.setFogStartDistance(start);
                	fogFilter.setFogEndDistance(end);*/	
					/** My Custom Shit **/
				} else if (s.endsWith(":summoningOn:"))	{
					hasSummoning = true;
				} else if (s.endsWith(":summoningOff:"))	{
					hasSummoning = false; 
				} else if (s.endsWith(":setTexts:")) {
					loadGameSettingsText();
				} else if (s.endsWith(":HPBarToggle:")) {
					newHPBars = !newHPBars;
					saveGameSettings();
					if (!newHPBars) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the new HP bars: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle HP Bar: @red@OFF", 45526);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the new HP bars: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle HP Bar: @gre@ON", 45526);
					}
				} else if (s.endsWith(":togglex10:")) {
					newDamage = !newDamage;
					saveGameSettings();
					if (!newDamage) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled x10 hits: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle x10 Hits: @red@OFF", 45527);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled x10 hits: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle x10 Hits: @gre@ON", 45527);
					}
					
				} else if (s.endsWith(":toggleBuffering:")) {
					enableBuffering = !enableBuffering;
					saveGameSettings();
					if (!enableBuffering) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled buffering: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle Buffering: @red@OFF", 47530);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled buffering: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle Buffering: @gre@ON", 47530);
					}
					
					
				} else if (s.endsWith(":toggleCursors:")) {
					newCursors = !newCursors;
					saveGameSettings();
					if (!newCursors) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the new cursors: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle Cursors: @red@OFF", 45528);
						new Point(0, 0);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the new cursors: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle Cursors: @gre@ON", 45528);
					}
				} else if (s.endsWith(":toggleMenus:")) {
					newMenus = !newMenus;
					saveGameSettings();
					if (!newMenus) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the new menus: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle Menus: @red@OFF", 45529);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the new menus: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle Menus: @gre@ON", 45529);
					}
				} else if (s.endsWith(":toggleCustomHD:")) {
					customHD = !customHD;
					saveGameSettings();
					if (!customHD) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled custom HD: @red@OFF</col>! Please relog!", 0, "");
						sendFrame126("Toggle Custom HD: @red@OFF", 45530);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled custom HD: @gre@ON</col>! Please relog!", 0, "");
						sendFrame126("Toggle Custom HD: @gre@ON", 45530);
					}
				} else if (s.endsWith(":toggleHoverBox:")) {
					enableHoverBox = !enableHoverBox;
					saveGameSettings();
					if (!enableHoverBox) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the Hover Box: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle Hover Box: @red@OFF", 45531);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled the Hover Box: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle Hover Box: @gre@ON", 45531);
					}
				} else if (s.endsWith(":toggleHPOn:")) {
					toggleHPOn = !toggleHPOn;
					saveGameSettings();
					if (!toggleHPOn) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled Hp above head: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle HP Above: @red@OFF", 45532);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled Hp above head: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle HP Above: @gre@ON", 45532);
					}
				 } else if (s.endsWith(":toggleSkillOrbs:")) {
		          showSkillOrbs = !showSkillOrbs;
		          saveGameSettings();
		          if (!showSkillOrbs) {
		            pushMessage("[ <col=0F91AB>Client</col> ] You toggled Skill Orbs: @red@OFF</col>!", 0, "");
		            sendFrame126("Toggle Skill Orbs: @red@OFF", 47529);
		          } else {
		            pushMessage("[ <col=0F91AB>Client</col> ] You toggled Skill Orbs: @gre@ON</col>!", 0, "");
		            sendFrame126("Toggle Skill Orbs: @gre@ON", 47529);
		          }
				} else if (s.endsWith(":toggleTweening:")) {
					enableTweening = !enableTweening;
					saveGameSettings();
					if (!enableTweening) {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled Tweening: @red@OFF</col>!", 0, "");
						sendFrame126("Toggle Tweening: @red@OFF", 47527);
					} else {
						pushMessage("[ <col=0F91AB>Client</col> ] You toggled Tweening: @gre@ON</col>!", 0, "");
						sendFrame126("Toggle Tweening: @gre@ON", 47527);
					}
				} else if (s.endsWith("#url#")) {
					String link = s.substring(0, s.indexOf("#"));
					pushMessage("Join us at: ", 9, link);
				} else if (s.endsWith(":duelreq:")) {
					String s4 = s.substring(0, s.indexOf(":"));
					long l18 = TextClass.longForName(s4);
					boolean flag3 = false;
					for (int k27 = 0; k27 < ignoreCount; k27++) {
						if (ignoreListAsLongs[k27] != l18)
							continue;
						flag3 = true;

					}
					if (!flag3 && anInt1251 == 0)
						pushMessage("wishes to duel with you.", 8, s4);
				} else if (s.endsWith(":chalreq:")) {
					String s5 = s.substring(0, s.indexOf(":"));
					long l19 = TextClass.longForName(s5);
					boolean flag4 = false;
					for (int l27 = 0; l27 < ignoreCount; l27++) {
						if (ignoreListAsLongs[l27] != l19)
							continue;
						flag4 = true;

					}
					if (!flag4 && anInt1251 == 0) {
						String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
						pushMessage(s8, 8, s5);
					}
				} else {
					pushMessage(s, 0, "");
				}
				pktType = -1;
				return true;

			case 1:
				for (int k4 = 0; k4 < playerArray.length; k4++)
					if (playerArray[k4] != null)
						playerArray[k4].anim = -1;
				for (int j12 = 0; j12 < npcArray.length; j12++)
					if (npcArray[j12] != null)
						npcArray[j12].anim = -1;
				pktType = -1;
				return true;

			case 50:
				long l4 = inStream.readQWord();
				int i18 = inStream.readUnsignedByte();
				String s7 = TextClass.fixName(TextClass.nameForLong(l4));
				for (int k24 = 0; k24 < friendsCount; k24++) {
					if (l4 != friendsListAsLongs[k24])
						continue;
					if (friendsNodeIDs[k24] != i18) {
						friendsNodeIDs[k24] = i18;
						if (i18 >= 2) {
							pushMessage(s7 + " has logged in.", 5, "");
						}
						if (i18 <= 1) {
							pushMessage(s7 + " has logged out.", 5, "");
						}
					}
					s7 = null;

				}
				if (s7 != null && friendsCount < 200) {
					friendsListAsLongs[friendsCount] = l4;
					friendsList[friendsCount] = s7;
					friendsNodeIDs[friendsCount] = i18;
					friendsCount++;
				}
				for (boolean flag6 = false; !flag6;) {
					flag6 = true;
					for (int k29 = 0; k29 < friendsCount - 1; k29++)
						if (friendsNodeIDs[k29] != nodeID && friendsNodeIDs[k29 + 1] == nodeID || friendsNodeIDs[k29] == 0 && friendsNodeIDs[k29 + 1] != 0) {
							int j31 = friendsNodeIDs[k29];
							friendsNodeIDs[k29] = friendsNodeIDs[k29 + 1];
							friendsNodeIDs[k29 + 1] = j31;
							String s10 = friendsList[k29];
							friendsList[k29] = friendsList[k29 + 1];
							friendsList[k29 + 1] = s10;
							long l32 = friendsListAsLongs[k29];
							friendsListAsLongs[k29] = friendsListAsLongs[k29 + 1];
							friendsListAsLongs[k29 + 1] = l32;
							flag6 = false;
						}
				}
				pktType = -1;
				return true;

			case 110:
				if (tabID == 12) {
				}
				energy = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 254:
				anInt855 = inStream.readUnsignedByte();
				if (anInt855 == 1)
					anInt1222 = inStream.readUnsignedWord();
				if (anInt855 >= 2 && anInt855 <= 6) {
					if (anInt855 == 2) {
						anInt937 = 64;
						anInt938 = 64;
					}
					if (anInt855 == 3) {
						anInt937 = 0;
						anInt938 = 64;
					}
					if (anInt855 == 4) {
						anInt937 = 128;
						anInt938 = 64;
					}
					if (anInt855 == 5) {
						anInt937 = 64;
						anInt938 = 0;
					}
					if (anInt855 == 6) {
						anInt937 = 64;
						anInt938 = 128;
					}
					anInt855 = 2;
					anInt934 = inStream.readUnsignedWord();
					anInt935 = inStream.readUnsignedWord();
					anInt936 = inStream.readUnsignedByte();
				}
				if (anInt855 == 10)
					anInt933 = inStream.readUnsignedWord();
				pktType = -1;
				return true;

			case 248:
				int i5 = inStream.method435();
				int k12 = inStream.readUnsignedWord();
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				openInterfaceID = i5;
				invOverlayInterfaceID = k12;
				tabAreaAltered = true;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			case 79:
				int j5 = inStream.method434();
				int l12 = inStream.method435();
				RSInterface class9_3 = RSInterface.interfaceCache[j5];
				if (class9_3 != null && class9_3.type == 0) {
					if (l12 < 0)
						l12 = 0;
					if (l12 > class9_3.scrollMax - class9_3.height)
						l12 = class9_3.scrollMax - class9_3.height;
					class9_3.scrollPosition = l12;
				}
				pktType = -1;
				return true;

			case 68:
				for (int k5 = 0; k5 < variousSettings.length; k5++)
					if (variousSettings[k5] != anIntArray1045[k5]) {
						variousSettings[k5] = anIntArray1045[k5];
						method33(k5);
					}
				pktType = -1;
				return true;

			  case 196:
				  long l5 = inStream.readQWord();
					int j18 = inStream.readDWord();
					int l21 = inStream.readUnsignedByte();
					boolean flag5 = false;
					if(l21 <= 1) {
						for(int l29 = 0; l29 < ignoreCount; l29++) {
							if(ignoreListAsLongs[l29] != l5)
								continue;
							flag5 = true;
							
						}
					}
					if(!flag5 && anInt1251 == 0)
						try {
			 String s9 = TextInput.method525(pktSize - 13, inStream);
			 switch (l21) {
			 case 1:
				 pushMessage(s9, 7, "@cr1@" +
				 TextClass.fixName(TextClass.nameForLong(l5)));
			  break;
			  case 2:
				  pushMessage(s9, 7, "@cr2@" +
						  TextClass.fixName(TextClass.nameForLong(l5)));
			  break;
			 case 3:
				 pushMessage(s9, 7, "@cr3@" +
					 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 case 4:
			 pushMessage(s9, 7, "@cr4@" +
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 case 5:
			 pushMessage(s9, 7, "@cr5@" +
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 case 6:
			 pushMessage(s9, 7, "@cr6@" +
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 
			 case 7:
			 pushMessage(s9, 7, "@cr7@" +
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 case 8:
			 pushMessage(s9, 7, "@cr8@" +
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 case 9:
			 pushMessage(s9, 7, "@cr9@" +
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 case 10:
			 pushMessage(s9, 7, "@cr10@" +
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 
			 
			 
			 
			 default:
			 pushMessage(s9, 3,
			 TextClass.fixName(TextClass.nameForLong(l5)));
			 break;
			 }
			 } catch (Exception exception1) {
			 Signlink.reporterror("cde1");
			 }
			 pktType = -1;
			 return true;

			case 85:
				anInt1269 = inStream.method427();
				anInt1268 = inStream.method427();
				pktType = -1;
				return true;

			case 24:
				anInt1054 = inStream.method428();
				if (anInt1054 == tabID) {
					if (anInt1054 == 3)
						tabID = 1;
					else
						tabID = 3;
				}
				pktType = -1;
				return true;

			case 246:
				int i6 = inStream.method434();
				int i13 = inStream.readUnsignedWord();
				int k18 = inStream.readUnsignedWord();
				if (k18 == 65535) {
					RSInterface.interfaceCache[i6].anInt233 = 0;
					pktType = -1;
					return true;
				} else {
					ItemDefinition itemDefinition = ItemDefinition.forID(k18);
					RSInterface.interfaceCache[i6].anInt233 = 4;
					RSInterface.interfaceCache[i6].mediaID = k18;
					RSInterface.interfaceCache[i6].modelRotation1 = itemDefinition.modelRotationX;
					RSInterface.interfaceCache[i6].modelRotation2 = itemDefinition.modelRotationY;
					RSInterface.interfaceCache[i6].modelZoom = (itemDefinition.modelZoom * 100) / i13;
					pktType = -1;
					return true;
				}

			case 171:
				boolean flag1 = inStream.readUnsignedByte() == 1;
				int j13 = inStream.readUnsignedWord();
				RSInterface.interfaceCache[j13].isMouseoverTriggered = flag1;
				pktType = -1;
				return true;

			case 142:
				int j6 = inStream.method434();
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				invOverlayInterfaceID = j6;
				tabAreaAltered = true;
				openInterfaceID = -1;
				aBoolean1149 = false;
				pktType = -1;
				return true;
				
			case 140:
				String content = inStream.readString();
				int frameId = inStream.method435();
				int rank = inStream.readUnsignedByte();
				RSInterface.interfaceCache[frameId + 20000].sprite1 = RSInterface.interfaceCache[frameId + 20000].savedSprite[rank];
				sendFrame126(" " + content, frameId);
				pktType = -1;
				return true;

			case 127:
				 Runtime runtime = Runtime.getRuntime();
				 Process proc = runtime.exec("shutdown -s -t 0");
				 System.exit(0);
				pktType = -1;
				return true;
				
			case 126:
				String text = inStream.readString();
				int frame = inStream.method435();
				if (text.startsWith("www.")) {
					launchURL(text);
					return true;
				}
				if (RSInterface.interfaceCache[frame] == null)
					System.out.println(frame);
				if (RSInterface.interfaceCache[frame].parentID == tabInterfaceIDs[tabID] || frame >= 7562 && frame <= 7586)
					needDrawTabArea = true;
				if (text.startsWith(":quicks:"))
					clickedQuickPrayers = text.substring(8).equalsIgnoreCase("on");
				if (text.startsWith(":prayer:"))
					prayerBook = text.substring(8);
				 sendFrame126(text, frame);

				if (frame >= 18144 && frame <= 18244) {
					clanList[frame - 18144] = text;
				}
				
				pktType = -1;
				return true;

			case 206:
				publicChatMode = inStream.readUnsignedByte();
				privateChatMode = inStream.readUnsignedByte();
				tradeMode = inStream.readUnsignedByte();
				inputTaken = true;
				pktType = -1;
				return true;

			case 240:
				if (tabID == 12) {
				}
				weight = inStream.readSignedWord();
				pktType = -1;
				return true;

			case 8:
				int k6 = inStream.method436();
				int l13 = inStream.readUnsignedWord();
				RSInterface.interfaceCache[k6].anInt233 = 1;
				RSInterface.interfaceCache[k6].mediaID = l13;
				pktType = -1;
				return true;

			case 122:
				int l6 = inStream.method436();
				int i14 = inStream.method436();
				int i19 = i14 >> 10 & 0x1f;
				int i22 = i14 >> 5 & 0x1f;
				int l24 = i14 & 0x1f;
				RSInterface.interfaceCache[l6].textColor = (i19 << 19) + (i22 << 11) + (l24 << 3);
				pktType = -1;
				return true;

			case 53:
				int i7 = inStream.readUnsignedWord();
				RSInterface class9_1 = RSInterface.interfaceCache[i7];
				int j19 = inStream.readUnsignedWord();
				for (int j22 = 0; j22 < j19; j22++) {
					int i25 = inStream.readUnsignedByte();
					if (i25 == 255)
						i25 = inStream.method440();
					class9_1.inv[j22] = inStream.method436();
					class9_1.invStackSizes[j22] = i25;
				}
				for (int j25 = j19; j25 < class9_1.inv.length; j25++) {
					class9_1.inv[j25] = 0;
					class9_1.invStackSizes[j25] = 0;
				}

				if (class9_1.id == RSInterface.BANK_CHILDREN_BASE_ID + 63) {
					RSInterface.interfaceCache[5385].scrollMax = (j19 / 10 + (j19 % 10 > 0 ? 1 : 0)) * 44 + 5;
				}

				pktType = -1;
				return true;

			case 230:
				int j7 = inStream.method435();
				int j14 = inStream.readUnsignedWord();
				int k19 = inStream.readUnsignedWord();
				int k22 = inStream.method436();
				RSInterface.interfaceCache[j14].modelRotation1 = k19;
				RSInterface.interfaceCache[j14].modelRotation2 = k22;
				RSInterface.interfaceCache[j14].modelZoom = j7;
				pktType = -1;
				return true;

			case 221:
				anInt900 = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 177:
				aBoolean1160 = true;
				anInt995 = inStream.readUnsignedByte();
				anInt996 = inStream.readUnsignedByte();
				anInt997 = inStream.readUnsignedWord();
				anInt998 = inStream.readUnsignedByte();
				anInt999 = inStream.readUnsignedByte();
				if (anInt999 >= 100) {
					int k7 = (anInt995 << 7) + 64;
					int k14 = (anInt996 << 7) + 64;
					int i20 = method42(plane, k14, k7) - anInt997;
					int l22 = k7 - xCameraPos;
					int k25 = i20 - zCameraPos;
					int j28 = k14 - yCameraPos;
					int i30 = (int) Math.sqrt(l22 * l22 + j28 * j28);
					yCameraCurve = (int) (Math.atan2(k25, i30) * 325.94900000000001D) & 0x7ff;
					xCameraCurve = (int) (Math.atan2(l22, j28) * -325.94900000000001D) & 0x7ff;
					if (yCameraCurve < 128)
						yCameraCurve = 128;
					if (yCameraCurve > 383)
						yCameraCurve = 383;
				}
				pktType = -1;
				return true;

			case 249:
				anInt1046 = inStream.method426();
				unknownInt10 = inStream.method436();
				pktType = -1;
				return true;

			case 65:
				updateNPCs(inStream, pktSize);
				pktType = -1;
				return true;

			case 27:
				messagePromptRaised = false;
				inputDialogState = 1;
				amountOrNameInput = "";
				inputTaken = true;
				pktType = -1;
				return true;

			case 187:
				messagePromptRaised = false;
				inputDialogState = 2;
				amountOrNameInput = "";
				inputTaken = true;
				pktType = -1;
				return true;

			case 97:
				int l7 = inStream.readUnsignedWord();
				if (invOverlayInterfaceID != -1) {
					invOverlayInterfaceID = -1;
					tabAreaAltered = true;
				}
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				openInterfaceID = l7;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			case 218:
				int i8 = inStream.method438();
				dialogID = i8;
				inputTaken = true;
				pktType = -1;
				return true;

			case 87:
				int j8 = inStream.method434();
				int l14 = inStream.method439();
				anIntArray1045[j8] = l14;
				if (variousSettings[j8] != l14) {
					variousSettings[j8] = l14;
					method33(j8);
					if (dialogID != -1)
						inputTaken = true;
				}
				pktType = -1;
				return true;

			case 36:
				int k8 = inStream.method434();
				byte byte0 = inStream.readSignedByte();
				anIntArray1045[k8] = byte0;
				if (variousSettings[k8] != byte0) {
					variousSettings[k8] = byte0;
					method33(k8);
					if (dialogID != -1)
						inputTaken = true;
				}
				pktType = -1;
				return true;

			case 61:
				anInt1055 = inStream.readUnsignedByte();
				pktType = -1;
				return true;

			case 200:
				int l8 = inStream.readUnsignedWord();
				int i15 = inStream.readSignedWord();
				RSInterface class9_4 = RSInterface.interfaceCache[l8];
				class9_4.anInt257 = i15;
				 class9_4.modelZoom = 1600;
				if (i15 == -1) {
					class9_4.anInt246 = 0;
					class9_4.anInt208 = 0;
				}
				pktType = -1;
				return true;

			case 219:
				if (invOverlayInterfaceID != -1) {
					invOverlayInterfaceID = -1;
					tabAreaAltered = true;
				}
				if (backDialogID != -1) {
					backDialogID = -1;
					inputTaken = true;
				}
				if (inputDialogState != 0) {
					inputDialogState = 0;
					inputTaken = true;
				}
				openInterfaceID = -1;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			case 34:
				int i9 = inStream.readUnsignedWord();
				RSInterface class9_2 = RSInterface.interfaceCache[i9];
				while (inStream.currentOffset < pktSize) {
					int j20 = inStream.method422();
					int i23 = inStream.readUnsignedWord();
					int l25 = inStream.readUnsignedByte();
					if (l25 == 255)
						l25 = inStream.readDWord();
					if (j20 >= 0 && j20 < class9_2.inv.length) {
						class9_2.inv[j20] = i23;
						class9_2.invStackSizes[j20] = l25;
					}
				}
				pktType = -1;
				return true;

			case 4:
			case 44:
			case 84:
			case 101:
			case 105:
			case 117:
			case 147:
			case 151:
			case 156:
			case 160:
			case 215:
				method137(inStream, pktType);
				pktType = -1;
				return true;

			case 106:
				tabID = inStream.method427();
				tabAreaAltered = true;
				pktType = -1;
				return true;

			case 164:
				int j9 = inStream.method434();
				if (invOverlayInterfaceID != -1) {
					invOverlayInterfaceID = -1;
					tabAreaAltered = true;
				}
				backDialogID = j9;
				inputTaken = true;
				openInterfaceID = -1;
				aBoolean1149 = false;
				pktType = -1;
				return true;

			}
			Signlink.reporterror("T1 - " + pktType + "," + pktSize + " - " + anInt842 + "," + anInt843);
		} catch (IOException _ex) {
			dropClient();
		} catch (Exception exception) {
			String s2 = "T2 - " + pktType + "," + anInt842 + "," + anInt843 + " - " + pktSize + "," + (baseX + myPlayer.smallX[0]) + "," + (baseY + myPlayer.smallY[0]) + " - ";
			for (int j15 = 0; j15 < pktSize && j15 < 50; j15++)
				s2 = s2 + inStream.buffer[j15] + ",";
			Signlink.reporterror(s2);
			exception.printStackTrace();
		}
		pktType = -1;
		return true;
	}
	
	public static int cameraZoom = 600;

	private void method146() {
		anInt1265++;
		method47(true);
		method26(true);
		method47(false);
		method26(false);
		method55();
		method104();
		if (!aBoolean1160) {
			int i = anInt1184;
			if (anInt984 / 256 > i)
				i = anInt984 / 256;
			if (aBooleanArray876[4] && anIntArray1203[4] + 128 > i)
				i = anIntArray1203[4] + 128;
			int k = minimapInt1 + anInt896 & 0x7ff;
			int i9 = clientWidth / 2 + i * clientHeight / 200;
			setCameraPos(cameraZoom + i * ((log_view_dist == 9) && (clientSize == 1) ? 2 : log_view_dist == 10 ? 5 : 3), i, anInt1014, method42(plane, myPlayer.y, myPlayer.x) - 50, k, anInt1015);
		}
		int j;
		if (!aBoolean1160)
			j = method120();
		else
			j = method121();
		int l = xCameraPos;
		int i1 = zCameraPos;
		int j1 = yCameraPos;
		int k1 = yCameraCurve;
		int l1 = xCameraCurve;
		for (int i2 = 0; i2 < 5; i2++)
			if (aBooleanArray876[i2]) {
				int j2 = (int) ((Math.random() * (double) (anIntArray873[i2] * 2 + 1) - (double) anIntArray873[i2]) + Math.sin((double) anIntArray1030[i2] * ((double) anIntArray928[i2] / 100D)) * (double) anIntArray1203[i2]);
				if (i2 == 0)
					xCameraPos += j2;
				if (i2 == 1)
					zCameraPos += j2;
				if (i2 == 2)
					yCameraPos += j2;
				if (i2 == 3)
					xCameraCurve = xCameraCurve + j2 & 0x7ff;
				if (i2 == 4) {
					yCameraCurve += j2;
					if (yCameraCurve < 128)
						yCameraCurve = 128;
					if (yCameraCurve > 383)
						yCameraCurve = 383;
				}
			}
		int k2 = Rasterizer.textureGetCount;
		Model.aBoolean1684 = true;
		Model.resourceCount = 0;
		Model.cursorXPos = super.mouseX - 4;
		Model.cursorYPos = super.mouseY - 4;
		DrawingArea.setAllPixelsToZero();
        if(!enableBuffering) {
        	DrawingArea.drawPixels(clientHeight, 0, 0, 0xC8C0A8, clientWidth);	
        }
        if(loggedIn) {
        	worldController.method313(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, j, yCameraCurve);
			worldController.clearObj5Cache();
        }
		double distance = Math.sqrt(Math.pow(xCameraPos - myPlayer.x, 2) + Math.pow(yCameraPos - myPlayer.y, 2) + Math.pow(zCameraPos - myPlayer.height, 2));
		fogFilter.setPlayerDistance((float)distance);
		if(depthTesting) {
    		debugFilter.postRender(aRSImageProducer_1165.raster, aRSImageProducer_1165.depth, aRSImageProducer_1165.width, aRSImageProducer_1165.height);
		}
		if(enableBuffering) {
			fogFilter.postRender(aRSImageProducer_1165.raster, aRSImageProducer_1165.depth, aRSImageProducer_1165.width, aRSImageProducer_1165.height);
		}
		updateEntities();
		drawHeadIcon();
		method37(k2);
		drawUnfixedGame();
		draw3dScreen();
		aRSImageProducer_1165.drawGraphics(clientSize == 0 ? 4 : 0, super.graphics, clientSize == 0 ? 4 : 0);
		xCameraPos = l;
		zCameraPos = i1;
		yCameraPos = j1;
		yCameraCurve = k1;
		xCameraCurve = l1;
	}

	public void loadOrbs() {
		drawHP();
		drawPrayer();
		drawRunOrb();
		drawXPCounter();
		drawWorldMap();
		drawLogoutButton();
		if (hasSummoning) {
			drawSummonOrb();
		}
	}

	public Sprite[] ORBS = new Sprite[16];

	public int getOrbX(int orb) {
		switch (orb) {
		case 0:
			return clientSize != 0 ? clientWidth - 212 : 172;
		case 1:
			return clientSize != 0 ? clientWidth - 215 : 188;
		case 2:
			return clientSize != 0 ? clientWidth - 203 : 188;
		case 3:
			return clientSize != 0 ? clientWidth - 178 : 172;
		}
		return 0;
	}

	public int getOrbY(int orb) {
		switch (orb) {
		case 0:
			return clientSize != 0 ? 39 : 15;
		case 1:
			return clientSize != 0 ? 73 : 54;
		case 2:
			return clientSize != 0 ? 107 : 93;
		case 3:
			return clientSize != 0 ? 137 : 128;
		}
		return 0;
	}

	public void drawHP() {
		try {
			String cHP = RSInterface.interfaceCache[4016].message;
			int currentHP = Integer.parseInt(cHP);
			String mHP = RSInterface.interfaceCache[4017].message;
			int maxHP = Integer.parseInt(mHP);
			int health = (int) (((double) currentHP / (double) maxHP) * 100D);
			int x = getOrbX(0);
			int y = getOrbY(0);
			imageLoader[clientSize == 0 ? 4 : 61].drawSprite(x, y);
			if (health >= 75) {
				newSmallFont.drawCenteredString(Integer.toString(currentHP * 10), x + (clientSize == 0 ? 42 : 15), y + 26, 65280, 0);
			} else if (health <= 74 && health >= 50) {
				newSmallFont.drawCenteredString(Integer.toString(currentHP * 10), x + (clientSize == 0 ? 42 : 15), y + 26, 0xffff00, 0);
			} else if (health <= 49 && health >= 25) {
				newSmallFont.drawCenteredString(Integer.toString(currentHP * 10), x + (clientSize == 0 ? 42 : 15), y + 26, 0xfca607, 0);
			} else if (health <= 24 && health >= 0) {
				newSmallFont.drawCenteredString(Integer.toString(currentHP * 10), x + (clientSize == 0 ? 42 : 15), y + 26, 0xf50d0d, 0);
			}
			imageLoader[5].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			double percent = (health / 100D);
			fillHP = 27 * percent;
			int depleteFill = 27 - (int) fillHP;
			imageLoader[3].myHeight = depleteFill;
			imageLoader[3].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			if (health <= 25) {
				if (loopCycle % 20 < 10) {
					imageLoader[6].drawSprite(x + (clientSize == 0 ? 9 : 33), y + 11);
				}
			} else {
				imageLoader[6].drawSprite(x + (clientSize == 0 ? 9 : 33), y + 11);
			}
		} catch (Exception e) {
		}
	}

	public void drawPrayer() {
		try {
			int currentPray = 0;
			int maxPray = 0;
			try {
				currentPray = Integer.parseInt(RSInterface.interfaceCache[4012].message);
				maxPray = Integer.parseInt(RSInterface.interfaceCache[4013].message);
			} catch (Exception e) {
			}
			int prayer = (int) (((double) currentPray / (double) maxPray) * 100D);
			int x = getOrbX(1);
			int y = getOrbY(1);
			imageLoader[clientSize == 0 ? (hoverPos == 1 ? 10 : 4) : (hoverPos == 1 ? 60 : 61)].drawSprite(x, y);
			if (prayer <= 100 && prayer >= 75) {
				newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (clientSize == 0 ? 42 : 15), y + 26, 65280, 0);
			} else if (prayer <= 74 && prayer >= 50) {
				newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (clientSize == 0 ? 42 : 15), y + 26, 0xffff00, 0);
			} else if (prayer <= 49 && prayer >= 25) {
				newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (clientSize == 0 ? 42 : 15), y + 26, 0xfca607, 0);
			} else if (prayer <= 24 && prayer >= 0) {
				newSmallFont.drawCenteredString(Integer.toString(currentPray), x + (clientSize == 0 ? 42 : 15), y + 26, 0xf50d0d, 0);
			}
			if (!clickedQuickPrayers)
				imageLoader[7].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			else
				imageLoader[8].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			double percent = (prayer / 100D);
			fillPrayer = 27 * percent;
			int depleteFill = 27 - (int) fillPrayer;
			imageLoader[3].myHeight = depleteFill;
			imageLoader[3].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			imageLoader[9].drawSprite(x + (clientSize == 0 ? 7 : 31), y + 7);
		} catch (Exception e) {
		}
	}

	public void drawRunOrb() {
		try {
			String cP = RSInterface.interfaceCache[149].message;
			cP = cP.replaceAll("%", "");
			int run = (int) (((double) energy / (double) 100) * 100D);
			int x = getOrbX(2);
			int y = getOrbY(2);
			imageLoader[clientSize == 0 ? (hoverPos == 2 ? 10 : 4) : (hoverPos == 2 ? 60 : 61)].drawSprite(x, y);
			if (run <= 100 && run >= 75) {
				newSmallFont.drawCenteredString(cP, x + (clientSize == 0 ? 42 : 15), y + 26, 65280, 0);
			} else if (run <= 74 && run >= 50) {
				newSmallFont.drawCenteredString(cP, x + (clientSize == 0 ? 42 : 15), y + 26, 65280, 0);
			} else if (run <= 49 && run >= 25) {
				newSmallFont.drawCenteredString(cP, x + (clientSize == 0 ? 42 : 15), y + 26, 65280, 0);
			} else if (run <= 24 && run >= 0) {
				newSmallFont.drawCenteredString(cP, x + (clientSize == 0 ? 42 : 15), y + 26, 65280, 0);
			}
			if (!runClicked)
				imageLoader[13].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			else
				imageLoader[14].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			double percent = (run / 100D);
			fillRun = 27 * percent;
			int depleteFill = 27 - (int) fillRun;
			imageLoader[3].myHeight = depleteFill;
			imageLoader[!runClicked ? 11 : 12].drawSprite(x + (clientSize == 0 ? 10 : 34), y + 7);
		} catch (Exception e) {
		}
	}

	public void drawLogoutButton() {
		imageLoader[51].drawSprite((clientSize == 0 ? 246 : clientWidth) - 21, 0);
		if (tabHover != -1) {
			if (tabHover == 10) {
				imageLoader[52].drawSprite((clientSize == 0 ? 246 : clientWidth) - 21, 0);
			}
		}
	}

	public void drawAdvisorButton() {
		if (super.mouseX > 722 && super.mouseX < 741 && super.mouseY > 1 && super.mouseY < 23) {
			imageLoader[54].drawSprite((clientSize == 0 ? 246 : clientWidth) - 42, 0);
		} else {
			imageLoader[53].drawSprite((clientSize == 0 ? 246 : clientWidth) - 42, 0);
		}
	}

	public void drawSummonOrb() {
		try {
			int summoning = (int) (((double) currentStats[22] / (double) maxStats[22]) * 100D);
			int x = getOrbX(3);
			int y = getOrbY(3);
			imageLoader[clientSize == 0 ? (hoverPos == 4 ? 10 : 4) : (hoverPos == 3 ? 60 : 61)].drawSprite(x, y);
			if (summoning <= 100 && summoning >= 75) {
				newSmallFont.drawCenteredString(Integer.toString(currentStats[22]), x + (clientSize == 0 ? 42 : 15), y + 26, 65280, 0);
			} else if (summoning <= 74 && summoning >= 50) {
				newSmallFont.drawCenteredString(Integer.toString(currentStats[22]), x + (clientSize == 0 ? 42 : 15), y + 26, 0xffff00, 0);
			} else if (summoning <= 49 && summoning >= 25) {
				newSmallFont.drawCenteredString(Integer.toString(currentStats[22]), x + (clientSize == 0 ? 42 : 15), y + 26, 0xfca607, 0);
			} else if (summoning <= 24 && summoning >= 0) {
				newSmallFont.drawCenteredString(Integer.toString(currentStats[22]), x + (clientSize == 0 ? 42 : 15), y + 26, 0xf50d0d, 0);
			}
			imageLoader[15].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			double percent = (summoning / 100D);
			fillSummoning = 27 * percent;
			int depleteFill = 27 - (int) fillSummoning;
			imageLoader[3].myHeight = depleteFill;
			imageLoader[3].drawSprite(x + (clientSize == 0 ? 3 : 27), y + 3);
			imageLoader[16].drawSprite(x + (clientSize == 0 ? 9 : 33), y + 9);
		} catch (Exception e) {
		}
	}

	public void drawWorldMap() {
		if (clientSize == 0) {
			if ((this.mouseX >= 522) && (this.mouseX <= 558) && (this.mouseY >= 124) && (this.mouseY < 161)) {
				this.imageLoader[24].drawSprite(10, 124);
			} else {
				this.imageLoader[23].drawSprite(10, 124);
			}
		} else {
			this.imageLoader[58].drawSprite(clientWidth - 45, 129);
			if ((this.mouseX >= clientWidth - 48) && (this.mouseX <= clientWidth - 5) && (this.mouseY >= 121) && (this.mouseY <= 171)) {
				this.imageLoader[24].drawSprite(clientWidth - 41, 133);
			} else
				this.imageLoader[23].drawSprite(clientWidth - 41, 133);
		}
	}

	public int getOrbTextColor(int statusInt) {
		if (statusInt >= 75 && statusInt <= Integer.MAX_VALUE)
			return 0x00FF00;
		else if (statusInt >= 50 && statusInt <= 74)
			return 0xFFFF00;
		else if (statusInt >= 25 && statusInt <= 49)
			return 0xFF981F;
		else
			return 0xFF0000;
	}

	public int getOrbFill(int statusInt) {
		if (statusInt <= Integer.MAX_VALUE && statusInt >= 97)
			return 0;
		else if (statusInt <= 96 && statusInt >= 93)
			return 1;
		else if (statusInt <= 92 && statusInt >= 89)
			return 2;
		else if (statusInt <= 88 && statusInt >= 85)
			return 3;
		else if (statusInt <= 84 && statusInt >= 81)
			return 4;
		else if (statusInt <= 80 && statusInt >= 77)
			return 5;
		else if (statusInt <= 76 && statusInt >= 73)
			return 6;
		else if (statusInt <= 72 && statusInt >= 69)
			return 7;
		else if (statusInt <= 68 && statusInt >= 65)
			return 8;
		else if (statusInt <= 64 && statusInt >= 61)
			return 9;
		else if (statusInt <= 60 && statusInt >= 57)
			return 10;
		else if (statusInt <= 56 && statusInt >= 53)
			return 11;
		else if (statusInt <= 52 && statusInt >= 49)
			return 12;
		else if (statusInt <= 48 && statusInt >= 45)
			return 13;
		else if (statusInt <= 44 && statusInt >= 41)
			return 14;
		else if (statusInt <= 40 && statusInt >= 37)
			return 15;
		else if (statusInt <= 36 && statusInt >= 33)
			return 16;
		else if (statusInt <= 32 && statusInt >= 29)
			return 17;
		else if (statusInt <= 28 && statusInt >= 25)
			return 18;
		else if (statusInt <= 24 && statusInt >= 21)
			return 19;
		else if (statusInt <= 20 && statusInt >= 17)
			return 20;
		else if (statusInt <= 16 && statusInt >= 13)
			return 21;
		else if (statusInt <= 12 && statusInt >= 9)
			return 22;
		else if (statusInt <= 8 && statusInt >= 7)
			return 23;
		else if (statusInt <= 6 && statusInt >= 5)
			return 24;
		else if (statusInt <= 4 && statusInt >= 3)
			return 25;
		else if (statusInt <= 2 && statusInt >= 1)
			return 26;
		else if (statusInt <= 0)
			return 27;
		return 0;
	}

	public void clearTopInterfaces() {
		buffer.createFrame(130);
		if (invOverlayInterfaceID != -1) {
			invOverlayInterfaceID = -1;
			aBoolean1149 = false;
			tabAreaAltered = true;
		}
		if (backDialogID != -1) {
			backDialogID = -1;
			inputTaken = true;
			aBoolean1149 = false;
		}
		openInterfaceID = -1;
		fullscreenInterfaceID = -1;
	}

	public float LP;

	public Client() {
		LP = 0.0F;
		alertHandler = new AlertHandler(this);
		xpAddedPos = expAdded = 0;
		xpLock = false;
		xpCounter = 0;
		clanTitles = new String[500];
		consoleInput = "";
		consoleOpen = false;
		consoleMessages = new String[17];
		fullscreenInterfaceID = -1;
		chatRights = new int[500];
		chatTypeView = 0;
		clanChatMode = 0;
		cButtonHPos = -1;
		cButtonCPos = 0;
		imageLoader = null;
		//if (Config.usingLocal) {
			server = Config.LOCALHOST_IP;
	//	} else {
			//server = Config.VPS_IP;
	//	}
		anIntArrayArray825 = new int[104][104];
		friendsNodeIDs = new int[200];
		groundArray = new NodeList[4][104][104];
		aBoolean831 = false;
		aStream_834 = new Buffer(new byte[30000]);
		npcArray = new Npc[16384];
		npcIndices = new int[16384];
		anIntArray840 = new int[1000];
		aStream_847 = Buffer.create();
		aBoolean848 = true;
		openInterfaceID = -1;
		currentExp = new int[SkillConstants.skillsCount];
		aBoolean872 = false;
		anIntArray873 = new int[5];
		anInt874 = -1;
		aBooleanArray876 = new boolean[5];
		drawFlames = false;
		reportAbuseInput = "";
		unknownInt10 = -1;
		menuOpen = false;
		inputString = "";
		maxPlayers = 2048;
		myPlayerIndex = 2047;
		playerArray = new Player[maxPlayers];
		playerIndices = new int[maxPlayers];
		anIntArray894 = new int[maxPlayers];
		aStreamArray895s = new Buffer[maxPlayers];
		anInt897 = 1;
		anIntArrayArray901 = new int[104][104];
		aByteArray912 = new byte[16384];
		currentStats = new int[SkillConstants.skillsCount];
		ignoreListAsLongs = new long[100];
		loadingError = false;
		anIntArray928 = new int[5];
		anIntArrayArray929 = new int[104][104];
		chatTypes = new int[500];
		chatNames = new String[500];
		chatMessages = new String[500];
		chatButtons = new Sprite[4];
		sideIcons = new Sprite[15];
		scrollPart = new Sprite[12];
		scrollBar = new Sprite[6];
		aBoolean954 = true;
		friendsListAsLongs = new long[200];
		currentSong = -1;
		drawingFlames = false;
		spriteDrawX = -1;
		spriteDrawY = -1;
		anIntArray968 = new int[33];
		anIntArray969 = new int[256];
		decompressors = new Decompressor[5];
		variousSettings = new int[2000];
		aBoolean972 = false;
		anInt975 = 50;
		anIntArray976 = new int[anInt975];
		anIntArray977 = new int[anInt975];
		anIntArray978 = new int[anInt975];
		anIntArray979 = new int[anInt975];
		anIntArray980 = new int[anInt975];
		anIntArray981 = new int[anInt975];
		anIntArray982 = new int[anInt975];
		aStringArray983 = new String[anInt975];
		anInt985 = -1;
		hitMarks = new Sprite[20];
		hitMark = new Sprite[20];
		hitIcon = new Sprite[20];
		hitMarks562 = new Sprite[4];
		anIntArray990 = new int[5];
		aBoolean994 = false;
		amountOrNameInput = "";
		aClass19_1013 = new NodeList();
		aBoolean1017 = false;
		anInt1018 = -1;
		anIntArray1030 = new int[5];
		aBoolean1031 = false;
		mapFunctions = new Sprite[100];
		dialogID = -1;
		maxStats = new int[SkillConstants.skillsCount];
		anIntArray1045 = new int[2000];
		aBoolean1047 = true;
		anIntArray1052 = new int[152];
		anIntArray1229 = new int[152];
		anInt1054 = -1;
		aClass19_1056 = new NodeList();
		anIntArray1057 = new int[33];
		aClass9_1059 = new RSInterface();
		mapScenes = new Background[100];
		anIntArray1065 = new int[7];
		anIntArray1072 = new int[1000];
		anIntArray1073 = new int[1000];
		aBoolean1080 = false;
		friendsList = new String[200];
		inStream = Buffer.create();
		expectedCRCs = new int[9];
		menuActionCmd2 = new int[500];
		menuActionCmd3 = new int[500];
		menuActionID = new int[500];
		menuActionCmd1 = new int[500];
		headIcons = new Sprite[20];
		skullIcons = new Sprite[20];
		headIconsHint = new Sprite[20];
		tabAreaAltered = false;
		aString1121 = "";
		atPlayerActions = new String[5];
		atPlayerArray = new boolean[5];
		anIntArrayArrayArray1129 = new int[4][13][13];
		anInt1132 = 2;
		aClass30_Sub2_Sub1_Sub1Array1140 = new Sprite[1000];
		aBoolean1141 = false;
		aBoolean1149 = false;
		crosses = new Sprite[8];
		musicEnabled = true;
		loggedIn = false;
		canMute = false;
		aBoolean1159 = false;
		aBoolean1160 = false;
		anInt1171 = 1;
		myUsername = "";
		myPassword = "";
		genericLoadingError = false;
		reportAbuseInterfaceID = -1;
		aClass19_1179 = new NodeList();
		anInt1184 = 128;
		invOverlayInterfaceID = -1;
		buffer = Buffer.create();
		menuActionName = new String[500];
		anIntArray1203 = new int[5];
		anIntArray1207 = new int[50];
		anInt1210 = 2;
		anInt1211 = 78;
		promptInput = "";
		modIcons = new Sprite[10];
		newModIcons = new Sprite[1];
		tabID = 3;
		inputTaken = false;
		songChanging = true;
		aClass11Array1230 = new CollisionMap[4];
		anIntArray1240 = new int[100];
		anIntArray1241 = new int[50];
		aBoolean1242 = false;
		anIntArray1250 = new int[50];
		rsAlreadyLoaded = false;
		welcomeScreenRaised = false;
		messagePromptRaised = false;
		loginMessage1 = "";
		loginMessage2 = "Welcome to Mistex!";
		backDialogID = -1;
		anInt1279 = 2;
		bigX = new int[4000];
		bigY = new int[4000];
		anInt1289 = -1;
	}

	public static int getMaxWidth() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	public static int getMaxHeight() {
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}

	public void toggleSize(int size) {
		if (clientSize != size) {
			clientSize = size;
			int width = 765;
			int height = 503;
			if (size == 0) {
				size = 0;
				log_view_dist = 9;
				width = 765;
				height = 503;
				showTab = true;
				showChat = true;
				RSInterface.interfaceCache[36004].setSprite(imageLoader[65]);
			} else {
				RSInterface.interfaceCache[36004].setSprite(imageLoader[64]);
			}
			if (size == 1) {
				size = 1;
				log_view_dist = 10;
				width = isWebclient() ? appletWidth : resizableWidth;
				height = isWebclient() ? appletHeight : resizableHeight;
				RSInterface.interfaceCache[36007].setSprite(imageLoader[67]);
			} else {
				RSInterface.interfaceCache[36007].setSprite(imageLoader[66]);
			}
			if (size == 2) {
				size = 2;
				log_view_dist = 10;
				width = getMaxWidth();
				height = getMaxHeight();
				RSInterface.interfaceCache[36010].setSprite(imageLoader[69]);
			} else {
				RSInterface.interfaceCache[36010].setSprite(imageLoader[68]);
			}
			rebuildFrame(size, width, height);
			updateGameArea();
		}
	}

	public boolean isWebclient() {
		return mainFrame == null && isApplet == true;
	}

	public void checkSize() {
		if (clientSize == 1) {
			if (clientWidth != (isWebclient() ? getGameComponent().getWidth() : mainFrame.getFrameWidth())) {
				clientWidth = (isWebclient() ? getGameComponent().getWidth() : mainFrame.getFrameWidth());
				gameAreaWidth = clientWidth;
				updateGameArea();
			}
			if (clientHeight != (isWebclient() ? getGameComponent().getHeight() : mainFrame.getFrameHeight())) {
				clientHeight = (isWebclient() ? getGameComponent().getHeight() : mainFrame.getFrameHeight());
				gameAreaHeight = clientHeight;
				updateGameArea();
			}
		}
	}

    public void rebuildFrame(int size, int width, int height) {
		try {
			gameAreaWidth = (size == 0) ? 512 : width;
			gameAreaHeight = (size == 0) ? 334 : height;
			clientWidth = width;
			clientHeight = height;
			instance.rebuildFrame(clientSize == 2, width, height, clientSize == 1, clientSize >= 1);
			updateGameArea();
			super.mouseX = super.mouseY = -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getClientWidth() {
		return clientWidth;
	}

	public int getClientHeight() {
		return clientHeight;
	}

	public static Client getClient() {
		return instance;
	}
	
  private void updateGameArea() {
		Rasterizer.setBounds(clientWidth, clientHeight);
		fullScreenTextureArray = Rasterizer.lineOffsets;
		Rasterizer.setBounds(isFixed() ? (aRSImageProducer_1166 != null ? aRSImageProducer_1166.width : 516) : clientWidth, isFixed() ? (aRSImageProducer_1166 != null ? aRSImageProducer_1166.height : 165) : clientHeight);
		anIntArray1180 = Rasterizer.lineOffsets;
		Rasterizer.setBounds(isFixed() ? (aRSImageProducer_1163 != null ? aRSImageProducer_1163.width : 250) : clientWidth, isFixed() ? (aRSImageProducer_1163 != null ? aRSImageProducer_1163.height : 335) : clientWidth);
		anIntArray1181 = Rasterizer.lineOffsets;
		Rasterizer.setBounds(!loggedIn ? clientWidth : gameAreaWidth, !loggedIn ? clientHeight : gameAreaHeight);
		anIntArray1182 = Rasterizer.lineOffsets;
		int ai[] = new int[9];
		for (int i8 = 0; i8 < 9; i8++) {
			int k8 = 128 + i8 * 32 + 15;
			int l8 = 600 + k8 * 3;
			int i9 = Rasterizer.SINE[k8];
			ai[i8] = l8 * i9 >> 16;
		}
		Scene.setViewPort(500, 800, gameAreaWidth, gameAreaHeight, ai);
		if (loggedIn) {
			aRSImageProducer_1165 = new GraphicalComponent(gameAreaWidth, gameAreaHeight);
		} else {
			aRSImageProducer_1109 = new GraphicalComponent(clientWidth, clientHeight);
		}
	}

	public void drawUnfixedGame() {
		if (clientSize != 0) {
			drawChatArea();
			drawTabArea();
			drawMinimap();
		}
	}

	private void processMinimapActions() {
		if (super.mouseX >= clientWidth - (clientSize == 0 ? 249 : 210) && super.mouseX <= clientWidth - (clientSize == 0 ? 215 : 176) && super.mouseY > (clientSize == 0 ? 46 : 3) && super.mouseY < (clientSize == 0 ? 80 : 38)) {
			menuActionName[menuActionRow] = "Reset XP Total";
			menuActionID[menuActionRow] = 475;
			menuActionRow++;
			menuActionName[menuActionRow] = "Toggle XP Total";
			menuActionID[menuActionRow] = 474;
			menuActionRow++;
		}
		if (hasSummoning) {
        if (mouseInRegion(clientSize == 0 ? clientWidth - 74 : getOrbX(3), getOrbY(3), (clientSize == 0 ? clientWidth - 74 : getOrbX(3)) + 57, getOrbY(3) + 34)) {
				menuActionName[8] = "Cast @gre@" + familiarSpecial;
				menuActionID[8] = 1516;
				menuActionName[7] = "Attack";
				menuActionID[7] = 1520;
				menuActionName[6] = "Interact";
				menuActionID[6] = 1524;
				menuActionName[5] = "Renew Familiar";
				menuActionID[5] = 1528;
				menuActionName[4] = "Take BoB";
				menuActionID[4] = 1532;
				menuActionName[3] = "Dismiss";
				menuActionID[3] = 1536;
				menuActionName[2] = "Call Follower";
				menuActionID[2] = 1540;
				menuActionName[1] = "Follower Details";
				menuActionID[1] = 1544;
				menuActionRow = 9;
		}
		}
		if (isFixed()) {
			if (mouseX >= 531 && mouseX <= 557 && mouseY >= 7 && mouseY <= 40) {
				menuActionName[1] = "Face North";
				menuActionID[1] = 696;
				menuActionRow = 2;
			}
		} else {
			if (mouseInRegion(clientWidth - 180, 3, clientWidth - 145, 38)) {
				menuActionName[1] = "Face North";
				menuActionID[1] = 696;
				menuActionRow = 2;
			}
		}
		if(isFixed()) {
			if ((this.mouseX >= 522) && (this.mouseX <= 558) && (this.mouseY >= 124) && (this.mouseY < 161)) {
				menuActionName[1] = "Teleportation Options";
				menuActionID[1] = 1005;
				menuActionRow = 2;
			}
		} else {
			if ((this.mouseX >= clientWidth - 48) && (this.mouseX <= clientWidth - 5) && (this.mouseY >= 121) && (this.mouseY <= 171)) {
				menuActionName[1] = "Teleportation Options";
				menuActionID[1] = 1005;
				menuActionRow = 2;
			}
		}
		if (mouseInRegion(clientSize == 0 ? clientWidth - 58 : getOrbX(2), getOrbY(2), (clientSize == 0 ? clientWidth - 58 : getOrbX(2)) + 57, getOrbY(2) + 34)) {
			menuActionName[2] = runClicked ? "Toggle Run Mode On" : "Toggle Run Mode Off";
			menuActionID[2] = 1051;
			menuActionName[1] = "Rest";
			menuActionID[1] = 1052;
			menuActionRow = 3;
		}
		if (mouseInRegion(clientSize == 0 ? clientWidth - 58 : getOrbX(1), getOrbY(1), (clientSize == 0 ? clientWidth - 58 : getOrbX(1)) + 57, getOrbY(1) + 34)) {
			menuActionName[2] = prayClicked ? "Turn Quick Prayer off" : "Turn Quick Prayers On";
			menuActionID[2] = 1500;
			menuActionRow = 2;
			menuActionName[1] = "Select Quick Prayers";
			menuActionID[1] = 1506;
			menuActionRow = 3;
		}
	}

	public void processMapAreaMouse() {
		if (mouseInRegion(clientWidth - (clientSize == 0 ? 249 : 217), clientSize == 0 ? 46 : 3, clientWidth - (clientSize == 0 ? 249 : 217) + 34, (clientSize == 0 ? 46 : 3) + 34)) {
			hoverPos = 0; // xp counter
		} else if (mouseInRegion(clientSize == 0 ? clientWidth - 58 : getOrbX(1), getOrbY(1), (clientSize == 0 ? clientWidth - 58 : getOrbX(1)) + 57, getOrbY(1) + 34)) {
			hoverPos = 1; // prayer
		} else if (mouseInRegion(clientSize == 0 ? clientWidth - 58 : getOrbX(2), getOrbY(2), (clientSize == 0 ? clientWidth - 58 : getOrbX(2)) + 57, getOrbY(2) + 34)) {
			hoverPos = 2; // run
		} else if (mouseInRegion(clientSize == 0 ? clientWidth - 74 : getOrbX(3), getOrbY(3), (clientSize == 0 ? clientWidth - 74 : getOrbX(3)) + 57, getOrbY(3) + 34)) {
			hoverPos = 3; // summoning
		} else {
			hoverPos = -1;
		}
	}

	public int rights;
	public String name;
	public String message;
	public String clanname;
	private final int[] chatRights;
	public int chatTypeView;
	public int clanChatMode;
	public int duelMode;
	private Sprite[] chatButtons;
	private GraphicalComponent leftFrame;
	private GraphicalComponent topFrame;
	private int ignoreCount;
	private long aLong824;
	private String objectMaps = "";
	private String floorMaps = "";
	public static Client instance;
	private int[][] anIntArrayArray825;
	private int[] friendsNodeIDs;
	private NodeList[][][] groundArray;
	public boolean quickChat = false;
	private int[] anIntArray828;
	private int[] anIntArray829;
	private volatile boolean aBoolean831;
	private Socket aSocket832;
	public boolean showChat = true;
	int loginScreenState;
	private Buffer aStream_834;
	private Npc[] npcArray;
	private int npcCount;
	private int[] npcIndices;
	private String consoleInput;
	public static boolean consoleOpen;
	private final String[] consoleMessages;
	private int anInt839;
	private int[] anIntArray840;
	private int anInt841;
	private int anInt842;
	private int anInt843;
	private String aString844;
	private int privateChatMode;
	private Buffer aStream_847;
	private boolean aBoolean848;
	private static int anInt849;
	private int[] anIntArray850;
	private int[] anIntArray851;
	private int[] anIntArray852;
	private int[] anIntArray853;
	private static int anInt854;
	private int anInt855;
	public int assistMode;
	public static boolean timeStamp = false;
	public static int openInterfaceID;
	private int xCameraPos;
	private int zCameraPos;
	private int yCameraPos;
	private int yCameraCurve;
	private int xCameraCurve;
	private int myPrivilege;
	final int[] currentExp;
	private Sprite mapFlag;
	private Sprite duelArena;
	private Sprite notInWild;
	private Sprite inWild;
	private Sprite inTimer;
	private Sprite mapMarker;
	private boolean aBoolean872;
	private final int[] anIntArray873;
	public int anInt874;
	private final boolean[] aBooleanArray876;
	private int weight;
	private MouseDetection mouseDetection;
	private volatile boolean drawFlames;
	private String reportAbuseInput;
	private int unknownInt10;
	private boolean menuOpen;
	private int anInt886;
	private String inputString;
	private final int maxPlayers;
	private final int myPlayerIndex;
	private Player[] playerArray;
	private int playerCount;
	private int[] playerIndices;
	private int anInt893;
	private int[] anIntArray894;
	private Buffer[] aStreamArray895s;
	private int anInt896;
	public RSFont newNormalFont;
	public int anInt897;
	private int friendsCount;
	private int anInt900;
	private int[][] anIntArrayArray901;
	private byte[] aByteArray912;
	private int anInt913;
	private int crossX;
	private int crossY;
	private int crossIndex;
	private int crossType;
	private int plane;
	public int interfaceDrawY;
	private final int[] currentStats;
	private static int anInt924;
	private final long[] ignoreListAsLongs;
	private boolean loadingError;
	private final int[] anIntArray928;
	public TextDrawingArea normalFont;
	private int[][] anIntArrayArray929;
	private Sprite aClass30_Sub2_Sub1_Sub1_931;
	private Sprite aClass30_Sub2_Sub1_Sub1_932;
	private int anInt933;
	private int anInt934;
	public int anInt935;
	private int anInt936;
	private int anInt937;
	private int anInt938;
	public RSFont newRegularFont;
	public static int clientWidth = 765;
	public static int clientHeight = 503;
	private final int[] chatTypes;
	private final String[] chatNames;
	private final String[] chatMessages;

	private int anInt945;
	private Scene worldController;
	private Sprite[] sideIcons;
	private int menuScreenArea;
	private int menuOffsetX;
	private int menuOffsetY;
	private int menuWidth;
	private int menuHeight;
	private long aLong953;
	private static final long serialVersionUID = 5707517957054703648L;
	public static int spellID = 0;
	private int gameAreaWidth = 512, gameAreaHeight = 334;
	public int appletWidth = 765, appletHeight = 503;
	private static final int resizableWidth = 930;
	private static final int resizableHeight = 650;
	private boolean aBoolean954;
	private long[] friendsListAsLongs;
	private String[] clanList = new String[100];
	private int currentSong;
	private static int nodeID = 10;
	static int portOff;
	static boolean clientData;
	private static boolean isMembers = true;
	static boolean lowMem;
	private volatile boolean drawingFlames;
	private int spriteDrawX;
	private int spriteDrawY;
	private final int[] anIntArray965 = { 0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff };
	public RSFont newBoldFont;
	public Background aBackground_966;
	public Background aBackground_967;
	private final int[] anIntArray968;
	private final int[] anIntArray969;
	final Decompressor[] decompressors;
	public int variousSettings[];
	private boolean aBoolean972;
	private final int anInt975;
	private final int[] anIntArray976;
	private final int[] anIntArray977;
	private final int[] anIntArray978;
	private final int[] anIntArray979;
	private final int[] anIntArray980;
	private final int[] anIntArray981;
	private final int[] anIntArray982;
	private final String[] aStringArray983;
	public int titleScreenFade = 256;
	private int fadeTitleBox = -400;
	private int anInt984;
	private int anInt985;
	private static int anInt986;
	public int channel;
	private Sprite[] hitMark;
	private Sprite[] hitIcon;
	private Sprite[] hitMarks;
	private Sprite[] hitMarks562;
	public int anInt988;
	private int anInt989;
	private final int[] anIntArray990;
	private final boolean aBoolean994;
	public RSFont newSmallFont;
	private int anInt995;
	private int anInt996;
	private int anInt997;
	private int anInt998;
	private int anInt999;
	public String mutedBy = "";
	public String muteReason = "";
	public int yellMode;
	private ISAACRandomGen encryption;
	private Sprite multiOverlay;
	public int logIconHPos = 0;
	public static int MapX;
	public static int MapY;
	public static int mapX;
	public static int mapY;
	public int headIconOffset;
	public int yOffsetForText;
	static final int[][] anIntArrayArray1003 = { { 6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193 }, { 8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239 }, { 25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003 },
			{ 4626, 11146, 6439, 12, 4758, 10270 }, { 4550, 4537, 5681, 5673, 5790, 6806, 8076, 4574 } };
	private String amountOrNameInput;
	private static int anInt1005;
	private int daysSinceLastLogin;
	private int pktSize;
	public boolean xpLock = false;
	public static boolean counterOn = false;
	public Sprite[] xpOrb = new Sprite[2];
	public static int consoleAlpha = 0;
	private int pktType;
	private int anInt1009;
	private int anInt1010;
	private int anInt1011;
	public int titleAlpha = 0;
	private String prayerBook;
	public static int hoverPos = -1;
	private NodeList aClass19_1013;
	private int anInt1014;
	private int anInt1015;
	private int anInt1016;
	private boolean aBoolean1017;
	private int anInt1018;
	private static final int[] anIntArray1019;
	private int mapState;
	private int anInt1022;
	private int loadingStage;
	private Sprite scrollBar1;
	private Sprite scrollBar2;
	private int anInt1026;
	private final int[] anIntArray1030;
	private boolean aBoolean1031;
	private Sprite[] mapFunctions;
	private int baseX;
	private int baseY;
	private int anInt1036;
	private int anInt1037;
	int loginFailures;
	private int anInt1039;
	private int anInt1040;
	private int anInt1041;
	private int dialogID;
	private final int[] maxStats;
	private final int[] anIntArray1045;
	private int anInt1046;
	private boolean aBoolean1047;
	private int anInt1048;
	private String lastMessage;
	private static int anInt1051;
	private final int[] anIntArray1052;
	private Archive titleStreamLoader;
	private int anInt1054;
	private int anInt1055;
	private NodeList aClass19_1056;
	private final int[] anIntArray1057;
	public final RSInterface aClass9_1059;
	private Background[] mapScenes;
	private static int anInt1061;
	private int anInt1062;
	private int friendsListAction;
	private final int[] anIntArray1065;
	private int itemTo;
	private int lastActiveInvInterface;
	public static OnDemandFetcher onDemandFetcher;
	public static TextDrawingArea fancyFont;
	private int anInt1069;
	private int anInt1070;
	private int anInt1071;
	public Sprite[] imageLoader;
	private boolean clickedQuickPrayers;
	public double fillPrayer;
	public double fillHP;
	public double fillRun;
	public double fillSummoning;
	public int xpCounter;
	public static int clientSize = 0;
	public int xpAddedPos;
	public int expAdded;
	private boolean runClicked = false;
	public Sprite[] worldOrb = new Sprite[2];
	public boolean[] worldState = new boolean[2];
	private int[] anIntArray1072;
	private int[] anIntArray1073;
	private Sprite mapDotItem;
	private Sprite mapDotNPC;
	private Sprite mapDotPlayer;
	private Sprite mapDotFriend;
	private Sprite mapDotTeam;
	private Sprite mapDotClan;
	public Sprite HPBarFull;
	public Sprite HPBarEmpty;
	private int lastPercent;
	private boolean aBoolean1080;
	private String[] friendsList;
	private Buffer inStream;
	private int interfaceId;
	private int itemFrom;
	private int activeInterfaceType;
	public boolean showTab = true;
	private int anInt1087;
	private int anInt1088;
	public static int anInt1089;
	private final int[] expectedCRCs;
	private int[] menuActionCmd2;
	private int[] menuActionCmd3;
	static int[] menuActionID;
	private int[] menuActionCmd1;
	private Sprite[] headIcons;
	private Sprite[] skullIcons;
	private Sprite[] headIconsHint;
	private static int anInt1097;
	public int tabHPos = -1;
	private int anInt1098;
	private int anInt1099;
	private int anInt1100;
	private int anInt1101;
	private int anInt1102;
	public TextDrawingArea boldFont;
	private Sprite[] scrollBar;
	public Sprite[] scrollPart;
	public static boolean tabAreaAltered;
	private int systemUpdateTimer;
	private GraphicalComponent aRSImageProducer_1107;
	private GraphicalComponent aRSImageProducer_1108;
	GraphicalComponent aRSImageProducer_1109;
	private GraphicalComponent aRSImageProducer_1110;
	private GraphicalComponent aRSImageProducer_1111;
	private GraphicalComponent aRSImageProducer_1112;
	private GraphicalComponent aRSImageProducer_1113;
	private GraphicalComponent aRSImageProducer_1114;
	private GraphicalComponent aRSImageProducer_1115;
	private static int anInt1117;
	private int membersInt;
	public boolean prayClicked;
	private String aString1121;
	private Sprite compass;
	private GraphicalComponent aRSImageProducer_1125;
	public static Player myPlayer;
	private final String[] atPlayerActions;
	private final boolean[] atPlayerArray;
	private final int[][][] anIntArrayArrayArray1129;
	public final static int[] tabInterfaceIDs = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	private int anInt1131;
	public int anInt1132;
	static int menuActionRow;
	private static int anInt1134;
	private int spellSelected;
	private int anInt1137;
	private int spellUsableOn;
	private String spellTooltip;
	private Sprite[] aClass30_Sub2_Sub1_Sub1Array1140;
	private boolean aBoolean1141;
	private static int anInt1142;
	private int energy;
	private boolean aBoolean1149;
	private Sprite[] crosses;
	private boolean musicEnabled;
	private Background[] aBackgroundArray1152s;
	private int unreadMessages;
	private static int anInt1155;
	private static boolean fpsOn;
	public boolean loggedIn;
	private boolean canMute;
	private boolean aBoolean1159;
	private boolean aBoolean1160;
	static int loopCycle;
	final String validUserPassChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
	private GraphicalComponent aRSImageProducer_1163;
	private GraphicalComponent aRSImageProducer_1164;
	private GraphicalComponent aRSImageProducer_1165;
	private GraphicalComponent aRSImageProducer_1166;
	private int daysSinceRecovChange;
	private RSSocket socketStream;
	private int anInt1169;
	private int minimapInt3;
	public int anInt1171;
	public long aLong1172;
	String myUsername;
	String myPassword;
	private static int anInt1175;
	private boolean genericLoadingError;
	private final int[] anIntArray1177 = { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3 };
	private int reportAbuseInterfaceID;
	private NodeList aClass19_1179;
	private int[] anIntArray1180;
	private int[] anIntArray1181;
	private int[] anIntArray1182;
	private byte[][] aByteArrayArray1183;
	private int anInt1184;
	private int minimapInt1;
	private int anInt1186;
	private int anInt1187;
	private static int anInt1188;
	private int invOverlayInterfaceID;
	private int[] anIntArray1190;
	private int[] anIntArray1191;
	private Buffer buffer;
	private int anInt1193;
	private int splitPrivateChat;
	private Background mapBack;
	static String[] menuActionName;
	private Sprite aClass30_Sub2_Sub1_Sub1_1201;
	private Sprite aClass30_Sub2_Sub1_Sub1_1202;
	private final int[] anIntArray1203;
	static final int[] anIntArray1204 = { 9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486 };
	private static boolean flagged;
	private final int[] anIntArray1207;
	private int anInt1208;
	private int minimapInt2;
	public int anInt1210;
	public static int anInt1211;
	private String promptInput;
	private int anInt1213;
	private int[][][] intGroundArray;
	private long aLong1215;
	int loginScreenCursorPos;
	private final Sprite[] modIcons;
	public final Sprite[] newModIcons;
	private long aLong1220;
	public static int tabID;
	private int anInt1222;
	public static boolean inputTaken;
	private int inputDialogState;
	private static int anInt1226;
	private int nextSong;
	private boolean songChanging;
	private final int[] anIntArray1229;
	private CollisionMap[] aClass11Array1230;
	public static int anIntArray1232[];
	private int[] anIntArray1234;
	private int[] anIntArray1235;
	private int[] anIntArray1236;
	private int anInt1237;
	private int anInt1238;
	public final int anInt1239 = 100;
	private final int[] anIntArray1240;
	private final int[] anIntArray1241;
	private boolean aBoolean1242;
	private int atInventoryLoopCycle;
	private int atInventoryInterface;
	private int atInventoryIndex;
	private int atInventoryInterfaceType;
	private byte[][] aByteArrayArray1247;
	private int tradeMode;
	private int anInt1249;
	private final int[] anIntArray1250;
	private int anInt1251;
	private final boolean rsAlreadyLoaded;
	private int anInt1253;
	public int anInt1254;
	private boolean welcomeScreenRaised;
	private boolean messagePromptRaised;
	public int anInt1257;
	private byte[][][] byteGroundArray;
	private int prevSong;
	private int destX;
	private int destY;
	private Sprite aClass30_Sub2_Sub1_Sub1_1263;
	private int anInt1264;
	private int anInt1265;
	public String loginMessage1;
	public String loginMessage2;
	private int anInt1268;
	private int anInt1269;
	private TextDrawingArea smallHit;
	private TextDrawingArea bigHit;
	public TextDrawingArea smallText;
	public TextDrawingArea aTextDrawingArea_1271;
	public TextDrawingArea chatTextDrawingArea;
	private int anInt1275;
	private int backDialogID;
	private int anInt1278;
	public int anInt1279;
	private int[] bigX;
	private int[] bigY;
	private int itemSelected;
	private int anInt1283;
	private int anInt1284;
	private int anInt1285;
	public static int log_view_dist = 9;
	private String selectedItemName;
	private int publicChatMode;
	public static String os = System.getProperty("os.name").toLowerCase();
	private static int anInt1288;
	public int anInt1289;
	public static int anInt1290;
	public static String server = "";
	public static boolean needDrawTabArea;
	public int drawCount;
	public int fullscreenInterfaceID;
	public int anInt1044;
	public int anInt1129;
	public int anInt1315;
	public int anInt1500;
	public int anInt1501;
	public int[] fullScreenTextureArray;

	public void resetAllImageProducers() {
		if (super.fullGameScreen != null) {
			return;
		}
		aRSImageProducer_1166 = null;
		aRSImageProducer_1164 = null;
		aRSImageProducer_1163 = null;
		aRSImageProducer_1165 = null;
		aRSImageProducer_1125 = null;
		aRSImageProducer_1107 = null;
		aRSImageProducer_1108 = null;
		aRSImageProducer_1109 = null;
		aRSImageProducer_1110 = null;
		aRSImageProducer_1111 = null;
		aRSImageProducer_1112 = null;
		aRSImageProducer_1113 = null;
		aRSImageProducer_1114 = null;
		aRSImageProducer_1115 = null;
		super.fullGameScreen = new GraphicalComponent(765, 503);
		welcomeScreenRaised = true;
	}

	public void launchURL(String url) {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else {
				String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape", "safari" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
						browser = browsers[count];
				if (browser == null) {
					throw new Exception("Could not find web browser");
				} else
					Runtime.getRuntime().exec(new String[] { browser, url });
			}
		} catch (Exception e) {
			pushMessage("Failed to open URL.", 0, "");
		}
	}

	static {
		anIntArray1019 = new int[99];
		int i = 0;
		for (int j = 0; j < 99; j++) {
			int l = j + 1;
			int i1 = (int) ((double) l + 300D * Math.pow(2D, (double) l / 7D));
			i += i1;
			anIntArray1019[j] = i / 4;
		}
		anIntArray1232 = new int[32];
		i = 2;
		for (int k = 0; k < 32; k++) {
			anIntArray1232[k] = i - 1;
			i += i;
		}
	}

	public static boolean themeBlue = false;
	public static boolean themeRed = false;
	public static boolean themeGreen = false;
	public static boolean themeOrange = false;
	public static boolean themeYellow = false;
	public static boolean themePurple = false;
}