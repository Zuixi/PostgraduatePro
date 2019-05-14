package com.bs.demo.myapplication.model;

/**
 * 用来显示
 */
public class coursemesBean {
    private  String stu_name;
    private  String stu_class;
    private  String course_name;
    private  int stu_id;
    private String courseId;

    public int getStu_id() {
        return stu_id;
    }

    public String getStu_name(){
        return stu_name;
    }

    public String getStu_class(){
        return stu_class;
    }

    public  String getCourse_name(){
        return  course_name;
    }

    public  String getCourseId(){return courseId;}

    public  void setId(int id){
        this.stu_id = id;
    }

    public void setStu_name(String name){
        this.stu_name = name;
    }

    public  void setStu_class(String stu_class){
        this.stu_class = stu_class;
    }

    public void setCourse_name(String course_name){
        this.course_name = course_name;
    }

    public void setCourseId(String courseId){
        this.courseId = courseId;
    }



}
