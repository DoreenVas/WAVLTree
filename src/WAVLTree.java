
/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree with
 * distinct integer keys and info
 *
 * 
 */

public class WAVLTree {
	
	private static WAVLNode EXT;	
	private WAVLNode root;	
	private int size;
	private WAVLNode min;
	private WAVLNode max;
	
	public WAVLTree(){
		this.root = null;
		this.size = 0;
		this.min = null;
		this.max = null;
		EXT = new WAVLNode(null,null,null,-1,(int) Double.NEGATIVE_INFINITY, "EXT");
	}

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
    return (this.root == null);
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k){
	  if( empty()) { return null; }
	WAVLNode x = treeSearch(this.root, k);
	if(x != EXT){
		return x.info;
	}
	else{
		return null;
	} 
  }
  
  private int rankDiff(WAVLNode x,WAVLNode y){
	  return x.rank-y.rank; 
  }

  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {	   
	  int count = 0;
	  WAVLNode newNode = new WAVLNode(EXT, null, EXT, 0, k, i);
	  /* if the tree is empty */
	  if(empty()){
		  this.root = newNode;
		  this.size++;
		  this.min = newNode;
		  this.max = newNode;
		  return count;
		}
	  
	  WAVLNode newNodeParent = treePosition(this.root, k);
	  // if z already exist in the tree
	  if(newNodeParent.key == k) {
		  return -1; 
	  }  
	  this.size++;
	  newNode.parent = newNodeParent;
	  // updating the min and max pointers if necessary
	  if(k < this.min.key){
		  this.min = newNode;
	  }
	  if(k > this.max.key){
		  this.max = newNode;
	  } 
	  // start checking the cases of insertion
	  if(isLeaf(newNodeParent)){
		  newNodeParent.rank++;
		  count ++; 
		  if(k < newNodeParent.key){  // k should be left child
			  newNodeParent.left = newNode;
		  }
		  else{ newNodeParent.right = newNode;}
		  WAVLNode y = newNodeParent.parent;
		  return rebalancingTreeInsert(y,newNodeParent,count);
		}
	  if(isUnaryRight(newNodeParent)){ // case B
		  newNodeParent.left = newNode; 
		  return count;
	  }
	  if(isUnaryLeft(newNodeParent)){ // case B
		  newNodeParent.right = newNode; 
		  return count;
	  }	   
	  return count;
}
  // rebalancing operations, y is z's parent 
  private int rebalancingTreeInsert(WAVLNode y, WAVLNode z, int counter) {
	  int count = counter;
	  
	  while (true){
		  if(y == null ||rankDiff(y,z)!=0){ return count; } // problem solved
		  /* if z is the left child of y */
		  if(y.left == z){ 
			  // case 1 (a)
			  if(rankDiff(y,z)==0 && rankDiff(y,y.right)==1){ 
				  y.rank++;
				  z = y;
				  y = y.parent;
				  count ++;
				  continue;			   
			  	}
			  // case 2
			  if( rankDiff(z,z.right)== 2){ 
				  y.rank--;
				  rightRotation(y);
				  count ++;
				  return count;
			  	}
			  // case 3
			  if( rankDiff(z,z.left)== 2 && rankDiff(z,z.right)== 1){ 
				  z.rank--;
				  y.rank --;
				  z.right.rank++;
				  doubleRightRotation(y);
				  count += 2;
				  return count;   
				}  
		  }
		  /* if z is the right child of y */	   
		  if(y.right == z){ 
			// case 1 (b)
			  if(rankDiff(y,z)==0 && rankDiff(y,y.left)== 1 ){ 
				  y.rank++;
				  z = y;
				  y = y.parent;
				  count ++;
				  continue;	   
			  	}
			// case 4 
			  if(rankDiff(z,z.left)== 2){ 
				  y.rank--;
				  leftRotation(y);
				  count ++;
				  return count;
			  	}  
			  // case 5
			  if( rankDiff(z,z.left) == 1 && rankDiff(z,z.right) == 2){ 
				  z.rank--;
				  y.rank --;
				  z.left.rank++;
				  doubleLeftRotation(y);
				  count += 2;
				  return count;	   
				}  
			}		   
		}
	
}

