package columndeletecsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/***********************************************************************************
 * @author Prathyusha 
 * Description:Deleting the Zcause,cause column from multiple comma separated files.
 ***********************************************************************************/
public class ColumnDeleteCSV {	
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	public static final String LOG_FILE_NAME = "C:\\Users\\pduvvur1\\Documents\\CSV\\log\\log.log";
	private JProgressBar progressBar;
	private JLabel jLabel;

	/**************************
	 * Method: ColumnDeleteCSV
	 **************************/
	public ColumnDeleteCSV(JProgressBar model, JLabel jLabel) {
		this.progressBar = model;
		this.jLabel = jLabel;
	}

	/********************************************
	 * Method: obtainDirectoryListing
	 * Description:List of files in input folder.
	 ********************************************/
	public synchronized void  obtainDirectoryListing(String sourceDirectory, String destinationDirectory, String columnToDelete)
			throws FileNotFoundException {	
		final File folder = new File(sourceDirectory);
		File[] fileNames = folder.listFiles();
		int i = 0;
		String line;
        BufferedReader br;
		for (File file : fileNames) {
			if (file.getName().endsWith(".CSV") || file.getName().endsWith(".csv")) {
				++i;
				int lineCounter;
				executor.execute(new StatusUpdater( String.valueOf(i), String.valueOf(fileNames.length), file.getName(), jLabel));											
				executor.execute(new ProgressUpdater(progressBar, String.valueOf(i), String.valueOf(fileNames.length)));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				StringBuffer buffer = new StringBuffer();
				try {
					br = new BufferedReader(new FileReader(file));
					lineCounter = 0;
					while ((line = br.readLine()) != null) {
						StringBuffer bufferToWrite = processFile(line, columnToDelete, lineCounter, buffer);
						writeFileToDisk(file.getName(), bufferToWrite, destinationDirectory, buffer);
					}
				} catch (FileNotFoundException ex) {
					Logger.getLogger(ColumnDeleteCSV.class.getName()).log(Level.SEVERE, null, ex);
					ex.printStackTrace();
				} catch (IOException ex) {
					Logger.getLogger(ColumnDeleteCSV.class.getName()).log(Level.SEVERE, null, ex);
					ex.printStackTrace();
				}
				logErrorMessage(file);
			}
		}
	}

	/***********************************************************
	 * Method: processFile
	 *
	 * Description:Open file for reading and Delete column in file
	 ************************************************************/
	public StringBuffer processFile(String line, String columnName, int lineCounter, StringBuffer buffer) {
		ArrayList columnCounterList = new ArrayList();
        StringBuffer csvBuffer = buffer;
		if (lineCounter == 0) {
			lineCounter++;
			
			String[] columns = line.split(",");
			for (int i = 0; i < columns.length; i++) {
				if (columns[i].equalsIgnoreCase(columnName)) {
					columnCounterList.add(i);
				} else {
					buffer.append(columns[i]).append(",");
				}
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("\n");
		} else {
			String[] columns = line.split(",");
			for (int i = 0; i < columns.length; i++) {
				if (columnCounterList.contains(i)) {
				} else {
					buffer.append(columns[i]).append(",");
				}
			}
			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append("\n");
		}
		return csvBuffer;
	}

	/**************************************************
	 * Method: writeFileToDisk
	 *
	 * Description:Successfully Writes file to disk
	 ****************************************************/
	private void writeFileToDisk(String fileName, StringBuffer csvBuffer, String destinationDirectory, StringBuffer buffer) throws IOException {		
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(destinationDirectory + fileName)));
		bwr.write(buffer.toString());
		bwr.flush();
		bwr.close();
	}

	/***********************************************************
	 * Method: logErrorMessage Description: WritesError Message to Log/OutputWindow
	 ***********************************************************/
	public void logErrorMessage(File file) {
		Logger logger = Logger.getLogger("My log");
		FileHandler fh;
		try {
			fh = new FileHandler(LOG_FILE_NAME);
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.info("Sucessfully Copied" + " " + "=>" + " " + file.getName());

		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Files failed");
			// Logger.getLogger(WriteLogEntriesToLogFile.class.getName()).log(Level.SEVERE,
			// null, ex);
			logger.info("FileNotFoundException" + ex);
		} catch (SecurityException | IOException ex) {
			ex.printStackTrace();
			logger.info("exception occured at" + ex);

		}
	}

}
