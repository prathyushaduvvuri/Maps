package columndeletecsv;

import java.io.File;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import columndeletecsv.ColumnDeleteCSV;

public class CSVColumnDeleter {
	String sourceDirectory = "";
	String destinationDirectory = "";
	BufferedReader br = null;
	String headerLine = ""; 
	String line = "";   		            
	ArrayList columnCounterList = new ArrayList(); 
	StringBuffer buffer = new StringBuffer();  
	int lineCounter = 0; 
	boolean allFilesWrittenSuccesfullyToDisk = false;
	int outPutDirectoryLength = 0;

	public boolean SourceDirectoryFile(File[] fileNames) {
		if(fileNames.length != 0){ 
			return true;
		}
		return false;
	}

	public boolean SourceDirectoryFileData(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));     
		if (br.readLine() != null) {
			return true;
		}
		return false;
	}

	public boolean ColumnDeleteCSV(StringBuffer buffer, String columnToDelete) {
		String line = "";
		line = buffer.toString();
		String [] columns = line.split(","); 
		for (int i=0;i<columns.length;i++) {
			if (columns[i].contains(columnToDelete)) { 
				return false;
			}
		}
		return true;
	}
	
	public void obtainDirectoryListing(String SourceDirectory, String DestinationDirectory,String ErrorColumn) throws FileNotFoundException {
		sourceDirectory = SourceDirectory;
		destinationDirectory = DestinationDirectory;
		final File folder = new File(SourceDirectory);
		File[] fileNames = folder.listFiles();
		int files = folder.listFiles().length;
		String columnToDelete = ErrorColumn;
		for (File file : fileNames) {
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
		}
	}

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
	
	private void writeFileToDisk(File file, StringBuffer csvBuffer) throws IOException {
        BufferedWriter bwr = new BufferedWriter(new FileWriter(new File(destinationDirectory+ file.getName())));	                   		                   
        bwr.write(buffer.toString());
        bwr.flush(); 
        bwr.close(); 
	}
	
	public boolean writeAllFilesToDisk(String input, String output) {
		final File inputDirectory = new File(input); 		       
		File [] inputfileNames = inputDirectory.listFiles(); 
		int inputDirectoryLength = inputfileNames.length;

		final File outputDirectory = new File(output); 		       
		File [] outputfileNames = outputDirectory.listFiles(); 
		int outputDirectoryLength = outputfileNames.length;

		for(File file:inputfileNames) {
			if(checkFileNames(file.getName(),outputfileNames)){
				if(outputDirectoryLength == inputDirectoryLength) {
					allFilesWrittenSuccesfullyToDisk = true;
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkFileNames(String name, File[] outputfileNames) {
		for(File outputFile:outputfileNames) {
			if(outputFile.getName().equals(name)) {
				outPutDirectoryLength = outPutDirectoryLength+1;
				return true;
			}
		}
		return false;
	}
	
	

}
