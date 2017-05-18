package hw6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class CourseScheduling {
	
	public static List<Course> courseList = new LinkedList<>();
	public static List<Student> studentList = new LinkedList<>();
	
	public static void populateCourseandStudents(String fname) {
		File file = new File(fname);
		try {
			Scanner scanner = new Scanner(file);
			scanner.nextLine();
			boolean loadCourse = true;
			while(scanner.hasNextLine()) {
				String nextLine = scanner.nextLine();
				if(nextLine.isEmpty()) {
					loadCourse = false;
					scanner.nextLine();
					continue;
				}
				Scanner wordscan = new Scanner(nextLine);
				if(loadCourse) {
					String id = wordscan.next();
					String cname = wordscan.next();
					int capacity = wordscan.nextInt();
					Course course = new Course(cname,id,capacity);
					courseList.add(course);
				} else {
					String sname = wordscan.next();
					String pid = wordscan.next();
					int coins = wordscan.nextInt();
					Student student = new Student(pid,sname,coins);
					studentList.add(student);
				}
				wordscan.close();
			}
			scanner.close();
		} catch(FileNotFoundException e) {
			
		}
		
	}
	
	public static void print(Student s, Course c, int coins, boolean enroll) {
		if(enroll)
			System.out.println("Enrolling "+ s.toString() + " to Course " + 
					c.toString() + " with coins " + coins);
		else
			System.out.println("Waitlisting " + s.toString() + " to Course " + 
					c.toString() + " with coins " + coins);
	}
	
	public static void printFail(Student s, Course c, boolean enroll) {
		if(enroll)
			System.out.println(s.toString() + " is already enrolled in Course "
					+ c.toString());
		else
			System.out.println( s.toString() 
					+ " is already waitlisted in Course "
					+ "	" + c.toString());
	}
	
	public static void printCapacity(Course c) {
		System.out.println(c.toString() + 
		"has already reached maximum capacity. There are no more seats left");
	}
	
	public static void printNoCoins(Student s, Course c) {
		System.out.println("Insufficient course coins. Cannot waitlist " + 
				s.toString() + " to " + c.toString());
	}
	
	public static void generateOutput() {
		
		System.out.println("\n\n########COURSE INFORMATION######");
		ListIterator<Course> iter = courseList.listIterator();
		while(iter.hasNext()) {
			Course temp = iter.next();
			System.out.println(temp.getCourseCode()+"  "+ temp.getCourseName());
			System.out.println("\tRoster: "+temp.getCourseRoster());
		}
		
		System.out.println("\n\n########STUDENT INFORMATION######");
		ListIterator<Student> iterS = studentList.listIterator();
		while(iterS.hasNext()) {
			Student temp = iterS.next();
			System.out.println(temp.toString());
			System.out.println("\t Enrolled courses: " + temp.getmyEnrolledCourses());
			System.out.println("\t Waitlisted courses: " + temp.getmyWaitlist());
		}
		
		
	}
	
	
	//**********************YOUR WORK STARTS HERE*****************************
	/**
	 * Returns a reference to the Course object whose courseCode is code
	 * @param code Course code of the Course to be returned
	 * @return Course Course with the course code passed as a parameter
	 */
	public static Course getCourse(String code) {
		ListIterator<Course> iter = courseList.listIterator();
		Course crs = null;
		for(int i =0; i<courseList.size(); i++){
			crs = iter.next();
			if(crs.getCourseCode().equals(code)){
				return crs;
			}
		}
		return null;
	}
	
	/**
	 * Returns a reference to the Student object whose StudentID is pid
	 * @param pid  StudentID of the Student to be returned
	 * @return Student student with the id passed as a parameter
	 */
	public static Student getStudent(String pid) {
		ListIterator<Student> iter = studentList.listIterator();
		Student std = null;
		for(int i =0; i<studentList.size(); i++){
			std = iter.next();
			if(std.getStudentID().equals(pid)){
				return std;
			}
		}
		return null;
		
	}
	
	public static void main(String[] args) {
		populateCourseandStudents(args[0]);
		File file = new File( args[1]);
		
		try{
			Scanner scLine = new Scanner(file);	//scan lines in file
			System.out.println("####START COURSE SCHEDULING####\n");
			while (scLine.hasNextLine()) {//if the file has next line
				Scanner scWord = new Scanner(scLine.nextLine());
				String property;
				
		        if(scWord.hasNext()){//if the file has next word
		        	property = scWord.next();
		        }else break;
		        if(property.equals("register")){
		        	//add a new student to the course list
		        	Student std = getStudent(scWord.next());
		        	Course crs = getCourse(scWord.next());
		        	int coins = scWord.nextInt();
		        	
		        	
		        	boolean enroll = true;
		        	for(int i = 0; i<std.getmyWaitlist().size();i++){
		        		if(std.getmyWaitlist().get(i).getCourseName().equals(crs)){
		        			enroll = false;
		        			printFail(std,crs,false);
		        		}
		        	}
		        	
		        	for(int i = 0; i<std.getmyEnrolledCourses().size();i++){
		        		if(std.getmyEnrolledCourses().get(i).getCourseName().equals(crs)){
		        			enroll = false;
		        			printFail(std,crs,true);
		        		}
		        	}
		        	
		        	if(std.getCoins()<=0){
		        		enroll = false;
		        		printNoCoins(std,crs);
		        	}
		        	
		        	if(enroll){
		        		Registration reg = new Registration(std,crs,coins);
			        	reg.setTimestamp();
			        	reg.getCourse().addToWaitlist(reg);
			        	reg.getStudent().waitlistCourse(crs);
			        	reg.getStudent().deductCoins(reg.getCoins());
			        	for(int i = 0; i < std.getmyWaitlist().size();i++){
		     				print(std,std.getmyWaitlist().get(i),coins,false);
		    			}
		        	}
		        	
		        	
		    		
		        	
		        }else if(property.equals("enroll")){
		        	//process registrations in the waitlist
		        	System.out.println("\n####STARTING BATCH ENROLLMENT####");
		        	int quantity = scWord.nextInt();
		        	for(int i = 1; i<=quantity; i++){
		        		for(int j = 0; j<courseList.size(); j++){
		        			Course crs = courseList.get(j);
			        			crs.processWaitlist();
		        		}
		        	}
		        	
		        	
		        	
		        	
		        	System.out.println("####ENDING BATCH ENROLLMENT####\n");
		        }else break;
		        scWord.close();
		    }
			scLine.close();
			System.out.println("\n####END COURSE SCHEDULING####");
		}
		catch(FileNotFoundException e){
			System.err.println("Failed to open "+file);
			System.exit(1);
		}
		
		generateOutput();
	}//end of main method
}
