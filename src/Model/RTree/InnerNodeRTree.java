package Model.RTree;

import Database.Coordinate;

public class InnerNodeRTree {
    private Coordinate object;
    private NodeRTree son;

    public InnerNodeRTree(){
        son = null;
        object = null;
    }

    public InnerNodeRTree(Coordinate coordinate) {
        this.object = coordinate;
        son = null;
    }

    public Coordinate getObject() {
        return object;
    }

    public void setObject(Coordinate object) {
        this.object = object;
    }

    public NodeRTree getSon() {
        return son;
    }

    public void setSon(NodeRTree son) {
        this.son = son;
    }

    public boolean isLeaf(){
        return son == null;
    }
}
