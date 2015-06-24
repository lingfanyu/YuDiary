package GroundUI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import Segment.client_Segment;

import java.io.*;
import java.net.Socket;

public class LoginFrame extends JFrame {
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
	
	public LoginFrame(final Socket socket) {
		this.setLocationRelativeTo(null);
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
		
	//	setUndecorated(true);
	//	this.setUndecorated(true);
	//	com.sun.awt.AWTUtilities.setWindowOpaque(this, false);
		
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
					login(socket);
				}
			}
		);
		
		jok.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					login(socket);
				}
			}
		);
		
		setTitle("Login");
		setSize(370,285);
		//setLocationRelativeTo(client);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = this.getSize();
		int x = (screenSize.width - size.width) / 2;
		int y = (screenSize.height - size.height) / 2;
		this.setLocation( x, y );
		
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
	private void login(Socket socket) {
		String uname = username.getText();
		String pwd = new String(password.getPassword());
		if (!is_valid(uname)) {
			JOptionPane.showMessageDialog(this, "wrong input!");
			return;
		} 
		if (uname != null && pwd != null && uname.length()>0 && pwd.length()>0 && usernameTyped && passwordTyped) {
			try {
				
				client_Segment s= new client_Segment();
				s.user.name = uname;
				s.user.password = pwd;
				s.head = 1;
				ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
		        send.writeObject(s);
		        send.flush();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
