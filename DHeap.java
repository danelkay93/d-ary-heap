/**
 * D-Heap
 */

public class DHeap
{
	
    public int size, max_size, d;
    public DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
    DHeap(int m_d, int m_size) {
               max_size = m_size;
			   d = m_d;
               array = new DHeap_Item[max_size];
               size = 0;
    }
	
	/**
     * Return the number of elements in the heap
	 * @return Returns the number of elements in the heap.
	 */
	public int getSize() {
		return size;
    }

    /**
     * Set the number of elements in the heap
     * @param newSize The new number of elements in the heap.
     */
    public void setSize(int newSize) {
        if (newSize > this.max_size) {
            return;
        }
        this.size = newSize;
    }
	
  /**
     * The function builds a new heap from the given array.
     * Previous data of the heap should be erased.
     * postcondition: isHeap()
     * 				  size = array.length()
     * @param array1 The array to build a new heap from.
   * @return Returns number of comparisons along the function run.
	 */
    public int arrayToHeap(DHeap_Item[] array1)
    {
        int comp = 0;
        this.size = 0;
        for (int i = 0; i < array1.length; i++) {
            this.array[i] = array1[i];
            if (this.array[i] != null) {
                this.array[i].setPos(i);
                this.size += 1;
            }
        }
//        this.setSize(array1.length);
        for (int i = parent(this.getSize() - 1, this.d); i >= 0; i--) { //runs through all parent nodes
            comp += heapifyDown(i);
        }
        return comp;
    }

    /**
     * @return The function returns true if and only if the D-ary tree rooted at array[0]
     * satisfies the heap property or has size == 0.
     */
    public boolean isHeap()
    {
         if (this.getSize() == 0) {
             return true;
         }

        return isSubHeap(0);
    }

    /**
     * The function checks if the subheap with the parIndex-th element of the parent heap is a valid heap.
     * @param parIndex The node to use as a root node for the subheap
     * @return True if the subheap satisfies the heap property, False otherwise.
     */
    public boolean isSubHeap(int parIndex) {

        for (int i = child(parIndex, 1, this.d); i <= child(parIndex, d, this.d); i++) {
            if (i >= this.getSize()) {
                break;
            }
            if (this.array[parIndex].getKey() > this.array[i].getKey()) {
                return false;
            }
        }

        for (int i = child(parIndex, 1, this.d); i <= child(parIndex, d, this.d); i++) {
            if (i >= this.getSize()) {
                break;
            }
            if (!isSubHeap(i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Rearrange nodes in the heap to fulfill the heap property again.
     * @param i The heap element that is out of place
     *          @return The number of comparison operations performed.
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
        }

        return comp;
    }

    /**
     * Rearrange nodes in the heap so that a newly inserted element ends up in the correct position.
     * @param i The position of the newly inserted heap element
     *          @return The number of comparison operations performed.
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
     * Find the parent node of the node at position i assuming a d-ary heap
     * @param i The position of the node whose parent the method will find
     * @param d The number of children for each parent node, assuming a full heap
     * @return The position of the parent of the i-th node
     */
    public static int parent(int i, int d)
    {
        return (i - 1) / d;
    }

    /**
     * Find the k-th child node of the i-th node assuming a d-ary heap
     * @param i The position of the node whose child the method will find
     * @param k Among the i-th nodes children, the index of the node to find
     * @param d The number of children for each parent node, assuming a full heap
     * @return The position of the k-th child of the i-th node
     */
    public static int child (int i, int k, int d)
    {
        return (i * d) + k;
    }

    /**
	* Inserts the given item to the heap.
    * postcondition: isHeap()
     * @return The number of comparisons performed during the insertion.
     * @param item The DHeap_Item to insert
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
	* Deletes the minimum item in the heap.
    * postcondition: isHeap()
  * @return Returns the number of comparisons made during the deletion.
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
     * Get the item in the heap with the smallest key
     * postcondition: isHeap()
     * @return The minimum item in the heap.
     */
    public DHeap_Item Get_Min()
    {
	    return array[0];
    }

  /**
	 * Decreases the key of the given item by delta.
     * postcondition: isHeap()
   * @return Returns number of comparison operations made as a result of the decrease.
   * @param item The item whose key will be decreased
   *             @param delta The delta by which the item's key will be decreased
     */
    public int Decrease_Key(DHeap_Item item, int delta)
    {
        int comp = 0;
        item.setKey(item.getKey() - delta);

        comp += heapifyUp(item.getPos());

        return comp;
    }

	  /**
	 * Deletes the given item from the heap.
     * postcondition: isHeap()
       * @return Returns number of comparisons during the deletion.
       * @param item The heap item to delete
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
	* postcondition: array1 is sorted
     * @return Returns the number of comparisons performed.
     * @param array1 The input array to sort
     *               @param d The type of heap to sort the array as
	*/
	public static int DHeapSort(int[] array1, int d) {
        int comp = 0;
        DHeap sortedHeap = new DHeap(d, array1.length);
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

    /**
     * Print each item in the heap
     */
    public void printHeap() {
		for (int i = 0; i < getSize(); i++) {
			System.out.print(array[i].getKey() + ", ");
		}
		System.out.print("\n");
	}
}
