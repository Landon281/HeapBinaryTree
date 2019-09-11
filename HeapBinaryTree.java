/*
Date: 05/01/19
Input: none
Output: none
Purpose: To create a Heap ADT Binary tree that utilizes an external "Heap" class that
uses helper methods located in this class. This implementation constantly swaps items
as needed in order to maintain the structure of heap characteristics: 1. complete binary
tree 2. no parent has children that store a value greater than its value
*/
public class HeapBinaryTree<T extends Comparable<T>> {

    // Attributes
    private T item;
    private HeapBinaryTree<T> leftChild;
    private HeapBinaryTree<T> rightChild;
    private int size;

// This constructor adds the new item, sets
// both children to null, and the size (of
// the subtree rooted at this node) to 1.
public HeapBinaryTree(T newItem) {
    item = newItem;
    leftChild = null;
    rightChild = null;
    size = 1;
} // end constructor

// This method returns the number of items
// within the subtree rooted at this node.
public int size() {
    return size;
} // get size


// Returns the item stored in this node.
public T getItem() {
    return item;
} // end getItem


// Calculate the number of possible leaf
// nodes in the subtree rooted by this node.
public int calcNumLeafSlots() {

    //this methodology returns the power that 2 will need to be raised
    //to in order to find max number of leaf slots at the bottom level
    double log = Math.floor(Math.log(this.size)  / Math.log(2));
    return (int)Math.floor(Math.pow( 2 , log ));

} // calcNumLeafSlots


//helper method for goleft method that returns number of vacant nodes
//at bottom level of the heap
private int calcUsable(){

    //calculate usable nodes at the bottom
    int usable = Math.abs(this.size - ((calcNumLeafSlots() * 2) - 1));

    //if level is full, the next available level has twice as many
    //slots available
    if (usable == 0)
            usable = calcNumLeafSlots() * 2;

    return usable;

}//end calcUsable helper method


// This method is a helper function used by the
// insert method to determine if the next open
// slot in the complete binary tree is along
// the left child or not.
private boolean goLeftToFirstEmptySlot() {

    //return if usable slots is more than half the max leaf slots
    return (calcUsable() > (calcNumLeafSlots() / 2));

} // end goLeftToFirstEmptySlot



// Helper method used by insert in order to
// swap items between nodes within the tree
// if the item in this node is smaller than
// its child.
// Type T must implement the Comparable
// interface and the compareTo method.
private void swapItems(HeapBinaryTree<T> child) {

        //store compareTo answer in a variable (will either be -1, 0, or 1)
        int answer = this.item.compareTo(child.item);

        //if result is -1, the child is larger than the parent
        //a swap must be done to maintain heap structure
        if (answer < 0) {

            T childValue = child.item;
            T thisValue = this.item;

            this.item = childValue;
            child.item = thisValue;

        }//end if

} // end swapItems



// Insert the newItem into the heap. Reorder
// to maintain heap order.
public void insert(T newItem) {

    //locate a position for the new item to be placed
    //with every addition to the tree, swap items with
    //child Heap if necessary
    //also call swap along with each recursive call
    if (this.leftChild == null) {
        this.leftChild = new HeapBinaryTree<T>(newItem);
        this.swapItems(leftChild);
    }
    else if (this.rightChild == null) {
        this.rightChild = new HeapBinaryTree<T>(newItem);
        this.swapItems(rightChild);
    }
    else if(this.goLeftToFirstEmptySlot()) {
        this.leftChild.insert(newItem);
        this.swapItems(leftChild);
    }
    else{
        this.rightChild.insert(newItem);
        this.swapItems(rightChild);
    }

    //increment size because insert has to have happened by now
    this.size++;

} // end insert



// This method is a helper function used by the
// getReplacement method to determine if the last
// filled slot in the complete binary tree is
// along the left child or not
private boolean goLeftToLastNode() {

    //if there are no more usable nodes on the bottom row,
    //the last added node must be on the right side of the
    //current nodes location
    if (calcUsable() == 0)
        return false;
    else
        //else return if node must be on the left OR if USABLE (empty)
        //slots are exactly half of the current nodes max leaf slots, or less than it
        return (calcUsable() >= (this.calcNumLeafSlots() / 2));

} // end goLeftToLastNode



// This method is a helper function for the
// deleteRoot method to determine what node
// (or what item) will replace the deleted
// one
private T getReplacement() {

    //if both children are null, a leaf position has been located
    //the value at this leaf must be  the last added node
    if (this.leftChild == null && this.rightChild == null)
        return this.item;

    //if the right child is null, but left isn't, the left child
    //has to be the last added node
    else if (this.leftChild != null && this.rightChild == null)
        return leftChild.item;

   //recursively call "getReplacement" in order to locate last added node
    //utilize "goLeftToLastNode" helper method to guide traversal in the right
    //direction
   if (goLeftToLastNode())
       return leftChild.getReplacement();
   else
       return rightChild.getReplacement();

} // end getReplacement



// This method is a helper function for the
// method deleteRoot which deletes the node
// that was replaced at the root.
private HeapBinaryTree<T> deleteReplacement() {

    //create HPT object that will store the deleted node
    //before emptying it so there's something to return afterward
    HeapBinaryTree<T> deletion;

    //if left child is not null but right is, and size is 2
    //the value at the left child must be the last added node
    //store node, remove it, decrement size, return node
    if (this.leftChild != null && this.rightChild == null && this.size == 2) {
        deletion = this.leftChild;
        this.leftChild = null;
        this.size--;
        return deletion;
    }//end if

    //if the right child is not null, and left isn't, and size is 3
    // the right child has to be the last added node
    //store node, remove it, decrement size, return node
    else if (this.rightChild != null && this.leftChild != null && this.size == 3){
        deletion = this.rightChild;
        this.rightChild= null;
        this.size--;
        return deletion;
    }//end else it


    //recursively call "deleteReplacement" in order to locate last added node
    //utilize "goLeftToLastNode" helper method to guide traversal in the right
    //direction
    if (goLeftToLastNode())
        return leftChild.deleteReplacement();
    else
        return rightChild.deleteReplacement();


} // end deleteReplacement



// This method is a helper function for the
// method deleteRoot which reorders the heap
// after the root item is deleted and replaced
// with the last node in the heap tree.
private void heapRebuild() {

        //base case
        //if a leaf has been found stop recursively calling heapRebuild
        if (rightChild == null && leftChild == null)
            return;

        //if both children are not null, call "swapItems" on both
        //of them
        if (this.leftChild != null && this.rightChild != null){
            this.swapItems(leftChild);
            this.swapItems(rightChild);
        }//end if

        //if leftChild is not null, call swapItems on it and recursively
        //call heapRebuild
        else if (this.leftChild != null){
            this.swapItems(leftChild);
            leftChild.heapRebuild();
        }//end else if

} // end heapRebuild


// This method deletes the root, replaces it
// with the last node (item) in the tree, and
// reorders the tree to meet the heap property.
public HeapBinaryTree<T> deleteRoot() {

        //reassign root item to replacement located
        this.item = this.getReplacement();


        //delete the replacement, decrement heap size
        this.deleteReplacement();
        this.size--;

        //if node was remove from left size of tree, decrement size of ROOTs left child node
        if(goLeftToLastNode())
            this.leftChild.size--;
        else
        //if node was removed from left side of tree, decrement size ROOTs right child node
            this.rightChild.size--;

        //re-heapify the Heap tree
        this.heapRebuild();

        return this;

} // end deleteRoot

    //implement toString method to visually see layout of heap
    //FOR TESTING PURPOSES ONLY
    public String toString() {
        if ((leftChild == null) && (rightChild == null))
            return "(" + this.item.toString() + ", "
                    + this.size + ", null, null)";
        else if (leftChild == null)
            return "(" + this.item.toString() + ", "
                    + this.size + ", null, "
                    + rightChild.toString() + ")";
        else if (rightChild == null)
            return "(" + this.item.toString() + ", "
                    + this.size + ", "
                    + leftChild.toString() + ", null)";
        else
            return "(" + this.item.toString() + ", "
                    + this.size + ", "
                    + leftChild.toString() + ", "
                    + rightChild.toString() + ")";
    } // end toString

} // end HeapBinaryTree
