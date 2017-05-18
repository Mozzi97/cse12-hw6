package hw6;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MyPriorityQueueTester {
	MyPriorityQueue queue;
	MyPriorityQueue queue2;

	@Before
	  public void setUp(){
		queue = new MyPriorityQueue(27);
		queue2 = new MyPriorityQueue(27);
		for(int i = 0; i<=26; i++){
			queue.offer(i);
		}
		
	}
	@Test
	public void testremove() {
		queue.poll();
		assertEquals(25,queue.peek());
		queue.poll();
		assertEquals(24,queue.peek());
		queue.poll();
		assertEquals(23,queue.peek());
	}
	
	@Test
	public void testadd() {
		queue2.offer(1);
		assertEquals(1,queue2.peek());
		queue2.offer(99);
		assertEquals(99,queue2.peek());
		queue2.offer(123);
		assertEquals(123,queue2.peek());
	}

}
