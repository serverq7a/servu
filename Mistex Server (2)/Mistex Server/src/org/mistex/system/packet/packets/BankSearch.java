package org.mistex.system.packet.packets;

import java.util.ArrayList;
import java.util.List;

import org.mistex.game.MistexUtility;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.bank.BankFields;
import org.mistex.game.world.player.bank.BankItem;
import org.mistex.game.world.player.bank.BankTab;
import org.mistex.game.world.player.item.Item;
import org.mistex.system.packet.PacketType;

public class BankSearch implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		String text = MistexUtility.longToPlayerName2(c.getInStream().readQWord());
		text = text.replaceAll("_", " ").trim().toLowerCase();
		searchFor(c, text);
	}

	public static void searchFor(Client client, String keyword) {
		if (client.isBanking) {
			client.sendMessage("Bank searching is currently disabled.");
			return;
		}
		List<BankItem> searchItems = new ArrayList<BankItem>();
		keyword = keyword.trim().toLowerCase();
		if (keyword.length() <= 1) {
			client.sendMessage("Please be more specific.");
			return;
		}
		for (BankTab tab : client.getBank().getBankTabs()) {
			if (tab != null) {
				for (BankItem item : tab.getItems()) {
					if (item != null) {

						if (Item.getItemName(item.getID() - 1) != null && Item.getItemName(item.getID() - 1).replaceAll("_", " ").trim().toLowerCase().contains(keyword)) {
							searchItems.add(item);
						}
					}
				}
			}
		}

		client.getOutStream().createFrameVarSizeWord(53);
		client.getOutStream().writeWord(BankFields.BASE_ID + 63);
		client.getOutStream().writeWord(searchItems.size());

		for (BankItem item : searchItems) {
			if (item.getAmount() > 254) {
				client.getOutStream().writeByte(255);
				client.getOutStream().writeDWord_v2(item.getAmount());
			} else {
				client.getOutStream().writeByte(item.getAmount());
			}
			client.getOutStream().writeWordBigEndianA(item.getID());
		}
		client.getOutStream().endFrameVarSizeWord();
		client.flushOutStream();
		client.sendMessage("@ceo@\'" + MistexUtility.capitalize(keyword) + "\' returned " + searchItems.size() + " result" + (searchItems.size() != 1 ? "s" : "") + ".");
	}
}