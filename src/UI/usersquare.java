package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.*;

import segment.Diary_Segment;

public class usersquare extends JFrame {
	private JLabel juser = new JLabel();
	//ËÑË÷½á¹û
	private ArrayList<Diary_Segment> result_list;
		
	private DefaultListModel<String> result_listmodel = new DefaultListModel<String>();
	private JList<String> jcatalog = new JList<String>(result_listmodel);
	private JScrollPane jscatalog = new JScrollPane(jcatalog);
	diarysquare dairysquare = null;
	
	public usersquare(Socket socket ,ArrayList<Diary_Segment> result) {
		result_list = result;
		for(int i=0;i<result_list.size();i++){
			result_listmodel.addElement(result_list.get(i).title);
		}
		
		setContentPane(
				new JPanel() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 2L;
					ImageIcon icon;
					Image img;
					{ 
						icon=new ImageIcon("icon/seabg.png" );
						img=icon.getImage();
					} 
					public void paintComponent(Graphics g)
					{ 
						super.paintComponent(g);
						g.drawImage(img,0,0,null);
					} 
				}
			);
		
		Font font1=new Font("Î¢ÈíÑÅºÚ",Font.BOLD,36);
		
		//juser
		String user="ÈÈ´øÓã";
		juser.setBounds(100,50,600,50);
		juser.setText(user);
		juser.setFont(font1);
		juser.setForeground(Color.white);
		juser.setAlignmentX(CENTER_ALIGNMENT);
		add(juser);
		
		//catalog
		jscatalog.setBounds(150, 120, 500, 400);
		jscatalog.setBorder(null);
		jscatalog.setOpaque(false);
		jscatalog.getViewport().setOpaque(false);
		add(jscatalog);
		
		
		jcatalog.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int[] indices=jcatalog.getSelectedIndices();
				//indices[0]
					
				if (dairysquare != null && dairysquare.isShowing())
		    		dairysquare.dispose();
		    	dairysquare = new diarysquare(socket,result_list.get(indices[0]));
				 
			}
		});
		setLayout(null);
		setSize(800,600);
 		setResizable(false);
 		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 		setLayout(null);
 		setLocationRelativeTo(null);
 		setVisible(true);
	}
}
