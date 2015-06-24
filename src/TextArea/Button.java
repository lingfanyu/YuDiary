package TextArea;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JButton;

public class Button extends JButton{
	
	int state;
	
	final int HIDE=-1;
	final int NORMAL=0;
	final int SELECTED=1;
	
	public Button(Icon icon,String string) {
		setIcon(icon);
		setToolTipText(string);
		setBorderPainted(false); //无边框
		setContentAreaFilled(false); //透明
		setMargin(new Insets(0,0,0,0)); //无空白
		setPreferredSize(new Dimension(22, 22)); //大小
		
		//setBorder(BorderFactory.createRaisedBevelBorder()); //凸显
	}
	
	public Button(String string) {
	
		setText(string);
		setBorderPainted(false); //无边框
		setContentAreaFilled(false); //透明
		//setMargin(new Insets(0,0,0,0)); //无空白
		//setPreferredSize(new Dimension(22, 22)); //大小
		
		//setBorder(BorderFactory.createRaisedBevelBorder()); //凸显
	}
}