/**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
	   boolean problem = false;
	   int count = 0;
	   WAVLNode itemToDelete;
	   // if the tree is empty
	   if( empty()) { return -1; }
	   // Deleting the minimum item
	   if( k == this.min.key){
		   itemToDelete = this.min;
		   this.min = successor(this.min);  // updating the minimum of the tree   
		   
	   }
	   // deleting the maximum item
	   else if ( k == this.max.key){
		   itemToDelete = this.max;
		   this.max = predecessor(this.max); // updating the maximum of the tree
	   }
	   else{
		   itemToDelete = treeSearch(this.root, k); 
		   // y is not found in the tree
		   if (itemToDelete == null || itemToDelete == EXT)
			   return -1;
		   
		   // if y has 2 children - replacing it with it's successor
		   if(itemToDelete.left!=EXT && itemToDelete.right!=EXT){ 
			   WAVLNode w = successor(itemToDelete);
			   swap(itemToDelete,w);
			   itemToDelete = w;  //changing the name of the variable
		   	}
	   }
	   this.size--;
	   WAVLNode z = itemToDelete.parent;
	   
	    // if itemToDelete is the root
	   if(z == null ){ 
		   return deletingRoot(count);
	   }
	   // Deleting a leaf
	   if(isLeaf(itemToDelete)){ 
		   // itemToDelete is the left child of z
		   if (z.left == itemToDelete){
			   z.left = EXT;  // removing itemToDelete from the tree
			   
				   if(rankDiff(z,z.left) == 2 && rankDiff(z,z.right)== 1){  // no problem at the tree
					   return count;
				   }	   
				   else if (rankDiff(z,z.left)== 2 && rankDiff(z,z.right)== 2){ // z is a (2,2) leaf
					   z.rank--;
					   count++;
				   }
				   else if (rankDiff(z,z.left)== 3){
					   problem = true; // we should send z into the rebalancing and not z.parent
				   } 
			   }
		   // itemToDelete is the right child of z
		   if (z.right == itemToDelete){
			   z.right = EXT; // removing itemToDelete from the tree
				   if(rankDiff(z,z.right)== 2 && rankDiff(z,z.left)== 1) // // no problem at the tree
					   return count;
				   else if (rankDiff(z,z.right)== 2 && rankDiff(z,z.left)== 2){ // z is a (2,2) leaf
					   z.rank--;
					   count++;
				   }
				   else if (rankDiff(z,z.right)== 3){ // we should send z into the rebalancing and not z.parent
					   problem = true;
				   } 
			   }
	   }
	   // itemToDelete has only left child
	   else if(isUnaryLeft(itemToDelete)){ 
		// itemToDelete is the left child of z
		   if(z.left == itemToDelete){  
			   z.left = itemToDelete.left;
			   itemToDelete.left.parent = z;
		   }
		// itemToDelete is the right child of z
		   if(z.right == itemToDelete){
			   z.right = itemToDelete.left;
			   itemToDelete.left.parent = z;
		   }
		   if(rankDiff(z,z.left)== 3 || rankDiff(z,z.right)== 3)
			   problem = true;
		   else
			   return count;  // no problem created
	   }
	    //itemToDelete has only right child
	   else if(isUnaryRight(itemToDelete)){ 
		   // itemToDelete is the left child of z
		   if(z.left == itemToDelete){
			   z.left = itemToDelete.right;
			   itemToDelete.right.parent = z;
		   }
		   // itemToDelete is the right child of z
		   if(z.right == itemToDelete){
			   z.right = itemToDelete.right;
			   itemToDelete.right.parent = z;
		   }
		   if(rankDiff(z,z.left)==3 || rankDiff(z,z.right)==3)
			   problem = true;
		   else
			   return count;  // no problem created
	   }	  
	   if(problem == false)
		   z = z.parent;  // we send the parent to the rebalancing method
	   return rebalancingTreeDeletion(z, count);

   }
   
    // rebalancing operations
   private int rebalancingTreeDeletion(WAVLNode z, int count) {
	   
	   while(true){
		   if(z == null)  // we are at the root
			   return count;
		   if(case_1(z)|| case_5(z)){
			  z.rank--;
			  count++;
			  z = z.parent;
			  continue;
		  }
		  if(case_2(z)){
			  z.rank--;
			  z.right.rank--;
			  count++;
			  z = z.parent;
			  continue;
		  }
		  if(case_3(z)){
			  z.rank--;
			  z.right.rank++;
			  leftRotation(z);
			  count++;
			  if(isLeaf(z)&& rankDiff(z,z.left)==2 && rankDiff(z,z.right)==2){ // z is a (2,2) leaf
				  z.rank--;
			  } 
			  return count;
		  }
		  if(case_4(z)){
			  z.rank-=2;
			  z.right.rank--;
			  z.right.left.rank+= 2;
			  count+= 2;
			  doubleLeftRotation(z);
			  return count;
		  }
		  if(case_6(z)){
			  z.rank--;
			  z.left.rank--;
			  count++;
			  z = z.parent;
			  continue;
		  }
		  if(case_7(z)){
			  z.rank--;
			  z.left.rank++;
			  rightRotation(z);
			  count++;
			  if(isLeaf(z)&& rankDiff(z,z.left)==2 && rankDiff(z,z.right)==2){  // z is a (2,2) leaf
				  z.rank--;
			  } 
			  return count;
		  }  
		  if(case_8(z)){
			  z.rank-=2;
			  z.left.rank--;
			  z.left.right.rank+=2;
			  doubleRightRotation(z);
			  count+=2;
			  return count;
		  } 
		  return count;
	   }
}

private int deletingRoot(int count) {
	
		WAVLNode y = this.root; // temporarily pointer to the root in order to update the nodes.
	   if (isLeaf(y)){
		   this.root = null;
		   this.min = null;
		   this.max = null;
		   this.size = 0;
		   return count;
	   }
	    // y has only left child
	   else if(isUnaryLeft(y)){ 
		   this.root = y.left;
		   this.min = y.left;
		   this.max = y.left;
		   this.root.parent = null; 
		   this.root.rank = 0;
		   return count;
	   }
	   // y has only right child
	   else if(isUnaryRight(y)){ 
		   this.root = y.right;
		   this.min = y.right;
		   this.max = y.right;
		   this.root.parent = null; 
		   this.root.rank = 0;
		   return count;
	   }
	   return count;  
}

private boolean case_8(WAVLNode z) {
	   if(rankDiff(z,z.left)== 1 && rankDiff(z,z.right)== 3){
			WAVLNode y = z.left;
			if(rankDiff(y,y.right)== 1 && rankDiff(y,y.left)== 2)
				return true;
		}
		return false;
}

private boolean case_7(WAVLNode z) {
	if(rankDiff(z,z.left)== 1 && rankDiff(z,z.right)== 3){
		WAVLNode y = z.left;
		if(rankDiff(y,y.left)== 1)
			return true;
	}
	return false;
}

private boolean case_6(WAVLNode z) {
	if(rankDiff(z,z.right)== 3 && rankDiff(z,z.left)== 1){
		WAVLNode y = z.left;
		if(rankDiff(y,y.left)== 2 && rankDiff(y,y.right)== 2)
			return true;
	}
	return false;
}

private boolean case_5(WAVLNode z) {
	if(rankDiff(z,z.left)== 2 && rankDiff(z,z.right)== 3)
		return true;
	return false;
}

private boolean case_4(WAVLNode z) {
	if(rankDiff(z,z.left)== 3 && rankDiff(z,z.right)== 1){
		WAVLNode y = z.right;
		if(rankDiff(y,y.right)== 2 && rankDiff(y,y.left)== 1)
			return true;
	}
	return false;
}

private boolean case_3(WAVLNode z) {
	if(rankDiff(z,z.left)== 3 && rankDiff(z,z.right)== 1){
		WAVLNode y = z.right;
		if(rankDiff(y,y.right)== 1)
			return true;
	}
	return false;
}

private boolean case_2(WAVLNode z) {
	if(rankDiff(z,z.left)== 3 && rankDiff(z,z.right)== 1){
		WAVLNode y = z.right;
		if(rankDiff(y,y.left)== 2 && rankDiff(y,y.right)== 2)
			return true;
	}
	return false;
}

private boolean case_1(WAVLNode z) {
	if(rankDiff(z,z.left)==3 && rankDiff(z,z.right)==2)
		return true;
	return false;
}

/**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
	   if (empty()) { return null; }  // the tree is empty
	   return this.min.info;
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()  
   {
	   if (empty()) { return null; }  // the tree is empty
	   return this.max.info;
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
  public int[] keysToArray()
  {
      int[] arr; 
      //empty tree check 
      if(empty()){
      	return arr = new int [0];
      }
      else{
      	arr = new int [this.size];
      	int position = 0; 
      	sortedOrder(this.root,arr, position);
      }
      return arr;              
             
  }

  private int sortedOrder(WAVLNode x,int[] arr, int position){
	  if (x!= EXT){
		  position = sortedOrder(x.left,arr,position);
		  arr[position] = x.key; 
		  position++;
		  position = sortedOrder(x.right,arr, position);
	  }
	  return position;

  }
  
  /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
  public String[] infoToArray()
  {
		 String[] arr; 
	      //empty tree check 
	      if(empty()){
	      	return arr = new String[0];
	      }
	      else{
	    	arr = new String [this.size];
	      	int position = 0; 
	      	sortedOrderInfo(this.root,arr, position); 	
	      }
	      return arr;              
             
}
 private int sortedOrderInfo(WAVLNode x,String[]arr, int position){
	  if (x!= EXT){
			 position=sortedOrderInfo(x.left,arr,position);
			 arr[position] = x.info;
			 position++;
			 position = sortedOrderInfo(x.right,arr,position);
		  }
		  return position;

  }


   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    */
   public int size()
   {
	   return this.size;
   }
   
   // return the Minimum in subtree x
   private WAVLNode min(WAVLNode x){
	   if(x == null){ return null; }
	   while(x.left != EXT){
		   x = x.left;
	   }
	   return x;
   }
