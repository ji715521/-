package videoplayer.main;

import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_list_t;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.media.Media;
import uk.co.caprica.vlcj.player.media.simple.SimpleMedia;
import videoplayer.window.MainWindow;

public class PlayerMain {
	protected static MainWindow frame;
	public static final String[] VIDEO_FORMATS = { "avi", "wmv", "mpeg", "mp4", "mov", "mkv", "flv", "f4v", "m4v",
			"rmvb", "rm", "3gp", "dat", "ts", "mts", "vob" };
	// 资源路径
	private static String[] files = { "D:\\迅雷下载\\《七个颠覆你思想的演讲》[中英字幕]视频下载\\第3集 耳聋：一个新兴的治疗策略.mp4",
			"E:\\BaiduYunDownload\\韩顺平\\韩顺平.循序渐进学.java.从入门到精通.第00讲-开山篇.avi" };

	// 播放器参数
	protected static String[] cans = { "--subsdec-coding=GB18030" };

	public static void main(final String[] args) {
		new NativeDiscovery().discover();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				frame = new MainWindow();
				frame.setVisible(true);
				// frame.getMediaPlayer().playMedia(files[0], cans);//直接播放
				frame.getMediaPlayer().prepareMedia(files[0], cans);// 控制播放
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
	 * 打开视频文件
	 */
	public static void openVideo() {
		JFileChooser chooser = new JFileChooser();
		int v = chooser.showOpenDialog(null);

		if (v == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			frame.getMediaPlayer().playMedia(file.getAbsolutePath());// 直接播放
		}
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
	 * 打开整个网页的所有视频
	 * 
	 * @param string
	 */
	public static void openURL(String string) {
		Media m = new SimpleMedia(
				"http://ugcbsy.qq.com/z06877m8vyv.p712.1.mp4?sdtfrom=v1010&guid=22c828793b84760c4b48cd36eaad2c33&vkey=8508900D675E8F1A08F75219CE6ADA3E01AEF0F09BC92DF928202979E18C851355E4B6BDC2EF22DE056A503D0EF36CD45F49BDAED3F468D2FA5002D2AAD9C83B1B2551ADD70BD22538B79491A4EF0B6D817CA7DE0F38830BF45EB41B9628F75C536DEACEC9971D237477E7BCD272524052342F623BCE4CEE");
		frame.getMediaPlayer().playMedia(m);// 直接播放

	}

	/**
	 * 打开网络视频
	 * 
	 * @param string
	 */
	public static void openVideoByURL(String string) {
		new Thread() {
			public void run() {
				List<String> list = getVideoURLListFromNet(string);

				Media m = new SimpleMedia(string);
				frame.getMediaPlayer().playMedia(m);// 直接播放

			}

			private List<String> getVideoURLListFromNet(String string) {

				return null;
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

		for (String string2 : VIDEO_FORMATS) {
			if (string.endsWith(string2)) {
				return true;
			}
		}
		return false;
	}
}