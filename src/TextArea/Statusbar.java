package TextArea;

import javax.swing.JLabel;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;

public class Statusbar extends JToolBar{
	
	Window window;
	Text textPane;
	
	final JLabel labelPos = new JLabel();
	final JLabel labelChar = new JLabel();
	
	Statusbar(Window window) {
		this.window=window;
		textPane=window.textPane;
		
		add(labelPos);
		add(labelChar);
	}
	
	void setLabelPos(int pos) {
		try {
			int row=1;
			int column=pos;
			String string=textPane.getText(0,pos);
			if(string.indexOf("\n")!=-1)
			{
				String[] strings=string.split("\n");
				row=strings.length;
				column=strings[strings.length-1].length();
			}
			labelPos.setText("行：" + row + "   列：" + column);
		} catch(BadLocationException e) {
			e.printStackTrace();
		}		
	}
	
	void setLabelChar() {
		labelChar.setText("   字数：" + textPane.getText().replaceAll("[\t\n]", "").length());
	}
	
}
