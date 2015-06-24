package Dict;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DictionaryClient extends JFrame implements OnlineDictDataConsts,DatabaseReturn {
	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private String word = null;
	final int nThreads = 5;
	private ExecutorService executor = Executors.newFixedThreadPool(nThreads);
	private Runnable[] search = {new BaiduSearch(),	new YoudaoSearch(),	new BingSearch()};
	private JTextField jword = new JTextField("请输入查询的单词：");
	private JButton jsearch = new JButton("");
	private JLabel jpic1 = new JLabel();
	
	private JTextPane jarea1 = new JTextPane();
	private JTextPane jarea2 = new JTextPane();
	int index = 0;
	private int currentShowingPanel = 0;
	
	JButton jportrait = new JButton();
	JTextField jname = new JTextField("游客");
	private JLabel jpic2 = new JLabel();
	JList<String> jclient = new JList<String>();
	JScrollPane jspclient = new JScrollPane(jclient);
	private JButton[] jb = new JButton[3];
	private JScrollPane[] sp = new JScrollPane[3];
	private JTextPane[] jta = new JTextPane[3];
	private JPanel[] backdrop = new JPanel[3];
	private JButton jwordcard = new JButton();
	private JButton jfavor = new JButton();
	
	private JButton jmenu = new JButton("");
	private JButton jview = new JButton("");
	
	private Socket socket = null;
	//IO streams
	DataOutputStream toServer = null;
	private DataInputStream fromServer = null;
	
	private boolean wordTyped = false;
	LoginFrame loginFrame = null;
	RegisterFrame registerFrame = null;
	private WordCard wordCard = null;
	private WordCard receiveWordCard = null;
	private MyWordCard myWordCard = null;
	DefaultListModel<String> clientList = null;
	
	
	public DictionaryClient() {
		String ipAddr = "127.0.0.1";//JOptionPane.showInputDialog("请输入服务器IP:");
		System.out.println(ipAddr);
		if (ipAddr == null)
			return;
		try {
			//create a socket
			socket = new Socket(ipAddr, 1234);
			//data input and output streams
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException ex) {
		//	ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "服务器连接超时");
		//	System.exit(0);
		}
				
		addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e){
					if (jname.getText().equals("游客"))
						return;
					try {
						if (socket != null) {
							toServer.writeInt(CLIENTSHUTDOWN);						
							toServer.close();
							fromServer.close();
							socket.close();
						}
					}catch (IOException ex) {
					//	ex.printStackTrace();
					}
				}
			}
		);
		
		
 		setSize(630,480);
 		setResizable(false);
 		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		setLayout(null);
 		setLocationRelativeTo(null);

//		ImageIcon picicon1 = new ImageIcon("bg/bg_sky.gif");
		ImageIcon picicon2 = new ImageIcon("pic/mpic2.png");
		ImageIcon searchicon = new ImageIcon("icon/search.png");
		ImageIcon pic0 = new ImageIcon("pic/pic0.jpg");
		ImageIcon pmenu = new ImageIcon("icon/menu.png");
		ImageIcon pview = new ImageIcon("icon/wc.png");
		ImageIcon addicon = new ImageIcon("icon/add.png");
		ImageIcon likeicon = new ImageIcon("icon/like.png");
		
		Font font0 = new Font("微软雅黑",Font.PLAIN,15);
		
		jword.setBounds(10, 10, 497, 35);
		jword.setForeground(Color.gray); 
		jword.addMouseListener(new MouseAdapter(){ 
			public void mousePressed(MouseEvent e){ 
				if (!wordTyped) {
					jword.setForeground(Color.black); 
					jword.setText(null);
					wordTyped = true;
				}
			} 
		});
		
		jword.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (!wordTyped) {
						jword.setForeground(Color.black); 
						jword.setText(null);
						wordTyped = true;
					}
				}
			}
		);
		
		jsearch.setBounds(505, 10, 107, 34);
		jsearch.setIcon(searchicon);
		
		jpic1.setBounds(25, 53, 230, 129);
