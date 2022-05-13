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
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addSchedule;
    private static PreparedStatement getScheduleBS;
    private static PreparedStatement getScheduleSC;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement getWaitlistedStudents;
    private static PreparedStatement updateStudentEntry;
    private static PreparedStatement getScheduleBStudent;
    private static PreparedStatement dropScheduleByCourse;
    
    private static ResultSet resultSBS;
    private static ResultSet resultSSC;
    private static ResultSet waitlist;
    private static ResultSet getScheduleByStudentResultSet;

    
    public static void addScheduleEntry(ScheduleEntry entry) 
    {
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getCourseCode());
            addSchedule.setString(3, entry.getStudentID());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<>();
        try
        {
            getScheduleBS = connection.prepareStatement("select * from app.schedule where semester = ? and studentid = ?");
            getScheduleBS.setString(1, semester);
            getScheduleBS.setString(2, studentID);
            resultSBS = getScheduleBS.executeQuery();
            
            while(resultSBS.next())
            {
                schedule.add(new ScheduleEntry(resultSBS.getString("semester"),resultSBS.getString("courseCode"),resultSBS.getString("studentid"),resultSBS.getString("status")));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode) 
    {
        connection = DBConnection.getConnection();
        int count = 0;
        try
        {
            getScheduleSC = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            getScheduleSC.setString(1, currentSemester);
            getScheduleSC.setString(2, courseCode);
            resultSSC = getScheduleSC.executeQuery();
             
            while(resultSSC.next()) {
                count = resultSSC.getInt(1);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
    
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> retList = new ArrayList<ScheduleEntry>();
        try
        {
            getScheduleBStudent = connection.prepareStatement("select studentid, timestamp from app.schedule where semester = ? and courseCode = ? and status = ?");
            getScheduleBStudent.setString(1, semester);
            getScheduleBStudent.setString(2, courseCode);
            getScheduleBStudent.setString(3, "s");
            getScheduleByStudentResultSet = getScheduleBStudent.executeQuery();
            
            while(getScheduleByStudentResultSet.next())
            {
                retList.add(new ScheduleEntry(semester, courseCode, getScheduleByStudentResultSet.getString(1), "s"));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return retList;
    };
    
    public static void dropStudentScheduleByCourse(String semester, String id, String course) {
        connection = DBConnection.getConnection();
        
        try
        {
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and studentid = ? and coursecode = ?");
            
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, id);
            dropStudentScheduleByCourse.setString(3, course);
            
            dropStudentScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    };
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String course) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> results = new ArrayList<>();
        
        try
        {
            getWaitlistedStudents = connection.prepareStatement("select * from app.schedule where semester = ? and courseCode = ? and status = ?");
            getWaitlistedStudents.setString(1, semester);
            getWaitlistedStudents.setString(2, course);
            getWaitlistedStudents.setString(3, "w");
            waitlist = getWaitlistedStudents.executeQuery();
            
            while(waitlist.next())
            {
                results.add(new ScheduleEntry(semester, course, waitlist.getString("studentid"), "w"));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
    };
    
    public static void dropScheduleByCourse(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        
        try
        {
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    };
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry)  {
        connection = DBConnection.getConnection();
        
        try
        {
            updateStudentEntry = connection.prepareStatement("update app.schedule set status = 's' where semester = ? and studentid = ? and coursecode = ?");
            updateStudentEntry.setString(1, semester);
            updateStudentEntry.setString(2, entry.getStudentID());
            updateStudentEntry.setString(3, entry.getCourseCode());
            updateStudentEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    };
}
