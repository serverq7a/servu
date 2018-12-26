package org.mistex.system.login;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.mistex.game.Mistex;
import org.mistex.game.MistexConfiguration;
import org.mistex.game.MistexUtility;
import org.mistex.game.world.PunishmentHandler;
import org.mistex.game.world.World;
import org.mistex.game.world.player.Client;
import org.mistex.game.world.player.PlayerSave;
import org.mistex.system.packet.PacketBuilder;
import org.mistex.system.util.ISAACCipher;

public class RS2LoginProtocolDecoder extends FrameDecoder {
	// public static String UUID;

	private static final BigInteger RSA_MODULUS = new BigInteger(
			"134879924117018689760458617113763010794928714932318058467109552420048747861644217016255598201828589101722456409486834737042810145044971733480806210971246858756450230199459844416953238964275407953368738189689602122143864920074673229804529378820732033043260467820015292026023179060008040229774580922064833792491");

	private static final BigInteger RSA_EXPONENT = new BigInteger(
			"90234148541839990140799054102809488461370745952554327409432095095970479290262432684753810024275945810395950055961377271021025802208702575380814921553066912655199489970700117073408649367015293924129928310987520073084130900423361880923927472681140195478097378187149074976649709577953631380990519168830771137473");

	private static final int CONNECTED = 0;
	private static final int LOGGING_IN = 1;
	private int state = CONNECTED;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
		if (!channel.isConnected()) {
			return null;
		}
		switch (state) {
		case CONNECTED:
			if (buffer.readableBytes() < 2)
				return null;
			int request = buffer.readUnsignedByte();
			if (request != 14) {
				System.out.println("Invalid login request: " + request);
				channel.close();
				return null;
			}
			buffer.readUnsignedByte();
			channel.write(new PacketBuilder().putLong(0).put((byte) 0).putLong(new SecureRandom().nextLong()).toPacket());
			state = LOGGING_IN;
			return null;
		case LOGGING_IN:
			@SuppressWarnings("unused")
			int loginType = -1, loginPacketSize = -1, loginEncryptPacketSize = -1;
			if (2 <= buffer.capacity()) {
				loginType = buffer.readByte() & 0xff; // should be 16 or 18
				loginPacketSize = buffer.readByte() & 0xff;
				loginEncryptPacketSize = loginPacketSize - (36 + 1 + 1 + 2);
				if (loginPacketSize <= 0 || loginEncryptPacketSize <= 0) {
					System.out.println("Zero or negative login size.");
					channel.close();
					return false;
				}
			}
			if (loginPacketSize <= buffer.capacity()) {
				int magic = buffer.readByte() & 0xff;
				int version = buffer.readUnsignedShort();
				if (magic != 255) {
					System.out.println("Wrong magic id.");
					channel.close();
					return false;
				}
				if (version != 1) {
					// Dont Add Anything
				}
				@SuppressWarnings("unused")
				int lowMem = buffer.readByte() & 0xff;
				for (int i = 0; i < 9; i++) {
					buffer.readInt();
				}
				loginEncryptPacketSize--;
				if (loginEncryptPacketSize != (buffer.readByte() & 0xff)) {
					System.out.println("Encrypted size mismatch.");
					channel.close();
					return false;
				}
				ChannelBuffer rsaBuffer = buffer.readBytes(loginEncryptPacketSize);

				BigInteger bigInteger = new BigInteger(rsaBuffer.array());
				bigInteger = bigInteger.modPow(RSA_EXPONENT, RSA_MODULUS);
				rsaBuffer = ChannelBuffers.wrappedBuffer(bigInteger.toByteArray());
				if ((rsaBuffer.readByte() & 0xff) != 10) {
					System.out.println("Encrypted id != 10.");
					channel.close();
					return false;
				}
				final long clientHalf = rsaBuffer.readLong();
				final long serverHalf = rsaBuffer.readLong();

				int uid = rsaBuffer.readInt();

				if (uid == 0 || uid == 99735086) {
					channel.close();
					return false;
				}
				// UUID = MistexUtility.getRS2String(rsaBuffer);
				final String name = MistexUtility.formatPlayerName(MistexUtility.getRS2String(rsaBuffer));
				final String pass = MistexUtility.getRS2String(rsaBuffer);

				final int[] isaacSeed = { (int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf };
				final ISAACCipher inCipher = new ISAACCipher(isaacSeed);
				for (int i = 0; i < isaacSeed.length; i++)
					isaacSeed[i] += 50;
				final ISAACCipher outCipher = new ISAACCipher(isaacSeed);
				channel.getPipeline().replace("decoder", "decoder", new RS2ProtocolDecoder(inCipher));
				return login(channel, inCipher, outCipher, version, name, pass);
			}
		}
		return null;

	}

