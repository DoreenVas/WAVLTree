import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TesterWAVL {
	public static void main(String[] args){
		//Tester1();
		Tester2015 a = new Tester2015();
		a.run();
		
		System.out.println("yo hooo!");
		
	}
	/** insertion tester **/ 
	public static void Tester1(){
		WAVLTree t = new WAVLTree();
		t.insert(10, "10*");
		t.insert(7, "7*");
		t.insert(6, "6*");
		t.insert(11, "11*");
		t.insert(12, "12*");
		t.insert(13, "13*");
		t.insert(14, "14*");
		t.insert(15, "15*");
		t.insert(20, "20*");
		t.insert(5, "5*");
		t.insert(4, "4*");
		t.insert(1, "1*");

		
		
		if (t.min() != "1*"){
			System.out.println("Tester1: Error in min");
		}
		if (t.max() != "20*"){
			System.out.println("Tester1: Error in max");
		}
		String[] arr = { "1*" , "4*", "5*", "6*", "7*", "10*","11*","12*","13*","14*","15*","20*" };
		if( ! Arrays.toString(t.infoToArray()).equals(Arrays.toString(arr)) ){
			System.out.println("Tester1: Error in infoToArray");
		}
		int[] keys = { 1,4,5,6,7,10,11,12,13,14,15,20 };
		if( ! Arrays.equals(keys, t.keysToArray()) ){
			System.out.println("Tester1: Error in keysToArray");
		}
		//String[] pre_o = {"b", "c", "d", "a","e"};
		//t.print_pre();
		System.out.println("");
		System.out.println("");
		System.out.println("*");
		//if( ! Arrays.toString(t.pre_order_tree()).equals(Arrays.toString(pre_o))){
		//	System.out.println("Tester1: Error in pre_order_tree");
		//}
		List<String> s = new ArrayList<String>();
		for(int k:keys){
			s.add(t.search(k));
			
		}
		if (! s.toString().equals(Arrays.toString(arr))){
			System.out.println("Tester1: Error in search");
		}
		
		
		String[] pre = {"d", "b", "c", "a", "e", "f"};
		//t.print_pre();
		System.out.println("");
		System.out.println("dbcaef");
		//if ( ! Arrays.toString(t.pre_order_tree()).equals(Arrays.toString(pre))){
			//System.out.println("Tester1: Error in insert 13");
		//}
		
		t.insert(13, "dd");
		//t.print_pre();
		System.out.println("");
		System.out.println("Deletion");
		System.out.println(t.delete(14));
		System.out.println(t.delete(20));
		System.out.println(t.delete(15));
		//System.out.println(t.check_ranks());
		//t.print_pre();
	}

}
