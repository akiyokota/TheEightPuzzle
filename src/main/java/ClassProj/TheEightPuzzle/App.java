package ClassProj.TheEightPuzzle;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class App 
{
	 //Comparator anonymous class implementation
    public static Comparator<puzzle> priorityComparator = new Comparator<puzzle>(){
         
       // @Override
        public int compare(puzzle p1, puzzle p2) {
            return (int) (p1.priority - p2.priority);
        }
    };
	
	//public static Queue<puzzle> nodes = new LinkedList<puzzle> ();
	public static PriorityQueue<puzzle>nodes = new PriorityQueue<puzzle> (10, priorityComparator);
    
	public static List<puzzle> moveHistory = new LinkedList<puzzle>();
	public static int searchMode = 0;
	
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
	
	/* This function asks user whether to use default puzzle or customized puzzle
	 * If use default puzzle, this function will return a default puzzle that
	 * is inside of deafult.txt. Else, it will ask user to input the puzzle line
	 * by line.
	 * This function also asks which search mode the user desires. This decision
	 * will be remembered by a global variable 'searchMode'.
	 */
	public static String createPuzzle () {
		String problem = null;
		System.out.println("Welcome to Akiyo Yokota n-puzzle solver");
    	System.out.println("Type \"1\" to use a default puzzle");
    	System.out.println("Type \"2\" to enter your own puzzle");
    	
    	@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);  // Reading from System.in
    	System.out.println("Enter a number: ");
    	String n = reader.nextLine(); // Scans the next token of the input as an int.
		
    	if(n.equals("1")) {
	    	problem  = readFile("default.txt");
	    	//List<String> normalizedInput = Arrays.asList(rawInput.split(" "));
	    	//Integer side = (int)Math.sqrt(normalizedInput.size());
	    	//p= new puzzle(side, normalizedInput);
    	} else if (n.equals("2")){
    		// obtain user input
    		String input;
    		System.out.println("Enter your puzzle, user a '*' to represent blank");
    		System.out.println("Enter first row, use space between numbers ");
    		input = reader.nextLine();
    		System.out.println("Enter second row, use space between numbers ");
    		input = input + " " + reader.nextLine();
    		System.out.println("Enter third row, use space between numbers ");
    		input = input + " " + reader.nextLine();
    		input = input.replaceAll("\\s+"," ");
    		problem = input;
    		//List<String> normalizedInput = Arrays.asList(input.split(" "));
	    	//Integer side = (int)Math.sqrt(normalizedInput.size());
	    	//p= new puzzle(side, normalizedInput);
    	} else {
    		System.err.println("Invalid user input, program abort!");
    		System.exit(0);
    	}
    	
    	System.out.println("Enter your choice of algorithm:\n"
    			+ "1. Uniform Cost Search.\n"
    			+ "2. A* with Misplaced Tile heuristic.\n"
    			+ "3. A* with the Manhattan distance heuristic.\n");
    	searchMode = reader.nextInt(); // Scans the next token of the input as an int.
	
    	return problem;
	}
	
	public static puzzle MAKE_NODE(String problem_INITIAL_STATE) {
		List<String> normalizedInput = Arrays.asList(problem_INITIAL_STATE.split(" "));
    	Integer side = (int)Math.sqrt(normalizedInput.size());
    	return new puzzle(side, normalizedInput);
	}
	
	public static PriorityQueue<puzzle> MAKE_QUEUE(puzzle p) {
		PriorityQueue<puzzle> q = new PriorityQueue<puzzle>(10, priorityComparator);
		q.add(p);
		return q;
	}
	
	public static List<puzzle> EXPAND(puzzle node, List<Integer> problemOPERATORS) {
		List<puzzle> states = new LinkedList<puzzle> ();
		for (Integer i : problemOPERATORS) {
			puzzle newNode = new puzzle(node);
			newNode.makeMove(i);
			newNode.depth = newNode.depth + 1;
			newNode.priority = newNode.priority + 1;
			states.add(newNode);
		}
		return states;
	}

	
	public static PriorityQueue<puzzle> QUEUEING_FUNCTION (PriorityQueue<puzzle> nodes, List<puzzle> problemOERATORS) {
		for(puzzle p: problemOERATORS) {
			if(!isRepeatedState(p)) {
				nodes.add(p);
				moveHistory.add(p);
			}
		}
		return nodes;
	}


	private static boolean isRepeatedState(puzzle node) {
		if(!moveHistory.isEmpty()) {
			for (puzzle p : moveHistory) {
				if(p.getInstance().equals(node.getInstance()))
					return true;
			}
		}
		return false;	
	}

	/*
	 * function general-search(problem,QUEUEING-FUNCTION)
	 * nodes = MAKE-QUEUE(MAKE-NODE(problem.INITIAL-STATE))
	 * loop do
	 * 	if EMPTY(nodes) then return "failure"
	 * 	node = REMOVE-FRONT(nodes)
	 * 	if problem GOAL-TEST(node.STATE) succeeds then return node
	 * 		nodes = QUEUEING-FUNCTION(nodes,EXPAND(node, problemOPERATORS))
	 *  end
	 */
	public static puzzle general_search(String problem, int queueing_function) {
		nodes = MAKE_QUEUE(MAKE_NODE(problem));
		while(true) {
			if(nodes.isEmpty())
				return null;
			puzzle node = nodes.remove(); 
			node.print();
			if(node.GOAL_TEST()) 
				return node;
			nodes = QUEUEING_FUNCTION(nodes, EXPAND(node, node.problemOPERATORS()));
		}
	}
	
    public static void main( String[] args )
    {
    	String problem = createPuzzle();
    	
    	general_search(problem, searchMode);
    	
    	return;
    }
}
