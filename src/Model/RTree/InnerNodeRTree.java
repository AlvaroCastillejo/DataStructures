package Model.RTree;

import Database.Coordinate;

/*This InnerNode is one of the containers of a Node
*  ____________Node_____
* |   ____   |   ____   |
* |  | 8  |  |  |  1 |  |
* |  |____|  |  |_InnerNode
* |__________|__________|
*
*
*/
public class InnerNodeRTree {
    private Coordinate object;
    private NodeRTree son;

    /**
     * Empty constructor.
     */
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
