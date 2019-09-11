public class Heap<T extends Comparable<T>> {

    private HeapBinaryTree<T> heapTop;

    // Constructor for empty Heap
    public Heap() {
        heapTop = null;
    } // end constructor


    // To query if the heap is empty
    public boolean isEmpty() {
        return heapTop == null;
    } // end isEmpty


    // Returns the number of items
    // stored in the heap
    public int size() {
        if (isEmpty())
            return 0;
        else
            return heapTop.size();
    } // end size


    // Inserts a new item into the heap
    public void insert(T newItem) {
        if (isEmpty())
            heapTop = new HeapBinaryTree<T>(newItem);
        else
            heapTop.insert(newItem);
    } // end insert


    // Extract the top of the heap
    public T extractMax() throws HeapException {
        if (this.isEmpty())
            throw new HeapException("Error: Heap is empty!");
        else {
            T returnValue = heapTop.getItem();
            heapTop = heapTop.deleteRoot();
            return returnValue;
    } // end else

    } // end extractMax



    @Override
    public String toString(){
        if (!isEmpty())
            return heapTop.toString();
        else
            return "heap is empty!";
    }


    public int printNumLeafSlots(){
        return heapTop.calcNumLeafSlots();
    }


} // end Heap
