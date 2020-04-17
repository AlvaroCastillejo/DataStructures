package Utils.LinkedList;

import Utils.Output;
import Utils.ScannerInput;

public class LinkedList {
    private int error;
    private Node head;
    private Node previous;

    public LinkedList(){
        head = new Node();
        previous = head;
    }

    public void add(Object element){
        Node newNode = new Node();
        newNode.setElement(element);
        newNode.setNext(this.previous.next());
        this.previous.setNext(newNode);
        this.previous = newNode;
    }

    public void removeCurrent(){
        if(!isAtEnd()){
            Node aux = this.previous.next();
            this.previous.setNext(this.previous.next().next());
        }
    }

    public void remove(int i){
        goToHead();
        for(int j = 0; j < i; j++){
            next();
        }
        if(getCurrent() == null){
            Output.print("Index out of bounds: (" + i +"/" + this.size() + "). Exiting.", "red");
            System.exit(0);
        }
        removeCurrent();
    }

    public Object getCurrent(){
        if(!isAtEnd()){
            return this.previous.next().element();
        }
        return null;
    }

    public Object get(int i){
        goToHead();
        for(int j = 0; j < i; j++){
            next();
        }
        if(getCurrent() == null){
            Output.print("Index out of bounds: (" + i +"/" + this.size() + "). Exiting.", "red");
            System.exit(0);
        }
        return getCurrent();
    }

    public int size(){
        int size = 0;
        if(isEmpty()){
            return size;
        }
        //size += 1;
        goToHead();
        for(size = 0; !isAtEnd(); size++){
            next();
        }
        return size;
    }

    public boolean isEmpty(){
        return this.head.next() == null;
    }

    public void goToHead(){
        this.previous = this.head;
    }

    public void next(){
        if(!isAtEnd()){
            this.previous = this.previous.next();
        }
    }

    private boolean isAtEnd() {
        return this.previous.next() == null;
    }

    public void fill(Object[] a){
        goToHead();
        for(Object obj : a){
            add(obj);
            next();
        }
    }

    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add(5);
        list.add(6);
        list.add(9);
        list.remove(3);
        System.out.println("List: " + list.get(0) + ", " + list.get(1));
    }
}
