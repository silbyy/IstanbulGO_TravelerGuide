package datastructures;
import java.util.Iterator;

public class GenericLinkedList<T> implements Iterable<T> {
    private Node<T> head;
    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) { this.data = data; }
    }
    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) { head = newNode; return; }
        Node<T> current = head;
        while (current.next != null) current = current.next;
        current.next = newNode;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;
            public boolean hasNext() { return current != null; }
            public T next() { T d = current.data; current = current.next; return d; }
        };
    }
}