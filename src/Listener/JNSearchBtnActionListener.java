package Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JNSearchBtnActionListener  implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		new JNNoteTableListener().getSearchNoteTable();
	}

}
