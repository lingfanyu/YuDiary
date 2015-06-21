package Action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import LocalPanel.JNMainPanel;

class MultiScreenAction extends AbstractAction {
	/** The drag enabled. */
	private boolean dragEnabled = false;
	/** The Constant ALL_SCREENS. */
	static final int ALL_SCREENS = -1;

	/** The screen. */
	int screen;

	/**
	 * Instantiates a new multi screen action.
	 * 
	 * @param swingset
	 *            the swingset
	 * @param screen
	 *            the screen
	 */
	protected MultiScreenAction(JNMainPanel mainPanel, int screen) {
		super("MultiScreenAction");
		this.screen = screen;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	}
}