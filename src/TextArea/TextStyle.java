package TextArea;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import javax.swing.text.StyledDocument;

public class TextStyle implements Serializable {

	StyledDocument document;
	Font font;
	Color fore;
	Color back;

	public void set(Text text) {
		document=text.getStyledDocument();
		font=text.getFont();
		fore=text.getForeground();
		back=text.getBackground();
	}

	public void get(Text text) {
		text.setStyledDocument(document);
		text.setFont(font);
		text.setForeground(fore);
		text.setBackground(back);
	}
}
