package Server;

import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class server_tcb {
	public Lock lock;
	public Condition client_condition;
	public Socket socket;
}
