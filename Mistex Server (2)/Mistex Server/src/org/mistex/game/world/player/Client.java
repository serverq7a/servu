package org.mistex.game.world.player;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.quests.QuestManager;
import org.mistex.game.world.Position;
import org.mistex.game.world.World;
import org.mistex.game.world.content.DoubleExpHandler;
import org.mistex.game.world.content.JoinDate;
import org.mistex.game.world.content.Miscellaneous;
import org.mistex.game.world.content.PotionMixing;
import org.mistex.game.world.content.PriceChecker;
import org.mistex.game.world.content.Resting;
import org.mistex.game.world.content.cannon.DwarfCannon;
import org.mistex.game.world.content.cannon.MultiCannonObject;
import org.mistex.game.world.content.clanchat.Clan;
import org.mistex.game.world.content.clanchat.ClanHandler;
import org.mistex.game.world.content.consumables.Food;
import org.mistex.game.world.content.consumables.Potions;
import org.mistex.game.world.content.dialogue.DialogueHandler;
import org.mistex.game.world.content.highscores.HighScoresBoard;
import org.mistex.game.world.content.interfaces.InterfaceText;
import org.mistex.game.world.content.interfaces.impl.AchievementTab;
import org.mistex.game.world.content.interfaces.impl.CleanedPlayerProfilerTab;
import org.mistex.game.world.content.interfaces.impl.InformationTab;
import org.mistex.game.world.content.interfaces.impl.SpawnTab;
import org.mistex.game.world.content.interfaces.impl.teleports.TrainingInterface;
import org.mistex.game.world.content.minigame.barrows.Barrows;
import org.mistex.game.world.content.minigame.barrows.BarrowsChallenge;
import org.mistex.game.world.content.minigame.duelarena.DuelArena;
import org.mistex.game.world.content.minigame.fightpits.FightPits;
import org.mistex.game.world.content.minigame.pestcontrol.PestControl;
import org.mistex.game.world.content.minigame.statuereward.StatueReward;
import org.mistex.game.world.content.minigame.weapongame.WeaponGame;
import org.mistex.game.world.content.prayers.QuickPrayers;
import org.mistex.game.world.content.pvp.Killstreak;
import org.mistex.game.world.content.pvp.PvPHandler;
import org.mistex.game.world.content.skill.SkillHandler;
import org.mistex.game.world.content.skill.farming.Allotments;
import org.mistex.game.world.content.skill.farming.Bushes;
import org.mistex.game.world.content.skill.farming.Compost;
import org.mistex.game.world.content.skill.farming.Flowers;
import org.mistex.game.world.content.skill.farming.FruitTree;
import org.mistex.game.world.content.skill.farming.Herbs;
import org.mistex.game.world.content.skill.farming.Hops;
import org.mistex.game.world.content.skill.farming.Seedling;
import org.mistex.game.world.content.skill.farming.SpecialPlantOne;
import org.mistex.game.world.content.skill.farming.SpecialPlantTwo;
import org.mistex.game.world.content.skill.farming.WoodTrees;
import org.mistex.game.world.content.skill.magic.Enchanting;
import org.mistex.game.world.content.skill.magic.SuperHeat;
import org.mistex.game.world.content.skill.slayer.Slayer;
import org.mistex.game.world.content.skill.summoning.BoB;
import org.mistex.game.world.content.skill.summoning.FamiliarSpecials;
import org.mistex.game.world.content.skill.summoning.PouchCreation;
import org.mistex.game.world.content.skill.summoning.Summoning;
import org.mistex.game.world.content.trade.Trading;
import org.mistex.game.world.event.CycleEvent;
import org.mistex.game.world.event.CycleEventContainer;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.event.events.FightPitsEvent;
import org.mistex.game.world.event.events.PlayerActionEvent;
import org.mistex.game.world.event.events.RunEnergyEvent;
import org.mistex.game.world.event.events.TimersEvent;
import org.mistex.game.world.event.events.WeaponGameEvent;
import org.mistex.game.world.player.bank.Bank;
import org.mistex.game.world.player.bank.BankPins;
import org.mistex.game.world.player.combat.CombatAssistant;
import org.mistex.game.world.player.combat.prayers.Curse;
import org.mistex.game.world.player.item.ItemAssistant;
import org.mistex.system.HostList;
import org.mistex.system.RS2Stream;
import org.mistex.system.packet.Packet;
import org.mistex.system.packet.Packet.Type;
import org.mistex.system.packet.PacketHandler;
import org.mistex.system.util.log.ChatLog;
import org.mistex.system.util.log.DonationsLogger;
import org.mistex.system.util.log.PrivateMessageLog;
import org.mistex.system.util.log.TradeLog;

@SuppressWarnings("static-access")
public class Client extends Player {

	private Queue<Packet> queuedPackets = new LinkedList<Packet>();

	/**
	 * Food consturctor
	 */
	private Food food = new Food(this);

	public Food getFood() {
		return food;
	}

	/**
	 * Potions constructor & getter
	 */
	private Potions potions = new Potions(this);

	public Potions getPotions() {
		return potions;
	}

	/**
	 * Bank pins constructor & getter
	 */
	private BankPins bankPins = new BankPins(this);

	public BankPins getBankPins() {
		return bankPins;
	}

	/**
	 * Potion mixing constructor & getter
	 */
	private PotionMixing potionMixing = new PotionMixing(this);

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	/**
	 * Item assistant constructor & getter
	 */
	private ItemAssistant itemAssistant = new ItemAssistant(this);

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	/**
	 * Player rights constructor & getter
	 */
	private PlayerRight playerRight = new PlayerRight(this);

	public PlayerRight getRights() {
		return playerRight;
	}

	/**
	 * Trading constructor & getter
	 */
	private Trading trading = new Trading(this);

	public Trading getTrade() {
		return trading;
	}

