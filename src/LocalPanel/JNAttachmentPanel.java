package LocalPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import LocalWork.FileCellRenderer;
import LocalWork.ListTransferHandler;
import YuNote.Attachment;
import YuNote.AttachmentDao;
import YuNote.CategoryDao;
import YuNote.Note;

import jodd.util.SystemUtil;



public class JNAttachmentPanel {
	private JPanel attachmentPanel;
	private JList list;
	private Note note;

	private int selectObj = -1;//记录当前选中的附件位置

	@SuppressWarnings("unchecked")
	public JNAttachmentPanel(Note note) {
		attachmentPanel = new JPanel();
		this.note = note;
		attachmentPanel.setLayout(new BorderLayout());

		final DefaultListModel model = new DefaultListModel();

		AttachmentDao attDao = new AttachmentDao();
		List<Attachment> attList = attDao.get(note.getUuid());
		if (attList.size() > 0) {
			String categoryPath = new CategoryDao().getCategoryPath(note.getCategoryId());
			String dataPath = SystemUtil.getWorkingFolder() + JNConstans.DATA_FILE_DIR + categoryPath;
			String attachmentPath = dataPath + note.getUuid() + "/";
			for (Attachment attachment : attList) {
				String attFilePath = attachmentPath + attachment.getAttachmentName();
				File file = new File(attFilePath);
				model.addElement(file);
			}
		}

		list = new JList(model) {
			public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
				int row;
				if (orientation == SwingConstants.VERTICAL && direction < 0 && (row = getFirstVisibleIndex()) != -1) {
					Rectangle r = getCellBounds(row, row);
					if ((r.y == visibleRect.y) && (row != 0)) {
						Point loc = r.getLocation();
						loc.y--;
						int prevIndex = locationToIndex(loc);
						Rectangle prevR = getCellBounds(prevIndex, prevIndex);

						if (prevR == null || prevR.y >= r.y) {
							return 0;
						}
						return prevR.height;
					}
				}
				return super.getScrollableUnitIncrement(visibleRect, orientation, direction);
			}
		};

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		String longValue = "00000000000000000   ";
		if (longValue != null) {
			list.setPrototypeCellValue(longValue); // 格式化LIST的宽度
		}
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		list.setCellRenderer(new FileCellRenderer());
		list.setTransferHandler(new ListTransferHandler(list));
		list.setDragEnabled(true);
		list.setDropMode(javax.swing.DropMode.INSERT);

		// jlist右键
		final JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem delItem = new JMenuItem("删除附件");
		delItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.remove(selectObj);
			}
		});
		popupMenu.add(delItem);
		list.add(popupMenu);

		// jlist双击打开文件
		list.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					File file = (File) list.getSelectedValue();
					System.out.println(file.getName());
					try {
						Desktop desktop = null;
						if (Desktop.isDesktopSupported()) {
							desktop = Desktop.getDesktop();
						}
						desktop.open(file);
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
				// e.getButton() 返回值有 1，2，3。1代表鼠标左键，3代表鼠标右键
				// jList.getSelected() 返回的是选中的JList中的项数。
				// if语句的意思也就是，在JList 中点击了右键而且JList选中了某项，显示右键菜单
				if (e.getButton() == 3 && list.getSelectedIndex() >= 0) {
					popupMenu.show(list, e.getX(), e.getY());
					selectObj=list.getSelectedIndex();
				}

			}

		});

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 200));
		listScroller.setAlignmentX(Component.LEFT_ALIGNMENT);

		JButton closeBtn = new JButton("X");
		closeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				attachmentPanel.setVisible(false);
			}
		});
		attachmentPanel.add(closeBtn, BorderLayout.EAST);
		attachmentPanel.add(listScroller, BorderLayout.CENTER);
		attachmentPanel.setVisible(false);
	}

	public JPanel getAttachmentPanel() {
		return attachmentPanel;
	}

	public JPanel getAttachmentStatusPanel() {
		JPanel statusPanel = new JPanel();
		JButton closeBtn = new JButton("X");
		closeBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				attachmentPanel.setVisible(false);
			}
		});
		return statusPanel;
	}

	public JList getList() {
		return list;
	}

}
