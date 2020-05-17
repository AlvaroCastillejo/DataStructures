package Model.HashMap;

import Database.Player;
import Utils.JsonUtils;
import Utils.Output;
import Utils.ScannerInput;

public class MyHashMap<K, V> {
    private Entry[] table;
    private int size;

    public MyHashMap(int capacity) {
        table = new Entry[capacity];
        size = capacity;
    }

    public void put(K k, V v){
        int hashCode = getHash(k);
        int pos = hashCode % table.length;

        if(table[pos] == null){
            table[pos] = new Entry();
        }

        table[pos].insert(v);
    }

    public V get(K k){
        int hashCode = getHash(k);
        int pos = hashCode % table.length;

        return (V) table[pos].get(k);
    }

    private int getHash(K k) {
        if(k instanceof String){
            String key = (String)k;
            int hashCode = 0;
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
