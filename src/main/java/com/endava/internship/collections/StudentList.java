package com.endava.internship.collections;

import java.util.*;

public class StudentList implements List<Student>, Iterable<Student> {
    private int size = 0;

    Student[] studentArray;

    public StudentList(int initialCapacity) {
        if (initialCapacity >= 0) {
            this.studentArray = new Student[initialCapacity];
        } else {
            throw new IndexOutOfBoundsException("Capacity cannot be negative!");
        }
    }

    public StudentList() {
        this.studentArray = new Student[10];
    }

    private void increaseCapacity() {
        int oldCapacity = studentArray.length;
        int newCapacity = oldCapacity * 2;
        Student[] newStudentArray = new Student[newCapacity];
        for (int i = 0; i < size; i++) {
            newStudentArray[i] = studentArray[i];
        }
        studentArray = newStudentArray;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null || studentArray[i] == null) {
                if (studentArray[i] == o) {
                    return true;
                }
            } else {
                if (studentArray[i].equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Iterator<Student> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<Student> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Student next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            Student student = studentArray[cursor];
            cursor++;
            return student;
        }

        @Override
        public void remove() {
            if (size == 0) {
                throw new IllegalStateException();
            }
            for (int i = cursor - 1; i < size - 1; i++) {
                studentArray[i] = studentArray[i + 1];
            }
            size--;
        }
    }

    @Override
    public Object[] toArray() {
        Student[] newArray = new Student[size];
        for (int i = 0; i < size; i++) {
            newArray[i] = studentArray[i];
        }
        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        int newSize = Math.min(size, ts.length);
        if (newSize > 0) {
            for (int i = 0; i < newSize; i++) {
                ts[i] = (T) studentArray[i];
            }
        }
        return ts;
    }

    @Override
    public boolean add(Student student) {
        if (size >= (studentArray.length * 0.75)) {
            increaseCapacity();
        }
        studentArray[size] = student;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null || studentArray[i] == null) {
                if (o == studentArray[i]) {
                    relocateElementsToLeft(i);
                    return true;
                }
            } else {
                if (o.equals(studentArray[i])) {
                    relocateElementsToLeft(i);
                    return true;
                }
            }
        }
        return false;
    }

    private void relocateElementsToLeft(int i) {
        for (int j = i; j < size; ) {
            studentArray[i++] = studentArray[++j];
        }
        size--;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            studentArray[i] = null;
        }
        size = 0;
    }

    @Override
    public Student get(int i) {
        if (i < size) {
            return studentArray[i];
        } else {
            throw new IndexOutOfBoundsException("Incorrect index.");
        }
    }

    @Override
    public Student set(int i, Student student) {
        if (i < size) {
            studentArray[i] = student;
            return studentArray[i];
        } else {
            throw new NullPointerException("No such element.");
        }
    }

    @Override
    public void add(int i, Student student) {
        if (i < size) {
            if (size >= (studentArray.length * 0.75)) {
                increaseCapacity();
            }
            Student[] tempSt;
            tempSt = studentArray;
            studentArray[i] = student;
            for (int j = i; j <= size; j++) {
                studentArray[++j] = tempSt[j];
            }
            size++;
        } else {
            throw new NullPointerException("No such element.");
        }
    }

    @Override
    public Student remove(int i) {
        if (i < size) {
            Student tmp = studentArray[i];
            for (int j = i; j < size; ) {
                studentArray[i++] = studentArray[++j];
            }
            size--;
            return tmp;
        } else {
            throw new IndexOutOfBoundsException("Incorrect index");
        }
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (o == null || studentArray[i] == null) {
                if (o == studentArray[i]) {
                    return i;
                }
            } else {
                if (o.equals(studentArray[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = (size - 1); i >= 0; i--) {
            if (o == null || studentArray[i] == null) {
                if (o == studentArray[i]) {
                    return i;
                }
            } else {
                if (o.equals(studentArray[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    class ListIter implements ListIterator<Student> {

        private int cursor = 0;

        public ListIter() {
        }

        public ListIter(int cursor) {
            this.cursor = cursor;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public Student next() {
            Student student = studentArray[cursor];
            cursor++;
            return student;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public Student previous() {
            Student student = studentArray[cursor - 1];
            cursor--;
            return student;
        }

        @Override
        public int nextIndex() {
            if (cursor == size) {
                return -1;
            }
            return cursor + 1;
        }

        @Override
        public int previousIndex() {
            if (cursor == 0) {
                return -1;
            }
            return cursor - 1;
        }

        @Override
        public void remove() {
            for (int i = cursor - 1; i <= size - 1; i++) {
                studentArray[i] = studentArray[i + 1];
            }
            size--;
        }

        @Override
        public void set(Student student) {
            studentArray[cursor] = student;
        }

        @Override
        public void add(Student student) {
            if (size >= (studentArray.length * 0.75)) {
                increaseCapacity();
            }
            Student[] newArray = new Student[size + 1];
            for (int i = 0; i < newArray.length; i++) {
                if (i < cursor) {
                    newArray[i] = studentArray[i];
                } else if (i == cursor) {
                    newArray[i] = student;
                } else {
                    newArray[i] = studentArray[i - 1];
                }
            }
            studentArray = newArray;
            size++;
        }
    }

    @Override
    public ListIterator<Student> listIterator() {
        return new ListIter();
    }

    @Override
    public ListIterator<Student> listIterator(int i) {
        return new ListIter(i);
    }

    @Override
    public List<Student> subList(int i, int i1) {
        if (i < size && i1 <= size) {
            List<Student> newStudentList = new StudentList();
            for (int c = i; c <= i1; c++) {
                newStudentList.add(studentArray[c]);
            }
            return newStudentList;
        } else if (i > size) {
            throw new IndexOutOfBoundsException("First index is incorrect.");
        } else {
            throw new IndexOutOfBoundsException("Second index is incorrect.");
        }
    }

    @Override
    public boolean addAll(Collection<? extends Student> collection) {
        for (Student s : collection) {
            add(s);
        }
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int i, Collection<? extends Student> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        //Ignore this for homework
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < size; i++) {
            result += studentArray[i];
            result += ", ";
        }
        result = result.substring(0, result.length() - 2);
        result += "]";
        return result;
    }
}
