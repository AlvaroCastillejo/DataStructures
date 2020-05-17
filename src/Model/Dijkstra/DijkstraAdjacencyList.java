package Model.Dijkstra;

import Database.Room;
import Utils.LinkedList.LinkedListCustom;
import Utils.Logic;

import java.util.Arrays;

public class DijkstraAdjacencyList {
    public static Room[] dijkstra(LinkedListCustom<LinkedListCustom<Room>> adjacencyMatrix, Room initialRoom, Room finalRoom, Room[] rooms) {
        double[] d = new double[adjacencyMatrix.size()];
        Room[] c = new Room[adjacencyMatrix.size()];

        Arrays.fill(d, Double.MAX_VALUE);
        d[initialRoom.getId()] = 0;
        Logic.fillC(c, rooms);

        Room current = Logic.findMinimumWeightRoom(rooms, d);
        for(int i = 0; i < adjacencyMatrix.size() && !current.getId().equals(finalRoom.getId()); i++){

            Room[] adjacents = Logic.getAdjacentsFromAdjacencyList(current, adjacencyMatrix, rooms);
            int j = 0;
            for(Room adj : adjacents){
                if(!adj.isVisited()){
                    LinkedListCustom<Room> list = (LinkedListCustom<Room>) adjacencyMatrix.get(current.getId());
                    double new_d = d[current.getId()] + ((Room)list.get(j)).getConnectionValue();                  //Falta hacer la media.
                    if(d[adj.getId()] > new_d){
                        d[adj.getId()] = new_d;
                        c = Logic.updateC(c, adj, current);
                    }
                }
                j++;
            }
            current.setVisited(true);
            Logic.setRoomVisited(current, rooms);
            current = Logic.findMinimumWeightRoom(rooms, d);
        }

        return Logic.getSolution(c, rooms, initialRoom, finalRoom);
    }
}
