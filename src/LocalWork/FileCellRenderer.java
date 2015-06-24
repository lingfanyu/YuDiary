package LocalWork;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

public class FileCellRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		if (c instanceof JLabel && value instanceof File) {
			JLabel l = (JLabel) c;
			File f = (File) value;
			l.setIcon(FileSystemView.getFileSystemView().getSystemIcon(f));
			l.setText(f.getName());
			l.setToolTipText(f.getAbsolutePath());

			// setIcon(FileSystemView.getFileSystemView().getSystemIcon(f));
			// setText(f.getName());
			// l.setVerticalTextPosition(SwingConstants.BOTTOM );
			// l.setHorizontalTextPosition(SwingConstants.CENTER );
			// setBounds(5, 5, 50, 50);
			// l.setPreferredSize(new Dimension(100,20));

		}

		return c;
	}

	static class Yeah extends JPanel {
		private boolean isSelected;

		public Yeah(boolean isSelected) {
			setPreferredSize(new Dimension(100, 100));
			this.isSelected = isSelected;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// setSize(100, 100); // doesn't change the bounds of the component
			// setBounds(0, 0, 100, 100); // this doesn't do any good either.
			if (isSelected)
				g.setColor(Color.GREEN);
			else
				g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}

}
