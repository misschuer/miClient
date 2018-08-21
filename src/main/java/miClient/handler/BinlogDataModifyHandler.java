package miClient.handler;

import cc.mi.core.generate.msg.BinlogDataModify;
import cc.mi.core.handler.HandlerImpl;
import cc.mi.core.packet.Packet;
import cc.mi.core.server.ServerContext;
import io.netty.channel.Channel;
import miClient.system.ClientManager;

public class BinlogDataModifyHandler extends HandlerImpl {

	@Override
	public void handle(ServerContext nil, Channel channel, Packet decoder) {
		BinlogDataModify bdm = (BinlogDataModify)decoder;
		ClientManager.INSTANCE.onBinlogDatasUpdated(bdm.getBinlogInfoList());
	}
}
