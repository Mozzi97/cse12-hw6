/*
 * Name: Jinxiao Chen
 * ID:A14236655
 * Login:cs12xii
 */

package hw6;

public class MyPriorityQueue<T extends Comparable<? super T>> {
    dHeap heap;
    
    public MyPriorityQueue( int initialSize ) {
        heap = new dHeap(3,initialSize,true);
        
    }
    
    /**
     * Inserts an element into the Priority Queue. The element received cannot
     * be null.
     *
     * @param element Element to be inserted.
     * @throws NullPointerException if the element received is null.
     * @return returns true
     */
    public boolean offer( T element ) throws NullPointerException {
    	if(element == null){
			   throw new NullPointerException();
		   }
    	heap.add(element);
        return true;
    }
    
    /**
     * Retrieves the head of this Priority Queue (largest element), or null 
     * if the queue is empty.
     *
     * @return The head of the queue (smallest element), or null if queue is
     *           empty.
     */
    public T poll() {
    	if(heap.size() == 0){
    		return null;
    	}
    	return (T) heap.remove(); 
    	
            
    }
    
    /**
     * Clears the contents of the queue
     */
    public void clear() {
    	heap.clear();
    }
    
    /**
     * Retrieves, but does not remove, the head of this queue, or
     *  returns null if this queue is empty.
     * @return the next item to be removed, null if the queue is empty
     */
    @SuppressWarnings("unchecked")
	public T peek() {
    	if(heap.size() == 0){
    		return null;
    	}
    	return (T) heap.element(); 
    }

}