	private QuestManager questManager;
	public QuestManager GetQuestManager() { return questManager;}
	/**
	 * Duel arena constructor & getter
	 */
	private DuelArena duelArena = new DuelArena(this);

	public DuelArena getDuel() {
		return duelArena;
	}

	/**
	 * Player assistant constructor & getter
	 */
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	/**
	 * Super heat constructor & getter
	 */
	private SuperHeat superHeat = new SuperHeat(this);

	public SuperHeat getSuperHeat() {
		return superHeat;
	}

	/**
	 * Combat assistant constructor & getter
	 */
	private CombatAssistant combatAssistant = new CombatAssistant(this);

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	/**
	 * Statue reward constructor & getter
	 */
	private StatueReward statueReward = new StatueReward(this);

	public StatueReward getStatue() {
		return statueReward;
	}

	/**
	 * Kill streak constructor & getter
	 */
	private Killstreak killstreak = new Killstreak(this);

	public Killstreak getStreak() {
		return killstreak;
	}

	/**
	 * Action handler constructor & getter
	 */
	private ActionHandler actionHandler = new ActionHandler(this);

	public ActionHandler getActions() {
		return actionHandler;
	}

	/**
	 * Dialogue handler constructor & getter
	 */
	private DialogueHandler dialogueHandler = new DialogueHandler(this);

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	/**
	 * Join date constructor & getter
	 */
	private JoinDate join = new JoinDate(this);

	public JoinDate getJoinDate() {
		return join;
	}

	/**
	 * Private messaging constructor & getter
	 */
	private PrivateMessageLog privateMessageLog = new PrivateMessageLog(this);

	public PrivateMessageLog getPMLog() {
		return privateMessageLog;
	}

	/**
	 * Enchanting constructor & getter
	 */
	private Enchanting enchanting = new Enchanting(this);

	public Enchanting getEnchanting() {
		return enchanting;
	}

	/**
	 * Slayer constructor & getter
	 */
	private Slayer slayer = new Slayer(this);

	public Slayer getSlayer() {
		return slayer;
	}

	/**
	 * Ancient curses constructor & getter
	 */
	private Curse curse = new Curse(this);

	public Curse getCurse() {
		return curse;
	}

	/**
	 * Barrows constructor & getter
	 */
	private Barrows barrows = new Barrows(this);

	public Barrows getBarrows() {
		return barrows;
	}

	/**
	 * Barrows challenge constructor & getter
	 */
	private BarrowsChallenge barrowsChallenge = new BarrowsChallenge(this);

	public BarrowsChallenge getBarrowsChallenge() {
		return barrowsChallenge;
	}

	/**
	 * Bank constructor & getter
	 */
	private Bank bank = new Bank(this);

	public Bank getBank() {
		return bank;
	}

	/**
	 * Price checker constructor & getter
	 */
	private PriceChecker priceChecker = new PriceChecker(this);

	public PriceChecker getPriceChecker() {
		return priceChecker;
	}

	/**
	 * Chat logger constructor & getter
	 */
	private ChatLog chatLog = new ChatLog(this);

	public ChatLog getChatlog() {
		return chatLog;
	}

	/**
	 * Dwarf cannon constructor & getter
	 */
	private DwarfCannon cannon = new DwarfCannon(this);

	public DwarfCannon getCannon() {
		return cannon;
	}

	/**
	 * Summoning constructor & getter
	 */
	public Summoning Summoning = new Summoning(this);

	public Summoning Summoning() {
		return Summoning;
	}

	/**
	 * Beast of burden constructor & getter
	 */
	private BoB boB = new BoB(this);

	public BoB getBOB() {
		return boB;
	}

	/**
	 * Familiar specials constructor & getter
	 */
	private FamiliarSpecials familiarSpecials = new FamiliarSpecials(this);

	public FamiliarSpecials getSpecials() {
		return familiarSpecials;
	}

	/**
	 * Pouch creation constructor & getter
	 */
	private PouchCreation pouchCreation = new PouchCreation(this);

	public PouchCreation getPouchCreation() {
		return pouchCreation;
	}

	/**
	 * Trade logger constructor & getter
	 */
	private TradeLog tradeLog = new TradeLog(this);

	public TradeLog getTradeLog() {
		return tradeLog;
	}

	/**
	 * Donation logger constructor & getter
	 */
	private DonationsLogger donationsLogger = new DonationsLogger(this);

	public DonationsLogger getDonationsLogger() {
		return donationsLogger;
	}

	/**
	 * Special plant one constructor & getter
	 */
	private SpecialPlantOne specialPlantOne = new SpecialPlantOne(this);

	public SpecialPlantOne getSpecialPlantOne() {
		return specialPlantOne;
	}

	/**
	 * Special plant one constructor & getter
	 */
	private SpecialPlantTwo specialPlantTwo = new SpecialPlantTwo(this);

	public SpecialPlantTwo getSpecialPlantTwo() {
		return specialPlantTwo;
	}

	/**
	 * Compost constructor & getter
	 */
	private Compost compost = new Compost(this);

	public Compost getCompost() {
		return compost;
	}

	/**
	 * Allotoments constructor & getter
	 */
	private Allotments allotment = new Allotments(this);

	public Allotments getAllotment() {
		return allotment;
	}

	/**
	 * Followers constructor & getter
	 */
	private Flowers flower = new Flowers(this);

	public Flowers getFlowers() {
		return flower;
	}

	/**
	 * Herbs constructor & getter
	 */
	private Herbs herb = new Herbs(this);

	public Herbs getHerbs() {
		return herb;
	}

	/**
	 * Hops constructor & getter
	 */
	private Hops hops = new Hops(this);

	public Hops getHops() {
		return hops;
	}

	/**
	 * Bushes constructor & getter
	 */
	private Bushes bushes = new Bushes(this);

	public Bushes getBushes() {
		return bushes;
	}

