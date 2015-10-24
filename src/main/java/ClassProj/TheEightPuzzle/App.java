package ClassProj.TheEightPuzzle;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class App 
{
	//public Queue<puzzle> nodes = new Queue<puzzle> ();
	
	public static void test() {
		List<String> a = new ArrayList<String> ();
    	a.add("1");
    	a.add("2");
    	a.add("3");
    	a.add("4");
    	a.add("5");
    	a.add("*");
    	a.add("7");
    	a.add("8");
    	a.add("9");
    	a.add("10");
    	a.add("11");
    	a.add("12");
    	a.add("13");
    	a.add("14");
    	a.add("15");
    	a.add("16");
    	
    	puzzle p = new puzzle(3, a);
    	//p.move_up();
    	//p.move_up();
    	//p.move_down();
    	//p.move_down();
    	//p.move_left();
    	//p.move_left();
    	//p.move_right();
    	//p.move_right();
    	p.print();
    	p.star_loc();
    	p.list_legal_moves();
	}
	
	/* This function reads the file provided by the filename(with lcoation)
	 * returns the content of the file
	 */
	public static String readFile(String filename) {
		String content = null;
	    File file = new File(filename); //for ex foo.txt
	    FileReader reader = null;
	    try {
	        reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return content;
	}
	
	/* This function takes in the filename(with location) 
	 * read the content and create the puzzle with the content 
	 * in the file. 
	 * This function returns an object puzzle
	 */
	public static puzzle createPuzzle () {
		System.out.println("Welcome to Akiyo Yokota n-puzzle solver");
    	System.out.println("Type \"1\" to use a default puzzle");
    	System.out.println("Type \"2\" to enter your own puzzle");
    	
    	Scanner reader = new Scanner(System.in);  // Reading from System.in
    	System.out.println("Enter a number: ");
    	int n = reader.nextInt(); // Scans the next token of the input as an int.
		
    	if(n == 1) {
	    	String rawInput = readFile("default.txt");
	    	List<String> normalizedInput = Arrays.asList(rawInput.split(","));
	    	Integer side = (int)Math.sqrt(normalizedInput.size());
	    	return new puzzle(side, normalizedInput);
    	} else if (n ==2){
    		return null;
    	} else {
    		System.err.println("Invalid user input, program abort!");
    		return null;
    	}
	}
	
    public static void main( String[] args )
    {
    	
    	
    	puzzle p = createPuzzle();
    	if (p == null)
    		return;
    	p.print();
    	p.list_legal_moves();
    	
    	return;
    }
}
