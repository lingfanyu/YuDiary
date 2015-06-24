package Dict;


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;

public class LoginFrame extends JFrame implements OnlineDictDataConsts {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField username = new JTextField("请输入账号");
	private JPasswordField password = new JPasswordField("请输入密码");
	private JButton jok = new JButton("登   录");
	private JLabel pic = new JLabel("hello");
	private boolean usernameTyped = false;
	private boolean passwordTyped = false;
	
	public LoginFrame(final DictionaryClient client) {
		setContentPane(
			new JPanel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				ImageIcon icon;
				Image img;
				{ 
					icon=new ImageIcon("bg/bg4.jpg" );
					img=icon.getImage();
				} 
				public void paintComponent(Graphics g)
				{ 
					super.paintComponent(g);
					g.drawImage(img,0,0,null);
				} 
			}
		);
		
		password.setEchoChar('\0');
		
		setResizable(false);
		
		jok.setBounds(120,200,175,30);

		Font sansbold14 = new Font("微软雅黑",Font.PLAIN,15);
		jok.setFont(sansbold14);
		jok.setBorderPainted(false);
		
		username.setBounds(120, 111, 180, 24);
		password.setBounds(120, 147, 180, 24);
		
		username.setForeground(Color.gray); 
		username.addMouseListener(new MouseAdapter(){ 
			public void mousePressed(MouseEvent e){
				if	(!usernameTyped){ 
					username.setForeground(Color.black); 
					username.setText(null);
					usernameTyped = true;
				} 
			} 
		});
		username.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (!usernameTyped) {
						username.setForeground(Color.black); 
						username.setText(null);
						usernameTyped = true;
					}
				}
			}
		);
		

		password.setForeground(Color.gray); 
		password.addMouseListener(new MouseAdapter(){ 
			public void mousePressed(MouseEvent e){ 
				if (!passwordTyped) {
					password.setEchoChar('*');
					password.setForeground(Color.black); 
					password.setText(null);
					passwordTyped = true;
				}
			} 
		});
		
		password.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (!passwordTyped) {
						password.setEchoChar('*');
						password.setForeground(Color.black); 
						password.setText(null);
						passwordTyped = true;
					}
				}
			}
		);
		
		ImageIcon picicon = new ImageIcon("pic/pic2.jpg");
		pic.setBounds(25, 101, 80, 80);
		pic.setBackground(Color.blue);
		pic.setIcon(picicon);
		
		
		setLayout(null);
		add(jok);
		add(username);
		add(password);
		add(pic);
		
		password.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					login(client);
				}
			}
		);
		
		jok.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					login(client);
				}
			}
		);
		
		setTitle("Login");
		setSize(370,285);
		setLocationRelativeTo(client);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	private boolean is_valid(String word) {
		String s = word;
		for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (!(Character.isLetter(ch)) && ch != '_')
				return false;
		} 
		return true;
	}
	private void login(DictionaryClient client) {
		String uname = username.getText();
		String pwd = new String(password.getPassword());
		if (!is_valid(uname)) {
			JOptionPane.showMessageDialog(this, "wrong input!");
			return;
		} 
		if (uname != null && pwd != null && uname.length()>0 && pwd.length()>0 && usernameTyped && passwordTyped) {
			synchronized (client.toServer) {
				try {
					client.toServer.writeInt(LOGIN);
					client.toServer.writeUTF(uname);
					client.toServer.writeUTF(pwd);
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			dispose();
		}
	}
}