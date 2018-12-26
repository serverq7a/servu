package org.mistex.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.util.HashedWheelTimer;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.World;
import org.mistex.game.world.clip.region.ObjectDef;
import org.mistex.game.world.content.GlobalMessages;
import org.mistex.game.world.content.SkillLead;
import org.mistex.game.world.content.clanchat.Clan;
import org.mistex.game.world.content.clanchat.ClanHandler;
import org.mistex.game.world.content.minigame.championsbattle.ChampionsBattle;
import org.mistex.game.world.content.minigame.fightcaves.FightCaves;
import org.mistex.game.world.content.minigame.fightpits.FightPits;
import org.mistex.game.world.content.minigame.pestcontrol.PestControl;
import org.mistex.game.world.content.minigame.triviabot.TriviaBot;
import org.mistex.game.world.content.minigame.warriorsguild.WarriorsGuild;
import org.mistex.game.world.content.minigame.weapongame.WeaponGame;
import org.mistex.game.world.event.CycleEventHandler;
import org.mistex.game.world.gameobject.ObjectHandler;
import org.mistex.game.world.gameobject.ObjectManager;
import org.mistex.game.world.grounditem.GroundItemHandler;
import org.mistex.game.world.npc.NPCHandler;
import org.mistex.game.world.npc.NpcDrop;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.Player;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.game.world.player.item.Item;
import org.mistex.game.world.shop.Shops;
import org.mistex.game.world.task.Task;
import org.mistex.game.world.task.TaskScheduler;
import org.mistex.system.PipelineFactory;
import org.mistex.system.util.HostBlacklist;
import org.mistex.system.util.MemoryCalculator;
import org.mistex.system.util.ShutdownHook;
import org.mistex.system.util.control.ControlPanel;

public class Mistex {

	/**
	 * Main Clan Chat
	 */
	public static Clan clan = new Clan("Mistex");

	/**
	 * Update server
	 */
	public static boolean UpdateServer = false, shutdownClientHandler;

	/**
	 * Server listening port
	 */
	private static int serverlistenerPort = 43594;

	/**
	 * Game time variable
	 */
	public static int gameTime = 0;

	/**
	 * Vote variable
	 */
	public static int voteCount = 0;
	public static String LastVoter = "";

	/**
	 * Ground items
	 */
	public static GroundItemHandler itemHandler = new GroundItemHandler();

	/**
	 * Player handler
	 */
	public static World playerHandler = new World();

	/**
	 * NPC handler
	 */
	public static NPCHandler npcHandler = new NPCHandler();

	/**
	 * Object handler
	 */
	public static ObjectHandler objectHandler = new ObjectHandler();

	/**
	 * Object manager
	 */
	public static ObjectManager objectManager = new ObjectManager();

	/**
	 * Control panel
	 */
	public static ControlPanel panel = new ControlPanel(false);

	/**
	 * Pest control
	 */
	public static PestControl pestControl = new PestControl();

	/**
	 * Weapon game
	 */
	public static WeaponGame weaponGame = new WeaponGame();

	/**
	 * Fight pits
	 */
	public static FightPits fightPits = new FightPits();

	/**
	 * Fight caves
	 */
	public static FightCaves fightCaves = new FightCaves();

	/**
	 * Champions battle
	 */
	public static ChampionsBattle championsBattle = new ChampionsBattle();

	/**
	 * Warriors guild
	 */
	private static WarriorsGuild warriorsGuild = new WarriorsGuild();

	/**
	 * Class logger
	 */
	private static final Logger logger = Logger.getLogger(Mistex.class.getName());

	/**
	 * Task scheduler
	 */
	private static final TaskScheduler taskScheduler = new TaskScheduler();

	public static TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	private Mistex() {

		ShutdownHook hook = new ShutdownHook();
		Runtime.getRuntime().addShutdownHook(hook);

		/* Object Definition Loading */
		ObjectDef.loadConfig();

		/* Region Loading */
		//Region.load();

		/* Items Loading */
		Item.load();

		/* Shops Loading */
		Shops.loadShops();

		/* Initializing Connections */
		PunishmentHandler.initialize();

		/* Black List Hosting */
		HostBlacklist.loadBlacklist();

		/* Minute */
		loadMinutesCounter();

		/* Npc Drops */
		NpcDrop.loadDrops();

		/* Initializing TriviaBot */
		taskScheduler.schedule(new TriviaBot());
		logger.info("TriviaBot initialized.");

		/* Initializing Global Messages */
		taskScheduler.schedule(new GlobalMessages());
		logger.info("Global Messages initialized.");

		/* Setting Up Login Channels */
		setupLoginChannels();
	}

	public static void main(String[] args) throws Exception {
		new Mistex().run();
		logger.info("Mistex is now online!");
		SkillLead.loadLeaders();

		/* Checking The Memory Usage */
		MemoryCalculator.handleMemoryUsage();
	}

	public void run() {
		taskScheduler.schedule(new Task(20) {
			@Override
			protected void execute() {
				setMinutesCounter(getMinutesCounter() + 1);
				for (Player player : World.players) {
					if (player == null)
						continue;
					Client c = (Client) player;
					c.getAllotment().doCalculations();
					c.getSpecialPlantOne().doCalculations();
					c.getSpecialPlantTwo().doCalculations();
					c.getFlowers().doCalculations();
					c.getBushes().doCalculations();
					c.getHops().doCalculations();
					c.getHerbs().doCalculations();
					c.getTrees().doCalculations();
					c.getFruitTrees().doCalculations();
				}
			}
		});
		taskScheduler.schedule(new Task(100) {
			@Override
			protected void execute() {
				for (Player player : World.players) {
					if (player != null) {
						((Client) player).gameTime++;
						((Client) player).getPA().sendFrame126(((Client) player).getPA().getSmallPlaytime(), 35036);
						((Client) player).getPA().sendFrame126(vpsTime(), 35026);
						((Client) player).getPA().sendFrame126("@lre@Server Votes: @gre@" + voteCount, 35027);
						PlayerSave.saveGame((Client) player);
					}
				}
				ClanHandler.process();
				System.out.println("All players have been saved.");
			}

		});

		taskScheduler.schedule(new Task() {
			@Override
			protected void execute() {
				playerHandler.process();
				npcHandler.process();
				objectManager.process();
				itemHandler.process();
				pestControl.process();
				Shops.process();
				CycleEventHandler.getSingleton().process();

			}

		});
	}

	private static void setupLoginChannels() {
		ServerBootstrap serverBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		serverBootstrap.setPipelineFactory(new PipelineFactory(new HashedWheelTimer()));
		logger.info("Login channels initialized.");
		try {
			serverBootstrap.bind(new InetSocketAddress(serverlistenerPort));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setMinutesCounter(int minutesCounter) {
		Mistex.gameTime = minutesCounter;
		try {
			File file = new File("./data/minutes.dat");
			DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
			out.writeInt(getMinutesCounter());
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadMinutesCounter() {
		try {
			File file = new File("./data/minutes.dat");
			if (file.exists()) {
				DataInputStream in = new DataInputStream(new FileInputStream(file));
				gameTime = in.readInt();
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String vpsTime() {
		return new SimpleDateFormat("EEEE, MMM dd, hh:mma").format(new Date());
	}

	public static int getMinutesCounter() {
		return gameTime;
	}

	public static WarriorsGuild getWarriorsGuild() {
		return warriorsGuild;
	}

}
