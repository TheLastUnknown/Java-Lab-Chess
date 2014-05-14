package module1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

public class Reader 
{
	public Map<Character,String> map = new HashMap<Character,String>()
			{
		{
			put('K', "King");
			put('Q', "Queen");
			put('B', "Bishop");
			put('N', "Knight");
			put('R', "Rook");
			put('P', "Pawn");
			put('l', "White");
			put('d', "Black");
		}
			};
	public String newLine = System.getProperty("line.separator");
	public String pieceFinder = "[KQBNRP][l|d][a-hA-h][1-8]";
	public String moveFinder = "[a-h][1-8] [a-h][1-8]\\*?";
	public String specialMove = "([a-h][1-8] [a-h][1-8]) ([a-h][1-8] [a-h][1-8])";
	
	//Once a file is recieved, the method reads through the chosen method
	//For each line, the method will read until it reads a new line
	//Once a new line is detected, the read line is split and sent into the String Array
	//After all lines are read and processed into the array, the method sends each line
	//into piecePlace() to determine the proper output. Once that is finished,
	//The readers are closed
	public void readFile(String FileName) throws IOException
	{
		//setupMap();
		try 
		{
			FileReader input = new FileReader(FileName);
			BufferedReader reader = new BufferedReader(input);
			
			String line = "";
			while(reader.ready())
			{
				line = reader.readLine();
				String[] split = line.split(newLine);
				for(String s : split)
				{
					System.out.println(piecePlace(s));
				}
			}
			input.close();
			reader.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("The file name you entered could not be found");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	//Matches the input from the file to regex in order to pick the correct output
	public String piecePlace(String piece)
	{
		if(Pattern.matches(pieceFinder, piece))
		{
			return matchesPiecePlaced(piece);
		}
		else if(Pattern.matches(moveFinder, piece))
		{
			return matchesPieceMoved(piece);
		}
		else if(Pattern.matches(specialMove,piece))
		{
			return matchesSpecialMove(piece);
		}
		else
		{
			return "Not a valid input";
		}
		
	}
	
	//Returns a string that specifies the color and type of chess piece by finding the
	//chosen values in the hash map
	public String showPiece(char type,char color)
	{
		return map.get(color) + " " + map.get(type);
	}
	
	//Returns the output for placing a specific piece on the specified space
	public String matchesPiecePlaced(String piece)
	{
		return "place " + showPiece(piece.charAt(0),piece.charAt(1)) + " on " + piece.substring(2, 4);
	}
	
	//Returns the output for a special move. Will only be hit if two moves are on the same line in the file
	public String matchesSpecialMove(String piece)
	{
		return matchesPieceMoved(piece.substring(0, 5)) + " and " + matchesPieceMoved(piece.substring(6, 11));
	}
	
	//returns the output for moving a piece from space A to space B
	//If the move specified is a capture, the program will respond with the proper output
	public String matchesPieceMoved(String spaces)
	{
		
		String spaceOne = spaces.substring(0,2).toUpperCase();
		String spaceTwo = spaces.substring(3, 5).toUpperCase();
		
		if(Pattern.matches("[a-h][1-8] [a-h][1-8]", spaces))
		{
			return spaceOne + " moves to " + spaceTwo;
		}
		else if(Pattern.matches("[a-h][1-8] [a-h][1-8]\\*", spaces))
		{
			return spaceOne + " moves to " + spaceTwo + " and captures the piece at " + spaceTwo;	
		}
		else
		{
			return "";
		}
	}
	
	//Fills a hash map with the values that correspond with all chess piece types and the
	//two colors
	public void setupMap()
	{
		map.put('K', "King");
		map.put('Q', "Queen");
		map.put('B', "Bishop");
		map.put('N', "Knight");
		map.put('R', "Rook");
		map.put('P', "Pawn");
		map.put('l', "White");
		map.put('d', "Black");
	}
}
