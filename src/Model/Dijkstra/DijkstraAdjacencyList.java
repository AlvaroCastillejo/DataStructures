package Model.Dijkstra;

import Database.Room;
import Utils.LinkedList.LinkedListCustom;
import Utils.Logic;

import java.util.Arrays;

public class DijkstraAdjacencyList {
    public static Room[] dijkstra(LinkedListCustom<LinkedListCustom<Room>> adjacencyMatrix, Room initialRoom, Room finalRoom, Room[] rooms) {
        //This parameter contain the weight of each room.
        double[] d = new double[adjacencyMatrix.size()];
        //This parameter contain the path to finalRoom.
        Room[] c = new Room[adjacencyMatrix.size()];

        //Initializing the parameters.
        Arrays.fill(d, Double.MAX_VALUE);
        d[initialRoom.getId()] = 0;
        Logic.fillC(c, rooms);

        //This parameter is the room to start with.
        Room current = Logic.findMinimumWeightRoom(rooms, d);
        //Loop that works until current is the finalRoom.
        for(int i = 0; i < adjacencyMatrix.size() && !current.getId().equals(finalRoom.getId()); i++){
            //Parameter that contains every room adjacent to current.
            Room[] adjacents = Logic.getAdjacentsFromAdjacencyList(current, adjacencyMatrix, rooms);
            int j = 0;
            for(Room adj : adjacents){
                if(!adj.isVisited()){
                    LinkedListCustom<Room> list = (LinkedListCustom<Room>) adjacencyMatrix.get(current.getId());
                    //Get the value of the connection through adjacencyMatrix.
                    double new_d = d[current.getId()] + ((Room)list.get(j)).getConnectionValue();                  //Falta hacer la media.
                    //If the new weight is lower than the original, switch it.
                    if(d[adj.getId()] > new_d){
                        d[adj.getId()] = new_d;
                        //Add the new room to the path
                        c = Logic.updateC(c, adj, current);
                    }
                }
                j++;
            }
            //Set visited the current room
            current.setVisited(true);
            Logic.setRoomVisited(current, rooms);
            //This parameter is the room to start with the new iteration
            current = Logic.findMinimumWeightRoom(rooms, d);
        }
        //Return the full path that is inside of c array
        return Logic.getSolution(c, rooms, initialRoom, finalRoom);
    }
}
