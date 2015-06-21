package Database;

import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import LocalPanel.JNMainPanel;

public class JPassDialog extends JFrame{
	static JDialog localJDialog ;
	static JPasswordField passwd = new JPasswordField(10);
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
	public static int test_pass(){
		int time = 1;
		String pass = get_pass();
		String input = "";
		JPasswordField pw = new JPasswordField();
		input = JPassDialog.showInputDialog("请输入日记密码o(*￣▽￣*)ブ");
		
	//	while(!input.equals(pass)&& time < 3){
		  //  input = JPassDialog.showInputDialog("╮(╯-╰)╭ 密码错误"+time+"次"+"\n请重新输入:");					
		//	time ++;
		//}
		if(input.equals(pass))
			{
			localJDialog.dispose();
			return 1;
			}
		else JOptionPane.showMessageDialog(null,"╮(╯-╰)╭密码错误", "System Info", JOptionPane.ERROR_MESSAGE);

		return 0;
		
	}
	
	public static  String showInputDialog(String info) {
		JPanel jp = new JPanel(new GridLayout(2,1));
		jp.add(new JLabel(info+"\n"));
		jp.add(passwd);
		JOptionPane localJOptionPane = new JOptionPane(jp,JOptionPane.PLAIN_MESSAGE);
		
		//localJOptionPane.add(passwd);
		passwd.setEchoChar('*');
		localJDialog = localJOptionPane.createDialog(localJOptionPane,"Welcome");
		localJDialog.setVisible(true);
		String localObject = String.valueOf(passwd.getPassword());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		return localObject;
}

}