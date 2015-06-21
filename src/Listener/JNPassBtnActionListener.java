package Listener;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

public class JNPassBtnActionListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		new denglu(get_pass());
		
	}
	
	class denglu extends JFrame{
	    JPasswordField jPasswordField1;
	    JPasswordField jPasswordField2;
	    JPasswordField jPasswordField3;
	    JLabel jLabel1,jLabel2,jLabel3;
	    JPanel jp1,jp2,jp3,jp4;
	    JButton jb1,jb2;
	    public denglu(final String pass){
	        
	        jPasswordField1 = new JPasswordField(13);
	        jPasswordField2 = new JPasswordField(13);
	        jPasswordField3 = new JPasswordField(13);
	        
	        
	        
	        jLabel1 = new JLabel("旧密码");
	        jLabel2 = new JLabel("新密码");
	        jLabel3 = new JLabel(" 重复");
	        
	        jb1 = new JButton("确认");
	        jb1.addMouseListener(new MouseAdapter(){ 
				public void mousePressed(MouseEvent e){ 
					String pwd = new String(jPasswordField1.getPassword());
					if(pwd.equals(pass)){
						String pwd2 = new String(jPasswordField2.getPassword());
						String pwd3 = new String(jPasswordField3.getPassword());
						if(pwd2.equals(pwd3)){
							try {
								write_pass(pwd2);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "密码修改成功o(*￣▽￣*)ブ", "Mes", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
						else 	JOptionPane.showMessageDialog(null, "重复密码不正确╮(╯-╰)╭", "Mes", JOptionPane.ERROR_MESSAGE);
					}
					else 	JOptionPane.showMessageDialog(null, "旧密码不正确╮(╯-╰)╭", "Mes", JOptionPane.ERROR_MESSAGE);

					
				} 
			});
	        
	        
	        jb2 = new JButton("取消");
	        jb1.addMouseListener(new MouseAdapter(){ 
				public void mousePressed(MouseEvent e){ 
					dispose();
					}
			});
	        
	        
	        jp1 = new JPanel();
	        jp2 = new JPanel();
	        jp3 = new JPanel();
	        jp4 = new JPanel();
	        
	        //设置布局
	        this.setLayout(new GridLayout(4,1));
	        
	        jp1.add(jLabel1); 
	        jp1.add(jPasswordField1);
	        
	        jp2.add(jLabel2);
	        jp2.add(jPasswordField2);
	        
	        jp3.add(jLabel3);
	        jp3.add(jPasswordField3);//
	        
	        
	        jp4.add(jb1);
	        jp4.add(jb2); 
	        
	        this.add(jp1);
	        this.add(jp2);
	        this.add(jp3); 
	        this.add(jp4);
	        
	        //设置显示
	        this.setSize(300, 200);
	        //this.pack();
	        
	        this.setVisible(true);
	        this.setTitle("修改密码");
	        this.setLocationRelativeTo(null);
	        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    }
	}
	
	public static int write_pass(String pwd) throws IOException{
		

        File write_path= new File("data");
        File write_dir = new File(write_path,"pass");
        if(write_dir.exists())
        	write_dir.delete(); 
      	write_dir.createNewFile();
     
        FileWriter writer = new FileWriter("data/pass", false);
        writer.write(pwd);
        System.out.println(pwd);
        writer.close();
			
		return 1;
		
	}
	public static String get_pass(){
		
		  String pass = null;
		  String fileName = "data/pass";
	      File file = new File(fileName);
	      BufferedReader reader = null;
	      try {
	          reader = new BufferedReader(new FileReader(file));
	          String tempString = null;
	          int line = 1;
	          while ((tempString = reader.readLine()) != null) {
	              // 显示行号
	              System.out.println("line " + line + ": " + tempString);
	              pass = tempString;
	              line++;
	          }
	          reader.close();
	      } catch (IOException e) {
	          e.printStackTrace();
	      } finally {
	          if (reader != null) {
	              try {
	                  reader.close();
	              } catch (IOException e1) {
	              }
	          }
	      }
	      return pass;
		}
	

}
