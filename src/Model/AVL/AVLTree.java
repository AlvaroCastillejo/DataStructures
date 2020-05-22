package Model.AVL;

import Database.Coordinate;
import Database.StoreObject;
import Utils.JsonUtils;
import Utils.Output;
import Utils.ScannerInput;
import Utils.TreeArrayRepresentation;

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
        //If the tree is empty insert the object directly to the root.
        if(tree.getRoot() == null){
            root = new AVLNode();
            root.setObject(object.getObject());
            root.setAutoBalanceFactor(0);
            return;
        }
        //If the tree is not empty look if the object to insert is bigger or lesser than the current node.
        if(object.getObject().getPrice() < tree.getRoot().getObject().getPrice()){
            //If the current node is a leaf node then insert.
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

        //Calculate the balance as we return to the root.
        tree.root.setAutoBalanceFactor(tree.calculateBalance());
        balance(tree);
    }

    /**
     * Recursive method that search for a given object. Then delete it.
     * @param tree The tree/subtree you want to search the object in.
     * @param object The object you want to find.
     * @return The found object;
     */
    public StoreObject searchFor(AVLTree tree, AVLNode object){
        StoreObject toReturn = null;
        try{
            //If object is found in the current level then deleted.
            if(object.getObject().getPrice().equals(tree.getRoot().getObject().getPrice())){
                toReturn = tree.getRoot().getObject();
                deleteElement(tree, object);
            } else {
                //Go to the left or the right.
                if(object.getObject().getPrice() < tree.getRoot().getObject().getPrice()){
                    return searchFor(new AVLTree(tree.getRoot().getL()), object);
                } else {
                    return searchFor(new AVLTree(tree.getRoot().getR()), object);
                }
            }
        } catch (NullPointerException ignore){}

        //Re-balance the tree
        try{
            tree.root.setAutoBalanceFactor(tree.calculateBalance());
            balance(tree);
        } catch (NullPointerException ignore){}

        return toReturn;
    }

    /**
     * Recursive method that search and deletes the desired element.
     * @param tree The tree that contains the object you want to delete.
     * @param object The object that represents the object in the tree that you want to delete.
     */
    private void deleteElement(AVLTree tree, AVLNode object) {
        StoreObject toReturn = new StoreObject(tree.getRoot().getObject().getName());
        //If we get to the leaf we just delete it.
        if(tree.root.isLeaf()){
            tree.root.setObject(null);
            return;
        }
        //If the node to delete has no left child it is replaced with the right child.
        if(!tree.root.hasLeftChild()){
            toReturn = new StoreObject(tree.getRoot().getObject().getName());
            tree.root = new AVLNode(tree.root.getR());
            return;
        }
        //If none of the above options. Take the first element in the post-order.
        AVLNode newNode = (postOrder(tree.getRoot().getL()));
        if(newNode.hasLeftChild()){
            tree.root.getL().setNodeR(newNode.getL());
        } else {
            deletePostOrder(tree.getRoot().getL());
        }
        tree.root.setObject(newNode.getObject());

        //Re-balance the tree.
        tree.root.getL().setAutoBalanceFactor(new AVLTree(tree.root.getL()).calculateBalance());
        balance(new AVLTree(tree.root.getL()));
    }

    /**
     * Searches for the first object in post-order and deletes it.
     * @param root The root node of the tree/subject.
     */
    private void deletePostOrder(AVLNode root) {
        if(root.getR().getR() == null){
            root.deleteR();
        } else {
            deletePostOrder(root.getR());
        }
    }

    /**
     * Searches for the first object in post-order.
     * @param root The root of the tree/subtree that you want to get the object from.
     * @return The object found.
     */
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

    /**
     * Prints the tree in a pre-order.
     * @param node The node from where you want to print.
     * @param level The current node in the given tree.
     */
    public static void preorderVisualization(AVLNode node, int level) {
        if(node == null){
            return;
        }
        if(node.isLeaf()){
            String tag = "";
            //Depending on the level of the tree we are, the representation will be more indented o less.
            for(int i = 0; i < level; i++){
                tag = tag.concat("\t");
            }
            //Print in color green because we are on a leaf node.
            Output.print(tag.concat(String.valueOf(node.getObject().getPrice())), "green");
        } else {
            String levelTag = "";
            for(int i = 0; i < level; i++){
                levelTag = levelTag.concat("\t");
            }
            levelTag = levelTag.concat(String.valueOf(node.getObject().getPrice()));
            Output.print(levelTag, "yellow");
            preorderVisualization(node.getL(),level+1);
            level--;
            preorderVisualization(node.getR(), level+1);
        }
    }

    /**
     * Searches for the first object in pre-order and deletes it.
     * @param node The root node of the tree/subject.
     */
    private static void preorderToDeleteRemaining(AVLNode node) {
        if(node.hasLeftChild()){
            if(node.getL().getObject() == null){
                node.deleteL();
            } else {
                preorderToDeleteRemaining(node.getL());
            }
        }

        if(node.hasRightChild()){
            if(node.getR().getObject() == null){
                node.deleteR();
            } else {
                preorderToDeleteRemaining(node.getR());
            }
        }
    }

    public static void main(String[] args) {
        StoreObject[] a = JsonUtils.getStoreObjects("dataset_ObjectS");

        AVLTree tree = new AVLTree();
        for(StoreObject object : a){
            tree.insert(tree, new AVLNode(object));
        }

        TreeArrayRepresentation arrayTree = new TreeArrayRepresentation();


        StoreObject found = null;

        outer:
        while (true){
            Output.print("------------------------\n\t1- Search\n\t2- Plot tree\n\t3. Exit\nOption:", "white");
            int option = ScannerInput.askInteger();
            switch (option){
                case 1:
                    Output.print("search for: ", "white");
                    int price = ScannerInput.askInteger();
                    found = tree.searchFor(tree, new AVLNode(new StoreObject(price)));
                    if(found!=null){
                        Output.print("Your item: " + found.getName(), "green");
                        preorderToDeleteRemaining(tree.getRoot());
                    } else {
                        Output.print("Your item doesn't exist", "red");
                    }
                    break;
                case 2:
                    preorderVisualization(tree.getRoot(), 0);
                    break;
                default:
                    break outer;
            }

        }
    }
}
