package videoplayer.window;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import videoplayer.main.PlayerMain;

public class URLDialog extends JDialog {
	private static final int WIDTH = 360;
	private static final int HEIGHT = 180;
	@SuppressWarnings("unused")
	private MainWindow mainWindow;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;

	public URLDialog(final MainWindow mainWindow, String title, boolean modal) {
		super(mainWindow, title, modal);
		this.mainWindow=mainWindow;
		getContentPane().setLayout(new GridLayout(4, 1, 0, 0));

		JLabel lblInputUrl = new JLabel("input URL");
		getContentPane().add(lblInputUrl);

		textField = new JTextField();
		getContentPane().add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("http://");
		label.setForeground(Color.MAGENTA);
		getContentPane().add(label);

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setBorder(new EmptyBorder(1, 20, 1, 20));
		panel.setLayout(new GridLayout(0, 2, 20, 10));

		JButton btnCance = new JButton("cancel");
		btnCance.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
				URLDialog.this.dispose();// ;
			}
		});
		panel.add(btnCance);

		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String string = textField.getText().trim();
				if (string == null || "".equals(string)) {
					JOptionPane.showMessageDialog(null, "链接地址不能为空");
				} else if (string.matches(
						"^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&amp;%\\$#\\=~_\\-@]*)*$")) {
					//如果匹配成功,说明是网址
					//判断是网页还是视频文件
					if (PlayerMain.isVideoURL(string)) {
						PlayerMain.openVideoByURL(string);
					} else {
						PlayerMain.openURL(string);
						mainWindow.setTitle(string);
					}
					URLDialog.this.dispose();// ;
				} else {
					JOptionPane.showMessageDialog(null, "链接地址格式不正确");
				}

			}
		});
		panel.add(btnOk);

		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setResizable(false);// 不可调整大小
		setLocation(230, 280);
	}

}