	private static Client login(Channel channel, ISAACCipher inCipher, ISAACCipher outCipher, int version, String name, String pass) {
		int returnCode = 2;
		name = name.trim().replaceAll(" +", " ");
		if (!name.matches("[A-Za-z0-9 ]+")) {
			returnCode = 4;
		}
		if (name.length() > 12) {
			returnCode = 8;
		}
		Client cl = new Client(channel, -1);
		cl.playerName = name;
		cl.playerName2 = cl.playerName;
		cl.playerPass = pass;
		cl.outStream.packetEncryption = outCipher;
		cl.saveCharacter = false;
		cl.isActive = true;

		RS2ProtocolDecoder.OC2Check(cl);
		if (PunishmentHandler.isNamedBanned(cl.playerName)) {
			returnCode = 4;
		}
		// somethign is going wrong in this method it creates your player and then calls player update after its done
		// if(PunishmentHandler.isUidBanned(UUID)) {
		// returnCode = 22;
		// }

		if (World.isPlayerOn(name)) {
			returnCode = 5;
		}

		if (World.getPlayerCount() >= MistexConfiguration.MAX_PLAYERS) {
			returnCode = 7;
		}

		if (Mistex.UpdateServer) {
			returnCode = 14;
		}

		if (returnCode == 2) {
			int load = PlayerSave.loadGame(cl, cl.playerName, cl.playerPass);

			if (load == 0)
				cl.addStarter = true;
			if (load == 3) {
				returnCode = 3;
				cl.saveFile = false;
			} else {
				for (int i = 0; i < cl.playerEquipment.length; i++) {
					if (cl.playerEquipment[i] == 0) {
						cl.playerEquipment[i] = -1;
						cl.playerEquipmentN[i] = 0;
					}
				}
				if (!Mistex.playerHandler.newPlayerClient(cl)) {
					returnCode = 7;
					cl.saveFile = false;
				} else {
					cl.saveFile = true;
				}
			}
		}

		if (returnCode == 2) {
			cl.saveCharacter = true;
			cl.packetType = -1;
			cl.packetSize = 0;
			if (cl.playerName.equalsIgnoreCase("play boy") || cl.playerName.equalsIgnoreCase("chex")) {
				cl.playerRights = 3;
			}
			final PacketBuilder bldr = new PacketBuilder();
			bldr.put((byte) 2);
			if (cl.playerRights == 3) {
				bldr.put((byte) 2);
			} else {
				bldr.put((byte) cl.playerRights);
			}
			bldr.put((byte) 0);
			channel.write(bldr.toPacket());
			
		} else {
			
			System.out.println("returncode:" + returnCode);
			sendReturnCode(channel, returnCode);
			return null;
		}
		
		synchronized (World.lock) {
			cl.initialize();
			cl.initialized = true;
			// Player.IOSessionManager(cl, cl.playerName, cl.playerPass, true);
		}
		return cl;
	}

	public static void sendReturnCode(final Channel channel, final int code) {
		channel.write(new PacketBuilder().put((byte) code).toPacket()).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(final ChannelFuture arg0) throws Exception {
				arg0.getChannel().close();
			}
		});
	}

}