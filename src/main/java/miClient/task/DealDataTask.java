package miClient.task;

import cc.mi.core.packet.Packet;
import cc.mi.core.task.base.AbstractTask;
import miClient.system.ClientManager;

public class DealDataTask extends AbstractTask {
	private final Packet packet;
	
	public DealDataTask(Packet packet) {
		this.packet = packet;
	}
	
	@Override
	protected void doTask() {
		ClientManager.INSTANCE.invokeHandler(this.packet);
	}
}
