package miClient.net;

import cc.mi.core.handler.ChannelHandlerGenerator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter implements ChannelHandlerGenerator {
	@Override
	public ChannelHandler newChannelHandler() {
		return new ClientHandler();
	}

}
