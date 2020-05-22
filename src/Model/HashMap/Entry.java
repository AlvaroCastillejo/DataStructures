package Model.HashMap;

import Database.Player;
import Utils.LinkedList.LinkedListCustom;

public class Entry<V> {
    private LinkedListCustom<V> entries;

    /**
     * Empty constructor. Creates a linkedList that will store all the elements that share the same HashCode.
     */
    public Entry() {
        entries = new LinkedListCustom<>();
    }

    /**
     * Insert an element to this entry.
     * @param v The element that will be added.
     */
    public void insert(V v) {
        entries.add(v);
    }

    /**
     * Searches in the list of the entry for a given key.
     * @param k The given key.
     * @param <V> The type of the value.
     * @param <K> The type of the key.
     * @return The value found.
     */
    public <V, K> V get(K k) {
        //If the list has only one element, this must be the desired one.
        if(entries.size() == 1){
            return (V) entries.get(0);
        }
        for(int i = 0; i < entries.size(); i++){
            if(entries.get(i) instanceof Player){
                Player toReturn = (Player) entries.get(i);
                if(toReturn.getName().equals(k)){
                    return (V) toReturn;
                }
            }
        }
        return null;
    }

    public int getListSize() {
        return entries.size();
    }
}