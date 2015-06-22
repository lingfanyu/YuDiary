//替换对话框
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

public class Replace extends JDialog {
	private static Window window;
	private JTextField textFieldSelect;
	private JCheckBox checkBoxIgnore;
	private int index = -1;
	private String selectValue;
	private String oldText;
	private JLabel labelSelectValue = new JLabel("\u67E5\u627E\u5185\u5BB9\uFF1A");
	private JButton buttonNext = new JButton("\u67E5\u627E\u4E0B\u4E00\u4E2A");
	private final JButton buttonPrevious = new JButton("\u67E5\u627E\u4E0A\u4E00\u4E2A");
	private final JLabel labelReplaceAs = new JLabel("\u66FF\u6362\u4E3A\uFF1A");
	private final JTextField textFieldReplaceAs = new JTextField();
	private final JButton buttonReplace = new JButton("\u66FF\u6362\u5F53\u524D\u5B57\u4E32");
	private final JButton buttonReplaceAll = new JButton("\u66FF\u6362\u5168\u90E8");
	
	
	public static void launch(Window window) {
		Replace.window = window;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				    Replace replace = new Replace();
					replace.setVisible(true);
					replace.setLocationRelativeTo(Replace.window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public Replace() {
		//super(window,"\u67E5\u627E");
		setResizable(false);
		setBounds(100, 100, 450, 189);
		getContentPane().setLayout(null);
		
		textFieldSelect = new JTextField();
		textFieldSelect.setBounds(94, 22, 198, 21);
		getContentPane().add(textFieldSelect);
		textFieldSelect.setColumns(10);
		
		labelSelectValue.setBounds(28, 25, 81, 15);
		getContentPane().add(labelSelectValue);
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectNext();
			}
		});
		

		buttonNext.setBounds(317, 54, 107, 23);
		getContentPane().add(buttonNext);
		
		checkBoxIgnore = new JCheckBox("\u5FFD\u7565\u5927\u5C0F\u5199");
		checkBoxIgnore.setBounds(111, 102, 103, 23);
		getContentPane().add(checkBoxIgnore);
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectPrevious();
			}
		});
		buttonPrevious.setBounds(317, 21, 107, 23);
		
		getContentPane().add(buttonPrevious);
		labelReplaceAs.setBounds(28, 56, 81, 15);
		
		getContentPane().add(labelReplaceAs);
		textFieldReplaceAs.setColumns(10);
		textFieldReplaceAs.setBounds(94, 53, 198, 21);
		
		getContentPane().add(textFieldReplaceAs);
		buttonReplace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replace();
			}
		});
		buttonReplace.setBounds(317, 87, 107, 23);
		
		getContentPane().add(buttonReplace);
		buttonReplaceAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replaceAll();			}
		});
		buttonReplaceAll.setBounds(317, 120, 107, 23);
		
		getContentPane().add(buttonReplaceAll);
		

	}
	
	private void selectPrevious(){
		oldText= null;
		selectValue = null;
		if(this.checkBoxIgnore.isSelected()){
			oldText = window.textPane.getText().toLowerCase();
			selectValue = this.textFieldSelect.getText().toLowerCase();
		}else{
			oldText = window.textPane.getText();
			selectValue = this.textFieldSelect.getText();
		}
		index = oldText.lastIndexOf(selectValue, index-1);
		window.textPane.select(index, index+selectValue.length());
		window.textPane.requestFocus();
		if(index == -1){
			JOptionPane.showMessageDialog(this, "前文找不到字串: "+this.textFieldSelect.getText());
			index = 0;
		}
	}
		
	
	private void selectNext(){
		oldText= null;
		selectValue = null;
		if(this.checkBoxIgnore.isSelected()){
			oldText = window.textPane.getText().toLowerCase();
			selectValue = this.textFieldSelect.getText().toLowerCase();
		}else{
			oldText = window.textPane.getText();
			selectValue = this.textFieldSelect.getText();
		}
		index = oldText.indexOf(selectValue, index+1);
		window.textPane.select(index, index+selectValue.length());
		window.textPane.requestFocus();
		if(index == -1){
			JOptionPane.showMessageDialog(this, "后文找不到字串: "+this.textFieldSelect.getText());
			index = oldText.length();
		}
	}
	private void replace(){
		StringBuffer sb = new StringBuffer(oldText);
		sb.replace(index, index+selectValue.length(), this.textFieldReplaceAs.getText());
		window.textPane.setText(sb.toString());
	}
	
	private void replaceAll(){
		index = -1;
		while(true){
			selectNext();
			if(index < this.oldText.length()&&index != -1)
				replace();
			else
				break;
		}		
	}
}
