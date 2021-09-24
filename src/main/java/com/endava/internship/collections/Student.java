package com.endava.internship.collections;

import com.sun.istack.internal.NotNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The class that defines the element that will be contained by your collection
 */
public class Student //TODO consider implementing any interfaces necessary for your collection
{
    private String name;
    private LocalDate dateOfBirth;
    private String details;

    public Student(@NotNull String name, @NotNull LocalDate dateOfBirth, String details) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.details = details;
    }

    public String getName() { return name; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }

    public String getDetails() { return details; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return name.equals(student.name) && dateOfBirth.equals(student.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", details='" + details + '\'' +
                '}';
    }
}
