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
public class StudentEntry {
    private String studentID;
    private String firstName;
    private String lastName;
    
    public StudentEntry(String studentID, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public String getStudentID() {
        return this.studentID;
    }
    
}
