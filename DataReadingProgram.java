/**
 * COMI2510 - Advanced Java Programming
 * November 7th, 2016
 * 
 * Program designed to read a file and export a file formatted to be read
 * in excel, only reads .txt files and delimitates data with tabs
 * 
 * @author Dylan Grandjean
 */
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.*;

public class DataReadingProgram 
{
	/**
	 * main method where user enters name of the
	 * text file to be scanned.
	 * @param args - Array of Strings
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		//prompt user for file name
		String fileName = JOptionPane.showInputDialog(null, 
													  "Enter the name of the file you wish to open (.txt only)", 
													  "File Name", 
													  JOptionPane.QUESTION_MESSAGE);
		
		//check for existing file to open, reprompt if nothing is found
		File file = new File(checkFileName(fileName));
		while(!file.exists())
		{
			fileName = JOptionPane.showInputDialog(null, 
												   "The file could not be found. Try again", 
												   "Error", 
												   JOptionPane.ERROR_MESSAGE);
			if(fileName == null)
				System.exit(0);
			file = new File(checkFileName(fileName));
		}
		
		//create file scanner
		Scanner inputFile = new Scanner(file);
		
		//read and export file
		readFile(inputFile);
	}
	
	/**
	 * Read given file and transfers data to excel readable text file
	 * @param inputFile - file to get data from
	 * @throws IOException
	 */
	private static void readFile(Scanner inputFile) throws IOException
	{
		//create printers
		FileWriter fwriter = new FileWriter("GradesData.txt", true);
		PrintWriter outputFile = new PrintWriter(fwriter);
		
		String input = "";
		String[] tokens;
		String courseNum;
		String subject;
		String course;
		String courseTitle;
		
		int[] grades = new int[12];
		int transfer = 0;
		int passing = 0;
		
		//transfers data to a string in order to detokenize it
		while(inputFile.hasNext())
			input += inputFile.nextLine() + "\n";
		tokens = input.split("\t");
		
		//print title bar for the excel document
		outputFile.print("Course No.\t");
		outputFile.print("Subject\t");
		outputFile.print("Course\t");
		outputFile.print("Course Title\t");
		outputFile.print("A\t");
		outputFile.print("A-\t");
		outputFile.print("B\t");
		outputFile.print("B-\t");
		outputFile.print("B+\t");
		outputFile.print("C\t");
		outputFile.print("C+\t");
		outputFile.print("D\t");
		outputFile.print("D+\t");
		outputFile.print("F\t");
		outputFile.print("I\t");
		outputFile.print("W\t");
		outputFile.print("Transfer\t");
		outputFile.println("Passing\t");
		
		//print course information
		outputFile.print(tokens[0] + "\t");
		outputFile.print(tokens[2] + "\t");
		outputFile.print(tokens[4] + "\t");
		outputFile.print(tokens[3] + "\t");
		//calculate grades
		for(String s : tokens)
		{
			switch(s)
			{
				case "A":
					grades[0] += 1; transfer += 1; passing += 1; break;
				case "A-":
					grades[1] +=1; transfer += 1; passing += 1; break;
				case "B":
					grades[2] += 1; transfer += 1; passing += 1; break;
				case "B-":
					grades[3] += 1; transfer += 1; passing += 1; break;
				case "B+":
					grades[4] += 1; transfer += 1; passing += 1; break;
				case "C":
					grades[5] += 1; transfer += 1; passing += 1; break;
				case "C+":
					grades[6] += 1; transfer += 1; passing += 1; break;
				case "D":
					grades[7] += 1; passing += 1; break;
				case "D+":
					grades[8] += 1; passing += 1; break;
				case "F":
					grades[9] += 1; break;
				case "I":
					grades[10] += 1; break;
				case "W":
					grades[11] += 1; break;
			}		
		}
		//print grades to text file
		for(int i = 0; i < grades.length; i++)
			outputFile.print(grades[i] + "\t");
		outputFile.print(transfer + "\t");
		outputFile.print(passing + "\t");
		
		//confirmation message to let the user know the program worked
		System.out.println("Data saved in file \"GradesData.txt\"");
		//close the output file
		outputFile.close();
	}
	
	/**
	 * check for the file name and modifies it to a .txt file
	 * if necessary
	 * @param name - file name
	 * @return valid .txt file
	 */
	private static String checkFileName(String name)
	{
		String name2 = "";
		char ch = ' ';
		int index = 0;
		
		//checks from a . in the name
		for(int i = 0; i < name.length(); i++)
		{
			ch = name.charAt(i);
			if(ch == '.')
			{
				index = i;
				i = name.length();
			}
			else
				index = name.length();
		}
		//return full name if full name was given
		if(name.length() == index + 3 && name.charAt(index + 1) == 't' && name.charAt(index + 2) == 'x' && name.charAt(index + 3) == 't')
			return name;
		//adds a .txt to the name if ncessary
		else
		{
			for(int i = 0; i < index; i++)
			{
				ch = name.charAt(i);
				name2 += ch;
			}
			return name2 + ".txt";
		}	
	}
}
