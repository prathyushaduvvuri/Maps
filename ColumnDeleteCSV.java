package columndeletecsv;

import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.BorderFactory;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.xml.soap.Text;

/**********************************************************************
 * @author Prathyusha Deleting the Zcause,cause column from multiple comma
 *         separated files.
 ***********************************************************************/
public class ColumnDeleteCSV {
	String sourceDirectory = "";
	String destinationDirectory = "";
	BufferedReader br = null;
	String headerLine = "";
	String line = "";
	ArrayList columnCounterList = new ArrayList();
	StringBuffer buffer = new StringBuffer();
	int lineCounter = 0;
	
	ExecutorService executor = Executors.newFixedThreadPool(1);	
	private DefaultBoundedRangeModel progressBar;
	private JLabel jLabel;
	
	public ColumnDeleteCSV(DefaultBoundedRangeModel model, JLabel jLabel) {		
		this.progressBar = model;
		this.jLabel = jLabel;
	}

	/******************************************
	 * Method: obtainDirectoryListing
	 *
	 * Description:List of files in input folder.
	 ********************************************/
	public void obtainDirectoryListing(String SourceDirectory, String DestinationDirectory, String ErrorColumn)
			throws FileNotFoundException {
		sourceDirectory = SourceDirectory;
		destinationDirectory = DestinationDirectory;
		final File folder = new File(SourceDirectory);
		File[] fileNames = folder.listFiles();
		int files = folder.listFiles().length;
		String columnToDelete = ErrorColumn;
		int i = 0;
		for (File file : fileNames) {
			i++;
			while(i<=2000){    
			    
				  i=i+20;    
				   		
			String message = MessageFormat.format("Status : {0} | Procesing file : {1}", i+" / "+fileNames.length, file.getName());
			executor.execute(new StatusUpdater(message, jLabel));
			this.progressBar.setValue((i/fileNames.length)*100);
			try{Thread.sleep(150);}catch(Exception e){}    
			}   
			
			buffer = new StringBuffer();
			try {
				br = new BufferedReader(new FileReader(file));
				lineCounter = 0;
				while ((line = br.readLine()) != null) {
					StringBuffer bufferToWrite = processFile(line, columnToDelete);
					writeFileToDisk(file, bufferToWrite);
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
	
	/***********************************************************
	 * Method: processFile
	 *
	 * Description:Open file for reading and Delete column in file
	 ************************************************************/
	public StringBuffer processFile(String FileName, String columnName) {
		StringBuffer csvBuffer = buffer;
		if (lineCounter == 0) {
			lineCounter++;
			headerLine = line;
			String[] columns = headerLine.split(",");
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
	private void writeFileToDisk(File file, StringBuffer csvBuffer) throws IOException {
		BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(destinationDirectory + file.getName())));
		bwr.write(buffer.toString());
		bwr.flush();
		bwr.close();
	}

	/***********************************************************
	 * Method: logErrorMessage 
	 * Description: WritesError Message to Log/OutputWindow
	 ***********************************************************/
	public void logErrorMessage(File file) {
		Logger logger = Logger.getLogger("My log");
		FileHandler fh;
		try {
			fh = new FileHandler("C:\\Users\\pduvvur1\\Documents\\CSV\\log\\log.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			logger.info("Sucessfully Copied" + " " + "=>" + " " + file.getName());

		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null, "Files failed");
			Logger.getLogger(WriteLogEntriesToLogFile.class.getName()).log(Level.SEVERE, null, ex);
			logger.info("FileNotFoundException" + ex);
		} catch (SecurityException | IOException ex) {
			ex.printStackTrace();
			logger.info("exception occured at" + ex);

		}
	}

}