// return the Maximum in subtree x
   private WAVLNode max(WAVLNode x){
	   if(x == null){ return null; }
	   while(x.right != EXT){
		   x = x.right;
	   }
	   return x;
   }
   
   private WAVLNode successor(WAVLNode x){
	   if(x.right != EXT){
		   return min(x.right);
	   }
	   
	   WAVLNode y = x.parent;
	   while(y != null && x == y.right){
		   x = y;
		   y = x.parent;
	   }
	   return y;
   }
   
   private WAVLNode predecessor(WAVLNode x){
	   if(x.left != EXT){
		   return max(x.left);
	   }
	   
	   WAVLNode y = x.parent;
	   while(y != null && x == y.left){
		   x = y;
		   y = x.parent;
	   }
	   return y;
   }

   // search in subtree x the key k
   private WAVLNode treeSearch(WAVLNode x, int k){
		while(x != EXT && x!=null){
			if(x.key == k) {
				return x;
			}
			if(x.key < k){ // should be at right child
				x = x.right;
			}
			else{
				x = x.left;
			}
		}
		return x; 
   }
   // returns the position for inserting key k. if k found in the subtree x, returns its node. 
   private WAVLNode treePosition(WAVLNode x, int k){
	   WAVLNode y = EXT;
	   while(x != EXT){
		   y = x;
		   if(x.key == k){
			   return x;
		   }
		   if(x.key > k){
			   x = x.left;
		   }
		   else{
			   x = x.right;
		   }
	   }
	   return y;
   }
   // left rotation of x with right child y
  private void leftRotation(WAVLNode x){
	   WAVLNode y = x.right;
		WAVLNode z = x.parent;
		x.right = y.left;
		if(x.right != EXT){
			x.right.parent = x;
		}	 
		y.left = x;
		y.parent = z;
		x.parent = y;
		// updating z's child to be y
		if(z != null){
			if(z.left == x){
				z.left = y;
			}
			else{
				z.right = y;
			}
		}
		if(this.root == x){
			this.root = y;
		}
		
	   }
   
