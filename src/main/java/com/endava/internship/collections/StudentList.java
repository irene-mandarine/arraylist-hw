package com.endava.internship.collections;

import java.util.*;
import java.util.stream.IntStream;

public class StudentList<E> implements List<E>, Iterable<E> {
    private int size = 0;

    E[] studentArray;

    @SuppressWarnings("unchecked")
    public StudentList(int initialCapacity) {
        if (initialCapacity >= 0) {
            this.studentArray = (E[]) new Object[initialCapacity];
        } else {
            throw new IndexOutOfBoundsException("Capacity cannot be negative!");
        }
    }

    @SuppressWarnings("unchecked")
    public StudentList() {
        this.studentArray = (E[]) new Object[10];
    }

    @SuppressWarnings("unchecked")
    private void increaseCapacity() {
        int oldCapacity = studentArray.length;
        int newCapacity = oldCapacity * 2;
        E[] newStudentArray = (E[]) new Object[newCapacity];
        IntStream.range(0, size).forEach(i -> newStudentArray[i] = studentArray[i]);
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
        return Arrays.stream(studentArray).anyMatch(e -> Objects.equals(o, e));
    }

    @Override
    public Iterator<E> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<E> {

        int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public E next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            E student = studentArray[cursor];
            cursor++;
            return student;
        }

        @Override
        public void remove() {
            if (size == 0) {
                throw new IllegalStateException();
            }
            IntStream.range(0, size - 1).forEach(i -> studentArray[i] = studentArray[i + 1]);
            size--;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] newArray = new Object[size];
        IntStream.range(0, size).forEach(i -> newArray[i] = studentArray[i]);
        return newArray;
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        int newSize = Math.min(size, ts.length);
        if (newSize > 0) {
            IntStream.range(0, newSize).forEach(i -> ts[i] = (T) studentArray[i]);
        }
        return ts;
    }

    @Override
    public boolean add(E student) {
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
        IntStream.range(0, size).forEach(i -> studentArray[i] = null);
        size = 0;
    }

    @Override
    public E get(int i) {
        if (i < size) {
            return studentArray[i];
        } else {
            throw new IndexOutOfBoundsException("Incorrect index.");
        }
    }

    @Override
    public E set(int i, E student) {
        if (i < size) {
            studentArray[i] = student;
            return studentArray[i];
        } else {
            throw new NullPointerException("No such element.");
        }
    }

    @Override
    public void add(int i, E student) {
        if (i < size) {
            if (size >= (studentArray.length * 0.75)) {
                increaseCapacity();
            }
            E[] tempSt;
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
    public E remove(int i) {
        if (i < size) {
            E tmp = studentArray[i];
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

    class ListIter implements ListIterator<E> {

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
        public E next() {
            E student = studentArray[cursor];
            cursor++;
            return student;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        public E previous() {
            E student = studentArray[cursor - 1];
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
            IntStream.range(0, cursor - 1).forEach(i -> studentArray[i] = studentArray[i + 1]);
            size--;
        }

        @Override
        public void set(E student) {
            studentArray[cursor] = student;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void add(E student) {
            if (size >= (studentArray.length * 0.75)) {
                increaseCapacity();
            }
            E[] newArray = (E[]) new Object[size + 1];
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
    public ListIterator<E> listIterator() {
        return new ListIter();
    }

    @Override
    public ListIterator<E> listIterator(int i) {
        return new ListIter(i);
    }

    @Override
    public List<E> subList(int i, int i1) {
        if (i < size && i1 <= size) {
            List<E> newStudentList = new StudentList();
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
    public boolean addAll(Collection<? extends E> collection) {
        for (E s : collection) {
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
    public boolean addAll(int i, Collection<? extends E> collection) {
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