package miClient.system;

import java.util.HashMap;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.log.CustomLogger;
import cc.mi.core.server.ServerObjectManager;

public class ClientObjectManager extends ServerObjectManager {
	static final CustomLogger logger = CustomLogger.getLogger(ClientObjectManager.class);
	protected final Map<String, BinlogData> objHash;
	
	public ClientObjectManager() {
		super(-1);
		this.objHash = new HashMap<>();
	}
	
	public BinlogData get(String guid) {
		return this.objHash.get(guid);
	}

	@Override
	protected BinlogData createBinlogData(String guid) {
		return null;
	}
}
