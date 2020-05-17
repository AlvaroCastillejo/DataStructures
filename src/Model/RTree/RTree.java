package Model.RTree;

import Database.Coordinate;
import Utils.JsonUtils;
import Utils.Logic;
import Utils.Output;
import Utils.ScannerInput;

public class RTree {
    private static int DEGREE = 0;
    private NodeRTree root;

    public RTree(int degree) {
        DEGREE = degree;
        root = new NodeRTree(DEGREE);
    }

    public RTree(NodeRTree son, int degree) {
        DEGREE = degree;
        root = son;
    }

    private void insert(RTree tree, Coordinate object) {
        if(tree.root.isLeaf()){
            tree.root.addObject(object);
        } else {
            double minArea = Double.MAX_VALUE;
            int minAreaIndex = 0;
            for(int i = 0; i < DEGREE; i++){
                double aux = areaAddingCoordinate(tree.root.getInnerNodes()[i].getSon(), object);
                if(aux < minArea){
                    minArea = aux;
                    minAreaIndex = i;
                }
            }
            insert(new RTree(tree.root.getInnerNodes()[minAreaIndex].getSon(), DEGREE), object);
            tree.root.recalculateAfterSplit();
        }
    }

    private Coordinate searchFor(int x, int y, RTree tree) {
        if(tree.root.isLeaf()){
            for(InnerNodeRTree i : tree.root.getInnerNodes()){
                if(i.getObject().getX1() <= x && i.getObject().getX2() >= x && i.getObject().getY1() <= y && i.getObject().getY2() >= y){
                    return i.getObject();
                }
            }
            return null;
        } else {
            InnerNodeRTree[] innerNodes = tree.root.getInnerNodes();
            for (int i1 = 0; i1 < innerNodes.length; i1++) {
                InnerNodeRTree i = innerNodes[i1];
                if(i.getSon() == null) continue;
                if (i.getSon().getRectangle().getX1() <= x && i.getSon().getRectangle().getX2() >= x && i.getSon().getRectangle().getY1() <= y && i.getSon().getRectangle().getY2() >= y) {
                    return searchFor(x, y, new RTree(tree.root.getInnerNodes()[i1].getSon(), DEGREE));
                }
            }
            return null;
        }
    }

    private void delete(int x, int y, RTree tree) {
        if(tree.root.isLeaf()){
            int index = 0;
            InnerNodeRTree[] innerNodes = tree.root.getInnerNodes();
            for (int i1 = 0; i1 < innerNodes.length; i1++) {
                InnerNodeRTree i = innerNodes[i1];
                if(i == null) continue;
                if (i.getObject().getX1() <= x && i.getObject().getX2() >= x && i.getObject().getY1() <= y && i.getObject().getY2() >= y) {
                    index = i1;
                }
            }
            if(index == 0){
                innerNodes[0] = innerNodes[1];
                innerNodes[1] = null;
            } else {
                innerNodes[index] = null;
            }

            tree.root.recalculate();
            //Output.print("Not found object to delete.", "red");
        } else {
            InnerNodeRTree[] innerNodes = tree.root.getInnerNodes();
            for (int i1 = 0; i1 < innerNodes.length; i1++) {
                InnerNodeRTree i = innerNodes[i1];
                if(i.getSon() == null) continue;
                if (i.getSon().getRectangle().getX1() <= x && i.getSon().getRectangle().getX2() >= x && i.getSon().getRectangle().getY1() <= y && i.getSon().getRectangle().getY2() >= y) {
                    delete(x, y, new RTree(tree.root.getInnerNodes()[i1].getSon(), DEGREE));
                }
            }
            Output.print("nose", "yellow");
        }
    }

    private double areaAddingCoordinate(NodeRTree son, Coordinate object) {
        Coordinate original = new Coordinate(son.getRectangle().getX1(), son.getRectangle().getY1(), son.getRectangle().getX2(), son.getRectangle().getY2());
        double areaOriginal = original.getArea();
        if(object.getX1() < original.getX1()) {
            original.setX1(object.getX1());
        }
        if(object.getX2() > original.getX2()) {
            original.setX2(object.getX2());
        }
        if(object.getY1() < original.getY1()){
            original.setY1(object.getY1());
        }
        if(object.getY2() > original.getY2()){
            original.setY2(object.getY2());
        }
        return original.getArea()-areaOriginal;
    }

