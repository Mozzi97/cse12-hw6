/*
 * Name: Jinxiao Chen
 * ID:A14236655
 * Login:cs12xii
 */

package hw6;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class dHeapTester {
	dHeap heap;
	dHeap empty;
	dHeap min;
	dHeap full;
	dHeap minimum;
	dHeap min2;
	

	@Before
	  public void setUp(){
		empty = new dHeap(0);
		heap = new dHeap(10);
		min = new dHeap(3,3,false);
		full = new dHeap(3,27,true);
		minimum = new dHeap(3,27,false);
		min2= new dHeap(3,27,false);
		heap.add(55);
		heap.add(24);
		heap.add(34);
		heap.add(33);
		heap.add(67);
		
		min.add(12);
		min.add(6);
		min.add(54);
		
		for(int i = 0; i<27; i++){
			full.add(i);
		}
		for(int i = 26; i>=0; i--){
			minimum.add(i);
		}
		for(int i = 0; i<=26; i++){
			min2.add(i);
		}
		
		
	}

	@Test
	public void testsize() {
		assertEquals("Check size",5,heap.size());
		assertEquals("Check size",0,empty.size());
	}
	
	@Test
	public void testelement() {
		assertEquals("Check element",67,heap.element());
		try{
			  empty.element();
			  fail("Should have generated an exception");  
		  }
		  catch(NoSuchElementException e){}
	}
	
	@Test
	public void testclear() {
		heap.clear();
		assertEquals("Check clear",0,heap.size());
		try{
			  heap.element();
			  fail("Should have generated an exception");  
		  }
		  catch(NoSuchElementException e){}
	
	}
	
	
	@Test
	public void testadd() {
		heap.add(99);
		assertEquals("Check add",99,heap.element());
		try{
			  heap.add(null);
			  fail("Should have generated an exception");  
		  }
		  catch(NullPointerException e){}
	
	}
	
	@Test
	public void testremove() {
		full.remove();
		assertEquals("Check remove",25,full.element());
		full.remove();
		assertEquals("Check remove",24,full.element());
		full.remove();
		assertEquals("Check remove",23,full.element());
//		full.print();
		try{
			  empty.remove();
			  fail("Should have generated an exception");  
		  }
		  catch(NoSuchElementException e){}
	
	}
	
	@Test
	public void testmin() {
		assertEquals("Check min-heap",6,min.element());
	}
	@Test
	public void testfull() {
		min.add(3);
		
		assertEquals("Check full and resize",3,min.element());
	}
	
	@Test
	public void testminimum1() {
//		minimum.print();
		minimum.remove();
		
		assertEquals("Check remove",1,minimum.element());
		minimum.remove();
		assertEquals("Check remove",2,minimum.element());
		minimum.remove();
		
		assertEquals("Check remove",3,minimum.element());
	}
	
	@Test
	public void testminimum2() {
		
		min2.remove();
		min2.print();
		assertEquals("Check remove",1,min2.element());
		min2.remove();
		assertEquals("Check remove",2,min2.element());
		min2.remove();
		
		assertEquals("Check remove",3,min2.element());
	}
	
}