	/**
	 * Seedling constructor & getter
	 */
	private Seedling seedling = new Seedling(this);

	public Seedling getSeedling() {
		return seedling;
	}

	/**
	 * Wood trees constructor & getter
	 */
	private WoodTrees trees = new WoodTrees(this);

	public WoodTrees getTrees() {
		return trees;
	}

	/**
	 * Fruit tree constructor & getter
	 */
	private FruitTree fruitTrees = new FruitTree((this));

	public FruitTree getFruitTrees() {
		return fruitTrees;
	}

	public static int Flower[] = { 2980, 2981, 2982, 2983, 2984, 2985, 2986, 2987, 2988 };

	public static int randomFlower() {
		return Flower[(int) (Math.random() * Flower.length)];
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public synchronized RS2Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized RS2Stream getOutStream() {
		return outStream;
	}

	public int maxstore = 0;

	public void storesummon(int npcType) {
		switch (npcType) {
		case 6807:
			if (lastsummon > 0) {
				for (int i = 0; i < Mistex.npcHandler.maxNPCs; i++) {
					if (Mistex.npcHandler.npcs[i] != null) {
						if (Mistex.npcHandler.npcs[i].summon == true) {
							if (Mistex.npcHandler.npcs[i].spawnedBy == getId() && Mistex.npcHandler.npcs[i].npcId == npcslot) {
								sendMessage("You are now storing items inside your npc");
								Summoning().store();
							}
						}
					}
				}

			}
			break;

		}
	}

	public int attackingplayer;
	public int lastsummon;
	public int summoningslot = 0;

	public int storeditems[] = new int[29];

	public boolean picking = false;

	public int amount[] = new int[29];
	public boolean occupied[] = new boolean[29];

	public boolean storing = false;

	/*
	 * Summoning
	 */
	public boolean summon;

	public int agX, agY;
	public boolean canPlayerMove = true;
	public int ag0, ag1, ag2, ag3, ag4, ag5;
	public boolean isSkilling;

	public String statedInterface = "";

	public String getStatedInterface() {
		return statedInterface;
	}

	private int tempInteger;

	public void setTempInteger(int i) {
		tempInteger = i;
	}

	public int getTempInteger() {
		return tempInteger;
	}

	public int fireX, fireY, fireHeight, fireId;

	public void setStatedInterface(String string) {
		this.statedInterface = string;
	}

	public long vote = 0;

	public int[] totalSkillExp = new int[25];
	public int[] skillPrestiges = new int[25];
	public boolean[] perksActivated = new boolean[12];
	public boolean isArrowing;

	public int currentArrow;
	public int stringu;
	public int logID;
	public boolean isStringing;
	public int counterxp;
	public int totalTasks;
	public int questStatus;
	public double lootSharePotential;
	public long lastLootDate;
        public int[] questProgress = new int[50];
	public long clanDelay;
	public boolean goodLootDistance;
	public boolean playerPrayerBook;
	public boolean[] quickPrayers = new boolean[QuickPrayers.MAX_PRAYERS];
	public int totalDonations;
	public int teleEndGfx;
	public boolean deletingPin = false;

	public Client(Channel s, int _playerId) {
		super(_playerId);
		this.session = s;
		outStream = new RS2Stream(new byte[MistexConfiguration.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		inStream = new RS2Stream(new byte[MistexConfiguration.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[MistexConfiguration.BUFFER_SIZE];
		this.questManager = new QuestManager(this);
	}

	public Channel getSession() {
		return session;
	}

	public void flushOutStream() {
		if (!session.isConnected() || disconnected || outStream.currentOffset == 0)
			return;
		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		Packet packet = new Packet(-1, Type.FIXED, ChannelBuffers.wrappedBuffer(temp));
		session.write(packet);
		outStream.currentOffset = 0;
	}

	public void sendClan(String name, String message, String clan, int rights) {
		if (getOutStream() != null) {
			outStream.createFrameVarSizeWord(217);
			outStream.writeString(name);
			outStream.writeString(MistexUtility.formatSentence(message));
			outStream.writeString(clan);
			outStream.writeWord(rights);
			outStream.endFrameVarSize();
		}
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0-9
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10-19
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20-29
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30-39
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40-49
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50-59
			-1, 8, 0, -1, -1, -1, 0, 0, 0, 0, 6, 0, 2, 2, 8, 6, 0, -1, 0, 6, 0, 0, 0, 0, 0, 1, 4, 6, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, -1, 0, 0, 13, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 1, 0, 6, 0, 0, 0, -1, 0, 2, 6, 0, 4, 6, 8, 0, 6, 0, 0, 0, 2, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 1, 2, 0, 2, 6, 0, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 3, 0, 2, 0, 0, 8, 1, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0, 0, 0, 7, 8, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, -1, 0, 6, 0, 1, 0, 0, 0, 6, 0, 6, 8, 1, 0, 0, 4, 0, 0, 0, 0, -1, 0, -1, 4, 0, 0, 6, 6, 0, 0, 0 };

	public void destruct() {
		if (session == null) {
			return;
		}

		PvPHandler.handleLogout(this);
		Mistex.panel.removeEntity(playerName);

		if (duelStatus >= 1 && duelStatus <= 5) {
			getDuel().declineDuel();
			saveCharacter = true;
			return;
		}
		if (hasNpc == true) {
			hasNpc = false;
			summonId = 0;
		}
		if (inPcGame() || inPcBoat()) {
			PestControl.removePlayerGame(this);
			getPA().movePlayer(3087, 3499, 0);
		}
		if (inWeaponGame() || inWeaponLobby()) {
			WeaponGame.handleLogout(this);
		}
		if (inFightPits()) {
			FightPits.removePlayer(this, true);
			getPA().movePlayer(3087, 3499, 0);
			return;
		}
		if (disconnected == true) {
			getTrade().declineTrade();
		}
		if (absX >= 2847 && absX <= 2876 && absY >= 3534 && absY <= 3556 || absX >= 2838 && absX <= 2847 && absY >= 3543 && absY <= 3556 && heightLevel == 2) {
			inCyclops = false;
			getPA().movePlayer(2846, 3541, 2);
			kamfreenaDone = false;
		}
		CycleEventHandler.getSingleton().stopEvents(this);
		PlayerSave.saveGame(this);

		Clan clan = ClanHandler.getClan(savedClan);
		if (clan != null)
			clan.leaveClan(this, true);

		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		MistexUtility.println("[DE-REGISTERED]: " + MistexUtility.capitalize(playerName) + "");
		super.destruct();
	}

	public void sendMessage(String s) {
		if (getOutStream() != null) {
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		if (getOutStream() != null) {
			outStream.createFrame(71);
			outStream.writeWord(form);
			outStream.writeByteA(menuId);
		}
	}

	public void fixPlayer() {
		playerAppearance[0] = 0; // gender
		playerAppearance[1] = 7; // head
		playerAppearance[2] = 25; // Torso
		playerAppearance[3] = 29; // arms
		playerAppearance[4] = 35; // hands
		playerAppearance[5] = 39; // legs
		playerAppearance[6] = 44; // feet
		playerAppearance[7] = 14; // beard
		playerAppearance[8] = 7; // hair colour
		playerAppearance[9] = 8; // torso colour
		playerAppearance[10] = 9; // legs colour
		playerAppearance[11] = 5; // feet colour
		playerAppearance[12] = 0; // skin colour

	}

	public void initialize() {

		outStream.createFrame(249);
		outStream.writeByteA(1);
		outStream.writeWordBigEndianA(playerId);

		// PvPHandler.handleLogin(this);
		for (int j = 0; j < World.players.length; j++) {
			if (j == playerId)
				continue;
			if (World.players[j] != null) {
				if (World.players[j].playerName.equalsIgnoreCase(playerName))
					disconnected = true;
			}
		}
		// UUID = RS2LoginProtocolDecoder.UUID;
		/* Skills Refresh */
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
		for (int index = 0; index < playerXP.length; index++) {
			if (getPA().getLevelForXP(playerXP[index]) >= 99) {
				total99s++;
			}
		}

		/* Prayer Glow Reset */
		for (int p = 0; p < PRAYER.length; p++) {
			prayerActive[p] = false;
			getPA().sendFrame36(PRAYER_GLOW[p], 0);
		}
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
		for (int p = 0; p < CURSE.length; p++) { // reset prayer glows
			curseActive[p] = false;
			getPA().sendFrame36(CURSE_GLOW[p], 0);
		}

		Mistex.panel.addEntity(playerName);
		getPA().sendCrashFrame();
		calcCombat();

		if (playerRights == 3) {
			// ClanHandler.loadClan(this);
		}

		getAllotment().doCalculations();
		getSpecialPlantOne().doCalculations();
		getSpecialPlantTwo().doCalculations();
		getFlowers().doCalculations();
		getBushes().doCalculations();
		getHops().doCalculations();
		getHerbs().doCalculations();
		getTrees().doCalculations();
		getFruitTrees().doCalculations();

		getPA().handleWeaponStyle();
		accountFlagged = getPA().checkForFlags();
		getPA().sendFrame36(108, 0); // resets autocast button
		getPA().sendFrame36(172, 1);
		getPA().sendFrame107(); // reset screen
		getPA().setChatOptions(0, 0, 0); // reset private messaging options
		/* Side bars */
		setSidebarInterface(0, 2423); // Combat
		setSidebarInterface(1, 31000); // Achievement
		setSidebarInterface(2, 3917); // Skilltab
		setSidebarInterface(3, 3213); // Inventory
		setSidebarInterface(4, 1644); // Equipment
		if (altarPrayed == 0) {
			setSidebarInterface(5, 5608);
		} else {
			setSidebarInterface(5, 22500);
		}
		magicBooks();
		setSidebarInterface(7, 18128); // Clanchat
		setSidebarInterface(8, 5065); // Friends
		setSidebarInterface(9, 5715); // ignore
		setSidebarInterface(10, 2449); // Logout
		setSidebarInterface(11, 904); // Wrench
		setSidebarInterface(12, 147); // Emotes
		setSidebarInterface(13, 29000); // Player Profiler
		setSidebarInterface(14, 37000); // Quest
		if (lastsummon <= 0) {
			setSidebarInterface(15, -1); // Summoning
		} else {
			setSidebarInterface(15, 18017); // Summoning
		}
		setSidebarInterface(16, 45500); // Settings
		// setSidebarInterface(17, -1); //Notes
		getPA().sendFrame126(runEnergy + "%", 149);
		/*
		 * Farming
		 */
		getAllotment().updateAllotmentsStates();
		getHerbs().updateHerbsStates();
		getTrees().updateTreeStates();
		getFruitTrees().updateFruitTreeStates();
		getFlowers().updateFlowerStates();
		getSpecialPlantOne().updateSpecialPlants();
		getSpecialPlantTwo().updateSpecialPlants();
		getHops().updateHopsStates();
		getBushes().updateBushesStates();

		/*
		
		for (int index = 1; index <= Bank.TABS; index++) {
			getPA().sendFrame36(1000 + index, 0);
			getBank().requestTabUpdate(index);
		}

		int usedTabs = getBank().getUsedTabs(); 

		if (usedTabs <= Bank.TABS) {
			getPA().sendFrame171(0, 50061);
			getPA().sendFrame171(0, 50033 + (usedTabs) * 2);
			getPA().sendFrame70(usedTabs == 0 ? 0 : 48 * (usedTabs - 1), 0, 50061);
		} else {
			getPA().sendFrame171(1, 50061);
		}

		getBank().setTab(0);
		getPA().sendFrame36(1000, 1);
		getPA().sendFrame171(0, 50033 + getBank().getUsedTabs() * 2);
		getPA().sendFrame126("" + getBank().getCurrentBankTab().getItems().size(), 50017);
		getPA().sendFrame126("" + getBank().getUsedSlots(), 50019);
		getPA().sendFrame126("" + Bank.BANK_SIZE, 50020);
		
		*/

		getPA().sendSkillXP(0, 0);
		/* Login Messages */
		sendMessage("Welcome to <col=1D64B5>Mistex</col>.");
		sendMessage("@ceo@News: Mistex is going heavy under heavy development. ");
		sendMessage("@red@Latest Updates: Client 3.6 has been released!");
		if (DoubleExpHandler.weekend()) {
			sendMessage("<col=1D64B5>It's double XP weekend! Enjoy double experience for everything.");
		}
		sendMessage("Alert##Welcome to Mistex!##We hope you enjoy your stay##Visit our forums: www.mistex.org");
		lastLoginDate = getLastLogin();
		getPA().showOption(5, 0, "Trade With", 3);
		getPA().showOption(4, 0, "Follow", 4);
		getItems().resetItems(3214);
		getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
		getItems().resetBonus();
		getItems().getBonus();
		getItems().writeBonus();
		getPA().refreshSkill(3);
		getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
		getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
		getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
		getItems().setEquipment(playerEquipment[playerArrows], playerEquipmentN[playerArrows], playerArrows);
		getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
		getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
		getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
		getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
		getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
		getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
		getItems().setEquipment(playerEquipment[playerWeapon], playerEquipmentN[playerWeapon], playerWeapon);
		getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
		getPA().logIntoPM();
		getItems().addSpecialBar(playerEquipment[playerWeapon]);
		saveTimer = MistexConfiguration.SAVE_TIMER;
		saveCharacter = true;
		MistexUtility.println("[REGISTERED]: " + playerName + "");
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
		if (joinDate.length() == 0) {
			getJoinDate().setJoinDate();
		}
		if (addStarter) {
			setTheAppearance = false;
			earningPotential = 0;
			getJoinDate().setJoinDate();
			specAmount = 10.0;
			isDoingTutorial = true;
			getDH().sendDialogues(250, 945);
			Mistex.clan.joinClan(this);
		}

		// if (playerRights == 3) {
		Clan clan = ClanHandler.getClan(savedClan);
		if (clan != null) {
			savedClan = null;
			clan.joinClan(this);
		} else {
			getPA().sendFrame126("Join chat", 18135);
			getPA().sendFrame126("Talking in: None", 18139);
			getPA().sendFrame126("Owner: None", 18140);
			for (int line = 18144; line < 18244; line++) {
				getPA().updateClanRank("", line, 0);
			}
			savedClan = null;
		}
		// }

		if (autoRet == 1) {
			getPA().sendFrame36(172, 1);
		} else {
			getPA().sendFrame36(172, 0);
		}
		if (getRights().isPlayer()) {
			HighScoresBoard.getLeaderBoard().setOption(this);
		}
		if (currentlyCompletedAll) {
			if (achievementsCompleted != totalAchievements) {
				sendMessage("@ceo@A new achievement has been added! You have lost your entitlement.");
				currentlyCompletedAll = false;
			}
		}
		if (hasNpc == true) {
			hasNpc = false;
			summonId = 0;
		}
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
		viewingTeleportingInterface = 1;
		InterfaceText.writeText(new TrainingInterface(this));
		setEvents();
		playerLogin();
		setTheTexts();
		correctCoords();
	}

	private int getLastLogin() {
		Calendar cal = new GregorianCalendar();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		return (year * 10000) + (month * 100) + day;
	}

	public void correctCoords() {
		if (inPcGame() || inPcBoat()) {
			getPA().movePlayer(3087, 3499, 0);
			sendMessage("@ceo@You were auto-detected to be in a pest control area. You have been sent home.");
		} else if (inWeaponLobby() || inWeaponGame()) {
			getPA().movePlayer(3087, 3499, 0);
			getItems().removeAllItems1();
			sendMessage("@ceo@You were auto-detected to be in a weapon game area. You have been sent home.");
		} else if (inFightPits()) {
			getPA().movePlayer(3087, 3499, 0);
			sendMessage("@ceo@You were auto-detected to be in a fight pits area. You have been sent home.");
		} else if (inFightCaves()) {
			getPA().movePlayer(3087, 3499, 0);
			sendMessage("@ceo@You were auto-detected to be in a fight caves area. You have been sent home.");
		}
	}

	private void setEvents() {
		CycleEventHandler.getSingleton().addEvent(this, new PlayerActionEvent(this), 1);
		CycleEventHandler.getSingleton().addEvent(this, new WeaponGameEvent(this), 5);
		CycleEventHandler.getSingleton().addEvent(this, new FightPitsEvent(), 5);
		CycleEventHandler.getSingleton().addEvent(this, new TimersEvent(this), 1);
		CycleEventHandler.getSingleton().addEvent(this, new RunEnergyEvent(this), 3);
	}

	private void setTheTexts() {
		InterfaceText.writeText(new InformationTab(this));
		InterfaceText.writeText(new SpawnTab(this));
		InterfaceText.writeText(new AchievementTab(this));
		InterfaceText.writeText(new CleanedPlayerProfilerTab(this));
		Miscellaneous.togglesTab(this);
		sendMessage(":setTexts:");
	}

	private void playerLogin() {
		if (playerRights == 1) {
			// canBanClick = true;
			getPA().yell("[ <col=255>Moderator</col> ] <img=0><col=255>" + MistexUtility.capitalize(playerName) + "</col> has just logged in, feel free to ask for help.");
		} else if (playerRights == 2) {
			canBanClick = true;
			getPA().yell("[ <col=FFF700>Administrator</col> ] <img=1><col=FFF700>" + MistexUtility.capitalize(playerName) + "</col> has just logged in, feel free to ask for help.");
		} else if (playerName.equalsIgnoreCase("play boy")) {
			canBanClick = true;
			getPA().yell("[ <col=F76323>CEO & Developer</col> ] <img=1><col=F76323>" + MistexUtility.capitalize(playerName) + "</col> has just logged in.");
		} else if (playerName.equalsIgnoreCase("chex")) {
			canBanClick = true;
			getPA().yell("[ <col=9203FF>Developer</col> ] <img=1><col=9203FF>" + MistexUtility.capitalize(playerName) + "</col> has just logged in.");
		} else if (playerName.equalsIgnoreCase("evan")) {
			canBanClick = true;
			getPA().yell("[ <col=9203FF>Client Developer</col> ] <img=1><col=9203FF>" + MistexUtility.capitalize(playerName) + "</col> has just logged in.");

		}
	}

	public void magicBooks() {
		if (playerMagicBook == 0) {
			setSidebarInterface(6, 1151); // modern
		}
		if (playerMagicBook == 1) {
			setSidebarInterface(6, 12855); // ancient
		}
		if (playerMagicBook == 2) {
			setSidebarInterface(6, 29999); // interface 16640(lunar)
		}
	}

	@Override
	public void update() {
		Mistex.playerHandler.updatePlayer(this, outStream);
		Mistex.playerHandler.updateNPC(this, outStream);
		flushOutStream();
	}

	public void logout() {
		if (System.currentTimeMillis() - logoutDelay > 10000) {
			outStream.createFrame(109);
			properLogout = true;
			PlayerSave.saveGame(this);

			Clan clan = ClanHandler.getClan(savedClan);
			if (clan != null)
				clan.leaveClan(this, true);

			if (inPcGame() || inPcBoat()) {
				PestControl.removePlayerGame(this);
				getPA().movePlayer(3087, 3499, 0);
			}
			if (inWeaponLobby() || inWeaponGame()) {
				WeaponGame.handleLogout(this);
			}
			if (inFightPits()) {
				FightPits.removePlayer(this, true);
			}
			if (hasNpc == true) {
				hasNpc = false;
				summonId = 0;
			}
			if (absX >= 2847 && absX <= 2876 && absY >= 3534 && absY <= 3556 || absX >= 2838 && absX <= 2847 && absY >= 3543 && absY <= 3556 && heightLevel == 2) {
				inCyclops = false;
				getPA().movePlayer(2846, 3541, 2);
				kamfreenaDone = false;
			}
			handleCannonDeath();

		} else {
			sendMessage("You must wait a few seconds from being out of combat to logout.");
		}
	}

	public void getYellTag() {
		if (customYell == true)
			return;
		for (int i = 0; i < 6; i++) {
			if (playerRights == i) {
				yellTag = defaultYellTag[i];
				return;
			}
		}
	}

	public void calcCombat() {
		int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));

		combatLevel = 0;
		if (ran > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25) + ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
		} else if (mag > attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25) + ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
		} else {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25) + ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
		}
	}

	public void process() {
		if (followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}

		if (clawDelay > 0) {
			clawDelay--;
		}
		if (clawDelay == 1) {
			delayedDamage = (clawDamage / 4);
			delayedDamage2 = (clawDamage / 4) + 1;
			if (clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 1, clawDamage / 4);
			}
			if (clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 1, clawDamage / 4);
			}
			if (clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 2, (clawDamage / 4) + 1);
			}
			if (clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 2, (clawDamage / 4) + 1);
			}
			clawDelay = 0;
			specEffect = 0;
			previousDamage = 0;
			usingClaws = false;
			clawType = 0;
		}
		if (this.updateItems)
			this.getItems().resetItems(3214);

		getPA().sendFrame126("@lre@Players Online @gre@" + World.getPlayerCount(), 37059);

		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage / 2;
			if (damage > 0) {
				sendMessage("The poison damages you.");
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					setHitDiff(damage);
					updateRequired = true;
					poisonMask = 1;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					setHitDiff2(damage);
					updateRequired = true;
					poisonMask = 2;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				dealDamage(damage);
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}
		}

		if (System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
			if (duelCount != 1) {
				forcedChat("" + (--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[MistexConfiguration.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}

		if (System.currentTimeMillis() - specDelay > PlayerConfiguration.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += .5;
				Miscellaneous.handleMessages(this);
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		getCombat().handlePrayerDrain();

		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - restoreStatsDelay > 60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if (System.currentTimeMillis() - teleGrabDelay > 1550 && usingMagic) {
			usingMagic = false;
			if (Mistex.itemHandler.itemExists(teleGrabItem, teleGrabX, teleGrabY)) {
				Mistex.itemHandler.removeGroundItem(this, teleGrabItem, teleGrabX, teleGrabY, true);
			}
		}
		if (inWild() && !inFunPk() && showingInterface == false) {
			// PvPHandler.handleInterfaces(this);
			getPA().walkableInterface(197);
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			if (PlayerConfiguration.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				} else {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if (!inWild() && inDuelArena() && showingInterface == false) {
			getPA().walkableInterface(201);
			if (duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if (isInBarrows() || isInBarrows2()) {
			getPA().walkableInterface(16128);
			getPA().sendFrame126("" + barrowsKillCount, 16137);
			if (barrowsNpcs[2][1] == 2) {
				getPA().sendFrame126("@red@Karils", 16135);
			}
			if (barrowsNpcs[3][1] == 2) {
				getPA().sendFrame126("@red@Guthans", 16134);
			}
			if (barrowsNpcs[1][1] == 2) {
				getPA().sendFrame126("@red@Torags", 16133);
			}
			if (barrowsNpcs[5][1] == 2) {
				getPA().sendFrame126("@red@Ahrims", 16132);
			}
			if (barrowsNpcs[0][1] == 2) {
				getPA().sendFrame126("@red@Veracs", 16131);
			}
			if (barrowsNpcs[4][1] == 2) {
				getPA().sendFrame126("@red@Dharoks", 16130);
			}
		} else if (!inWild() && inFightPits() && showingInterface == false) {
			getPA().showOption(3, 0, "Attack", 1);
			getPA().walkableInterface(2804);
		} else if (getPA().inPitsWait()) {
			getPA().walkableInterface(2804);
			getPA().showOption(3, 0, "Null", 1);
		} else if (inPcBoat()) {
			getPA().walkableInterface(21119);
		} else if (inPcGame()) {
			getPA().walkableInterface(21100);
		} else if (inWeaponLobby()) {
			getPA().walkableInterface(25347);
		} else if (inWeaponGame()) {
			getPA().showOption(3, 0, "Attack", 1);
			getPA().walkableInterface(25347);

		} else if (inBoxingArena) {
			getPA().showOption(3, 0, "Attack", 1);
			getPA().sendFrame126("@ceo@Boxing Arena", 4536);
			getPA().walkableInterface(4535);

		} else if (inFunPk()) {
			getPA().sendFrame126("@ceo@FunPk", 4536);
			getPA().walkableInterface(4535);
			getPA().showOption(3, 0, "Attack", 1);
		} else if (!inDuelArena() && !isDoingTutorial && !inFightCaves() && !inWeaponGame() && !inWeaponLobby() && !inFightPits() && !inWild() && !inCwWait && showingInterface == false) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			if (canBanClick)
				getPA().showOption(3, 0, "Punish", 1);
			else
				getPA().showOption(3, 0, "Null", 1);
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}
		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(7185);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (World.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, World.players[frozenBy].absX, World.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			if (rest != null)
				Resting.endRest(this);
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
					SkillHandler.resetPlayerVariables(this);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombat().delayedHit(this, oldNpcIndex);
			}
			if (oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(this, oldPlayerIndex);
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}
	}

	private int index = -1;

	public int getIndex() {
		return index;
	}

	public void queueMessage(Packet arg1) {
		synchronized (queuedPackets) {
			queuedPackets.add(arg1);
		}
	}

	public boolean processQueuedPackets() {
		synchronized (queuedPackets) {
			Packet p = null;
			while ((p = queuedPackets.poll()) != null) {
				inStream.currentOffset = 0;
				packetType = p.getOpcode();
				packetSize = p.getLength();
				inStream.buffer = p.getPayload().array();
				if (packetType > 0) {
					PacketHandler.processPacket(this, packetType, packetSize);
				}
			}
			return true;
		}
	}

	public void jadSpawn() {
		sendMessage("<col=13631F>Good luck " + MistexUtility.capitalize(playerName) + "!");
		// FightCaves.updateInterface(this);
		CycleEventHandler.getSingleton().addEvent(this, new CycleEvent() {
			@Override
			public void execute(CycleEventContainer container) {
				Mistex.fightCaves.spawnNextWave((Client) World.players[playerId]);
				container.stop();
			}

			@Override
			public void stop() {
			}

		}, 18);
	}

	public void StartBestItemScan() {
		if (isSkulled && !prayerActive[10]) {
			ItemKeptInfo(0);
			return;
		}
		FindItemKeptInfo();
		ResetKeepItems();
		BestItem1();
	}

	public void ResetKeepItems() {
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		WillKeepAmt1 = 0;
		WillKeepAmt2 = 0;
		WillKeepAmt3 = 0;
	}

	public void FindItemKeptInfo() {
		if (isSkulled && prayerActive[10])
			ItemKeptInfo(1);
		else if (!isSkulled && !prayerActive[10])
			ItemKeptInfo(3);
		else if (!isSkulled && prayerActive[10])
			ItemKeptInfo(4);
		else if (inPits || inFightCaves()) {
			ItemKeptInfo(5);
		}
	}

	public void BestItem1() {
		int BestValue = 0;
		int NextValue = 0;
		int ItemsContained = 0;
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(getItems().getItemPrice(playerItems[ITEM] - 1));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerItems[ITEM] - 1;
					WillKeepItem1Slot = ITEM;
					if (playerItemsN[ITEM] > 2 && !prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerItemsN[ITEM] > 3 && prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(getItems().getItemPrice(playerEquipment[EQUIP]));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerEquipment[EQUIP];
					WillKeepItem1Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 && !prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerEquipmentN[EQUIP] > 3 && prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 1 && (WillKeepAmt1 < 3 || (prayerActive[10] && WillKeepAmt1 < 4))) {
			BestItem2(ItemsContained);
		}
	}

	public void BestItem2(int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(getItems().getItemPrice(playerItems[ITEM] - 1));
				if (NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerItems[ITEM] - 1;
					WillKeepItem2Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - WillKeepAmt1 && !prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerItemsN[ITEM] > 3 - WillKeepAmt1 && prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(getItems().getItemPrice(playerEquipment[EQUIP]));
				if (NextValue > BestValue && !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerEquipment[EQUIP];
					WillKeepItem2Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - WillKeepAmt1 && !prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1 && prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 2 && (WillKeepAmt1 + WillKeepAmt2 < 3 || (prayerActive[10] && WillKeepAmt1 + WillKeepAmt2 < 4))) {
			BestItem3(ItemsContained);
		}
	}

	public int getCombatLevel() {
		int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));

		if (ran > attstr && mag > attstr && mag < ran || ran > attstr && mag < attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25) + ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
		} else if (mag > attstr && ran > attstr && ran < mag || mag > attstr && ran < attstr) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25) + ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
		} else if (ran > attstr && mag > attstr && ran == mag) {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25) + ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
		} else {
			combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25) + ((getLevelForXP(playerXP[3])) * 0.25) + ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
		}
		return combatLevel;
	}

	public void BestItem3(int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(getItems().getItemPrice(playerItems[ITEM] - 1));
				if (NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1) && !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerItems[ITEM] - 1;
					WillKeepItem3Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - (WillKeepAmt1 + WillKeepAmt2) && !prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerItemsN[ITEM] > 3 - (WillKeepAmt1 + WillKeepAmt2) && prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(getItems().getItemPrice(playerEquipment[EQUIP]));
				if (NextValue > BestValue && !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1) && !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerEquipment[EQUIP];
					WillKeepItem3Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - (WillKeepAmt1 + WillKeepAmt2) && !prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1 && prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 3 && prayerActive[10] && ((WillKeepAmt1 + WillKeepAmt2 + WillKeepAmt3) < 4)) {
			BestItem4();
		}
	}

	public void BestItem4() {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(getItems().getItemPrice(playerItems[ITEM] - 1));
				if (NextValue > BestValue && !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1) && !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2) && !(ITEM == WillKeepItem3Slot && playerItems[ITEM] - 1 == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerItems[ITEM] - 1;
					WillKeepItem4Slot = ITEM;
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(getItems().getItemPrice(playerEquipment[EQUIP]));
				if (NextValue > BestValue && !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1) && !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2) && !(EQUIP + 28 == WillKeepItem3Slot && playerEquipment[EQUIP] == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerEquipment[EQUIP];
					WillKeepItem4Slot = EQUIP + 28;
				}
			}
		}
	}

	public void ItemKeptInfo(int Lose) {
		for (int i = 17109; i < 17131; i++) {
			getPA().sendFrame126("", i);
		}
		getPA().sendFrame126("Items you will keep on death:", 17104);
		getPA().sendFrame126("Items you will lose on death:", 17105);
		getPA().sendFrame126("Mistex", 17106);
		getPA().sendFrame126("Max items kept on death:", 17107);
		getPA().sendFrame126("~ " + Lose + " ~", 17108);
		getPA().sendFrame126("The normal amount of", 17111);
		getPA().sendFrame126("items kept is three.", 17112);
		switch (Lose) {
		case 0:
		default:
			getPA().sendFrame126("Items you will keep on death:", 17104);
			getPA().sendFrame126("Items you will lose on death:", 17105);
			getPA().sendFrame126("You're marked with a", 17111);
			getPA().sendFrame126("@red@skull. @lre@This reduces the", 17112);
			getPA().sendFrame126("items you keep from", 17113);
			getPA().sendFrame126("three to zero!", 17114);
			break;
		case 1:
			getPA().sendFrame126("Items you will keep on death:", 17104);
			getPA().sendFrame126("Items you will lose on death:", 17105);
			getPA().sendFrame126("You're marked with a", 17111);
			getPA().sendFrame126("@red@Skull. @lre@This reduces the", 17112);
			getPA().sendFrame126("items you keep from", 17113);
			getPA().sendFrame126("three to zero!", 17114);
			getPA().sendFrame126("However, you also have", 17115);
			getPA().sendFrame126("@red@Protect @lre@Item prayer", 17118);
			getPA().sendFrame126("active, which saves", 17119);
			getPA().sendFrame126("you one extra item!", 17120);
			break;
		case 3:
			getPA().sendFrame126("Items you will keep on death(if not skulled):", 17104);
			getPA().sendFrame126("Items you will lose on death(if not skulled):", 17105);
			getPA().sendFrame126("You have no factors", 17111);
			getPA().sendFrame126("affecting the items", 17112);
			getPA().sendFrame126("you keep.", 17113);
			break;
		case 4:
			getPA().sendFrame126("Items you will keep on death(if not skulled):", 17104);
			getPA().sendFrame126("Items you will lose on death(if not skulled):", 17105);
			getPA().sendFrame126("You have the @red@Protect", 17111);
			getPA().sendFrame126("@red@Item @lre@prayer active,", 17112);
			getPA().sendFrame126("which saves you one", 17113);
			getPA().sendFrame126("extra item!", 17114);
			break;
		case 5:
			getPA().sendFrame126("Items you will keep on death(if not skulled):", 17104);
			getPA().sendFrame126("Items you will lose on death(if not skulled):", 17105);
			getPA().sendFrame126("@red@You are in a @red@Dangerous", 17111);
			getPA().sendFrame126("@red@Zone, and will lose all", 17112);
			getPA().sendFrame126("@red@if you die.", 17113);
			getPA().sendFrame126("", 17114);
			break;
		}
	}

	public void handleCannonDeath() {
		MultiCannonObject o = getCannon().cannon;

		if (o != null) {
			Position p = new Position(o.getPosition().getX(), o.getPosition().getY(), o.getPosition().getZ());
			getCannon().pickup(p);
		}
	}

	public void setCannon(DwarfCannon dwarfCannon) {
		cannon = dwarfCannon;
	}

        public int[] getQuestProgress() {
            return questProgress;
        }
        
	public int clawDamage;
	public int clawIndex;
	public int clawType = 0;
	public int clawDelay;

	public boolean[] urnCompleted = { false, false, false, false, false, false, false, false, false, false, false, false, false };

	public int plunderFloor = 0;
	public boolean[] brotherSpawned = new boolean[6];
	public boolean[] brotherKilled = new boolean[6];
	public int lastBrother;
	public byte buffer[] = null;
	public RS2Stream inStream = null, outStream = null;
	private Channel session;
	private Future<?> currentTask;
	public int packetSize = 0, packetType = -1;
	public boolean ExchangingTokensForPoints = false;
	public boolean ExchangingPointsForTokens = false;
	public String savedClan = null;
	public boolean hasAClan = false;
	public int clanId2;
	public int chatId = -1;
	public int lastClanJoined = 0;
	public boolean notNow = false;
	public boolean doNotSendClanMsg = false;
	public String clanPass;
	public boolean doNotCCLog;
	public int total99s = 0;

	public boolean dropArrow;

	public Resting.Rest rest;
}