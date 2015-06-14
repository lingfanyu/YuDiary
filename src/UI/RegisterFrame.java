package UI;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import segment.client_Segment;

import java.io.*;
import java.net.Socket;

public class RegisterFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField username = new JTextField("«Î ‰»Î’À∫≈");
	private JPasswordField password = new JPasswordField("«Î ‰»Î√‹¬Î");
	private JPasswordField confirm = new JPasswordField("«Î»∑»œ√‹¬Î");
	private JButton jok = new JButton("◊¢  ≤· ");
	private JLabel north = new JLabel("");
	private JLabel pic = new JLabel("hello");
	private JLabel tx = new JLabel("Õ∑œÒ—°‘Ò");
	String icon = "1";
	private JComboBox<String> comboBox = new JComboBox<String>();
	
	private boolean nameTyped = false;
	private boolean pwdTyped = false;
	private boolean cfnTyped = false;
	
	
//	public RegisterFrame(DictionaryClient client) {
	public RegisterFrame(Socket socket) {
		ImageIcon img = new ImageIcon("bg/bg_sky.gif"); 
		
		Color dblue = new Color(0,159,217);
		jok.setBounds(247,230,80,30);
		jok.setBackground(dblue);
		jok.setForeground(Color.white);
		Font font0 = new Font("Œ¢»Ì—≈∫⁄",Font.PLAIN,15);
		jok.setFont(font0);
		jok.setBorderPainted(false);
		
		north.setBounds(0, 0, 356, 130);
		north.setIcon(img);
		username.setBounds(39, 151, 170, 24);
		password.setBounds(39, 205, 170, 24);
		confirm.setBounds(39, 232, 170, 24);
		
		password.setEchoChar('\0');
		confirm.setEchoChar('\0');
		
		username.setForeground(Color.gray); 
		username.addMouseListener(new MouseAdapter(){ 
			public void mousePressed(MouseEvent e){ 
				if(!nameTyped){
					username.setForeground(Color.black); 
					username.setText(null);
					nameTyped = true;
				} 
			} 
		});
		
		username.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (!nameTyped) {
						username.setForeground(Color.black); 
						username.setText(null);
						nameTyped = true;
					}
				}
			}
		);
		
		password.setForeground(Color.gray); 
		password.addMouseListener(new MouseAdapter(){ 
			public void mousePressed(MouseEvent e){ 
				if (!pwdTyped) {
					password.setEchoChar('*');
					password.setForeground(Color.black); 
					password.setText(null);
					pwdTyped = true;
				}
			} 
		});
		
		password.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (!pwdTyped) {
						password.setEchoChar('*');
						password.setForeground(Color.black); 
						password.setText(null);
						pwdTyped = true;
					}
				}
			}
		);
		
		confirm.setForeground(Color.gray); 
		confirm.addMouseListener(new MouseAdapter(){ 
			public void mousePressed(MouseEvent e){ 
				if (!cfnTyped) {
					confirm.setEchoChar('*');
					confirm.setForeground(Color.black); 
					confirm.setText(null);
					cfnTyped = true;
				}
			} 
		});
		
		confirm.addKeyListener(
			new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (!cfnTyped) {
						confirm.setEchoChar('*');
						confirm.setForeground(Color.black); 
						confirm.setText(null);
						cfnTyped = true;
					}
				}
			}
		);
		
		tx.setBounds(39, 177, 70, 24);
		tx.setFont(font0);
		comboBox.setAutoscrolls(true);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3"}));
		comboBox.setBounds(122, 177, 87, 24);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				icon = (String)comboBox.getSelectedItem();
				Icon logo = new ImageIcon("icon/pic"+ icon +".jpg");
				pic.setIcon(logo);
			}
		});
		
		ImageIcon picicon = new ImageIcon("icon/pic1.jpg");
		pic.setBounds(247, 142, 80, 80);
		pic.setBackground(Color.blue);
		pic.setIcon(picicon);
		
		setLayout(null);
		add(jok);
		add(north);
		add(username);
		add(password);
		add(confirm);
		add(pic);
		add(comboBox);
		add(tx);
		
		setTitle("Register");
		setSize(356,320); //353*335
	//	setLocationRelativeTo(client);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		jok.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					register(socket);
				}
			}
		);

		confirm.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					register(socket);
				}
			}
		);	
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
	public void register(Socket socket) {
		String name = username.getText(); 
		if (!is_valid(name)) {
			JOptionPane.showMessageDialog(this, "wrong input!");
			return;
		} 
		String pwd = new String(password.getPassword());
		String cfn = new String(confirm.getPassword());
		int portrait = comboBox.getSelectedIndex() + 1;
		if (name != null && pwd != null && cfn != null &&  name.length()>0 && pwd.length()>0 && cfn.length()>0 && nameTyped && pwdTyped && cfnTyped) {						
			if (!pwd.equals(cfn)) {
				JOptionPane.showMessageDialog(null, "√‹¬Î≤ª“ª÷¬£°");
				return;
			}
			else {
				try {
					
					client_Segment s= new client_Segment();
					s.user.name = name;
					s.user.password = pwd;
					s.user.icon = icon.charAt(0)-'0';
					s.head = 2;
					ObjectOutputStream send = new ObjectOutputStream(socket.getOutputStream());
			        send.writeObject(s);
			        send.flush();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			dispose();
		}
	}
	
} 
