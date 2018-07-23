package columndeletecsv;

import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ProgressUpdater implements Runnable {
	JProgressBar bar;
	String value;
	String length;

	public ProgressUpdater(JProgressBar bar, String value, String length) {
		this.bar = bar;
		this.value = value;
		this.length = length;
	}

	public void run() {
		bar.getModel().setMaximum(Integer.parseInt(length));
		bar.getModel().setValue(Integer.parseInt(value));
	}

	private synchronized int getValue() {
		int progress = (Integer.parseInt(value) / Integer.parseInt(length)) * 100;
		return progress;
	}
}