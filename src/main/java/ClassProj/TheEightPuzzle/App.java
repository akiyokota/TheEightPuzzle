package ClassProj.TheEightPuzzle;

import java.util.ArrayList;
import java.util.List;


public class App 
{
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
	
    public static void main( String[] args )
    {
    	
    }
}
