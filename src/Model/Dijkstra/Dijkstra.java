package Model.Dijkstra;

import Database.Room;
import Utils.Logic;

import java.util.Arrays;

public class Dijkstra {
    public static Room[] dijkstra(double[][] adjacencyMatrix, Room initialRoom, Room finalRoom, Room[] rooms) {
        double[] d = new double[adjacencyMatrix.length];
        Room[] c = new Room[adjacencyMatrix.length];

        Arrays.fill(d, Double.MAX_VALUE);
        d[initialRoom.getId()] = 0;
        Logic.fillC(c, rooms);

        Room current = Logic.findMinimumWeightRoom(rooms, d);
        for(int i = 0; i < adjacencyMatrix.length && !current.getId().equals(finalRoom.getId()); i++){

            Room[] adjacents = Logic.getAdjacents(current, adjacencyMatrix, rooms);
            for(Room adj : adjacents){
                if(!adj.isVisited()){
                    double new_d = d[current.getId()] + adjacencyMatrix[current.getId()][adj.getId()];                  //Falta hacer la media.
                    if(d[adj.getId()] > new_d){
                        d[adj.getId()] = new_d;
                        c = Logic.updateC(c, adj, current);
                    }
                }
            }
            current.setVisited(true);
            Logic.setRoomVisited(current, rooms);
            current = Logic.findMinimumWeightRoom(rooms, d);
        }
        return Logic.getSolution(c, rooms, initialRoom, finalRoom);
    }
}
