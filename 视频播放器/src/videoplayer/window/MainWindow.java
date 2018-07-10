package videoplayer.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import videoplayer.main.PlayerMain;
import javax.swing.ImageIcon;

public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WINDOW_WIDTH = 550;// 窗口大小
	private static final int WINDOW_HEIGHT = 500;
	private static final int LIST_WIDTH = 250;// 播放列表
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
	private JMenuItem mntmOpenDir;
	private JPanel panel_2;
	private JButton btnFill;
	private JButton btnWrap;
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JList<String> list;
	private ListModel<String> model;

	/**
	 * Create the frame.
	 */
	public MainWindow() {

		setTitle("Java 视频播放器");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
		setLocationRelativeTo(null); // 让窗体居中显示
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				splitPane.setDividerLocation(splitPane.getWidth() - LIST_WIDTH);
				// splitPane.setResizeWeight(0.75);
			}
		});
		addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent e) {
				splitPane.setDividerLocation(splitPane.getWidth() - LIST_WIDTH);
				// splitPane.setResizeWeight(0.75);
			}
		});

		menuBar = new JMenuBar();
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
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

		mntmOpenDir = new JMenuItem("Open Dir");
		mntmOpenDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayerMain.openDir();
				scrollPane.setVisible(true);
				setSize(getWidth() + LIST_WIDTH, getHeight());

			}
		});
		mnFile.add(mntmOpenDir);
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

		panel = new JPanel();
		videoPanel.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

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

		panel_2 = new JPanel();
		btnPanel.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		slider = new JSlider();
		panel_2.add(slider);
		slider.setPaintLabels(true);
		slider.setValue(100);
		slider.setMaximum(200);

		btnFill = new JButton("");
		btnFill.setIcon(
				new ImageIcon(MainWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/maximize.gif")));
		btnFill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayerMain.setFullScreen(true);
			}
		});
		btnFill.setMargin(new Insets(2, 2, 2, 2));
		btnFill.setVisible(true);
		panel_2.add(btnFill);

		btnWrap = new JButton("");
		btnWrap.setIcon(
				new ImageIcon(MainWindow.class.getResource("/javax/swing/plaf/metal/icons/ocean/minimize.gif")));
		btnWrap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				PlayerMain.setFullScreen(false);
			}
		});
		btnWrap.setMargin(new Insets(2, 2, 2, 2));
		btnWrap.setVisible(false);
		panel_2.add(btnWrap);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				PlayerMain.setVol(slider.getValue());
			}
		});

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

		// 创建一个分割布局
		splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);// 设置可以伸缩
		splitPane.setDividerLocation(splitPane.getWidth() - LIST_WIDTH);
		// splitPane.setResizeWeight(0.75);
		// 实例化播放器界面
		playerComponent = new EmbeddedMediaPlayerComponent();
		playerComponent.getMediaPlayer().setFullScreenStrategy(new DefaultAdaptiveRuntimeFullScreenStrategy(this) {
			@Override
			protected void beforeEnterFullScreen() {
				menuBar.setVisible(false);
				progressBar.setVisible(false);
				panel_1.setVisible(false);
				slider.setVisible(false);
				btnFill.setVisible(false);
				btnWrap.setVisible(true);
				scrollPane.setVisible(false);
			}

			@Override
			protected void afterExitFullScreen() {
				menuBar.setVisible(true);
				progressBar.setVisible(true);
				panel_1.setVisible(true);
				slider.setVisible(true);
				btnFill.setVisible(true);
				btnWrap.setVisible(false);
				// scrollPane.setVisible(true);
			}
		});
		splitPane.setLeftComponent(playerComponent);

		scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setVisible(false);

		model = new DefaultListModel<String>();
		list = new JList<String>();
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				String selectedValue = (String) list.getSelectedValue();
				PlayerMain.openVideo(selectedValue);
				MainWindow.this.setTitle(selectedValue);
				System.out.println(selectedValue);
			}
		});
		list.setModel(model);
		scrollPane.setViewportView(list);
		// splitPane.setRightComponent(null);
		splitPane.setRightComponent(scrollPane);

		videoPanel.add(splitPane, BorderLayout.CENTER);

	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return playerComponent.getMediaPlayer();
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	@SuppressWarnings({})
	public void setListData(String[] strings) {
		if (model.getSize() > 0) {
			((DefaultListModel<String>) model).clear();
		}
		for (String string : strings) {
			((DefaultListModel<String>) model).addElement(string);
		}
		list.setModel(model);
	}
}
