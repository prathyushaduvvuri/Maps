package columndeletecsv;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;	
	private JPanel contentPane;
	protected AbstractButton txtInputPath;
	protected JFrame frmDeleteUnusedFiles;
	private JTextField txtSourceDirectory;
	private JTextField txtDestinationDirectory;
	private JTextField ErrorColumn;

/******************************************************************************
* @author PDUVVUR1
* *GUI for deleting the Zcause,cause column from multiple comma separated  files.
*******************************************************************************/
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
/*****************
* Create the frame.
******************/	
	public Gui() 
	{
	setResizable(false);
		setTitle("DeleteCSVColumn");
		setBackground(Color.CYAN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1141, 713);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Header Label
		JLabel DeleteCSVColumn = new JLabel("DeleteCSVColumn");
		DeleteCSVColumn.setBounds(52, 46, 232, 29);
		DeleteCSVColumn.setForeground(Color.RED);
		DeleteCSVColumn.setFont(new Font("Calibri", Font.BOLD, 22));
		contentPane.add(DeleteCSVColumn);
				
        //SourceDirectory Label
		JLabel SourceDirectory = new JLabel("Source Directory");
		SourceDirectory.setBounds(89, 104, 177, 29);
		SourceDirectory.setFont(new Font("Calibri", Font.BOLD, 20));
		contentPane.add(SourceDirectory);
	
		//SourceDirectory text
		txtSourceDirectory = new JTextField();
		txtSourceDirectory.setBounds(311, 99, 457, 40);
		txtSourceDirectory.setText("");
		txtSourceDirectory.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
       
			}
		});
		txtSourceDirectory.setFont(new Font("Calibri", Font.BOLD, 18));
		contentPane.add(txtSourceDirectory);
		txtSourceDirectory.setColumns(10);
		
		//DestinationDirectory Label
		JLabel lblDestinationDirectory = new JLabel("Destination Directory");
		lblDestinationDirectory.setBounds(89, 187, 175, 26);
		contentPane.add(lblDestinationDirectory);
		lblDestinationDirectory.setFont(new Font("Calibri", Font.BOLD, 20));
		
		//DestinationDirectory text
		txtDestinationDirectory = new JTextField();
		txtDestinationDirectory.setBounds(311, 182, 457, 38);
		txtDestinationDirectory.setText("");
		txtDestinationDirectory.setFont(new Font("Calibri", Font.BOLD, 18));
		txtDestinationDirectory.setColumns(10);
		contentPane.add(txtDestinationDirectory);
		
		//Errorcolumn Label
		JLabel lblErrorcolumn = new JLabel("Error Column Name");
		lblErrorcolumn.setBounds(89, 255, 177, 40);
		lblErrorcolumn.setFont(new Font("Calibri", Font.BOLD, 20));
		contentPane.add(lblErrorcolumn);
		 final DefaultBoundedRangeModel model = new DefaultBoundedRangeModel();
		 model.setMinimum(0);
         model.setMaximum(100);
	       JProgressBar progressBar = new JProgressBar(model);
	       progressBar.setBounds(298, 432, 554, 37);
			JLabel lblStatus = new JLabel("");
			JLabel lblStatusMessage = new JLabel("Status Message:: ");
			lblStatusMessage.setBounds(94, 494, 943, 159);
			lblStatusMessage.setHorizontalAlignment(SwingConstants.LEFT);
			lblStatusMessage.setVerticalAlignment(SwingConstants.TOP);

			//Errorcolumn Text	
			ErrorColumn = new JTextField("Ex: ZCause, Cause");
			ErrorColumn.setBounds(311, 255, 301, 42);
			ErrorColumn.setForeground(Color.GRAY);
			ErrorColumn.addFocusListener(new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
			        if (ErrorColumn.getText().equals("Ex: ZCause, Cause")) {
			        	ErrorColumn.setText("");
			        	ErrorColumn.setForeground(Color.BLACK);
			        }				
				}
				@Override
				public void focusLost(FocusEvent e) {
			        if (ErrorColumn.getText().isEmpty()) {
			        	ErrorColumn.setForeground(Color.GRAY);
			            ErrorColumn.setText("Search");
			        }				
				}
			    });
			ErrorColumn.setFont(new Font("Calibri", Font.BOLD, 18));
			ErrorColumn.setColumns(10);
			contentPane.add(ErrorColumn);
		
		//Button to DeleteColumn
		JButton btnDeleteColumn = new JButton("Delete Column");
		btnDeleteColumn.setBounds(311, 366, 154, 29);
		btnDeleteColumn.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnDeleteColumn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {      						
				ColumnDeleteCSV columnDeleteCSV = new ColumnDeleteCSV(model, lblStatusMessage); //New Object for PerformAction class							        
				String SourceDirectory = txtSourceDirectory.getText();				
				String DestinationDirectory = txtDestinationDirectory.getText();
				String textErrorColumn = ErrorColumn.getText();
			    System.out.println("" + SourceDirectory);		    				
     			System.out.println("" + DestinationDirectory);
     			System.out.println("" + textErrorColumn);     			
     			try {
     				columnDeleteCSV.obtainDirectoryListing(SourceDirectory, DestinationDirectory, textErrorColumn);
     				String title = "Status";
     				JOptionPane.showMessageDialog(null, "Succesfully copied all files", title, JOptionPane.INFORMATION_MESSAGE);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (SecurityException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}    			     			
     			}		
		});
		contentPane.add(btnDeleteColumn);
		
		//Reset Button
		JButton btnReset = new JButton("Clear");
		btnReset.setBounds(526, 366, 115, 29);
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnReset.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent arg0) {		   
				txtSourceDirectory.setText(null);
				txtDestinationDirectory.setText(null);				
			}
		});
		contentPane.add(btnReset);
		
		//Exit
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(690, 366, 115, 29);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExit.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
                frmDeleteUnusedFiles = new JFrame("Exit");
				if (JOptionPane.showConfirmDialog(DeleteCSVColumn, " Confirm if you want to exit", "DeleteCSVColumn", 
						JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}								
			}
		});
		contentPane.add(btnExit);
		
		//Seperator
		JSeparator separator = new JSeparator();
		separator.setBounds(52, 418, 987, 14);
		contentPane.add(separator);
		
		//label Progress Bar		
		JLabel lblProgress = new JLabel("Progress");
		lblProgress.setBounds(95, 430, 133, 37);
		lblProgress.setFont(new Font("Calibri", Font.BOLD, 24));
		contentPane.add(lblProgress);
		
		//Progress Bar
		progressBar.setStringPainted(true);
		progressBar.setStringPainted(true);  	
		//add(progressBar);    
		//setSize(1200,664);    
		getContentPane().setLayout(null);  
    	getContentPane().add(progressBar);
  
		contentPane.add(progressBar);
		
		//Browse
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.setBounds(797, 97, 133, 40);
		btnBrowse.addActionListener(new ActionListener() {			
			public void actionPerformed(java.awt.event.ActionEvent evt) {
			
				JFileChooser fileChooser = new JFileChooser();				 
		        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);		 		     	 
		        fileChooser.setAcceptAllFileFilterUsed(false);		 
		        int rVal = fileChooser.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		        	txtSourceDirectory.setText(fileChooser.getSelectedFile().toString());		        
		        }
			}
		});
		contentPane.add(btnBrowse);
			
		JButton btnBrowse_1 = new JButton("Browse");
		btnBrowse_1.setBounds(797, 179, 133, 40);
		btnBrowse_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();				 		       
		        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);		 		       	 
		        fileChooser.setAcceptAllFileFilterUsed(false);		 
		        int rVal = fileChooser.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		        	txtDestinationDirectory.setText(fileChooser.getSelectedFile().toString());		        
		        }
			}
		});
		contentPane.add(btnBrowse_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(50, 70, 987, 267);		
		contentPane.add(panel);
	
		
		lblStatusMessage.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblStatusMessage);
						
       
    	   
    	   
       }
	}
	

