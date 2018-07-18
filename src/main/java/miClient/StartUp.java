package miClient;

import cc.mi.core.net.ClientCore;
import miClient.net.ClientHandler;

public class StartUp {
	public static void main(String[] args) throws Exception {
		String host = "127.0.0.1";
        int port = 8001;
		ClientCore.INSTANCE.start(host, port, new ClientHandler());
	}
}
