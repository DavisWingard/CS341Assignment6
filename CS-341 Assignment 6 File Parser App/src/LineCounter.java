import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class LineCounter {

	private JFrame frame;
	private JButton btnNewButton;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LineCounter window = new LineCounter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LineCounter() {
		initialize();
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getFile();
			}
		});
	}
	
	public void getFile() {
		File inputFile;
		Scanner fileInputScan = null;
		
		Stack<String> braceStack = new Stack<>();
		
		int lines = 0;
		int invalidLines = 0;
		int blockCounter = 0;
		int forCounter = 0;
		int ifCounter = 0;
		int whileCounter = 0;
		int methodCounter = 0;
		
		try {
		//use filechooser to select a file
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
		
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				inputFile = fileChooser.getSelectedFile();
				
				
				//count number of comment lines and control structures
				fileInputScan = new Scanner(inputFile);
				while (fileInputScan.hasNextLine()) {
					String s = fileInputScan.nextLine();
					//line counter will only count a line if it is not a comment and it is not an empty line
					if (s.trim().startsWith("//") || s.trim().length() == 0) {
						invalidLines++;
					}
					else if(s.trim().startsWith("for")) {
						forCounter++;
					}
					else if(s.trim().startsWith("while")) {
						whileCounter++;
					}
					else if(s.trim().startsWith("if")) {
						ifCounter++;
					}
				}
				
				
				//count the number of lines in each method
				//the stack is used to keep track of how many curly braces are present in the method. if the stack is empty, the last curly brace of the method has been counted
				fileInputScan = new Scanner(inputFile);
				while (fileInputScan.hasNextLine()) {
					String s = fileInputScan.nextLine();
					if(s.trim().startsWith("public static") || s.trim().startsWith("private static") || s.trim().startsWith("static")) {
						methodCounter = 0;
						braceStack.push("{");
						while (fileInputScan.hasNextLine() && !braceStack.isEmpty()) {
							String string = fileInputScan.nextLine();
							if (string.contains("{")) {
								braceStack.push("{");
							}
							else if (string.contains("}")) {
								braceStack.pop();
							}
							methodCounter++;
						}
						//display the declaration of each method as well as the number of lines in each method
						textArea.setText(textArea.getText() + s.trim().substring(0, s.indexOf("(")-1) + "\n" + "Total number of lines: " + (methodCounter + 1) + "\n");
					}
				}
			
				
				//find total number of lines in the program
				fileInputScan = new Scanner(inputFile);
				while (fileInputScan.hasNextLine()) {
					fileInputScan.nextLine();
					lines++;
				}
				
				
				//find total number of block comments
				fileInputScan = new Scanner(inputFile);
				while (fileInputScan.hasNextLine()) {
					String s = fileInputScan.nextLine();
					if (s.trim().startsWith("/*")) {
						blockCounter = 1;
						braceStack.push("/*");
						while (fileInputScan.hasNextLine() && !braceStack.isEmpty()) {
							String string = fileInputScan.nextLine();
							if (string.contains("*/")) {
								braceStack.pop();
							}
							invalidLines += blockCounter;
						}
					}
				}
				
				
				//display total non comment lines
				textArea.setText(textArea.getText() + "\n" + "Total non comment lines: " + (lines - invalidLines - blockCounter));
				textArea.setText(textArea.getText() + "\n" + "Total for loop structures: " + forCounter);
				textArea.setText(textArea.getText() + "\n" + "Total while loop structures: " + whileCounter);
				textArea.setText(textArea.getText() + "\n" + "Total if statement structures: " + ifCounter);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error. File could not be found");
		} finally {
			if (fileInputScan != null) {
				fileInputScan.close();
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Select File:");
		btnNewButton.setBounds(168, 18, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 4, 4);
		frame.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBounds(34, 59, 381, 207);
		frame.getContentPane().add(textArea);
	}
	
	/* TESTING SUMMARY
	 * Line count does not change when white space is added
	 * Extra comments added are not counted
	 * Keywords inside comments are not counted as control structures
	 * Method line counters will only stop counting when the corresponding closing brace is encountered - loop structures with curly braces are not considered
	 * 
	 */
}