package miClient.net;

import cc.mi.core.handler.ChannelHandlerGenerator;
import cc.mi.core.log.CustomLogger;
import cc.mi.core.packet.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import miClient.system.ClientManager;
import miClient.task.DealDataTask;

public class ClientHandler extends SimpleChannelInboundHandler<Packet> implements ChannelHandlerGenerator {
	static final CustomLogger logger = CustomLogger.getLogger(ClientHandler.class);
	
	public void channelActive(final ChannelHandlerContext ctx) {
		ClientManager.INSTANCE.onServerConnected(ctx.channel());
	}
	
	@Override
	public void channelRead0(final ChannelHandlerContext ctx, final Packet coder) throws Exception {
		ClientManager.INSTANCE.submitTask(new DealDataTask(coder));
	}
	
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ClientManager.INSTANCE.onServerDisconnected();
		ctx.fireChannelInactive();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
		throwable.printStackTrace();
		ctx.close();
	}
	
	@Override
	public ChannelHandler newChannelHandler() {
		return new ClientHandler();
	}

}
