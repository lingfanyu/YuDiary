//筛选对话框
package TextArea;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Select extends JDialog {
	private static Window window;
	private JTextField textField;
	private JLabel labelSelectValue = new JLabel("\u67E5\u627E\u5185\u5BB9\uFF1A");
	private JButton buttonNext = new JButton("\u67E5\u627E\u4E0B\u4E00\u4E2A");
	private JCheckBox checkBoxIgnore;
	private int index = -1;
	private final JButton buttonPrevious = new JButton("\u67E5\u627E\u4E0A\u4E00\u4E2A");
	
	public static void launch(Window window) {
		Select.window = window;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Select select = new Select();
					select.setVisible(true);
					select.setLocationRelativeTo(Select.window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Select() {
		//super(window,"\u67E5\u627E");
		setResizable(false);
		setBounds(100, 100, 450, 128);
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(94, 22, 198, 21);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		labelSelectValue.setBounds(28, 25, 81, 15);
		getContentPane().add(labelSelectValue);
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectNext();
			}
		});
		
		buttonNext.setBounds(315, 54, 109, 23);
		getContentPane().add(buttonNext);	
		checkBoxIgnore = new JCheckBox("\u5FFD\u7565\u5927\u5C0F\u5199");
		checkBoxIgnore.setBounds(17, 54, 103, 23);
		getContentPane().add(checkBoxIgnore);
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPrevious();
			}
		});
		buttonPrevious.setBounds(315, 21, 109, 23);
		getContentPane().add(buttonPrevious);	
	}
	
	private void selectPrevious(){
		String text= null;
		String selectValue = null;
		if(this.checkBoxIgnore.isSelected()){
			text = window.textPane.getText().toLowerCase();
			selectValue = this.textField.getText().toLowerCase();
		}else{
			text = window.textPane.getText();
			selectValue = this.textField.getText();
		}
		index = text.lastIndexOf(selectValue, index-1);
		window.textPane.select(index, index+selectValue.length());
		window.textPane.requestFocus();
		if(index == -1){
			JOptionPane.showMessageDialog(this, "找不到字串: "+this.textField.getText());
			index = 0;
		}
	}
		
	
	private void selectNext(){
		String text= null;
		String selectValue = null;
		if(this.checkBoxIgnore.isSelected()){
			text = window.textPane.getText().toLowerCase();
			selectValue = this.textField.getText().toLowerCase();
		}else{
			text = window.textPane.getText();
			selectValue = this.textField.getText();
		}
		index = text.indexOf(selectValue, index+1);
		window.textPane.select(index, index+selectValue.length());
		window.textPane.requestFocus();
		if(index == -1){
			JOptionPane.showMessageDialog(this, "找不到字串: "+this.textField.getText());
			index = text.length();
		}
	}
}
