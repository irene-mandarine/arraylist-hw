package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StudentListTest {

    private StudentList studentList;
    Student st1 = new Student("Andrei", LocalDate.of(1965, 12, 12), "student");
    Student st2 = new Student("Vitali", LocalDate.of(1975, 11, 19), "student");
    Student st3 = new Student("Cristina", LocalDate.of(1968, 5, 22), "student");
    Student st4 = new Student("Diana", LocalDate.of(1995, 3, 2), "student");
    Student st5 = new Student("Stas", LocalDate.of(1995, 2, 1), "student");

    @BeforeEach
    void CreateStudentList() {
        studentList = new StudentList();
    }

    void addThreeStudents() {
        studentList.add(st1);
        studentList.add(st2);
        studentList.add(st4);
    }

    @Test
    public void ShouldBeEmptyWhenIsCreated() {
        assertThat(studentList.size()).isEqualTo(0);
        assertThat(studentList.isEmpty()).isTrue();
    }

    @Test
    void ShouldNotBeEmptyAfterAddingObjects() {
        studentList.add(st1);

        assertThat(studentList.size()).isEqualTo(1);
    }

    @Test
    void ShouldContainAddedObject() {
        addThreeStudents();

        assertThat(studentList.contains(st3)).isFalse();
        assertThat(studentList.contains(st1)).isTrue();
    }

    @Test
    void CorrectSizeAfterRemovingOneStudentByIndex() {
        addThreeStudents();
        studentList.remove(1);

        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    void CorrectSizeAfterRemovingOneStudentByObject() {
        addThreeStudents();
        studentList.remove(st2);

        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    void CorrectSizeAfterRemovingNonExistingObject() {
        addThreeStudents();
        studentList.add(st3);
        studentList.add(st4);
        studentList.remove(null);

        assertThat(studentList.size()).isEqualTo(5);
    }

    @Test
    void CheckingSizeAfterClearing() {
        addThreeStudents();
        studentList.clear();

        assertThat(studentList.size()).isEqualTo(0);
    }

    @Test
    void CheckTheGetMethod() {
        studentList.add(st3);

        assertThat(studentList.get(0)).isNotNull();
        assertThat(studentList.get(0)).isEqualTo(st3);

    }

    @Test
    void GetMethodThrowsException() {
        studentList.add(st3);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            studentList.get(6);
        });
    }

    @Test
    void CheckTAddMethodWithCapacityOverload_SizeShouldBeMoreThanTen() {
        addThreeStudents();
        addThreeStudents();
        addThreeStudents();
        addThreeStudents();

        assertThat(studentList.size()).isEqualTo(12);
    }

    @Test
    void CheckingCorrectUsageOfSetMethod() {
        addTheSameStudentFourTimes();
        studentList.set(2, st2);

        assertThat(studentList.get(2)).isEqualTo(st2);
    }

    @Test
    void CheckingIfGetMethodThrowsException() {
        addTheSameStudentFourTimes();

        assertThrows(NullPointerException.class, () -> {
            studentList.set(8, st2);
        });
    }

    @Test
    void AddInACertainPosition_CorrectObjectInCorrectPlace() {
        addTheSameStudentFourTimes();
        studentList.add(2, st2);

        assertThat(studentList.get(2)).isEqualTo(st2);
    }

    @Test
    void AddInACertainPosition_CorrectSize() {
        addTheSameStudentFourTimes();
        studentList.add(2, st2);

        assertThat(studentList.size()).isEqualTo(5);
    }

    private void addTheSameStudentFourTimes() {
        studentList.add(st1);
        studentList.add(st1);
        studentList.add(st1);
        studentList.add(st1);
    }

    @Test
    void testIndexOfMethodWithNull() {
        addTheSameStudentFourTimes();
        studentList.add(null);
        addThreeStudents();

        assertThat(studentList).contains(st2);
    }
}
