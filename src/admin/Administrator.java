package admin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import Segment.client_Segment;
import Segment.server_Segment;

public class Administrator {
	public Socket socket= null;
	public Administrator() {
		try {
			socket = new Socket("localhost", 8000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client_Segment sendseg = new client_Segment();
		sendseg.head = 4;
		ObjectOutputStream send = null;
		ObjectInputStream recv = null;
		try {
			//发送到服务器
			send = new ObjectOutputStream(socket.getOutputStream());
			send.writeObject(sendseg);
	        send.flush();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			Adminsquare adsqr=new Adminsquare(socket,1);
			server_Segment recvseg = null;
			while (true) {
				recv = new ObjectInputStream(socket.getInputStream());
				recvseg = (server_Segment)recv.readObject();
				switch(recvseg.head) {
					case 3:				
						System.out.println("type 3");
						adsqr.setup(recvseg,null);
						break;
					case 6:
						System.out.println("type 6");
						int i=0;
		        		for(;i<recvseg.dairylist.size();i++){
		        			 adsqr.add_result(recvseg.dairylist.get(i));
		        		}
		        		adsqr.showresult(socket);
		        		break;
		        	default:
		        		break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Administrator();
	}

}
