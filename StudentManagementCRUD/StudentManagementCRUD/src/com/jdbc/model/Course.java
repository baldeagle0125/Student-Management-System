package com.jdbc.model;

public class Course {
    
    // Variables representing course data
    private int courseId;
    private String courseName;
    private String courseCategory;
    private int courseCredits;
    private String courseLevel;
    private String courseDelivery;
    private String courseDuration;
    
    
    // Constructor with all course attributes
    public Course(int courseId, String courseName, String courseCategory, int courseCredits, 
                  String courseLevel, String courseDelivery, String courseDuration) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCategory = courseCategory;
        this.courseCredits = courseCredits;
        this.courseLevel = courseLevel;
        this.courseDelivery = courseDelivery;
        this.courseDuration = courseDuration;
    }

    // Constructor for creating a course without an ID (for new courses)
    public Course(String courseName, String courseCategory, int courseCredits, 
                  String courseLevel, String courseDelivery, String courseDuration) {
        this.courseName = courseName;
        this.courseCategory = courseCategory;
        this.courseCredits = courseCredits;
        this.courseLevel = courseLevel;
        this.courseDelivery = courseDelivery;
        this.courseDuration = courseDuration;
    }
    
    // Getters and Setters for the course attributes
    public int getCourseId() {
        return courseId;
    }
    
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCategory() {
        return courseCategory;
    }
    
    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
    }

    public int getCourseCredits() {
        return courseCredits;
    }
    
    public void setCourseCredits(int courseCredits) {
        this.courseCredits = courseCredits;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getCourseDelivery() {
        return courseDelivery;
    }
    
    public void setCourseDelivery(String courseDelivery) {
        this.courseDelivery = courseDelivery;
    }

    public String getCourseDuration() {
        return courseDuration;
    }
    
    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }
}
