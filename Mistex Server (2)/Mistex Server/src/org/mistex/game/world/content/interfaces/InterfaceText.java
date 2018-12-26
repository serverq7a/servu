package org.mistex.game.world.content.interfaces;

import org.mistex.game.world.player.Client;

public abstract class InterfaceText {
	
	public InterfaceText(Client player) {
		this.player = player;
	}
	
	protected Client player;
	
	protected abstract String[] text();

	protected abstract int startingLine();
	
	//protected abstract int interfaceId();
	
	public static void writeText(InterfaceText interfacetext) {
		int line = interfacetext.startingLine();
			for(int i1 = 0; i1 < interfacetext.text().length; i1++) {
				interfacetext.player.getPA().sendFrame126(interfacetext.text()[i1], line++);
		}
		//if (interfacetext.interfaceId() > 0)
			//interfacetext.player.getPA().showInterface(interfacetext.interfaceId());
	}
	
}

