package hw6;

import java.util.ArrayList;
import java.util.List;

public class Course implements Course_Interface {
	
	private String courseName;
	private String courseCode;
	private MyPriorityQueue<Registration> waitlistQueue;
	private List<Student> roster;
	private int maxCapacity;
	private Registration reg;
	
	public Course(String name, String code, int capacity) {
		this.courseName = name;
		this.courseCode = code;
		this.maxCapacity = capacity;
		waitlistQueue = new MyPriorityQueue<Registration>(maxCapacity);
		roster = new ArrayList<Student>(maxCapacity);
	}
	
	/**
	 * Returns a string representation of course code
	 * @return course code
	 */
	public String toString() {
		return courseCode;
	}
	/**
	 * Getter for course name
	 * @return course name
	 */

	public String getCourseName() {
		return courseName;
	}
	
	/**
	 * Getter for course code
	 * @return course code
	 */
	public String getCourseCode() {
		return courseCode;
	}

	/**
	 * Getter for course capacity
	 * @return course capacity
	 */
	public int getCourseCapacity() {
		return maxCapacity;
	}

	/**
	 * Getter for Course Roster
	 * @return course roster
	 */
	public List<Student> getCourseRoster() {
		return roster;
	}

	
	/**
	 * Checks whether the course enrollment has reached its capacity
	 * @return Returns true if the number of enrolled students is equal to 
	 * capacity, false otherwise
	 */
	public boolean isFull() {

		return roster.size() == this.maxCapacity;
	}

	/**
	 * Enqueues the student to the waitlist for this course
	 * @param Registration r to be waitlisted
	 */
	public void addToWaitlist(Registration r) {
		waitlistQueue.offer(r);
		
	}
	
	/**
	 * Enrolls the next student in the waitlist to the course.
	 * Does nothing if the waitlist is empty
	 */
	public Registration processWaitlist() {
		if(waitlistQueue.peek() == null){
			return null;
		}
		else if(this.isFull()){
			reg = waitlistQueue.peek();
			CourseScheduling.printCapacity(reg.getCourse());
			return null;
		}
		
		else{
			reg = waitlistQueue.poll();
			roster.add(reg.getStudent());
			reg.getStudent().enrollCourse(reg.getCourse());
			CourseScheduling.print(reg.getStudent(),reg.getCourse(),
					reg.getCoins(),true);
			return reg;
		}
		
	}
	
}
