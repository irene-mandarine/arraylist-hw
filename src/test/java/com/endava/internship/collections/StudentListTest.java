package com.endava.internship.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    private void addTheSameStudentFourTimes() {
        studentList.add(st1);
        studentList.add(st1);
        studentList.add(st1);
        studentList.add(st1);
    }

    @Test
    public void testConstructor() {
        assertAll(
                () -> assertThat(studentList.size()).isEqualTo(0),
                () -> assertThat(studentList.isEmpty()).isTrue()
        );
    }

    @Test
    void testIteratorOnEmptyList() {
        Iterator<Student> iterator = studentList.iterator();

        assertThrows(IllegalStateException.class, () -> {
            iterator.remove();
        });
        assertThrows(NoSuchElementException.class, () -> {
            iterator.next();
        });
    }

    @Test
    void testAddMethod() {
        studentList.add(st1);

        assertAll(
                () -> assertThat(studentList.size()).isEqualTo(1),
                () -> assertThat(studentList.contains(st3)).isFalse(),
                () -> assertThat(studentList.contains(st1)).isTrue(),
                () -> assertThat(studentList.iterator().next()).isEqualTo(st1)
        );
    }

    @Test
    void testAddMethodResizeOption() {
        addThreeStudents();
        addThreeStudents();
        addThreeStudents();
        addThreeStudents();

        assertThat(studentList.size()).isEqualTo(12);
    }

    @Test
    void testRemoveByIndex() {
        addThreeStudents();
        studentList.remove(1);

        assertThat(studentList).hasSize(2);
        assertThat(studentList.get(1)).isNotEqualTo(st2);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            studentList.remove(22);
        });
    }

    @Test
    void testRemoveByObject() {
        addThreeStudents();
        studentList.add(st2);
        studentList.remove(st2);
        studentList.add(null);

        assertThat(studentList.size()).isEqualTo(4);
        assertThat(studentList.get(1)).isNotEqualTo(st2);
        assertThat(studentList.get(2)).isEqualTo(st2);
        assertThat(studentList.remove(null)).isTrue();
        assertThat(studentList.remove(st5)).isFalse();
    }

    @Test
    void testClear() {
        addThreeStudents();
        studentList.clear();

        assertThat(studentList.size()).isEqualTo(0);
        assertThat(studentList).doesNotContain(st4);
    }

    @Test
    void testGetMethod() {
        studentList.add(st3);

        assertThat(studentList.get(0)).isNotNull();
        assertThat(studentList.get(0)).isEqualTo(st3);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            studentList.get(6);
        });
    }

    @Test
    void testSetMethod() {
        addTheSameStudentFourTimes();
        studentList.set(2, st2);

        assertThat(studentList.get(2)).isEqualTo(st2);
        assertThrows(NullPointerException.class, () -> {
            studentList.set(8, st2);
        });
    }

    @Test
    void testAddByIndexMethod() {
        addTheSameStudentFourTimes();
        addTheSameStudentFourTimes();
        studentList.add(2, st2);

        assertThat(studentList.get(2)).isEqualTo(st2);
        assertThat(studentList.size()).isEqualTo(9);
        assertThrows(NullPointerException.class, () -> {
            studentList.add(18, st1);
        });
    }

    @Test
    void testContainsMethod() {
        studentList.add(null);
        addThreeStudents();

        assertThat(studentList.contains(null)).isTrue();
        assertThat(studentList.contains(st1)).isTrue();
        assertThat(studentList.contains(new Student("Vera", LocalDate.of(2002, 5, 6), "lazy"))).isFalse();
    }

    @Test
    void testIndexOfMethod() {
        addThreeStudents();

        assertThat(studentList.indexOf(st1)).isEqualTo(0);
        assertThat(studentList.indexOf(null)).isEqualTo(-1);
    }

    @Test
    void testLastIndexOfMethod() {
        addThreeStudents();
        addTheSameStudentFourTimes();
        addThreeStudents();

        assertThat(studentList.lastIndexOf(st1)).isEqualTo(7);
        assertThat(studentList.lastIndexOf(null)).isEqualTo(-1);
    }

    @Test
    void testToArray() {
        addThreeStudents();
        Student[] arr = {st1, st2, st4};

        assertThat(studentList.toArray()).isEqualTo(arr);
        assertThat(studentList.toArray()).isNotEmpty();
    }

    @Test
    void testToArrayWithParameter() {
        Student[] arr = new Student[5];
        addThreeStudents();
        studentList.toArray(arr);

        assertThat(arr).isNotEmpty();
        assertThat(arr.length).isEqualTo(5);
        assertThat(arr[4]).isNull();
    }

    @Test
    void testSubList() {
        addThreeStudents();
        addTheSameStudentFourTimes();
        List<Student> sub;
        sub = studentList.subList(2, 4);

        assertThat(sub.size()).isEqualTo(3);
        assertThat(sub.contains(st4)).isTrue();
    }

    @Test
    void testIterator() {
        addThreeStudents();
        Iterator<Student> iterator = studentList.iterator();

        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(st1);
    }

    @Test
    void testIteratorRemove() {
        addThreeStudents();
        Iterator<Student> iterator = studentList.iterator();
        iterator.next();
        iterator.remove();

        assertThat(studentList).doesNotContain(st1);
        assertThat(studentList).hasSize(2);
    }

    @Test
    void testAddAll() {
        LinkedList<Student> temp = new LinkedList<>();
        temp.add(st5);
        temp.add(st3);
        temp.add(new Student("Nina", LocalDate.of(1999, 12, 6), "A-girl"));
        addThreeStudents();
        addTheSameStudentFourTimes();
        studentList.addAll(temp);

        assertThat(studentList.size()).isEqualTo(10);
        assertThat(studentList).contains(st5);
    }

    @Test
    void testListIterator() {
        addThreeStudents();
        ListIterator<Student> listIterator = studentList.listIterator();

        assertThat(listIterator.hasNext()).isTrue();
        assertThat(listIterator.next()).isEqualTo(st1);
        assertThat(listIterator.nextIndex()).isEqualTo(2);
    }

    @Test
    void testListIteratorWithConstructor() {
        addThreeStudents();
        ListIterator<Student> listIterator = studentList.listIterator(2);

        assertThat(listIterator.hasPrevious()).isTrue();
        assertThat(listIterator.previous()).isEqualTo(st2);
        assertThat(listIterator.nextIndex()).isEqualTo(2);
        assertThat(listIterator.previousIndex()).isEqualTo(0);
    }

    @Test
    void testListIteratorRemoveAddSetMethods() {
        addTheSameStudentFourTimes();
        ListIterator<Student> listIterator = studentList.listIterator();
        listIterator.next();
        listIterator.remove();
        listIterator.add(st1);
        listIterator.set(st5);

        assertThat(listIterator).hasSize(3);
    }
}
