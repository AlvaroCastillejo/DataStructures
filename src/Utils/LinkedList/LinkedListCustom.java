package Utils.LinkedList;

import Database.Connection;
import Database.Room;
import Utils.Output;

public class LinkedListCustom<E> {
    private int error;
    private Node<E> head;
    private Node<E> previous;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedListCustom(){
        head = new Node<E>();
        previous = head;
    }

    public void add(Object element){
        Node<E> newNode = new Node<E>();
        newNode.setElement(element);
        newNode.setNext(this.previous.next());
        if(element instanceof Room){
            newNode.setName(((Room)element).getRoomName());
        }
        if(element instanceof Connection){
            newNode.setName(((Connection)element).getConnectionName());
        }
        this.previous.setNext(newNode);
        this.previous = newNode;
    }

    public void removeCurrent(){
        if(!isAtEnd()){
            Node<E> aux = this.previous.next();
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
            //System.exit(0);
            return null;
        }
        Object obj = getCurrent();
        if(obj instanceof Room){
            Room aux = (Room)obj;
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

    public void print(String color) {
        goToHead();
        while (!isAtEnd()){
            if(getCurrent() instanceof LinkedListCustom){
                LinkedListCustom<Room> aux = (LinkedListCustom<Room>)this.getCurrent();
                Output.print("--" + aux.getName() + "--", "white");
                aux.print("yellow");
            } else {
                Output.print(((Room)getCurrent()).getRoomName() + ": " + ((Room)getCurrent()).getConnectionValue(), color);
            }
            next();
        }
    }

    public static void main(String[] args) {
        LinkedListCustom<Integer> list = new LinkedListCustom<>();
        list.add(4);
        list.add(10);
        System.out.println(list.get(0));
    }
}
