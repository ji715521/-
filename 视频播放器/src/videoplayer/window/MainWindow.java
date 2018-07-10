package videoplayer.window;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import videoplayer.main.PlayerMain;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	EmbeddedMediaPlayerComponent playerComponent;
	private JPanel panel;
	private JButton btnPlay;
	private JButton btnStop;
	private JButton btnPause;
	private JPanel btnPanel;
	private JProgressBar progressBar;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenuItem mntmOpenVideo;
	private JMenuItem mntmOpenSubtitle;
	private JMenuItem mntmExit;
	private JMenuItem mntmOpenUrl;
	private JSlider slider;
	private JButton btnRecede;
	private JButton btnSpeed;
	private JPanel panel_1;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Java 视频播放器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmOpenVideo = new JMenuItem("Open Video");
		mntmOpenVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayerMain.openVideo();
			}
		});
		mnFile.add(mntmOpenVideo);
		
		mntmOpenSubtitle = new JMenuItem("Open Subtitle");
		mntmOpenSubtitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayerMain.openSubtitle();
			}
		});
		mnFile.add(mntmOpenSubtitle);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayerMain.exit();
			}
		});
		
		mntmOpenUrl = new JMenuItem("Open URL");
		mntmOpenUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new URLDialog(MainWindow.this, "Please input URL", true);
			}
		});
		mnFile.add(mntmOpenUrl);
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel videoPanel = new JPanel();
		contentPane.add(videoPanel, BorderLayout.CENTER);
		videoPanel.setLayout(new BorderLayout(0, 0));
		// 实例化播放器界面
		playerComponent = new EmbeddedMediaPlayerComponent();
		videoPanel.add(playerComponent);

		panel = new JPanel();
		videoPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0,0));

		btnPanel = new JPanel();
		panel.add(btnPanel);
		btnPanel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		btnPanel.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 5, 0, 0));

		btnStop = new JButton("□");
		panel_1.add(btnStop);
		
		btnRecede = new JButton("|<<");
		panel_1.add(btnRecede);
		
				btnPlay = new JButton("▷");
				panel_1.add(btnPlay);
				
				btnSpeed = new JButton(">>|");
				panel_1.add(btnSpeed);
				
						btnPause = new JButton("▷||");
						panel_1.add(btnPause);
						btnPause.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								PlayerMain.pasue();
							}
						});
				btnSpeed.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						PlayerMain.speed();
					}
				});
				btnPlay.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						PlayerMain.play();
					}
				});
		btnRecede.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayerMain.recede();
			}
		});
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayerMain.stop();
			}
		});
		
		slider = new JSlider();
		slider.setPaintLabels(true);
		slider.setValue(100);
		slider.setMaximum(200);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				PlayerMain.setVol(slider.getValue());
			}
		});
		btnPanel.add(slider, BorderLayout.EAST);

		progressBar = new JProgressBar();
		progressBar.setFont(new Font("宋体", Font.PLAIN, 10));
		progressBar.setStringPainted(true);
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 获取用户所点的百分比
				float p = ((float) e.getX()) / progressBar.getWidth();
				// 将播放进度跳到对应的位置
				PlayerMain.jumpTo(p);
			}
		});
		panel.add(progressBar, BorderLayout.NORTH);

	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

}
