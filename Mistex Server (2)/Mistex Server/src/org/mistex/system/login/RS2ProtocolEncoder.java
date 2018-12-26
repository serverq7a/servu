package org.mistex.system.login;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.mistex.system.packet.Packet;

public class RS2ProtocolEncoder extends OneToOneEncoder {

	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel,
			Object object) throws Exception {
		return ((Packet) object).getPayload();
	}

}
