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
    
	//keeps track of all the moves made with the puzzle, mainly used to check repeated states
	public static List<puzzle> moveHistory = new LinkedList<puzzle>();
	
	//user input : choice of search algorithm
	public static int searchMode = 0;
	
	public static int maxNodesInQueue = 0;
	
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
    	maxNodesInQueue = 1;
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
			states.add(newNode);
		}
		return states;
	}

	public static int misplacedTileEstimation (puzzle p) {
		int estimation = 0;
		
		Integer val = 1;
		for (int i = 0; i < p.getSide(); i++) {
			for (int j = 0; j < p.getSide(); j++) {
				if (!( (i==p.getSide()-1) && (j==p.getSide()-1) )) {
					if(!p.getInstance().get(i).get(j).equals(Integer.toString(val)))
						estimation = estimation + 1;
				}
				val = val + 1;
			}
		}
		
		return estimation;
	}
	
	public static int manhattanDistanceCalc (int x, int y, int side, int val) {
		Integer v = 1;
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				if (v == val) 
					return Math.abs(x - i) + Math.abs(x - j);
				v = v + 1;
			}
		}
		return 0;
	}
	
	public static int manhattanDistanceEstimation (puzzle p) {
		int estimation = 0;
		
		Integer val = 1;
		for (int i = 0; i < p.getSide(); i++) {
			for (int j = 0; j < p.getSide(); j++) {
				if (!(p.getInstance().get(i).get(j).equals("*"))) {
					if(!p.getInstance().get(i).get(j).equals(Integer.toString(val)))
						estimation = estimation + manhattanDistanceCalc(i, j, p.getSide(), Integer.parseInt(p.getInstance().get(i).get(j)));
				}
				val = val + 1;
			}
		}
		
		return estimation;
	}
	
	public static void costGenerator (puzzle p) {
		if(searchMode==1) {
			p.priority = p.priority + 1;
		} else if(searchMode ==2) {
			p.priority = misplacedTileEstimation(p) + p.depth;
		} else if(searchMode ==3) {
			p.priority = manhattanDistanceEstimation(p) + p.depth;
		} else {
			System.err.println("Illegal move ID, program abort!");
    		System.exit(0);
		}
	}
	
	public static PriorityQueue<puzzle> QUEUEING_FUNCTION (PriorityQueue<puzzle> nodes, List<puzzle> problemOERATORS) {
		for(puzzle p: problemOERATORS) {
			if(!isRepeatedState(p)) {
				costGenerator(p);
				nodes.add(p);
				moveHistory.add(p);
			}
		}
		maxNodesInQueue = Math.max(maxNodesInQueue, nodes.size());
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
    	
    	puzzle p = general_search(problem, searchMode);
    	System.out.println("To solve this problem, the search algorithm expanded total of " + moveHistory.size() + " nodes.");
    	System.out.println("The maximum number of nodes in the queue at any one time was " + maxNodesInQueue);
    	System.out.println("The depth of the goal node was " + p.depth);
    	return;
    }
}
