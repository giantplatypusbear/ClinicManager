import java.util.Iterator;
import java.util.NoSuchElementException;

public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size;

    // Default constructor with an initial capacity of 4.
    public List() {
        objects = (E[]) new Object[4];
        size = 0;
    }

    private int find(E e) {
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i;  // Return the index if the element is found
            }
        }
        return -1;
    }

    private void grow() {
        E[] newObjects = (E[]) new Object[objects.length + 4];
        for (int i = 0; i < objects.length; i++) {
            newObjects[i] = objects[i];
        }
        objects = newObjects;
    }

    public boolean contains(E e) {
        return find(e) != -1;
    }

    public void add(E e) {
        if (size == objects.length) {
            grow();
        }
        objects[size++] = e;
    }

    public void remove(E e) {
        int index = find(e);
        if (index != -1) {
            for (int i = index; i < size - 1; i++) {
                objects[i] = objects[i + 1];
            }
            objects[--size] = null;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Iterator<E> iterator() {
        return new ListIterator();
    }

    public E get(int index) {
        return objects[index];
    } // Return the object at the index

    public void set(int index, E e) {
        objects[index] = e;
    } // Put object e at the index

    public int indexOf(E e) {
        return find(e);
    } // Return the index of the object or return -1


    private class ListIterator implements Iterator<E> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list.");
            }
            E element = objects[index];
            index = (index + 1) % size; // Reset index to 0 when it reaches the end
            return element;
        } // Return the next object in the list
    }

}