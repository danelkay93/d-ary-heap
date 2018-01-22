/**
 * D-Heap
 */

public class DHeap
{
	
    private int size, max_size, d;
    private DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
    DHeap(int m_d, int m_size) {
               max_size = m_size;
			   d = m_d;
               array = new DHeap_Item[max_size];
               size = 0;
    }
	
	/**
	 * public int getSize()
	 * Returns the number of elements in the heap.
	 */
	public int getSize() {
		return size;
    }
    
    public void setSize(int newSize) {
        if (newSize > this.max_size) {
            return;
        }
        this.size = newSize;
    }
	
  /**
     * public int arrayToHeap()
     *
     * The function builds a new heap from the given array.
     * Previous data of the heap should be erased.
     * preconidtion: array1.length() <= max_size
     * postcondition: isHeap()
     * 				  size = array.length()
     * Returns number of comparisons along the function run. 
	 */
    public int arrayToHeap(DHeap_Item[] array1) 
    {   
        int comp = 0;
        for (int i = 0; i < array1.length; i++) {
            array[i] = array1[i];
        }
        this.setSize(array1.length);
        for (int i = parent(this.getSize() - 1, this.d); i >= 0; i--) { //runs through all parent nodes
            comp += heapifyDown(i);
        }
        return comp;
    }

    /**
     * public boolean isHeap()
     *
     * The function returns true if and only if the D-ary tree rooted at array[0]
     * satisfies the heap property or has size == 0.
     *   
     */
    public boolean isHeap() 
    {
        return false; // just for illustration - should be replaced by student code
    }
    
    /** 
     * heapifyDown()
     * 
     */
    public int heapifyDown(int i) 
    {
        int comp = 0;
        int smallest = i;
        for (int t = child(i, 1, this.d); t <= child(i, this.d, this.d); t++) {
            if (t >= this.getSize()) {
                break; // we reached the end of the heap
            }
            if (t < this.getSize() && this.array[t].getKey() < this.array[smallest].getKey()) {
                smallest = t;
            }
            comp++;
        }
        if (smallest != i) {
            DHeap_Item temp = this.array[i];
            this.array[i] = this.array[smallest];
            this.array[i].setPos(i);
            this.array[smallest] = temp;
            this.array[smallest].setPos(smallest);
            comp += heapifyDown(smallest);
        }

        return comp;
    }

    /**
     * HeapifyUp()
     * 
     */
    public int heapifyUp(int i) {
        int comp = 0;
        while (i > 0 && this.array[i].getKey() < this.array[parent(i, this.d)].getKey()) {
            int parPos = parent(i, this.d);
            DHeap_Item temp = this.array[i];
            this.array[i] = this.array[parPos];
            this.array[i].setPos(i);
            this.array[parPos] = temp;
            this.array[parPos].setPos(parPos);
            i = parPos;
            comp++;
        }
        return comp;
    }

    
 /**
     * public static int parent(i,d), child(i,k,d)
     * (2 methods)
     *
     * precondition: i >= 0, d >= 2, 1 <= k <= d
     *
     * The methods compute the index of the parent and the k-th child of 
     * vertex i in a complete D-ary tree stored in an array. 
     * Note that indices of arrays in Java start from 0.
     */
    public static int parent(int i, int d) 
    { 
        return (i - 1) / d;
    }
    
    public static int child (int i, int k, int d) 
    { 
        return (i * d) + k;
    }

    /**
    * public int Insert(DHeap_Item item)
    *
	* Inserts the given item to the heap.
	* Returns number of comparisons during the insertion.
	*
    * precondition: item != null
    *               isHeap()
    *               size < max_size
    * 
    * postcondition: isHeap()
    */
    public int Insert(DHeap_Item item) 
    {
        int comp = 0;
        this.array[getSize()] = item;
        item.setPos(getSize());
        this.setSize(this.getSize() + 1);

        comp += heapifyUp(item.getPos());
        
        return comp;
    }

 /**
    * public int Delete_Min()
    *
	* Deletes the minimum item in the heap.
	* Returns the number of comparisons made during the deletion.
    * 
	* precondition: size > 0
    *               isHeap()
    * 
    * postcondition: isHeap()
    */
    public int Delete_Min()
    {
        int comp = 0;
        this.array[0] = this.array[this.getSize() - 1];
        this.array[0].setPos(0);
        this.setSize(this.getSize() - 1);
        comp += heapifyDown(0);
        return comp;
    }


    /**
     * public DHeap_Item Get_Min()
     *
	 * Returns the minimum item in the heap.
	 *
     * precondition: heapsize > 0
     *               isHeap()
     *		size > 0
     * 
     * postcondition: isHeap()
     */
    public DHeap_Item Get_Min()
    {
	    return array[0]; // should be replaced by student code
    }
	
  /**
     * public int Decrease_Key(DHeap_Item item, int delta)
     *
	 * Decerases the key of the given item by delta.
	 * Returns number of comparisons made as a result of the decrease.
	 *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public int Decrease_Key(DHeap_Item item, int delta)
    {
        int comp = 0;
        item.setKey(item.getKey() - delta);

        comp += heapifyUp(item.getPos());

        return comp;
    }
	
	  /**
     * public int Delete(DHeap_Item item)
     *
	 * Deletes the given item from the heap.
	 * Returns number of comparisons during the deletion.
	 *
     * precondition: item.pos < size;
     *               item != null
     *               isHeap()
     * 
     * postcondition: isHeap()
     */
    public int Delete(DHeap_Item item)
    {
        if (item.getPos() >= this.getSize() - 1) { // last item is being deleted no comparisons
            this.setSize(this.getSize() - 1);
            return 0;
        }
        
        int comp = 0;
        DHeap_Item temp = this.array[this.getSize() - 1];
        this.array[item.getPos()] = temp;
        temp.setPos(item.getPos());
        this.setSize(this.getSize() - 1);
        
        comp += heapifyDown(temp.getPos());

        return comp;
    }
	
	/**
	* Sort the input array using heap-sort (build a heap, and 
	* perform n times: get-min, del-min).
	* Sorting should be done using the DHeap, name of the items is irrelevant.
	* 
	* Returns the number of comparisons performed.
	* 
	* postcondition: array1 is sorted 
	*/
	public static int DHeapSort(int[] array1, int d) {
        int comp = 0;
        DHeap sortedHeap = new DHeap(d, arrary1.length);
        DHeap_Item[] itemArr = new DHeap_Item[array1.length];
        
        for(int i = 0; i < array1.length; i++) {
            itemArr[i] = new DHeap_Item("Key: " + array1[i], array1[i]);
            itemArr[i].setPos(i);
        }

        comp += sortedHeap.arrayToHeap(itemArr);

        for (int i = 0; sortedHeap.getSize() != 0; i++) {
            array1[i] = sortedHeap.Get_Min().getKey();
            comp += sortedHeap.Delete_Min();
        }    
        return comp;
	}
}
