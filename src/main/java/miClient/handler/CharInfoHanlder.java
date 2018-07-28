package miClient.handler;

import cc.mi.core.generate.msg.CreateChar;
import cc.mi.core.generate.msg.PlayerLogin;
import cc.mi.core.generate.msg.SendCharInfo;
import cc.mi.core.generate.stru.CharCreateInfo;
import cc.mi.core.generate.stru.CharInfo;
import cc.mi.core.handler.HandlerImpl;
import cc.mi.core.packet.Packet;
import cc.mi.core.server.ServerContext;
import io.netty.channel.Channel;

public class CharInfoHanlder extends HandlerImpl {

	@Override
	public void handle(ServerContext player, Channel channel, Packet decoder) {
		SendCharInfo sci = (SendCharInfo) decoder;
		if (sci.getChars().size() == 0) {
			// 创建角色
			CreateChar cc = new CreateChar();
			CharCreateInfo charData = new CharCreateInfo();
			charData.setModelId((byte) 1);
			charData.setName("机器人1");
			
			cc.setCharData(charData);
			channel.writeAndFlush(cc);
			return;
		}
		CharInfo charInfo = sci.getChars().get(0);
		PlayerLogin pl = new PlayerLogin();
		pl.setGuid(charInfo.getGuid());
		channel.writeAndFlush(pl);
	}
}
