package com.example.university_assignment;

public class Lector {
    private String name;
    private String department;
    private String position;
    private String head;
    private int salary;

    @Override
    public String toString() {
        return "Lector{" +
                "name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", head='" + head + '\'' +
                ", salary=" + salary +
                '}';
    }

    public Lector(){}

    public Lector(String name, String department, String position, String head, int salary) {
        this.name = name;
        this.department = department;
        this.position = position;
        this.head = head;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
