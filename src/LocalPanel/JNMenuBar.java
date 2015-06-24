package LocalPanel;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import Action.ExitAction;
import LocalUI.GUIUtil;
public class JNMenuBar {
	// Menus
	private JMenuBar menuBar = null;
	private JMenu lafMenu = null;
	/** The laf menu group. */
	private ButtonGroup lafMenuGroup = new ButtonGroup();

	public JNMenuBar(JNMainPanel mainPanel) {
		JMenuItem mi = null;
		// ***** create the menubar ****
		menuBar = new JMenuBar();
		menuBar.getAccessibleContext().setAccessibleName(GUIUtil.getResourceString("key"));

		// ***** create File menu
		JMenu fileMenu = (JMenu) menuBar.add(new JMenu(GUIUtil.getResourceString("key")));
		fileMenu.setMnemonic(getMnemonic("FileMenu.file_mnemonic"));
		fileMenu.getAccessibleContext().setAccessibleDescription(GUIUtil.getResourceString("key"));

		createMenuItem(fileMenu, "FileMenu.about_label", "FileMenu.about_mnemonic",
				"FileMenu.about_accessible_description", null);

		fileMenu.addSeparator();

		createMenuItem(fileMenu, "FileMenu.open_label", "FileMenu.open_mnemonic",
				"FileMenu.open_accessible_description", null);

		createMenuItem(fileMenu, "FileMenu.save_label", "FileMenu.save_mnemonic",
				"FileMenu.save_accessible_description", null);

		createMenuItem(fileMenu, "FileMenu.save_as_label", "FileMenu.save_as_mnemonic",
				"FileMenu.save_as_accessible_description", null);

		fileMenu.addSeparator();

		createMenuItem(fileMenu, "FileMenu.exit_label", "FileMenu.exit_mnemonic",
				"FileMenu.exit_accessible_description", new ExitAction(mainPanel));

	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * 创建菜单ITEM
	 * 
	 * @param menu
	 * @param label
	 * @param mnemonic
	 * @param accessibleDescription
	 * @param action
	 * @return
	 */
	public JMenuItem createMenuItem(JMenu menu, String label, String mnemonic, String accessibleDescription,
			Action action) {
		JMenuItem mi = (JMenuItem) menu.add(new JMenuItem(GUIUtil.getResourceString("key")));
		// mi.setBorder(BorderFactory.createEmptyBorder());
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(GUIUtil.getResourceString("key"));
		mi.addActionListener(action);
		if (action == null) {
			mi.setEnabled(false);
		}
		return mi;
	}

	public JMenuItem createLafMenuItem(JMenu menu, String label, String mnemonic, String accessibleDescription,
			String laf) {
		JMenuItem mi = (JRadioButtonMenuItem) menu.add(new JRadioButtonMenuItem(GUIUtil.getResourceString("key")));
		lafMenuGroup.add(mi);
		mi.setMnemonic(getMnemonic(mnemonic));
		mi.getAccessibleContext().setAccessibleDescription(GUIUtil.getResourceString("key"));
		// mi.addActionListener(new ChangeLookAndFeelAction(this, laf));

		// mi.setEnabled(isAvailableLookAndFeel(laf));

		return mi;
	}

	public char getMnemonic(String key) {
		return (GUIUtil.getResourceString("key")).charAt(0);
	}
}