//		jpic1.setBounds(25, 53, 490, 129);
//		jpic1.setIcon(picicon1);
		SimpleAttributeSet aSet = new SimpleAttributeSet();
		SimpleAttributeSet bSet = new SimpleAttributeSet();       
		StyleConstants.setFontFamily(aSet, "华文隶书");  
		StyleConstants.setFontSize(aSet, 18);  
		StyleConstants.setLeftIndent(aSet, 5);
		StyleConstants.setRightIndent(aSet, 5);
		StyleConstants.setSpaceAbove(aSet, 10);
		
		StyledDocument doc1 = jarea1.getStyledDocument();    
		jarea1.setBounds(250, 53, 250, 81);
	//	jarea1.setText("Your dream doesn't have an expiration date. Take a deep breathe and then try again");
		jarea1.setEditable(false);
		doc1.setParagraphAttributes(0, doc1.getLength(), aSet, false);
	//	jarea1.setLineWrap(true);	
	//	jarea1.setFont(font1);
		StyleConstants.setFontFamily(bSet, "微软雅黑");  
		StyleConstants.setFontSize(bSet, 12);  
		StyleConstants.setLeftIndent(bSet, 5);
		StyleConstants.setRightIndent(bSet, 5);
		StyleConstants.setSpaceBelow(bSet, 10);
		
		StyledDocument doc2 = jarea2.getStyledDocument();    
		jarea2.setBounds(250, 134, 250, 48);
	//	jarea2.setText("�����������ڣ������,�����ԡ�");
		jarea2.setForeground(Color.gray);
		jarea2.setEditable(false);
		doc2.setParagraphAttributes(0, doc2.getLength(), bSet, false);
		
		jportrait.setBounds(515, 69, 80, 80);
		jportrait.setIcon(pic0);
		jname.setBounds(515, 150, 80, 20);
		
		jname.setFont(font0);
		jname.setBorder(null);
		jname.setEditable(false);
		jname.setHorizontalAlignment(JTextField.CENTER);
		
		jpic2.setBounds(29, 191, 120, 69);
		jpic2.setIcon(picicon2);
		jspclient.setBounds(29, 260, 120, 134);
		jspclient.setBorder(null);
		jspclient.setOpaque(false);
		jspclient.getViewport().setOpaque(false);
		jmenu.setBounds(5,400,40,40);
		jmenu.setIcon(pmenu);
		jmenu.setBackground(new Color(238,238,238));
 		jmenu.setBorder(null);
		jview.setBounds(50, 410, 40, 31);
		jview.setIcon(pview);
		jview.setBorder(null);
		jview.setToolTipText("我的单词卡");
 		
 		for (index = 0; index < 3; index++) { 			
 			backdrop[index] = new JPanel() {
 		 		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				private int i = 0;
				{
					i = index;
 			 		setOpaque(false);
 			 		setLayout(new BorderLayout());
 		 		}
 				public void paintComponent(Graphics g) {
 					g.drawImage(new ImageIcon("bg/mbg" + i + ".png").getImage(), 0, 0, this);
 					super.paintComponents(g);
 				}
 			};
 		}
 		
 		SimpleAttributeSet[] Set = new SimpleAttributeSet[3];
 		String[] dictName = {"baidu", "youdao", "bing"};
 		String[] dictionaryName = {"百度","有道","必应"}; 
 		for (int i = 0; i < 3; i++) {
 			jb[i] = new JButton(new ImageIcon("icon/" + dictName[i] + ".jpg"));
 			jb[i].setBorder(null);
 			jb[i].setBounds(164, 212 + i * 60, 40, 40);
 			jb[i].setToolTipText(dictionaryName[i]);
 			add(jb[i]);
 			jta[i] = new JTextPane();
			jta[i].setEditable(false);
			jta[i].setOpaque(false);
		//	jta[i].setLineWrap(true);
			sp[i] = new JScrollPane(jta[i]);
			sp[i].setBorder(null);
			sp[i].setOpaque(false);
			sp[i].getViewport().setOpaque(false);
			backdrop[i].add(sp[i]);
			backdrop[i].setBounds(235-10*i,210-10*i,333,206);
			Set[i] = new SimpleAttributeSet();       
			StyleConstants.setFontFamily(Set[i], "微软雅黑");  
			StyleConstants.setFontSize(Set[i], 13);  
			StyleConstants.setLeftIndent(Set[i], 20);
			StyleConstants.setRightIndent(Set[i], 20);
			StyleConstants.setSpaceAbove(Set[i], 10);
			StyleConstants.setSpaceBelow(Set[i], 10);
			StyleConstants.setFirstLineIndent(Set[i], 0);
			
			StyledDocument doc = jta[i].getStyledDocument();           
	        doc.setParagraphAttributes(0, doc.getLength(), Set[i], false);  
			add(backdrop[i]);
			sp[i].setPreferredSize(new Dimension(253,150));
			sp[i].setLocation(50, 28);
		}		
 	 	 
 		jwordcard = new JButton("");
 		jwordcard.setBounds(575, 320, 30, 30);
 		jwordcard.setBorder(null);
 		jwordcard.setBackground(new Color(238,238,238));
 		jwordcard.setIcon(addicon);
 		jwordcard.setToolTipText("创建单词卡");
 		jfavor = new JButton("");
 		jfavor.setBounds(575, 360, 30, 30);
 		jfavor.setBorder(null);
 		jfavor.setBackground(new Color(238,238,238));
 		jfavor.setIcon(likeicon);
 		jfavor.setToolTipText("赞");
 		
 		Image img = toolkit.getImage("icon/dic.png");
		setIconImage(img);
 		add(jword);
 		add(jsearch);
 		add(jpic1);
 		add(jarea1);
 		add(jarea2);
 		
 		add(jportrait);
 		add(jname);
 		add(jpic1);
 		add(jpic2);
 		add(jspclient);
 		add(jmenu);
 		add(jview);
 		add(jwordcard);
 		add(jfavor);
 		
 		JMenuBar jmb = new JMenuBar();
 		
 		JMenu fileMenu = new JMenu("File");
 		JMenu helpMenu = new JMenu("Help");
 		jmb.add(fileMenu);
 		jmb.add(helpMenu);
 		final PopupMenuDemo pop1 = new PopupMenuDemo(this, false);
 		final PopupMenuDemo pop2 = new PopupMenuDemo(this, true);
 		
 		jsearch.addActionListener(
 				new ActionListener() {
 					public void actionPerformed(ActionEvent e) {
 						searchWord();					
 					}			
 				}
 			);
 			
		jword.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					searchWord();
				}
			}
		);
		
		jfavor.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jta[currentShowingPanel].getText().length() == 0)
						return;
					if (jname.getText().equals("游客")) {
						JOptionPane.showMessageDialog(DictionaryClient.this, "请先登录");
						return;
					}
					try {
						toServer.writeInt(FAVOR);
						toServer.writeUTF(word);
						toServer.writeInt(currentShowingPanel);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		);
		
		jwordcard.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jta[currentShowingPanel].getText().length() == 0)
						return;
					if (jname.getText().equals("游客")) {
						JOptionPane.showMessageDialog(DictionaryClient.this, "请先登录");
						return;
					}
					if (wordCard != null && wordCard.frame.isShowing())
						wordCard.frame.dispose();
					wordCard = new WordCard(DictionaryClient.this,word,jta[currentShowingPanel].getText(),currentShowingPanel);	
				}
			}
		);
		
		for (int i = 0; i < 3; i++)
			jb[i].addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton src = (JButton)(e.getSource());
						int j = 0;
						for (j = 0; j < 3; j++)
							if (src == jb[j]) 
								break;
						if (j ==3) {
							System.out.println("Unknown Button Event Source!");
							return;
						}
						currentShowingPanel = j;
						for (int k = 0; k < 3; k++) 
							remove(backdrop[k]);
						
						for (int k = 0; k < 3; k++) { 
							backdrop[(j + k)%3].setBounds(235 - k * 10,210 - k * 10,333,206);
					 		add(backdrop[(j + k)%3]);
						}
						revalidate();
						repaint();
					 }
				}
			);
 		
 		jmenu.addMouseListener(
 			new MouseAdapter() {
				public void mousePressed(MouseEvent e){
					if (!jname.getText().equals("游客"))
						pop1.showPopup(e);
					else 
						pop2.showPopup(e);
				}
				public void  mouseReleased(MouseEvent e) {
					if (!jname.getText().equals("游客"))
						pop1.showPopup(e);
					else 
						pop2.showPopup(e);
				}
 			}
 		);
 		
 		
 		jview.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (jname.getText().equals("游客")) {
						JOptionPane.showMessageDialog(DictionaryClient.this, "请先登录");
						return;
					}
					synchronized (toServer) {
						try {
							toServer.writeInt(GETWORDLIST);
						} catch(IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
 		);
 		
 		
 		jportrait.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (!jname.getText().equals("游客"))
						return;
					if (loginFrame != null && loginFrame.isShowing())
						loginFrame.dispose();
					loginFrame = new LoginFrame(DictionaryClient.this);							
				}
			}
		);
 		
 		executor.execute(new updateMainUI());
 		
 		setVisible(true);
 		if (socket != null)
 			startListening();
	}
	
	private void startListening() {
		while (true) {
			int type = OnlineDictDataConsts.UNDEFINED;
			try {
				type = fromServer.readInt();
			
				switch (type) {
					case REGISTER:registerHandle();break;
					case LOGIN:loginHandle();break;
					case CURRENTUSER:currentUserHandle();break;
					case FORCEDOFFLINE:forcedOfflineHandle();break;			
					case FAVOR:favorHandle();break; 
					case FAVORCOUNT:favorCountHandle();break;
					case RECEIVEWORDCARD:receiveWordCardHandle();break;
					case SENDWORDSUCCEED:sendWordSucceedHandle();break;
					case SENDWORDFAIL:sendWordFailHandle();break;
					case SAVEWORDCARD:saveWordCardHandle();break;
					case GETWORDLIST:getWordListHandle();break;
					/*	case GETWORDDICTIONARY:getWordDictionaryHandle();break;*/
					case GETWORDCARD:getWordCardHandle();break; 
					default: System.out.println("Service type error!\n" + type);System.exit(0);
				}	
				
			} catch (IOException e) {
			//	e.printStackTrace();
				JOptionPane.showMessageDialog(this, "您与服务器的连接已断开!");
				jclient.setModel(new DefaultListModel<String>());				
				jportrait.setIcon(new ImageIcon("pic/pic0.jpg"));
				jname.setText("游客");
				jclient.setModel(new DefaultListModel<String>());
				closeAll();
				return;
			}
		}
	}
	
	private void searchWord() {
		word = jword.getText();	
		for (int i = 0; i < 3; i++)
			jta[i].setText("");
		if (!wordTyped || word.length() == 0)
			return;
		if(!is_valid()) {
			JOptionPane.showMessageDialog(this, "wrong input!");
			return;
		} 
		for (int i = 0; i < 3; i++) 
			executor.execute(search[i]);
		try {
			toServer.writeInt(FAVORCOUNT);
			toServer.writeUTF(word);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void loginHandle() throws IOException {
		int result = fromServer.readInt();
		switch (result) {
			case USERNOTEXIST:
				JOptionPane.showMessageDialog(this, "该用户不存在");
				return;
			case PASSWORDERROR:
				JOptionPane.showMessageDialog(this, "密码错误");
				return;
			case PASSWORDCORRECT:
				JOptionPane.showMessageDialog(this, "登陆成功");
				break;
			default:
				JOptionPane.showMessageDialog(this, "登陆失败");
				return;
		}
		jname.setText(fromServer.readUTF());	
		if (result == PASSWORDCORRECT) {
			int portrait = fromServer.readInt();
			jportrait.setIcon(new ImageIcon("pic/pic" + portrait + ".jpg"));
		}
	}
	
	private synchronized void currentUserHandle() throws IOException {
		int n = fromServer.readInt();
		clientList = new DefaultListModel<String>();
		for (int i = 0; i < n; i++)
			clientList.addElement(fromServer.readUTF());
		jclient.setModel(clientList);
	}
	
	private void registerHandle() throws IOException {
		int result = fromServer.readInt();
		switch (result) {
			case ADDUSERSUCCEED:
				JOptionPane.showMessageDialog(this, "注册成功");
				break;
			case ADDUSEREXIST:
				JOptionPane.showMessageDialog(this, "用户名已存在");
				break;
			default:
				JOptionPane.showMessageDialog(this, "注册失败");
		}
	}
	
	private synchronized void forcedOfflineHandle() {
		jclient.setModel(new DefaultListModel<String>());
		JOptionPane.showMessageDialog(this, "您的账号已在其他客户端登陆");
		
		jportrait.setIcon(new ImageIcon("pic/pic0.jpg"));
		jname.setText("游客");
		jclient.setModel(new DefaultListModel<String>());
		closeAll();
	}
	
	private void favorHandle() throws IOException {
		int result = fromServer.readInt();
		System.out.println(result);
		switch (result) {
			case FAVORSUCCEED:
				JOptionPane.showMessageDialog(this, "点赞成功");
				break;
			case FAVORFAIL:
				JOptionPane.showMessageDialog(this, "您已点过赞");
				break;
			default:
				JOptionPane.showMessageDialog(this, "点赞失败");
		}
	}
	
	private void favorCountHandle() throws IOException {
		int[] count = new int[3];
		for (int i = 0; i < 3; i++) 
			count[i] = fromServer.readInt();
		int maxIndex = 0;
		int maxCount = count[0];
		for (int i = 1; i < 3; i++) 
			if (maxCount < count[i]) {
				maxIndex = i;
				maxCount = count[i];
			}
		if (maxCount == 0)
			maxIndex = 1;
		currentShowingPanel = maxIndex;
		for (int k = 0; k < 3; k++) 
			remove(backdrop[k]);
		
		for (int k = 0; k < 3; k++) { 
			backdrop[(maxIndex + k)%3].setBounds(235 - k * 10,210 - k * 10,333,206);
	 		add(backdrop[(maxIndex + k)%3]);
		}
		revalidate();
		repaint();
	}
	
	private void receiveWordCardHandle() throws IOException {
		String sender = fromServer.readUTF();
		int dictionary = fromServer.readInt();
		String w = fromServer.readUTF();
		String meaning = fromServer.readUTF();
		int result = JOptionPane.showConfirmDialog(this, "您收到来自" + sender + "的单词卡，是否查看", "收到单词卡", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.NO_OPTION)
			return;
		if (receiveWordCard != null && receiveWordCard.frame.isShowing())
			receiveWordCard.frame.dispose();
		receiveWordCard = new WordCard(this,w,meaning,dictionary);		
	}
	
	private void sendWordSucceedHandle() {
		JOptionPane.showMessageDialog(this, "单词卡发送成功");
	}
	
	private void sendWordFailHandle() {
		JOptionPane.showMessageDialog(this, "单词卡发送失败");
	}
	
	private void saveWordCardHandle() throws IOException {
		int result = fromServer.readInt();
		switch (result) {
			case ADDWORDEXISTS:
				JOptionPane.showMessageDialog(this, "单词卡已存在");
				break;
			case ADDWORDSUCCEED:
				JOptionPane.showMessageDialog(this, "单词卡保存成功");
				break;
			default:
				JOptionPane.showMessageDialog(this, "单词卡保存失败");
				break;
		}
	}
	
	private void getWordListHandle() throws IOException {
		int n = fromServer.readInt();
		String[] wordList = new String[n];
		for (int i = 0; i < n; i++)
			wordList[i] = fromServer.readUTF();
		if (jname.getText().equals("游客"))
			return;
		if (myWordCard != null && myWordCard.isShowing())
			myWordCard.dispose();
		myWordCard = new MyWordCard(this,wordList);
	}
	
	private void getWordCardHandle() throws IOException {
		String meaning = fromServer.readUTF();
		if (meaning.length() == 0) {
			JOptionPane.showMessageDialog(myWordCard, "该词典对应的单词卡不存在");
			return;
		}
		myWordCard.setMeaning(meaning);
	}
	
	void closeAll() {
		if (wordCard != null && wordCard.frame.isShowing())
			wordCard.frame.dispose();
		wordCard = null;
		if (receiveWordCard != null && receiveWordCard.frame.isShowing())
			receiveWordCard.frame.dispose();
		receiveWordCard = null;
		if (myWordCard != null && myWordCard.isShowing())
			myWordCard.dispose();
		myWordCard = null;
	}
	
	public static void main(String[] args) throws Exception {
		new DictionaryClient();	
	}
	
	private boolean is_valid() {
		String s = word;
	/*	for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (!(Character.isLetter(ch)) && ch != (' ') && ch != ('\'') && ch != '.' && ch != '-')
				return false;
		} */
		if (!(s.matches("[a-zA-Z-.\' ]*")))
			return false;
		return true;
	}
	private String changeword() {
		String s = word;
		String n = "";
		for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if(ch == ' ') {
				n += '+';
			}
			else n += ch;
		}
		return n;
	}
	class BaiduSearch implements Runnable {		
		public void run() {
			String baidu = "http://cidian.baidu.com/s?wd=";
			URL url = null;
			Scanner input = null;
			String sword = changeword();
			try {
				url = new URL(baidu + sword);
				input = new Scanner(url.openStream(),"utf-8");
			//	PrintWriter output = new PrintWriter("webpage.txt");
				String temp = null;
				while (input.hasNext()) {
					temp = input.nextLine();
					if (temp.indexOf(" 词典中没有与您搜索的关键词匹配的内容！") != -1) {
						jta[0].setText("Word Not Found");
						return;
					}
					if (temp.indexOf(new String("explain: \"")) != -1)
						break;
				}
				String pattern1 = "(?<=explain: \")(.*?)(?=<br />)";
				String pattern2 = "(?<=<br />)(.*?)(?=<br />)";
				Pattern r1 = Pattern.compile(pattern1);
				Pattern r2 = Pattern.compile(pattern2);	
				
				String meaning = "";
				Matcher m1 = r1.matcher(temp);
				if (m1.find())
					meaning = m1.group(1) + "\n";
				else {
					jta[0].setText("Word Not Found");
						return;
				}
				jta[0].setText(meaning);
				Matcher m2 = r2.matcher(temp);
				while (m2.find())
					meaning += m2.group(1) + "\n";
				meaning = meaning.replaceAll("&amp;", "&");
				meaning = meaning.replaceAll("&gt;", ">");
				meaning = meaning.replaceAll("&lt;", "<");
				jta[0].setText(meaning);
			}
			
			catch (MalformedURLException ex) {
				System.out.println("URL not found!");
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			}
			finally {
				if (input != null)
					input.close();
			}			
		}
	}
	
	class YoudaoSearch implements Runnable {
		public void run() {
			String youdao = "http://dict.youdao.com/search?q=";
			URL url = null;
			Scanner input = null;
			try {
				url = new URL(youdao + word);
				input = new Scanner(url.openStream(),"utf-8");
			//	PrintWriter output = new PrintWriter("webpage.txt");
				
				while (input.hasNext()) {
					String temp = input.nextLine();
					if (temp.indexOf(new String("<ul>")) != -1)
						break;
				}
				if (!input.hasNext()) {
					jta[1].setText("Word Not Found!\n");
					return;
				}
				String pattern ="(<li>(?!<))(.*)</li>"; 
				Pattern r = Pattern.compile(pattern);

				String meaning = "";
				while (input.hasNext()) {
					String str = input.nextLine();
					if (str.indexOf(new String("</ul>")) != -1)
						break;
					Matcher m = r.matcher(str);
					if (m.find())
						meaning += m.group(2) + "\n";
				}	
				jta[1].setText((meaning.equals(""))? "Word Not Found!" : meaning);
			}
			catch (MalformedURLException ex) {
				System.out.println("URL not found!\n");
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			}
			finally {
				if (input != null)
					input.close();	
			}
		
		}
	}
	
	class BingSearch implements Runnable {
		public void run() {
			String bing = "http://cn.bing.com/dict/search?q=";
			URL url = null;
			Scanner input = null;
			String sword = changeword();
			try {
				url = new URL(bing + sword);
				input = new Scanner(url.openStream(),"utf-8");
			//	PrintWriter output = new PrintWriter("webpage.txt");
				String temp = null;
				while (input.hasNext()) {
					temp = input.nextLine();
					if (temp.indexOf(new String("<ul>")) != -1)
						break;
				}
				if (!input.hasNext()) {
					jta[2].setText("Word Not Found!\n");
					return;
				}
				String pattern1 = "<ul>(.*?)</ul>";
				Pattern r1 = Pattern.compile(pattern1);
				Matcher m1 = r1.matcher(temp);
	
				if (m1.find()) 
					temp = m1.group(1);
				else {
					jta[2].setText("Word Not Found!\n");
					return;
				}
				
				String meaning = "";
				String pattern2 = "<li>(.*?)</li>";
				Pattern r2 = Pattern.compile(pattern2);	
				
				String pattern3 = "(<span.*?>)+(.+?)(</span>)+";
				Pattern r3 = Pattern.compile(pattern3);
				
				Matcher m2 = r2.matcher(temp);
				while (m2.find()) {
					temp = m2.group(1);
					Matcher m3 = r3.matcher(temp);
					boolean flag = false;
					while (m3.find()) {
						if (m3.group(2).charAt(0) == '网') 
							meaning += "网络" ;
						else
							meaning += m3.group(2);
						flag =true;
					}
					if (!flag) {
						jta[2].setText("Word Not Found!\n");
						return;
					}
					meaning += "\n";					
				}
				jta[2].setText(meaning);
					
			}
			catch (MalformedURLException ex) {
				System.out.println("URL not found!");
			}
			catch (IOException e) {
				System.out.println(e.getMessage());
			}
			finally {
				if (input != null)
					input.close();
			}
		}
	}
	class updateMainUI implements Runnable {
		private int numberOfPicture = 8;
		private int numberOfSentence = 7;
		private String[] english = new String[numberOfSentence];
		private String[] chinese = new String[numberOfSentence];
		public updateMainUI () {
			try {
				Scanner input = new Scanner(new File("sentence.txt"));
				for (int i = 0; i < numberOfSentence; i++) {
					english[i] = input.nextLine();
					chinese[i] = input.nextLine();
				}
				input.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
		public void run() {
			int i = 0;
			int k = 0;
			while (true) {
				i = (i + 1) % numberOfPicture;
				k = (k + 1) % numberOfSentence;
				String picName = "dbg/" + i;
				if (i == 1 || i == 5)
					picName += ".gif";
				else 
					picName += ".jpg";
				jpic1.setIcon(new ImageIcon(picName));
				jarea1.setText(english[k]);
				jarea2.setText(chinese[k]);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
