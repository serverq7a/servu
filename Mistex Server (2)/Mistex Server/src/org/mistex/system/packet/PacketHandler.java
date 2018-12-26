package org.mistex.system.packet;

import org.mistex.game.MistexConfiguration;
import org.mistex.game.world.content.dialogue.Dialogue;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.packets.AttackPlayer;
import org.mistex.system.packet.packets.Bank10;
import org.mistex.system.packet.packets.Bank5;
import org.mistex.system.packet.packets.BankAll;
import org.mistex.system.packet.packets.BankSearch;
import org.mistex.system.packet.packets.BankX1;
import org.mistex.system.packet.packets.BankX2;
import org.mistex.system.packet.packets.ChallengePlayer;
import org.mistex.system.packet.packets.ChangeAppearance;
import org.mistex.system.packet.packets.ChangeRegions;
import org.mistex.system.packet.packets.Chat;
import org.mistex.system.packet.packets.ClanChat;
import org.mistex.system.packet.packets.ClickItem;
import org.mistex.system.packet.packets.ClickNPC;
import org.mistex.system.packet.packets.ClickObject;
import org.mistex.system.packet.packets.ClickingButtons;
import org.mistex.system.packet.packets.ClickingInGame;
import org.mistex.system.packet.packets.ClickingStuff;
import org.mistex.system.packet.packets.CommandPacket;
import org.mistex.system.packet.packets.DropItem;
import org.mistex.system.packet.packets.FollowPlayer;
import org.mistex.system.packet.packets.IdleLogout;
import org.mistex.system.packet.packets.ItemClick2;
import org.mistex.system.packet.packets.ItemClick3;
import org.mistex.system.packet.packets.ItemOnGroundItem;
import org.mistex.system.packet.packets.ItemOnItem;
import org.mistex.system.packet.packets.ItemOnNpc;
import org.mistex.system.packet.packets.ItemOnObject;
import org.mistex.system.packet.packets.ItemOnPlayer;
import org.mistex.system.packet.packets.MagicOnFloorItems;
import org.mistex.system.packet.packets.MagicOnItems;
import org.mistex.system.packet.packets.MoveItems;
import org.mistex.system.packet.packets.PickupItem;
import org.mistex.system.packet.packets.PrivateMessaging;
import org.mistex.system.packet.packets.RemoveItem;
import org.mistex.system.packet.packets.ResetExpCounter;
import org.mistex.system.packet.packets.SilentPacket;
import org.mistex.system.packet.packets.Trade;
import org.mistex.system.packet.packets.Walking;
import org.mistex.system.packet.packets.WearItem;

public class PacketHandler {

	private static Client c;
	
	private static PacketType packetId[] = new PacketType[256];

	static {
		SilentPacket u = new SilentPacket();
		packetId[3] = u;
		packetId[202] = u;
		packetId[77] = u;
		packetId[86] = u;
		packetId[78] = u;
		packetId[36] = u;
		packetId[226] = u;
		packetId[246] = u;
		//packetId[218] = new ReportPacket();
		packetId[14] = new ItemOnPlayer();
		packetId[148] = u;
		packetId[183] = u;
		packetId[230] = u;
		packetId[136] = u;
		packetId[189] = u;
		packetId[152] = u;
		packetId[200] = u;
		packetId[85] = u;
		packetId[165] = u;
		packetId[238] = u;
		packetId[150] = u;
		packetId[40] = new Dialogue();
		ClickObject co = new ClickObject();
		packetId[132] = co;
		packetId[252] = co;
		packetId[70] = co;
		packetId[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packetId[72] = cn;
		packetId[131] = cn;
		packetId[155] = cn;
		packetId[17] = cn;
		packetId[21] = cn;
		packetId[16] = new ItemClick2();
		packetId[75] = new ItemClick3();
		packetId[122] = new ClickItem();
		packetId[241] = new ClickingInGame();
		packetId[4] = new Chat();
		packetId[236] = new PickupItem();
		packetId[87] = new DropItem();
		packetId[185] = new ClickingButtons();
		packetId[130] = new ClickingStuff();
		packetId[103] = new CommandPacket(c);
		packetId[214] = new MoveItems();
		packetId[237] = new MagicOnItems();
		packetId[181] = new MagicOnFloorItems();
		packetId[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packetId[73] = ap;
		packetId[249] = ap;
		packetId[128] = new ChallengePlayer();
		packetId[39] = new Trade();
		packetId[139] = new FollowPlayer();
		packetId[41] = new WearItem();
		packetId[145] = new RemoveItem();
		packetId[117] = new Bank5();
		packetId[43] = new Bank10();
		packetId[129] = new BankAll();
		packetId[101] = new ChangeAppearance();
		PrivateMessaging pm = new PrivateMessaging();
		packetId[188] = pm;
		packetId[126] = pm;
		packetId[215] = pm;
		packetId[59] = pm;
		packetId[95] = pm;
		packetId[133] = pm;
		packetId[135] = new BankX1();
		packetId[208] = new BankX2();
		Walking w = new Walking();
		packetId[98] = w;
		packetId[164] = w;
		packetId[248] = w;
		packetId[53] = new ItemOnItem();
		packetId[192] = new ItemOnObject();
		packetId[25] = new ItemOnGroundItem();
		ChangeRegions cr = new ChangeRegions();
		packetId[121] = cr;
		packetId[210] = cr;
		ClanChat cc = new ClanChat();
		packetId[60] = cc;
		packetId[63] = cc;
		packetId[64] = cc;
		packetId[65] = cc;
		packetId[61] = new BankSearch();
		packetId[62] = new ResetExpCounter();
	}
	
	public static void processPacket(Client c, int packetType, int packetSize) {
        PacketType p = packetId[packetType];
        if(p != null && packetType > 0 && packetType < 257 && packetType == c.packetType && packetSize == c.packetSize) {
            if (MistexConfiguration.sendServerPackets && c.playerRights == 3) {
                c.sendMessage("PacketType: " + packetType + ". PacketSize: " + packetSize + ".");
            }
            try {
                p.processPacket(c, packetType, packetSize);
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            c.disconnected = true;
            System.out.println(c.playerName + "is sending invalid PacketType: " + packetType + ". PacketSize: " + packetSize);
        }
    }

/*	public static void processPacket(Client c, int packetType, int packetSize) {
		if (packetType == -1) {
			return;
		}
		PacketType p = packetId[packetType];
		if (p != null) {
			try {
				// System.out.println("packet: " + packetType);
				p.processPacket(c, packetType, packetSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Unhandled packet type: " + packetType+ " - size: " + packetSize);
		}
	}*/

}
