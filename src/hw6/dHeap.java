/*
 * Name: Jinxiao Chen
 * ID:A14236655
 * Login:cs12xii
 */

package hw6;

import java.util.*;
import java.lang.*;

public class dHeap<T extends Comparable<? super T>> 
        implements dHeapInterface<T> {
    
    private T[] heap; //heap array
    private int d; //branching factor
    private int nelems;
    private boolean isMaxHeap; //boolean to indicate whether heap is max or min
	
	/**
	 * Initializes a binary max heap with capacity = 7
	 */
	public dHeap() {
		heap = (T[]) new Comparable[7];
		isMaxHeap = true;
		nelems = 0;
		this.d = 2;
	}
    /**
     * Initializes a binary max heap with a given initial capacity.
     * 
     * @param heapSize The initial capacity of the heap.
     */
	public dHeap( int heapSize ) {
		heap = (T[]) new Comparable[heapSize];
		nelems = 0;
		isMaxHeap = true;
		this.d = 2;
		
	}

	/**
	 * Initializes a d-ary heap (with a given value for d), with a given
	 * initial capacity.
	 * 
	 * @param d The number of child nodes each node in the heap should have.
	 * @param heapSize The initial capacity of the heap.
	 * @param isMaxHeap indicates whether the heap should be max or min
	 * @throws IllegalArgumentException if d is less than one.
	 */
	@SuppressWarnings( "unchecked" )
    public dHeap( int d, int heapSize, boolean isMaxHeap) 
    		throws IllegalArgumentException {
	   if(d < 1){
		   throw new IllegalArgumentException();
	   }
	   this.d = d;
	   this.isMaxHeap = isMaxHeap;
	   nelems = 0;
	   heap = (T[]) new Comparable[heapSize];
	}

	
    /**
     * Returns the number of elements stored in the heap.
     * 
     * @return The number of elements stored in the heap.
     */
	public int size() {
	    
	   return nelems; 
	    
	}
	
	
	/**
     * Adds the specified element to the heap; o cannot be null. 
     * Resizes the storage if full.
     * 
     * @param o The element to add.
     * @throws NullPointerException if o is null.
     */
	public void add( T data ) throws NullPointerException {
		   if(data == null){
			   throw new NullPointerException();
		   }
		   if(this.nelems  == heap.length){
			   this.resize();
		   }
		   
		   heap[nelems] = data;
		   this.bubbleUp(nelems);
		   nelems ++;
		   
	    
	}
	


	 /**
     * Removes and returns the element at the root. If the 
     * heap is empty, then this method throws a NoSuchElementException.
     * 
     * @return The element at the root stored in the heap.
     * @throws NoSuchElementException if the heap is empty
     */
	public T remove() throws NoSuchElementException {
		if(this.isEmpty()){
			throw new NoSuchElementException();
		}
		
		T eleRemoved = heap[0];
		heap[0] = heap[nelems-1];
		nelems --;
		
		this.trickleDown(0);
		
		
	    return eleRemoved; 
	    
	}
	
	
    /**
     * Clears all the items in the heap
     * Heap will be empty after this call returns
     */
	public void clear() {
		 this.nelems = 0;
	}
	
	
    /**
     * Retrieves, but does not remove, the element at the root.
     * @return item at the root of the heap
     * @throws NoSuchElementException - if this heap is empty
     */
	public T element() {
		if(isEmpty()){
			throw new NoSuchElementException();
		}

		return heap[0];

	}
	
	
	private boolean isEmpty(){
		return nelems == 0;
	}
	
	private void resize(){
		T[] newheap = (T[]) new Comparable[2 * nelems];
		for(int i = 0; i < heap.length; i++){
			newheap[i] = heap[i];
		}
		this.heap = newheap;
	}
	
	private void bubbleUp(int index){
		T temp;
		
		int newindex = index;
		if(isMaxHeap){
			while(newindex != 0 && 
					heap[newindex].compareTo(heap[parent(newindex)])>0){
				
				temp = heap[this.parent(newindex)];
				heap[this.parent(newindex)] = heap[newindex];
				heap[newindex] = temp;
				
				newindex = this.parent(newindex);
			}
		}
		else{
			while(newindex != 0 && 
					heap[newindex].compareTo(heap[parent(newindex)])<0){
				
				temp = heap[this.parent(newindex)];
				heap[this.parent(newindex)] = heap[newindex];
				heap[newindex] = temp;
				
				newindex = this.parent(newindex);
			}
		}
	}
	
	private void trickleDown(int index){
		int child;
		int nindex = index;
		T temp;
		if(isMaxHeap){
			while (d*nindex + 1 < nelems && heap[d*index+1] != null){
				child = this.findmax(nindex);
				if (heap[nindex].compareTo(heap[child])<0){
					temp = heap[nindex];
	                heap[nindex] = heap[child];
	                heap[child] = temp;
				}
	            else{
	                break;
	            }
				nindex = child;
	        }
		}
		else{
			while (d*nindex + 1 < nelems && heap[d*index+1] != null){
				child = this.findmin(nindex);
				if (heap[nindex].compareTo(heap[child])>0){
					temp = heap[nindex];
	                heap[nindex] = heap[child];
	                heap[child] = temp;
				}
	            else{
	                break;
	            }
				nindex = child;
	        }
		}
	}
	
	private int parent(int index){
		return (int) Math.floor((index - 1) / d);
	}
	
	protected void print(){
		
		for(int i = 0; i <heap.length; i++){
			System.out.println(heap[i]);
		}
		
	}
	
	
	private int findmin(int j){
		T min = heap[d*j+1];
		int loc = d * j +1;
		

		for(int i=2; i<=this.d;i++){
			int child = d * j + i;
			if(heap[child] != null && child<nelems){

			if(min.compareTo(heap[child])>0){
				
				min = heap[child];
				loc = child;
			}
			}
			else{
				break;
			}
		}

        
		return loc;
	}
	
	private int findmax(int j){
		T max = heap[d*j+1];
		int loc = d * j +1;
		

		for(int i=2; i<=this.d;i++){
			int child = d * j + i;
			if(heap[child] != null && child<nelems){

			if(max.compareTo(heap[child])<0){
				
				max = heap[child];
				loc = child;
			}
			}
			else{
				break;
			}
		}

        
		return loc;
	}
} // End of public class dHeap<T extends Comparable<? super T>> 
  // implements dHeapInterface<T>.
