package Model.RTree;

import Database.Coordinate;

import java.util.Arrays;

public class NodeRTree {
    static int MAXNODES;
    private boolean isRoot;
    private boolean isLeaf;
    private Coordinate[] regions;
    private String name;
    private NodeRTree son;
    private int elementsCount;

    public NodeRTree(int dimension) {
        MAXNODES = dimension;
        regions = new Coordinate[MAXNODES];
        Arrays.fill(regions, null);
        name = "";
    }

    public boolean isFull() {
        return regions[MAXNODES-1] != null;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public Coordinate[] getRegions() {
        return regions;
    }

    public void setRegions(Coordinate[] regions) {
        this.regions = regions;
    }

    public NodeRTree getSon() {
        return son;
    }

    public void setSon(NodeRTree son) {
        this.son = son;
    }

    public int getElementsCount() {
        return elementsCount;
    }

    public void setElementsCount(int elementsCount) {
        this.elementsCount = elementsCount;
    }

    public boolean isEmpty() {
        return this.regions[0] == null;
    }

    public void addCoordinate(Coordinate coordinate, String name) {
        this.regions[lastNotNull()] = coordinate;
        this.name = name;
    }

    private int lastNotNull() {
        for (int i = 0; i < regions.length; i++) {
            Coordinate coordinate = regions[i];
            if (coordinate == null) {
                return i;
            }
        }
        return -1;
    }
}
