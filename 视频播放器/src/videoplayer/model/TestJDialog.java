package videoplayer.model;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;

public class TestJDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TestJDialog dialog = new TestJDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TestJDialog() {
		setBackground(new Color(0, 0, 51));
		setTitle("打开URL视频");
		setBounds(100, 100, 333, 163);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblurl = new JLabel("请输入URL地址");
			lblurl.setBounds(12, 13, 78, 15);
			contentPanel.add(lblurl);
		}
		
		textField = new JTextField();
		textField.setBounds(12, 38, 295, 21);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JLabel lblhttpHttpsFtp = new JLabel("目前支持http https ftp ");
		lblhttpHttpsFtp.setForeground(Color.MAGENTA);
		lblhttpHttpsFtp.setBounds(12, 67, 138, 15);
		contentPanel.add(lblhttpHttpsFtp);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
