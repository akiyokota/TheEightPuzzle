package ClassProj.TheEightPuzzle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class puzzle {
	private List < List<String> > instance;
	private Integer side;
	private Integer star_x;
	private Integer star_y;
	public Integer depth;
	public Integer priority;
	
	/*
	 * This is a default constructor
	 */
	public puzzle() {
		this.instance = new ArrayList< List<String> > ();
		this.side = 0;
		this.star_x = 0;
		this.star_y = 0;
		depth = 0;
		priority = 0;
	}
	
	public puzzle(puzzle p) {
		this.instance = new ArrayList< List<String> > ();
		for(int i = 0; i< p.getInstance().size(); i++) {
			this.instance.add(new ArrayList<String> (p.getInstance().get(i)));
		}
		this.side = p.getSide();
		this.star_x = p.getStar_x();
		this.star_y = p.getStar_y();
		this.depth = p.depth;
		this.priority = p.priority;
	}


	/*
	 * This is a constructor, it takes in the sides of the puzzle
	 * as well as the values in the puzzle in a form of List of Strings
	 * and construct the initial state of the puzzle. 
	 * This function also record the x and y coordinate of the '*'
	 */
	public puzzle (Integer side, List<String> input) {
		this.instance = new ArrayList< List<String> > ();
		this.side = side;
		this.star_x = 0;
		this.star_y = 0;
		this.depth = 0;
		this.priority = 0;
		for (int i = 0; i < side; i++) {
			List <String> row = new ArrayList<String> ();
			for (int j = 0; j < side; j++) {
				row.add(input.get(i*side + j));
				/* check if current input is the '*' 
				 * if it is, set the coordinate of the location
				 */
				if(input.get(i*side+j).equals("*")) {
					this.star_x = i;
					this.star_y = j;
				}
			}
			this.instance.add(row);
		}
	}

	/*
	 * This function returns all the legal moves
	 */
	public List<Integer> problemOPERATORS() {
		List<Integer> legal_move_list = new LinkedList<Integer> ();
		/*
		 * up 		- 1
		 * down 	- 2
		 * left 	- 3 
		 * right 	- 4
		 */
		if(this.star_x>0) {
			legal_move_list.add(1);
		}
		if(this.star_x<this.side-1) {
			legal_move_list.add(2);
		}
		if(this.star_y>0) {
			legal_move_list.add(3);
		}
		if(this.star_y<this.side-1) {
			legal_move_list.add(4);
		}
		return legal_move_list;
	}
	
	public void makeMove(Integer moveID) {
		/*
		 * up 		- 1
		 * down 	- 2
		 * left 	- 3 
		 * right 	- 4
		 */
		if (moveID ==1) {
			move_up();
		} else if (moveID==2) {
			move_down();
		} else if (moveID==3) {
			move_left();
		} else if (moveID==4) {
			move_right();
		} else {
			System.err.println("Illegal move ID, program abort!");
    		System.exit(0);
		}
	}
	
	/*
	 * This function moves the * up in the puzzle
	 */
	public void move_up () {
		if(star_x.equals(0)) {
			System.out.println("bumping the wall!");
			return;
		}
		String temp = this.instance.get(this.star_x -1).get(this.star_y);
		this.instance.get(this.star_x-1).set(this.star_y, "*");
		this.instance.get(this.star_x).set(this.star_y, temp);
		this.star_x = this.star_x -1 ;
	}
	
	/*
	 * This function moves the * down in the puzzle
	 */
	public void move_down () {
		if(star_x.equals(this.side-1)) {
			System.out.println("bumping the wall!");
			return;
		}
		String temp = instance.get(this.star_x +1).get(this.star_y);
		instance.get(this.star_x+1).set(this.star_y, "*");
		instance.get(this.star_x).set(this.star_y, temp);
		this.star_x = this.star_x +1 ;
	}
	
	/*
	 * This function moves the * left in the puzzle
	 */
	public void move_left () {
		if(star_y.equals(0)) {
			System.out.println("bumping the wall!");
			return;
		}
		String temp = instance.get(this.star_x).get(this.star_y -1);
		instance.get(this.star_x).set(this.star_y-1, "*");
		instance.get(this.star_x).set(this.star_y, temp);
		this.star_y = this.star_y -1 ;
	}
	
	/*
	 * This function moves the * right in the puzzle
	 */
	public void move_right () {
		if(star_y.equals(this.side-1)) {
			System.out.println("bumping the wall!");
			return;
		}
		String temp = instance.get(this.star_x).get(this.star_y +1);
		instance.get(this.star_x).set(this.star_y+1, "*");
		instance.get(this.star_x).set(this.star_y, temp);
		this.star_y = this.star_y +1 ;
	}
	
	/*
	 * This function reports the location of *
	 */
	public void star_loc() {
		System.out.println("The coordinate of * is : " +
				" x= " + this.star_x + " y = " + this.star_y);
	}
	
	/*
	 * This function prints out the puzzle at current state
	 */
	public void print() {
		System.out.println("The puzzle is : ");
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				System.out.print(this.instance.get(i).get(j) + "\t" );
			}
			System.out.print("\n");
		}
	}

	/* check if the puzzle is at goal state
	 * function returns false if its not goal state
	 * function returns true if it is at goal state
	 */
	public boolean GOAL_TEST() {
		Integer val = 1;
		for (int i = 0; i < side; i++) {
			for (int j = 0; j < side; j++) {
				if (!( (i==this.side-1) && (j==this.side-1) )) {
					if(!this.instance.get(i).get(j).equals(Integer.toString(val)))
						return false;
				}
				val = val + 1;
			}
		}
		
		return true;
	}
	

	
	/*
	 * Returns side of the puzzle
	 */
	public Integer getSide() {
		return side;
	}

	/*
	 * Set sides of the puzzle
	 */
	public void setSide(Integer side) {
		this.side = side;
	}

	/*
	 * Returns the instance of the puzzle
	 */
	public List<List<String>> getInstance() {
		return instance;
	}

	/*
	 * Set instance of the puzzle
	 */
	public void setInstance(List<List<String>> instance) {
		this.instance = instance;
	}
	
	public Integer getStar_x() {
		return star_x;
	}

	public void setStar_x(Integer star_x) {
		this.star_x = star_x;
	}

	public Integer getStar_y() {
		return star_y;
	}

	public void setStar_y(Integer star_y) {
		this.star_y = star_y;
	}
}
