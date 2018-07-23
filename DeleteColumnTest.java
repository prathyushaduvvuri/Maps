package test;

import static org.junit.Assert.assertEquals;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.junit.Ignore;
import org.junit.Test;

import columndeletecsv.CSVColumnDeleter;
import columndeletecsv.ColumnDeleteCSV;

/************************************
/* @author Prathyusha
* Test Cases for ColumnDeleteCSV class
**************************************/

public class DeleteColumnTest {	

	String input = "C:\\\\Users\\\\pduvvur1\\\\Documents\\\\CSV\\\\Input\\\\";	
	String output = "C:\\\\Users\\\\pduvvur1\\\\Documents\\\\CSV\\\\Output\\\\";
	String columnToDelete = "ZCause";	
	StringBuffer buffer = new StringBuffer();	   
	ArrayList columnCounterList = new ArrayList(); 
	boolean allFilesWrittenSuccesfullyToDisk = false;
	int outPutDirectoryLength = 0;
	CSVColumnDeleter csvColumnDeleter = new CSVColumnDeleter();
	
	 @Test
	/*******************************************************************
	* Test Case:testSourceDirectoryInputFiles
	* Description:It will check if the input folder contains files or not.
	* Returns true if files exists, else false. 
	*********************************************************************/
	public void testSourceDirectoryInputFiles() throws IOException {
		final File folder = new File(input); 		       
		File [] fileNames = folder.listFiles(); 
		assertEquals(true, csvColumnDeleter.sourceDirectoryFile(fileNames));
	}

    @Test
	/*********************************************************
	* Test Case:testInputFileData
	* Description:Check if the input files contain data or not
	* Returns true if data exists, else false. 
	**********************************************************/
	public void testInputFileData() throws IOException {
		final File folder = new File(input); 		       
		File [] fileNames = folder.listFiles(); 
		for(File file:fileNames) {
			assertEquals(true, csvColumnDeleter.sourceDirectoryFileData(file));
		}
	}
	
	@Test
	/*************************************************************************************
	*Test Case:testDeleteColumn
	*Description:Check whether that Error column data which user entered is deleted or not.
	**************************************************************************************/	
	
	public void testDeleteColumn() throws FileNotFoundException {
		csvColumnDeleter.obtainDirectoryListing(input, output, columnToDelete);		
		assertEquals(true, csvColumnDeleter.columnDeleteCSV(buffer,columnToDelete));				
	}
	
	@Test
	/*****************************************************************************************
	*Test Case:testwriteFilesToDisk
	*Description:It will check whether all the input files are present in the output directory
	******************************************************************************************/
	public void testwriteFilesToDisk() throws FileNotFoundException {
		assertEquals(true, csvColumnDeleter.writeAllFilesToDisk(input,output));				
	}

	

}
