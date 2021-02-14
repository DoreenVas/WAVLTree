import WAVLTree.WAVLNode;

public class func {
	   public String min()
	   {
		   if (empty()) { return null; }  // the tree is empty
		   return this.min.info;
		   /*WAVLNode x = min(this.root);
		   if(x == null){
			   return null;
		   }
		    return x.info; */ 
	   }
	   
	   public String max()  
	   {
		   if (empty()) { return null; }  // the tree is empty
		   return this.max.info;
		   /*WAVLNode x = max(this.root);
		   if(x == null){
			   return null;
		   }
		   return x.info;  */
	   }
	   
	   public void switchNodes(WAVLNode x, WAVLNode y){
		   replaceChildren(x,y);
		   replaceParents(x,y);	   
	   }
	   
	   private void replaceParents(WAVLNode x, WAVLNode y) {
		   WAVLNode x_parent = x.parent;
		   WAVLNode y_parent = y.parent;
		   // updating x's parent
		   x.parent = y_parent;
		   if(y_parent.left == y){
			   y_parent.left = x; 
		   }
		   else{
			   y_parent.right = x;
		   }
		   // updating y's parent
		   y.parent = x_parent;
		   if(x_parent.left == x){
			   x_parent.left = y;
		   }
		   else{
			   x_parent.right = y;
		   }
		
	}

	   public void replaceChildren(WAVLNode x, WAVLNode y) {
	   	WAVLNode y_left = y.left;
	   	WAVLNode y_right = y.right;
	   	// updating y's left child
	   	y.left = x.left;
	   	if(y.left != EXT){
	   		y.left.parent = y;
	   	}
	   	// updating y's left child
	   	y.right = x.right;
	   	if(y.right != EXT){
	   		y.right.parent = y;
	   	}
	   	// updating x's left child
	   	x.left = y_left;
	   	if(x.left != EXT){
	   		x.left.parent = x;
	   	}
	   	// updating x's right child
	   	x.right = y_right;
	   	if(x.right != EXT){
	   		x.right.parent = x;
	   	}	
	   }
	   // for tester
	   // public String[] pre_order_tree(){
	 	   //list<String> arr = new ArrayList<>();
	 	   //return pre_order_creator(this.root,arr);  
	   // }

	  // public ArrayList<String> pre_order_creator(WAVLNode x, arr) {
	 	//if( x.left == EXT || x.right == EXT){ return new String[0];}
	 	//ArrayList<String> s = pre_order_creator(x.left);
	 	//ArrayList<String> s1 = pre_order_creator(x.right);
	 	//ArrayList<String> s2 = new ArrayList();
	 	//s2.add(x.info);
	 	//s2.addAll(s);
	 	//s2.addAll(s1);
	 	//return s2;
	 //}
	   
	   if(empty()){//in the insert
			  this.root = new_node;
			  //this.root.left = EXT;
			  //this.root.right = EXT;
			  //this.root.parent=null;
			  this.size++;
			  this.min = new_node;
			  this.max = new_node;
			  return count;
			}
}
