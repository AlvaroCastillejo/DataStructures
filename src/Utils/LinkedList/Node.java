package Utils.LinkedList;

public class Node {
    private Object element;
    private Node next;

    public Node() {
        this.next = null;
    }

    public Object element() {
        return element;
    }

    public void setElement(Object element) {
        this.element = element;
    }

    public Node next() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
