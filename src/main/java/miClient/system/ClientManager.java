package miClient.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cc.mi.core.generate.Opcodes;
import cc.mi.core.generate.msg.CheckSession;
import cc.mi.core.generate.stru.BinlogInfo;
import cc.mi.core.handler.Handler;
import cc.mi.core.packet.Packet;
import cc.mi.core.task.base.Task;
import io.netty.channel.Channel;
import miClient.handler.BinlogDataModifyHandler;
import miClient.handler.CharInfoHanlder;

public enum ClientManager {
	INSTANCE;
	
	// 消息收到以后的回调
	private final Map<Integer, Handler> handlers = new HashMap<>();
	private final ExecutorService excutor = Executors.newSingleThreadExecutor();
	private Channel serverChannel;
	// 对象管理
	public final ClientObjectManager objManager = new ClientObjectManager();
	
	private ClientManager() {
		handlers.put(Opcodes.MSG_BINLOGDATAMODIFY, new BinlogDataModifyHandler());
		handlers.put(Opcodes.MSG_SENDCHARINFO, new CharInfoHanlder());
	}
	
	public void invokeHandler(Packet packet) {
		int opcode = packet.getOpcode();
		Handler handle = handlers.get(opcode);
		if (handle != null) {
			handle.handle(null, this.serverChannel, packet);
		}
	}
	
	public void submitTask(Task task) {
		this.excutor.submit(task);
	}
	
	public void onServerConnected(Channel channel) {
		this.serverChannel = channel;
		this.checkSession();
	}
	
	public void checkSession() {
		CheckSession cs = new CheckSession();
		cs.setFd(0);
		cs.setSessionkey("");
		this.serverChannel.writeAndFlush(cs);
	}
	
	public void onServerDisconnected() {
		this.serverChannel = null;
	}
	
	public void onBinlogDatasUpdated(List<BinlogInfo> binlogInfoList) {
		for (BinlogInfo binlogInfo : binlogInfoList) {
			this.objManager.parseBinlogInfo(binlogInfo);
		}
	}
}
