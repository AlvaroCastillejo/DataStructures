package Model.AVL;

import Database.StoreObject;
import Utils.JsonUtils;
import Utils.LinkedList.LinkedListCustom;

public class AVLTree {

    private AVLNode root;

    /**
     * Empty constructor for the tree.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * Creates a tree given a node. It represents a subtree.
     * @param root The given node.
     */
    public AVLTree(AVLNode root){
        this.root = root;
    }

    /**
     * Insertion method.
     * @param tree The tree/subtree you want to insert the given node to.
     * @param object The node you want to insert.
     */
    public void insert(AVLTree tree, AVLNode object) {
        if(tree.getRoot() == null){
            root = new AVLNode();
            root.setObject(object.getObject());
            root.setAutoBalanceFactor(0);
            return;
        }

        if(object.getObject().getPrice() < tree.getRoot().getObject().getPrice()){
            if(tree.getRoot().getL() == null){
                tree.root.setL(object.getObject());
            } else {
                insert(new AVLTree(tree.root.getL()), object);
            }
        } else {
            if(tree.getRoot().getR() == null){
                tree.root.setR(object.getObject());
            } else {
                insert(new AVLTree(tree.root.getR()), object);
            }
        }

        tree.root.setAutoBalanceFactor(tree.calculateBalance());
        balance(tree);
    }

    public void delete(AVLTree tree, AVLNode object){
        if(object.getObject().getPrice().equals(tree.getRoot().getObject().getPrice())){
            deleteElement(tree, object);
        } else {
           if(object.getObject().getPrice() < tree.getRoot().getObject().getPrice()){
               delete(new AVLTree(tree.getRoot().getL()), object);
           } else {
               delete(new AVLTree(tree.getRoot().getR()), object);
           }
        }

        tree.root.setAutoBalanceFactor(tree.calculateBalance());
        balance(tree);
    }

    private void deleteElement(AVLTree tree, AVLNode object) {
        if(tree.root.isLeaf()){
            tree.root = null;
            return;
        }
        if(!tree.root.hasLeftChild()){
            tree.root = new AVLNode(tree.root.getR());
            return;
        }
        AVLNode newNode = (postOrder(tree.getRoot().getL()));
        tree.root.setObject(newNode.getObject());
        try {
            tree.root.setNodeR(newNode.getR());
        } catch (NullPointerException ignore){}
        try{
            tree.root.setNodeL(newNode.getL());
        } catch (NullPointerException ignore){}
    }

    public AVLNode postOrder(AVLNode root) {
        AVLNode auxNode = (root);
        for(int i = 0; auxNode.getR() != null; i++){
            auxNode = (auxNode.getR());
        }
        return auxNode;
    }
    /**
     * Rotates the tree/subtree if needed. This maintains the tree balanced.
     * @param tree The tree to balance.
     */
    private void balance(AVLTree tree) {
        int autoBalanceFactor = tree.getRoot().getAutoBalanceFactor();

        if(autoBalanceFactor < -1){
            if((tree.root.getL().getAutoBalanceFactor()*autoBalanceFactor)<0){
                //Aplicar rotación de dos nodos hacia la izquierda
                tree.rotateMiniLeft();
            }
            tree.rotateRight();
            tree.root.setAutoBalanceFactor(tree.calculateBalance());
        }
        if(autoBalanceFactor > 1){
            if((tree.root.getR().getAutoBalanceFactor()*autoBalanceFactor)<0){
                //Aplicar rotacion de dos nodos hacia la derecha
                tree.rotateMiniRight();
            }
            tree.rotateLeft();
            tree.root.setAutoBalanceFactor(tree.calculateBalance());
        }
    }

    /**
     * Rotates two nodes to the right in the following case
     *
     *       200                 200
     *      /   \               /   \
     *    100   500           100   500
     *             \    ->            \
     *             700               600
     *            /                    \
     *          600                   700
     *
     * After this method is called it's always needed a full rotation to the left.
     */
    private void rotateMiniRight() {
        AVLNode aux = new AVLNode(this.root.getR());
        this.root.setR(this.root.getR().getL().getObject());
        this.root.getR().setR(aux.getObject());
    }

