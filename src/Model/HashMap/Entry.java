package Model.HashMap;

import Database.Player;
import Utils.LinkedList.LinkedListCustom;

public class Entry<V> {
    private LinkedListCustom<V> entries;

    public Entry() {
        entries = new LinkedListCustom<>();
    }

    public void insert(V v) {
        entries.add(v);
    }

    public <V, K> V get(K k) {
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
