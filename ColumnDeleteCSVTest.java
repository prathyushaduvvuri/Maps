package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import org.junit.Before;
import org.junit.Test;

import columndeletecsv.ColumnDeleteCSV;

public class ColumnDeleteCSVTest {
	
	String inputDir = "files/input/";
	String outputDir = "files/output/";
	String inputFileName = "input.csv";
	String columnToDelete = "ZCause";
		
	@Before
	public void deleteOutputFile() {
		try {
			Files.deleteIfExists(Paths.get(outputDir + inputFileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testObtainDirectoryListing() throws FileNotFoundException {		
		ColumnDeleteCSV columnDeleteCSV = new ColumnDeleteCSV(new JProgressBar(), new JLabel());
		columnDeleteCSV.obtainDirectoryListing(inputDir, outputDir, columnToDelete);		
		File outputFile = new File(outputDir+inputFileName);
		assertTrue("Output file not found", outputFile.exists());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(outputFile));
			//Read first line and get the column names
			String line = br.readLine();	
			int index = line.indexOf(columnToDelete);
			assertTrue("Mentioned columns have not been deleted", index == -1);			
			String[] updatedColumns = line.split(",");
			assertTrue("Some columns Have not been updated", updatedColumns.length == 23); // Total 36 columns in input. 13 are ZCause, After removing, only 23 columns remain
		}catch (Exception e) {
			fail("Test failed with exception" + e.getMessage());
		}
		
	}

}
