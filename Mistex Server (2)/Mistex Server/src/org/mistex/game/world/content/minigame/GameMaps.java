package org.mistex.game.world.content.minigame;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;


public class GameMaps {
	
	public int lumbridgeX, lumbridgeY;
	public int yanilleX, yanilleY;
	public int varrockX, varrockY;
	public int faladorX, faladorY;
	public int height;
	public int mapId;
	
	public static final int MAXIMUM_MAPS = 1;
	
	enum MapPack {
		TILED(0, 2, TILED_RESPAWN, new int[] {2655, 10071}, new int[] {2659, 10091}, new int[] {2647, 10076}, new int[] {2664, 10081}),
		CLAN_WARS(1, 0, CLAN_WARS_RESPAWN, new int[] {2884, 5900}, new int[] {2857, 5919}, new int[] {2884, 5941}, new int[] {2906, 5924});
		
		int id, height;
		int[][] respawns;
		int[] lumbridgeCoords, varrockCoords, yanilleCoords, faladorCoords;
		
		MapPack(int id, int height, int[][] respawns, int[] lumby, int[] varrock, int[] yanille, int[] falador) {
			this.id = id;
			this.height = height;
			this.respawns = respawns;
			this.lumbridgeCoords = lumby;
			this.varrockCoords = varrock;
			this.yanilleCoords = yanille;
			this.faladorCoords = falador;
		}
		
		public int getId() {
			return id;
		}
		
		public int getHeight() {
			return height;
		}
		
		public int getRespawns()[][] {
			return respawns;
		}
		
		public int getLumbridgeCoords()[] {
			return lumbridgeCoords;
		}
		
		public int getVarrockCoords()[] {
			return varrockCoords;
		}
		
		public int getYanilleCoords()[] {
			return yanilleCoords;
		}
		
		public int getFaladorCoords()[] {
			return faladorCoords;
		}
	}
	
	public static MapPack forId(int mapId) {
		for(MapPack map : MapPack.values()) {
			if(map.getId() == mapId)
				return map;
		}
		return null;
	}
	
	public static final int TILED_RESPAWN[][] = {
		{2658, 10085}, {2649, 10087}, {2650, 10076},
		{2661, 10075},
	};
	
	public static final int CLAN_WARS_RESPAWN[][] = {
		{2873, 5931}, {2885, 5931}, {2897, 5924},
		{2892, 5912}, {2869, 5922}, {2882, 5923},
		{2887, 5919},
	};
	
	public GameMaps(int mapId) {
		this.mapId = mapId;
	}
	
	public void initialize() {
		loadMap(mapId);
	}
	
	private void loadMap(int mapId) {
		MapPack map = forId(mapId);
		if(map != null) {
			lumbridgeX = map.getLumbridgeCoords()[0];
			lumbridgeY = map.getLumbridgeCoords()[1];
			varrockX = map.getVarrockCoords()[0];
			varrockY = map.getVarrockCoords()[1];
			faladorX = map.getFaladorCoords()[0];
			faladorY = map.getFaladorCoords()[1];
			yanilleX = map.getYanilleCoords()[0];
			yanilleY = map.getYanilleCoords()[1];
			height = map.getHeight();
		} else
			System.out.println("GUILD WARS MAP IS NULL!");
	}
	
	public void spawn(Client player) {
		//if(player.getGuildName().equalsIgnoreCase(Configuration.GUILD_LUMBRIDGE))
			//player.getPA().movePlayer(lumbridgeX, lumbridgeY, height);
		//if(player.getGuildName().equalsIgnoreCase(Configuration.GUILD_YANILLE))
			//player.getPA().movePlayer(yanilleX, yanilleY, height);
		//if(player.getGuildName().equalsIgnoreCase(Configuration.GUILD_VARROCK))
			//player.getPA().movePlayer(varrockX, varrockY, height);
		//if(player.getGuildName().equalsIgnoreCase(Configuration.GUILD_FALADOR))
			//player.getPA().movePlayer(faladorX, faladorY, height);
	}

	public void respawn(Client player) {
		MapPack map = forId(mapId);
		if(map != null) {
			int index = MistexUtility.random(map.getRespawns().length - 1);
			player.getPA().movePlayer(map.getRespawns()[index][0], map.getRespawns()[index][1], height);
		}
	}

}
