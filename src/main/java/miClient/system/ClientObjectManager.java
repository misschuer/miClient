package miClient.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.mi.core.binlog.data.BinlogData;
import cc.mi.core.constance.BinlogOptType;
import cc.mi.core.generate.stru.BinlogInfo;
import cc.mi.core.log.CustomLogger;

public enum ClientObjectManager {
	INSTANCE;
	
	static final CustomLogger logger = CustomLogger.getLogger(ClientObjectManager.class);
	protected final Map<String, BinlogData> objHash;
	private ClientObjectManager() {
		this.objHash = new HashMap<>();
	}
	
	public BinlogData get(String guid) {
		return this.objHash.get(guid);
	}
	
	public void parseBinlogInfo(BinlogInfo binlogInfo) {
		String guid = binlogInfo.getBinlogId();
		BinlogData binlogData = this.get(guid);
		if (binlogData == null) {
			binlogData = new BinlogData(1 << 6, 1 << 6);
		}
		List<Integer> intMask = binlogInfo.getIntMask();
		List<Integer> intValueChanged = binlogInfo.getIntValues();
		List<Integer> strMask = binlogInfo.getStrMask();
		List<String>  strValueChanged = binlogInfo.getStrValues();
		if (binlogInfo.getState() == BinlogOptType.OPT_NEW) {
			binlogData.setGuid(binlogInfo.getBinlogId());
			binlogData.onCreateEvent(intMask, intValueChanged, strMask, strValueChanged);
			this.objHash.put(guid, binlogData);
			logger.devLog("create guid {}", binlogData.getGuid());
		} else if (binlogInfo.getState() == BinlogOptType.OPT_UPDATE) {
			binlogData.onUpdateEvent(intMask, intValueChanged, strMask, strValueChanged);
		}
	}
}
