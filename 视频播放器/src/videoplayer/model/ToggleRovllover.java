package videoplayer.model;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ToggleRovllover {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		JFrame f = new JFrame("ToggleRollover");
		Container contentPane = f.getContentPane();
		JToggleButton b = new JToggleButton();
		b.setRolloverEnabled(true);
		b.setIcon(new ImageIcon(".\\icons\\rollover1.jpg"));
		b.setRolloverSelectedIcon(new ImageIcon(".\\icons\\rollover2.jpg"));
		b.setSelectedIcon(new ImageIcon(".\\icons\\rollover3.jpg"));
		contentPane.add(b);
		f.pack();
		f.show();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}