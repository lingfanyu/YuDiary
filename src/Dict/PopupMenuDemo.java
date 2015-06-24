package Dict;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.io.*;
import java.util.Scanner;

public class PopupMenuDemo extends JPopupMenu implements OnlineDictDataConsts{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PopupMenuDemo(final DictionaryClient client, boolean tourist) {
		
		if (!tourist) {
			JMenuItem jmiWord = new JMenuItem("单词卡");
			JMenuItem jmiLogout = new JMenuItem("注销");
			add(jmiWord);
			addSeparator();		
			add(jmiLogout);
			addSeparator();
		
			jmiLogout.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) { 
						synchronized (client.toServer) {
							try {
								client.toServer.writeInt(LOGOUT);
							}
							catch (IOException ex) {
								ex.printStackTrace();
							}
						}
						client.jportrait.setIcon(new ImageIcon("pic/pic0.jpg"));
						client.jname.setText("游客");
						client.jclient.setModel(new DefaultListModel<String>());	
						client.closeAll();
					}
				}
			);			
			jmiWord.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						synchronized (client.toServer) {
							try {
								client.toServer.writeInt(GETWORDLIST);
							} catch(IOException ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			);
		}
		else {
			JMenuItem jmiLogin = new JMenuItem("登陆");
			JMenuItem jmiRegister = new JMenuItem("注册");
			add(jmiLogin);
			addSeparator();
			add(jmiRegister);
			addSeparator();
			jmiLogin.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (client.loginFrame != null && client.loginFrame.isShowing())
							client.loginFrame.dispose();
						client.loginFrame = new LoginFrame(client);							
					}
				}
			);
			jmiRegister.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) { 
						if (client.registerFrame != null && client.registerFrame.isShowing())
							client.registerFrame.dispose();
						client.registerFrame = new RegisterFrame(client);
					}
				}
			);
		}
		
		JMenuItem jmiHelp = new JMenuItem("帮助");
		add(jmiHelp);
		jmiHelp.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new JFrame() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						{
							JTextArea jta = new JTextArea();
							jta.setText("");
							jta.setLineWrap(true);
							try {
								Scanner input = new Scanner(new File("Readme.txt"));
								while (input.hasNext()) {
									jta.append(input.nextLine() + "\n");
								}
								input.close();
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							jta.setEditable(false);
							jta.setCaretPosition(0);
							add(new JScrollPane(jta));
							setSize(400,300);
							setLocationRelativeTo(client);
							setVisible(true);
							setResizable(false);
							setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						}
					};
				}
			}
		);
	}
	public void showPopup(java.awt.event.MouseEvent evt) {
		show(evt.getComponent(), evt.getX(), evt.getY());
	}
}	