
package com.jdbc.model;
/**
 * @author mazds
 */
public class Student {
    private int studentId;
    private String studentName;
    private String studentEmail;
    private String studentPhone;
    private String studentDOB;
    private String studentAddress;
    private double studentBalance;

    public Student(int studentId, String studentName, String studentEmail, String studentPhone, String studentDOB, String studentAddress, double studentBalance) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.studentDOB = studentDOB;
        this.studentAddress = studentAddress;
        this.studentBalance = studentBalance;
    }

    public Student(String studentName, String studentEmail, String studentPhone, String studentDOB, String studentAddress, double studentBalance) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.studentPhone = studentPhone;
        this.studentDOB = studentDOB;
        this.studentAddress = studentAddress;
        this.studentBalance = studentBalance;
    }
    
    // Getters & Setters

    public int getStudentId() {
        return studentId;
    }
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentAddress() {
        return studentAddress;
    }
    public void setStudentAddress(String studentAddress) {
        this.studentAddress = studentAddress;
    }
    public double getStudentBalance() {
        return studentBalance;
    }
    public void setStudentBalance(double studentBalance) {
        this.studentBalance = studentBalance;
    }
        public String getStudentEmail() {
        return studentEmail;
    }
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    public String getStudentPhone() {
        return studentPhone;
    }
    public void setStudentPhone(String studentPhone) {
        this.studentPhone = studentPhone;
    }
    public String getStudentDOB() {
        return studentDOB;
    }
    public void setStudentDOB(String studentDOB) {
        this.studentDOB = studentDOB;
    }
    
    @Override
    public String toString() {
        return String.format(
            "Student ID: %d, Name: %s, Email: %s, Phone: %s, DOB: %s, Address: %s, Balance: %.2f",
            studentId, studentName, studentEmail, studentPhone, studentDOB, studentAddress, studentBalance
        );
    }
   
}
