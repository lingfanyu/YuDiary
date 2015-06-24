package Dict;
import java.net.*;
import java.io.*;
import java.util.*;

public class DictionaryServer implements OnlineDictDataConsts, DatabaseReturn {
	Database database = new Database();
	TreeMap<String,DataOutputStream> map = new TreeMap<String,DataOutputStream>();
	
	public static void main(String[] args) {
		new DictionaryServer();
	}
	
	public DictionaryServer() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(1234);		
			while (true) {
				Socket socket = serverSocket.accept();
				HandleClient task = new HandleClient(socket);
				new Thread(task).start();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class HandleClient implements Runnable {
		private DataInputStream fromClient = null;
		private DataOutputStream toClient = null;
		public HandleClient(Socket socket) {
			try {
				fromClient = new DataInputStream(socket.getInputStream());
				toClient = new DataOutputStream(socket.getOutputStream());
			}
			catch (IOException ex) {
				ex.printStackTrace();
				System.exit(0);
			}
		}
		
		public void run() {
			while (true) {
				int type = OnlineDictDataConsts.UNDEFINED;
				try {
					type = fromClient.readInt();
					switch (type) {
						case REGISTER:register();break;
						case LOGIN:login();break;
						case LOGOUT:logout();break;
						case FAVOR:addFavor();break;
						case FAVORCOUNT:favorCount();break;
						case SAVEWORDCARD:saveWordCard();break;
						case SENDWORDCARD:sendWordCard();break;
						case GETWORDLIST:getWordList();break;
						case GETWORDCARD:getWordCard();break;
						case CLIENTSHUTDOWN:clientShutdown();return;
						default: System.out.println("Service type error!\n" + type);System.exit(0);
					}
				} catch (IOException e) {
				//	e.printStackTrace();
					try {
						clientShutdown();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return;
				} 
			}
		}
		
		public void register() throws IOException {
			String username = fromClient.readUTF();
			String password = fromClient.readUTF();
			int portrait = fromClient.readInt();
			int result = Database.UNDEFINED;
			synchronized (database) {
				result = database.adduser(username, password, portrait);
			}
			synchronized (toClient) {
				toClient.writeInt(REGISTER);
				toClient.writeInt(result);
			}
		}
		
		public void login() throws IOException {
			String username = fromClient.readUTF();
			String password = fromClient.readUTF();
			int result = DatabaseReturn.UNDEFINED;
			int portrait = 0;
			synchronized (database)  {
				result = database.checkPassword(username, password);
				if (result == PASSWORDCORRECT)
					portrait = database.getPortrait(username);
			}
			synchronized (toClient) {
				toClient.writeInt(LOGIN);
				toClient.writeInt(result);
				if (result == PASSWORDCORRECT) {
					toClient.writeUTF(username);
					toClient.writeInt(portrait);
				}
			}
			if (result == PASSWORDCORRECT) {
				synchronized (map) {
					if (map.containsKey(username)) {
						DataOutputStream output = map.get(username);
						output.writeInt(FORCEDOFFLINE);
						map.put(username, toClient);
						Set<Map.Entry<String, DataOutputStream>> entrySet = map.entrySet();
						synchronized (toClient) {
							toClient.writeInt(CURRENTUSER);
							toClient.writeInt(map.size());
							for (Map.Entry<String, DataOutputStream> j: entrySet) 
								toClient.writeUTF(j.getKey());
						}
					}
					else {						
						map.put(username, toClient);
						Set<Map.Entry<String, DataOutputStream>> entrySet = map.entrySet();
						for (Map.Entry<String, DataOutputStream> i: entrySet) {
							DataOutputStream output = i.getValue();
							synchronized (output) {
								output.writeInt(CURRENTUSER);
								output.writeInt(map.size());
								for (Map.Entry<String, DataOutputStream> j: entrySet) 
									output.writeUTF(j.getKey());
							}
						}
					}
				}
			}
		}
		public void logout() throws IOException {
			synchronized (map) {
				map.remove(getMyName(),toClient);
				Set<Map.Entry<String, DataOutputStream>> entrySet = map.entrySet();
				for (Map.Entry<String, DataOutputStream> i: entrySet) {
					DataOutputStream output = i.getValue();
					synchronized (output) {
						output.writeInt(CURRENTUSER);
						output.writeInt(map.size());
						for (Map.Entry<String, DataOutputStream> j: entrySet) 
							output.writeUTF(j.getKey());
					}
				}
			}
		}
		
		public void addFavor() throws IOException {
			String word = fromClient.readUTF();
			String user = getMyName();
			int dictionary = fromClient.readInt();
			int result = DatabaseReturn.UNDEFINED;
			synchronized (database) {
				result = database.addfavor(word, user, dictionary);
			}
			synchronized (toClient) {
				toClient.writeInt(FAVOR);
				toClient.writeInt(result);
			}
		}
		
		public void favorCount() throws IOException {
			String word = fromClient.readUTF();
			int[] result = null;
			synchronized (database) {
				result = database.searchword(word);
			}
			synchronized (toClient) {
				toClient.writeInt(FAVORCOUNT);
				for (int i = 0; i < 3; i++) 
					toClient.writeInt(result[i]);
			}
		}
		
		public void sendWordCard() throws IOException {
			String target = fromClient.readUTF();
			DataOutputStream temp = null;
			synchronized (map) {
				temp = map.get(target);
			}
			int dictionary = fromClient.readInt();
			String w = fromClient.readUTF();
			String m = fromClient.readUTF();
			if (temp == null) {
				synchronized (toClient) {
					toClient.writeInt(SENDWORDFAIL);
				}
				return;
			}
			synchronized (temp) {
				temp.writeInt(RECEIVEWORDCARD);
				temp.writeUTF(getMyName());
				temp.writeInt(dictionary);
				temp.writeUTF(w);
				temp.writeUTF(m);
			}
			synchronized (toClient) {
				toClient.writeInt(SENDWORDSUCCEED);
			}
		}
		
		public void saveWordCard() throws IOException {
			String username = getMyName();
			int result = DatabaseReturn.UNDEFINED;
			String word = fromClient.readUTF();
			int dictionary = fromClient.readInt();
			String meaning = fromClient.readUTF();
			if (meaning.length() > 255)
				meaning = meaning.substring(0, 255);
			synchronized (database) {
				result = database.addWord(username, word, dictionary, meaning);
			}
			synchronized (toClient) {
				toClient.writeInt(SAVEWORDCARD);
				toClient.writeInt(result);
			}
		}
		
		public void getWordList() throws IOException {
			String username = null;
			synchronized (map) {
				username = getMyName();
			}
			String [] words = null;
			synchronized (database) {
				words = database.getWords(username);
			}
			synchronized (toClient) {
				toClient.writeInt(GETWORDLIST);
				int n = words.length;
				toClient.writeInt(n);
				for (int i = 0; i < n; i++) {
					toClient.writeUTF(words[i]);
				}
			}
		}

		public void getWordCard() throws IOException {
			String username = null;
			String word = fromClient.readUTF();
			int dictionary = fromClient.readInt();
			synchronized (map) {
				username = getMyName();
			}
			String meaning = null;
			synchronized (database) {
				meaning = database.getMeaning(username, word, dictionary);
			}
			if (meaning == null)
				meaning = "";
			synchronized (toClient) {
				toClient.writeInt(GETWORDCARD);
				toClient.writeUTF(meaning);
			}
		}
		
		public void clientShutdown() throws IOException {
			String username = getMyName();
			fromClient.close();
			toClient.close();
			if (username == null)
				return;
			synchronized (map) {
				map.remove(username,toClient);
				Set<Map.Entry<String, DataOutputStream>> entrySet = map.entrySet();
				for (Map.Entry<String, DataOutputStream> i: entrySet) {
					DataOutputStream output = i.getValue();
					synchronized (output) {
						output.writeInt(CURRENTUSER);
						output.writeInt(map.size());
						for (Map.Entry<String, DataOutputStream> j: entrySet) 
							output.writeUTF(j.getKey());	
					}
				}
			}
		}
		
		private String getMyName() {
			synchronized (map) {
				Set<Map.Entry<String, DataOutputStream>> entrySet = map.entrySet();
				for (Map.Entry<String, DataOutputStream> i: entrySet) {
					if (i.getValue() == toClient)
						return i.getKey();
				}
				return null;
			}
		}
	}
}