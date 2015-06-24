package LocalWork;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.TransferHandler;

public class ListTransferHandler extends TransferHandler {

	private JList list;

	public ListTransferHandler(JList list) {
		this.list = list;
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport info) {
		// we only import FileList
		if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport info) {
		if (!info.isDrop()) {
			return false;
		}

		// Check for FileList flavor
		if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			displayDropLocation("List doesn't accept a drop of this type.");
			return false;
		}

		// Get the fileList that is being dropped.
		Transferable t = info.getTransferable();
		List<File> data;
		try {
			data = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);
		} catch (Exception e) {
			return false;
		}
		DefaultListModel model = (DefaultListModel) list.getModel();
		for (Object file : data) {
			model.addElement((File) file);
		}
		return true;
	}

	public void displayDropLocation(String string) {
		System.out.println(string);
	}

}
