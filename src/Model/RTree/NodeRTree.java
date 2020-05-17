package Model.RTree;

import Database.Coordinate;
import Utils.LinkedList.LinkedListCustom;
import Utils.Logic;

public class NodeRTree {
    private Coordinate rectangle;
    private static int DEGREE = 0;
    private InnerNodeRTree[] innerNodes;

    public NodeRTree(int degree) {
        rectangle = new Coordinate(0, 0, 0, 0);
        DEGREE = degree;
        innerNodes = new InnerNodeRTree[degree+1];
    }

    public void addObject(Coordinate object) {
        for(int i = 0; i < DEGREE; i++){
            if(this.innerNodes[i] == null){
                this.innerNodes[i] = new InnerNodeRTree();
                this.innerNodes[i].setObject(object);
                recalculate();
                return;
            }
        }

        innerNodes[innerNodes.length-1] = new InnerNodeRTree();
        innerNodes[innerNodes.length-1].setObject(object);
        split();
        recalculateAfterSplit();
    }

    public void removeObject(Coordinate coordinate) {
        int i;
        for(i = 0; i < innerNodes.length; i++){
            if(innerNodes[i].getObject().getId().equals(coordinate.getId())){
                break;
            }
        }
        for(int j = i; j < innerNodes.length; j++){
            try {
                innerNodes[j] = innerNodes[j + 1];
            } catch (IndexOutOfBoundsException e){
                innerNodes[j] = null;
            }
        }
    }

    private void split() {
        LinkedListCustom<Coordinate> objectsToDistribute = new LinkedListCustom<>();
        for(int i = 0; i < innerNodes.length; i++){
            objectsToDistribute.add(innerNodes[i].getObject());
            innerNodes[i].setObject(null);
        }

        NodeRTree leftSon = new NodeRTree(DEGREE);
        NodeRTree rightSon = new NodeRTree(DEGREE);

        int[] moreDistant = get2MoreDistant(objectsToDistribute);

        leftSon.addObject((Coordinate) objectsToDistribute.get(moreDistant[0]));
        rightSon.addObject((Coordinate) objectsToDistribute.get(moreDistant[1]));

        objectsToDistribute.remove(moreDistant[0]);
        objectsToDistribute.remove(moreDistant[1]-1);

        for(int i = 0; i < objectsToDistribute.size(); i++){
            Coordinate aux = (Coordinate) objectsToDistribute.get(i);

            if(areaAddingCoordinate(aux, leftSon) > areaAddingCoordinate(aux, rightSon)){
                rightSon.addObject(aux);
            } else {
                leftSon.addObject(aux);
            }
        }

        this.innerNodes[0].setSon(leftSon);
        innerNodes[0].getSon().recalculate();
        this.innerNodes[1].setSon(rightSon);
        innerNodes[1].getSon().recalculate();
    }

    private double areaAddingCoordinate(Coordinate coordinate, NodeRTree i) {
        double areaOriginal = i.rectangle.getArea();
        i.addObject(coordinate);
        double area = i.rectangle.getArea();
        i.removeObject(coordinate);
        return area-areaOriginal;
    }

    private int[] get2MoreDistant(LinkedListCustom<Coordinate> objectsToDistribute) {
        double maxDistance = Double.MIN_VALUE;
        int indexA, indexB;
        indexA = 0;
        indexB = 0;
        for(int i = 0; i < objectsToDistribute.size(); i++){
            for(int j = 0; j < objectsToDistribute.size(); j++){
                if(((Coordinate) objectsToDistribute.get(i)).getId().equals(((Coordinate) objectsToDistribute.get(j)).getId())) continue;
                double distance = Logic.distanceBetween((Coordinate)objectsToDistribute.get(i), (Coordinate)objectsToDistribute.get(j));
                if(distance > maxDistance){
                    maxDistance = distance;
                    indexA = i;
                    indexB = j;
                }
            }
        }
        return new int[]{indexA, indexB};
    }


    public void recalculate() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for(InnerNodeRTree i : this.innerNodes){
            if(i == null) break;
            if(i.getObject().getX1() < minX) minX = i.getObject().getX1();
            if(i.getObject().getY1() < minY) minY = i.getObject().getY1();
            if(i.getObject().getX2() > maxX) maxX = i.getObject().getX2();
            if(i.getObject().getY2() > maxY) maxY = i.getObject().getY2();
        }

        rectangle = new Coordinate(minX, minY, maxX, maxY);

        int area = Math.abs(maxX-minX) * Math.abs(maxY-minY);
        this.rectangle.setArea(area);
    }

    public void recalculateAfterSplit() {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;

        if(this.innerNodes[0] == null){
            minX = 0;
            minY = 0;
            maxX = 0;
            maxY = 0;
        } else {
            minX = Integer.MAX_VALUE;
            minY = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            maxY = Integer.MIN_VALUE;
        }

        for(InnerNodeRTree i : this.innerNodes){
            if(i == null) break;
            if(i.getSon() == null) break;
            if(i.getSon().getRectangle().getX1() < minX) minX = i.getSon().getRectangle().getX1();
            if(i.getSon().getRectangle().getY1() < minY) minY = i.getSon().getRectangle().getY1();
            if(i.getSon().getRectangle().getX2() > maxX) maxX = i.getSon().getRectangle().getX2();
            if(i.getSon().getRectangle().getY2() > maxY) maxY = i.getSon().getRectangle().getY2();
        }

        rectangle = new Coordinate(minX, minY, maxX, maxY);

        int area = Math.abs(maxX-minX) * Math.abs(maxY-minY);
        this.rectangle.setArea(area);
    }

    public boolean isLeaf() {
        for(InnerNodeRTree i : innerNodes){
            if(i == null) continue;
            if(i.getSon() != null) return false;
        }
        return true;
    }

    public InnerNodeRTree[] getInnerNodes() {
        return innerNodes;
    }

    public void setInnerNodes(InnerNodeRTree[] innerNodes) {
        this.innerNodes = innerNodes;
    }

    public Coordinate getRectangle() {
        return rectangle;
    }
}
