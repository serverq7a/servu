package org.mistex.game.world.content;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Logger;

import org.mistex.game.Mistex;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerAssistant;

/**
 * 
 * @author Michael | Chex
 */
public class SkillLead {

	public class SkillLeaderData implements Comparable<SkillLeaderData> {
		private int skillExp;
		private String name;

		public SkillLeaderData(int skillExp, String name) {
			this.skillExp = skillExp;
			this.name = name;
		}

		public int skillExp() {
			return skillExp;
		}

		public String getName() {
			return name;
		}

		@Override
		public int compareTo(SkillLeaderData toCheck) {
			if (toCheck.skillExp() > skillExp() && !toCheck.getName().equalsIgnoreCase(getName()))
				return 1;
			return 0;
		}
	}

	public static SkillLeaderData[] leaders = new SkillLeaderData[25];

	public static File saveFile = new File("./data/skillLeaders.dat");

	public static void loadLeaders() {
		try {
			if (!saveFile.exists()) {
				System.out.println("Creating new Skill Leader save file");
				saveLeaders();
				return;
			}

			DataInputStream in = new DataInputStream(new FileInputStream(saveFile));

			for (int index = 0; index < leaders.length; index++) {
				leaders[in.readInt()] = new SkillLead().new SkillLeaderData(in.readInt(), in.readUTF());
			}

			Logger.getLogger(Mistex.class.getName()).info("Skill Leaders have been loaded succesfully.");

			in.close();
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public static void saveLeaders() {
		try {
			DataOutputStream out = new DataOutputStream(new FileOutputStream(saveFile));
			for (int index = 0; index < leaders.length; index++) {
				if (leaders[index] == null)
					leaders[index] = new SkillLead().new SkillLeaderData(0, "");
				out.writeInt(index);
				out.writeInt(leaders[index].skillExp());
				out.writeUTF(leaders[index].getName());
			}
			out.close();
		} catch (Exception e) {
			Logger.getLogger(Mistex.class.getName()).warning("Error saving leaders.");
		}
	}

	public static void checkNewLeader(Client c, int skill) {
		if (c.playerRights == 3 || c.playerRights == 2)
			return;

		if (c.getPA().getLevelForXP(c.playerXP[skill]) < 99)
			return;

		SkillLeaderData data = new SkillLead().new SkillLeaderData(c.playerXP[skill], c.playerName);

		if (leaders[skill].compareTo(data) == 1) {
			Client toTrim = ((Client) World.getPlayer(leaders[skill].getName()));
			if (toTrim != null) {
				if (toTrim.total99s > 1)
					toTrim.getItems().trimCapes();
				toTrim.sendMessage("[ <col=C42BAD>Mistex</col> ] You have just <col=FF0000>lost</col> the lead in <col=C42BAD>" + MistexUtility.capitalize(PlayerAssistant.getSkillName(skill)) + "</col>!");
			}

			leaders[skill] = data;

			c.sendMessage("[ <col=C42BAD>Mistex</col> ] You have just gained the lead in <col=C42BAD>" + MistexUtility.capitalize(PlayerAssistant.getSkillName(skill)) + "</col>!");
			saveLeaders();
		} else {
			for (int index = 0; index < leaders.length; index++) {
				if (leaders[index].getName().equalsIgnoreCase(c.playerName)) {
					leaders[index] = data;
					saveLeaders();
					break;
				}
			}
		}
	}
}