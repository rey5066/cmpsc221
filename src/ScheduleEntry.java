/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author acv
 */
public class ScheduleEntry {
    private String semester;
    private String courseCode;
    private String studentID;
    private String status;
    private Timestamp timestamp;
    
    public ScheduleEntry(String semester, String courseCode, String studentID, String status) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.studentID = studentID;
        this.status = status;
        this.timestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
    }
    
    public String getSemester() {
        return this.semester;
    }
    
    public String getCourseCode() {
        return this.courseCode;
    }
    
    public String getStudentID() {
        return this.studentID;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public Timestamp getTimestamp() {
        return this.timestamp;
    }
}
