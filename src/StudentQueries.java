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
public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static PreparedStatement getSingleStudent;
    
    private static ResultSet resultSet;
    private static ResultSet singleStudentSet;
    private static ResultSet getStudentSet;
    private static ResultSet singleStudentResult;
    
    public static void addStudent(StudentEntry student) 
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentid, firstname, lastname) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents() 
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> allStudents = new ArrayList<>();
        try
        {
            getAllStudents = connection.prepareStatement("select * from app.student order by studentid");
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                allStudents.add(new StudentEntry(resultSet.getString("studentid"),resultSet.getString("firstname"),resultSet.getString("lastname")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allStudents;
        
    }
    
    public static StudentEntry getStudent(String studentID) 
    {
        connection = DBConnection.getConnection();
        
        try
        {
            getStudent = connection.prepareStatement("select studentid, firstname, lastname from app.student where studentid = ?");
            getStudent.setString(1, studentID);
            
            getStudentSet = getStudent.executeQuery();
            
            getStudentSet.next();
            
            return new StudentEntry(getStudentSet.getString(1), getStudentSet.getString(2), getStudentSet.getString(3));
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            return null;
        }
        
    }
    
    //helper method to get id of student
    public static String getStudentID(String fn, String ln) 
    {
        connection = DBConnection.getConnection();
        ArrayList<String> oneStudent = new ArrayList<>();
        try
        {
            getAllStudents = connection.prepareStatement("select * from app.student where firstname, lastname = (?, ?)");
            addStudent.setString(1, fn);
            addStudent.setString(2, ln);
            System.out.println(getAllStudents.executeQuery());
            
            singleStudentSet = getAllStudents.executeQuery();
            
            while(singleStudentSet.next()) {
                oneStudent.add((singleStudentSet.getString("studentid")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return oneStudent.get(0);
        
    }
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentid = ?");
            
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getSingleStudent(String first, String last){
        connection = DBConnection.getConnection();
        
        ArrayList<StudentEntry> student = new ArrayList<>();
        
        try
        {
            getSingleStudent = connection.prepareStatement("select * from app.student where firstname = ? and lastname = ?");
            getSingleStudent.setString(1, first);
            getSingleStudent.setString(2, last);

            singleStudentResult = getSingleStudent.executeQuery();
            
            while(singleStudentResult.next())
            {
                student.add(new StudentEntry(singleStudentResult.getString("studentid"),singleStudentResult.getString("firstname"),singleStudentResult.getString("lastname")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
    
    }
    
    
}
