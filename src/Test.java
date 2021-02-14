import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		WAVLTree t = new WAVLTree();
		String errors="";
		
		if(t.min()!=null) errors+="1,";
		t.insert(10, "10");
		t.insert(5, "5");
		t.insert(15, "15");
		if(t.min()!="5") errors+="3,";
		if(t.max()!="15") errors+="4,";
		t.insert(9, "9");
		t.insert(8, "8");
		if(t.root.left.left.key!=5) errors+="20,";
		if(!t.search(8).equals("8")) errors+="17,";
		t.insert(111, "111");
		if(t.max()!="111") errors+="5,";
		t.insert(14, "14");
		t.insert(523, "523");
		if(t.root.right.right.right.key!=523) errors+="21,";
		if(t.size()!=8) errors+="9,";
		int[] ar={5, 8, 9, 10, 14, 15, 111, 523};
		String[] ars={"5", "8", "9", "10", "14", "15", "111", "523"};
		if(!Arrays.equals(t.keysToArray(), ar)) errors+="2,";
		if(!Arrays.equals(t.infoToArray(), ars)) errors+="18,";
		t.delete(14);
		if(t.root.right.key!=111) errors+="22,";
		if(t.root.right.left.parent.key!=111) errors+="23,";
		if(t.delete(0)!=-1) errors+="7,";
		if(t.size()!=7) errors+="8,";
		t.delete(10);
		if(t.root.key!=15) errors+="24,";
		if(t.search(10)!=null) errors+="18,";
		t.delete(5);
		if(t.min()=="5") errors+="6,";
		t.delete(8);
		t.delete(9);
		t.delete(15);
		if(t.root.key!=111) errors+="14,";
		//if(t.root.left.key!=null) errors+="15,";
		if(t.root.right.key!=523) errors+="16,";
		t.delete(111);
		if(t.empty()) errors+="11,";
		int[] ar2={523};
		if(!Arrays.equals(t.keysToArray(), ar2)) errors+="13,";
		t.delete(523);
		int[] ar3={};
		String[] ars3={};
		if(!Arrays.equals(t.keysToArray(), ar3)) errors+="12,";
		if(!Arrays.equals(t.infoToArray(), ars3)) errors+="19,";
		t.delete(0);
		if(!t.empty()) errors+="10,";
		if(errors.equals("")) System.out.println("Done! no errors");
		else System.out.println("Errors: "+errors);


	}

}