// right rotation of x with left child y
   private void rightRotation(WAVLNode x){
	WAVLNode y = x.left;
	WAVLNode z = x.parent;
	x.left = y.right;
	if(x.left != EXT){
		x.left.parent = x;
	}	 
	y.right = x;
	y.parent = z;
	x.parent = y;
	// updating z's child to be y
	if(z!= null){
		if(z.left == x){
			z.left = y;
		}
		else{
			z.right = y;
		}
	}
	if(this.root == x){
		this.root = y;
	}
   }
   
   private void doubleLeftRotation(WAVLNode x){
	   rightRotation(x.right);
	   leftRotation(x);
   }
   
   private void doubleRightRotation(WAVLNode x){
	   leftRotation(x.left);
	   rightRotation(x);
	
   }
   
   // switch node x with y
   // precondition: (x != EXT && Y != EXT)  
  private void swap(WAVLNode x,WAVLNode y){
	  String info = x.info;
	  int key = x.key;
	  x.key = y.key;
	  x.info = y.info;
	  y.key = key;
	  y.info = info;
	  if( y == this.min){
		  this.min = x;
	  }
	  if(y == this.max){
		  this.max = x;
	  }
  }

   private boolean isLeaf(WAVLNode x){
	   return (x.left == EXT && x.right == EXT );
   }
   
   // x has only left child
   private boolean isUnaryLeft(WAVLNode x){
	   return (x.left != EXT && x.right == EXT);
   }
   // x has only right child
   private boolean isUnaryRight(WAVLNode x){
	   return (x.left == EXT && x.right != EXT);
   }


/**
   * public class WAVLNode
   *
   * If you wish to implement classes other than WAVLTree
   * (for example WAVLNode), do it in this file, not in 
   * another file.
   * This is an example which can be deleted if no such classes are necessary.
   */
   private class WAVLNode{
	  
	  private WAVLNode left;
	  private WAVLNode parent;
	  private WAVLNode right;
	  private int rank;
	  private int key;
	  private String info;
	  
	  private WAVLNode(WAVLNode left, WAVLNode parent, WAVLNode right, int rank, int key, String info){
		  this.left = left;
		  this.parent = parent;
		  this.right = right; 
		  this.rank = rank;
		  this.key = key;
		  this.info = info;
	  }  
  }
 
}


  