    /**
     * Rotates two nodes to the left in the following case
     *
     *       200                200
     *      /   \              /   \
     *    100   500          100   500
     *         /        ->        /
     *       400                450
     *         \               /
     *         450           400
     *
     * After this method is called it's always needed a full rotation to the right.
     */
    private void rotateMiniLeft() {
        AVLNode aux = new AVLNode(this.root.getL());
        this.root.setL(this.root.getL().getR().getObject());
        this.root.getL().setL(aux.getObject());
    }

    /**
     * Rotates the tree to the left.
     *       500
     *          \               600
     *         600      ->     /   \
     *           \           500   700
     *          700
     */
    private void rotateLeft() {
        AVLNode aux = new AVLNode();
        aux.setObject(this.root.getObject());
        try{
            aux.setNodeL(this.root.getL());
        } catch (NullPointerException ignore){}
        try{
            //aux.setNodeR(this.root.getR());
        } catch (NullPointerException ignore){}
        aux.setAutoBalanceFactor(new AVLTree(aux).calculateBalance());


        AVLNode lostNode = new AVLNode();
        if(this.root.getR().hasLeftChild()){
            lostNode = new AVLNode(this.root.getR().getL());
        }

        AVLNode sameAt = new AVLNode();
        sameAt = this.root;
        sameAt.setObject(this.root.getR().getObject());
        sameAt.setAutoBalanceFactor(this.root.getR().getAutoBalanceFactor());
        sameAt.setNodeR(this.root.getR().getR());

        this.root = sameAt;

        if(lostNode.getObject() != null){
            insert(new AVLTree(aux), lostNode);
        }
        this.root.setNodeL(aux);
    }

    /**
     * Rotates the tree to the right.
     *
     *          500
     *         /               450
     *       450       ->     /  \
     *      /               400  500
     *    400
     *
     */
    private void rotateRight() {
        AVLNode aux = new AVLNode();
        aux.setObject(this.root.getObject());
        try{
            //aux.setNodeL(this.root.getL());
        } catch (NullPointerException ignore){}
        try{
            aux.setNodeR(this.root.getR());
        } catch (NullPointerException ignore){}
        aux.setAutoBalanceFactor(new AVLTree(aux).calculateBalance());

        AVLNode lostNode = new AVLNode();
        if(this.root.getL().hasRightChild()){
            lostNode = new AVLNode(this.root.getL().getR());
        }

        AVLNode sameAt = new AVLNode();
        sameAt = this.root;
        sameAt.setObject(this.root.getL().getObject());
        sameAt.setAutoBalanceFactor(this.root.getL().getAutoBalanceFactor());
        sameAt.setNodeL(this.root.getL().getL());
        this.root = sameAt;

        if(lostNode.getObject() != null){
            insert(new AVLTree(aux), lostNode);
        }

        this.root.setNodeR(aux);
    }

    /**
     * Calculates for the current root node its balance.
     * @return the balance obtained.
     */
    private int calculateBalance() {
        int rCount = 0;
        int lCount = 0;
        try{
            rCount = this.root.getR().getMaximumHeight();
        } catch (NullPointerException ignore){}
        try{
            lCount = this.root.getL().getMaximumHeight();
        } catch (NullPointerException ignore){}

        return rCount-lCount;
    }

    public AVLNode getRoot() {
        return root;
    }

    public static void visualization(AVLNode node) {

    }

    public static void main(String[] args) {
        StoreObject[] a = JsonUtils.getStoreObjects("dataset_ObjectS");

        AVLTree tree = new AVLTree();
        for(StoreObject object : a){
            tree.insert(tree, new AVLNode(object));
        }

        tree.delete(tree, new AVLNode(a[0]));

        visualization(tree.getRoot());
    }
}
