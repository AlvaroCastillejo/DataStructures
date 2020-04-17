package Utils.LinkedList;

public class Node<E> {
    private Object element;
    private Node<E> next;

    public Node() {
        this.next = null;
    }

    public Object element() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public Node<E> next() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }
}
