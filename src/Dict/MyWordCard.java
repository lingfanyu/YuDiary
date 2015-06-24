package Dict;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class MyWordCard extends JFrame implements OnlineDictDataConsts {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel pic = new JLabel();
	
	private JButton baidu = new JButton();
	private JButton youdao = new JButton();
	private JButton bing = new JButton();
	private JLabel line = new JLabel();
		
	WordCard jcard = new WordCard(this);
	
	private JButton jok = new JButton("");
	
	private int dictionary = -1;

	String word = null;
	
	public MyWordCard(final DictionaryClient client, final String[] wordList) {
		
		final JList<String> jword = new JList<String>(wordList);
		JScrollPane jw = new JScrollPane(jword);
			
		ImageIcon imgpic = new ImageIcon("pic/card1.png");
		ImageIcon sendpic = new ImageIcon("icon/send.png");
		ImageIcon linepic = new ImageIcon("pic/line.png");
		
		setLayout(null);
		Color bg = new Color(238,238,238);
		pic.setBounds(0,0,150,75);
		pic.setIcon(imgpic);
		jw.setBounds(0,75,150,352);
		jword.setOpaque(false);
		
		for (int i = 0; i < 3; i++) {
			JButton temp = new JButton(new ImageIcon("pic/card" + (i + 2) + ".png"));
			temp.setBounds(170 + 140 * i,i + 14 + 3*(1-Math.abs(i-1)),122,37);
			temp.setBackground(bg);
			temp.setBorderPainted(false);
			add(temp);
			dictionary = i;
			temp.addActionListener(
				new ActionListener() {
					private int i = dictionary;
					public void actionPerformed(ActionEvent e) {
						int index = jword.getSelectedIndex();
						if (index < 0) 
							return;
						word = wordList[index];
						synchronized (client.toServer) {
							try {
								client.toServer.writeInt(GETWORDCARD);
								client.toServer.writeUTF(word);
								client.toServer.writeInt(i);
							} catch (IOException ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			);	
		}
		
		jok.setBounds(660,16,40,40);
		jok.setIcon(sendpic);
		jok.setBackground(bg);
		jok.setBorderPainted(false);
		jok.setToolTipText("���͵��ʿ�");
		
		line.setBounds(170,65,560,2);
		line.setIcon(linepic);	
		
		jcard.panel.setBounds(170,85,560,320);
		
		
		add(pic);
		add(jw);
		add(baidu);
		add(youdao);
		add(bing);
		add(jok);
		add(line);
		add(jcard.panel);
		
		setTitle("WordCard LIST");
		setSize(760,470);
		setLocationRelativeTo(client);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		
		baidu.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int index = jword.getSelectedIndex();
					if (index < 0) 
						return;
					synchronized (client.toServer) {
						try {
							client.toServer.writeInt(GETWORDCARD);
							client.toServer.writeUTF(wordList[index]);
							client.toServer.writeInt(0);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		);	
		jok.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int i = client.jclient.getSelectedIndex();
					Object[] list = client.clientList.toArray();
					Object possible = null;
					if (i>=0)
						possible = list[i];
					String target = (String) JOptionPane.showInputDialog
							(MyWordCard.this, "���͵��ʿ�����", "��ѡ���û�", JOptionPane.QUESTION_MESSAGE, null, list, possible);
					if (target == null)
						return;
					synchronized (client.toServer) {
						try {							
							client.toServer.writeInt(SENDWORDCARD);
							client.toServer.writeUTF(target);
							client.toServer.writeInt(dictionary);
							client.toServer.writeUTF(word);
							client.toServer.writeUTF(jcard.cmeaning.getText());
							
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		);	
	}
	
	public void setMeaning(String meaning) {
		jcard.setContent(word, meaning);
	}
}