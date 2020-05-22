package Model.HashMap;

import Database.Player;
import Utils.JsonUtils;
import Utils.Output;
import Utils.ScannerInput;

public class MyHashMap<K, V> {
    private Entry[] table;
    private int size;

    /**
     * Constructor for the map.
     * @param capacity the capacity.
     */
    public MyHashMap(int capacity) {
        table = new Entry[capacity];
        size = capacity;
    }

    /**
     * Insertion method.
     * @param k The key that will represent the value in the map.
     * @param v The value to insert.
     */
    public void put(K k, V v){
        int hashCode = getHash(k);
        //Get the final position in the map.
        int pos = hashCode % table.length;

        //Check if the position has been initialized.
        if(table[pos] == null){
            table[pos] = new Entry();
        }

        //Then insert the element in the position.
        table[pos].insert(v);
    }

    /**
     * Search method. Shearches in the map for the given Key.
     * @param k The key to look for.
     * @return The value found if any.
     */
    public V get(K k){
        int hashCode = getHash(k);
        int pos = hashCode % table.length;

        return (V) table[pos].get(k);
    }

    /**
     * Function to get the HashCode for a given Key (String).
     * @param k The key to get the HashCode from.
     * @return The value of the hashCode.
     */
    private int getHash(K k) {
        //Making sure the key is a string, otherwise the hashcode will always be 0.
        if(k instanceof String){
            String key = (String)k;
            int hashCode = 0;
            //The ASCII values of each character in the string are added together.
            for(int i = key.length()-1; i >= 0; i--){
                hashCode += (int)key.charAt(i);
            }
            return hashCode;
        }
        return 0;
    }

    public Entry[] getTable() {
        return table;
    }

    public static void main(String[] args) {
        Player[] players = JsonUtils.getPlayers("HashMapL");

        MyHashMap<String, Player> hashMap = new MyHashMap<>(100);
        for(Player i : players){
            hashMap.put(i.getName(), i);
        }

        while (true){
            System.out.println("Name of the player: ");
            String name = ScannerInput.askString();
            if(name.equals("exit")) break;
            Player player = hashMap.get(name);
            if(player == null){
                System.out.println("Not found!\n");
                continue;
            }
            System.out.println("\t" + player.getName() +
                               "\n\t" + player.getGames() +
                               "\n\t" + player.getKDA());
        }
    }
}