package Dict;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;


public class WordCard implements OnlineDictDataConsts {
	private JTextField cword = new JTextField("");
	JTextArea cmeaning = new JTextArea();
	private JScrollPane jsp = new JScrollPane(cmeaning);
	private JLabel cpic = new JLabel("");
	JFrame frame = null;
	JPanel panel = null;
	
	public WordCard(MyWordCard myWordCard) {
		panel = new JPanel();
		commonSettings();
		cword.setBounds(250,40,350,80);
		jsp.setBounds(340, 145, 200, 125);
		cpic.setBounds(50,50,240,240);
		panel.setSize(620,400);	
		panel.setBackground(new Color(204,152,102));
		panel.setLayout(null);
		panel.add(cword);
		panel.add(jsp);
		panel.add(cpic);
	}	
	
	public WordCard(final DictionaryClient client, final String word, final String meaning, final int dictionary) {
		frame = new JFrame();
		commonSettings();		
		cword.setBounds(300,40,310,80);
		jsp.setBounds(340, 145, 240, 100);		
		cpic.setBounds(50,50,240,240);
		setContent(word,meaning);
		ImageIcon saveicon = new ImageIcon("icon/save.png");
		ImageIcon sendicon = new ImageIcon("icon/send.png");
		Color cbrown = new Color(204,152,102);
		Color bg = cbrown;
		JButton jsave = new JButton("");
		JButton jsend = new JButton("");
		jsave = new JButton("");
		jsave.setBounds(375, 280, 40, 40);
		jsave.setBackground(bg);
		jsave.setBorder(null);
		jsave.setIcon(saveicon);
		jsave.setToolTipText("���浥�ʿ�");
		jsend = new JButton("");
		jsend.setBounds(455, 280, 40, 40);
		jsend.setBackground(bg);
		jsend.setBorder(null);
		jsend.setIcon(sendicon);
		jsend.setToolTipText("���͵��ʿ�");

		frame.getContentPane().setBackground(bg);
		frame.setLayout(null);
		frame.add(cword);
		frame.add(jsp);
		frame.add(cpic);
		frame.add(jsave);
		frame.add(jsend);
		
		jsave.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					synchronized (client.toServer) {
						try {
							client.toServer.writeInt(SAVEWORDCARD);
							client.toServer.writeUTF(word);
							client.toServer.writeInt(dictionary);
							client.toServer.writeUTF(meaning);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		);
		jsend.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int i = client.jclient.getSelectedIndex();
					Object[] list = client.clientList.toArray();
					Object possible = null;
					if (i>=0)
						possible = list[i];
					String target = (String) JOptionPane.showInputDialog
							(frame, "���͵��ʿ�����", "��ѡ���û�", JOptionPane.QUESTION_MESSAGE, null, list, possible);
					if (target == null)
						return;
					synchronized (client.toServer) {
						try {							
							client.toServer.writeInt(SENDWORDCARD);
							client.toServer.writeUTF(target);
							client.toServer.writeInt(dictionary);
							client.toServer.writeUTF(word);
							client.toServer.writeUTF(meaning);
							
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		);
		
		frame.setTitle("Word Card");
		frame.setSize(620,400);		
		frame.setLocationRelativeTo(client);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void commonSettings() {		
		cword.setForeground(Color.white);
		cword.setHorizontalAlignment(JTextField.CENTER);		
		cword.setOpaque(false);
		cword.setEditable(false);
		cword.setBorder(null);
		cword.setHorizontalAlignment(JTextField.CENTER);	
	
		jsp.setBorder(null);
		jsp.setOpaque(false);
		jsp.getViewport().setOpaque(false);
		Font font4 = new Font("΢���ź�",Font.PLAIN,15);
		cmeaning.setForeground(Color.white);
		cmeaning.setFont(font4);
		cmeaning.setEditable(false);
		cmeaning.setOpaque(false);
		cmeaning.setLineWrap(true);	
		cmeaning.setCaretPosition(0);		
	}
	
	public void setContent(String word, String meaning) {
		int n = word.length();
		n-=8;
		if (n < 0) n = 0;
		if (n > 16) n = 16;
		int size = 60;
		if (frame == null) {
			if (n > 13) n = 13;
			size = 50;
		}
		Font font1 = new Font("���Ĳ���",Font.BOLD,size - (int)(n * 3));
		cword.setVisible(false);
		cword.setText(word);
		cword.setFont(font1);
		cword.setVisible(true);
		cmeaning.setText(meaning);
		cmeaning.setCaretPosition(0);
		cpic.setVisible(false);
		Image image = MyImage.getImage(word);
		cpic.setIcon(new ImageIcon(image.getScaledInstance(240, 240, java.awt.Image.SCALE_SMOOTH)));
		cpic.setVisible(true);
	}
	
}