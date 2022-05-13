/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class CourseQueries {
    private static Connection connection;
    
    private static PreparedStatement getAllCourses;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement deleteCourse;
    //private static PreparedStatement dropCourse;
    
    private static ResultSet allCourseResults;
    private static ResultSet allCourseCodeResults;
    private static ResultSet courseSeats;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<>();
        try
        {
            getAllCourses = connection.prepareStatement("select * from app.course where semester = ?");
            getAllCourses.setString(1, semester);
            allCourseResults = getAllCourses.executeQuery();
            
            while(allCourseResults.next())
            {
                courses.add(new CourseEntry(allCourseResults.getString("semester"), allCourseResults.getString("coursecode"), allCourseResults.getString("description"), allCourseResults.getInt("seats")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static void addCourse(CourseEntry course) {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (semester, coursecode, description, seats) values (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester) {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course where semester = (?)");
            getAllCourseCodes.setString(1, semester);
            allCourseCodeResults = getAllCourseCodes.executeQuery();
            
            while(allCourseCodeResults.next())
            {
                courseCodes.add(allCourseCodeResults.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        System.out.println(courseCodes);
        return courseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;

        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester = ? and coursecode = ?");
            getCourseSeats.setString(1, semester);
            getCourseSeats.setString(2, courseCode);
            
            courseSeats = getCourseSeats.executeQuery();
            
            while(courseSeats.next()){
                seats = courseSeats.getInt(1);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            deleteCourse = connection.prepareStatement("delete from app.course where semester = ? and coursecode = ?");
            deleteCourse.setString(1, semester);
            deleteCourse.setString(2, courseCode);
            
            deleteCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    } 
}