    private void visualize(NodeRTree node, int level) {
        if(node.isLeaf()){
            try{
                if(node.getInnerNodes()[1].getObject().getId() == 6){
                    System.out.println("");
                }
            } catch (NullPointerException ignore){
                System.out.println("");
            }
            Output.print(" __ ", "green");

            String levelTag = "";
            for(int i = 0; i < level; i++){
                levelTag = levelTag.concat("\t");
            }
            levelTag = levelTag.concat(String.valueOf(level));
            //Output.print(levelTag, "yellow");
            for(InnerNodeRTree inode : node.getInnerNodes()){
                if(inode == null) continue;
                levelTag = "";
                for(int i = 0; i < level; i++){
                    //levelTag = levelTag.concat("\t");
                }
                levelTag = levelTag.concat(String.valueOf(inode.getObject().getId()));
                Output.print("|" + levelTag + "|", "green");
            }
            Output.print(" ¯¯ ", "green");
            return;
        } else {
            String levelTag = "";
            for(int i = 0; i < level; i++){
                levelTag = levelTag.concat("\t");
            }
            levelTag = levelTag.concat(String.valueOf(level));
            //Output.print(levelTag, "yellow");
            visualize(node.getInnerNodes()[0].getSon(),level+1);
            level--;
            visualize(node.getInnerNodes()[1].getSon(), level+1);
        }
    }

    public static void main(String[] args) {
        Coordinate[] coordinates = JsonUtils.getCoordinates("dataset_MapCustom_2");
        Logic.redefineCoordinates(coordinates);

        RTree rTree = new RTree(2);
        for(Coordinate coordinate : coordinates){
            rTree.insert(rTree, coordinate);
        }

        Coordinate coord = null;
        outer:
        while (true){
            Output.print("------------------------\n\t1- Search\n\t2- Delete\n\t3- Plot tree\nOption:", "white");
            int option = ScannerInput.askInteger();
            switch (option){
                case 1:
                    boolean exit = false;
                    while(!exit){
                        Output.print("Search\n\t1- by Coordinates\n\t2- by Id\n\t3- exit\nOption:", "white");
                        int optionSub = ScannerInput.askInteger();
                        switch (optionSub){
                            case 1:
                                Output.print("\t\tsearch for x: ", "white");
                                int x = ScannerInput.askInteger();
                                Output.print("search for y: ", "white");
                                int y = ScannerInput.askInteger();
                                Coordinate hit = rTree.searchFor(x, y, rTree);
                                if(hit!=null) System.out.println("HIT: " + hit.getId());
                                else System.out.println("\t\tMISS");
                                break;
                            case 2:
                                Output.print("\t\tsearch for id: ", "white");
                                int askFor = ScannerInput.askInteger();
                                coord = getCoord(askFor, coordinates);
                                if(coord == null) continue;
                                hit = rTree.searchFor(coord.getMiddleX(), coord.getMiddleY(), rTree);
                                if(hit!=null) System.out.println("HIT: " + hit.getId());
                                else System.out.println("\t\tMISS");
                                break;
                            case 3:
                                exit = true;
                                break;
                        }
                    }
                    break;
                case 2:
                    Output.print("delete: ", "white");
                    int askFor = ScannerInput.askInteger();
                    coord = getCoord(askFor, coordinates);
                    if(coord == null) continue;
                    rTree.delete(coord.getMiddleX(), coord.getMiddleY(), rTree);
                    break;
                case 3:
                    rTree.visualize(rTree.root, 0);
                    break;
                default:
                    break outer;
            }

        }
    }

    private static Coordinate getCoord(int askFor, Coordinate[] coordinates) {
        for(Coordinate i : coordinates){
            if(i.getId().equals(askFor)) return i;
        }
        return null;
    }
}