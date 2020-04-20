package Model.AVL;

import Database.StoreObject;

public class AVLNode {
    private StoreObject object;
    private AVLNode R, L;
    private int autoBalanceFactor;

    public AVLNode() {
        R = null;
        L = null;
        autoBalanceFactor = 0;
        object = null;
    }

    public AVLNode(StoreObject object) {
        this.object = object;
        R = null;
        L = null;
        autoBalanceFactor = 0;
    }

    public AVLNode(AVLNode node){

        this.R = node.getR();
        this.L = node.getL();
        this.autoBalanceFactor = node.getAutoBalanceFactor();
        this.object = node.getObject();

    }

    public StoreObject getObject() {
        return object;
    }

    public void setObject(StoreObject object) {
        this.object = object;
    }

    public AVLNode getR() {
        return R;
    }

    public void setR(StoreObject object) {
        AVLNode r = new AVLNode();
        r.setObject(object);
        r.setAutoBalanceFactor(0);


        R = r;

    }

    public AVLNode getL() {
        return L;
    }

    public void setL(StoreObject object) {
        AVLNode l = new AVLNode();
        l.setObject(object);
        l.setAutoBalanceFactor(0);

        L = l;

    }

    public void setNodeL(AVLNode node){
        AVLNode l = new AVLNode();
        l.setObject(node.getObject());
        try{
            l.setNodeL(node.getL());
        } catch (NullPointerException ignore){}
        try{
            l.setNodeR(node.getR());
        } catch (NullPointerException ignore){}
        l.setAutoBalanceFactor(node.getAutoBalanceFactor());

        this.L = l;
    }

    public void setNodeR(AVLNode node) {
        AVLNode r = new AVLNode();
        r.setObject(node.getObject());
        try{
            r.setNodeL(node.getL());
        } catch (NullPointerException ignore){}
        try{
            r.setNodeR(node.getR());
        } catch (NullPointerException ignore){}
        r.setAutoBalanceFactor(node.getAutoBalanceFactor());

        this.R = r;
    }


    public int getAutoBalanceFactor() {
        return autoBalanceFactor;
    }

    public void setAutoBalanceFactor(int autoBalanceFactor) {
        this.autoBalanceFactor = autoBalanceFactor;
    }

    public int getMaximumHeight() {
        if(R == null && L == null){
            return 1;
        } else {
            int rightMaximumHeight = 0;
            int leftMaximumHeight = 0;
            try{
                rightMaximumHeight = this.R.getMaximumHeight();
            } catch (NullPointerException ignore){}
            try {
                leftMaximumHeight = this.L.getMaximumHeight();
            } catch (NullPointerException ignore){}
            return Math.max(rightMaximumHeight, leftMaximumHeight) + 1;
        }
    }

    public boolean hasLeftChild() {
        return L != null;
    }

    public boolean hasRightChild() {
        return R != null;
    }

    public boolean isLeaf() {
        return L == null && R == null;
    }
}
