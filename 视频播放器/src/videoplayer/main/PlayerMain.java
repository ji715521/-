package videoplayer.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.media.Media;
import uk.co.caprica.vlcj.player.media.simple.SimpleMedia;
import videoplayer.util.spider.SpiderUtils;
import videoplayer.window.MainWindow;

/*
 * videoplayer2.0
 * 添加:
 * 		打开文件夹
 * 		
 * 		标题显示正在播放
 */
public class PlayerMain {
	/**
	 * 窗体引用
	 */
	protected static MainWindow frame;
	/**
	 * 视频文件格式数组
	 */
	public static final String[] VIDEO_FORMATS = { "avi", "wmv", "mpeg", "mp4", "mov", "mkv", "flv", "f4v", "m4v",
			"rmvb", "rm", "3gp", "dat", "ts", "mts", "vob","m3u8" };
	/**
	 * 打开文件窗口的初始路径
	 */
	private static final String CURRENT_DIR = "E:\\BaiduYunDownload\\";
	/**
	 * 资源路径
	 */
	private static String openVideoFile;

	/**
	 * 播放器参数
	 */
	protected static String[] cans = { "--subsdec-coding=GB18030" };

	public static void main(final String[] args) {
		new NativeDiscovery().discover();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				frame = new MainWindow();
				frame.setVisible(true);
				// frame.getMediaPlayer().playMedia(files[0], cans);//直接播放
				// frame.getMediaPlayer().prepareMedia(openVideoFile, cans);// 控制播放
				// 设置进度条
				new SwingWorker<String, Integer>() {

					// 复写方法
					@Override
					protected String doInBackground() throws Exception {
						while (true) {
							long max = frame.getMediaPlayer().getLength(); // 视频总时长
							long index = frame.getMediaPlayer().getTime(); // 当前播放位置
							float p = (float) index / max; // 比值
							publish((int) (p * 100)); // 拿到百分比

							Thread.sleep(500);
						}
					}

					// 复写方法
					protected void process(java.util.List<Integer> chunks) {
						for (int val : chunks) {
							frame.getProgressBar().setValue(val);
						}
					};

				}.execute();

			}
		});
	}

	/**
	 * 播放
	 */
	public static void play() {
		frame.getMediaPlayer().play();
	}

	/**
	 * 快进
	 */
	public static void speed() {
		frame.getMediaPlayer().skip(10000);
	}

	/**
	 * 后退
	 */
	public static void recede() {
		frame.getMediaPlayer().skip(-10000);
	}

	/**
	 * 暂停
	 */
	public static void pasue() {
		frame.getMediaPlayer().pause();
	}

	/**
	 * 停止
	 */
	public static void stop() {
		frame.getMediaPlayer().stop();
	}

	/**
	 * 跳到
	 * 
	 * @param to
	 */
	public static void jumpTo(float to) {
		frame.getMediaPlayer().setTime((long) (to * frame.getMediaPlayer().getLength()));
	}

	/**
	 * 设置音量
	 * 
	 * @param value
	 */
	public static void setVol(int value) {
		frame.getMediaPlayer().setVolume(value);
	}

	/**
	 * 设置全屏
	 * 
	 * @param b
	 */
	public static void setFullScreen(boolean b) {
		frame.getMediaPlayer().setFullScreen(b);
	}

	/**
	 * 打开视频文件
	 */
	public static void openVideo() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(CURRENT_DIR));
		int v = chooser.showOpenDialog(null);

		if (v == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			setOpenVideoFile(file.getAbsolutePath());
			frame.setTitle(getOpenVideoFile());
			frame.getMediaPlayer().playMedia(getOpenVideoFile());// 直接播放
		}
	}

	/**
	 * 根据用户选择打开视频
	 * 
	 * @param selectedValue
	 */
	public static void openVideo(String selectedValue) {
		setOpenVideoFile(selectedValue);
		frame.setTitle(selectedValue);
		frame.getMediaPlayer().playMedia(selectedValue);// 直接播放
	}

	/**
	 * 打开文件夹,获取list表数据
	 * 
	 * @return
	 */
	public static void openDir() {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(new File(CURRENT_DIR));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("打开文件夹");
		int v = chooser.showOpenDialog(null);

		String[] strings = null;
		if (v == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			File[] listFiles = file.listFiles();

			strings = new String[listFiles.length];

			for (int i = 0; i < listFiles.length; i++) {
				File file2 = listFiles[i];
				String path = file2.getAbsolutePath();
				if (isVideoEnds(path)) {
					strings[i] = new String(path);
				}
			}
		}

		frame.setListData(strings);
	}

	/**
	 * 打开字幕文件
	 */
	public static void openSubtitle() {
		JFileChooser chooser = new JFileChooser();
		int v = chooser.showOpenDialog(null);

		if (v == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			frame.getMediaPlayer().setSubTitleFile(file);// 直接播放
		}
	}

	/**
	 * 打开网络视频
	 * 
	 * @param string
	 */
	public static void openVideoByURL(String string) {
		// Media m = new SimpleMedia(
		// "http://ugcbsy.qq.com/z06877m8vyv.p712.1.mp4?sdtfrom=v1010&guid=22c828793b84760c4b48cd36eaad2c33&vkey=8508900D675E8F1A08F75219CE6ADA3E01AEF0F09BC92DF928202979E18C851355E4B6BDC2EF22DE056A503D0EF36CD45F49BDAED3F468D2FA5002D2AAD9C83B1B2551ADD70BD22538B79491A4EF0B6D817CA7DE0F38830BF45EB41B9628F75C536DEACEC9971D237477E7BCD272524052342F623BCE4CEE");
		Media m = new SimpleMedia(string);
		setOpenVideoFile(string);
		frame.getMediaPlayer().playMedia(m);// 直接播放

	}

	/**
	 * 打开整个网页的所有视频
	 * 
	 * @param string
	 */
	public static void openURL(final String string) {

		new Thread() {
			public void run() {
				try {
					List<String> list = getVideoURLListFromNet(string);

					if (list.size() <= 0) {
						System.out.println("网页中没有找到视频连接");
					} else {

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				Media m = new SimpleMedia(string);
				setOpenVideoFile(string);
				frame.getMediaPlayer().playMedia(m);// 直接播放

			}

			private List<String> getVideoURLListFromNet(String string) throws Exception {

				List<String> list = null;
				String htmlFromNet = SpiderUtils.getHtmlFromNet(string);

				Document document = Jsoup.parse(htmlFromNet);

				Elements aElements = document.select("a");
				if (aElements.size() > 0) {
					list = new ArrayList<String>();

					for (org.jsoup.nodes.Element element : aElements) {
						String urlString = element.attr("href");
						if (isVideoURL(urlString)) {

							System.out.println(urlString);
							list.add(urlString);
						}
					}
				}

				return list;
			};
		}.start();

	}

	/**
	 * 退出程序
	 */
	public static void exit() {
		frame.getMediaPlayer().release();
		System.exit(0);
	}

	/**
	 * 判断字符串是否为视频链接
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isVideoURL(String string) {
		if (!string.matches(
				"^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-Z0-9\\.&amp;%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&amp;%\\$#\\=~_\\-@]*)*$")) {
			// 如果匹配不成功,说明不是网址
			return false;
		}
		return isVideoEnds(string);
	}

	private static boolean isVideoEnds(String string) {

		for (String string2 : VIDEO_FORMATS) {
			if (string.indexOf(string2) != -1) {
				return true;
			}
		}
		return false;
	}

	public static String getOpenVideoFile() {
		return openVideoFile;
	}

	public static void setOpenVideoFile(String openVideoFile) {
		PlayerMain.openVideoFile = openVideoFile;
	}

}