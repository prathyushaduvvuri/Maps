package columndeletecsv;

import java.text.MessageFormat;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class StatusUpdater implements Runnable {
	String value;
	String length;
	JLabel jLabel;
	String filename;
	
	public StatusUpdater( String value, String length, String fileName, JLabel jLabel2) {		
		this.value = value;
	    this.length = length;
		this.jLabel = jLabel2;	
		this.filename = fileName;
	}

	@Override
	public void run() {
		jLabel.setText(this.getMessage());		
	}
	
	 private synchronized String getMessage() {
		 return MessageFormat.format("Status : {0} | Processing file : {1}", value + " / " + length,
				 filename);		
	 } 
}
