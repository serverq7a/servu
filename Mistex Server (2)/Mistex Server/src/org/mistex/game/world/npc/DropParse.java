package org.mistex.game.world.npc;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DropParse {
	
	public static HashMap<Integer, List<Drop>> constantDrops = new HashMap<Integer, List<Drop>>();
	public static HashMap<Integer, List<Drop>> comonDrops = new HashMap<Integer, List<Drop>>();
	public static HashMap<Integer, List<Drop>> uncomonDrops = new HashMap<Integer, List<Drop>>();
	public static HashMap<Integer, List<Drop>> rareDrops = new HashMap<Integer, List<Drop>>();
	public static HashMap<Integer, List<Drop>> veryRareDrops = new HashMap<Integer, List<Drop>>();
	
	public static void main(String[] args) {
		String data = null;
		try {
			byte[] encoded = Files.readAllBytes(Paths.get("./Data/cfg/DropTable.txt"));
			data = StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (data == null) {
			System.out.println("Error!");
			return;
		}
		String[] components = data.replaceAll("\\r\\n|\\r|\\n|\\t", "").split("\\}");
		for (int i = 0; i < components.length; i++) {
			String[] s = components[i].split("\\{");
			String[] main = s[0].split("\"|\'");
			int npcId = Integer.parseInt(main[0].replaceAll("NPC:", "").replaceAll(" ", ""));
			List<Drop> constantList = new ArrayList<Drop>();
			List<Drop> comonList = new ArrayList<Drop>();
			List<Drop> uncomonList = new ArrayList<Drop>();
			List<Drop> rareList = new ArrayList<Drop>();
			List<Drop> veryRareList = new ArrayList<Drop>();
			if (s.length > 1) {
				for (String dat : s[1].replaceAll("<", "").replaceAll(" ", "").split(">")) {
					String[] dropData = dat.replaceAll(",", ":").split(":");
					int item = Integer.parseInt(dropData[1]);
					int min;
					int max;
					if (dropData[3].contains("-")) {
						min = Integer.parseInt(dropData[3].split("-")[0]);
						max = Integer.parseInt(dropData[3].split("-")[1]);
					} else {
						min = max = Integer.parseInt(dropData[3].split("-")[0]);
					}
					
					Drop drop = new Drop(item, min, max);
					
					switch (dropData[5]) {
					case "ALWAYS":
						constantList.add(drop);
						break;
					case "COMMON":
						comonList.add(drop);
						break;
					case "UNCOMMON":
						uncomonList.add(drop);
						break;
					case "RARE":
						rareList.add(drop);
						break;
					case "VERY_RARE":
						veryRareList.add(drop);
						break;
					case "ULTRA_RARE":
						veryRareList.add(drop);
					}
				}
			} else {
				constantList.add(new Drop(526, 1, 1));
			}
			constantDrops.put(npcId, constantList);
			comonDrops.put(npcId, comonList);
			uncomonDrops.put(npcId, uncomonList);
			rareDrops.put(npcId, rareList);
			veryRareDrops.put(npcId, veryRareList);
		}
	}
}