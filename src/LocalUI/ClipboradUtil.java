package LocalUI;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import jodd.io.FileUtil;
import jodd.util.SystemUtil;

public class ClipboradUtil {
	public static void main(String[] args) {
		try {
			String path = SystemUtil.getWorkingFolder() + "/bin/data/image/";
			System.out.println(path);
			FileUtil.mkdirs(path);

			Object o = ClipboradUtil.getClipboard();
			if (o instanceof String) {
				System.out.println(o);
			}
			if (o instanceof Image) {
				ImageIO.write((RenderedImage) o, "jpg", new File("d:/d.jpg"));
			}
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object getClipboard() throws UnsupportedFlavorException,
			IOException {
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();// 获取系统剪贴板
		// 获取剪切板中的内容
		Transferable clipT = clip.getContents(null);
		if (clipT != null) {
			// 检查内容是否是文本类型
			if (clipT.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				return (String) clipT.getTransferData(DataFlavor.stringFlavor);
				// 检查内容是否是图片类型
			} else if (clipT.isDataFlavorSupported(DataFlavor.imageFlavor)) {
				return (Image) clipT.getTransferData(DataFlavor.imageFlavor);
			} else if (clipT
					.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				List<File> fileList = (List<File>) clipT
						.getTransferData(DataFlavor.javaFileListFlavor);
				return fileList;
			}
		}
		return null;
	}

	// 往剪切板写文本数据
	public static void setClipboardText(Clipboard clip, String writeMe) {
		Transferable tText = new StringSelection(writeMe);
		clip.setContents(tText, null);
	}

	// 写图像到剪切板
	public static void setClipboardImage2(final Image image) {
		Transferable trans = new Transferable() {

			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}

			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return image;
				throw new UnsupportedFlavorException(flavor);
			}
		};
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(trans, null);
	}
}
