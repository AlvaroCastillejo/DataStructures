package Model.RTree;

import Database.Coordinate;
import Database.StoreObject;
import Utils.JsonUtils;
import Utils.List;

public class RTree {
    private int dimension;
    private NodeRTree root;

    public RTree(int dimension) {
        this.dimension = dimension;
        root = new NodeRTree(dimension);
    }

    private void insert(RTree t, Coordinate coordinate) {
        //Primero mirar si el root está vacío, significará que el abrol aún no tiene nada
        if(root.isEmpty()){
            root.setRoot(true);
            root.setLeaf(true);
            root.addCoordinate(coordinate, "R1");
        }
    }

    private void insertInto(NodeRTree s, Coordinate coordinate) {

    }

    private void split(NodeRTree s, int i, NodeRTree current){

    }

    private void visualize() {

    }

    public static void main(String[] args) {
        Coordinate[] coordinates = JsonUtils.getCoordinates("dataset_MapS");

        RTree rTree = new RTree(2);
        for(Coordinate coordinate : coordinates){
            rTree.insert(rTree, coordinate);
        }

        rTree.visualize();
    }


}

